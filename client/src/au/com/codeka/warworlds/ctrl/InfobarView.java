package au.com.codeka.warworlds.ctrl;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import au.com.codeka.Cash;
import au.com.codeka.warworlds.R;
import au.com.codeka.warworlds.api.RequestManager;
import au.com.codeka.warworlds.api.RequestManager.RequestManagerState;
import au.com.codeka.warworlds.eventbus.EventHandler;
import au.com.codeka.warworlds.model.Empire;
import au.com.codeka.warworlds.model.EmpireManager;
import au.com.codeka.warworlds.model.MyEmpire;

/**
 * The "infobar" control displays the current empire name, your cash level
 * and few other indicators that we want visible (almost) everywhere.
 */
public class InfobarView extends FrameLayout
                         implements RequestManager.RequestManagerStateChangedHandler {
    private Handler mHandler;
    private View mView;

    // The cash value we're currently displaying, so we can animate to the "real" value as it's
    // updated.
    private static float sCurrCash;
    private static float sRealCash;
    private static int sEmpireID; // if this changes, we update the cash instantly

    public InfobarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mView = inflate(context, R.layout.infobar_ctrl, null);
        this.addView(mView);
    }

    public void hideEmpireName() {
        mView.findViewById(R.id.empire_name).setVisibility(View.GONE);
    }

    private Runnable mUpdateCashRunnable = new Runnable() {
        @Override
        public void run() {
            boolean increasing = sRealCash > sCurrCash;
            float newCash = sCurrCash + (increasing ? 125f : -125f);
            if ((increasing && newCash > sRealCash) || (!increasing && newCash < sRealCash)) {
                newCash = sRealCash;
            }
            TextView cash = (TextView) mView.findViewById(R.id.cash);
            cash.setText(Cash.format(newCash));
            sCurrCash = newCash;

            if (sCurrCash != sRealCash) {
                mHandler.post(mUpdateCashRunnable);
            }
        }
    };

    @Override
    public void onStateChanged() {
        if (isInEditMode()) {
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ProgressBar working = (ProgressBar) mView.findViewById(R.id.working);

                RequestManagerState state = RequestManager.getCurrentState();
                // We will always have one busy request for the notification long-poll, but
                // that one doesn't really count.
                if (state.numInProgressRequests > 1) {
                    working.setVisibility(View.VISIBLE);
                } else {
                    working.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            return;
        }
        mHandler = new Handler();

        EmpireManager.eventBus.register(mEventHandler);
        RequestManager.addRequestManagerStateChangedHandler(this);

        refreshEmpire(EmpireManager.i.getEmpire());
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isInEditMode()) {
            return;
        }

        EmpireManager.eventBus.unregister(mEventHandler);
        RequestManager.removeRequestManagerStateChangedHandler(this);
    }

    private void refreshEmpire(Empire empire) {
        MyEmpire myEmpire = EmpireManager.i.getEmpire();
        if (empire != null && myEmpire != null && myEmpire.getID() == empire.getID()) {
            sRealCash = empire.getCash();
            if (sCurrCash == 0.0f || sEmpireID != myEmpire.getID()) {
                sCurrCash = sRealCash;
                sEmpireID = myEmpire.getID();
            } else if (sRealCash != sCurrCash) {
                mHandler.post(mUpdateCashRunnable);
            }
            TextView cash = (TextView) mView.findViewById(R.id.cash);
            cash.setText(Cash.format(sCurrCash));

            TextView empireName = (TextView) mView.findViewById(R.id.empire_name);
            empireName.setText(empire.getDisplayName());
        }

        // set up the initial state
        onStateChanged();
    }

    private Object mEventHandler = new Object() {
        @EventHandler
        public void onEmpireUpdated(Empire empire) {
            refreshEmpire(empire);
        }
    };
}

package au.com.codeka.warworlds.game.solarsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import au.com.codeka.common.model.BaseFleet;
import au.com.codeka.warworlds.ActivityBackgroundGenerator;
import au.com.codeka.warworlds.BaseActivity;
import au.com.codeka.warworlds.R;
import au.com.codeka.warworlds.ServerGreeter;
import au.com.codeka.warworlds.ServerGreeter.ServerGreeting;
import au.com.codeka.warworlds.StyledDialog;
import au.com.codeka.warworlds.WarWorldsActivity;
import au.com.codeka.warworlds.ctrl.PlanetDetailsView;
import au.com.codeka.warworlds.eventbus.EventHandler;
import au.com.codeka.warworlds.model.Colony;
import au.com.codeka.warworlds.model.EmpireManager;
import au.com.codeka.warworlds.model.MyEmpire;
import au.com.codeka.warworlds.model.Planet;
import au.com.codeka.warworlds.model.Star;
import au.com.codeka.warworlds.model.StarManager;

public class EmptyPlanetActivity extends BaseActivity {
    private Star mStar;
    private Planet mPlanet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.planet_empty);

        View rootView = findViewById(android.R.id.content);
        ActivityBackgroundGenerator.setBackground(rootView);

        Button colonizeBtn = (Button) findViewById(R.id.colonize_btn);
        colonizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onColonizeClick();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ServerGreeter.waitForHello(this, new ServerGreeter.HelloCompleteHandler() {
            @Override
            public void onHelloComplete(boolean success, ServerGreeting greeting) {
                if (!success) {
                    startActivity(new Intent(EmptyPlanetActivity.this, WarWorldsActivity.class));
                } else {
                    String starKey = getIntent().getExtras().getString("au.com.codeka.warworlds.StarKey");
                    StarManager.eventBus.register(mEventHandler);
                    Star star = StarManager.i.getStar(Integer.parseInt(starKey));
                    if (star != null) {
                        refresh(star);
                    }
                }
            }
        });
    }

    private Object mEventHandler = new Object() {
        @EventHandler
        public void onStarFetched(Star s) {
            if (mStar != null && !mStar.getKey().equals(s.getKey())) {
                return;
            }

            refresh(s);
        }
    };

    private void refresh(Star star) {
        int planetIndex = getIntent().getExtras().getInt("au.com.codeka.warworlds.PlanetIndex");

        mStar = star;
        mPlanet = (Planet) star.getPlanets()[planetIndex - 1];

        PlanetDetailsView planetDetails = (PlanetDetailsView) findViewById(R.id.planet_details);
        planetDetails.setup(mStar, mPlanet, null);
    }

    private void onColonizeClick() {
        MyEmpire empire = EmpireManager.i.getEmpire();

        // check that we have a colony ship (the server will check too, but this is easy)
        boolean hasColonyShip = false;
        for (BaseFleet fleet : mStar.getFleets()) {
            if (fleet.getEmpireKey() == null) {
                continue;
            }

            if (fleet.getEmpireKey().equals(empire.getKey())) {
                if (fleet.getDesignID().equals("colonyship")) { // TODO: hardcoded?
                    hasColonyShip = true;
                }
            }
        }

        if (!hasColonyShip) {
            // TODO: better errors...
            StyledDialog dialog = new StyledDialog.Builder(this)
                .setMessage("You don't have a colony ship around this star, so you cannot colonize this planet.")
                .setPositiveButton("OK", null)
                .create();
            dialog.show();
        }

        empire.colonize(mPlanet, new MyEmpire.ColonizeCompleteHandler() {
            @Override
            public void onColonizeComplete(Colony colony) {
                finish();
            }
        });
    }
}

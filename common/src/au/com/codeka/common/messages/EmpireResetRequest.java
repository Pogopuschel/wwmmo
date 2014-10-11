// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: ./messages.proto
package au.com.codeka.common.messages;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

/**
 *
 * A request to reset an empire.
 */
public final class EmpireResetRequest extends Message {

  @ProtoField(tag = 1)
  public final PurchaseInfo purchase_info;

  public EmpireResetRequest(PurchaseInfo purchase_info) {
    this.purchase_info = purchase_info;
  }

  private EmpireResetRequest(Builder builder) {
    this(builder.purchase_info);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof EmpireResetRequest)) return false;
    return equals(purchase_info, ((EmpireResetRequest) other).purchase_info);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    return result != 0 ? result : (hashCode = purchase_info != null ? purchase_info.hashCode() : 0);
  }

  public static final class Builder extends Message.Builder<EmpireResetRequest> {

    public PurchaseInfo purchase_info;

    public Builder() {
    }

    public Builder(EmpireResetRequest message) {
      super(message);
      if (message == null) return;
      this.purchase_info = message.purchase_info;
    }

    public Builder purchase_info(PurchaseInfo purchase_info) {
      this.purchase_info = purchase_info;
      return this;
    }

    @Override
    public EmpireResetRequest build() {
      return new EmpireResetRequest(this);
    }
  }
}

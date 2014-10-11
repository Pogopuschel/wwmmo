// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: ./messages.proto
package au.com.codeka.common.messages;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoEnum;
import com.squareup.wire.ProtoField;

import static com.squareup.wire.Message.Datatype.ENUM;
import static com.squareup.wire.Message.Datatype.FLOAT;
import static com.squareup.wire.Message.Datatype.INT32;
import static com.squareup.wire.Message.Datatype.INT64;
import static com.squareup.wire.Message.Datatype.STRING;

public final class CashAuditRecord extends Message {

  public static final Integer DEFAULT_ID = 0;
  public static final Integer DEFAULT_EMPIRE_ID = 0;
  public static final Reason DEFAULT_REASON = Reason.FleetMove;
  public static final Float DEFAULT_BEFORE_CASH = 0F;
  public static final Float DEFAULT_AFTER_CASH = 0F;
  public static final Long DEFAULT_TIME = 0L;
  public static final Integer DEFAULT_FLEET_ID = 0;
  public static final String DEFAULT_FLEET_DESIGN_ID = "";
  public static final Float DEFAULT_NUM_SHIPS = 0F;
  public static final Integer DEFAULT_STAR_ID = 0;
  public static final String DEFAULT_STAR_NAME = "";
  public static final Float DEFAULT_MOVE_DISTANCE = 0F;
  public static final String DEFAULT_BUILD_DESIGN_ID = "";
  public static final Integer DEFAULT_BUILD_COUNT = 0;
  public static final Float DEFAULT_ACCELERATE_AMOUNT = 0F;
  public static final String DEFAULT_ALLIANCE_NAME = "";

  @ProtoField(tag = 1, type = INT32)
  public final Integer id;

  @ProtoField(tag = 2, type = INT32)
  public final Integer empire_id;

  @ProtoField(tag = 3, type = ENUM)
  public final Reason reason;

  @ProtoField(tag = 4, type = FLOAT)
  public final Float before_cash;

  @ProtoField(tag = 5, type = FLOAT)
  public final Float after_cash;

  @ProtoField(tag = 16, type = INT64)
  public final Long time;

  /**
   * these will be filled out if it's a "FleetMove" operation
   */
  @ProtoField(tag = 6, type = INT32)
  public final Integer fleet_id;

  @ProtoField(tag = 7, type = STRING)
  public final String fleet_design_id;

  @ProtoField(tag = 8, type = FLOAT)
  public final Float num_ships;

  @ProtoField(tag = 9, type = INT32)
  public final Integer star_id;

  @ProtoField(tag = 10, type = STRING)
  public final String star_name;

  @ProtoField(tag = 11, type = FLOAT)
  public final Float move_distance;

  /**
   * these will be filled out if it's a "BuildAccelerate" operation
   */
  @ProtoField(tag = 12, type = STRING)
  public final String build_design_id;

  @ProtoField(tag = 13, type = INT32)
  public final Integer build_count;

  @ProtoField(tag = 14, type = FLOAT)
  public final Float accelerate_amount;

  /**
   * these will be filled out for the "CreateAlliance" operation
   */
  @ProtoField(tag = 15, type = STRING)
  public final String alliance_name;

  public CashAuditRecord(Integer id, Integer empire_id, Reason reason, Float before_cash, Float after_cash, Long time, Integer fleet_id, String fleet_design_id, Float num_ships, Integer star_id, String star_name, Float move_distance, String build_design_id, Integer build_count, Float accelerate_amount, String alliance_name) {
    this.id = id;
    this.empire_id = empire_id;
    this.reason = reason;
    this.before_cash = before_cash;
    this.after_cash = after_cash;
    this.time = time;
    this.fleet_id = fleet_id;
    this.fleet_design_id = fleet_design_id;
    this.num_ships = num_ships;
    this.star_id = star_id;
    this.star_name = star_name;
    this.move_distance = move_distance;
    this.build_design_id = build_design_id;
    this.build_count = build_count;
    this.accelerate_amount = accelerate_amount;
    this.alliance_name = alliance_name;
  }

  private CashAuditRecord(Builder builder) {
    this(builder.id, builder.empire_id, builder.reason, builder.before_cash, builder.after_cash, builder.time, builder.fleet_id, builder.fleet_design_id, builder.num_ships, builder.star_id, builder.star_name, builder.move_distance, builder.build_design_id, builder.build_count, builder.accelerate_amount, builder.alliance_name);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof CashAuditRecord)) return false;
    CashAuditRecord o = (CashAuditRecord) other;
    return equals(id, o.id)
        && equals(empire_id, o.empire_id)
        && equals(reason, o.reason)
        && equals(before_cash, o.before_cash)
        && equals(after_cash, o.after_cash)
        && equals(time, o.time)
        && equals(fleet_id, o.fleet_id)
        && equals(fleet_design_id, o.fleet_design_id)
        && equals(num_ships, o.num_ships)
        && equals(star_id, o.star_id)
        && equals(star_name, o.star_name)
        && equals(move_distance, o.move_distance)
        && equals(build_design_id, o.build_design_id)
        && equals(build_count, o.build_count)
        && equals(accelerate_amount, o.accelerate_amount)
        && equals(alliance_name, o.alliance_name);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = id != null ? id.hashCode() : 0;
      result = result * 37 + (empire_id != null ? empire_id.hashCode() : 0);
      result = result * 37 + (reason != null ? reason.hashCode() : 0);
      result = result * 37 + (before_cash != null ? before_cash.hashCode() : 0);
      result = result * 37 + (after_cash != null ? after_cash.hashCode() : 0);
      result = result * 37 + (time != null ? time.hashCode() : 0);
      result = result * 37 + (fleet_id != null ? fleet_id.hashCode() : 0);
      result = result * 37 + (fleet_design_id != null ? fleet_design_id.hashCode() : 0);
      result = result * 37 + (num_ships != null ? num_ships.hashCode() : 0);
      result = result * 37 + (star_id != null ? star_id.hashCode() : 0);
      result = result * 37 + (star_name != null ? star_name.hashCode() : 0);
      result = result * 37 + (move_distance != null ? move_distance.hashCode() : 0);
      result = result * 37 + (build_design_id != null ? build_design_id.hashCode() : 0);
      result = result * 37 + (build_count != null ? build_count.hashCode() : 0);
      result = result * 37 + (accelerate_amount != null ? accelerate_amount.hashCode() : 0);
      result = result * 37 + (alliance_name != null ? alliance_name.hashCode() : 0);
      hashCode = result;
    }
    return result;
  }

  public static final class Builder extends Message.Builder<CashAuditRecord> {

    public Integer id;
    public Integer empire_id;
    public Reason reason;
    public Float before_cash;
    public Float after_cash;
    public Long time;
    public Integer fleet_id;
    public String fleet_design_id;
    public Float num_ships;
    public Integer star_id;
    public String star_name;
    public Float move_distance;
    public String build_design_id;
    public Integer build_count;
    public Float accelerate_amount;
    public String alliance_name;

    public Builder() {
    }

    public Builder(CashAuditRecord message) {
      super(message);
      if (message == null) return;
      this.id = message.id;
      this.empire_id = message.empire_id;
      this.reason = message.reason;
      this.before_cash = message.before_cash;
      this.after_cash = message.after_cash;
      this.time = message.time;
      this.fleet_id = message.fleet_id;
      this.fleet_design_id = message.fleet_design_id;
      this.num_ships = message.num_ships;
      this.star_id = message.star_id;
      this.star_name = message.star_name;
      this.move_distance = message.move_distance;
      this.build_design_id = message.build_design_id;
      this.build_count = message.build_count;
      this.accelerate_amount = message.accelerate_amount;
      this.alliance_name = message.alliance_name;
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder empire_id(Integer empire_id) {
      this.empire_id = empire_id;
      return this;
    }

    public Builder reason(Reason reason) {
      this.reason = reason;
      return this;
    }

    public Builder before_cash(Float before_cash) {
      this.before_cash = before_cash;
      return this;
    }

    public Builder after_cash(Float after_cash) {
      this.after_cash = after_cash;
      return this;
    }

    public Builder time(Long time) {
      this.time = time;
      return this;
    }

    /**
     * these will be filled out if it's a "FleetMove" operation
     */
    public Builder fleet_id(Integer fleet_id) {
      this.fleet_id = fleet_id;
      return this;
    }

    public Builder fleet_design_id(String fleet_design_id) {
      this.fleet_design_id = fleet_design_id;
      return this;
    }

    public Builder num_ships(Float num_ships) {
      this.num_ships = num_ships;
      return this;
    }

    public Builder star_id(Integer star_id) {
      this.star_id = star_id;
      return this;
    }

    public Builder star_name(String star_name) {
      this.star_name = star_name;
      return this;
    }

    public Builder move_distance(Float move_distance) {
      this.move_distance = move_distance;
      return this;
    }

    /**
     * these will be filled out if it's a "BuildAccelerate" operation
     */
    public Builder build_design_id(String build_design_id) {
      this.build_design_id = build_design_id;
      return this;
    }

    public Builder build_count(Integer build_count) {
      this.build_count = build_count;
      return this;
    }

    public Builder accelerate_amount(Float accelerate_amount) {
      this.accelerate_amount = accelerate_amount;
      return this;
    }

    /**
     * these will be filled out for the "CreateAlliance" operation
     */
    public Builder alliance_name(String alliance_name) {
      this.alliance_name = alliance_name;
      return this;
    }

    @Override
    public CashAuditRecord build() {
      return new CashAuditRecord(this);
    }
  }

  public enum Reason
      implements ProtoEnum {
    FleetMove(0),
    BuildAccelerate(1),
    CollectFromColonies(2),
    CreateAlliance(3),
    AllianceDeposit(4),
    /**
     * deposited into alliance bank
     */
    AllianceWithdraw(5);

    private final int value;

    private Reason(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }
  }
}

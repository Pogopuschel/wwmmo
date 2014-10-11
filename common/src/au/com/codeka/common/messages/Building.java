// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: ./messages.proto
package au.com.codeka.common.messages;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

import static com.squareup.wire.Message.Datatype.INT32;
import static com.squareup.wire.Message.Datatype.STRING;

/**
 *
 * Represents a completed building in a colony.
 */
public final class Building extends Message {

  public static final String DEFAULT_KEY = "";
  public static final String DEFAULT_COLONY_KEY = "";
  public static final String DEFAULT_DESIGN_ID = "";
  public static final Integer DEFAULT_LEVEL = 0;
  public static final String DEFAULT_NOTES = "";

  /**
   * The unique key of this building.
   */
  @ProtoField(tag = 1, type = STRING)
  public final String key;

  /**
   * The key of the colony to which this building belongs.
   */
  @ProtoField(tag = 2, type = STRING)
  public final String colony_key;

  /**
   * The identifier of the design of this building (defined in data/buildings.xml)
   */
  @ProtoField(tag = 3, type = STRING)
  public final String design_id;

  /**
   * The level of this building
   */
  @ProtoField(tag = 4, type = INT32)
  public final Integer level;

  /**
   * not as useful for buildings, but notes the player can attach
   */
  @ProtoField(tag = 5, type = STRING)
  public final String notes;

  public Building(String key, String colony_key, String design_id, Integer level, String notes) {
    this.key = key;
    this.colony_key = colony_key;
    this.design_id = design_id;
    this.level = level;
    this.notes = notes;
  }

  private Building(Builder builder) {
    this(builder.key, builder.colony_key, builder.design_id, builder.level, builder.notes);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof Building)) return false;
    Building o = (Building) other;
    return equals(key, o.key)
        && equals(colony_key, o.colony_key)
        && equals(design_id, o.design_id)
        && equals(level, o.level)
        && equals(notes, o.notes);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = key != null ? key.hashCode() : 0;
      result = result * 37 + (colony_key != null ? colony_key.hashCode() : 0);
      result = result * 37 + (design_id != null ? design_id.hashCode() : 0);
      result = result * 37 + (level != null ? level.hashCode() : 0);
      result = result * 37 + (notes != null ? notes.hashCode() : 0);
      hashCode = result;
    }
    return result;
  }

  public static final class Builder extends Message.Builder<Building> {

    public String key;
    public String colony_key;
    public String design_id;
    public Integer level;
    public String notes;

    public Builder() {
    }

    public Builder(Building message) {
      super(message);
      if (message == null) return;
      this.key = message.key;
      this.colony_key = message.colony_key;
      this.design_id = message.design_id;
      this.level = message.level;
      this.notes = message.notes;
    }

    /**
     * The unique key of this building.
     */
    public Builder key(String key) {
      this.key = key;
      return this;
    }

    /**
     * The key of the colony to which this building belongs.
     */
    public Builder colony_key(String colony_key) {
      this.colony_key = colony_key;
      return this;
    }

    /**
     * The identifier of the design of this building (defined in data/buildings.xml)
     */
    public Builder design_id(String design_id) {
      this.design_id = design_id;
      return this;
    }

    /**
     * The level of this building
     */
    public Builder level(Integer level) {
      this.level = level;
      return this;
    }

    /**
     * not as useful for buildings, but notes the player can attach
     */
    public Builder notes(String notes) {
      this.notes = notes;
      return this;
    }

    @Override
    public Building build() {
      return new Building(this);
    }
  }
}

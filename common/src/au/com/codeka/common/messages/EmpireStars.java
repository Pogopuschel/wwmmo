// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: ./messages.proto
package au.com.codeka.common.messages;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

import static com.squareup.wire.Message.Datatype.INT32;
import static com.squareup.wire.Message.Label.REPEATED;

public final class EmpireStars extends Message {

  public static final List<EmpireStar> DEFAULT_STARS = Collections.emptyList();
  public static final Integer DEFAULT_TOTAL_STARS = 0;

  @ProtoField(tag = 1, label = REPEATED)
  public final List<EmpireStar> stars;

  @ProtoField(tag = 2, type = INT32)
  public final Integer total_stars;

  public EmpireStars(List<EmpireStar> stars, Integer total_stars) {
    this.stars = immutableCopyOf(stars);
    this.total_stars = total_stars;
  }

  private EmpireStars(Builder builder) {
    this(builder.stars, builder.total_stars);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof EmpireStars)) return false;
    EmpireStars o = (EmpireStars) other;
    return equals(stars, o.stars)
        && equals(total_stars, o.total_stars);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = stars != null ? stars.hashCode() : 1;
      result = result * 37 + (total_stars != null ? total_stars.hashCode() : 0);
      hashCode = result;
    }
    return result;
  }

  public static final class Builder extends Message.Builder<EmpireStars> {

    public List<EmpireStar> stars;
    public Integer total_stars;

    public Builder() {
    }

    public Builder(EmpireStars message) {
      super(message);
      if (message == null) return;
      this.stars = copyOf(message.stars);
      this.total_stars = message.total_stars;
    }

    public Builder stars(List<EmpireStar> stars) {
      this.stars = checkForNulls(stars);
      return this;
    }

    public Builder total_stars(Integer total_stars) {
      this.total_stars = total_stars;
      return this;
    }

    @Override
    public EmpireStars build() {
      return new EmpireStars(this);
    }
  }
}

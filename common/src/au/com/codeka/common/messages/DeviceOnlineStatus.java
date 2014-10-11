// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: ./messages.proto
package au.com.codeka.common.messages;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

import static com.squareup.wire.Message.Datatype.BOOL;

/**
 *
 * The "online" status of a device. We'll use this to update whether or not
 * to send chat messages via GCM (or wait until you poll) etc.
 */
public final class DeviceOnlineStatus extends Message {

  public static final Boolean DEFAULT_IS_ONLINE = false;

  @ProtoField(tag = 1, type = BOOL)
  public final Boolean is_online;

  public DeviceOnlineStatus(Boolean is_online) {
    this.is_online = is_online;
  }

  private DeviceOnlineStatus(Builder builder) {
    this(builder.is_online);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof DeviceOnlineStatus)) return false;
    return equals(is_online, ((DeviceOnlineStatus) other).is_online);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    return result != 0 ? result : (hashCode = is_online != null ? is_online.hashCode() : 0);
  }

  public static final class Builder extends Message.Builder<DeviceOnlineStatus> {

    public Boolean is_online;

    public Builder() {
    }

    public Builder(DeviceOnlineStatus message) {
      super(message);
      if (message == null) return;
      this.is_online = message.is_online;
    }

    public Builder is_online(Boolean is_online) {
      this.is_online = is_online;
      return this;
    }

    @Override
    public DeviceOnlineStatus build() {
      return new DeviceOnlineStatus(this);
    }
  }
}

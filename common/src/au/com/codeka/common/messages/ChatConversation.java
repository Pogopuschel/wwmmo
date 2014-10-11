// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: ./messages.proto
package au.com.codeka.common.messages;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

import static com.squareup.wire.Message.Datatype.INT32;
import static com.squareup.wire.Message.Label.REPEATED;

/**
 *
 * A conversation is a private chat between two or more participants.
 */
public final class ChatConversation extends Message {

  public static final Integer DEFAULT_ID = 0;
  public static final List<ChatConversationParticipant> DEFAULT_PARTICIPANTS = Collections.emptyList();

  @ProtoField(tag = 1, type = INT32)
  public final Integer id;

  @ProtoField(tag = 2, label = REPEATED)
  public final List<ChatConversationParticipant> participants;

  public ChatConversation(Integer id, List<ChatConversationParticipant> participants) {
    this.id = id;
    this.participants = immutableCopyOf(participants);
  }

  private ChatConversation(Builder builder) {
    this(builder.id, builder.participants);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof ChatConversation)) return false;
    ChatConversation o = (ChatConversation) other;
    return equals(id, o.id)
        && equals(participants, o.participants);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = id != null ? id.hashCode() : 0;
      result = result * 37 + (participants != null ? participants.hashCode() : 1);
      hashCode = result;
    }
    return result;
  }

  public static final class Builder extends Message.Builder<ChatConversation> {

    public Integer id;
    public List<ChatConversationParticipant> participants;

    public Builder() {
    }

    public Builder(ChatConversation message) {
      super(message);
      if (message == null) return;
      this.id = message.id;
      this.participants = copyOf(message.participants);
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder participants(List<ChatConversationParticipant> participants) {
      this.participants = checkForNulls(participants);
      return this;
    }

    @Override
    public ChatConversation build() {
      return new ChatConversation(this);
    }
  }
}
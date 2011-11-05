package javax.mail.event;

import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

public abstract class MessageCountAdapter implements MessageCountListener {

   public MessageCountAdapter() {}

   public void messagesAdded(MessageCountEvent var1) {}

   public void messagesRemoved(MessageCountEvent var1) {}
}

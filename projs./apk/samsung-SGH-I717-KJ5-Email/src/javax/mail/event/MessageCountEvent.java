package javax.mail.event;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.event.MailEvent;
import javax.mail.event.MessageCountListener;

public class MessageCountEvent extends MailEvent {

   public static final int ADDED = 1;
   public static final int REMOVED = 2;
   protected transient Message[] msgs;
   protected boolean removed;
   protected int type;


   public MessageCountEvent(Folder var1, int var2, boolean var3, Message[] var4) {
      super(var1);
      this.type = var2;
      this.removed = var3;
      this.msgs = var4;
   }

   public void dispatch(Object var1) {
      MessageCountListener var2 = (MessageCountListener)var1;
      switch(this.type) {
      case 1:
         var2.messagesAdded(this);
         return;
      case 2:
         var2.messagesRemoved(this);
         return;
      default:
      }
   }

   public Message[] getMessages() {
      return this.msgs;
   }

   public int getType() {
      return this.type;
   }

   public boolean isRemoved() {
      return this.removed;
   }
}

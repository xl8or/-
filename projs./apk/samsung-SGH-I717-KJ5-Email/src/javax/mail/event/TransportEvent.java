package javax.mail.event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.event.MailEvent;
import javax.mail.event.TransportListener;

public class TransportEvent extends MailEvent {

   public static final int MESSAGE_DELIVERED = 1;
   public static final int MESSAGE_NOT_DELIVERED = 2;
   public static final int MESSAGE_PARTIALLY_DELIVERED = 3;
   protected transient Address[] invalid;
   protected transient Message msg;
   protected int type;
   protected transient Address[] validSent;
   protected transient Address[] validUnsent;


   public TransportEvent(Transport var1, int var2, Address[] var3, Address[] var4, Address[] var5, Message var6) {
      super(var1);
      this.type = var2;
      this.validSent = var3;
      this.validUnsent = var4;
      this.invalid = var5;
      this.msg = var6;
   }

   public void dispatch(Object var1) {
      TransportListener var2 = (TransportListener)var1;
      switch(this.type) {
      case 1:
         var2.messageDelivered(this);
         return;
      case 2:
         var2.messageNotDelivered(this);
         return;
      case 3:
         var2.messagePartiallyDelivered(this);
         return;
      default:
      }
   }

   public Address[] getInvalidAddresses() {
      return this.invalid;
   }

   public Message getMessage() {
      return this.msg;
   }

   public int getType() {
      return this.type;
   }

   public Address[] getValidSentAddresses() {
      return this.validSent;
   }

   public Address[] getValidUnsentAddresses() {
      return this.validUnsent;
   }
}

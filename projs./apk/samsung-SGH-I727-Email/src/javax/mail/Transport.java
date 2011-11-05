package javax.mail;

import java.util.ArrayList;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

public abstract class Transport extends Service {

   private ArrayList transportListeners = null;


   public Transport(Session var1, URLName var2) {
      super(var1, var2);
   }

   private static void doSend(Message param0, Address[] param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public static void send(Message var0) throws MessagingException {
      var0.saveChanges();
      Address[] var1 = var0.getAllRecipients();
      doSend(var0, var1);
   }

   public static void send(Message var0, Address[] var1) throws MessagingException {
      var0.saveChanges();
      doSend(var0, var1);
   }

   public void addTransportListener(TransportListener var1) {
      if(this.transportListeners == null) {
         ArrayList var2 = new ArrayList();
         this.transportListeners = var2;
      }

      ArrayList var3 = this.transportListeners;
      synchronized(var3) {
         this.transportListeners.add(var1);
      }
   }

   void fireMessageDelivered(TransportEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireMessageNotDelivered(TransportEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireMessagePartiallyDelivered(TransportEvent param1) {
      // $FF: Couldn't be decompiled
   }

   protected void notifyTransportListeners(int var1, Address[] var2, Address[] var3, Address[] var4, Message var5) {
      TransportEvent var12 = new TransportEvent(this, var1, var2, var3, var4, var5);
      switch(var1) {
      case 1:
         this.fireMessageDelivered(var12);
         return;
      case 2:
         this.fireMessageNotDelivered(var12);
         return;
      case 3:
         this.fireMessagePartiallyDelivered(var12);
         return;
      default:
      }
   }

   public void removeTransportListener(TransportListener var1) {
      if(this.transportListeners != null) {
         ArrayList var2 = this.transportListeners;
         synchronized(var2) {
            this.transportListeners.remove(var1);
         }
      }
   }

   public abstract void sendMessage(Message var1, Address[] var2) throws MessagingException;
}

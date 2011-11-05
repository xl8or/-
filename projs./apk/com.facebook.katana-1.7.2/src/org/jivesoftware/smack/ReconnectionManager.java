package org.jivesoftware.smack;

import java.util.Iterator;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.StreamError;

public class ReconnectionManager implements ConnectionListener {

   private Connection connection;
   boolean done;


   static {
      Connection.addConnectionCreationListener(new ReconnectionManager.1());
   }

   private ReconnectionManager(Connection var1) {
      this.done = (boolean)0;
      this.connection = var1;
   }

   // $FF: synthetic method
   ReconnectionManager(Connection var1, ReconnectionManager.1 var2) {
      this(var1);
   }

   private boolean isReconnectionAllowed() {
      boolean var1;
      if(!this.done && !this.connection.isConnected() && this.connection.isReconnectionAllowed()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void connectionClosed() {
      this.done = (boolean)1;
   }

   public void connectionClosedOnError(Exception var1) {
      this.done = (boolean)0;
      if(var1 instanceof XMPPException) {
         StreamError var2 = ((XMPPException)var1).getStreamError();
         if(var2 != null) {
            String var3 = var2.getCode();
            if("conflict".equals(var3)) {
               return;
            }
         }
      }

      if(this.isReconnectionAllowed()) {
         this.reconnect();
      }
   }

   protected void notifyAttemptToReconnectIn(int var1) {
      if(this.isReconnectionAllowed()) {
         Iterator var2 = this.connection.connectionListeners.iterator();

         while(var2.hasNext()) {
            ((ConnectionListener)var2.next()).reconnectingIn(var1);
         }

      }
   }

   protected void notifyReconnectionFailed(Exception var1) {
      if(this.isReconnectionAllowed()) {
         Iterator var2 = this.connection.connectionListeners.iterator();

         while(var2.hasNext()) {
            ((ConnectionListener)var2.next()).reconnectionFailed(var1);
         }

      }
   }

   protected void reconnect() {
      if(this.isReconnectionAllowed()) {
         ReconnectionManager.2 var1 = new ReconnectionManager.2();
         var1.setName("Smack Reconnection Manager");
         var1.setDaemon((boolean)1);
         var1.start();
      }
   }

   public void reconnectingIn(int var1) {}

   public void reconnectionFailed(Exception var1) {}

   public void reconnectionSuccessful() {}

   static class 1 implements ConnectionCreationListener {

      1() {}

      public void connectionCreated(Connection var1) {
         ReconnectionManager var2 = new ReconnectionManager(var1, (ReconnectionManager.1)null);
         var1.addConnectionListener(var2);
      }
   }

   class 2 extends Thread {

      private int attempts = 0;


      2() {}

      private int timeDelay() {
         short var1;
         if(this.attempts > 13) {
            var1 = 300;
         } else if(this.attempts > 7) {
            var1 = 60;
         } else {
            var1 = 10;
         }

         return var1;
      }

      public void run() {
         while(ReconnectionManager.this.isReconnectionAllowed()) {
            int var1 = this.timeDelay();

            while(ReconnectionManager.this.isReconnectionAllowed() && var1 > 0) {
               long var2 = 1000L;

               try {
                  Thread.sleep(var2);
                  var1 += -1;
                  ReconnectionManager.this.notifyAttemptToReconnectIn(var1);
               } catch (InterruptedException var9) {
                  var9.printStackTrace();
                  ReconnectionManager.this.notifyReconnectionFailed(var9);
                  var1 = var1;
               }
            }

            try {
               if(ReconnectionManager.this.isReconnectionAllowed()) {
                  ReconnectionManager.this.connection.connect();
               }
            } catch (XMPPException var8) {
               ReconnectionManager.this.notifyReconnectionFailed(var8);
            }
         }

      }
   }
}

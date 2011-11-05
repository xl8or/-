package com.facebook.katana.service.xmpp;

import android.os.Handler;
import android.os.SystemClock;
import com.facebook.katana.binding.WorkerThread;
import com.facebook.katana.service.xmpp.FBChatAuthMechanism;
import com.facebook.katana.service.xmpp.FacebookChatConnectionListener;
import com.facebook.katana.service.xmpp.FacebookChatSocketFactory;
import com.facebook.katana.service.xmpp.FacebookXmppPacket;
import com.facebook.katana.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class FacebookChatConnection {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String JABBER_RESOURCE = "fbandroid";
   public static final int KEEPALIVE_FREQ_MSEC = 20000;
   public static final int KEEPALIVE_TIMEOUT_MSEC = 5000;
   public static final int RECONNECTION_TIMEOUT_MSEC = 10000;
   private static final String TAG = "FacebookChatConnection";
   private static final AtomicInteger reconnectionAttempt;
   private final ConnectionCreationListener connectionInstaller;
   private final Runnable mCheckConnection;
   private final Runnable mConnectToServer;
   private XMPPConnection mConnection;
   private final ConnectionListener mConnectionListener;
   private final List<FacebookChatConnectionListener> mConnectionListeners;
   private final WorkerThread mConnectionThread;
   private volatile long mLastReceived;
   private final Handler mListenerHandler;
   private final PacketListener mPacketListener;
   private final Map<PacketListener, PacketFilter> mPacketListeners;
   private final boolean mRunSafe;
   private final Runnable mSendKeepAlive;
   private final String mSessionKey;
   private final String mSessionSecret;
   private FacebookChatConnection.CONNECTION_STATUS mState;
   private final BlockingQueue<Packet> pendingQueue;


   static {
      byte var0;
      if(!FacebookChatConnection.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      reconnectionAttempt = new AtomicInteger(0);
   }

   public FacebookChatConnection(String var1, String var2, boolean var3) {
      LinkedBlockingQueue var4 = new LinkedBlockingQueue();
      this.pendingQueue = var4;
      FacebookChatConnection.1 var5 = new FacebookChatConnection.1();
      this.connectionInstaller = var5;
      FacebookChatConnection.7 var6 = new FacebookChatConnection.7();
      this.mConnectionListener = var6;
      FacebookChatConnection.8 var7 = new FacebookChatConnection.8();
      this.mPacketListener = var7;
      FacebookChatConnection.9 var8 = new FacebookChatConnection.9();
      this.mCheckConnection = var8;
      FacebookChatConnection.10 var9 = new FacebookChatConnection.10();
      this.mSendKeepAlive = var9;
      FacebookChatConnection.11 var10 = new FacebookChatConnection.11();
      this.mConnectToServer = var10;
      this.mSessionKey = var1;
      this.mSessionSecret = var2;
      this.mRunSafe = var3;
      ConnectionConfiguration var11 = new ConnectionConfiguration("chat.facebook.com", 5222);
      FacebookChatSocketFactory var12 = new FacebookChatSocketFactory();
      var11.setSocketFactory(var12);
      var11.setRosterLoadedAtLogin((boolean)0);
      var11.setSendPresence((boolean)1);
      XMPPConnection var13 = new XMPPConnection(var11);
      this.mConnection = var13;
      Connection.addConnectionCreationListener(this.connectionInstaller);
      PacketTypeFilter var14 = new PacketTypeFilter(Packet.class);
      XMPPConnection var15 = this.mConnection;
      PacketListener var16 = this.mPacketListener;
      var15.addPacketListener(var16, var14);
      WorkerThread var17 = new WorkerThread();
      this.mConnectionThread = var17;
      ArrayList var18 = new ArrayList();
      this.mConnectionListeners = var18;
      HashMap var19 = new HashMap();
      this.mPacketListeners = var19;
      Handler var20 = new Handler();
      this.mListenerHandler = var20;
      FacebookChatConnection.CONNECTION_STATUS var21 = FacebookChatConnection.CONNECTION_STATUS.NEW;
      this.mState = var21;
      SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM", FBChatAuthMechanism.class);
      SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);
   }

   private void announceConnectionEstablished() {
      Handler var1 = this.mListenerHandler;
      FacebookChatConnection.4 var2 = new FacebookChatConnection.4();
      var1.post(var2);
   }

   private void announceConnectionPaused() {
      Handler var1 = this.mListenerHandler;
      FacebookChatConnection.5 var2 = new FacebookChatConnection.5();
      var1.post(var2);
   }

   private void announceConnectionStopped() {
      Handler var1 = this.mListenerHandler;
      FacebookChatConnection.6 var2 = new FacebookChatConnection.6();
      var1.post(var2);
      Handler var4 = this.mConnectionThread.getThreadHandler();
      Runnable var5 = this.mSendKeepAlive;
      var4.removeCallbacks(var5);
      Handler var6 = this.mConnectionThread.getThreadHandler();
      Runnable var7 = this.mCheckConnection;
      var6.removeCallbacks(var7);
   }

   private void verifyAlive() {
      if(!$assertionsDisabled) {
         FacebookChatConnection.CONNECTION_STATUS var1 = this.mState;
         FacebookChatConnection.CONNECTION_STATUS var2 = FacebookChatConnection.CONNECTION_STATUS.DISCONNECTED;
         if(var1 == var2) {
            throw new AssertionError();
         }
      }
   }

   public void addConnectionListener(FacebookChatConnectionListener var1) {
      synchronized(this){}

      try {
         this.verifyAlive();
         this.mConnectionListeners.add(var1);
      } finally {
         ;
      }

   }

   public void addPacketListener(PacketListener var1, PacketFilter var2) {
      this.verifyAlive();
      this.mPacketListeners.put(var1, var2);
   }

   public void connect() {
      this.verifyAlive();
      FacebookChatConnection.CONNECTION_STATUS var1 = this.mState;
      FacebookChatConnection.CONNECTION_STATUS var2 = FacebookChatConnection.CONNECTION_STATUS.NEW;
      if(var1 == var2) {
         FacebookChatConnection.CONNECTION_STATUS var3 = FacebookChatConnection.CONNECTION_STATUS.CONNECTING;
         this.mState = var3;
         reconnectionAttempt.set(0);
         Handler var4 = this.mConnectionThread.getThreadHandler();
         Runnable var5 = this.mConnectToServer;
         var4.post(var5);
      } else {
         FacebookChatConnection.CONNECTION_STATUS var7 = this.mState;
         FacebookChatConnection.CONNECTION_STATUS var8 = FacebookChatConnection.CONNECTION_STATUS.CONNECTING;
         if(var7 == var8) {
            reconnectionAttempt.set(0);
         }
      }
   }

   public void disconnect() {
      this.verifyAlive();
      XMPPConnection var1 = this.mConnection;
      PacketListener var2 = this.mPacketListener;
      var1.removePacketListener(var2);
      XMPPConnection var3 = this.mConnection;
      ConnectionListener var4 = this.mConnectionListener;
      var3.removeConnectionListener(var4);
      Connection.removeConnectionCreationListener(this.connectionInstaller);
      this.mConnectionListeners.clear();
      this.mPacketListeners.clear();
      FacebookChatConnection.CONNECTION_STATUS var5 = FacebookChatConnection.CONNECTION_STATUS.DISCONNECTED;
      this.mState = var5;
      Handler var6 = this.mConnectionThread.getThreadHandler();
      Runnable var7 = this.mSendKeepAlive;
      var6.removeCallbacks(var7);
      Handler var8 = this.mConnectionThread.getThreadHandler();
      Runnable var9 = this.mCheckConnection;
      var8.removeCallbacks(var9);
      Handler var10 = this.mConnectionThread.getThreadHandler();
      Runnable var11 = this.mConnectToServer;
      var10.removeCallbacks(var11);
      Handler var12 = this.mConnectionThread.getThreadHandler();
      FacebookChatConnection.2 var13 = new FacebookChatConnection.2();
      var12.post(var13);
   }

   public Handler getWorkerHandler() {
      Handler var1;
      if(this.mConnectionThread != null) {
         var1 = this.mConnectionThread.getThreadHandler();
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean isConnected() {
      FacebookChatConnection.CONNECTION_STATUS var1 = this.mState;
      FacebookChatConnection.CONNECTION_STATUS var2 = FacebookChatConnection.CONNECTION_STATUS.CONNECTED;
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void removeConnectionListener(FacebookChatConnectionListener var1) {
      synchronized(this){}

      try {
         this.verifyAlive();
         this.mConnectionListeners.remove(var1);
      } finally {
         ;
      }

   }

   public void removePacketListener(PacketListener var1) {
      this.verifyAlive();
      this.mPacketListeners.remove(var1);
   }

   public void sendPacket(Packet var1) {
      if(var1 != null) {
         Handler var2 = this.mConnectionThread.getThreadHandler();
         FacebookChatConnection.3 var3 = new FacebookChatConnection.3(var1);
         var2.post(var3);
      }
   }

   class 10 implements Runnable {

      private final FacebookXmppPacket keepAlive;


      10() {
         FacebookXmppPacket.PacketType var2 = FacebookXmppPacket.PacketType.CONNECT_TEST;
         FacebookXmppPacket var3 = new FacebookXmppPacket(var2);
         this.keepAlive = var3;
      }

      public void run() {
         long var1 = SystemClock.uptimeMillis();
         if(FacebookChatConnection.this.mConnection.isConnected() && FacebookChatConnection.this.mConnection.isAuthenticated() && FacebookChatConnection.this.mLastReceived + 5000L < var1) {
            FacebookChatConnection var3 = FacebookChatConnection.this;
            FacebookXmppPacket var4 = this.keepAlive;
            var3.sendPacket(var4);
         }

         Handler var5 = FacebookChatConnection.this.mConnectionThread.getThreadHandler();
         Runnable var6 = FacebookChatConnection.this.mSendKeepAlive;
         if(var5.postDelayed(var6, 20000L)) {
            Handler var7 = FacebookChatConnection.this.mConnectionThread.getThreadHandler();
            Runnable var8 = FacebookChatConnection.this.mCheckConnection;
            var7.postDelayed(var8, 5000L);
         }
      }
   }

   class 11 implements Runnable {

      11() {}

      private void doConnect() {
         FacebookChatConnection.CONNECTION_STATUS var1 = FacebookChatConnection.this.mState;
         FacebookChatConnection.CONNECTION_STATUS var2 = FacebookChatConnection.CONNECTION_STATUS.DISCONNECTED;
         if(var1 != var2) {
            FacebookChatConnection.CONNECTION_STATUS var3 = FacebookChatConnection.this.mState;
            FacebookChatConnection.CONNECTION_STATUS var4 = FacebookChatConnection.CONNECTION_STATUS.CONNECTED;
            if(var3 != var4) {
               try {
                  if(!FacebookChatConnection.this.mConnection.isConnected()) {
                     FacebookChatConnection.this.mConnection.connect();
                  }

                  if(!FacebookChatConnection.this.mConnection.isAuthenticated()) {
                     XMPPConnection var5 = FacebookChatConnection.this.mConnection;
                     String var6 = FacebookChatConnection.this.mSessionKey;
                     String var7 = FacebookChatConnection.this.mSessionSecret;
                     var5.login(var6, var7, "fbandroid");
                  }

                  FacebookChatConnection.this.announceConnectionEstablished();
                  Handler var8 = FacebookChatConnection.this.mConnectionThread.getThreadHandler();
                  Runnable var9 = FacebookChatConnection.this.mSendKeepAlive;
                  var8.postDelayed(var9, 20000L);
                  this.sendPendingPackets();
               } catch (XMPPException var21) {
                  FacebookChatConnection.this.announceConnectionStopped();
                  Handler var12 = FacebookChatConnection.this.mConnectionThread.getThreadHandler();
                  long var13 = (long)(FacebookChatConnection.reconnectionAttempt.incrementAndGet() * 10000);
                  var12.postDelayed(this, var13);
               } catch (IllegalStateException var22) {
                  FacebookChatConnection.this.announceConnectionStopped();
                  Handler var17 = FacebookChatConnection.this.mConnectionThread.getThreadHandler();
                  long var18 = (long)(FacebookChatConnection.reconnectionAttempt.incrementAndGet() * 10000);
                  var17.postDelayed(this, var18);
               }
            }
         }
      }

      private void sendPendingPackets() {
         while(true) {
            Packet var1 = (Packet)FacebookChatConnection.this.pendingQueue.poll();
            if(var1 == null) {
               return;
            }

            FacebookChatConnection.this.mConnection.sendPacket(var1);
         }
      }

      public void run() {
         if(FacebookChatConnection.this.mRunSafe) {
            try {
               this.doConnect();
            } catch (Exception var2) {
               FacebookChatConnection.this.announceConnectionStopped();
               Log.d("FacebookChatConnection", "E X C E P T I O N", var2);
            }
         } else {
            this.doConnect();
         }
      }
   }

   private static enum CONNECTION_STATUS {

      // $FF: synthetic field
      private static final FacebookChatConnection.CONNECTION_STATUS[] $VALUES;
      CONNECTED("CONNECTED", 2),
      CONNECTING("CONNECTING", 1),
      DISCONNECTED("DISCONNECTED", 3),
      NEW("NEW", 0);


      static {
         FacebookChatConnection.CONNECTION_STATUS[] var0 = new FacebookChatConnection.CONNECTION_STATUS[4];
         FacebookChatConnection.CONNECTION_STATUS var1 = NEW;
         var0[0] = var1;
         FacebookChatConnection.CONNECTION_STATUS var2 = CONNECTING;
         var0[1] = var2;
         FacebookChatConnection.CONNECTION_STATUS var3 = CONNECTED;
         var0[2] = var3;
         FacebookChatConnection.CONNECTION_STATUS var4 = DISCONNECTED;
         var0[3] = var4;
         $VALUES = var0;
      }

      private CONNECTION_STATUS(String var1, int var2) {}
   }

   class 8 implements PacketListener {

      8() {}

      public void processPacket(Packet var1) {
         FacebookChatConnection var2 = FacebookChatConnection.this;
         long var3 = SystemClock.uptimeMillis();
         var2.mLastReceived = var3;
         FacebookChatConnection.this.announceConnectionEstablished();
         Handler var7 = FacebookChatConnection.this.mListenerHandler;
         FacebookChatConnection.8.1 var8 = new FacebookChatConnection.8.1(var1);
         var7.post(var8);
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final Packet val$p;


         1(Packet var2) {
            this.val$p = var2;
         }

         public void run() {
            Iterator var1 = FacebookChatConnection.this.mPacketListeners.keySet().iterator();

            while(var1.hasNext()) {
               PacketListener var2 = (PacketListener)var1.next();
               PacketFilter var3 = (PacketFilter)FacebookChatConnection.this.mPacketListeners.get(var2);
               Packet var4 = this.val$p;
               if(var3.accept(var4)) {
                  Packet var5 = this.val$p;
                  var2.processPacket(var5);
               }
            }

         }
      }
   }

   class 9 implements Runnable {

      9() {}

      public void run() {
         long var1 = SystemClock.uptimeMillis();
         long var3 = FacebookChatConnection.this.mLastReceived + 20000L + 5000L;
         if(var1 > var3) {
            FacebookChatConnection.this.announceConnectionPaused();
         }
      }
   }

   class 6 implements Runnable {

      6() {}

      public void run() {
         FacebookChatConnection.CONNECTION_STATUS var1 = FacebookChatConnection.this.mState;
         FacebookChatConnection.CONNECTION_STATUS var2 = FacebookChatConnection.CONNECTION_STATUS.DISCONNECTED;
         if(var1 != var2) {
            FacebookChatConnection var3 = FacebookChatConnection.this;
            FacebookChatConnection.CONNECTION_STATUS var4 = FacebookChatConnection.CONNECTION_STATUS.CONNECTING;
            var3.mState = var4;
         }

         Iterator var6 = FacebookChatConnection.this.mConnectionListeners.iterator();

         while(var6.hasNext()) {
            ((FacebookChatConnectionListener)var6.next()).onConnectionStopped();
         }

      }
   }

   class 7 implements ConnectionListener {

      7() {}

      public void connectionClosed() {}

      public void connectionClosedOnError(Exception var1) {
         FacebookChatConnection.this.announceConnectionStopped();
         Handler var2 = FacebookChatConnection.this.mConnectionThread.getThreadHandler();
         Runnable var3 = FacebookChatConnection.this.mConnectToServer;
         var2.post(var3);
      }

      public void reconnectingIn(int var1) {}

      public void reconnectionFailed(Exception var1) {}

      public void reconnectionSuccessful() {
         FacebookChatConnection.this.announceConnectionEstablished();
         FacebookChatConnection.reconnectionAttempt.set(0);
      }
   }

   class 5 implements Runnable {

      5() {}

      public void run() {
         FacebookChatConnection.CONNECTION_STATUS var1 = FacebookChatConnection.this.mState;
         FacebookChatConnection.CONNECTION_STATUS var2 = FacebookChatConnection.CONNECTION_STATUS.CONNECTED;
         if(var1 == var2) {
            FacebookChatConnection var3 = FacebookChatConnection.this;
            FacebookChatConnection.CONNECTION_STATUS var4 = FacebookChatConnection.CONNECTION_STATUS.CONNECTING;
            var3.mState = var4;
            Iterator var6 = FacebookChatConnection.this.mConnectionListeners.iterator();

            while(var6.hasNext()) {
               ((FacebookChatConnectionListener)var6.next()).onConnectionPaused();
            }

         }
      }
   }

   class 4 implements Runnable {

      4() {}

      public void run() {
         FacebookChatConnection.CONNECTION_STATUS var1 = FacebookChatConnection.this.mState;
         FacebookChatConnection.CONNECTION_STATUS var2 = FacebookChatConnection.CONNECTION_STATUS.CONNECTING;
         if(var1 == var2) {
            FacebookChatConnection var3 = FacebookChatConnection.this;
            FacebookChatConnection.CONNECTION_STATUS var4 = FacebookChatConnection.CONNECTION_STATUS.CONNECTED;
            var3.mState = var4;
            Iterator var6 = FacebookChatConnection.this.mConnectionListeners.iterator();

            while(var6.hasNext()) {
               ((FacebookChatConnectionListener)var6.next()).onConnectionEstablished();
            }

         }
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final Packet val$p;


      3(Packet var2) {
         this.val$p = var2;
      }

      public void run() {
         if(FacebookChatConnection.this.isConnected()) {
            try {
               XMPPConnection var1 = FacebookChatConnection.this.mConnection;
               Packet var2 = this.val$p;
               var1.sendPacket(var2);
            } catch (Exception var10) {
               BlockingQueue var4 = FacebookChatConnection.this.pendingQueue;
               Packet var5 = this.val$p;
               var4.offer(var5);
               var10.printStackTrace();
            }
         } else {
            BlockingQueue var7 = FacebookChatConnection.this.pendingQueue;
            Packet var8 = this.val$p;
            var7.offer(var8);
         }
      }
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         XMPPConnection var1 = FacebookChatConnection.this.mConnection;
         Presence.Type var2 = Presence.Type.unavailable;
         Presence var3 = new Presence(var2);
         var1.disconnect(var3);
         FacebookChatConnection.this.mConnectionThread.quit();
         XMPPConnection var4 = FacebookChatConnection.this.mConnection = null;
      }
   }

   class 1 implements ConnectionCreationListener {

      1() {}

      public void connectionCreated(Connection var1) {
         ConnectionListener var2 = FacebookChatConnection.this.mConnectionListener;
         var1.addConnectionListener(var2);
      }
   }
}

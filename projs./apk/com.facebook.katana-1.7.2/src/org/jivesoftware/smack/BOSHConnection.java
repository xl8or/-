package org.jivesoftware.smack;

import com.kenai.jbosh.BOSHClient;
import com.kenai.jbosh.BOSHClientConnEvent;
import com.kenai.jbosh.BOSHClientConnListener;
import com.kenai.jbosh.BOSHClientRequestListener;
import com.kenai.jbosh.BOSHClientResponseListener;
import com.kenai.jbosh.BOSHException;
import com.kenai.jbosh.BOSHMessageEvent;
import com.kenai.jbosh.BodyQName;
import com.kenai.jbosh.ComposableBody;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.BOSHConfiguration;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.NonSASLAuthentication;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterStorage;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

public class BOSHConnection extends Connection {

   public static final String BOSH_URI = "http://jabber.org/protocol/httpbind";
   public static final String XMPP_BOSH_NS = "urn:xmpp:xbosh";
   private boolean anonymous;
   protected String authID;
   private boolean authenticated;
   private BOSHClient client;
   private final BOSHConfiguration config;
   private boolean connected;
   private boolean done;
   private boolean isFirstInitialization;
   private ExecutorService listenerExecutor;
   private Thread readerConsumer;
   private PipedWriter readerPipe;
   private Roster roster;
   protected String sessionID;
   private String user;
   private boolean wasAuthenticated;


   public BOSHConnection(BOSHConfiguration var1) {
      super(var1);
      this.connected = (boolean)0;
      this.authenticated = (boolean)0;
      this.anonymous = (boolean)0;
      this.isFirstInitialization = (boolean)1;
      this.wasAuthenticated = (boolean)0;
      this.done = (boolean)0;
      this.authID = null;
      this.sessionID = null;
      this.user = null;
      this.roster = null;
      this.config = var1;
   }

   public BOSHConnection(boolean var1, String var2, int var3, String var4, String var5) {
      BOSHConfiguration var11 = new BOSHConfiguration(var1, var2, var3, var4, var5);
      super(var11);
      this.connected = (boolean)0;
      this.authenticated = (boolean)0;
      this.anonymous = (boolean)0;
      this.isFirstInitialization = (boolean)1;
      this.wasAuthenticated = (boolean)0;
      this.done = (boolean)0;
      this.authID = null;
      this.sessionID = null;
      this.user = null;
      this.roster = null;
      BOSHConfiguration var12 = (BOSHConfiguration)this.getConfiguration();
      this.config = var12;
   }

   // $FF: synthetic method
   static boolean access$302(BOSHConnection var0, boolean var1) {
      var0.connected = var1;
      return var1;
   }

   // $FF: synthetic method
   static boolean access$400(BOSHConnection var0) {
      return var0.isFirstInitialization;
   }

   // $FF: synthetic method
   static boolean access$402(BOSHConnection var0, boolean var1) {
      var0.isFirstInitialization = var1;
      return var1;
   }

   // $FF: synthetic method
   static boolean access$500(BOSHConnection var0) {
      return var0.wasAuthenticated;
   }

   // $FF: synthetic method
   static BOSHConfiguration access$600(BOSHConnection var0) {
      return var0.config;
   }

   private void setWasAuthenticated(boolean var1) {
      if(!this.wasAuthenticated) {
         this.wasAuthenticated = var1;
      }
   }

   public void connect() throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   public void disconnect(Presence var1) {
      if(this.connected) {
         this.shutdown(var1);
         if(this.roster != null) {
            this.roster.cleanup();
            this.roster = null;
         }

         this.sendListeners.clear();
         this.recvListeners.clear();
         this.collectors.clear();
         this.interceptors.clear();
         this.wasAuthenticated = (boolean)0;
         this.isFirstInitialization = (boolean)1;
         Iterator var2 = this.getConnectionListeners().iterator();

         while(var2.hasNext()) {
            ConnectionListener var4 = (ConnectionListener)var2.next();

            try {
               var4.connectionClosed();
            } catch (Exception var3) {
               var3.printStackTrace();
            }
         }

      }
   }

   public String getConnectionID() {
      String var1;
      if(!this.connected) {
         var1 = null;
      } else if(this.authID != null) {
         var1 = this.authID;
      } else {
         var1 = this.sessionID;
      }

      return var1;
   }

   public Roster getRoster() {
      // $FF: Couldn't be decompiled
   }

   public String getUser() {
      return this.user;
   }

   protected void initDebugger() {
      BOSHConnection.2 var1 = new BOSHConnection.2();
      this.writer = var1;

      try {
         PipedWriter var2 = new PipedWriter();
         this.readerPipe = var2;
         PipedWriter var3 = this.readerPipe;
         PipedReader var4 = new PipedReader(var3);
         this.reader = var4;
      } catch (IOException var11) {
         ;
      }

      super.initDebugger();
      BOSHClient var5 = this.client;
      BOSHConnection.3 var6 = new BOSHConnection.3();
      var5.addBOSHClientResponseListener(var6);
      BOSHClient var7 = this.client;
      BOSHConnection.4 var8 = new BOSHConnection.4();
      var7.addBOSHClientRequestListener(var8);
      BOSHConnection.5 var9 = new BOSHConnection.5();
      this.readerConsumer = var9;
      this.readerConsumer.setDaemon((boolean)1);
      this.readerConsumer.start();
   }

   public boolean isAnonymous() {
      return this.anonymous;
   }

   public boolean isAuthenticated() {
      return this.authenticated;
   }

   public boolean isConnected() {
      return this.connected;
   }

   public boolean isSecureConnection() {
      return false;
   }

   public boolean isUsingCompression() {
      return false;
   }

   public void login(String var1, String var2, String var3) throws XMPPException {
      if(!this.isConnected()) {
         throw new IllegalStateException("Not connected to server.");
      } else if(this.authenticated) {
         throw new IllegalStateException("Already logged in to server.");
      } else {
         String var4 = var1.toLowerCase().trim();
         String var5;
         if(this.config.isSASLAuthenticationEnabled() && this.saslAuthentication.hasNonAnonymousAuthentication()) {
            if(var2 != null) {
               var5 = this.saslAuthentication.authenticate(var4, var2, var3);
            } else {
               SASLAuthentication var13 = this.saslAuthentication;
               CallbackHandler var14 = this.config.getCallbackHandler();
               var5 = var13.authenticate(var4, var3, var14);
            }
         } else {
            var5 = (new NonSASLAuthentication(this)).authenticate(var4, var2, var3);
         }

         if(var5 != null) {
            this.user = var5;
            BOSHConfiguration var6 = this.config;
            String var7 = StringUtils.parseServer(var5);
            var6.setServiceName(var7);
         } else {
            StringBuilder var15 = (new StringBuilder()).append(var4).append("@");
            String var16 = this.getServiceName();
            String var17 = var15.append(var16).toString();
            this.user = var17;
            if(var3 != null) {
               StringBuilder var18 = new StringBuilder();
               String var19 = this.user;
               String var20 = var18.append(var19).append("/").append(var3).toString();
               this.user = var20;
            }
         }

         if(this.roster == null) {
            if(this.rosterStorage == null) {
               Roster var8 = new Roster(this);
               this.roster = var8;
            } else {
               RosterStorage var21 = this.rosterStorage;
               Roster var22 = new Roster(this, var21);
               this.roster = var22;
            }
         }

         if(this.config.isRosterLoadedAtLogin()) {
            this.roster.reload();
         }

         if(this.config.isSendPresence()) {
            Presence.Type var9 = Presence.Type.available;
            Presence var10 = new Presence(var9);
            this.sendPacket(var10);
         }

         this.authenticated = (boolean)1;
         this.anonymous = (boolean)0;
         this.config.setLoginInfo(var4, var2, var3);
         if(this.config.isDebuggerEnabled()) {
            if(this.debugger != null) {
               SmackDebugger var11 = this.debugger;
               String var12 = this.user;
               var11.userHasLogged(var12);
            }
         }
      }
   }

   public void loginAnonymously() throws XMPPException {
      if(!this.isConnected()) {
         throw new IllegalStateException("Not connected to server.");
      } else if(this.authenticated) {
         throw new IllegalStateException("Already logged in to server.");
      } else {
         String var1;
         if(this.config.isSASLAuthenticationEnabled() && this.saslAuthentication.hasAnonymousAuthentication()) {
            var1 = this.saslAuthentication.authenticateAnonymously();
         } else {
            var1 = (new NonSASLAuthentication(this)).authenticateAnonymously();
         }

         this.user = var1;
         BOSHConfiguration var2 = this.config;
         String var3 = StringUtils.parseServer(var1);
         var2.setServiceName(var3);
         this.roster = null;
         if(this.config.isSendPresence()) {
            Presence.Type var4 = Presence.Type.available;
            Presence var5 = new Presence(var4);
            this.sendPacket(var5);
         }

         this.authenticated = (boolean)1;
         this.anonymous = (boolean)1;
         if(this.config.isDebuggerEnabled()) {
            if(this.debugger != null) {
               SmackDebugger var6 = this.debugger;
               String var7 = this.user;
               var6.userHasLogged(var7);
            }
         }
      }
   }

   protected void notifyConnectionError(Exception var1) {
      Presence.Type var2 = Presence.Type.unavailable;
      Presence var3 = new Presence(var2);
      this.shutdown(var3);
      var1.printStackTrace();
      Iterator var4 = this.getConnectionListeners().iterator();

      while(var4.hasNext()) {
         ConnectionListener var6 = (ConnectionListener)var4.next();

         try {
            var6.connectionClosedOnError(var1);
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

   }

   protected void processPacket(Packet var1) {
      if(var1 != null) {
         Iterator var2 = this.getPacketCollectors().iterator();

         while(var2.hasNext()) {
            ((PacketCollector)var2.next()).processPacket(var1);
         }

         ExecutorService var3 = this.listenerExecutor;
         BOSHConnection.ListenerNotification var4 = new BOSHConnection.ListenerNotification(var1);
         var3.submit(var4);
      }
   }

   protected void send(ComposableBody var1) throws BOSHException {
      if(!this.connected) {
         throw new IllegalStateException("Not connected to a server!");
      } else if(var1 == null) {
         throw new NullPointerException("Body mustn\'t be null!");
      } else {
         ComposableBody var5;
         if(this.sessionID != null) {
            ComposableBody.Builder var2 = var1.rebuild();
            BodyQName var3 = BodyQName.create("http://jabber.org/protocol/httpbind", "sid");
            String var4 = this.sessionID;
            var5 = var2.setAttribute(var3, var4).build();
         } else {
            var5 = var1;
         }

         this.client.send(var5);
      }
   }

   public void sendPacket(Packet var1) {
      if(!this.isConnected()) {
         throw new IllegalStateException("Not connected to server.");
      } else if(var1 == null) {
         throw new NullPointerException("Packet is null.");
      } else if(!this.done) {
         this.firePacketInterceptors(var1);

         try {
            ComposableBody.Builder var2 = ComposableBody.builder();
            String var3 = var1.toXML();
            ComposableBody var4 = var2.setPayloadXML(var3).build();
            this.send(var4);
         } catch (BOSHException var5) {
            var5.printStackTrace();
            return;
         }

         this.firePacketSendingListeners(var1);
      }
   }

   public void setRosterStorage(RosterStorage var1) throws IllegalStateException {
      if(this.roster != null) {
         throw new IllegalStateException("Roster is already initialized");
      } else {
         this.rosterStorage = var1;
      }
   }

   protected void shutdown(Presence var1) {
      boolean var2 = this.authenticated;
      this.setWasAuthenticated(var2);
      this.authID = null;
      this.sessionID = null;
      this.done = (boolean)1;
      this.authenticated = (boolean)0;
      this.connected = (boolean)0;
      this.isFirstInitialization = (boolean)0;

      try {
         BOSHClient var3 = this.client;
         ComposableBody.Builder var4 = ComposableBody.builder().setNamespaceDefinition("xmpp", "urn:xmpp:xbosh");
         String var5 = var1.toXML();
         ComposableBody var6 = var4.setPayloadXML(var5).build();
         var3.disconnect(var6);
         Thread.sleep(150L);
      } catch (Exception var14) {
         ;
      }

      if(this.readerPipe != null) {
         try {
            this.readerPipe.close();
         } catch (Throwable var13) {
            ;
         }

         this.reader = null;
      }

      if(this.reader != null) {
         try {
            this.reader.close();
         } catch (Throwable var12) {
            ;
         }

         this.reader = null;
      }

      if(this.writer != null) {
         try {
            this.writer.close();
         } catch (Throwable var11) {
            ;
         }

         this.writer = null;
      }

      if(this.listenerExecutor != null) {
         this.listenerExecutor.shutdown();
      }

      this.readerConsumer = null;
   }

   class 1 implements ThreadFactory {

      1() {}

      public Thread newThread(Runnable var1) {
         StringBuilder var2 = (new StringBuilder()).append("Smack Listener Processor (");
         int var3 = BOSHConnection.this.connectionCounterValue;
         String var4 = var2.append(var3).append(")").toString();
         Thread var5 = new Thread(var1, var4);
         var5.setDaemon((boolean)1);
         return var5;
      }
   }

   class 2 extends Writer {

      2() {}

      public void close() {}

      public void flush() {}

      public void write(char[] var1, int var2, int var3) {}
   }

   class 3 implements BOSHClientResponseListener {

      3() {}

      public void responseReceived(BOSHMessageEvent var1) {
         if(var1.getBody() != null) {
            try {
               PipedWriter var2 = BOSHConnection.this.readerPipe;
               String var3 = var1.getBody().toXML();
               var2.write(var3);
               BOSHConnection.this.readerPipe.flush();
            } catch (Exception var5) {
               ;
            }
         }
      }
   }

   class 4 implements BOSHClientRequestListener {

      4() {}

      public void requestSent(BOSHMessageEvent var1) {
         if(var1.getBody() != null) {
            try {
               Writer var2 = BOSHConnection.this.writer;
               String var3 = var1.getBody().toXML();
               var2.write(var3);
            } catch (Exception var5) {
               ;
            }
         }
      }
   }

   private class ListenerNotification implements Runnable {

      private Packet packet;


      public ListenerNotification(Packet var2) {
         this.packet = var2;
      }

      public void run() {
         Iterator var1 = BOSHConnection.this.recvListeners.values().iterator();

         while(var1.hasNext()) {
            Connection.ListenerWrapper var2 = (Connection.ListenerWrapper)var1.next();
            Packet var3 = this.packet;
            var2.notifyListener(var3);
         }

      }
   }

   private class BOSHConnectionListener implements BOSHClientConnListener {

      private final BOSHConnection connection;


      public BOSHConnectionListener(BOSHConnection var2) {
         this.connection = var2;
      }

      public void connectionEvent(BOSHClientConnEvent param1) {
         // $FF: Couldn't be decompiled
      }
   }

   class 5 extends Thread {

      private int bufferLength = 1024;
      private Thread thread = this;


      5() {}

      public void run() {
         try {
            char[] var1 = new char[this.bufferLength];

            while(true) {
               Thread var2 = BOSHConnection.this.readerConsumer;
               Thread var3 = this.thread;
               if(var2 != var3) {
                  return;
               }

               if(BOSHConnection.this.done) {
                  return;
               }

               Reader var4 = BOSHConnection.this.reader;
               int var5 = this.bufferLength;
               var4.read(var1, 0, var5);
            }
         } catch (IOException var8) {
            ;
         }
      }
   }
}

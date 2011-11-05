package org.jivesoftware.smack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.NonSASLAuthentication;
import org.jivesoftware.smack.PacketInterceptor;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.PacketReader;
import org.jivesoftware.smack.PacketWriter;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterStorage;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

public class XMPPConnection extends Connection {

   private boolean anonymous;
   private boolean authenticated;
   private Collection<String> compressionMethods;
   private boolean connected;
   String connectionID;
   PacketReader packetReader;
   PacketWriter packetWriter;
   Roster roster;
   protected Socket socket;
   private String user;
   private boolean usingCompression;
   private boolean usingTLS;
   private boolean wasAuthenticated;


   public XMPPConnection(String var1) {
      ConnectionConfiguration var2 = new ConnectionConfiguration(var1);
      super(var2);
      this.connectionID = null;
      this.user = null;
      this.connected = (boolean)0;
      this.authenticated = (boolean)0;
      this.wasAuthenticated = (boolean)0;
      this.anonymous = (boolean)0;
      this.usingTLS = (boolean)0;
      this.roster = null;
      this.config.setCompressionEnabled((boolean)0);
      this.config.setSASLAuthenticationEnabled((boolean)1);
      ConnectionConfiguration var3 = this.config;
      boolean var4 = DEBUG_ENABLED;
      var3.setDebuggerEnabled(var4);
   }

   public XMPPConnection(String var1, CallbackHandler var2) {
      ConnectionConfiguration var3 = new ConnectionConfiguration(var1);
      super(var3);
      this.connectionID = null;
      this.user = null;
      this.connected = (boolean)0;
      this.authenticated = (boolean)0;
      this.wasAuthenticated = (boolean)0;
      this.anonymous = (boolean)0;
      this.usingTLS = (boolean)0;
      this.roster = null;
      this.config.setCompressionEnabled((boolean)0);
      this.config.setSASLAuthenticationEnabled((boolean)1);
      ConnectionConfiguration var4 = this.config;
      boolean var5 = DEBUG_ENABLED;
      var4.setDebuggerEnabled(var5);
      this.config.setCallbackHandler(var2);
   }

   public XMPPConnection(ConnectionConfiguration var1) {
      super(var1);
      this.connectionID = null;
      this.user = null;
      this.connected = (boolean)0;
      this.authenticated = (boolean)0;
      this.wasAuthenticated = (boolean)0;
      this.anonymous = (boolean)0;
      this.usingTLS = (boolean)0;
      this.roster = null;
   }

   public XMPPConnection(ConnectionConfiguration var1, CallbackHandler var2) {
      super(var1);
      this.connectionID = null;
      this.user = null;
      this.connected = (boolean)0;
      this.authenticated = (boolean)0;
      this.wasAuthenticated = (boolean)0;
      this.anonymous = (boolean)0;
      this.usingTLS = (boolean)0;
      this.roster = null;
      var1.setCallbackHandler(var2);
   }

   private void connectUsingConfiguration(ConnectionConfiguration var1) throws XMPPException {
      String var2 = var1.getHost();
      int var3 = var1.getPort();

      try {
         if(var1.getSocketFactory() == null) {
            Socket var4 = new Socket(var2, var3);
            this.socket = var4;
         } else {
            Socket var5 = var1.getSocketFactory().createSocket(var2, var3);
            this.socket = var5;
         }
      } catch (UnknownHostException var14) {
         String var7 = "Could not connect to " + var2 + ":" + var3 + ".";
         XMPPError.Condition var8 = XMPPError.Condition.remote_server_timeout;
         XMPPError var9 = new XMPPError(var8, var7);
         throw new XMPPException(var7, var9, var14);
      } catch (IOException var15) {
         String var11 = "XMPPError connecting to " + var2 + ":" + var3 + ".";
         XMPPError.Condition var12 = XMPPError.Condition.remote_server_error;
         XMPPError var13 = new XMPPError(var12, var11);
         throw new XMPPException(var11, var13, var15);
      }

      this.initConnection();
   }

   private boolean hasAvailableCompressionMethod(String var1) {
      boolean var2;
      if(this.compressionMethods != null && this.compressionMethods.contains(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void initConnection() throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   private void initReaderAndWriter() throws XMPPException {
      try {
         if(!this.usingCompression) {
            InputStream var1 = this.socket.getInputStream();
            InputStreamReader var2 = new InputStreamReader(var1, "UTF-8");
            BufferedReader var3 = new BufferedReader(var2);
            this.reader = var3;
            OutputStream var4 = this.socket.getOutputStream();
            OutputStreamWriter var5 = new OutputStreamWriter(var4, "UTF-8");
            BufferedWriter var6 = new BufferedWriter(var5);
            this.writer = var6;
         } else {
            try {
               Class var7 = Class.forName("com.jcraft.jzlib.ZOutputStream");
               Class[] var8 = new Class[]{OutputStream.class, null};
               Class var9 = Integer.TYPE;
               var8[1] = var9;
               Constructor var10 = var7.getConstructor(var8);
               Object[] var11 = new Object[2];
               OutputStream var12 = this.socket.getOutputStream();
               var11[0] = var12;
               Integer var13 = Integer.valueOf(9);
               var11[1] = var13;
               Object var14 = var10.newInstance(var11);
               Class[] var15 = new Class[1];
               Class var16 = Integer.TYPE;
               var15[0] = var16;
               Method var17 = var7.getMethod("setFlushMode", var15);
               Object[] var18 = new Object[1];
               Integer var19 = Integer.valueOf(2);
               var18[0] = var19;
               var17.invoke(var14, var18);
               OutputStream var21 = (OutputStream)var14;
               OutputStreamWriter var22 = new OutputStreamWriter(var21, "UTF-8");
               BufferedWriter var23 = new BufferedWriter(var22);
               this.writer = var23;
               Class var24 = Class.forName("com.jcraft.jzlib.ZInputStream");
               Class[] var25 = new Class[]{InputStream.class};
               Constructor var26 = var24.getConstructor(var25);
               Object[] var27 = new Object[1];
               InputStream var28 = this.socket.getInputStream();
               var27[0] = var28;
               Object var29 = var26.newInstance(var27);
               Class[] var30 = new Class[1];
               Class var31 = Integer.TYPE;
               var30[0] = var31;
               Method var32 = var24.getMethod("setFlushMode", var30);
               Object[] var33 = new Object[1];
               Integer var34 = Integer.valueOf(2);
               var33[0] = var34;
               var32.invoke(var29, var33);
               InputStream var36 = (InputStream)var29;
               InputStreamReader var37 = new InputStreamReader(var36, "UTF-8");
               BufferedReader var38 = new BufferedReader(var37);
               this.reader = var38;
            } catch (Exception var49) {
               var49.printStackTrace();
               InputStream var40 = this.socket.getInputStream();
               InputStreamReader var41 = new InputStreamReader(var40, "UTF-8");
               BufferedReader var42 = new BufferedReader(var41);
               this.reader = var42;
               OutputStream var43 = this.socket.getOutputStream();
               OutputStreamWriter var44 = new OutputStreamWriter(var43, "UTF-8");
               BufferedWriter var45 = new BufferedWriter(var44);
               this.writer = var45;
            }
         }
      } catch (IOException var50) {
         XMPPError.Condition var47 = XMPPError.Condition.remote_server_error;
         XMPPError var48 = new XMPPError(var47, "XMPPError establishing connection with server.");
         throw new XMPPException("XMPPError establishing connection with server.", var48, var50);
      }

      this.initDebugger();
   }

   private void requestStreamCompression() {
      try {
         this.writer.write("<compress xmlns=\'http://jabber.org/protocol/compress\'>");
         this.writer.write("<method>zlib</method></compress>");
         this.writer.flush();
      } catch (IOException var2) {
         this.packetReader.notifyConnectionError(var2);
      }
   }

   private void setWasAuthenticated(boolean var1) {
      if(!this.wasAuthenticated) {
         this.wasAuthenticated = var1;
      }
   }

   private boolean useCompression() {
      // $FF: Couldn't be decompiled
   }

   public void addPacketWriterInterceptor(PacketInterceptor var1, PacketFilter var2) {
      this.addPacketInterceptor(var1, var2);
   }

   public void addPacketWriterListener(PacketListener var1, PacketFilter var2) {
      this.addPacketSendingListener(var1, var2);
   }

   public void connect() throws XMPPException {
      ConnectionConfiguration var1 = this.config;
      this.connectUsingConfiguration(var1);
      if(this.connected) {
         if(this.wasAuthenticated) {
            try {
               if(this.isAnonymous()) {
                  this.loginAnonymously();
               } else {
                  String var2 = this.config.getUsername();
                  String var3 = this.config.getPassword();
                  String var4 = this.config.getResource();
                  this.login(var2, var3, var4);
               }

               this.packetReader.notifyReconnection();
            } catch (XMPPException var5) {
               var5.printStackTrace();
            }
         }
      }
   }

   public void disconnect(Presence var1) {
      if(this.packetReader != null) {
         if(this.packetWriter != null) {
            this.shutdown(var1);
            if(this.roster != null) {
               this.roster.cleanup();
               this.roster = null;
            }

            this.wasAuthenticated = (boolean)0;
            this.packetWriter.cleanup();
            this.packetWriter = null;
            this.packetReader.cleanup();
            this.packetReader = null;
         }
      }
   }

   public String getConnectionID() {
      String var1;
      if(!this.isConnected()) {
         var1 = null;
      } else {
         var1 = this.connectionID;
      }

      return var1;
   }

   public Roster getRoster() {
      // $FF: Couldn't be decompiled
   }

   public String getUser() {
      String var1;
      if(!this.isAuthenticated()) {
         var1 = null;
      } else {
         var1 = this.user;
      }

      return var1;
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
      return this.isUsingTLS();
   }

   public boolean isUsingCompression() {
      return this.usingCompression;
   }

   public boolean isUsingTLS() {
      return this.usingTLS;
   }

   public void login(String var1, String var2, String var3) throws XMPPException {
      synchronized(this){}

      try {
         if(!this.isConnected()) {
            throw new IllegalStateException("Not connected to server.");
         }

         if(this.authenticated) {
            throw new IllegalStateException("Already logged in to server.");
         }

         String var5 = var1.toLowerCase().trim();
         String var6;
         if(this.config.isSASLAuthenticationEnabled() && this.saslAuthentication.hasNonAnonymousAuthentication()) {
            if(var2 != null) {
               var6 = this.saslAuthentication.authenticate(var5, var2, var3);
            } else {
               SASLAuthentication var16 = this.saslAuthentication;
               CallbackHandler var17 = this.config.getCallbackHandler();
               var6 = var16.authenticate(var5, var3, var17);
            }
         } else {
            var6 = (new NonSASLAuthentication(this)).authenticate(var5, var2, var3);
         }

         if(var6 != null) {
            this.user = var6;
            ConnectionConfiguration var7 = this.config;
            String var8 = StringUtils.parseServer(var6);
            var7.setServiceName(var8);
         } else {
            StringBuilder var18 = (new StringBuilder()).append(var5).append("@");
            String var19 = this.getServiceName();
            String var20 = var18.append(var19).toString();
            this.user = var20;
            if(var3 != null) {
               StringBuilder var21 = new StringBuilder();
               String var22 = this.user;
               String var23 = var21.append(var22).append("/").append(var3).toString();
               this.user = var23;
            }
         }

         if(this.config.isCompressionEnabled()) {
            boolean var9 = this.useCompression();
         }

         if(this.roster == null) {
            if(this.rosterStorage == null) {
               Roster var10 = new Roster(this);
               this.roster = var10;
            } else {
               RosterStorage var24 = this.rosterStorage;
               Roster var25 = new Roster(this, var24);
               this.roster = var25;
            }
         }

         if(this.config.isRosterLoadedAtLogin()) {
            this.roster.reload();
         }

         if(this.config.isSendPresence()) {
            PacketWriter var11 = this.packetWriter;
            Presence.Type var12 = Presence.Type.available;
            Presence var13 = new Presence(var12);
            var11.sendPacket(var13);
         }

         this.authenticated = (boolean)1;
         this.anonymous = (boolean)0;
         this.config.setLoginInfo(var5, var2, var3);
         if(this.config.isDebuggerEnabled() && this.debugger != null) {
            SmackDebugger var14 = this.debugger;
            String var15 = this.user;
            var14.userHasLogged(var15);
         }
      } finally {
         ;
      }

   }

   public void loginAnonymously() throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   void proceedTLSReceived() throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void removePacketWriterInterceptor(PacketInterceptor var1) {
      this.removePacketInterceptor(var1);
   }

   public void removePacketWriterListener(PacketListener var1) {
      this.removePacketSendingListener(var1);
   }

   public void sendPacket(Packet var1) {
      if(!this.isConnected()) {
         throw new IllegalStateException("Not connected to server.");
      } else if(var1 == null) {
         throw new NullPointerException("Packet is null.");
      } else {
         this.packetWriter.sendPacket(var1);
      }
   }

   void setAvailableCompressionMethods(Collection<String> var1) {
      this.compressionMethods = var1;
   }

   public void setRosterStorage(RosterStorage var1) throws IllegalStateException {
      if(this.roster != null) {
         throw new IllegalStateException("Roster is already initialized");
      } else {
         this.rosterStorage = var1;
      }
   }

   protected void shutdown(Presence var1) {
      if(this.packetWriter != null) {
         this.packetWriter.sendPacket(var1);
      }

      boolean var2 = this.authenticated;
      this.setWasAuthenticated(var2);
      this.authenticated = (boolean)0;
      this.connected = (boolean)0;
      if(this.packetReader != null) {
         this.packetReader.shutdown();
      }

      if(this.packetWriter != null) {
         this.packetWriter.shutdown();
      }

      long var3 = 150L;

      try {
         Thread.sleep(var3);
      } catch (Exception var12) {
         ;
      }

      if(this.reader != null) {
         try {
            this.reader.close();
         } catch (Throwable var11) {
            ;
         }

         this.reader = null;
      }

      if(this.writer != null) {
         try {
            this.writer.close();
         } catch (Throwable var10) {
            ;
         }

         this.writer = null;
      }

      try {
         this.socket.close();
      } catch (Exception var9) {
         ;
      }

      this.saslAuthentication.init();
   }

   void startStreamCompression() throws Exception {
      this.usingCompression = (boolean)1;
      this.initReaderAndWriter();
      PacketWriter var1 = this.packetWriter;
      Writer var2 = this.writer;
      var1.setWriter(var2);
      this.packetWriter.openStream();
      synchronized(this) {
         this.notify();
      }
   }

   void startTLSReceived(boolean var1) {
      if(var1) {
         ConnectionConfiguration.SecurityMode var2 = this.config.getSecurityMode();
         ConnectionConfiguration.SecurityMode var3 = ConnectionConfiguration.SecurityMode.disabled;
         if(var2 == var3) {
            PacketReader var4 = this.packetReader;
            IllegalStateException var5 = new IllegalStateException("TLS required by server but not allowed by connection configuration");
            var4.notifyConnectionError(var5);
            return;
         }
      }

      ConnectionConfiguration.SecurityMode var6 = this.config.getSecurityMode();
      ConnectionConfiguration.SecurityMode var7 = ConnectionConfiguration.SecurityMode.disabled;
      if(var6 != var7) {
         try {
            this.writer.write("<starttls xmlns=\"urn:ietf:params:xml:ns:xmpp-tls\"/>");
            this.writer.flush();
         } catch (IOException var9) {
            this.packetReader.notifyConnectionError(var9);
         }
      }
   }

   void streamCompressionDenied() {
      synchronized(this) {
         this.notify();
      }
   }
}

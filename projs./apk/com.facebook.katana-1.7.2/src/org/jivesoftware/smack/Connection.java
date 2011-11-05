package org.jivesoftware.smack;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketInterceptor;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterStorage;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public abstract class Connection {

   public static boolean DEBUG_ENABLED = 0;
   private static final AtomicInteger connectionCounter = new AtomicInteger(0);
   private static final Set<ConnectionCreationListener> connectionEstablishedListeners = new CopyOnWriteArraySet();
   private AccountManager accountManager;
   private ChatManager chatManager;
   protected final Collection<PacketCollector> collectors;
   protected final ConnectionConfiguration config;
   protected final int connectionCounterValue;
   protected final Collection<ConnectionListener> connectionListeners;
   protected SmackDebugger debugger;
   protected final Map<PacketInterceptor, Connection.InterceptorWrapper> interceptors;
   protected Reader reader;
   protected final Map<PacketListener, Connection.ListenerWrapper> recvListeners;
   protected RosterStorage rosterStorage;
   protected SASLAuthentication saslAuthentication;
   protected final Map<PacketListener, Connection.ListenerWrapper> sendListeners;
   protected Writer writer;


   static {
      try {
         DEBUG_ENABLED = Boolean.getBoolean("smack.debugEnabled");
      } catch (Exception var2) {
         ;
      }

      String var0 = SmackConfiguration.getVersion();
   }

   protected Connection(ConnectionConfiguration var1) {
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();
      this.connectionListeners = var2;
      ConcurrentLinkedQueue var3 = new ConcurrentLinkedQueue();
      this.collectors = var3;
      ConcurrentHashMap var4 = new ConcurrentHashMap();
      this.recvListeners = var4;
      ConcurrentHashMap var5 = new ConcurrentHashMap();
      this.sendListeners = var5;
      ConcurrentHashMap var6 = new ConcurrentHashMap();
      this.interceptors = var6;
      this.accountManager = null;
      this.chatManager = null;
      this.debugger = null;
      SASLAuthentication var7 = new SASLAuthentication(this);
      this.saslAuthentication = var7;
      int var8 = connectionCounter.getAndIncrement();
      this.connectionCounterValue = var8;
      this.config = var1;
   }

   public static void addConnectionCreationListener(ConnectionCreationListener var0) {
      boolean var1 = connectionEstablishedListeners.add(var0);
   }

   protected static Collection<ConnectionCreationListener> getConnectionCreationListeners() {
      return Collections.unmodifiableCollection(connectionEstablishedListeners);
   }

   public static void removeConnectionCreationListener(ConnectionCreationListener var0) {
      boolean var1 = connectionEstablishedListeners.remove(var0);
   }

   public void addConnectionListener(ConnectionListener var1) {
      if(!this.isConnected()) {
         throw new IllegalStateException("Not connected to server.");
      } else if(var1 != null) {
         if(!this.connectionListeners.contains(var1)) {
            this.connectionListeners.add(var1);
         }
      }
   }

   public void addPacketInterceptor(PacketInterceptor var1, PacketFilter var2) {
      if(var1 == null) {
         throw new NullPointerException("Packet interceptor is null.");
      } else {
         Map var3 = this.interceptors;
         Connection.InterceptorWrapper var4 = new Connection.InterceptorWrapper(var1, var2);
         var3.put(var1, var4);
      }
   }

   public void addPacketListener(PacketListener var1, PacketFilter var2) {
      if(var1 == null) {
         throw new NullPointerException("Packet listener is null.");
      } else {
         Connection.ListenerWrapper var3 = new Connection.ListenerWrapper(var1, var2);
         this.recvListeners.put(var1, var3);
      }
   }

   public void addPacketSendingListener(PacketListener var1, PacketFilter var2) {
      if(var1 == null) {
         throw new NullPointerException("Packet listener is null.");
      } else {
         Connection.ListenerWrapper var3 = new Connection.ListenerWrapper(var1, var2);
         this.sendListeners.put(var1, var3);
      }
   }

   public abstract void connect() throws XMPPException;

   public PacketCollector createPacketCollector(PacketFilter var1) {
      PacketCollector var2 = new PacketCollector(this, var1);
      this.collectors.add(var2);
      return var2;
   }

   public void disconnect() {
      Presence.Type var1 = Presence.Type.unavailable;
      Presence var2 = new Presence(var1);
      this.disconnect(var2);
   }

   public abstract void disconnect(Presence var1);

   protected void firePacketInterceptors(Packet var1) {
      if(var1 != null) {
         Iterator var2 = this.interceptors.values().iterator();

         while(var2.hasNext()) {
            ((Connection.InterceptorWrapper)var2.next()).notifyListener(var1);
         }

      }
   }

   protected void firePacketSendingListeners(Packet var1) {
      Iterator var2 = this.sendListeners.values().iterator();

      while(var2.hasNext()) {
         ((Connection.ListenerWrapper)var2.next()).notifyListener(var1);
      }

   }

   public AccountManager getAccountManager() {
      if(this.accountManager == null) {
         AccountManager var1 = new AccountManager(this);
         this.accountManager = var1;
      }

      return this.accountManager;
   }

   public String getCapsNode() {
      return this.config.getCapsNode();
   }

   public ChatManager getChatManager() {
      synchronized(this){}

      ChatManager var2;
      try {
         if(this.chatManager == null) {
            ChatManager var1 = new ChatManager(this);
            this.chatManager = var1;
         }

         var2 = this.chatManager;
      } finally {
         ;
      }

      return var2;
   }

   protected ConnectionConfiguration getConfiguration() {
      return this.config;
   }

   public abstract String getConnectionID();

   protected Collection<ConnectionListener> getConnectionListeners() {
      return this.connectionListeners;
   }

   public String getHost() {
      return this.config.getHost();
   }

   protected Collection<PacketCollector> getPacketCollectors() {
      return this.collectors;
   }

   protected Map<PacketInterceptor, Connection.InterceptorWrapper> getPacketInterceptors() {
      return this.interceptors;
   }

   protected Map<PacketListener, Connection.ListenerWrapper> getPacketListeners() {
      return this.recvListeners;
   }

   protected Map<PacketListener, Connection.ListenerWrapper> getPacketSendingListeners() {
      return this.sendListeners;
   }

   public int getPort() {
      return this.config.getPort();
   }

   public abstract Roster getRoster();

   public SASLAuthentication getSASLAuthentication() {
      return this.saslAuthentication;
   }

   public String getServiceName() {
      return this.config.getServiceName();
   }

   public abstract String getUser();

   protected void initDebugger() {
      if(this.reader != null && this.writer != null) {
         if(this.config.isDebuggerEnabled()) {
            if(this.debugger == null) {
               String var2;
               label57: {
                  String var1;
                  try {
                     var1 = System.getProperty("smack.debuggerClass");
                  } catch (Throwable var26) {
                     var2 = null;
                     break label57;
                  }

                  var2 = var1;
               }

               Class var3;
               Class var27;
               label53: {
                  if(var2 != null) {
                     label51: {
                        try {
                           var27 = Class.forName(var2);
                        } catch (Exception var25) {
                           var25.printStackTrace();
                           break label51;
                        }

                        var3 = var27;
                        break label53;
                     }
                  }

                  var3 = null;
               }

               if(var3 == null) {
                  label45: {
                     try {
                        var27 = Class.forName("de.measite.smack.AndroidDebugger");
                     } catch (Exception var24) {
                        try {
                           var27 = Class.forName("org.jivesoftware.smack.debugger.ConsoleDebugger");
                        } catch (Exception var23) {
                           var23.printStackTrace();
                           break label45;
                        }

                        var3 = var27;
                        break label45;
                     }

                     var3 = var27;
                  }
               }

               byte var4 = 3;

               try {
                  Class[] var5 = new Class[var4];
                  var5[0] = Connection.class;
                  var5[1] = Writer.class;
                  var5[2] = Reader.class;
                  Constructor var6 = var3.getConstructor(var5);
                  Object[] var7 = new Object[]{this, null, null};
                  Writer var8 = this.writer;
                  var7[1] = var8;
                  Reader var9 = this.reader;
                  var7[2] = var9;
                  SmackDebugger var10 = (SmackDebugger)var6.newInstance(var7);
                  this.debugger = var10;
                  Reader var11 = this.debugger.getReader();
                  this.reader = var11;
                  Writer var12 = this.debugger.getWriter();
                  this.writer = var12;
               } catch (Exception var22) {
                  throw new IllegalArgumentException("Can\'t initialize the configured debugger!", var22);
               }
            } else {
               SmackDebugger var16 = this.debugger;
               Reader var17 = this.reader;
               Reader var18 = var16.newConnectionReader(var17);
               this.reader = var18;
               SmackDebugger var19 = this.debugger;
               Writer var20 = this.writer;
               Writer var21 = var19.newConnectionWriter(var20);
               this.writer = var21;
            }
         }
      } else {
         throw new NullPointerException("Reader or writer isn\'t initialized.");
      }
   }

   public abstract boolean isAnonymous();

   public abstract boolean isAuthenticated();

   public abstract boolean isConnected();

   protected boolean isReconnectionAllowed() {
      return this.config.isReconnectionAllowed();
   }

   public abstract boolean isSecureConnection();

   public boolean isSendPresence() {
      return this.config.isSendPresence();
   }

   public abstract boolean isUsingCompression();

   public void login(String var1, String var2) throws XMPPException {
      this.login(var1, var2, "Smack");
   }

   public abstract void login(String var1, String var2, String var3) throws XMPPException;

   public abstract void loginAnonymously() throws XMPPException;

   public void removeConnectionListener(ConnectionListener var1) {
      this.connectionListeners.remove(var1);
   }

   protected void removePacketCollector(PacketCollector var1) {
      this.collectors.remove(var1);
   }

   public void removePacketInterceptor(PacketInterceptor var1) {
      this.interceptors.remove(var1);
   }

   public void removePacketListener(PacketListener var1) {
      this.recvListeners.remove(var1);
   }

   public void removePacketSendingListener(PacketListener var1) {
      this.sendListeners.remove(var1);
   }

   public abstract void sendPacket(Packet var1);

   public abstract void setRosterStorage(RosterStorage var1) throws IllegalStateException;

   protected static class ListenerWrapper {

      private PacketFilter packetFilter;
      private PacketListener packetListener;


      public ListenerWrapper(PacketListener var1, PacketFilter var2) {
         this.packetListener = var1;
         this.packetFilter = var2;
      }

      public void notifyListener(Packet var1) {
         if(this.packetFilter == null || this.packetFilter.accept(var1)) {
            this.packetListener.processPacket(var1);
         }
      }
   }

   protected static class InterceptorWrapper {

      private PacketFilter packetFilter;
      private PacketInterceptor packetInterceptor;


      public InterceptorWrapper(PacketInterceptor var1, PacketFilter var2) {
         this.packetInterceptor = var1;
         this.packetFilter = var2;
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == null) {
            var2 = false;
         } else if(var1 instanceof Connection.InterceptorWrapper) {
            PacketInterceptor var3 = ((Connection.InterceptorWrapper)var1).packetInterceptor;
            PacketInterceptor var4 = this.packetInterceptor;
            var2 = var3.equals(var4);
         } else if(var1 instanceof PacketInterceptor) {
            PacketInterceptor var5 = this.packetInterceptor;
            var2 = var1.equals(var5);
         } else {
            var2 = false;
         }

         return var2;
      }

      public void notifyListener(Packet var1) {
         if(this.packetFilter == null || this.packetFilter.accept(var1)) {
            this.packetInterceptor.interceptPacket(var1);
         }
      }
   }
}

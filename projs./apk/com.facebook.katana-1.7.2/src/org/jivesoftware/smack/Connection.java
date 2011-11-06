// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Connection.java

package org.jivesoftware.smack;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

// Referenced classes of package org.jivesoftware.smack:
//            SmackConfiguration, SASLAuthentication, XMPPException, PacketCollector, 
//            AccountManager, ConnectionConfiguration, ChatManager, RosterStorage, 
//            ConnectionCreationListener, ConnectionListener, PacketInterceptor, PacketListener, 
//            Roster

public abstract class Connection
{
    protected static class InterceptorWrapper
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if(obj == null)
                flag = false;
            else
            if(obj instanceof InterceptorWrapper)
                flag = ((InterceptorWrapper)obj).packetInterceptor.equals(packetInterceptor);
            else
            if(obj instanceof PacketInterceptor)
                flag = obj.equals(packetInterceptor);
            else
                flag = false;
            return flag;
        }

        public void notifyListener(Packet packet)
        {
            if(packetFilter == null || packetFilter.accept(packet))
                packetInterceptor.interceptPacket(packet);
        }

        private PacketFilter packetFilter;
        private PacketInterceptor packetInterceptor;

        public InterceptorWrapper(PacketInterceptor packetinterceptor, PacketFilter packetfilter)
        {
            packetInterceptor = packetinterceptor;
            packetFilter = packetfilter;
        }
    }

    protected static class ListenerWrapper
    {

        public void notifyListener(Packet packet)
        {
            if(packetFilter == null || packetFilter.accept(packet))
                packetListener.processPacket(packet);
        }

        private PacketFilter packetFilter;
        private PacketListener packetListener;

        public ListenerWrapper(PacketListener packetlistener, PacketFilter packetfilter)
        {
            packetListener = packetlistener;
            packetFilter = packetfilter;
        }
    }


    protected Connection(ConnectionConfiguration connectionconfiguration)
    {
        accountManager = null;
        chatManager = null;
        debugger = null;
        saslAuthentication = new SASLAuthentication(this);
        connectionCounterValue = connectionCounter.getAndIncrement();
        config = connectionconfiguration;
    }

    public static void addConnectionCreationListener(ConnectionCreationListener connectioncreationlistener)
    {
        connectionEstablishedListeners.add(connectioncreationlistener);
    }

    protected static Collection getConnectionCreationListeners()
    {
        return Collections.unmodifiableCollection(connectionEstablishedListeners);
    }

    public static void removeConnectionCreationListener(ConnectionCreationListener connectioncreationlistener)
    {
        connectionEstablishedListeners.remove(connectioncreationlistener);
    }

    public void addConnectionListener(ConnectionListener connectionlistener)
    {
        if(!isConnected())
            throw new IllegalStateException("Not connected to server.");
        break MISSING_BLOCK_LABEL_17;
        if(connectionlistener != null && !connectionListeners.contains(connectionlistener))
            connectionListeners.add(connectionlistener);
        return;
    }

    public void addPacketInterceptor(PacketInterceptor packetinterceptor, PacketFilter packetfilter)
    {
        if(packetinterceptor == null)
        {
            throw new NullPointerException("Packet interceptor is null.");
        } else
        {
            interceptors.put(packetinterceptor, new InterceptorWrapper(packetinterceptor, packetfilter));
            return;
        }
    }

    public void addPacketListener(PacketListener packetlistener, PacketFilter packetfilter)
    {
        if(packetlistener == null)
        {
            throw new NullPointerException("Packet listener is null.");
        } else
        {
            ListenerWrapper listenerwrapper = new ListenerWrapper(packetlistener, packetfilter);
            recvListeners.put(packetlistener, listenerwrapper);
            return;
        }
    }

    public void addPacketSendingListener(PacketListener packetlistener, PacketFilter packetfilter)
    {
        if(packetlistener == null)
        {
            throw new NullPointerException("Packet listener is null.");
        } else
        {
            ListenerWrapper listenerwrapper = new ListenerWrapper(packetlistener, packetfilter);
            sendListeners.put(packetlistener, listenerwrapper);
            return;
        }
    }

    public abstract void connect()
        throws XMPPException;

    public PacketCollector createPacketCollector(PacketFilter packetfilter)
    {
        PacketCollector packetcollector = new PacketCollector(this, packetfilter);
        collectors.add(packetcollector);
        return packetcollector;
    }

    public void disconnect()
    {
        disconnect(new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable));
    }

    public abstract void disconnect(Presence presence);

    protected void firePacketInterceptors(Packet packet)
    {
        if(packet != null)
        {
            for(Iterator iterator = interceptors.values().iterator(); iterator.hasNext(); ((InterceptorWrapper)iterator.next()).notifyListener(packet));
        }
    }

    protected void firePacketSendingListeners(Packet packet)
    {
        for(Iterator iterator = sendListeners.values().iterator(); iterator.hasNext(); ((ListenerWrapper)iterator.next()).notifyListener(packet));
    }

    public AccountManager getAccountManager()
    {
        if(accountManager == null)
            accountManager = new AccountManager(this);
        return accountManager;
    }

    public String getCapsNode()
    {
        return config.getCapsNode();
    }

    /**
     * @deprecated Method getChatManager is deprecated
     */

    public ChatManager getChatManager()
    {
        this;
        JVM INSTR monitorenter ;
        ChatManager chatmanager;
        if(chatManager == null)
            chatManager = new ChatManager(this);
        chatmanager = chatManager;
        this;
        JVM INSTR monitorexit ;
        return chatmanager;
        Exception exception;
        exception;
        throw exception;
    }

    protected ConnectionConfiguration getConfiguration()
    {
        return config;
    }

    public abstract String getConnectionID();

    protected Collection getConnectionListeners()
    {
        return connectionListeners;
    }

    public String getHost()
    {
        return config.getHost();
    }

    protected Collection getPacketCollectors()
    {
        return collectors;
    }

    protected Map getPacketInterceptors()
    {
        return interceptors;
    }

    protected Map getPacketListeners()
    {
        return recvListeners;
    }

    protected Map getPacketSendingListeners()
    {
        return sendListeners;
    }

    public int getPort()
    {
        return config.getPort();
    }

    public abstract Roster getRoster();

    public SASLAuthentication getSASLAuthentication()
    {
        return saslAuthentication;
    }

    public String getServiceName()
    {
        return config.getServiceName();
    }

    public abstract String getUser();

    protected void initDebugger()
    {
        if(reader == null || writer == null)
            throw new NullPointerException("Reader or writer isn't initialized.");
        if(!config.isDebuggerEnabled()) goto _L2; else goto _L1
_L1:
        if(debugger != null)
            break MISSING_BLOCK_LABEL_242;
        String s1 = System.getProperty("smack.debuggerClass");
        String s = s1;
_L5:
        if(s == null) goto _L4; else goto _L3
_L3:
        Class class4 = Class.forName(s);
        Class class1 = class4;
_L6:
        if(class1 != null)
            break MISSING_BLOCK_LABEL_81;
        Class class3 = Class.forName("de.measite.smack.AndroidDebugger");
        class1 = class3;
_L7:
        Class aclass[] = new Class[3];
        aclass[0] = org/jivesoftware/smack/Connection;
        aclass[1] = java/io/Writer;
        aclass[2] = java/io/Reader;
        Constructor constructor = class1.getConstructor(aclass);
        Object aobj[] = new Object[3];
        aobj[0] = this;
        aobj[1] = writer;
        aobj[2] = reader;
        debugger = (SmackDebugger)constructor.newInstance(aobj);
        reader = debugger.getReader();
        writer = debugger.getWriter();
_L2:
        return;
        Throwable throwable;
        throwable;
        s = null;
          goto _L5
        Exception exception3;
        exception3;
        exception3.printStackTrace();
_L4:
        class1 = null;
          goto _L6
        Exception exception1;
        exception1;
        Class class2 = Class.forName("org.jivesoftware.smack.debugger.ConsoleDebugger");
        class1 = class2;
          goto _L7
        Exception exception2;
        exception2;
        exception2.printStackTrace();
          goto _L7
        Exception exception;
        exception;
        throw new IllegalArgumentException("Can't initialize the configured debugger!", exception);
        reader = debugger.newConnectionReader(reader);
        writer = debugger.newConnectionWriter(writer);
          goto _L2
    }

    public abstract boolean isAnonymous();

    public abstract boolean isAuthenticated();

    public abstract boolean isConnected();

    protected boolean isReconnectionAllowed()
    {
        return config.isReconnectionAllowed();
    }

    public abstract boolean isSecureConnection();

    public boolean isSendPresence()
    {
        return config.isSendPresence();
    }

    public abstract boolean isUsingCompression();

    public void login(String s, String s1)
        throws XMPPException
    {
        login(s, s1, "Smack");
    }

    public abstract void login(String s, String s1, String s2)
        throws XMPPException;

    public abstract void loginAnonymously()
        throws XMPPException;

    public void removeConnectionListener(ConnectionListener connectionlistener)
    {
        connectionListeners.remove(connectionlistener);
    }

    protected void removePacketCollector(PacketCollector packetcollector)
    {
        collectors.remove(packetcollector);
    }

    public void removePacketInterceptor(PacketInterceptor packetinterceptor)
    {
        interceptors.remove(packetinterceptor);
    }

    public void removePacketListener(PacketListener packetlistener)
    {
        recvListeners.remove(packetlistener);
    }

    public void removePacketSendingListener(PacketListener packetlistener)
    {
        sendListeners.remove(packetlistener);
    }

    public abstract void sendPacket(Packet packet);

    public abstract void setRosterStorage(RosterStorage rosterstorage)
        throws IllegalStateException;

    public static boolean DEBUG_ENABLED = false;
    private static final AtomicInteger connectionCounter = new AtomicInteger(0);
    private static final Set connectionEstablishedListeners = new CopyOnWriteArraySet();
    private AccountManager accountManager;
    private ChatManager chatManager;
    protected final Collection collectors = new ConcurrentLinkedQueue();
    protected final ConnectionConfiguration config;
    protected final int connectionCounterValue;
    protected final Collection connectionListeners = new CopyOnWriteArrayList();
    protected SmackDebugger debugger;
    protected final Map interceptors = new ConcurrentHashMap();
    protected Reader reader;
    protected final Map recvListeners = new ConcurrentHashMap();
    protected RosterStorage rosterStorage;
    protected SASLAuthentication saslAuthentication;
    protected final Map sendListeners = new ConcurrentHashMap();
    protected Writer writer;

    static 
    {
        try
        {
            DEBUG_ENABLED = Boolean.getBoolean("smack.debugEnabled");
        }
        catch(Exception exception) { }
        SmackConfiguration.getVersion();
    }
}

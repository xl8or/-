// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOSHConnection.java

package org.jivesoftware.smack;

import com.kenai.jbosh.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack:
//            Connection, BOSHConfiguration, XMPPException, SASLAuthentication, 
//            BOSHPacketReader, SmackConfiguration, Roster, ConnectionListener, 
//            NonSASLAuthentication, PacketCollector, RosterStorage, ConnectionCreationListener

public class BOSHConnection extends Connection
{
    private class ListenerNotification
        implements Runnable
    {

        public void run()
        {
            for(Iterator iterator = recvListeners.values().iterator(); iterator.hasNext(); ((Connection.ListenerWrapper)iterator.next()).notifyListener(packet));
        }

        private Packet packet;
        final BOSHConnection this$0;

        public ListenerNotification(Packet packet1)
        {
            this$0 = BOSHConnection.this;
            super();
            packet = packet1;
        }
    }

    private class BOSHConnectionListener
        implements BOSHClientConnListener
    {

        public void connectionEvent(BOSHClientConnEvent boshclientconnevent)
        {
            if(!boshclientconnevent.isConnected()) goto _L2; else goto _L1
_L1:
            connected = true;
            if(!isFirstInitialization) goto _L4; else goto _L3
_L3:
            isFirstInitialization = false;
            for(Iterator iterator2 = Connection.getConnectionCreationListeners().iterator(); iterator2.hasNext(); ((ConnectionCreationListener)iterator2.next()).connectionCreated(connection));
              goto _L5
            Exception exception;
            exception;
            synchronized(connection)
            {
                connection.notifyAll();
            }
            throw exception;
_L4:
            if(wasAuthenticated)
                connection.login(config.getUsername(), config.getPassword(), config.getResource());
            for(Iterator iterator1 = getConnectionListeners().iterator(); iterator1.hasNext(); ((ConnectionListener)iterator1.next()).reconnectionSuccessful());
              goto _L5
            XMPPException xmppexception;
            xmppexception;
            for(Iterator iterator = getConnectionListeners().iterator(); iterator.hasNext(); ((ConnectionListener)iterator.next()).reconnectionFailed(xmppexception));
              goto _L5
_L2:
            boolean flag = boshclientconnevent.isError();
            if(!flag)
                break MISSING_BLOCK_LABEL_247;
            boshclientconnevent.getCause();
_L6:
            connected = false;
_L5:
            synchronized(connection)
            {
                connection.notifyAll();
            }
            return;
            Exception exception3;
            exception3;
            notifyConnectionError(exception3);
              goto _L6
            exception2;
            boshconnection1;
            JVM INSTR monitorexit ;
            throw exception2;
            exception1;
            boshconnection;
            JVM INSTR monitorexit ;
            throw exception1;
        }

        private final BOSHConnection connection;
        final BOSHConnection this$0;

        public BOSHConnectionListener(BOSHConnection boshconnection1)
        {
            this$0 = BOSHConnection.this;
            super();
            connection = boshconnection1;
        }
    }


    public BOSHConnection(BOSHConfiguration boshconfiguration)
    {
        super(boshconfiguration);
        connected = false;
        authenticated = false;
        anonymous = false;
        isFirstInitialization = true;
        wasAuthenticated = false;
        done = false;
        authID = null;
        sessionID = null;
        user = null;
        roster = null;
        config = boshconfiguration;
    }

    public BOSHConnection(boolean flag, String s, int i, String s1, String s2)
    {
        super(new BOSHConfiguration(flag, s, i, s1, s2));
        connected = false;
        authenticated = false;
        anonymous = false;
        isFirstInitialization = true;
        wasAuthenticated = false;
        done = false;
        authID = null;
        sessionID = null;
        user = null;
        roster = null;
        config = (BOSHConfiguration)getConfiguration();
    }

    private void setWasAuthenticated(boolean flag)
    {
        if(!wasAuthenticated)
            wasAuthenticated = flag;
    }

    public void connect()
        throws XMPPException
    {
        if(connected)
            throw new IllegalStateException("Already connected to a server.");
        done = false;
        long l;
        long l1;
        InterruptedException interruptedexception;
        try
        {
            if(client != null)
            {
                client.close();
                client = null;
            }
            saslAuthentication.init();
            sessionID = null;
            authID = null;
            com.kenai.jbosh.BOSHClientConfig.Builder builder = com.kenai.jbosh.BOSHClientConfig.Builder.create(config.getURI(), config.getServiceName());
            if(config.isProxyEnabled())
                builder.setProxy(config.getProxyAddress(), config.getProxyPort());
            client = BOSHClient.create(builder.build());
            listenerExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {

                public Thread newThread(Runnable runnable)
                {
                    Thread thread = new Thread(runnable, (new StringBuilder()).append("Smack Listener Processor (").append(connectionCounterValue).append(")").toString());
                    thread.setDaemon(true);
                    return thread;
                }

                final BOSHConnection this$0;

            
            {
                this$0 = BOSHConnection.this;
                super();
            }
            }
);
            client.addBOSHClientConnListener(new BOSHConnectionListener(this));
            client.addBOSHClientResponseListener(new BOSHPacketReader(this));
            if(config.isDebuggerEnabled())
            {
                initDebugger();
                if(isFirstInitialization)
                {
                    if(debugger.getReaderListener() != null)
                        addPacketListener(debugger.getReaderListener(), null);
                    if(debugger.getWriterListener() != null)
                        addPacketSendingListener(debugger.getWriterListener(), null);
                }
            }
            client.send(ComposableBody.builder().setNamespaceDefinition("xmpp", "urn:xmpp:xbosh").setAttribute(BodyQName.createWithPrefix("urn:xmpp:xbosh", "version", "xmpp"), "1.0").build());
        }
        catch(Exception exception)
        {
            throw new XMPPException((new StringBuilder()).append("Can't connect to ").append(getServiceName()).toString(), exception);
        }
        this;
        JVM INSTR monitorenter ;
        l = System.currentTimeMillis() + (long)(6 * SmackConfiguration.getPacketReplyTimeout());
_L1:
        if(connected)
            break MISSING_BLOCK_LABEL_358;
        l1 = System.currentTimeMillis();
        if(l1 >= l)
            break MISSING_BLOCK_LABEL_358;
        try
        {
            wait(Math.abs(l - System.currentTimeMillis()));
        }
        // Misplaced declaration of an exception variable
        catch(InterruptedException interruptedexception) { }
          goto _L1
        this;
        JVM INSTR monitorexit ;
        Exception exception1;
        if(!connected && !done)
        {
            done = true;
            String s = (new StringBuilder()).append("Timeout reached for the connection to ").append(getHost()).append(":").append(getPort()).append(".").toString();
            throw new XMPPException(s, new XMPPError(org.jivesoftware.smack.packet.XMPPError.Condition.remote_server_timeout, s));
        } else
        {
            return;
        }
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    public void disconnect(Presence presence)
    {
        if(connected)
        {
            shutdown(presence);
            if(roster != null)
            {
                roster.cleanup();
                roster = null;
            }
            sendListeners.clear();
            recvListeners.clear();
            collectors.clear();
            interceptors.clear();
            wasAuthenticated = false;
            isFirstInitialization = true;
            Iterator iterator = getConnectionListeners().iterator();
            while(iterator.hasNext()) 
            {
                ConnectionListener connectionlistener = (ConnectionListener)iterator.next();
                try
                {
                    connectionlistener.connectionClosed();
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
    }

    public String getConnectionID()
    {
        String s;
        if(!connected)
            s = null;
        else
        if(authID != null)
            s = authID;
        else
            s = sessionID;
        return s;
    }

    public Roster getRoster()
    {
        if(roster != null) goto _L2; else goto _L1
_L1:
        Roster roster1 = null;
_L6:
        return roster1;
_L2:
        if(!config.isRosterLoadedAtLogin())
            roster.reload();
        if(roster.rosterInitialized) goto _L4; else goto _L3
_L3:
        Roster roster2 = roster;
        roster2;
        JVM INSTR monitorenter ;
        long l2;
        long l3;
        long l = SmackConfiguration.getPacketReplyTimeout();
        long l1 = System.currentTimeMillis();
        l2 = l;
        l3 = l1;
_L7:
        if(!roster.rosterInitialized && l2 > 0L) goto _L5; else goto _L4
_L4:
        roster1 = roster;
          goto _L6
_L5:
        roster.wait(l2);
        long l4 = System.currentTimeMillis();
        l2 -= l4 - l3;
        l3 = l4;
          goto _L7
        Exception exception;
        exception;
        try
        {
            throw exception;
        }
        catch(InterruptedException interruptedexception) { }
          goto _L4
    }

    public String getUser()
    {
        return user;
    }

    protected void initDebugger()
    {
        writer = new Writer() {

            public void close()
            {
            }

            public void flush()
            {
            }

            public void write(char ac[], int i, int j)
            {
            }

            final BOSHConnection this$0;

            
            {
                this$0 = BOSHConnection.this;
                super();
            }
        }
;
        try
        {
            readerPipe = new PipedWriter();
            reader = new PipedReader(readerPipe);
        }
        catch(IOException ioexception) { }
        super.initDebugger();
        client.addBOSHClientResponseListener(new BOSHClientResponseListener() {

            public void responseReceived(BOSHMessageEvent boshmessageevent)
            {
                if(boshmessageevent.getBody() == null)
                    break MISSING_BLOCK_LABEL_34;
                readerPipe.write(boshmessageevent.getBody().toXML());
                readerPipe.flush();
_L2:
                return;
                Exception exception;
                exception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final BOSHConnection this$0;

            
            {
                this$0 = BOSHConnection.this;
                super();
            }
        }
);
        client.addBOSHClientRequestListener(new BOSHClientRequestListener() {

            public void requestSent(BOSHMessageEvent boshmessageevent)
            {
                if(boshmessageevent.getBody() == null)
                    break MISSING_BLOCK_LABEL_24;
                writer.write(boshmessageevent.getBody().toXML());
_L2:
                return;
                Exception exception;
                exception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final BOSHConnection this$0;

            
            {
                this$0 = BOSHConnection.this;
                super();
            }
        }
);
        readerConsumer = new Thread() {

            public void run()
            {
                try
                {
                    char ac[] = new char[bufferLength];
                    while(readerConsumer == thread && !done) 
                        reader.read(ac, 0, bufferLength);
                }
                catch(IOException ioexception1) { }
            }

            private int bufferLength;
            final BOSHConnection this$0;
            private Thread thread;

            
            {
                this$0 = BOSHConnection.this;
                super();
                thread = this;
                bufferLength = 1024;
            }
        }
;
        readerConsumer.setDaemon(true);
        readerConsumer.start();
    }

    public boolean isAnonymous()
    {
        return anonymous;
    }

    public boolean isAuthenticated()
    {
        return authenticated;
    }

    public boolean isConnected()
    {
        return connected;
    }

    public boolean isSecureConnection()
    {
        return false;
    }

    public boolean isUsingCompression()
    {
        return false;
    }

    public void login(String s, String s1, String s2)
        throws XMPPException
    {
        if(!isConnected())
            throw new IllegalStateException("Not connected to server.");
        if(authenticated)
            throw new IllegalStateException("Already logged in to server.");
        String s3 = s.toLowerCase().trim();
        String s4;
        if(config.isSASLAuthenticationEnabled() && saslAuthentication.hasNonAnonymousAuthentication())
        {
            if(s1 != null)
                s4 = saslAuthentication.authenticate(s3, s1, s2);
            else
                s4 = saslAuthentication.authenticate(s3, s2, config.getCallbackHandler());
        } else
        {
            s4 = (new NonSASLAuthentication(this)).authenticate(s3, s1, s2);
        }
        if(s4 != null)
        {
            user = s4;
            config.setServiceName(StringUtils.parseServer(s4));
        } else
        {
            user = (new StringBuilder()).append(s3).append("@").append(getServiceName()).toString();
            if(s2 != null)
                user = (new StringBuilder()).append(user).append("/").append(s2).toString();
        }
        if(roster == null)
            if(rosterStorage == null)
                roster = new Roster(this);
            else
                roster = new Roster(this, rosterStorage);
        if(config.isRosterLoadedAtLogin())
            roster.reload();
        if(config.isSendPresence())
            sendPacket(new Presence(org.jivesoftware.smack.packet.Presence.Type.available));
        authenticated = true;
        anonymous = false;
        config.setLoginInfo(s3, s1, s2);
        if(config.isDebuggerEnabled() && debugger != null)
            debugger.userHasLogged(user);
    }

    public void loginAnonymously()
        throws XMPPException
    {
        if(!isConnected())
            throw new IllegalStateException("Not connected to server.");
        if(authenticated)
            throw new IllegalStateException("Already logged in to server.");
        String s;
        if(config.isSASLAuthenticationEnabled() && saslAuthentication.hasAnonymousAuthentication())
            s = saslAuthentication.authenticateAnonymously();
        else
            s = (new NonSASLAuthentication(this)).authenticateAnonymously();
        user = s;
        config.setServiceName(StringUtils.parseServer(s));
        roster = null;
        if(config.isSendPresence())
            sendPacket(new Presence(org.jivesoftware.smack.packet.Presence.Type.available));
        authenticated = true;
        anonymous = true;
        if(config.isDebuggerEnabled() && debugger != null)
            debugger.userHasLogged(user);
    }

    protected void notifyConnectionError(Exception exception)
    {
        shutdown(new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable));
        exception.printStackTrace();
        for(Iterator iterator = getConnectionListeners().iterator(); iterator.hasNext();)
        {
            ConnectionListener connectionlistener = (ConnectionListener)iterator.next();
            try
            {
                connectionlistener.connectionClosedOnError(exception);
            }
            catch(Exception exception1)
            {
                exception1.printStackTrace();
            }
        }

    }

    protected void processPacket(Packet packet)
    {
        if(packet != null)
        {
            for(Iterator iterator = getPacketCollectors().iterator(); iterator.hasNext(); ((PacketCollector)iterator.next()).processPacket(packet));
            listenerExecutor.submit(new ListenerNotification(packet));
        }
    }

    protected void send(ComposableBody composablebody)
        throws BOSHException
    {
        if(!connected)
            throw new IllegalStateException("Not connected to a server!");
        if(composablebody == null)
            throw new NullPointerException("Body mustn't be null!");
        ComposableBody composablebody1;
        if(sessionID != null)
            composablebody1 = composablebody.rebuild().setAttribute(BodyQName.create("http://jabber.org/protocol/httpbind", "sid"), sessionID).build();
        else
            composablebody1 = composablebody;
        client.send(composablebody1);
    }

    public void sendPacket(Packet packet)
    {
        if(!isConnected())
            throw new IllegalStateException("Not connected to server.");
        if(packet == null)
            throw new NullPointerException("Packet is null.");
        if(done)
            break MISSING_BLOCK_LABEL_67;
        firePacketInterceptors(packet);
        send(ComposableBody.builder().setPayloadXML(packet.toXML()).build());
        firePacketSendingListeners(packet);
_L2:
        return;
        BOSHException boshexception;
        boshexception;
        boshexception.printStackTrace();
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void setRosterStorage(RosterStorage rosterstorage)
        throws IllegalStateException
    {
        if(roster != null)
        {
            throw new IllegalStateException("Roster is already initialized");
        } else
        {
            rosterStorage = rosterstorage;
            return;
        }
    }

    protected void shutdown(Presence presence)
    {
        setWasAuthenticated(authenticated);
        authID = null;
        sessionID = null;
        done = true;
        authenticated = false;
        connected = false;
        isFirstInitialization = false;
        try
        {
            client.disconnect(ComposableBody.builder().setNamespaceDefinition("xmpp", "urn:xmpp:xbosh").setPayloadXML(presence.toXML()).build());
            Thread.sleep(150L);
        }
        catch(Exception exception) { }
        if(readerPipe != null)
        {
            Throwable throwable;
            Throwable throwable1;
            try
            {
                readerPipe.close();
            }
            catch(Throwable throwable2) { }
            reader = null;
        }
        if(reader != null)
        {
            try
            {
                reader.close();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable1) { }
            reader = null;
        }
        if(writer != null)
        {
            try
            {
                writer.close();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable) { }
            writer = null;
        }
        if(listenerExecutor != null)
            listenerExecutor.shutdown();
        readerConsumer = null;
    }

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





/*
    static boolean access$302(BOSHConnection boshconnection, boolean flag)
    {
        boshconnection.connected = flag;
        return flag;
    }

*/



/*
    static boolean access$402(BOSHConnection boshconnection, boolean flag)
    {
        boshconnection.isFirstInitialization = flag;
        return flag;
    }

*/


}

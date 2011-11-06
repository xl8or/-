// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookChatConnection.java

package com.facebook.katana.service.xmpp;

import android.os.Handler;
import android.os.SystemClock;
import com.facebook.katana.binding.WorkerThread;
import com.facebook.katana.util.Log;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

// Referenced classes of package com.facebook.katana.service.xmpp:
//            FacebookChatSocketFactory, FBChatAuthMechanism, FacebookChatConnectionListener, FacebookXmppPacket

public class FacebookChatConnection
{
    private static final class CONNECTION_STATUS extends Enum
    {

        public static CONNECTION_STATUS valueOf(String s)
        {
            return (CONNECTION_STATUS)Enum.valueOf(com/facebook/katana/service/xmpp/FacebookChatConnection$CONNECTION_STATUS, s);
        }

        public static CONNECTION_STATUS[] values()
        {
            return (CONNECTION_STATUS[])$VALUES.clone();
        }

        private static final CONNECTION_STATUS $VALUES[];
        public static final CONNECTION_STATUS CONNECTED;
        public static final CONNECTION_STATUS CONNECTING;
        public static final CONNECTION_STATUS DISCONNECTED;
        public static final CONNECTION_STATUS NEW;

        static 
        {
            NEW = new CONNECTION_STATUS("NEW", 0);
            CONNECTING = new CONNECTION_STATUS("CONNECTING", 1);
            CONNECTED = new CONNECTION_STATUS("CONNECTED", 2);
            DISCONNECTED = new CONNECTION_STATUS("DISCONNECTED", 3);
            CONNECTION_STATUS aconnection_status[] = new CONNECTION_STATUS[4];
            aconnection_status[0] = NEW;
            aconnection_status[1] = CONNECTING;
            aconnection_status[2] = CONNECTED;
            aconnection_status[3] = DISCONNECTED;
            $VALUES = aconnection_status;
        }

        private CONNECTION_STATUS(String s, int i)
        {
            super(s, i);
        }
    }


    public FacebookChatConnection(String s, String s1, boolean flag)
    {
        mSessionKey = s;
        mSessionSecret = s1;
        mRunSafe = flag;
        ConnectionConfiguration connectionconfiguration = new ConnectionConfiguration("chat.facebook.com", 5222);
        connectionconfiguration.setSocketFactory(new FacebookChatSocketFactory());
        connectionconfiguration.setRosterLoadedAtLogin(false);
        connectionconfiguration.setSendPresence(true);
        mConnection = new XMPPConnection(connectionconfiguration);
        Connection.addConnectionCreationListener(connectionInstaller);
        PacketTypeFilter packettypefilter = new PacketTypeFilter(org/jivesoftware/smack/packet/Packet);
        mConnection.addPacketListener(mPacketListener, packettypefilter);
        mState = CONNECTION_STATUS.NEW;
        SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM", com/facebook/katana/service/xmpp/FBChatAuthMechanism);
        SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);
    }

    private void announceConnectionEstablished()
    {
        mListenerHandler.post(new Runnable() {

            public void run()
            {
                if(mState == CONNECTION_STATUS.CONNECTING)
                {
                    mState = CONNECTION_STATUS.CONNECTED;
                    for(Iterator iterator = mConnectionListeners.iterator(); iterator.hasNext(); ((FacebookChatConnectionListener)iterator.next()).onConnectionEstablished());
                }
            }

            final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
        }
);
    }

    private void announceConnectionPaused()
    {
        mListenerHandler.post(new Runnable() {

            public void run()
            {
                if(mState == CONNECTION_STATUS.CONNECTED)
                {
                    mState = CONNECTION_STATUS.CONNECTING;
                    for(Iterator iterator = mConnectionListeners.iterator(); iterator.hasNext(); ((FacebookChatConnectionListener)iterator.next()).onConnectionPaused());
                }
            }

            final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
        }
);
    }

    private void announceConnectionStopped()
    {
        mListenerHandler.post(new Runnable() {

            public void run()
            {
                if(mState != CONNECTION_STATUS.DISCONNECTED)
                    mState = CONNECTION_STATUS.CONNECTING;
                for(Iterator iterator = mConnectionListeners.iterator(); iterator.hasNext(); ((FacebookChatConnectionListener)iterator.next()).onConnectionStopped());
            }

            final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
        }
);
        mConnectionThread.getThreadHandler().removeCallbacks(mSendKeepAlive);
        mConnectionThread.getThreadHandler().removeCallbacks(mCheckConnection);
    }

    private void verifyAlive()
    {
        if(!$assertionsDisabled && mState == CONNECTION_STATUS.DISCONNECTED)
            throw new AssertionError();
        else
            return;
    }

    /**
     * @deprecated Method addConnectionListener is deprecated
     */

    public void addConnectionListener(FacebookChatConnectionListener facebookchatconnectionlistener)
    {
        this;
        JVM INSTR monitorenter ;
        verifyAlive();
        mConnectionListeners.add(facebookchatconnectionlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addPacketListener(PacketListener packetlistener, PacketFilter packetfilter)
    {
        verifyAlive();
        mPacketListeners.put(packetlistener, packetfilter);
    }

    public void connect()
    {
        verifyAlive();
        if(mState != CONNECTION_STATUS.NEW) goto _L2; else goto _L1
_L1:
        mState = CONNECTION_STATUS.CONNECTING;
        reconnectionAttempt.set(0);
        mConnectionThread.getThreadHandler().post(mConnectToServer);
_L4:
        return;
_L2:
        if(mState == CONNECTION_STATUS.CONNECTING)
            reconnectionAttempt.set(0);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void disconnect()
    {
        verifyAlive();
        mConnection.removePacketListener(mPacketListener);
        mConnection.removeConnectionListener(mConnectionListener);
        Connection.removeConnectionCreationListener(connectionInstaller);
        mConnectionListeners.clear();
        mPacketListeners.clear();
        mState = CONNECTION_STATUS.DISCONNECTED;
        mConnectionThread.getThreadHandler().removeCallbacks(mSendKeepAlive);
        mConnectionThread.getThreadHandler().removeCallbacks(mCheckConnection);
        mConnectionThread.getThreadHandler().removeCallbacks(mConnectToServer);
        mConnectionThread.getThreadHandler().post(new Runnable() {

            public void run()
            {
                mConnection.disconnect(new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable));
                mConnectionThread.quit();
                mConnection = null;
            }

            final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
        }
);
    }

    public Handler getWorkerHandler()
    {
        Handler handler;
        if(mConnectionThread != null)
            handler = mConnectionThread.getThreadHandler();
        else
            handler = null;
        return handler;
    }

    public boolean isConnected()
    {
        boolean flag;
        if(mState == CONNECTION_STATUS.CONNECTED)
            flag = true;
        else
            flag = false;
        return flag;
    }

    /**
     * @deprecated Method removeConnectionListener is deprecated
     */

    public void removeConnectionListener(FacebookChatConnectionListener facebookchatconnectionlistener)
    {
        this;
        JVM INSTR monitorenter ;
        verifyAlive();
        mConnectionListeners.remove(facebookchatconnectionlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void removePacketListener(PacketListener packetlistener)
    {
        verifyAlive();
        mPacketListeners.remove(packetlistener);
    }

    public void sendPacket(final Packet p)
    {
        if(p != null)
            mConnectionThread.getThreadHandler().post(new Runnable() {

                public void run()
                {
                    if(!isConnected())
                        break MISSING_BLOCK_LABEL_50;
                    mConnection.sendPacket(p);
_L1:
                    return;
                    Exception exception;
                    exception;
                    pendingQueue.offer(p);
                    exception.printStackTrace();
                      goto _L1
                    pendingQueue.offer(p);
                      goto _L1
                }

                final FacebookChatConnection this$0;
                final Packet val$p;

            
            {
                this$0 = FacebookChatConnection.this;
                p = packet;
                super();
            }
            }
);
    }

    static final boolean $assertionsDisabled = false;
    private static final String JABBER_RESOURCE = "fbandroid";
    public static final int KEEPALIVE_FREQ_MSEC = 20000;
    public static final int KEEPALIVE_TIMEOUT_MSEC = 5000;
    public static final int RECONNECTION_TIMEOUT_MSEC = 10000;
    private static final String TAG = "FacebookChatConnection";
    private static final AtomicInteger reconnectionAttempt = new AtomicInteger(0);
    private final ConnectionCreationListener connectionInstaller = new ConnectionCreationListener() {

        public void connectionCreated(Connection connection)
        {
            connection.addConnectionListener(mConnectionListener);
        }

        final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
    }
;
    private final Runnable mCheckConnection = new Runnable() {

        public void run()
        {
            if(SystemClock.uptimeMillis() > 5000L + (20000L + mLastReceived))
                announceConnectionPaused();
        }

        final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
    }
;
    private final Runnable mConnectToServer = new Runnable() {

        private void doConnect()
        {
            if(mState != CONNECTION_STATUS.DISCONNECTED && mState != CONNECTION_STATUS.CONNECTED)
                try
                {
                    if(!mConnection.isConnected())
                        mConnection.connect();
                    if(!mConnection.isAuthenticated())
                        mConnection.login(mSessionKey, mSessionSecret, "fbandroid");
                    announceConnectionEstablished();
                    mConnectionThread.getThreadHandler().postDelayed(mSendKeepAlive, 20000L);
                    sendPendingPackets();
                }
                catch(XMPPException xmppexception)
                {
                    announceConnectionStopped();
                    mConnectionThread.getThreadHandler().postDelayed(this, 10000 * FacebookChatConnection.reconnectionAttempt.incrementAndGet());
                }
                catch(IllegalStateException illegalstateexception)
                {
                    announceConnectionStopped();
                    mConnectionThread.getThreadHandler().postDelayed(this, 10000 * FacebookChatConnection.reconnectionAttempt.incrementAndGet());
                }
        }

        private void sendPendingPackets()
        {
            do
            {
                Packet packet = (Packet)pendingQueue.poll();
                if(packet != null)
                    mConnection.sendPacket(packet);
                else
                    return;
            } while(true);
        }

        public void run()
        {
            if(!mRunSafe)
                break MISSING_BLOCK_LABEL_34;
            doConnect();
_L1:
            return;
            Exception exception;
            exception;
            announceConnectionStopped();
            Log.d("FacebookChatConnection", "E X C E P T I O N", exception);
              goto _L1
            doConnect();
              goto _L1
        }

        final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
    }
;
    private XMPPConnection mConnection;
    private final ConnectionListener mConnectionListener = new ConnectionListener() {

        public void connectionClosed()
        {
        }

        public void connectionClosedOnError(Exception exception)
        {
            announceConnectionStopped();
            mConnectionThread.getThreadHandler().post(mConnectToServer);
        }

        public void reconnectingIn(int i)
        {
        }

        public void reconnectionFailed(Exception exception)
        {
        }

        public void reconnectionSuccessful()
        {
            announceConnectionEstablished();
            FacebookChatConnection.reconnectionAttempt.set(0);
        }

        final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
    }
;
    private final List mConnectionListeners = new ArrayList();
    private final WorkerThread mConnectionThread = new WorkerThread();
    private volatile long mLastReceived;
    private final Handler mListenerHandler = new Handler();
    private final PacketListener mPacketListener = new PacketListener() {

        public void processPacket(final Packet p)
        {
            mLastReceived = SystemClock.uptimeMillis();
            announceConnectionEstablished();
            mListenerHandler.post(new Runnable() {

                public void run()
                {
                    Iterator iterator = mPacketListeners.keySet().iterator();
                    do
                    {
                        if(!iterator.hasNext())
                            break;
                        PacketListener packetlistener = (PacketListener)iterator.next();
                        if(((PacketFilter)mPacketListeners.get(packetlistener)).accept(p))
                            packetlistener.processPacket(p);
                    } while(true);
                }

                final _cls8 this$1;
                final Packet val$p;

                    
                    {
                        this$1 = _cls8.this;
                        p = packet;
                        super();
                    }
            }
);
        }

        final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
            }
    }
;
    private final Map mPacketListeners = new HashMap();
    private final boolean mRunSafe;
    private final Runnable mSendKeepAlive = new Runnable() {

        public void run()
        {
            long l = SystemClock.uptimeMillis();
            if(mConnection.isConnected() && mConnection.isAuthenticated() && 5000L + mLastReceived < l)
                sendPacket(keepAlive);
            if(mConnectionThread.getThreadHandler().postDelayed(mSendKeepAlive, 20000L))
                mConnectionThread.getThreadHandler().postDelayed(mCheckConnection, 5000L);
        }

        private final FacebookXmppPacket keepAlive;
        final FacebookChatConnection this$0;

            
            {
                this$0 = FacebookChatConnection.this;
                super();
                keepAlive = new FacebookXmppPacket(FacebookXmppPacket.PacketType.CONNECT_TEST);
            }
    }
;
    private final String mSessionKey;
    private final String mSessionSecret;
    private CONNECTION_STATUS mState;
    private final BlockingQueue pendingQueue = new LinkedBlockingQueue();

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/xmpp/FacebookChatConnection.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }





/*
    static long access$1002(FacebookChatConnection facebookchatconnection, long l)
    {
        facebookchatconnection.mLastReceived = l;
        return l;
    }

*/


/*
    static XMPPConnection access$102(FacebookChatConnection facebookchatconnection, XMPPConnection xmppconnection)
    {
        facebookchatconnection.mConnection = xmppconnection;
        return xmppconnection;
    }

*/













/*
    static CONNECTION_STATUS access$402(FacebookChatConnection facebookchatconnection, CONNECTION_STATUS connection_status)
    {
        facebookchatconnection.mState = connection_status;
        return connection_status;
    }

*/





}

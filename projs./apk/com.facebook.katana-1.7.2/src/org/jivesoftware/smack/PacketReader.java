// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketReader.java

package org.jivesoftware.smack;

import java.util.*;
import java.util.concurrent.*;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.xmlpull.v1.*;

// Referenced classes of package org.jivesoftware.smack:
//            XMPPConnection, SASLAuthentication, ConnectionConfiguration, AccountManager, 
//            XMPPException, PacketWriter, PacketCollector, ConnectionListener, 
//            SmackConfiguration

class PacketReader
{
    private class ListenerNotification
        implements Runnable
    {

        public void run()
        {
            for(Iterator iterator = connection.recvListeners.values().iterator(); iterator.hasNext(); ((Connection.ListenerWrapper)iterator.next()).notifyListener(packet));
        }

        private Packet packet;
        final PacketReader this$0;

        public ListenerNotification(Packet packet1)
        {
            this$0 = PacketReader.this;
            super();
            packet = packet1;
        }
    }


    protected PacketReader(XMPPConnection xmppconnection)
    {
        connectionID = null;
        connection = xmppconnection;
        init();
    }

    private void parseFeatures(XmlPullParser xmlpullparser)
        throws Exception
    {
        boolean flag = false;
        boolean flag1 = flag;
        boolean flag2 = flag;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("starttls"))
                    flag2 = true;
                else
                if(xmlpullparser.getName().equals("mechanisms"))
                    connection.getSASLAuthentication().setAvailableSASLMethods(PacketParserUtils.parseMechanisms(xmlpullparser));
                else
                if(xmlpullparser.getName().equals("bind"))
                    connection.getSASLAuthentication().bindingRequired();
                else
                if(xmlpullparser.getName().equals("ver"))
                    connection.getConfiguration().setRosterVersioningAvailable(true);
                else
                if(xmlpullparser.getName().equals("c"))
                {
                    String s = xmlpullparser.getAttributeValue(null, "node");
                    String s1 = xmlpullparser.getAttributeValue(null, "ver");
                    String s2 = (new StringBuilder()).append(s).append("#").append(s1).toString();
                    connection.getConfiguration().setCapsNode(s2);
                } else
                if(xmlpullparser.getName().equals("session"))
                    connection.getSASLAuthentication().sessionsSupported();
                else
                if(xmlpullparser.getName().equals("compression"))
                    connection.setAvailableCompressionMethods(PacketParserUtils.parseCompressionMethods(xmlpullparser));
                else
                if(xmlpullparser.getName().equals("register"))
                    connection.getAccountManager().setSupportsAccountCreation(true);
            } else
            if(i == 3)
                if(xmlpullparser.getName().equals("starttls"))
                    connection.startTLSReceived(flag1);
                else
                if(xmlpullparser.getName().equals("required") && flag2)
                    flag1 = true;
                else
                if(xmlpullparser.getName().equals("features"))
                    flag = true;
        } while(true);
        if(!connection.isSecureConnection() && !flag2 && connection.getConfiguration().getSecurityMode() == ConnectionConfiguration.SecurityMode.required)
            throw new XMPPException("Server does not support security (TLS), but security required by connection configuration.", new XMPPError(org.jivesoftware.smack.packet.XMPPError.Condition.forbidden));
        if(!flag2 || connection.getConfiguration().getSecurityMode() == ConnectionConfiguration.SecurityMode.disabled)
            releaseConnectionIDLock();
    }

    private void parsePackets(Thread thread)
    {
        int i = parser.getEventType();
_L6:
        if(i != 2) goto _L2; else goto _L1
_L1:
        if(!parser.getName().equals("message")) goto _L4; else goto _L3
_L3:
        processPacket(PacketParserUtils.parseMessage(parser));
_L9:
        i = parser.next();
        if(!done && i != 1 && thread == readerThread) goto _L6; else goto _L5
_L4:
        if(!parser.getName().equals("iq")) goto _L8; else goto _L7
_L7:
        processPacket(PacketParserUtils.parseIQ(parser, connection));
          goto _L9
        Exception exception;
        exception;
        if(!done)
            notifyConnectionError(exception);
          goto _L5
_L8:
label0:
        {
            if(!parser.getName().equals("presence"))
                break label0;
            processPacket(PacketParserUtils.parsePresence(parser));
        }
          goto _L9
        if(!parser.getName().equals("stream")) goto _L11; else goto _L10
_L10:
        if(!"jabber:client".equals(parser.getNamespace(null))) goto _L9; else goto _L12
_L12:
        int j = 0;
_L13:
        if(j < parser.getAttributeCount())
        {
            if(parser.getAttributeName(j).equals("id"))
            {
                connectionID = parser.getAttributeValue(j);
                if(!"1.0".equals(parser.getAttributeValue("", "version")))
                    releaseConnectionIDLock();
            } else
            if(parser.getAttributeName(j).equals("from"))
                connection.config.setServiceName(parser.getAttributeValue(j));
            break MISSING_BLOCK_LABEL_694;
        }
          goto _L9
_L11:
        if(parser.getName().equals("error"))
            throw new XMPPException(PacketParserUtils.parseStreamError(parser));
        if(parser.getName().equals("features"))
            parseFeatures(parser);
        else
        if(parser.getName().equals("proceed"))
        {
            connection.proceedTLSReceived();
            resetParser();
        } else
        if(parser.getName().equals("failure"))
        {
            String s1 = parser.getNamespace(null);
            if("urn:ietf:params:xml:ns:xmpp-tls".equals(s1))
                throw new Exception("TLS negotiation has failed");
            if("http://jabber.org/protocol/compress".equals(s1))
            {
                connection.streamCompressionDenied();
            } else
            {
                processPacket(PacketParserUtils.parseSASLFailure(parser));
                connection.getSASLAuthentication().authenticationFailed();
            }
        } else
        if(parser.getName().equals("challenge"))
        {
            String s = parser.nextText();
            processPacket(new org.jivesoftware.smack.sasl.SASLMechanism.Challenge(s));
            connection.getSASLAuthentication().challengeReceived(s);
        } else
        if(parser.getName().equals("success"))
        {
            processPacket(new org.jivesoftware.smack.sasl.SASLMechanism.Success(parser.nextText()));
            connection.packetWriter.openStream();
            resetParser();
            connection.getSASLAuthentication().authenticated();
        } else
        if(parser.getName().equals("compressed"))
        {
            connection.startStreamCompression();
            resetParser();
        }
          goto _L9
_L2:
        if(i == 3 && parser.getName().equals("stream"))
            connection.disconnect();
          goto _L9
_L5:
        return;
        j++;
          goto _L13
    }

    private void processPacket(Packet packet)
    {
        if(packet != null)
        {
            for(Iterator iterator = connection.getPacketCollectors().iterator(); iterator.hasNext(); ((PacketCollector)iterator.next()).processPacket(packet));
            listenerExecutor.submit(new ListenerNotification(packet));
        }
    }

    private void releaseConnectionIDLock()
    {
        connectionSemaphore.release();
    }

    private void resetParser()
    {
        parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        parser.setInput(connection.reader);
_L1:
        return;
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        xmlpullparserexception.printStackTrace();
          goto _L1
    }

    void cleanup()
    {
        connection.recvListeners.clear();
        connection.collectors.clear();
    }

    protected void init()
    {
        done = false;
        connectionID = null;
        readerThread = new Thread() {

            public void run()
            {
                parsePackets(this);
            }

            final PacketReader this$0;

            
            {
                this$0 = PacketReader.this;
                super();
            }
        }
;
        readerThread.setName((new StringBuilder()).append("Smack Packet Reader (").append(connection.connectionCounterValue).append(")").toString());
        readerThread.setDaemon(true);
        listenerExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {

            public Thread newThread(Runnable runnable)
            {
                Thread thread = new Thread(runnable, (new StringBuilder()).append("Smack Listener Processor (").append(connection.connectionCounterValue).append(")").toString());
                thread.setDaemon(true);
                return thread;
            }

            final PacketReader this$0;

            
            {
                this$0 = PacketReader.this;
                super();
            }
        }
);
        resetParser();
    }

    void notifyConnectionError(Exception exception)
    {
        done = true;
        connection.shutdown(new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable));
        exception.printStackTrace();
        for(Iterator iterator = connection.getConnectionListeners().iterator(); iterator.hasNext();)
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

    protected void notifyReconnection()
    {
        for(Iterator iterator = connection.getConnectionListeners().iterator(); iterator.hasNext();)
        {
            ConnectionListener connectionlistener = (ConnectionListener)iterator.next();
            try
            {
                connectionlistener.reconnectionSuccessful();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }

    }

    public void shutdown()
    {
        if(!done)
        {
            for(Iterator iterator = connection.getConnectionListeners().iterator(); iterator.hasNext();)
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
        done = true;
        listenerExecutor.shutdown();
    }

    public void startup()
        throws XMPPException
    {
        connectionSemaphore = new Semaphore(1);
        readerThread.start();
        try
        {
            connectionSemaphore.acquire();
            int i = SmackConfiguration.getPacketReplyTimeout();
            connectionSemaphore.tryAcquire(i * 3, TimeUnit.MILLISECONDS);
        }
        catch(InterruptedException interruptedexception) { }
        if(connectionID == null)
        {
            throw new XMPPException("Connection failed. No response from server.");
        } else
        {
            connection.connectionID = connectionID;
            return;
        }
    }

    private XMPPConnection connection;
    private String connectionID;
    private Semaphore connectionSemaphore;
    private boolean done;
    private ExecutorService listenerExecutor;
    private XmlPullParser parser;
    private Thread readerThread;


}

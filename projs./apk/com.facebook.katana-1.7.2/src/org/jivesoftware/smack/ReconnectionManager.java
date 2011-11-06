// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReconnectionManager.java

package org.jivesoftware.smack;

import java.util.Collection;
import java.util.Iterator;
import org.jivesoftware.smack.packet.StreamError;

// Referenced classes of package org.jivesoftware.smack:
//            ConnectionListener, Connection, XMPPException, ConnectionCreationListener

public class ReconnectionManager
    implements ConnectionListener
{

    private ReconnectionManager(Connection connection1)
    {
        done = false;
        connection = connection1;
    }


    private boolean isReconnectionAllowed()
    {
        boolean flag;
        if(!done && !connection.isConnected() && connection.isReconnectionAllowed())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void connectionClosed()
    {
        done = true;
    }

    public void connectionClosedOnError(Exception exception)
    {
        done = false;
        if(!(exception instanceof XMPPException)) goto _L2; else goto _L1
_L1:
        StreamError streamerror = ((XMPPException)exception).getStreamError();
        if(streamerror == null || !"conflict".equals(streamerror.getCode())) goto _L2; else goto _L3
_L3:
        return;
_L2:
        if(isReconnectionAllowed())
            reconnect();
        if(true) goto _L3; else goto _L4
_L4:
    }

    protected void notifyAttemptToReconnectIn(int i)
    {
        if(isReconnectionAllowed())
        {
            for(Iterator iterator = connection.connectionListeners.iterator(); iterator.hasNext(); ((ConnectionListener)iterator.next()).reconnectingIn(i));
        }
    }

    protected void notifyReconnectionFailed(Exception exception)
    {
        if(isReconnectionAllowed())
        {
            for(Iterator iterator = connection.connectionListeners.iterator(); iterator.hasNext(); ((ConnectionListener)iterator.next()).reconnectionFailed(exception));
        }
    }

    protected void reconnect()
    {
        if(isReconnectionAllowed())
        {
            Thread thread = new Thread() {

                private int timeDelay()
                {
                    char c;
                    if(attempts > 13)
                        c = '\u012C';
                    else
                    if(attempts > 7)
                        c = '<';
                    else
                        c = '\n';
                    return c;
                }

                public void run()
                {
                    do
                    {
                        if(!isReconnectionAllowed())
                            break;
                        for(int i = timeDelay(); isReconnectionAllowed() && i > 0;)
                            try
                            {
                                Thread.sleep(1000L);
                                i--;
                                notifyAttemptToReconnectIn(i);
                            }
                            catch(InterruptedException interruptedexception)
                            {
                                int j = i;
                                interruptedexception.printStackTrace();
                                notifyReconnectionFailed(interruptedexception);
                                i = j;
                            }

                        try
                        {
                            if(isReconnectionAllowed())
                                connection.connect();
                        }
                        catch(XMPPException xmppexception)
                        {
                            notifyReconnectionFailed(xmppexception);
                        }
                    } while(true);
                }

                private int attempts;
                final ReconnectionManager this$0;

            
            {
                this$0 = ReconnectionManager.this;
                super();
                attempts = 0;
            }
            }
;
            thread.setName("Smack Reconnection Manager");
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void reconnectingIn(int i)
    {
    }

    public void reconnectionFailed(Exception exception)
    {
    }

    public void reconnectionSuccessful()
    {
    }

    private Connection connection;
    boolean done;

    static 
    {
        Connection.addConnectionCreationListener(new ConnectionCreationListener() {

            public void connectionCreated(Connection connection1)
            {
                connection1.addConnectionListener(new ReconnectionManager(connection1));
            }

        }
);
    }


}

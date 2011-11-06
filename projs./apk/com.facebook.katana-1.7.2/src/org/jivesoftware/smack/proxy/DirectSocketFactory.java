// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DirectSocketFactory.java

package org.jivesoftware.smack.proxy;

import java.io.IOException;
import java.net.*;
import javax.net.SocketFactory;

class DirectSocketFactory extends SocketFactory
{

    public DirectSocketFactory()
    {
    }

    public Socket createSocket(String s, int i)
        throws IOException, UnknownHostException
    {
        Socket socket = new Socket(Proxy.NO_PROXY);
        socket.connect(new InetSocketAddress(s, i));
        return socket;
    }

    public Socket createSocket(String s, int i, InetAddress inetaddress, int j)
        throws IOException, UnknownHostException
    {
        return new Socket(s, i, inetaddress, j);
    }

    public Socket createSocket(InetAddress inetaddress, int i)
        throws IOException
    {
        Socket socket = new Socket(Proxy.NO_PROXY);
        socket.connect(new InetSocketAddress(inetaddress, i));
        return socket;
    }

    public Socket createSocket(InetAddress inetaddress, int i, InetAddress inetaddress1, int j)
        throws IOException
    {
        return new Socket(inetaddress, i, inetaddress1, j);
    }
}

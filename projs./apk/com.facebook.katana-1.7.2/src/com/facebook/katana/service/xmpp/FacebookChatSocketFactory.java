// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookChatSocketFactory.java

package com.facebook.katana.service.xmpp;

import java.io.IOException;
import java.net.*;
import javax.net.SocketFactory;

public class FacebookChatSocketFactory extends SocketFactory
{

    FacebookChatSocketFactory()
    {
    }

    private Socket addChatSocketOptions(Socket socket)
        throws SocketException
    {
        socket.setSoTimeout(40000);
        return socket;
    }

    public Socket createSocket()
        throws IOException
    {
        return addChatSocketOptions(mySocketFactory.createSocket());
    }

    public Socket createSocket(String s, int i)
        throws IOException
    {
        return addChatSocketOptions(mySocketFactory.createSocket(s, i));
    }

    public Socket createSocket(String s, int i, InetAddress inetaddress, int j)
        throws IOException, UnknownHostException
    {
        return addChatSocketOptions(mySocketFactory.createSocket(s, i));
    }

    public Socket createSocket(InetAddress inetaddress, int i)
        throws IOException
    {
        return addChatSocketOptions(mySocketFactory.createSocket(inetaddress, i));
    }

    public Socket createSocket(InetAddress inetaddress, int i, InetAddress inetaddress1, int j)
        throws IOException
    {
        return addChatSocketOptions(mySocketFactory.createSocket(inetaddress, i));
    }

    private final SocketFactory mySocketFactory = SocketFactory.getDefault();
}

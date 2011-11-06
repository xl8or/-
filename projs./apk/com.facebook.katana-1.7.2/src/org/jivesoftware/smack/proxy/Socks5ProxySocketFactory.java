// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Socks5ProxySocketFactory.java

package org.jivesoftware.smack.proxy;

import java.io.*;
import java.net.*;
import javax.net.SocketFactory;

// Referenced classes of package org.jivesoftware.smack.proxy:
//            ProxyException, ProxyInfo

public class Socks5ProxySocketFactory extends SocketFactory
{

    public Socks5ProxySocketFactory(ProxyInfo proxyinfo)
    {
        proxy = proxyinfo;
    }

    private void fill(InputStream inputstream, byte abyte0[], int i)
        throws IOException
    {
        int k;
        for(int j = 0; j < i; j += k)
        {
            k = inputstream.read(abyte0, j, i - j);
            if(k <= 0)
                throw new ProxyException(ProxyInfo.ProxyType.SOCKS5, "stream is closed");
        }

    }

    private Socket socks5ProxifiedSocket(String s, int i)
        throws IOException
    {
        String s1;
        int j;
        String s2;
        String s3;
        s1 = proxy.getProxyAddress();
        j = proxy.getProxyPort();
        s2 = proxy.getProxyUsername();
        s3 = proxy.getProxyPassword();
        Socket socket = new Socket(s1, j);
        InputStream inputstream;
        OutputStream outputstream;
        byte abyte0[];
        byte byte0;
        inputstream = socket.getInputStream();
        outputstream = socket.getOutputStream();
        socket.setTcpNoDelay(true);
        abyte0 = new byte[1024];
        int k = 0 + 1;
        abyte0[0] = 5;
        int l = k + 1;
        abyte0[k] = 2;
        int i1 = l + 1;
        abyte0[l] = 0;
        int j1 = i1 + 1;
        abyte0[i1] = 2;
        outputstream.write(abyte0, 0, j1);
        fill(inputstream, abyte0, 2);
        byte0 = abyte0[1];
        byte0 & 0xff;
        JVM INSTR tableswitch 0 2: default 176
    //                   0 207
    //                   1 176
    //                   2 213;
           goto _L1 _L2 _L1 _L3
_L1:
        boolean flag = false;
_L6:
        if(flag) goto _L5; else goto _L4
_L4:
        Exception exception;
        Socket socket1;
        RuntimeException runtimeexception;
        int k1;
        byte byte1;
        try
        {
            socket.close();
        }
        catch(Exception exception3) { }
        throw new ProxyException(ProxyInfo.ProxyType.SOCKS5, "fail in SOCKS5 proxy");
        runtimeexception;
        throw runtimeexception;
_L2:
        flag = true;
          goto _L6
_L3:
        if(s2 == null) goto _L1; else goto _L7
_L7:
        if(s3 != null) goto _L9; else goto _L8
_L8:
        flag = false;
          goto _L6
_L9:
        k1 = 0 + 1;
        abyte0[0] = 1;
        int l1 = k1 + 1;
        abyte0[k1] = (byte)s2.length();
        System.arraycopy(s2.getBytes(), 0, abyte0, l1, s2.length());
        int i2 = 2 + s2.length();
        int j2 = i2 + 1;
        abyte0[i2] = (byte)s3.length();
        System.arraycopy(s3.getBytes(), 0, abyte0, j2, s3.length());
        outputstream.write(abyte0, 0, j2 + s3.length());
        fill(inputstream, abyte0, 2);
        if(abyte0[1] != 0) goto _L1; else goto _L10
_L10:
        flag = true;
          goto _L6
_L5:
        int k2 = 0 + 1;
        abyte0[0] = 5;
        int l2 = k2 + 1;
        abyte0[k2] = 1;
        int i3 = l2 + 1;
        abyte0[l2] = 0;
        byte abyte1[] = s.getBytes();
        int j3 = abyte1.length;
        int k3 = i3 + 1;
        abyte0[i3] = 3;
        int l3 = k3 + 1;
        abyte0[k3] = (byte)j3;
        System.arraycopy(abyte1, 0, abyte0, l3, j3);
        int i4 = j3 + 5;
        int j4 = i4 + 1;
        abyte0[i4] = (byte)(i >>> 8);
        int k4 = j4 + 1;
        abyte0[j4] = (byte)(i & 0xff);
        outputstream.write(abyte0, 0, k4);
        fill(inputstream, abyte0, 4);
        byte1 = abyte0[1];
        if(byte1 == 0) goto _L12; else goto _L11
_L11:
        try
        {
            socket.close();
        }
        catch(Exception exception2) { }
        throw new ProxyException(ProxyInfo.ProxyType.SOCKS5, (new StringBuilder()).append("server returns ").append(abyte0[1]).toString());
        exception;
        socket1 = socket;
_L14:
        String s4;
        if(socket1 != null)
            try
            {
                socket1.close();
            }
            catch(Exception exception1) { }
        s4 = (new StringBuilder()).append("ProxySOCKS5: ").append(exception.toString()).toString();
        if(exception instanceof Throwable)
            throw new ProxyException(ProxyInfo.ProxyType.SOCKS5, s4, exception);
        else
            throw new IOException(s4);
_L12:
        switch(0xff & abyte0[3])
        {
        case 1: // '\001'
            fill(inputstream, abyte0, 6);
            break;

        case 3: // '\003'
            fill(inputstream, abyte0, 1);
            fill(inputstream, abyte0, 2 + (0xff & abyte0[0]));
            break;

        case 4: // '\004'
            fill(inputstream, abyte0, 18);
            break;
        }
        break; /* Loop/switch isn't completed */
        Exception exception4;
        exception4;
        socket1 = null;
        exception = exception4;
        if(true) goto _L14; else goto _L13
_L13:
        return socket;
    }

    public Socket createSocket(String s, int i)
        throws IOException, UnknownHostException
    {
        return socks5ProxifiedSocket(s, i);
    }

    public Socket createSocket(String s, int i, InetAddress inetaddress, int j)
        throws IOException, UnknownHostException
    {
        return socks5ProxifiedSocket(s, i);
    }

    public Socket createSocket(InetAddress inetaddress, int i)
        throws IOException
    {
        return socks5ProxifiedSocket(inetaddress.getHostAddress(), i);
    }

    public Socket createSocket(InetAddress inetaddress, int i, InetAddress inetaddress1, int j)
        throws IOException
    {
        return socks5ProxifiedSocket(inetaddress.getHostAddress(), i);
    }

    private ProxyInfo proxy;
}

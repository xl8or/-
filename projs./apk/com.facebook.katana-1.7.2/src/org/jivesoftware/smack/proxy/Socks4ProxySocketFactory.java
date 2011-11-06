// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Socks4ProxySocketFactory.java

package org.jivesoftware.smack.proxy;

import java.io.*;
import java.net.*;
import javax.net.SocketFactory;

// Referenced classes of package org.jivesoftware.smack.proxy:
//            ProxyInfo, ProxyException

public class Socks4ProxySocketFactory extends SocketFactory
{

    public Socks4ProxySocketFactory(ProxyInfo proxyinfo)
    {
        proxy = proxyinfo;
    }

    private Socket socks4ProxifiedSocket(String s, int i)
        throws IOException
    {
        String s1;
        int j;
        String s2;
        s1 = proxy.getProxyAddress();
        j = proxy.getProxyPort();
        s2 = proxy.getProxyUsername();
        proxy.getProxyPassword();
        Socket socket = new Socket(s1, j);
        InputStream inputstream;
        OutputStream outputstream;
        byte abyte0[];
        int j1;
        inputstream = socket.getInputStream();
        outputstream = socket.getOutputStream();
        socket.setTcpNoDelay(true);
        abyte0 = new byte[1024];
        int k = 0 + 1;
        abyte0[0] = 4;
        int l = k + 1;
        abyte0[k] = 1;
        int i1 = l + 1;
        abyte0[l] = (byte)(i >>> 8);
        j1 = i1 + 1;
        abyte0[i1] = (byte)(i & 0xff);
        byte abyte1[];
        int k1;
        int l1;
        abyte1 = InetAddress.getByName(s).getAddress();
        k1 = j1;
        l1 = 0;
_L1:
        int i3;
        if(l1 >= abyte1.length)
            break MISSING_BLOCK_LABEL_203;
        i3 = k1 + 1;
        abyte0[k1] = abyte1[l1];
        l1++;
        k1 = i3;
          goto _L1
        UnknownHostException unknownhostexception;
        unknownhostexception;
        throw new ProxyException(ProxyInfo.ProxyType.SOCKS4, unknownhostexception.toString(), unknownhostexception);
        RuntimeException runtimeexception;
        runtimeexception;
        throw runtimeexception;
        if(s2 == null)
            break MISSING_BLOCK_LABEL_475;
        int i2;
        System.arraycopy(s2.getBytes(), 0, abyte0, k1, s2.length());
        i2 = k1 + s2.length();
_L7:
        int k2;
        int j2 = i2 + 1;
        abyte0[i2] = 0;
        outputstream.write(abyte0, 0, j2);
        k2 = 0;
_L5:
        if(k2 >= 6) goto _L3; else goto _L2
_L2:
        int l2;
        l2 = inputstream.read(abyte0, k2, 6 - k2);
        if(l2 <= 0)
            throw new ProxyException(ProxyInfo.ProxyType.SOCKS4, "stream is closed");
          goto _L4
        Exception exception;
        exception;
        Socket socket1 = socket;
_L6:
        byte byte0;
        Exception exception2;
        String s3;
        if(socket1 != null)
            try
            {
                socket1.close();
            }
            catch(Exception exception1) { }
        throw new ProxyException(ProxyInfo.ProxyType.SOCKS4, exception.toString());
_L4:
        k2 += l2;
          goto _L5
_L3:
        if(abyte0[0] != 0)
            throw new ProxyException(ProxyInfo.ProxyType.SOCKS4, (new StringBuilder()).append("server returns VN ").append(abyte0[0]).toString());
        byte0 = abyte0[1];
        if(byte0 == 90)
            break MISSING_BLOCK_LABEL_439;
        try
        {
            socket.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception2) { }
        s3 = (new StringBuilder()).append("ProxySOCKS4: server returns CD ").append(abyte0[1]).toString();
        throw new ProxyException(ProxyInfo.ProxyType.SOCKS4, s3);
        inputstream.read(new byte[2], 0, 2);
        return socket;
        Exception exception3;
        exception3;
        socket1 = null;
        exception = exception3;
          goto _L6
        i2 = k1;
          goto _L7
    }

    public Socket createSocket(String s, int i)
        throws IOException, UnknownHostException
    {
        return socks4ProxifiedSocket(s, i);
    }

    public Socket createSocket(String s, int i, InetAddress inetaddress, int j)
        throws IOException, UnknownHostException
    {
        return socks4ProxifiedSocket(s, i);
    }

    public Socket createSocket(InetAddress inetaddress, int i)
        throws IOException
    {
        return socks4ProxifiedSocket(inetaddress.getHostAddress(), i);
    }

    public Socket createSocket(InetAddress inetaddress, int i, InetAddress inetaddress1, int j)
        throws IOException
    {
        return socks4ProxifiedSocket(inetaddress.getHostAddress(), i);
    }

    private ProxyInfo proxy;
}

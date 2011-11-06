// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HTTPProxySocketFactory.java

package org.jivesoftware.smack.proxy;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.SocketFactory;
import org.jivesoftware.smack.util.Base64;

// Referenced classes of package org.jivesoftware.smack.proxy:
//            ProxyInfo, ProxyException

class HTTPProxySocketFactory extends SocketFactory
{

    public HTTPProxySocketFactory(ProxyInfo proxyinfo)
    {
        proxy = proxyinfo;
    }

    private Socket httpProxifiedSocket(String s, int i)
        throws IOException
    {
        String s1 = proxy.getProxyAddress();
        Socket socket = new Socket(s1, proxy.getProxyPort());
        String s2 = (new StringBuilder()).append("CONNECT ").append(s).append(":").append(i).toString();
        String s3 = proxy.getProxyUsername();
        String s5;
        InputStream inputstream;
        StringBuilder stringbuilder;
        int j;
        if(s3 == null)
        {
            s5 = "";
        } else
        {
            String s4 = proxy.getProxyPassword();
            s5 = (new StringBuilder()).append("\r\nProxy-Authorization: Basic ").append(new String(Base64.encodeBytes((new StringBuilder()).append(s3).append(":").append(s4).toString().getBytes("UTF-8")))).toString();
        }
        socket.getOutputStream().write((new StringBuilder()).append(s2).append(" HTTP/1.1\r\nHost: ").append(s2).append(s5).append("\r\n\r\n").toString().getBytes("UTF-8"));
        inputstream = socket.getInputStream();
        stringbuilder = new StringBuilder(100);
        j = 0;
        do
        {
            char c = (char)inputstream.read();
            stringbuilder.append(c);
            if(stringbuilder.length() > 1024)
                throw new ProxyException(ProxyInfo.ProxyType.HTTP, (new StringBuilder()).append("Recieved header of >1024 characters from ").append(s1).append(", cancelling connection").toString());
            if(c == '\uFFFF')
                throw new ProxyException(ProxyInfo.ProxyType.HTTP);
            if((j == 0 || j == 2) && c == '\r')
                j++;
            else
            if((j == 1 || j == 3) && c == '\n')
                j++;
            else
                j = 0;
        } while(j != 4);
        if(j != 4)
            throw new ProxyException(ProxyInfo.ProxyType.HTTP, (new StringBuilder()).append("Never received blank line from ").append(s1).append(", cancelling connection").toString());
        String s6 = (new BufferedReader(new StringReader(stringbuilder.toString()))).readLine();
        if(s6 == null)
            throw new ProxyException(ProxyInfo.ProxyType.HTTP, (new StringBuilder()).append("Empty proxy response from ").append(s1).append(", cancelling").toString());
        Matcher matcher = RESPONSE_PATTERN.matcher(s6);
        if(!matcher.matches())
            throw new ProxyException(ProxyInfo.ProxyType.HTTP, (new StringBuilder()).append("Unexpected proxy response from ").append(s1).append(": ").append(s6).toString());
        if(Integer.parseInt(matcher.group(1)) != 200)
            throw new ProxyException(ProxyInfo.ProxyType.HTTP);
        else
            return socket;
    }

    public Socket createSocket(String s, int i)
        throws IOException, UnknownHostException
    {
        return httpProxifiedSocket(s, i);
    }

    public Socket createSocket(String s, int i, InetAddress inetaddress, int j)
        throws IOException, UnknownHostException
    {
        return httpProxifiedSocket(s, i);
    }

    public Socket createSocket(InetAddress inetaddress, int i)
        throws IOException
    {
        return httpProxifiedSocket(inetaddress.getHostAddress(), i);
    }

    public Socket createSocket(InetAddress inetaddress, int i, InetAddress inetaddress1, int j)
        throws IOException
    {
        return httpProxifiedSocket(inetaddress.getHostAddress(), i);
    }

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("HTTP/\\S+\\s(\\d+)\\s(.*)\\s*");
    private ProxyInfo proxy;

}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOSHConfiguration.java

package org.jivesoftware.smack;

import java.net.URI;
import java.net.URISyntaxException;
import org.jivesoftware.smack.proxy.ProxyInfo;

// Referenced classes of package org.jivesoftware.smack:
//            ConnectionConfiguration

public class BOSHConfiguration extends ConnectionConfiguration
{

    public BOSHConfiguration(String s)
    {
        super(s, 7070);
        setSASLAuthenticationEnabled(true);
        ssl = false;
        file = "/http-bind/";
    }

    public BOSHConfiguration(String s, int i)
    {
        super(s, i);
        setSASLAuthenticationEnabled(true);
        ssl = false;
        file = "/http-bind/";
    }

    public BOSHConfiguration(boolean flag, String s, int i, String s1, String s2)
    {
        super(s, i, s2);
        setSASLAuthenticationEnabled(true);
        ssl = flag;
        String s3;
        if(s1 != null)
            s3 = s1;
        else
            s3 = "/";
        file = s3;
    }

    public BOSHConfiguration(boolean flag, String s, int i, String s1, ProxyInfo proxyinfo, String s2)
    {
        super(s, i, s2, proxyinfo);
        setSASLAuthenticationEnabled(true);
        ssl = flag;
        String s3;
        if(s1 != null)
            s3 = s1;
        else
            s3 = "/";
        file = s3;
    }

    public String getProxyAddress()
    {
        String s;
        if(proxy != null)
            s = proxy.getProxyAddress();
        else
            s = null;
        return s;
    }

    public ProxyInfo getProxyInfo()
    {
        return proxy;
    }

    public int getProxyPort()
    {
        int i;
        if(proxy != null)
            i = proxy.getProxyPort();
        else
            i = 8080;
        return i;
    }

    public URI getURI()
        throws URISyntaxException
    {
        if(file.charAt(0) != '/')
            file = (new StringBuilder()).append('/').append(file).toString();
        StringBuilder stringbuilder = new StringBuilder();
        String s;
        if(ssl)
            s = "https://";
        else
            s = "http://";
        return new URI(stringbuilder.append(s).append(getHost()).append(":").append(getPort()).append(file).toString());
    }

    public boolean isProxyEnabled()
    {
        boolean flag;
        if(proxy != null && proxy.getProxyType() != org.jivesoftware.smack.proxy.ProxyInfo.ProxyType.NONE)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isUsingSSL()
    {
        return ssl;
    }

    private String file;
    private boolean ssl;
}

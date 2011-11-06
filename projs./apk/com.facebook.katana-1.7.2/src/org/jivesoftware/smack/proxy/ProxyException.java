// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProxyException.java

package org.jivesoftware.smack.proxy;

import java.io.IOException;

public class ProxyException extends IOException
{

    public ProxyException(ProxyInfo.ProxyType proxytype)
    {
        super((new StringBuilder()).append("Proxy Exception ").append(proxytype.toString()).append(" : ").append("Unknown Error").toString());
    }

    public ProxyException(ProxyInfo.ProxyType proxytype, String s)
    {
        super((new StringBuilder()).append("Proxy Exception ").append(proxytype.toString()).append(" : ").append(s).toString());
    }

    public ProxyException(ProxyInfo.ProxyType proxytype, String s, Throwable throwable)
    {
        super((new StringBuilder()).append("Proxy Exception ").append(proxytype.toString()).append(" : ").append(s).append(", ").append(throwable).toString());
    }
}

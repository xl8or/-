// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOSHClientConfig.java

package com.kenai.jbosh;

import java.net.URI;
import javax.net.ssl.SSLContext;

public final class BOSHClientConfig
{
    public static final class Builder
    {

        public static Builder create(BOSHClientConfig boshclientconfig)
        {
            Builder builder = new Builder(boshclientconfig.getURI(), boshclientconfig.getTo());
            builder.bFrom = boshclientconfig.getFrom();
            builder.bLang = boshclientconfig.getLang();
            builder.bRoute = boshclientconfig.getRoute();
            builder.bProxyHost = boshclientconfig.getProxyHost();
            builder.bProxyPort = boshclientconfig.getProxyPort();
            builder.bSSLContext = boshclientconfig.getSSLContext();
            builder.bCompression = Boolean.valueOf(boshclientconfig.isCompressionEnabled());
            return builder;
        }

        public static Builder create(URI uri1, String s)
        {
            if(uri1 == null)
                throw new IllegalArgumentException("Connection manager URI must not be null");
            if(s == null)
                throw new IllegalArgumentException("Target domain must not be null");
            String s1 = uri1.getScheme();
            if(!"http".equals(s1) && !"https".equals(s1))
                throw new IllegalArgumentException("Only 'http' and 'https' URI are allowed");
            else
                return new Builder(uri1, s);
        }

        public BOSHClientConfig build()
        {
            String s;
            int i;
            boolean flag;
            if(bLang == null)
                s = "en";
            else
                s = bLang;
            if(bProxyHost == null)
                i = 0;
            else
                i = bProxyPort;
            if(bCompression == null)
                flag = false;
            else
                flag = bCompression.booleanValue();
            return new BOSHClientConfig(bURI, bDomain, bFrom, s, bRoute, bProxyHost, i, bSSLContext, flag);
        }

        public Builder setCompressionEnabled(boolean flag)
        {
            bCompression = Boolean.valueOf(flag);
            return this;
        }

        public Builder setFrom(String s)
        {
            if(s == null)
            {
                throw new IllegalArgumentException("Client ID must not be null");
            } else
            {
                bFrom = s;
                return this;
            }
        }

        public Builder setProxy(String s, int i)
        {
            if(s == null || s.length() == 0)
                throw new IllegalArgumentException("Proxy host name cannot be null or empty");
            if(i <= 0)
            {
                throw new IllegalArgumentException("Proxy port must be > 0");
            } else
            {
                bProxyHost = s;
                bProxyPort = i;
                return this;
            }
        }

        public Builder setRoute(String s, String s1, int i)
        {
            if(s == null)
                throw new IllegalArgumentException("Protocol cannot be null");
            if(s.contains(":"))
                throw new IllegalArgumentException("Protocol cannot contain the ':' character");
            if(s1 == null)
                throw new IllegalArgumentException("Host cannot be null");
            if(s1.contains(":"))
                throw new IllegalArgumentException("Host cannot contain the ':' character");
            if(i <= 0)
            {
                throw new IllegalArgumentException("Port number must be > 0");
            } else
            {
                bRoute = (new StringBuilder()).append(s).append(":").append(s1).append(":").append(i).toString();
                return this;
            }
        }

        public Builder setSSLContext(SSLContext sslcontext)
        {
            if(sslcontext == null)
            {
                throw new IllegalArgumentException("SSL context cannot be null");
            } else
            {
                bSSLContext = sslcontext;
                return this;
            }
        }

        public Builder setXMLLang(String s)
        {
            if(s == null)
            {
                throw new IllegalArgumentException("Default language ID must not be null");
            } else
            {
                bLang = s;
                return this;
            }
        }

        private Boolean bCompression;
        private final String bDomain;
        private String bFrom;
        private String bLang;
        private String bProxyHost;
        private int bProxyPort;
        private String bRoute;
        private SSLContext bSSLContext;
        private final URI bURI;

        private Builder(URI uri1, String s)
        {
            bURI = uri1;
            bDomain = s;
        }
    }


    private BOSHClientConfig(URI uri1, String s, String s1, String s2, String s3, String s4, int i, 
            SSLContext sslcontext, boolean flag)
    {
        uri = uri1;
        to = s;
        from = s1;
        lang = s2;
        route = s3;
        proxyHost = s4;
        proxyPort = i;
        sslContext = sslcontext;
        compressionEnabled = flag;
    }


    public String getFrom()
    {
        return from;
    }

    public String getLang()
    {
        return lang;
    }

    public String getProxyHost()
    {
        return proxyHost;
    }

    public int getProxyPort()
    {
        return proxyPort;
    }

    public String getRoute()
    {
        return route;
    }

    public SSLContext getSSLContext()
    {
        return sslContext;
    }

    public String getTo()
    {
        return to;
    }

    public URI getURI()
    {
        return uri;
    }

    boolean isCompressionEnabled()
    {
        return compressionEnabled;
    }

    private final boolean compressionEnabled;
    private final String from;
    private final String lang;
    private final String proxyHost;
    private final int proxyPort;
    private final String route;
    private final SSLContext sslContext;
    private final String to;
    private final URI uri;
}

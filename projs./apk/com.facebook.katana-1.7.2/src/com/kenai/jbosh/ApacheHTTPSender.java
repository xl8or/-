// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApacheHTTPSender.java

package com.kenai.jbosh;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.*;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.*;

// Referenced classes of package com.kenai.jbosh:
//            HTTPSender, BOSHClientConfig, ApacheHTTPResponse, CMSessionParams, 
//            AbstractBody, HTTPResponse

final class ApacheHTTPSender
    implements HTTPSender
{

    ApacheHTTPSender()
    {
        org/apache/http/client/HttpClient.getName();
    }

    /**
     * @deprecated Method initHttpClient is deprecated
     */

    private HttpClient initHttpClient(BOSHClientConfig boshclientconfig)
    {
        this;
        JVM INSTR monitorenter ;
        DefaultHttpClient defaulthttpclient;
        BasicHttpParams basichttpparams = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(basichttpparams, 100);
        HttpProtocolParams.setVersion(basichttpparams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUseExpectContinue(basichttpparams, false);
        if(boshclientconfig != null && boshclientconfig.getProxyHost() != null && boshclientconfig.getProxyPort() != 0)
            basichttpparams.setParameter("http.route.default-proxy", new HttpHost(boshclientconfig.getProxyHost(), boshclientconfig.getProxyPort()));
        SchemeRegistry schemeregistry = new SchemeRegistry();
        schemeregistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        SSLSocketFactory sslsocketfactory = SSLSocketFactory.getSocketFactory();
        sslsocketfactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        schemeregistry.register(new Scheme("https", sslsocketfactory, 443));
        defaulthttpclient = new DefaultHttpClient(new ThreadSafeClientConnManager(basichttpparams, schemeregistry), basichttpparams);
        this;
        JVM INSTR monitorexit ;
        return defaulthttpclient;
        Exception exception;
        exception;
        throw exception;
    }

    public void destroy()
    {
        lock.lock();
        if(httpClient != null)
            httpClient.getConnectionManager().shutdown();
        cfg = null;
        httpClient = null;
        lock.unlock();
        return;
        Exception exception;
        exception;
        cfg = null;
        httpClient = null;
        lock.unlock();
        throw exception;
    }

    public void init(BOSHClientConfig boshclientconfig)
    {
        lock.lock();
        cfg = boshclientconfig;
        httpClient = initHttpClient(boshclientconfig);
        lock.unlock();
        return;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public HTTPResponse send(CMSessionParams cmsessionparams, AbstractBody abstractbody)
    {
        lock.lock();
        HttpClient httpclient;
        BOSHClientConfig boshclientconfig;
        if(httpClient == null)
            httpClient = initHttpClient(cfg);
        httpclient = httpClient;
        boshclientconfig = cfg;
        lock.unlock();
        return new ApacheHTTPResponse(httpclient, boshclientconfig, cmsessionparams, abstractbody);
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    private BOSHClientConfig cfg;
    private HttpClient httpClient;
    private final Lock lock = new ReentrantLock();
}

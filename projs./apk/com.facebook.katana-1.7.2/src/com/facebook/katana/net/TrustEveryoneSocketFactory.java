// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TrustEveryoneSocketFactory.java

package com.facebook.katana.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class TrustEveryoneSocketFactory extends SSLSocketFactory
{

    private TrustEveryoneSocketFactory(KeyStore keystore)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        super(keystore);
        sslContext = null;
        sslContext = SSLContext.getInstance("TLS");
        SSLContext sslcontext = sslContext;
        TrustManager atrustmanager[] = new TrustManager[1];
        atrustmanager[0] = trustManager;
        sslcontext.init(null, atrustmanager, new SecureRandom());
    }

    public static SSLSocketFactory getTrustEveryoneSocketFactory()
    {
        TrustEveryoneSocketFactory trusteveryonesocketfactory1;
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null, null);
        trusteveryonesocketfactory1 = new TrustEveryoneSocketFactory(keystore);
        trusteveryonesocketfactory1.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        TrustEveryoneSocketFactory trusteveryonesocketfactory = trusteveryonesocketfactory1;
_L2:
        return trusteveryonesocketfactory;
        Exception exception;
        exception;
        trusteveryonesocketfactory = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Socket createSocket()
        throws IOException
    {
        return sslContext.getSocketFactory().createSocket();
    }

    public Socket createSocket(Socket socket, String s, int i, boolean flag)
        throws IOException, UnknownHostException
    {
        return sslContext.getSocketFactory().createSocket(socket, s, i, flag);
    }

    private static TrustManager trustManager = new X509TrustManager() {

        public void checkClientTrusted(X509Certificate ax509certificate[], String s)
            throws CertificateException
        {
        }

        public void checkServerTrusted(X509Certificate ax509certificate[], String s)
            throws CertificateException
        {
        }

        public X509Certificate[] getAcceptedIssuers()
        {
            return trusted;
        }

        X509Certificate trusted[];

            
            {
                trusted = new X509Certificate[0];
            }
    }
;
    private SSLContext sslContext;

}

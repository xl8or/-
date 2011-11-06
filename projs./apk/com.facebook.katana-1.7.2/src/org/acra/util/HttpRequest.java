// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpRequest.java

package org.acra.util;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.*;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.acra.util:
//            FakeSocketFactory

public class HttpRequest
{

    public HttpRequest(String s, String s1)
    {
        httpPost = null;
        httpGet = null;
        creds = null;
        if(s != null || s1 != null)
            creds = new UsernamePasswordCredentials(s, s1);
        BasicHttpParams basichttpparams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basichttpparams, ACRA.getConfig().socketTimeout());
        HttpConnectionParams.setSoTimeout(basichttpparams, ACRA.getConfig().socketTimeout());
        HttpConnectionParams.setSocketBufferSize(basichttpparams, 8192);
        SchemeRegistry schemeregistry = new SchemeRegistry();
        schemeregistry.register(new Scheme("http", new PlainSocketFactory(), 80));
        schemeregistry.register(new Scheme("https", new FakeSocketFactory(), 443));
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(basichttpparams, schemeregistry), basichttpparams);
        localContext = new BasicHttpContext();
    }

    public void abort()
    {
        if(httpClient != null)
        {
            Log.d(ACRA.LOG_TAG, "Abort HttpClient request.");
            httpPost.abort();
        }
_L1:
        return;
        Exception exception;
        exception;
        Log.e(ACRA.LOG_TAG, "Error while aborting HttpClient request", exception);
          goto _L1
    }

    public void clearCookies()
    {
        httpClient.getCookieStore().clear();
    }

    public InputStream getHttpStream(String s)
        throws IOException
    {
label0:
        {
            InputStream inputstream = null;
            java.net.URLConnection urlconnection = (new URL(s)).openConnection();
            if(!(urlconnection instanceof HttpURLConnection))
                throw new IOException("Not an HTTP connection");
            InputStream inputstream1;
            try
            {
                HttpURLConnection httpurlconnection = (HttpURLConnection)urlconnection;
                httpurlconnection.setAllowUserInteraction(false);
                httpurlconnection.setInstanceFollowRedirects(true);
                httpurlconnection.setRequestMethod("GET");
                httpurlconnection.connect();
                if(httpurlconnection.getResponseCode() != 200)
                    break label0;
                inputstream1 = httpurlconnection.getInputStream();
            }
            catch(Exception exception)
            {
                throw new IOException("Error connecting");
            }
            inputstream = inputstream1;
        }
        return inputstream;
    }

    public String sendGet(String s)
        throws ClientProtocolException, IOException
    {
        httpGet = new HttpGet(s);
        return EntityUtils.toString(httpClient.execute(httpGet).getEntity());
    }

    public String sendPost(String s, String s1)
        throws ClientProtocolException, IOException
    {
        return sendPost(s, s1, null);
    }

    public String sendPost(String s, String s1, String s2)
        throws ClientProtocolException, IOException
    {
        httpClient.getParams().setParameter("http.protocol.cookie-policy", "rfc2109");
        httpPost = new HttpPost(s);
        Log.d(ACRA.LOG_TAG, "Setting httpPost headers");
        if(creds != null)
            httpPost.addHeader(BasicScheme.authenticate(creds, "UTF-8", false));
        httpPost.setHeader("User-Agent", "Android");
        httpPost.setHeader("Accept", "text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
        StringEntity stringentity;
        HttpResponse httpresponse;
        if(s2 != null)
            httpPost.setHeader("Content-Type", s2);
        else
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        stringentity = new StringEntity(s1, "UTF-8");
        httpPost.setEntity(stringentity);
        Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("Sending request to ").append(s).toString());
        httpresponse = httpClient.execute(httpPost, localContext);
        String s3;
        if(httpresponse != null)
        {
            if(httpresponse.getStatusLine() != null)
            {
                String s4 = Integer.toString(httpresponse.getStatusLine().getStatusCode());
                if(s4.startsWith("4") || s4.startsWith("5"))
                    throw new IOException((new StringBuilder()).append("Host returned error code ").append(s4).toString());
            }
            s3 = EntityUtils.toString(httpresponse.getEntity());
        } else
        {
            s3 = null;
        }
        return s3;
    }

    UsernamePasswordCredentials creds;
    DefaultHttpClient httpClient;
    HttpGet httpGet;
    HttpPost httpPost;
    HttpContext localContext;
}

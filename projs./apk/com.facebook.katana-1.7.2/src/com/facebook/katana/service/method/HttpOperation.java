// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpOperation.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import com.facebook.katana.UserAgent;
import com.facebook.katana.net.TrustEveryoneSocketFactory;
import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.*;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.*;
import org.apache.http.protocol.HttpContext;

public class HttpOperation extends Thread
{
    public static interface HttpOperationListener
    {

        public abstract void onHttpOperationComplete(HttpOperation httpoperation, int i, String s, OutputStream outputstream, Exception exception);

        public abstract void onHttpOperationProgress(HttpOperation httpoperation, long l, long l1);
    }

    private class CountingOutputStream extends FilterOutputStream
    {

        public void write(int i)
            throws IOException
        {
            out.write(i);
            mTransferred = 1L + mTransferred;
            if(mTransferred >= mNext)
            {
                super.flush();
                if(mListener != null)
                    mListener.onHttpOperationProgress(HttpOperation.this, mTransferred, mLength);
                mNext = mNext + mChunk;
            }
        }

        public void write(byte abyte0[], int i, int j)
            throws IOException
        {
            out.write(abyte0, i, j);
            mTransferred = mTransferred + (long)j;
            if(mTransferred >= mNext)
            {
                super.flush();
                if(mListener != null)
                    mListener.onHttpOperationProgress(HttpOperation.this, mTransferred, mLength);
                mNext = mNext + mChunk;
            }
        }

        private final long mChunk;
        private final long mLength;
        private long mNext;
        private long mTransferred;
        final HttpOperation this$0;

        public CountingOutputStream(OutputStream outputstream, long l)
        {
            this$0 = HttpOperation.this;
            super(outputstream);
            mLength = l;
            mTransferred = 0L;
            mChunk = mLength / 5L;
            mNext = mChunk;
        }
    }

    private class MyInputStreamEntity extends InputStreamEntity
    {

        public void writeTo(OutputStream outputstream)
            throws IOException
        {
            super.writeTo(new CountingOutputStream(outputstream, getContentLength()));
        }

        final HttpOperation this$0;

        public MyInputStreamEntity(InputStream inputstream, long l)
        {
            this$0 = HttpOperation.this;
            super(inputstream, l);
        }
    }

    private class FBMultipartEntity extends MultipartEntity
    {

        public void setContentLength(long l)
        {
            mContentLength = l;
        }

        public void writeTo(OutputStream outputstream)
            throws IOException
        {
            super.writeTo(new CountingOutputStream(outputstream, mContentLength));
        }

        private long mContentLength;
        final HttpOperation this$0;

        private FBMultipartEntity()
        {
            this$0 = HttpOperation.this;
            super();
            mContentLength = 0L;
        }

    }


    public HttpOperation(Context context, String s, InputStream inputstream, OutputStream outputstream, String s1, HttpOperationListener httpoperationlistener, boolean flag)
        throws IOException
    {
        mTimeStart = 0L;
        mTimeEnd = 0L;
        initSchemeRegistry(context);
        mHttpMethod = new HttpPost(s);
        if(inputstream != null)
        {
            ((HttpPost)mHttpMethod).setEntity(new MyInputStreamEntity(inputstream, inputstream.available()));
            mHttpMethod.addHeader("Content-Type", s1);
        }
        if(flag)
            mHttpMethod.addHeader("User-Agent", getUserAgentString(context));
        mOutputStream = outputstream;
        mListener = httpoperationlistener;
        httpContext = null;
    }

    public HttpOperation(Context context, String s, String s1, OutputStream outputstream, HttpOperationListener httpoperationlistener, String s2, String s3, 
            HttpContext httpcontext)
        throws IOException
    {
        mTimeStart = 0L;
        mTimeEnd = 0L;
        initSchemeRegistry(context);
        if(s.equals("GET"))
            mHttpMethod = new HttpGet(s1);
        else
        if(s.equals("POST"))
            mHttpMethod = new HttpPost(s1);
        else
            throw new IOException("Unsupported method");
        if(s2 != null)
            mHttpMethod.addHeader("User-Agent", s2);
        if(s3 != null)
            mHttpMethod.addHeader("Accept", s3);
        mOutputStream = outputstream;
        mListener = httpoperationlistener;
        httpContext = httpcontext;
    }

    public HttpOperation(Context context, String s, String s1, OutputStream outputstream, HttpOperationListener httpoperationlistener, boolean flag)
        throws IOException
    {
        String s2;
        if(flag)
            s2 = getUserAgentString(context);
        else
            s2 = null;
        this(context, s, s1, outputstream, httpoperationlistener, s2, null, null);
    }

    public HttpOperation(Context context, String s, Map map, Long long1, OutputStream outputstream, HttpOperationListener httpoperationlistener, boolean flag)
    {
        mTimeStart = 0L;
        mTimeEnd = 0L;
        initSchemeRegistry(context);
        mHttpMethod = new HttpPost(s);
        if(map != null)
        {
            FBMultipartEntity fbmultipartentity = new FBMultipartEntity();
            long l = long1.longValue();
            for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); iterator.remove())
            {
                String s1 = (String)iterator.next();
                ContentBody contentbody = (ContentBody)map.get(s1);
                fbmultipartentity.addPart(s1, contentbody);
                l += 35L + contentbody.getContentLength();
            }

            fbmultipartentity.setContentLength(l);
            ((HttpPost)mHttpMethod).setEntity(fbmultipartentity);
        }
        if(flag)
            mHttpMethod.addHeader("User-Agent", getUserAgentString(context));
        mOutputStream = outputstream;
        mListener = httpoperationlistener;
        httpContext = null;
    }

    protected static String getUserAgentString(Context context)
    {
        if(userAgent == null)
            userAgent = UserAgent.getUserAgentString(context, Boolean.valueOf(false));
        return userAgent;
    }

    public static void initSchemeRegistry(Context context)
    {
        initSchemeRegistry(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("check_certs", true));
    }

    public static void initSchemeRegistry(boolean flag)
    {
        if(mSupportedSchemes == null || mCheckCertsMode != flag)
        {
            mSupportedSchemes = new SchemeRegistry();
            mSupportedSchemes.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            SSLSocketFactory sslsocketfactory;
            if(flag)
            {
                sslsocketfactory = SSLSocketFactory.getSocketFactory();
                sslsocketfactory.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            } else
            {
                sslsocketfactory = TrustEveryoneSocketFactory.getTrustEveryoneSocketFactory();
            }
            mSupportedSchemes.register(new Scheme("https", sslsocketfactory, 443));
            mCheckCertsMode = flag;
        }
    }

    private void readFromHTTPStream(InputStream inputstream, String s, String s1, int i)
        throws IOException
    {
        if(s1 != null && s1.equals("gzip"))
            inputstream = new GZIPInputStream(inputstream);
        if(s != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int j;
        byte abyte0[];
        boolean flag;
        int k;
        Exception exception;
        int k1;
        if(s.startsWith("application/json") || s.startsWith("text/html") | s.startsWith("text/javascript"))
            j = 0x9eb10;
        else
        if(s.equals("image/jpeg") || s.equals("image/gif") || s.equals("image/png"))
            j = 0x35b60;
        else
            throw new IllegalArgumentException((new StringBuilder()).append("Unsupported content type: ").append(s).toString());
        abyte0 = (byte[])m_bufferCache.remove(0);
        flag = true;
_L8:
        k = 0;
        if(i != -1) goto _L4; else goto _L3
_L3:
        k1 = inputstream.read(abyte0, 0, abyte0.length);
        if(k1 == -1) goto _L6; else goto _L5
_L5:
        mOutputStream.write(abyte0, 0, k1);
        k += k1;
        if(k <= j) goto _L3; else goto _L7
_L7:
        throw new IOException((new StringBuilder()).append("Content too large (length unknown): ").append(k).append(" (").append(s).append(")").toString());
        exception;
        if(flag)
            m_bufferCache.add(abyte0);
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_199;
        inputstream.close();
        mOutputStream.flush();
        mOutputStream.close();
_L9:
        throw exception;
        IndexOutOfBoundsException indexoutofboundsexception;
        indexoutofboundsexception;
        abyte0 = new byte[2048];
        flag = false;
          goto _L8
_L4:
        if(i <= j)
            break MISSING_BLOCK_LABEL_530;
        throw new IOException((new StringBuilder()).append("Content too large: ").append(i).append(" (").append(s).append(")").toString());
        l = i;
        while(l > 0) 
        {
            int j1 = inputstream.read(abyte0, 0, Math.min(l, abyte0.length));
            if(j1 == -1)
                throw new IOException((new StringBuilder()).append("Invalid content length: ").append(l).toString());
            if(j1 > 0)
            {
                l -= j1;
                mOutputStream.write(abyte0, 0, j1);
                k += j1;
            }
        }
        do
        {
            int i1 = inputstream.read(abyte0, 0, abyte0.length);
            if(i1 == -1)
                break;
            k += i1;
            mOutputStream.write(abyte0, 0, i1);
        } while(true);
_L6:
        if(flag)
            m_bufferCache.add(abyte0);
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_503;
        inputstream.close();
        mOutputStream.flush();
        mOutputStream.close();
          goto _L1
        ioexception;
          goto _L1
        ioexception1;
          goto _L9
    }

    public static SchemeRegistry supportedSchemes()
    {
        return mSupportedSchemes;
    }

    public long calculateTimeElapsed()
    {
        long l;
        if(mTimeEnd > 0L)
            l = mTimeEnd - mTimeStart;
        else
            l = 0L;
        return l;
    }

    public void cancel()
    {
        interrupt();
    }

    public void run()
    {
        SingleClientConnManager singleclientconnmanager;
        DefaultHttpClient defaulthttpclient;
        setPriority(1);
        BasicHttpParams basichttpparams = new BasicHttpParams();
        basichttpparams.setParameter("http.socket.timeout", new Integer(0x19a28));
        basichttpparams.setParameter("http.connection.timeout", new Integer(20000));
        HttpConnectionParams.setSocketBufferSize(basichttpparams, 8192);
        singleclientconnmanager = new SingleClientConnManager(basichttpparams, mSupportedSchemes);
        defaulthttpclient = new DefaultHttpClient(singleclientconnmanager, basichttpparams);
        mTimeStart = SystemClock.elapsedRealtime();
        mHttpMethod.addHeader("Accept-Encoding", "gzip");
        if(httpContext == null) goto _L2; else goto _L1
_L1:
        HttpResponse httpresponse1 = defaulthttpclient.execute(mHttpMethod, httpContext);
_L9:
        int i;
        String s;
        i = httpresponse1.getStatusLine().getStatusCode();
        s = httpresponse1.getStatusLine().getReasonPhrase();
        if(i != 200) goto _L4; else goto _L3
_L3:
        HttpEntity httpentity = httpresponse1.getEntity();
        if(httpentity.getContentEncoding() == null) goto _L6; else goto _L5
_L5:
        String s1 = httpentity.getContentEncoding().getValue();
_L10:
        if(httpentity.getContentType() == null) goto _L8; else goto _L7
_L7:
        String s2 = httpentity.getContentType().getValue();
_L11:
        InputStream inputstream = httpentity.getContent();
        int j = (int)httpentity.getContentLength();
        readFromHTTPStream(inputstream, s2, s1, j);
_L4:
        mTimeEnd = SystemClock.elapsedRealtime();
        if(mListener != null)
            mListener.onHttpOperationComplete(this, i, s, mOutputStream, null);
        singleclientconnmanager.shutdown();
_L12:
        return;
_L2:
        HttpResponse httpresponse = defaulthttpclient.execute(mHttpMethod);
        httpresponse1 = httpresponse;
          goto _L9
_L6:
        s1 = null;
          goto _L10
_L8:
        s2 = null;
          goto _L11
        Exception exception1;
        exception1;
        if(mListener != null)
            mListener.onHttpOperationComplete(this, 0, null, null, exception1);
        singleclientconnmanager.shutdown();
          goto _L12
        Exception exception;
        exception;
        singleclientconnmanager.shutdown();
        throw exception;
          goto _L9
    }

    private static final int BUFFER_CACHE_SIZE = 1;
    private static final int CONNECT_TIMEOUT = 20000;
    private static final int IMAGE_SIZE_LIMIT = 0x35b60;
    private static final int JSON_SIZE_LIMIT = 0x9eb10;
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    private static final int READ_TIMEOUT = 0x19a28;
    private static final int SOCKET_BUFFER_SIZE = 8192;
    private static boolean mCheckCertsMode = true;
    private static SchemeRegistry mSupportedSchemes;
    private static final Vector m_bufferCache;
    protected static String userAgent;
    public final HttpContext httpContext;
    public final HttpRequestBase mHttpMethod;
    private final HttpOperationListener mListener;
    private final OutputStream mOutputStream;
    private long mTimeEnd;
    private long mTimeStart;

    static 
    {
        m_bufferCache = new Vector(1);
        for(int i = 0; i < 1; i++)
            m_bufferCache.add(new byte[2048]);

    }

}

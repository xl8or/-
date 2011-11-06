// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApacheHTTPResponse.java

package com.kenai.jbosh;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

// Referenced classes of package com.kenai.jbosh:
//            HTTPResponse, ZLIBCodec, GZIPCodec, BOSHClientConfig, 
//            AbstractBody, CMSessionParams, AttrAccept, BOSHException, 
//            StaticBody

final class ApacheHTTPResponse
    implements HTTPResponse
{

    ApacheHTTPResponse(HttpClient httpclient, BOSHClientConfig boshclientconfig, CMSessionParams cmsessionparams, AbstractBody abstractbody)
    {
        lock = new ReentrantLock();
        client = httpclient;
        context = new BasicHttpContext();
        post = new HttpPost(boshclientconfig.getURI().toString());
        sent = false;
        byte abyte0[];
        AttrAccept attraccept;
        abyte0 = abstractbody.toXML().getBytes("UTF-8");
        if(!boshclientconfig.isCompressionEnabled() || cmsessionparams == null)
            break MISSING_BLOCK_LABEL_230;
        attraccept = cmsessionparams.getAccept();
        if(attraccept == null)
            break MISSING_BLOCK_LABEL_230;
        if(!attraccept.isAccepted(ZLIBCodec.getID())) goto _L2; else goto _L1
_L1:
        byte abyte1[];
        String s;
        String s2 = ZLIBCodec.getID();
        abyte1 = ZLIBCodec.encode(abyte0);
        s = s2;
_L3:
        ByteArrayEntity bytearrayentity = new ByteArrayEntity(abyte1);
        bytearrayentity.setContentType("text/xml; charset=utf-8");
        if(s != null)
            bytearrayentity.setContentEncoding(s);
        post.setEntity(bytearrayentity);
        if(boshclientconfig.isCompressionEnabled())
            post.setHeader("Accept-Encoding", ACCEPT_ENCODING_VAL);
        break MISSING_BLOCK_LABEL_240;
_L2:
        String s1;
        byte abyte2[];
        if(!attraccept.isAccepted(GZIPCodec.getID()))
            break MISSING_BLOCK_LABEL_230;
        s1 = GZIPCodec.getID();
        abyte2 = GZIPCodec.encode(abyte0);
        abyte1 = abyte2;
        s = s1;
          goto _L3
        Exception exception;
        exception;
        toThrow = new BOSHException("Could not generate request", exception);
        break MISSING_BLOCK_LABEL_240;
        abyte1 = abyte0;
        s = null;
          goto _L3
    }

    /**
     * @deprecated Method awaitResponse is deprecated
     */

    private void awaitResponse()
        throws BOSHException
    {
        this;
        JVM INSTR monitorenter ;
        HttpResponse httpresponse;
        HttpEntity httpentity;
        byte abyte0[];
        httpresponse = client.execute(post, context);
        httpentity = httpresponse.getEntity();
        abyte0 = EntityUtils.toByteArray(httpentity);
        if(httpentity.getContentEncoding() == null) goto _L2; else goto _L1
_L1:
        String s = httpentity.getContentEncoding().getValue();
_L5:
        if(!ZLIBCodec.getID().equalsIgnoreCase(s)) goto _L4; else goto _L3
_L3:
        byte abyte1[] = ZLIBCodec.decode(abyte0);
_L6:
        body = StaticBody.fromString(new String(abyte1, "UTF-8"));
        statusCode = httpresponse.getStatusLine().getStatusCode();
        sent = true;
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        s = null;
          goto _L5
_L4:
        byte abyte2[];
        if(!GZIPCodec.getID().equalsIgnoreCase(s))
            break MISSING_BLOCK_LABEL_188;
        abyte2 = GZIPCodec.decode(abyte0);
        abyte1 = abyte2;
          goto _L6
        IOException ioexception;
        ioexception;
        abort();
        toThrow = new BOSHException("Could not obtain response", ioexception);
        throw toThrow;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        RuntimeException runtimeexception;
        runtimeexception;
        abort();
        throw runtimeexception;
        abyte1 = abyte0;
          goto _L6
    }

    public void abort()
    {
        if(post != null)
        {
            post.abort();
            toThrow = new BOSHException("HTTP request aborted");
        }
    }

    public AbstractBody getBody()
        throws InterruptedException, BOSHException
    {
        if(toThrow != null)
            throw toThrow;
        lock.lock();
        if(!sent)
            awaitResponse();
        lock.unlock();
        return body;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public int getHTTPStatus()
        throws InterruptedException, BOSHException
    {
        if(toThrow != null)
            throw toThrow;
        lock.lock();
        if(!sent)
            awaitResponse();
        lock.unlock();
        return statusCode;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String ACCEPT_ENCODING_VAL = (new StringBuilder()).append(ZLIBCodec.getID()).append(", ").append(GZIPCodec.getID()).toString();
    private static final String CHARSET = "UTF-8";
    private static final String CONTENT_TYPE = "text/xml; charset=utf-8";
    private AbstractBody body;
    private final HttpClient client;
    private final HttpContext context;
    private final Lock lock;
    private final HttpPost post;
    private boolean sent;
    private int statusCode;
    private BOSHException toThrow;

}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HTTPExchange.java

package com.kenai.jbosh;

import java.util.concurrent.locks.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Referenced classes of package com.kenai.jbosh:
//            AbstractBody, HTTPResponse

final class HTTPExchange
{

    HTTPExchange(AbstractBody abstractbody)
    {
        ready = lock.newCondition();
        if(abstractbody == null)
        {
            throw new IllegalArgumentException("Request body cannot be null");
        } else
        {
            request = abstractbody;
            return;
        }
    }

    HTTPResponse getHTTPResponse()
    {
        lock.lock();
_L1:
        HTTPResponse httpresponse = response;
        if(httpresponse != null)
            break MISSING_BLOCK_LABEL_60;
        ready.await();
          goto _L1
        InterruptedException interruptedexception;
        interruptedexception;
        LOG.log(Level.FINEST, "Interrupted", interruptedexception);
          goto _L1
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        HTTPResponse httpresponse1 = response;
        lock.unlock();
        return httpresponse1;
    }

    AbstractBody getRequest()
    {
        return request;
    }

    void setHTTPResponse(HTTPResponse httpresponse)
    {
        lock.lock();
        if(response != null)
            throw new IllegalStateException("HTTPResponse was already set");
        break MISSING_BLOCK_LABEL_38;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        response = httpresponse;
        ready.signalAll();
        lock.unlock();
        return;
    }

    private static final Logger LOG = Logger.getLogger(com/kenai/jbosh/HTTPExchange.getName());
    private final Lock lock = new ReentrantLock();
    private final Condition ready;
    private final AbstractBody request;
    private HTTPResponse response;

}

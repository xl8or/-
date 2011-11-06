// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOSHClient.java

package com.kenai.jbosh;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

// Referenced classes of package com.kenai.jbosh:
//            ApacheHTTPSender, RequestIDSequence, BOSHClientConfig, Attributes, 
//            BOSHException, ComposableBody, AttrVersion, CMSessionParams, 
//            AttrSessionID, TerminalBindingCondition, HTTPSender, BOSHClientConnListener, 
//            BOSHClientConnEvent, BOSHClientRequestListener, BOSHMessageEvent, BOSHClientResponseListener, 
//            AttrPolling, AbstractBody, AttrRequests, HTTPExchange, 
//            HTTPResponse, AttrPause, AttrMaxPause

public final class BOSHClient
{
    static abstract class ExchangeInterceptor
    {

        abstract HTTPExchange interceptExchange(HTTPExchange httpexchange);

        ExchangeInterceptor()
        {
        }
    }


    private BOSHClient(BOSHClientConfig boshclientconfig)
    {
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        drained = lock.newCondition();
        exchanges = new LinkedList();
        pendingResponseAcks = new TreeSet();
        responseAck = Long.valueOf(-1L);
        pendingRequestAcks = new ArrayList();
        cfg = boshclientconfig;
        init();
    }

    private void applyFrom(ComposableBody.Builder builder)
    {
        assertLocked();
        String s = cfg.getFrom();
        if(s != null)
            builder.setAttribute(Attributes.FROM, s);
    }

    private void applyResponseAcknowledgement(ComposableBody.Builder builder, long l)
    {
        assertLocked();
        if(!responseAck.equals(Long.valueOf(-1L))) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Long long1 = Long.valueOf(l - 1L);
        if(!responseAck.equals(long1))
            builder.setAttribute(Attributes.ACK, responseAck.toString());
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void applyRoute(ComposableBody.Builder builder)
    {
        assertLocked();
        String s = cfg.getRoute();
        if(s != null)
            builder.setAttribute(Attributes.ROUTE, s);
    }

    private ComposableBody applySessionCreationRequest(long l, ComposableBody composablebody)
        throws BOSHException
    {
        assertLocked();
        ComposableBody.Builder builder = composablebody.rebuild();
        builder.setAttribute(Attributes.TO, cfg.getTo());
        builder.setAttribute(Attributes.XML_LANG, cfg.getLang());
        builder.setAttribute(Attributes.VER, AttrVersion.getSupportedVersion().toString());
        builder.setAttribute(Attributes.WAIT, "60");
        builder.setAttribute(Attributes.HOLD, "1");
        builder.setAttribute(Attributes.RID, Long.toString(l));
        applyRoute(builder);
        applyFrom(builder);
        builder.setAttribute(Attributes.ACK, "1");
        builder.setAttribute(Attributes.SID, null);
        return builder.build();
    }

    private ComposableBody applySessionData(long l, ComposableBody composablebody)
        throws BOSHException
    {
        assertLocked();
        ComposableBody.Builder builder = composablebody.rebuild();
        builder.setAttribute(Attributes.SID, cmParams.getSessionID().toString());
        builder.setAttribute(Attributes.RID, Long.toString(l));
        applyResponseAcknowledgement(builder, l);
        return builder.build();
    }

    private void assertLocked()
    {
        if(ASSERTIONS && !lock.isHeldByCurrentThread())
            throw new AssertionError("Lock is not held by current thread");
        else
            return;
    }

    private void assertUnlocked()
    {
        if(ASSERTIONS && lock.isHeldByCurrentThread())
            throw new AssertionError("Lock is held by current thread");
        else
            return;
    }

    private void blockUntilSendable(AbstractBody abstractbody)
    {
        assertLocked();
        while(isWorking() && !isImmediatelySendable(abstractbody)) 
            try
            {
                notFull.await();
            }
            catch(InterruptedException interruptedexception)
            {
                LOG.log(Level.FINEST, "Interrupted", interruptedexception);
            }
    }

    private void checkForTerminalBindingConditions(AbstractBody abstractbody, int i)
        throws BOSHException
    {
        TerminalBindingCondition terminalbindingcondition = getTerminalBindingCondition(i, abstractbody);
        if(terminalbindingcondition != null)
            throw new BOSHException((new StringBuilder()).append("Terminal binding condition encountered: ").append(terminalbindingcondition.getCondition()).append("  (").append(terminalbindingcondition.getMessage()).append(")").toString());
        else
            return;
    }

    private void clearEmptyRequest()
    {
        assertLocked();
        if(emptyRequestFuture != null)
        {
            emptyRequestFuture.cancel(false);
            emptyRequestFuture = null;
        }
    }

    public static BOSHClient create(BOSHClientConfig boshclientconfig)
    {
        if(boshclientconfig == null)
            throw new IllegalArgumentException("Client configuration may not be null");
        else
            return new BOSHClient(boshclientconfig);
    }

    private void dispose(Throwable throwable)
    {
        assertUnlocked();
        lock.lock();
        Thread thread = procThread;
        if(thread != null) goto _L2; else goto _L1
_L1:
        lock.unlock();
_L4:
        return;
_L2:
        procThread = null;
        lock.unlock();
        Exception exception;
        if(throwable == null)
            fireConnectionClosed();
        else
            fireConnectionClosedOnError(throwable);
        lock.lock();
        clearEmptyRequest();
        exchanges = null;
        cmParams = null;
        pendingResponseAcks = null;
        pendingRequestAcks = null;
        notEmpty.signalAll();
        notFull.signalAll();
        drained.signalAll();
        lock.unlock();
        httpSender.destroy();
        schedExec.shutdownNow();
        if(true) goto _L4; else goto _L3
_L3:
        exception;
        lock.unlock();
        throw exception;
        Exception exception1;
        exception1;
        lock.unlock();
        throw exception1;
    }

    private void fireConnectionClosed()
    {
        assertUnlocked();
        Iterator iterator = connListeners.iterator();
        BOSHClientConnEvent boshclientconnevent = null;
        while(iterator.hasNext()) 
        {
            BOSHClientConnListener boshclientconnlistener = (BOSHClientConnListener)iterator.next();
            if(boshclientconnevent == null)
                boshclientconnevent = BOSHClientConnEvent.createConnectionClosedEvent(this);
            try
            {
                boshclientconnlistener.connectionEvent(boshclientconnevent);
            }
            catch(Exception exception)
            {
                LOG.log(Level.WARNING, "Unhandled Exception", exception);
            }
        }
    }

    private void fireConnectionClosedOnError(Throwable throwable)
    {
        assertUnlocked();
        Iterator iterator = connListeners.iterator();
        BOSHClientConnEvent boshclientconnevent = null;
        while(iterator.hasNext()) 
        {
            BOSHClientConnListener boshclientconnlistener = (BOSHClientConnListener)iterator.next();
            if(boshclientconnevent == null)
                boshclientconnevent = BOSHClientConnEvent.createConnectionClosedOnErrorEvent(this, pendingRequestAcks, throwable);
            try
            {
                boshclientconnlistener.connectionEvent(boshclientconnevent);
            }
            catch(Exception exception)
            {
                LOG.log(Level.WARNING, "Unhandled Exception", exception);
            }
        }
    }

    private void fireConnectionEstablished()
    {
        boolean flag;
        flag = lock.isHeldByCurrentThread();
        if(flag)
            lock.unlock();
        Iterator iterator;
        BOSHClientConnEvent boshclientconnevent;
        iterator = connListeners.iterator();
        boshclientconnevent = null;
_L3:
        BOSHClientConnListener boshclientconnlistener;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_111;
        boshclientconnlistener = (BOSHClientConnListener)iterator.next();
        if(boshclientconnevent != null) goto _L2; else goto _L1
_L1:
        BOSHClientConnEvent boshclientconnevent1 = BOSHClientConnEvent.createConnectionEstablishedEvent(this);
        boshclientconnevent = boshclientconnevent1;
_L2:
        boshclientconnlistener.connectionEvent(boshclientconnevent);
          goto _L3
        Exception exception1;
        exception1;
        LOG.log(Level.WARNING, "Unhandled Exception", exception1);
          goto _L3
        Exception exception;
        exception;
        if(flag)
            lock.lock();
        throw exception;
        if(flag)
            lock.lock();
        return;
    }

    private void fireRequestSent(AbstractBody abstractbody)
    {
        assertUnlocked();
        Iterator iterator = requestListeners.iterator();
        BOSHMessageEvent boshmessageevent = null;
        while(iterator.hasNext()) 
        {
            BOSHClientRequestListener boshclientrequestlistener = (BOSHClientRequestListener)iterator.next();
            if(boshmessageevent == null)
                boshmessageevent = BOSHMessageEvent.createRequestSentEvent(this, abstractbody);
            try
            {
                boshclientrequestlistener.requestSent(boshmessageevent);
            }
            catch(Exception exception)
            {
                LOG.log(Level.WARNING, "Unhandled Exception", exception);
            }
        }
    }

    private void fireResponseReceived(AbstractBody abstractbody)
    {
        assertUnlocked();
        Iterator iterator = responseListeners.iterator();
        BOSHMessageEvent boshmessageevent = null;
        while(iterator.hasNext()) 
        {
            BOSHClientResponseListener boshclientresponselistener = (BOSHClientResponseListener)iterator.next();
            if(boshmessageevent == null)
                boshmessageevent = BOSHMessageEvent.createResponseReceivedEvent(this, abstractbody);
            try
            {
                boshclientresponselistener.responseReceived(boshmessageevent);
            }
            catch(Exception exception)
            {
                LOG.log(Level.WARNING, "Unhandled Exception", exception);
            }
        }
    }

    private long getDefaultEmptyRequestDelay()
    {
        assertLocked();
        AttrPolling attrpolling = cmParams.getPollingInterval();
        long l;
        if(attrpolling == null)
            l = EMPTY_REQUEST_DELAY;
        else
            l = attrpolling.getInMilliseconds();
        return l;
    }

    private TerminalBindingCondition getTerminalBindingCondition(int i, AbstractBody abstractbody)
    {
        assertLocked();
        TerminalBindingCondition terminalbindingcondition;
        if(isTermination(abstractbody))
            terminalbindingcondition = TerminalBindingCondition.forString(abstractbody.getAttribute(Attributes.CONDITION));
        else
        if(cmParams != null && cmParams.getVersion() == null)
            terminalbindingcondition = TerminalBindingCondition.forHTTPResponseCode(i);
        else
            terminalbindingcondition = null;
        return terminalbindingcondition;
    }

    private void init()
    {
        assertUnlocked();
        lock.lock();
        httpSender.init(cfg);
        procThread = new Thread(procRunnable);
        procThread.setDaemon(true);
        procThread.setName((new StringBuilder()).append(com/kenai/jbosh/BOSHClient.getSimpleName()).append("[").append(System.identityHashCode(this)).append("]: Receive thread").toString());
        procThread.start();
        lock.unlock();
        return;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    private boolean isImmediatelySendable(AbstractBody abstractbody)
    {
        assertLocked();
        boolean flag;
        if(cmParams == null)
        {
            flag = exchanges.isEmpty();
        } else
        {
            AttrRequests attrrequests = cmParams.getRequests();
            if(attrrequests == null)
            {
                flag = true;
            } else
            {
                int i = attrrequests.intValue();
                if(exchanges.size() < i)
                    flag = true;
                else
                if(exchanges.size() == i && (isTermination(abstractbody) || isPause(abstractbody)))
                    flag = true;
                else
                    flag = false;
            }
        }
        return flag;
    }

    private static boolean isPause(AbstractBody abstractbody)
    {
        boolean flag;
        if(abstractbody.getAttribute(Attributes.PAUSE) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isRecoverableBindingCondition(AbstractBody abstractbody)
    {
        return "error".equals(abstractbody.getAttribute(Attributes.TYPE));
    }

    private static boolean isTermination(AbstractBody abstractbody)
    {
        return "terminate".equals(abstractbody.getAttribute(Attributes.TYPE));
    }

    private boolean isWorking()
    {
        assertLocked();
        boolean flag;
        if(procThread != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private HTTPExchange nextExchange()
    {
        Thread thread;
        HTTPExchange httpexchange;
        assertUnlocked();
        thread = Thread.currentThread();
        httpexchange = null;
        lock.lock();
_L5:
        boolean flag = thread.equals(procThread);
        if(flag) goto _L2; else goto _L1
_L1:
        lock.unlock();
        return httpexchange;
_L2:
        httpexchange = (HTTPExchange)exchanges.peek();
        if(httpexchange != null) goto _L4; else goto _L3
_L3:
        notEmpty.await();
_L4:
        if(httpexchange == null) goto _L5; else goto _L1
        InterruptedException interruptedexception;
        interruptedexception;
        LOG.log(Level.FINEST, "Interrupted", interruptedexception);
          goto _L4
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    private void processExchange(HTTPExchange httpexchange)
    {
        assertUnlocked();
        AbstractBody abstractbody;
        int i;
        HTTPResponse httpresponse = httpexchange.getHTTPResponse();
        abstractbody = httpresponse.getBody();
        i = httpresponse.getHTTPStatus();
        AbstractBody abstractbody1;
        fireResponseReceived(abstractbody);
        abstractbody1 = httpexchange.getRequest();
        lock.lock();
        if(isWorking()) goto _L2; else goto _L1
_L1:
        lock.unlock();
        if(!lock.isHeldByCurrentThread())
            break MISSING_BLOCK_LABEL_120;
        exchanges.remove(httpexchange);
        if(exchanges.isEmpty())
            scheduleEmptyRequest(processPauseRequest(abstractbody1));
        notFull.signalAll();
        lock.unlock();
_L3:
        return;
        BOSHException boshexception;
        boshexception;
        LOG.log(Level.FINEST, "Could not obtain response", boshexception);
        dispose(boshexception);
          goto _L3
        InterruptedException interruptedexception;
        interruptedexception;
        LOG.log(Level.FINEST, "Interrupted", interruptedexception);
        dispose(interruptedexception);
          goto _L3
        Exception exception5;
        exception5;
        lock.unlock();
        throw exception5;
_L2:
        CMSessionParams cmsessionparams;
        if(cmParams == null)
        {
            cmParams = CMSessionParams.fromSessionInit(abstractbody1, abstractbody);
            fireConnectionEstablished();
        }
        cmsessionparams = cmParams;
        checkForTerminalBindingConditions(abstractbody, i);
        if(!isTermination(abstractbody))
            break MISSING_BLOCK_LABEL_306;
        lock.unlock();
        dispose(null);
        if(!lock.isHeldByCurrentThread()) goto _L3; else goto _L4
_L4:
        exchanges.remove(httpexchange);
        if(exchanges.isEmpty())
            scheduleEmptyRequest(processPauseRequest(abstractbody1));
        notFull.signalAll();
        lock.unlock();
          goto _L3
        Exception exception4;
        exception4;
        lock.unlock();
        throw exception4;
        if(!isRecoverableBindingCondition(abstractbody)) goto _L6; else goto _L5
_L5:
        ArrayList arraylist2;
        if(null != null)
            break MISSING_BLOCK_LABEL_833;
        arraylist2 = new ArrayList(exchanges.size());
_L11:
        for(Iterator iterator1 = exchanges.iterator(); iterator1.hasNext(); arraylist2.add(new HTTPExchange(((HTTPExchange)iterator1.next()).getRequest())));
          goto _L7
        BOSHException boshexception1;
        boshexception1;
        LOG.log(Level.FINEST, "Could not process response", boshexception1);
        lock.unlock();
        dispose(boshexception1);
        if(!lock.isHeldByCurrentThread()) goto _L3; else goto _L8
_L8:
        exchanges.remove(httpexchange);
        if(exchanges.isEmpty())
            scheduleEmptyRequest(processPauseRequest(abstractbody1));
        notFull.signalAll();
        lock.unlock();
          goto _L3
_L7:
        HTTPExchange httpexchange3;
        for(Iterator iterator2 = arraylist2.iterator(); iterator2.hasNext(); exchanges.add(httpexchange3))
            httpexchange3 = (HTTPExchange)iterator2.next();

        break MISSING_BLOCK_LABEL_589;
        Exception exception;
        exception;
        if(!lock.isHeldByCurrentThread())
            break MISSING_BLOCK_LABEL_586;
        exchanges.remove(httpexchange);
        if(exchanges.isEmpty())
            scheduleEmptyRequest(processPauseRequest(abstractbody1));
        notFull.signalAll();
        lock.unlock();
        throw exception;
        ArrayList arraylist = arraylist2;
_L10:
        if(!lock.isHeldByCurrentThread())
            continue; /* Loop/switch isn't completed */
        exchanges.remove(httpexchange);
        if(exchanges.isEmpty())
            scheduleEmptyRequest(processPauseRequest(abstractbody1));
        notFull.signalAll();
        lock.unlock();
        if(arraylist == null) goto _L3; else goto _L9
_L9:
        Iterator iterator = arraylist.iterator();
        while(iterator.hasNext()) 
        {
            HTTPExchange httpexchange2 = (HTTPExchange)iterator.next();
            httpexchange2.setHTTPResponse(httpSender.send(cmsessionparams, httpexchange2.getRequest()));
            fireRequestSent(httpexchange2.getRequest());
        }
          goto _L3
_L6:
        ArrayList arraylist1;
        processRequestAcknowledgements(abstractbody1, abstractbody);
        processResponseAcknowledgementData(abstractbody1);
        HTTPExchange httpexchange1 = processResponseAcknowledgementReport(abstractbody);
        if(httpexchange1 == null || null != null)
            break MISSING_BLOCK_LABEL_827;
        arraylist1 = new ArrayList(1);
        arraylist1.add(httpexchange1);
        exchanges.add(httpexchange1);
        arraylist = arraylist1;
          goto _L10
        Exception exception3;
        exception3;
        lock.unlock();
        throw exception3;
        Exception exception2;
        exception2;
        lock.unlock();
        throw exception2;
        Exception exception1;
        exception1;
        lock.unlock();
        throw exception1;
        arraylist = null;
          goto _L10
        arraylist2 = null;
          goto _L11
    }

    private void processMessages()
    {
        LOG.log(Level.FINEST, "Processing thread starting");
_L2:
        HTTPExchange httpexchange = nextExchange();
        if(httpexchange == null)
        {
            LOG.log(Level.FINEST, "Processing thread exiting");
            return;
        }
        HTTPExchange httpexchange1;
        ExchangeInterceptor exchangeinterceptor = (ExchangeInterceptor)exchInterceptor.get();
        if(exchangeinterceptor == null)
            break; /* Loop/switch isn't completed */
        httpexchange1 = exchangeinterceptor.interceptExchange(httpexchange);
        if(httpexchange1 != null)
            break MISSING_BLOCK_LABEL_154;
        LOG.log(Level.FINE, (new StringBuilder()).append("Discarding exchange on request of test hook: RID=").append(httpexchange.getRequest().getAttribute(Attributes.RID)).toString());
        lock.lock();
        exchanges.remove(httpexchange);
        lock.unlock();
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        LOG.log(Level.FINEST, "Processing thread exiting");
        throw exception;
        Exception exception1;
        exception1;
        lock.unlock();
        throw exception1;
_L3:
        processExchange(httpexchange1);
        if(true) goto _L2; else goto _L1
_L1:
        httpexchange1 = httpexchange;
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    private long processPauseRequest(AbstractBody abstractbody)
    {
        assertLocked();
        if(cmParams == null || cmParams.getMaxPause() == null) goto _L2; else goto _L1
_L1:
        AttrPause attrpause = AttrPause.createFromString(abstractbody.getAttribute(Attributes.PAUSE));
        if(attrpause == null) goto _L2; else goto _L3
_L3:
        long l = attrpause.getInMilliseconds() - PAUSE_MARGIN;
        if(l >= 0L) goto _L5; else goto _L4
_L4:
        int i = EMPTY_REQUEST_DELAY;
        l = i;
_L5:
        return l;
        BOSHException boshexception;
        boshexception;
        LOG.log(Level.FINEST, "Could not extract", boshexception);
_L2:
        l = getDefaultEmptyRequestDelay();
        if(true) goto _L5; else goto _L6
_L6:
    }

    private void processRequestAcknowledgements(AbstractBody abstractbody, AbstractBody abstractbody1)
    {
        assertLocked();
        break MISSING_BLOCK_LABEL_4;
        if(cmParams.isAckingRequests() && abstractbody1.getAttribute(Attributes.REPORT) == null)
        {
            String s = abstractbody1.getAttribute(Attributes.ACK);
            Long long1;
            Iterator iterator;
            if(s == null)
                long1 = Long.valueOf(Long.parseLong(abstractbody.getAttribute(Attributes.RID)));
            else
                long1 = Long.valueOf(Long.parseLong(s));
            if(LOG.isLoggable(Level.FINEST))
                LOG.finest((new StringBuilder()).append("Removing pending acks up to: ").append(long1).toString());
            iterator = pendingRequestAcks.iterator();
            while(iterator.hasNext()) 
                if(Long.valueOf(Long.parseLong(((AbstractBody)iterator.next()).getAttribute(Attributes.RID))).compareTo(long1) <= 0)
                    iterator.remove();
        }
        return;
    }

    private void processResponseAcknowledgementData(AbstractBody abstractbody)
    {
        assertLocked();
        Long long1 = Long.valueOf(Long.parseLong(abstractbody.getAttribute(Attributes.RID)));
        if(responseAck.equals(Long.valueOf(-1L)))
        {
            responseAck = long1;
        } else
        {
            pendingResponseAcks.add(long1);
            Long long2 = Long.valueOf(1L + responseAck.longValue());
            while(!pendingResponseAcks.isEmpty() && long2.equals(pendingResponseAcks.first())) 
            {
                responseAck = long2;
                pendingResponseAcks.remove(long2);
                long2 = Long.valueOf(1L + long2.longValue());
            }
        }
    }

    private HTTPExchange processResponseAcknowledgementReport(AbstractBody abstractbody)
        throws BOSHException
    {
        assertLocked();
        String s = abstractbody.getAttribute(Attributes.REPORT);
        HTTPExchange httpexchange;
        if(s == null)
        {
            httpexchange = null;
        } else
        {
            Long long1 = Long.valueOf(Long.parseLong(s));
            Long long2 = Long.valueOf(Long.parseLong(abstractbody.getAttribute(Attributes.TIME)));
            if(LOG.isLoggable(Level.FINE))
                LOG.fine((new StringBuilder()).append("Received report of missing request (RID=").append(long1).append(", time=").append(long2).append("ms)").toString());
            Iterator iterator = pendingRequestAcks.iterator();
            AbstractBody abstractbody1 = null;
            while(iterator.hasNext() && abstractbody1 == null) 
            {
                AbstractBody abstractbody2 = (AbstractBody)iterator.next();
                if(!long1.equals(Long.valueOf(Long.parseLong(abstractbody2.getAttribute(Attributes.RID)))))
                    abstractbody2 = abstractbody1;
                abstractbody1 = abstractbody2;
            }
            if(abstractbody1 == null)
                throw new BOSHException((new StringBuilder()).append("Report of missing message with RID '").append(s).append("' but local copy of that request was not found").toString());
            httpexchange = new HTTPExchange(abstractbody1);
            exchanges.add(httpexchange);
            notEmpty.signalAll();
        }
        return httpexchange;
    }

    private void scheduleEmptyRequest(long l)
    {
        assertLocked();
        if(l < 0L)
            throw new IllegalArgumentException((new StringBuilder()).append("Empty request delay must be >= 0 (was: ").append(l).append(")").toString());
        clearEmptyRequest();
        if(isWorking())
        {
            if(LOG.isLoggable(Level.FINER))
                LOG.finer((new StringBuilder()).append("Scheduling empty request in ").append(l).append("ms").toString());
            try
            {
                emptyRequestFuture = schedExec.schedule(emptyRequestRunnable, l, TimeUnit.MILLISECONDS);
            }
            catch(RejectedExecutionException rejectedexecutionexception)
            {
                LOG.log(Level.FINEST, "Could not schedule empty request", rejectedexecutionexception);
            }
            drained.signalAll();
        }
    }

    private void sendEmptyRequest()
    {
        assertUnlocked();
        LOG.finest("Sending empty request");
        send(ComposableBody.builder().build());
_L1:
        return;
        BOSHException boshexception;
        boshexception;
        dispose(boshexception);
          goto _L1
    }

    public void addBOSHClientConnListener(BOSHClientConnListener boshclientconnlistener)
    {
        if(boshclientconnlistener == null)
        {
            throw new IllegalArgumentException("Listener may not be null");
        } else
        {
            connListeners.add(boshclientconnlistener);
            return;
        }
    }

    public void addBOSHClientRequestListener(BOSHClientRequestListener boshclientrequestlistener)
    {
        if(boshclientrequestlistener == null)
        {
            throw new IllegalArgumentException("Listener may not be null");
        } else
        {
            requestListeners.add(boshclientrequestlistener);
            return;
        }
    }

    public void addBOSHClientResponseListener(BOSHClientResponseListener boshclientresponselistener)
    {
        if(boshclientresponselistener == null)
        {
            throw new IllegalArgumentException("Listener may not be null");
        } else
        {
            responseListeners.add(boshclientresponselistener);
            return;
        }
    }

    public void close()
    {
        dispose(new BOSHException("Session explicitly closed by caller"));
    }

    public void disconnect()
        throws BOSHException
    {
        disconnect(ComposableBody.builder().build());
    }

    public void disconnect(ComposableBody composablebody)
        throws BOSHException
    {
        if(composablebody == null)
        {
            throw new IllegalArgumentException("Message body may not be null");
        } else
        {
            ComposableBody.Builder builder = composablebody.rebuild();
            builder.setAttribute(Attributes.TYPE, "terminate");
            send(builder.build());
            return;
        }
    }

    void drain()
    {
        lock.lock();
        LOG.finest("Waiting while draining...");
_L3:
        if(!isWorking())
            break MISSING_BLOCK_LABEL_82;
        if(emptyRequestFuture == null) goto _L2; else goto _L1
_L1:
        boolean flag = emptyRequestFuture.isDone();
        if(!flag)
            break MISSING_BLOCK_LABEL_82;
_L2:
        drained.await();
          goto _L3
        InterruptedException interruptedexception;
        interruptedexception;
        LOG.log(Level.FINEST, "Interrupted", interruptedexception);
          goto _L3
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        LOG.finest("Drained");
        lock.unlock();
        return;
    }

    public BOSHClientConfig getBOSHClientConfig()
    {
        return cfg;
    }

    CMSessionParams getCMSessionParams()
    {
        lock.lock();
        CMSessionParams cmsessionparams = cmParams;
        lock.unlock();
        return cmsessionparams;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public boolean pause()
    {
        assertUnlocked();
        lock.lock();
        CMSessionParams cmsessionparams = cmParams;
        if(cmsessionparams != null) goto _L2; else goto _L1
_L1:
        boolean flag;
        lock.unlock();
        flag = false;
_L4:
        return flag;
_L2:
        AttrMaxPause attrmaxpause = cmParams.getMaxPause();
        if(attrmaxpause == null)
        {
            lock.unlock();
            flag = false;
        } else
        {
            lock.unlock();
            Exception exception;
            try
            {
                send(ComposableBody.builder().setAttribute(Attributes.PAUSE, attrmaxpause.toString()).build());
            }
            catch(BOSHException boshexception)
            {
                LOG.log(Level.FINEST, "Could not send pause", boshexception);
            }
            flag = true;
        }
        if(true) goto _L4; else goto _L3
_L3:
        exception;
        lock.unlock();
        throw exception;
    }

    public void removeBOSHClientConnListener(BOSHClientConnListener boshclientconnlistener)
    {
        if(boshclientconnlistener == null)
        {
            throw new IllegalArgumentException("Listener may not be null");
        } else
        {
            connListeners.remove(boshclientconnlistener);
            return;
        }
    }

    public void removeBOSHClientRequestListener(BOSHClientRequestListener boshclientrequestlistener)
    {
        if(boshclientrequestlistener == null)
        {
            throw new IllegalArgumentException("Listener may not be null");
        } else
        {
            requestListeners.remove(boshclientrequestlistener);
            return;
        }
    }

    public void removeBOSHClientResponseListener(BOSHClientResponseListener boshclientresponselistener)
    {
        if(boshclientresponselistener == null)
        {
            throw new IllegalArgumentException("Listener may not be null");
        } else
        {
            responseListeners.remove(boshclientresponselistener);
            return;
        }
    }

    public void send(ComposableBody composablebody)
        throws BOSHException
    {
        assertUnlocked();
        if(composablebody == null)
            throw new IllegalArgumentException("Message body may not be null");
        lock.lock();
        blockUntilSendable(composablebody);
        if(!isWorking() && !isTermination(composablebody))
            throw new BOSHException("Cannot send message when session is closed");
        break MISSING_BLOCK_LABEL_66;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        long l;
        CMSessionParams cmsessionparams;
        ComposableBody composablebody1;
        l = requestIDSeq.getNextRID();
        cmsessionparams = cmParams;
        if(cmsessionparams != null || !exchanges.isEmpty())
            break MISSING_BLOCK_LABEL_180;
        composablebody1 = applySessionCreationRequest(l, composablebody);
_L1:
        HTTPExchange httpexchange;
        httpexchange = new HTTPExchange(composablebody1);
        exchanges.add(httpexchange);
        notEmpty.signalAll();
        clearEmptyRequest();
        lock.unlock();
        AbstractBody abstractbody = httpexchange.getRequest();
        httpexchange.setHTTPResponse(httpSender.send(cmsessionparams, abstractbody));
        fireRequestSent(abstractbody);
        return;
        composablebody1 = applySessionData(l, composablebody);
        if(cmParams.isAckingRequests())
            pendingRequestAcks.add(composablebody1);
          goto _L1
    }

    void setExchangeInterceptor(ExchangeInterceptor exchangeinterceptor)
    {
        exchInterceptor.set(exchangeinterceptor);
    }

    static final boolean $assertionsDisabled = false;
    private static final boolean ASSERTIONS = false;
    private static final int DEFAULT_EMPTY_REQUEST_DELAY = 100;
    private static final int DEFAULT_PAUSE_MARGIN = 500;
    private static final int EMPTY_REQUEST_DELAY = 0;
    private static final String ERROR = "error";
    private static final String INTERRUPTED = "Interrupted";
    private static final Logger LOG;
    private static final String NULL_LISTENER = "Listener may not be null";
    private static final int PAUSE_MARGIN = 0;
    private static final String TERMINATE = "terminate";
    private static final String UNHANDLED = "Unhandled Exception";
    private final BOSHClientConfig cfg;
    private CMSessionParams cmParams;
    private final Set connListeners = new CopyOnWriteArraySet();
    private final Condition drained;
    private ScheduledFuture emptyRequestFuture;
    private final Runnable emptyRequestRunnable = new Runnable() {

        public void run()
        {
            sendEmptyRequest();
        }

        final BOSHClient this$0;

            
            {
                this$0 = BOSHClient.this;
                super();
            }
    }
;
    private final AtomicReference exchInterceptor = new AtomicReference();
    private Queue exchanges;
    private final HTTPSender httpSender = new ApacheHTTPSender();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty;
    private final Condition notFull;
    private List pendingRequestAcks;
    private SortedSet pendingResponseAcks;
    private final Runnable procRunnable = new Runnable() {

        public void run()
        {
            processMessages();
        }

        final BOSHClient this$0;

            
            {
                this$0 = BOSHClient.this;
                super();
            }
    }
;
    private Thread procThread;
    private final RequestIDSequence requestIDSeq = new RequestIDSequence();
    private final Set requestListeners = new CopyOnWriteArraySet();
    private Long responseAck;
    private final Set responseListeners = new CopyOnWriteArraySet();
    private final ScheduledExecutorService schedExec = Executors.newSingleThreadScheduledExecutor();

    static 
    {
        String s;
        boolean flag;
        if(!com/kenai/jbosh/BOSHClient.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
        LOG = Logger.getLogger(com/kenai/jbosh/BOSHClient.getName());
        EMPTY_REQUEST_DELAY = Integer.getInteger((new StringBuilder()).append(com/kenai/jbosh/BOSHClient.getName()).append(".emptyRequestDelay").toString(), 100).intValue();
        PAUSE_MARGIN = Integer.getInteger((new StringBuilder()).append(com/kenai/jbosh/BOSHClient.getName()).append(".pauseMargin").toString(), 500).intValue();
        s = (new StringBuilder()).append(com/kenai/jbosh/BOSHClient.getSimpleName()).append(".assertionsEnabled").toString();
        if(System.getProperty(s) != null) goto _L2; else goto _L1
_L2:
        flag1 = Boolean.getBoolean(s);
_L4:
        ASSERTIONS = flag1;
        return;
_L1:
        boolean flag1;
        if(!$assertionsDisabled)
        {
            if(false)
                throw new AssertionError();
            flag1 = true;
            continue; /* Loop/switch isn't completed */
        }
        flag1 = false;
        if(true) goto _L4; else goto _L3
_L3:
    }


}

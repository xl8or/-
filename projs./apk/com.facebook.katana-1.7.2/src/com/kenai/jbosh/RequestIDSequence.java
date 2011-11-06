// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestIDSequence.java

package com.kenai.jbosh;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class RequestIDSequence
{

    RequestIDSequence()
    {
        nextRequestID = new AtomicLong();
        nextRequestID = new AtomicLong(generateInitialValue());
    }

    private long generateInitialValue()
    {
        LOCK.lock();
_L2:
        long l = RAND.nextLong();
        long l1 = l & 0xffffffffL;
        if(l1 > 0x0L) goto _L2; else goto _L1
_L1:
        LOCK.unlock();
        return l1;
        Exception exception;
        exception;
        LOCK.unlock();
        throw exception;
    }

    public long getNextRID()
    {
        return nextRequestID.getAndIncrement();
    }

    private static final int INCREMENT_BITS = 32;
    private static final Lock LOCK = new ReentrantLock();
    private static final long MASK = 0xffffffffL;
    private static final int MAX_BITS = 53;
    private static final long MAX_INITIAL = 0x0L;
    private static final long MIN_INCREMENTS = 0x0L;
    private static final SecureRandom RAND = new SecureRandom();
    private AtomicLong nextRequestID;

}

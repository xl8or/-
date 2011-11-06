// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkerThread.java

package com.facebook.katana.binding;

import android.os.Handler;
import android.os.Looper;

public class WorkerThread extends Thread
{

    public WorkerThread()
    {
        start();
        do
        {
            if(m_threadHandler != null)
                break;
            try
            {
                Thread.sleep(100L);
                continue;
            }
            catch(InterruptedException interruptedexception) { }
            break;
        } while(true);
    }

    public Handler getHandler()
    {
        return m_handler;
    }

    public Handler getThreadHandler()
    {
        return m_threadHandler;
    }

    public void quit()
    {
        if(m_looper == null)
            break MISSING_BLOCK_LABEL_26;
        m_looper.quit();
        m_looper = null;
        join(3000L);
_L2:
        return;
        InterruptedException interruptedexception;
        interruptedexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void run()
    {
        setPriority(1);
        Looper.prepare();
        m_threadHandler = new Handler();
        m_looper = Looper.myLooper();
        Looper.loop();
    }

    private final Handler m_handler = new Handler();
    private Looper m_looper;
    private Handler m_threadHandler;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReentrantCallback.java

package com.facebook.katana.util;

import java.util.*;

public class ReentrantCallback
{

    public ReentrantCallback()
    {
    }

    public void addListener(Object obj)
    {
        mListenerPendingDeletions.remove(obj);
        mListenerPendingAdditions.add(obj);
    }

    public void clear()
    {
        mListeners.clear();
        mListenerPendingAdditions.clear();
        mListenerPendingDeletions.clear();
    }

    public int count()
    {
        int i = 0;
        Iterator iterator = mListeners.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Object obj1 = iterator.next();
            if(!mListenerPendingDeletions.contains(obj1))
                i++;
        } while(true);
        for(Iterator iterator1 = mListenerPendingAdditions.iterator(); iterator1.hasNext();)
        {
            Object obj = iterator1.next();
            if(!mListeners.contains(obj))
            {
                i++;
                if(!$assertionsDisabled && mListenerPendingDeletions.contains(obj))
                    throw new AssertionError();
            }
        }

        return i;
    }

    public Set getListeners()
    {
        mListeners.removeAll(mListenerPendingDeletions);
        mListeners.addAll(mListenerPendingAdditions);
        mListenerPendingAdditions.clear();
        mListenerPendingDeletions.clear();
        return mListeners;
    }

    public void removeListener(Object obj)
    {
        mListenerPendingAdditions.remove(obj);
        mListenerPendingDeletions.add(obj);
    }

    static final boolean $assertionsDisabled;
    private final Set mListenerPendingAdditions = new HashSet();
    private final Set mListenerPendingDeletions = new HashSet();
    private final Set mListeners = new HashSet();

    static 
    {
        boolean flag;
        if(!com/facebook/katana/util/ReentrantCallback.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

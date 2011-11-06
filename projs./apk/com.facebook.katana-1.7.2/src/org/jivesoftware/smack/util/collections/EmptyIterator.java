// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyIterator.java

package org.jivesoftware.smack.util.collections;

import java.util.Iterator;

// Referenced classes of package org.jivesoftware.smack.util.collections:
//            AbstractEmptyIterator, ResettableIterator

public class EmptyIterator extends AbstractEmptyIterator
    implements ResettableIterator
{

    protected EmptyIterator()
    {
    }

    public static Iterator getInstance()
    {
        return INSTANCE;
    }

    public volatile void add(Object obj)
    {
        super.add(obj);
    }

    public volatile Object getKey()
    {
        return super.getKey();
    }

    public volatile Object getValue()
    {
        return super.getValue();
    }

    public volatile boolean hasNext()
    {
        return super.hasNext();
    }

    public volatile boolean hasPrevious()
    {
        return super.hasPrevious();
    }

    public volatile Object next()
    {
        return super.next();
    }

    public volatile int nextIndex()
    {
        return super.nextIndex();
    }

    public volatile Object previous()
    {
        return super.previous();
    }

    public volatile int previousIndex()
    {
        return super.previousIndex();
    }

    public volatile void remove()
    {
        super.remove();
    }

    public volatile void reset()
    {
        super.reset();
    }

    public volatile void set(Object obj)
    {
        super.set(obj);
    }

    public volatile Object setValue(Object obj)
    {
        return super.setValue(obj);
    }

    public static final Iterator INSTANCE;
    public static final ResettableIterator RESETTABLE_INSTANCE;

    static 
    {
        RESETTABLE_INSTANCE = new EmptyIterator();
        INSTANCE = RESETTABLE_INSTANCE;
    }
}

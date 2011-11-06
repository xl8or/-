// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractEmptyIterator.java

package org.jivesoftware.smack.util.collections;

import java.util.NoSuchElementException;

abstract class AbstractEmptyIterator
{

    protected AbstractEmptyIterator()
    {
    }

    public void add(Object obj)
    {
        throw new UnsupportedOperationException("add() not supported for empty Iterator");
    }

    public Object getKey()
    {
        throw new IllegalStateException("Iterator contains no elements");
    }

    public Object getValue()
    {
        throw new IllegalStateException("Iterator contains no elements");
    }

    public boolean hasNext()
    {
        return false;
    }

    public boolean hasPrevious()
    {
        return false;
    }

    public Object next()
    {
        throw new NoSuchElementException("Iterator contains no elements");
    }

    public int nextIndex()
    {
        return 0;
    }

    public Object previous()
    {
        throw new NoSuchElementException("Iterator contains no elements");
    }

    public int previousIndex()
    {
        return -1;
    }

    public void remove()
    {
        throw new IllegalStateException("Iterator contains no elements");
    }

    public void reset()
    {
    }

    public void set(Object obj)
    {
        throw new IllegalStateException("Iterator contains no elements");
    }

    public Object setValue(Object obj)
    {
        throw new IllegalStateException("Iterator contains no elements");
    }
}

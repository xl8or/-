// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BoundedLinkedList.java

package org.acra.util;

import java.util.*;

public class BoundedLinkedList extends LinkedList
{

    public BoundedLinkedList(int i)
    {
        maxSize = -1;
        maxSize = i;
    }

    public void add(int i, Object obj)
    {
        if(size() == maxSize)
            removeFirst();
        super.add(i, obj);
    }

    public boolean add(Object obj)
    {
        if(size() == maxSize)
            removeFirst();
        return super.add(obj);
    }

    public boolean addAll(int i, Collection collection)
    {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection collection)
    {
        int i = (size() + collection.size()) - maxSize;
        if(i > 0)
            removeRange(0, i);
        return super.addAll(collection);
    }

    public void addFirst(Object obj)
    {
        throw new UnsupportedOperationException();
    }

    public void addLast(Object obj)
    {
        add(obj);
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        for(Iterator iterator = iterator(); iterator.hasNext(); stringbuilder.append(iterator.next().toString()));
        return stringbuilder.toString();
    }

    private int maxSize;
}

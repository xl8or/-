// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.util;

import java.lang.reflect.Array;

public final class ObjectBuffer
{
    static final class Node
    {

        public Object[] getData()
        {
            return _data;
        }

        public void linkNext(Node node)
        {
            if(_next != null)
            {
                throw new IllegalStateException();
            } else
            {
                _next = node;
                return;
            }
        }

        public Node next()
        {
            return _next;
        }

        final Object _data[];
        Node _next;

        public Node(Object aobj[])
        {
            _data = aobj;
        }
    }


    public ObjectBuffer()
    {
    }

    protected final void _copyTo(Object obj, int i, Object aobj[], int j)
    {
        Node node = _bufferHead;
        int k = 0;
        for(; node != null; node = node.next())
        {
            Object aobj1[] = node.getData();
            int i1 = aobj1.length;
            System.arraycopy(((Object) (aobj1)), 0, obj, k, i1);
            k += i1;
        }

        System.arraycopy(((Object) (aobj)), 0, obj, k, j);
        int l = k + j;
        if(l != i)
            throw new IllegalStateException((new StringBuilder()).append("Should have gotten ").append(i).append(" entries, got ").append(l).toString());
        else
            return;
    }

    protected void _reset()
    {
        if(_bufferTail != null)
            _freeBuffer = _bufferTail.getData();
        _bufferTail = null;
        _bufferHead = null;
        _bufferedEntryCount = 0;
    }

    public Object[] appendCompletedChunk(Object aobj[])
    {
        Node node = new Node(aobj);
        int i;
        int j;
        if(_bufferHead == null)
        {
            _bufferTail = node;
            _bufferHead = node;
        } else
        {
            _bufferTail.linkNext(node);
            _bufferTail = node;
        }
        i = aobj.length;
        _bufferedEntryCount = i + _bufferedEntryCount;
        if(i < 16384)
            j = i + i;
        else
            j = i + (i >> 2);
        return new Object[j];
    }

    public int bufferedSize()
    {
        return _bufferedEntryCount;
    }

    public Object[] completeAndClearBuffer(Object aobj[], int i)
    {
        int j = i + _bufferedEntryCount;
        Object aobj1[] = new Object[j];
        _copyTo(((Object) (aobj1)), j, aobj, i);
        return aobj1;
    }

    public Object[] completeAndClearBuffer(Object aobj[], int i, Class class1)
    {
        int j = i + _bufferedEntryCount;
        Object aobj1[] = (Object[])(Object[])Array.newInstance(class1, j);
        _copyTo(((Object) (aobj1)), j, aobj, i);
        _reset();
        return aobj1;
    }

    public int initialCapacity()
    {
        int i;
        if(_freeBuffer == null)
            i = 0;
        else
            i = _freeBuffer.length;
        return i;
    }

    public Object[] resetAndStart()
    {
        _reset();
        Object aobj[];
        if(_freeBuffer == null)
            aobj = new Object[12];
        else
            aobj = _freeBuffer;
        return aobj;
    }

    static final int INITIAL_CHUNK_SIZE = 12;
    static final int MAX_CHUNK_SIZE = 0x40000;
    static final int SMALL_CHUNK_SIZE = 16384;
    Node _bufferHead;
    Node _bufferTail;
    int _bufferedEntryCount;
    Object _freeBuffer[];
}

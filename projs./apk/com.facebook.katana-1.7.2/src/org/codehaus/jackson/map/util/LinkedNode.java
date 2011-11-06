// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.util;


public final class LinkedNode
{

    public LinkedNode(Object obj, LinkedNode linkednode)
    {
        _value = obj;
        _next = linkednode;
    }

    public static boolean contains(LinkedNode linkednode, Object obj)
    {
        LinkedNode linkednode1 = linkednode;
_L3:
        if(linkednode1 == null)
            break MISSING_BLOCK_LABEL_26;
        if(linkednode1.value() != obj) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        linkednode1 = linkednode1.next();
          goto _L3
        flag = false;
          goto _L4
    }

    public LinkedNode next()
    {
        return _next;
    }

    public Object value()
    {
        return _value;
    }

    final LinkedNode _next;
    final Object _value;
}

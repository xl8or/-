// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractKeyValue.java

package org.jivesoftware.smack.util.collections;


// Referenced classes of package org.jivesoftware.smack.util.collections:
//            KeyValue

public abstract class AbstractKeyValue
    implements KeyValue
{

    protected AbstractKeyValue(Object obj, Object obj1)
    {
        key = obj;
        value = obj1;
    }

    public Object getKey()
    {
        return key;
    }

    public Object getValue()
    {
        return value;
    }

    public String toString()
    {
        return (new StringBuilder()).append(getKey()).append('=').append(getValue()).toString();
    }

    protected Object key;
    protected Object value;
}

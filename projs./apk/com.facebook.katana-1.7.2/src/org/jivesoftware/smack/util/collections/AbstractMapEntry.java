// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractMapEntry.java

package org.jivesoftware.smack.util.collections;


// Referenced classes of package org.jivesoftware.smack.util.collections:
//            AbstractKeyValue

public abstract class AbstractMapEntry extends AbstractKeyValue
    implements java.util.Map.Entry
{

    protected AbstractMapEntry(Object obj, Object obj1)
    {
        super(obj, obj1);
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == this)
            flag = true;
        else
        if(!(obj instanceof java.util.Map.Entry))
        {
            flag = false;
        } else
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)obj;
            if((getKey() != null ? getKey().equals(entry.getKey()) : entry.getKey() == null) && (getValue() != null ? getValue().equals(entry.getValue()) : entry.getValue() == null))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public int hashCode()
    {
        int i;
        int j;
        if(getKey() == null)
            i = 0;
        else
            i = getKey().hashCode();
        if(getValue() == null)
            j = 0;
        else
            j = getValue().hashCode();
        return i ^ j;
    }

    public Object setValue(Object obj)
    {
        Object obj1 = value;
        value = obj;
        return obj1;
    }
}

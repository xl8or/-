// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.sym;


public abstract class Name
{

    protected Name(String s, int i)
    {
        mName = s;
        mHashCode = i;
    }

    public abstract boolean equals(int i);

    public abstract boolean equals(int i, int j);

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == this)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public abstract boolean equals(int ai[], int i);

    public String getName()
    {
        return mName;
    }

    public final int hashCode()
    {
        return mHashCode;
    }

    public String toString()
    {
        return mName;
    }

    protected final int mHashCode;
    protected final String mName;
}

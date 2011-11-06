// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JMDictDestination.java

package com.facebook.katana.util.jsonmirror;

import java.util.List;
import java.util.Map;

// Referenced classes of package com.facebook.katana.util.jsonmirror:
//            JMException

public abstract class JMDictDestination
{

    public JMDictDestination()
    {
    }

    protected void postprocess()
        throws JMException
    {
    }

    protected abstract void setBoolean(String s, boolean flag)
        throws JMException;

    protected abstract void setDictionary(String s, JMDictDestination jmdictdestination)
        throws JMException;

    protected abstract void setDouble(String s, double d)
        throws JMException;

    protected abstract void setList(String s, List list)
        throws JMException;

    protected abstract void setLong(String s, long l)
        throws JMException;

    protected abstract void setSimpleDictionary(String s, Map map)
        throws JMException;

    protected abstract void setString(String s, String s1)
        throws JMException;
}

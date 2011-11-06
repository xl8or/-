// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JMDict.java

package com.facebook.katana.util.jsonmirror.types;

import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.jsonmirror.JMDictDestination;
import com.facebook.katana.util.jsonmirror.JMFatalException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

// Referenced classes of package com.facebook.katana.util.jsonmirror.types:
//            JMBase

public class JMDict extends JMBase
{

    public JMDict(Class class1, Map map)
    {
        this(class1, map, false);
    }

    public JMDict(Class class1, Map map, boolean flag)
    {
        try
        {
            mConstructor = class1.getDeclaredConstructor(new Class[0]);
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            throw new JMFatalException(nosuchmethodexception.getMessage());
        }
        mConstructor.setAccessible(true);
        mFieldTypes = map;
        mIgnoreUnexpectedJsonFields = flag;
    }

    public Map getFieldTypes()
    {
        return mFieldTypes;
    }

    public JMDictDestination getNewInstance()
    {
        JMDictDestination jmdictdestination1 = (JMDictDestination)mConstructor.newInstance(new Object[0]);
        JMDictDestination jmdictdestination = jmdictdestination1;
_L2:
        return jmdictdestination;
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        jmdictdestination = null;
        continue; /* Loop/switch isn't completed */
        InvocationTargetException invocationtargetexception;
        invocationtargetexception;
        jmdictdestination = null;
        continue; /* Loop/switch isn't completed */
        InstantiationException instantiationexception;
        instantiationexception;
        jmdictdestination = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Tuple getObjectForJsonField(String s)
    {
        return (Tuple)mFieldTypes.get(s);
    }

    private final Constructor mConstructor;
    private final Map mFieldTypes;
    public final boolean mIgnoreUnexpectedJsonFields;
}

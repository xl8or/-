// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JMBase.java

package com.facebook.katana.util.jsonmirror.types;

import com.facebook.katana.util.jsonmirror.JMFatalException;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.facebook.katana.util.jsonmirror.types:
//            JMString, JMBoolean, JMLong, JMDouble, 
//            JMSimpleDict

public abstract class JMBase
{

    public JMBase()
    {
    }

    public static JMBase getInstanceForClass(Class class1)
    {
        Object obj = (JMBase)classCache.get(class1);
        if(obj != null) goto _L2; else goto _L1
_L1:
        if(class1 != java/lang/String) goto _L4; else goto _L3
_L3:
        obj = STRING;
_L6:
        if(obj != null)
            classCache.put(class1, obj);
_L2:
        return ((JMBase) (obj));
_L4:
        if(class1 == java/lang/Boolean || class1 == Boolean.TYPE)
            obj = BOOLEAN;
        else
        if(class1 == java/lang/Long || class1 == Long.TYPE || class1 == java/lang/Integer || class1 == Integer.TYPE)
            obj = LONG;
        else
        if(class1 == java/lang/Double || class1 == Double.TYPE || class1 == java/lang/Float || class1 == Float.TYPE)
            obj = DOUBLE;
        else
        if(java/util/Map.isAssignableFrom(class1))
            obj = SIMPLE_DICT;
        else
        if(com/facebook/katana/util/jsonmirror/types/JMBase.isAssignableFrom(class1))
            try
            {
                obj = (JMBase)class1.newInstance();
            }
            catch(IllegalAccessException illegalaccessexception)
            {
                throw new JMFatalException((new StringBuilder()).append("Error instantiating element parser for class ").append(class1.getName()).toString());
            }
            catch(InstantiationException instantiationexception)
            {
                throw new JMFatalException((new StringBuilder()).append("Error instantiating element parser for class ").append(class1.getName()).toString());
            }
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static final JMBoolean BOOLEAN = new JMBoolean();
    public static final JMDouble DOUBLE = new JMDouble();
    public static final JMLong LONG = new JMLong();
    public static final JMSimpleDict SIMPLE_DICT = new JMSimpleDict();
    public static final JMString STRING = new JMString();
    protected static Map classCache = new HashMap();

}

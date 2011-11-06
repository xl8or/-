// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.introspect;

import java.lang.reflect.Method;

public final class MethodKey
{

    public MethodKey(String s, Class aclass[])
    {
        _name = s;
        Class aclass1[];
        if(aclass == null)
            aclass1 = NO_CLASSES;
        else
            aclass1 = aclass;
        _argTypes = aclass1;
    }

    public MethodKey(Method method)
    {
        this(method.getName(), method.getParameterTypes());
    }

    public boolean equals(Object obj)
    {
        if(obj != this) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L9:
        return flag;
_L2:
        Class aclass[];
        int i;
        int j;
        if(obj == null)
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        if(obj.getClass() != getClass())
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        MethodKey methodkey = (MethodKey)obj;
        if(!_name.equals(methodkey._name))
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        aclass = methodkey._argTypes;
        i = _argTypes.length;
        if(aclass.length != i)
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        j = 0;
_L5:
        Class class1;
        Class class2;
        if(j >= i)
            break; /* Loop/switch isn't completed */
        class1 = aclass[j];
        class2 = _argTypes[j];
          goto _L3
_L7:
        j++;
        if(true) goto _L5; else goto _L4
_L3:
        if(class1 == class2 || class1.isAssignableFrom(class2) || class2.isAssignableFrom(class1)) goto _L7; else goto _L6
_L6:
        flag = false;
        continue; /* Loop/switch isn't completed */
_L4:
        flag = true;
        if(true) goto _L9; else goto _L8
_L8:
    }

    public int hashCode()
    {
        return _name.hashCode() + _argTypes.length;
    }

    public String toString()
    {
        return (new StringBuilder()).append(_name).append("(").append(_argTypes.length).append("-args)").toString();
    }

    static final Class NO_CLASSES[] = new Class[0];
    final Class _argTypes[];
    final String _name;

}

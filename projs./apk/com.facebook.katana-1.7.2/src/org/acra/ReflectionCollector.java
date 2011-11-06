// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReflectionCollector.java

package org.acra;

import java.lang.reflect.*;

public class ReflectionCollector
{

    public ReflectionCollector()
    {
    }

    public static String collectConstants(Class class1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Field afield[] = class1.getFields();
        int i = afield.length;
        int j = 0;
        while(j < i) 
        {
            Field field = afield[j];
            stringbuilder.append(field.getName()).append("=");
            try
            {
                stringbuilder.append(field.get(null).toString());
            }
            catch(IllegalArgumentException illegalargumentexception)
            {
                stringbuilder.append("N/A");
            }
            catch(IllegalAccessException illegalaccessexception)
            {
                stringbuilder.append("N/A");
            }
            stringbuilder.append("\n");
            j++;
        }
        return stringbuilder.toString();
    }

    public static String collectStaticGettersResults(Class class1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Method amethod[] = class1.getMethods();
        int i = amethod.length;
        int j = 0;
        while(j < i) 
        {
            Method method = amethod[j];
            if(method.getParameterTypes().length == 0 && (method.getName().startsWith("get") || method.getName().startsWith("is")) && !method.getName().equals("getClass"))
                try
                {
                    stringbuilder.append(method.getName()).append('=').append(method.invoke(null, (Object[])null)).append("\n");
                }
                catch(IllegalArgumentException illegalargumentexception) { }
                catch(IllegalAccessException illegalaccessexception) { }
                catch(InvocationTargetException invocationtargetexception) { }
            j++;
        }
        return stringbuilder.toString();
    }
}

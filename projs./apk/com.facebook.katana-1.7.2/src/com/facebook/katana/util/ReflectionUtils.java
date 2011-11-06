// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReflectionUtils.java

package com.facebook.katana.util;

import java.lang.reflect.AccessibleObject;
import java.util.*;

public class ReflectionUtils
{
    public static interface Filter
    {

        public abstract Object mapper(AccessibleObject accessibleobject);
    }

    public static final class IncludeFlags extends Enum
    {

        public static IncludeFlags valueOf(String s)
        {
            return (IncludeFlags)Enum.valueOf(com/facebook/katana/util/ReflectionUtils$IncludeFlags, s);
        }

        public static IncludeFlags[] values()
        {
            return (IncludeFlags[])$VALUES.clone();
        }

        private static final IncludeFlags $VALUES[];
        public static final IncludeFlags INCLUDE_CONSTRUCTORS;
        public static final IncludeFlags INCLUDE_FIELDS;
        public static final IncludeFlags INCLUDE_METHODS;
        public static final IncludeFlags INCLUDE_SUPERCLASSES;

        static 
        {
            INCLUDE_SUPERCLASSES = new IncludeFlags("INCLUDE_SUPERCLASSES", 0);
            INCLUDE_CONSTRUCTORS = new IncludeFlags("INCLUDE_CONSTRUCTORS", 1);
            INCLUDE_METHODS = new IncludeFlags("INCLUDE_METHODS", 2);
            INCLUDE_FIELDS = new IncludeFlags("INCLUDE_FIELDS", 3);
            IncludeFlags aincludeflags[] = new IncludeFlags[4];
            aincludeflags[0] = INCLUDE_SUPERCLASSES;
            aincludeflags[1] = INCLUDE_CONSTRUCTORS;
            aincludeflags[2] = INCLUDE_METHODS;
            aincludeflags[3] = INCLUDE_FIELDS;
            $VALUES = aincludeflags;
        }

        private IncludeFlags(String s, int i)
        {
            super(s, i);
        }
    }


    public ReflectionUtils()
    {
    }

    public static Map getComponents(Class class1, Filter filter, EnumSet enumset)
    {
        HashMap hashmap = new HashMap();
        getComponents(class1, filter, enumset, ((Map) (hashmap)));
        return hashmap;
    }

    protected static void getComponents(Class class1, Filter filter, EnumSet enumset, Map map)
    {
        if(enumset.contains(IncludeFlags.INCLUDE_SUPERCLASSES))
        {
            Class class2 = class1.getSuperclass();
            if(class2 != null)
                getComponents(class2, filter, enumset, map);
        }
        if(enumset.contains(IncludeFlags.INCLUDE_CONSTRUCTORS))
        {
            java.lang.reflect.Constructor aconstructor[] = class1.getDeclaredConstructors();
            for(int k = 0; k < aconstructor.length; k++)
            {
                Object obj2 = filter.mapper(aconstructor[k]);
                if(obj2 != null)
                    map.put(aconstructor[k], obj2);
            }

        }
        if(enumset.contains(IncludeFlags.INCLUDE_METHODS))
        {
            java.lang.reflect.Method amethod[] = class1.getDeclaredMethods();
            for(int j = 0; j < amethod.length; j++)
            {
                Object obj1 = filter.mapper(amethod[j]);
                if(obj1 != null)
                    map.put(amethod[j], obj1);
            }

        }
        if(enumset.contains(IncludeFlags.INCLUDE_FIELDS))
        {
            java.lang.reflect.Field afield[] = class1.getDeclaredFields();
            for(int i = 0; i < afield.length; i++)
            {
                Object obj = filter.mapper(afield[i]);
                if(obj != null)
                    map.put(afield[i], obj);
            }

        }
    }
}

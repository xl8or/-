// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.util;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public final class ClassUtil
{

    private ClassUtil()
    {
    }

    private static void _addSuperTypes(Class class1, Class class2, ArrayList arraylist, boolean flag)
    {
        if(class1 != class2 && class1 != null && class1 != java/lang/Object) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(flag)
        {
            if(arraylist.contains(class1))
                continue; /* Loop/switch isn't completed */
            arraylist.add(class1);
        }
        Class aclass[] = class1.getInterfaces();
        int i = aclass.length;
        for(int j = 0; j < i; j++)
            _addSuperTypes(aclass[j], class2, arraylist, true);

        _addSuperTypes(class1.getSuperclass(), class2, arraylist, true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public static String canBeABeanType(Class class1)
    {
        String s;
        if(class1.isAnnotation())
            s = "annotation";
        else
        if(class1.isArray())
            s = "array";
        else
        if(class1.isEnum())
            s = "enum";
        else
        if(class1.isPrimitive())
            s = "primitive";
        else
            s = null;
        return s;
    }

    public static void checkAndFixAccess(Member member)
    {
        AccessibleObject accessibleobject = (AccessibleObject)member;
        accessibleobject.setAccessible(true);
_L1:
        return;
        SecurityException securityexception;
        securityexception;
        if(!accessibleobject.isAccessible())
        {
            Class class1 = member.getDeclaringClass();
            throw new IllegalArgumentException((new StringBuilder()).append("Can not access ").append(member).append(" (from class ").append(class1.getName()).append("; failed to set access: ").append(securityexception.getMessage()).toString());
        }
          goto _L1
    }

    public static Object createInstance(Class class1, boolean flag)
        throws IllegalArgumentException
    {
        Constructor constructor1 = class1.getDeclaredConstructor(new Class[0]);
        Constructor constructor = constructor1;
_L2:
        if(constructor == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Class ").append(class1.getName()).append(" has no default (no arg) constructor").toString());
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        unwrapAndThrowAsIAE(exception, (new StringBuilder()).append("Failed to find default constructor of class ").append(class1.getName()).append(", problem: ").append(exception.getMessage()).toString());
        constructor = null;
        if(true) goto _L2; else goto _L1
_L1:
        Object obj;
        Object obj1;
        if(flag)
            checkAndFixAccess(constructor);
        else
        if(!Modifier.isPublic(constructor.getModifiers()))
            throw new IllegalArgumentException((new StringBuilder()).append("Default constructor for ").append(class1.getName()).append(" is not accessible (non-public?): not allowed to try modify access via Reflection: can not instantiate type").toString());
        obj1 = constructor.newInstance(new Object[0]);
        obj = obj1;
_L4:
        return obj;
        Exception exception1;
        exception1;
        unwrapAndThrowAsIAE(exception1, (new StringBuilder()).append("Failed to instantiate class ").append(class1.getName()).append(", problem: ").append(exception1.getMessage()).toString());
        obj = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static List findSuperTypes(Class class1, Class class2)
    {
        ArrayList arraylist = new ArrayList();
        _addSuperTypes(class1, class2, arraylist, false);
        return arraylist;
    }

    public static Throwable getRootCause(Throwable throwable)
    {
        Throwable throwable1;
        for(throwable1 = throwable; throwable1.getCause() != null; throwable1 = throwable1.getCause());
        return throwable1;
    }

    public static boolean hasGetterSignature(Method method)
    {
        boolean flag;
        if(Modifier.isStatic(method.getModifiers()))
        {
            flag = false;
        } else
        {
            Class aclass[] = method.getParameterTypes();
            if(aclass != null && aclass.length != 0)
                flag = false;
            else
            if(Void.TYPE == method.getReturnType())
                flag = false;
            else
                flag = true;
        }
        return flag;
    }

    public static boolean isConcrete(Class class1)
    {
        boolean flag;
        if((0x600 & class1.getModifiers()) == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static String isLocalType(Class class1)
    {
        String s;
        if(class1.getEnclosingMethod() != null)
            s = "local/anonymous";
        else
        if(class1.getEnclosingClass() != null && !Modifier.isStatic(class1.getModifiers()))
            s = "non-static member class";
        else
            s = null;
        return s;
    }

    public static boolean isProxyType(Class class1)
    {
        boolean flag;
        if(Proxy.isProxyClass(class1))
        {
            flag = true;
        } else
        {
            String s = class1.getName();
            if(s.startsWith("net.sf.cglib.proxy.") || s.startsWith("org.hibernate.proxy."))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public static void throwAsIAE(Throwable throwable)
    {
        throwAsIAE(throwable, throwable.getMessage());
    }

    public static void throwAsIAE(Throwable throwable, String s)
    {
        if(throwable instanceof RuntimeException)
            throw (RuntimeException)throwable;
        if(throwable instanceof Error)
            throw (Error)throwable;
        else
            throw new IllegalArgumentException(s, throwable);
    }

    public static void unwrapAndThrowAsIAE(Throwable throwable)
    {
        throwAsIAE(getRootCause(throwable));
    }

    public static void unwrapAndThrowAsIAE(Throwable throwable, String s)
    {
        throwAsIAE(getRootCause(throwable), s);
    }
}

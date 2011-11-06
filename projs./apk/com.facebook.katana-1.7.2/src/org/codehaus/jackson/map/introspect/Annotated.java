// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;

public abstract class Annotated
{

    public Annotated()
    {
    }

    public abstract AnnotatedElement getAnnotated();

    public abstract Annotation getAnnotation(Class class1);

    protected abstract int getModifiers();

    public abstract String getName();

    public abstract Class getType();

    public final boolean hasAnnotation(Class class1)
    {
        boolean flag;
        if(getAnnotation(class1) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public final boolean isPublic()
    {
        return Modifier.isPublic(getModifiers());
    }
}

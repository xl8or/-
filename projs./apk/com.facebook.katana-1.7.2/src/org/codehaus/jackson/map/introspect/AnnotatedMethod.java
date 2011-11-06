// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import org.codehaus.jackson.map.util.ClassUtil;

// Referenced classes of package org.codehaus.jackson.map.introspect:
//            Annotated, AnnotationMap

public final class AnnotatedMethod extends Annotated
{

    public AnnotatedMethod(Method method, AnnotationMap annotationmap)
    {
        _method = method;
        _annotations = annotationmap;
    }

    public void addIfNotPresent(Annotation annotation)
    {
        _annotations.addIfNotPresent(annotation);
    }

    public void fixAccess()
    {
        ClassUtil.checkAndFixAccess(_method);
    }

    public volatile AnnotatedElement getAnnotated()
    {
        return getAnnotated();
    }

    public Method getAnnotated()
    {
        return _method;
    }

    public Annotation getAnnotation(Class class1)
    {
        return _annotations.get(class1);
    }

    public int getAnnotationCount()
    {
        return _annotations.size();
    }

    public Class getDeclaringClass()
    {
        return _method.getDeclaringClass();
    }

    public String getFullName()
    {
        return (new StringBuilder()).append(getDeclaringClass().getName()).append("#").append(getName()).append("(").append(getParameterCount()).append(" params)").toString();
    }

    public Type[] getGenericParameterTypes()
    {
        return _method.getGenericParameterTypes();
    }

    public Type getGenericReturnType()
    {
        return _method.getGenericReturnType();
    }

    public int getModifiers()
    {
        return _method.getModifiers();
    }

    public String getName()
    {
        return _method.getName();
    }

    public int getParameterCount()
    {
        return getParameterTypes().length;
    }

    public Class[] getParameterTypes()
    {
        if(_paramTypes == null)
            _paramTypes = _method.getParameterTypes();
        return _paramTypes;
    }

    public Class getReturnType()
    {
        return _method.getReturnType();
    }

    public Class getType()
    {
        return getReturnType();
    }

    public String toString()
    {
        return (new StringBuilder()).append("[method ").append(getName()).append(", annotations: ").append(_annotations).append("]").toString();
    }

    final AnnotationMap _annotations;
    final Method _method;
    public Class _paramTypes[];
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import org.codehaus.jackson.map.util.ClassUtil;

// Referenced classes of package org.codehaus.jackson.map.introspect:
//            Annotated, AnnotationMap

public final class AnnotatedField extends Annotated
{

    public AnnotatedField(Field field, AnnotationMap annotationmap)
    {
        _field = field;
        _annotations = annotationmap;
    }

    public void fixAccess()
    {
        ClassUtil.checkAndFixAccess(_field);
    }

    public volatile AnnotatedElement getAnnotated()
    {
        return getAnnotated();
    }

    public Field getAnnotated()
    {
        return _field;
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
        return _field.getDeclaringClass();
    }

    public String getFullName()
    {
        return (new StringBuilder()).append(getDeclaringClass().getName()).append("#").append(getName()).toString();
    }

    public Type getGenericType()
    {
        return _field.getGenericType();
    }

    public int getModifiers()
    {
        return _field.getModifiers();
    }

    public String getName()
    {
        return _field.getName();
    }

    public Class getType()
    {
        return _field.getType();
    }

    public String toString()
    {
        return (new StringBuilder()).append("[field ").append(getName()).append(", annotations: ").append(_annotations).append("]").toString();
    }

    final AnnotationMap _annotations;
    final Field _field;
}

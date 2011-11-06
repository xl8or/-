// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import org.codehaus.jackson.map.util.ClassUtil;

// Referenced classes of package org.codehaus.jackson.map.introspect:
//            Annotated, AnnotationMap

public final class AnnotatedConstructor extends Annotated
{

    public AnnotatedConstructor(Constructor constructor, AnnotationMap annotationmap)
    {
        _constructor = constructor;
        _annotations = annotationmap;
    }

    public void fixAccess()
    {
        ClassUtil.checkAndFixAccess(_constructor);
    }

    public volatile AnnotatedElement getAnnotated()
    {
        return getAnnotated();
    }

    public Constructor getAnnotated()
    {
        return _constructor;
    }

    public Annotation getAnnotation(Class class1)
    {
        return _annotations.get(class1);
    }

    public int getModifiers()
    {
        return _constructor.getModifiers();
    }

    public String getName()
    {
        return _constructor.getName();
    }

    public Class[] getParameterTypes()
    {
        return _constructor.getParameterTypes();
    }

    public Class getType()
    {
        return _constructor.getDeclaringClass();
    }

    final AnnotationMap _annotations;
    final Constructor _constructor;
}

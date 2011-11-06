// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.util.HashMap;

public final class AnnotationMap
{

    public AnnotationMap()
    {
    }

    protected final void _add(Annotation annotation)
    {
        if(_annotations == null)
            _annotations = new HashMap();
        _annotations.put(annotation.annotationType(), annotation);
    }

    public void add(Annotation annotation)
    {
        _add(annotation);
    }

    public void addIfNotPresent(Annotation annotation)
    {
        if(_annotations == null || !_annotations.containsKey(annotation.annotationType()))
            _add(annotation);
    }

    public Annotation get(Class class1)
    {
        Annotation annotation;
        if(_annotations == null)
            annotation = null;
        else
            annotation = (Annotation)_annotations.get(class1);
        return annotation;
    }

    public int size()
    {
        int i;
        if(_annotations == null)
            i = 0;
        else
            i = _annotations.size();
        return i;
    }

    public String toString()
    {
        String s;
        if(_annotations == null)
            s = "[null]";
        else
            s = _annotations.toString();
        return s;
    }

    HashMap _annotations;
}

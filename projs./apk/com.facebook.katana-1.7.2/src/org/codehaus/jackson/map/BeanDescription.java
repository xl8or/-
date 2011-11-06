// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map;

import java.util.Collection;
import java.util.LinkedHashMap;
import org.codehaus.jackson.type.JavaType;

public abstract class BeanDescription
{

    protected BeanDescription(JavaType javatype)
    {
        _type = javatype;
    }

    public abstract LinkedHashMap findGetters(boolean flag, Collection collection);

    public abstract LinkedHashMap findSetters(boolean flag);

    public Class getBeanClass()
    {
        return _type.getRawClass();
    }

    public JavaType getType()
    {
        return _type;
    }

    protected final JavaType _type;
}

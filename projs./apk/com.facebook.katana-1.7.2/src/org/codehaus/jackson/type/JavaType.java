// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.type;

import java.lang.reflect.Modifier;

public abstract class JavaType
{

    protected JavaType(Class class1)
    {
        _class = class1;
        _hashCode = class1.getName().hashCode();
    }

    protected void _assertSubclass(Class class1, Class class2)
    {
        if(!_class.isAssignableFrom(class1))
            throw new IllegalArgumentException((new StringBuilder()).append("Class ").append(class1.getName()).append(" is not assignable to ").append(_class.getName()).toString());
        else
            return;
    }

    protected abstract JavaType _narrow(Class class1);

    protected JavaType _widen(Class class1)
    {
        return _narrow(class1);
    }

    public abstract boolean equals(Object obj);

    public JavaType findVariableType(String s)
    {
        return null;
    }

    public final Class getRawClass()
    {
        return _class;
    }

    public final boolean hasRawClass(Class class1)
    {
        boolean flag;
        if(_class == class1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public final int hashCode()
    {
        return _hashCode;
    }

    public final boolean isAbstract()
    {
        return Modifier.isAbstract(_class.getModifiers());
    }

    public final boolean isArrayType()
    {
        return _class.isArray();
    }

    public abstract boolean isContainerType();

    public final boolean isEnumType()
    {
        return _class.isEnum();
    }

    public abstract boolean isFullyTyped();

    public final boolean isInterface()
    {
        return _class.isInterface();
    }

    public final boolean isPrimitive()
    {
        return _class.isPrimitive();
    }

    public final JavaType narrowBy(Class class1)
    {
        JavaType javatype;
        if(class1 == _class)
        {
            javatype = this;
        } else
        {
            _assertSubclass(class1, _class);
            javatype = _narrow(class1);
        }
        return javatype;
    }

    public abstract JavaType narrowContentsBy(Class class1);

    public abstract String toString();

    public final JavaType widenBy(Class class1)
    {
        JavaType javatype;
        if(class1 == _class)
        {
            javatype = this;
        } else
        {
            _assertSubclass(_class, class1);
            javatype = _widen(class1);
        }
        return javatype;
    }

    protected final Class _class;
    protected int _hashCode;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map;

import org.codehaus.jackson.type.JavaType;

// Referenced classes of package org.codehaus.jackson.map:
//            DeserializationConfig, BeanDescription, SerializationConfig

public abstract class ClassIntrospector
{

    protected ClassIntrospector()
    {
    }

    public abstract BeanDescription forClassAnnotations(DeserializationConfig deserializationconfig, Class class1);

    public abstract BeanDescription forClassAnnotations(SerializationConfig serializationconfig, Class class1);

    public abstract BeanDescription forCreation(DeserializationConfig deserializationconfig, Class class1);

    public abstract BeanDescription forDeserialization(DeserializationConfig deserializationconfig, JavaType javatype);

    public abstract BeanDescription forSerialization(SerializationConfig serializationconfig, Class class1);
}

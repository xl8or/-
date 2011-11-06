// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map;


// Referenced classes of package org.codehaus.jackson.map:
//            SerializationConfig, JsonSerializer

public abstract class SerializerFactory
{

    public SerializerFactory()
    {
    }

    public abstract JsonSerializer createSerializer(Class class1, SerializationConfig serializationconfig);
}

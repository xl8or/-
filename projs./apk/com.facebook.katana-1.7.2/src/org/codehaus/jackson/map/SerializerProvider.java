// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map;

import java.io.IOException;
import java.util.Date;
import org.codehaus.jackson.*;
import org.codehaus.jackson.schema.JsonSchema;

// Referenced classes of package org.codehaus.jackson.map:
//            JsonSerializer, JsonMappingException, SerializationConfig, SerializerFactory

public abstract class SerializerProvider
{

    protected SerializerProvider(SerializationConfig serializationconfig)
    {
        _config = serializationconfig;
    }

    public abstract void defaultSerializeDateValue(long l, JsonGenerator jsongenerator)
        throws IOException, JsonProcessingException;

    public abstract void defaultSerializeDateValue(Date date, JsonGenerator jsongenerator)
        throws IOException, JsonProcessingException;

    public final void defaultSerializeField(String s, Object obj, JsonGenerator jsongenerator)
        throws IOException, JsonProcessingException
    {
        jsongenerator.writeFieldName(s);
        if(obj == null)
            getNullValueSerializer().serialize(null, jsongenerator, this);
        else
            findValueSerializer(obj.getClass()).serialize(obj, jsongenerator, this);
    }

    public final void defaultSerializeValue(Object obj, JsonGenerator jsongenerator)
        throws IOException, JsonProcessingException
    {
        if(obj == null)
            getNullValueSerializer().serialize(null, jsongenerator, this);
        else
            findValueSerializer(obj.getClass()).serialize(obj, jsongenerator, this);
    }

    public abstract JsonSerializer findValueSerializer(Class class1)
        throws JsonMappingException;

    public JsonSchema generateJsonSchema(Class class1, SerializationConfig serializationconfig, SerializerFactory serializerfactory)
        throws JsonMappingException
    {
        throw new UnsupportedOperationException();
    }

    public final SerializationConfig getConfig()
    {
        return _config;
    }

    public abstract JsonSerializer getKeySerializer();

    public abstract JsonSerializer getNullKeySerializer();

    public abstract JsonSerializer getNullValueSerializer();

    public abstract JsonSerializer getUnknownTypeSerializer(Class class1);

    public abstract boolean hasSerializerFor(SerializationConfig serializationconfig, Class class1, SerializerFactory serializerfactory);

    public final boolean isEnabled(SerializationConfig.Feature feature)
    {
        return _config.isEnabled(feature);
    }

    public abstract void serializeValue(SerializationConfig serializationconfig, JsonGenerator jsongenerator, Object obj, SerializerFactory serializerfactory)
        throws IOException, JsonGenerationException;

    protected final SerializationConfig _config;
}

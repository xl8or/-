// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map;

import java.util.Calendar;
import java.util.Date;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.util.ArrayBuilders;
import org.codehaus.jackson.map.util.ObjectBuffer;

// Referenced classes of package org.codehaus.jackson.map:
//            DeserializationConfig, JsonMappingException

public abstract class DeserializationContext
{

    protected DeserializationContext(DeserializationConfig deserializationconfig)
    {
        _config = deserializationconfig;
    }

    public abstract Calendar constructCalendar(Date date);

    public abstract ArrayBuilders getArrayBuilders();

    public Base64Variant getBase64Variant()
    {
        return _config.getBase64Variant();
    }

    public DeserializationConfig getConfig()
    {
        return _config;
    }

    public abstract JsonParser getParser();

    public abstract JsonMappingException instantiationException(Class class1, Exception exception);

    public boolean isEnabled(DeserializationConfig.Feature feature)
    {
        return _config.isEnabled(feature);
    }

    public abstract ObjectBuffer leaseObjectBuffer();

    public abstract JsonMappingException mappingException(Class class1);

    public abstract Date parseDate(String s)
        throws IllegalArgumentException;

    public abstract void returnObjectBuffer(ObjectBuffer objectbuffer);

    public abstract JsonMappingException unknownFieldException(Object obj, String s);

    public abstract JsonMappingException weirdKeyException(Class class1, String s, String s1);

    public abstract JsonMappingException weirdNumberException(Class class1, String s);

    public abstract JsonMappingException weirdStringException(Class class1, String s);

    protected final DeserializationConfig _config;
}

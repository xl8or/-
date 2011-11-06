// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.math.BigDecimal;
import org.codehaus.jackson.*;

// Referenced classes of package org.codehaus.jackson.impl:
//            JsonWriteContext, DefaultPrettyPrinter

public abstract class JsonGeneratorBase extends JsonGenerator
{

    protected JsonGeneratorBase(int i, ObjectCodec objectcodec)
    {
        _features = i;
        _writeContext = JsonWriteContext.createRootContext();
        _objectCodec = objectcodec;
    }

    protected void _cantHappen()
    {
        throw new RuntimeException("Internal error: should never end up through this code path");
    }

    protected abstract void _releaseBuffers();

    protected void _reportError(String s)
        throws JsonGenerationException
    {
        throw new JsonGenerationException(s);
    }

    protected abstract void _verifyValueWrite(String s)
        throws IOException, JsonGenerationException;

    protected abstract void _writeEndArray()
        throws IOException, JsonGenerationException;

    protected abstract void _writeEndObject()
        throws IOException, JsonGenerationException;

    protected abstract void _writeFieldName(String s, boolean flag)
        throws IOException, JsonGenerationException;

    protected abstract void _writeStartArray()
        throws IOException, JsonGenerationException;

    protected abstract void _writeStartObject()
        throws IOException, JsonGenerationException;

    public void close()
        throws IOException
    {
        _closed = true;
    }

    public final void copyCurrentEvent(JsonParser jsonparser)
        throws IOException, JsonProcessingException
    {
        class _cls1
        {

            static final int $SwitchMap$org$codehaus$jackson$JsonToken[];

            static 
            {
                $SwitchMap$org$codehaus$jackson$JsonToken = new int[JsonToken.values().length];
                NoSuchFieldError nosuchfielderror10;
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.START_OBJECT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.END_OBJECT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.START_ARRAY.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.END_ARRAY.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.FIELD_NAME.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_STRING.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_NUMBER_INT.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_NUMBER_FLOAT.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_TRUE.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_FALSE.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_NULL.ordinal()] = 11;
_L2:
                return;
                nosuchfielderror10;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.org.codehaus.jackson.JsonToken[jsonparser.getCurrentToken().ordinal()];
        JVM INSTR tableswitch 1 11: default 68
    //                   1 73
    //                   2 80
    //                   3 87
    //                   4 94
    //                   5 101
    //                   6 112
    //                   7 131
    //                   8 142
    //                   9 153
    //                   10 161
    //                   11 169;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12
_L1:
        _cantHappen();
_L14:
        return;
_L2:
        writeStartObject();
        continue; /* Loop/switch isn't completed */
_L3:
        writeEndObject();
        continue; /* Loop/switch isn't completed */
_L4:
        writeStartArray();
        continue; /* Loop/switch isn't completed */
_L5:
        writeEndArray();
        continue; /* Loop/switch isn't completed */
_L6:
        writeFieldName(jsonparser.getCurrentName());
        continue; /* Loop/switch isn't completed */
_L7:
        writeString(jsonparser.getTextCharacters(), jsonparser.getTextOffset(), jsonparser.getTextLength());
        continue; /* Loop/switch isn't completed */
_L8:
        writeNumber(jsonparser.getIntValue());
        continue; /* Loop/switch isn't completed */
_L9:
        writeNumber(jsonparser.getDoubleValue());
        continue; /* Loop/switch isn't completed */
_L10:
        writeBoolean(true);
        continue; /* Loop/switch isn't completed */
_L11:
        writeBoolean(false);
        continue; /* Loop/switch isn't completed */
_L12:
        writeNull();
        if(true) goto _L14; else goto _L13
_L13:
    }

    public final void copyCurrentStructure(JsonParser jsonparser)
        throws IOException, JsonProcessingException
    {
        JsonToken jsontoken;
        jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.FIELD_NAME)
        {
            writeFieldName(jsonparser.getCurrentName());
            jsontoken = jsonparser.nextToken();
        }
        _cls1..SwitchMap.org.codehaus.jackson.JsonToken[jsontoken.ordinal()];
        JVM INSTR tableswitch 1 3: default 60
    //                   1 95
    //                   2 60
    //                   3 66;
           goto _L1 _L2 _L1 _L3
_L1:
        copyCurrentEvent(jsonparser);
_L5:
        return;
_L3:
        writeStartArray();
        for(; jsonparser.nextToken() != JsonToken.END_ARRAY; copyCurrentStructure(jsonparser));
        writeEndArray();
        continue; /* Loop/switch isn't completed */
_L2:
        writeStartObject();
        for(; jsonparser.nextToken() != JsonToken.END_OBJECT; copyCurrentStructure(jsonparser));
        writeEndObject();
        if(true) goto _L5; else goto _L4
_L4:
    }

    public void disableFeature(org.codehaus.jackson.JsonGenerator.Feature feature)
    {
        _features = _features & (-1 ^ feature.getMask());
    }

    public void enableFeature(org.codehaus.jackson.JsonGenerator.Feature feature)
    {
        _features = _features | feature.getMask();
    }

    public abstract void flush()
        throws IOException;

    public final ObjectCodec getCodec()
    {
        return _objectCodec;
    }

    public volatile JsonStreamContext getOutputContext()
    {
        return getOutputContext();
    }

    public final JsonWriteContext getOutputContext()
    {
        return _writeContext;
    }

    public boolean isClosed()
    {
        return _closed;
    }

    public final boolean isFeatureEnabled(org.codehaus.jackson.JsonGenerator.Feature feature)
    {
        boolean flag;
        if((_features & feature.getMask()) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public final void setCodec(ObjectCodec objectcodec)
    {
        _objectCodec = objectcodec;
    }

    public final void useDefaultPrettyPrinter()
    {
        setPrettyPrinter(new DefaultPrettyPrinter());
    }

    public abstract void writeBoolean(boolean flag)
        throws IOException, JsonGenerationException;

    public final void writeEndArray()
        throws IOException, JsonGenerationException
    {
        if(!_writeContext.inArray())
            _reportError((new StringBuilder()).append("Current context not an ARRAY but ").append(_writeContext.getTypeDesc()).toString());
        if(_cfgPrettyPrinter != null)
            _cfgPrettyPrinter.writeEndArray(this, _writeContext.getEntryCount());
        else
            _writeEndArray();
        _writeContext = _writeContext.getParent();
    }

    public final void writeEndObject()
        throws IOException, JsonGenerationException
    {
        if(!_writeContext.inObject())
            _reportError((new StringBuilder()).append("Current context not an object but ").append(_writeContext.getTypeDesc()).toString());
        _writeContext = _writeContext.getParent();
        if(_cfgPrettyPrinter != null)
            _cfgPrettyPrinter.writeEndObject(this, _writeContext.getEntryCount());
        else
            _writeEndObject();
    }

    public final void writeFieldName(String s)
        throws IOException, JsonGenerationException
    {
        int i = _writeContext.writeFieldName(s);
        if(i == 4)
            _reportError("Can not write a field name, expecting a value");
        boolean flag;
        if(i == 1)
            flag = true;
        else
            flag = false;
        _writeFieldName(s, flag);
    }

    public abstract void writeNull()
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(double d)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(float f)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(int i)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(long l)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(BigDecimal bigdecimal)
        throws IOException, JsonGenerationException;

    public abstract void writeObject(Object obj)
        throws IOException, JsonProcessingException;

    public final void writeStartArray()
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("start an array");
        _writeContext = _writeContext.createChildArrayContext();
        if(_cfgPrettyPrinter != null)
            _cfgPrettyPrinter.writeStartArray(this);
        else
            _writeStartArray();
    }

    public final void writeStartObject()
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("start an object");
        _writeContext = _writeContext.createChildObjectContext();
        if(_cfgPrettyPrinter != null)
            _cfgPrettyPrinter.writeStartObject(this);
        else
            _writeStartObject();
    }

    public abstract void writeTree(JsonNode jsonnode)
        throws IOException, JsonProcessingException;

    protected boolean _closed;
    protected int _features;
    protected ObjectCodec _objectCodec;
    protected JsonWriteContext _writeContext;
}

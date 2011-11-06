// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

// Referenced classes of package org.codehaus.jackson:
//            JsonProcessingException, JsonGenerationException, Base64Variants, PrettyPrinter, 
//            JsonParser, ObjectCodec, JsonStreamContext, Base64Variant, 
//            JsonNode

public abstract class JsonGenerator
{
    public static final class Feature extends Enum
    {

        public static int collectDefaults()
        {
            int i = 0;
            Feature afeature[] = values();
            int j = afeature.length;
            int k = i;
            for(; i < j; i++)
            {
                Feature feature = afeature[i];
                if(feature.enabledByDefault())
                    k |= feature.getMask();
            }

            return k;
        }

        public static Feature valueOf(String s)
        {
            return (Feature)Enum.valueOf(org/codehaus/jackson/JsonGenerator$Feature, s);
        }

        public static Feature[] values()
        {
            return (Feature[])$VALUES.clone();
        }

        public boolean enabledByDefault()
        {
            return _defaultState;
        }

        public int getMask()
        {
            return 1 << ordinal();
        }

        private static final Feature $VALUES[];
        public static final Feature AUTO_CLOSE_JSON_CONTENT;
        public static final Feature AUTO_CLOSE_TARGET;
        public static final Feature QUOTE_FIELD_NAMES;
        final boolean _defaultState;

        static 
        {
            AUTO_CLOSE_TARGET = new Feature("AUTO_CLOSE_TARGET", 0, true);
            AUTO_CLOSE_JSON_CONTENT = new Feature("AUTO_CLOSE_JSON_CONTENT", 1, true);
            QUOTE_FIELD_NAMES = new Feature("QUOTE_FIELD_NAMES", 2, true);
            Feature afeature[] = new Feature[3];
            afeature[0] = AUTO_CLOSE_TARGET;
            afeature[1] = AUTO_CLOSE_JSON_CONTENT;
            afeature[2] = QUOTE_FIELD_NAMES;
            $VALUES = afeature;
        }

        private Feature(String s, int i, boolean flag)
        {
            super(s, i);
            _defaultState = flag;
        }
    }


    protected JsonGenerator()
    {
    }

    public abstract void close()
        throws IOException;

    public abstract void copyCurrentEvent(JsonParser jsonparser)
        throws IOException, JsonProcessingException;

    public abstract void copyCurrentStructure(JsonParser jsonparser)
        throws IOException, JsonProcessingException;

    public abstract void disableFeature(Feature feature);

    public abstract void enableFeature(Feature feature);

    public abstract void flush()
        throws IOException;

    public abstract ObjectCodec getCodec();

    public abstract JsonStreamContext getOutputContext();

    public abstract boolean isClosed();

    public abstract boolean isFeatureEnabled(Feature feature);

    public abstract void setCodec(ObjectCodec objectcodec);

    public void setFeature(Feature feature, boolean flag)
    {
        if(flag)
            enableFeature(feature);
        else
            disableFeature(feature);
    }

    public final void setPrettyPrinter(PrettyPrinter prettyprinter)
    {
        _cfgPrettyPrinter = prettyprinter;
    }

    public abstract void useDefaultPrettyPrinter();

    public final void writeArrayFieldStart(String s)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeStartArray();
    }

    public abstract void writeBinary(Base64Variant base64variant, byte abyte0[], int i, int j)
        throws IOException, JsonGenerationException;

    public final void writeBinary(byte abyte0[])
        throws IOException, JsonGenerationException
    {
        writeBinary(Base64Variants.getDefaultVariant(), abyte0, 0, abyte0.length);
    }

    public final void writeBinary(byte abyte0[], int i, int j)
        throws IOException, JsonGenerationException
    {
        writeBinary(Base64Variants.getDefaultVariant(), abyte0, i, j);
    }

    public abstract void writeBoolean(boolean flag)
        throws IOException, JsonGenerationException;

    public final void writeBooleanField(String s, boolean flag)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeBoolean(flag);
    }

    public abstract void writeEndArray()
        throws IOException, JsonGenerationException;

    public abstract void writeEndObject()
        throws IOException, JsonGenerationException;

    public abstract void writeFieldName(String s)
        throws IOException, JsonGenerationException;

    public abstract void writeNull()
        throws IOException, JsonGenerationException;

    public final void writeNullField(String s)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeNull();
    }

    public abstract void writeNumber(double d)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(float f)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(int i)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(long l)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(String s)
        throws IOException, JsonGenerationException, UnsupportedOperationException;

    public abstract void writeNumber(BigDecimal bigdecimal)
        throws IOException, JsonGenerationException;

    public abstract void writeNumber(BigInteger biginteger)
        throws IOException, JsonGenerationException;

    public final void writeNumberField(String s, double d)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeNumber(d);
    }

    public final void writeNumberField(String s, float f)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeNumber(f);
    }

    public final void writeNumberField(String s, int i)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeNumber(i);
    }

    public final void writeNumberField(String s, long l)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeNumber(l);
    }

    public final void writeNumberField(String s, BigDecimal bigdecimal)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeNumber(bigdecimal);
    }

    public abstract void writeObject(Object obj)
        throws IOException, JsonProcessingException;

    public final void writeObjectField(String s, Object obj)
        throws IOException, JsonProcessingException
    {
        writeFieldName(s);
        writeObject(obj);
    }

    public final void writeObjectFieldStart(String s)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeStartObject();
    }

    public abstract void writeRaw(char c)
        throws IOException, JsonGenerationException;

    public abstract void writeRaw(String s)
        throws IOException, JsonGenerationException;

    public abstract void writeRaw(String s, int i, int j)
        throws IOException, JsonGenerationException;

    public abstract void writeRaw(char ac[], int i, int j)
        throws IOException, JsonGenerationException;

    public abstract void writeRawValue(String s)
        throws IOException, JsonGenerationException;

    public abstract void writeRawValue(String s, int i, int j)
        throws IOException, JsonGenerationException;

    public abstract void writeRawValue(char ac[], int i, int j)
        throws IOException, JsonGenerationException;

    public abstract void writeStartArray()
        throws IOException, JsonGenerationException;

    public abstract void writeStartObject()
        throws IOException, JsonGenerationException;

    public abstract void writeString(String s)
        throws IOException, JsonGenerationException;

    public abstract void writeString(char ac[], int i, int j)
        throws IOException, JsonGenerationException;

    public final void writeStringField(String s, String s1)
        throws IOException, JsonGenerationException
    {
        writeFieldName(s);
        writeString(s1);
    }

    public abstract void writeTree(JsonNode jsonnode)
        throws IOException, JsonProcessingException;

    protected PrettyPrinter _cfgPrettyPrinter;
}

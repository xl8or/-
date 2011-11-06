// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.type.TypeReference;

// Referenced classes of package org.codehaus.jackson:
//            JsonParseException, Base64Variants, JsonProcessingException, JsonToken, 
//            Base64Variant, JsonLocation, JsonStreamContext, JsonNode

public abstract class JsonParser
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
            return (Feature)Enum.valueOf(org/codehaus/jackson/JsonParser$Feature, s);
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
        public static final Feature ALLOW_COMMENTS;
        public static final Feature AUTO_CLOSE_SOURCE;
        final boolean _defaultState;

        static 
        {
            AUTO_CLOSE_SOURCE = new Feature("AUTO_CLOSE_SOURCE", 0, true);
            ALLOW_COMMENTS = new Feature("ALLOW_COMMENTS", 1, false);
            Feature afeature[] = new Feature[2];
            afeature[0] = AUTO_CLOSE_SOURCE;
            afeature[1] = ALLOW_COMMENTS;
            $VALUES = afeature;
        }

        private Feature(String s, int i, boolean flag)
        {
            super(s, i);
            _defaultState = flag;
        }
    }

    public static final class NumberType extends Enum
    {

        public static NumberType valueOf(String s)
        {
            return (NumberType)Enum.valueOf(org/codehaus/jackson/JsonParser$NumberType, s);
        }

        public static NumberType[] values()
        {
            return (NumberType[])$VALUES.clone();
        }

        private static final NumberType $VALUES[];
        public static final NumberType BIG_DECIMAL;
        public static final NumberType BIG_INTEGER;
        public static final NumberType DOUBLE;
        public static final NumberType FLOAT;
        public static final NumberType INT;
        public static final NumberType LONG;

        static 
        {
            INT = new NumberType("INT", 0);
            LONG = new NumberType("LONG", 1);
            BIG_INTEGER = new NumberType("BIG_INTEGER", 2);
            FLOAT = new NumberType("FLOAT", 3);
            DOUBLE = new NumberType("DOUBLE", 4);
            BIG_DECIMAL = new NumberType("BIG_DECIMAL", 5);
            NumberType anumbertype[] = new NumberType[6];
            anumbertype[0] = INT;
            anumbertype[1] = LONG;
            anumbertype[2] = BIG_INTEGER;
            anumbertype[3] = FLOAT;
            anumbertype[4] = DOUBLE;
            anumbertype[5] = BIG_DECIMAL;
            $VALUES = anumbertype;
        }

        private NumberType(String s, int i)
        {
            super(s, i);
        }
    }


    protected JsonParser()
    {
    }

    public final void clearCurrentToken()
    {
        if(_currToken != null)
        {
            _lastClearedToken = _currToken;
            _currToken = null;
        }
    }

    public abstract void close()
        throws IOException;

    public void disableFeature(Feature feature)
    {
        _features = _features & (-1 ^ feature.getMask());
    }

    public void enableFeature(Feature feature)
    {
        _features = _features | feature.getMask();
    }

    public abstract BigInteger getBigIntegerValue()
        throws IOException, JsonParseException;

    public byte[] getBinaryValue()
        throws IOException, JsonParseException
    {
        return getBinaryValue(Base64Variants.getDefaultVariant());
    }

    public abstract byte[] getBinaryValue(Base64Variant base64variant)
        throws IOException, JsonParseException;

    public abstract byte getByteValue()
        throws IOException, JsonParseException;

    public abstract JsonLocation getCurrentLocation();

    public abstract String getCurrentName()
        throws IOException, JsonParseException;

    public final JsonToken getCurrentToken()
    {
        return _currToken;
    }

    public abstract BigDecimal getDecimalValue()
        throws IOException, JsonParseException;

    public abstract double getDoubleValue()
        throws IOException, JsonParseException;

    public abstract float getFloatValue()
        throws IOException, JsonParseException;

    public abstract int getIntValue()
        throws IOException, JsonParseException;

    public JsonToken getLastClearedToken()
    {
        return _lastClearedToken;
    }

    public abstract long getLongValue()
        throws IOException, JsonParseException;

    public abstract NumberType getNumberType()
        throws IOException, JsonParseException;

    public abstract Number getNumberValue()
        throws IOException, JsonParseException;

    public abstract JsonStreamContext getParsingContext();

    public abstract short getShortValue()
        throws IOException, JsonParseException;

    public abstract String getText()
        throws IOException, JsonParseException;

    public abstract char[] getTextCharacters()
        throws IOException, JsonParseException;

    public abstract int getTextLength()
        throws IOException, JsonParseException;

    public abstract int getTextOffset()
        throws IOException, JsonParseException;

    public abstract JsonLocation getTokenLocation();

    public final boolean hasCurrentToken()
    {
        boolean flag;
        if(_currToken != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public abstract boolean isClosed();

    public final boolean isFeatureEnabled(Feature feature)
    {
        boolean flag;
        if((_features & feature.getMask()) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public abstract JsonToken nextToken()
        throws IOException, JsonParseException;

    public abstract JsonToken nextValue()
        throws IOException, JsonParseException;

    public abstract Object readValueAs(Class class1)
        throws IOException, JsonProcessingException;

    public abstract Object readValueAs(TypeReference typereference)
        throws IOException, JsonProcessingException;

    public abstract JsonNode readValueAsTree()
        throws IOException, JsonProcessingException;

    public void setFeature(Feature feature, boolean flag)
    {
        if(flag)
            enableFeature(feature);
        else
            disableFeature(feature);
    }

    public abstract void skipChildren()
        throws IOException, JsonParseException;

    protected JsonToken _currToken;
    protected int _features;
    protected JsonToken _lastClearedToken;
}

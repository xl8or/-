// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;


public final class JsonToken extends Enum
{

    private JsonToken(String s, int i, String s1)
    {
        super(s, i);
        if(s1 == null)
        {
            _serialized = null;
            _serializedChars = null;
            _serializedBytes = null;
        } else
        {
            _serialized = s1;
            _serializedChars = s1.toCharArray();
            int j = _serializedChars.length;
            _serializedBytes = new byte[j];
            int k = 0;
            while(k < j) 
            {
                _serializedBytes[k] = (byte)_serializedChars[k];
                k++;
            }
        }
    }

    public static JsonToken valueOf(String s)
    {
        return (JsonToken)Enum.valueOf(org/codehaus/jackson/JsonToken, s);
    }

    public static JsonToken[] values()
    {
        return (JsonToken[])$VALUES.clone();
    }

    public byte[] asByteArray()
    {
        return _serializedBytes;
    }

    public char[] asCharArray()
    {
        return _serializedChars;
    }

    public String asString()
    {
        return _serialized;
    }

    public boolean isNumeric()
    {
        boolean flag;
        if(this == VALUE_NUMBER_INT || this == VALUE_NUMBER_FLOAT)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isScalarValue()
    {
        boolean flag;
        if(ordinal() >= VALUE_STRING.ordinal())
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static final JsonToken $VALUES[];
    public static final JsonToken END_ARRAY;
    public static final JsonToken END_OBJECT;
    public static final JsonToken FIELD_NAME;
    public static final JsonToken NOT_AVAILABLE;
    public static final JsonToken START_ARRAY;
    public static final JsonToken START_OBJECT;
    public static final JsonToken VALUE_EMBEDDED_OBJECT;
    public static final JsonToken VALUE_FALSE;
    public static final JsonToken VALUE_NULL;
    public static final JsonToken VALUE_NUMBER_FLOAT;
    public static final JsonToken VALUE_NUMBER_INT;
    public static final JsonToken VALUE_STRING;
    public static final JsonToken VALUE_TRUE;
    final String _serialized;
    final byte _serializedBytes[];
    final char _serializedChars[];

    static 
    {
        NOT_AVAILABLE = new JsonToken("NOT_AVAILABLE", 0, null);
        START_OBJECT = new JsonToken("START_OBJECT", 1, "{");
        END_OBJECT = new JsonToken("END_OBJECT", 2, "}");
        START_ARRAY = new JsonToken("START_ARRAY", 3, "[");
        END_ARRAY = new JsonToken("END_ARRAY", 4, "]");
        FIELD_NAME = new JsonToken("FIELD_NAME", 5, null);
        VALUE_EMBEDDED_OBJECT = new JsonToken("VALUE_EMBEDDED_OBJECT", 6, null);
        VALUE_STRING = new JsonToken("VALUE_STRING", 7, null);
        VALUE_NUMBER_INT = new JsonToken("VALUE_NUMBER_INT", 8, null);
        VALUE_NUMBER_FLOAT = new JsonToken("VALUE_NUMBER_FLOAT", 9, null);
        VALUE_TRUE = new JsonToken("VALUE_TRUE", 10, "true");
        VALUE_FALSE = new JsonToken("VALUE_FALSE", 11, "false");
        VALUE_NULL = new JsonToken("VALUE_NULL", 12, "null");
        JsonToken ajsontoken[] = new JsonToken[13];
        ajsontoken[0] = NOT_AVAILABLE;
        ajsontoken[1] = START_OBJECT;
        ajsontoken[2] = END_OBJECT;
        ajsontoken[3] = START_ARRAY;
        ajsontoken[4] = END_ARRAY;
        ajsontoken[5] = FIELD_NAME;
        ajsontoken[6] = VALUE_EMBEDDED_OBJECT;
        ajsontoken[7] = VALUE_STRING;
        ajsontoken[8] = VALUE_NUMBER_INT;
        ajsontoken[9] = VALUE_NUMBER_FLOAT;
        ajsontoken[10] = VALUE_TRUE;
        ajsontoken[11] = VALUE_FALSE;
        ajsontoken[12] = VALUE_NULL;
        $VALUES = ajsontoken;
    }
}

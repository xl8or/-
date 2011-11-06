// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;

import java.io.IOException;

// Referenced classes of package org.codehaus.jackson:
//            JsonGenerationException, JsonGenerator

public interface PrettyPrinter
{

    public abstract void beforeArrayValues(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;

    public abstract void beforeObjectEntries(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;

    public abstract void writeArrayValueSeparator(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;

    public abstract void writeEndArray(JsonGenerator jsongenerator, int i)
        throws IOException, JsonGenerationException;

    public abstract void writeEndObject(JsonGenerator jsongenerator, int i)
        throws IOException, JsonGenerationException;

    public abstract void writeObjectEntrySeparator(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;

    public abstract void writeObjectFieldValueSeparator(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;

    public abstract void writeRootValueSeparator(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;

    public abstract void writeStartArray(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;

    public abstract void writeStartObject(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.util.Arrays;
import org.codehaus.jackson.*;

// Referenced classes of package org.codehaus.jackson.impl:
//            Indenter

public class DefaultPrettyPrinter
    implements PrettyPrinter
{
    public static class Lf2SpacesIndenter
        implements Indenter
    {

        public boolean isInline()
        {
            return false;
        }

        public void writeIndentation(JsonGenerator jsongenerator, int i)
            throws IOException, JsonGenerationException
        {
            jsongenerator.writeRaw(SYSTEM_LINE_SEPARATOR);
            int j;
            for(j = i + i; j > 64; j -= SPACES.length)
                jsongenerator.writeRaw(SPACES, 0, 64);

            jsongenerator.writeRaw(SPACES, 0, j);
        }

        static final char SPACES[];
        static final int SPACE_COUNT = 64;
        static final String SYSTEM_LINE_SEPARATOR;

        static 
        {
            String s = null;
            String s1 = System.getProperty("line.separator");
            s = s1;
_L2:
            if(s == null)
                s = "\n";
            SYSTEM_LINE_SEPARATOR = s;
            SPACES = new char[64];
            Arrays.fill(SPACES, ' ');
            return;
            Throwable throwable;
            throwable;
            if(true) goto _L2; else goto _L1
_L1:
        }

        public Lf2SpacesIndenter()
        {
        }
    }

    public static class FixedSpaceIndenter
        implements Indenter
    {

        public boolean isInline()
        {
            return true;
        }

        public void writeIndentation(JsonGenerator jsongenerator, int i)
            throws IOException, JsonGenerationException
        {
            jsongenerator.writeRaw(' ');
        }

        public FixedSpaceIndenter()
        {
        }
    }

    public static class NopIndenter
        implements Indenter
    {

        public boolean isInline()
        {
            return true;
        }

        public void writeIndentation(JsonGenerator jsongenerator, int i)
        {
        }

        public NopIndenter()
        {
        }
    }


    public DefaultPrettyPrinter()
    {
        mArrayIndenter = new FixedSpaceIndenter();
        mObjectIndenter = new Lf2SpacesIndenter();
        mSpacesInObjectEntries = true;
        mNesting = 0;
    }

    public void beforeArrayValues(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException
    {
        mArrayIndenter.writeIndentation(jsongenerator, mNesting);
    }

    public void beforeObjectEntries(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException
    {
        mObjectIndenter.writeIndentation(jsongenerator, mNesting);
    }

    public void indentArraysWith(Indenter indenter)
    {
        Object obj;
        if(indenter == null)
            obj = new NopIndenter();
        else
            obj = indenter;
        mArrayIndenter = ((Indenter) (obj));
    }

    public void indentObjectsWith(Indenter indenter)
    {
        Object obj;
        if(indenter == null)
            obj = new NopIndenter();
        else
            obj = indenter;
        mObjectIndenter = ((Indenter) (obj));
    }

    public void spacesInObjectEntries(boolean flag)
    {
        mSpacesInObjectEntries = flag;
    }

    public void writeArrayValueSeparator(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException
    {
        jsongenerator.writeRaw(',');
        mArrayIndenter.writeIndentation(jsongenerator, mNesting);
    }

    public void writeEndArray(JsonGenerator jsongenerator, int i)
        throws IOException, JsonGenerationException
    {
        if(!mArrayIndenter.isInline())
            mNesting = mNesting - 1;
        if(i > 0)
            mArrayIndenter.writeIndentation(jsongenerator, mNesting);
        else
            jsongenerator.writeRaw(' ');
        jsongenerator.writeRaw(']');
    }

    public void writeEndObject(JsonGenerator jsongenerator, int i)
        throws IOException, JsonGenerationException
    {
        if(!mObjectIndenter.isInline())
            mNesting = mNesting - 1;
        if(i > 0)
            mObjectIndenter.writeIndentation(jsongenerator, mNesting);
        else
            jsongenerator.writeRaw(' ');
        jsongenerator.writeRaw('}');
    }

    public void writeObjectEntrySeparator(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException
    {
        jsongenerator.writeRaw(',');
        mObjectIndenter.writeIndentation(jsongenerator, mNesting);
    }

    public void writeObjectFieldValueSeparator(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException
    {
        if(mSpacesInObjectEntries)
            jsongenerator.writeRaw(" : ");
        else
            jsongenerator.writeRaw(':');
    }

    public void writeRootValueSeparator(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException
    {
        jsongenerator.writeRaw(' ');
    }

    public void writeStartArray(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException
    {
        if(!mArrayIndenter.isInline())
            mNesting = 1 + mNesting;
        jsongenerator.writeRaw('[');
    }

    public void writeStartObject(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException
    {
        jsongenerator.writeRaw('{');
        if(!mObjectIndenter.isInline())
            mNesting = 1 + mNesting;
    }

    protected Indenter mArrayIndenter;
    protected int mNesting;
    protected Indenter mObjectIndenter;
    protected boolean mSpacesInObjectEntries;
}

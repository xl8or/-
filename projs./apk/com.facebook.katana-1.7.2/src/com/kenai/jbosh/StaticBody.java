// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StaticBody.java

package com.kenai.jbosh;

import java.io.*;
import java.util.Collections;
import java.util.Map;

// Referenced classes of package com.kenai.jbosh:
//            AbstractBody, BodyParserXmlPull, BOSHException, BodyParser, 
//            BodyParserResults

final class StaticBody extends AbstractBody
{

    private StaticBody(Map map, String s)
    {
        attrs = map;
        raw = s;
    }

    public static StaticBody fromStream(InputStream inputstream)
        throws BOSHException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte abyte0[] = new byte[1024];
        int i;
        do
        {
            i = inputstream.read(abyte0);
            if(i > 0)
                bytearrayoutputstream.write(abyte0, 0, i);
        } while(i >= 0);
        return fromString(bytearrayoutputstream.toString());
        IOException ioexception;
        ioexception;
        throw new BOSHException("Could not read body data", ioexception);
    }

    public static StaticBody fromString(String s)
        throws BOSHException
    {
        return new StaticBody(PARSER.parse(s).getAttributes(), s);
    }

    public Map getAttributes()
    {
        return Collections.unmodifiableMap(attrs);
    }

    public String toXML()
    {
        return raw;
    }

    private static final int BUFFER_SIZE = 1024;
    private static final BodyParser PARSER = new BodyParserXmlPull();
    private final Map attrs;
    private final String raw;

}

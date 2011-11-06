// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FBJsonFactory.java

package com.facebook.katana.service.method;

import java.io.*;
import org.codehaus.jackson.*;
import org.codehaus.jackson.impl.ReaderBasedParser;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;

public class FBJsonFactory extends JsonFactory
{
    class FBJsonParser extends ReaderBasedParser
    {

        public JsonToken nextToken()
            throws IOException, JsonParseException
        {
            JsonToken jsontoken = super.nextToken();
            if(jsontoken == null)
                throw new IOException("Unexpected end of json input");
            else
                return jsontoken;
        }

        final FBJsonFactory this$0;

        public FBJsonParser(IOContext iocontext, int i, Reader reader, ObjectCodec objectcodec, CharsToNameCanonicalizer charstonamecanonicalizer)
        {
            this$0 = FBJsonFactory.this;
            super(iocontext, i, reader, objectcodec, charstonamecanonicalizer);
        }
    }


    public FBJsonFactory()
    {
    }

    public JsonParser createJsonParser(String s)
        throws IOException, JsonParseException
    {
        StringReader stringreader = new StringReader(s);
        return new FBJsonParser(_createContext(stringreader, true), getParserFeatures(), stringreader, _objectCodec, _rootCharSymbols.makeChild());
    }
}

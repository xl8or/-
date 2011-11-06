// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;

import java.io.*;
import java.lang.ref.SoftReference;
import java.net.URL;
import org.codehaus.jackson.impl.ByteSourceBootstrapper;
import org.codehaus.jackson.impl.ReaderBasedParser;
import org.codehaus.jackson.impl.WriterBasedGenerator;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.io.UTF8Writer;
import org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;
import org.codehaus.jackson.util.BufferRecycler;

// Referenced classes of package org.codehaus.jackson:
//            JsonParseException, JsonEncoding, ObjectCodec, JsonParser, 
//            JsonGenerator

public class JsonFactory
{

    public JsonFactory()
    {
        this(null);
    }

    public JsonFactory(ObjectCodec objectcodec)
    {
        _rootCharSymbols = CharsToNameCanonicalizer.createRoot();
        _rootByteSymbols = BytesToNameCanonicalizer.createRoot();
        _parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
        _generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
        _objectCodec = objectcodec;
    }

    private JsonParser _createJsonParser(InputStream inputstream, IOContext iocontext)
        throws IOException, JsonParseException
    {
        return (new ByteSourceBootstrapper(iocontext, inputstream)).constructParser(_parserFeatures, _objectCodec, _rootByteSymbols, _rootCharSymbols);
    }

    protected static final InputStream _optimizedStreamFromURL(URL url)
        throws IOException
    {
        Object obj;
        if("file".equals(url.getProtocol()) && url.getHost() == null)
            obj = new FileInputStream(url.getPath());
        else
            obj = url.openStream();
        return ((InputStream) (obj));
    }

    protected final IOContext _createContext(Object obj, boolean flag)
    {
        return new IOContext(_getBufferRecycler(), obj, flag);
    }

    protected final BufferRecycler _getBufferRecycler()
    {
        SoftReference softreference = (SoftReference)_recyclerRef.get();
        BufferRecycler bufferrecycler;
        if(softreference == null)
            bufferrecycler = null;
        else
            bufferrecycler = (BufferRecycler)softreference.get();
        if(bufferrecycler == null)
        {
            bufferrecycler = new BufferRecycler();
            if(softreference == null)
                _recyclerRef.set(new SoftReference(bufferrecycler));
        }
        return bufferrecycler;
    }

    public final JsonGenerator createJsonGenerator(File file, JsonEncoding jsonencoding)
        throws IOException
    {
        return createJsonGenerator(((OutputStream) (new FileOutputStream(file))), jsonencoding);
    }

    public final JsonGenerator createJsonGenerator(OutputStream outputstream, JsonEncoding jsonencoding)
        throws IOException
    {
        IOContext iocontext = _createContext(outputstream, false);
        iocontext.setEncoding(jsonencoding);
        WriterBasedGenerator writerbasedgenerator;
        if(jsonencoding == JsonEncoding.UTF8)
            writerbasedgenerator = new WriterBasedGenerator(iocontext, _generatorFeatures, _objectCodec, new UTF8Writer(iocontext, outputstream));
        else
            writerbasedgenerator = new WriterBasedGenerator(iocontext, _generatorFeatures, _objectCodec, new OutputStreamWriter(outputstream, jsonencoding.getJavaName()));
        return writerbasedgenerator;
    }

    public final JsonGenerator createJsonGenerator(Writer writer)
        throws IOException
    {
        return new WriterBasedGenerator(_createContext(writer, false), _generatorFeatures, _objectCodec, writer);
    }

    public JsonParser createJsonParser(File file)
        throws IOException, JsonParseException
    {
        return _createJsonParser(new FileInputStream(file), _createContext(file, true));
    }

    public JsonParser createJsonParser(InputStream inputstream)
        throws IOException, JsonParseException
    {
        return _createJsonParser(inputstream, _createContext(inputstream, false));
    }

    public JsonParser createJsonParser(Reader reader)
        throws IOException, JsonParseException
    {
        return new ReaderBasedParser(_createContext(reader, false), _parserFeatures, reader, _objectCodec, _rootCharSymbols.makeChild());
    }

    public JsonParser createJsonParser(String s)
        throws IOException, JsonParseException
    {
        StringReader stringreader = new StringReader(s);
        return new ReaderBasedParser(_createContext(stringreader, true), _parserFeatures, stringreader, _objectCodec, _rootCharSymbols.makeChild());
    }

    public JsonParser createJsonParser(URL url)
        throws IOException, JsonParseException
    {
        return _createJsonParser(_optimizedStreamFromURL(url), _createContext(url, true));
    }

    public JsonParser createJsonParser(byte abyte0[])
        throws IOException, JsonParseException
    {
        return createJsonParser(abyte0, 0, abyte0.length);
    }

    public JsonParser createJsonParser(byte abyte0[], int i, int j)
        throws IOException, JsonParseException
    {
        return (new ByteSourceBootstrapper(_createContext(abyte0, true), abyte0, i, j)).constructParser(_parserFeatures, _objectCodec, _rootByteSymbols, _rootCharSymbols);
    }

    public final void disableGeneratorFeature(JsonGenerator.Feature feature)
    {
        _generatorFeatures = _generatorFeatures & (-1 ^ feature.getMask());
    }

    public final void disableParserFeature(JsonParser.Feature feature)
    {
        _parserFeatures = _parserFeatures & (-1 ^ feature.getMask());
    }

    public final void enableGeneratorFeature(JsonGenerator.Feature feature)
    {
        _generatorFeatures = _generatorFeatures | feature.getMask();
    }

    public final void enableParserFeature(JsonParser.Feature feature)
    {
        _parserFeatures = _parserFeatures | feature.getMask();
    }

    public ObjectCodec getCodec()
    {
        return _objectCodec;
    }

    protected final int getParserFeatures()
    {
        return _parserFeatures;
    }

    public final boolean isGeneratorFeatureEnabled(JsonGenerator.Feature feature)
    {
        boolean flag;
        if((_generatorFeatures & feature.getMask()) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public final boolean isParserFeatureEnabled(JsonParser.Feature feature)
    {
        boolean flag;
        if((_parserFeatures & feature.getMask()) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void setCodec(ObjectCodec objectcodec)
    {
        _objectCodec = objectcodec;
    }

    public final void setGeneratorFeature(JsonGenerator.Feature feature, boolean flag)
    {
        if(flag)
            enableGeneratorFeature(feature);
        else
            disableGeneratorFeature(feature);
    }

    public final void setParserFeature(JsonParser.Feature feature, boolean flag)
    {
        if(flag)
            enableParserFeature(feature);
        else
            disableParserFeature(feature);
    }

    static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
    static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
    static final ThreadLocal _recyclerRef = new ThreadLocal();
    private int _generatorFeatures;
    protected ObjectCodec _objectCodec;
    private int _parserFeatures;
    protected BytesToNameCanonicalizer _rootByteSymbols;
    protected CharsToNameCanonicalizer _rootCharSymbols;

}

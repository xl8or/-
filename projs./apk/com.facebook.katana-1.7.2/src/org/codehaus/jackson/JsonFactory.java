package org.codehaus.jackson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.net.URL;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.impl.ByteSourceBootstrapper;
import org.codehaus.jackson.impl.ReaderBasedParser;
import org.codehaus.jackson.impl.WriterBasedGenerator;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.io.UTF8Writer;
import org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;
import org.codehaus.jackson.util.BufferRecycler;

public class JsonFactory {

   static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
   static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
   static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef = new ThreadLocal();
   private int _generatorFeatures;
   protected ObjectCodec _objectCodec;
   private int _parserFeatures;
   protected BytesToNameCanonicalizer _rootByteSymbols;
   protected CharsToNameCanonicalizer _rootCharSymbols;


   public JsonFactory() {
      this((ObjectCodec)null);
   }

   public JsonFactory(ObjectCodec var1) {
      CharsToNameCanonicalizer var2 = CharsToNameCanonicalizer.createRoot();
      this._rootCharSymbols = var2;
      BytesToNameCanonicalizer var3 = BytesToNameCanonicalizer.createRoot();
      this._rootByteSymbols = var3;
      int var4 = DEFAULT_PARSER_FEATURE_FLAGS;
      this._parserFeatures = var4;
      int var5 = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._generatorFeatures = var5;
      this._objectCodec = var1;
   }

   private JsonParser _createJsonParser(InputStream var1, IOContext var2) throws IOException, JsonParseException {
      ByteSourceBootstrapper var3 = new ByteSourceBootstrapper(var2, var1);
      int var4 = this._parserFeatures;
      ObjectCodec var5 = this._objectCodec;
      BytesToNameCanonicalizer var6 = this._rootByteSymbols;
      CharsToNameCanonicalizer var7 = this._rootCharSymbols;
      return var3.constructParser(var4, var5, var6, var7);
   }

   protected static final InputStream _optimizedStreamFromURL(URL var0) throws IOException {
      String var1 = var0.getProtocol();
      Object var3;
      if("file".equals(var1) && var0.getHost() == null) {
         String var2 = var0.getPath();
         var3 = new FileInputStream(var2);
      } else {
         var3 = var0.openStream();
      }

      return (InputStream)var3;
   }

   protected final IOContext _createContext(Object var1, boolean var2) {
      BufferRecycler var3 = this._getBufferRecycler();
      return new IOContext(var3, var1, var2);
   }

   protected final BufferRecycler _getBufferRecycler() {
      SoftReference var1 = (SoftReference)_recyclerRef.get();
      BufferRecycler var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = (BufferRecycler)var1.get();
      }

      if(var2 == null) {
         BufferRecycler var3 = new BufferRecycler();
         if(var1 == null) {
            ThreadLocal var4 = _recyclerRef;
            SoftReference var5 = new SoftReference(var3);
            var4.set(var5);
         }
      }

      return var2;
   }

   public final JsonGenerator createJsonGenerator(File var1, JsonEncoding var2) throws IOException {
      FileOutputStream var3 = new FileOutputStream(var1);
      return this.createJsonGenerator((OutputStream)var3, var2);
   }

   public final JsonGenerator createJsonGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      IOContext var3 = this._createContext(var1, (boolean)0);
      var3.setEncoding(var2);
      JsonEncoding var4 = JsonEncoding.UTF8;
      WriterBasedGenerator var12;
      if(var2 == var4) {
         int var5 = this._generatorFeatures;
         ObjectCodec var6 = this._objectCodec;
         UTF8Writer var7 = new UTF8Writer(var3, var1);
         var12 = new WriterBasedGenerator(var3, var5, var6, var7);
      } else {
         int var8 = this._generatorFeatures;
         ObjectCodec var9 = this._objectCodec;
         String var10 = var2.getJavaName();
         OutputStreamWriter var11 = new OutputStreamWriter(var1, var10);
         var12 = new WriterBasedGenerator(var3, var8, var9, var11);
      }

      return var12;
   }

   public final JsonGenerator createJsonGenerator(Writer var1) throws IOException {
      IOContext var2 = this._createContext(var1, (boolean)0);
      int var3 = this._generatorFeatures;
      ObjectCodec var4 = this._objectCodec;
      return new WriterBasedGenerator(var2, var3, var4, var1);
   }

   public JsonParser createJsonParser(File var1) throws IOException, JsonParseException {
      FileInputStream var2 = new FileInputStream(var1);
      IOContext var3 = this._createContext(var1, (boolean)1);
      return this._createJsonParser(var2, var3);
   }

   public JsonParser createJsonParser(InputStream var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, (boolean)0);
      return this._createJsonParser(var1, var2);
   }

   public JsonParser createJsonParser(Reader var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, (boolean)0);
      int var3 = this._parserFeatures;
      ObjectCodec var4 = this._objectCodec;
      CharsToNameCanonicalizer var5 = this._rootCharSymbols.makeChild();
      return new ReaderBasedParser(var2, var3, var1, var4, var5);
   }

   public JsonParser createJsonParser(String var1) throws IOException, JsonParseException {
      StringReader var2 = new StringReader(var1);
      IOContext var3 = this._createContext(var2, (boolean)1);
      int var4 = this._parserFeatures;
      ObjectCodec var5 = this._objectCodec;
      CharsToNameCanonicalizer var6 = this._rootCharSymbols.makeChild();
      return new ReaderBasedParser(var3, var4, var2, var5, var6);
   }

   public JsonParser createJsonParser(URL var1) throws IOException, JsonParseException {
      InputStream var2 = _optimizedStreamFromURL(var1);
      IOContext var3 = this._createContext(var1, (boolean)1);
      return this._createJsonParser(var2, var3);
   }

   public JsonParser createJsonParser(byte[] var1) throws IOException, JsonParseException {
      int var2 = var1.length;
      return this.createJsonParser(var1, 0, var2);
   }

   public JsonParser createJsonParser(byte[] var1, int var2, int var3) throws IOException, JsonParseException {
      IOContext var4 = this._createContext(var1, (boolean)1);
      ByteSourceBootstrapper var5 = new ByteSourceBootstrapper(var4, var1, var2, var3);
      int var6 = this._parserFeatures;
      ObjectCodec var7 = this._objectCodec;
      BytesToNameCanonicalizer var8 = this._rootByteSymbols;
      CharsToNameCanonicalizer var9 = this._rootCharSymbols;
      return var5.constructParser(var6, var7, var8, var9);
   }

   public final void disableGeneratorFeature(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      int var3 = ~var1.getMask();
      int var4 = var2 & var3;
      this._generatorFeatures = var4;
   }

   public final void disableParserFeature(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      int var3 = ~var1.getMask();
      int var4 = var2 & var3;
      this._parserFeatures = var4;
   }

   public final void enableGeneratorFeature(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      int var3 = var1.getMask();
      int var4 = var2 | var3;
      this._generatorFeatures = var4;
   }

   public final void enableParserFeature(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      int var3 = var1.getMask();
      int var4 = var2 | var3;
      this._parserFeatures = var4;
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   protected final int getParserFeatures() {
      return this._parserFeatures;
   }

   public final boolean isGeneratorFeatureEnabled(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      int var3 = var1.getMask();
      boolean var4;
      if((var2 & var3) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public final boolean isParserFeatureEnabled(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      int var3 = var1.getMask();
      boolean var4;
      if((var2 & var3) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public void setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
   }

   public final void setGeneratorFeature(JsonGenerator.Feature var1, boolean var2) {
      if(var2) {
         this.enableGeneratorFeature(var1);
      } else {
         this.disableGeneratorFeature(var1);
      }
   }

   public final void setParserFeature(JsonParser.Feature var1, boolean var2) {
      if(var2) {
         this.enableParserFeature(var1);
      } else {
         this.disableParserFeature(var1);
      }
   }
}

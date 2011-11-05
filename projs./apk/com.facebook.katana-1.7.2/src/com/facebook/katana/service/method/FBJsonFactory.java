package com.facebook.katana.service.method;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.impl.ReaderBasedParser;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;

public class FBJsonFactory extends JsonFactory {

   public FBJsonFactory() {}

   public JsonParser createJsonParser(String var1) throws IOException, JsonParseException {
      StringReader var2 = new StringReader(var1);
      IOContext var3 = this._createContext(var2, (boolean)1);
      int var4 = this.getParserFeatures();
      ObjectCodec var5 = this._objectCodec;
      CharsToNameCanonicalizer var6 = this._rootCharSymbols.makeChild();
      return new FBJsonFactory.FBJsonParser(var3, var4, var2, var5, var6);
   }

   class FBJsonParser extends ReaderBasedParser {

      public FBJsonParser(IOContext var2, int var3, Reader var4, ObjectCodec var5, CharsToNameCanonicalizer var6) {
         super(var2, var3, var4, var5, var6);
      }

      public JsonToken nextToken() throws IOException, JsonParseException {
         JsonToken var1 = super.nextToken();
         if(var1 == null) {
            throw new IOException("Unexpected end of json input");
         } else {
            return var1;
         }
      }
   }
}

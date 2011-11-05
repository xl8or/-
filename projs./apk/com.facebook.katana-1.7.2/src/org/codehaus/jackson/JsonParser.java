package org.codehaus.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.Base64Variants;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.type.TypeReference;

public abstract class JsonParser {

   protected JsonToken _currToken;
   protected int _features;
   protected JsonToken _lastClearedToken;


   protected JsonParser() {}

   public final void clearCurrentToken() {
      if(this._currToken != null) {
         JsonToken var1 = this._currToken;
         this._lastClearedToken = var1;
         this._currToken = null;
      }
   }

   public abstract void close() throws IOException;

   public void disableFeature(JsonParser.Feature var1) {
      int var2 = this._features;
      int var3 = ~var1.getMask();
      int var4 = var2 & var3;
      this._features = var4;
   }

   public void enableFeature(JsonParser.Feature var1) {
      int var2 = this._features;
      int var3 = var1.getMask();
      int var4 = var2 | var3;
      this._features = var4;
   }

   public abstract BigInteger getBigIntegerValue() throws IOException, JsonParseException;

   public byte[] getBinaryValue() throws IOException, JsonParseException {
      Base64Variant var1 = Base64Variants.getDefaultVariant();
      return this.getBinaryValue(var1);
   }

   public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException;

   public abstract byte getByteValue() throws IOException, JsonParseException;

   public abstract JsonLocation getCurrentLocation();

   public abstract String getCurrentName() throws IOException, JsonParseException;

   public final JsonToken getCurrentToken() {
      return this._currToken;
   }

   public abstract BigDecimal getDecimalValue() throws IOException, JsonParseException;

   public abstract double getDoubleValue() throws IOException, JsonParseException;

   public abstract float getFloatValue() throws IOException, JsonParseException;

   public abstract int getIntValue() throws IOException, JsonParseException;

   public JsonToken getLastClearedToken() {
      return this._lastClearedToken;
   }

   public abstract long getLongValue() throws IOException, JsonParseException;

   public abstract JsonParser.NumberType getNumberType() throws IOException, JsonParseException;

   public abstract Number getNumberValue() throws IOException, JsonParseException;

   public abstract JsonStreamContext getParsingContext();

   public abstract short getShortValue() throws IOException, JsonParseException;

   public abstract String getText() throws IOException, JsonParseException;

   public abstract char[] getTextCharacters() throws IOException, JsonParseException;

   public abstract int getTextLength() throws IOException, JsonParseException;

   public abstract int getTextOffset() throws IOException, JsonParseException;

   public abstract JsonLocation getTokenLocation();

   public final boolean hasCurrentToken() {
      boolean var1;
      if(this._currToken != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract boolean isClosed();

   public final boolean isFeatureEnabled(JsonParser.Feature var1) {
      int var2 = this._features;
      int var3 = var1.getMask();
      boolean var4;
      if((var2 & var3) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public abstract JsonToken nextToken() throws IOException, JsonParseException;

   public abstract JsonToken nextValue() throws IOException, JsonParseException;

   public abstract <T extends Object> T readValueAs(Class<T> var1) throws IOException, JsonProcessingException;

   public abstract <T extends Object> T readValueAs(TypeReference<?> var1) throws IOException, JsonProcessingException;

   public abstract JsonNode readValueAsTree() throws IOException, JsonProcessingException;

   public void setFeature(JsonParser.Feature var1, boolean var2) {
      if(var2) {
         this.enableFeature(var1);
      } else {
         this.disableFeature(var1);
      }
   }

   public abstract void skipChildren() throws IOException, JsonParseException;

   public static enum Feature {

      // $FF: synthetic field
      private static final JsonParser.Feature[] $VALUES;
      ALLOW_COMMENTS("ALLOW_COMMENTS", 1, (boolean)0),
      AUTO_CLOSE_SOURCE("AUTO_CLOSE_SOURCE", 0, (boolean)1);
      final boolean _defaultState;


      static {
         JsonParser.Feature[] var0 = new JsonParser.Feature[2];
         JsonParser.Feature var1 = AUTO_CLOSE_SOURCE;
         var0[0] = var1;
         JsonParser.Feature var2 = ALLOW_COMMENTS;
         var0[1] = var2;
         $VALUES = var0;
      }

      private Feature(String var1, int var2, boolean var3) {
         this._defaultState = var3;
      }

      public static int collectDefaults() {
         int var0 = 0;
         JsonParser.Feature[] var1 = values();
         int var2 = var1.length;

         int var3;
         for(var3 = var0; var0 < var2; ++var0) {
            JsonParser.Feature var4 = var1[var0];
            if(var4.enabledByDefault()) {
               int var5 = var4.getMask();
               var3 |= var5;
            }
         }

         return var3;
      }

      public boolean enabledByDefault() {
         return this._defaultState;
      }

      public int getMask() {
         int var1 = this.ordinal();
         return 1 << var1;
      }
   }

   public static enum NumberType {

      // $FF: synthetic field
      private static final JsonParser.NumberType[] $VALUES;
      BIG_DECIMAL("BIG_DECIMAL", 5),
      BIG_INTEGER("BIG_INTEGER", 2),
      DOUBLE("DOUBLE", 4),
      FLOAT("FLOAT", 3),
      INT("INT", 0),
      LONG("LONG", 1);


      static {
         JsonParser.NumberType[] var0 = new JsonParser.NumberType[6];
         JsonParser.NumberType var1 = INT;
         var0[0] = var1;
         JsonParser.NumberType var2 = LONG;
         var0[1] = var2;
         JsonParser.NumberType var3 = BIG_INTEGER;
         var0[2] = var3;
         JsonParser.NumberType var4 = FLOAT;
         var0[3] = var4;
         JsonParser.NumberType var5 = DOUBLE;
         var0[4] = var5;
         JsonParser.NumberType var6 = BIG_DECIMAL;
         var0[5] = var6;
         $VALUES = var0;
      }

      private NumberType(String var1, int var2) {}
   }
}

package org.codehaus.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.Base64Variants;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.PrettyPrinter;

public abstract class JsonGenerator {

   protected PrettyPrinter _cfgPrettyPrinter;


   protected JsonGenerator() {}

   public abstract void close() throws IOException;

   public abstract void copyCurrentEvent(JsonParser var1) throws IOException, JsonProcessingException;

   public abstract void copyCurrentStructure(JsonParser var1) throws IOException, JsonProcessingException;

   public abstract void disableFeature(JsonGenerator.Feature var1);

   public abstract void enableFeature(JsonGenerator.Feature var1);

   public abstract void flush() throws IOException;

   public abstract ObjectCodec getCodec();

   public abstract JsonStreamContext getOutputContext();

   public abstract boolean isClosed();

   public abstract boolean isFeatureEnabled(JsonGenerator.Feature var1);

   public abstract void setCodec(ObjectCodec var1);

   public void setFeature(JsonGenerator.Feature var1, boolean var2) {
      if(var2) {
         this.enableFeature(var1);
      } else {
         this.disableFeature(var1);
      }
   }

   public final void setPrettyPrinter(PrettyPrinter var1) {
      this._cfgPrettyPrinter = var1;
   }

   public abstract void useDefaultPrettyPrinter();

   public final void writeArrayFieldStart(String var1) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeStartArray();
   }

   public abstract void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException;

   public final void writeBinary(byte[] var1) throws IOException, JsonGenerationException {
      Base64Variant var2 = Base64Variants.getDefaultVariant();
      int var3 = var1.length;
      this.writeBinary(var2, var1, 0, var3);
   }

   public final void writeBinary(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      Base64Variant var4 = Base64Variants.getDefaultVariant();
      this.writeBinary(var4, var1, var2, var3);
   }

   public abstract void writeBoolean(boolean var1) throws IOException, JsonGenerationException;

   public final void writeBooleanField(String var1, boolean var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeBoolean(var2);
   }

   public abstract void writeEndArray() throws IOException, JsonGenerationException;

   public abstract void writeEndObject() throws IOException, JsonGenerationException;

   public abstract void writeFieldName(String var1) throws IOException, JsonGenerationException;

   public abstract void writeNull() throws IOException, JsonGenerationException;

   public final void writeNullField(String var1) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNull();
   }

   public abstract void writeNumber(double var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(float var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(int var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(long var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(String var1) throws IOException, JsonGenerationException, UnsupportedOperationException;

   public abstract void writeNumber(BigDecimal var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(BigInteger var1) throws IOException, JsonGenerationException;

   public final void writeNumberField(String var1, double var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, float var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, int var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, long var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, BigDecimal var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public abstract void writeObject(Object var1) throws IOException, JsonProcessingException;

   public final void writeObjectField(String var1, Object var2) throws IOException, JsonProcessingException {
      this.writeFieldName(var1);
      this.writeObject(var2);
   }

   public final void writeObjectFieldStart(String var1) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeStartObject();
   }

   public abstract void writeRaw(char var1) throws IOException, JsonGenerationException;

   public abstract void writeRaw(String var1) throws IOException, JsonGenerationException;

   public abstract void writeRaw(String var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeRaw(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeRawValue(String var1) throws IOException, JsonGenerationException;

   public abstract void writeRawValue(String var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeRawValue(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

   public abstract void writeStartArray() throws IOException, JsonGenerationException;

   public abstract void writeStartObject() throws IOException, JsonGenerationException;

   public abstract void writeString(String var1) throws IOException, JsonGenerationException;

   public abstract void writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

   public final void writeStringField(String var1, String var2) throws IOException, JsonGenerationException {
      this.writeFieldName(var1);
      this.writeString(var2);
   }

   public abstract void writeTree(JsonNode var1) throws IOException, JsonProcessingException;

   public static enum Feature {

      // $FF: synthetic field
      private static final JsonGenerator.Feature[] $VALUES;
      AUTO_CLOSE_JSON_CONTENT("AUTO_CLOSE_JSON_CONTENT", 1, (boolean)1),
      AUTO_CLOSE_TARGET("AUTO_CLOSE_TARGET", 0, (boolean)1),
      QUOTE_FIELD_NAMES("QUOTE_FIELD_NAMES", 2, (boolean)1);
      final boolean _defaultState;


      static {
         JsonGenerator.Feature[] var0 = new JsonGenerator.Feature[3];
         JsonGenerator.Feature var1 = AUTO_CLOSE_TARGET;
         var0[0] = var1;
         JsonGenerator.Feature var2 = AUTO_CLOSE_JSON_CONTENT;
         var0[1] = var2;
         JsonGenerator.Feature var3 = QUOTE_FIELD_NAMES;
         var0[2] = var3;
         $VALUES = var0;
      }

      private Feature(String var1, int var2, boolean var3) {
         this._defaultState = var3;
      }

      public static int collectDefaults() {
         int var0 = 0;
         JsonGenerator.Feature[] var1 = values();
         int var2 = var1.length;

         int var3;
         for(var3 = var0; var0 < var2; ++var0) {
            JsonGenerator.Feature var4 = var1[var0];
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
}

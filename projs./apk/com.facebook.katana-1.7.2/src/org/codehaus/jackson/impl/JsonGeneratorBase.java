package org.codehaus.jackson.impl;

import java.io.IOException;
import java.math.BigDecimal;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.PrettyPrinter;
import org.codehaus.jackson.impl.DefaultPrettyPrinter;
import org.codehaus.jackson.impl.JsonWriteContext;

public abstract class JsonGeneratorBase extends JsonGenerator {

   protected boolean _closed;
   protected int _features;
   protected ObjectCodec _objectCodec;
   protected JsonWriteContext _writeContext;


   protected JsonGeneratorBase(int var1, ObjectCodec var2) {
      this._features = var1;
      JsonWriteContext var3 = JsonWriteContext.createRootContext();
      this._writeContext = var3;
      this._objectCodec = var2;
   }

   protected void _cantHappen() {
      throw new RuntimeException("Internal error: should never end up through this code path");
   }

   protected abstract void _releaseBuffers();

   protected void _reportError(String var1) throws JsonGenerationException {
      throw new JsonGenerationException(var1);
   }

   protected abstract void _verifyValueWrite(String var1) throws IOException, JsonGenerationException;

   protected abstract void _writeEndArray() throws IOException, JsonGenerationException;

   protected abstract void _writeEndObject() throws IOException, JsonGenerationException;

   protected abstract void _writeFieldName(String var1, boolean var2) throws IOException, JsonGenerationException;

   protected abstract void _writeStartArray() throws IOException, JsonGenerationException;

   protected abstract void _writeStartObject() throws IOException, JsonGenerationException;

   public void close() throws IOException {
      this._closed = (boolean)1;
   }

   public final void copyCurrentEvent(JsonParser var1) throws IOException, JsonProcessingException {
      int[] var2 = JsonGeneratorBase.1.$SwitchMap$org$codehaus$jackson$JsonToken;
      int var3 = var1.getCurrentToken().ordinal();
      switch(var2[var3]) {
      case 1:
         this.writeStartObject();
         return;
      case 2:
         this.writeEndObject();
         return;
      case 3:
         this.writeStartArray();
         return;
      case 4:
         this.writeEndArray();
         return;
      case 5:
         String var4 = var1.getCurrentName();
         this.writeFieldName(var4);
         return;
      case 6:
         char[] var5 = var1.getTextCharacters();
         int var6 = var1.getTextOffset();
         int var7 = var1.getTextLength();
         this.writeString(var5, var6, var7);
         return;
      case 7:
         int var8 = var1.getIntValue();
         this.writeNumber(var8);
         return;
      case 8:
         double var9 = var1.getDoubleValue();
         this.writeNumber(var9);
         return;
      case 9:
         this.writeBoolean((boolean)1);
         return;
      case 10:
         this.writeBoolean((boolean)0);
         return;
      case 11:
         this.writeNull();
         return;
      default:
         this._cantHappen();
      }
   }

   public final void copyCurrentStructure(JsonParser var1) throws IOException, JsonProcessingException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.FIELD_NAME;
      if(var2 == var3) {
         String var4 = var1.getCurrentName();
         this.writeFieldName(var4);
         var2 = var1.nextToken();
      }

      int[] var5 = JsonGeneratorBase.1.$SwitchMap$org$codehaus$jackson$JsonToken;
      int var6 = var2.ordinal();
      switch(var5[var6]) {
      case 1:
         this.writeStartObject();

         while(true) {
            JsonToken var9 = var1.nextToken();
            JsonToken var10 = JsonToken.END_OBJECT;
            if(var9 == var10) {
               this.writeEndObject();
               return;
            }

            this.copyCurrentStructure(var1);
         }
      case 2:
      default:
         this.copyCurrentEvent(var1);
         return;
      case 3:
         this.writeStartArray();

         while(true) {
            JsonToken var7 = var1.nextToken();
            JsonToken var8 = JsonToken.END_ARRAY;
            if(var7 == var8) {
               this.writeEndArray();
               return;
            }

            this.copyCurrentStructure(var1);
         }
      }
   }

   public void disableFeature(JsonGenerator.Feature var1) {
      int var2 = this._features;
      int var3 = ~var1.getMask();
      int var4 = var2 & var3;
      this._features = var4;
   }

   public void enableFeature(JsonGenerator.Feature var1) {
      int var2 = this._features;
      int var3 = var1.getMask();
      int var4 = var2 | var3;
      this._features = var4;
   }

   public abstract void flush() throws IOException;

   public final ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public final JsonWriteContext getOutputContext() {
      return this._writeContext;
   }

   public boolean isClosed() {
      return this._closed;
   }

   public final boolean isFeatureEnabled(JsonGenerator.Feature var1) {
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

   public final void setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
   }

   public final void useDefaultPrettyPrinter() {
      DefaultPrettyPrinter var1 = new DefaultPrettyPrinter();
      this.setPrettyPrinter(var1);
   }

   public abstract void writeBoolean(boolean var1) throws IOException, JsonGenerationException;

   public final void writeEndArray() throws IOException, JsonGenerationException {
      if(!this._writeContext.inArray()) {
         StringBuilder var1 = (new StringBuilder()).append("Current context not an ARRAY but ");
         String var2 = this._writeContext.getTypeDesc();
         String var3 = var1.append(var2).toString();
         this._reportError(var3);
      }

      if(this._cfgPrettyPrinter != null) {
         PrettyPrinter var4 = this._cfgPrettyPrinter;
         int var5 = this._writeContext.getEntryCount();
         var4.writeEndArray(this, var5);
      } else {
         this._writeEndArray();
      }

      JsonWriteContext var6 = this._writeContext.getParent();
      this._writeContext = var6;
   }

   public final void writeEndObject() throws IOException, JsonGenerationException {
      if(!this._writeContext.inObject()) {
         StringBuilder var1 = (new StringBuilder()).append("Current context not an object but ");
         String var2 = this._writeContext.getTypeDesc();
         String var3 = var1.append(var2).toString();
         this._reportError(var3);
      }

      JsonWriteContext var4 = this._writeContext.getParent();
      this._writeContext = var4;
      if(this._cfgPrettyPrinter != null) {
         PrettyPrinter var5 = this._cfgPrettyPrinter;
         int var6 = this._writeContext.getEntryCount();
         var5.writeEndObject(this, var6);
      } else {
         this._writeEndObject();
      }
   }

   public final void writeFieldName(String var1) throws IOException, JsonGenerationException {
      int var2 = this._writeContext.writeFieldName(var1);
      if(var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      byte var3;
      if(var2 == 1) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      this._writeFieldName(var1, (boolean)var3);
   }

   public abstract void writeNull() throws IOException, JsonGenerationException;

   public abstract void writeNumber(double var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(float var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(int var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(long var1) throws IOException, JsonGenerationException;

   public abstract void writeNumber(BigDecimal var1) throws IOException, JsonGenerationException;

   public abstract void writeObject(Object var1) throws IOException, JsonProcessingException;

   public final void writeStartArray() throws IOException, JsonGenerationException {
      this._verifyValueWrite("start an array");
      JsonWriteContext var1 = this._writeContext.createChildArrayContext();
      this._writeContext = var1;
      if(this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartArray(this);
      } else {
         this._writeStartArray();
      }
   }

   public final void writeStartObject() throws IOException, JsonGenerationException {
      this._verifyValueWrite("start an object");
      JsonWriteContext var1 = this._writeContext.createChildObjectContext();
      this._writeContext = var1;
      if(this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         this._writeStartObject();
      }
   }

   public abstract void writeTree(JsonNode var1) throws IOException, JsonProcessingException;

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$org$codehaus$jackson$JsonToken = new int[JsonToken.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var1 = JsonToken.START_OBJECT.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var43) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var3 = JsonToken.END_OBJECT.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var42) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var5 = JsonToken.START_ARRAY.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var41) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var7 = JsonToken.END_ARRAY.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var40) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var9 = JsonToken.FIELD_NAME.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var39) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var11 = JsonToken.VALUE_STRING.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var38) {
            ;
         }

         try {
            int[] var12 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var13 = JsonToken.VALUE_NUMBER_INT.ordinal();
            var12[var13] = 7;
         } catch (NoSuchFieldError var37) {
            ;
         }

         try {
            int[] var14 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var15 = JsonToken.VALUE_NUMBER_FLOAT.ordinal();
            var14[var15] = 8;
         } catch (NoSuchFieldError var36) {
            ;
         }

         try {
            int[] var16 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var17 = JsonToken.VALUE_TRUE.ordinal();
            var16[var17] = 9;
         } catch (NoSuchFieldError var35) {
            ;
         }

         try {
            int[] var18 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var19 = JsonToken.VALUE_FALSE.ordinal();
            var18[var19] = 10;
         } catch (NoSuchFieldError var34) {
            ;
         }

         try {
            int[] var20 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var21 = JsonToken.VALUE_NULL.ordinal();
            var20[var21] = 11;
         } catch (NoSuchFieldError var33) {
            ;
         }
      }
   }
}

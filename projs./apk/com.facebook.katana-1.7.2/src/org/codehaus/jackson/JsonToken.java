package org.codehaus.jackson;


public enum JsonToken {

   // $FF: synthetic field
   private static final JsonToken[] $VALUES;
   END_ARRAY("END_ARRAY", 4, "]"),
   END_OBJECT("END_OBJECT", 2, "}"),
   FIELD_NAME("FIELD_NAME", 5, (String)null),
   NOT_AVAILABLE("NOT_AVAILABLE", 0, (String)null),
   START_ARRAY("START_ARRAY", 3, "["),
   START_OBJECT("START_OBJECT", 1, "{"),
   VALUE_EMBEDDED_OBJECT("VALUE_EMBEDDED_OBJECT", 6, (String)null),
   VALUE_FALSE("VALUE_FALSE", 11, "false"),
   VALUE_NULL("VALUE_NULL", 12, "null"),
   VALUE_NUMBER_FLOAT("VALUE_NUMBER_FLOAT", 9, (String)null),
   VALUE_NUMBER_INT("VALUE_NUMBER_INT", 8, (String)null),
   VALUE_STRING("VALUE_STRING", 7, (String)null),
   VALUE_TRUE("VALUE_TRUE", 10, "true");
   final String _serialized;
   final byte[] _serializedBytes;
   final char[] _serializedChars;


   static {
      JsonToken[] var0 = new JsonToken[13];
      JsonToken var1 = NOT_AVAILABLE;
      var0[0] = var1;
      JsonToken var2 = START_OBJECT;
      var0[1] = var2;
      JsonToken var3 = END_OBJECT;
      var0[2] = var3;
      JsonToken var4 = START_ARRAY;
      var0[3] = var4;
      JsonToken var5 = END_ARRAY;
      var0[4] = var5;
      JsonToken var6 = FIELD_NAME;
      var0[5] = var6;
      JsonToken var7 = VALUE_EMBEDDED_OBJECT;
      var0[6] = var7;
      JsonToken var8 = VALUE_STRING;
      var0[7] = var8;
      JsonToken var9 = VALUE_NUMBER_INT;
      var0[8] = var9;
      JsonToken var10 = VALUE_NUMBER_FLOAT;
      var0[9] = var10;
      JsonToken var11 = VALUE_TRUE;
      var0[10] = var11;
      JsonToken var12 = VALUE_FALSE;
      var0[11] = var12;
      JsonToken var13 = VALUE_NULL;
      var0[12] = var13;
      $VALUES = var0;
   }

   private JsonToken(String var1, int var2, String var3) {
      if(var3 == null) {
         this._serialized = null;
         this._serializedChars = null;
         this._serializedBytes = null;
      } else {
         this._serialized = var3;
         char[] var4 = var3.toCharArray();
         this._serializedChars = var4;
         int var5 = this._serializedChars.length;
         byte[] var6 = new byte[var5];
         this._serializedBytes = var6;

         for(int var7 = 0; var7 < var5; ++var7) {
            byte[] var8 = this._serializedBytes;
            byte var9 = (byte)this._serializedChars[var7];
            var8[var7] = var9;
         }

      }
   }

   public byte[] asByteArray() {
      return this._serializedBytes;
   }

   public char[] asCharArray() {
      return this._serializedChars;
   }

   public String asString() {
      return this._serialized;
   }

   public boolean isNumeric() {
      JsonToken var1 = VALUE_NUMBER_INT;
      boolean var3;
      if(this != var1) {
         JsonToken var2 = VALUE_NUMBER_FLOAT;
         if(this != var2) {
            var3 = false;
            return var3;
         }
      }

      var3 = true;
      return var3;
   }

   public boolean isScalarValue() {
      int var1 = this.ordinal();
      int var2 = VALUE_STRING.ordinal();
      boolean var3;
      if(var1 >= var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}

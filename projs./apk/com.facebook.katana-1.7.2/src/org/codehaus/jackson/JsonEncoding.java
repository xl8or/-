package org.codehaus.jackson;


public enum JsonEncoding {

   // $FF: synthetic field
   private static final JsonEncoding[] $VALUES;
   UTF16_BE("UTF16_BE", 1, "UTF-16BE", (boolean)1),
   UTF16_LE("UTF16_LE", 2, "UTF-16LE", (boolean)0),
   UTF32_BE("UTF32_BE", 3, "UTF-32BE", (boolean)1),
   UTF32_LE("UTF32_LE", 4, "UTF-32LE", (boolean)0),
   UTF8("UTF8", 0, "UTF-8", (boolean)0);
   final boolean mBigEndian;
   final String mJavaName;


   static {
      JsonEncoding[] var0 = new JsonEncoding[5];
      JsonEncoding var1 = UTF8;
      var0[0] = var1;
      JsonEncoding var2 = UTF16_BE;
      var0[1] = var2;
      JsonEncoding var3 = UTF16_LE;
      var0[2] = var3;
      JsonEncoding var4 = UTF32_BE;
      var0[3] = var4;
      JsonEncoding var5 = UTF32_LE;
      var0[4] = var5;
      $VALUES = var0;
   }

   private JsonEncoding(String var1, int var2, String var3, boolean var4) {
      this.mJavaName = var3;
      this.mBigEndian = var4;
   }

   public String getJavaName() {
      return this.mJavaName;
   }

   public boolean isBigEndian() {
      return this.mBigEndian;
   }
}

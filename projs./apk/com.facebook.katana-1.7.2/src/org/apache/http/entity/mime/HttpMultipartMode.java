package org.apache.http.entity.mime;


public enum HttpMultipartMode {

   // $FF: synthetic field
   private static final HttpMultipartMode[] $VALUES;
   BROWSER_COMPATIBLE("BROWSER_COMPATIBLE", 1),
   STRICT("STRICT", 0);


   static {
      HttpMultipartMode[] var0 = new HttpMultipartMode[2];
      HttpMultipartMode var1 = STRICT;
      var0[0] = var1;
      HttpMultipartMode var2 = BROWSER_COMPATIBLE;
      var0[1] = var2;
      $VALUES = var0;
   }

   private HttpMultipartMode(String var1, int var2) {}
}

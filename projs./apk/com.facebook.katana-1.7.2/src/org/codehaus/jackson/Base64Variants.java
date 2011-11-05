package org.codehaus.jackson;

import org.codehaus.jackson.Base64Variant;

public final class Base64Variants {

   public static final Base64Variant MIME = new Base64Variant("MIME", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", (boolean)1, '=', 76);
   public static final Base64Variant MIME_NO_LINEFEEDS;
   public static final Base64Variant MODIFIED_FOR_URL;
   public static final Base64Variant PEM;
   static final String STD_BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";


   static {
      Base64Variant var0 = MIME;
      MIME_NO_LINEFEEDS = new Base64Variant(var0, "MIME-NO-LINEFEEDS", Integer.MAX_VALUE);
      Base64Variant var1 = MIME;
      PEM = new Base64Variant(var1, "PEM", (boolean)1, '=', 64);
      StringBuffer var2 = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
      int var3 = var2.indexOf("+");
      var2.setCharAt(var3, '-');
      int var4 = var2.indexOf("/");
      var2.setCharAt(var4, '_');
      String var5 = var2.toString();
      byte var6 = 0;
      char var7 = 0;
      int var8 = Integer.MAX_VALUE;
      MODIFIED_FOR_URL = new Base64Variant("MODIFIED-FOR-URL", var5, (boolean)var6, var7, var8);
   }

   public Base64Variants() {}

   public static Base64Variant getDefaultVariant() {
      return MIME_NO_LINEFEEDS;
   }
}

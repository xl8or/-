package com.beetstra.jutf7;

import com.beetstra.jutf7.Base64Util;
import com.beetstra.jutf7.UTF7StyleCharsetDecoder;
import com.beetstra.jutf7.UTF7StyleCharsetEncoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.List;

abstract class UTF7StyleCharset extends Charset {

   private static final List CONTAINED;
   Base64Util base64;
   final boolean strict;


   static {
      String[] var0 = new String[]{"US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16", "UTF-16LE", "UTF-16BE"};
      CONTAINED = Arrays.asList(var0);
   }

   protected UTF7StyleCharset(String var1, String[] var2, String var3, boolean var4) {
      super(var1, var2);
      Base64Util var5 = new Base64Util(var3);
      this.base64 = var5;
      this.strict = var4;
   }

   abstract boolean canEncodeDirectly(char var1);

   public boolean contains(Charset var1) {
      List var2 = CONTAINED;
      String var3 = var1.name();
      return var2.contains(var3);
   }

   public CharsetDecoder newDecoder() {
      Base64Util var1 = this.base64;
      boolean var2 = this.strict;
      return new UTF7StyleCharsetDecoder(this, var1, var2);
   }

   public CharsetEncoder newEncoder() {
      Base64Util var1 = this.base64;
      boolean var2 = this.strict;
      return new UTF7StyleCharsetEncoder(this, var1, var2);
   }

   abstract byte shift();

   abstract byte unshift();
}

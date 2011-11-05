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

   private final List CONTAINED;
   Base64Util base64;
   final boolean strict;


   protected UTF7StyleCharset(String var1, String[] var2, String var3, boolean var4) {
      super(var1, var2);
      String[] var5 = new String[]{"US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16", "UTF-16LE", "UTF-16BE"};
      List var6 = Arrays.asList(var5);
      this.CONTAINED = var6;
      Base64Util var7 = new Base64Util(var3);
      this.base64 = var7;
      this.strict = var4;
   }

   abstract boolean canEncodeDirectly(char var1);

   public boolean contains(Charset var1) {
      List var2 = this.CONTAINED;
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

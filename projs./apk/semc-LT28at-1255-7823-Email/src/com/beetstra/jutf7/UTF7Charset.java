package com.beetstra.jutf7;

import com.beetstra.jutf7.UTF7StyleCharset;

class UTF7Charset extends UTF7StyleCharset {

   private static final String BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
   private static final String RULE_3 = " \t\r\n";
   private static final String SET_D = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789\'(),-./:?";
   private static final String SET_O = "!\"#$%&*;<=>@[]^_`{|}";
   final String directlyEncoded;


   UTF7Charset(String var1, String[] var2, boolean var3) {
      super(var1, var2, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", (boolean)0);
      if(var3) {
         this.directlyEncoded = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789\'(),-./:?!\"#$%&*;<=>@[]^_`{|} \t\r\n";
      } else {
         this.directlyEncoded = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789\'(),-./:? \t\r\n";
      }
   }

   boolean canEncodeDirectly(char var1) {
      boolean var2;
      if(this.directlyEncoded.indexOf(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   byte shift() {
      return (byte)43;
   }

   byte unshift() {
      return (byte)45;
   }
}

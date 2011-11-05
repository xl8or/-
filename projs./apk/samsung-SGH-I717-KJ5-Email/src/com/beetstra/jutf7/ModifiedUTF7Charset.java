package com.beetstra.jutf7;

import com.beetstra.jutf7.UTF7StyleCharset;

class ModifiedUTF7Charset extends UTF7StyleCharset {

   private static final String MODIFIED_BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+,";


   ModifiedUTF7Charset(String var1, String[] var2) {
      super(var1, var2, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+,", (boolean)1);
   }

   boolean canEncodeDirectly(char var1) {
      byte var2 = this.shift();
      boolean var3;
      if(var1 == var2) {
         var3 = false;
      } else if(var1 >= 32 && var1 <= 126) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   byte shift() {
      return (byte)38;
   }

   byte unshift() {
      return (byte)45;
   }
}

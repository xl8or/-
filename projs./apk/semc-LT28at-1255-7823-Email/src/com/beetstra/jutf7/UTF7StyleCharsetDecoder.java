package com.beetstra.jutf7;

import com.beetstra.jutf7.Base64Util;
import com.beetstra.jutf7.UTF7StyleCharset;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

class UTF7StyleCharsetDecoder extends CharsetDecoder {

   private final Base64Util base64;
   private boolean base64mode;
   private int bitsRead;
   private boolean justShifted;
   private boolean justUnshifted;
   private final byte shift;
   private final boolean strict;
   private int tempChar;
   private final byte unshift;


   UTF7StyleCharsetDecoder(UTF7StyleCharset var1, Base64Util var2, boolean var3) {
      super(var1, 0.6F, 1.0F);
      this.base64 = var2;
      this.strict = var3;
      byte var4 = var1.shift();
      this.shift = var4;
      byte var5 = var1.unshift();
      this.unshift = var5;
   }

   private boolean base64bitsWaiting() {
      boolean var1;
      if(this.tempChar == 0 && this.bitsRead < 6) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private CoderResult handleBase64(ByteBuffer var1, CharBuffer var2, byte var3) {
      CoderResult var4 = null;
      int var5 = this.base64.getSextet(var3);
      CoderResult var12;
      if(var5 >= 0) {
         int var6 = this.bitsRead + 6;
         this.bitsRead = var6;
         if(this.bitsRead < 16) {
            int var7 = this.tempChar;
            int var8 = this.bitsRead;
            int var9 = 16 - var8;
            int var10 = var5 << var9;
            int var11 = var7 + var10;
            this.tempChar = var11;
         } else {
            int var13 = this.bitsRead - 16;
            this.bitsRead = var13;
            int var14 = this.tempChar;
            int var15 = this.bitsRead;
            int var16 = var5 >> var15;
            int var17 = var14 + var16;
            this.tempChar = var17;
            char var18 = (char)this.tempChar;
            var2.put(var18);
            int var20 = this.bitsRead;
            int var21 = 16 - var20;
            int var22 = var5 << var21 & '\uffff';
            this.tempChar = var22;
         }
      } else {
         if(this.strict) {
            var12 = this.malformed(var1);
            return var12;
         }

         char var23 = (char)var3;
         var2.put(var23);
         if(this.base64bitsWaiting()) {
            var4 = this.malformed(var1);
         }

         this.setUnshifted();
      }

      var12 = var4;
      return var12;
   }

   private CoderResult malformed(ByteBuffer var1) {
      int var2 = var1.position() - 1;
      var1.position(var2);
      return CoderResult.malformedForLength(1);
   }

   private CoderResult overflow(ByteBuffer var1) {
      int var2 = var1.position() - 1;
      var1.position(var2);
      return CoderResult.OVERFLOW;
   }

   private void setUnshifted() {
      this.base64mode = (boolean)0;
      this.bitsRead = 0;
      this.tempChar = 0;
   }

   protected CoderResult decodeLoop(ByteBuffer var1, CharBuffer var2) {
      while(true) {
         CoderResult var5;
         if(var1.hasRemaining()) {
            byte var3 = var1.get();
            if(this.base64mode) {
               byte var4 = this.unshift;
               if(var3 == var4) {
                  if(this.base64bitsWaiting()) {
                     var5 = this.malformed(var1);
                     return var5;
                  }

                  if(this.justShifted) {
                     if(!var2.hasRemaining()) {
                        var5 = this.overflow(var1);
                        return var5;
                     }

                     char var6 = (char)this.shift;
                     var2.put(var6);
                  } else {
                     this.justUnshifted = (boolean)1;
                  }

                  this.setUnshifted();
               } else {
                  if(!var2.hasRemaining()) {
                     var5 = this.overflow(var1);
                     return var5;
                  }

                  CoderResult var8 = this.handleBase64(var1, var2, var3);
                  if(var8 != null) {
                     var5 = var8;
                     return var5;
                  }
               }

               this.justShifted = (boolean)0;
               continue;
            } else {
               byte var9 = this.shift;
               if(var3 == var9) {
                  this.base64mode = (boolean)1;
                  if(!this.justUnshifted || !this.strict) {
                     this.justShifted = (boolean)1;
                     continue;
                  }

                  var5 = this.malformed(var1);
               } else {
                  if(var2.hasRemaining()) {
                     char var10 = (char)var3;
                     var2.put(var10);
                     this.justUnshifted = (boolean)0;
                     continue;
                  }

                  var5 = this.overflow(var1);
               }
            }
         } else {
            var5 = CoderResult.UNDERFLOW;
         }

         return var5;
      }
   }

   protected CoderResult implFlush(CharBuffer var1) {
      CoderResult var2;
      if((!this.base64mode || !this.strict) && !this.base64bitsWaiting()) {
         var2 = CoderResult.UNDERFLOW;
      } else {
         var2 = CoderResult.malformedForLength(1);
      }

      return var2;
   }

   protected void implReset() {
      this.setUnshifted();
      this.justUnshifted = (boolean)0;
   }
}

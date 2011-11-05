package com.beetstra.jutf7;

import com.beetstra.jutf7.Base64Util;
import com.beetstra.jutf7.UTF7StyleCharset;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

class UTF7StyleCharsetEncoder extends CharsetEncoder {

   private static final float AVG_BYTES_PER_CHAR = 1.5F;
   private static final float MAX_BYTES_PER_CHAR = 5.0F;
   static boolean useUglyHackToForceCallToFlushInJava5;
   private final Base64Util base64;
   private boolean base64mode;
   private int bitsToOutput;
   private final UTF7StyleCharset cs;
   private int sextet;
   private final byte shift;
   private final boolean strict;
   private final byte unshift;


   static {
      String var0 = System.getProperty("java.specification.version");
      String var1 = System.getProperty("java.vm.vendor");
      byte var2;
      if(!"1.4".equals(var0) && !"1.5".equals(var0)) {
         var2 = 0;
      } else {
         var2 = 1;
      }

      useUglyHackToForceCallToFlushInJava5 = (boolean)var2;
      boolean var3 = useUglyHackToForceCallToFlushInJava5;
      boolean var4 = "Sun Microsystems Inc.".equals(var1);
      useUglyHackToForceCallToFlushInJava5 = var3 & var4;
   }

   UTF7StyleCharsetEncoder(UTF7StyleCharset var1, Base64Util var2, boolean var3) {
      super(var1, 1.5F, 5.0F);
      this.cs = var1;
      this.base64 = var2;
      this.strict = var3;
      byte var4 = var1.shift();
      this.shift = var4;
      byte var5 = var1.unshift();
      this.unshift = var5;
   }

   private void encodeBase64(char var1, ByteBuffer var2) {
      if(!this.base64mode) {
         byte var3 = this.shift;
         var2.put(var3);
      }

      this.base64mode = (boolean)1;
      int var5 = this.bitsToOutput + 16;

      for(this.bitsToOutput = var5; this.bitsToOutput >= 6; this.sextet = 0) {
         int var6 = this.bitsToOutput - 6;
         this.bitsToOutput = var6;
         int var7 = this.sextet;
         int var8 = this.bitsToOutput;
         int var9 = var1 >> var8;
         int var10 = var7 + var9;
         this.sextet = var10;
         int var11 = this.sextet & 63;
         this.sextet = var11;
         Base64Util var12 = this.base64;
         int var13 = this.sextet;
         byte var14 = var12.getChar(var13);
         var2.put(var14);
      }

      int var16 = this.bitsToOutput;
      int var17 = 6 - var16;
      int var18 = var1 << var17 & 63;
      this.sextet = var18;
   }

   private void unshift(ByteBuffer var1, char var2) {
      if(this.base64mode) {
         if(this.bitsToOutput != 0) {
            Base64Util var3 = this.base64;
            int var4 = this.sextet;
            byte var5 = var3.getChar(var4);
            var1.put(var5);
         }

         label18: {
            if(!this.base64.contains(var2)) {
               byte var7 = this.unshift;
               if(var2 != var7 && !this.strict) {
                  break label18;
               }
            }

            byte var8 = this.unshift;
            var1.put(var8);
         }

         this.base64mode = (boolean)0;
         this.sextet = 0;
         this.bitsToOutput = 0;
      }
   }

   protected CoderResult encodeLoop(CharBuffer var1, ByteBuffer var2) {
      while(true) {
         CoderResult var3;
         if(var1.hasRemaining()) {
            if(var2.remaining() >= 4) {
               char var4 = var1.get();
               if(this.cs.canEncodeDirectly(var4)) {
                  this.unshift(var2, var4);
                  byte var5 = (byte)var4;
                  var2.put(var5);
                  continue;
               }

               if(!this.base64mode) {
                  byte var7 = this.shift;
                  if(var4 == var7) {
                     byte var8 = this.shift;
                     var2.put(var8);
                     byte var10 = this.unshift;
                     var2.put(var10);
                     continue;
                  }
               }

               this.encodeBase64(var4, var2);
               continue;
            }

            var3 = CoderResult.OVERFLOW;
         } else {
            if(this.base64mode && useUglyHackToForceCallToFlushInJava5) {
               float var12 = (float)var2.limit();
               float var13 = (float)var1.limit();
               float var14 = 5.0F * var13;
               if(var12 != var14) {
                  var3 = CoderResult.OVERFLOW;
                  return var3;
               }
            }

            var3 = CoderResult.UNDERFLOW;
         }

         return var3;
      }
   }

   protected CoderResult implFlush(ByteBuffer var1) {
      CoderResult var2;
      if(this.base64mode) {
         if(var1.remaining() < 2) {
            var2 = CoderResult.OVERFLOW;
            return var2;
         }

         if(this.bitsToOutput != 0) {
            Base64Util var3 = this.base64;
            int var4 = this.sextet;
            byte var5 = var3.getChar(var4);
            var1.put(var5);
         }

         byte var7 = this.unshift;
         var1.put(var7);
      }

      var2 = CoderResult.UNDERFLOW;
      return var2;
   }

   protected void implReset() {
      this.base64mode = (boolean)0;
      this.sextet = 0;
      this.bitsToOutput = 0;
   }
}

package com.kenai.jbosh;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

final class GZIPCodec {

   private static final int BUFFER_SIZE = 512;


   private GZIPCodec() {}

   public static byte[] decode(byte[] var0) throws IOException {
      ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      Object var3 = null;
      boolean var13 = false;

      GZIPInputStream var4;
      try {
         var13 = true;
         var4 = new GZIPInputStream(var1);
         var13 = false;
      } finally {
         if(var13) {
            ((GZIPInputStream)var3).close();
            var2.close();
         }
      }

      short var5 = 512;

      byte[] var6;
      try {
         byte[] var17 = new byte[var5];

         while(true) {
            int var16 = var4.read(var17);
            if(var16 > 0) {
               var2.write(var17, 0, var16);
            }

            if(var16 < 0) {
               var6 = var2.toByteArray();
               break;
            }
         }
      } finally {
         ;
      }

      var4.close();
      var2.close();
      return var6;
   }

   public static byte[] encode(byte[] param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static String getID() {
      return "gzip";
   }
}

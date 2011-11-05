package org.apache.commons.io;

import java.io.IOException;
import java.io.OutputStream;

public class HexDump {

   public static final String EOL = System.getProperty("line.separator");
   private static final char[] _hexcodes = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   private static final int[] _shifts = new int[]{28, 24, 20, 16, 12, 8, 4, 0};


   public HexDump() {}

   private static StringBuffer dump(StringBuffer var0, byte var1) {
      for(int var2 = 0; var2 < 2; ++var2) {
         char[] var3 = _hexcodes;
         int[] var4 = _shifts;
         int var5 = var2 + 6;
         int var6 = var4[var5];
         int var7 = var1 >> var6 & 15;
         char var8 = var3[var7];
         var0.append(var8);
      }

      return var0;
   }

   private static StringBuffer dump(StringBuffer var0, long var1) {
      for(int var3 = 0; var3 < 8; ++var3) {
         char[] var4 = _hexcodes;
         int var5 = _shifts[var3];
         int var6 = (int)(var1 >> var5) & 15;
         char var7 = var4[var6];
         var0.append(var7);
      }

      return var0;
   }

   public static void dump(byte[] var0, long var1, OutputStream var3, int var4) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
      if(var4 >= 0) {
         int var5 = var0.length;
         if(var4 < var5) {
            if(var3 == null) {
               throw new IllegalArgumentException("cannot write to nullstream");
            }

            long var9 = (long)var4;
            long var11 = var1 + var9;
            StringBuffer var13 = new StringBuffer(74);
            int var14 = var4;

            while(true) {
               int var15 = var0.length;
               if(var14 >= var15) {
                  return;
               }

               int var16 = var0.length - var14;
               if(var16 > 16) {
                  var16 = 16;
               }

               StringBuffer var17 = dump(var13, var11).append(' ');

               for(int var18 = 0; var18 < 16; ++var18) {
                  if(var18 < var16) {
                     int var19 = var18 + var14;
                     byte var20 = var0[var19];
                     dump(var13, var20);
                  } else {
                     StringBuffer var23 = var13.append("  ");
                  }

                  StringBuffer var22 = var13.append(' ');
               }

               for(int var24 = 0; var24 < var16; ++var24) {
                  int var25 = var24 + var14;
                  if(var0[var25] >= 32) {
                     int var26 = var24 + var14;
                     if(var0[var26] < 127) {
                        int var27 = var24 + var14;
                        char var28 = (char)var0[var27];
                        var13.append(var28);
                        continue;
                     }
                  }

                  StringBuffer var30 = var13.append('.');
               }

               String var31 = EOL;
               var13.append(var31);
               byte[] var33 = var13.toString().getBytes();
               var3.write(var33);
               var3.flush();
               var13.setLength(0);
               long var34 = (long)var16;
               var11 += var34;
               var14 += 16;
            }
         }
      }

      StringBuilder var6 = (new StringBuilder()).append("illegal index: ").append(var4).append(" into array of length ");
      int var7 = var0.length;
      String var8 = var6.append(var7).toString();
      throw new ArrayIndexOutOfBoundsException(var8);
   }
}

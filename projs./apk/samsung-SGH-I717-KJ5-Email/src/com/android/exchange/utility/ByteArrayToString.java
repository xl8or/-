package com.android.exchange.utility;

import android.util.Log;
import com.android.exchange.AbstractSyncService;

public class ByteArrayToString {

   private static final String LOGTAG = "ByteArrayToString";


   public ByteArrayToString() {}

   public static String convert(byte[] var0) {
      String var1;
      if(var0 != null && var0.length > 0) {
         String[] var2 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
         StringBuffer var3 = new StringBuffer(36);
         int var4 = 0;

         while(true) {
            int var5 = var0.length;
            if(var4 >= var5) {
               var1 = new String(var3);
               break;
            }

            byte var6 = (byte)((byte)(var0[var4] >> 4) & 15);
            String var7 = var2[var6];
            var3.append(var7);
            byte var9 = (byte)(var0[var4] & 15);
            String var10 = var2[var9];
            var3.append(var10);
            ++var4;
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   public static void printByteArray(AbstractSyncService var0, byte[] var1) {
      if(var1 != null) {
         if(var1.length > 0) {
            String[] var2 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
            StringBuffer var3 = new StringBuffer(36);
            int var4 = 0;

            while(true) {
               int var5 = var1.length;
               if(var4 >= var5) {
                  if(var0 == null) {
                     String var17 = new String(var3);
                     int var18 = Log.d("ByteArrayToString", var17);
                     return;
                  }

                  String[] var19 = new String[1];
                  String var20 = new String(var3);
                  var19[0] = var20;
                  var0.userLog(var19);
                  return;
               }

               byte var6 = (byte)((byte)(var1[var4] >> 4) & 15);
               String var7 = var2[var6];
               var3.append(var7);
               byte var9 = (byte)(var1[var4] & 15);
               String var10 = var2[var9];
               var3.append(var10);
               ++var4;
               if(var4 % 16 == 0) {
                  if(var0 == null) {
                     String var12 = new String(var3);
                     int var13 = Log.d("ByteArrayToString", var12);
                  } else {
                     String[] var15 = new String[1];
                     String var16 = new String(var3);
                     var15[0] = var16;
                     var0.userLog(var15);
                  }

                  StringBuffer var14 = var3.delete(0, 32);
               }
            }
         }
      }
   }
}

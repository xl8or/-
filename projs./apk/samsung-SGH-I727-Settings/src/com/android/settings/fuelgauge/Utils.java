package com.android.settings.fuelgauge;

import android.content.Context;

public class Utils {

   private static final int SECONDS_PER_DAY = 86400;
   private static final int SECONDS_PER_HOUR = 3600;
   private static final int SECONDS_PER_MINUTE = 60;


   public Utils() {}

   public static String formatBytes(Context var0, double var1) {
      String var5;
      if(var1 > 1000000.0D) {
         Object[] var3 = new Object[1];
         Float var4 = Float.valueOf((float)((int)(var1 / 1000.0D)) / 1000.0F);
         var3[0] = var4;
         var5 = String.format("%.2f MB", var3);
      } else if(var1 > 1024.0D) {
         Object[] var6 = new Object[1];
         Float var7 = Float.valueOf((float)((int)(var1 / 10.0D)) / 100.0F);
         var6[0] = var7;
         var5 = String.format("%.2f KB", var6);
      } else {
         Object[] var8 = new Object[1];
         Integer var9 = Integer.valueOf((int)var1);
         var8[0] = var9;
         var5 = String.format("%d bytes", var8);
      }

      return var5;
   }

   public static String formatElapsedTime(Context var0, double var1) {
      StringBuilder var3 = new StringBuilder();
      int var4 = (int)Math.floor(var1 / 1000.0D);
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;
      if(var4 > 86400) {
         var5 = var4 / 86400;
         int var8 = var5 * 86400;
         var4 -= var8;
      }

      if(var4 > 3600) {
         var6 = var4 / 3600;
         int var9 = var6 * 3600;
         var4 -= var9;
      }

      if(var4 > 60) {
         var7 = var4 / 60;
         int var10 = var7 * 60;
         var4 -= var10;
      }

      if(var5 > 0) {
         Object[] var11 = new Object[4];
         Integer var12 = Integer.valueOf(var5);
         var11[0] = var12;
         Integer var13 = Integer.valueOf(var6);
         var11[1] = var13;
         Integer var14 = Integer.valueOf(var7);
         var11[2] = var14;
         Integer var15 = Integer.valueOf(var4);
         var11[3] = var15;
         String var16 = var0.getString(2131231755, var11);
         var3.append(var16);
      } else if(var6 > 0) {
         Object[] var18 = new Object[3];
         Integer var19 = Integer.valueOf(var6);
         var18[0] = var19;
         Integer var20 = Integer.valueOf(var7);
         var18[1] = var20;
         Integer var21 = Integer.valueOf(var4);
         var18[2] = var21;
         String var22 = var0.getString(2131231756, var18);
         var3.append(var22);
      } else if(var7 > 0) {
         Object[] var24 = new Object[2];
         Integer var25 = Integer.valueOf(var7);
         var24[0] = var25;
         Integer var26 = Integer.valueOf(var4);
         var24[1] = var26;
         String var27 = var0.getString(2131231757, var24);
         var3.append(var27);
      } else {
         Object[] var29 = new Object[1];
         Integer var30 = Integer.valueOf(var4);
         var29[0] = var30;
         String var31 = var0.getString(2131231758, var29);
         var3.append(var31);
      }

      return var3.toString();
   }
}

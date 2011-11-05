package com.google.android.finsky.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.text.TextUtils;
import com.google.android.finsky.config.G;
import java.lang.ref.SoftReference;

public class DrawingUtils {

   private static SoftReference<Bitmap> sOverlayBitmapRef;


   public DrawingUtils() {}

   private static Bitmap createOverlayBitmap() {
      int var0 = 12 * 8;
      int var1 = 12 * 2;
      Config var2 = Config.ARGB_8888;
      Bitmap var5 = Bitmap.createBitmap(var0, var1, var2);
      String var6 = Long.toHexString(((Long)G.androidId.get()).longValue());
      if(!TextUtils.isEmpty(var6)) {
         String var7 = "0";
         if(!var6.equals(var7)) {
            int var8 = var6.length();
            byte var9 = 16;
            if(var8 != var9) {
               byte var10 = 0;
               byte var11 = 16;
               var6 = var6.substring(var10, var11);
            }

            var6 = var6.toLowerCase();
            int var12 = -1610612736;

            for(int var13 = 0; var13 < var0; var13 += 4) {
               byte var15 = 0;
               var5.setPixel(var13, var15, var12);
            }

            for(int var16 = 0; var16 < var1; var16 += 4) {
               byte var17 = 0;
               var5.setPixel(var17, var16, var12);
            }

            int var18 = 0;

            while(true) {
               int var19 = var6.length();
               if(var18 >= var19) {
                  SoftReference var55 = new SoftReference(var5);
                  sOverlayBitmapRef = var55;
                  return var5;
               }

               char var20 = var6.charAt(var18);
               byte var21 = 57;
               int var22;
               if(var20 <= var21) {
                  var22 = var20 + -48;
               } else {
                  var22 = var20 + 10 + -97;
               }

               int var23 = var22 >> 3 & 1;
               byte var24 = 1;
               boolean var25;
               if(var23 == var24) {
                  var25 = true;
               } else {
                  var25 = false;
               }

               int var26 = var22 >> 2 & 1;
               byte var27 = 1;
               boolean var28;
               if(var26 == var27) {
                  var28 = true;
               } else {
                  var28 = false;
               }

               int var29 = var22 >> 1 & 1;
               byte var30 = 1;
               boolean var31;
               if(var29 == var30) {
                  var31 = true;
               } else {
                  var31 = false;
               }

               int var32 = var22 & 1;
               byte var33 = 1;
               boolean var34;
               if(var32 == var33) {
                  var34 = true;
               } else {
                  var34 = false;
               }

               int var35 = var18 % 8;
               int var36 = 12 * var35;
               int var37 = var18 / 8;
               int var38 = 12 * var37;
               if(var25) {
                  int var39 = var36 + 4;
                  int var40 = var38 + 4;
                  var5.setPixel(var39, var40, var12);
               }

               if(var28) {
                  int var43 = var36 + 8;
                  int var44 = var38 + 4;
                  var5.setPixel(var43, var44, var12);
               }

               if(var31) {
                  int var47 = var36 + 4;
                  int var48 = var38 + 8;
                  var5.setPixel(var47, var48, var12);
               }

               if(var34) {
                  int var51 = var36 + 8;
                  int var52 = var38 + 8;
                  var5.setPixel(var51, var52, var12);
               }

               ++var18;
            }
         }
      }

      var5 = null;
      return var5;
   }

   public static void drawTrackerOverlay(Canvas var0, int var1, int var2) {
      Bitmap var3;
      if(sOverlayBitmapRef != null) {
         var3 = (Bitmap)sOverlayBitmapRef.get();
      } else {
         var3 = null;
      }

      if(var3 == null) {
         var3 = createOverlayBitmap();
      }

      if(var3 != null) {
         int var4 = var3.getWidth();
         int var5 = var3.getHeight();

         for(int var6 = 0; var6 < var1; var6 += var4) {
            for(int var7 = 0; var7 < var2; var7 += var5) {
               float var8 = (float)var6;
               float var9 = (float)var7;
               var0.drawBitmap(var3, var8, var9, (Paint)null);
            }
         }

      }
   }
}

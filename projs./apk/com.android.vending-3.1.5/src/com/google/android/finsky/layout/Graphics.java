package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class Graphics {

   private Graphics() {}

   public static void fadeIn(View var0) {
      if(var0.getVisibility() != 0) {
         var0.setVisibility(0);
         Animation var1 = getFadeAnimation(0.0F, 1.0F);
         var0.startAnimation(var1);
      }
   }

   public static void fadeOut(View var0) {
      if(var0.getVisibility() != 4) {
         var0.setVisibility(4);
         Animation var1 = getFadeAnimation(1.0F, 0.0F);
         var0.startAnimation(var1);
      }
   }

   private static Animation getFadeAnimation(float var0, float var1) {
      AlphaAnimation var2 = new AlphaAnimation(var0, var1);
      var2.setDuration(1000L);
      return var2;
   }

   public static int getScaledSize(Context var0, float var1) {
      float var2 = var0.getResources().getDisplayMetrics().density;
      return Math.round(var1 * var2);
   }

   public static int getScaledSize(Context var0, int var1) {
      float var2 = (float)var1;
      return getScaledSize(var0, var2);
   }

   public static int getTextHeight(String var0, Paint var1) {
      return getTextSize(var0, var1)[1];
   }

   public static int[] getTextSize(String var0, Paint var1) {
      Rect var2 = new Rect();
      int var3 = var0.length();
      var1.getTextBounds(var0, 0, var3, var2);
      int[] var4 = new int[2];
      int var5 = var2.width();
      var4[0] = var5;
      int var6 = var2.height();
      var4[1] = var6;
      return var4;
   }

   public static int getTextWidth(String var0, Paint var1) {
      return getTextSize(var0, var1)[0];
   }

   public static LinearGradient getVerticalGradient(int var0, int[] var1) {
      float var2 = (float)var0;
      TileMode var3 = TileMode.CLAMP;
      float var4 = 0.0F;
      float var5 = 0.0F;
      return new LinearGradient(0.0F, var4, var5, var2, var1, (float[])null, var3);
   }
}

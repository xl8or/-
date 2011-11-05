package com.android.settings.fuelgauge;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

class PercentageBar extends Drawable {

   Drawable bar;
   int lastWidth = -1;
   double percent;


   PercentageBar() {}

   private int getBarWidth() {
      double var1 = (double)this.getBounds().width();
      double var3 = this.percent;
      int var5 = (int)(var1 * var3 / 100.0D);
      int var6 = this.bar.getIntrinsicWidth();
      return Math.max(var5, var6);
   }

   public void draw(Canvas var1) {
      if(this.lastWidth == -1) {
         int var2 = this.getBarWidth();
         this.lastWidth = var2;
         Drawable var3 = this.bar;
         int var4 = this.lastWidth;
         int var5 = this.bar.getIntrinsicHeight();
         var3.setBounds(0, 0, var4, var5);
      }

      this.bar.draw(var1);
   }

   public int getIntrinsicHeight() {
      return this.bar.getIntrinsicHeight();
   }

   public int getOpacity() {
      return -1;
   }

   public void setAlpha(int var1) {}

   public void setColorFilter(ColorFilter var1) {}
}

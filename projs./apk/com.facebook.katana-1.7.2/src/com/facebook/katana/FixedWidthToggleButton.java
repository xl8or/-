package com.facebook.katana;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.util.AttributeSet;
import android.widget.ToggleButton;

class FixedWidthToggleButton extends ToggleButton {

   public FixedWidthToggleButton(Context var1) {
      super(var1);
   }

   public FixedWidthToggleButton(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public FixedWidthToggleButton(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void onMeasure(int var1, int var2) {
      CharSequence[] var3 = new CharSequence[2];
      CharSequence var4 = this.getTextOn();
      var3[0] = var4;
      CharSequence var5 = this.getTextOff();
      var3[1] = var5;
      Paint var6 = new Paint();
      Typeface var7 = this.getTypeface();
      Typeface var10 = var6.setTypeface(var7);
      int var11 = 0;
      CharSequence[] var12 = var3;
      int var13 = var3.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         CharSequence var15 = var12[var14];
         TextPaint var16 = this.getPaint();
         Alignment var17 = Alignment.ALIGN_NORMAL;
         int var18 = (int)(new StaticLayout(var15, var16, 1000, var17, 1.0F, 0.0F, (boolean)1)).getLineWidth(0);
         if(var18 > var11) {
            var11 = var18;
         }
      }

      int var21 = this.getPaddingLeft() + var11;
      int var22 = this.getPaddingRight();
      int var23 = var21 + var22;
      int var25 = resolveSize(var23, var1);
      int var26 = this.getSuggestedMinimumHeight();
      int var28 = resolveSize(var26, var2);
      this.setMeasuredDimension(var25, var28);
   }
}

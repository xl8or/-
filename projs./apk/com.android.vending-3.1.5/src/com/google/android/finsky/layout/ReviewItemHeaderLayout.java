package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ReviewItemHeaderLayout extends LinearLayout {

   public ReviewItemHeaderLayout(Context var1) {
      super(var1);
   }

   public ReviewItemHeaderLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = this.getPaddingTop();
      int var7 = this.getPaddingLeft();
      RatingBar var8 = (RatingBar)this.getChildAt(0);
      ViewGroup var9 = (ViewGroup)this.getChildAt(1);
      TextView var10 = (TextView)this.getChildAt(2);
      if(var8.getVisibility() != 8) {
         int var11 = var8.getMeasuredWidth() + var7;
         int var12 = var8.getMeasuredHeight() + var6;
         var8.layout(var7, var6, var11, var12);
         LayoutParams var13 = (LayoutParams)var8.getLayoutParams();
         int var14 = var8.getMeasuredWidth();
         int var15 = var13.rightMargin;
         int var16 = var14 + var15;
         var7 += var16;
      }

      int var17 = var9.getMeasuredWidth() + var7;
      int var18 = var9.getMeasuredHeight() + var6;
      var9.layout(var7, var6, var17, var18);
      int var19 = var9.getMeasuredWidth();
      int var20 = var7 + var19;
      int var21 = var10.getMeasuredWidth() + var20;
      int var22 = var10.getMeasuredHeight() + var6;
      var10.layout(var20, var6, var21, var22);
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1);
      int var4 = MeasureSpec.getMode(var1);
      int var5 = this.getPaddingLeft();
      int var6 = var3 - var5;
      int var7 = this.getPaddingRight();
      int var8 = var6 - var7;
      RatingBar var9 = (RatingBar)this.getChildAt(0);
      ViewGroup var10 = (ViewGroup)this.getChildAt(1);
      TextView var11 = (TextView)this.getChildAt(2);
      var10.measure(0, 0);
      int var12 = var10.getMeasuredWidth();
      var11.measure(0, 0);
      int var13 = var11.getMeasuredWidth();
      int var14 = 0;
      if(var9.getVisibility() != 8) {
         var9.measure(0, 0);
         LayoutParams var15 = (LayoutParams)var9.getLayoutParams();
         int var16 = var9.getMeasuredWidth();
         int var17 = var15.rightMargin;
         var14 = var16 + var17;
      }

      int var18 = var12 + var13 + var14;
      if(var4 != 0 && var18 > var8) {
         int var19 = MeasureSpec.makeMeasureSpec(var8 - var13 - var14, 1073741824);
         var10.measure(var19, 0);
      }

      int var20 = var10.getMeasuredHeight();
      int var21 = var11.getMeasuredHeight();
      int var22 = Math.max(var20, var21);
      int var23 = var9.getMeasuredHeight();
      int var24 = Math.max(var22, var23);
      this.setMeasuredDimension(var3, var24);
   }
}

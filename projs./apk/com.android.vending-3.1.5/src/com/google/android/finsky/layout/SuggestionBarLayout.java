package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;

public class SuggestionBarLayout extends RelativeLayout {

   public SuggestionBarLayout(Context var1) {
      super(var1);
   }

   public SuggestionBarLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public SuggestionBarLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = this.getPaddingLeft();
      int var7 = this.getPaddingRight();
      int var8 = this.getPaddingTop();
      int var9 = this.getPaddingBottom();
      int var11 = 2131755309;
      View var12 = this.findViewById(var11);
      int var14 = 2131755310;
      View var15 = this.findViewById(var14);
      int var17 = 2131755311;
      View var18 = this.findViewById(var17);
      int var19 = (this.getHeight() - var8 - var9) / 2;
      int var20 = var8 + var19;
      int var21 = var18.getMeasuredHeight();
      int var22 = var18.getMeasuredWidth();
      int var23 = this.getWidth() - var7 - var22;
      int var24 = var21 / 2;
      int var25 = var20 - var24;
      int var26 = this.getWidth() - var7;
      int var27 = var21 / 2 + var20;
      var18.layout(var23, var25, var26, var27);
      int var32 = var12.getMeasuredWidth();
      int var33 = var15.getMeasuredWidth();
      int var34 = this.getWidth() - var6 - var7 - var22;
      if(var32 + var33 <= var34) {
         int var35 = var12.getMeasuredHeight();
         int var36 = var35 / 2;
         int var37 = var20 - var36;
         int var38 = var6 + var32;
         int var39 = var35 / 2 + var20;
         var12.layout(var6, var37, var38, var39);
         int var44 = var6 + var32;
         int var45 = var35 / 2;
         int var46 = var20 - var45;
         int var47 = var6 + var32 + var33;
         int var48 = var35 / 2 + var20;
         var15.layout(var44, var46, var47, var48);
      } else {
         int var54 = var12.getMeasuredHeight();
         int var55 = var15.getMeasuredHeight();
         int var56 = ((MarginLayoutParams)var15.getLayoutParams()).topMargin;
         int var57 = (var54 + var55 + var56) / 2;
         int var58 = var20 - var57;
         int var59 = var58 + var54 + var56;
         int var60 = var6 + var32;
         int var61 = var58 + var54;
         var12.layout(var6, var58, var60, var61);
         int var66 = var6 + var33;
         int var67 = var59 + var55;
         var15.layout(var6, var59, var66, var67);
      }
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1);
      int var4 = this.getPaddingLeft();
      int var5 = this.getPaddingRight();
      View var6 = this.findViewById(2131755309);
      View var7 = this.findViewById(2131755310);
      View var8 = this.findViewById(2131755311);
      var8.measure(0, 0);
      int var9 = var3 - var4 - var5;
      int var10 = var8.getMeasuredWidth();
      int var11 = MeasureSpec.makeMeasureSpec(var9 - var10, Integer.MIN_VALUE);
      var6.measure(var11, 0);
      var7.measure(var11, 0);
      int var12 = MeasureSpec.getSize(var1);
      int var13 = MeasureSpec.getSize(var2);
      this.setMeasuredDimension(var12, var13);
   }
}

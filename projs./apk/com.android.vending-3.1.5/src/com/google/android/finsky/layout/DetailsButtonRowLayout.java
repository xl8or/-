package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;

public class DetailsButtonRowLayout extends LinearLayout {

   public DetailsButtonRowLayout(Context var1) {
      super(var1);
   }

   public DetailsButtonRowLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private View getSingleVisibleChild() {
      int var1 = this.getChildCount();
      int var2 = 0;

      View var3;
      while(true) {
         if(var2 >= var1) {
            var3 = null;
            break;
         }

         var3 = this.getChildAt(var2);
         if(var3.getVisibility() == 0) {
            break;
         }

         ++var2;
      }

      return var3;
   }

   private int getVisibleChildrenCount() {
      int var1 = 0;
      int var2 = this.getChildCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         if(this.getChildAt(var3).getVisibility() == 0) {
            ++var1;
         }
      }

      return var1;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      if(this.getVisibleChildrenCount() == 1) {
         View var6 = this.getSingleVisibleChild();
         int var7 = this.getWidth();
         int var8 = this.getPaddingRight();
         int var9 = var7 - var8;
         int var10 = var6.getMeasuredWidth();
         int var11 = var9 - var10;
         int var12 = var6.getTop();
         int var13 = var6.getBottom();
         var6.layout(var11, var12, var9, var13);
      }
   }

   protected void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      if(this.getVisibleChildrenCount() == 1) {
         View var3 = this.getSingleVisibleChild();
         int var4 = MeasureSpec.getSize(var1);
         int var5 = this.getPaddingLeft();
         int var6 = var4 - var5;
         int var7 = this.getPaddingRight();
         int var8 = var6 - var7;
         int var9 = var3.getMeasuredHeight();
         int var10 = MeasureSpec.makeMeasureSpec(var8 / 2, 1073741824);
         int var11 = MeasureSpec.makeMeasureSpec(var9, 1073741824);
         var3.measure(var10, var11);
      }
   }
}

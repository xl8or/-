package com.google.android.finsky.activities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import com.google.android.finsky.layout.ObservableScrollView;

public class PurchaseContentLayout extends ViewGroup {

   private ObservableScrollView mDetailsScroller;
   private View mInputPanel;
   private View mLeadingStrip;
   private View mPurchasePanel;


   public PurchaseContentLayout(Context var1) {
      super(var1);
   }

   public PurchaseContentLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public PurchaseContentLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public int getAcquireRowTop() {
      ObservableScrollView var1 = (ObservableScrollView)this.getParent();
      ObservableScrollView var2 = this.mDetailsScroller;
      if(var1 != var2) {
         this.mDetailsScroller = var1;
         View var3 = this.mDetailsScroller.findViewById(2131755277);
         this.mInputPanel = var3;
         View var4 = this.mDetailsScroller.findViewById(2131755278);
         this.mPurchasePanel = var4;
         View var5 = this.findViewById(2131755030);
         this.mLeadingStrip = var5;
      }

      int var6 = this.mDetailsScroller.getViewportTop();
      int var7 = this.mInputPanel.getTop();
      if(this.mPurchasePanel.getVisibility() != 8) {
         int var8 = this.mPurchasePanel.getBottom();
         var7 += var8;
      }

      if(this.mLeadingStrip != null) {
         int var9 = this.mLeadingStrip.getBottom();
         var7 += var9;
      }

      if(var6 <= var7) {
         var6 = var7;
      }

      return var6;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      View var6 = this.getChildAt(0);
      View var7 = this.getChildAt(1);
      int var8 = this.getPaddingLeft();
      int var9 = this.getPaddingTop();
      int var10 = this.getWidth() - var8;
      int var11 = this.getPaddingRight();
      int var12 = var10 - var11;
      int var13 = this.getHeight() - var9;
      int var14 = this.getPaddingBottom();
      int var15 = var13 - var14;
      int var16 = var8 + var12;
      int var17 = var9 + var15;
      var6.layout(var8, var9, var16, var17);
      int var18 = this.getAcquireRowTop();
      int var19 = var8 + var12;
      int var20 = var7.getMeasuredHeight() + var18;
      var7.layout(var8, var18, var19, var20);
   }

   protected void onMeasure(int var1, int var2) {
      View var3 = this.getChildAt(0);
      View var4 = this.getChildAt(1);
      int var5 = MeasureSpec.getSize(var1);
      int var6 = this.getPaddingLeft();
      int var7 = var5 - var6;
      int var8 = this.getPaddingRight();
      int var9 = MeasureSpec.makeMeasureSpec(var7 - var8, 1073741824);
      var3.measure(var9, 0);
      int var10 = MeasureSpec.makeMeasureSpec(var3.getMeasuredHeight(), 1073741824);
      var3.measure(var9, var10);
      int var11 = MeasureSpec.makeMeasureSpec(var3.getMeasuredHeight(), Integer.MIN_VALUE);
      var4.measure(var9, var11);
      int var12 = MeasureSpec.getSize(var1);
      int var13 = var3.getMeasuredHeight();
      this.setMeasuredDimension(var12, var13);
   }
}

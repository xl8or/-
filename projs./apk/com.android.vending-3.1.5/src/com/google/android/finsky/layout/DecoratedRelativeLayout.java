package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Checkable;
import com.google.android.finsky.layout.IndexedRelativeLayout;

public class DecoratedRelativeLayout extends IndexedRelativeLayout implements Checkable {

   private static final int[] CHECKED_STATE_SET;
   private static final int FADE_END_INDEX = 50;
   private static final int FADE_START_INDEX = 20;
   private final int mCellBackgroundColor;
   private boolean mChecked;
   private final int mWatermarkActivatedTextColor;
   private final int mWatermarkTextColor;
   private Paint sWatermarkPaint;


   static {
      int[] var0 = new int[]{16842912};
      CHECKED_STATE_SET = var0;
   }

   public DecoratedRelativeLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public DecoratedRelativeLayout(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public DecoratedRelativeLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.setWillNotDraw((boolean)0);
      Resources var4 = var1.getResources();
      int var5 = var4.getColor(2131361836);
      this.mWatermarkActivatedTextColor = var5;
      int var6 = var4.getColor(2131361835);
      this.mWatermarkTextColor = var6;
      int var7 = var4.getColor(2131361839);
      this.mCellBackgroundColor = var7;
   }

   public boolean isChecked() {
      return this.mChecked;
   }

   protected int[] onCreateDrawableState(int var1) {
      int var2 = var1 + 1;
      int[] var3 = super.onCreateDrawableState(var2);
      if(this.mChecked) {
         int[] var4 = CHECKED_STATE_SET;
         mergeDrawableStates(var3, var4);
      }

      return var3;
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      if(this.mItemIndex >= 0) {
         boolean var2;
         if(!this.isPressed() && !this.isFocused()) {
            var2 = false;
         } else {
            var2 = true;
         }

         if(var2 || this.mItemIndex < 50) {
            Resources var3 = this.getContext().getResources();
            if(this.sWatermarkPaint == null) {
               Paint var4 = new Paint(1);
               this.sWatermarkPaint = var4;
               float var5 = var3.getDimension(2131427387);
               this.sWatermarkPaint.setTextSize(var5);
            }

            if(!var2 && this.mItemIndex > 20) {
               float var12 = (float)(this.mItemIndex + -20) / 30.0F;
               float var13 = (float)Color.red(this.mCellBackgroundColor) * var12;
               float var14 = 1.0F - var12;
               float var15 = (float)Color.red(this.mWatermarkTextColor);
               float var16 = var14 * var15;
               int var17 = (int)(var13 + var16);
               float var18 = (float)Color.green(this.mCellBackgroundColor) * var12;
               float var19 = 1.0F - var12;
               float var20 = (float)Color.green(this.mWatermarkTextColor);
               float var21 = var19 * var20;
               int var22 = (int)(var18 + var21);
               float var23 = (float)Color.blue(this.mCellBackgroundColor) * var12;
               float var24 = 1.0F - var12;
               float var25 = (float)Color.blue(this.mWatermarkTextColor);
               float var26 = var24 * var25;
               int var27 = (int)(var23 + var26);
               Paint var28 = this.sWatermarkPaint;
               int var29 = Color.rgb(var17, var22, var27);
               var28.setColor(var29);
            } else {
               int var6;
               if(var2) {
                  var6 = this.mWatermarkActivatedTextColor;
               } else {
                  var6 = this.mWatermarkTextColor;
               }

               this.sWatermarkPaint.setColor(var6);
            }

            String var7 = Integer.toString(this.mItemIndex);
            float var8 = this.sWatermarkPaint.measureText(var7);
            float var9 = (float)this.getWidth() - var8;
            float var10 = (float)this.getHeight();
            Paint var11 = this.sWatermarkPaint;
            var1.drawText(var7, var9, var10, var11);
         }
      }
   }

   public void setChecked(boolean var1) {
      if(this.mChecked != var1) {
         this.mChecked = var1;
         this.refreshDrawableState();
      }
   }

   public void toggle() {
      byte var1;
      if(!this.mChecked) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      this.setChecked((boolean)var1);
   }
}

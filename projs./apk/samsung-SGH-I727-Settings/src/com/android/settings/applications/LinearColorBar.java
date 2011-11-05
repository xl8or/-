package com.android.settings.applications;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearColorBar extends LinearLayout {

   static final int LEFT_COLOR = 10526880;
   static final int MIDDLE_COLOR = 10526880;
   static final int RIGHT_COLOR = 10535072;
   final Paint mColorGradientPaint;
   final Path mColorPath;
   final Paint mEdgeGradientPaint;
   final Path mEdgePath;
   private float mGreenRatio;
   int mLastInterestingLeft;
   int mLastInterestingRight;
   int mLineWidth;
   final Paint mPaint;
   final Rect mRect;
   private float mRedRatio;
   private boolean mShowingGreen;
   private float mYellowRatio;


   public LinearColorBar(Context var1, AttributeSet var2) {
      super(var1, var2);
      Rect var3 = new Rect();
      this.mRect = var3;
      Paint var4 = new Paint();
      this.mPaint = var4;
      Path var5 = new Path();
      this.mColorPath = var5;
      Path var6 = new Path();
      this.mEdgePath = var6;
      Paint var7 = new Paint();
      this.mColorGradientPaint = var7;
      Paint var8 = new Paint();
      this.mEdgeGradientPaint = var8;
      this.setWillNotDraw((boolean)0);
      Paint var9 = this.mPaint;
      Style var10 = Style.FILL;
      var9.setStyle(var10);
      Paint var11 = this.mColorGradientPaint;
      Style var12 = Style.FILL;
      var11.setStyle(var12);
      this.mColorGradientPaint.setAntiAlias((boolean)1);
      Paint var13 = this.mEdgeGradientPaint;
      Style var14 = Style.STROKE;
      var13.setStyle(var14);
      byte var15;
      if(this.getResources().getDisplayMetrics().densityDpi >= 240) {
         var15 = 2;
      } else {
         var15 = 1;
      }

      this.mLineWidth = var15;
      Paint var16 = this.mEdgeGradientPaint;
      float var17 = (float)this.mLineWidth;
      var16.setStrokeWidth(var17);
      this.mEdgeGradientPaint.setAntiAlias((boolean)1);
   }

   private void updateIndicator() {
      int var1 = this.getPaddingTop();
      int var2 = this.getPaddingBottom();
      int var3 = var1 - var2;
      if(var3 < 0) {
         var3 = 0;
      }

      this.mRect.top = var3;
      Rect var4 = this.mRect;
      int var5 = this.getHeight();
      var4.bottom = var5;
      if(this.mShowingGreen) {
         Paint var6 = this.mColorGradientPaint;
         float var7 = (float)(var3 - 2);
         TileMode var8 = TileMode.CLAMP;
         float var9 = 0.0F;
         float var10 = 0.0F;
         LinearGradient var11 = new LinearGradient(0.0F, var9, var10, var7, 10535072, -6242144, var8);
         var6.setShader(var11);
      } else {
         Paint var22 = this.mColorGradientPaint;
         float var23 = (float)(var3 - 2);
         TileMode var24 = TileMode.CLAMP;
         float var25 = 0.0F;
         float var26 = 0.0F;
         int var27 = 10526880;
         int var28 = -6250336;
         LinearGradient var29 = new LinearGradient(0.0F, var25, var26, var23, var27, var28, var24);
         var22.setShader(var29);
      }

      Paint var13 = this.mEdgeGradientPaint;
      float var14 = (float)(var3 / 2);
      TileMode var15 = TileMode.CLAMP;
      float var16 = 0.0F;
      float var17 = 0.0F;
      int var18 = 10526880;
      int var19 = -6250336;
      LinearGradient var20 = new LinearGradient(0.0F, var16, var17, var14, var18, var19, var15);
      var13.setShader(var20);
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      int var2 = this.getWidth();
      int var3 = 0;
      float var4 = (float)var2;
      float var5 = this.mRedRatio;
      int var6 = (int)(var4 * var5);
      int var7 = var3 + var6;
      float var8 = (float)var2;
      float var9 = this.mYellowRatio;
      int var10 = (int)(var8 * var9);
      int var11 = var7 + var10;
      float var12 = (float)var2;
      float var13 = this.mGreenRatio;
      int var14 = (int)(var12 * var13);
      int var15 = var11 + var14;
      int var17;
      int var16;
      if(this.mShowingGreen) {
         var16 = var11;
         var17 = var15;
      } else {
         var16 = var7;
         var17 = var11;
      }

      if(this.mLastInterestingLeft != var16 || this.mLastInterestingRight != var17) {
         this.mColorPath.reset();
         this.mEdgePath.reset();
         if(var16 < var17) {
            int var18 = this.mRect.top;
            Path var19 = this.mColorPath;
            float var20 = (float)var16;
            float var21 = (float)this.mRect.top;
            var19.moveTo(var20, var21);
            Path var22 = this.mColorPath;
            float var23 = (float)var16;
            float var24 = (float)var18;
            var22.cubicTo(var23, 0.0F, -2.0F, var24, -2.0F, 0.0F);
            Path var25 = this.mColorPath;
            float var26 = (float)(var2 + 2 - 1);
            var25.lineTo(var26, 0.0F);
            Path var27 = this.mColorPath;
            float var28 = (float)(var2 + 2 - 1);
            float var29 = (float)var18;
            float var30 = (float)var17;
            float var31 = (float)var17;
            float var32 = (float)this.mRect.top;
            var27.cubicTo(var28, var29, var30, 0.0F, var31, var32);
            this.mColorPath.close();
            float var33 = (float)this.mLineWidth + 0.5F;
            Path var34 = this.mEdgePath;
            float var35 = -2.0F + var33;
            var34.moveTo(var35, 0.0F);
            Path var36 = this.mEdgePath;
            float var37 = -2.0F + var33;
            float var38 = (float)var18;
            float var39 = (float)var16 + var33;
            float var40 = (float)var16 + var33;
            float var41 = (float)this.mRect.top;
            var36.cubicTo(var37, var38, var39, 0.0F, var40, var41);
            Path var42 = this.mEdgePath;
            float var43 = (float)(var2 + 2 - 1) - var33;
            var42.moveTo(var43, 0.0F);
            Path var44 = this.mEdgePath;
            float var45 = (float)(var2 + 2 - 1) - var33;
            float var46 = (float)var18;
            float var47 = (float)var17 - var33;
            float var48 = (float)var17 - var33;
            float var49 = (float)this.mRect.top;
            var44.cubicTo(var45, var46, var47, 0.0F, var48, var49);
         }

         this.mLastInterestingLeft = var16;
         this.mLastInterestingRight = var17;
      }

      if(!this.mEdgePath.isEmpty()) {
         Path var52 = this.mEdgePath;
         Paint var53 = this.mEdgeGradientPaint;
         var1.drawPath(var52, var53);
         Path var57 = this.mColorPath;
         Paint var58 = this.mColorGradientPaint;
         var1.drawPath(var57, var58);
      }

      byte var62 = 0;
      if(var62 < var7) {
         this.mRect.left = 0;
         Rect var64 = this.mRect;
         var64.right = var7;
         this.mPaint.setColor(-6250336);
         Rect var66 = this.mRect;
         Paint var67 = this.mPaint;
         var1.drawRect(var66, var67);
         int var71 = var7 - 0;
         var2 -= var71;
         var3 = var7;
      }

      if(var3 < var11) {
         this.mRect.left = var3;
         Rect var75 = this.mRect;
         var75.right = var11;
         this.mPaint.setColor(-6250336);
         Rect var77 = this.mRect;
         Paint var78 = this.mPaint;
         var1.drawRect(var77, var78);
         int var82 = var11 - var3;
         var2 -= var82;
         var3 = var11;
      }

      int var83 = var3 + var2;
      if(var3 < var83) {
         this.mRect.left = var3;
         Rect var86 = this.mRect;
         var86.right = var83;
         this.mPaint.setColor(-6242144);
         Rect var88 = this.mRect;
         Paint var89 = this.mPaint;
         var1.drawRect(var88, var89);
      }
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      this.updateIndicator();
   }

   public void setRatios(float var1, float var2, float var3) {
      this.mRedRatio = var1;
      this.mYellowRatio = var2;
      this.mGreenRatio = var3;
      this.invalidate();
   }

   public void setShowingGreen(boolean var1) {
      if(this.mShowingGreen != var1) {
         this.mShowingGreen = var1;
         this.updateIndicator();
         this.invalidate();
      }
   }
}

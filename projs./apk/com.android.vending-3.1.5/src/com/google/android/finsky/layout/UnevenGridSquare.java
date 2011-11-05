package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class UnevenGridSquare extends RelativeLayout {

   private Paint mReflectionSeparatorPaint;


   public UnevenGridSquare(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public UnevenGridSquare(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public UnevenGridSquare(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.setWillNotDraw((boolean)0);
      Paint var4 = new Paint();
      this.mReflectionSeparatorPaint = var4;
      this.mReflectionSeparatorPaint.setColor(-16777216);
      this.mReflectionSeparatorPaint.setAntiAlias((boolean)0);
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      ImageView var2 = (ImageView)this.findViewById(2131755317);
      Drawable var3 = var2.getDrawable();
      if(var3 != null) {
         int var4 = var2.getBottom();
         int var5 = var2.getHeight();
         int var6 = var1.save();
         float var7 = (float)(var4 * 2 + 1);
         var1.translate(0.0F, var7);
         float var8 = (float)var5;
         float var9 = (float)var3.getIntrinsicHeight();
         float var10 = var8 / var9;
         float var11 = -var10;
         var1.scale(var10, var11);
         var3.draw(var1);
         var1.restore();
         float var12 = (float)var4;
         float var13 = (float)this.getWidth();
         float var14 = (float)var4;
         Paint var15 = this.mReflectionSeparatorPaint;
         var1.drawLine(0.0F, var12, var13, var14, var15);
      }
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      ImageView var6 = (ImageView)this.findViewById(2131755317);
      View var7 = this.findViewById(2131755318);
      RatingBar var8 = (RatingBar)this.findViewById(2131755037);
      TextView var9 = (TextView)this.findViewById(2131755019);
      int var10 = this.getWidth();
      int var11 = this.getHeight();
      int var12 = var6.getMeasuredHeight();
      var6.layout(0, 0, var10, var12);
      int var13 = var7.getMeasuredHeight();
      int var14 = var11 - var13;
      var7.layout(0, var14, var10, var11);
      LayoutParams var15 = (LayoutParams)var8.getLayoutParams();
      int var16 = var15.leftMargin;
      int var17 = var8.getMeasuredHeight();
      int var18 = var11 - var17;
      int var19 = var15.bottomMargin;
      int var20 = var18 - var19;
      int var21 = var15.leftMargin;
      int var22 = var8.getMeasuredWidth();
      int var23 = var21 + var22;
      int var24 = var15.bottomMargin;
      int var25 = var11 - var24;
      var8.layout(var16, var20, var23, var25);
      LayoutParams var26 = (LayoutParams)var9.getLayoutParams();
      int var27 = var9.getMeasuredWidth();
      int var28 = var10 - var27;
      int var29 = var26.rightMargin;
      int var30 = var28 - var29;
      int var31 = var9.getMeasuredHeight();
      int var32 = var11 - var31;
      int var33 = var26.bottomMargin;
      int var34 = var32 - var33;
      int var35 = var26.rightMargin;
      int var36 = var10 - var35;
      int var37 = var26.bottomMargin;
      int var38 = var11 - var37;
      var9.layout(var30, var34, var36, var38);
   }

   protected void onMeasure(int var1, int var2) {
      ImageView var3 = (ImageView)this.findViewById(2131755317);
      View var4 = this.findViewById(2131755318);
      RatingBar var5 = (RatingBar)this.findViewById(2131755037);
      TextView var6 = (TextView)this.findViewById(2131755019);
      Drawable var7 = var3.getDrawable();
      if(var7 == null) {
         int var8 = MeasureSpec.makeMeasureSpec(0, 1073741824);
         var3.measure(var1, var8);
         var4.measure(var1, var8);
      } else {
         int var11 = MeasureSpec.getSize(var1);
         float var12 = (float)var7.getIntrinsicWidth();
         float var13 = (float)var11;
         float var14 = var12 / var13;
         int var15 = (int)((float)var7.getIntrinsicHeight() / var14);
         int var16 = MeasureSpec.makeMeasureSpec(var15, 1073741824);
         var3.measure(var1, var16);
         int var17 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(var2) - var15, 1073741824);
         var4.measure(var1, var17);
      }

      var5.measure(0, 0);
      var6.measure(0, 0);
      int var9 = MeasureSpec.getSize(var1);
      int var10 = MeasureSpec.getSize(var2);
      this.setMeasuredDimension(var9, var10);
   }
}

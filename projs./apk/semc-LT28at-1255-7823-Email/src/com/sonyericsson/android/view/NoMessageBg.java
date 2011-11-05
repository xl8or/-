package com.sonyericsson.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class NoMessageBg extends RelativeLayout {

   private Bitmap mBg;
   private Paint mPaint;


   public NoMessageBg(Context var1) {
      super(var1);
   }

   public NoMessageBg(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private void drawBgPattern(Canvas var1) {
      if(this.mBg == null) {
         Bitmap var2 = ((BitmapDrawable)this.getResources().getDrawable(2130837517)).getBitmap();
         this.mBg = var2;
         Paint var3 = new Paint();
         this.mPaint = var3;
         Paint var4 = this.mPaint;
         Bitmap var5 = this.mBg;
         TileMode var6 = TileMode.REPEAT;
         TileMode var7 = TileMode.REPEAT;
         BitmapShader var8 = new BitmapShader(var5, var6, var7);
         var4.setShader(var8);
         this.mPaint.setColor(-1);
         Paint var10 = this.mPaint;
         Style var11 = Style.FILL;
         var10.setStyle(var11);
      }

      float var12 = (float)this.getWidth();
      float var13 = (float)this.getHeight();
      Paint var14 = this.mPaint;
      float var16 = 0.0F;
      var1.drawRect(0.0F, var16, var12, var13, var14);
   }

   protected void dispatchDraw(Canvas var1) {
      this.drawBgPattern(var1);
      super.dispatchDraw(var1);
   }
}

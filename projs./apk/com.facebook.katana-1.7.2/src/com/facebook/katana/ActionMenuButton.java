package com.facebook.katana;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class ActionMenuButton extends TextView {

   private static final int CORNER_RADIUS = 8;
   private static final int PADDING_H = 5;
   private static final int PADDING_V = 1;
   private Paint mPaint;
   private final RectF mRect;


   public ActionMenuButton(Context var1) {
      super(var1);
      RectF var2 = new RectF();
      this.mRect = var2;
      this.init();
   }

   public ActionMenuButton(Context var1, AttributeSet var2) {
      super(var1, var2);
      RectF var3 = new RectF();
      this.mRect = var3;
      this.init();
   }

   public ActionMenuButton(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      RectF var4 = new RectF();
      this.mRect = var4;
      this.init();
   }

   private void init() {
      this.setFocusable((boolean)1);
      this.setPadding(5, 0, 5, 1);
      Paint var1 = new Paint(1);
      this.mPaint = var1;
      Paint var2 = this.mPaint;
      int var3 = this.getContext().getResources().getColor(2131165190);
      var2.setColor(var3);
   }

   public void draw(Canvas var1) {
      Layout var2 = this.getLayout();
      RectF var3 = this.mRect;
      int var4 = this.getCompoundPaddingLeft();
      int var5 = this.getExtendedPaddingTop();
      float var6 = (float)var4;
      float var7 = var2.getLineLeft(0);
      float var8 = var6 + var7 - 5.0F;
      float var9 = (float)(var2.getLineTop(0) + var5 - 1);
      float var10 = (float)var4;
      float var11 = var2.getLineRight(0);
      float var12 = var10 + var11 + 5.0F;
      int var13 = this.getScrollX();
      int var14 = this.getRight();
      int var15 = var13 + var14;
      int var16 = this.getLeft();
      float var17 = (float)(var15 - var16);
      float var18 = Math.min(var12, var17);
      float var19 = (float)(var2.getLineBottom(0) + var5 + 1);
      var3.set(var8, var9, var18, var19);
      Paint var20 = this.mPaint;
      var1.drawRoundRect(var3, 8.0F, 8.0F, var20);
      super.draw(var1);
   }

   protected void drawableStateChanged() {
      this.invalidate();
      super.drawableStateChanged();
   }
}

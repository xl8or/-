package com.facebook.katana.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.util.AttributeSet;
import android.widget.Button;

public class ResizableFontButton extends Button {

   private static final String ELLIPSIS = "…";
   private static final Canvas HELPER_CANVAS = new Canvas();
   private static final float MIN_TEXT_SIZE = 8.0F;
   private static final float TEXT_RESIZE_STEP = 1.0F;
   private final float SCALE;
   private boolean mAddEllipsis;
   private boolean mForceResize;
   private float mMinTextSize;
   private float mSpacingAdd;
   private float mSpacingMult;
   private float mTextSize;


   public ResizableFontButton(Context var1) {
      super(var1);
      float var2 = this.getContext().getResources().getDisplayMetrics().density;
      this.SCALE = var2;
      this.mForceResize = (boolean)0;
      this.mMinTextSize = 8.0F;
      this.mSpacingMult = 1.0F;
      this.mSpacingAdd = 0.0F;
      this.mAddEllipsis = (boolean)1;
      float var3 = this.getTextSizeInDip();
      this.mTextSize = var3;
   }

   public ResizableFontButton(Context var1, AttributeSet var2) {
      super(var1, var2);
      float var3 = this.getContext().getResources().getDisplayMetrics().density;
      this.SCALE = var3;
      this.mForceResize = (boolean)0;
      this.mMinTextSize = 8.0F;
      this.mSpacingMult = 1.0F;
      this.mSpacingAdd = 0.0F;
      this.mAddEllipsis = (boolean)1;
      float var4 = this.getTextSizeInDip();
      this.mTextSize = var4;
   }

   public ResizableFontButton(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      float var4 = this.getContext().getResources().getDisplayMetrics().density;
      this.SCALE = var4;
      this.mForceResize = (boolean)0;
      this.mMinTextSize = 8.0F;
      this.mSpacingMult = 1.0F;
      this.mSpacingAdd = 0.0F;
      this.mAddEllipsis = (boolean)1;
      float var5 = this.getTextSizeInDip();
      this.mTextSize = var5;
   }

   private int diptoPixel(float var1) {
      return (int)(this.SCALE * var1 + 0.5F);
   }

   private int getTextHeight(CharSequence var1, TextPaint var2, float var3) {
      float var4 = (float)this.diptoPixel(var3);
      var2.setTextSize(var4);
      Rect var5 = new Rect();
      String var6 = var1.toString();
      int var7 = var1.length();
      var2.getTextBounds(var6, 0, var7, var5);
      return var5.bottom;
   }

   private float getTextSizeInDip() {
      float var1 = this.getTextSize();
      return this.pixelToDip(var1);
   }

   private float getTextWidth(CharSequence var1, TextPaint var2, float var3) {
      float var4 = (float)this.diptoPixel(var3);
      var2.setTextSize(var4);
      int var5 = var1.length();
      return var2.measureText(var1, 0, var5);
   }

   private float pixelToDip(float var1) {
      float var2 = this.SCALE;
      return var1 / var2;
   }

   private void resetTextSize() {
      float var1 = this.mTextSize;
      super.setTextSize(1, var1);
   }

   private void resizeText() {
      int var1 = this.getWidth();
      int var2 = this.getCompoundPaddingLeft();
      int var3 = var1 - var2;
      int var4 = this.getCompoundPaddingRight();
      int var5 = var3 - var4;
      int var6 = this.getHeight();
      int var7 = this.getCompoundPaddingTop();
      int var8 = var6 - var7;
      int var9 = this.getCompoundPaddingBottom();
      int var10 = var8 - var9;
      CharSequence var11 = this.getText();
      if(var11 != null) {
         if(var11.length() != 0) {
            if(var10 > 0) {
               if(var5 > 0) {
                  TextPaint var12 = this.getPaint();
                  TextPaint var13 = new TextPaint(var12);
                  float var14 = this.mTextSize;
                  float var15 = this.mMinTextSize;
                  float var16 = Math.max(var14, var15);
                  float var21 = (float)this.getTextHeight(var11, var13, var16);

                  while(true) {
                     float var22 = (float)var10;
                     if(var21 <= var22) {
                        break;
                     }

                     float var23 = this.mMinTextSize;
                     if(var16 <= var23) {
                        break;
                     }

                     float var24 = var16 - 1.0F;
                     float var25 = this.mMinTextSize;
                     var16 = Math.max(var24, var25);
                     var21 = (float)this.getTextHeight(var11, var13, var16);
                  }

                  float var34 = this.getTextWidth(var11, var13, var16);

                  while(true) {
                     float var35 = (float)var5;
                     if(var34 <= var35) {
                        break;
                     }

                     float var36 = this.mMinTextSize;
                     if(var16 <= var36) {
                        break;
                     }

                     float var37 = var16 - 1.0F;
                     float var38 = this.mMinTextSize;
                     var16 = Math.max(var37, var38);
                     var34 = this.getTextWidth(var11, var13, var16);
                  }

                  if(this.mAddEllipsis) {
                     float var43 = this.mMinTextSize;
                     if(var16 <= var43) {
                        label69: {
                           float var44 = (float)var5;
                           if(var34 <= var44) {
                              float var45 = (float)var10;
                              if(var21 <= var45) {
                                 break label69;
                              }
                           }

                           Alignment var46 = Alignment.ALIGN_NORMAL;
                           float var47 = this.mSpacingMult;
                           float var48 = this.mSpacingAdd;
                           StaticLayout var49 = new StaticLayout(var11, var13, var5, var46, var47, var48, (boolean)0);
                           Canvas var50 = HELPER_CANVAS;
                           var49.draw(var50);
                           int var51 = var49.getLineForVertical(var10) - 1;
                           int var52 = var49.getLineStart(var51);
                           int var53 = var49.getLineEnd(var51);
                           float var54 = var49.getLineWidth(var51);
                           float var55 = var13.measureText("…");

                           while(true) {
                              float var56 = (float)var5;
                              float var57 = var54 + var55;
                              if(var56 >= var57) {
                                 StringBuilder var62 = new StringBuilder();
                                 CharSequence var63 = var11.subSequence(0, var53);
                                 String var64 = var62.append(var63).append("…").toString();
                                 this.setText(var64);
                                 break;
                              }

                              String var61 = var11.subSequence(var52, var53).toString();
                              var54 = var13.measureText(var61);
                              var53 += -1;
                           }
                        }
                     }
                  }

                  byte var68 = 1;
                  super.setTextSize(var68, var16);
                  float var70 = this.mSpacingAdd;
                  float var71 = this.mSpacingMult;
                  this.setLineSpacing(var70, var71);
                  byte var75 = 0;
                  this.mForceResize = (boolean)var75;
               }
            }
         }
      }
   }

   public float getMinTextSize() {
      return this.mMinTextSize;
   }

   public boolean isAddEllipsis() {
      return this.mAddEllipsis;
   }

   protected void onDraw(Canvas var1) {
      if(this.mForceResize) {
         this.resizeText();
      }

      super.onDraw(var1);
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      if(var1 != var3 || var2 != var4) {
         this.mForceResize = (boolean)1;
      }
   }

   protected void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
      this.mForceResize = (boolean)1;
      this.resetTextSize();
   }

   public void setAddEllipsis(boolean var1) {
      this.mAddEllipsis = var1;
   }

   public void setLineSpacing(float var1, float var2) {
      super.setLineSpacing(var1, var2);
      this.mSpacingMult = var2;
      this.mSpacingAdd = var1;
   }

   public void setMinTextSize(float var1) {
      this.mMinTextSize = var1;
      this.mForceResize = (boolean)1;
      this.requestLayout();
      this.invalidate();
   }

   public void setTextSize(float var1) {
      super.setTextSize(var1);
      float var2 = this.getTextSizeInDip();
      this.mTextSize = var2;
   }

   public void setTextSize(int var1, float var2) {
      super.setTextSize(var1, var2);
      float var3 = this.getTextSizeInDip();
      this.mTextSize = var3;
   }
}

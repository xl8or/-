package com.android.settings;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class ThemeGallery extends Gallery {

   static final int ALPHA_VALUE_120 = 120;
   static final int ALPHA_VALUE_FOR_SECOND_ITEM = 50;
   static final int ALPHA_VALUE_MAX = 255;
   static final int ALPHA_VALUE_MIN = 0;
   static final float AXIS_OFFSET_15 = 15.0F;
   private static final boolean DEBUG_THEME = false;
   static final float DEFAULT_Z_AXIS_OFFSET = 100.0F;
   private static final String TAG = "ThemeGallery";
   static final float VALUE_ZERO_FLOAT = 0.0F;
   static final int VALUE_ZERO_INT = 0;
   static final float X_AXIS_OFFSET_FOR_FIRST_ITEM = 50.0F;
   static final float X_AXIS_OFFSET_FOR_SECOND_ITEM = 10.0F;
   static final int ZOOM_VALUE_FOR_FIRST_ITEM = 65386;
   static final int ZOOM_VALUE_FOR_SECOND_ITEM = 206;
   private boolean bcurrentmiddle;
   private int indexdistance;
   private Camera mCamera;
   private int mCoveflowCenter;
   private int mMaxRotationAngle;
   private int mMaxZoom;
   private int selectindex;


   public ThemeGallery(Context var1) {
      super(var1);
      Camera var2 = new Camera();
      this.mCamera = var2;
      this.mMaxRotationAngle = '\uffdd';
      this.mMaxZoom = 120;
      this.selectindex = -1;
      this.bcurrentmiddle = (boolean)0;
      this.indexdistance = -1;
      this.setStaticTransformationsEnabled((boolean)1);
   }

   public ThemeGallery(Context var1, AttributeSet var2) {
      super(var1, var2);
      Camera var3 = new Camera();
      this.mCamera = var3;
      this.mMaxRotationAngle = '\uffdd';
      this.mMaxZoom = 120;
      this.selectindex = -1;
      this.bcurrentmiddle = (boolean)0;
      this.indexdistance = -1;
      this.setStaticTransformationsEnabled((boolean)1);
   }

   public ThemeGallery(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      Camera var4 = new Camera();
      this.mCamera = var4;
      this.mMaxRotationAngle = '\uffdd';
      this.mMaxZoom = 120;
      this.selectindex = -1;
      this.bcurrentmiddle = (boolean)0;
      this.indexdistance = -1;
      this.setStaticTransformationsEnabled((boolean)1);
   }

   private int getCenterOfCoverflow() {
      int var1 = this.getWidth();
      int var2 = this.getPaddingLeft();
      int var3 = var1 - var2;
      int var4 = this.getPaddingRight();
      int var5 = (var3 - var4) / 2;
      int var6 = this.getPaddingLeft();
      return var5 + var6;
   }

   private static int getCenterOfView(View var0) {
      int var1 = var0.getLeft();
      int var2 = var0.getWidth() / 2;
      return var1 + var2;
   }

   private void transformImageBitmap(ImageView var1, Transformation var2, int var3) {
      this.mCamera.save();
      Matrix var4 = var2.getMatrix();
      int var5 = var1.getLayoutParams().height;
      int var6 = var1.getLayoutParams().width;
      int var7 = Math.abs(var3);
      float var8 = 15.0F;
      float var9 = 0.0F;
      if(!this.bcurrentmiddle) {
         if(this.indexdistance != 1 && this.indexdistance != -1) {
            if(this.indexdistance != 2 && this.indexdistance != -1) {
               if(this.indexdistance <= 2 && this.indexdistance >= -1) {
                  this.mMaxZoom = '\uff6a';
                  var1.setAlpha(255);
               } else {
                  var1.setAlpha(0);
               }
            } else {
               var8 = 0.0F;
               this.mMaxZoom = '\uffce';
               var1.setAlpha(50);
               float var25 = (float)(-this.indexdistance);
               var9 = 10.0F * var25;
            }
         } else {
            var8 = 15.0F;
            this.mMaxZoom = '\uff6a';
            var1.setAlpha(120);
            float var10 = (float)(-this.indexdistance);
            var9 = 50.0F * var10;
         }
      } else {
         this.mMaxZoom = '\uff6a';
         var1.setAlpha(255);
      }

      this.mCamera.translate(var9, var8, 100.0F);
      int var11 = this.mMaxRotationAngle;
      if(var7 > var11) {
         double var12 = (double)this.mMaxZoom;
         double var14 = (double)var7 * 2.5D;
         float var16 = (float)(var12 + var14);
         this.mCamera.translate(0.0F, 0.0F, var16);
      }

      Camera var17 = this.mCamera;
      float var18 = (float)var3;
      var17.rotateY(var18);
      this.mCamera.getMatrix(var4);
      float var19 = (float)(-(var6 / 2));
      float var20 = (float)(-(var5 / 2));
      var4.preTranslate(var19, var20);
      float var22 = (float)(var6 / 2);
      float var23 = (float)(var5 / 2);
      var4.postTranslate(var22, var23);
      this.mCamera.restore();
   }

   protected boolean getChildStaticTransformation(View var1, Transformation var2) {
      int var3 = getCenterOfView(var1);
      int var4 = var1.getWidth();
      int var5 = this.getSelectedItemPosition();
      int var6 = this.getPositionForView(var1);
      int var7 = var5 - var6;
      this.indexdistance = var7;
      var2.clear();
      int var8 = Transformation.TYPE_MATRIX;
      var2.setTransformationType(var8);
      int var9 = this.mCoveflowCenter;
      if(var3 == var9) {
         this.bcurrentmiddle = (boolean)1;
         int var10 = this.getPositionForView(var1);
         this.selectindex = var10;
         ImageView var11 = (ImageView)var1;
         this.transformImageBitmap(var11, var2, 0);
      } else {
         float var12 = (float)(this.mCoveflowCenter - var3);
         float var13 = (float)var4;
         float var14 = var12 / var13;
         float var15 = (float)this.mMaxRotationAngle;
         int var16 = (int)(var14 * var15);
         this.bcurrentmiddle = (boolean)0;
         ImageView var17 = (ImageView)var1;
         this.transformImageBitmap(var17, var2, var16);
      }

      return true;
   }

   public int getMaxRotationAngle() {
      return this.mMaxRotationAngle;
   }

   public int getMaxZoom() {
      return this.mMaxZoom;
   }

   public int getSelectedthemeIndex() {
      return this.selectindex;
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      int var5 = this.getCenterOfCoverflow();
      this.mCoveflowCenter = var5;
      super.onSizeChanged(var1, var2, var3, var4);
   }

   public void setMaxRotationAngle(int var1) {
      this.mMaxRotationAngle = var1;
   }

   public void setMaxZoom(int var1) {
      this.mMaxZoom = var1;
   }
}

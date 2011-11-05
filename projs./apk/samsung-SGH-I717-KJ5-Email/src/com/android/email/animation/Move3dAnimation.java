package com.android.email.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Move3dAnimation extends Animation {

   private Camera mCamera;
   private final float mCenterX;
   private final float mCenterY;
   private final float mDepthZ;
   private final float mMoveX;
   private final float mMoveY;
   private final float mMoveZ;
   private final boolean mReverse;


   public Move3dAnimation(float var1, float var2, float var3, float var4, float var5, float var6, boolean var7) {
      this.mCenterX = var1;
      this.mCenterY = var2;
      this.mDepthZ = var3;
      this.mMoveX = var4;
      this.mMoveY = var5;
      this.mMoveZ = var6;
      this.mReverse = var7;
   }

   protected void applyTransformation(float var1, Transformation var2) {
      double var3;
      if(this.mReverse) {
         var3 = 0.0D;
      } else {
         double var10 = (double)var1;
         double var12 = 2.6179938779914944D * var10;
         var3 = 2.0943951023931953D + var12;
      }

      Camera var5 = this.mCamera;
      Matrix var6 = var2.getMatrix();
      var5.save();
      if(this.mReverse) {
         float var7 = this.mCenterX * var1;
         float var8 = this.mCenterY * var1;
         float var9 = this.mDepthZ * var1;
         var5.translate(var7, var8, var9);
      } else {
         float var14 = this.mCenterX;
         float var15 = this.mMoveX * var1;
         float var16 = (float)Math.cos(var3);
         float var17 = var15 * var16;
         float var18 = var14 + var17;
         float var19 = this.mCenterY;
         float var20 = this.mMoveY * var1;
         float var21 = (float)Math.sin(var3);
         float var22 = var20 * var21;
         float var23 = var19 - var22;
         float var24 = this.mDepthZ;
         var5.translate(var18, var23, var24);
      }

      var5.getMatrix(var6);
      var5.restore();
   }

   public void initialize(int var1, int var2, int var3, int var4) {
      super.initialize(var1, var2, var3, var4);
      Camera var5 = new Camera();
      this.mCamera = var5;
   }
}

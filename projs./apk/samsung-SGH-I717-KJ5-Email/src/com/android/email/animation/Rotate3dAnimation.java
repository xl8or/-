package com.android.email.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {

   private Camera mCamera;
   private final float mCenterX;
   private final float mCenterY;
   private final float mDepthZ;
   private final float mFromDegrees;
   private final boolean mReverse;
   private final float mToDegrees;


   public Rotate3dAnimation(float var1, float var2, float var3, float var4, float var5, boolean var6) {
      this.mFromDegrees = var1;
      this.mToDegrees = var2;
      this.mCenterX = var3;
      this.mCenterY = var4;
      this.mDepthZ = var5;
      this.mReverse = var6;
   }

   protected void applyTransformation(float var1, Transformation var2) {
      float var3 = this.mFromDegrees;
      float var4 = (this.mToDegrees - var3) * var1;
      float var5 = var3 + var4;
      float var6 = this.mCenterX;
      float var7 = this.mCenterY;
      Camera var8 = this.mCamera;
      Matrix var9 = var2.getMatrix();
      var8.save();
      if(this.mReverse) {
         float var10 = this.mDepthZ * var1;
         var8.translate(0.0F, 0.0F, var10);
      } else {
         float var15 = this.mDepthZ;
         float var16 = 1.0F - var1;
         float var17 = var15 * var16;
         var8.translate(0.0F, 0.0F, var17);
      }

      var8.rotateX(var5);
      var8.getMatrix(var9);
      var8.restore();
      float var11 = -var6;
      float var12 = -var7;
      var9.preTranslate(var11, var12);
      var9.postTranslate(var6, var7);
   }

   public void initialize(int var1, int var2, int var3, int var4) {
      super.initialize(var1, var2, var3, var4);
      Camera var5 = new Camera();
      this.mCamera = var5;
   }
}

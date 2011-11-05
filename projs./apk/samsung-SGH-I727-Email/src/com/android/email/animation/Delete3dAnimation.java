package com.android.email.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Delete3dAnimation extends Animation {

   private Camera mCamera;
   private final float mCenterX;
   private final float mCenterY;
   private final float mDepthZ;
   private final float mMoveX;
   private final float mMoveY;
   private final float mMoveZ;
   private final boolean mReverse;


   public Delete3dAnimation(float var1, float var2, float var3, float var4, float var5, float var6, boolean var7) {
      this.mCenterX = var1;
      this.mCenterY = var2;
      this.mDepthZ = var3;
      this.mMoveX = var4;
      this.mMoveY = var5;
      this.mMoveZ = var6;
      this.mReverse = var7;
   }

   protected void applyTransformation(float var1, Transformation var2) {
      Camera var3 = this.mCamera;
      if(!this.mReverse) {
         float var4 = 1.0F - var1;
         var2.setAlpha(var4);
      }

      Matrix var5 = var2.getMatrix();
      var3.save();
      if(this.mReverse) {
         float var6 = this.mCenterX * var1;
         float var7 = this.mCenterY * var1;
         float var8 = this.mDepthZ * var1;
         var3.translate(var6, var7, var8);
      } else {
         float var9 = this.mCenterX;
         float var10 = this.mMoveX * var1;
         float var11 = var9 + var10;
         float var12 = this.mCenterY;
         float var13 = this.mMoveY * var1;
         float var14 = var12 + var13;
         float var15 = this.mDepthZ;
         float var16 = this.mMoveZ * var1;
         float var17 = var15 + var16;
         var3.translate(var11, var14, var17);
      }

      var3.getMatrix(var5);
      var3.restore();
   }

   public void initialize(int var1, int var2, int var3, int var4) {
      super.initialize(var1, var2, var3, var4);
      Camera var5 = new Camera();
      this.mCamera = var5;
   }
}

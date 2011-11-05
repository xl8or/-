package com.facebook.katana;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class RotateBitmap {

   public static final String TAG = "RotateBitmap";
   private Bitmap mBitmap;
   private int mRotation;


   public RotateBitmap(Bitmap var1) {
      this.mBitmap = var1;
      this.mRotation = 0;
   }

   public RotateBitmap(Bitmap var1, int var2) {
      this.mBitmap = var1;
      int var3 = var2 % 360;
      this.mRotation = var3;
   }

   public Bitmap getBitmap() {
      return this.mBitmap;
   }

   public int getHeight() {
      int var1;
      if(this.isOrientationChanged()) {
         var1 = this.mBitmap.getWidth();
      } else {
         var1 = this.mBitmap.getHeight();
      }

      return var1;
   }

   public Matrix getRotateMatrix() {
      Matrix var1 = new Matrix();
      if(this.mRotation != 0) {
         int var2 = this.mBitmap.getWidth() / 2;
         int var3 = this.mBitmap.getHeight() / 2;
         float var4 = (float)(-var2);
         float var5 = (float)(-var3);
         var1.preTranslate(var4, var5);
         float var7 = (float)this.mRotation;
         var1.postRotate(var7);
         float var9 = (float)(this.getWidth() / 2);
         float var10 = (float)(this.getHeight() / 2);
         var1.postTranslate(var9, var10);
      }

      return var1;
   }

   public int getRotation() {
      return this.mRotation;
   }

   public int getWidth() {
      int var1;
      if(this.isOrientationChanged()) {
         var1 = this.mBitmap.getHeight();
      } else {
         var1 = this.mBitmap.getWidth();
      }

      return var1;
   }

   public boolean isOrientationChanged() {
      boolean var1;
      if(this.mRotation / 90 % 2 != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void recycle() {
      if(this.mBitmap != null) {
         this.mBitmap.recycle();
         this.mBitmap = null;
      }
   }

   public void setBitmap(Bitmap var1) {
      this.mBitmap = var1;
   }

   public void setRotation(int var1) {
      this.mRotation = var1;
   }
}

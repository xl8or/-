package com.facebook.katana.util;

import android.content.Context;
import android.view.MotionEvent;

public class ScaleGestureDetector {

   private static final float PRESSURE_THRESHOLD = 0.67F;
   private final Context mContext;
   private MotionEvent mCurrEvent;
   private float mCurrFingerDiffX;
   private float mCurrFingerDiffY;
   private float mCurrLen;
   private float mCurrPressure;
   private float mFocusX;
   private float mFocusY;
   private boolean mGestureInProgress;
   private final ScaleGestureDetector.OnScaleGestureListener mListener;
   private MotionEvent mPrevEvent;
   private float mPrevFingerDiffX;
   private float mPrevFingerDiffY;
   private float mPrevLen;
   private float mPrevPressure;
   private float mScaleFactor;
   private long mTimeDelta;


   public ScaleGestureDetector(Context var1, ScaleGestureDetector.OnScaleGestureListener var2) {
      this.mContext = var1;
      this.mListener = var2;
   }

   private void reset() {
      if(this.mPrevEvent != null) {
         this.mPrevEvent.recycle();
         this.mPrevEvent = null;
      }

      if(this.mCurrEvent != null) {
         this.mCurrEvent.recycle();
         this.mCurrEvent = null;
      }
   }

   private void setContext(MotionEvent var1) {
      if(this.mCurrEvent != null) {
         this.mCurrEvent.recycle();
      }

      MotionEvent var2 = MotionEvent.obtain(var1);
      this.mCurrEvent = var2;
      float var3 = -1.0F;
      this.mCurrLen = var3;
      float var4 = -1.0F;
      this.mPrevLen = var4;
      float var5 = -1.0F;
      this.mScaleFactor = var5;
      MotionEvent var6 = this.mPrevEvent;
      byte var8 = 0;
      float var9 = var6.getX(var8);
      byte var11 = 0;
      float var12 = var6.getY(var11);
      byte var14 = 1;
      float var15 = var6.getX(var14);
      byte var17 = 1;
      float var18 = var6.getY(var17);
      byte var20 = 0;
      float var21 = var1.getX(var20);
      byte var23 = 0;
      float var24 = var1.getY(var23);
      byte var26 = 1;
      float var27 = var1.getX(var26);
      byte var29 = 1;
      float var30 = var1.getY(var29);
      float var31 = var15 - var9;
      float var32 = var18 - var12;
      float var33 = var27 - var21;
      float var34 = var30 - var24;
      this.mPrevFingerDiffX = var31;
      this.mPrevFingerDiffY = var32;
      this.mCurrFingerDiffX = var33;
      this.mCurrFingerDiffY = var34;
      float var39 = 0.5F * var33 + var21;
      this.mFocusX = var39;
      float var40 = 0.5F * var34 + var24;
      this.mFocusY = var40;
      long var41 = var1.getEventTime();
      long var43 = var6.getEventTime();
      long var45 = var41 - var43;
      this.mTimeDelta = var45;
      byte var48 = 0;
      float var49 = var1.getPressure(var48);
      byte var51 = 1;
      float var52 = var1.getPressure(var51);
      float var53 = var49 + var52;
      this.mCurrPressure = var53;
      byte var55 = 0;
      float var56 = var6.getPressure(var55);
      byte var58 = 1;
      float var59 = var6.getPressure(var58);
      float var60 = var56 + var59;
      this.mPrevPressure = var60;
   }

   public float getCurrentSpan() {
      if(this.mCurrLen == -1.0F) {
         float var1 = this.mCurrFingerDiffX;
         float var2 = this.mCurrFingerDiffY;
         float var3 = var1 * var1;
         float var4 = var2 * var2;
         float var5 = (float)Math.sqrt((double)(var3 + var4));
         this.mCurrLen = var5;
      }

      return this.mCurrLen;
   }

   public long getEventTime() {
      return this.mCurrEvent.getEventTime();
   }

   public float getFocusX() {
      return this.mFocusX;
   }

   public float getFocusY() {
      return this.mFocusY;
   }

   public float getPreviousSpan() {
      if(this.mPrevLen == -1.0F) {
         float var1 = this.mPrevFingerDiffX;
         float var2 = this.mPrevFingerDiffY;
         float var3 = var1 * var1;
         float var4 = var2 * var2;
         float var5 = (float)Math.sqrt((double)(var3 + var4));
         this.mPrevLen = var5;
      }

      return this.mPrevLen;
   }

   public float getScaleFactor() {
      if(this.mScaleFactor == -1.0F) {
         float var1 = this.getCurrentSpan();
         float var2 = this.getPreviousSpan();
         float var3 = var1 / var2;
         this.mScaleFactor = var3;
      }

      return this.mScaleFactor;
   }

   public long getTimeDelta() {
      return this.mTimeDelta;
   }

   public boolean isInProgress() {
      return this.mGestureInProgress;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      int var2 = var1.getAction();
      if(!this.mGestureInProgress) {
         if((var2 == 5 || var2 == 261) && var1.getPointerCount() >= 2) {
            this.reset();
            MotionEvent var3 = MotionEvent.obtain(var1);
            this.mPrevEvent = var3;
            this.mTimeDelta = 0L;
            this.setContext(var1);
            boolean var4 = this.mListener.onScaleBegin(this);
            this.mGestureInProgress = var4;
         }
      } else {
         switch(var2) {
         case 2:
            this.setContext(var1);
            float var5 = this.mCurrPressure;
            float var6 = this.mPrevPressure;
            if(var5 / var6 > 0.67F && this.mListener.onScale(this)) {
               this.mPrevEvent.recycle();
               MotionEvent var7 = MotionEvent.obtain(var1);
               this.mPrevEvent = var7;
            }
            break;
         case 3:
            this.mListener.onScaleEnd(this);
            this.mGestureInProgress = (boolean)0;
            this.reset();
            break;
         case 6:
         case 262:
            this.setContext(var1);
            byte var8;
            if(('\uff00' & var2) >> 8 == 0) {
               var8 = 1;
            } else {
               var8 = 0;
            }

            float var9 = var1.getX(var8);
            this.mFocusX = var9;
            float var10 = var1.getY(var8);
            this.mFocusY = var10;
            this.mListener.onScaleEnd(this);
            this.mGestureInProgress = (boolean)0;
            this.reset();
         }
      }

      return true;
   }

   public class SimpleOnScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

      public SimpleOnScaleGestureListener() {}

      public boolean onScale(ScaleGestureDetector var1) {
         return true;
      }

      public boolean onScaleBegin(ScaleGestureDetector var1) {
         return true;
      }

      public void onScaleEnd(ScaleGestureDetector var1) {}
   }

   public interface OnScaleGestureListener {

      boolean onScale(ScaleGestureDetector var1);

      boolean onScaleBegin(ScaleGestureDetector var1);

      void onScaleEnd(ScaleGestureDetector var1);
   }
}

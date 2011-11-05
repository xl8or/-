package com.facebook.katana.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import com.facebook.katana.ui.ImageViewTouchBase;
import custom.android.Gallery;
import custom.android.ScaleGestureDetector;

public class PhotoGallery extends Gallery implements ScaleGestureDetector.OnScaleGestureListener, OnDoubleTapListener {

   private static final int DEFAULT_SPACING = 100;
   private static final float MAX_ZOOM_FOR_SWIPE = 1.5F;
   ScaleGestureDetector mScaleDetector;
   ImageViewTouchBase mView;


   public PhotoGallery(Context var1, AttributeSet var2) {
      super(var1, var2, 0);
      ScaleGestureDetector var3 = new ScaleGestureDetector(var1, this);
      this.mScaleDetector = var3;
      (new GestureDetector(this)).setOnDoubleTapListener(this);
      this.setSpacing(100);
   }

   private boolean isScrollingLeft(MotionEvent var1, MotionEvent var2) {
      float var3 = var2.getX();
      float var4 = var1.getX();
      boolean var5;
      if(var3 > var4) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public void destroy() {
      this.mScaleDetector = null;
   }

   public boolean onDoubleTap(MotionEvent var1) {
      if(this.mView.getScale() > 2.0F) {
         this.mView.zoomTo(1.0F);
      } else {
         ImageViewTouchBase var2 = this.mView;
         float var3 = var1.getX();
         float var4 = var1.getY();
         var2.zoomToPoint(3.0F, var3, var4);
      }

      return true;
   }

   public boolean onDoubleTapEvent(MotionEvent var1) {
      return false;
   }

   public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
      float var5 = this.mView.getImageRight();
      float var6 = (float)this.mView.getWidth();
      boolean var7;
      if(var5 > var6 && !this.isScrollingLeft(var1, var2)) {
         var7 = false;
      } else if(this.mView.getImageLeft() < 0.0F && this.isScrollingLeft(var1, var2)) {
         var7 = false;
      } else {
         byte var8;
         if(this.isScrollingLeft(var1, var2)) {
            var8 = 21;
         } else {
            var8 = 22;
         }

         this.onKeyDown(var8, (KeyEvent)null);
         var7 = true;
      }

      return var7;
   }

   public boolean onScale(ScaleGestureDetector var1) {
      float var2 = var1.getScaleFactor();
      float var3 = this.mView.getScale();
      float var4 = var2 * var3;
      ImageViewTouchBase var5 = this.mView;
      float var6 = var1.getFocusX();
      float var7 = var1.getFocusY();
      var5.zoomTo(var4, var6, var7);
      return true;
   }

   public boolean onScaleBegin(ScaleGestureDetector var1) {
      return true;
   }

   public void onScaleEnd(ScaleGestureDetector var1) {}

   public boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4) {
      boolean var10;
      if(this.mView.getScale() <= 1.5F) {
         float var5 = this.mView.getImageRight();
         float var6 = (float)this.mView.getWidth();
         if((var5 <= var6 || this.isScrollingLeft(var1, var2)) && (this.mView.getImageLeft() >= 0.0F || !this.isScrollingLeft(var1, var2))) {
            var10 = super.onScroll(var1, var2, var3, var4);
            return var10;
         }
      }

      ImageViewTouchBase var7 = this.mView;
      float var8 = -var3;
      float var9 = -var4;
      var7.postTranslateCenter(var8, var9);
      var10 = true;
      return var10;
   }

   public boolean onSingleTapConfirmed(MotionEvent var1) {
      return false;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      byte var2;
      if(this.getChildCount() == 0) {
         var2 = 0;
      } else {
         View var3 = this.getSelectedView();
         if(var3 == null) {
            var2 = super.onTouchEvent(var1);
         } else {
            ImageViewTouchBase var4 = (ImageViewTouchBase)((ImageViewTouchBase)var3.findViewById(2131623981));
            this.mView = var4;
            this.mScaleDetector.onTouchEvent(var1);
            byte var6 = this.mScaleDetector.isInProgress();
            if(var6 == 0) {
               var6 = super.onTouchEvent(var1);
            }

            var2 = var6;
         }
      }

      return (boolean)var2;
   }
}

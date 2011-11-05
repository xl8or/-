package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public abstract class ScrollableViewGroup extends ViewGroup {

   private static final int SMOOTH_SCROLL_DURATION = 500;
   private boolean mIsBeingDragged;
   private float[] mLastPosition;
   private final int[] mLimits;
   private int mScrollDirection;
   private Scroller mScroller;
   private int mTouchSlop;


   public ScrollableViewGroup(Context var1, AttributeSet var2) {
      super(var1, var2);
      float[] var3 = new float[]{(float)false, (float)false};
      this.mLastPosition = var3;
      int[] var4 = new int[]{-2147483647, Integer.MAX_VALUE};
      this.mLimits = var4;
      this.mScrollDirection = 0;
      this.mIsBeingDragged = (boolean)0;
      this.setFocusable((boolean)0);
      int var5 = ViewConfiguration.get(var1).getScaledTouchSlop();
      this.mTouchSlop = var5;
      Scroller var6 = new Scroller(var1);
      this.mScroller = var6;
   }

   private int clampToScrollLimits(int var1) {
      int var2 = this.mLimits[0];
      if(var1 < var2) {
         var1 = this.mLimits[0];
      } else {
         int var3 = this.mLimits[1];
         if(var1 > var3) {
            var1 = this.mLimits[1];
         }
      }

      return var1;
   }

   private boolean motionShouldStartDrag(MotionEvent var1) {
      byte var2;
      float var8;
      boolean var11;
      label29: {
         var2 = 1;
         float var3 = var1.getX();
         float var4 = this.mLastPosition[0];
         float var5 = var3 - var4;
         float var6 = var1.getY();
         float var7 = this.mLastPosition[var2];
         var8 = var6 - var7;
         float var9 = (float)this.mTouchSlop;
         if(var5 <= var9) {
            float var10 = (float)(-this.mTouchSlop);
            if(var5 >= var10) {
               var11 = false;
               break label29;
            }
         }

         var11 = true;
      }

      boolean var14;
      label24: {
         float var12 = (float)this.mTouchSlop;
         if(var8 <= var12) {
            float var13 = (float)(-this.mTouchSlop);
            if(var8 >= var13) {
               var14 = false;
               break label24;
            }
         }

         var14 = true;
      }

      if(!var11 || var14) {
         var2 = 0;
      }

      return (boolean)var2;
   }

   private boolean shouldStartDrag(MotionEvent var1) {
      boolean var2 = false;
      if(this.mIsBeingDragged) {
         this.mIsBeingDragged = (boolean)0;
      } else {
         switch(var1.getAction()) {
         case 0:
            this.updatePosition(var1);
            if(!this.mScroller.isFinished()) {
               var2 = true;
            }
         case 1:
         default:
            break;
         case 2:
            if(this.motionShouldStartDrag(var1)) {
               this.updatePosition(var1);
               this.startDrag();
               var2 = true;
            }
         }
      }

      return var2;
   }

   private void startDrag() {
      this.mIsBeingDragged = (boolean)1;
      this.mScrollDirection = 0;
      this.mScroller.abortAnimation();
   }

   private void stopDrag(boolean var1) {
      this.mIsBeingDragged = (boolean)0;
      int var2 = this.mScrollDirection;
      this.onScrollFinished(var2);
   }

   private void updatePosition(MotionEvent var1) {
      float[] var2 = this.mLastPosition;
      float var3 = var1.getX();
      var2[0] = var3;
      float[] var4 = this.mLastPosition;
      float var5 = var1.getY();
      var4[1] = var5;
   }

   private float updatePositionAndComputeDelta(MotionEvent var1) {
      float var2 = this.mLastPosition[0];
      this.updatePosition(var1);
      float var3 = this.mLastPosition[0];
      return var2 - var3;
   }

   public void computeScroll() {
      if(this.mScroller.computeScrollOffset()) {
         int var1 = this.mScroller.getCurrX();
         this.scrollTo(var1);
         this.invalidate();
         int var2 = this.mScroller.getFinalX();
         if(var1 == var2) {
            this.mScroller.abortAnimation();
         }
      }
   }

   public int getScroll() {
      return this.getScrollX();
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      return this.shouldStartDrag(var1);
   }

   protected void onScrollFinished(int var1) {}

   public boolean onTouchEvent(MotionEvent var1) {
      boolean var2 = true;
      int var3 = var1.getAction();
      if(!this.mIsBeingDragged) {
         if(!this.shouldStartDrag(var1) && var3 == 1) {
            var2 = this.performClick();
         }
      } else {
         switch(var3) {
         case 1:
         case 3:
            byte var4;
            if(var3 == 3) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            this.stopDrag((boolean)var4);
            break;
         case 2:
            float var5 = this.updatePositionAndComputeDelta(var1);
            if(var5 < -1.0F) {
               this.mScrollDirection = -1;
            } else if(var5 > 1.0F) {
               this.mScrollDirection = 1;
            }

            int var6 = this.getScroll();
            int var7 = (int)var5;
            int var8 = var6 + var7;
            this.scrollTo(var8);
         }
      }

      return var2;
   }

   protected void scrollTo(int var1) {
      int var2 = this.clampToScrollLimits(var1);
      this.scrollTo(var2, 0);
   }

   public void setScrollLimits(int var1, int var2) {
      this.mLimits[0] = var1;
      this.mLimits[1] = var2;
   }

   public void smoothScrollTo(int var1) {
      int var2 = this.clampToScrollLimits(var1);
      int var3 = this.getScroll();
      int var4 = var2 - var3;
      Scroller var5 = this.mScroller;
      int var6 = this.getScrollX();
      byte var7 = 0;
      var5.startScroll(var6, 0, var4, var7, 500);
      this.invalidate();
   }
}

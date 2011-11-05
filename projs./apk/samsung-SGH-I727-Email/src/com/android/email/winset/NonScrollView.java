package com.android.email.winset;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Scroller;
import com.android.internal.R.styleable;
import java.util.ArrayList;

public class NonScrollView extends FrameLayout {

   static final int ANIMATED_SCROLL_GAP = 250;
   private static final int INVALID_POINTER = 255;
   static final float MAX_SCROLL_FACTOR = 0.5F;
   private boolean bPreScrollState;
   private boolean bScrollBottom;
   private int mActivePointerId;
   private View mChildToScrollTo;
   private boolean mFillViewport;
   private boolean mIsBeingDragged;
   private boolean mIsLayoutDirty;
   private float mLastMotionY;
   private long mLastScroll;
   private int mMaximumVelocity;
   private int mMinimumVelocity;
   private boolean mScrollViewMovedFocus;
   private Scroller mScroller;
   private boolean mSmoothScrollingEnabled;
   private final Rect mTempRect;
   private int mTouchSlop;
   private VelocityTracker mVelocityTracker;


   public NonScrollView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public NonScrollView(Context var1, AttributeSet var2) {
      this(var1, var2, 16842880);
   }

   public NonScrollView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      Rect var4 = new Rect();
      this.mTempRect = var4;
      this.mIsLayoutDirty = (boolean)1;
      this.mChildToScrollTo = null;
      this.mIsBeingDragged = (boolean)0;
      this.mSmoothScrollingEnabled = (boolean)1;
      this.mActivePointerId = -1;
      this.bScrollBottom = (boolean)0;
      this.bPreScrollState = (boolean)0;
      this.initScrollView();
      int[] var5 = styleable.ScrollView;
      TypedArray var6 = var1.obtainStyledAttributes(var2, var5, var3, 0);
      boolean var7 = var6.getBoolean(0, (boolean)0);
      this.setFillViewport(var7);
      var6.recycle();
   }

   private boolean canScroll() {
      View var1 = this.getChildAt(0);
      boolean var7;
      if(var1 != null) {
         int var2 = var1.getHeight();
         int var3 = this.getHeight();
         int var4 = this.mPaddingTop + var2;
         int var5 = this.mPaddingBottom;
         int var6 = var4 + var5;
         if(var3 < var6) {
            var7 = true;
         } else {
            var7 = false;
         }
      } else {
         var7 = false;
      }

      return var7;
   }

   private int clamp(int var1, int var2, int var3) {
      int var4;
      if(var2 < var3 && var1 >= 0) {
         if(var2 + var1 > var3) {
            var4 = var3 - var2;
         } else {
            var4 = var1;
         }
      } else {
         var4 = 0;
      }

      return var4;
   }

   private void doScrollY(int var1) {
      if(var1 != 0) {
         if(this.mSmoothScrollingEnabled) {
            this.smoothScrollBy(0, var1);
         } else {
            this.scrollBy(0, var1);
         }
      }
   }

   private View findFocusableViewInBounds(boolean var1, int var2, int var3) {
      ArrayList var4 = this.getFocusables(2);
      View var5 = null;
      int var6 = var4.size();

      for(int var7 = 0; var7 < var6; ++var7) {
         View var8 = (View)var4.get(var7);
         int var9 = var8.getTop();
         int var10 = var8.getBottom();
         if(var2 < var10 && var9 < var3) {
            boolean var11;
            if(var2 < var9 && var10 < var3) {
               var11 = true;
            } else {
               var11 = false;
            }

            if(var5 == null) {
               var5 = var8;
            } else {
               boolean var15;
               label51: {
                  label63: {
                     if(var1) {
                        int var13 = var5.getTop();
                        if(var9 < var13) {
                           break label63;
                        }
                     }

                     if(!var1) {
                        int var14 = var5.getBottom();
                        if(var10 > var14) {
                           break label63;
                        }
                     }

                     var15 = false;
                     break label51;
                  }

                  var15 = true;
               }

               if(false) {
                  if(var11 && var15) {
                     var5 = var8;
                  }
               } else if(var11) {
                  var5 = var8;
               } else if(var15) {
                  var5 = var8;
               }
            }
         }
      }

      return var5;
   }

   private View findFocusableViewInMyBounds(boolean var1, int var2, View var3) {
      int var4 = this.getVerticalFadingEdgeLength() / 2;
      int var5 = var2 + var4;
      int var6 = this.getHeight() + var2 - var4;
      View var7;
      if(var3 != null && var3.getTop() < var6 && var3.getBottom() > var5) {
         var7 = var3;
      } else {
         var7 = this.findFocusableViewInBounds(var1, var5, var6);
      }

      return var7;
   }

   private int getScrollRange() {
      int var1 = 0;
      if(this.getChildCount() > 0) {
         int var2 = this.getChildAt(0).getHeight();
         int var3 = this.getHeight();
         int var4 = var2 - var3;
         int var5 = this.mPaddingBottom;
         int var6 = var4 - var5;
         int var7 = this.mPaddingTop;
         int var8 = var6 - var7;
         var1 = Math.max(0, var8);
      }

      return var1;
   }

   private boolean inChild(int var1, int var2) {
      boolean var9;
      if(this.getChildCount() > 0) {
         int var3 = this.mScrollY;
         View var4 = this.getChildAt(0);
         int var5 = var4.getTop() - var3;
         if(var2 >= var5) {
            int var6 = var4.getBottom() - var3;
            if(var2 < var6) {
               int var7 = var4.getLeft();
               if(var1 >= var7) {
                  int var8 = var4.getRight();
                  if(var1 < var8) {
                     var9 = true;
                     return var9;
                  }
               }
            }
         }

         var9 = false;
      } else {
         var9 = false;
      }

      return var9;
   }

   private void initScrollView() {
      Context var1 = this.getContext();
      Scroller var2 = new Scroller(var1);
      this.mScroller = var2;
      this.setFocusable((boolean)1);
      this.setDescendantFocusability(262144);
      this.setWillNotDraw((boolean)0);
      ViewConfiguration var3 = ViewConfiguration.get(this.mContext);
      int var4 = var3.getScaledTouchSlop();
      this.mTouchSlop = var4;
      int var5 = var3.getScaledMinimumFlingVelocity();
      this.mMinimumVelocity = var5;
      int var6 = var3.getScaledMaximumFlingVelocity();
      this.mMaximumVelocity = var6;
   }

   private boolean isOffScreen(View var1) {
      int var2 = this.getHeight();
      boolean var3;
      if(!this.isWithinDeltaOfScreen(var1, 0, var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private boolean isViewDescendantOf(View var1, View var2) {
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         ViewParent var4 = var1.getParent();
         if(var4 instanceof ViewGroup) {
            View var5 = (View)var4;
            if(this.isViewDescendantOf(var5, var2)) {
               var3 = true;
               return var3;
            }
         }

         var3 = false;
      }

      return var3;
   }

   private boolean isWithinDeltaOfScreen(View var1, int var2, int var3) {
      Rect var4 = this.mTempRect;
      var1.getDrawingRect(var4);
      Rect var5 = this.mTempRect;
      this.offsetDescendantRectToMyCoords(var1, var5);
      int var6 = this.mTempRect.bottom + var2;
      int var7 = this.getScrollY();
      boolean var10;
      if(var6 >= var7) {
         int var8 = this.mTempRect.top - var2;
         int var9 = this.getScrollY() + var3;
         if(var8 <= var9) {
            var10 = true;
            return var10;
         }
      }

      var10 = false;
      return var10;
   }

   private void onSecondaryPointerUp(MotionEvent var1) {
      int var2 = (var1.getAction() & '\uff00') >> 8;
      int var3 = var1.getPointerId(var2);
      int var4 = this.mActivePointerId;
      if(var3 == var4) {
         byte var5;
         if(var2 == 0) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         float var6 = var1.getY(var5);
         this.mLastMotionY = var6;
         int var7 = var1.getPointerId(var5);
         this.mActivePointerId = var7;
         if(this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
         }
      }
   }

   private boolean scrollAndFocus(int var1, int var2, int var3) {
      boolean var4 = true;
      int var5 = this.getHeight();
      int var6 = this.getScrollY();
      int var7 = var6 + var5;
      byte var8;
      if(var1 == 33) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      Object var9 = this.findFocusableViewInBounds((boolean)var8, var2, var3);
      if(var9 == null) {
         var9 = this;
      }

      if(var2 >= var6 && var3 <= var7) {
         var4 = false;
      } else {
         int var11;
         if(var8 != 0) {
            var11 = var2 - var6;
         } else {
            var11 = var3 - var7;
         }

         this.doScrollY(var11);
      }

      View var10 = this.findFocus();
      if(var9 != var10 && ((View)var9).requestFocus(var1)) {
         this.mScrollViewMovedFocus = (boolean)1;
         this.mScrollViewMovedFocus = (boolean)0;
      }

      return var4;
   }

   private void scrollToChild(View var1) {
      Rect var2 = this.mTempRect;
      var1.getDrawingRect(var2);
      Rect var3 = this.mTempRect;
      this.offsetDescendantRectToMyCoords(var1, var3);
      Rect var4 = this.mTempRect;
      int var5 = this.computeScrollDeltaToGetChildRectOnScreen(var4);
      if(var5 != 0) {
         this.scrollBy(0, var5);
      }
   }

   private boolean scrollToChildRect(Rect var1, boolean var2) {
      int var3 = this.computeScrollDeltaToGetChildRectOnScreen(var1);
      boolean var4;
      if(var3 != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4) {
         if(var2) {
            this.scrollBy(0, var3);
         } else {
            this.smoothScrollBy(0, var3);
         }
      }

      return var4;
   }

   public void addView(View var1) {
      if(this.getChildCount() > 0) {
         throw new IllegalStateException("ScrollView can host only one direct child");
      } else {
         super.addView(var1);
      }
   }

   public void addView(View var1, int var2) {
      if(this.getChildCount() > 0) {
         throw new IllegalStateException("ScrollView can host only one direct child");
      } else {
         super.addView(var1, var2);
      }
   }

   public void addView(View var1, int var2, LayoutParams var3) {
      if(this.getChildCount() > 0) {
         throw new IllegalStateException("ScrollView can host only one direct child");
      } else {
         super.addView(var1, var2, var3);
      }
   }

   public void addView(View var1, LayoutParams var2) {
      if(this.getChildCount() > 0) {
         throw new IllegalStateException("ScrollView can host only one direct child");
      } else {
         super.addView(var1, var2);
      }
   }

   public boolean arrowScroll(int var1) {
      View var2 = this.findFocus();
      if(var2 == this) {
         var2 = null;
      }

      boolean var13;
      label56: {
         View var3 = FocusFinder.getInstance().findNextFocus(this, var2, var1);
         int var4 = this.getMaxScrollAmount();
         if(var3 != null) {
            int var5 = this.getHeight();
            if(this.isWithinDeltaOfScreen(var3, var4, var5)) {
               Rect var6 = this.mTempRect;
               var3.getDrawingRect(var6);
               Rect var7 = this.mTempRect;
               this.offsetDescendantRectToMyCoords(var3, var7);
               Rect var8 = this.mTempRect;
               int var9 = this.computeScrollDeltaToGetChildRectOnScreen(var8);
               this.doScrollY(var9);
               var3.requestFocus(var1);
               break label56;
            }
         }

         int var14 = var4;
         if(var1 == 33 && this.getScrollY() < var4) {
            var14 = this.getScrollY();
         } else if(var1 == 130 && this.getChildCount() > 0) {
            int var15 = this.getChildAt(0).getBottom();
            int var16 = this.getScrollY();
            int var17 = this.getHeight();
            int var18 = var16 + var17;
            if(var15 - var18 < var4) {
               var14 = var15 - var18;
            }
         }

         if(var14 == 0) {
            var13 = false;
            return var13;
         }

         int var19;
         if(var1 == 130) {
            var19 = var14;
         } else {
            var19 = -var14;
         }

         this.doScrollY(var19);
      }

      if(var2 != null && var2.isFocused() && this.isOffScreen(var2)) {
         int var11 = this.getDescendantFocusability();
         this.setDescendantFocusability(131072);
         boolean var12 = this.requestFocus();
         this.setDescendantFocusability(var11);
      }

      var13 = true;
      return var13;
   }

   public void computeScroll() {
      if(this.mScroller.computeScrollOffset()) {
         int var1 = this.mScrollX;
         int var2 = this.mScrollY;
         int var3 = this.mScroller.getCurrX();
         int var4 = this.mScroller.getCurrY();
         if(this.getChildCount() > 0) {
            View var5 = this.getChildAt(0);
            int var6 = this.getWidth();
            int var7 = this.mPaddingRight;
            int var8 = var6 - var7;
            int var9 = this.mPaddingLeft;
            int var10 = var8 - var9;
            int var11 = var5.getWidth();
            var3 = this.clamp(var3, var10, var11);
            int var12 = this.getHeight();
            int var13 = this.mPaddingBottom;
            int var14 = var12 - var13;
            int var15 = this.mPaddingTop;
            int var16 = var14 - var15;
            int var17 = var5.getHeight();
            var4 = this.clamp(var4, var16, var17);
            if(var3 != var1 || var4 != var2) {
               this.mScrollX = var3;
               this.mScrollY = var4;
               this.onScrollChanged(var3, var4, var1, var2);
            }
         }

         boolean var18 = this.awakenScrollBars();
         this.postInvalidate();
      }
   }

   protected int computeScrollDeltaToGetChildRectOnScreen(Rect var1) {
      int var2;
      if(this.getChildCount() == 0) {
         var2 = 0;
      } else {
         int var3 = this.getHeight();
         int var4 = this.getScrollY();
         int var5 = var4 + var3;
         int var6 = this.getVerticalFadingEdgeLength();
         if(var1.top > 0) {
            var4 += var6;
         }

         int var7 = var1.bottom;
         int var8 = this.getChildAt(0).getHeight();
         if(var7 < var8) {
            var5 -= var6;
         }

         int var9 = 0;
         if(var1.bottom > var5 && var1.top > var4) {
            int var11;
            if(var1.height() > var3) {
               int var10 = var1.top - var4;
               var11 = var9 + var10;
            } else {
               int var13 = var1.bottom - var5;
               var11 = var9 + var13;
            }

            int var12 = this.getChildAt(0).getBottom() - var5;
            var9 = Math.min(var11, var12);
         } else if(var1.top < var4 && var1.bottom < var5) {
            int var16;
            if(var1.height() > var3) {
               int var14 = var1.bottom;
               int var15 = var5 - var14;
               var16 = var9 - var15;
            } else {
               int var18 = var1.top;
               int var19 = var4 - var18;
               var16 = var9 - var19;
            }

            int var17 = -this.getScrollY();
            var9 = Math.max(var16, var17);
         }

         var2 = var9;
      }

      return var2;
   }

   protected int computeVerticalScrollOffset() {
      int var1 = super.computeVerticalScrollOffset();
      return Math.max(0, var1);
   }

   protected int computeVerticalScrollRange() {
      int var1 = this.getChildCount();
      int var2 = this.getHeight();
      int var3 = this.mPaddingBottom;
      int var4 = var2 - var3;
      int var5 = this.mPaddingTop;
      int var6 = var4 - var5;
      int var7;
      if(var1 == 0) {
         var7 = var6;
      } else {
         var7 = this.getChildAt(0).getBottom();
      }

      return var7;
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      boolean var2;
      if(!super.dispatchKeyEvent(var1) && !this.executeKeyEvent(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean executeKeyEvent(KeyEvent var1) {
      this.mTempRect.setEmpty();
      boolean var4;
      if(!this.canScroll()) {
         if(this.isFocused() && var1.getKeyCode() != 4) {
            View var2 = this.findFocus();
            if(var2 == this) {
               var2 = null;
            }

            View var3 = FocusFinder.getInstance().findNextFocus(this, var2, 130);
            if(var3 != null && var3 != this && var3.requestFocus(130)) {
               var4 = true;
            } else {
               var4 = false;
            }
         } else {
            var4 = false;
         }
      } else {
         boolean var5 = false;
         if(var1.getAction() == 0) {
            switch(var1.getKeyCode()) {
            case 19:
               if(!var1.isAltPressed()) {
                  var5 = this.arrowScroll(33);
               } else {
                  var5 = this.fullScroll(33);
               }
               break;
            case 20:
               if(!var1.isAltPressed()) {
                  var5 = this.arrowScroll(130);
               } else {
                  var5 = this.fullScroll(130);
               }
               break;
            case 62:
               short var6;
               if(var1.isShiftPressed()) {
                  var6 = 33;
               } else {
                  var6 = 130;
               }

               this.pageScroll(var6);
            }
         }

         var4 = var5;
      }

      return var4;
   }

   public void fling(int var1) {
      if(this.getChildCount() > 0) {
         int var2 = this.getHeight();
         int var3 = this.mPaddingBottom;
         int var4 = var2 - var3;
         int var5 = this.mPaddingTop;
         int var6 = var4 - var5;
         int var7 = this.getChildAt(0).getHeight();
         Scroller var8 = this.mScroller;
         int var9 = this.mScrollX;
         int var10 = this.mScrollY;
         int var11 = var7 - var6;
         int var12 = Math.max(0, var11);
         byte var14 = 0;
         byte var15 = 0;
         byte var16 = 0;
         var8.fling(var9, var10, 0, var1, var14, var15, var16, var12);
         byte var17;
         if(var1 > 0) {
            var17 = 1;
         } else {
            var17 = 0;
         }

         int var18 = this.mScroller.getFinalY();
         View var19 = this.findFocus();
         Object var20 = this.findFocusableViewInMyBounds((boolean)var17, var18, var19);
         if(var20 == null) {
            var20 = this;
         }

         View var21 = this.findFocus();
         if(var20 != var21) {
            short var22;
            if(var17 != 0) {
               var22 = 130;
            } else {
               var22 = 33;
            }

            if(((View)var20).requestFocus(var22)) {
               this.mScrollViewMovedFocus = (boolean)1;
               this.mScrollViewMovedFocus = (boolean)0;
            }
         }

         this.invalidate();
      }
   }

   public boolean fullScroll(int var1) {
      boolean var2;
      if(var1 == 130) {
         var2 = true;
      } else {
         var2 = false;
      }

      int var3 = this.getHeight();
      this.mTempRect.top = 0;
      this.mTempRect.bottom = var3;
      if(var2) {
         int var4 = this.getChildCount();
         if(var4 > 0) {
            int var5 = var4 - 1;
            View var6 = this.getChildAt(var5);
            Rect var7 = this.mTempRect;
            int var8 = var6.getBottom();
            var7.bottom = var8;
            Rect var9 = this.mTempRect;
            int var10 = this.mTempRect.bottom - var3;
            var9.top = var10;
         }
      }

      int var11 = this.mTempRect.top;
      int var12 = this.mTempRect.bottom;
      return this.scrollAndFocus(var1, var11, var12);
   }

   protected float getBottomFadingEdgeStrength() {
      float var1;
      if(this.getChildCount() == 0) {
         var1 = (float)false;
      } else {
         int var2 = this.getVerticalFadingEdgeLength();
         int var3 = this.getHeight();
         int var4 = this.mPaddingBottom;
         int var5 = var3 - var4;
         int var6 = this.getChildAt(0).getBottom();
         int var7 = this.mScrollY;
         int var8 = var6 - var7 - var5;
         if(var8 < var2) {
            float var9 = (float)var8;
            float var10 = (float)var2;
            var1 = var9 / var10;
         } else {
            var1 = (float)1065353216;
         }
      }

      return var1;
   }

   public int getMaxScrollAmount() {
      int var1 = this.mBottom;
      int var2 = this.mTop;
      float var3 = (float)(var1 - var2);
      return (int)(0.5F * var3);
   }

   protected float getTopFadingEdgeStrength() {
      float var1;
      if(this.getChildCount() == 0) {
         var1 = (float)false;
      } else {
         int var2 = this.getVerticalFadingEdgeLength();
         if(this.mScrollY < var2) {
            float var3 = (float)this.mScrollY;
            float var4 = (float)var2;
            var1 = var3 / var4;
         } else {
            var1 = (float)1065353216;
         }
      }

      return var1;
   }

   public boolean isFillViewport() {
      return this.mFillViewport;
   }

   public boolean isScrollBottom() {
      int var1 = this.computeVerticalScrollRange();
      int var2 = this.mBottom;
      int var3 = var1 - var2;
      int var4 = this.mScrollY;
      boolean var5;
      if(var3 - var4 == 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public boolean isSmoothScrollingEnabled() {
      return this.mSmoothScrollingEnabled;
   }

   protected void measureChild(View var1, int var2, int var3) {
      LayoutParams var4 = var1.getLayoutParams();
      int var5 = this.mPaddingLeft;
      int var6 = this.mPaddingRight;
      int var7 = var5 + var6;
      int var8 = var4.width;
      int var9 = getChildMeasureSpec(var2, var7, var8);
      int var10 = MeasureSpec.makeMeasureSpec(0, 0);
      var1.measure(var9, var10);
   }

   protected void measureChildWithMargins(View var1, int var2, int var3, int var4, int var5) {
      MarginLayoutParams var6 = (MarginLayoutParams)var1.getLayoutParams();
      int var7 = this.mPaddingLeft;
      int var8 = this.mPaddingRight;
      int var9 = var7 + var8;
      int var10 = var6.leftMargin;
      int var11 = var9 + var10;
      int var12 = var6.rightMargin;
      int var13 = var11 + var12 + var3;
      int var14 = var6.width;
      int var15 = getChildMeasureSpec(var2, var13, var14);
      int var16 = var6.topMargin;
      int var17 = var6.bottomMargin;
      int var18 = MeasureSpec.makeMeasureSpec(var16 + var17, 0);
      var1.measure(var15, var18);
   }

   public void onConfigurationChanged(Configuration var1) {
      boolean var2 = this.isScrollBottom();
      this.bPreScrollState = var2;
      this.setIsScrollBottom((boolean)0);
      super.onConfigurationChanged(var1);
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      int var2 = var1.getAction();
      byte var3;
      if(var2 == 2 && this.mIsBeingDragged) {
         var3 = 1;
      } else {
         float var6;
         switch(var2 & 255) {
         case 0:
            var6 = var1.getY();
            int var10 = (int)var1.getX();
            int var11 = (int)var6;
            if(!this.inChild(var10, var11)) {
               this.mIsBeingDragged = (boolean)0;
            } else {
               this.mLastMotionY = var6;
               int var12 = var1.getPointerId(0);
               this.mActivePointerId = var12;
               byte var13;
               if(!this.mScroller.isFinished()) {
                  var13 = 1;
               } else {
                  var13 = 0;
               }

               this.mIsBeingDragged = (boolean)var13;
            }
            break;
         case 1:
         case 3:
            this.mIsBeingDragged = (boolean)0;
            this.mActivePointerId = -1;
            break;
         case 2:
            if(!this.bScrollBottom) {
               int var4 = this.mActivePointerId;
               if(var4 != -1) {
                  int var5 = var1.findPointerIndex(var4);
                  var6 = var1.getY(var5);
                  float var7 = this.mLastMotionY;
                  int var8 = (int)Math.abs(var6 - var7);
                  int var9 = this.mTouchSlop;
                  if(var8 > var9) {
                     this.mIsBeingDragged = (boolean)1;
                     this.mLastMotionY = var6;
                  }
               }
            }
         case 4:
         case 5:
         default:
            break;
         case 6:
            this.onSecondaryPointerUp(var1);
         }

         var3 = this.mIsBeingDragged;
      }

      return (boolean)var3;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      this.mIsLayoutDirty = (boolean)0;
      if(this.mChildToScrollTo != null) {
         View var6 = this.mChildToScrollTo;
         if(this.isViewDescendantOf(var6, this)) {
            View var7 = this.mChildToScrollTo;
            this.scrollToChild(var7);
         }
      }

      this.mChildToScrollTo = null;
      int var8 = this.mScrollX;
      int var9 = this.mScrollY;
      this.scrollTo(var8, var9);
   }

   protected void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      if(this.mFillViewport) {
         if(MeasureSpec.getMode(var2) != 0) {
            if(this.getChildCount() > 0) {
               View var3 = this.getChildAt(0);
               int var4 = this.getMeasuredHeight();
               if(var3.getMeasuredHeight() < var4) {
                  android.widget.FrameLayout.LayoutParams var5 = (android.widget.FrameLayout.LayoutParams)var3.getLayoutParams();
                  int var6 = this.mPaddingLeft;
                  int var7 = this.mPaddingRight;
                  int var8 = var6 + var7;
                  int var9 = var5.width;
                  int var10 = getChildMeasureSpec(var1, var8, var9);
                  int var11 = this.mPaddingTop;
                  int var12 = var4 - var11;
                  int var13 = this.mPaddingBottom;
                  int var14 = MeasureSpec.makeMeasureSpec(var12 - var13, 1073741824);
                  var3.measure(var10, var14);
               }
            }
         }
      }
   }

   protected boolean onRequestFocusInDescendants(int var1, Rect var2) {
      if(var1 == 2) {
         var1 = 130;
      } else if(var1 == 1) {
         var1 = 33;
      }

      View var3;
      if(var2 == null) {
         var3 = FocusFinder.getInstance().findNextFocus(this, (View)null, var1);
      } else {
         var3 = FocusFinder.getInstance().findNextFocusFromRect(this, var2, var1);
      }

      byte var4;
      if(var3 == null) {
         var4 = 0;
      } else if(this.isOffScreen(var3)) {
         var4 = 0;
      } else {
         var4 = var3.requestFocus(var1, var2);
      }

      return (boolean)var4;
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      if(this.bPreScrollState == 1) {
         int var5 = this.computeVerticalScrollRange();
         int var6 = this.getHeight();
         int var7 = var5 - var6;
         this.scrollTo(0, var7);
      }

      View var8 = this.findFocus();
      if(var8 != null) {
         if(this == var8) {
            ;
         }
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      boolean var2;
      if(var1.getAction() == 0 && var1.getEdgeFlags() != 0) {
         var2 = false;
      } else {
         if(this.mVelocityTracker == null) {
            VelocityTracker var3 = VelocityTracker.obtain();
            this.mVelocityTracker = var3;
         }

         if(this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(var1);
         }

         switch(var1.getAction() & 255) {
         case 0:
            float var4 = var1.getY();
            int var5 = (int)var1.getX();
            int var6 = (int)var4;
            boolean var7 = this.inChild(var5, var6);
            this.mIsBeingDragged = var7;
            if(!var7) {
               var2 = false;
               return var2;
            }

            if(!this.mScroller.isFinished()) {
               this.mScroller.abortAnimation();
            }

            this.mLastMotionY = var4;
            int var8 = var1.getPointerId(0);
            this.mActivePointerId = var8;
            break;
         case 1:
            if(this.bScrollBottom != 1 && this.mIsBeingDragged) {
               VelocityTracker var13 = this.mVelocityTracker;
               if(var13 != null) {
                  float var14 = (float)this.mMaximumVelocity;
                  var13.computeCurrentVelocity(1000, var14);
                  int var15 = this.mActivePointerId;
                  int var16 = (int)var13.getYVelocity(var15);
                  if(this.getChildCount() > 0) {
                     int var17 = Math.abs(var16);
                     int var18 = this.mMinimumVelocity;
                     if(var17 > var18) {
                        int var19 = -var16;
                        this.fling(var19);
                     }
                  }
               }

               this.mActivePointerId = -1;
               this.mIsBeingDragged = (boolean)0;
               if(this.mVelocityTracker != null) {
                  this.mVelocityTracker.recycle();
                  this.mVelocityTracker = null;
               }
            }
            break;
         case 2:
            if(this.bScrollBottom != 1 && this.mIsBeingDragged) {
               int var9 = this.mActivePointerId;
               int var10 = var1.findPointerIndex(var9);
               float var11 = var1.getY(var10);
               int var12 = (int)(this.mLastMotionY - var11);
               this.mLastMotionY = var11;
               this.scrollBy(0, var12);
            }
            break;
         case 3:
            if(this.mIsBeingDragged && this.getChildCount() > 0) {
               this.mActivePointerId = -1;
               this.mIsBeingDragged = (boolean)0;
               if(this.mVelocityTracker != null) {
                  this.mVelocityTracker.recycle();
                  this.mVelocityTracker = null;
               }
            }
         case 4:
         case 5:
         default:
            break;
         case 6:
            this.onSecondaryPointerUp(var1);
         }

         var2 = true;
      }

      return var2;
   }

   public boolean pageScroll(int var1) {
      boolean var2;
      if(var1 == 130) {
         var2 = true;
      } else {
         var2 = false;
      }

      int var3 = this.getHeight();
      if(var2) {
         Rect var4 = this.mTempRect;
         int var5 = this.getScrollY() + var3;
         var4.top = var5;
         int var6 = this.getChildCount();
         if(var6 > 0) {
            int var7 = var6 - 1;
            View var8 = this.getChildAt(var7);
            int var9 = this.mTempRect.top + var3;
            int var10 = var8.getBottom();
            if(var9 > var10) {
               Rect var11 = this.mTempRect;
               int var12 = var8.getBottom() - var3;
               var11.top = var12;
            }
         }
      } else {
         Rect var17 = this.mTempRect;
         int var18 = this.getScrollY() - var3;
         var17.top = var18;
         if(this.mTempRect.top < 0) {
            this.mTempRect.top = 0;
         }
      }

      Rect var13 = this.mTempRect;
      int var14 = this.mTempRect.top + var3;
      var13.bottom = var14;
      int var15 = this.mTempRect.top;
      int var16 = this.mTempRect.bottom;
      return this.scrollAndFocus(var1, var15, var16);
   }

   public void requestChildFocus(View var1, View var2) {
      if(!this.mScrollViewMovedFocus) {
         if(!this.mIsLayoutDirty) {
            this.scrollToChild(var2);
         } else {
            this.mChildToScrollTo = var2;
         }
      }

      super.requestChildFocus(var1, var2);
   }

   public boolean requestChildRectangleOnScreen(View var1, Rect var2, boolean var3) {
      int var4 = var1.getLeft();
      int var5 = var1.getScrollX();
      int var6 = var4 - var5;
      int var7 = var1.getTop();
      int var8 = var1.getScrollY();
      int var9 = var7 - var8;
      var2.offset(var6, var9);
      return this.scrollToChildRect(var2, var3);
   }

   public void requestLayout() {
      this.mIsLayoutDirty = (boolean)1;
      super.requestLayout();
   }

   public void resume() {
      boolean var1 = this.isScrollBottom();
      this.bPreScrollState = var1;
      this.setIsScrollBottom((boolean)0);
   }

   public void scrollTo(int var1, int var2) {
      if(this.getChildCount() > 0) {
         View var3 = this.getChildAt(0);
         int var4 = this.getWidth();
         int var5 = this.mPaddingRight;
         int var6 = var4 - var5;
         int var7 = this.mPaddingLeft;
         int var8 = var6 - var7;
         int var9 = var3.getWidth();
         var1 = this.clamp(var1, var8, var9);
         int var10 = this.getHeight();
         int var11 = this.mPaddingBottom;
         int var12 = var10 - var11;
         int var13 = this.mPaddingTop;
         int var14 = var12 - var13;
         int var15 = var3.getHeight();
         var2 = this.clamp(var2, var14, var15);
         int var16 = this.mScrollX;
         if(var1 == var16) {
            int var17 = this.mScrollY;
            if(var2 == var17) {
               return;
            }
         }

         super.scrollTo(var1, var2);
      }
   }

   public void setFillViewport(boolean var1) {
      boolean var2 = this.mFillViewport;
      if(var1 != var2) {
         this.mFillViewport = var1;
         this.requestLayout();
      }
   }

   public void setIsScrollBottom(boolean var1) {
      this.bScrollBottom = var1;
   }

   public void setScrollBottom() {}

   public void setSmoothScrollingEnabled(boolean var1) {
      this.mSmoothScrollingEnabled = var1;
   }

   public final void smoothScrollBy(int var1, int var2) {
      if(this.getChildCount() != 0) {
         long var3 = AnimationUtils.currentAnimationTimeMillis();
         long var5 = this.mLastScroll;
         if(var3 - var5 > 250L) {
            int var7 = this.getHeight();
            int var8 = this.mPaddingBottom;
            int var9 = var7 - var8;
            int var10 = this.mPaddingTop;
            int var11 = var9 - var10;
            int var12 = this.getChildAt(0).getHeight() - var11;
            int var13 = Math.max(0, var12);
            int var14 = this.mScrollY;
            int var15 = Math.min(var14 + var2, var13);
            int var16 = Math.max(0, var15) - var14;
            Scroller var17 = this.mScroller;
            int var18 = this.mScrollX;
            var17.startScroll(var18, var14, 0, var16);
            this.invalidate();
         } else {
            if(!this.mScroller.isFinished()) {
               this.mScroller.abortAnimation();
            }

            this.scrollBy(var1, var2);
         }

         long var19 = AnimationUtils.currentAnimationTimeMillis();
         this.mLastScroll = var19;
      }
   }

   public final void smoothScrollTo(int var1, int var2) {
      int var3 = this.mScrollX;
      int var4 = var1 - var3;
      int var5 = this.mScrollY;
      int var6 = var2 - var5;
      this.smoothScrollBy(var4, var6);
   }
}

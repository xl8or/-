package android.support.v4.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Parcelable.Creator;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Scroller;
import java.util.ArrayList;

public class ViewPager extends ViewGroup {

   private static final boolean DEBUG = false;
   private static final int INVALID_POINTER = 255;
   public static final int SCROLL_STATE_DRAGGING = 1;
   public static final int SCROLL_STATE_IDLE = 0;
   public static final int SCROLL_STATE_SETTLING = 2;
   private static final String TAG = "ViewPager";
   private static final boolean USE_CACHE;
   private int mActivePointerId;
   private PagerAdapter mAdapter;
   private int mChildHeightMeasureSpec;
   private int mChildWidthMeasureSpec;
   private int mCurItem;
   private long mFakeDragBeginTime;
   private boolean mFakeDragging;
   private boolean mInLayout;
   private float mInitialMotionX;
   private boolean mIsBeingDragged;
   private boolean mIsUnableToDrag;
   private final ArrayList<ViewPager.ItemInfo> mItems;
   private float mLastMotionX;
   private float mLastMotionY;
   private int mMaximumVelocity;
   private int mMinimumVelocity;
   private PagerAdapter.DataSetObserver mObserver;
   private ViewPager.OnPageChangeListener mOnPageChangeListener;
   private boolean mPopulatePending;
   private Parcelable mRestoredAdapterState;
   private ClassLoader mRestoredClassLoader;
   private int mRestoredCurItem;
   private int mScrollState;
   private Scroller mScroller;
   private boolean mScrolling;
   private boolean mScrollingCacheEnabled;
   private int mTouchSlop;
   private VelocityTracker mVelocityTracker;


   public ViewPager(Context var1) {
      super(var1);
      ArrayList var2 = new ArrayList();
      this.mItems = var2;
      this.mRestoredCurItem = -1;
      this.mRestoredAdapterState = null;
      this.mRestoredClassLoader = null;
      this.mActivePointerId = -1;
      this.mScrollState = 0;
      this.initViewPager();
   }

   public ViewPager(Context var1, AttributeSet var2) {
      super(var1, var2);
      ArrayList var3 = new ArrayList();
      this.mItems = var3;
      this.mRestoredCurItem = -1;
      this.mRestoredAdapterState = null;
      this.mRestoredClassLoader = null;
      this.mActivePointerId = -1;
      this.mScrollState = 0;
      this.initViewPager();
   }

   static boolean canScroll(View var0, boolean var1, int var2, int var3, int var4) {
      boolean var5 = true;
      if(var0 instanceof ViewGroup) {
         ViewGroup var6 = (ViewGroup)var0;
         int var7 = var0.getScrollX();
         int var8 = var0.getScrollY();

         for(int var9 = var6.getChildCount() + -1; var9 >= 0; var9 += -1) {
            View var10 = var6.getChildAt(var9);
            int var11 = var3 + var7;
            int var12 = var10.getLeft();
            if(var11 >= var12) {
               int var13 = var3 + var7;
               int var14 = var10.getRight();
               if(var13 < var14) {
                  int var15 = var4 + var8;
                  int var16 = var10.getTop();
                  if(var15 >= var16) {
                     int var17 = var4 + var8;
                     int var18 = var10.getBottom();
                     if(var17 < var18) {
                        int var19 = var3 + var7;
                        int var20 = var10.getLeft();
                        int var21 = var19 - var20;
                        int var22 = var4 + var8;
                        int var23 = var10.getTop();
                        int var24 = var22 - var23;
                        if(canScroll(var10, (boolean)1, var2, var21, var24)) {
                           return var5;
                        }
                     }
                  }
               }
            }
         }
      }

      if(var1) {
         int var25 = -var2;
         if(ViewCompat.canScrollHorizontally(var0, var25)) {
            return var5;
         }
      }

      var5 = false;
      return var5;
   }

   private void completeScroll() {
      byte var1 = this.mScrolling;
      if(var1 != 0) {
         this.setScrollingCacheEnabled((boolean)0);
         this.mScroller.abortAnimation();
         int var2 = this.getScrollX();
         int var3 = this.getScrollY();
         int var4 = this.mScroller.getCurrX();
         int var5 = this.mScroller.getCurrY();
         if(var2 != var4 || var3 != var5) {
            this.scrollTo(var4, var5);
         }

         this.setScrollState(0);
      }

      this.mPopulatePending = (boolean)0;
      this.mScrolling = (boolean)0;
      int var6 = 0;

      while(true) {
         int var7 = this.mItems.size();
         if(var6 >= var7) {
            if(var1 == 0) {
               return;
            }

            this.populate();
            return;
         }

         ViewPager.ItemInfo var8 = (ViewPager.ItemInfo)this.mItems.get(var6);
         if(var8.scrolling) {
            var1 = 1;
            var8.scrolling = (boolean)0;
         }

         ++var6;
      }
   }

   private void endDrag() {
      this.mIsBeingDragged = (boolean)0;
      this.mIsUnableToDrag = (boolean)0;
      if(this.mVelocityTracker != null) {
         this.mVelocityTracker.recycle();
         this.mVelocityTracker = null;
      }
   }

   private void onSecondaryPointerUp(MotionEvent var1) {
      int var2 = MotionEventCompat.getActionIndex(var1);
      int var3 = MotionEventCompat.getPointerId(var1, var2);
      int var4 = this.mActivePointerId;
      if(var3 == var4) {
         byte var5;
         if(var2 == 0) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         float var6 = MotionEventCompat.getX(var1, var5);
         this.mLastMotionX = var6;
         int var7 = MotionEventCompat.getPointerId(var1, var5);
         this.mActivePointerId = var7;
         if(this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
         }
      }
   }

   private void setScrollState(int var1) {
      if(this.mScrollState != var1) {
         this.mScrollState = var1;
         if(this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(var1);
         }
      }
   }

   private void setScrollingCacheEnabled(boolean var1) {
      if(this.mScrollingCacheEnabled != var1) {
         this.mScrollingCacheEnabled = var1;
      }
   }

   void addNewItem(int var1, int var2) {
      ViewPager.ItemInfo var3 = new ViewPager.ItemInfo();
      var3.position = var1;
      Object var4 = this.mAdapter.instantiateItem(this, var1);
      var3.object = var4;
      if(var2 < 0) {
         this.mItems.add(var3);
      } else {
         this.mItems.add(var2, var3);
      }
   }

   public void addView(View var1, int var2, LayoutParams var3) {
      if(this.mInLayout) {
         this.addViewInLayout(var1, var2, var3);
         int var5 = this.mChildWidthMeasureSpec;
         int var6 = this.mChildHeightMeasureSpec;
         var1.measure(var5, var6);
      } else {
         super.addView(var1, var2, var3);
      }
   }

   public boolean beginFakeDrag() {
      boolean var1 = false;
      if(!this.mIsBeingDragged) {
         this.mFakeDragging = (boolean)1;
         this.setScrollState(1);
         this.mLastMotionX = 0.0F;
         this.mInitialMotionX = 0.0F;
         if(this.mVelocityTracker == null) {
            VelocityTracker var2 = VelocityTracker.obtain();
            this.mVelocityTracker = var2;
         } else {
            this.mVelocityTracker.clear();
         }

         long var3 = SystemClock.uptimeMillis();
         float var7 = 0.0F;
         byte var8 = 0;
         MotionEvent var9 = MotionEvent.obtain(var3, var3, 0, 0.0F, var7, var8);
         this.mVelocityTracker.addMovement(var9);
         var9.recycle();
         this.mFakeDragBeginTime = var3;
         var1 = true;
      }

      return var1;
   }

   public void computeScroll() {
      if(!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
         int var1 = this.getScrollX();
         int var2 = this.getScrollY();
         int var3 = this.mScroller.getCurrX();
         int var4 = this.mScroller.getCurrY();
         if(var1 != var3 || var2 != var4) {
            this.scrollTo(var3, var4);
         }

         if(this.mOnPageChangeListener != null) {
            int var5 = this.getWidth();
            int var6 = var3 / var5;
            int var7 = var3 % var5;
            float var8 = (float)var7;
            float var9 = (float)var5;
            float var10 = var8 / var9;
            this.mOnPageChangeListener.onPageScrolled(var6, var10, var7);
         }

         this.invalidate();
      } else {
         this.completeScroll();
      }
   }

   void dataSetChanged() {
      boolean var1;
      if(this.mItems.isEmpty() && this.mAdapter.getCount() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      int var2 = -1;
      int var3 = 0;

      while(true) {
         int var4 = this.mItems.size();
         if(var3 >= var4) {
            if(var2 >= 0) {
               this.setCurrentItemInternal(var2, (boolean)0, (boolean)1);
               var1 = true;
            }

            if(!var1) {
               return;
            }

            this.populate();
            this.requestLayout();
            return;
         }

         ViewPager.ItemInfo var5 = (ViewPager.ItemInfo)this.mItems.get(var3);
         PagerAdapter var6 = this.mAdapter;
         Object var7 = var5.object;
         int var8 = var6.getItemPosition(var7);
         if(var8 != -1) {
            if(var8 == -1) {
               this.mItems.remove(var3);
               var3 += -1;
               PagerAdapter var10 = this.mAdapter;
               int var11 = var5.position;
               Object var12 = var5.object;
               var10.destroyItem(this, var11, var12);
               var1 = true;
               int var13 = this.mCurItem;
               int var14 = var5.position;
               if(var13 == var14) {
                  int var15 = this.mCurItem;
                  int var16 = this.mAdapter.getCount() + -1;
                  int var17 = Math.min(var15, var16);
                  var2 = Math.max(0, var17);
               }
            } else if(var5.position != var8) {
               int var18 = var5.position;
               int var19 = this.mCurItem;
               if(var18 == var19) {
                  var2 = var8;
               }

               var5.position = var8;
               var1 = true;
            }
         }

         ++var3;
      }
   }

   public void endFakeDrag() {
      if(!this.mFakeDragging) {
         throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
      } else {
         label18: {
            VelocityTracker var1 = this.mVelocityTracker;
            float var2 = (float)this.mMaximumVelocity;
            var1.computeCurrentVelocity(1000, var2);
            int var3 = this.mActivePointerId;
            int var4 = (int)VelocityTrackerCompat.getYVelocity(var1, var3);
            this.mPopulatePending = (boolean)1;
            int var5 = Math.abs(var4);
            int var6 = this.mMinimumVelocity;
            if(var5 <= var6) {
               float var7 = this.mInitialMotionX;
               float var8 = this.mLastMotionX;
               float var9 = Math.abs(var7 - var8);
               float var10 = (float)(this.getWidth() / 3);
               if(var9 < var10) {
                  int var15 = this.mCurItem;
                  this.setCurrentItemInternal(var15, (boolean)1, (boolean)1);
                  break label18;
               }
            }

            float var11 = this.mLastMotionX;
            float var12 = this.mInitialMotionX;
            if(var11 > var12) {
               int var13 = this.mCurItem + -1;
               this.setCurrentItemInternal(var13, (boolean)1, (boolean)1);
            } else {
               int var14 = this.mCurItem + 1;
               this.setCurrentItemInternal(var14, (boolean)1, (boolean)1);
            }
         }

         this.endDrag();
         this.mFakeDragging = (boolean)0;
      }
   }

   public void fakeDragBy(float var1) {
      if(!this.mFakeDragging) {
         throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
      } else {
         float var2 = this.mLastMotionX + var1;
         this.mLastMotionX = var2;
         float var3 = (float)this.getScrollX() - var1;
         int var4 = this.getWidth();
         int var5 = (this.mCurItem + -1) * var4;
         float var6 = (float)Math.max(0, var5);
         int var7 = this.mCurItem + 1;
         int var8 = this.mAdapter.getCount() + -1;
         float var9 = (float)(Math.min(var7, var8) * var4);
         if(var3 < var6) {
            var3 = var6;
         } else if(var3 > var9) {
            var3 = var9;
         }

         float var10 = this.mLastMotionX;
         float var11 = (float)((int)var3);
         float var12 = var3 - var11;
         float var13 = var10 + var12;
         this.mLastMotionX = var13;
         int var14 = (int)var3;
         int var15 = this.getScrollY();
         this.scrollTo(var14, var15);
         if(this.mOnPageChangeListener != null) {
            int var16 = (int)var3 / var4;
            int var17 = (int)var3 % var4;
            float var18 = (float)var17;
            float var19 = (float)var4;
            float var20 = var18 / var19;
            this.mOnPageChangeListener.onPageScrolled(var16, var20, var17);
         }

         long var21 = SystemClock.uptimeMillis();
         long var23 = this.mFakeDragBeginTime;
         float var25 = this.mLastMotionX;
         MotionEvent var26 = MotionEvent.obtain(var23, var21, 2, var25, 0.0F, 0);
         this.mVelocityTracker.addMovement(var26);
         var26.recycle();
      }
   }

   public PagerAdapter getAdapter() {
      return this.mAdapter;
   }

   public int getCurrentItem() {
      return this.mCurItem;
   }

   ViewPager.ItemInfo infoForChild(View var1) {
      int var2 = 0;

      ViewPager.ItemInfo var4;
      while(true) {
         int var3 = this.mItems.size();
         if(var2 >= var3) {
            var4 = null;
            break;
         }

         var4 = (ViewPager.ItemInfo)this.mItems.get(var2);
         PagerAdapter var5 = this.mAdapter;
         Object var6 = var4.object;
         if(var5.isViewFromObject(var1, var6)) {
            break;
         }

         ++var2;
      }

      return var4;
   }

   void initViewPager() {
      this.setWillNotDraw((boolean)0);
      Context var1 = this.getContext();
      Scroller var2 = new Scroller(var1);
      this.mScroller = var2;
      ViewConfiguration var3 = ViewConfiguration.get(this.getContext());
      int var4 = ViewConfigurationCompat.getScaledPagingTouchSlop(var3);
      this.mTouchSlop = var4;
      int var5 = var3.getScaledMinimumFlingVelocity();
      this.mMinimumVelocity = var5;
      int var6 = var3.getScaledMaximumFlingVelocity();
      this.mMaximumVelocity = var6;
   }

   public boolean isFakeDragging() {
      return this.mFakeDragging;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      if(this.mAdapter != null) {
         this.populate();
      }
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      int var2 = var1.getAction() & 255;
      boolean var3;
      if(var2 != 3 && var2 != 1) {
         if(var2 != 0) {
            if(this.mIsBeingDragged) {
               var3 = true;
               return var3;
            }

            if(this.mIsUnableToDrag) {
               var3 = false;
               return var3;
            }
         }

         switch(var2) {
         case 0:
            float var23 = var1.getX();
            this.mInitialMotionX = var23;
            this.mLastMotionX = var23;
            float var24 = var1.getY();
            this.mLastMotionY = var24;
            int var25 = MotionEventCompat.getPointerId(var1, 0);
            this.mActivePointerId = var25;
            if(this.mScrollState == 2) {
               this.mIsBeingDragged = (boolean)1;
               this.mIsUnableToDrag = (boolean)0;
               this.setScrollState(1);
            } else {
               this.completeScroll();
               this.mIsBeingDragged = (boolean)0;
               this.mIsUnableToDrag = (boolean)0;
            }
            break;
         case 2:
            int var4 = this.mActivePointerId;
            if(var4 != -1) {
               label92: {
                  float var6;
                  float var8;
                  float var9;
                  float var10;
                  float var12;
                  boolean var17;
                  label80: {
                     int var5 = MotionEventCompat.findPointerIndex(var1, var4);
                     var6 = MotionEventCompat.getX(var1, var5);
                     float var7 = this.mLastMotionX;
                     var8 = var6 - var7;
                     var9 = Math.abs(var8);
                     var10 = MotionEventCompat.getY(var1, var5);
                     float var11 = this.mLastMotionY;
                     var12 = Math.abs(var10 - var11);
                     int var13 = this.getScrollX();
                     if(var8 <= 0.0F || var13 != 0) {
                        label78: {
                           if(var8 < 0.0F && this.mAdapter != null) {
                              int var14 = this.mAdapter.getCount() + -1;
                              int var15 = this.getWidth();
                              int var16 = var14 * var15 + -1;
                              if(var13 >= var16) {
                                 break label78;
                              }
                           }

                           var17 = false;
                           break label80;
                        }
                     }

                     var17 = true;
                  }

                  if(!var17) {
                     int var18 = (int)var8;
                     int var19 = (int)var6;
                     int var20 = (int)var10;
                     if(!canScroll(this, (boolean)0, var18, var19, var20)) {
                        float var21 = (float)this.mTouchSlop;
                        if(var9 > var21 && var9 > var12) {
                           this.mIsBeingDragged = (boolean)1;
                           this.setScrollState(1);
                           this.mLastMotionX = var6;
                           this.setScrollingCacheEnabled((boolean)1);
                        } else {
                           float var22 = (float)this.mTouchSlop;
                           if(var12 > var22) {
                              this.mIsUnableToDrag = (boolean)1;
                           }
                        }
                        break label92;
                     }
                  }

                  this.mLastMotionX = var6;
                  this.mInitialMotionX = var6;
                  this.mLastMotionY = var10;
                  var3 = false;
                  return var3;
               }
            }
            break;
         case 6:
            this.onSecondaryPointerUp(var1);
         }

         var3 = this.mIsBeingDragged;
      } else {
         this.mIsBeingDragged = (boolean)0;
         this.mIsUnableToDrag = (boolean)0;
         this.mActivePointerId = -1;
         var3 = false;
      }

      return var3;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      this.mInLayout = (boolean)1;
      this.populate();
      this.mInLayout = (boolean)0;
      int var6 = this.getChildCount();
      int var7 = var4 - var2;

      for(int var8 = 0; var8 < var6; ++var8) {
         View var9 = this.getChildAt(var8);
         if(var9.getVisibility() != 8) {
            ViewPager.ItemInfo var10 = this.infoForChild(var9);
            if(var10 != null) {
               int var11 = var10.position;
               int var12 = var7 * var11;
               int var13 = this.getPaddingLeft() + var12;
               int var14 = this.getPaddingTop();
               int var15 = var9.getMeasuredWidth() + var13;
               int var16 = var9.getMeasuredHeight() + var14;
               var9.layout(var13, var14, var15, var16);
            }
         }
      }

   }

   protected void onMeasure(int var1, int var2) {
      int var3 = getDefaultSize(0, var1);
      int var4 = getDefaultSize(0, var2);
      this.setMeasuredDimension(var3, var4);
      int var5 = this.getMeasuredWidth();
      int var6 = this.getPaddingLeft();
      int var7 = var5 - var6;
      int var8 = this.getPaddingRight();
      int var9 = MeasureSpec.makeMeasureSpec(var7 - var8, 1073741824);
      this.mChildWidthMeasureSpec = var9;
      int var10 = this.getMeasuredHeight();
      int var11 = this.getPaddingTop();
      int var12 = var10 - var11;
      int var13 = this.getPaddingBottom();
      int var14 = MeasureSpec.makeMeasureSpec(var12 - var13, 1073741824);
      this.mChildHeightMeasureSpec = var14;
      this.mInLayout = (boolean)1;
      this.populate();
      this.mInLayout = (boolean)0;
      int var15 = this.getChildCount();

      for(int var16 = 0; var16 < var15; ++var16) {
         View var17 = this.getChildAt(var16);
         if(var17.getVisibility() != 8) {
            int var18 = this.mChildWidthMeasureSpec;
            int var19 = this.mChildHeightMeasureSpec;
            var17.measure(var18, var19);
         }
      }

   }

   public void onRestoreInstanceState(Parcelable var1) {
      if(!(var1 instanceof ViewPager.SavedState)) {
         super.onRestoreInstanceState(var1);
      } else {
         ViewPager.SavedState var2 = (ViewPager.SavedState)var1;
         Parcelable var3 = var2.getSuperState();
         super.onRestoreInstanceState(var3);
         if(this.mAdapter != null) {
            PagerAdapter var4 = this.mAdapter;
            Parcelable var5 = var2.adapterState;
            ClassLoader var6 = var2.loader;
            var4.restoreState(var5, var6);
            int var7 = var2.position;
            this.setCurrentItemInternal(var7, (boolean)0, (boolean)1);
         } else {
            int var8 = var2.position;
            this.mRestoredCurItem = var8;
            Parcelable var9 = var2.adapterState;
            this.mRestoredAdapterState = var9;
            ClassLoader var10 = var2.loader;
            this.mRestoredClassLoader = var10;
         }
      }
   }

   public Parcelable onSaveInstanceState() {
      Parcelable var1 = super.onSaveInstanceState();
      ViewPager.SavedState var2 = new ViewPager.SavedState(var1);
      int var3 = this.mCurItem;
      var2.position = var3;
      if(this.mAdapter != null) {
         Parcelable var4 = this.mAdapter.saveState();
         var2.adapterState = var4;
      }

      return var2;
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      int var5 = this.mCurItem * var1;
      int var6 = this.getScrollX();
      if(var5 != var6) {
         this.completeScroll();
         int var7 = this.getScrollY();
         this.scrollTo(var5, var7);
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      boolean var2;
      if(this.mFakeDragging) {
         var2 = true;
      } else if(var1.getAction() == 0 && var1.getEdgeFlags() != 0) {
         var2 = false;
      } else if(this.mAdapter != null && this.mAdapter.getCount() != 0) {
         if(this.mVelocityTracker == null) {
            VelocityTracker var3 = VelocityTracker.obtain();
            this.mVelocityTracker = var3;
         }

         VelocityTracker var4 = this.mVelocityTracker;
         var4.addMovement(var1);
         switch(var1.getAction() & 255) {
         case 0:
            this.completeScroll();
            float var6 = var1.getX();
            this.mInitialMotionX = var6;
            this.mLastMotionX = var6;
            byte var10 = 0;
            int var11 = MotionEventCompat.getPointerId(var1, var10);
            this.mActivePointerId = var11;
            break;
         case 1:
            if(this.mIsBeingDragged) {
               label56: {
                  VelocityTracker var57 = this.mVelocityTracker;
                  float var58 = (float)this.mMaximumVelocity;
                  short var60 = 1000;
                  var57.computeCurrentVelocity(var60, var58);
                  int var62 = this.mActivePointerId;
                  int var65 = (int)VelocityTrackerCompat.getYVelocity(var57, var62);
                  byte var66 = 1;
                  this.mPopulatePending = (boolean)var66;
                  int var67 = Math.abs(var65);
                  int var68 = this.mMinimumVelocity;
                  if(var67 <= var68) {
                     float var71 = this.mInitialMotionX;
                     float var72 = this.mLastMotionX;
                     float var73 = Math.abs(var71 - var72);
                     float var74 = (float)(this.getWidth() / 3);
                     if(var73 < var74) {
                        int var88 = this.mCurItem;
                        byte var91 = 1;
                        byte var92 = 1;
                        this.setCurrentItemInternal(var88, (boolean)var91, (boolean)var92);
                        break label56;
                     }
                  }

                  float var75 = this.mLastMotionX;
                  float var76 = this.mInitialMotionX;
                  if(var75 > var76) {
                     int var77 = this.mCurItem + -1;
                     byte var80 = 1;
                     byte var81 = 1;
                     this.setCurrentItemInternal(var77, (boolean)var80, (boolean)var81);
                  } else {
                     int var83 = this.mCurItem + 1;
                     byte var86 = 1;
                     byte var87 = 1;
                     this.setCurrentItemInternal(var83, (boolean)var86, (boolean)var87);
                  }
               }

               char var82 = '\uffff';
               this.mActivePointerId = var82;
               this.endDrag();
            }
            break;
         case 2:
            if(!this.mIsBeingDragged) {
               int var12 = this.mActivePointerId;
               int var15 = MotionEventCompat.findPointerIndex(var1, var12);
               float var16 = MotionEventCompat.getX(var1, var15);
               float var17 = this.mLastMotionX;
               float var18 = Math.abs(var16 - var17);
               float var19 = MotionEventCompat.getY(var1, var15);
               float var20 = this.mLastMotionY;
               float var21 = Math.abs(var19 - var20);
               float var22 = (float)this.mTouchSlop;
               if(var18 > var22 && var18 > var21) {
                  byte var23 = 1;
                  this.mIsBeingDragged = (boolean)var23;
                  this.mLastMotionX = var16;
                  byte var26 = 1;
                  this.setScrollState(var26);
                  byte var28 = 1;
                  this.setScrollingCacheEnabled((boolean)var28);
               }
            }

            if(this.mIsBeingDragged) {
               int var29 = this.mActivePointerId;
               int var32 = MotionEventCompat.findPointerIndex(var1, var29);
               float var33 = MotionEventCompat.getX(var1, var32);
               float var34 = this.mLastMotionX - var33;
               this.mLastMotionX = var33;
               float var36 = (float)this.getScrollX() + var34;
               int var37 = this.getWidth();
               int var38 = (this.mCurItem + -1) * var37;
               float var39 = (float)Math.max(0, var38);
               int var40 = this.mCurItem + 1;
               int var41 = this.mAdapter.getCount() + -1;
               float var42 = (float)(Math.min(var40, var41) * var37);
               if(var36 < var39) {
                  var36 = var39;
               } else if(var36 > var42) {
                  var36 = var42;
               }

               float var43 = this.mLastMotionX;
               float var44 = (float)((int)var36);
               float var45 = var36 - var44;
               float var46 = var43 + var45;
               this.mLastMotionX = var46;
               int var47 = (int)var36;
               int var48 = this.getScrollY();
               this.scrollTo(var47, var48);
               if(this.mOnPageChangeListener != null) {
                  int var52 = (int)var36 / var37;
                  int var53 = (int)var36 % var37;
                  float var54 = (float)var53;
                  float var55 = (float)var37;
                  float var56 = var54 / var55;
                  this.mOnPageChangeListener.onPageScrolled(var52, var56, var53);
               }
            }
            break;
         case 3:
            if(this.mIsBeingDragged) {
               int var93 = this.mCurItem;
               byte var96 = 1;
               byte var97 = 1;
               this.setCurrentItemInternal(var93, (boolean)var96, (boolean)var97);
               char var98 = '\uffff';
               this.mActivePointerId = var98;
               this.endDrag();
            }
         case 4:
         default:
            break;
         case 5:
            int var99 = MotionEventCompat.getActionIndex(var1);
            float var100 = MotionEventCompat.getX(var1, var99);
            this.mLastMotionX = var100;
            int var101 = MotionEventCompat.getPointerId(var1, var99);
            this.mActivePointerId = var101;
            break;
         case 6:
            this.onSecondaryPointerUp(var1);
            int var102 = this.mActivePointerId;
            int var105 = MotionEventCompat.findPointerIndex(var1, var102);
            float var108 = MotionEventCompat.getX(var1, var105);
            this.mLastMotionX = var108;
         }

         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   void populate() {
      if(this.mAdapter != null) {
         if(!this.mPopulatePending) {
            if(this.getWindowToken() != null) {
               this.mAdapter.startUpdate(this);
               int var1;
               if(this.mCurItem > 0) {
                  var1 = this.mCurItem + -1;
               } else {
                  var1 = this.mCurItem;
               }

               int var2 = this.mAdapter.getCount();
               int var3 = this.mCurItem;
               int var4 = var2 + -1;
               int var5;
               if(var3 < var4) {
                  var5 = this.mCurItem + 1;
               } else {
                  var5 = var2 + -1;
               }

               int var6 = -1;
               int var7 = 0;

               while(true) {
                  int var8 = this.mItems.size();
                  if(var7 >= var8) {
                     int var19;
                     if(this.mItems.size() > 0) {
                        ArrayList var17 = this.mItems;
                        int var18 = this.mItems.size() + -1;
                        var19 = ((ViewPager.ItemInfo)var17.get(var18)).position;
                     } else {
                        var19 = -1;
                     }

                     if(var19 < var5) {
                        ++var19;
                        if(var19 <= var1) {
                           var19 = var1;
                        }

                        while(var19 <= var5) {
                           this.addNewItem(var19, -1);
                           ++var19;
                        }
                     }

                     this.mAdapter.finishUpdate(this);
                     return;
                  }

                  ViewPager.ItemInfo var9 = (ViewPager.ItemInfo)this.mItems.get(var7);
                  if((var9.position < var1 || var9.position > var5) && !var9.scrolling) {
                     this.mItems.remove(var7);
                     var7 += -1;
                     PagerAdapter var11 = this.mAdapter;
                     int var12 = var9.position;
                     Object var13 = var9.object;
                     var11.destroyItem(this, var12, var13);
                  } else if(var6 < var5 && var9.position > var1) {
                     ++var6;
                     if(var6 < var1) {
                        ;
                     }

                     while(var6 <= var5) {
                        int var16 = var9.position;
                        if(var6 >= var16) {
                           break;
                        }

                        this.addNewItem(var6, var7);
                        ++var6;
                        ++var7;
                     }
                  }

                  var6 = var9.position;
                  int var14 = var7 + 1;
               }
            }
         }
      }
   }

   public void setAdapter(PagerAdapter var1) {
      if(this.mAdapter != null) {
         this.mAdapter.setDataSetObserver((PagerAdapter.DataSetObserver)null);
         this.mAdapter.startUpdate(this);
         int var2 = 0;

         while(true) {
            int var3 = this.mItems.size();
            if(var2 >= var3) {
               this.mAdapter.finishUpdate(this);
               this.mItems.clear();
               this.removeAllViews();
               this.mCurItem = 0;
               this.scrollTo(0, 0);
               break;
            }

            ViewPager.ItemInfo var4 = (ViewPager.ItemInfo)this.mItems.get(var2);
            PagerAdapter var5 = this.mAdapter;
            int var6 = var4.position;
            Object var7 = var4.object;
            var5.destroyItem(this, var6, var7);
            ++var2;
         }
      }

      this.mAdapter = var1;
      if(this.mAdapter != null) {
         if(this.mObserver == null) {
            ViewPager.DataSetObserver var8 = new ViewPager.DataSetObserver((ViewPager.1)null);
            this.mObserver = var8;
         }

         PagerAdapter var9 = this.mAdapter;
         PagerAdapter.DataSetObserver var10 = this.mObserver;
         var9.setDataSetObserver(var10);
         this.mPopulatePending = (boolean)0;
         if(this.mRestoredCurItem >= 0) {
            PagerAdapter var11 = this.mAdapter;
            Parcelable var12 = this.mRestoredAdapterState;
            ClassLoader var13 = this.mRestoredClassLoader;
            var11.restoreState(var12, var13);
            int var14 = this.mRestoredCurItem;
            this.setCurrentItemInternal(var14, (boolean)0, (boolean)1);
            this.mRestoredCurItem = -1;
            this.mRestoredAdapterState = null;
            this.mRestoredClassLoader = null;
         } else {
            this.populate();
         }
      }
   }

   public void setCurrentItem(int var1) {
      this.mPopulatePending = (boolean)0;
      this.setCurrentItemInternal(var1, (boolean)1, (boolean)0);
   }

   void setCurrentItemInternal(int var1, boolean var2, boolean var3) {
      boolean var4 = true;
      if(this.mAdapter != null && this.mAdapter.getCount() > 0) {
         if(!var3 && this.mCurItem == var1 && this.mItems.size() != 0) {
            this.setScrollingCacheEnabled((boolean)0);
         } else {
            if(var1 < 0) {
               var1 = 0;
            } else {
               int var9 = this.mAdapter.getCount();
               if(var1 >= var9) {
                  var1 = this.mAdapter.getCount() + -1;
               }
            }

            label64: {
               int var5 = this.mCurItem + 1;
               if(var1 <= var5) {
                  int var6 = this.mCurItem + -1;
                  if(var1 >= var6) {
                     break label64;
                  }
               }

               int var7 = 0;

               while(true) {
                  int var8 = this.mItems.size();
                  if(var7 >= var8) {
                     break;
                  }

                  ((ViewPager.ItemInfo)this.mItems.get(var7)).scrolling = (boolean)1;
                  ++var7;
               }
            }

            if(this.mCurItem == var1) {
               var4 = false;
            }

            this.mCurItem = var1;
            this.populate();
            if(var2) {
               int var10 = this.getWidth() * var1;
               this.smoothScrollTo(var10, 0);
               if(var4) {
                  if(this.mOnPageChangeListener != null) {
                     this.mOnPageChangeListener.onPageSelected(var1);
                  }
               }
            } else {
               if(var4 && this.mOnPageChangeListener != null) {
                  this.mOnPageChangeListener.onPageSelected(var1);
               }

               this.completeScroll();
               int var11 = this.getWidth() * var1;
               this.scrollTo(var11, 0);
            }
         }
      } else {
         this.setScrollingCacheEnabled((boolean)0);
      }
   }

   public void setOnPageChangeListener(ViewPager.OnPageChangeListener var1) {
      this.mOnPageChangeListener = var1;
   }

   void smoothScrollTo(int var1, int var2) {
      if(this.getChildCount() == 0) {
         this.setScrollingCacheEnabled((boolean)0);
      } else {
         int var3 = this.getScrollX();
         int var4 = this.getScrollY();
         int var5 = var1 - var3;
         int var6 = var2 - var4;
         if(var5 == 0 && var6 == 0) {
            this.completeScroll();
         } else {
            this.setScrollingCacheEnabled((boolean)1);
            this.mScrolling = (boolean)1;
            this.setScrollState(2);
            this.mScroller.startScroll(var3, var4, var5, var6);
            this.invalidate();
         }
      }
   }

   static class ItemInfo {

      Object object;
      int position;
      boolean scrolling;


      ItemInfo() {}
   }

   private class DataSetObserver implements PagerAdapter.DataSetObserver {

      private DataSetObserver() {}

      // $FF: synthetic method
      DataSetObserver(ViewPager.1 var2) {
         this();
      }

      public void onDataSetChanged() {
         ViewPager.this.dataSetChanged();
      }
   }

   public static class SimpleOnPageChangeListener implements ViewPager.OnPageChangeListener {

      public SimpleOnPageChangeListener() {}

      public void onPageScrollStateChanged(int var1) {}

      public void onPageScrolled(int var1, float var2, int var3) {}

      public void onPageSelected(int var1) {}
   }

   public interface OnPageChangeListener {

      void onPageScrollStateChanged(int var1);

      void onPageScrolled(int var1, float var2, int var3);

      void onPageSelected(int var1);
   }

   public static class SavedState extends BaseSavedState {

      public static final Creator<ViewPager.SavedState> CREATOR = ParcelableCompat.newCreator(new ViewPager.SavedState.1());
      Parcelable adapterState;
      ClassLoader loader;
      int position;


      SavedState(Parcel var1, ClassLoader var2) {
         super(var1);
         if(var2 == null) {
            var2 = this.getClass().getClassLoader();
         }

         int var3 = var1.readInt();
         this.position = var3;
         Parcelable var4 = var1.readParcelable(var2);
         this.adapterState = var4;
         this.loader = var2;
      }

      public SavedState(Parcelable var1) {
         super(var1);
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("FragmentPager.SavedState{");
         String var2 = Integer.toHexString(System.identityHashCode(this));
         StringBuilder var3 = var1.append(var2).append(" position=");
         int var4 = this.position;
         return var3.append(var4).append("}").toString();
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         int var3 = this.position;
         var1.writeInt(var3);
         Parcelable var4 = this.adapterState;
         var1.writeParcelable(var4, var2);
      }

      static class 1 implements ParcelableCompatCreatorCallbacks<ViewPager.SavedState> {

         1() {}

         public ViewPager.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
            return new ViewPager.SavedState(var1, var2);
         }

         public ViewPager.SavedState[] newArray(int var1) {
            return new ViewPager.SavedState[var1];
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

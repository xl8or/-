package com.htc.android.mail;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.htc.android.mail.Account;
import com.htc.android.mail.HtcEditableWebView;
import com.htc.android.mail.HtcWebView;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ReadScreen;
import com.htc.android.mail.ZoomControl;

public class ReadScreenScrollView extends ScrollView {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final int SNAP_FREE = 2;
   private static final int SNAP_NONE = 0;
   private static final int SNAP_Y = 1;
   static final String TAG = "readscreenscrollview";
   private boolean isDraging = 0;
   private boolean isMultiTouch = 0;
   private Account mAccount = null;
   private int mDetectOffset = 120;
   private HtcEditableWebView mEditableWebView;
   private float mLastDownY = 0.0F;
   protected float mLastMotionX;
   private int mMode = 0;
   private ReadScreen mReadScreen = null;
   private float mScrollRatio = -1.0F;
   private int mSnapScrollMode = 0;
   private boolean mWasDrag = 0;
   private HtcWebView mWebView;
   ZoomControl mZoomControl;
   Runnable showQuickAction;


   public ReadScreenScrollView(Context var1) {
      super(var1);
      ReadScreenScrollView.1 var2 = new ReadScreenScrollView.1();
      this.showQuickAction = var2;
      this.setOverScrollMode(2);
   }

   public ReadScreenScrollView(Context var1, AttributeSet var2) {
      super(var1, var2);
      ReadScreenScrollView.1 var3 = new ReadScreenScrollView.1();
      this.showQuickAction = var3;
      this.setOverScrollMode(2);
   }

   public ReadScreenScrollView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      ReadScreenScrollView.1 var4 = new ReadScreenScrollView.1();
      this.showQuickAction = var4;
      this.setOverScrollMode(2);
   }

   public float ScrollToRatio() {
      float var1 = this.mScrollRatio;
      this.mScrollRatio = -1.0F;
      this.isMultiTouch = (boolean)0;
      if(var1 != -1.0F) {
         int var2 = this.computeVerticalScrollRange();
         int var3 = this.computeVerticalScrollExtent();
         int var4 = Math.round((float)(var2 - var3) * var1);
         String var5 = "after zoom and jump to y =" + var4 + ", page ratio = " + var1;
         int var6 = Log.d("htcWebView", var5);
         if(var4 > 0) {
            this.scrollTo(0, var4);
         }
      }

      return var1;
   }

   public void checkGetRemain() {
      int var1 = this.getHeight();
      int var2 = this.mPaddingBottom;
      int var3 = var1 - var2;
      int var4 = this.getChildAt(0).getBottom();
      int var5 = this.mScrollY;
      if(var4 - var5 - var3 == 0) {
         if(this.mAccount != null) {
            if(this.mAccount.getDownloadMessageWhenScroll()) {
               if(this.mReadScreen != null) {
                  if(this.mReadScreen.unfinishedL != null) {
                     int var6 = Log.d("ReadScreenScrollView", "OnePage : should getremain");
                     this.mReadScreen.startScrollPageEnd();
                  }
               }
            }
         }
      }
   }

   public void fling(int var1) {
      if(this.getChildCount() > 0) {
         int var2 = this.getHeight();
         int var3 = this.mPaddingBottom;
         int var4 = var2 - var3;
         int var5 = this.mPaddingTop;
         int var6 = var4 - var5;
         int var7 = this.getChildAt(0).getHeight();
         OverScroller var8 = this.mScroller;
         int var9 = this.mScrollX;
         int var10 = this.mScrollY;
         int var11 = var7 - var6;
         int var12 = Math.max(0, var11);
         int var13 = var6 / 2;
         byte var15 = 0;
         byte var16 = 0;
         byte var17 = 0;
         byte var18 = 0;
         var8.fling(var9, var10, 0, var1, var15, var16, var17, var12, var18, var13);
         if(ContentResolver.getMasterSyncAutomatically()) {
            int var19 = this.mScroller.getDuration();
            this.awakenScrollBars(var19);
            if(this.getMode() == 10) {
               Handler var21 = this.getHandler();
               Runnable var22 = this.showQuickAction;
               long var23 = (long)(this.mScroller.getDuration() + 100);
               var21.postDelayed(var22, var23);
            }

            this.invalidate();
         }
      }
   }

   public int getMode() {
      return this.mMode;
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      int var2 = var1.getAction();
      boolean var9;
      if(!this.isMultiTouch && var1.getPointerCount() <= 1) {
         if(var2 == 2 && this.mIsBeingDragged) {
            var9 = true;
         } else if(!this.canScroll()) {
            this.mIsBeingDragged = (boolean)0;
            var9 = false;
         } else {
            float var10 = var1.getY();
            switch(var2) {
            case 0:
               this.mLastMotionY = var10;
               Handler var12 = this.getHandler();
               Runnable var13 = this.showQuickAction;
               var12.removeCallbacks(var13);
               if(!this.mScroller.isFinished()) {
                  this.mScroller.abortAnimation();
               }

               this.mLastMotionY = var10;
               this.mLastDownY = var10;
               float var14 = var1.getX();
               this.mLastMotionX = var14;
               this.mIsBeingDragged = (boolean)0;
               break;
            case 1:
            case 3:
               this.mIsBeingDragged = (boolean)0;
               break;
            case 2:
               float var11 = this.mLastMotionY;
               if((int)Math.abs(var10 - var11) >= 10) {
                  this.mIsBeingDragged = (boolean)1;
               }
            }

            var9 = this.mIsBeingDragged;
         }
      } else {
         if(!this.isMultiTouch) {
            this.mIsBeingDragged = (boolean)0;
            this.isDraging = (boolean)0;
            this.mVelocityTracker = null;
            this.mSnapScrollMode = 0;
            int var3 = this.computeVerticalScrollRange();
            int var4 = this.computeVerticalScrollExtent();
            int var5 = var3 - var4;
            if(var5 > 0) {
               float var6 = (float)this.mScrollY;
               float var7 = (float)var5;
               float var8 = var6 / var7;
               this.mScrollRatio = var8;
            } else {
               this.mScrollRatio = 0.0F;
            }

            this.isMultiTouch = (boolean)1;
         } else if(var2 == 1) {
            this.isMultiTouch = (boolean)0;
         }

         var9 = false;
      }

      return var9;
   }

   protected void onScrollChanged(int var1, int var2, int var3, int var4) {
      if(this.mWebView != null) {
         this.mWebView.onScrollChanged(var1, var2, var3, var4);
      }

      super.onScrollChanged(var1, var2, var3, var4);
   }

   public boolean onTouchEvent(MotionEvent var1) {
      int var2 = var1.getAction();
      boolean var9;
      if(!this.isMultiTouch) {
         int var3 = var1.getPointerCount();
         byte var4 = 1;
         if(var3 <= var4) {
            if(var2 == 0 && var1.getEdgeFlags() != 0) {
               var9 = false;
               return var9;
            } else {
               if(!this.canScroll()) {
                  if(this.mZoomControl != null) {
                     this.mZoomControl.showControlsFromMove();
                  }

                  var9 = false;
               } else {
                  if(this.mVelocityTracker == null) {
                     VelocityTracker var10 = VelocityTracker.obtain();
                     this.mVelocityTracker = var10;
                  }

                  VelocityTracker var11 = this.mVelocityTracker;
                  var11.addMovement(var1);
                  float var13 = var1.getY();
                  float var14 = var1.getX();
                  switch(var2) {
                  case 0:
                     byte var15 = 0;
                     this.mWasDrag = (boolean)var15;
                     break;
                  case 1:
                  case 3:
                     byte var78 = 0;
                     this.mSnapScrollMode = var78;
                     VelocityTracker var79 = this.mVelocityTracker;
                     var79.computeCurrentVelocity(1000);
                     int var80 = (int)var79.getYVelocity();
                     int var81 = Math.abs(var80);
                     int var82 = ViewConfiguration.getMinimumFlingVelocity();
                     if(var81 > var82 && this.getChildCount() > 0) {
                        int var85 = -var80;
                        this.fling(var85);
                     }

                     if(this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        Object var88 = null;
                        this.mVelocityTracker = (VelocityTracker)var88;
                     }

                     if(this.isDraging) {
                        byte var90 = 3;
                        var1.setAction(var90);
                        if(this.mWebView != null) {
                           int var91 = this.mMode;
                           byte var92 = 10;
                           if(var91 != var92) {
                              HtcWebView var93 = this.mWebView;
                              var93.onTouchEvent(var1);
                           } else if(this.mScroller.isFinished()) {
                              HtcWebView var97 = this.mWebView;
                              var97.onTouchEvent(var1);
                           }
                        } else if(this.mEditableWebView != null) {
                           int var100 = this.mMode;
                           byte var101 = 10;
                           if(var100 == var101 && this.mScroller.isFinished()) {
                              HtcEditableWebView var102 = this.mEditableWebView;
                              var102.onTouchEvent(var1);
                           }
                        }

                        byte var96 = 0;
                        this.isDraging = (boolean)var96;
                     }
                     break;
                  case 2:
                     int var16 = (int)(this.mLastMotionY - var13);
                     this.mLastMotionY = var13;
                     int var18 = (int)(this.mLastMotionX - var14);
                     this.mLastMotionX = var14;
                     if(!this.isDraging) {
                        float var20 = this.mLastMotionY;
                        float var21 = this.mLastDownY;
                        if(Math.abs(var20 - var21) >= 10.0F) {
                           if(this.mWebView != null) {
                              int var22 = this.mMode;
                              byte var23 = 10;
                              if(var22 != var23) {
                                 MotionEvent var24 = MotionEvent.obtain(var1);
                                 var24.setAction(3);
                                 this.mWebView.onTouchEvent(var24);
                              }
                           }

                           byte var26 = 1;
                           this.isDraging = (boolean)var26;
                           byte var27 = 1;
                           this.mWasDrag = (boolean)var27;
                           if(var16 > 0 && this.mAccount != null && this.mAccount.getDownloadMessageWhenScroll() && this.mReadScreen != null && this.mReadScreen.unfinishedL != null) {
                              if(DEBUG) {
                                 int var28 = Log.d("ReadScreenScrollView", "shouldGetRemain");
                              }

                              this.mReadScreen.startScrollPageEnd();
                           }
                        }
                     }

                     if(var16 < 0) {
                        if(this.mScrollY > 0) {
                           byte var30 = 0;
                           this.scrollBy(var30, var16);
                        }
                     } else if(var16 > 0) {
                        int var51 = this.getHeight();
                        int var52 = this.mPaddingBottom;
                        int var53 = var51 - var52;
                        byte var55 = 0;
                        int var56 = this.getChildAt(var55).getBottom();
                        int var57 = this.mScrollY;
                        int var58 = var56 - var57 - var53;
                        if(var58 > 0) {
                           int var59 = Math.min(var58, var16);
                           byte var61 = 0;
                           this.scrollBy(var61, var59);
                        }
                     }

                     if(this.mSnapScrollMode == 0) {
                        int var32 = Math.abs(var16);
                        int var33 = ViewConfiguration.getTouchSlop();
                        if(var32 > var33) {
                           double var36 = (double)Math.abs(var18);
                           double var38 = (double)var16;
                           double var40 = Math.abs(0.0F * var38);
                           if(var36 > var40) {
                              byte var42 = 2;
                              this.mSnapScrollMode = var42;
                           } else {
                              double var63 = (double)Math.abs(var16);
                              double var65 = (double)var18;
                              double var67 = Math.abs(0.0F * var65);
                              if(var63 > var67) {
                                 byte var69 = 1;
                                 this.mSnapScrollMode = var69;
                              }
                           }
                        }
                     }

                     int var43 = this.mSnapScrollMode;
                     byte var44 = 1;
                     if(var43 != var44 && var18 != 0 && this.mWebView != null && this.mWebView.getVisibility() == 0) {
                        HtcWebView var45 = this.mWebView;
                        int var46 = this.mWebView.getScrollX() + var18;
                        int var47 = var45.pinLocXInternal(var46);
                        HtcWebView var48 = this.mWebView;
                        byte var50 = 0;
                        var48.scrollTo(var47, var50);
                     } else {
                        int var70 = this.mSnapScrollMode;
                        byte var71 = 1;
                        if(var70 != var71 && var18 != 0 && this.mEditableWebView != null && this.mEditableWebView.getVisibility() == 0) {
                           HtcEditableWebView var72 = this.mEditableWebView;
                           int var73 = this.mEditableWebView.getScrollX() + var18;
                           int var74 = var72.pinLocXInternal(var73);
                           HtcEditableWebView var75 = this.mEditableWebView;
                           byte var77 = 0;
                           var75.scrollTo(var74, var77);
                        }
                     }

                     if(this.mZoomControl != null) {
                        this.mZoomControl.showControlsFromMove();
                     }
                  }

                  var9 = true;
               }

               return var9;
            }
         }
      }

      if(!this.isMultiTouch) {
         MotionEvent var5 = MotionEvent.obtain(var1);
         var5.setAction(0);
         boolean var8 = this.dispatchTouchEvent(var5);
         var9 = false;
      } else {
         var9 = false;
      }

      return var9;
   }

   public void resetDragFlag() {
      this.mWasDrag = (boolean)0;
   }

   public void setAccount(Account var1) {
      this.mAccount = var1;
   }

   public void setEditableWebView(HtcEditableWebView var1) {
      this.mEditableWebView = var1;
   }

   public void setMode(int var1) {
      this.mMode = var1;
   }

   public void setReadScreen(ReadScreen var1) {
      this.mReadScreen = var1;
   }

   public void setWebView(HtcWebView var1) {
      this.mWebView = var1;
   }

   public void setZoomControl(ZoomControl var1) {
      this.mZoomControl = var1;
   }

   public boolean wasBeenScroll() {
      return this.mWasDrag;
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         long var1 = SystemClock.uptimeMillis();
         long var3 = SystemClock.uptimeMillis();
         float var5 = ReadScreenScrollView.this.mLastMotionX;
         float var6 = ReadScreenScrollView.this.mLastMotionY;
         MotionEvent var7 = MotionEvent.obtain(var1, var3, 3, var5, var6, 0);
         if(ReadScreenScrollView.this.mWebView != null) {
            boolean var8 = ReadScreenScrollView.this.mWebView.onTouchEvent(var7);
         } else if(ReadScreenScrollView.this.mEditableWebView != null) {
            boolean var9 = ReadScreenScrollView.this.mEditableWebView.onTouchEvent(var7);
         }
      }
   }
}

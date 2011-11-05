package com.htc.android.mail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.PictureListener;
import android.widget.FrameLayout.LayoutParams;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ReadScreenScrollView;
import com.htc.android.mail.ll;

public class HtcWebView extends WebView {

   private static final boolean DEBUG = true;
   public static final int MAX_SCAN_NODE = 10;
   public static final int TARGET_FONT_SIZE = 14;
   boolean firstSelecting = 0;
   boolean heightChanged = 0;
   boolean isPreventParentIntecept = 0;
   private View mBottomBar;
   private int mBottomViewHeight = 0;
   boolean mCheckFirstHeightChange = 0;
   private Context mContext;
   float mDownX = 0.0F;
   float mDownY = 0.0F;
   private boolean mEnableEditorWhenActionUp = 0;
   private boolean mIsEnableQuickSelection = 0;
   private int mLastContentHeight = 0;
   private int mLastTitleHeight = 0;
   private int mNowTitleHeight = 0;
   private ReadScreenScrollView mReadScreenScrollView;
   private View mTitleBar;


   public HtcWebView(Context var1) {
      super(var1);
      this.mContext = var1;
      boolean var2 = this.getSettings().isEnableQuickSelection();
      this.mIsEnableQuickSelection = var2;
   }

   public int FindNextTableNode(int var1) {
      return this.mHTCWebCore.nativeFindNextTableNode(var1);
   }

   public Rect GetNodeRect(int var1, boolean var2, int var3) {
      return this.mHTCWebCore.nativeGetNodeRect(var1, var2, var3);
   }

   public void clear() {
      this.clearCache((boolean)1);
      this.setPictureListener((PictureListener)null);
      this.setOnCreateContextMenuListener((OnCreateContextMenuListener)null);
      this.setOnLongClickListener((OnLongClickListener)null);
      this.setScrollView((ReadScreenScrollView)null);
      this.setWebViewClient((WebViewClient)null);
      this.setWebChromeClient((WebChromeClient)null);
      this.setOnCreateContextMenuListener((OnCreateContextMenuListener)null);
      this.SetOwnerActivityContext((Context)null);
   }

   protected void clearMemory() {
      this.freeMemory();
      this.clearWebCoreMemoryCache();
   }

   protected int computeVerticalScrollRange() {
      float var1 = (float)this.getContentHeight();
      float var2 = this.getScale();
      float var3 = var1 * var2;
      float var4 = (float)(this.mBottomViewHeight * 2);
      return (int)Math.floor((double)(var3 + var4));
   }

   protected boolean drawChild(Canvas var1, View var2, long var3) {
      View var5 = this.mBottomBar;
      if(var2 == var5) {
         View var6 = this.mBottomBar;
         int var7 = this.mScrollX;
         int var8 = this.mBottomBar.getLeft();
         int var9 = var7 - var8;
         var6.offsetLeftAndRight(var9);
      }

      return super.drawChild(var1, var2, var3);
   }

   public Bitmap getNodeCapture(int var1, int var2, int var3) {
      return this.mHTCWebCore.getNodeCapture(var1, var2, var3);
   }

   protected int getViewHeight() {
      return this.getHeight();
   }

   public float getZoomRatio() {
      float var1;
      if(this.mHTCWebCore == null) {
         var1 = 1.0F;
      } else {
         int var2 = this.mHTCWebCore.nativeFindNextTextNode(0);
         int var3 = 14;
         String var4 = " next node:" + var2;
         ll.d("HtcWebView", var4);

         for(int var5 = 0; var5 < 10 && var2 != 0; ++var5) {
            int var7 = this.mHTCWebCore.nativeGetFontPixelSizeOfTextNode(var2);
            var2 = this.mHTCWebCore.nativeFindNextTextNode(var2);
            String var8 = " size is " + var7 + ", next node:" + var2;
            ll.d("HtcWebView", var8);
            if(var7 != -1 && var3 > var7) {
               var3 = var7;
               String var9 = "smallestFont is " + var7;
               ll.d("HtcWebView", var9);
            }
         }

         String var6 = "final smallestFont is " + var3;
         ll.d("HtcWebView", var6);
         if(var3 >= 14) {
            ll.d("HtcWebView", "return 1");
            var1 = 1.0F;
         } else {
            StringBuilder var10 = (new StringBuilder()).append("return ");
            float var11 = (float)var3;
            float var12 = 14.0F / var11;
            String var13 = var10.append(var12).toString();
            ll.d("HtcWebView", var13);
            float var14 = (float)var3;
            var1 = 14.0F / var14;
         }
      }

      return var1;
   }

   public boolean isEnableQuickSelection() {
      return this.mIsEnableQuickSelection;
   }

   protected void onDraw(Canvas var1) {
      try {
         super.onDraw(var1);
      } catch (OutOfMemoryError var5) {
         String var3 = "out of memory in onDraw():" + var5;
         int var4 = Log.d("htcWebView", var3);
      }
   }

   protected void onFocusChanged(boolean var1, int var2, Rect var3) {
      super.onFocusChanged(var1, var2, var3);
      if(!var1) {
         if(this.mIsEnableQuickSelection) {
            if(this.mReadScreenScrollView != null) {
               if(this.mReadScreenScrollView.getMode() == 10) {
                  this.selectionDone();
               }
            }
         }
      }
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = this.mBottomViewHeight + var2;
      super.onMeasure(var1, var3);
   }

   public void onScrollChanged(int var1, int var2, int var3, int var4) {
      super.onScrollChanged(var1, var2, var3, var4);
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      if(this.mReadScreenScrollView != null) {
         float var5 = this.mReadScreenScrollView.ScrollToRatio();
      }

      if(this.mCheckFirstHeightChange && this.heightChanged && var2 >= var4) {
         this.mCheckFirstHeightChange = (boolean)0;
         this.heightChanged = (boolean)0;
      } else {
         String var6 = "after call Set New Layout Height w = " + var1 + " h = " + var2 + " ow = " + var3 + " oh = " + var4;
         int var7 = Log.d("htcWebView", var6);
         super.onSizeChanged(var1, var2, var3, var4);
         if(!this.heightChanged) {
            if(var2 != var4) {
               if(var2 > 0) {
                  this.heightChanged = (boolean)1;
               }
            }
         }
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      int var2 = var1.getAction();
      float var3 = var1.getX();
      float var4 = var1.getY();
      if(Mail.MAIL_DETAIL_DEBUG) {
         String var5 = "[HtcWebView::onTouchEvent] >> ev=" + var1;
         int var6 = Log.i("HtcWebView", var5);
      }

      switch(var2) {
      case 0:
         this.mDownX = var3;
         this.mDownY = var4;
         break;
      case 1:
      case 3:
         this.isPreventParentIntecept = (boolean)0;
         this.firstSelecting = (boolean)0;
         break;
      case 2:
         if(this.selectDialogIsUp() && !this.firstSelecting) {
            if(!this.isPreventParentIntecept) {
               float var7 = this.mDownX;
               if(Math.abs(var3 - var7) >= 10.0F && this.getParent() != null) {
                  this.getParent().requestDisallowInterceptTouchEvent((boolean)1);
                  this.isPreventParentIntecept = (boolean)1;
                  float var8 = this.mDownY;
                  float var9 = var4 - var8;
                  var1.offsetLocation(0.0F, var9);
               } else {
                  float var10 = this.mDownX;
                  float var11 = this.mDownY;
                  var1.setLocation(var10, var11);
               }
            } else {
               float var12 = this.mDownY;
               float var13 = var4 - var12;
               var1.offsetLocation(0.0F, var13);
            }
         }
      }

      return super.onTouchEvent(var1);
   }

   public boolean onTrackballEvent(MotionEvent var1) {
      return super.onTrackballEvent(var1);
   }

   public boolean performLongClick() {
      if(this.getParent() != null) {
         this.getParent().requestDisallowInterceptTouchEvent((boolean)1);
      }

      boolean var1 = super.performLongClick();
      if(this.selectDialogIsUp()) {
         this.firstSelecting = (boolean)1;
      }

      return var1;
   }

   public int pinLocXInternal(int var1) {
      int var2 = this.getWidth();
      int var3 = this.computeHorizontalScrollRange();
      if(var3 < var2) {
         var1 = 0;
      } else if(var1 >= 0 && var1 + var2 > var3) {
         var1 = var3 - var2;
      }

      return var1;
   }

   public int pinLocYInternal(int var1) {
      int var2 = this.getHeight();
      int var3 = this.computeVerticalScrollRange();
      int var4;
      if(this.mTitleBar != null) {
         var4 = this.mTitleBar.getHeight();
      } else {
         var4 = 0;
      }

      int var5;
      if(var1 < var4) {
         var5 = var1;
      } else {
         var1 -= var4;
         if(var3 < var2) {
            var1 = 0;
         } else if(var1 < 0) {
            var1 = 0;
         } else if(var1 + var2 > var3) {
            var1 = var3 - var2;
         }

         var5 = var1 + var4;
      }

      return var5;
   }

   public void removeEmbeddedBottomBar() {
      this.mBottomViewHeight = 0;
      View var1 = this.mBottomBar;
      this.removeView(var1);
      this.mBottomBar = null;
   }

   public void resetEmbeddedBottomLoc() {
      int var1 = this.getContentHeight();
      if(this.mLastContentHeight == var1) {
         int var2 = this.mLastTitleHeight;
         int var3 = this.mTitleBar.getHeight();
         if(var2 == var3) {
            return;
         }
      }

      String var4 = "resetEmbeddedBottomLoc " + var1;
      int var5 = Log.d("htcWebView", var4);
      this.mLastContentHeight = var1;
      int var6 = this.mTitleBar.getHeight();
      this.mLastTitleHeight = var6;
      if(this.mBottomBar != null) {
         if(this.mTitleBar == null) {
            View var7 = this.mBottomBar;
            LayoutParams var8 = new LayoutParams(-1, -1);
            var7.setLayoutParams(var8);
         } else {
            View var9 = this.mBottomBar;
            LayoutParams var10 = new LayoutParams(-1, -1);
            var9.setLayoutParams(var10);
         }
      }
   }

   public void setEmbeddedBottomBar(View var1, int var2) {
      if(var1 != null) {
         if(this.mTitleBar == null) {
            LayoutParams var3 = new LayoutParams(-1, -1);
            this.addView(var1, var3);
         } else {
            LayoutParams var5 = new LayoutParams(-1, -1);
            this.addView(var1, var5);
         }
      }

      this.mBottomBar = var1;
      int var4 = this.mTitleBar.getHeight();
      this.mLastTitleHeight = var4;
      if(this.mBottomBar != null) {
         this.mBottomViewHeight = var2;
      }
   }

   public void setEmbeddedTitleBar(View var1) {
      this.mTitleBar = var1;
      super.setEmbeddedTitleBar(var1);
   }

   public void setEnableQuickSelection(boolean var1) {
      if(this.mIsEnableQuickSelection != var1) {
         this.mIsEnableQuickSelection = var1;
         this.getSettings().setEnableQuickSelection(var1);
      }
   }

   public void setNewZoomScale(float var1, boolean var2) {
      super.setNewZoomScale(var1, (boolean)0, var2);
   }

   public void setScrollView(ReadScreenScrollView var1) {
      this.mReadScreenScrollView = var1;
   }

   public void updateEmbeddedTitleBar(int var1) {
      if(this.mNowTitleHeight == 0) {
         int var2 = this.mTitleBar.getHeight();
         this.mNowTitleHeight = var2;
      }

      if(this.mTitleBar != null) {
         View var3 = this.mTitleBar;
         int var4 = this.mNowTitleHeight + var1;
         LayoutParams var5 = new LayoutParams(-1, var4);
         var3.setLayoutParams(var5);
      }

      StringBuilder var6 = (new StringBuilder()).append(" title ");
      int var7 = this.mNowTitleHeight;
      String var8 = var6.append(var7).append(",").append(var1).toString();
      int var9 = Log.d("HtcWebView", var8);
   }
}

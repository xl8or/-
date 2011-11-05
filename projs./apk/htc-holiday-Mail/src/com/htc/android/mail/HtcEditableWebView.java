package com.htc.android.mail;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.webkit.EditableWebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.EditableWebView.OnContentChangedListener;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewSelectionMethod.SelectionMode;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ReadScreenScrollView;

public class HtcEditableWebView extends EditableWebView {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   boolean isPreventParentIntecept = 0;
   private boolean mDestory = 0;
   float mDownX = 0.0F;
   float mDownY = 0.0F;
   private ReadScreenScrollView mReadScreenScrollView;


   public HtcEditableWebView(Context var1) {
      super(var1);
   }

   public void clear() {
      this.clearCache((boolean)1);
      this.setPictureListener((PictureListener)null);
      this.setOnCreateContextMenuListener((OnCreateContextMenuListener)null);
      this.setOnLongClickListener((OnLongClickListener)null);
      this.setOnContentChangedListener((OnContentChangedListener)null);
      this.setWebViewClient((WebViewClient)null);
      this.setWebChromeClient((WebChromeClient)null);
   }

   public void destroy() {
      this.mDestory = (boolean)1;
      super.destroy();
   }

   public void onEndSelect(boolean var1) {
      super.onEndSelect(var1);
      this.mReadScreenScrollView.setMode(0);
   }

   public boolean onTouchEvent(MotionEvent var1) {
      float var2 = var1.getX();
      float var3 = var1.getY();
      if(DEBUG) {
         String var4 = "[HtcEditableWebView::onTouchEvent] >> ev=" + var1 + "," + var2 + "," + var3;
         int var5 = Log.i("HtcEditableWebView", var4);
      }

      switch(var1.getAction()) {
      case 0:
         this.mDownX = var2;
         this.mDownY = var3;
         SelectionMode var6 = this.getSelectionMethod().getMode();
         SelectionMode var7 = SelectionMode.NONE;
         if(var6 != var7) {
            this.mReadScreenScrollView.setMode(10);
         }
         break;
      case 1:
      default:
         this.isPreventParentIntecept = (boolean)0;
         break;
      case 2:
         SelectionMode var8 = this.getSelectionMethod().getMode();
         SelectionMode var9 = SelectionMode.EXTENDABLE;
         if(var8 == var9) {
            if(!this.isPreventParentIntecept) {
               float var10 = this.mDownX;
               if(Math.abs(var2 - var10) >= 10.0F && this.getParent() != null) {
                  this.getParent().requestDisallowInterceptTouchEvent((boolean)1);
                  this.isPreventParentIntecept = (boolean)1;
               }
            }

            float var11 = this.mDownY;
            float var12 = var3 - var11;
            var1.offsetLocation(0.0F, var12);
         }
      }

      return super.onTouchEvent(var1);
   }

   public boolean onTouchEventForEditable(MotionEvent var1) {
      float var2 = var1.getX();
      float var3 = var1.getY();
      if(DEBUG) {
         String var4 = "[HtcEditableWebView::onTouchEventForEditable] >> ev=" + var1 + "," + var2 + "," + var3;
         int var5 = Log.i("HtcEditableWebView", var4);
      }

      return super.onTouchEventForEditable(var1);
   }

   public void onWindowFocusChanged(boolean var1) {
      if(!this.mDestory) {
         super.onWindowFocusChanged(var1);
      }
   }

   public boolean performLongClick() {
      if(this.getParent() != null) {
         this.getParent().requestDisallowInterceptTouchEvent((boolean)1);
      }

      return super.performLongClick();
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

   void setScrollView(ReadScreenScrollView var1) {
      this.mReadScreenScrollView = var1;
   }
}

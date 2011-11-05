package com.google.android.finsky.layout;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LayoutSwitcher {

   public static final int BLANK_MODE = 3;
   public static final int DATA_MODE = 2;
   public static final int ERROR_MODE = 1;
   public static final int LOADING_MODE;
   private final View mContentLayout;
   private int mDataLayoutId;
   private final int mErrorLayoutId;
   private final Handler mHandler;
   private final int mLoadingLayoutId;
   private int mMode;
   private boolean mPendingLoad;
   private final LayoutSwitcher.RetryButtonListener mRetryListener;
   private final OnClickListener retryClickListener;


   public LayoutSwitcher(View var1, int var2, int var3, int var4, LayoutSwitcher.RetryButtonListener var5) {
      Handler var6 = new Handler();
      this.mHandler = var6;
      LayoutSwitcher.1 var7 = new LayoutSwitcher.1();
      this.retryClickListener = var7;
      this.mPendingLoad = (boolean)0;
      this.mDataLayoutId = var2;
      this.mErrorLayoutId = var3;
      this.mLoadingLayoutId = var4;
      this.mContentLayout = var1;
      this.mRetryListener = var5;
      this.resetMode();
   }

   public LayoutSwitcher(View var1, int var2, int var3, int var4, LayoutSwitcher.RetryButtonListener var5, int var6) {
      Handler var7 = new Handler();
      this.mHandler = var7;
      LayoutSwitcher.1 var8 = new LayoutSwitcher.1();
      this.retryClickListener = var8;
      this.mPendingLoad = (boolean)0;
      this.mDataLayoutId = var2;
      this.mErrorLayoutId = var3;
      this.mLoadingLayoutId = var4;
      this.mContentLayout = var1;
      this.mRetryListener = var5;
      this.mMode = var6;
   }

   public LayoutSwitcher(View var1, int var2, LayoutSwitcher.RetryButtonListener var3) {
      this(var1, var2, 2131755193, 2131755150, var3);
   }

   private void performSwitch(int var1, String var2) {
      this.mPendingLoad = (boolean)0;
      if(this.mMode != var1) {
         switch(this.mMode) {
         case 0:
            this.setLoadingVisible((boolean)0);
            break;
         case 1:
            this.setErrorVisible((boolean)0, (String)null);
            break;
         case 2:
            this.setDataVisible((boolean)0);
         }

         switch(var1) {
         case 0:
            this.setLoadingVisible((boolean)1);
            break;
         case 1:
            this.setErrorVisible((boolean)1, var2);
            break;
         case 2:
            this.setDataVisible((boolean)1);
         case 3:
            break;
         default:
            String var3 = "Invalid mode " + var1 + "should be LOADING_MODE, ERROR_MODE, DATA_MODE, or BLANK_MODE";
            throw new IllegalStateException(var3);
         }

         this.mMode = var1;
      }
   }

   private void resetMode() {
      this.mMode = 3;
      this.setLoadingVisible((boolean)0);
      this.setErrorVisible((boolean)0, (String)null);
      this.setDataVisible((boolean)0);
   }

   private void setDataVisible(boolean var1) {
      View var2 = this.mContentLayout;
      int var3 = this.mDataLayoutId;
      ViewGroup var4 = (ViewGroup)var2.findViewById(var3);
      byte var5;
      if(var1) {
         var5 = 0;
      } else {
         var5 = 8;
      }

      var4.setVisibility(var5);
   }

   private void setErrorVisible(boolean var1, String var2) {
      View var3 = this.mContentLayout;
      int var4 = this.mErrorLayoutId;
      View var5 = var3.findViewById(var4);
      byte var6;
      if(var1) {
         var6 = 0;
      } else {
         var6 = 8;
      }

      var5.setVisibility(var6);
      if(var1) {
         ((TextView)var5.findViewById(2131755191)).setText(var2);
      }

      Button var7 = (Button)var5.findViewById(2131755192);
      OnClickListener var8;
      if(var1) {
         var8 = this.retryClickListener;
      } else {
         var8 = null;
      }

      var7.setOnClickListener(var8);
   }

   private void setLoadingVisible(boolean var1) {
      View var2 = this.mContentLayout;
      int var3 = this.mLoadingLayoutId;
      View var4 = var2.findViewById(var3);
      byte var5;
      if(var1) {
         var5 = 0;
      } else {
         var5 = 8;
      }

      var4.setVisibility(var5);
   }

   public boolean isDataMode() {
      boolean var1;
      if(this.mMode == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void switchToBlankMode() {
      this.performSwitch(3, (String)null);
   }

   public void switchToDataMode() {
      this.performSwitch(2, (String)null);
   }

   public void switchToDataMode(int var1) {
      this.mDataLayoutId = var1;
      this.switchToDataMode();
   }

   public void switchToErrorMode(String var1) {
      this.performSwitch(1, var1);
   }

   public void switchToLoadingDelayed(int var1) {
      this.mPendingLoad = (boolean)1;
      Handler var2 = this.mHandler;
      LayoutSwitcher.2 var3 = new LayoutSwitcher.2();
      long var4 = (long)var1;
      var2.postDelayed(var3, var4);
   }

   public void switchToLoadingMode() {
      this.performSwitch(0, (String)null);
   }

   public interface RetryButtonListener {

      void onRetry();
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         LayoutSwitcher.this.switchToLoadingMode();
         LayoutSwitcher.this.mRetryListener.onRetry();
      }
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         if(LayoutSwitcher.this.mPendingLoad) {
            LayoutSwitcher.this.switchToLoadingMode();
         }
      }
   }
}

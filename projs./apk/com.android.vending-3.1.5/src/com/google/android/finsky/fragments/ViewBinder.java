package com.google.android.finsky.fragments;

import android.content.Context;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;

public abstract class ViewBinder<T extends Object> {

   protected BitmapLoader mBitmapLoader;
   protected Context mContext;
   protected T mData;
   protected NavigationManager mNavManager;


   public ViewBinder() {}

   public boolean hasData() {
      boolean var1;
      if(this.mData != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void init(Context var1, NavigationManager var2, BitmapLoader var3) {
      if(this.mContext != var1) {
         this.mContext = var1;
         this.mNavManager = var2;
         this.mBitmapLoader = var3;
         this.mData = null;
      }
   }

   public void setData(T var1) {
      this.mData = var1;
   }
}

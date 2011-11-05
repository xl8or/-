package com.seven.Z7.app;

import android.os.Parcel;
import android.text.style.URLSpan;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomURLSpan extends URLSpan {

   private OnClickListener mOnClickDelegate;


   public CustomURLSpan(Parcel var1) {
      super(var1);
   }

   public CustomURLSpan(String var1) {
      super(var1);
   }

   public OnClickListener getOnClickDelegate() {
      return this.mOnClickDelegate;
   }

   public void onClick(View var1) {
      if(this.mOnClickDelegate != null) {
         this.mOnClickDelegate.onClick(var1);
      }

      super.onClick(var1);
   }

   public void setOnClickDelegate(OnClickListener var1) {
      this.mOnClickDelegate = var1;
   }
}

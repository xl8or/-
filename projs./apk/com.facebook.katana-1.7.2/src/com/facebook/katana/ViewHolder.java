package com.facebook.katana;

import android.view.View;
import android.widget.ImageView;

public class ViewHolder<T extends Object> {

   public final ImageView mImageView;
   private T mItemId;


   public ViewHolder(View var1, int var2) {
      ImageView var3 = (ImageView)var1.findViewById(var2);
      this.mImageView = var3;
   }

   public T getItemId() {
      return this.mItemId;
   }

   public void setItemId(T var1) {
      this.mItemId = var1;
   }
}

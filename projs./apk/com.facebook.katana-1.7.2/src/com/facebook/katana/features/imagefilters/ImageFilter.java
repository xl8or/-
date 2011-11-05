package com.facebook.katana.features.imagefilters;

import android.graphics.Bitmap;

public abstract class ImageFilter {

   private String mName;


   public ImageFilter(String var1) {
      this.mName = var1;
   }

   public abstract Bitmap applyFilter(Bitmap var1);

   public String getName() {
      return this.mName;
   }
}

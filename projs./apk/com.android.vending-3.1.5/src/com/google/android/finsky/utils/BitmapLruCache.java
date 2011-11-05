package com.google.android.finsky.utils;

import android.graphics.Bitmap;
import com.google.android.finsky.utils.LruCache;

public class BitmapLruCache extends LruCache<String, Bitmap> {

   public BitmapLruCache(int var1) {
      super(var1);
   }

   protected int sizeOf(String var1, Bitmap var2) {
      int var3 = var2.getRowBytes();
      int var4 = var2.getHeight();
      return var3 * var4;
   }
}

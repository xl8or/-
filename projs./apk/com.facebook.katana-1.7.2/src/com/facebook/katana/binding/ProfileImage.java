package com.facebook.katana.binding;

import android.graphics.Bitmap;
import java.lang.ref.SoftReference;

public class ProfileImage {

   public final long id;
   private SoftReference<Bitmap> mBmpSoftReference;
   private int mUsageCount;
   public final String url;


   public ProfileImage(long var1, String var3, Bitmap var4) {
      this.id = var1;
      this.url = var3;
      this.mUsageCount = 0;
      SoftReference var5 = new SoftReference(var4);
      this.mBmpSoftReference = var5;
   }

   public Bitmap getBitmap() {
      return (Bitmap)this.mBmpSoftReference.get();
   }

   public int getUsageCount() {
      return this.mUsageCount;
   }

   public void incrUsageCount() {
      int var1 = this.mUsageCount + 1;
      this.mUsageCount = var1;
   }

   public void setBitmap(Bitmap var1) {
      SoftReference var2 = new SoftReference(var1);
      this.mBmpSoftReference = var2;
   }
}

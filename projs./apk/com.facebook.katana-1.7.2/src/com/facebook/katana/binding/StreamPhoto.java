package com.facebook.katana.binding;

import android.graphics.Bitmap;
import android.net.Uri;
import java.lang.ref.SoftReference;

public class StreamPhoto {

   private SoftReference<Bitmap> mBmpSoftReference;
   private final Uri mContentUri;
   private final String mFilename;
   private final long mLength;
   private final String mUrl;
   private int mUsageCount;


   public StreamPhoto(Uri var1, String var2, String var3, long var4, Bitmap var6) {
      this.mContentUri = var1;
      this.mUrl = var2;
      this.mFilename = var3;
      this.mLength = var4;
      this.mUsageCount = 0;
      SoftReference var7 = new SoftReference(var6);
      this.mBmpSoftReference = var7;
   }

   public Bitmap getBitmap() {
      return (Bitmap)this.mBmpSoftReference.get();
   }

   public Uri getContentUri() {
      return this.mContentUri;
   }

   public String getFilename() {
      return this.mFilename;
   }

   public long getLength() {
      return this.mLength;
   }

   public String getUrl() {
      return this.mUrl;
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

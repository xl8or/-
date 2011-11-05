package com.google.android.finsky.adapters;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.Doc;

public class ImageStripAdapter {

   private final DataSetObservable mDataSetObservable;
   private final String[] mIds;
   private final int mImageCount;
   private final Doc.Image.Dimension[] mImageDimensions;
   private final Drawable[] mImages;
   private final Document[] mTags;


   public ImageStripAdapter(int var1) {
      this.mImageCount = var1;
      String[] var2 = new String[this.mImageCount];
      this.mIds = var2;
      Drawable[] var3 = new Drawable[this.mImageCount];
      this.mImages = var3;
      Doc.Image.Dimension[] var4 = new Doc.Image.Dimension[this.mImageCount];
      this.mImageDimensions = var4;
      Document[] var5 = new Document[this.mImageCount];
      this.mTags = var5;
      DataSetObservable var6 = new DataSetObservable();
      this.mDataSetObservable = var6;
   }

   public void getDimensionAt(int var1, Doc.Image.Dimension var2, float var3) {
      Drawable var4 = this.getImageAt(var1);
      if(var4 != null) {
         int var5 = (int)((float)var4.getIntrinsicWidth() * var3);
         var2.setWidth(var5);
         int var7 = (int)((float)var4.getIntrinsicHeight() * var3);
         var2.setHeight(var7);
      } else if(this.mImageDimensions[var1] != false) {
         int var9 = this.mImageDimensions[var1].getWidth();
         var2.setWidth(var9);
         int var11 = this.mImageDimensions[var1].getHeight();
         var2.setHeight(var11);
      } else {
         Doc.Image.Dimension var13 = var2.setWidth(0);
         Doc.Image.Dimension var14 = var2.setHeight(0);
      }
   }

   public Drawable getImageAt(int var1) {
      return this.mImages[var1];
   }

   public int getImageCount() {
      return this.mImageCount;
   }

   public Document getTagAt(int var1) {
      return this.mTags[var1];
   }

   public void notifyDataSetChanged() {
      this.mDataSetObservable.notifyChanged();
   }

   public void notifyDataSetInvalidated() {
      this.mDataSetObservable.notifyInvalidated();
   }

   public void recycleBitmaps() {
      int var1 = this.getImageCount();

      for(int var2 = 0; var2 < var1; ++var2) {
         BitmapDrawable var3 = (BitmapDrawable)this.getImageAt(var2);
         Bitmap var4;
         if(var3 != null) {
            var4 = var3.getBitmap();
         } else {
            var4 = null;
         }

         if(var4 != null) {
            var4.recycle();
         }

         this.mImages[var2] = false;
      }

   }

   public void registerDataSetObserver(DataSetObserver var1) {
      this.mDataSetObservable.registerObserver(var1);
   }

   public void resetDrawables() {
      int var1 = 0;

      while(true) {
         int var2 = this.mImages.length;
         if(var1 >= var2) {
            return;
         }

         this.mImages[var1] = false;
         ++var1;
      }
   }

   public void setDimensionAt(int var1, Doc.Image.Dimension var2) {
      this.mImageDimensions[var1] = var2;
   }

   public void setIdAt(int var1, String var2) {
      this.mIds[var1] = var2;
   }

   public void setImageAt(int var1, Drawable var2) {
      this.mImages[var1] = var2;
      this.notifyDataSetChanged();
   }

   public void setImageAt(String var1, Drawable var2) {
      int var3 = 0;

      while(true) {
         int var4 = this.mImageCount;
         if(var3 >= var4) {
            return;
         }

         String var5 = this.mIds[var3];
         if(var5 != null && var5.compareTo(var1) == 0) {
            this.setImageAt(var3, var2);
            return;
         }

         ++var3;
      }
   }

   public void setTagAt(int var1, Document var2) {
      this.mTags[var1] = var2;
   }

   public void unregisterAll() {
      this.mDataSetObservable.unregisterAll();
   }

   public void unregisterDataSetObserver(DataSetObserver var1) {
      this.mDataSetObservable.unregisterObserver(var1);
   }
}

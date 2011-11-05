package com.google.android.finsky.layout;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.ThumbnailUtils;

public class ThumbnailListener implements BitmapLoader.BitmapLoadedHandler {

   private final boolean mFadeIn;
   protected final ImageView mImageView;
   private final View mViewToBeUpdated;


   public ThumbnailListener(ImageView var1, View var2, boolean var3) {
      this.mImageView = var1;
      this.mViewToBeUpdated = var2;
      this.mFadeIn = var3;
   }

   public ThumbnailListener(ImageView var1, boolean var2) {
      this.mImageView = var1;
      this.mViewToBeUpdated = null;
      this.mFadeIn = var2;
   }

   protected void onImageFailed() {}

   public void onResponse(BitmapLoader.BitmapContainer var1) {
      Bitmap var2 = var1.getBitmap();
      if(var2 == null) {
         this.onImageFailed();
      } else {
         if(this.mViewToBeUpdated != null) {
            this.mImageView.getLayoutParams().width = -1;
            this.mImageView.getLayoutParams().height = -1;
            LayoutParams var3 = this.mViewToBeUpdated.getLayoutParams();
            int var4 = var2.getWidth();
            int var5 = this.mImageView.getPaddingLeft();
            int var6 = var4 + var5;
            int var7 = this.mImageView.getPaddingRight();
            int var8 = var6 + var7;
            var3.width = var8;
         }

         if(this.mFadeIn) {
            ThumbnailUtils.setImageBitmapWithFade(this.mImageView, var2);
         } else {
            this.mImageView.setImageBitmap(var2);
         }
      }
   }
}

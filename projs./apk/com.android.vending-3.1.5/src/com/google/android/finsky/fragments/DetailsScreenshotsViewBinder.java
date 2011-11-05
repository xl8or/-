package com.google.android.finsky.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.google.android.finsky.adapters.ImageStripAdapter;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.layout.HorizontalStrip;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.Doc;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Iterator;
import java.util.List;

public class DetailsScreenshotsViewBinder extends DetailsViewBinder implements LayoutSwitcher.RetryButtonListener {

   private BitmapLoader mBitmapLoader;
   private HorizontalStrip mImageStrip;
   private ImageStripAdapter mImageStripAdapter;
   private int mLoadRequestCount = 0;
   private int mMaxImageHeight = 0;
   private int mNumImagesFailed;
   private List<Doc.Image> mPreviewImages;
   private int mScreenshotsRightPadding;
   private int mScreenshotsSpacing;


   public DetailsScreenshotsViewBinder() {}

   // $FF: synthetic method
   static int access$204(DetailsScreenshotsViewBinder var0) {
      int var1 = var0.mNumImagesFailed + 1;
      var0.mNumImagesFailed = var1;
      return var1;
   }

   private void loadImages() {
      int var1 = 1;
      int var2 = this.mLoadRequestCount + 1;
      this.mLoadRequestCount = var2;
      int var3 = this.mPreviewImages.size();
      this.mNumImagesFailed = 0;

      for(Iterator var4 = this.mPreviewImages.iterator(); var4.hasNext(); ++var1) {
         Doc.Image var5 = (Doc.Image)var4.next();
         ImageStripAdapter var6 = this.mImageStripAdapter;
         Doc.Image.Dimension var7 = var5.getDimension();
         var6.setDimensionAt(var1, var7);
         BitmapLoader var9 = this.mBitmapLoader;
         String var10 = var5.getImageUrl();
         DetailsScreenshotsViewBinder.2 var11 = new DetailsScreenshotsViewBinder.2(var2, var3, var1);
         int var12 = this.mMaxImageHeight;
         BitmapLoader.BitmapContainer var13 = var9.get(var10, (Bitmap)null, var11, 0, var12);
         if(var13.getBitmap() != null) {
            ImageStripAdapter var14 = this.mImageStripAdapter;
            Bitmap var15 = var13.getBitmap();
            BitmapDrawable var16 = new BitmapDrawable(var15);
            var14.setImageAt(var1, var16);
         }
      }

   }

   public void bind(View var1, Document var2) {
      super.bind(var1, var2, 2131755140, 2131230976);
      List var3;
      if(this.mDoc.hasScreenshots()) {
         var3 = this.mDoc.getImages(1);
      } else {
         var3 = null;
      }

      this.mPreviewImages = var3;
      if(this.mPreviewImages == null) {
         this.mLayout.setVisibility(8);
      } else {
         this.mLayout.setVisibility(0);
         HorizontalStrip var4 = (HorizontalStrip)this.mLayout.findViewById(2131755155);
         this.mImageStrip = var4;
         HorizontalStrip var5 = this.mImageStrip;
         int var6 = this.mScreenshotsSpacing;
         var5.setLayoutMargin(var6);
         if(this.mImageStripAdapter != null) {
            this.mImageStripAdapter.unregisterAll();
         }

         int var7 = this.mPreviewImages.size() + 2;
         ImageStripAdapter var8 = new ImageStripAdapter(var7);
         this.mImageStripAdapter = var8;
         HorizontalStrip var9 = this.mImageStrip;
         ImageStripAdapter var10 = this.mImageStripAdapter;
         var9.setAdapter(var10);
         HorizontalStrip var11 = this.mImageStrip;
         DetailsScreenshotsViewBinder.1 var12 = new DetailsScreenshotsViewBinder.1();
         var11.setChildTappedListener(var12);
         ImageStripAdapter var13 = this.mImageStripAdapter;
         int var14 = this.mPreviewImages.size() + 1;
         Doc.Image.Dimension var15 = new Doc.Image.Dimension();
         int var16 = this.mScreenshotsRightPadding;
         Doc.Image.Dimension var17 = var15.setWidth(var16);
         var13.setDimensionAt(var14, var17);
         View var18 = this.mLayout;
         LayoutSwitcher var19 = new LayoutSwitcher(var18, 2131755155, this);
         this.mLayoutSwitcher = var19;
         this.mLayoutSwitcher.switchToDataMode();
         this.loadImages();
      }
   }

   public void init(Context var1, BitmapLoader var2, NavigationManager var3) {
      super.init(var1, (DfeApi)null, var3);
      this.mBitmapLoader = var2;
      Resources var4 = this.mContext.getResources();
      int var5 = var4.getDimensionPixelOffset(2131427377);
      this.mScreenshotsSpacing = var5;
      int var6 = var4.getDimensionPixelOffset(2131427378);
      this.mScreenshotsRightPadding = var6;
      int var7 = var4.getDimensionPixelOffset(2131427376);
      this.mMaxImageHeight = var7;
   }

   public void onRetry() {
      this.loadImages();
   }

   class 1 implements HorizontalStrip.OnChildViewTapListener {

      1() {}

      public void onChildViewTap(int var1) {
         int var2 = var1 + -1;
         int var3 = DetailsScreenshotsViewBinder.this.mPreviewImages.size();
         if(var2 < var3) {
            if(var1 > 0) {
               NavigationManager var4 = DetailsScreenshotsViewBinder.this.mNavigationManager;
               Document var5 = DetailsScreenshotsViewBinder.this.mDoc;
               int var6 = var1 + -1;
               var4.goToScreenshots(var5, var6);
            }
         }
      }
   }

   class 2 implements BitmapLoader.BitmapLoadedHandler {

      // $FF: synthetic field
      final int val$index;
      // $FF: synthetic field
      final int val$loadId;
      // $FF: synthetic field
      final int val$numImagesToLoad;


      2(int var2, int var3, int var4) {
         this.val$loadId = var2;
         this.val$numImagesToLoad = var3;
         this.val$index = var4;
      }

      public void onResponse(BitmapLoader.BitmapContainer var1) {
         int var2 = DetailsScreenshotsViewBinder.this.mLoadRequestCount;
         int var3 = this.val$loadId;
         if(var2 != var3) {
            Object[] var4 = new Object[2];
            Integer var5 = Integer.valueOf(DetailsScreenshotsViewBinder.this.mLoadRequestCount);
            var4[0] = var5;
            Integer var6 = Integer.valueOf(this.val$loadId);
            var4[1] = var6;
            FinskyLog.w("Expected response for load %s but got %s", var4);
         } else {
            Bitmap var7 = var1.getBitmap();
            if(var7 == null) {
               int var8 = DetailsScreenshotsViewBinder.access$204(DetailsScreenshotsViewBinder.this);
               int var9 = this.val$numImagesToLoad;
               if(var8 == var9) {
                  LayoutSwitcher var10 = DetailsScreenshotsViewBinder.this.mLayoutSwitcher;
                  String var11 = DetailsScreenshotsViewBinder.this.mContext.getString(2131231082);
                  var10.switchToErrorMode(var11);
               }
            } else {
               ImageStripAdapter var12 = DetailsScreenshotsViewBinder.this.mImageStripAdapter;
               int var13 = this.val$index;
               BitmapDrawable var14 = new BitmapDrawable(var7);
               var12.setImageAt(var13, var14);
            }
         }
      }
   }
}

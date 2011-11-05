package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.adapters.AggregatedAdapter;
import com.google.android.finsky.adapters.DownloadProgressHelper;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.ThumbnailUtils;
import com.google.android.vending.model.Asset;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MyDownloadsAdapter extends BaseAdapter {

   public static final int TYPE_ASSET = 1;
   private static final int TYPE_DOWNLOADING_ASSET = 2;
   public static final int TYPE_HEADER;
   private static final Collator sAssetAbcCollator = Collator.getInstance();
   private static final Comparator<Asset> sAssetAbcSorter = new MyDownloadsAdapter.1();
   private static BitmapDrawable sLoadingImageBitmap;
   private final AggregatedAdapter<MyDownloadsAdapter.SectionAdapter> mAggregatedAdapter;
   protected Context mContext;
   private final MyDownloadsAdapter.SectionAdapter mDownloadingSectionAdapter;
   private final boolean mHasAssetView;
   private final MyDownloadsAdapter.SectionAdapter mInstalledSectionAdapter;
   private final Installer mInstaller;
   protected final LayoutInflater mLayoutInflater;
   protected ListView mListView;
   private final OnClickListener mListener;
   private final MyDownloadsAdapter.SectionAdapter mManualUpdatesSectionAdapter;
   private final MyDownloadsAdapter.SectionAdapter mOwnedSectionAdapter;
   private final List<Asset> mUnsortedAssets;
   private final MyDownloadsAdapter.SectionAdapter mUpdatesSectionAdapter;


   public MyDownloadsAdapter(Context var1, Installer var2, OnClickListener var3, boolean var4) {
      ArrayList var5 = new ArrayList();
      this.mUnsortedAssets = var5;
      this.mContext = var1;
      this.mInstaller = var2;
      this.mListener = var3;
      LayoutInflater var6 = LayoutInflater.from(var1);
      this.mLayoutInflater = var6;
      Resources var7 = var1.getResources();
      if(sLoadingImageBitmap == null && var7 != null) {
         sLoadingImageBitmap = (BitmapDrawable)var7.getDrawable(2130837608);
      }

      this.mHasAssetView = var4;
      MyDownloadsAdapter.AssetType var8 = MyDownloadsAdapter.AssetType.DOWNLOADING;
      MyDownloadsAdapter.AssetBulkAction var9 = MyDownloadsAdapter.AssetBulkAction.STOP_ALL_DOWNLOADS;
      MyDownloadsAdapter.SectionAdapter var10 = new MyDownloadsAdapter.SectionAdapter(var8, 2131230906, var9);
      this.mDownloadingSectionAdapter = var10;
      MyDownloadsAdapter.AssetType var11 = MyDownloadsAdapter.AssetType.UPDATE_AVAILABLE;
      MyDownloadsAdapter.AssetBulkAction var12 = MyDownloadsAdapter.AssetBulkAction.UPDATE_ALL;
      MyDownloadsAdapter.SectionAdapter var13 = new MyDownloadsAdapter.SectionAdapter(var11, 2131230907, var12);
      this.mUpdatesSectionAdapter = var13;
      MyDownloadsAdapter.AssetType var14 = MyDownloadsAdapter.AssetType.UPDATE_AVAILABLE;
      MyDownloadsAdapter.SectionAdapter var15 = new MyDownloadsAdapter.SectionAdapter(var14, 2131230908, (MyDownloadsAdapter.AssetBulkAction)null);
      this.mManualUpdatesSectionAdapter = var15;
      MyDownloadsAdapter.AssetType var16 = MyDownloadsAdapter.AssetType.OWNED;
      MyDownloadsAdapter.SectionAdapter var17 = new MyDownloadsAdapter.SectionAdapter(var16, 2131230909, (MyDownloadsAdapter.AssetBulkAction)null);
      this.mOwnedSectionAdapter = var17;
      MyDownloadsAdapter.AssetType var18 = MyDownloadsAdapter.AssetType.INSTALLED;
      MyDownloadsAdapter.SectionAdapter var19 = new MyDownloadsAdapter.SectionAdapter(var18, 2131230910, (MyDownloadsAdapter.AssetBulkAction)null);
      this.mInstalledSectionAdapter = var19;
      MyDownloadsAdapter.SectionAdapter[] var20 = new MyDownloadsAdapter.SectionAdapter[5];
      MyDownloadsAdapter.SectionAdapter var21 = this.mDownloadingSectionAdapter;
      var20[0] = var21;
      MyDownloadsAdapter.SectionAdapter var22 = this.mUpdatesSectionAdapter;
      var20[1] = var22;
      MyDownloadsAdapter.SectionAdapter var23 = this.mManualUpdatesSectionAdapter;
      var20[2] = var23;
      MyDownloadsAdapter.SectionAdapter var24 = this.mInstalledSectionAdapter;
      var20[3] = var24;
      MyDownloadsAdapter.SectionAdapter var25 = this.mOwnedSectionAdapter;
      var20[4] = var25;
      AggregatedAdapter var26 = new AggregatedAdapter(var20);
      this.mAggregatedAdapter = var26;
   }

   private void bindBitmapForAsset(Asset var1, ImageView var2) {
      BitmapLoader var3 = FinskyApp.get().getBitmapLoader();
      String var4 = var1.getId();
      MyDownloadsAdapter.2 var5 = new MyDownloadsAdapter.2(var1);
      if(var3.getOrBindImmediately(var4, 0, var2, (Bitmap)null, var5).getBitmap() == null) {
         BitmapDrawable var7 = sLoadingImageBitmap;
         var2.setImageDrawable(var7);
      }
   }

   private void configureCommonSettings(Asset var1, View var2) {
      OnClickListener var3 = this.mListener;
      var2.setOnClickListener(var3);
      int var4 = CorpusMetadata.getCorpusCellContentDescriptionResource(3);
      Context var5 = this.mContext;
      Object[] var6 = new Object[2];
      String var7 = var1.getTitle();
      var6[0] = var7;
      String var8 = var1.getDevName();
      var6[1] = var8;
      String var9 = var5.getString(var4, var6);
      var2.setContentDescription(var9);
      Drawable var10 = CorpusMetadata.getBucketEntryBackground(this.mContext, 3);
      var2.setBackgroundDrawable(var10);
   }

   private View getAssetView(Asset var1, View var2, ViewGroup var3, MyDownloadsAdapter.AssetType var4) {
      if(var2 == null) {
         LayoutInflater var5 = this.mLayoutInflater;
         int var6;
         if(this.mHasAssetView) {
            var6 = 2130968581;
         } else {
            var6 = 2130968654;
         }

         var2 = var5.inflate(var6, var3, (boolean)0);
      }

      MyDownloadsAdapter.ViewHolder var7 = (MyDownloadsAdapter.ViewHolder)var2.getTag();
      if(var7 == null) {
         var7 = new MyDownloadsAdapter.ViewHolder(var2);
      }

      var7.asset = var1;
      TextView var8 = var7.title;
      String var9 = var1.getTitle();
      var8.setText(var9);
      if(var7.ratingBar != null) {
         if(var1.hasRating() && var1.getRatingCount() > 0L) {
            RatingBar var10 = var7.ratingBar;
            float var11 = (float)var1.getAverageRating();
            var10.setRating(var11);
            var7.ratingBar.setVisibility(0);
         } else {
            var7.ratingBar.setVisibility(4);
         }
      }

      TextView var12 = var7.author;
      String var13 = var1.getDevName();
      var12.setText(var13);
      ImageView var14 = var7.thumbnail;
      this.bindBitmapForAsset(var1, var14);
      TextView var15 = var7.price;
      int var16 = CorpusMetadata.getBackendForegroundColor(this.mContext, 3);
      var15.setTextColor(var16);
      int[] var17 = MyDownloadsAdapter.4.$SwitchMap$com$google$android$finsky$adapters$MyDownloadsAdapter$AssetType;
      int var18 = var4.ordinal();
      switch(var17[var18]) {
      case 1:
         var7.price.setVisibility(0);
         var7.price.setText(2131230942);
         break;
      case 2:
      case 3:
         var7.price.setVisibility(8);
      }

      this.configureCommonSettings(var1, var2);
      return var2;
   }

   private View getDownloadingAssetView(Asset var1, View var2, ViewGroup var3) {
      if(var2 == null) {
         var2 = this.mLayoutInflater.inflate(2130968646, var3, (boolean)0);
      }

      MyDownloadsAdapter.DownloadingViewHolder var4 = (MyDownloadsAdapter.DownloadingViewHolder)var2.getTag();
      if(var4 == null) {
         var4 = new MyDownloadsAdapter.DownloadingViewHolder(var2);
      }

      var4.asset = var1;
      TextView var5 = var4.title;
      String var6 = var1.getTitle();
      var5.setText(var6);
      TextView var7 = var4.author;
      String var8 = var1.getDevName();
      var7.setText(var8);
      ImageView var9 = var4.thumbnail;
      this.bindBitmapForAsset(var1, var9);
      AssetStore var10 = FinskyInstance.get().getAssetStore();
      String var11 = var1.getPackageName();
      LocalAsset var12 = var10.getAsset(var11);
      DownloadQueue var13 = FinskyInstance.get().getDownloadQueue();
      String var14 = var1.getPackageName();
      Download var15 = var13.getByPackageName(var14);
      Context var16 = this.mContext;
      TextView var17 = var4.downloadingBytes;
      TextView var18 = var4.downloadingPercentage;
      ProgressBar var19 = var4.progressBar;
      DownloadProgressHelper.configureDownloadProgressUi(var16, var12, var15, var17, var18, var19);
      this.configureCommonSettings(var1, var2);
      return var2;
   }

   private View getHeaderView(int var1, View var2, ViewGroup var3, MyDownloadsAdapter.SectionAdapter var4) {
      if(var2 == null) {
         var2 = this.mLayoutInflater.inflate(2130968669, var3, (boolean)0);
      }

      MyDownloadsAdapter.SectionHeaderHolder var5 = (MyDownloadsAdapter.SectionHeaderHolder)var2.getTag();
      if(var5 == null) {
         var5 = new MyDownloadsAdapter.SectionHeaderHolder(var2);
      }

      TextView var6 = var5.titleView;
      Context var7 = this.mContext;
      int var8 = var4.mHeaderTextId;
      String var9 = var7.getString(var8);
      var6.setText(var9);
      var5.bulkActionButton.setVisibility(8);
      var5.loadingProgress.setVisibility(8);
      var5.divider.setVisibility(8);
      MyDownloadsAdapter.AssetBulkAction var10 = var4.mHeaderAction;
      boolean var11;
      if(var10 != null && var10.isVisible(this)) {
         var11 = true;
      } else {
         var11 = false;
      }

      boolean var12;
      if(var10 != null && var10.isWaiting(this)) {
         var12 = true;
      } else {
         var12 = false;
      }

      if(var12) {
         var5.loadingProgress.setVisibility(0);
      } else if(var11) {
         var5.divider.setVisibility(0);
         var5.bulkActionButton.setVisibility(0);
         Button var18 = var5.bulkActionButton;
         Context var19 = this.mContext;
         int var20 = var10.getLabelId();
         Object[] var21 = new Object[1];
         Integer var22 = Integer.valueOf(var4.getCount() + -1);
         var21[0] = var22;
         String var23 = var19.getString(var20, var21);
         var18.setText(var23);
         Button var24 = var5.bulkActionButton;
         MyDownloadsAdapter.3 var25 = new MyDownloadsAdapter.3(var10);
         var24.setOnClickListener(var25);
         Context var26 = this.mContext;
         Drawable var27 = var10.getIcon(var26);
         if(var27 == null) {
            var5.bulkActionButton.setCompoundDrawables((Drawable)null, (Drawable)null, (Drawable)null, (Drawable)null);
         } else {
            int var28 = var27.getIntrinsicWidth();
            int var29 = var27.getIntrinsicHeight();
            var27.setBounds(0, 0, var28, var29);
            var5.bulkActionButton.setCompoundDrawables(var27, (Drawable)null, (Drawable)null, (Drawable)null);
         }
      }

      boolean var13;
      if(!var12 && var11) {
         var13 = false;
      } else {
         var13 = true;
      }

      TextView var14 = var5.countView;
      byte var15;
      if(var13) {
         var15 = 0;
      } else {
         var15 = 8;
      }

      var14.setVisibility(var15);
      if(var13) {
         TextView var16 = var5.countView;
         String var17 = Integer.toString(var4.getCount() + -1);
         var16.setText(var17);
      }

      return var2;
   }

   public static Asset getViewAsset(View var0) {
      Object var1 = var0.getTag();
      Asset var2;
      if(var1 instanceof MyDownloadsAdapter.BaseViewHolder) {
         var2 = ((MyDownloadsAdapter.BaseViewHolder)var1).asset;
      } else {
         var2 = null;
      }

      return var2;
   }

   private void putAssetsInBuckets() {
      MyDownloadsAdapter.SectionAdapter[] var1 = (MyDownloadsAdapter.SectionAdapter[])this.mAggregatedAdapter.getAdapters();
      MyDownloadsAdapter.SectionAdapter[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var2[var4].clearAssets();
      }

      AssetStore var5 = FinskyInstance.get().getAssetStore();
      ArrayList var6 = new ArrayList();
      Iterator var7 = this.mUnsortedAssets.iterator();

      while(var7.hasNext()) {
         Asset var8 = (Asset)var7.next();
         String var9 = var8.getPackageName();
         LocalAsset var10 = var5.getAsset(var9);
         if(var10 == null) {
            this.mOwnedSectionAdapter.addAsset(var8);
         } else {
            AssetState var11 = var10.getState();
            AssetState var12 = AssetState.INSTALLED;
            if(var11 == var12) {
               long var13 = (long)var10.getVersionCode();
               long var15 = var8.getVersionCode();
               if(var13 >= var15) {
                  this.mInstalledSectionAdapter.addAsset(var8);
               } else {
                  var6.add(var8);
               }
            } else if(var10.isDownloadingOrInstalling()) {
               this.mDownloadingSectionAdapter.addAsset(var8);
            } else {
               this.mOwnedSectionAdapter.addAsset(var8);
            }
         }
      }

      List var18 = this.mInstaller.getAppsEligibleForAutoUpdate(var6, (boolean)0);
      Iterator var19 = var6.iterator();

      while(var19.hasNext()) {
         Asset var20 = (Asset)var19.next();
         if(var18.contains(var20)) {
            this.mUpdatesSectionAdapter.addAsset(var20);
         } else {
            this.mManualUpdatesSectionAdapter.addAsset(var20);
         }
      }

      MyDownloadsAdapter.SectionAdapter[] var21 = var1;
      int var22 = var1.length;

      for(int var23 = 0; var23 < var22; ++var23) {
         MyDownloadsAdapter.SectionAdapter var24 = var21[var23];
         var24.sortAssets();
         var24.notifyDataSetChanged();
      }

   }

   private void updateAssetIconIfNecessary(Asset var1, Bitmap var2) {
      int var3 = this.mListView.getFirstVisiblePosition();
      int var4 = this.mListView.getChildCount();
      int var5 = var3;

      while(true) {
         int var6 = var3 + var4;
         if(var5 >= var6) {
            return;
         }

         Object var7 = this.getItem(var5);
         if(var7 instanceof Asset) {
            String var8 = ((Asset)var7).getId();
            String var9 = var1.getId();
            if(var8.equals(var9)) {
               ListView var10 = this.mListView;
               int var11 = var5 - var3;
               MyDownloadsAdapter.BaseViewHolder var12 = (MyDownloadsAdapter.BaseViewHolder)var10.getChildAt(var11).getTag();
               if(var12 == null) {
                  return;
               }

               if(var12.thumbnail == null) {
                  return;
               }

               ThumbnailUtils.setImageBitmapWithFade(var12.thumbnail, var2);
               return;
            }
         }

         ++var5;
      }
   }

   public void addAssets(List<Asset> var1) {
      this.mUnsortedAssets.clear();
      this.mUnsortedAssets.addAll(var1);
      this.notifyDataSetChanged();
   }

   public boolean areAllItemsEnabled() {
      return this.mAggregatedAdapter.areAllItemsEnabled();
   }

   public int getCount() {
      return this.mAggregatedAdapter.getCount();
   }

   public Object getItem(int var1) {
      return this.mAggregatedAdapter.getItem(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public int getItemViewType(int var1) {
      return this.mAggregatedAdapter.getItemViewType(var1);
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      ListView var4 = (ListView)var3;
      this.mListView = var4;
      return this.mAggregatedAdapter.getView(var1, var2, var3);
   }

   public int getViewTypeCount() {
      return 3;
   }

   public boolean isEnabled(int var1) {
      return this.mAggregatedAdapter.isEnabled(var1);
   }

   public void notifyDataSetChanged() {
      this.putAssetsInBuckets();
      super.notifyDataSetChanged();
   }

   // $FF: synthetic class
   static class 4 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$adapters$MyDownloadsAdapter$AssetType = new int[MyDownloadsAdapter.AssetType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$adapters$MyDownloadsAdapter$AssetType;
            int var1 = MyDownloadsAdapter.AssetType.UPDATE_AVAILABLE.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$adapters$MyDownloadsAdapter$AssetType;
            int var3 = MyDownloadsAdapter.AssetType.OWNED.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$adapters$MyDownloadsAdapter$AssetType;
            int var5 = MyDownloadsAdapter.AssetType.INSTALLED.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }

   class 2 implements BitmapLoader.BitmapLoadedHandler {

      // $FF: synthetic field
      final Asset val$asset;


      2(Asset var2) {
         this.val$asset = var2;
      }

      public void onResponse(BitmapLoader.BitmapContainer var1) {
         if(var1.getBitmap() != null) {
            MyDownloadsAdapter var2 = MyDownloadsAdapter.this;
            Asset var3 = this.val$asset;
            Bitmap var4 = var1.getBitmap();
            var2.updateAssetIconIfNecessary(var3, var4);
         }
      }
   }

   private class SectionAdapter extends BaseAdapter {

      private final MyDownloadsAdapter.AssetType mAssetType;
      private final List<Asset> mAssets;
      private final MyDownloadsAdapter.AssetBulkAction mHeaderAction;
      private final int mHeaderTextId;


      public SectionAdapter(MyDownloadsAdapter.AssetType var2, int var3, MyDownloadsAdapter.AssetBulkAction var4) {
         this.mAssetType = var2;
         this.mHeaderTextId = var3;
         this.mHeaderAction = var4;
         ArrayList var5 = new ArrayList();
         this.mAssets = var5;
      }

      void addAsset(Asset var1) {
         this.mAssets.add(var1);
      }

      void clearAssets() {
         this.mAssets.clear();
      }

      public int getCount() {
         int var1;
         if(this.isVisible()) {
            var1 = this.mAssets.size() + 1;
         } else {
            var1 = 0;
         }

         return var1;
      }

      public Object getItem(int var1) {
         Object var2;
         if(var1 == 0) {
            var2 = null;
         } else {
            List var3 = this.mAssets;
            int var4 = var1 + -1;
            var2 = var3.get(var4);
         }

         return var2;
      }

      public long getItemId(int var1) {
         return (long)var1;
      }

      public int getItemViewType(int var1) {
         byte var2;
         if(var1 == 0) {
            var2 = 0;
         } else {
            MyDownloadsAdapter.AssetType var3 = this.mAssetType;
            MyDownloadsAdapter.AssetType var4 = MyDownloadsAdapter.AssetType.DOWNLOADING;
            if(var3 == var4) {
               var2 = 2;
            } else {
               var2 = 1;
            }
         }

         return var2;
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         int var4 = this.getItemViewType(var1);
         View var5 = null;
         switch(var4) {
         case 0:
            var5 = MyDownloadsAdapter.this.getHeaderView(var1, var2, var3, this);
            break;
         case 1:
            MyDownloadsAdapter var9 = MyDownloadsAdapter.this;
            Asset var10 = (Asset)this.getItem(var1);
            MyDownloadsAdapter.AssetType var11 = this.mAssetType;
            var5 = var9.getAssetView(var10, var2, var3, var11);
            break;
         case 2:
            MyDownloadsAdapter var7 = MyDownloadsAdapter.this;
            Asset var8 = (Asset)this.getItem(var1);
            var5 = var7.getDownloadingAssetView(var8, var2, var3);
         }

         if(var5 == null) {
            String var6 = "Null row view for position " + var1 + " and type " + var4;
            throw new IllegalStateException(var6);
         } else {
            return var5;
         }
      }

      public int getViewTypeCount() {
         return 3;
      }

      public boolean isVisible() {
         boolean var1;
         if(this.mAssets.size() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      void sortAssets() {
         List var1 = this.mAssets;
         Comparator var2 = MyDownloadsAdapter.sAssetAbcSorter;
         Collections.sort(var1, var2);
      }
   }

   private static class BaseViewHolder {

      public Asset asset;
      public final TextView author;
      public final ImageView thumbnail;
      public final TextView title;


      public BaseViewHolder(View var1) {
         TextView var2 = (TextView)var1.findViewById(2131755020);
         this.title = var2;
         ImageView var3 = (ImageView)var1.findViewById(2131755018);
         this.thumbnail = var3;
         TextView var4 = (TextView)var1.findViewById(2131755021);
         this.author = var4;
      }
   }

   class 3 implements OnClickListener {

      // $FF: synthetic field
      final MyDownloadsAdapter.AssetBulkAction val$sectionAction;


      3(MyDownloadsAdapter.AssetBulkAction var2) {
         this.val$sectionAction = var2;
      }

      public void onClick(View var1) {
         MyDownloadsAdapter.AssetBulkAction var2 = this.val$sectionAction;
         Context var3 = MyDownloadsAdapter.this.mContext;
         MyDownloadsAdapter var4 = MyDownloadsAdapter.this;
         var2.run(var3, var4);
      }
   }

   static class 1 implements Comparator<Asset> {

      1() {}

      public int compare(Asset var1, Asset var2) {
         String var3 = var1.getTitle();
         String var4 = var2.getTitle();
         int var5 = MyDownloadsAdapter.sAssetAbcCollator.compare(var3, var4);
         if(var5 == 0) {
            String var6 = var1.getPackageName();
            String var7 = var2.getPackageName();
            var5 = var6.compareTo(var7);
         }

         return var5;
      }
   }

   private static final class SectionHeaderHolder {

      public final Button bulkActionButton;
      public final TextView countView;
      public final View divider;
      public final View loadingProgress;
      public final TextView titleView;


      public SectionHeaderHolder(View var1) {
         TextView var2 = (TextView)var1.findViewById(2131755249);
         this.titleView = var2;
         Button var3 = (Button)var1.findViewById(2131755251);
         this.bulkActionButton = var3;
         View var4 = var1.findViewById(2131755185);
         this.loadingProgress = var4;
         View var5 = var1.findViewById(2131755250);
         this.divider = var5;
         TextView var6 = (TextView)var1.findViewById(2131755252);
         this.countView = var6;
         var1.setTag(this);
      }
   }

   private static final class DownloadingViewHolder extends MyDownloadsAdapter.BaseViewHolder {

      public final TextView downloadingBytes;
      public final TextView downloadingPercentage;
      public final ProgressBar progressBar;


      public DownloadingViewHolder(View var1) {
         super(var1);
         TextView var2 = (TextView)var1.findViewById(2131755135);
         this.downloadingBytes = var2;
         TextView var3 = (TextView)var1.findViewById(2131755134);
         this.downloadingPercentage = var3;
         ProgressBar var4 = (ProgressBar)var1.findViewById(2131755136);
         this.progressBar = var4;
         var1.setTag(this);
      }
   }

   private static final class ViewHolder extends MyDownloadsAdapter.BaseViewHolder {

      public final TextView price;
      public final RatingBar ratingBar;


      public ViewHolder(View var1) {
         super(var1);
         RatingBar var2 = (RatingBar)var1.findViewById(2131755037);
         this.ratingBar = var2;
         TextView var3 = (TextView)var1.findViewById(2131755019);
         this.price = var3;
         var1.setTag(this);
      }
   }

   private static enum AssetType {

      // $FF: synthetic field
      private static final MyDownloadsAdapter.AssetType[] $VALUES;
      DOWNLOADING("DOWNLOADING", 0),
      INSTALLED("INSTALLED", 1),
      OWNED("OWNED", 3),
      UPDATE_AVAILABLE("UPDATE_AVAILABLE", 2);


      static {
         MyDownloadsAdapter.AssetType[] var0 = new MyDownloadsAdapter.AssetType[4];
         MyDownloadsAdapter.AssetType var1 = DOWNLOADING;
         var0[0] = var1;
         MyDownloadsAdapter.AssetType var2 = INSTALLED;
         var0[1] = var2;
         MyDownloadsAdapter.AssetType var3 = UPDATE_AVAILABLE;
         var0[2] = var3;
         MyDownloadsAdapter.AssetType var4 = OWNED;
         var0[3] = var4;
         $VALUES = var0;
      }

      private AssetType(String var1, int var2) {}
   }

   public static enum AssetBulkAction {

      // $FF: synthetic field
      private static final MyDownloadsAdapter.AssetBulkAction[] $VALUES;
      STOP_ALL_DOWNLOADS("STOP_ALL_DOWNLOADS", 1),
      UPDATE_ALL("UPDATE_ALL", 0);


      static {
         MyDownloadsAdapter.AssetBulkAction[] var0 = new MyDownloadsAdapter.AssetBulkAction[2];
         MyDownloadsAdapter.AssetBulkAction var1 = UPDATE_ALL;
         var0[0] = var1;
         MyDownloadsAdapter.AssetBulkAction var2 = STOP_ALL_DOWNLOADS;
         var0[1] = var2;
         $VALUES = var0;
      }

      private AssetBulkAction(String var1, int var2) {}

      // $FF: synthetic method
      AssetBulkAction(String var1, int var2, MyDownloadsAdapter.1 var3) {
         this(var1, var2);
      }

      public abstract Drawable getIcon(Context var1);

      public abstract int getLabelId();

      public abstract boolean isVisible(MyDownloadsAdapter var1);

      public abstract boolean isWaiting(MyDownloadsAdapter var1);

      public abstract void run(Context var1, MyDownloadsAdapter var2);

      static enum 2 {

         2(String var1, int var2) {}

         public Drawable getIcon(Context var1) {
            return var1.getResources().getDrawable(2130837650);
         }

         public int getLabelId() {
            return 2131230915;
         }

         public boolean isVisible(MyDownloadsAdapter var1) {
            AssetStore var2 = FinskyInstance.get().getAssetStore();
            Iterator var3 = var1.mUnsortedAssets.iterator();

            boolean var6;
            while(true) {
               if(var3.hasNext()) {
                  String var4 = ((Asset)var3.next()).getPackageName();
                  LocalAsset var5 = var2.getAsset(var4);
                  if(var5 == null || !var5.isDownloadingOrInstalling()) {
                     continue;
                  }

                  var6 = true;
                  break;
               }

               var6 = false;
               break;
            }

            return var6;
         }

         public boolean isWaiting(MyDownloadsAdapter var1) {
            return false;
         }

         public void run(Context var1, MyDownloadsAdapter var2) {
            FinskyInstance.get().getDownloadQueue().cancelAll();
         }
      }

      static enum 1 {

         1(String var1, int var2) {}

         public Drawable getIcon(Context var1) {
            return var1.getResources().getDrawable(2130837651);
         }

         public int getLabelId() {
            return 2131231018;
         }

         public boolean isVisible(MyDownloadsAdapter var1) {
            boolean var2 = true;
            int var3 = var1.mDownloadingSectionAdapter.getCount();
            int var4 = var1.mUpdatesSectionAdapter.getCount() + -1;
            if(var3 != 0 || var4 <= 1) {
               var2 = false;
            }

            return var2;
         }

         public boolean isWaiting(MyDownloadsAdapter var1) {
            return var1.mInstaller.hasPendingAssetRequests();
         }

         public void run(Context var1, MyDownloadsAdapter var2) {
            List var3 = var2.mUpdatesSectionAdapter.mAssets;
            var2.mInstaller.attemptInstallAssets(var3);
            String var4 = "updateAll";
            boolean var5 = true;
            Iterator var6 = var3.iterator();

            while(var6.hasNext()) {
               Asset var7 = (Asset)var6.next();
               if(var7 != null && !TextUtils.isEmpty(var7.getPackageName())) {
                  if(var5) {
                     StringBuilder var8 = (new StringBuilder()).append(var4).append("?doc[]=");
                     String var9 = var7.getPackageName();
                     var4 = var8.append(var9).toString();
                     var5 = false;
                  } else {
                     StringBuilder var10 = (new StringBuilder()).append(var4).append("&doc[]=");
                     String var11 = var7.getPackageName();
                     var4 = var10.append(var11).toString();
                  }
               }
            }

            FinskyApp.get().getAnalytics().logPageView((String)null, (String)null, var4);
         }
      }
   }
}

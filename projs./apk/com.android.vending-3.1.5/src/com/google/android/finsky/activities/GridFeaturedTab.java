package com.google.android.finsky.activities;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SlidingPanelTab;
import com.google.android.finsky.adapters.PromotedListGridItem;
import com.google.android.finsky.adapters.UnevenGridAdapter;
import com.google.android.finsky.adapters.UnevenGridItemType;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.GridSequencer;
import com.google.android.finsky.layout.UnevenGrid;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.GridAdapterUtils;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.List;

public class GridFeaturedTab implements SlidingPanelTab, OnDataChangedListener, SlidingPanelTab.OnListLoaded {

   private static final int FILLER_ITEMS_PER_EXTERNAL_LIST = 12;
   private static final UnevenGridItemType[] GRID_LAYOUT_SEQUENCE;
   private static final UnevenGridItemType[] GRID_LAYOUT_SEQUENCE_8xN;
   private static final int MAX_PROMO_ITEMS = 14;
   private ViewGroup mCachedView;
   private List<PromotedListGridItem.PromotedListGridItemConfig> mCannedData;
   private boolean mCannedDataLoaded;
   private final Context mContext;
   private final String mCurrentBrowseUrl;
   private boolean mFillerDataLoaded;
   private boolean mIsCurrentlySelected;
   private final LayoutInflater mLayoutInflater;
   private final GridSequencer mLayoutSequencer;
   private boolean mPromoDataLoaded;
   private DfeList mPromoList;
   private final String mReferrerBrowseUrl;
   private final UnevenGridItemType[] mSequence;


   static {
      UnevenGridItemType[] var0 = new UnevenGridItemType[9];
      UnevenGridItemType var1 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var0[0] = var1;
      UnevenGridItemType var2 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var0[1] = var2;
      UnevenGridItemType var3 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
      var0[2] = var3;
      UnevenGridItemType var4 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
      var0[3] = var4;
      UnevenGridItemType var5 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var0[4] = var5;
      UnevenGridItemType var6 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
      var0[5] = var6;
      UnevenGridItemType var7 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var0[6] = var7;
      UnevenGridItemType var8 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
      var0[7] = var8;
      UnevenGridItemType var9 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var0[8] = var9;
      GRID_LAYOUT_SEQUENCE = var0;
      UnevenGridItemType[] var10 = new UnevenGridItemType[12];
      UnevenGridItemType var11 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var10[0] = var11;
      UnevenGridItemType var12 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var10[1] = var12;
      UnevenGridItemType var13 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var10[2] = var13;
      UnevenGridItemType var14 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var10[3] = var14;
      UnevenGridItemType var15 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
      var10[4] = var15;
      UnevenGridItemType var16 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
      var10[5] = var16;
      UnevenGridItemType var17 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
      var10[6] = var17;
      UnevenGridItemType var18 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
      var10[7] = var18;
      UnevenGridItemType var19 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
      var10[8] = var19;
      UnevenGridItemType var20 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
      var10[9] = var20;
      UnevenGridItemType var21 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
      var10[10] = var21;
      UnevenGridItemType var22 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
      var10[11] = var22;
      GRID_LAYOUT_SEQUENCE_8xN = var10;
   }

   public GridFeaturedTab(Context var1, LayoutInflater var2, BitmapLoader var3, NavigationManager var4, DfeList var5, String var6, String var7, DfeToc var8) {
      ArrayList var9 = Lists.newArrayList();
      this.mCannedData = var9;
      this.mContext = var1;
      this.mLayoutInflater = var2;
      this.mPromoList = var5;
      this.mReferrerBrowseUrl = var6;
      this.mCurrentBrowseUrl = var7;
      if(var1.getResources().getInteger(2131492868) == 8) {
         UnevenGridItemType[] var10 = GRID_LAYOUT_SEQUENCE_8xN;
         this.mSequence = var10;
      } else {
         UnevenGridItemType[] var18 = GRID_LAYOUT_SEQUENCE;
         this.mSequence = var18;
      }

      Context var11 = this.mContext;
      UnevenGridItemType[] var12 = this.mSequence;
      GridSequencer var17 = new GridSequencer(var11, var4, var3, var12, var8, var7);
      this.mLayoutSequencer = var17;
      if(!this.mPromoList.isMoreAvailable() && this.mPromoList.isReady()) {
         this.onDataChanged();
      } else {
         this.mPromoList.startLoadItems();
         this.mPromoList.addDataChangedListener(this);
      }
   }

   private void logClickForCurrentPage() {
      if(this.mPromoList.isReady()) {
         if(this.mPromoList.getBucketCount() > 0) {
            Analytics var1 = FinskyApp.get().getAnalytics();
            String var2 = this.mReferrerBrowseUrl;
            String var3 = this.mCurrentBrowseUrl;
            String var4 = this.mPromoList.getBucket(0).getAnalyticsCookie();
            var1.logListViewOnPage(var2, (String)null, var3, var4);
         }
      }
   }

   private void setSequencerDataFullyLoadedIfNecessary() {
      if(this.mFillerDataLoaded) {
         if(this.mPromoDataLoaded) {
            if(this.mCannedDataLoaded) {
               this.mLayoutSequencer.dataFullyLoaded();
            }
         }
      }
   }

   public List<PromotedListGridItem.PromotedListGridItemConfig> getCannedDataFromSequencer() {
      return this.mCannedData;
   }

   public String getTitle() {
      String var1 = null;
      if(this.mPromoList != null && this.mPromoList.isReady() && this.mPromoList.getBucketCount() > 0) {
         var1 = this.mPromoList.getBucket(0).getTitle();
      }

      if(TextUtils.isEmpty(var1)) {
         var1 = this.mContext.getString(2131231045);
      }

      return var1;
   }

   public View getView(int var1) {
      if(this.mCachedView == null) {
         ViewGroup var2 = (ViewGroup)this.mLayoutInflater.inflate(2130968674, (ViewGroup)null);
         this.mCachedView = var2;
         UnevenGrid var3 = (UnevenGrid)this.mCachedView.findViewById(2131755257);
         var3.setHasTopGutter((boolean)1);
         UnevenGridAdapter var4 = this.mLayoutSequencer.getAdapter();
         var3.setAdapter(var4);
      }

      return this.mCachedView;
   }

   public int numCannedItemsSlots() {
      int var1 = 0;
      UnevenGridItemType[] var2 = this.mSequence;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         UnevenGridItemType var5 = var2[var4];
         UnevenGridItemType var6 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
         if(var5 == var6) {
            ++var1;
         }
      }

      return var1;
   }

   public void onCannedDataReady(List<PromotedListGridItem.PromotedListGridItemConfig> var1) {
      this.mCannedData.addAll(var1);
      this.mLayoutSequencer.addCannedListData(var1);
      this.mCannedDataLoaded = (boolean)1;
      this.setSequencerDataFullyLoadedIfNecessary();
   }

   public void onDataChanged() {
      if(this.mIsCurrentlySelected) {
         this.logClickForCurrentPage();
      }

      GridSequencer var1 = this.mLayoutSequencer;
      List var2 = GridAdapterUtils.getDocumentListFromDfeList(this.mPromoList, 14);
      var1.addPromoData(var2);
      this.mPromoDataLoaded = (boolean)1;
      this.setSequencerDataFullyLoadedIfNecessary();
   }

   public void onDestroy() {
      this.mPromoList.removeDataChangedListener(this);
      this.mCannedData.clear();
      this.mLayoutSequencer.onDestroy();
      this.mCachedView = null;
   }

   public void onListReady(DfeList var1) {
      GridSequencer var2 = this.mLayoutSequencer;
      List var3 = GridAdapterUtils.getDocumentListFromDfeList(var1, 12);
      var2.addFillerData(var3);
      this.mFillerDataLoaded = (boolean)1;
      this.setSequencerDataFullyLoadedIfNecessary();
   }

   public void setImagesEnabled(boolean var1) {
      this.mLayoutSequencer.setImagesEnabled(var1);
   }

   public void setTabSelected(boolean var1) {
      if(var1 && !this.mIsCurrentlySelected) {
         this.logClickForCurrentPage();
      }

      this.mIsCurrentlySelected = var1;
   }
}

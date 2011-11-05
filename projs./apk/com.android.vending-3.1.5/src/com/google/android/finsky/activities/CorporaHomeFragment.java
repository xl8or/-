package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.UnevenGridAdapter;
import com.google.android.finsky.adapters.UnevenGridItemType;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeBrowse;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.UrlBasedPageFragment;
import com.google.android.finsky.layout.GridSequencer;
import com.google.android.finsky.layout.UnevenGrid;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.GridAdapterUtils;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CorporaHomeFragment extends UrlBasedPageFragment {

   private static final UnevenGridItemType[] GRID_LAYOUT_SEQUENCE_4xN;
   private static final UnevenGridItemType[] GRID_LAYOUT_SEQUENCE_6xN;
   private static final UnevenGridItemType[] GRID_LAYOUT_SEQUENCE_6xN_PROMO_HEAVY;
   private static final UnevenGridItemType[] GRID_LAYOUT_SEQUENCE_8xN;
   private static final int MAX_FILLER_DOCUMENTS = 20;
   private DfeBrowse mBrowseData;
   private DfeList mContentListData;
   private boolean mGridSequenceDataAdded;
   private GridSequencer mGridSequencer;
   private DfeList mPromoListData;


   static {
      UnevenGridItemType[] var0 = new UnevenGridItemType[7];
      UnevenGridItemType var1 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var0[0] = var1;
      UnevenGridItemType var2 = UnevenGridItemType.CORPORA_LIST_2XN;
      var0[1] = var2;
      UnevenGridItemType var3 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var0[2] = var3;
      UnevenGridItemType var4 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var0[3] = var4;
      UnevenGridItemType var5 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var0[4] = var5;
      UnevenGridItemType var6 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var0[5] = var6;
      UnevenGridItemType var7 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var0[6] = var7;
      GRID_LAYOUT_SEQUENCE_4xN = var0;
      UnevenGridItemType[] var8 = new UnevenGridItemType[8];
      UnevenGridItemType var9 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var8[0] = var9;
      UnevenGridItemType var10 = UnevenGridItemType.CORPORA_LIST_2XN;
      var8[1] = var10;
      UnevenGridItemType var11 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var8[2] = var11;
      UnevenGridItemType var12 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var8[3] = var12;
      UnevenGridItemType var13 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var8[4] = var13;
      UnevenGridItemType var14 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var8[5] = var14;
      UnevenGridItemType var15 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var8[6] = var15;
      UnevenGridItemType var16 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var8[7] = var16;
      GRID_LAYOUT_SEQUENCE_6xN = var8;
      UnevenGridItemType[] var17 = new UnevenGridItemType[11];
      UnevenGridItemType var18 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var17[0] = var18;
      UnevenGridItemType var19 = UnevenGridItemType.CORPORA_LIST_2XN;
      var17[1] = var19;
      UnevenGridItemType var20 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var17[2] = var20;
      UnevenGridItemType var21 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var17[3] = var21;
      UnevenGridItemType var22 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
      var17[4] = var22;
      UnevenGridItemType var23 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var17[5] = var23;
      UnevenGridItemType var24 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var17[6] = var24;
      UnevenGridItemType var25 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var17[7] = var25;
      UnevenGridItemType var26 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var17[8] = var26;
      UnevenGridItemType var27 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var17[9] = var27;
      UnevenGridItemType var28 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var17[10] = var28;
      GRID_LAYOUT_SEQUENCE_6xN_PROMO_HEAVY = var17;
      UnevenGridItemType[] var29 = new UnevenGridItemType[7];
      UnevenGridItemType var30 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var29[0] = var30;
      UnevenGridItemType var31 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      var29[1] = var31;
      UnevenGridItemType var32 = UnevenGridItemType.CORPORA_LIST_2XN;
      var29[2] = var32;
      UnevenGridItemType var33 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var29[3] = var33;
      UnevenGridItemType var34 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var29[4] = var34;
      UnevenGridItemType var35 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      var29[5] = var35;
      UnevenGridItemType var36 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
      var29[6] = var36;
      GRID_LAYOUT_SEQUENCE_8xN = var29;
   }

   public CorporaHomeFragment() {}

   private void addItemsToGridSequence() {
      if(!this.mGridSequenceDataAdded) {
         boolean var1 = false;
         if(this.getPromoListData() != null && this.getPromoListData().isReady()) {
            GridSequencer var2 = this.mGridSequencer;
            List var3 = GridAdapterUtils.getDocumentListFromDfeList(this.getPromoListData(), Integer.MAX_VALUE);
            var2.addPromoData(var3);
            var1 = true;
         }

         if(this.getContentListData() != null && this.getContentListData().isReady()) {
            ArrayList var4 = Lists.newArrayList();
            Iterator var5 = this.mContentListData.getBucketList().iterator();

            while(var5.hasNext()) {
               DocList.Bucket var6 = (DocList.Bucket)var5.next();
               int var7 = 0;

               while(true) {
                  int var8 = var6.getDocumentCount();
                  if(var7 >= var8) {
                     break;
                  }

                  DeviceDoc.DeviceDocument var9 = var6.getDocument(var7);
                  String var10 = var6.getAnalyticsCookie();
                  Document var11 = new Document(var9, var10);
                  var4.add(var11);
                  if(var4.size() > 20) {
                     break;
                  }

                  ++var7;
               }

               if(var4.size() > 20) {
                  break;
               }
            }

            this.mGridSequencer.addFillerData(var4);
            var1 = true;
         }

         this.mGridSequencer.dataFullyLoaded();
         if(!var1) {
            this.mGridSequencer.forceDataDisplay();
         }

         this.mGridSequenceDataAdded = (boolean)1;
      }
   }

   private DfeList getContentListData() {
      DfeList var1;
      if(this.mContentListData == null) {
         if(this.mBrowseData == null || !this.mBrowseData.isReady()) {
            var1 = null;
            return var1;
         }

         DfeList var2 = this.mBrowseData.buildContentList();
         this.mContentListData = var2;
         if(this.mContentListData != null) {
            this.mContentListData.addDataChangedListener(this);
            this.mContentListData.addErrorListener(this);
         }
      }

      var1 = this.mContentListData;
      return var1;
   }

   private DfeList getPromoListData() {
      DfeList var1;
      if(this.mPromoListData == null) {
         if(this.mBrowseData == null || !this.mBrowseData.isReady() || !this.mBrowseData.hasPromotionalItems()) {
            var1 = null;
            return var1;
         }

         DfeList var2 = this.mBrowseData.buildPromoList();
         this.mPromoListData = var2;
         this.mPromoListData.addDataChangedListener(this);
         this.mPromoListData.addErrorListener(this);
      }

      var1 = this.mPromoListData;
      return var1;
   }

   public static CorporaHomeFragment newInstance(DfeToc var0, String var1) {
      CorporaHomeFragment var2 = new CorporaHomeFragment();
      var2.setArguments(var0, var1);
      return var2;
   }

   private void resetDfeModels() {
      if(this.mBrowseData != null) {
         this.mBrowseData.removeDataChangedListener(this);
         this.mBrowseData.removeErrorListener(this);
      }

      this.mBrowseData = null;
      if(this.mContentListData != null) {
         this.mContentListData.removeDataChangedListener(this);
         this.mContentListData.removeErrorListener(this);
      }

      this.mContentListData = null;
      if(this.mPromoListData != null) {
         this.mPromoListData.removeDataChangedListener(this);
         this.mPromoListData.removeErrorListener(this);
      }

      this.mPromoListData = null;
   }

   protected int getLayoutRes() {
      return 2130968602;
   }

   protected boolean isDataReady() {
      boolean var1;
      if(this.mBrowseData != null && this.mBrowseData.isReady() && (this.getContentListData() == null || this.getContentListData().isReady()) && (!this.mBrowseData.hasPromotionalItems() || this.getPromoListData().isReady())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      if(!this.isDataReady()) {
         this.switchToLoading();
         this.requestData();
         this.rebindActionBar();
      }
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4 = super.onCreateView(var1, var2, var3);
      if(this.isDataReady()) {
         this.rebindViews();
      }

      return var4;
   }

   public void onDataChanged() {
      if(this.isAdded()) {
         if(this.mBrowseData != null && this.mBrowseData.isReady() && this.mContentListData == null && this.mPromoListData == null) {
            DfeList var1 = this.getContentListData();
            if(this.mContentListData != null) {
               this.mContentListData.startLoadItems();
            }

            DfeList var2 = this.getPromoListData();
            if(this.mPromoListData != null) {
               this.mPromoListData.startLoadItems();
            }
         }

         if(this.isDataReady()) {
            String var3;
            if(this.mBrowseData.hasPromotionalItems()) {
               var3 = this.getPromoListData().getBucket(0).getAnalyticsCookie();
            } else {
               var3 = null;
            }

            Analytics var4 = FinskyApp.get().getAnalytics();
            String var5 = this.mUrl;
            var4.logListViewOnPage((String)null, (String)null, var5, var3);
            this.addItemsToGridSequence();
            super.onDataChanged();
         }
      }
   }

   protected void onInitViewBinders() {
      int var1 = this.getResources().getInteger(2131492868);
      UnevenGridItemType[] var2;
      if(var1 == 8) {
         var2 = GRID_LAYOUT_SEQUENCE_8xN;
      } else if(var1 == 6) {
         if(this.getResources().getBoolean(2131296261)) {
            var2 = GRID_LAYOUT_SEQUENCE_6xN_PROMO_HEAVY;
         } else {
            var2 = GRID_LAYOUT_SEQUENCE_6xN;
         }
      } else {
         var2 = GRID_LAYOUT_SEQUENCE_4xN;
      }

      Context var3 = this.mContext;
      NavigationManager var4 = this.mNavigationManager;
      BitmapLoader var5 = this.mBitmapLoader;
      DfeToc var6 = this.getToc();
      String var7 = this.mUrl;
      GridSequencer var8 = new GridSequencer(var3, var4, var5, var2, var6, var7);
      this.mGridSequencer = var8;
      this.mGridSequencer.setImagesEnabled((boolean)1);
   }

   public void rebindActionBar() {
      PageFragmentHost var1 = this.mPageFragmentHost;
      String var2 = this.mContext.getString(2131231074);
      var1.updateBreadcrumb(var2);
      this.mPageFragmentHost.updateCurrentBackendId(0);
   }

   protected void rebindViews() {
      super.rebindViews();
      this.switchToData();
      this.rebindActionBar();
      UnevenGrid var1 = (UnevenGrid)this.mDataView.findViewById(2131755112);
      var1.setHasTopGutter((boolean)0);
      View var2 = (View)var1.getParent();
      int var3 = this.mContext.getResources().getColor(2131361804);
      var2.setBackgroundColor(var3);
      UnevenGridAdapter var4 = this.mGridSequencer.getAdapter();
      var4.setShowCorpusStrip((boolean)1);
      var1.setAdapter(var4);
   }

   protected void requestData() {
      this.resetDfeModels();
      DfeApi var1 = this.mDfeApi;
      String var2 = this.mUrl;
      DfeBrowse var3 = new DfeBrowse(var1, var2);
      this.mBrowseData = var3;
      this.mBrowseData.addDataChangedListener(this);
      this.mBrowseData.addErrorListener(this);
   }
}

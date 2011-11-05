package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.CategoryTab;
import com.google.android.finsky.activities.GridFeaturedTab;
import com.google.android.finsky.activities.ListTab;
import com.google.android.finsky.activities.SlidingPanelTab;
import com.google.android.finsky.adapters.ImageLoadState;
import com.google.android.finsky.adapters.PromotedListGridItem;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeBrowse;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.UrlBasedPageFragment;
import com.google.android.finsky.layout.SlidingPanel;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.Browse;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.remoting.protos.Toc;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.ElegantFeaturedGridHackIsolator;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TabbedBrowseFragment extends UrlBasedPageFragment implements SlidingPanel.OnPanelSelectedListener {

   private static final String KEY_BACKENDID = "TabbedBrowseFragment.BackendId";
   private static final String KEY_BREADCRUMB = "TabbedBrowseFragment.Breadcrumb";
   private static final String KEY_BROWSE_DATA = "TabbedBrowseFragment.BrowseData";
   private static final String KEY_LISTS_DATA = "TabbedBrowseFragment.ListsData";
   private static final String KEY_LISTS_TITLE = "TabbedBrowseFragment.ListsTitles";
   private static final String KEY_LISTS_VIEW_PARCELS = "TabbedBrowseFragment.ListsParcels";
   private static final String KEY_PROMO_CANNED_DATA = "TabbedBrowseFragment.PromoCannedData";
   private static final String KEY_PROMO_DATA = "TabbedBrowseFragment.PromoData";
   private static final String KEY_SELECTED_TAB = "TabbedBrowseFragment.SelectedTab";
   private static final int ON_SWIPE_IMAGE_LOAD_DELAY_MS = 200;
   private static final String REFERRER_URL = "TabbedBrowseFragment.ReferrerUrl";
   private static final int TAB_CACHED_IMAGE_LOADING_WINDOW_SIZE = 1;
   private int mBackendId = 0;
   private String mBreadcrumb;
   private DfeBrowse mBrowseData;
   private CategoryTab mCategoryTab;
   private DfeList mContentListData;
   private GridFeaturedTab mFeaturedTab;
   private Handler mHandler;
   private long mLastPanelSwipeTime;
   private List<ListTab> mListTabs = null;
   private DfeList mPromoList;
   private String mReferringBrowseUrl;
   private int mRestoreSelectedPanel;
   private Bundle mSavedInstanceState;
   private boolean mShouldAlwaysKeepFeaturedData;
   private SlidingPanel mSlidingPanel;


   public TabbedBrowseFragment() {
      Looper var1 = Looper.getMainLooper();
      Handler var2 = new Handler(var1);
      this.mHandler = var2;
      Bundle var3 = new Bundle();
      this.mSavedInstanceState = var3;
      this.mLastPanelSwipeTime = 0L;
      this.mRestoreSelectedPanel = -1;
   }

   private void clearState() {
      if(this.mCategoryTab != null) {
         this.mCategoryTab.onDestroy();
         this.mCategoryTab = null;
      }

      if(this.mFeaturedTab != null) {
         this.mFeaturedTab.onDestroy();
         this.mFeaturedTab = null;
      }

      if(this.mListTabs != null) {
         Iterator var1 = this.mListTabs.iterator();

         while(var1.hasNext()) {
            ((ListTab)var1.next()).onDestroy();
         }

         this.mListTabs = null;
      }

      if(this.mBrowseData != null) {
         this.mBrowseData.removeDataChangedListener(this);
         this.mBrowseData.removeErrorListener(this);
         this.mBrowseData = null;
      }

      if(this.mContentListData != null) {
         this.mContentListData.removeDataChangedListener(this);
         this.mContentListData.removeErrorListener(this);
         this.mContentListData = null;
      }

      if(this.mPromoList != null) {
         this.mPromoList.removeDataChangedListener(this);
         this.mPromoList.removeErrorListener(this);
         this.mPromoList = null;
      }
   }

   private String getBreadcrumbText() {
      int var1 = this.mBrowseData.getBreadcrumbList().size();
      String var4;
      if(var1 > 0) {
         List var2 = this.mBrowseData.getBreadcrumbList();
         int var3 = var1 + -1;
         var4 = ((Browse.BrowseLink)var2.get(var3)).getName();
      } else {
         DfeToc var5 = this.getToc();
         int var6 = this.mBackendId;
         Toc.CorpusMetadata var7 = var5.getCorpus(var6);
         if(var7 == null) {
            var4 = "";
         } else if(!this.mNavigationManager.canGoUp()) {
            var4 = this.mContext.getString(2131231074);
         } else {
            var4 = var7.getName();
         }
      }

      return var4;
   }

   private int getFeaturedTabIndex() {
      byte var1;
      if(this.mFeaturedTab == null) {
         var1 = -1;
      } else if(this.mCategoryTab == null) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      return var1;
   }

   private ImageLoadState getImageLoadStateForTab(int var1) {
      int var2 = this.mSlidingPanel.getSelectedPanel();
      ImageLoadState var3;
      if(var2 == var1) {
         var3 = ImageLoadState.ALL;
      } else if(Math.abs(var2 - var1) <= 1) {
         var3 = ImageLoadState.CACHED_ONLY;
      } else {
         var3 = ImageLoadState.NONE;
      }

      return var3;
   }

   private int getTabCount() {
      int var1 = 0;
      if(this.mCategoryTab != null) {
         var1 = 0 + 1;
      }

      if(this.mFeaturedTab != null) {
         ++var1;
      }

      if(this.mListTabs != null) {
         int var2 = this.mListTabs.size();
         var1 += var2;
      }

      return var1;
   }

   private boolean isCannedTile(String var1, List<PromotedListGridItem.PromotedListGridItemConfig> var2) {
      boolean var3 = false;
      if(var2 != null) {
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            if(((PromotedListGridItem.PromotedListGridItemConfig)var4.next()).url.equals(var1)) {
               var3 = true;
               break;
            }
         }
      }

      return var3;
   }

   public static TabbedBrowseFragment newInstance(String var0, String var1, int var2, String var3) {
      TabbedBrowseFragment var4 = new TabbedBrowseFragment();
      if(var2 >= 0) {
         var4.mBackendId = var2;
      }

      if(!TextUtils.isEmpty(var1)) {
         var4.mBreadcrumb = var1;
      }

      DfeToc var5 = FinskyApp.get().getToc();
      var4.setArguments(var5, var0);
      var4.setArgument("TabbedBrowseFragment.ReferrerUrl", var3);
      return var4;
   }

   private void recordState() {
      if(this.isDataReady()) {
         Bundle var1 = this.mSavedInstanceState;
         DfeBrowse var2 = this.mBrowseData;
         var1.putParcelable("TabbedBrowseFragment.BrowseData", var2);
         if(this.mPromoList != null) {
            Bundle var3 = this.mSavedInstanceState;
            DfeList var4 = this.mPromoList;
            var3.putParcelable("TabbedBrowseFragment.PromoData", var4);
            if(this.mFeaturedTab != null && this.mFeaturedTab.getCannedDataFromSequencer() != null) {
               List var5 = this.mFeaturedTab.getCannedDataFromSequencer();
               ArrayList var6 = new ArrayList(var5);
               this.mSavedInstanceState.putParcelableArrayList("TabbedBrowseFragment.PromoCannedData", var6);
            }
         }

         if(this.mBreadcrumb != null) {
            Bundle var7 = this.mSavedInstanceState;
            String var8 = this.mBreadcrumb;
            var7.putString("TabbedBrowseFragment.Breadcrumb", var8);
         }

         Bundle var9 = this.mSavedInstanceState;
         int var10 = this.mBackendId;
         var9.putInt("TabbedBrowseFragment.BackendId", var10);
         if(this.mListTabs != null) {
            ArrayList var11 = Lists.newArrayList();
            ArrayList var12 = Lists.newArrayList();
            ArrayList var13 = Lists.newArrayList();
            Iterator var14 = this.mListTabs.iterator();

            while(var14.hasNext()) {
               ListTab var15 = (ListTab)var14.next();
               DfeList var16 = var15.getDfeList();
               var11.add(var16);
               String var18 = var15.getTitle();
               var12.add(var18);
               Parcelable var20 = var15.getListViewParcelable();
               var13.add(var20);
            }

            this.mSavedInstanceState.putParcelableArrayList("TabbedBrowseFragment.ListsData", var11);
            this.mSavedInstanceState.putStringArrayList("TabbedBrowseFragment.ListsTitles", var12);
            this.mSavedInstanceState.putParcelableArrayList("TabbedBrowseFragment.ListsParcels", var13);
            if(this.mSlidingPanel != null) {
               Bundle var22 = this.mSavedInstanceState;
               int var23 = this.mSlidingPanel.getSelectedPanel();
               var22.putInt("TabbedBrowseFragment.SelectedTab", var23);
            }
         }
      }
   }

   private void replacePageContents(View var1) {
      ViewGroup var2 = (ViewGroup)this.mDataView.findViewById(2131755111);
      var2.removeAllViews();
      LayoutParams var3 = new LayoutParams(-1, -1);
      var2.addView(var1, var3);
   }

   private void setSelectedPanel() {
      int var1 = this.mSlidingPanel.getSelectedPanel();
      ArrayList var2 = Lists.newArrayList();
      if(this.mCategoryTab != null) {
         CategoryTab var3 = this.mCategoryTab;
         var2.add(var3);
      }

      if(this.mFeaturedTab != null) {
         GridFeaturedTab var5 = this.mFeaturedTab;
         var2.add(var5);
      }

      if(this.mListTabs != null) {
         Iterator var7 = this.mListTabs.iterator();

         while(var7.hasNext()) {
            ListTab var8 = (ListTab)var7.next();
            var2.add(var8);
         }
      }

      int var10 = 0;

      while(true) {
         int var11 = var2.size();
         if(var10 >= var11) {
            return;
         }

         SlidingPanelTab var12 = (SlidingPanelTab)var2.get(var10);
         byte var13;
         if(var10 == var1) {
            var13 = 1;
         } else {
            var13 = 0;
         }

         var12.setTabSelected((boolean)var13);
         ++var10;
      }
   }

   protected int getLayoutRes() {
      return 2130968711;
   }

   protected boolean isDataReady() {
      boolean var1;
      if(this.mBrowseData != null && this.mBrowseData.isReady() && (this.mContentListData == null || this.mContentListData.isReady()) && (this.mPromoList == null || this.mPromoList.isReady())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      String var2 = this.getArguments().getString("TabbedBrowseFragment.ReferrerUrl");
      this.mReferringBrowseUrl = var2;
      if(var1 != null) {
         this.mSavedInstanceState = var1;
      }

      if(this.mSavedInstanceState.containsKey("TabbedBrowseFragment.BrowseData")) {
         if(this.mBrowseData != null) {
            DfeBrowse var4 = this.mBrowseData;
            var4.removeDataChangedListener(this);
         }

         DfeBrowse var6 = (DfeBrowse)this.mSavedInstanceState.getParcelable("TabbedBrowseFragment.BrowseData");
         this.mBrowseData = var6;
         if(this.mBrowseData.hasCategories()) {
            Context var7 = this.mContext;
            LayoutInflater var8 = this.getActivity().getLayoutInflater();
            NavigationManager var9 = this.mNavigationManager;
            BitmapLoader var10 = this.mBitmapLoader;
            DfeBrowse var11 = this.mBrowseData;
            String var12 = this.mUrl;
            CategoryTab var13 = new CategoryTab(var7, var8, var9, var10, var11, var12);
            this.mCategoryTab = var13;
         }

         if(this.mSavedInstanceState.containsKey("TabbedBrowseFragment.PromoData")) {
            DfeList var14 = (DfeList)this.mSavedInstanceState.getParcelable("TabbedBrowseFragment.PromoData");
            this.mPromoList = var14;
            DfeList var15 = this.mPromoList;
            DfeApi var16 = this.mDfeApi;
            var15.setDfeApi(var16);
            if(var1 != null) {
               this.mPromoList.resetItems();
            }

            DfeList var17 = this.mPromoList;
            var17.addDataChangedListener(this);
            DfeList var19 = this.mPromoList;
            var19.addErrorListener(this);
            Context var21 = this.mContext;
            LayoutInflater var22 = this.getActivity().getLayoutInflater();
            BitmapLoader var23 = this.mBitmapLoader;
            NavigationManager var24 = this.mNavigationManager;
            DfeList var25 = this.mPromoList;
            String var26 = this.mReferringBrowseUrl;
            String var27 = this.mUrl;
            DfeToc var28 = this.getToc();
            GridFeaturedTab var29 = new GridFeaturedTab(var21, var22, var23, var24, var25, var26, var27, var28);
            this.mFeaturedTab = var29;
            if(this.mSavedInstanceState.containsKey("TabbedBrowseFragment.PromoCannedData")) {
               ArrayList var30 = this.mSavedInstanceState.getParcelableArrayList("TabbedBrowseFragment.PromoCannedData");
               this.mFeaturedTab.onCannedDataReady(var30);
            }
         }

         if(this.mSavedInstanceState.containsKey("TabbedBrowseFragment.Breadcrumb")) {
            String var31 = this.mSavedInstanceState.getString("TabbedBrowseFragment.Breadcrumb");
            this.mBreadcrumb = var31;
         }

         if(this.mSavedInstanceState.containsKey("TabbedBrowseFragment.BackendId")) {
            int var32 = this.mSavedInstanceState.getInt("TabbedBrowseFragment.BackendId");
            this.mBackendId = var32;
         }

         if(this.mSavedInstanceState.containsKey("TabbedBrowseFragment.ListsData")) {
            ArrayList var33 = this.mSavedInstanceState.getParcelableArrayList("TabbedBrowseFragment.ListsData");
            ArrayList var34 = this.mSavedInstanceState.getParcelableArrayList("TabbedBrowseFragment.ListsParcels");
            ArrayList var35 = this.mSavedInstanceState.getStringArrayList("TabbedBrowseFragment.ListsTitles");
            ArrayList var36 = Lists.newArrayList();
            this.mListTabs = var36;
            GridFeaturedTab var37 = this.mFeaturedTab;
            int var38 = 0;

            while(true) {
               int var39 = var33.size();
               if(var38 >= var39) {
                  break;
               }

               DfeList var40 = (DfeList)var33.get(var38);
               DfeApi var41 = this.mDfeApi;
               var40.setDfeApi(var41);
               if(var1 != null) {
                  ((DfeList)var33.get(var38)).resetItems();
               }

               Context var42 = this.mContext;
               NavigationManager var43 = this.mNavigationManager;
               BitmapLoader var44 = this.mBitmapLoader;
               LayoutInflater var45 = this.getActivity().getLayoutInflater();
               DfeList var46 = (DfeList)var33.get(var38);
               String var47 = (String)var35.get(var38);
               String var48 = this.mReferringBrowseUrl;
               String var49 = this.mUrl;
               ListTab var50 = new ListTab(var42, var43, var44, var45, var46, var47, var37, var48, var49);
               Parcelable var51 = (Parcelable)var34.get(var38);
               var50.setListViewParcelable(var51);
               this.mListTabs.add(var50);
               var37 = null;
               ++var38;
            }
         }

         if(this.mSavedInstanceState.containsKey("TabbedBrowseFragment.SelectedTab")) {
            int var53 = this.mSavedInstanceState.getInt("TabbedBrowseFragment.SelectedTab");
            this.mRestoreSelectedPanel = var53;
         }
      }

      if(!this.isDataReady()) {
         this.switchToLoading();
         this.requestData();
         this.rebindActionBar();
      } else {
         this.rebindViews();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      boolean var2 = this.getResources().getBoolean(2131296259);
      this.mShouldAlwaysKeepFeaturedData = var2;
      this.setRetainInstance((boolean)1);
   }

   public void onDataChanged() {
      if(this.mBrowseData.isReady()) {
         if(this.mCategoryTab == null && this.mBrowseData.hasCategories()) {
            Context var1 = this.mContext;
            LayoutInflater var2 = this.getActivity().getLayoutInflater();
            NavigationManager var3 = this.mNavigationManager;
            BitmapLoader var4 = this.mBitmapLoader;
            DfeBrowse var5 = this.mBrowseData;
            String var6 = this.mUrl;
            CategoryTab var7 = new CategoryTab(var1, var2, var3, var4, var5, var6);
            this.mCategoryTab = var7;
         }

         if(this.mFeaturedTab == null && this.mBrowseData.hasPromotionalItems()) {
            DfeList var8 = this.mBrowseData.buildPromoList();
            this.mPromoList = var8;
            this.mPromoList.addDataChangedListener(this);
            this.mPromoList.addErrorListener(this);
            Context var9 = this.mContext;
            LayoutInflater var10 = this.getActivity().getLayoutInflater();
            BitmapLoader var11 = this.mBitmapLoader;
            NavigationManager var12 = this.mNavigationManager;
            DfeList var13 = this.mPromoList;
            String var14 = this.mReferringBrowseUrl;
            String var15 = this.mUrl;
            DfeToc var16 = this.getToc();
            GridFeaturedTab var17 = new GridFeaturedTab(var9, var10, var11, var12, var13, var14, var15, var16);
            this.mFeaturedTab = var17;
         }

         if(this.mContentListData == null && this.mListTabs == null) {
            DfeList var18 = this.mBrowseData.buildContentList();
            this.mContentListData = var18;
            if(this.mContentListData != null) {
               this.mContentListData.removeDataChangedListener(this);
               this.mContentListData.removeErrorListener(this);
               this.mContentListData.addDataChangedListener(this);
               this.mContentListData.addErrorListener(this);
               DfeList var19 = this.mContentListData;
               DfeApi var20 = this.mDfeApi;
               var19.setDfeApi(var20);
               this.mContentListData.startLoadItems();
            } else {
               ArrayList var42 = Lists.newArrayList();
               this.mListTabs = var42;
            }
         }
      }

      if(this.mContentListData != null && this.mListTabs == null && this.mContentListData.isReady()) {
         int var21 = this.mContentListData.getBackendId();
         this.mBackendId = var21;
         String var22 = this.getBreadcrumbText();
         this.mBreadcrumb = var22;
         List var23 = null;
         if(this.mFeaturedTab != null) {
            Context var24 = this.mContext;
            String var25 = this.mUrl;
            DfeList var26 = this.mContentListData;
            int var27 = this.mFeaturedTab.numCannedItemsSlots();
            DfeToc var28 = this.getToc();
            var23 = ElegantFeaturedGridHackIsolator.getPromotedItemsForPageFromList(var24, var25, var26, var27, var28);
            this.mFeaturedTab.onCannedDataReady(var23);
         }

         ArrayList var29 = Lists.newArrayList();
         this.mListTabs = var29;
         if(this.mContentListData.getBucketCount() == 1) {
            List var30 = this.mListTabs;
            Context var31 = this.mContext;
            NavigationManager var32 = this.mNavigationManager;
            BitmapLoader var33 = this.mBitmapLoader;
            LayoutInflater var34 = this.getActivity().getLayoutInflater();
            DfeList var35 = this.mContentListData;
            String var36 = this.mContentListData.getBucket(0).getTitle();
            GridFeaturedTab var37 = this.mFeaturedTab;
            String var38 = this.mReferringBrowseUrl;
            String var39 = this.mUrl;
            ListTab var40 = new ListTab(var31, var32, var33, var34, var35, var36, var37, var38, var39);
            var30.add(var40);
         } else {
            GridFeaturedTab var43 = this.mFeaturedTab;
            Iterator var44 = this.mContentListData.getBucketList().iterator();

            while(var44.hasNext()) {
               DocList.Bucket var45 = (DocList.Bucket)var44.next();
               String var46 = var45.getFullContentsUrl();
               if(!this.isCannedTile(var46, var23)) {
                  String var47 = var45.getFullContentsUrl().replaceFirst("browse", "list");
                  if(!TextUtils.isEmpty(var47)) {
                     DfeApi var48 = this.mDfeApi;
                     DfeList var49 = new DfeList(var48, var47, (boolean)1);
                     List var50 = this.mListTabs;
                     Context var51 = this.mContext;
                     NavigationManager var52 = this.mNavigationManager;
                     BitmapLoader var53 = this.mBitmapLoader;
                     LayoutInflater var54 = this.getActivity().getLayoutInflater();
                     String var55 = var45.getTitle();
                     String var56 = this.mReferringBrowseUrl;
                     String var57 = this.mUrl;
                     ListTab var58 = new ListTab(var51, var52, var53, var54, var49, var55, var43, var56, var57);
                     var50.add(var58);
                     var43 = null;
                  }
               }
            }
         }
      }

      if(this.isDataReady()) {
         super.onDataChanged();
      }
   }

   public void onDestroyView() {
      this.recordState();
      ((ViewGroup)this.mDataView.findViewById(2131755111)).removeAllViews();
      if(this.mSlidingPanel != null) {
         this.mSlidingPanel.removeOnPanelSelectedListener();
         this.mSlidingPanel.removeAllViews();
         this.mSlidingPanel = null;
      }

      this.clearState();
      super.onDestroyView();
   }

   protected void onInitViewBinders() {}

   public void onPanelSelected(View var1) {
      this.setSelectedPanel();
      if(this.mListTabs != null) {
         Iterator var2 = this.mListTabs.iterator();

         while(var2.hasNext()) {
            ((ListTab)var2.next()).flushUnusedPages();
         }
      }

      long var3 = System.currentTimeMillis();
      this.mLastPanelSwipeTime = var3;
      Handler var5 = this.mHandler;
      TabbedBrowseFragment.1 var6 = new TabbedBrowseFragment.1();
      var5.postDelayed(var6, 200L);
   }

   public void onSaveInstanceState(Bundle var1) {
      this.recordState();
      Bundle var2 = this.mSavedInstanceState;
      var1.putAll(var2);
      super.onSaveInstanceState(var1);
   }

   public void rebindActionBar() {
      PageFragmentHost var1 = this.mPageFragmentHost;
      String var2 = this.mBreadcrumb;
      var1.updateBreadcrumb(var2);
      PageFragmentHost var3 = this.mPageFragmentHost;
      int var4 = this.mBackendId;
      var3.updateCurrentBackendId(var4);
   }

   protected void rebindViews() {
      super.rebindViews();
      this.switchToData();
      this.rebindActionBar();
      if(this.mSlidingPanel != null) {
         int var1 = this.mSlidingPanel.getPanelCount();
         int var2 = this.getTabCount();
         if(var1 == var2) {
            return;
         }
      }

      ArrayList var3 = Lists.newArrayList();
      ArrayList var4 = Lists.newArrayList();
      if(this.mCategoryTab != null) {
         CategoryTab var5 = this.mCategoryTab;
         int var6 = this.mBackendId;
         View var7 = var5.getView(var6);
         var3.add(var7);
         String var9 = this.mCategoryTab.getTitle();
         var4.add(var9);
      }

      if(this.mFeaturedTab != null) {
         GridFeaturedTab var11 = this.mFeaturedTab;
         int var12 = this.mBackendId;
         View var13 = var11.getView(var12);
         var3.add(var13);
         String var15 = this.mFeaturedTab.getTitle();
         var4.add(var15);
      }

      Iterator var17 = this.mListTabs.iterator();

      while(var17.hasNext()) {
         ListTab var18 = (ListTab)var17.next();
         int var19 = this.mBackendId;
         View var20 = var18.getView(var19);
         var3.add(var20);
         String var22 = var18.getTitle();
         var4.add(var22);
      }

      if(this.mSlidingPanel == null) {
         Context var24 = this.mContext;
         SlidingPanel var25 = new SlidingPanel(var24, (AttributeSet)null);
         this.mSlidingPanel = var25;
         this.mSlidingPanel.setOnPanelSelectedListener(this);
         SlidingPanel var26 = this.mSlidingPanel;
         this.replacePageContents(var26);
      }

      SlidingPanel var27 = this.mSlidingPanel;
      String[] var28 = new String[0];
      String[] var29 = (String[])var4.toArray(var28);
      View[] var30 = new View[0];
      View[] var31 = (View[])var3.toArray(var30);
      int var32 = this.mBackendId;
      var27.setPanels(var29, var31, var32);
      if(this.mContext.getResources().getBoolean(2131296258) && this.mCategoryTab != null && var3.size() > 1) {
         int var33 = this.mContext.getResources().getInteger(2131492868) / 2;
         if(var33 == 3) {
            this.mSlidingPanel.setFirstTabPartialWidth(0.33333334F);
         } else if(var33 == 4) {
            this.mSlidingPanel.setFirstTabPartialWidth(0.5F);
         }
      }

      if(this.mRestoreSelectedPanel == -1) {
         if(this.getTabCount() > 0) {
            SlidingPanel var34 = this.mSlidingPanel;
            byte var35;
            if(this.mCategoryTab == null) {
               var35 = 0;
            } else {
               var35 = 1;
            }

            var34.setSelectedPanel(var35);
         }
      } else {
         SlidingPanel var36 = this.mSlidingPanel;
         int var37 = this.mRestoreSelectedPanel;
         var36.setSelectedPanel(var37);
         this.mRestoreSelectedPanel = -1;
      }
   }

   public void refresh() {
      if(this.isDataReady()) {
         this.rebindViews();
      } else {
         super.refresh();
      }
   }

   protected void requestData() {
      this.clearState();
      DfeApi var1 = this.mDfeApi;
      String var2 = this.mUrl;
      DfeBrowse var3 = new DfeBrowse(var1, var2);
      this.mBrowseData = var3;
      this.mBrowseData.removeDataChangedListener(this);
      this.mBrowseData.addDataChangedListener(this);
      this.mBrowseData.removeErrorListener(this);
      this.mBrowseData.addErrorListener(this);
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         byte var1 = 1;
         long var2 = TabbedBrowseFragment.this.mLastPanelSwipeTime + 200L;
         long var4 = System.currentTimeMillis();
         if(var2 <= var4) {
            if(TabbedBrowseFragment.this.mSlidingPanel != null) {
               if(TabbedBrowseFragment.this.mFeaturedTab != null) {
                  GridFeaturedTab var9;
                  byte var11;
                  label41: {
                     TabbedBrowseFragment var6 = TabbedBrowseFragment.this;
                     int var7 = TabbedBrowseFragment.this.getFeaturedTabIndex();
                     ImageLoadState var8 = var6.getImageLoadStateForTab(var7);
                     var9 = TabbedBrowseFragment.this.mFeaturedTab;
                     if(!TabbedBrowseFragment.this.mShouldAlwaysKeepFeaturedData) {
                        ImageLoadState var10 = ImageLoadState.ALL;
                        if(var8 != var10) {
                           var11 = 0;
                           break label41;
                        }
                     }

                     var11 = 1;
                  }

                  var9.setImagesEnabled((boolean)var11);
               }

               if(TabbedBrowseFragment.this.mListTabs != null) {
                  byte var12;
                  if(TabbedBrowseFragment.this.mCategoryTab != null) {
                     var12 = 1;
                  } else {
                     var12 = 0;
                  }

                  if(TabbedBrowseFragment.this.mFeaturedTab == null) {
                     var1 = 0;
                  }

                  int var13 = var12 + var1;
                  int var14 = var13;

                  while(true) {
                     int var15 = TabbedBrowseFragment.this.getTabCount();
                     if(var14 >= var15) {
                        return;
                     }

                     List var16 = TabbedBrowseFragment.this.mListTabs;
                     int var17 = var14 - var13;
                     ListTab var18 = (ListTab)var16.get(var17);
                     ImageLoadState var19 = TabbedBrowseFragment.this.getImageLoadStateForTab(var14);
                     var18.setImageLoadState(var19);
                     ++var14;
                  }
               }
            }
         }
      }
   }
}

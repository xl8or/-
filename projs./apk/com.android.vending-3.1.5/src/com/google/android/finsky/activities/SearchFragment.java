package com.google.android.finsky.activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.BucketedList;
import com.google.android.finsky.api.model.DfeSearch;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.BucketedViewBinder;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.SingleCorpusSearchViewBinder;
import com.google.android.finsky.layout.CustomActionBar;
import com.google.android.finsky.model.Bucket;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.utils.BitmapLoader;

public class SearchFragment extends PageFragment implements OnDataChangedListener {

   private static final String KEY_BACKEND = "finsky.PageFragment.SearchFragment.backend";
   private static final String KEY_QUERY = "finsky.PageFragment.SearchFragment.query";
   private static final String KEY_REFERRER = "finsky.PageFragment.SearchFragment.referrer";
   private int mBackend;
   private final BucketedViewBinder mBucketListViewBinder;
   private Bucket mLastKnownBucket;
   private int mNumCellsTallSearch;
   private int mNumCellsWideSearch;
   private String mQuery;
   private String mReferrerUrl;
   private DfeSearch mSearchData;
   private SingleCorpusSearchViewBinder mSingleBucketViewBinder;


   public SearchFragment() {
      BucketedViewBinder var1 = new BucketedViewBinder(2130968654, 2130968710);
      this.mBucketListViewBinder = var1;
   }

   public static SearchFragment newInstance(String var0, int var1, String var2) {
      SearchFragment var3 = new SearchFragment();
      DfeToc var4 = FinskyApp.get().getToc();
      var3.setArguments(var4);
      var3.setArgument("finsky.PageFragment.SearchFragment.query", var0);
      var3.setArgument("finsky.PageFragment.SearchFragment.backend", var1);
      var3.setArgument("finsky.PageFragment.SearchFragment.referrer", var2);
      return var3;
   }

   private void setData() {
      if(this.isDataReady()) {
         this.mDataView.findViewById(2131755069).setVisibility(0);
         if(this.mSearchData.getBucketCount() > 1) {
            if(!this.mBucketListViewBinder.hasData()) {
               BucketedViewBinder var1 = this.mBucketListViewBinder;
               DfeSearch var2 = this.mSearchData;
               var1.setData(var2);
               if(this.isAdded()) {
                  this.switchToData();
                  this.rebindViews();
               }
            }
         } else {
            if(this.mSearchData.getBucketCount() > 0 && this.mLastKnownBucket == null) {
               DocList.Bucket var3 = this.mSearchData.getBucket(0);
               Bucket var4 = new Bucket(var3);
               this.mLastKnownBucket = var4;
            }

            if(!this.mSingleBucketViewBinder.hasData()) {
               SingleCorpusSearchViewBinder var5 = this.mSingleBucketViewBinder;
               DfeSearch var6 = this.mSearchData;
               var5.setData((BucketedList)var6);
               if(this.isAdded()) {
                  this.switchToData();
                  this.rebindViews();
               }
            }
         }
      }
   }

   protected int getLayoutRes() {
      return 2130968705;
   }

   public String getQuery() {
      return this.mQuery;
   }

   protected boolean isDataReady() {
      boolean var1;
      if(this.mSearchData != null && this.mSearchData.isReady()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void logListView() {
      if(this.isDataReady()) {
         int var1 = 0;

         while(true) {
            int var2 = this.mSearchData.getBucketCount();
            if(var1 >= var2) {
               return;
            }

            Analytics var3 = FinskyApp.get().getAnalytics();
            String var4 = this.mReferrerUrl;
            String var5 = this.mSearchData.getUrl();
            String var6 = this.mSearchData.getBucket(var1).getAnalyticsCookie();
            var3.logListViewOnPage(var4, (String)null, var5, var6);
            ++var1;
         }
      }
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      if(this.mSearchData == null) {
         DfeApi var2 = this.mDfeApi;
         String var3 = this.mQuery;
         int var4 = this.mBackend;
         DfeSearch var5 = new DfeSearch(var2, var3, var4);
         this.mSearchData = var5;
         this.mSearchData.addDataChangedListener(this);
         this.mSearchData.addErrorListener(this);
         Analytics var6 = FinskyApp.get().getAnalytics();
         String var7 = this.mReferrerUrl;
         String var8 = this.mSearchData.getUrl();
         var6.logPageView(var7, (String)null, var8);
      }

      int var9 = this.getResources().getInteger(2131492873);
      this.mNumCellsWideSearch = var9;
      int var10 = this.getResources().getInteger(2131492872);
      this.mNumCellsTallSearch = var10;
      if(!this.isDataReady()) {
         this.switchToLoading();
         this.requestData();
         this.rebindActionBar();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      String var2 = this.getArguments().getString("finsky.PageFragment.SearchFragment.query");
      this.mQuery = var2;
      int var3 = this.getArguments().getInt("finsky.PageFragment.SearchFragment.backend");
      this.mBackend = var3;
      String var4 = this.getArguments().getString("finsky.PageFragment.SearchFragment.referrer");
      this.mReferrerUrl = var4;
      SingleCorpusSearchViewBinder var5 = new SingleCorpusSearchViewBinder;
      byte var6;
      if(this.getToc().getCorpusList().size() > 1) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      var5.<init>(2130968654, 2130968591, 2130968710, (boolean)1, (boolean)var6);
      this.mSingleBucketViewBinder = var5;
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4 = super.onCreateView(var1, var2, var3);
      this.setData();
      ((ListView)var4.findViewById(2131755069)).setItemsCanFocus((boolean)1);
      return var4;
   }

   public void onDataChanged() {
      this.setData();
      this.logListView();
   }

   public void onDestroy() {
      if(this.mSearchData != null) {
         this.mSearchData.unregisterAll();
         this.mSearchData = null;
      }

      super.onDestroy();
   }

   public void onDestroyView() {
      this.mSingleBucketViewBinder.onDestroyView();
      this.mBucketListViewBinder.onDestroyView();
      super.onDestroyView();
   }

   protected void onInitViewBinders() {
      BucketedViewBinder var1 = this.mBucketListViewBinder;
      Context var2 = this.mContext;
      NavigationManager var3 = this.mNavigationManager;
      BitmapLoader var4 = this.mBitmapLoader;
      var1.init(var2, var3, var4);
      SingleCorpusSearchViewBinder var5 = this.mSingleBucketViewBinder;
      Context var6 = this.mContext;
      NavigationManager var7 = this.mNavigationManager;
      BitmapLoader var8 = this.mBitmapLoader;
      var5.init(var6, var7, var8);
   }

   public void rebindActionBar() {
      PageFragmentHost var1 = this.mPageFragmentHost;
      Resources var2 = this.getResources();
      Object[] var3 = new Object[1];
      String var4 = this.mQuery;
      var3[0] = var4;
      String var5 = var2.getString(2131231150, var3);
      var1.updateBreadcrumb(var5);
      CustomActionBar var6 = ((MainActivity)this.mPageFragmentHost).getCustomActionBar();
      String var7 = this.mSearchData.getQuery();
      var6.updateSearchQuery(var7);
      if(this.mLastKnownBucket != null) {
         PageFragmentHost var8 = this.mPageFragmentHost;
         int var9 = this.mLastKnownBucket.getBackend();
         var8.updateCurrentBackendId(var9);
      } else {
         PageFragmentHost var10 = this.mPageFragmentHost;
         int var11 = this.mBackend;
         var10.updateCurrentBackendId(var11);
      }
   }

   protected void rebindViews() {
      this.rebindActionBar();
      String var1 = this.mSearchData.getUrl();
      if(this.mSearchData.getBucketCount() > 1) {
         BucketedViewBinder var2 = this.mBucketListViewBinder;
         ViewGroup var3 = this.mDataView;
         int var4 = this.mNumCellsWideSearch;
         int var5 = this.mNumCellsTallSearch;
         String var6 = this.mSearchData.getQuery();
         String var7 = this.mSearchData.getSuggestedQuery();
         var2.bind(var3, var4, var5, var6, var7, var1);
      } else {
         SingleCorpusSearchViewBinder var8 = this.mSingleBucketViewBinder;
         ViewGroup var9 = this.mDataView;
         int var10 = this.mNumCellsWideSearch;
         Bucket var11 = this.mLastKnownBucket;
         String var12 = this.mSearchData.getSuggestedQuery();
         var8.bind(var9, var10, var11, var12, var1);
      }
   }

   public void refresh() {
      if(this.isDataReady()) {
         this.logListView();
         this.rebindViews();
      } else {
         this.mSearchData.clearTransientState();
         super.refresh();
      }
   }

   protected void requestData() {
      this.mSearchData.startLoadItems();
   }
}

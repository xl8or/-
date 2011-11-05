package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SlidingPanelTab;
import com.google.android.finsky.adapters.ImageLoadState;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.model.BucketedList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.SingleCorpusSearchViewBinder;
import com.google.android.finsky.model.Bucket;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.ErrorStrings;

public class ListTab implements SlidingPanelTab, OnDataChangedListener, Response.ErrorListener {

   private final SingleCorpusSearchViewBinder mBinder;
   private final String mCurrentBrowseUrl;
   private ImageLoadState mImageLoadState;
   private boolean mIsCurrentlySelected;
   private Bucket mLastKnownBucket;
   private final LayoutInflater mLayoutInflater;
   private final DfeList mList;
   private boolean mListBoundAlready;
   private SlidingPanelTab.OnListLoaded mListLoadedListener;
   private ViewGroup mListTabContainer;
   private Parcelable mListViewParcel;
   private final int mNumCellsWide;
   private final String mReferrerBrowseUrl;
   private final String mTitle;


   public ListTab(Context var1, NavigationManager var2, BitmapLoader var3, LayoutInflater var4, DfeList var5, String var6, SlidingPanelTab.OnListLoaded var7, String var8, String var9) {
      byte var10 = 0;
      SingleCorpusSearchViewBinder var11 = new SingleCorpusSearchViewBinder(2130968675, 2130968591, 2130968710, (boolean)0, (boolean)var10);
      this.mBinder = var11;
      this.mListBoundAlready = (boolean)0;
      ImageLoadState var12 = ImageLoadState.NONE;
      this.mImageLoadState = var12;
      this.mLayoutInflater = var4;
      this.mBinder.init(var1, var2, var3);
      SingleCorpusSearchViewBinder var13 = this.mBinder;
      ImageLoadState var14 = this.mImageLoadState;
      var13.setImageLoadState(var14);
      this.mList = var5;
      this.mListLoadedListener = var7;
      this.mReferrerBrowseUrl = var8;
      this.mCurrentBrowseUrl = var9;
      int var15 = var1.getResources().getInteger(2131492866);
      this.mNumCellsWide = var15;
      DfeList var16 = this.mList;
      int var17 = var1.getResources().getInteger(2131492867);
      int var18 = this.mNumCellsWide;
      int var19 = var17 * var18;
      var16.setWindowDistance(var19);
      this.mList.addDataChangedListener(this);
      this.mList.addErrorListener(this);
      this.mList.startLoadItems();
      String var20 = this.normalizeTitle(var6);
      this.mTitle = var20;
   }

   private void bindList(View var1, ListView var2) {
      if(!this.mListBoundAlready) {
         var1.setVisibility(8);
         var2.setVisibility(0);
         SingleCorpusSearchViewBinder var3 = this.mBinder;
         DfeList var4 = this.mList;
         var3.setData((BucketedList)var4);
         SingleCorpusSearchViewBinder var5 = this.mBinder;
         ViewGroup var6 = this.mListTabContainer;
         int var7 = this.mNumCellsWide;
         Bucket var8 = this.mLastKnownBucket;
         String var9 = this.mCurrentBrowseUrl;
         var5.bind(var6, var7, var8, (String)null, var9);
         if(this.mListViewParcel != null) {
            Parcelable var10 = this.mListViewParcel;
            var2.onRestoreInstanceState(var10);
            this.mListViewParcel = null;
         }

         ImageLoadState var11 = this.mImageLoadState;
         this.setImageLoadState(var11);
         this.mListBoundAlready = (boolean)1;
      }
   }

   private void logClickForCurrentPage() {
      if(this.mList.isReady()) {
         if(this.mList.getBucketCount() > 0) {
            Analytics var1 = FinskyApp.get().getAnalytics();
            String var2 = this.mReferrerBrowseUrl;
            String var3 = this.mCurrentBrowseUrl;
            String var4 = this.mList.getBucket(0).getAnalyticsCookie();
            var1.logListViewOnPage(var2, (String)null, var3, var4);
         }
      }
   }

   private String normalizeTitle(String var1) {
      String var2 = var1.toLowerCase();
      if(var2.toLowerCase().contains("top free -")) {
         StringBuilder var3 = (new StringBuilder()).append("Top ");
         int var4 = var1.length();
         String var5 = var1.substring(11, var4);
         var2 = var3.append(var5).toString();
      } else if(var2.toLowerCase().contains("best selling -")) {
         StringBuilder var6 = (new StringBuilder()).append("Best Selling ");
         int var7 = var1.length();
         String var8 = var1.substring(15, var7);
         var2 = var6.append(var8).toString();
      }

      return var2;
   }

   private void syncViewToState() {
      if(this.mListTabContainer != null) {
         View var1 = this.mListTabContainer.findViewById(2131755312);
         ListView var2 = (ListView)this.mListTabContainer.findViewById(2131755069);
         View var3 = this.mListTabContainer.findViewById(2131755258);
         if(this.mList.isReady()) {
            if(this.mListLoadedListener != null) {
               SlidingPanelTab.OnListLoaded var4 = this.mListLoadedListener;
               DfeList var5 = this.mList;
               var4.onListReady(var5);
               this.mListLoadedListener = null;
            }

            if(this.mList.getBucketCount() > 0 && this.mLastKnownBucket == null) {
               DocList.Bucket var6 = this.mList.getBucket(0);
               Bucket var7 = new Bucket(var6);
               this.mLastKnownBucket = var7;
            }

            var3.setVisibility(8);
            var2.setVisibility(0);
            var1.setVisibility(8);
            this.bindList(var1, var2);
         } else if(this.mList.inErrorState()) {
            var3.setVisibility(0);
            TextView var8 = (TextView)var3.findViewById(2131755191);
            FinskyApp var9 = FinskyApp.get();
            Response.ErrorCode var10 = this.mList.getErrorCode();
            String var11 = this.mList.getErrorMessage();
            String var12 = ErrorStrings.get(var9, var10, var11);
            var8.setText(var12);
            View var13 = var3.findViewById(2131755192);
            ListTab.1 var14 = new ListTab.1();
            var13.setOnClickListener(var14);
            var2.setVisibility(8);
            var1.setVisibility(8);
         } else {
            var3.setVisibility(8);
            var2.setVisibility(8);
            var1.setVisibility(0);
         }
      }
   }

   public void flushUnusedPages() {
      this.mList.flushUnusedPages();
   }

   public DfeList getDfeList() {
      return this.mList;
   }

   public Parcelable getListViewParcelable() {
      Parcelable var2;
      if(this.mListTabContainer != null) {
         ListView var1 = (ListView)this.mListTabContainer.findViewById(2131755069);
         if(var1.getVisibility() == 0) {
            var2 = var1.onSaveInstanceState();
            return var2;
         }
      }

      var2 = null;
      return var2;
   }

   public String getTitle() {
      return this.mTitle;
   }

   public View getView(int var1) {
      if(this.mListTabContainer == null) {
         ViewGroup var2 = (ViewGroup)this.mLayoutInflater.inflate(2130968712, (ViewGroup)null);
         this.mListTabContainer = var2;
         this.syncViewToState();
      }

      return this.mListTabContainer;
   }

   public void onDataChanged() {
      if(this.mIsCurrentlySelected) {
         this.logClickForCurrentPage();
      }

      this.syncViewToState();
   }

   public void onDestroy() {
      this.mBinder.onDestroyView();
      this.mList.removeDataChangedListener(this);
      this.mList.removeErrorListener(this);
      this.mList.flushUnusedPages();
      this.mListTabContainer = null;
      this.mListBoundAlready = (boolean)0;
      this.mListLoadedListener = null;
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      this.syncViewToState();
   }

   public void setImageLoadState(ImageLoadState var1) {
      this.mImageLoadState = var1;
      this.mBinder.setImageLoadState(var1);
   }

   public void setListViewParcelable(Parcelable var1) {
      this.mListViewParcel = var1;
   }

   public void setTabSelected(boolean var1) {
      if(var1 && !this.mIsCurrentlySelected) {
         this.logClickForCurrentPage();
      }

      this.mIsCurrentlySelected = var1;
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         ListTab.this.mList.resetItems();
         ListTab.this.mList.clearTransientState();
         ListTab.this.mList.startLoadItems();
         ListTab.this.syncViewToState();
      }
   }
}

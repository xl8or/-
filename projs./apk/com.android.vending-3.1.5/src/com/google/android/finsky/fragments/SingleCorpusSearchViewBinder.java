package com.google.android.finsky.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.adapters.ImageLoadState;
import com.google.android.finsky.adapters.SingleCorpusSearchAdapter;
import com.google.android.finsky.api.model.BucketedList;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.fragments.ViewBinder;
import com.google.android.finsky.model.Bucket;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;

public class SingleCorpusSearchViewBinder extends ViewBinder<BucketedList<?>> implements Response.ErrorListener, OnDataChangedListener {

   private SingleCorpusSearchAdapter mAdapter;
   private final int mCellLayoutId;
   private ViewGroup mContentLayout;
   private boolean mHasLoadedAtLeastOnce;
   private final int mHeaderLayoutId;
   private ImageLoadState mImageLoadState;
   private boolean mIncludeCorpusSpinner;
   private ListView mListView;
   private boolean mShowBucketHeaders;
   private final int mSuggestionBarLayoutId;


   public SingleCorpusSearchViewBinder(int var1, int var2, int var3, boolean var4, boolean var5) {
      ImageLoadState var6 = ImageLoadState.ALL;
      this.mImageLoadState = var6;
      this.mCellLayoutId = var1;
      this.mHeaderLayoutId = var2;
      this.mSuggestionBarLayoutId = var3;
      this.mShowBucketHeaders = var4;
      this.mIncludeCorpusSpinner = var5;
   }

   private void detachFromData() {
      if(this.mData != null) {
         ((BucketedList)this.mData).removeDataChangedListener(this);
         ((BucketedList)this.mData).removeErrorListener(this);
         this.mData = null;
      }
   }

   public void bind(ViewGroup var1, int var2, Bucket var3, String var4, String var5) {
      this.mContentLayout = var1;
      ListView var6 = (ListView)var1.findViewById(2131755069);
      this.mListView = var6;
      this.mListView.setItemsCanFocus((boolean)1);
      if(var3 == null) {
         ListView var7 = this.mListView;
         View var8 = this.mContentLayout.findViewById(2131755255);
         var7.setEmptyView(var8);
      } else {
         if(this.mAdapter != null) {
            this.mAdapter.onDestroyView();
         }

         Context var9 = this.mContext;
         BitmapLoader var10 = this.mBitmapLoader;
         NavigationManager var11 = this.mNavManager;
         PaginatedList var12 = (PaginatedList)this.mData;
         int var13 = this.mCellLayoutId;
         int var14 = this.mHeaderLayoutId;
         int var15 = this.mSuggestionBarLayoutId;
         boolean var16 = this.mShowBucketHeaders;
         boolean var17 = this.mIncludeCorpusSpinner;
         SingleCorpusSearchAdapter var22 = new SingleCorpusSearchAdapter(var9, var10, var11, var12, var2, var13, var14, var15, var3, var4, var16, var17, var5);
         this.mAdapter = var22;
         if(this.mHasLoadedAtLeastOnce) {
            this.mListView.setEmptyView((View)null);
         } else {
            ListView var27 = this.mListView;
            View var28 = this.mContentLayout.findViewById(2131755255);
            var27.setEmptyView(var28);
         }

         SingleCorpusSearchAdapter var23 = this.mAdapter;
         ImageLoadState var24 = this.mImageLoadState;
         var23.setImageLoadState(var24);
         ListView var25 = this.mListView;
         SingleCorpusSearchAdapter var26 = this.mAdapter;
         var25.setAdapter(var26);
      }
   }

   public void onDataChanged() {
      if(!this.mHasLoadedAtLeastOnce) {
         if(this.mListView != null) {
            ListView var1 = this.mListView;
            View var2 = this.mContentLayout.findViewById(2131755255);
            var1.setEmptyView(var2);
            this.mHasLoadedAtLeastOnce = (boolean)1;
         }
      }
   }

   public void onDestroyView() {
      this.detachFromData();
      if(this.mAdapter != null) {
         this.mAdapter.onDestroyView();
         this.mAdapter = null;
      }

      this.mListView = null;
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      if(this.mListView != null) {
         this.mAdapter.triggerFooterErrorMode();
      }
   }

   public void setData(BucketedList<?> var1) {
      this.detachFromData();
      super.setData(var1);
      this.mHasLoadedAtLeastOnce = (boolean)0;
      if(this.mData != null) {
         ((BucketedList)this.mData).addDataChangedListener(this);
         ((BucketedList)this.mData).addErrorListener(this);
      }
   }

   public void setImageLoadState(ImageLoadState var1) {
      this.mImageLoadState = var1;
      if(this.mAdapter != null) {
         this.mAdapter.setImageLoadState(var1);
      }
   }
}

package com.google.android.finsky.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.activities.RateReviewDialog;
import com.google.android.finsky.adapters.ReviewsAdapter;
import com.google.android.finsky.api.model.DfeReviews;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.ViewBinder;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.Rev;
import com.google.android.finsky.utils.BitmapLoader;

public class ReviewListViewBinder extends ViewBinder<DfeReviews> implements OnDataChangedListener, Response.ErrorListener, ReviewsAdapter.RateReviewHandler {

   private static final String TAG_RATE_REVIEW_DIALOG = "rate_review_dialog";
   private ReviewsAdapter mAdapter;
   private Fragment mContainerFragment;
   private ViewGroup mContentLayout;
   private Document mDocument;
   private boolean mHasLoadedAtLeastOnce;
   private ListView mReviewList;


   public ReviewListViewBinder() {}

   public void bind(View var1, Document var2) {
      ViewGroup var3 = (ViewGroup)var1;
      this.mContentLayout = var3;
      ListView var4 = (ListView)this.mContentLayout.findViewById(2131755214);
      this.mReviewList = var4;
      if(this.mAdapter != null) {
         this.mAdapter.onDestroyView();
      }

      Context var5 = this.mContext;
      DfeReviews var6 = (DfeReviews)this.mData;
      ReviewsAdapter var7 = new ReviewsAdapter(var5, var2, var6, this);
      this.mAdapter = var7;
      this.mDocument = var2;
      if(this.mHasLoadedAtLeastOnce) {
         this.mReviewList.setEmptyView((View)null);
      } else {
         ListView var10 = this.mReviewList;
         View var11 = this.mContentLayout.findViewById(2131755255);
         var10.setEmptyView(var11);
      }

      this.mReviewList.setItemsCanFocus((boolean)1);
      ListView var8 = this.mReviewList;
      ReviewsAdapter var9 = this.mAdapter;
      var8.setAdapter(var9);
   }

   public ReviewsAdapter getAdapter() {
      return this.mAdapter;
   }

   public void init(Context var1, Fragment var2, NavigationManager var3, BitmapLoader var4) {
      super.init(var1, var3, var4);
      this.mContainerFragment = var2;
   }

   public void onDataChanged() {
      if(!this.mHasLoadedAtLeastOnce) {
         if(this.mReviewList != null) {
            ListView var1 = this.mReviewList;
            View var2 = this.mContentLayout.findViewById(2131755255);
            var1.setEmptyView(var2);
            this.mHasLoadedAtLeastOnce = (boolean)1;
         }
      }
   }

   public void onDestroyView() {
      if(this.mAdapter != null) {
         this.mAdapter.onDestroyView();
         this.mAdapter = null;
      }

      this.mReviewList = null;
      if(this.mData != null) {
         ((DfeReviews)this.mData).removeDataChangedListener(this);
         ((DfeReviews)this.mData).removeErrorListener(this);
         this.mData = null;
      }
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      if(this.mReviewList != null) {
         this.mAdapter.triggerFooterErrorMode();
      }
   }

   public void onRateReview(Rev.Review var1) {
      FragmentManager var2 = this.mContainerFragment.getFragmentManager();
      if(var2.findFragmentByTag("rate_review_dialog") == null) {
         String var3 = this.mDocument.getDocId();
         String var4 = var1.getCommentId();
         RateReviewDialog var5 = RateReviewDialog.newInstance(var3, var4, (RateReviewDialog.CommentRating)null);
         Fragment var6 = this.mContainerFragment;
         var5.setTargetFragment(var6, 0);
         var5.show(var2, "rate_review_dialog");
      }
   }

   public void setData(DfeReviews var1) {
      this.mHasLoadedAtLeastOnce = (boolean)0;
      if(this.mData != null) {
         ((DfeReviews)this.mData).removeDataChangedListener(this);
         ((DfeReviews)this.mData).removeErrorListener(this);
      }

      super.setData(var1);
      ((DfeReviews)this.mData).clearTransientState();
      ((DfeReviews)this.mData).addDataChangedListener(this);
      ((DfeReviews)this.mData).addErrorListener(this);
   }
}

package com.google.android.finsky.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.RateReviewDialog;
import com.google.android.finsky.activities.ReviewListViewBinder;
import com.google.android.finsky.activities.ReviewsFragmentXLarge;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeRateReview;
import com.google.android.finsky.api.model.DfeReviews;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;

public class ReviewsFragment extends PageFragment implements RateReviewDialog.Listener {

   private static final String KEY_DOCUMENT = "finsky.ReviewsFragment.document";
   private static final int RATE_REVIEW_UPLOAD_TOAST_DURATION;
   protected DfeDetails mDfeDetails;
   protected Document mDocument;
   protected final ReviewListViewBinder mReviewsBinder;
   private DfeReviews mReviewsData;


   public ReviewsFragment() {
      ReviewListViewBinder var1 = new ReviewListViewBinder();
      this.mReviewsBinder = var1;
   }

   public static ReviewsFragment newInstance(Context var0, Document var1) {
      Configuration var2 = var0.getResources().getConfiguration();
      Object var3;
      if(var0.getResources().getBoolean(2131296256)) {
         var3 = new ReviewsFragmentXLarge();
      } else {
         var3 = new ReviewsFragment();
      }

      DfeToc var4 = FinskyApp.get().getToc();
      ((ReviewsFragment)var3).setArguments(var4);
      ((ReviewsFragment)var3).setArgument("finsky.ReviewsFragment.document", var1);
      return (ReviewsFragment)var3;
   }

   private void reloadReviews() {
      DfeApi var1 = FinskyApp.get().getDfeApi();
      String var2 = this.mDocument.getReviewsUrl();
      var1.invalidateReviewsCache(var2, (boolean)1);
      this.mReviewsData.removeDataChangedListener(this);
      this.mReviewsData.removeErrorListener(this);
      this.mReviewsData.resetItems();
      this.switchToLoading();
      this.mReviewsData.addDataChangedListener(this);
      this.mReviewsData.addErrorListener(this);
      this.requestData();
   }

   protected int getLayoutRes() {
      return 2130968701;
   }

   protected boolean isDataReady() {
      boolean var1;
      if(this.mReviewsData != null && this.mReviewsData.isReady()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      DfeApi var2 = this.mDfeApi;
      String var3 = this.mDocument.getDetailsUrl();
      DfeDetails var4 = new DfeDetails(var2, var3);
      this.mDfeDetails = var4;
      if(this.mReviewsData == null) {
         DfeApi var5 = this.mDfeApi;
         String var6 = this.mDocument.getReviewsUrl();
         DfeReviews var7 = new DfeReviews(var5, var6, (boolean)1);
         this.mReviewsData = var7;
         this.mReviewsData.addDataChangedListener(this);
         this.mReviewsData.addErrorListener(this);
      }

      if(!this.isDataReady()) {
         this.switchToLoading();
         this.requestData();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Document var2 = (Document)this.getArguments().getParcelable("finsky.ReviewsFragment.document");
      this.mDocument = var2;
      this.setRetainInstance((boolean)1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4 = super.onCreateView(var1, var2, var3);
      if(this.isDataReady()) {
         this.onDataChanged();
      }

      return var4;
   }

   public void onDataChanged() {
      this.mReviewsData.removeDataChangedListener(this);
      this.mReviewsData.removeErrorListener(this);
      ReviewListViewBinder var1 = this.mReviewsBinder;
      DfeReviews var2 = this.mReviewsData;
      var1.setData(var2);
      super.onDataChanged();
   }

   public void onDestroyView() {
      this.mReviewsBinder.onDestroyView();
      if(this.mReviewsData != null) {
         this.mReviewsData.removeDataChangedListener(this);
         this.mReviewsData.removeErrorListener(this);
      }

      super.onDestroyView();
   }

   protected void onInitViewBinders() {
      ReviewListViewBinder var1 = this.mReviewsBinder;
      Context var2 = this.mContext;
      NavigationManager var3 = this.mNavigationManager;
      BitmapLoader var4 = this.mBitmapLoader;
      var1.init(var2, this, var3, var4);
   }

   public void onPositiveClick(String var1, String var2, RateReviewDialog.CommentRating var3) {
      this.toast(2131231000, 0);
      DfeApi var4 = FinskyApp.get().getDfeApi();
      int var5 = var3.getRpcId();
      DfeRateReview var6 = new DfeRateReview(var4, var1, var2, var5);
      ReviewsFragment.1 var7 = new ReviewsFragment.1(var3);
      var6.addDataChangedListener(var7);
      ReviewsFragment.2 var8 = new ReviewsFragment.2();
      var6.addErrorListener(var8);
   }

   public void rebindActionBar() {
      PageFragmentHost var1 = this.mPageFragmentHost;
      int var2 = this.mDocument.getBackend();
      var1.updateCurrentBackendId(var2);
      PageFragmentHost var3 = this.mPageFragmentHost;
      String var4 = this.mDocument.getTitle();
      var3.updateBreadcrumb(var4);
   }

   protected void rebindViews() {
      this.rebindActionBar();
      ReviewListViewBinder var1 = this.mReviewsBinder;
      ViewGroup var2 = this.mDataView;
      Document var3 = this.mDocument;
      var1.bind(var2, var3);
   }

   protected void requestData() {
      this.mReviewsData.startLoadItems();
   }

   protected void toast(int var1, int var2) {
      Toast.makeText(this.mContext, var1, var2).show();
   }

   class 1 implements OnDataChangedListener {

      // $FF: synthetic field
      final RateReviewDialog.CommentRating val$newRating;


      1(RateReviewDialog.CommentRating var2) {
         this.val$newRating = var2;
      }

      public void onDataChanged() {
         ReviewsFragment.this.toast(2131231001, 0);
         RateReviewDialog.CommentRating var1 = this.val$newRating;
         RateReviewDialog.CommentRating var2 = RateReviewDialog.CommentRating.SPAM;
         if(var1 == var2) {
            ReviewsFragment.this.reloadReviews();
         }
      }
   }

   class 2 implements Response.ErrorListener {

      2() {}

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         ReviewsFragment.this.toast(2131231002, 0);
      }
   }
}

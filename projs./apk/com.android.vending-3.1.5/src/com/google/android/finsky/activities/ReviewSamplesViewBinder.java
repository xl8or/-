package com.google.android.finsky.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.android.finsky.activities.RateReviewDialog;
import com.google.android.finsky.adapters.ReviewsAdapter;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeReviews;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.Rev;

public class ReviewSamplesViewBinder extends DetailsViewBinder implements OnDataChangedListener, ReviewsAdapter.RateReviewHandler {

   private static final int SAMPLE_REVIEWS_COUNT = 3;
   private static final String TAG_RATE_REVIEW_DIALOG = "rate_review_dialog";
   private ReviewsAdapter mAdapter;
   private boolean mAlwaysShowMore;
   private Fragment mContainerFragment;
   private DfeReviews mData;
   private LinearLayout mReviewHolder;


   public ReviewSamplesViewBinder() {}

   public void bind(View var1, Document var2) {
      super.bind(var1, var2, 2131755140, 2131230980);
      LinearLayout var3 = (LinearLayout)var1.findViewById(2131755153);
      this.mReviewHolder = var3;
      this.refresh();
   }

   public void init(Context var1, Fragment var2, DfeApi var3, NavigationManager var4) {
      super.init(var1, var3, var4);
      this.mContainerFragment = var2;
      boolean var5 = this.mContext.getResources().getBoolean(2131296260);
      this.mAlwaysShowMore = var5;
   }

   public void onDataChanged() {
      int var1 = this.mData.getCount();
      if(var1 == 0) {
         this.mLayout.setVisibility(8);
      } else {
         this.mLayout.setVisibility(0);
         if(!this.mAlwaysShowMore && this.mData.getCount() <= 3) {
            this.setButtonVisibility(2131755138, 8, 2131230953);
         } else {
            this.setButtonVisibility(2131755138, 0, 2131230953);
            ReviewSamplesViewBinder.1 var2 = new ReviewSamplesViewBinder.1();
            this.setButtonClickListener(2131755138, var2);
         }

         this.mReviewHolder.removeAllViews();
         int var3 = 0;

         while(true) {
            int var4 = Math.min(3, var1);
            if(var3 >= var4) {
               return;
            }

            ReviewsAdapter var5 = this.mAdapter;
            int var6 = this.mAdapter.getFirstReviewViewIndex() + var3;
            LinearLayout var7 = this.mReviewHolder;
            View var8 = var5.getView(var6, (View)null, var7);
            this.mReviewHolder.addView(var8);
            ++var3;
         }
      }
   }

   public void onDestroyView() {
      if(this.mAdapter != null) {
         this.mAdapter.onDestroyView();
      }

      if(this.mData != null) {
         this.mData.removeDataChangedListener(this);
      }

      super.onDestroyView();
   }

   public void onRateReview(Rev.Review var1) {
      FragmentManager var2 = this.mContainerFragment.getFragmentManager();
      if(var2.findFragmentByTag("rate_review_dialog") == null) {
         String var3 = this.mDoc.getDocId();
         String var4 = var1.getCommentId();
         RateReviewDialog var5 = RateReviewDialog.newInstance(var3, var4, (RateReviewDialog.CommentRating)null);
         Fragment var6 = this.mContainerFragment;
         var5.setTargetFragment(var6, 0);
         var5.show(var2, "rate_review_dialog");
      }
   }

   public void refresh() {
      if(this.mDoc != null) {
         if(this.mData != null) {
            this.mData.removeDataChangedListener(this);
         }

         DfeApi var1 = this.mDfeApi;
         String var2 = this.mDoc.getReviewsUrl();
         DfeReviews var3 = new DfeReviews(var1, var2, (boolean)0);
         this.mData = var3;
         Context var4 = this.mContext;
         Document var5 = this.mDoc;
         DfeReviews var6 = this.mData;
         ReviewsAdapter var7 = new ReviewsAdapter(var4, var5, var6, this);
         this.mAdapter = var7;
         this.mData.addDataChangedListener(this);
         this.mData.startLoadItems();
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         NavigationManager var2 = ReviewSamplesViewBinder.this.mNavigationManager;
         Document var3 = ReviewSamplesViewBinder.this.mDoc;
         var2.goToAllReviews(var3);
      }
   }
}

package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.adapters.PaginatedListAdapter;
import com.google.android.finsky.api.model.DfeReviews;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.Rev;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.IntentUtils;
import java.util.Date;

public class ReviewsAdapter extends PaginatedListAdapter implements Response.ErrorListener {

   private static final int HEADER_POSITION = 0;
   private static final int VIEW_TYPE_COUNT = 4;
   private static final int VIEW_TYPE_ERROR_FOOTER = 3;
   private static final int VIEW_TYPE_HEADER = 0;
   private static final int VIEW_TYPE_LOADING_FOOTER = 2;
   private static final int VIEW_TYPE_ROW = 1;
   private final DfeReviews mData;
   private final Document mDoc;
   private boolean mHeaderVisible;
   private final LayoutInflater mLayoutInflater;
   private final ReviewsAdapter.RateReviewHandler mRatingHandler;


   public ReviewsAdapter(Context var1, Document var2, DfeReviews var3, ReviewsAdapter.RateReviewHandler var4) {
      boolean var5 = var3.inErrorState();
      boolean var6 = var3.isMoreAvailable();
      super(var1, (NavigationManager)null, var5, var6);
      this.mHeaderVisible = (boolean)1;
      LayoutInflater var7 = LayoutInflater.from(var1);
      this.mLayoutInflater = var7;
      this.mDoc = var2;
      this.mData = var3;
      this.mData.addDataChangedListener(this);
      this.mData.addErrorListener(this);
      this.mRatingHandler = var4;
   }

   private void bindItemView(View var1, ReviewsAdapter.ViewHolder var2, int var3) {
      byte var4 = 0;
      Rev.Review var5 = this.getItem(var3);
      String var6 = var5.getAuthorName();
      String var7 = var5.getSource();
      String var8 = var5.getUrl();
      if(!TextUtils.isEmpty(var6)) {
         var2.author.setText(var6);
         var2.author.setVisibility(0);
      } else {
         var2.author.setVisibility(8);
      }

      if(!TextUtils.isEmpty(var7)) {
         TextView var9 = var2.source;
         String var10 = var7.toUpperCase();
         var9.setText(var10);
         var2.source.setVisibility(0);
         TextView var11 = var2.source;
         ReviewsAdapter.1 var12 = new ReviewsAdapter.1(var8);
         var11.setOnClickListener(var12);
      } else {
         var2.source.setVisibility(8);
      }

      String var13 = var5.getComment();
      if(!TextUtils.isEmpty(var13)) {
         var2.reviewText.setText(var13);
      }

      if(var5.hasStarRating()) {
         var2.ratingBar.setVisibility(0);
         RatingBar var14 = var2.ratingBar;
         float var15 = (float)var5.getStarRating();
         var14.setRating(var15);
      } else {
         var2.ratingBar.setVisibility(8);
      }

      if(var5.hasTimestampMsec()) {
         TextView var16 = var2.reviewDate;
         long var17 = var5.getTimestampMsec();
         String var19 = DateUtils.formatDisplayDate(new Date(var17));
         var16.setText(var19);
         var2.reviewDate.setVisibility(0);
      } else {
         var2.reviewDate.setVisibility(8);
      }

      boolean var20 = CorpusMetadata.canRateReview(this.mDoc.getBackend());
      ImageView var21 = var2.ratingImage;
      if(!var20) {
         var4 = 8;
      }

      var21.setVisibility(var4);
      if(var20) {
         ReviewsAdapter.2 var22 = new ReviewsAdapter.2(var5);
         var1.setOnClickListener(var22);
      } else {
         var1.setOnClickListener((OnClickListener)null);
      }
   }

   private View getHeaderView(View var1, ViewGroup var2) {
      TextView var3;
      if(var1 == null) {
         var3 = (TextView)this.inflate(2130968702, var2, (boolean)0);
      } else {
         var3 = (TextView)var1;
      }

      Context var4 = this.mContext;
      int var5 = this.mDoc.getBackend();
      int var6 = CorpusMetadata.getBackendForegroundColor(var4, var5);
      var3.setTextColor(var6);
      return var3;
   }

   private View getItemView(int var1, View var2, ViewGroup var3) {
      if(var2 == null) {
         var2 = this.mLayoutInflater.inflate(2130968703, var3, (boolean)0);
      }

      ReviewsAdapter.ViewHolder var4 = (ReviewsAdapter.ViewHolder)var2.getTag();
      if(var4 == null) {
         var4 = new ReviewsAdapter.ViewHolder(var2);
      }

      this.bindItemView(var2, var4, var1);
      return var2;
   }

   public int getCount() {
      int var1 = this.mData.getCount();
      if(this.mHeaderVisible) {
         ++var1;
      }

      PaginatedListAdapter.FooterMode var2 = this.getFooterMode();
      PaginatedListAdapter.FooterMode var3 = PaginatedListAdapter.FooterMode.NONE;
      if(var2 != var3) {
         ++var1;
      }

      return var1;
   }

   public int getFirstReviewViewIndex() {
      byte var1;
      if(this.mData.getCount() == 0) {
         var1 = -1;
      } else {
         var1 = 1;
      }

      return var1;
   }

   public Rev.Review getItem(int var1) {
      int var2 = this.mData.getCount();
      Rev.Review var3;
      if(var1 < var2) {
         var3 = (Rev.Review)this.mData.getItem(var1);
      } else {
         var3 = null;
      }

      return var3;
   }

   public int getItemViewType(int var1) {
      byte var2 = 1;
      int var3 = this.getCount() + -1;
      if(this.mHeaderVisible && var1 == 0) {
         var2 = 0;
      } else if(var1 == var3) {
         int[] var4 = ReviewsAdapter.3.$SwitchMap$com$google$android$finsky$adapters$PaginatedListAdapter$FooterMode;
         int var5 = this.getFooterMode().ordinal();
         switch(var4[var5]) {
         case 1:
            var2 = 2;
            break;
         case 2:
            var2 = 3;
         case 3:
            break;
         default:
            String var6 = "No footer or item at row " + var1;
            throw new IllegalStateException(var6);
         }
      }

      return var2;
   }

   protected String getLastRequestError() {
      return this.mData.getErrorMessage();
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var7;
      switch(this.getItemViewType(var1)) {
      case 0:
         var7 = this.getHeaderView(var2, var3);
         break;
      case 1:
         byte var8;
         if(this.mHeaderVisible) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         int var9 = var1 - var8;
         var7 = this.getItemView(var9, var2, var3);
         break;
      case 2:
         var7 = this.getLoadingFooterView(var2, var3);
         break;
      case 3:
         var7 = this.getErrorFooterView(var2, var3);
         break;
      default:
         StringBuilder var4 = (new StringBuilder()).append("Unknown type for getView ");
         int var5 = this.getItemViewType(var1);
         String var6 = var4.append(var5).toString();
         throw new IllegalStateException(var6);
      }

      return var7;
   }

   public int getViewTypeCount() {
      return 4;
   }

   protected boolean isMoreDataAvailable() {
      return this.mData.isMoreAvailable();
   }

   public void onDestroyView() {
      this.mData.removeDataChangedListener(this);
      this.mData.removeErrorListener(this);
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      this.triggerFooterErrorMode();
   }

   protected void retryLoadingItems() {
      this.mData.retryLoadItems();
   }

   public void setHeaderVisible(boolean var1) {
      this.mHeaderVisible = var1;
   }

   public interface RateReviewHandler {

      void onRateReview(Rev.Review var1);
   }

   public static class ViewHolder {

      TextView author;
      RatingBar ratingBar;
      ImageView ratingImage;
      TextView reviewDate;
      TextView reviewText;
      TextView source;


      public ViewHolder(View var1) {
         TextView var2 = (TextView)var1.findViewById(2131755302);
         this.author = var2;
         TextView var3 = (TextView)var1.findViewById(2131755303);
         this.source = var3;
         TextView var4 = (TextView)var1.findViewById(2131755305);
         this.reviewText = var4;
         TextView var5 = (TextView)var1.findViewById(2131755304);
         this.reviewDate = var5;
         RatingBar var6 = (RatingBar)var1.findViewById(2131755301);
         this.ratingBar = var6;
         ImageView var7 = (ImageView)var1.findViewById(2131755306);
         this.ratingImage = var7;
         var1.setTag(this);
      }
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final String val$sourceUrl;


      1(String var2) {
         this.val$sourceUrl = var2;
      }

      public void onClick(View var1) {
         Context var2 = var1.getContext();
         Intent var3 = IntentUtils.createViewIntentForUrl(Uri.parse(this.val$sourceUrl));
         var2.startActivity(var3);
      }
   }

   // $FF: synthetic class
   static class 3 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$adapters$PaginatedListAdapter$FooterMode = new int[PaginatedListAdapter.FooterMode.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$adapters$PaginatedListAdapter$FooterMode;
            int var1 = PaginatedListAdapter.FooterMode.LOADING.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$adapters$PaginatedListAdapter$FooterMode;
            int var3 = PaginatedListAdapter.FooterMode.ERROR.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$adapters$PaginatedListAdapter$FooterMode;
            int var5 = PaginatedListAdapter.FooterMode.NONE.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final Rev.Review val$review;


      2(Rev.Review var2) {
         this.val$review = var2;
      }

      public void onClick(View var1) {
         ReviewsAdapter.RateReviewHandler var2 = ReviewsAdapter.this.mRatingHandler;
         Rev.Review var3 = this.val$review;
         var2.onRateReview(var3);
      }
   }
}

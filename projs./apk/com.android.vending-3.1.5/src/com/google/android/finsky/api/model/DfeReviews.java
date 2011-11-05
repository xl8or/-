package com.google.android.finsky.api.model;

import com.android.volley.Request;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.PaginatedDfeRequest;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.remoting.protos.Rev;
import com.google.android.finsky.remoting.protos.ReviewResponse;
import java.util.List;

public class DfeReviews extends PaginatedList<ReviewResponse, Rev.Review> implements PaginatedDfeRequest.PaginatedListener<ReviewResponse> {

   private DfeApi mDfeApi;


   public DfeReviews(DfeApi var1, String var2, boolean var3) {
      super(var2, var3);
      this.mDfeApi = var1;
   }

   protected List<Rev.Review> getItemsFromResponse(ReviewResponse var1) {
      return var1.getGetResponse().getReviewList();
   }

   protected String getNextPageUrl(ReviewResponse var1) {
      return var1.getNextPageUrl();
   }

   protected Request<?> makeRequest(String var1) {
      return this.mDfeApi.getReviews(var1, this, this);
   }

   public void resetItems() {
      super.resetItems();
   }
}

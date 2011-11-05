package com.google.android.finsky.api.model;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeModel;
import com.google.android.finsky.remoting.protos.ReviewResponse;

public class DfeRateReview extends DfeModel implements Response.Listener<ReviewResponse> {

   private boolean mResponseRecieved;


   public DfeRateReview(DfeApi var1, String var2, String var3, int var4) {
      Request var11 = var1.rateReview(var2, var3, var4, this, this);
   }

   public boolean isReady() {
      return this.mResponseRecieved;
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      this.mResponseRecieved = (boolean)1;
      super.onErrorResponse(var1, var2, var3);
      this.unregisterAll();
   }

   public void onResponse(ReviewResponse var1) {
      this.mResponseRecieved = (boolean)1;
      this.notifyDataSetChanged();
      this.unregisterAll();
   }
}

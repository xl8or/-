package com.google.android.finsky.api.model;

import com.android.volley.Response;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeModel;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.DetailsResponse;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.Rev;

public class DfeDetails extends DfeModel implements Response.Listener<DetailsResponse> {

   private final String mAnalyticsCookie;
   private DetailsResponse mDetailsResponse;
   private final String mDetailsUrl;


   public DfeDetails(DfeApi var1, String var2) {
      this(var1, var2, (String)null);
   }

   public DfeDetails(DfeApi var1, String var2, String var3) {
      this.mAnalyticsCookie = var3;
      this.mDetailsUrl = var2;
      String var4 = this.mDetailsUrl;
      var1.getDetails(var4, this, this);
   }

   public int getBackendId() {
      int var1;
      if(this.getDocument() != null) {
         var1 = this.getDocument().getBackend();
      } else {
         var1 = -1;
      }

      return var1;
   }

   public String getDetailsUrl() {
      return this.mDetailsUrl;
   }

   public Document getDocument() {
      Document var1;
      if(this.mDetailsResponse != null && this.mDetailsResponse.hasDoc()) {
         Document var2 = new Document;
         DeviceDoc.DeviceDocument var3 = this.mDetailsResponse.getDoc();
         String var4;
         if(this.mAnalyticsCookie != null) {
            var4 = this.mAnalyticsCookie;
         } else {
            var4 = this.mDetailsResponse.getAnalyticsCookie();
         }

         var2.<init>(var3, var4);
         var1 = var2;
      } else {
         var1 = null;
      }

      return var1;
   }

   public Rev.Review getUserReview() {
      Rev.Review var1;
      if(this.mDetailsResponse != null && this.mDetailsResponse.hasUserReview()) {
         var1 = this.mDetailsResponse.getUserReview();
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean isReady() {
      boolean var1;
      if(this.mDetailsResponse != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onResponse(DetailsResponse var1) {
      this.mDetailsResponse = var1;
      this.notifyDataSetChanged();
   }
}

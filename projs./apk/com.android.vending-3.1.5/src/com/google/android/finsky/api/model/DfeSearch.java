package com.google.android.finsky.api.model;

import com.android.volley.Request;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.BucketedList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.remoting.protos.SearchResponse;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DfeSearch extends BucketedList<SearchResponse> {

   private final DfeApi mDfeApi;
   private final String mInitialUrl;
   private String mQuery;
   private String mSuggestedQuery;


   public DfeSearch(DfeApi var1, String var2, int var3) {
      String var4 = DfeApi.formSearchUrl(var2, var3);
      super(var4);
      String var5 = DfeApi.formSearchUrl(var2, var3);
      this.mInitialUrl = var5;
      this.mDfeApi = var1;
      this.mQuery = var2;
   }

   public int getBackendId() {
      int var1 = 0;
      if(this.getBucketCount() == 0) {
         var1 = -1;
      } else if(!this.isAggregateResult() && this.getBucketCount() <= 1 && this.getBucket(0).getDocumentCount() != 0) {
         var1 = this.getBucket(0).getDocument(0).getFinskyDoc().getDocid().getBackend();
      }

      return var1;
   }

   public DocList.Bucket getBucket(int var1) {
      return ((SearchResponse)this.mLastResponse).getBucket(0);
   }

   public int getBucketCount() {
      return ((SearchResponse)this.mLastResponse).getBucketCount();
   }

   public List<DocList.Bucket> getBucketList() {
      return ((SearchResponse)this.mLastResponse).getBucketList();
   }

   protected List<Document> getItemsFromResponse(SearchResponse var1) {
      ArrayList var2 = Lists.newArrayList();
      if(var1.getBucketCount() == 1) {
         String var3 = var1.getBucket(0).getAnalyticsCookie();
         Iterator var4 = var1.getBucket(0).getDocumentList().iterator();

         while(var4.hasNext()) {
            DeviceDoc.DeviceDocument var5 = (DeviceDoc.DeviceDocument)var4.next();
            Document var6 = new Document(var5, var3);
            var2.add(var6);
         }
      }

      if(var1.hasSuggestedQuery()) {
         String var8 = var1.getSuggestedQuery();
         this.mSuggestedQuery = var8;
      }

      return var2;
   }

   protected String getNextPageUrl(SearchResponse var1) {
      String var2 = null;
      if(var1.getBucketCount() == 1) {
         var2 = var1.getBucket(0).getNextPageUrl();
      }

      return var2;
   }

   public String getQuery() {
      return this.mQuery;
   }

   public String getSuggestedQuery() {
      return this.mSuggestedQuery;
   }

   public String getUrl() {
      return this.mInitialUrl;
   }

   public boolean isAggregateResult() {
      return ((SearchResponse)this.mLastResponse).getAggregateQuery();
   }

   protected Request<?> makeRequest(String var1) {
      return this.mDfeApi.search(var1, this, this);
   }
}

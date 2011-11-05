package com.google.android.finsky.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.android.volley.Request;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.BucketedList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DfeList extends BucketedList<DocList.ListResponse> implements Parcelable {

   public static Creator<DfeList> CREATOR = new DfeList.1();
   private DfeApi mDfeApi;


   public DfeList(DfeApi var1, String var2, boolean var3) {
      super(var2, var3);
      this.mDfeApi = var1;
   }

   private DfeList(List<PaginatedList.UrlOffsetPair> var1, int var2, boolean var3) {
      super(var1, var2, var3);
   }

   // $FF: synthetic method
   DfeList(List var1, int var2, boolean var3, DfeList.1 var4) {
      this(var1, var2, var3);
   }

   public int describeContents() {
      return 0;
   }

   public int getBackendId() {
      int var1 = 0;
      Iterator var2 = this.getBucketList().iterator();

      while(var2.hasNext()) {
         DocList.Bucket var3 = (DocList.Bucket)var2.next();
         if(var3.getMultiCorpus()) {
            var1 = 0;
            break;
         }

         if(var3.getDocumentCount() != 0) {
            int var4 = var3.getDocument(0).getFinskyDoc().getDocid().getBackend();
            if(var1 != 0 && var1 != var4) {
               var1 = 0;
               break;
            }

            var1 = var4;
         }
      }

      return var1;
   }

   public DocList.Bucket getBucket(int var1) {
      return ((DocList.ListResponse)this.mLastResponse).getBucket(var1);
   }

   public int getBucketCount() {
      return ((DocList.ListResponse)this.mLastResponse).getBucketCount();
   }

   public List<DocList.Bucket> getBucketList() {
      return ((DocList.ListResponse)this.mLastResponse).getBucketList();
   }

   protected List<Document> getItemsFromResponse(DocList.ListResponse var1) {
      ArrayList var2 = Lists.newArrayList();
      if(var1.getBucketCount() > 0) {
         String var3 = var1.getBucket(0).getAnalyticsCookie();
         Iterator var4 = var1.getBucket(0).getDocumentList().iterator();

         while(var4.hasNext()) {
            DeviceDoc.DeviceDocument var5 = (DeviceDoc.DeviceDocument)var4.next();
            Document var6 = new Document(var5, var3);
            var2.add(var6);
         }
      }

      return var2;
   }

   protected String getNextPageUrl(DocList.ListResponse var1) {
      String var2 = null;
      if(var1.getBucketCount() == 1) {
         var2 = var1.getBucket(0).getNextPageUrl();
      }

      return var2;
   }

   protected Request<?> makeRequest(String var1) {
      return this.mDfeApi.getList(var1, this, this);
   }

   public void setDfeApi(DfeApi var1) {
      this.mDfeApi = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.mUrlOffsetList.size();
      var1.writeInt(var3);
      Iterator var4 = this.mUrlOffsetList.iterator();

      while(var4.hasNext()) {
         PaginatedList.UrlOffsetPair var5 = (PaginatedList.UrlOffsetPair)var4.next();
         int var6 = var5.offset;
         var1.writeInt(var6);
         String var7 = var5.url;
         var1.writeString(var7);
      }

      int var8 = this.getCount();
      var1.writeInt(var8);
      byte var9;
      if(this.mAutoLoadNextPage) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      var1.writeInt(var9);
   }

   static class 1 implements Creator<DfeList> {

      1() {}

      public DfeList createFromParcel(Parcel var1) {
         byte var2 = 1;
         int var3 = var1.readInt();
         ArrayList var4 = Lists.newArrayList();

         for(int var5 = 0; var5 < var3; ++var5) {
            int var6 = var1.readInt();
            String var7 = var1.readString();
            PaginatedList.UrlOffsetPair var8 = new PaginatedList.UrlOffsetPair(var6, var7);
            var4.add(var8);
         }

         int var10 = var1.readInt();
         if(var1.readInt() != 1) {
            var2 = 0;
         }

         return new DfeList(var4, var10, (boolean)var2, (DfeList.1)null);
      }

      public DfeList[] newArray(int var1) {
         return new DfeList[var1];
      }
   }
}

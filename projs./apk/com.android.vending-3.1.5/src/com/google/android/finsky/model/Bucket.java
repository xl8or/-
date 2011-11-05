package com.google.android.finsky.model;

import android.text.TextUtils;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bucket {

   private final DocList.Bucket mBucket;


   public Bucket(DocList.Bucket var1) {
      this.mBucket = var1;
   }

   public static List<Bucket> fromProtos(List<DocList.Bucket> var0) {
      ArrayList var1 = Lists.newArrayList();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         DocList.Bucket var3 = (DocList.Bucket)var2.next();
         Bucket var4 = new Bucket(var3);
         var1.add(var4);
      }

      return var1;
   }

   public int getBackend() {
      int var1 = 0;
      if(!this.mBucket.getMultiCorpus()) {
         if(this.mBucket.getDocumentCount() == 0) {
            var1 = -1;
         } else {
            var1 = this.mBucket.getDocument(0).getFinskyDoc().getDocid().getBackend();
         }
      }

      return var1;
   }

   public String getCookie() {
      return this.mBucket.getAnalyticsCookie();
   }

   public int getEstimatedResults() {
      return (int)this.mBucket.getEstimatedResults();
   }

   public String getHeaderText() {
      return this.mBucket.getTitle();
   }

   public String getHeaderUrl() {
      return this.mBucket.getFullContentsUrl();
   }

   public String getIconUrl() {
      return this.mBucket.getIconUrl();
   }

   public DeviceDoc.DeviceDocument getItem(int var1) {
      return this.mBucket.getDocument(var1);
   }

   public int getItemCount() {
      return this.mBucket.getDocumentCount();
   }

   public boolean hasMoreItems() {
      boolean var1;
      if(!TextUtils.isEmpty(this.mBucket.getFullContentsUrl())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isOrdered() {
      boolean var1;
      if(this.mBucket.hasOrdered() && this.mBucket.getOrdered()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isSongsList() {
      boolean var1 = false;
      if(this.mBucket.getDocumentCount() != 0 && this.mBucket.getDocument(0).getFinskyDoc().getDocid().getType() == 4) {
         var1 = true;
      }

      return var1;
   }
}

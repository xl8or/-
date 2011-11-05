package com.google.android.finsky.utils;

import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.List;

public class GridAdapterUtils {

   public GridAdapterUtils() {}

   public static List<Document> getDocumentListFromDfeList(DfeList var0, int var1) {
      ArrayList var2 = Lists.newArrayList();
      if(var0.getBucketCount() == 1) {
         DocList.Bucket var3 = var0.getBucket(0);
         int var4 = Math.min(var3.getDocumentCount(), var1);

         for(int var5 = 0; var5 < var4; ++var5) {
            if(var3.getDocument(var5) != null) {
               DeviceDoc.DeviceDocument var6 = var3.getDocument(var5);
               String var7 = var3.getAnalyticsCookie();
               Document var8 = new Document(var6, var7);
               var2.add(var8);
            }
         }
      }

      return var2;
   }
}

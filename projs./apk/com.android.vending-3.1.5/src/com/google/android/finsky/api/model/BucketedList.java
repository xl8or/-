package com.google.android.finsky.api.model;

import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.remoting.protos.DocList;
import java.util.List;

public abstract class BucketedList<T extends Object> extends PaginatedList<T, Document> {

   protected BucketedList(String var1) {
      super(var1);
   }

   protected BucketedList(String var1, boolean var2) {
      super(var1, var2);
   }

   protected BucketedList(List<PaginatedList.UrlOffsetPair> var1, int var2, boolean var3) {
      super(var1, var2, var3);
   }

   public abstract DocList.Bucket getBucket(int var1);

   public abstract int getBucketCount();

   public abstract List<DocList.Bucket> getBucketList();
}

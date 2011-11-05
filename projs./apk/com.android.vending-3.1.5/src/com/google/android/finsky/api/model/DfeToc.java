package com.google.android.finsky.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.api.model.DfeModel;
import com.google.android.finsky.remoting.protos.Toc;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DfeToc extends DfeModel implements Parcelable {

   public static Creator<DfeToc> CREATOR = new DfeToc.1();
   private static final int DEFAULT_CHANNEL = 3;
   private final Map<Integer, Toc.CorpusMetadata> mCorpusMap;
   private int mSelectedBackendId = 3;
   private final Toc.TocResponse mToc;


   public DfeToc(Toc.TocResponse var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap();
      this.mCorpusMap = var2;
      this.mToc = var1;
      Iterator var3 = this.mToc.getCorpusList().iterator();

      while(var3.hasNext()) {
         Toc.CorpusMetadata var4 = (Toc.CorpusMetadata)var3.next();
         Map var5 = this.mCorpusMap;
         Integer var6 = Integer.valueOf(var4.getBackend());
         var5.put(var6, var4);
      }

   }

   public int describeContents() {
      return 0;
   }

   public Toc.CorpusMetadata getCorpus(int var1) {
      Map var2 = this.mCorpusMap;
      Integer var3 = Integer.valueOf(var1);
      return (Toc.CorpusMetadata)var2.get(var3);
   }

   public List<Toc.CorpusMetadata> getCorpusList() {
      ArrayList var1 = Lists.newArrayList();
      Collection var2 = this.mCorpusMap.values();
      var1.addAll(var2);
      return var1;
   }

   public String getHomeUrl() {
      return this.mToc.getHomeUrl();
   }

   public int getIndexForBackendId(int var1) {
      int var2 = 0;
      Iterator var3 = this.mToc.getCorpusList().iterator();

      while(true) {
         if(!var3.hasNext()) {
            var2 = -1;
            break;
         }

         if(((Toc.CorpusMetadata)var3.next()).getBackend() == var1) {
            break;
         }

         ++var2;
      }

      return var2;
   }

   public int getSelectedBackendId() {
      return this.mSelectedBackendId;
   }

   public String getTosContent() {
      return this.mToc.getTosContent();
   }

   public int getTosVersion() {
      return this.mToc.getTosVersion();
   }

   public boolean hasTosVersion() {
      return this.mToc.hasTosVersion();
   }

   public boolean isReady() {
      return true;
   }

   public void selectCorpusWithId(int var1) {
      this.mSelectedBackendId = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      ParcelableProto var3 = ParcelableProto.forProto(this.mToc);
      var1.writeParcelable(var3, 0);
   }

   static class 1 implements Creator<DfeToc> {

      1() {}

      public DfeToc createFromParcel(Parcel var1) {
         ClassLoader var2 = ParcelableProto.class.getClassLoader();
         Toc.TocResponse var3 = (Toc.TocResponse)ParcelableProto.getProtoFromParcel(var1, var2);
         return new DfeToc(var3);
      }

      public DfeToc[] newArray(int var1) {
         return new DfeToc[var1];
      }
   }
}

package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASMailSearchResult implements Parcelable {

   public static final Creator<EASMailSearchResult> CREATOR = new EASMailSearchResult.1();
   public int httpStatus;
   public String range;
   public int searchStatus;
   public int storeStatus;
   public int total;


   public EASMailSearchResult() {
      this.total = 0;
      this.httpStatus = 0;
      this.searchStatus = 0;
      this.storeStatus = 0;
      this.range = null;
      this.total = 0;
      this.httpStatus = 0;
      this.searchStatus = 0;
      this.storeStatus = 0;
      this.range = null;
   }

   private EASMailSearchResult(Parcel var1) {
      this.total = 0;
      this.httpStatus = 0;
      this.searchStatus = 0;
      this.storeStatus = 0;
      this.range = null;
      int var2 = var1.readInt();
      this.total = var2;
      int var3 = var1.readInt();
      this.httpStatus = var3;
      int var4 = var1.readInt();
      this.searchStatus = var4;
      int var5 = var1.readInt();
      this.storeStatus = var5;
      String var6 = var1.readString();
      this.range = var6;
   }

   // $FF: synthetic method
   EASMailSearchResult(Parcel var1, EASMailSearchResult.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.total;
      var1.writeInt(var3);
      int var4 = this.httpStatus;
      var1.writeInt(var4);
      int var5 = this.searchStatus;
      var1.writeInt(var5);
      int var6 = this.storeStatus;
      var1.writeInt(var6);
      String var7 = this.range;
      var1.writeString(var7);
   }

   static class 1 implements Creator<EASMailSearchResult> {

      1() {}

      public EASMailSearchResult createFromParcel(Parcel var1) {
         return new EASMailSearchResult(var1, (EASMailSearchResult.1)null);
      }

      public EASMailSearchResult[] newArray(int var1) {
         return new EASMailSearchResult[var1];
      }
   }
}

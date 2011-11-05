package com.android.exchange;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.android.exchange.OoOData;
import java.util.ArrayList;
import java.util.Date;

public class OoODataList implements Parcelable {

   public static final Creator<OoODataList> CREATOR = new OoODataList.1();
   private static final String TAG = "OoODataList";
   ArrayList<OoOData> dataList;


   public OoODataList() {
      ArrayList var1 = new ArrayList();
      this.dataList = var1;
   }

   private OoODataList(Parcel var1) {
      ArrayList var2 = new ArrayList();
      this.dataList = var2;
      this.readFromParcel(var1);
   }

   // $FF: synthetic method
   OoODataList(Parcel var1, OoODataList.1 var2) {
      this(var1);
   }

   public int AddOoOData(int var1, int var2, int var3, String var4) {
      ArrayList var5 = this.dataList;
      OoOData var6 = new OoOData(var1, var2, var3, var4);
      var5.add(var6);
      return 0;
   }

   public int AddOoOData(int var1, int var2, int var3, String var4, long var5, long var7) {
      ArrayList var9 = this.dataList;
      OoOData var18 = new OoOData(var1, var2, var3, var4, var5, var7);
      var9.add(var18);
      return 0;
   }

   public int AddOoOData(int var1, int var2, int var3, String var4, Date var5, Date var6) {
      ArrayList var7 = this.dataList;
      OoOData var14 = new OoOData(var1, var2, var3, var4, var5, var6);
      var7.add(var14);
      return 0;
   }

   public int describeContents() {
      return 0;
   }

   public void displayOoOData() {
      StringBuilder var1 = (new StringBuilder()).append("OOF ResultList: Number of results = ");
      int var2 = this.dataList.size();
      String var3 = var1.append(var2).toString();
      int var4 = Log.d("OoODataList", var3);
   }

   public int getCount() {
      return this.dataList.size();
   }

   public OoOData getItem(int var1) {
      OoOData var3;
      if(var1 >= 0) {
         int var2 = this.getCount();
         if(var1 < var2) {
            var3 = (OoOData)this.dataList.get(var1);
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   public void readFromParcel(Parcel var1) {
      ClassLoader var2 = this.getClass().getClassLoader();
      Parcelable[] var3 = var1.readParcelableArray(var2);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Parcelable var6 = var3[var5];
         ArrayList var7 = this.dataList;
         OoOData var8 = (OoOData)var6;
         var7.add(var8);
      }

   }

   public void writeToParcel(Parcel var1, int var2) {
      ArrayList var3 = this.dataList;
      OoOData[] var4 = new OoOData[0];
      Parcelable[] var5 = (Parcelable[])var3.toArray(var4);
      var1.writeParcelableArray(var5, var2);
   }

   static class 1 implements Creator<OoODataList> {

      1() {}

      public OoODataList createFromParcel(Parcel var1) {
         return new OoODataList(var1, (OoODataList.1)null);
      }

      public OoODataList[] newArray(int var1) {
         return new OoODataList[var1];
      }
   }
}

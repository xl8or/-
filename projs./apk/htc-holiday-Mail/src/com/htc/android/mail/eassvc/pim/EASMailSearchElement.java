package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class EASMailSearchElement implements Parcelable {

   public static final Creator<EASMailSearchElement> CREATOR = new EASMailSearchElement.1();
   public boolean includeSubFolder;
   public ArrayList<EASMailSearchElement.QueryElement> queryList;
   public int rangeFrom;
   public int rangeTo;
   public boolean rebuildResults;


   public EASMailSearchElement() {
      this.includeSubFolder = (boolean)1;
      this.rebuildResults = (boolean)1;
      ArrayList var1 = new ArrayList();
      this.queryList = var1;
      this.includeSubFolder = (boolean)1;
      this.rebuildResults = (boolean)1;
      this.rangeFrom = 0;
      this.rangeTo = 999;
      this.queryList.clear();
   }

   private EASMailSearchElement(Parcel var1) {
      this.includeSubFolder = (boolean)1;
      this.rebuildResults = (boolean)1;
      ArrayList var2 = new ArrayList();
      this.queryList = var2;
      byte var3;
      if(var1.readInt() == 1) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      this.includeSubFolder = (boolean)var3;
      byte var4;
      if(var1.readInt() == 1) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      this.rebuildResults = (boolean)var4;
      int var5 = var1.readInt();
      this.rangeFrom = var5;
      int var6 = var1.readInt();
      this.rangeTo = var6;
      ArrayList var7 = this.queryList;
      Creator var8 = EASMailSearchElement.QueryElement.CREATOR;
      var1.readTypedList(var7, var8);
   }

   // $FF: synthetic method
   EASMailSearchElement(Parcel var1, EASMailSearchElement.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      byte var3;
      if(this.includeSubFolder == 1) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      var1.writeInt(var3);
      byte var4;
      if(this.rebuildResults == 1) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      var1.writeInt(var4);
      int var5 = this.rangeFrom;
      var1.writeInt(var5);
      int var6 = this.rangeTo;
      var1.writeInt(var6);
      ArrayList var7 = this.queryList;
      var1.writeTypedList(var7);
   }

   static class 1 implements Creator<EASMailSearchElement> {

      1() {}

      public EASMailSearchElement createFromParcel(Parcel var1) {
         return new EASMailSearchElement(var1, (EASMailSearchElement.1)null);
      }

      public EASMailSearchElement[] newArray(int var1) {
         return new EASMailSearchElement[var1];
      }
   }

   public static class QueryElement implements Parcelable {

      public static final int AND = 1;
      public static final Creator<EASMailSearchElement.QueryElement> CREATOR = new EASMailSearchElement.QueryElement.1();
      public static final int GreaterThan = 1;
      public static final int LessThan = 2;
      public static final int OR = 2;
      public String Class;
      public String FreeText;
      public String collectionId;
      public String dateGreaterThan;
      public String dateLessThan;
      public int queryCondition;
      public int timeFilter;


      public QueryElement() {
         this.Class = "Email";
         this.timeFilter = 0;
         this.queryCondition = 1;
         this.Class = "Email";
         this.FreeText = null;
         this.collectionId = null;
         this.dateGreaterThan = null;
         this.dateLessThan = null;
      }

      private QueryElement(Parcel var1) {
         this.Class = "Email";
         int var2 = var1.readInt();
         this.timeFilter = var2;
         int var3 = var1.readInt();
         this.queryCondition = var3;
         String var4 = var1.readString();
         this.Class = var4;
         String var5 = var1.readString();
         this.FreeText = var5;
         String var6 = var1.readString();
         this.collectionId = var6;
         String var7 = var1.readString();
         this.dateGreaterThan = var7;
         String var8 = var1.readString();
         this.dateLessThan = var8;
      }

      // $FF: synthetic method
      QueryElement(Parcel var1, EASMailSearchElement.1 var2) {
         this(var1);
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel var1, int var2) {
         int var3 = this.timeFilter;
         var1.writeInt(var3);
         int var4 = this.queryCondition;
         var1.writeInt(var4);
         String var5 = this.Class;
         var1.writeString(var5);
         String var6 = this.FreeText;
         var1.writeString(var6);
         String var7 = this.collectionId;
         var1.writeString(var7);
         String var8 = this.dateGreaterThan;
         var1.writeString(var8);
         String var9 = this.dateLessThan;
         var1.writeString(var9);
      }

      static class 1 implements Creator<EASMailSearchElement.QueryElement> {

         1() {}

         public EASMailSearchElement.QueryElement createFromParcel(Parcel var1) {
            return new EASMailSearchElement.QueryElement(var1, (EASMailSearchElement.1)null);
         }

         public EASMailSearchElement.QueryElement[] newArray(int var1) {
            return new EASMailSearchElement.QueryElement[var1];
         }
      }
   }
}

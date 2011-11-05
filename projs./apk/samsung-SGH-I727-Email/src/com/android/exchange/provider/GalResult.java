package com.android.exchange.provider;

import java.util.ArrayList;

public class GalResult {

   public int endRange = 0;
   public ArrayList<GalResult.GalData> galData;
   public double protocolVerison;
   public int startRange = 0;
   public int total;


   public GalResult() {
      ArrayList var1 = new ArrayList();
      this.galData = var1;
   }

   public void addGalData(long var1, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13) {
      ArrayList var14 = this.galData;
      GalResult.GalData var28 = new GalResult.GalData(var1, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, (GalResult.1)null);
      boolean var31 = var14.add(var28);
   }

   public static class GalData {

      long _id;
      String displayName;
      String emailAddress;
      String mAlias;
      String mCompany;
      String mFirstName;
      String mHomePhone;
      String mLastName;
      String mMobilePhone;
      String mOffice;
      String mPhone;
      String mTitle;


      private GalData(long var1, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13) {
         this._id = 0L;
         this.displayName = "";
         this.mPhone = "";
         this.mOffice = "";
         this.mTitle = "";
         this.mCompany = "";
         this.mAlias = "";
         this.emailAddress = "";
         this.mFirstName = "";
         this.mLastName = "";
         this.mHomePhone = "";
         this.mMobilePhone = "";
         this._id = var1;
         if(var3 != null) {
            this.displayName = var3;
         }

         if(var4 != null) {
            this.mPhone = var4;
         }

         if(var5 != null) {
            this.mOffice = var5;
         }

         if(var6 != null) {
            this.mTitle = var6;
         }

         if(var7 != null) {
            this.mCompany = var7;
         }

         if(var8 != null) {
            this.mAlias = var8;
         }

         if(var9 != null) {
            this.emailAddress = var9;
         }

         if(var10 != null) {
            this.mFirstName = var10;
         }

         if(var11 != null) {
            this.mLastName = var11;
         }

         if(var12 != null) {
            this.mHomePhone = var12;
         }

         if(var13 != null) {
            this.mMobilePhone = var13;
         }
      }

      // $FF: synthetic method
      GalData(long var1, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, GalResult.1 var14) {
         this(var1, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

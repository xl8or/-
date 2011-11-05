package com.android.exchange.provider;

import java.util.ArrayList;

public class GalResult {

   public ArrayList<GalResult.GalData> galData;
   public int total;


   public GalResult() {
      ArrayList var1 = new ArrayList();
      this.galData = var1;
   }

   public void addGalData(long var1, String var3, String var4) {
      ArrayList var5 = this.galData;
      GalResult.GalData var10 = new GalResult.GalData(var1, var3, var4, (GalResult.1)null);
      var5.add(var10);
   }

   public static class GalData {

      final long _id;
      final String displayName;
      final String emailAddress;


      private GalData(long var1, String var3, String var4) {
         this._id = var1;
         this.displayName = var3;
         this.emailAddress = var4;
      }

      // $FF: synthetic method
      GalData(long var1, String var3, String var4, GalResult.1 var5) {
         this(var1, var3, var4);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

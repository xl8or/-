package com.android.i18n.addressinput;

import com.android.i18n.addressinput.Util;

class RegionData {

   private String mKey;
   private String mName;


   private RegionData() {}

   // $FF: synthetic method
   RegionData(RegionData.1 var1) {
      this();
   }

   private RegionData(RegionData var1) {
      Util.checkNotNull(var1);
      String var2 = var1.mKey;
      this.mKey = var2;
      String var3 = var1.mName;
      this.mName = var3;
   }

   // $FF: synthetic method
   RegionData(RegionData var1, RegionData.1 var2) {
      this(var1);
   }

   public String getDisplayName() {
      String var1;
      if(this.mName != null) {
         var1 = this.mName;
      } else {
         var1 = this.mKey;
      }

      return var1;
   }

   String getKey() {
      return this.mKey;
   }

   String getName() {
      return this.mName;
   }

   boolean isValidName(String var1) {
      boolean var2 = false;
      if(var1 != null) {
         String var3 = this.mKey;
         if(!var1.equalsIgnoreCase(var3)) {
            String var4 = this.mName;
            if(!var1.equalsIgnoreCase(var4)) {
               return var2;
            }
         }

         var2 = true;
      }

      return var2;
   }

   static class Builder {

      RegionData mData;


      Builder() {
         RegionData var1 = new RegionData((RegionData.1)null);
         this.mData = var1;
      }

      RegionData build() {
         RegionData var1 = this.mData;
         return new RegionData(var1, (RegionData.1)null);
      }

      RegionData.Builder setKey(String var1) {
         Util.checkNotNull(var1, "Key should not be null.");
         this.mData.mKey = var1;
         return this;
      }

      RegionData.Builder setName(String var1) {
         RegionData var2 = this.mData;
         String var3 = Util.trimToNull(var1);
         var2.mName = var3;
         return this;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

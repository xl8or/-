package com.google.wireless.gdata2.contacts.data;


public class Name {

   private String additionalName;
   private String additionalNameYomi;
   private String familyName;
   private String familyNameYomi;
   private String fullName;
   private String fullNameYomi;
   private String givenName;
   private String givenNameYomi;
   private String namePrefix;
   private String nameSuffix;


   public Name() {}

   public String getAdditionalName() {
      return this.additionalName;
   }

   public String getAdditionalNameYomi() {
      return this.additionalNameYomi;
   }

   public String getFamilyName() {
      return this.familyName;
   }

   public String getFamilyNameYomi() {
      return this.familyNameYomi;
   }

   public String getFullName() {
      return this.fullName;
   }

   public String getFullNameYomi() {
      return this.fullNameYomi;
   }

   public String getGivenName() {
      return this.givenName;
   }

   public String getGivenNameYomi() {
      return this.givenNameYomi;
   }

   public String getNamePrefix() {
      return this.namePrefix;
   }

   public String getNameSuffix() {
      return this.nameSuffix;
   }

   public void setAdditionalName(String var1) {
      this.additionalName = var1;
   }

   public void setAdditionalNameYomi(String var1) {
      this.additionalNameYomi = var1;
   }

   public void setFamilyName(String var1) {
      this.familyName = var1;
   }

   public void setFamilyNameYomi(String var1) {
      this.familyNameYomi = var1;
   }

   public void setFullName(String var1) {
      this.fullName = var1;
   }

   public void setFullNameYomi(String var1) {
      this.fullNameYomi = var1;
   }

   public void setGivenName(String var1) {
      this.givenName = var1;
   }

   public void setGivenNameYomi(String var1) {
      this.givenNameYomi = var1;
   }

   public void setNamePrefix(String var1) {
      this.namePrefix = var1;
   }

   public void setNameSuffix(String var1) {
      this.nameSuffix = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("Name");
      if(this.fullName != null) {
         StringBuffer var3 = var1.append(" fullName:");
         String var4 = this.fullName;
         var3.append(var4);
      }

      if(this.nameSuffix != null) {
         StringBuffer var6 = var1.append(" nameSuffix:");
         String var7 = this.nameSuffix;
         var6.append(var7);
      }

      if(this.namePrefix != null) {
         StringBuffer var9 = var1.append(" namePrefix:");
         String var10 = this.namePrefix;
         var9.append(var10);
      }

      if(this.familyName != null) {
         StringBuffer var12 = var1.append(" familyName:");
         String var13 = this.familyName;
         var12.append(var13);
      }

      if(this.additionalName != null) {
         StringBuffer var15 = var1.append(" additionalName:");
         String var16 = this.additionalName;
         var15.append(var16);
      }

      if(this.givenName != null) {
         StringBuffer var18 = var1.append(" givenName:");
         String var19 = this.givenName;
         var18.append(var19);
      }

      if(this.givenNameYomi != null) {
         StringBuffer var21 = var1.append(" givenNameYomi:");
         String var22 = this.givenNameYomi;
         var21.append(var22);
      }

      if(this.familyNameYomi != null) {
         StringBuffer var24 = var1.append(" familyNameYomi:");
         String var25 = this.familyNameYomi;
         var24.append(var25);
      }

      if(this.additionalNameYomi != null) {
         StringBuffer var27 = var1.append(" additionalNameYomi:");
         String var28 = this.additionalNameYomi;
         var27.append(var28);
      }

      if(this.fullNameYomi != null) {
         StringBuffer var30 = var1.append(" fullNameYomi:");
         String var31 = this.fullNameYomi;
         var30.append(var31);
      }
   }
}

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.ContactsElement;

public class StructuredPostalAddress extends ContactsElement {

   public static final byte TYPE_HOME = 1;
   public static final byte TYPE_OTHER = 3;
   public static final byte TYPE_WORK = 2;
   private String city;
   private String country;
   private String formattedAddress;
   private String neighborhood;
   private String pobox;
   private String postcode;
   private String region;
   private String street;


   public StructuredPostalAddress() {}

   public StructuredPostalAddress(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, byte var9, String var10, boolean var11) {
      super(var9, var10, var11);
      this.street = var1;
      this.pobox = var2;
      this.city = var3;
      this.postcode = var4;
      this.country = var5;
      this.region = var6;
      this.neighborhood = var7;
      this.formattedAddress = var8;
   }

   public String getCity() {
      return this.city;
   }

   public String getCountry() {
      return this.country;
   }

   public String getFormattedAddress() {
      return this.formattedAddress;
   }

   public String getNeighborhood() {
      return this.neighborhood;
   }

   public String getPobox() {
      return this.pobox;
   }

   public String getPostcode() {
      return this.postcode;
   }

   public String getRegion() {
      return this.region;
   }

   public String getStreet() {
      return this.street;
   }

   public void setCity(String var1) {
      this.city = var1;
   }

   public void setCountry(String var1) {
      this.country = var1;
   }

   public void setFormattedAddress(String var1) {
      this.formattedAddress = var1;
   }

   public void setNeighborhood(String var1) {
      this.neighborhood = var1;
   }

   public void setPobox(String var1) {
      this.pobox = var1;
   }

   public void setPostcode(String var1) {
      this.postcode = var1;
   }

   public void setRegion(String var1) {
      this.region = var1;
   }

   public void setStreet(String var1) {
      this.street = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("PostalAddress");
      super.toString(var1);
      if(this.street != null) {
         StringBuffer var3 = var1.append(" street:");
         String var4 = this.street;
         var3.append(var4);
      }

      if(this.pobox != null) {
         StringBuffer var6 = var1.append(" pobox:");
         String var7 = this.pobox;
         var6.append(var7);
      }

      if(this.neighborhood != null) {
         StringBuffer var9 = var1.append(" neighborhood:");
         String var10 = this.neighborhood;
         var9.append(var10);
      }

      if(this.city != null) {
         StringBuffer var12 = var1.append(" city:");
         String var13 = this.city;
         var12.append(var13);
      }

      if(this.region != null) {
         StringBuffer var15 = var1.append(" region:");
         String var16 = this.region;
         var15.append(var16);
      }

      if(this.postcode != null) {
         StringBuffer var18 = var1.append(" postcode:");
         String var19 = this.postcode;
         var18.append(var19);
      }

      if(this.country != null) {
         StringBuffer var21 = var1.append(" country:");
         String var22 = this.country;
         var21.append(var22);
      }

      if(this.formattedAddress != null) {
         StringBuffer var24 = var1.append(" formattedAddress:");
         String var25 = this.formattedAddress;
         var24.append(var25);
      }
   }
}

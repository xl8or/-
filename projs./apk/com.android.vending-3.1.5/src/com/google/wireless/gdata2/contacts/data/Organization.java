package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.ContactsElement;

public class Organization extends ContactsElement {

   public static final byte TYPE_OTHER = 2;
   public static final byte TYPE_WORK = 1;
   private String name;
   private String nameYomi;
   private String orgDepartment;
   private String orgJobDescription;
   private String orgSymbol;
   private String title;
   private String where;


   public Organization() {}

   public Organization(String var1, String var2, String var3, String var4, String var5, String var6, String var7, byte var8, String var9, boolean var10) {
      super(var8, var9, var10);
      this.name = var1;
      this.nameYomi = var2;
      this.title = var3;
      this.orgDepartment = var4;
      this.orgJobDescription = var5;
      this.orgSymbol = var6;
      this.where = var7;
   }

   public String getName() {
      return this.name;
   }

   public String getNameYomi() {
      return this.nameYomi;
   }

   public String getOrgDepartment() {
      return this.orgDepartment;
   }

   public String getOrgJobDescription() {
      return this.orgJobDescription;
   }

   public String getOrgSymbol() {
      return this.orgSymbol;
   }

   public String getTitle() {
      return this.title;
   }

   public String getWhere() {
      return this.where;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setNameYomi(String var1) {
      this.nameYomi = var1;
   }

   public void setOrgDepartment(String var1) {
      this.orgDepartment = var1;
   }

   public void setOrgJobDescription(String var1) {
      this.orgJobDescription = var1;
   }

   public void setOrgSymbol(String var1) {
      this.orgSymbol = var1;
   }

   public void setTitle(String var1) {
      this.title = var1;
   }

   public void setWhere(String var1) {
      this.where = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("Organization");
      super.toString(var1);
      if(this.name != null) {
         StringBuffer var3 = var1.append(" name:");
         String var4 = this.name;
         var3.append(var4);
      }

      if(this.title != null) {
         StringBuffer var6 = var1.append(" title:");
         String var7 = this.title;
         var6.append(var7);
      }

      if(this.orgDepartment != null) {
         StringBuffer var9 = var1.append(" orgDepartment:");
         String var10 = this.orgDepartment;
         var9.append(var10);
      }

      if(this.orgJobDescription != null) {
         StringBuffer var12 = var1.append(" orgJobDescription:");
         String var13 = this.orgJobDescription;
         var12.append(var13);
      }

      if(this.orgSymbol != null) {
         StringBuffer var15 = var1.append(" orgSymbol:");
         String var16 = this.orgSymbol;
         var15.append(var16);
      }

      if(this.nameYomi != null) {
         StringBuffer var18 = var1.append(" nameYomi:");
         String var19 = this.nameYomi;
         var18.append(var19);
      }
   }
}

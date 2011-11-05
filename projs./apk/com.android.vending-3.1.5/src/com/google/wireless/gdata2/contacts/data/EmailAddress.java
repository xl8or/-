package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.ContactsElement;

public class EmailAddress extends ContactsElement {

   public static final byte TYPE_HOME = 1;
   public static final byte TYPE_OTHER = 3;
   public static final byte TYPE_WORK = 2;
   private String address;
   private String displayName;
   private String linksTo;


   public EmailAddress() {}

   public EmailAddress(String var1, String var2, String var3, byte var4, String var5, boolean var6) {
      super(var4, var5, var6);
      this.address = var1;
      this.displayName = var2;
      String var7 = this.linksTo;
      this.linksTo = var7;
   }

   public String getAddress() {
      return this.address;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getLinksTo() {
      return this.linksTo;
   }

   public void setAddress(String var1) {
      this.address = var1;
   }

   public void setDisplayName(String var1) {
      this.displayName = var1;
   }

   public void setLinksTo(String var1) {
      this.linksTo = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("EmailAddress");
      super.toString(var1);
      if(this.address != null) {
         StringBuffer var3 = var1.append(" address:");
         String var4 = this.address;
         var3.append(var4);
      }

      if(this.displayName != null) {
         StringBuffer var6 = var1.append(" displayName:");
         String var7 = this.displayName;
         var6.append(var7);
      }

      if(this.linksTo != null) {
         StringBuffer var9 = var1.append(" linksTo:");
         String var10 = this.linksTo;
         var9.append(var10);
      }
   }
}

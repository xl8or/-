package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.contacts.data.ContactsElement;

public class EmailAddress extends ContactsElement {

   public static final byte TYPE_HOME = 1;
   public static final byte TYPE_OTHER = 3;
   public static final byte TYPE_WORK = 2;
   private String address;


   public EmailAddress() {}

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String var1) {
      this.address = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("EmailAddress");
      super.toString(var1);
      if(this.address != null) {
         StringBuffer var3 = var1.append(" address:");
         String var4 = this.address;
         var3.append(var4);
      }
   }
}

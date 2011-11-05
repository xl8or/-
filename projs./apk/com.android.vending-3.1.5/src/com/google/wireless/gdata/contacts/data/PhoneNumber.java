package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.contacts.data.ContactsElement;

public class PhoneNumber extends ContactsElement {

   public static final byte TYPE_HOME = 2;
   public static final byte TYPE_HOME_FAX = 5;
   public static final byte TYPE_MOBILE = 1;
   public static final byte TYPE_OTHER = 7;
   public static final byte TYPE_PAGER = 6;
   public static final byte TYPE_WORK = 3;
   public static final byte TYPE_WORK_FAX = 4;
   private String phoneNumber;


   public PhoneNumber() {}

   public String getPhoneNumber() {
      return this.phoneNumber;
   }

   public void setPhoneNumber(String var1) {
      this.phoneNumber = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("PhoneNumber");
      super.toString(var1);
      if(this.phoneNumber != null) {
         StringBuffer var3 = var1.append(" phoneNumber:");
         String var4 = this.phoneNumber;
         var3.append(var4);
      }
   }
}

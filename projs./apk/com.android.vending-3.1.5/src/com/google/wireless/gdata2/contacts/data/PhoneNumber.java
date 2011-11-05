package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.ContactsElement;

public class PhoneNumber extends ContactsElement {

   public static final byte TYPE_ASSISTANT = 7;
   public static final byte TYPE_CALLBACK = 8;
   public static final byte TYPE_CAR = 9;
   public static final byte TYPE_COMPANY_MAIN = 10;
   public static final byte TYPE_HOME = 2;
   public static final byte TYPE_HOME_FAX = 5;
   public static final byte TYPE_ISDN = 11;
   public static final byte TYPE_MAIN = 12;
   public static final byte TYPE_MOBILE = 1;
   public static final byte TYPE_OTHER = 19;
   public static final byte TYPE_OTHER_FAX = 13;
   public static final byte TYPE_PAGER = 6;
   public static final byte TYPE_RADIO = 14;
   public static final byte TYPE_TELEX = 15;
   public static final byte TYPE_TTY_TDD = 16;
   public static final byte TYPE_WORK = 3;
   public static final byte TYPE_WORK_FAX = 4;
   public static final byte TYPE_WORK_MOBILE = 17;
   public static final byte TYPE_WORK_PAGER = 18;
   private String linksTo;
   private String phoneNumber;


   public PhoneNumber() {}

   public PhoneNumber(String var1, String var2, byte var3, String var4, boolean var5) {
      super(var3, var4, var5);
      this.phoneNumber = var1;
      this.linksTo = var2;
   }

   public String getLinksTo() {
      return this.linksTo;
   }

   public String getPhoneNumber() {
      return this.phoneNumber;
   }

   public void setLinksTo(String var1) {
      this.linksTo = var1;
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

      if(this.linksTo != null) {
         StringBuffer var6 = var1.append(" linksTo:");
         String var7 = this.linksTo;
         var6.append(var7);
      }
   }
}

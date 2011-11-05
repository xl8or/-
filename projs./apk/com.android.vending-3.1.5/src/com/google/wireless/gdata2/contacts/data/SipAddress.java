package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.ContactsElement;

public class SipAddress extends ContactsElement {

   public static final byte TYPE_HOME = 1;
   public static final byte TYPE_OTHER = 3;
   public static final byte TYPE_WORK = 2;
   private String address;


   public SipAddress() {}

   public SipAddress(String var1, byte var2, String var3, boolean var4) {
      super(var2, var3, var4);
      this.address = var1;
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String var1) {
      this.address = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("SipAddress");
      super.toString(var1);
      if(this.address != null) {
         StringBuffer var3 = var1.append(" address:");
         String var4 = this.address;
         var3.append(var4);
      }
   }
}

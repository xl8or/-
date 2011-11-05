package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.contacts.data.ContactsElement;

public class PostalAddress extends ContactsElement {

   public static final byte TYPE_HOME = 1;
   public static final byte TYPE_OTHER = 3;
   public static final byte TYPE_WORK = 2;
   private String value;


   public PostalAddress() {}

   public String getValue() {
      return this.value;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("PostalAddress");
      super.toString(var1);
      if(this.value != null) {
         StringBuffer var3 = var1.append(" value:");
         String var4 = this.value;
         var3.append(var4);
      }
   }
}

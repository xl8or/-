package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.ContactsElement;
import com.google.wireless.gdata2.data.StringUtils;

public class CalendarLink extends ContactsElement {

   public static final byte TYPE_FREE_BUSY = 3;
   public static final byte TYPE_HOME = 1;
   public static final byte TYPE_WORK = 2;
   private String href;


   public CalendarLink() {}

   public CalendarLink(String var1, byte var2, String var3, boolean var4) {
      super(var2, var3, var4);
      this.setHRef(var1);
   }

   public String getHRef() {
      return this.href;
   }

   public void setHRef(String var1) {
      this.href = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("CalendarLink");
      super.toString(var1);
      if(!StringUtils.isEmpty(this.href)) {
         StringBuffer var3 = var1.append(" href:");
         String var4 = this.href;
         var3.append(var4);
      }
   }
}

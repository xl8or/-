package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.TypedElement;

public class Event extends TypedElement {

   public static final byte TYPE_ANNIVERSARY = 1;
   public static final byte TYPE_OTHER = 2;
   private String startDate;


   public Event() {}

   public Event(String var1, byte var2, String var3) {
      super(var2, var3);
      this.startDate = var1;
   }

   public String getStartDate() {
      return this.startDate;
   }

   public void setStartDate(String var1) {
      this.startDate = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("Event");
      super.toString(var1);
      StringBuffer var3 = var1.append(" date:");
      String var4 = this.startDate.toString();
      var3.append(var4);
   }
}

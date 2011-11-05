package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.contacts.data.ContactsElement;
import com.google.wireless.gdata.parser.ParseException;

public class Organization extends ContactsElement {

   public static final byte TYPE_OTHER = 2;
   public static final byte TYPE_WORK = 1;
   private String name;
   private String title;


   public Organization() {}

   public String getName() {
      return this.name;
   }

   public String getTitle() {
      return this.title;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setTitle(String var1) {
      this.title = var1;
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
   }

   public void validate() throws ParseException {
      super.validate();
      if(this.name == null) {
         if(this.title == null) {
            throw new ParseException("at least one of name or title must be present");
         }
      }
   }
}

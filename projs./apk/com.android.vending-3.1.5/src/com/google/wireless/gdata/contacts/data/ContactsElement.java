package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.parser.ParseException;

public abstract class ContactsElement {

   public static final byte TYPE_NONE = -1;
   private boolean isPrimary;
   private String label;
   private byte type = -1;


   public ContactsElement() {}

   public String getLabel() {
      return this.label;
   }

   public byte getType() {
      return this.type;
   }

   public boolean isPrimary() {
      return this.isPrimary;
   }

   public void setIsPrimary(boolean var1) {
      this.isPrimary = var1;
   }

   public void setLabel(String var1) {
      this.label = var1;
   }

   public void setType(byte var1) {
      this.type = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append(" type:");
      byte var3 = this.type;
      var2.append(var3);
      StringBuffer var5 = var1.append(" isPrimary:");
      boolean var6 = this.isPrimary;
      var5.append(var6);
      if(this.label != null) {
         StringBuffer var8 = var1.append(" label:");
         String var9 = this.label;
         var8.append(var9);
      }
   }

   public void validate() throws ParseException {
      if(this.label != null || this.type != -1) {
         if(this.label == null) {
            return;
         }

         if(this.type == -1) {
            return;
         }
      }

      throw new ParseException("exactly one of label or type must be set");
   }
}

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;

public abstract class TypedElement {

   public static final byte TYPE_NONE = -1;
   private String label;
   private byte type = -1;


   public TypedElement() {}

   public TypedElement(byte var1, String var2) {
      this.type = var1;
      this.label = var2;
   }

   public String getLabel() {
      return this.label;
   }

   public byte getType() {
      return this.type;
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
      if(this.label != null) {
         StringBuffer var5 = var1.append(" label:");
         String var6 = this.label;
         var5.append(var6);
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

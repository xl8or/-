package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;

public class Language {

   private String code;
   private String label;


   public Language() {}

   public Language(String var1, String var2) {
      this.setLabel(var1);
      this.setCode(var2);
   }

   public String getCode() {
      return this.code;
   }

   public String getLabel() {
      return this.label;
   }

   public void setCode(String var1) {
      this.code = var1;
   }

   public void setLabel(String var1) {
      this.label = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("Language");
      if(!StringUtils.isEmpty(this.code)) {
         StringBuffer var3 = var1.append(" code:");
         String var4 = this.code;
         var3.append(var4);
      }

      if(!StringUtils.isEmpty(this.label)) {
         StringBuffer var6 = var1.append(" label:");
         String var7 = this.label;
         var6.append(var7);
      }
   }

   public void validate() throws ParseException {
      if(!StringUtils.isEmpty(this.label) || !StringUtils.isEmpty(this.code)) {
         if(StringUtils.isEmpty(this.label)) {
            return;
         }

         if(StringUtils.isEmpty(this.code)) {
            return;
         }
      }

      throw new ParseException("exactly one of label or code must be set");
   }
}

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;

public class UserDefinedField {

   private String key;
   private String value;


   public UserDefinedField() {}

   public UserDefinedField(String var1, String var2) {
      this.key = var1;
      this.value = var2;
   }

   public String getKey() {
      return this.key;
   }

   public String getValue() {
      return this.value;
   }

   public void setKey(String var1) {
      this.key = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("UserDefinedField");
      if(!StringUtils.isEmpty(this.key)) {
         StringBuffer var3 = var1.append(" key:");
         String var4 = this.key;
         var3.append(var4);
      }

      if(!StringUtils.isEmpty(this.value)) {
         StringBuffer var6 = var1.append(" value:");
         String var7 = this.value;
         var6.append(var7);
      }
   }

   public void validate() throws ParseException {
      if(StringUtils.isEmpty(this.key)) {
         if(StringUtils.isEmpty(this.value)) {
            throw new ParseException("key and value can\'t both be empty");
         }
      }
   }
}

package com.google.wireless.gdata.data;

import com.google.wireless.gdata.parser.ParseException;

public class ExtendedProperty {

   private String name;
   private String value;
   private String xmlBlob;


   public ExtendedProperty() {}

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public String getXmlBlob() {
      return this.xmlBlob;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public void setXmlBlob(String var1) {
      this.xmlBlob = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("ExtendedProperty");
      if(this.name != null) {
         StringBuffer var3 = var1.append(" name:");
         String var4 = this.name;
         var3.append(var4);
      }

      if(this.value != null) {
         StringBuffer var6 = var1.append(" value:");
         String var7 = this.value;
         var6.append(var7);
      }

      if(this.xmlBlob != null) {
         StringBuffer var9 = var1.append(" xmlBlob:");
         String var10 = this.xmlBlob;
         var9.append(var10);
      }
   }

   public void validate() throws ParseException {
      if(this.name == null) {
         throw new ParseException("name must not be null");
      } else {
         if(this.value != null || this.xmlBlob != null) {
            if(this.value == null) {
               return;
            }

            if(this.xmlBlob == null) {
               return;
            }
         }

         throw new ParseException("exactly one of value and xmlBlob must be present");
      }
   }
}

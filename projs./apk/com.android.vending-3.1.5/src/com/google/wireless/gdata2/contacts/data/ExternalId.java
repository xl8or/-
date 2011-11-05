package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.TypedElement;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;

public class ExternalId extends TypedElement {

   public static final byte TYPE_ACCOUNT = 1;
   public static final byte TYPE_CUSTOMER = 2;
   public static final byte TYPE_NETWORK = 3;
   public static final byte TYPE_ORGANIZATION = 4;
   private String value;


   public ExternalId() {}

   public ExternalId(String var1, byte var2, String var3) {
      super(var2, var3);
      this.setValue(var1);
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("ExternalId");
      super.toString(var1);
      if(!StringUtils.isEmpty(this.value)) {
         StringBuffer var3 = var1.append(" value:");
         String var4 = this.value;
         var3.append(var4);
      }
   }

   public void validate() throws ParseException {
      if(this.value == null) {
         throw new ParseException("the value must be set");
      }
   }
}

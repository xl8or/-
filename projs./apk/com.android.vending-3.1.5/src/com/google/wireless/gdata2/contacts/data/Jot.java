package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.TypedElement;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;

public class Jot extends TypedElement {

   public static final byte TYPE_HOME = 1;
   public static final byte TYPE_KEYWORDS = 3;
   public static final byte TYPE_OTHER = 5;
   public static final byte TYPE_USER = 4;
   public static final byte TYPE_WORK = 2;
   private String value;


   public Jot() {}

   public Jot(String var1, byte var2, String var3) {
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
      StringBuffer var2 = var1.append("Jot");
      super.toString(var1);
      if(!StringUtils.isEmpty(this.value)) {
         StringBuffer var3 = var1.append(" value:");
         String var4 = this.value;
         var3.append(var4);
      }
   }

   public void validate() throws ParseException {}
}

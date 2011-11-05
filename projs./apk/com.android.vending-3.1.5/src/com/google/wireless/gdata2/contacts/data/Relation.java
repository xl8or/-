package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.TypedElement;
import com.google.wireless.gdata2.data.StringUtils;

public class Relation extends TypedElement {

   public static final byte TYPE_ASSISTANT = 1;
   public static final byte TYPE_BROTHER = 2;
   public static final byte TYPE_CHILD = 3;
   public static final byte TYPE_DOMESTICPARTNER = 4;
   public static final byte TYPE_FATHER = 5;
   public static final byte TYPE_FRIEND = 6;
   public static final byte TYPE_MANAGER = 7;
   public static final byte TYPE_MOTHER = 8;
   public static final byte TYPE_PARENT = 9;
   public static final byte TYPE_PARTNER = 10;
   public static final byte TYPE_REFERREDBY = 11;
   public static final byte TYPE_RELATIVE = 12;
   public static final byte TYPE_SISTER = 13;
   public static final byte TYPE_SPOUSE = 14;
   private String text;


   public Relation() {}

   public Relation(String var1, byte var2, String var3) {
      super(var2, var3);
      this.text = var1;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("Relation");
      super.toString(var1);
      if(!StringUtils.isEmpty(this.text)) {
         StringBuffer var3 = var1.append(" text:");
         String var4 = this.text;
         var3.append(var4);
      }
   }
}

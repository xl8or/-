package com.novell.sasl.client;


class ParsedDirective {

   public static final int QUOTED_STRING_VALUE = 1;
   public static final int TOKEN_VALUE = 2;
   private String m_name;
   private String m_value;
   private int m_valueType;


   ParsedDirective(String var1, String var2, int var3) {
      this.m_name = var1;
      this.m_value = var2;
      this.m_valueType = var3;
   }

   String getName() {
      return this.m_name;
   }

   String getValue() {
      return this.m_value;
   }

   int getValueType() {
      return this.m_valueType;
   }
}

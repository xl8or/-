package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Map;

public enum AddressField {

   // $FF: synthetic field
   private static final AddressField[] $VALUES;
   ADDRESS_LINE_1("ADDRESS_LINE_1", 4, '1', "street1"),
   ADDRESS_LINE_2("ADDRESS_LINE_2", 5, '2', "street2"),
   ADMIN_AREA("ADMIN_AREA", 0, 'S', "state"),
   COUNTRY("COUNTRY", 10, 'R'),
   DEPENDENT_LOCALITY("DEPENDENT_LOCALITY", 6, 'D');
   private static final Map<Character, AddressField> FIELD_MAPPING;
   LOCALITY("LOCALITY", 1, 'C', "city"),
   ORGANIZATION("ORGANIZATION", 3, 'O', "organization"),
   POSTAL_CODE("POSTAL_CODE", 7, 'Z'),
   RECIPIENT("RECIPIENT", 2, 'N', "name"),
   SORTING_CODE("SORTING_CODE", 8, 'X'),
   STREET_ADDRESS("STREET_ADDRESS", 9, 'A');
   private final String mAttributeName;
   private final char mField;


   static {
      AddressField[] var0 = new AddressField[11];
      AddressField var1 = ADMIN_AREA;
      var0[0] = var1;
      AddressField var2 = LOCALITY;
      var0[1] = var2;
      AddressField var3 = RECIPIENT;
      var0[2] = var3;
      AddressField var4 = ORGANIZATION;
      var0[3] = var4;
      AddressField var5 = ADDRESS_LINE_1;
      var0[4] = var5;
      AddressField var6 = ADDRESS_LINE_2;
      var0[5] = var6;
      AddressField var7 = DEPENDENT_LOCALITY;
      var0[6] = var7;
      AddressField var8 = POSTAL_CODE;
      var0[7] = var8;
      AddressField var9 = SORTING_CODE;
      var0[8] = var9;
      AddressField var10 = STREET_ADDRESS;
      var0[9] = var10;
      AddressField var11 = COUNTRY;
      var0[10] = var11;
      $VALUES = var0;
      FIELD_MAPPING = new HashMap();
      AddressField[] var12 = values();
      int var13 = var12.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         AddressField var15 = var12[var14];
         Map var16 = FIELD_MAPPING;
         Character var17 = Character.valueOf(var15.getChar());
         var16.put(var17, var15);
      }

   }

   private AddressField(String var1, int var2, char var3) {
      this(var1, var2, var3, (String)null);
   }

   private AddressField(String var1, int var2, char var3, String var4) {
      this.mField = var3;
      this.mAttributeName = var4;
   }

   static AddressField of(char var0) {
      Map var1 = FIELD_MAPPING;
      Character var2 = Character.valueOf(var0);
      return (AddressField)var1.get(var2);
   }

   String getAttributeName() {
      return this.mAttributeName;
   }

   char getChar() {
      return this.mField;
   }
}

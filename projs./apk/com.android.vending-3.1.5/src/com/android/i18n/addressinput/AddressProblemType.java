package com.android.i18n.addressinput;


public enum AddressProblemType {

   // $FF: synthetic field
   private static final AddressProblemType[] $VALUES;
   MISMATCHING_VALUE("MISMATCHING_VALUE", 4),
   MISSING_REQUIRED_FIELD("MISSING_REQUIRED_FIELD", 1),
   UNKNOWN_VALUE("UNKNOWN_VALUE", 2),
   UNRECOGNIZED_FORMAT("UNRECOGNIZED_FORMAT", 3),
   USING_UNUSED_FIELD("USING_UNUSED_FIELD", 0);


   static {
      AddressProblemType[] var0 = new AddressProblemType[5];
      AddressProblemType var1 = USING_UNUSED_FIELD;
      var0[0] = var1;
      AddressProblemType var2 = MISSING_REQUIRED_FIELD;
      var0[1] = var2;
      AddressProblemType var3 = UNKNOWN_VALUE;
      var0[2] = var3;
      AddressProblemType var4 = UNRECOGNIZED_FORMAT;
      var0[3] = var4;
      AddressProblemType var5 = MISMATCHING_VALUE;
      var0[4] = var5;
      $VALUES = var0;
   }

   private AddressProblemType(String var1, int var2) {}

   public String keyname() {
      return this.name().toLowerCase();
   }
}

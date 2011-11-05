package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Map;

enum AddressDataKey {

   // $FF: synthetic field
   private static final AddressDataKey[] $VALUES;
   private static final Map<String, AddressDataKey> ADDRESS_KEY_NAME_MAP;
   COUNTRIES("COUNTRIES", 0),
   FMT("FMT", 1),
   ID("ID", 2),
   KEY("KEY", 3),
   LANG("LANG", 4),
   LFMT("LFMT", 5),
   REQUIRE("REQUIRE", 6),
   STATE_NAME_TYPE("STATE_NAME_TYPE", 7),
   SUB_KEYS("SUB_KEYS", 8),
   SUB_LNAMES("SUB_LNAMES", 9),
   SUB_MORES("SUB_MORES", 10),
   SUB_NAMES("SUB_NAMES", 11),
   XZIP("XZIP", 12),
   ZIP("ZIP", 13),
   ZIP_NAME_TYPE("ZIP_NAME_TYPE", 14);


   static {
      AddressDataKey[] var0 = new AddressDataKey[15];
      AddressDataKey var1 = COUNTRIES;
      var0[0] = var1;
      AddressDataKey var2 = FMT;
      var0[1] = var2;
      AddressDataKey var3 = ID;
      var0[2] = var3;
      AddressDataKey var4 = KEY;
      var0[3] = var4;
      AddressDataKey var5 = LANG;
      var0[4] = var5;
      AddressDataKey var6 = LFMT;
      var0[5] = var6;
      AddressDataKey var7 = REQUIRE;
      var0[6] = var7;
      AddressDataKey var8 = STATE_NAME_TYPE;
      var0[7] = var8;
      AddressDataKey var9 = SUB_KEYS;
      var0[8] = var9;
      AddressDataKey var10 = SUB_LNAMES;
      var0[9] = var10;
      AddressDataKey var11 = SUB_MORES;
      var0[10] = var11;
      AddressDataKey var12 = SUB_NAMES;
      var0[11] = var12;
      AddressDataKey var13 = XZIP;
      var0[12] = var13;
      AddressDataKey var14 = ZIP;
      var0[13] = var14;
      AddressDataKey var15 = ZIP_NAME_TYPE;
      var0[14] = var15;
      $VALUES = var0;
      ADDRESS_KEY_NAME_MAP = new HashMap();
      AddressDataKey[] var16 = values();
      int var17 = var16.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         AddressDataKey var19 = var16[var18];
         Map var20 = ADDRESS_KEY_NAME_MAP;
         String var21 = var19.toString().toLowerCase();
         var20.put(var21, var19);
      }

   }

   private AddressDataKey(String var1, int var2) {}

   static AddressDataKey get(String var0) {
      Map var1 = ADDRESS_KEY_NAME_MAP;
      String var2 = var0.toLowerCase();
      return (AddressDataKey)var1.get(var2);
   }
}

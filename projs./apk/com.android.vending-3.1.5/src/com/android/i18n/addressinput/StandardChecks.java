package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblemType;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardChecks {

   public static final Map<AddressField, List<AddressProblemType>> PROBLEM_MAP;


   static {
      HashMap var0 = new HashMap();
      AddressField var1 = AddressField.COUNTRY;
      AddressProblemType[] var2 = new AddressProblemType[3];
      AddressProblemType var3 = AddressProblemType.USING_UNUSED_FIELD;
      var2[0] = var3;
      AddressProblemType var4 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var2[1] = var4;
      AddressProblemType var5 = AddressProblemType.UNKNOWN_VALUE;
      var2[2] = var5;
      addToMap(var0, var1, var2);
      AddressField var6 = AddressField.ADMIN_AREA;
      AddressProblemType[] var7 = new AddressProblemType[3];
      AddressProblemType var8 = AddressProblemType.USING_UNUSED_FIELD;
      var7[0] = var8;
      AddressProblemType var9 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var7[1] = var9;
      AddressProblemType var10 = AddressProblemType.UNKNOWN_VALUE;
      var7[2] = var10;
      addToMap(var0, var6, var7);
      AddressField var11 = AddressField.LOCALITY;
      AddressProblemType[] var12 = new AddressProblemType[3];
      AddressProblemType var13 = AddressProblemType.USING_UNUSED_FIELD;
      var12[0] = var13;
      AddressProblemType var14 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var12[1] = var14;
      AddressProblemType var15 = AddressProblemType.UNKNOWN_VALUE;
      var12[2] = var15;
      addToMap(var0, var11, var12);
      AddressField var16 = AddressField.DEPENDENT_LOCALITY;
      AddressProblemType[] var17 = new AddressProblemType[3];
      AddressProblemType var18 = AddressProblemType.USING_UNUSED_FIELD;
      var17[0] = var18;
      AddressProblemType var19 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var17[1] = var19;
      AddressProblemType var20 = AddressProblemType.UNKNOWN_VALUE;
      var17[2] = var20;
      addToMap(var0, var16, var17);
      AddressField var21 = AddressField.POSTAL_CODE;
      AddressProblemType[] var22 = new AddressProblemType[4];
      AddressProblemType var23 = AddressProblemType.USING_UNUSED_FIELD;
      var22[0] = var23;
      AddressProblemType var24 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var22[1] = var24;
      AddressProblemType var25 = AddressProblemType.UNRECOGNIZED_FORMAT;
      var22[2] = var25;
      AddressProblemType var26 = AddressProblemType.MISMATCHING_VALUE;
      var22[3] = var26;
      addToMap(var0, var21, var22);
      AddressField var27 = AddressField.STREET_ADDRESS;
      AddressProblemType[] var28 = new AddressProblemType[2];
      AddressProblemType var29 = AddressProblemType.USING_UNUSED_FIELD;
      var28[0] = var29;
      AddressProblemType var30 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var28[1] = var30;
      addToMap(var0, var27, var28);
      AddressField var31 = AddressField.SORTING_CODE;
      AddressProblemType[] var32 = new AddressProblemType[2];
      AddressProblemType var33 = AddressProblemType.USING_UNUSED_FIELD;
      var32[0] = var33;
      AddressProblemType var34 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var32[1] = var34;
      addToMap(var0, var31, var32);
      AddressField var35 = AddressField.ORGANIZATION;
      AddressProblemType[] var36 = new AddressProblemType[2];
      AddressProblemType var37 = AddressProblemType.USING_UNUSED_FIELD;
      var36[0] = var37;
      AddressProblemType var38 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var36[1] = var38;
      addToMap(var0, var35, var36);
      AddressField var39 = AddressField.RECIPIENT;
      AddressProblemType[] var40 = new AddressProblemType[2];
      AddressProblemType var41 = AddressProblemType.USING_UNUSED_FIELD;
      var40[0] = var41;
      AddressProblemType var42 = AddressProblemType.MISSING_REQUIRED_FIELD;
      var40[1] = var42;
      addToMap(var0, var39, var40);
      PROBLEM_MAP = Collections.unmodifiableMap(var0);
   }

   private StandardChecks() {}

   private static void addToMap(Map<AddressField, List<AddressProblemType>> var0, AddressField var1, AddressProblemType ... var2) {
      List var3 = Collections.unmodifiableList(Arrays.asList(var2));
      var0.put(var1, var3);
   }
}

package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressDataKey;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressWidget;
import com.android.i18n.addressinput.FormOptions;
import com.android.i18n.addressinput.LookupKey;
import com.android.i18n.addressinput.RegionDataConstants;
import com.android.i18n.addressinput.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

class FormatInterpreter {

   private static final String NEW_LINE = "%n";
   private final String mDefaultFormat;
   private final FormOptions mFormOptions;


   FormatInterpreter(FormOptions var1) {
      Util.checkNotNull(RegionDataConstants.getCountryFormatMap(), "null country name map not allowed");
      Util.checkNotNull(var1);
      this.mFormOptions = var1;
      AddressDataKey var2 = AddressDataKey.FMT;
      String var3 = this.getJsonValue("ZZ", var2);
      this.mDefaultFormat = var3;
      Util.checkNotNull(this.mDefaultFormat, "null default format not allowed");
   }

   private String getFormatString(LookupKey.ScriptType var1, String var2) {
      LookupKey.ScriptType var3 = LookupKey.ScriptType.LOCAL;
      String var5;
      if(var1 == var3) {
         AddressDataKey var4 = AddressDataKey.FMT;
         var5 = this.getJsonValue(var2, var4);
      } else {
         AddressDataKey var6 = AddressDataKey.LFMT;
         var5 = this.getJsonValue(var2, var6);
      }

      return var5;
   }

   private List<String> getFormatSubStrings(LookupKey.ScriptType var1, String var2) {
      String var3 = this.getFormatString(var1, var2);
      ArrayList var4 = new ArrayList();
      boolean var5 = false;
      char[] var6 = var3.toCharArray();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         char var9 = var6[var8];
         if(var5) {
            var5 = false;
            String var10 = "%" + var9;
            if("%n".equals(var10)) {
               boolean var11 = var4.add("%n");
            } else {
               AddressField var12 = AddressField.of(var9);
               String var13 = "Unrecognized character \'" + var9 + "\' in format pattern: " + var3;
               Util.checkNotNull(var12, var13);
               String var14 = "%" + var9;
               var4.add(var14);
            }
         } else if(var9 == 37) {
            var5 = true;
         } else {
            String var16 = var9 + "";
            var4.add(var16);
         }
      }

      return var4;
   }

   private String getJsonValue(String var1, AddressDataKey var2) {
      Util.checkNotNull(var1);
      String var3 = (String)RegionDataConstants.getCountryFormatMap().get(var1);
      String var4;
      if(var3 == null) {
         var4 = this.mDefaultFormat;
      } else {
         String var9;
         try {
            JSONTokener var5 = new JSONTokener(var3);
            JSONObject var6 = new JSONObject(var5);
            String var7 = var2.name().toLowerCase();
            if(!var6.has(var7)) {
               var4 = this.mDefaultFormat;
               return var4;
            }

            String var8 = var2.name().toLowerCase();
            var9 = var6.getString(var8);
         } catch (JSONException var12) {
            String var11 = "Invalid json for region code " + var1 + ": " + var3;
            throw new RuntimeException(var11);
         }

         var4 = var9;
      }

      return var4;
   }

   private void overrideFieldOrder(String var1, List<AddressField> var2) {
      if(this.mFormOptions.getCustomFieldOrder(var1) != null) {
         HashMap var3 = new HashMap();
         int var4 = 0;
         AddressField[] var5 = this.mFormOptions.getCustomFieldOrder(var1);
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            AddressField var8 = var5[var7];
            Integer var9 = Integer.valueOf(var4);
            var3.put(var8, var9);
            ++var4;
         }

         ArrayList var11 = new ArrayList();
         ArrayList var12 = new ArrayList();
         int var13 = 0;

         for(Iterator var14 = var2.iterator(); var14.hasNext(); ++var13) {
            AddressField var15 = (AddressField)var14.next();
            if(var3.containsKey(var15)) {
               var11.add(var15);
               Integer var17 = Integer.valueOf(var13);
               var12.add(var17);
            }
         }

         FormatInterpreter.1 var19 = new FormatInterpreter.1(var3);
         Collections.sort(var11, var19);
         int var20 = 0;

         while(true) {
            int var21 = var11.size();
            if(var20 >= var21) {
               return;
            }

            int var22 = ((Integer)var12.get(var20)).intValue();
            Object var23 = var11.get(var20);
            var2.set(var22, var23);
            ++var20;
         }
      }
   }

   private String removeAllRedundantSpaces(String var1) {
      return var1.trim().replaceAll(" +", " ");
   }

   List<AddressField> getAddressFieldOrder(LookupKey.ScriptType var1, String var2) {
      Util.checkNotNull(var1);
      Util.checkNotNull(var2);
      ArrayList var3 = new ArrayList();
      Iterator var4 = this.getFormatSubStrings(var1, var2).iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         if(var5.matches("%.") && !var5.equals("%n")) {
            AddressField var6 = AddressField.of(var5.charAt(1));
            var3.add(var6);
         }
      }

      this.overrideFieldOrder(var2, var3);
      ArrayList var8 = new ArrayList();
      Iterator var9 = var3.iterator();

      while(var9.hasNext()) {
         AddressField var10 = (AddressField)var9.next();
         AddressField var11 = AddressField.STREET_ADDRESS;
         if(var10 == var11) {
            AddressField var12 = AddressField.ADDRESS_LINE_1;
            var8.add(var12);
            AddressField var14 = AddressField.ADDRESS_LINE_2;
            var8.add(var14);
         } else {
            var8.add(var10);
         }
      }

      return var8;
   }

   List<AddressField> getAddressFieldOrder(String var1) {
      Util.checkNotNull(var1);
      LookupKey.ScriptType var2 = LookupKey.ScriptType.LOCAL;
      return this.getAddressFieldOrder(var2, var1);
   }

   List<String> getEnvelopeAddress(AddressData var1, String var2) {
      Util.checkNotNull(var1, "null input address not allowed");
      String var3 = var1.getPostalCountry();
      if(!AddressWidget.isValidRegionCode(var3)) {
         var3 = var2;
      }

      String var4 = var1.getLanguageCode();
      LookupKey.ScriptType var5 = LookupKey.ScriptType.LOCAL;
      if(var4 != null) {
         if(Util.isExplicitLatinScript(var4)) {
            var5 = LookupKey.ScriptType.LATIN;
         } else {
            var5 = LookupKey.ScriptType.LOCAL;
         }
      }

      ArrayList var6 = new ArrayList();
      StringBuilder var7 = new StringBuilder();
      Iterator var8 = this.getFormatSubStrings(var5, var3).iterator();

      while(var8.hasNext()) {
         String var9 = (String)var8.next();
         if(var9.equals("%n")) {
            String var10 = var7.toString();
            String var11 = this.removeAllRedundantSpaces(var10);
            if(var11.length() > 0) {
               var6.add(var11);
               var7.setLength(0);
            }
         } else if(var9.startsWith("%")) {
            char var13 = var9.charAt(1);
            AddressField var14 = AddressField.of(var13);
            String var15 = "null address field for character " + var13;
            Util.checkNotNull(var14, var15);
            String var16 = null;
            int[] var17 = FormatInterpreter.2.$SwitchMap$com$android$i18n$addressinput$AddressField;
            int var18 = var14.ordinal();
            switch(var17[var18]) {
            case 1:
               String[] var20 = new String[2];
               String var21 = var1.getAddressLine1();
               var20[0] = var21;
               String var22 = var1.getAddressLine2();
               var20[1] = var22;
               var16 = Util.joinAndSkipNulls("\n", var20);
            case 2:
            default:
               break;
            case 3:
               var16 = var1.getAdministrativeArea();
               break;
            case 4:
               var16 = var1.getLocality();
               break;
            case 5:
               var16 = var1.getDependentLocality();
               break;
            case 6:
               var16 = var1.getRecipient();
               break;
            case 7:
               var16 = var1.getOrganization();
               break;
            case 8:
               var16 = var1.getPostalCode();
            }

            if(var16 != null) {
               var7.append(var16);
            }
         } else {
            var7.append(var9);
         }
      }

      String var24 = var7.toString();
      String var25 = this.removeAllRedundantSpaces(var24);
      if(var25.length() > 0) {
         var6.add(var25);
      }

      return var6;
   }

   class 1 implements Comparator<AddressField> {

      // $FF: synthetic field
      final Map val$fieldPriority;


      1(Map var2) {
         this.val$fieldPriority = var2;
      }

      public int compare(AddressField var1, AddressField var2) {
         int var3 = ((Integer)this.val$fieldPriority.get(var1)).intValue();
         int var4 = ((Integer)this.val$fieldPriority.get(var2)).intValue();
         return var3 - var4;
      }
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$i18n$addressinput$AddressField = new int[AddressField.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var1 = AddressField.STREET_ADDRESS.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var31) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var3 = AddressField.COUNTRY.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var30) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var5 = AddressField.ADMIN_AREA.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var29) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var7 = AddressField.LOCALITY.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var28) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var9 = AddressField.DEPENDENT_LOCALITY.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var27) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var11 = AddressField.RECIPIENT.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var26) {
            ;
         }

         try {
            int[] var12 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var13 = AddressField.ORGANIZATION.ordinal();
            var12[var13] = 7;
         } catch (NoSuchFieldError var25) {
            ;
         }

         try {
            int[] var14 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var15 = AddressField.POSTAL_CODE.ordinal();
            var14[var15] = 8;
         } catch (NoSuchFieldError var24) {
            ;
         }
      }
   }
}

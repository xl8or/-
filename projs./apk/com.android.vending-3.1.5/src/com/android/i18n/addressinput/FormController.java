package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressDataKey;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressVerificationNodeData;
import com.android.i18n.addressinput.ClientData;
import com.android.i18n.addressinput.DataLoadListener;
import com.android.i18n.addressinput.LookupKey;
import com.android.i18n.addressinput.RegionData;
import com.android.i18n.addressinput.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class FormController {

   private static final AddressField[] ADDRESS_HIERARCHY;
   private static final String DASH_DELIM = "--";
   private static final String DEFAULT_REGION_CODE = "ZZ";
   private static final LookupKey ROOT_KEY = getDataKeyForRoot();
   private static final String SLASH_DELIM = "/";
   private static final String TILDE_DELIM = "~";
   private String mCurrentCountry;
   private ClientData mIntegratedData;
   private String mLanguageCode;


   static {
      AddressField[] var0 = new AddressField[4];
      AddressField var1 = AddressField.COUNTRY;
      var0[0] = var1;
      AddressField var2 = AddressField.ADMIN_AREA;
      var0[1] = var2;
      AddressField var3 = AddressField.LOCALITY;
      var0[2] = var3;
      AddressField var4 = AddressField.DEPENDENT_LOCALITY;
      var0[3] = var4;
      ADDRESS_HIERARCHY = var0;
   }

   FormController(ClientData var1, String var2, String var3) {
      Util.checkNotNull(var1, "null data not allowed");
      this.mLanguageCode = var2;
      this.mCurrentCountry = var3;
      AddressData var4 = (new AddressData.Builder()).setCountry("ZZ").build();
      LookupKey var5 = this.getDataKeyFor(var4);
      String var6 = var5.toString();
      AddressVerificationNodeData var7 = var1.getDefaultData(var6);
      String var8 = "require data for default country key: " + var5;
      Util.checkNotNull(var7, var8);
      this.mIntegratedData = var1;
   }

   private LookupKey buildDataLookupKey(LookupKey var1, String var2) {
      String[] var3 = var1.toString().split("/");
      String var4;
      if(this.mLanguageCode == null) {
         var4 = null;
      } else {
         var4 = Util.getLanguageSubtag(this.mLanguageCode);
      }

      StringBuilder var5 = new StringBuilder();
      String var6 = var1.toString();
      String var7 = var5.append(var6).append("/").append(var2).toString();
      if(var3.length == 1 && var4 != null && !this.isDefaultLanguage(var4)) {
         StringBuilder var8 = (new StringBuilder()).append(var7).append("--");
         String var9 = var4.toString();
         var7 = var8.append(var9).toString();
      }

      return (new LookupKey.Builder(var7)).build();
   }

   private static LookupKey getDataKeyForRoot() {
      AddressData var0 = (new AddressData.Builder()).build();
      LookupKey.KeyType var1 = LookupKey.KeyType.DATA;
      return (new LookupKey.Builder(var1)).setAddressData(var0).build();
   }

   private LookupKey.ScriptType getScriptType() {
      LookupKey.ScriptType var1;
      if(this.mLanguageCode != null && Util.isExplicitLatinScript(this.mLanguageCode)) {
         var1 = LookupKey.ScriptType.LATIN;
      } else {
         var1 = LookupKey.ScriptType.LOCAL;
      }

      return var1;
   }

   private String getSubKey(LookupKey var1, String var2) {
      Iterator var3 = this.getRegionData(var1).iterator();

      String var5;
      while(true) {
         if(var3.hasNext()) {
            RegionData var4 = (RegionData)var3.next();
            if(!var4.isValidName(var2)) {
               continue;
            }

            var5 = var4.getKey();
            break;
         }

         var5 = null;
         break;
      }

      return var5;
   }

   private LookupKey normalizeLookupKey(LookupKey var1) {
      Util.checkNotNull(var1);
      LookupKey.KeyType var2 = var1.getKeyType();
      LookupKey.KeyType var3 = LookupKey.KeyType.DATA;
      if(var2 != var3) {
         throw new RuntimeException("Only DATA keyType is supported");
      } else {
         String[] var4 = var1.toString().split("/");
         if(var4.length >= 2) {
            String var5 = var4[0];
            StringBuilder var6 = new StringBuilder(var5);
            int var7 = 1;

            label35:
            while(true) {
               int var8 = var4.length;
               if(var7 >= var8) {
                  break;
               }

               String var9 = null;
               if(var7 == 1 && var4[var7].contains("--")) {
                  String[] var10 = var4[var7].split("--");
                  String var11 = var10[0];
                  var4[var7] = var11;
                  var9 = var10[1];
               }

               String var12 = var6.toString();
               LookupKey var13 = (new LookupKey.Builder(var12)).build();
               String var14 = var4[var7];
               String var15 = this.getSubKey(var13, var14);
               if(var15 == null) {
                  while(true) {
                     int var16 = var4.length;
                     if(var7 >= var16) {
                        break label35;
                     }

                     StringBuilder var17 = var6.append("/");
                     String var18 = var4[var7];
                     var17.append(var18);
                     ++var7;
                  }
               }

               StringBuilder var20 = var6.append("/").append(var15);
               if(var9 != null) {
                  StringBuilder var21 = var6.append("--").append(var9);
               }

               ++var7;
            }

            String var22 = var6.toString();
            var1 = (new LookupKey.Builder(var22)).build();
         }

         return var1;
      }
   }

   private void requestDataRecursively(LookupKey var1, Queue<String> var2, DataLoadListener var3) {
      Util.checkNotNull(var1, "Null key not allowed");
      Util.checkNotNull(var2, "Null subkeys not allowed");
      ClientData var4 = this.mIntegratedData;
      FormController.1 var5 = new FormController.1(var1, var3, var2);
      var4.requestData(var1, var5);
   }

   private String[] splitData(String var1) {
      String[] var2;
      if(var1 != null && var1.length() != 0) {
         var2 = var1.split("~");
      } else {
         var2 = new String[0];
      }

      return var2;
   }

   LookupKey getDataKeyFor(AddressData var1) {
      LookupKey.KeyType var2 = LookupKey.KeyType.DATA;
      return (new LookupKey.Builder(var2)).setAddressData(var1).build();
   }

   List<RegionData> getRegionData(LookupKey var1) {
      LookupKey.KeyType var2 = var1.getKeyType();
      LookupKey.KeyType var3 = LookupKey.KeyType.EXAMPLES;
      if(var2 == var3) {
         throw new RuntimeException("example key not allowed for getting region data");
      } else {
         Util.checkNotNull(var1, "null regionKey not allowed");
         LookupKey var4 = this.normalizeLookupKey(var1);
         ArrayList var5 = new ArrayList();
         LookupKey var6 = ROOT_KEY;
         int var13;
         if(var4.equals(var6)) {
            ClientData var7 = this.mIntegratedData;
            String var8 = var4.toString();
            AddressVerificationNodeData var9 = var7.getDefaultData(var8);
            AddressDataKey var10 = AddressDataKey.COUNTRIES;
            String var11 = var9.get(var10);
            String[] var12 = this.splitData(var11);
            var13 = 0;

            while(true) {
               int var14 = var12.length;
               if(var13 >= var14) {
                  break;
               }

               RegionData.Builder var15 = new RegionData.Builder();
               String var16 = var12[var13];
               RegionData.Builder var17 = var15.setKey(var16);
               String var18 = var12[var13];
               RegionData var19 = var17.setName(var18).build();
               var5.add(var19);
               ++var13;
            }
         } else {
            ClientData var21 = this.mIntegratedData;
            String var22 = var4.toString();
            AddressVerificationNodeData var23 = var21.get(var22);
            if(var23 != null) {
               AddressDataKey var24 = AddressDataKey.SUB_KEYS;
               String var25 = var23.get(var24);
               String[] var26 = this.splitData(var25);
               LookupKey.ScriptType var27 = this.getScriptType();
               LookupKey.ScriptType var28 = LookupKey.ScriptType.LOCAL;
               String[] var31;
               if(var27 == var28) {
                  AddressDataKey var29 = AddressDataKey.SUB_NAMES;
                  String var30 = var23.get(var29);
                  var31 = this.splitData(var30);
               } else {
                  AddressDataKey var40 = AddressDataKey.SUB_LNAMES;
                  String var41 = var23.get(var40);
                  var31 = this.splitData(var41);
               }

               var13 = 0;

               while(true) {
                  int var32 = var26.length;
                  if(var13 >= var32) {
                     break;
                  }

                  RegionData.Builder var33 = new RegionData.Builder();
                  String var34 = var26[var13];
                  RegionData.Builder var35 = var33.setKey(var34);
                  int var36 = var31.length;
                  String var37;
                  if(var13 < var36) {
                     var37 = var31[var13];
                  } else {
                     var37 = var26[var13];
                  }

                  RegionData var38 = var35.setName(var37).build();
                  var5.add(var38);
                  ++var13;
               }
            }
         }

         return var5;
      }
   }

   boolean isDefaultLanguage(String var1) {
      boolean var2 = true;
      if(var1 != null) {
         AddressData.Builder var3 = new AddressData.Builder();
         String var4 = this.mCurrentCountry;
         AddressData var5 = var3.setCountry(var4).build();
         LookupKey var6 = this.getDataKeyFor(var5);
         ClientData var7 = this.mIntegratedData;
         String var8 = var6.toString();
         AddressVerificationNodeData var9 = var7.getDefaultData(var8);
         AddressDataKey var10 = AddressDataKey.LANG;
         if(Util.trimToNull(var9.get(var10)) != null) {
            String var11 = Util.getLanguageSubtag(var1);
            String var12 = Util.getLanguageSubtag(var1);
            if(!var11.equals(var12)) {
               var2 = false;
            }
         }
      }

      return var2;
   }

   void requestDataForAddress(AddressData var1, DataLoadListener var2) {
      Util.checkNotNull(var1.getPostalCountry(), "null country not allowed");
      LinkedList var3 = new LinkedList();
      AddressField[] var4 = ADDRESS_HIERARCHY;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         AddressField var7 = var4[var6];
         String var8 = var1.getFieldValue(var7);
         if(var8 == null) {
            break;
         }

         var3.add(var8);
      }

      if(var3.size() == 0) {
         throw new RuntimeException("Need at least country level info");
      } else {
         if(var2 != null) {
            var2.dataLoadingBegin();
         }

         LookupKey var10 = ROOT_KEY;
         this.requestDataRecursively(var10, var3, var2);
      }
   }

   void setCurrentCountry(String var1) {
      this.mCurrentCountry = var1;
   }

   void setLanguageCode(String var1) {
      this.mLanguageCode = var1;
   }

   class 1 implements DataLoadListener {

      // $FF: synthetic field
      final LookupKey val$key;
      // $FF: synthetic field
      final DataLoadListener val$listener;
      // $FF: synthetic field
      final Queue val$subkeys;


      1(LookupKey var2, DataLoadListener var3, Queue var4) {
         this.val$key = var2;
         this.val$listener = var3;
         this.val$subkeys = var4;
      }

      public void dataLoadingBegin() {}

      public void dataLoadingEnd() {
         FormController var1 = FormController.this;
         LookupKey var2 = this.val$key;
         List var3 = var1.getRegionData(var2);
         if(var3.isEmpty()) {
            if(this.val$listener != null) {
               this.val$listener.dataLoadingEnd();
            }
         } else {
            if(this.val$subkeys.size() > 0) {
               String var4 = (String)this.val$subkeys.remove();
               Iterator var5 = var3.iterator();

               while(var5.hasNext()) {
                  RegionData var6 = (RegionData)var5.next();
                  if(var6.isValidName(var4)) {
                     FormController var7 = FormController.this;
                     LookupKey var8 = this.val$key;
                     String var9 = var6.getKey();
                     LookupKey var10 = var7.buildDataLookupKey(var8, var9);
                     FormController var11 = FormController.this;
                     Queue var12 = this.val$subkeys;
                     DataLoadListener var13 = this.val$listener;
                     var11.requestDataRecursively(var10, var12, var13);
                     return;
                  }
               }
            }

            String var14 = ((RegionData)var3.get(0)).getKey();
            FormController var15 = FormController.this;
            LookupKey var16 = this.val$key;
            LookupKey var17 = var15.buildDataLookupKey(var16, var14);
            LinkedList var18 = new LinkedList();
            FormController var19 = FormController.this;
            DataLoadListener var20 = this.val$listener;
            var19.requestDataRecursively(var17, var18, var20);
         }
      }
   }
}

package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressDataKey;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblemType;
import com.android.i18n.addressinput.AddressProblems;
import com.android.i18n.addressinput.AddressVerificationNodeData;
import com.android.i18n.addressinput.DataSource;
import com.android.i18n.addressinput.LookupKey;
import com.android.i18n.addressinput.Util;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class FieldVerifier {

   private static final String DATA_DELIMITER = "~";
   private static final String KEY_DELIMITER = "/";
   private Map<String, String> mCandidateValues;
   private DataSource mDataSource;
   private Pattern mFormat;
   private String mId;
   private String[] mKeys;
   private String[] mLatinNames;
   private String[] mLocalNames;
   private Pattern mMatch;
   private Set<AddressField> mPossibleFields;
   private Set<AddressField> mRequired;


   public FieldVerifier(DataSource var1) {
      this.mDataSource = var1;
      this.populateRootVerifier();
   }

   private FieldVerifier(FieldVerifier var1, AddressVerificationNodeData var2) {
      Set var3 = var1.mPossibleFields;
      this.mPossibleFields = var3;
      Set var4 = var1.mRequired;
      this.mRequired = var4;
      DataSource var5 = var1.mDataSource;
      this.mDataSource = var5;
      Pattern var6 = var1.mFormat;
      this.mFormat = var6;
      Pattern var7 = var1.mMatch;
      this.mMatch = var7;
      this.populate(var2);
      String[] var8 = this.mKeys;
      String[] var9 = this.mLocalNames;
      String[] var10 = this.mLatinNames;
      Map var11 = Util.buildNameToKeyMap(var8, var9, var10);
      this.mCandidateValues = var11;
   }

   private boolean isCountryKey() {
      Util.checkNotNull(this.mId, "Cannot use null as key");
      boolean var1;
      if(this.mId.split("/").length == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isKnownInScript(LookupKey.ScriptType var1, String var2) {
      boolean var3 = true;
      String var4 = Util.trimToNull(var2);
      Util.checkNotNull(var4);
      if(var1 == null) {
         if(this.mCandidateValues != null) {
            Map var5 = this.mCandidateValues;
            String var6 = var4.toLowerCase();
            if(!var5.containsKey(var6)) {
               var3 = false;
            }
         }
      } else {
         LookupKey.ScriptType var7 = LookupKey.ScriptType.LATIN;
         String[] var8;
         if(var1 == var7) {
            var8 = this.mLatinNames;
         } else {
            var8 = this.mLocalNames;
         }

         HashSet var9 = new HashSet();
         String[] var10;
         int var11;
         int var12;
         if(var8 != null) {
            var10 = var8;
            var11 = var8.length;

            for(var12 = 0; var12 < var11; ++var12) {
               String var13 = var10[var12].toLowerCase();
               var9.add(var13);
            }
         }

         if(this.mKeys != null) {
            var10 = this.mKeys;
            var11 = var10.length;

            for(var12 = 0; var12 < var11; ++var12) {
               String var15 = var10[var12].toLowerCase();
               var9.add(var15);
            }
         }

         if(var9.size() != 0 && var4 != null) {
            String var17 = var2.toLowerCase();
            var3 = var9.contains(var17);
         }
      }

      return var3;
   }

   private static Set<AddressField> parseAddressFields(String var0) {
      EnumSet var1 = EnumSet.of(AddressField.COUNTRY);
      boolean var2 = false;
      char[] var3 = var0.toCharArray();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var3[var5];
         if(var2) {
            var2 = false;
            if(var6 != 110) {
               AddressField var7 = AddressField.of(var6);
               if(var7 == null) {
                  String var8 = "Unrecognized character \'" + var6 + "\' in format pattern: " + var0;
                  throw new RuntimeException(var8);
               }

               var1.add(var7);
            }
         } else if(var6 == 37) {
            var2 = true;
         }
      }

      AddressField var10 = AddressField.ADDRESS_LINE_1;
      var1.remove(var10);
      AddressField var12 = AddressField.ADDRESS_LINE_2;
      var1.remove(var12);
      return var1;
   }

   private static Set<AddressField> parseRequireString(String var0) {
      EnumSet var1 = EnumSet.of(AddressField.COUNTRY);
      char[] var2 = var0.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var2[var4];
         AddressField var6 = AddressField.of(var5);
         if(var6 == null) {
            String var7 = "Unrecognized character \'" + var5 + "\' in require pattern: " + var0;
            throw new RuntimeException(var7);
         }

         var1.add(var6);
      }

      AddressField var9 = AddressField.ADDRESS_LINE_1;
      var1.remove(var9);
      AddressField var11 = AddressField.ADDRESS_LINE_2;
      var1.remove(var11);
      return var1;
   }

   private void populate(AddressVerificationNodeData var1) {
      if(var1 != null) {
         AddressDataKey var2 = AddressDataKey.ID;
         if(var1.containsKey(var2)) {
            AddressDataKey var3 = AddressDataKey.ID;
            String var4 = var1.get(var3);
            this.mId = var4;
         }

         AddressDataKey var5 = AddressDataKey.SUB_KEYS;
         if(var1.containsKey(var5)) {
            AddressDataKey var6 = AddressDataKey.SUB_KEYS;
            String[] var7 = var1.get(var6).split("~");
            this.mKeys = var7;
         }

         AddressDataKey var8 = AddressDataKey.SUB_LNAMES;
         if(var1.containsKey(var8)) {
            AddressDataKey var9 = AddressDataKey.SUB_LNAMES;
            String[] var10 = var1.get(var9).split("~");
            this.mLatinNames = var10;
         }

         AddressDataKey var11 = AddressDataKey.SUB_NAMES;
         if(var1.containsKey(var11)) {
            AddressDataKey var12 = AddressDataKey.SUB_NAMES;
            String[] var13 = var1.get(var12).split("~");
            this.mLocalNames = var13;
         }

         AddressDataKey var14 = AddressDataKey.FMT;
         if(var1.containsKey(var14)) {
            AddressDataKey var15 = AddressDataKey.FMT;
            Set var16 = parseAddressFields(var1.get(var15));
            this.mPossibleFields = var16;
         }

         AddressDataKey var17 = AddressDataKey.REQUIRE;
         if(var1.containsKey(var17)) {
            AddressDataKey var18 = AddressDataKey.REQUIRE;
            Set var19 = parseRequireString(var1.get(var18));
            this.mRequired = var19;
         }

         AddressDataKey var20 = AddressDataKey.XZIP;
         if(var1.containsKey(var20)) {
            AddressDataKey var21 = AddressDataKey.XZIP;
            Pattern var22 = Pattern.compile(var1.get(var21), 2);
            this.mFormat = var22;
         }

         AddressDataKey var23 = AddressDataKey.ZIP;
         if(var1.containsKey(var23)) {
            if(this.isCountryKey()) {
               AddressDataKey var24 = AddressDataKey.ZIP;
               Pattern var25 = Pattern.compile(var1.get(var24), 2);
               this.mFormat = var25;
            } else {
               AddressDataKey var29 = AddressDataKey.ZIP;
               Pattern var30 = Pattern.compile(var1.get(var29), 2);
               this.mMatch = var30;
            }
         }

         if(this.mKeys != null) {
            if(this.mLocalNames == null) {
               if(this.mLatinNames != null) {
                  int var26 = this.mKeys.length;
                  int var27 = this.mLatinNames.length;
                  if(var26 == var27) {
                     String[] var28 = this.mKeys;
                     this.mLocalNames = var28;
                  }
               }
            }
         }
      }
   }

   private void populateRootVerifier() {
      this.mId = "data";
      AddressVerificationNodeData var1 = this.mDataSource.getDefaultData("data");
      AddressDataKey var2 = AddressDataKey.COUNTRIES;
      if(var1.containsKey(var2)) {
         AddressDataKey var3 = AddressDataKey.COUNTRIES;
         String[] var4 = var1.get(var3).split("~");
         this.mKeys = var4;
      }

      Map var5 = Util.buildNameToKeyMap(this.mKeys, (String[])null, (String[])null);
      this.mCandidateValues = var5;
      AddressVerificationNodeData var6 = this.mDataSource.getDefaultData("data/ZZ");
      HashSet var7 = new HashSet();
      this.mPossibleFields = var7;
      AddressDataKey var8 = AddressDataKey.FMT;
      if(var6.containsKey(var8)) {
         AddressDataKey var9 = AddressDataKey.FMT;
         Set var10 = parseAddressFields(var6.get(var9));
         this.mPossibleFields = var10;
      }

      HashSet var11 = new HashSet();
      this.mRequired = var11;
      AddressDataKey var12 = AddressDataKey.REQUIRE;
      if(var6.containsKey(var12)) {
         AddressDataKey var13 = AddressDataKey.REQUIRE;
         Set var14 = parseRequireString(var6.get(var13));
         this.mRequired = var14;
      }
   }

   protected boolean check(LookupKey.ScriptType var1, AddressProblemType var2, AddressField var3, String var4, AddressProblems var5) {
      boolean var6 = true;
      boolean var7 = false;
      String var8 = Util.trimToNull(var4);
      int[] var9 = FieldVerifier.1.$SwitchMap$com$android$i18n$addressinput$AddressProblemType;
      int var10 = var2.ordinal();
      switch(var9[var10]) {
      case 1:
         if(var8 != null && !this.mPossibleFields.contains(var3)) {
            var7 = true;
         }
         break;
      case 2:
         if(this.mRequired.contains(var3) && var8 == null) {
            var7 = true;
         }
         break;
      case 3:
         if(var8 != null) {
            if(!this.isKnownInScript(var1, var8)) {
               var7 = true;
            } else {
               var7 = false;
            }
         }
         break;
      case 4:
         if(var8 != null && this.mFormat != null && !this.mFormat.matcher(var8).matches()) {
            var7 = true;
         }
         break;
      case 5:
         if(var8 != null && this.mMatch != null && !this.mMatch.matcher(var8).lookingAt()) {
            var7 = true;
         }
         break;
      default:
         String var11 = "Unknown problem: " + var2;
         throw new RuntimeException(var11);
      }

      if(var7) {
         var5.add(var3, var2);
      }

      if(var7) {
         var6 = false;
      }

      return var6;
   }

   FieldVerifier refineVerifier(String var1) {
      FieldVerifier var2;
      if(Util.trimToNull(var1) == null) {
         var2 = new FieldVerifier(this, (AddressVerificationNodeData)null);
      } else {
         StringBuilder var3 = new StringBuilder();
         String var4 = this.mId;
         String var5 = var3.append(var4).append("/").append(var1).toString();
         AddressVerificationNodeData var6 = this.mDataSource.get(var5);
         if(var6 != null) {
            var2 = new FieldVerifier(this, var6);
         } else if(this.mLatinNames == null) {
            var2 = new FieldVerifier(this, (AddressVerificationNodeData)null);
         } else {
            int var7 = 0;

            while(true) {
               int var8 = this.mLatinNames.length;
               if(var7 >= var8) {
                  var2 = new FieldVerifier(this, (AddressVerificationNodeData)null);
                  break;
               }

               if(this.mLatinNames[var7].equalsIgnoreCase(var1)) {
                  StringBuilder var9 = new StringBuilder();
                  String var10 = this.mId;
                  StringBuilder var11 = var9.append(var10).append("/");
                  String var12 = this.mLocalNames[var7];
                  String var13 = var11.append(var12).toString();
                  var6 = this.mDataSource.get(var13);
                  if(var6 != null) {
                     var2 = new FieldVerifier(this, var6);
                     break;
                  }
               }

               ++var7;
            }
         }
      }

      return var2;
   }

   public String toString() {
      return this.mId;
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$i18n$addressinput$AddressProblemType = new int[AddressProblemType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$i18n$addressinput$AddressProblemType;
            int var1 = AddressProblemType.USING_UNUSED_FIELD.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$i18n$addressinput$AddressProblemType;
            int var3 = AddressProblemType.MISSING_REQUIRED_FIELD.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var18) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$android$i18n$addressinput$AddressProblemType;
            int var5 = AddressProblemType.UNKNOWN_VALUE.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var17) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$android$i18n$addressinput$AddressProblemType;
            int var7 = AddressProblemType.UNRECOGNIZED_FORMAT.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$android$i18n$addressinput$AddressProblemType;
            int var9 = AddressProblemType.MISMATCHING_VALUE.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var15) {
            ;
         }
      }
   }
}

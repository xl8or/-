package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.Util;
import java.util.EnumMap;
import java.util.Map;

final class LookupKey {

   private static final String DASH_DELIM = "--";
   private static final String DEFAULT_LANGUAGE = "_default";
   private static final AddressField[] HIERARCHY;
   private static final String SLASH_DELIM = "/";
   private final String mKeyString;
   private final LookupKey.KeyType mKeyType;
   private final String mLanguageCode;
   private final Map<AddressField, String> mNodes;
   private final LookupKey.ScriptType mScriptType;


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
      HIERARCHY = var0;
   }

   private LookupKey(LookupKey.Builder var1) {
      LookupKey.KeyType var2 = var1.keyType;
      this.mKeyType = var2;
      LookupKey.ScriptType var3 = var1.script;
      this.mScriptType = var3;
      Map var4 = var1.nodes;
      this.mNodes = var4;
      String var5 = var1.languageCode;
      this.mLanguageCode = var5;
      String var6 = this.getKeyString();
      this.mKeyString = var6;
   }

   // $FF: synthetic method
   LookupKey(LookupKey.Builder var1, LookupKey.1 var2) {
      this(var1);
   }

   private String getKeyString() {
      String var1 = this.mKeyType.name().toLowerCase();
      StringBuilder var2 = new StringBuilder(var1);
      LookupKey.KeyType var3 = this.mKeyType;
      LookupKey.KeyType var4 = LookupKey.KeyType.DATA;
      if(var3 == var4) {
         AddressField[] var5 = HIERARCHY;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            AddressField var8 = var5[var7];
            if(!this.mNodes.containsKey(var8)) {
               break;
            }

            AddressField var9 = AddressField.COUNTRY;
            if(var8 == var9 && this.mLanguageCode != null) {
               StringBuilder var10 = var2.append("/");
               String var11 = (String)this.mNodes.get(var8);
               StringBuilder var12 = var10.append(var11).append("--");
               String var13 = this.mLanguageCode;
               var12.append(var13);
            } else {
               StringBuilder var15 = var2.append("/");
               String var16 = (String)this.mNodes.get(var8);
               var15.append(var16);
            }
         }
      } else {
         Map var18 = this.mNodes;
         AddressField var19 = AddressField.COUNTRY;
         if(var18.containsKey(var19)) {
            StringBuilder var20 = var2.append("/");
            Map var21 = this.mNodes;
            AddressField var22 = AddressField.COUNTRY;
            String var23 = (String)var21.get(var22);
            StringBuilder var24 = var20.append(var23).append("/");
            String var25 = this.mScriptType.name().toLowerCase();
            StringBuilder var26 = var24.append(var25).append("/").append("_default");
         }
      }

      return var2.toString();
   }

   static boolean hasValidKeyPrefix(String var0) {
      LookupKey.KeyType[] var1 = LookupKey.KeyType.values();
      int var2 = var1.length;
      int var3 = 0;

      boolean var5;
      while(true) {
         if(var3 >= var2) {
            var5 = false;
            break;
         }

         String var4 = var1[var3].name().toLowerCase();
         if(var0.startsWith(var4)) {
            var5 = true;
            break;
         }

         ++var3;
      }

      return var5;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else {
         if(var1 != null) {
            Class var3 = var1.getClass();
            Class var4 = this.getClass();
            if(var3 == var4) {
               String var5 = ((LookupKey)var1).toString();
               String var6 = this.mKeyString;
               var2 = var5.equals(var6);
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   LookupKey getKeyForUpperLevelField(AddressField var1) {
      LookupKey var2 = null;
      LookupKey.KeyType var3 = this.mKeyType;
      LookupKey.KeyType var4 = LookupKey.KeyType.DATA;
      if(var3 != var4) {
         throw new RuntimeException("Only support getting parent keys for the data key type.");
      } else {
         LookupKey.Builder var5 = new LookupKey.Builder(this);
         boolean var6 = false;
         boolean var7 = false;
         AddressField[] var8 = HIERARCHY;
         int var9 = var8.length;
         int var10 = 0;

         while(true) {
            if(var10 >= var9) {
               if(var7) {
                  String var13 = this.mLanguageCode;
                  var5.languageCode = var13;
                  LookupKey.ScriptType var15 = this.mScriptType;
                  var5.script = var15;
                  var2 = var5.build();
               }
               break;
            }

            AddressField var11 = var8[var10];
            if(var6 && var5.nodes.containsKey(var11)) {
               Object var12 = var5.nodes.remove(var11);
            }

            if(var11 == var1) {
               if(!var5.nodes.containsKey(var11)) {
                  break;
               }

               var6 = true;
               var7 = true;
            }

            ++var10;
         }

         return var2;
      }
   }

   LookupKey.KeyType getKeyType() {
      return this.mKeyType;
   }

   LookupKey getParentKey() {
      LookupKey.KeyType var1 = this.mKeyType;
      LookupKey.KeyType var2 = LookupKey.KeyType.DATA;
      if(var1 != var2) {
         throw new RuntimeException("Only support getting parent keys for the data key type.");
      } else {
         Map var3 = this.mNodes;
         AddressField var4 = AddressField.COUNTRY;
         LookupKey var5;
         if(!var3.containsKey(var4)) {
            var5 = null;
         } else {
            LookupKey.Builder var6 = new LookupKey.Builder(this);
            AddressField var7 = AddressField.COUNTRY;
            AddressField[] var8 = HIERARCHY;
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               AddressField var11 = var8[var10];
               if(!this.mNodes.containsKey(var11)) {
                  break;
               }

               var7 = var11;
            }

            Object var12 = var6.nodes.remove(var7);
            var5 = var6.build();
         }

         return var5;
      }
   }

   String getValueForUpperLevelField(AddressField var1) {
      LookupKey var2 = this.getKeyForUpperLevelField(var1);
      String var7;
      if(var2 != null) {
         String var3 = var2.toString();
         int var4 = var3.lastIndexOf("/");
         if(var4 > 0) {
            int var5 = var3.length();
            if(var4 != var5) {
               int var6 = var4 + 1;
               var7 = var3.substring(var6);
               return var7;
            }
         }
      }

      var7 = "";
      return var7;
   }

   public int hashCode() {
      return this.mKeyString.hashCode();
   }

   public String toString() {
      return this.mKeyString;
   }

   // $FF: synthetic class
   static class 1 {
   }

   static enum KeyType {

      // $FF: synthetic field
      private static final LookupKey.KeyType[] $VALUES;
      DATA("DATA", 0),
      EXAMPLES("EXAMPLES", 1);


      static {
         LookupKey.KeyType[] var0 = new LookupKey.KeyType[2];
         LookupKey.KeyType var1 = DATA;
         var0[0] = var1;
         LookupKey.KeyType var2 = EXAMPLES;
         var0[1] = var2;
         $VALUES = var0;
      }

      private KeyType(String var1, int var2) {}
   }

   static enum ScriptType {

      // $FF: synthetic field
      private static final LookupKey.ScriptType[] $VALUES;
      LATIN("LATIN", 0),
      LOCAL("LOCAL", 1);


      static {
         LookupKey.ScriptType[] var0 = new LookupKey.ScriptType[2];
         LookupKey.ScriptType var1 = LATIN;
         var0[0] = var1;
         LookupKey.ScriptType var2 = LOCAL;
         var0[1] = var2;
         $VALUES = var0;
      }

      private ScriptType(String var1, int var2) {}
   }

   static class Builder {

      private LookupKey.KeyType keyType;
      private String languageCode;
      private Map<AddressField, String> nodes;
      private LookupKey.ScriptType script;


      Builder(LookupKey.KeyType var1) {
         LookupKey.ScriptType var2 = LookupKey.ScriptType.LOCAL;
         this.script = var2;
         EnumMap var3 = new EnumMap(AddressField.class);
         this.nodes = var3;
         this.keyType = var1;
      }

      Builder(LookupKey var1) {
         LookupKey.ScriptType var2 = LookupKey.ScriptType.LOCAL;
         this.script = var2;
         EnumMap var3 = new EnumMap(AddressField.class);
         this.nodes = var3;
         LookupKey.KeyType var4 = var1.mKeyType;
         this.keyType = var4;
         LookupKey.ScriptType var5 = var1.mScriptType;
         this.script = var5;
         String var6 = var1.mLanguageCode;
         this.languageCode = var6;
         AddressField[] var7 = LookupKey.HIERARCHY;
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            AddressField var10 = var7[var9];
            if(!var1.mNodes.containsKey(var10)) {
               return;
            }

            Map var11 = this.nodes;
            Object var12 = var1.mNodes.get(var10);
            var11.put(var10, var12);
         }

      }

      Builder(String var1) {
         LookupKey.ScriptType var2 = LookupKey.ScriptType.LOCAL;
         this.script = var2;
         EnumMap var3 = new EnumMap(AddressField.class);
         this.nodes = var3;
         String[] var4 = var1.split("/");
         String var5 = var4[0];
         String var6 = LookupKey.KeyType.DATA.name().toLowerCase();
         if(!var5.equals(var6)) {
            String var7 = var4[0];
            String var8 = LookupKey.KeyType.EXAMPLES.name().toLowerCase();
            if(!var7.equals(var8)) {
               StringBuilder var9 = (new StringBuilder()).append("Wrong key type: ");
               String var10 = var4[0];
               String var11 = var9.append(var10).toString();
               throw new RuntimeException(var11);
            }
         }

         int var12 = var4.length;
         int var13 = LookupKey.HIERARCHY.length + 1;
         if(var12 > var13) {
            String var14 = "input key \'" + var1 + "\' deeper than supported hierarchy";
            throw new RuntimeException(var14);
         } else if(!var4[0].equals("data")) {
            if(var4[0].equals("examples")) {
               LookupKey.KeyType var29 = LookupKey.KeyType.EXAMPLES;
               this.keyType = var29;
               if(var4.length > 1) {
                  Map var30 = this.nodes;
                  AddressField var31 = AddressField.COUNTRY;
                  String var32 = var4[1];
                  var30.put(var31, var32);
               }

               if(var4.length > 2) {
                  String var34 = var4[2];
                  if(var34.equals("local")) {
                     LookupKey.ScriptType var35 = LookupKey.ScriptType.LOCAL;
                     this.script = var35;
                  } else {
                     if(!var34.equals("latin")) {
                        throw new RuntimeException("Script type has to be either latin or local.");
                     }

                     LookupKey.ScriptType var37 = LookupKey.ScriptType.LATIN;
                     this.script = var37;
                  }
               }

               if(var4.length > 3) {
                  if(!var4[3].equals("_default")) {
                     String var36 = var4[3];
                     this.languageCode = var36;
                  }
               }
            }
         } else {
            LookupKey.KeyType var15 = LookupKey.KeyType.DATA;
            this.keyType = var15;
            String var16;
            if(var4.length > 1) {
               var16 = Util.trimToNull(var4[1]);
               if(var16.contains("--")) {
                  String[] var17 = var16.split("--");
                  if(var17.length != 2) {
                     throw new RuntimeException("Wrong format: Substring should be country code--language code");
                  }

                  var16 = var17[0];
                  String var18 = var17[1];
                  this.languageCode = var18;
               }

               Map var19 = this.nodes;
               AddressField var20 = LookupKey.HIERARCHY[0];
               var19.put(var20, var16);
            }

            if(var4.length > 2) {
               int var22 = 2;

               while(true) {
                  int var23 = var4.length;
                  if(var22 >= var23) {
                     return;
                  }

                  var16 = Util.trimToNull(var4[var22]);
                  if(var16 == null) {
                     return;
                  }

                  Map var24 = this.nodes;
                  AddressField[] var25 = LookupKey.HIERARCHY;
                  int var26 = var22 + -1;
                  AddressField var27 = var25[var26];
                  var24.put(var27, var16);
                  ++var22;
               }
            }
         }
      }

      LookupKey build() {
         return new LookupKey(this, (LookupKey.1)null);
      }

      LookupKey.Builder setAddressData(AddressData var1) {
         String var2 = var1.getLanguageCode();
         this.languageCode = var2;
         if(this.languageCode != null && Util.isExplicitLatinScript(this.languageCode)) {
            LookupKey.ScriptType var3 = LookupKey.ScriptType.LATIN;
            this.script = var3;
         }

         if(var1.getPostalCountry() != null) {
            Map var4 = this.nodes;
            AddressField var5 = AddressField.COUNTRY;
            String var6 = var1.getPostalCountry();
            var4.put(var5, var6);
            if(var1.getAdministrativeArea() != null) {
               Map var8 = this.nodes;
               AddressField var9 = AddressField.ADMIN_AREA;
               String var10 = var1.getAdministrativeArea();
               var8.put(var9, var10);
               if(var1.getLocality() != null) {
                  Map var12 = this.nodes;
                  AddressField var13 = AddressField.LOCALITY;
                  String var14 = var1.getLocality();
                  var12.put(var13, var14);
                  if(var1.getDependentLocality() != null) {
                     Map var16 = this.nodes;
                     AddressField var17 = AddressField.DEPENDENT_LOCALITY;
                     String var18 = var1.getDependentLocality();
                     var16.put(var17, var18);
                  }
               }
            }
         }

         return this;
      }

      LookupKey.Builder setLanguageCode(String var1) {
         this.languageCode = var1;
         return this;
      }
   }
}

package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressDataKey;
import com.android.i18n.addressinput.AddressVerificationNodeData;
import com.android.i18n.addressinput.DataSource;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AddressVerificationData implements DataSource {

   private static final Pattern KEY_VALUES_PATTERN = Pattern.compile("\"([^\"]+)\":\"([^\"]*)\"");
   private static final Pattern SEPARATOR_PATTERN = Pattern.compile("\",\"");
   private final Map<String, String> mPropertiesMap;


   AddressVerificationData(Map<String, String> var1) {
      this.mPropertiesMap = var1;
   }

   private boolean isValidKey(String var1) {
      return var1.startsWith("data");
   }

   AddressVerificationNodeData createNodeData(String var1) {
      int var2 = var1.length() + -1;
      String var3 = var1.substring(1, var2);
      EnumMap var4 = new EnumMap(AddressDataKey.class);
      Pattern var5 = SEPARATOR_PATTERN;
      Matcher var7 = var5.matcher(var3);
      int var8 = 0;

      while(true) {
         int var9 = var3.length();
         if(var8 >= var9) {
            return new AddressVerificationNodeData(var4);
         }

         String var11;
         if(var7.find()) {
            int var10 = var7.start() + 1;
            var11 = var3.substring(var8, var10);
            var8 = var7.start() + 2;
         } else {
            var11 = var3.substring(var8);
            int var20 = var3.length();
         }

         Matcher var12 = KEY_VALUES_PATTERN.matcher(var11);
         if(!var12.matches()) {
            StringBuilder var24 = (new StringBuilder()).append("could not match \'").append(var11).append("\' in \'");
            String var26 = var24.append(var3).append("\'").toString();
            throw new RuntimeException(var26);
         }

         String var13 = var12.group(2);
         if(var13.length() > 0) {
            char[] var14 = var12.group(2).toCharArray();
            int var15 = 1;
            int var16 = var15;

            while(true) {
               int var17 = var14.length;
               if(var16 >= var17) {
                  var13 = new String(var14, 0, var15);
                  break;
               }

               label32: {
                  char var18 = var14[var16];
                  if(var18 == 92) {
                     int var19 = var15 + -1;
                     if(var14[var19] == 92) {
                        break label32;
                     }
                  }

                  int var21 = var15 + 1;
                  var14[var15] = var18;
                  var15 = var21;
               }

               ++var16;
            }
         }

         AddressDataKey var22 = AddressDataKey.get(var12.group(1));
         if(var22 != null) {
            var4.put(var22, var13);
         }
      }
   }

   public AddressVerificationNodeData get(String var1) {
      String var2 = (String)this.mPropertiesMap.get(var1);
      AddressVerificationNodeData var3;
      if(var2 != null && this.isValidKey(var1)) {
         var3 = this.createNodeData(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public AddressVerificationNodeData getDefaultData(String var1) {
      if(var1.split("/").length > 1) {
         String[] var2 = var1.split("/");
         StringBuilder var3 = new StringBuilder();
         String var4 = var2[0];
         StringBuilder var5 = var3.append(var4).append("/");
         String var6 = var2[1];
         var1 = var5.append(var6).toString();
      }

      AddressVerificationNodeData var7 = this.get(var1);
      if(var7 == null) {
         String var8 = "failed to get default data with key " + var1;
         throw new RuntimeException(var8);
      } else {
         return var7;
      }
   }

   Set<String> keys() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.mPropertiesMap.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         if(this.isValidKey(var3)) {
            var1.add(var3);
         }
      }

      return Collections.unmodifiableSet(var1);
   }
}

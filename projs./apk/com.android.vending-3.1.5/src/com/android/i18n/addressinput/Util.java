package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Util {

   private static final String LATIN_SCRIPT = "LATN";
   private static final Map<String, String> nonLatinLocalLanguageCountries = new HashMap();


   static {
      Object var0 = nonLatinLocalLanguageCountries.put("AM", "hy");
      Object var1 = nonLatinLocalLanguageCountries.put("CN", "zh");
      Object var2 = nonLatinLocalLanguageCountries.put("HK", "zh");
      Object var3 = nonLatinLocalLanguageCountries.put("JP", "ja");
      Object var4 = nonLatinLocalLanguageCountries.put("KP", "ko");
      Object var5 = nonLatinLocalLanguageCountries.put("KR", "ko");
      Object var6 = nonLatinLocalLanguageCountries.put("MO", "zh");
      Object var7 = nonLatinLocalLanguageCountries.put("TH", "th");
      Object var8 = nonLatinLocalLanguageCountries.put("TW", "zh");
      Object var9 = nonLatinLocalLanguageCountries.put("VN", "vi");
   }

   private Util() {}

   static Map<String, String> buildNameToKeyMap(String[] var0, String[] var1, String[] var2) {
      HashMap var3;
      if(var0 == null) {
         var3 = null;
      } else {
         var3 = new HashMap();
         int var4 = var0.length;
         String[] var5 = var0;
         int var6 = var0.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];
            String var9 = var8.toLowerCase();
            var3.put(var9, var8);
         }

         int var16;
         if(var1 != null) {
            if(var1.length > var4) {
               StringBuilder var11 = (new StringBuilder()).append("names length (");
               int var12 = var1.length;
               StringBuilder var13 = var11.append(var12).append(") is greater than keys length (");
               int var14 = var0.length;
               String var15 = var13.append(var14).append(")").toString();
               throw new IllegalStateException(var15);
            }

            for(var16 = 0; var16 < var4; ++var16) {
               int var17 = var1.length;
               if(var16 < var17 && var1[var16].length() > 0) {
                  String var18 = var1[var16].toLowerCase();
                  String var19 = var0[var16];
                  var3.put(var18, var19);
               }
            }
         }

         if(var2 != null) {
            if(var2.length > var4) {
               StringBuilder var21 = (new StringBuilder()).append("lnames length (");
               int var22 = var2.length;
               StringBuilder var23 = var21.append(var22).append(") is greater than keys length (");
               int var24 = var0.length;
               String var25 = var23.append(var24).append(")").toString();
               throw new IllegalStateException(var25);
            }

            for(var16 = 0; var16 < var4; ++var16) {
               int var26 = var2.length;
               if(var16 < var26 && var2[var16].length() > 0) {
                  String var27 = var2[var16].toLowerCase();
                  String var28 = var0[var16];
                  var3.put(var27, var28);
               }
            }
         }
      }

      return var3;
   }

   static void checkNotNull(Object var0) throws NullPointerException {
      checkNotNull(var0, "This object should not be null.");
   }

   static void checkNotNull(Object var0, String var1) throws NullPointerException {
      if(var0 == null) {
         throw new NullPointerException(var1);
      }
   }

   static String getLanguageSubtag(String var0) {
      Matcher var1 = Pattern.compile("(\\w{2,3})(?:[-_]\\w{4})?(?:[-_]\\w{2})?").matcher(var0);
      String var2;
      if(var1.matches()) {
         var2 = var1.group(1).toLowerCase();
      } else {
         var2 = "und";
      }

      return var2;
   }

   static String getWidgetCompatibleLanguageCode(Locale var0, String var1) {
      String var2 = var1.toUpperCase();
      String var10;
      if(nonLatinLocalLanguageCountries.containsKey(var2)) {
         String var3 = var0.getLanguage();
         Object var4 = nonLatinLocalLanguageCountries.get(var2);
         if(!var3.equals(var4)) {
            StringBuilder var5 = new StringBuilder(var3);
            StringBuilder var6 = var5.append("_latn");
            if(var0.getCountry().length() > 0) {
               StringBuilder var7 = var5.append("_");
               String var8 = var0.getCountry();
               var5.append(var8);
            }

            var10 = var5.toString();
            return var10;
         }
      }

      var10 = var0.toString();
      return var10;
   }

   static boolean isExplicitLatinScript(String var0) {
      boolean var1 = true;
      String var2 = var0.toUpperCase();
      Matcher var3 = Pattern.compile("\\w{2,3}[-_](\\w{4})").matcher(var2);
      if(!var3.lookingAt() || !var3.group(1).equals("LATN")) {
         var1 = false;
      }

      return var1;
   }

   static String joinAndSkipNulls(String var0, String ... var1) {
      StringBuilder var2 = null;
      String[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         if(var6 != null) {
            var6 = var6.trim();
            if(var6.length() > 0) {
               if(var2 == null) {
                  var2 = new StringBuilder(var6);
               } else {
                  StringBuilder var7 = var2.append(var0).append(var6);
               }
            }
         }
      }

      String var8;
      if(var2 == null) {
         var8 = null;
      } else {
         var8 = var2.toString();
      }

      return var8;
   }

   static String trimToNull(String var0) {
      String var1 = null;
      if(var0 != null) {
         String var2 = var0.trim();
         if(var2.length() == 0) {
            var2 = null;
         }

         var1 = var2;
      }

      return var1;
   }
}

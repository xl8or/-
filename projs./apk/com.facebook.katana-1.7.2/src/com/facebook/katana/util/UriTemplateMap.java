package com.facebook.katana.util;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.katana.util.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriTemplateMap<T extends Object> {

   private static final Pattern QUERY_REGEX = Pattern.compile("&?([^=]+)=([^&]+)");
   private static final Pattern QUERY_TEMPLATE_REGEX = Pattern.compile("\\{([#!]?)([^ }]+)(?: ([^}]+))?\\}");
   private static final Pattern SHP_TEMPLATE_REGEX = Pattern.compile("\\{([#]?)([^ }]+)\\}");
   private final List<UriTemplateMap.MapEntry> mEntries;


   public UriTemplateMap() {
      ArrayList var1 = new ArrayList();
      this.mEntries = var1;
   }

   private static String[] getComponents(String var0) {
      int var1 = 0;

      while(true) {
         int var2 = var0.length();
         String[] var3;
         if(var1 >= var2) {
            var3 = new String[]{var0, ""};
            return var3;
         }

         switch(var0.charAt(var1)) {
         case 63:
            if(true) {
               var3 = new String[2];
               String var4 = var0.substring(0, var1);
               var3[0] = var4;
               int var5 = var1 + 1;
               String var6 = var0.substring(var5);
               var3[1] = var6;
               return var3;
            }
         case 123:
         case 125:
         default:
            ++var1;
         }
      }
   }

   private static Map<String, String> getQueryAsMap(String var0) {
      HashMap var1 = new HashMap();
      Matcher var2 = QUERY_REGEX.matcher(var0);

      while(var2.find()) {
         String var3 = Uri.decode(var2.group(1));
         String var4 = Uri.decode(var2.group(2));
         var1.put(var3, var4);
      }

      return var1;
   }

   public UriTemplateMap.UriMatch<T> get(String var1) throws UriTemplateMap.InvalidUriException {
      if(var1 == null) {
         throw new UriTemplateMap.InvalidUriException("Key may not be null");
      } else {
         Iterator var2 = this.mEntries.iterator();

         UriTemplateMap.UriMatch var4;
         while(true) {
            if(var2.hasNext()) {
               UriTemplateMap.UriMatch var3 = ((UriTemplateMap.MapEntry)var2.next()).match(var1);
               if(var3 == null) {
                  continue;
               }

               var4 = var3;
               break;
            }

            var4 = null;
            break;
         }

         return var4;
      }
   }

   public void put(String var1, T var2) throws UriTemplateMap.InvalidUriTemplateException {
      if(var1 == null) {
         throw new UriTemplateMap.InvalidUriTemplateException("Key template may not be null");
      } else {
         List var3 = this.mEntries;
         UriTemplateMap.MapEntry var4 = new UriTemplateMap.MapEntry(var1, var2);
         var3.add(var4);
      }
   }

   public static class InvalidUriException extends Exception {

      private static final long serialVersionUID = -9103998986876759379L;


      public InvalidUriException(String var1) {
         super(var1);
      }
   }

   private static enum TemplateValueType {

      // $FF: synthetic field
      private static final UriTemplateMap.TemplateValueType[] $VALUES;
      BOOLEAN("BOOLEAN", 2),
      LONG("LONG", 1),
      STRING("STRING", 0);


      static {
         UriTemplateMap.TemplateValueType[] var0 = new UriTemplateMap.TemplateValueType[3];
         UriTemplateMap.TemplateValueType var1 = STRING;
         var0[0] = var1;
         UriTemplateMap.TemplateValueType var2 = LONG;
         var0[1] = var2;
         UriTemplateMap.TemplateValueType var3 = BOOLEAN;
         var0[2] = var3;
         $VALUES = var0;
      }

      private TemplateValueType(String var1, int var2) {}
   }

   private class MapEntry {

      private final Map<String, UriTemplateMap.QueryParameter> mQueryParameters;
      private final Pattern mSchemeHostPathRegex;
      List<Tuple<Class<?>, String>> mTemplateParams;
      private final T mValue;


      MapEntry(String var2, Object var3) throws UriTemplateMap.InvalidUriTemplateException {
         HashMap var5 = new HashMap();
         this.mQueryParameters = var5;
         ArrayList var6 = new ArrayList();
         this.mTemplateParams = var6;
         this.mValue = var3;
         String[] var8 = UriTemplateMap.getComponents(var2);
         String var9 = var8[0];
         String var10 = var8[1];
         Pattern var11 = UriTemplateMap.SHP_TEMPLATE_REGEX;
         Matcher var13 = var11.matcher(var9);
         String var14 = var9;

         HashSet var15;
         String var18;
         String var34;
         for(var15 = new HashSet(); var13.find(); var14 = var14.replace(var18, var34)) {
            byte var17 = 0;
            var18 = var13.group(var17);
            byte var20 = 1;
            boolean var21 = var13.group(var20).equals("#");
            Class var22;
            if(var21) {
               var22 = Long.class;
            } else {
               var22 = String.class;
            }

            byte var24 = 2;
            String var25 = var13.group(var24);
            List var26 = this.mTemplateParams;
            Tuple var27 = new Tuple(var22, var25);
            var26.add(var27);
            if(!var15.add(var25)) {
               throw new UriTemplateMap.InvalidUriTemplateException("Duplicate template key");
            }

            if(var21) {
               var34 = "(-?[0-9]+)";
            } else {
               var34 = "([^/]+)";
            }
         }

         if(var9 == var14) {
            String var41 = "[A-Za-z]+://[A-Za-z]+";
            if(var9.matches(var41)) {
               StringBuilder var42 = new StringBuilder();
               var14 = var42.append(var14).append("[/]?").toString();
            }
         }

         Pattern var44 = Pattern.compile(var14);
         this.mSchemeHostPathRegex = var44;

         UriTemplateMap.QueryParameter var68;
         Map var67;
         Object var77;
         String var61;
         for(Iterator var45 = UriTemplateMap.getQueryAsMap(var10).entrySet().iterator(); var45.hasNext(); var77 = var67.put(var61, var68)) {
            Entry var46 = (Entry)var45.next();
            Pattern var47 = UriTemplateMap.QUERY_TEMPLATE_REGEX;
            CharSequence var48 = (CharSequence)var46.getValue();
            var13 = var47.matcher(var48);
            if(!var13.matches()) {
               throw new UriTemplateMap.InvalidUriTemplateException("Query parameter does not match templating syntax");
            }

            UriTemplateMap.TemplateValueType var51 = UriTemplateMap.TemplateValueType.STRING;
            byte var53 = 1;
            String var54 = var13.group(var53);
            if("#".equals(var54)) {
               var51 = UriTemplateMap.TemplateValueType.LONG;
            } else {
               byte var65 = 1;
               String var66 = var13.group(var65);
               if("!".equals(var66)) {
                  var51 = UriTemplateMap.TemplateValueType.BOOLEAN;
               }
            }

            byte var56 = 2;
            String var57 = var13.group(var56);
            byte var59 = 3;
            String var60 = var13.group(var59);
            var61 = (String)var46.getKey();
            if(!var15.add(var61)) {
               throw new UriTemplateMap.InvalidUriTemplateException("Duplicate template key");
            }

            var67 = this.mQueryParameters;
            var68 = UriTemplateMap.this.new QueryParameter(var57, var51, var60);
         }

      }

      UriTemplateMap.UriMatch<T> match(String var1) throws UriTemplateMap.InvalidUriException {
         String[] var2 = UriTemplateMap.getComponents(var1);
         String var3 = var2[0];
         String var4 = var2[1];
         Pattern var5 = this.mSchemeHostPathRegex;
         Matcher var7 = var5.matcher(var3);
         UriTemplateMap.UriMatch var8;
         if(!var7.matches()) {
            var8 = null;
         } else {
            Bundle var9 = new Bundle();
            int var10 = 0;

            while(true) {
               int var11 = this.mTemplateParams.size();
               if(var10 >= var11) {
                  Map var37 = UriTemplateMap.getQueryAsMap(var4);
                  Iterator var38 = this.mQueryParameters.entrySet().iterator();

                  while(var38.hasNext()) {
                     Entry var39 = (Entry)var38.next();
                     String var40 = (String)var39.getKey();
                     UriTemplateMap.QueryParameter var41 = (UriTemplateMap.QueryParameter)var39.getValue();
                     String var42 = var41.mFieldName;
                     if(var41.mRequired && !var37.containsKey(var40)) {
                        var8 = null;
                        return var8;
                     }

                     String var43;
                     if(var37.containsKey(var40)) {
                        var43 = (String)var37.get(var40);
                     } else {
                        var43 = var41.mDefaultValue;
                     }

                     UriTemplateMap.TemplateValueType var44 = var41.mType;
                     UriTemplateMap.TemplateValueType var45 = UriTemplateMap.TemplateValueType.LONG;
                     if(var44 == var45) {
                        long var48 = Long.parseLong(var43);
                        var9.putLong(var42, var48);
                     } else {
                        UriTemplateMap.TemplateValueType var54 = var41.mType;
                        UriTemplateMap.TemplateValueType var55 = UriTemplateMap.TemplateValueType.BOOLEAN;
                        if(var54 == var55) {
                           boolean var58 = Boolean.valueOf(var43).booleanValue();
                           var9.putBoolean(var42, var58);
                        } else {
                           var9.putString(var42, var43);
                        }
                     }
                  }

                  var8 = new UriTemplateMap.UriMatch;
                  Object var65 = this.mValue;
                  var8.<init>(var65, var9);
                  break;
               }

               List var14 = this.mTemplateParams;
               Tuple var16 = (Tuple)var14.get(var10);
               Object var17 = var16.d0;
               Class var18 = Long.class;
               if(var17 == var18) {
                  String var19 = (String)var16.d1;
                  int var20 = var10 + 1;
                  long var23 = Long.parseLong(var7.group(var20));
                  var9.putLong(var19, var23);
               } else {
                  String var29 = (String)var16.d1;
                  int var30 = var10 + 1;
                  String var33 = var7.group(var30);
                  var9.putString(var29, var33);
               }

               ++var10;
            }
         }

         return var8;
      }
   }

   public static class UriMatch<X extends Object> {

      public final Bundle parameters;
      public final X value;


      public UriMatch(X var1, Bundle var2) {
         this.value = var1;
         this.parameters = var2;
      }
   }

   private class QueryParameter {

      String mDefaultValue;
      String mFieldName;
      boolean mRequired;
      UriTemplateMap.TemplateValueType mType;


      QueryParameter(String var2, UriTemplateMap.TemplateValueType var3, String var4) {
         this.mFieldName = var2;
         this.mType = var3;
         this.mDefaultValue = var4;
         byte var5;
         if(var4 == null) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         this.mRequired = (boolean)var5;
      }
   }

   public static class InvalidUriTemplateException extends RuntimeException {

      private static final long serialVersionUID = 12098347109238471L;


      public InvalidUriTemplateException(String var1) {
         super(var1);
      }
   }
}

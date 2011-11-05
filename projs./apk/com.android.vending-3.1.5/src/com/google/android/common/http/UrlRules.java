package com.google.android.common.http;

import android.content.ContentResolver;
import android.util.Log;
import com.google.android.gsf.Gservices;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlRules {

   private static final Pattern PATTERN_SPACE_PLUS = Pattern.compile(" +");
   private static final Pattern RULE_PATTERN = Pattern.compile("\\W");
   public static final String TAG = "UrlRules";
   private static UrlRules sCachedRules;
   private static Object sCachedVersionToken;
   private final Pattern mPattern;
   private final UrlRules.Rule[] mRules;


   static {
      UrlRules.Rule[] var0 = new UrlRules.Rule[0];
      sCachedRules = new UrlRules(var0);
   }

   public UrlRules(UrlRules.Rule[] var1) {
      Arrays.sort(var1);
      StringBuilder var2 = new StringBuilder("(");
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            Pattern var10 = Pattern.compile(var2.append(")").toString());
            this.mPattern = var10;
            this.mRules = var1;
            return;
         }

         if(var3 > 0) {
            StringBuilder var5 = var2.append(")|(");
         }

         Pattern var6 = RULE_PATTERN;
         String var7 = var1[var3].mPrefix;
         String var8 = var6.matcher(var7).replaceAll("\\\\$0");
         var2.append(var8);
         ++var3;
      }
   }

   public static UrlRules getRules(ContentResolver var0) {
      synchronized(UrlRules.class){}

      UrlRules var5;
      try {
         Object var1 = Gservices.getVersionToken(var0);
         Object var2 = sCachedVersionToken;
         if(var1 == var2) {
            if(Log.isLoggable("UrlRules", 2)) {
               String var3 = "Using cached rules, versionToken: " + var1;
               int var4 = Log.v("UrlRules", var3);
            }

            var5 = sCachedRules;
         } else {
            if(Log.isLoggable("UrlRules", 2)) {
               int var6 = Log.v("UrlRules", "Scanning for Gservices \"url:*\" rules");
            }

            String[] var7 = new String[]{"url:"};
            Map var8 = Gservices.getStringsByPrefix(var0, var7);
            ArrayList var9 = new ArrayList();
            Iterator var10 = var8.entrySet().iterator();

            while(var10.hasNext()) {
               Entry var11 = (Entry)var10.next();

               try {
                  String var12 = ((String)var11.getKey()).substring(4);
                  String var13 = (String)var11.getValue();
                  if(var13 != null && var13.length() != 0) {
                     if(Log.isLoggable("UrlRules", 2)) {
                        String var14 = "  Rule " + var12 + ": " + var13;
                        int var15 = Log.v("UrlRules", var14);
                     }

                     UrlRules.Rule var16 = new UrlRules.Rule(var12, var13);
                     var9.add(var16);
                  }
               } catch (UrlRules.RuleFormatException var27) {
                  int var19 = Log.e("UrlRules", "Invalid rule from Gservices", var27);
               }
            }

            UrlRules.Rule[] var21 = new UrlRules.Rule[var9.size()];
            UrlRules.Rule[] var22 = (UrlRules.Rule[])var9.toArray(var21);
            sCachedRules = new UrlRules(var22);
            sCachedVersionToken = var1;
            if(Log.isLoggable("UrlRules", 2)) {
               String var23 = "New rules stored, versionToken: " + var1;
               int var24 = Log.v("UrlRules", var23);
            }

            var5 = sCachedRules;
         }
      } finally {
         ;
      }

      return var5;
   }

   public UrlRules.Rule matchRule(String var1) {
      Matcher var2 = this.mPattern.matcher(var1);
      UrlRules.Rule var6;
      if(var2.lookingAt()) {
         int var3 = 0;

         while(true) {
            int var4 = this.mRules.length;
            if(var3 >= var4) {
               break;
            }

            int var5 = var3 + 1;
            if(var2.group(var5) != null) {
               var6 = this.mRules[var3];
               return var6;
            }

            ++var3;
         }
      }

      var6 = UrlRules.Rule.DEFAULT;
      return var6;
   }

   public static class RuleFormatException extends Exception {

      public RuleFormatException(String var1) {
         super(var1);
      }
   }

   public static class Rule implements Comparable {

      public static final UrlRules.Rule DEFAULT = new UrlRules.Rule();
      public final boolean mBlock;
      public final String mName;
      public final String mPrefix;
      public final String mRewrite;


      private Rule() {
         this.mName = "DEFAULT";
         this.mPrefix = "";
         this.mRewrite = null;
         this.mBlock = (boolean)0;
      }

      public Rule(String var1, String var2) throws UrlRules.RuleFormatException {
         this.mName = var1;
         String[] var3 = UrlRules.PATTERN_SPACE_PLUS.split(var2);
         if(var3.length == 0) {
            throw new UrlRules.RuleFormatException("Empty rule");
         } else {
            String var4 = var3[0];
            this.mPrefix = var4;
            String var5 = null;
            byte var6 = 0;
            int var7 = 1;

            while(true) {
               int var8 = var3.length;
               if(var7 >= var8) {
                  this.mRewrite = var5;
                  this.mBlock = (boolean)var6;
                  return;
               }

               String var9 = var3[var7].toLowerCase();
               if(var9.equals("rewrite")) {
                  int var10 = var7 + 1;
                  int var11 = var3.length;
                  if(var10 < var11) {
                     int var12 = var7 + 1;
                     var5 = var3[var12];
                     var7 += 2;
                     continue;
                  }
               }

               if(!var9.equals("block")) {
                  String var13 = "Illegal rule: " + var2;
                  throw new UrlRules.RuleFormatException(var13);
               }

               var6 = 1;
               ++var7;
            }
         }
      }

      public String apply(String var1) {
         if(this.mBlock) {
            var1 = null;
         } else if(this.mRewrite != null) {
            StringBuilder var2 = new StringBuilder();
            String var3 = this.mRewrite;
            StringBuilder var4 = var2.append(var3);
            int var5 = this.mPrefix.length();
            String var6 = var1.substring(var5);
            var1 = var4.append(var6).toString();
         }

         return var1;
      }

      public int compareTo(Object var1) {
         String var2 = ((UrlRules.Rule)var1).mPrefix;
         String var3 = this.mPrefix;
         return var2.compareTo(var3);
      }
   }
}

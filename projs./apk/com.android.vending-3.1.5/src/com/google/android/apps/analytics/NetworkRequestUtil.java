package com.google.android.apps.analytics;

import android.util.Log;
import com.google.android.apps.analytics.AnalyticsParameterEncoder;
import com.google.android.apps.analytics.CustomVariable;
import com.google.android.apps.analytics.CustomVariableBuffer;
import com.google.android.apps.analytics.Event;
import java.util.Locale;

class NetworkRequestUtil {

   private static final String FAKE_DOMAIN_HASH = "999";
   private static final String GOOGLE_ANALYTICS_GIF_PATH = "/__utm.gif";
   private static final int X10_PROJECT_NAMES = 8;
   private static final int X10_PROJECT_SCOPES = 11;
   private static final int X10_PROJECT_VALUES = 9;


   NetworkRequestUtil() {}

   public static String constructEventRequestPath(Event var0, String var1) {
      Locale var2 = Locale.getDefault();
      StringBuilder var3 = new StringBuilder();
      StringBuilder var4 = new StringBuilder();
      Object[] var5 = new Object[2];
      String var6 = encode(var0.category);
      var5[0] = var6;
      String var7 = encode(var0.action);
      var5[1] = var7;
      String var8 = String.format("5(%s*%s", var5);
      var4.append(var8);
      if(var0.label != null) {
         StringBuilder var10 = var4.append("*");
         String var11 = encode(var0.label);
         var10.append(var11);
      }

      StringBuilder var13 = var4.append(")");
      if(var0.value > -1) {
         Object[] var14 = new Object[1];
         Integer var15 = Integer.valueOf(var0.value);
         var14[0] = var15;
         String var16 = String.format("(%d)", var14);
         var4.append(var16);
      }

      String var18 = getCustomVariableParams(var0);
      var4.append(var18);
      StringBuilder var20 = var3.append("/__utm.gif");
      StringBuilder var21 = var3.append("?utmwv=4.5ma");
      StringBuilder var22 = var3.append("&utmn=");
      int var23 = var0.randomVal;
      var22.append(var23);
      StringBuilder var25 = var3.append("&utmt=event");
      StringBuilder var26 = var3.append("&utme=");
      String var27 = var4.toString();
      var26.append(var27);
      StringBuilder var29 = var3.append("&utmcs=UTF-8");
      Object[] var30 = new Object[2];
      Integer var31 = Integer.valueOf(var0.screenWidth);
      var30[0] = var31;
      Integer var32 = Integer.valueOf(var0.screenHeight);
      var30[1] = var32;
      String var33 = String.format("&utmsr=%dx%d", var30);
      var3.append(var33);
      Object[] var35 = new Object[2];
      String var36 = var2.getLanguage();
      var35[0] = var36;
      String var37 = var2.getCountry();
      var35[1] = var37;
      String var38 = String.format("&utmul=%s-%s", var35);
      var3.append(var38);
      StringBuilder var40 = var3.append("&utmac=");
      String var41 = var0.accountId;
      var40.append(var41);
      StringBuilder var43 = var3.append("&utmcc=");
      String var44 = getEscapedCookieString(var0, var1);
      var43.append(var44);
      String var46 = var3.toString();
      int var47 = Log.d("NetworkRequestUtil/ConstructEventRequestPath", var46);
      return var3.toString();
   }

   public static String constructPageviewRequestPath(Event var0, String var1) {
      String var2 = "";
      if(var0.action != null) {
         var2 = var0.action;
      }

      if(!var2.startsWith("/")) {
         var2 = "/" + var2;
      }

      String var3 = encode(var2);
      String var4 = getCustomVariableParams(var0);
      Locale var5 = Locale.getDefault();
      StringBuilder var6 = new StringBuilder();
      StringBuilder var7 = var6.append("/__utm.gif");
      StringBuilder var8 = var6.append("?utmwv=4.5ma");
      StringBuilder var9 = var6.append("&utmn=");
      int var10 = var0.randomVal;
      var9.append(var10);
      if(var4.length() > 0) {
         StringBuilder var12 = var6.append("&utme=").append(var4);
      }

      StringBuilder var13 = var6.append("&utmcs=UTF-8");
      Object[] var14 = new Object[2];
      Integer var15 = Integer.valueOf(var0.screenWidth);
      var14[0] = var15;
      Integer var16 = Integer.valueOf(var0.screenHeight);
      var14[1] = var16;
      String var17 = String.format("&utmsr=%dx%d", var14);
      var6.append(var17);
      Object[] var19 = new Object[2];
      String var20 = var5.getLanguage();
      var19[0] = var20;
      String var21 = var5.getCountry();
      var19[1] = var21;
      String var22 = String.format("&utmul=%s-%s", var19);
      var6.append(var22);
      StringBuilder var24 = var6.append("&utmp=").append(var3);
      StringBuilder var25 = var6.append("&utmac=");
      String var26 = var0.accountId;
      var25.append(var26);
      StringBuilder var28 = var6.append("&utmcc=");
      String var29 = getEscapedCookieString(var0, var1);
      var28.append(var29);
      String var31 = var6.toString();
      int var32 = Log.d("NetworkRequestUtil/ConstructPageviewRequestPath", var31);
      return var6.toString();
   }

   private static void createX10Project(CustomVariable[] var0, StringBuilder var1, int var2) {
      StringBuilder var3 = var1.append(var2).append("(");
      int var4 = 0;

      while(true) {
         int var5 = var0.length;
         if(var4 >= var5) {
            StringBuilder var17 = var1.append(")");
            return;
         }

         if(var0[var4] != false) {
            CustomVariable var6 = var0[var4];
            if(false) {
               StringBuilder var7 = var1.append("*");
            } else {
               boolean var10 = false;
            }

            int var8 = var6.getIndex();
            StringBuilder var9 = var1.append(var8).append("!");
            switch(var2) {
            case 8:
               String var11 = x10Escape(encode(var6.getName()));
               var1.append(var11);
               break;
            case 9:
               String var13 = x10Escape(encode(var6.getValue()));
               var1.append(var13);
            case 10:
            default:
               break;
            case 11:
               int var15 = var6.getScope();
               var1.append(var15);
            }
         }

         ++var4;
      }
   }

   private static String encode(String var0) {
      return AnalyticsParameterEncoder.encode(var0);
   }

   public static String getCustomVariableParams(Event var0) {
      StringBuilder var1 = new StringBuilder();
      CustomVariableBuffer var2 = var0.getCustomVariableBuffer();
      String var4;
      if(var2 == null) {
         var4 = "";
      } else if(!var2.hasCustomVariables()) {
         var4 = "";
      } else {
         CustomVariable[] var3 = var2.getCustomVariableArray();
         createX10Project(var3, var1, 8);
         createX10Project(var3, var1, 9);
         createX10Project(var3, var1, 11);
         var4 = var1.toString();
      }

      return var4;
   }

   public static String getEscapedCookieString(Event var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = var2.append("__utma=");
      StringBuilder var4 = var2.append("999").append(".");
      int var5 = var0.userId;
      StringBuilder var6 = var2.append(var5).append(".");
      int var7 = var0.timestampFirst;
      StringBuilder var8 = var2.append(var7).append(".");
      int var9 = var0.timestampPrevious;
      StringBuilder var10 = var2.append(var9).append(".");
      int var11 = var0.timestampCurrent;
      StringBuilder var12 = var2.append(var11).append(".");
      int var13 = var0.visits;
      var2.append(var13);
      if(var1 != null) {
         StringBuilder var15 = var2.append("+__utmz=");
         StringBuilder var16 = var2.append("999").append(".");
         int var17 = var0.timestampFirst;
         StringBuilder var18 = var2.append(var17).append(".");
         StringBuilder var19 = var2.append("1.1.");
         var2.append(var1);
      }

      return encode(var2.toString());
   }

   private static String x10Escape(String var0) {
      return var0.replace("\'", "\'0").replace(")", "\'1").replace("*", "\'2").replace("!", "\'3");
   }
}

package com.google.android.apps.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.apps.analytics.PersistentEventStore;
import java.util.HashMap;

public class AnalyticsReceiver extends BroadcastReceiver {

   private static final String INSTALL_ACTION = "com.android.vending.INSTALL_REFERRER";


   public AnalyticsReceiver() {}

   static String formatReferrer(String var0) {
      String[] var1 = var0.split("&");
      HashMap var2 = new HashMap();
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String[] var5 = var1[var4].split("=");
         if(var5.length != 2) {
            break;
         }

         String var8 = var5[0];
         String var9 = var5[1];
         var2.put(var8, var9);
      }

      boolean var6;
      if(var2.get("utm_campaign") != null) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var32;
      if(var2.get("utm_medium") != null) {
         var32 = true;
      } else {
         var32 = false;
      }

      String[] var34;
      if(var2.get("utm_source") != null) {
         var34 = null;
      } else {
         var34 = null;
      }

      String var35;
      if(var6 && var32 && var34 != null) {
         var34 = new String[7];
         String[] var11 = new String[]{"utmcid", null};
         String var12 = (String)var2.get("utm_id");
         var11[1] = var12;
         var34[0] = var11;
         String[] var13 = new String[]{"utmcsr", null};
         String var14 = (String)var2.get("utm_source");
         var13[1] = var14;
         var34[1] = var13;
         String[] var15 = new String[]{"utmgclid", null};
         String var16 = (String)var2.get("gclid");
         var15[1] = var16;
         var34[2] = var15;
         String[] var17 = new String[]{"utmccn", null};
         String var18 = (String)var2.get("utm_campaign");
         var17[1] = var18;
         var34[3] = var17;
         String[] var19 = new String[]{"utmcmd", null};
         String var20 = (String)var2.get("utm_medium");
         var19[1] = var20;
         var34[4] = var19;
         String[] var21 = new String[]{"utmctr", null};
         String var22 = (String)var2.get("utm_term");
         var21[1] = var22;
         var34[5] = var21;
         String[] var23 = new String[]{"utmcct", null};
         String var24 = (String)var2.get("utm_content");
         var23[1] = var24;
         var34[6] = var23;
         StringBuilder var33 = new StringBuilder();
         int var25 = 0;
         var32 = true;

         while(true) {
            int var26 = var34.length;
            if(var25 >= var26) {
               var35 = var33.toString();
               break;
            }

            if(((Object[])var34[var25])[1] != false) {
               String var27 = ((Object[])var34[var25])[1].replace("+", "%20").replace(" ", "%20");
               if(var32) {
                  boolean var28 = false;
               } else {
                  StringBuilder var31 = var33.append("|");
               }

               String var29 = ((Object[])var34[var25])[0];
               StringBuilder var30 = var33.append(var29).append("=").append(var27);
            }

            ++var25;
         }
      } else {
         int var7 = Log.w("googleanalytics", "Badly formatted referrer missing campaign, name or source");
         var35 = null;
      }

      return var35;
   }

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getStringExtra("referrer");
      String var4 = var2.getAction();
      if("com.android.vending.INSTALL_REFERRER".equals(var4)) {
         if(var3 != null) {
            String var5 = formatReferrer(var3);
            if(var5 != null) {
               PersistentEventStore.DataBaseHelper var6 = new PersistentEventStore.DataBaseHelper(var1);
               (new PersistentEventStore(var6)).setReferrer(var5);
               String var7 = "Stored referrer:" + var5;
               int var8 = Log.d("googleanalytics", var7);
            } else {
               int var9 = Log.w("googleanalytics", "Badly formatted referrer, ignored");
            }
         }
      }
   }
}

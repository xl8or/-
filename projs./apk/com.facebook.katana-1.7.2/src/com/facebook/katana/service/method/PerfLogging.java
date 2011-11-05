package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.Constants;
import com.facebook.katana.UserAgent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.ApiLogging;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfLogging extends ApiLogging {

   private static final String ACTIVITY_ID_PARAM = "activity_id";
   private static final String APP_VERSION_PARAM = "app_version";
   private static final String CARRIER_PARAM = "carrier";
   private static final String DEVICE_PARAM = "device";
   private static final String HREF_PARAM = "href";
   protected static final int LOG_ID_PAGE_VIEW = 109;
   private static final String LOG_ID_PARAM = "lid";
   protected static final int LOG_ID_PERF_TTI = 111;
   private static final String OS_VERSION_PARAM = "os_version";
   private static final String PAGE_PARAM = "page";
   private static final int PERF_LOGGING_RATIO = 10000;
   private static final int PV_LOGGING_RATIO = 10000;
   private static final String STEP_PARAM = "step";
   private static final String TIMESTAMP_PARAM = "time";
   private static final String UID_PARAM = "uid";


   public PerfLogging() {}

   public static JSONObject getCommonParams(Context var0, long var1) {
      JSONObject var3 = new JSONObject();

      try {
         AppSession var4 = AppSession.getActiveSession(var0, (boolean)0);
         if(var4 != null) {
            FacebookSessionInfo var5 = var4.getSessionInfo();
            if(var5 != null) {
               long var6 = var5.userId;
               var3.put("uid", var6);
            }
         }

         var3.put("time", var1);
         String var10 = UserAgent.getDevice();
         var3.put("device", var10);
         String var12 = UserAgent.getCarrier(var0);
         var3.put("carrier", var12);
         String var14 = UserAgent.getAppVersion(var0);
         var3.put("app_version", var14);
         String var16 = UserAgent.getOSVersion();
         var3.put("os_version", var16);
      } catch (JSONException var19) {
         ;
      }

      return var3;
   }

   public static void logPageView(Context var0, String var1, long var2) {
      long var4 = System.currentTimeMillis();
      logPageView(var0, var1, var2, var4);
   }

   public static void logPageView(Context var0, String var1, long var2, long var4) {
      if(var2 % 10000L == 0L || Constants.isBetaBuild()) {
         JSONObject var6 = getCommonParams(var0, var4);

         try {
            JSONObject var7 = var6.put("lid", 109);
            var6.put("page", var1);
            String var9 = var6.toString();
            StringBuilder var10 = new StringBuilder(var9);
            logAction(var0, var10);
         } catch (JSONException var12) {
            ;
         }
      }
   }

   public static void logStep(Context var0, PerfLogging.Step var1, String var2, long var3) {
      logStep(var0, var1, var2, var3, (String)null);
   }

   public static void logStep(Context var0, PerfLogging.Step var1, String var2, long var3, String var5) {
      long var6 = System.currentTimeMillis();
      if(var3 % 10000L == 0L || Constants.isBetaBuild()) {
         JSONObject var8 = getCommonParams(var0, var6);

         try {
            JSONObject var9 = var8.put("lid", 111);
            var8.put("page", var2);
            var8.put("activity_id", var3);
            var8.put("step", var1);
            if(var5 != null) {
               var8.put("href", var5);
            }

            String var14 = var8.toString();
            StringBuilder var15 = new StringBuilder(var14);
            logAction(var0, var15);
         } catch (JSONException var17) {
            ;
         }
      }
   }

   public static enum Step {

      // $FF: synthetic field
      private static final PerfLogging.Step[] $VALUES;
      DATA_RECEIVED("DATA_RECEIVED", 4),
      DATA_REQUESTED("DATA_REQUESTED", 3),
      ONCREATE("ONCREATE", 0),
      ONRESUME("ONRESUME", 1),
      UI_DRAWN_FRESH("UI_DRAWN_FRESH", 5),
      UI_DRAWN_STALE("UI_DRAWN_STALE", 2);


      static {
         PerfLogging.Step[] var0 = new PerfLogging.Step[6];
         PerfLogging.Step var1 = ONCREATE;
         var0[0] = var1;
         PerfLogging.Step var2 = ONRESUME;
         var0[1] = var2;
         PerfLogging.Step var3 = UI_DRAWN_STALE;
         var0[2] = var3;
         PerfLogging.Step var4 = DATA_REQUESTED;
         var0[3] = var4;
         PerfLogging.Step var5 = DATA_RECEIVED;
         var0[4] = var5;
         PerfLogging.Step var6 = UI_DRAWN_FRESH;
         var0[5] = var6;
         $VALUES = var0;
      }

      private Step(String var1, int var2) {}
   }
}

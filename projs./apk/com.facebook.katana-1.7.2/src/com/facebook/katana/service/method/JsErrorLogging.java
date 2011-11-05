package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.Constants;
import com.facebook.katana.UserAgent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.service.method.ApiLogging;
import org.json.JSONException;
import org.json.JSONObject;

public class JsErrorLogging extends ApiLogging {

   private static final String APP_VERSION_PARAM = "app_version";
   private static final String CARRIER_PARAM = "carrier";
   private static final String DEVICE_PARAM = "device";
   private static final int ERR_LOGGING_RATIO = 10000;
   private static final String EXC_TEXT_PARAM = "exc_text";
   protected static final int LOG_ID_JS_ERROR = 112;
   private static final String LOG_ID_PARAM = "lid";
   private static final String OS_VERSION_PARAM = "os_version";
   private static final String SCRIPT_PARAM = "script";
   private static final String UID_PARAM = "uid";


   public JsErrorLogging() {}

   public static void log(Context var0, String var1, String var2) {
      if(com.facebook.katana.util.Utils.RNG.nextInt() % 10000 == 0 || Constants.isBetaBuild()) {
         JSONObject var3 = new JSONObject();

         try {
            long var4 = AppSession.getActiveSession(var0, (boolean)0).getSessionInfo().userId;
            var3.put("uid", var4);
            String var7 = UserAgent.getDevice();
            var3.put("device", var7);
            String var9 = UserAgent.getCarrier(var0);
            var3.put("carrier", var9);
            String var11 = UserAgent.getAppVersion(var0);
            var3.put("app_version", var11);
            String var13 = UserAgent.getOSVersion();
            var3.put("os_version", var13);
            JSONObject var15 = var3.put("lid", 112);
            var3.put("script", var1);
            var3.put("exc_text", var2);
            String var18 = var3.toString();
            StringBuilder var19 = new StringBuilder(var18);
            logAction(var0, var19);
         } catch (JSONException var21) {
            ;
         }
      }
   }
}

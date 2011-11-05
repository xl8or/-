package com.facebook.katana;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import com.facebook.katana.binding.AppSession;

public class FacebookWidgetProvider extends AppWidgetProvider {

   public FacebookWidgetProvider() {}

   public void onEnabled(Context var1) {
      AppSession var2 = AppSession.getActiveSession(var1, (boolean)0);
      if(var2 != null) {
         String var3 = var1.getString(2131362353);
         AppSession.clearWidget(var1, var3, "");
         var2.scheduleStatusPollingAlarm(var1, 0, 0);
      }
   }

   public void onUpdate(Context var1, AppWidgetManager var2, int[] var3) {
      AppSession var4 = AppSession.getActiveSession(var1, (boolean)0);
      if(var4 != null) {
         var4.widgetUpdate(var1);
      } else {
         String var5 = var1.getString(2131362352);
         String var6 = var1.getString(2131362351);
         AppSession.clearWidget(var1, var5, var6);
      }
   }
}

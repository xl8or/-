package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookNotifications;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class NotificationsGet extends ApiMethod {

   private FacebookNotifications mNotifications;


   public NotificationsGet(Context var1, Intent var2, String var3, ApiMethodListener var4) {
      String var5 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "notifications.get", var5, var4);
      Map var10 = this.mParams;
      StringBuilder var11 = (new StringBuilder()).append("");
      long var12 = System.currentTimeMillis();
      String var14 = var11.append(var12).toString();
      var10.put("call_id", var14);
      this.mParams.put("session_key", var3);
   }

   public FacebookNotifications getNotifications() {
      return this.mNotifications;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      FacebookNotifications var2 = new FacebookNotifications(var1);
      this.mNotifications = var2;
      if(this.mNotifications.hasNewNotifications()) {
         FacebookNotifications.save(this.mContext);
      }
   }
}

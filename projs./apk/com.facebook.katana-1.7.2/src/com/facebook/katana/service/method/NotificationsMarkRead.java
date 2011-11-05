package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.NotificationsProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class NotificationsMarkRead extends ApiMethod {

   public NotificationsMarkRead(Context var1, Intent var2, String var3, long[] var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "notifications.markRead", var6, var5);
      Map var11 = this.mParams;
      StringBuilder var12 = (new StringBuilder()).append("");
      long var13 = System.currentTimeMillis();
      String var15 = var12.append(var13).toString();
      var11.put("call_id", var15);
      this.mParams.put("session_key", var3);
      StringBuffer var18 = new StringBuffer(64);
      boolean var19 = true;
      int var20 = 0;

      while(true) {
         int var21 = var4.length;
         if(var20 >= var21) {
            Map var26 = this.mParams;
            String var27 = var18.toString();
            var26.put("notification_ids", var27);
            return;
         }

         if(!var19) {
            StringBuffer var22 = var18.append(",");
         } else {
            var19 = false;
         }

         long var23 = var4[var20];
         var18.append(var23);
         ++var20;
      }
   }

   private void updateReadFlag() {
      ContentValues var1 = new ContentValues();
      Integer var2 = Integer.valueOf(1);
      var1.put("is_read", var2);
      StringBuilder var3 = (new StringBuilder()).append("notif_id IN(");
      String var4 = (String)this.mParams.get("notification_ids");
      String var5 = var3.append(var4).append(")").toString();
      ContentResolver var6 = this.mContext.getContentResolver();
      Uri var7 = NotificationsProvider.CONTENT_URI;
      var6.update(var7, var1, var5, (String[])null);
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         this.updateReadFlag();
      }
   }
}

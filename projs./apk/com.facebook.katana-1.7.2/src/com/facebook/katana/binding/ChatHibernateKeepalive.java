package com.facebook.katana.binding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.BackgroundRequestService;
import com.facebook.katana.util.URLQueryBuilder;
import java.util.HashMap;
import java.util.Map;

public class ChatHibernateKeepalive extends BroadcastReceiver {

   public ChatHibernateKeepalive() {}

   public void onReceive(Context var1, Intent var2) {
      AppSession var3 = AppSession.getActiveSession(var1, (boolean)0);
      if(var3 != null) {
         FacebookSessionInfo var4 = var3.getSessionInfo();
         if(var4 != null) {
            HashMap var5 = new HashMap();
            Object var6 = var5.put("api_key", "882a8490361da98702bf97a021ddc14d");
            String var7 = String.valueOf(var4.userId);
            var5.put("uid", var7);
            String var9 = var4.sessionKey;
            var5.put("session_key", var9);
            String var11 = var4.sessionSecret;
            StringBuilder var12 = URLQueryBuilder.buildSignedQueryString((Map)var5, var11);
            StringBuilder var13 = new StringBuilder();
            String var14 = Constants.URL.getChatHibernateUrl(var1);
            StringBuilder var15 = var13.append(var14).append("?");
            String var16 = var12.toString();
            String var17 = var15.append(var16).toString();
            Intent var18 = new Intent(var1, BackgroundRequestService.class);
            BackgroundRequestService.Operation var19 = BackgroundRequestService.Operation.HTTP_REQUEST;
            var18.putExtra("com.facebook.katana.service.BackgroundRequestService.operation", var19);
            var18.putExtra("com.facebook.katana.service.BackgroundRequestService.uri", var17);
            var1.startService(var18);
         }
      }
   }
}

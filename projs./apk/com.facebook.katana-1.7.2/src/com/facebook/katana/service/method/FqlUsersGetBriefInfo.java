package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetUsersProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FqlUsersGetBriefInfo extends FqlGetUsersProfile implements ApiMethodCallback {

   public FqlUsersGetBriefInfo(Context var1, Intent var2, String var3, ApiMethodListener var4, Map<Long, FacebookUser> var5) {
      super(var1, var2, var3, var4, var5, FacebookUser.class);
   }

   public static String getUsersBriefInfo(AppSession var0, Context var1, Long[] var2) {
      HashMap var3 = new HashMap();
      Long[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Long var7 = var4[var6];
         var3.put(var7, (Object)null);
      }

      String var9 = var0.getSessionInfo().sessionKey;
      FqlUsersGetBriefInfo var11 = new FqlUsersGetBriefInfo(var1, (Intent)null, var9, (ApiMethodListener)null, var3);
      return var0.postToService(var1, var11, 1001, 1020, (Bundle)null);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         Collection var10 = this.getUsers().values();
         ArrayList var11 = new ArrayList(var10);
         var9.onUsersGetInfoComplete(var1, var4, var5, var6, var7, var11);
      }

   }
}

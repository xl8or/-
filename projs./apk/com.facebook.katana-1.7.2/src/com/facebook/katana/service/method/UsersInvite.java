package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.StringUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UsersInvite extends ApiMethod implements ApiMethodCallback {

   protected static final String EMAILS_PARAM = "emails";
   protected List<String> mContacts;


   public UsersInvite(Context var1, Intent var2, String var3, List<String> var4, String var5, String var6, ApiMethodListener var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "users.invite", var8, var7);
      this.mContacts = var4;
      Map var13 = this.mParams;
      String var14 = String.valueOf(System.currentTimeMillis());
      var13.put("call_id", var14);
      this.mParams.put("session_key", var3);
      StringBuilder var17 = new StringBuilder();
      UsersInvite.1 var18 = new UsersInvite.1();
      Object[] var19 = new Object[]{var4};
      StringUtils.join(var17, ",", var18, var19);
      Map var20 = this.mParams;
      String var21 = var17.toString();
      var20.put("emails", var21);
      if(var5 != null) {
         this.mParams.put("message", var5);
      }

      this.mParams.put("country_code", var6);
   }

   public static String invite(AppSession var0, Context var1, List<String> var2, String var3, String var4) {
      String var5 = var0.getSessionInfo().sessionKey;
      Object var10 = null;
      UsersInvite var11 = new UsersInvite(var1, (Intent)null, var5, var2, var3, var4, (ApiMethodListener)var10);
      Object var15 = null;
      return var0.postToService(var1, var11, 1001, 1020, (Bundle)var15);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         List var10 = this.mContacts;
         var9.onUsersInviteComplete(var1, var4, var5, var6, var7, var10);
      }

   }

   class 1 implements StringUtils.StringProcessor {

      1() {}

      public String formatString(Object var1) {
         return var1.toString().replace(",", "");
      }
   }
}

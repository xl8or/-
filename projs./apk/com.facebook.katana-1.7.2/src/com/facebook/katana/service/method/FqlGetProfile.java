package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetProfileGeneric;
import com.facebook.katana.util.StringUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FqlGetProfile extends FqlGetProfileGeneric<FacebookProfile> implements ApiMethodCallback {

   private static final String TAG = "FqlGetProfile";


   public FqlGetProfile(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      super(var1, var2, var3, var4, var5, FacebookProfile.class);
   }

   public FqlGetProfile(Context var1, Intent var2, String var3, ApiMethodListener var4, Map<Long, FacebookProfile> var5) {
      String var6 = buildWhereClause(var5);
      this(var1, var2, var3, var4, var6);
   }

   public static String RequestGroupMembers(Context var0, long var1) {
      AppSession var3 = AppSession.getActiveSession(var0, (boolean)0);
      String var4 = "id IN (SELECT uid FROM group_member WHERE gid=" + var1 + ")";
      String var5 = var3.getSessionInfo().sessionKey;
      Object var7 = null;
      FqlGetProfile var8 = new FqlGetProfile(var0, (Intent)null, var5, (ApiMethodListener)var7, var4);
      Object var11 = null;
      return var3.postToService(var0, var8, 1001, 601, (Bundle)var11);
   }

   public static String RequestSingleProfile(Context var0, long var1) {
      AppSession var3 = AppSession.getActiveSession(var0, (boolean)0);
      HashMap var4 = new HashMap();
      Long var5 = Long.valueOf(var1);
      var4.put(var5, (Object)null);
      String var7 = var3.getSessionInfo().sessionKey;
      Object var9 = null;
      FqlGetProfile var10 = new FqlGetProfile(var0, (Intent)null, var7, (ApiMethodListener)var9, var4);
      Object var13 = null;
      return var3.postToService(var0, var10, 1001, 84, (Bundle)var13);
   }

   private static String buildWhereClause(Map<Long, FacebookProfile> var0) {
      StringBuilder var1 = new StringBuilder(" id IN(");
      Object[] var2 = new Object[1];
      Set var3 = var0.keySet();
      var2[0] = var3;
      StringUtils.join(var1, ",", (StringUtils.StringProcessor)null, var2);
      StringBuilder var4 = var1.append(")");
      return var1.toString();
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      String var9 = "extended_type";
      byte var10 = -1;
      Iterator var13;
      switch(var3.getIntExtra(var9, var10)) {
      case 84:
         FacebookProfile var11 = null;
         Iterator var12 = this.mProfiles.values().iterator();
         if(var12.hasNext()) {
            var11 = (FacebookProfile)var12.next();
         }

         var13 = var1.getListeners().iterator();

         while(var13.hasNext()) {
            AppSessionListener var14 = (AppSessionListener)var13.next();
            var14.onGetProfileComplete(var1, var4, var5, var6, var7, var11);
         }

         return;
      case 601:
         var13 = var1.getListeners().iterator();

         while(var13.hasNext()) {
            AppSessionListener var20 = (AppSessionListener)var13.next();
            Map var21 = this.mProfiles;
            var20.onGetGroupsMembersComplete(var1, var4, var5, var6, var7, var21);
         }

         return;
      default:
      }
   }

   public Map<Long, FacebookProfile> getProfiles() {
      return Collections.unmodifiableMap(this.mProfiles);
   }
}

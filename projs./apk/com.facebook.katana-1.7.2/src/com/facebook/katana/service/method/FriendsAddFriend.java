package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FriendsAddFriend extends ApiMethod implements ApiMethodCallback {

   protected List<Long> mUids;


   protected FriendsAddFriend(Context var1, Intent var2, String var3, Long var4, String var5, ApiMethodListener var6) {
      String var7 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "facebook.friends_add", var7, var6);
      ArrayList var12 = new ArrayList();
      this.mUids = var12;
      this.mUids.add(var4);
      Map var14 = this.mParams;
      String var15 = String.valueOf(var4);
      var14.put("uid", var15);
      this.addCommonParams(var3, var5);
   }

   protected FriendsAddFriend(Context var1, Intent var2, String var3, List<Long> var4, String var5, ApiMethodListener var6) {
      String var7 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "facebook.friends_add", var7, var6);
      this.mUids = var4;
      Map var12 = this.mParams;
      Object[] var13 = new Object[]{var4};
      String var14 = StringUtils.join(",", var13);
      var12.put("uids", var14);
      this.addCommonParams(var3, var5);
   }

   public static String friendsAddFriend(AppSession var0, Context var1, Long var2, String var3) {
      String var4 = var0.getSessionInfo().sessionKey;
      Object var8 = null;
      FriendsAddFriend var9 = new FriendsAddFriend(var1, (Intent)null, var4, var2, var3, (ApiMethodListener)var8);
      Object var13 = null;
      return var0.postToService(var1, var9, 1001, 1020, (Bundle)var13);
   }

   public static String friendsAddFriends(AppSession var0, Context var1, List<Long> var2, String var3) {
      String var4 = var0.getSessionInfo().sessionKey;
      Object var8 = null;
      FriendsAddFriend var9 = new FriendsAddFriend(var1, (Intent)null, var4, var2, var3, (ApiMethodListener)var8);
      Object var13 = null;
      return var0.postToService(var1, var9, 1001, 1020, (Bundle)var13);
   }

   protected void addCommonParams(String var1, String var2) {
      Map var3 = this.mParams;
      String var4 = Long.toString(System.currentTimeMillis());
      var3.put("call_id", var4);
      this.mParams.put("session_key", var1);
      if(var2 != null) {
         this.mParams.put("message", var2);
      }
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         List var10 = this.mUids;
         var9.onFriendsAddFriendComplete(var1, var4, var5, var6, var7, var10);
      }

   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      }
   }
}

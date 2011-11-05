package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import java.util.Iterator;
import java.util.Map;

public class PlacesRemoveTag extends ApiMethod implements ApiMethodCallback {

   public FacebookPost mPost;
   protected long mUserId;


   public PlacesRemoveTag(Context var1, Intent var2, String var3, ApiMethodListener var4, FacebookPost var5, long var6) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "places.removeTag", var8, var4);
      this.mPost = var5;
      this.mUserId = var6;
      Map var13 = this.mParams;
      StringBuilder var14 = (new StringBuilder()).append("");
      long var15 = System.currentTimeMillis();
      String var17 = var14.append(var15).toString();
      var13.put("call_id", var17);
      this.mParams.put("session_key", var3);
      Map var20 = this.mParams;
      String var21 = this.mPost.postId;
      var20.put("post_id", var21);
      Map var23 = this.mParams;
      String var24 = Long.toString(this.mUserId);
      var23.put("tagged_uid", var24);
   }

   public static String RemoveTag(AppSession var0, Context var1, FacebookPost var2, long var3) {
      String var5 = var0.getSessionInfo().sessionKey;
      Object var7 = null;
      PlacesRemoveTag var11 = new PlacesRemoveTag(var1, (Intent)null, var5, (ApiMethodListener)var7, var2, var3);
      Object var15 = null;
      return var0.postToService(var1, var11, 1001, 1020, (Bundle)var15);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         FacebookPost var10 = this.mPost;
         long var11 = this.mUserId;
         var9.onPlacesRemoveTagComplete(var1, var4, var5, var6, var7, var10, var11);
      }

   }
}

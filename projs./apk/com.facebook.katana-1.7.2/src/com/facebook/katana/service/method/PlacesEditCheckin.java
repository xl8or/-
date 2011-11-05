package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookCheckin;
import com.facebook.katana.model.FacebookCheckinDetails;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONException;
import org.json.JSONObject;

public class PlacesEditCheckin extends ApiMethod implements ApiMethodCallback {

   protected long mCheckinId;
   public String mMessage;
   public Set<Long> mTaggedUids;


   public PlacesEditCheckin(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, String var7, Set<Long> var8) throws JSONException {
      String var9 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "places.editCheckin", var9, var4);
      Map var14 = this.mParams;
      String var15 = Long.toString(System.currentTimeMillis());
      var14.put("call_id", var15);
      this.mParams.put("session_key", var3);
      Map var18 = this.mParams;
      String var19 = Long.toString(var5);
      var18.put("checkin_id", var19);
      JSONObject var21 = new JSONObject();
      if(var7 != null) {
         String var23 = "message";
         var21.put(var23, var7);
      }

      if(var8 != null) {
         String var27 = "tagged_uids";
         var21.put(var27, var8);
      }

      Map var30 = this.mParams;
      String var31 = var21.toString();
      var30.put("checkin_data", var31);
      this.mCheckinId = var5;
      this.mMessage = var7;
      this.mTaggedUids = var8;
   }

   public static String EditCheckin(AppSession var0, Context var1, long var2, String var4, Set<Long> var5) throws JSONException {
      String var6 = var0.getSessionInfo().sessionKey;
      Object var8 = null;
      PlacesEditCheckin var13 = new PlacesEditCheckin(var1, (Intent)null, var6, (ApiMethodListener)var8, var2, var4, var5);
      Object var17 = null;
      return var0.postToService(var1, var13, 1001, 1020, (Bundle)var17);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      short var9 = 200;
      if(var5 == var9) {
         String var11 = "places:last_checkin";
         Object var12 = null;
         String var13 = UserValuesManager.getValue(var2, var11, (String)var12);
         if(var13 != null) {
            Class var15 = FacebookCheckin.class;
            FacebookCheckin var16 = (FacebookCheckin)JMCachingDictDestination.jsonDecode(var13, var15);
            long var17 = var16.mCheckinId;
            long var19 = this.mCheckinId;
            if(var17 == var19) {
               FacebookCheckinDetails var21 = var16.getDetails();
               Set var22 = this.mTaggedUids;
               ArrayList var23 = new ArrayList(var22);
               var21.mTaggedUids = var23;
               String var24 = var16.jsonEncode();
               if(var24 != null) {
                  String var26 = "places:last_checkin";
                  UserValuesManager.setValue(var2, var26, var24);
               }
            }
         }
      }

      Iterator var28 = var1.getListeners().iterator();

      while(var28.hasNext()) {
         AppSessionListener var29 = (AppSessionListener)var28.next();
         long var30 = this.mCheckinId;
         String var32 = this.mMessage;
         Set var33 = this.mTaggedUids;
         var29.onPlacesEditCheckinComplete(var1, var4, var5, var6, var7, var30, var32, var33);
      }

   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.VALUE_NULL;
      if(var2 != var3) {
         throw new FacebookApiException(var1);
      }
   }
}

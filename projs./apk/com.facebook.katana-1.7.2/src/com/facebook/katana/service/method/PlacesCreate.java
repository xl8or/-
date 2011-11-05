package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.PlacesCreateException;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMLong;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlacesCreate extends ApiMethod implements ApiMethodCallback {

   static final String TAG = "service.method.PlacesCreate";
   public String mDescription;
   public Location mLocation;
   public String mName;
   public List<Long> mOverrideIds;
   protected long mPlaceId;


   public PlacesCreate(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5, String var6, Location var7, List<Long> var8) {
      String var9 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "places.create", var9, var4);
      Map var14 = this.mParams;
      String var15 = Long.toString(System.currentTimeMillis());
      var14.put("call_id", var15);
      this.mParams.put("session_key", var3);
      this.mParams.put("name", var5);
      Map var19 = this.mParams;
      String var20 = this.jsonEncode(var7);
      var19.put("coords", var20);
      this.mParams.put("description", var6);
      Map var23 = this.mParams;
      String var24 = this.jsonEncode(var8);
      var23.put("override_ids", var24);
      this.mName = var5;
      this.mDescription = var6;
      this.mLocation = var7;
      this.mOverrideIds = var8;
      this.mPlaceId = 65535L;
   }

   public static String placesCreate(Context var0, String var1, String var2, Location var3, List<Long> var4) {
      AppSession var5 = AppSession.getActiveSession(var0, (boolean)0);
      String var6 = var5.getSessionInfo().sessionKey;
      Object var8 = null;
      PlacesCreate var13 = new PlacesCreate(var0, (Intent)null, var6, (ApiMethodListener)var8, var1, var2, var3, var4);
      byte var17 = 0;
      Object var18 = null;
      return var5.postToService(var0, var13, 1001, var17, (Bundle)var18);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      long var8 = this.getCreatedPlace();
      Iterator var10 = var1.getListeners().iterator();

      while(var10.hasNext()) {
         AppSessionListener var11 = (AppSessionListener)var10.next();
         var11.onPlacesCreateComplete(var1, var4, var5, var6, var7, var8);
      }

   }

   public long getCreatedPlace() {
      return this.mPlaceId;
   }

   protected String jsonEncode(Location var1) {
      String var21;
      String var22;
      try {
         JSONObject var2 = new JSONObject();
         double var3 = var1.getLatitude();
         var2.put("latitude", var3);
         double var6 = var1.getLongitude();
         var2.put("longitude", var6);
         if(var1.hasAccuracy()) {
            double var9 = (double)var1.getAccuracy();
            var2.put("accuracy", var9);
         }

         if(var1.hasAltitude()) {
            double var12 = var1.getAltitude();
            var2.put("altitude", var12);
         }

         if(var1.hasBearing()) {
            double var15 = (double)var1.getBearing();
            var2.put("heading", var15);
         }

         if(var1.hasSpeed()) {
            double var18 = (double)var1.getSpeed();
            var2.put("speed", var18);
         }

         var21 = var2.toString();
      } catch (JSONException var27) {
         Object[] var24 = new Object[1];
         String var25 = var27.getMessage();
         var24[0] = var25;
         String var26 = String.format("How do we get a JSONException when *encoding* data? %s", var24);
         Log.e("service.method.PlacesCreate", var26);
         var22 = "";
         return var22;
      }

      var22 = var21;
      return var22;
   }

   protected String jsonEncode(List<Long> var1) {
      JSONArray var2 = new JSONArray();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         long var4 = ((Long)var3.next()).longValue();
         var2.put(var4);
      }

      return var2.toString();
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         throw new PlacesCreateException(var1);
      } else {
         JMLong var4 = JMBase.LONG;
         Object var5 = JMParser.parseJsonResponse(var1, (JMBase)var4);
         if(var5 != null && var5 instanceof Long) {
            long var6 = ((Long)var5).longValue();
            this.mPlaceId = var6;
         } else {
            throw new FacebookApiException(-1, "unexpected value in response");
         }
      }
   }
}

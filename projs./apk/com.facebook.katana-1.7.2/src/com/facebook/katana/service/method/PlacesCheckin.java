package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMLong;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlacesCheckin extends ApiMethod {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   protected long mCheckinId;
   public Location mLocation;
   public String mMessage;
   public FacebookPlace mPlace;
   public Set<Long> mTaggedUids;


   static {
      byte var0;
      if(!PlacesCheckin.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public PlacesCheckin(Context var1, Intent var2, String var3, ApiMethodListener var4, FacebookPlace var5, Location var6, String var7, Set<Long> var8, Long var9, String var10) throws JSONException {
      String var11 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "places.checkin", var11, var4);
      Map var16 = this.mParams;
      StringBuilder var17 = (new StringBuilder()).append("");
      long var18 = System.currentTimeMillis();
      String var20 = var17.append(var18).toString();
      var16.put("call_id", var20);
      this.mParams.put("session_key", var3);
      Map var23 = this.mParams;
      String var24 = Long.toString(var5.mPageId);
      var23.put("page_id", var24);
      Map var26 = this.mParams;
      String var29 = this.jsonEncode(var6);
      var26.put("coords", var29);
      if(var7 != null) {
         Map var31 = this.mParams;
         String var32 = "message";
         var31.put(var32, var7);
      }

      if(var8 != null && var8.size() != 0) {
         Map var35 = this.mParams;
         JSONArray var36 = new JSONArray(var8);
         String var39 = var36.toString();
         var35.put("tagged_uids", var39);
      }

      if(var9 != null && var9.longValue() != 65535L) {
         Map var41 = this.mParams;
         String var42 = String.valueOf(var9);
         var41.put("group_id", var42);
      }

      if(var10 != null) {
         Map var44 = this.mParams;
         JSONObject var45 = new JSONObject(var10);
         String var48 = var45.toString();
         var44.put("privacy", var48);
      }

      this.mPlace = var5;
      if(!$assertionsDisabled && var5.getPageInfo() == null) {
         throw new AssertionError();
      } else {
         this.mLocation = var6;
         this.mMessage = var7;
         this.mTaggedUids = var8;
         this.mCheckinId = 65535L;
      }
   }

   public long getCheckinId() {
      return this.mCheckinId;
   }

   protected String jsonEncode(Location var1) throws JSONException {
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

      return var2.toString();
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         throw new FacebookApiException(var1);
      } else {
         JMLong var4 = JMBase.LONG;
         Object var5 = JMParser.parseJsonResponse(var1, (JMBase)var4);
         if(var5 != null && var5 instanceof Long) {
            long var6 = ((Long)var5).longValue();
            this.mCheckinId = var6;
         } else {
            throw new FacebookApiException(-1, "unexpected value in response");
         }
      }
   }
}

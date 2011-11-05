package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMLong;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONException;

public class PlacesFlag extends ApiMethod implements ApiMethodCallback {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   public static String CLOSED;
   public static String DUPLICATE;
   public static String INFO_INCORRECT;
   public static final long INVALID_ID = 255L;
   public static String OFFENSIVE;
   protected long mFlagId;
   protected String mFlagType;
   public FacebookPlace mPlace;


   static {
      byte var0;
      if(!PlacesFlag.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      INFO_INCORRECT = "info_incorrect";
      OFFENSIVE = "offensive";
      CLOSED = "closed";
      DUPLICATE = "duplicate";
   }

   public PlacesFlag(Context var1, Intent var2, String var3, ApiMethodListener var4, FacebookPlace var5, String var6) throws JSONException {
      String var7 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "places.setFlag", var7, var4);
      this.mPlace = var5;
      this.mFlagType = var6;
      Map var12 = this.mParams;
      StringBuilder var13 = (new StringBuilder()).append("");
      long var14 = System.currentTimeMillis();
      String var16 = var13.append(var14).toString();
      var12.put("call_id", var16);
      this.mParams.put("session_key", var3);
      Map var19 = this.mParams;
      String var20 = Long.toString(this.mPlace.mPageId);
      var19.put("page_id", var20);
      Map var22 = this.mParams;
      String var23 = this.mFlagType;
      var22.put("flag", var23);
      Object var25 = this.mParams.put("value", "1");
      if(!$assertionsDisabled && var5.getPageInfo() == null) {
         throw new AssertionError();
      } else {
         this.mFlagId = 65535L;
      }
   }

   public static String FlagPlace(AppSession var0, Context var1, FacebookPlace var2, String var3) throws JSONException {
      String var4 = var0.getSessionInfo().sessionKey;
      Object var6 = null;
      PlacesFlag var9 = new PlacesFlag(var1, (Intent)null, var4, (ApiMethodListener)var6, var2, var3);
      Object var13 = null;
      return var0.postToService(var1, var9, 1001, 505, (Bundle)var13);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      switch(var3.getIntExtra("extended_type", -1)) {
      case 505:
         Iterator var8 = var1.getListeners().iterator();

         while(var8.hasNext()) {
            AppSessionListener var9 = (AppSessionListener)var8.next();
            var9.onFlagPlaceComplete(var1, var4, var5, var6, var7);
         }

         return;
      default:
      }
   }

   public long getFlagId() {
      return this.mFlagId;
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
            this.mFlagId = var6;
         } else {
            throw new FacebookApiException(-1, "unexpected value in response");
         }
      }
   }
}

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.GeoRegion;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetNearbyRegions extends FqlGeneratedQuery implements ApiMethodCallback {

   protected static final String TAG = com.facebook.katana.util.Utils.getClassName(FqlGetNearbyRegions.class);
   public List<GeoRegion> regions;


   public FqlGetNearbyRegions(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      super(var1, var2, var3, var4, "geo_region", var5, GeoRegion.class);
   }

   public static String GetRegions(Context var0, String var1) {
      AppSession var2 = AppSession.getActiveSession(var0, (boolean)0);
      String var3 = var2.getSessionInfo().sessionKey;
      Object var5 = null;
      FqlGetNearbyRegions var7 = new FqlGetNearbyRegions(var0, (Intent)null, var3, (ApiMethodListener)var5, var1);
      Object var11 = null;
      return var2.postToService(var0, var7, 1001, 1020, (Bundle)var11);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         List var10 = this.regions;
         var9.onGetNearbyRegionsComplete(var1, var4, var5, var6, var7, var10);
      }

   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, GeoRegion.class);
      this.regions = var2;
   }
}

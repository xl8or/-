package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.GraphRequestResponse;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.GraphApiMethod;
import com.facebook.katana.service.method.GraphBatchRequest;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

public class PhotosPublish extends GraphBatchRequest implements ApiMethodCallback {

   private int mNumFail;
   private int mNumSuccess;
   protected final List<String> mPhotoIds;


   private PhotosPublish(Context var1, String var2, List<String> var3, String var4, JSONObject var5, List<Long> var6, String var7, Long var8) {
      String var9 = Constants.URL.getGraphUrl(var1);
      List var17 = genRequests(var1, var3, var4, var5, var6, var7, var8);
      super(var1, var2, var9, var17);
      this.mPhotoIds = var3;
      this.mNumFail = 0;
      this.mNumSuccess = 0;
   }

   public static String Publish(Context var0, List<String> var1, String var2, JSONObject var3, List<Long> var4, String var5, Long var6) {
      AppSession var7 = AppSession.getActiveSession(var0, (boolean)0);
      String var8 = var7.getSessionInfo().oAuthToken;
      PhotosPublish var16 = new PhotosPublish(var0, var8, var1, var2, var3, var4, var5, var6);
      return var7.postToService(var0, var16, 1001, 1020, (Bundle)null);
   }

   private static List<GraphApiMethod> genRequests(Context var0, List<String> var1, String var2, JSONObject var3, List<Long> var4, String var5, Long var6) {
      HashMap var7 = new HashMap();
      Object var8 = var7.put("published", "1");
      if(var6 != null && var6.longValue() != 65535L) {
         String var9 = String.valueOf(var6);
         var7.put("target", var9);
      }

      var7.put("name", var2);
      if(var3 != null) {
         String var12 = var3.toString();
         var7.put("privacy", var12);
      }

      if(var5 != null) {
         var7.put("place", var5);
      }

      if(var4 != null && var4.size() > 0) {
         JSONArray var15 = new JSONArray();
         Iterator var16 = var4.iterator();

         while(var16.hasNext()) {
            Long var17 = (Long)var16.next();
            var15.put(var17);
         }

         String var19 = var15.toString();
         var7.put("tags", var19);
      }

      ArrayList var21 = new ArrayList();
      Iterator var22 = var1.iterator();

      while(var22.hasNext()) {
         String var23 = (String)var22.next();
         String var24 = Constants.URL.getGraphUrl(var0);
         GraphApiMethod var25 = new GraphApiMethod(var0, "POST", var23, var24);
         var25.mParams.putAll(var7);
         var21.add(var25);
      }

      return var21;
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         List var10 = this.mPhotoIds;
         int var11 = this.mNumSuccess;
         int var12 = this.mNumFail;
         var9.onPhotosPublishComplete(var1, var4, var5, var6, var7, var10, var11, var12);
      }

   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      this.parseResponse(var1);
      Iterator var2 = this.responses.iterator();

      while(var2.hasNext()) {
         GraphRequestResponse var3 = (GraphRequestResponse)var2.next();
         if(var3.code == 200 && var3.body.equals("true")) {
            int var5 = this.mNumSuccess + 1;
            this.mNumSuccess = var5;
         } else {
            int var4 = this.mNumFail + 1;
            this.mNumFail = var4;
         }
      }

   }
}

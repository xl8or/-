package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.GraphApiMethod;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMBoolean;
import java.io.IOException;
import java.util.Iterator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class MarkGroupRead extends GraphApiMethod implements ApiMethodCallback {

   public final long mGroupId;
   protected boolean mSuccess;


   private MarkGroupRead(Context var1, String var2, long var3) {
      String var5 = Long.toString(var3);
      String var6 = Constants.URL.getGraphUrl(var1);
      super(var1, var2, "POST", var5, var6);
      Object var10 = this.mParams.put("viewed", "true");
      this.mGroupId = var3;
   }

   public static String Request(Context var0, long var1) {
      AppSession var3 = AppSession.getActiveSession(var0, (boolean)0);
      String var4 = var3.getSessionInfo().oAuthToken;
      MarkGroupRead var5 = new MarkGroupRead(var0, var4, var1);
      return var3.postToService(var0, var5, 1001, 1020, (Bundle)null);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      if(!this.mSuccess) {
         var5 = 0;
      }

      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         long var10 = this.mGroupId;
         var9.onMarkGroupReadComplete(var1, var4, var5, var6, var7, var10);
      }

   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JMBoolean var2 = JMBase.BOOLEAN;
      Object var3 = JMParser.parseJsonResponse(var1, (JMBase)var2);
      if(var3 != null) {
         if(var3 instanceof Boolean) {
            boolean var4 = ((Boolean)var3).booleanValue();
            this.mSuccess = var4;
         }
      }
   }
}

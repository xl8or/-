package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMBoolean;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PagesAddFan extends ApiMethod implements ApiMethodCallback {

   protected boolean mSuccess;


   public PagesAddFan(Context var1, Intent var2, String var3, long var4, ApiMethodListener var6) {
      String var7 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "pages.addFan", var7, var6);
      this.mListener = var6;
      Map var12 = this.mParams;
      String var13 = String.valueOf(System.currentTimeMillis());
      var12.put("call_id", var13);
      this.mParams.put("session_key", var3);
      Map var16 = this.mParams;
      String var17 = String.valueOf(var4);
      var16.put("page_id", var17);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         boolean var10 = this.mSuccess;
         var9.onPagesAddFanComplete(var1, var4, var5, var6, var7, var10);
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

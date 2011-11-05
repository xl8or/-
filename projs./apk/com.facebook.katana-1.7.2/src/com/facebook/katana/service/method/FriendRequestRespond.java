package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FriendRequestRespond extends ApiMethod {

   private long mRequesterUserId;
   private boolean mSuccess;


   public FriendRequestRespond(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, boolean var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "facebook.friends.confirm", var8, var4);
      Map var13 = this.mParams;
      StringBuilder var14 = (new StringBuilder()).append("");
      long var15 = System.currentTimeMillis();
      String var17 = var14.append(var15).toString();
      var13.put("call_id", var17);
      this.mParams.put("session_key", var3);
      Map var20 = this.mParams;
      String var21 = Long.toString(var5);
      var20.put("uid", var21);
      Map var23 = this.mParams;
      String var24;
      if(var7) {
         var24 = "1";
      } else {
         var24 = "0";
      }

      var23.put("confirm", var24);
      this.mRequesterUserId = var5;
      this.mSuccess = (boolean)0;
   }

   public long getRequesterUserId() {
      return this.mRequesterUserId;
   }

   public boolean getSuccess() {
      return this.mSuccess;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.VALUE_TRUE;
      if(var2 == var3) {
         this.mSuccess = (boolean)1;
      }
   }
}

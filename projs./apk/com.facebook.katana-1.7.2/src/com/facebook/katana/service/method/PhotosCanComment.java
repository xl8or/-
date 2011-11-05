package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PhotosCanComment extends ApiMethod {

   private boolean mCanComment;


   public PhotosCanComment(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos_cancomment", var6, var5);
      this.mParams.put("session_key", var3);
      Map var12 = this.mParams;
      StringBuilder var13 = (new StringBuilder()).append("");
      long var14 = System.currentTimeMillis();
      String var16 = var13.append(var14).toString();
      var12.put("call_id", var16);
      this.mParams.put("pid", var4);
   }

   public boolean getCanComment() {
      return this.mCanComment;
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         boolean var3 = var1.trim().equals("true");
         this.mCanComment = var3;
      }
   }
}

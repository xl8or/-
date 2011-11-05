package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhotoComment;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PhotosAddComment extends ApiMethod {

   private FacebookPhotoComment mComment;
   private final long mMyUserId;


   public PhotosAddComment(Context var1, Intent var2, String var3, long var4, String var6, String var7, ApiMethodListener var8) {
      String var9 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos_addcomment", var9, var8);
      this.mParams.put("session_key", var3);
      Map var15 = this.mParams;
      StringBuilder var16 = (new StringBuilder()).append("");
      long var17 = System.currentTimeMillis();
      String var19 = var16.append(var17).toString();
      var15.put("call_id", var19);
      this.mParams.put("pid", var6);
      this.mParams.put("body", var7);
      this.mMyUserId = var4;
   }

   public FacebookPhotoComment getComment() {
      return this.mComment;
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         long var3 = Long.parseLong(var1.trim());
         String var5 = (String)this.mParams.get("pid");
         long var6 = this.mMyUserId;
         String var8 = (String)this.mParams.get("body");
         long var9 = System.currentTimeMillis() / 1000L;
         FacebookPhotoComment var11 = new FacebookPhotoComment(var5, var6, var8, var9, var3);
         this.mComment = var11;
      }
   }
}

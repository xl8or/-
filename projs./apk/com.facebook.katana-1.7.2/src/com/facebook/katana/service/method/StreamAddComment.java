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

public class StreamAddComment extends ApiMethod {

   private String mCommentId;


   public StreamAddComment(Context var1, Intent var2, String var3, long var4, String var6, String var7, ApiMethodListener var8) {
      String var9 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "stream.addComment", var9, var8);
      Map var14 = this.mParams;
      StringBuilder var15 = (new StringBuilder()).append("");
      long var16 = System.currentTimeMillis();
      String var18 = var15.append(var16).toString();
      var14.put("call_id", var18);
      this.mParams.put("session_key", var3);
      Map var21 = this.mParams;
      String var22 = "" + var4;
      var21.put("uid", var22);
      this.mParams.put("post_id", var6);
      this.mParams.put("comment", var7);
   }

   public String getCommentId() {
      return this.mCommentId;
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         String var3 = removeChar(var1, '\"');
         this.mCommentId = var3;
      }
   }
}

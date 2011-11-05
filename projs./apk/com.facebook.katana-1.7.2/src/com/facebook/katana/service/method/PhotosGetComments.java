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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class PhotosGetComments extends ApiMethod {

   private final List<FacebookPhotoComment> mComments;


   public PhotosGetComments(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos_getcomments", var6, var5);
      this.mParams.put("session_key", var3);
      Map var12 = this.mParams;
      StringBuilder var13 = (new StringBuilder()).append("");
      long var14 = System.currentTimeMillis();
      String var16 = var13.append(var14).toString();
      var12.put("call_id", var16);
      this.mParams.put("pid", var4);
      ArrayList var19 = new ArrayList();
      this.mComments = var19;
   }

   public List<FacebookPhotoComment> getComments() {
      return this.mComments;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         FacebookApiException var4 = new FacebookApiException(var1);
         if(var4.getErrorCode() != -1) {
            throw var4;
         }
      } else {
         JsonToken var5 = JsonToken.START_ARRAY;
         if(var2 == var5) {
            while(true) {
               JsonToken var6 = JsonToken.END_ARRAY;
               if(var2 == var6) {
                  return;
               }

               JsonToken var7 = JsonToken.START_OBJECT;
               if(var2 == var7) {
                  List var8 = this.mComments;
                  FacebookPhotoComment var9 = new FacebookPhotoComment(var1);
                  var8.add(var9);
               }

               var2 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhotoTag;
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

public class PhotosGetTags extends ApiMethod {

   private final List<FacebookPhotoTag> mTags;


   public PhotosGetTags(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos.getTags", var6, var5);
      this.mParams.put("session_key", var3);
      Map var12 = this.mParams;
      StringBuilder var13 = (new StringBuilder()).append("");
      long var14 = System.currentTimeMillis();
      String var16 = var13.append(var14).toString();
      var12.put("call_id", var16);
      this.mParams.put("pids", var4);
      ArrayList var19 = new ArrayList();
      this.mTags = var19;
   }

   public List<FacebookPhotoTag> getTags() {
      return this.mTags;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         throw new FacebookApiException(var1);
      } else {
         JsonToken var4 = JsonToken.START_ARRAY;
         if(var2 == var4) {
            while(true) {
               JsonToken var5 = JsonToken.END_ARRAY;
               if(var2 == var5) {
                  return;
               }

               JsonToken var6 = JsonToken.START_OBJECT;
               if(var2 == var6) {
                  List var7 = this.mTags;
                  FacebookPhotoTag var8 = new FacebookPhotoTag(var1);
                  var7.add(var8);
               }

               var2 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}

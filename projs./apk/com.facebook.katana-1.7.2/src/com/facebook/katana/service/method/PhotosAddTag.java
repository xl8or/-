package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhotoTag;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class PhotosAddTag extends ApiMethod {

   private final List<FacebookPhotoTag> m_tags;


   public PhotosAddTag(Context var1, Intent var2, String var3, String var4, String var5, ApiMethodListener var6) {
      String var7 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos.addTag", var7, var6);
      this.mParams.put("session_key", var3);
      Map var13 = this.mParams;
      StringBuilder var14 = (new StringBuilder()).append("");
      long var15 = System.currentTimeMillis();
      String var17 = var14.append(var15).toString();
      var13.put("call_id", var17);
      this.mParams.put("pid", var4);
      this.mParams.put("tags", var5);
      ArrayList var21 = new ArrayList();
      this.m_tags = var21;
   }

   public List<FacebookPhotoTag> getTags() {
      return this.m_tags;
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         FBJsonFactory var3 = mJsonFactory;
         String var4 = (String)this.mParams.get("tags");
         JsonParser var5 = var3.createJsonParser(var4);
         JsonToken var6 = var5.nextToken();
         JsonToken var7 = var5.getCurrentToken();
         JsonToken var8 = JsonToken.START_ARRAY;
         if(var7 == var8) {
            while(true) {
               JsonToken var9 = JsonToken.END_ARRAY;
               if(var7 == var9) {
                  return;
               }

               JsonToken var10 = JsonToken.START_OBJECT;
               if(var7 == var10) {
                  List var11 = this.m_tags;
                  FacebookPhotoTag var12 = new FacebookPhotoTag(var5);
                  var11.add(var12);
               }

               var7 = var5.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}

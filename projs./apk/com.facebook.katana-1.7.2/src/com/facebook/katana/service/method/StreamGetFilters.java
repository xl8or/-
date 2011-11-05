package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class StreamGetFilters extends ApiMethod {

   private String m_newsFeedFilter;


   public StreamGetFilters(Context var1, Intent var2, String var3, ApiMethodListener var4) {
      String var5 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "stream.getFilters", var5, var4);
      this.mParams.put("session_key", var3);
   }

   private void parseFilter(JsonParser var1) throws JsonParseException, IOException {
      String var2 = null;
      String var3 = null;
      JsonToken var4 = var1.nextToken();

      while(true) {
         JsonToken var5 = JsonToken.END_OBJECT;
         if(var4 == var5) {
            if(var2 == null) {
               return;
            }

            if(!var2.equals("News Feed")) {
               return;
            }

            this.m_newsFeedFilter = var3;
            return;
         }

         JsonToken var6 = JsonToken.VALUE_STRING;
         if(var4 == var6) {
            String var7 = var1.getCurrentName();
            if(var7.equals("name")) {
               var2 = var1.getText();
            } else if(var7.equals("filter_key")) {
               var3 = var1.getText();
            }
         }

         var4 = var1.nextToken();
      }
   }

   public String getNewsFeedFilter() {
      return this.m_newsFeedFilter;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         throw new FacebookApiException(var1);
      } else {
         JsonToken var4 = JsonToken.START_ARRAY;
         if(var2 == var4) {
            JsonToken var5 = var1.nextToken();

            while(true) {
               JsonToken var6 = JsonToken.END_ARRAY;
               if(var5 == var6) {
                  return;
               }

               JsonToken var7 = JsonToken.START_OBJECT;
               if(var5 == var7) {
                  this.parseFilter(var1);
               }

               var5 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}

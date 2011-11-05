package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONException;
import org.json.JSONStringer;

public class FqlMultiQuery extends ApiMethod {

   private final Map<String, FqlQuery> mQueries;


   public FqlMultiQuery(Context var1, Intent var2, String var3, LinkedHashMap<String, FqlQuery> var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiReadUrl(var1);
      super(var1, var2, "GET", "fql.multiquery", var6, var5);
      this.mQueries = var4;
      Map var11 = this.mParams;
      StringBuilder var12 = (new StringBuilder()).append("");
      long var13 = System.currentTimeMillis();
      String var15 = var12.append(var13).toString();
      var11.put("call_id", var15);
      JSONStringer var17 = new JSONStringer();

      try {
         JSONStringer var18 = var17.object();
         Iterator var19 = var4.entrySet().iterator();

         while(var19.hasNext()) {
            Entry var20 = (Entry)var19.next();
            String var21 = (String)var20.getKey();
            var17.key(var21);
            String var23 = ((FqlQuery)var20.getValue()).getQueryString();
            var17.value(var23);
         }

         JSONStringer var27 = var17.endObject();
         Map var28 = this.mParams;
         String var29 = var17.toString();
         var28.put("queries", var29);
      } catch (JSONException var31) {
         ;
      }

      if(var3 != null) {
         this.mParams.put("session_key", var3);
      }
   }

   public void addAuthenticationData(FacebookSessionInfo var1) {
      super.addAuthenticationData(var1);
      Iterator var2 = this.mQueries.values().iterator();

      while(var2.hasNext()) {
         ((FqlQuery)var2.next()).addAuthenticationData(var1);
      }

   }

   protected String generateLogParams() {
      StringBuilder var1 = new StringBuilder(500);
      StringBuilder var2 = var1.append(",\"method\":\"");
      String var3 = this.mFacebookMethod;
      var1.append(var3);
      StringBuilder var5 = var1.append("\",\"args\":\"");

      StringBuilder var9;
      for(Iterator var6 = this.mQueries.values().iterator(); var6.hasNext(); var9 = var1.append(':')) {
         String var7 = ((FqlQuery)var6.next()).getSanitizedQueryString();
         var1.append(var7);
      }

      return var1.toString();
   }

   public FqlQuery getQueryByName(String var1) {
      return (FqlQuery)this.mQueries.get(var1);
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var3 = var1.getCurrentToken();
      JsonToken var4 = JsonToken.START_OBJECT;
      if(var3 == var4) {
         throw new FacebookApiException(var1);
      } else {
         JsonToken var5 = JsonToken.START_ARRAY;
         if(var3 != var5) {
            throw new IOException("Unexpected JSON response");
         } else {
            JsonToken var6 = var1.nextToken();

            while(true) {
               JsonToken var7 = JsonToken.END_ARRAY;
               if(var6 == var7) {
                  return;
               }

               JsonToken var8 = JsonToken.START_OBJECT;
               if(var6 != var8) {
                  throw new IOException("Unexpected JSON response");
               }

               Object var9 = null;
               Object var10 = null;
               FqlQuery var11 = null;
               JsonToken var12 = var1.nextToken();

               while(true) {
                  JsonToken var13 = JsonToken.END_OBJECT;
                  if(var12 == var13) {
                     var6 = var1.nextToken();
                     break;
                  }

                  JsonToken var14 = JsonToken.FIELD_NAME;
                  if(var12 == var14) {
                     String var15 = var1.getText();
                     JsonToken var16 = var1.nextToken();
                     if(var15.equals("fql_result_set")) {
                        if(var11 == null) {
                           JsonLocation var17 = var1.getCurrentLocation();
                           var1.skipChildren();
                           JsonLocation var18 = var1.getCurrentLocation();
                        } else {
                           var11.parseJSON(var1);
                        }
                     } else if(var15.equals("name")) {
                        Map var19 = this.mQueries;
                        String var20 = var1.getText();
                        var11 = (FqlQuery)var19.get(var20);
                        if(var9 != null) {
                           int var21 = (int)((JsonLocation)var9).getCharOffset();
                           int var22 = (int)((JsonLocation)var10).getCharOffset() + 1;
                           String var23 = var2.substring(var21, var22);
                           var11.parseResponse(var23);
                        }
                     }
                  }

                  var12 = var1.nextToken();
               }
            }
         }
      }
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      JsonParser var2 = mJsonFactory.createJsonParser(var1);
      JsonToken var3 = var2.nextToken();
      this.parseJSON(var2, var1);
   }
}

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONArray;

public class BatchRun extends ApiMethod {

   private final List<ApiMethod> mMethods;


   public BatchRun(Context var1, Intent var2, String var3, List<ApiMethod> var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "batch.run", var6, var5);

      try {
         JSONArray var11 = new JSONArray();
         Iterator var12 = var4.iterator();

         while(var12.hasNext()) {
            ApiMethod var13 = (ApiMethod)var12.next();
            var13.addCommonParameters();
            var13.addSignature();
            StringBuilder var14 = var13.buildQueryString();
            var11.put(var14);
         }

         Map var23 = this.mParams;
         String var24 = "session_key";
         var23.put(var24, var3);
         Map var27 = this.mParams;
         StringBuilder var28 = (new StringBuilder()).append("");
         long var29 = System.currentTimeMillis();
         String var31 = var28.append(var29).toString();
         var27.put("call_id", var31);
         Map var33 = this.mParams;
         String var34 = var11.toString();
         var33.put("method_feed", var34);
      } catch (NoSuchAlgorithmException var42) {
         byte var19 = 0;
         Object var20 = null;
         var5.onOperationComplete(this, var19, (String)var20, var42);
      } catch (UnsupportedEncodingException var43) {
         byte var39 = 0;
         Object var40 = null;
         var5.onOperationComplete(this, var39, (String)var40, var43);
      }

      this.mMethods = var4;
   }

   public void addAuthenticationData(FacebookSessionInfo var1) {
      super.addAuthenticationData(var1);
      Iterator var2 = this.mMethods.iterator();

      while(var2.hasNext()) {
         ((ApiMethod)var2.next()).addAuthenticationData(var1);
      }

   }

   protected String generateLogParams() {
      StringBuilder var1 = new StringBuilder(500);
      StringBuilder var2 = var1.append(",\"method\":\"");
      String var3 = this.mFacebookMethod;
      var1.append(var3);
      StringBuilder var5 = var1.append("\",\"args\":\"");

      StringBuilder var9;
      for(Iterator var6 = this.mMethods.iterator(); var6.hasNext(); var9 = var1.append(':')) {
         String var7 = ((ApiMethod)var6.next()).mFacebookMethod;
         var1.append(var7);
      }

      return var1.toString();
   }

   public List<ApiMethod> getMethods() {
      return this.mMethods;
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
            byte var6 = 0;

            while(true) {
               JsonToken var7 = JsonToken.END_ARRAY;
               if(var5 == var7) {
                  return;
               }

               JsonToken var8 = JsonToken.VALUE_STRING;
               if(var5 == var8) {
                  int var9 = this.mMethods.size();
                  if(var6 >= var9) {
                     StringBuilder var10 = (new StringBuilder()).append("Methods index: ").append(var6).append(", size: ");
                     int var11 = this.mMethods.size();
                     String var12 = var10.append(var11).toString();
                     throw new IOException(var12);
                  }

                  ApiMethod var13 = (ApiMethod)this.mMethods.get(var6);
                  String var14 = var1.getText();
                  var13.parseResponse(var14);
                  int var15 = var6 + 1;
               }

               var5 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}

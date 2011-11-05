package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FqlGetFacebookAffiliation extends FqlQuery {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String FACEBOOK_NETWORK_ID = "50431648";
   private static final String TAG = "FqlGetFacebookAffiliation";


   static {
      byte var0;
      if(!FqlGetFacebookAffiliation.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public FqlGetFacebookAffiliation(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
      String var7 = buildQuery(var5);
      super(var1, var2, var3, var7, var4);
   }

   private static String buildQuery(long var0) {
      StringBuilder var2 = new StringBuilder("SELECT affiliations FROM user WHERE uid=");
      var2.append(var0);
      return var2.toString();
   }

   private static boolean parseAffiliationsJSON(JsonParser var0) throws JsonParseException, IOException {
      JsonToken var1 = var0.getCurrentToken();
      if(!$assertionsDisabled) {
         JsonToken var2 = JsonToken.START_ARRAY;
         if(var1 != var2) {
            throw new AssertionError();
         }
      }

      JsonToken var3 = var0.nextToken();

      while(true) {
         JsonToken var4 = JsonToken.END_ARRAY;
         boolean var9;
         if(var3 == var4) {
            var9 = false;
            return var9;
         }

         JsonToken var5 = JsonToken.START_OBJECT;
         if(var3 == var5) {
            var3 = var0.nextToken();

            while(true) {
               JsonToken var6 = JsonToken.END_OBJECT;
               if(var3 == var6) {
                  break;
               }

               label45: {
                  JsonToken var7 = JsonToken.VALUE_STRING;
                  if(var3 != var7) {
                     JsonToken var8 = JsonToken.VALUE_NUMBER_INT;
                     if(var3 != var8) {
                        break label45;
                     }
                  }

                  if(var0.getCurrentName().equals("nid") && var0.getText().equals("50431648")) {
                     var9 = true;
                     return var9;
                  }
               }

               label34: {
                  JsonToken var10 = JsonToken.START_OBJECT;
                  if(var3 != var10) {
                     JsonToken var11 = JsonToken.START_ARRAY;
                     if(var3 != var11) {
                        break label34;
                     }
                  }

                  var0.skipChildren();
               }

               var3 = var0.nextToken();
            }
         } else {
            JsonToken var12 = JsonToken.START_ARRAY;
            if(var3 == var12) {
               var0.skipChildren();
            }
         }

         var3 = var0.nextToken();
      }
   }

   private static boolean parseUserArrayJSON(JsonParser var0) throws JsonParseException, IOException {
      JsonToken var1 = var0.getCurrentToken();
      if(!$assertionsDisabled) {
         JsonToken var2 = JsonToken.START_ARRAY;
         if(var1 != var2) {
            throw new AssertionError();
         }
      }

      while(true) {
         JsonToken var3 = JsonToken.END_ARRAY;
         boolean var10;
         if(var1 == var3) {
            var10 = false;
            return var10;
         }

         JsonToken var4 = JsonToken.START_OBJECT;
         if(var1 == var4) {
            Object var5 = null;
            var1 = var0.nextToken();

            while(true) {
               JsonToken var6 = JsonToken.END_OBJECT;
               if(var1 == var6) {
                  break;
               }

               JsonToken var7 = JsonToken.FIELD_NAME;
               if(var1 == var7) {
                  String var8 = var0.getText();
               } else {
                  JsonToken var9 = JsonToken.START_ARRAY;
                  if(var1 == var9 && var5 != null && ((String)var5).equals("affiliations")) {
                     var10 = parseAffiliationsJSON(var0);
                     return var10;
                  }
               }

               var1 = var0.nextToken();
            }
         }

         var1 = var0.nextToken();
      }
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      byte var3 = 0;
      JsonToken var4 = JsonToken.START_ARRAY;
      if(var2 == var4) {
         var3 = parseUserArrayJSON(var1);
      }

      FacebookAffiliation.setIsEmployee(this.mContext, (boolean)var3);
   }
}

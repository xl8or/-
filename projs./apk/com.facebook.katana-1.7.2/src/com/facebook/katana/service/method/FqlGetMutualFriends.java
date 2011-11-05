package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FqlGetMutualFriends extends FqlQuery {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String TAG = "FqlGetMutualFriends";
   private final Map<Long, List<Long>> mMutualFriends;


   static {
      byte var0;
      if(!FqlGetMutualFriends.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public FqlGetMutualFriends(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, String var7) {
      String var8 = buildQuery(var5, var7);
      super(var1, var2, var3, var8, var4);
      HashMap var14 = new HashMap();
      this.mMutualFriends = var14;
   }

   protected static String buildQuery(long var0, String var2) {
      StringBuilder var3 = new StringBuilder("SELECT uid1, uid2 FROM friend WHERE uid1 IN (SELECT uid1  FROM friend  WHERE uid2=");
      String var4 = String.valueOf(var0);
      var3.append(var4);
      StringBuilder var6 = var3.append(") AND ");
      var3.append(var2);
      return var3.toString();
   }

   public Map<Long, List<Long>> getMutualFriends() {
      return Collections.unmodifiableMap(this.mMutualFriends);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      if(!$assertionsDisabled) {
         JsonToken var3 = JsonToken.START_ARRAY;
         if(var2 != var3) {
            throw new AssertionError();
         }
      }

      JsonToken var4 = var1.nextToken();

      while(true) {
         JsonToken var5 = JsonToken.END_ARRAY;
         if(var4 == var5) {
            return;
         }

         JsonToken var6 = JsonToken.START_OBJECT;
         if(var4 == var6) {
            Long var7 = null;
            Long var8 = null;
            var4 = var1.nextToken();

            while(true) {
               JsonToken var9 = JsonToken.END_OBJECT;
               if(var4 == var9) {
                  if(var7 != null && var8 != null) {
                     Object var15 = (List)this.mMutualFriends.get(var8);
                     if(var15 == null) {
                        var15 = new ArrayList();
                        this.mMutualFriends.put(var8, var15);
                     }

                     ((List)var15).add(var7);
                  } else {
                     Log.e("FqlGetMutualFriends", "Missing uid1 or uid2 from response");
                  }
                  break;
               }

               label49: {
                  JsonToken var10 = JsonToken.VALUE_NUMBER_INT;
                  if(var4 != var10) {
                     JsonToken var11 = JsonToken.VALUE_STRING;
                     if(var4 != var11) {
                        JsonToken var12 = JsonToken.START_OBJECT;
                        if(var4 != var12) {
                           JsonToken var13 = JsonToken.START_ARRAY;
                           if(var4 != var13) {
                              break label49;
                           }
                        }

                        var1.skipChildren();
                        break label49;
                     }
                  }

                  if(var1.getCurrentName() == "uid1") {
                     var7 = Long.valueOf(var1.getText());
                  } else if(var1.getCurrentName() == "uid2") {
                     var8 = Long.valueOf(var1.getText());
                  }
               }

               var4 = var1.nextToken();
            }
         } else {
            JsonToken var18 = JsonToken.START_ARRAY;
            if(var4 == var18) {
               var1.skipChildren();
            }
         }

         JsonToken var14 = var1.nextToken();
      }
   }
}

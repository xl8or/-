package com.facebook.katana.features.composer;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.MinorStatusModel;
import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.service.method.FqlGetMinorStatus;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

class MinorStatusClient implements ManagedDataStore.Client<Object, Boolean, Object> {

   MinorStatusClient() {}

   public Boolean deserialize(String var1) {
      List var4;
      Boolean var6;
      try {
         JsonParser var2 = (new FBJsonFactory()).createJsonParser(var1);
         JsonToken var3 = var2.nextToken();
         var4 = JMParser.parseObjectListJson(var2, MinorStatusModel.class);
      } catch (JsonParseException var10) {
         var6 = null;
         return var6;
      } catch (IOException var11) {
         var6 = null;
         return var6;
      } catch (JMException var12) {
         var6 = null;
         return var6;
      }

      if(var4 != null && var4.size() == 1) {
         var6 = Boolean.valueOf(((MinorStatusModel)var4.get(0)).isMinor);
      } else {
         var6 = null;
      }

      return var6;
   }

   public int getCacheTtl(Object var1, Boolean var2) {
      int var3;
      if(Boolean.FALSE.equals(var2)) {
         var3 = 604800;
      } else {
         var3 = 10800;
      }

      return var3;
   }

   public String getKey(Object var1) {
      return "fql:user_minor_status";
   }

   public int getPersistentStoreTtl(Object var1, Boolean var2) {
      return this.getCacheTtl(var1, var2);
   }

   public String initiateNetworkRequest(Context var1, Object var2, NetworkRequestCallback<Object, Boolean, Object> var3) {
      AppSession var4 = AppSession.getActiveSession(var1, (boolean)0);
      String var8;
      if(var4 != null) {
         FacebookSessionInfo var5 = var4.getSessionInfo();
         if(var5 != null) {
            long var6 = var5.userId;
            var8 = FqlGetMinorStatus.get(var1, var3, var6);
            return var8;
         }
      }

      var8 = null;
      return var8;
   }

   public boolean staleDataAcceptable(Object var1, Boolean var2) {
      return true;
   }
}

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

class FqlGetProfileGeneric<T extends FacebookProfile> extends FqlGeneratedQuery {

   protected final Class<? extends T> mCls;
   protected final Map<Long, T> mProfiles;


   FqlGetProfileGeneric(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5, Class<? extends T> var6) {
      super(var1, var2, var3, var4, "profile", var5, var6);
      HashMap var14 = new HashMap();
      this.mProfiles = var14;
      this.mCls = var6;
   }

   protected Map<Long, T> getProfiles() {
      return Collections.unmodifiableMap(this.mProfiles);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      Class var2 = this.mCls;
      Iterator var3 = JMParser.parseObjectListJson(var1, var2).iterator();

      while(var3.hasNext()) {
         FacebookProfile var4 = (FacebookProfile)var3.next();
         Map var5 = this.mProfiles;
         Long var6 = Long.valueOf(var4.mId);
         var5.put(var6, var4);
      }

   }
}

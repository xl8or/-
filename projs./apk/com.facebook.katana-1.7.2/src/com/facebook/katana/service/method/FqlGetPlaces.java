package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

class FqlGetPlaces extends FqlGeneratedQuery {

   private static final String TAG = "FqlGetPlaces";
   private Map<Long, FacebookPlace> mPlaces;


   public FqlGetPlaces(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      super(var1, var2, var3, var4, "place", var5, FacebookPlace.class);
   }

   public Map<Long, FacebookPlace> getPlaces() {
      return Collections.unmodifiableMap(this.mPlaces);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, FacebookPlace.class);
      LinkedHashMap var3 = new LinkedHashMap();
      this.mPlaces = var3;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         FacebookPlace var5 = (FacebookPlace)var4.next();
         Map var6 = this.mPlaces;
         Long var7 = Long.valueOf(var5.mPageId);
         var6.put(var7, var5);
      }

   }
}

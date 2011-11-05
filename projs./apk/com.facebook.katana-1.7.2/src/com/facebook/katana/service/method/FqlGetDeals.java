package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookDeal;
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

public class FqlGetDeals extends FqlGeneratedQuery {

   private static final String TAG = "FqlGetDeals";
   protected Map<Long, FacebookDeal> mDeals;


   public FqlGetDeals(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      super(var1, var2, var3, var4, "checkin_promotion", var5, FacebookDeal.class);
   }

   public Map<Long, FacebookDeal> getDeals() {
      return Collections.unmodifiableMap(this.mDeals);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, FacebookDeal.class);
      LinkedHashMap var3 = new LinkedHashMap();
      this.mDeals = var3;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         FacebookDeal var5 = (FacebookDeal)var4.next();
         Map var6 = this.mDeals;
         Long var7 = Long.valueOf(var5.mPageId);
         var6.put(var7, var5);
      }

   }
}

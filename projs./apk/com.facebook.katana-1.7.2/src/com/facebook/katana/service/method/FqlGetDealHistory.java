package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookDealHistory;
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

public class FqlGetDealHistory extends FqlGeneratedQuery {

   private static final String TAG = "FqlGetDealHistory";
   protected Map<Long, FacebookDealHistory> mDealHistories;


   public FqlGetDealHistory(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      super(var1, var2, var3, var4, "checkin_promotion_claim_history", var5, FacebookDealHistory.class);
   }

   public Map<Long, FacebookDealHistory> getDealHistories() {
      return Collections.unmodifiableMap(this.mDealHistories);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, FacebookDealHistory.class);
      LinkedHashMap var3 = new LinkedHashMap();
      this.mDealHistories = var3;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         FacebookDealHistory var5 = (FacebookDealHistory)var4.next();
         Map var6 = this.mDealHistories;
         Long var7 = Long.valueOf(var5.mDealId);
         var6.put(var7, var5);
      }

   }
}

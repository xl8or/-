package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookDeal;
import com.facebook.katana.model.FacebookDealHistory;
import com.facebook.katana.model.FacebookDealStatus;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetDealHistory;
import com.facebook.katana.service.method.FqlGetDealStatus;
import com.facebook.katana.service.method.FqlGetDeals;
import com.facebook.katana.service.method.FqlGetPages;
import com.facebook.katana.service.method.FqlGetPlaces;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetPlaceById extends FqlMultiQuery implements ApiMethodCallback {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String TAG = "FqlGetPlaceById";
   private AppSessionListener.GetObjectListener<FacebookPlace> mCallback;
   private long mId;
   protected FacebookPlace mPlace;


   static {
      byte var0;
      if(!FqlGetPlaceById.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public FqlGetPlaceById(Context var1, Intent var2, String var3, long var4, AppSessionListener.GetObjectListener<FacebookPlace> var6) {
      LinkedHashMap var7 = buildQueries(var1, var2, var3, var4);
      super(var1, var2, var3, var7, (ApiMethodListener)null);
      this.mId = var4;
      this.mCallback = var6;
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long var3) {
      LinkedHashMap var5 = new LinkedHashMap();
      Object[] var6 = new Object[1];
      Long var7 = Long.valueOf(var3);
      var6[0] = var7;
      String var8 = String.format("page_id IN ( %d )", var6);
      FqlGetPlaces var12 = new FqlGetPlaces(var0, var1, var2, (ApiMethodListener)null, var8);
      String var14 = "places";
      var5.put(var14, var12);
      FqlGetPages var20 = new FqlGetPages(var0, var1, var2, (ApiMethodListener)null, var8, FacebookPage.class);
      String var22 = "pages";
      var5.put(var22, var20);
      FqlGetDeals var28 = new FqlGetDeals(var0, var1, var2, (ApiMethodListener)null, "creator_id IN (SELECT page_id FROM #places)");
      String var30 = "deals";
      var5.put(var30, var28);
      FqlGetDealStatus var36 = new FqlGetDealStatus(var0, var1, var2, (ApiMethodListener)null, "promotion_id IN (SELECT promotion_id FROM #deals)");
      String var38 = "deal_status";
      var5.put(var38, var36);
      FqlGetDealHistory var44 = new FqlGetDealHistory(var0, var1, var2, (ApiMethodListener)null, "promotion_id IN (SELECT promotion_id FROM #deals)");
      String var46 = "deal_history";
      var5.put(var46, var44);
      return var5;
   }

   public static String loadPlaceById(Context var0, long var1, AppSessionListener.GetObjectListener<FacebookPlace> var3) {
      AppSession var4 = AppSession.getActiveSession(var0, (boolean)0);
      String var5 = var4.getSessionInfo().sessionKey;
      FqlGetPlaceById var10 = new FqlGetPlaceById(var0, (Intent)null, var5, var1, var3);
      short var14 = 1001;
      short var15 = 1001;
      Object var16 = null;
      return var4.postToService(var0, var10, var14, var15, (Bundle)var16);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      if(var5 == 200) {
         AppSessionListener.GetObjectListener var8 = this.mCallback;
         FacebookPlace var9 = this.mPlace;
         var8.onObjectLoaded(var9);
      } else if(!$assertionsDisabled && var7 == null) {
         throw new AssertionError();
      } else {
         this.mCallback.onLoadError(var7);
      }
   }

   public FacebookPlace getPlace() {
      return this.mPlace;
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      Map var3 = ((FqlGetPlaces)this.getQueryByName("places")).getPlaces();
      Map var4 = ((FqlGetPages)this.getQueryByName("pages")).getPages();
      Map var5 = ((FqlGetDeals)this.getQueryByName("deals")).getDeals();
      Map var6 = ((FqlGetDealStatus)this.getQueryByName("deal_status")).getDealStatuses();
      Map var7 = ((FqlGetDealHistory)this.getQueryByName("deal_history")).getDealHistories();
      Long var8 = Long.valueOf(this.mId);
      FacebookPlace var9 = (FacebookPlace)var3.get(var8);
      this.mPlace = var9;
      FacebookPlace var10 = this.mPlace;
      Long var11 = Long.valueOf(this.mId);
      FacebookPage var12 = (FacebookPage)var4.get(var11);
      var10.setPageInfo(var12);
      if(!$assertionsDisabled && this.mPlace.getPageInfo() == null) {
         throw new AssertionError();
      } else {
         Long var13 = Long.valueOf(this.mId);
         FacebookDeal var14 = (FacebookDeal)var5.get(var13);
         if(var14 != null) {
            Long var15 = Long.valueOf(var14.mDealId);
            FacebookDealStatus var16 = (FacebookDealStatus)var6.get(var15);
            Long var17 = Long.valueOf(var14.mDealId);
            FacebookDealHistory var18 = (FacebookDealHistory)var7.get(var17);
            var14.mDealHistory = var18;
            var14.mDealStatus = var16;
         }

         this.mPlace.setDealInfo(var14);
      }
   }
}

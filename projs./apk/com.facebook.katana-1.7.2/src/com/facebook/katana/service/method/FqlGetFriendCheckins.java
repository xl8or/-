package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookApp;
import com.facebook.katana.model.FacebookCheckin;
import com.facebook.katana.model.FacebookCheckinDetails;
import com.facebook.katana.model.FacebookDeal;
import com.facebook.katana.model.FacebookDealHistory;
import com.facebook.katana.model.FacebookDealStatus;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.service.method.FqlGetAppsProfile;
import com.facebook.katana.service.method.FqlGetDealHistory;
import com.facebook.katana.service.method.FqlGetDealStatus;
import com.facebook.katana.service.method.FqlGetDeals;
import com.facebook.katana.service.method.FqlGetPages;
import com.facebook.katana.service.method.FqlGetPlaces;
import com.facebook.katana.service.method.FqlGetUsersProfile;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetFriendCheckins extends FqlMultiQuery {

   private static final String TAG = "FqlGetFriendCheckins";
   protected List<FacebookCheckin> mCheckins;


   public FqlGetFriendCheckins(Context var1, Intent var2, String var3, ApiMethodListener var4) {
      LinkedHashMap var5 = buildQueries(var1, var2, var3);
      super(var1, var2, var3, var5, var4);
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2) {
      LinkedHashMap var3 = new LinkedHashMap();
      FqlGetFriendCheckins.FqlGetCheckins var4 = new FqlGetFriendCheckins.FqlGetCheckins(var0, var1, var2, (ApiMethodListener)null);
      var3.put("checkins", var4);
      FqlGetFriendCheckins.FqlGetCheckinDetails var9 = new FqlGetFriendCheckins.FqlGetCheckinDetails(var0, var1, var2, (ApiMethodListener)null, "checkin_id IN (SELECT checkin_id FROM #checkins)");
      var3.put("details", var9);
      FqlGetUsersProfile var14 = new FqlGetUsersProfile(var0, var1, var2, (ApiMethodListener)null, "uid IN (SELECT actor_uid FROM #checkins)", FacebookUser.class);
      var3.put("users", var14);
      FqlGetPlaces var19 = new FqlGetPlaces(var0, var1, var2, (ApiMethodListener)null, "page_id IN (SELECT page_id FROM #details)");
      var3.put("places", var19);
      FqlGetPages var24 = new FqlGetPages(var0, var1, var2, (ApiMethodListener)null, "page_id IN (SELECT page_id FROM #details)", FacebookPage.class);
      var3.put("pages", var24);
      FqlGetAppsProfile var29 = new FqlGetAppsProfile(var0, var1, var2, (ApiMethodListener)null, "app_id IN (SELECT app_id FROM #details) AND is_facebook_app=0");
      var3.put("apps", var29);
      FqlGetDeals var34 = new FqlGetDeals(var0, var1, var2, (ApiMethodListener)null, "creator_id IN (SELECT page_id FROM #places)");
      var3.put("deals", var34);
      FqlGetDealStatus var39 = new FqlGetDealStatus(var0, var1, var2, (ApiMethodListener)null, "promotion_id IN (SELECT promotion_id FROM #deals)");
      var3.put("deal_status", var39);
      FqlGetDealHistory var44 = new FqlGetDealHistory(var0, var1, var2, (ApiMethodListener)null, "promotion_id IN (SELECT promotion_id FROM #deals)");
      var3.put("deal_history", var44);
      return var3;
   }

   public List<FacebookCheckin> getCheckins() {
      return Collections.unmodifiableList(this.mCheckins);
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      String var4 = "checkins";
      List var5 = ((FqlGetFriendCheckins.FqlGetCheckins)this.getQueryByName(var4)).mCheckins;
      String var7 = "details";
      Map var8 = ((FqlGetFriendCheckins.FqlGetCheckinDetails)this.getQueryByName(var7)).mDetails;
      String var10 = "users";
      Map var11 = ((FqlGetUsersProfile)this.getQueryByName(var10)).getUsers();
      String var13 = "apps";
      Map var14 = ((FqlGetAppsProfile)this.getQueryByName(var13)).getApps();
      String var16 = "places";
      Map var17 = ((FqlGetPlaces)this.getQueryByName(var16)).getPlaces();
      String var19 = "pages";
      Map var20 = ((FqlGetPages)this.getQueryByName(var19)).getPages();
      String var22 = "deals";
      Map var23 = ((FqlGetDeals)this.getQueryByName(var22)).getDeals();
      String var25 = "deal_status";
      Map var26 = ((FqlGetDealStatus)this.getQueryByName(var25)).getDealStatuses();
      String var28 = "deal_history";
      Map var29 = ((FqlGetDealHistory)this.getQueryByName(var28)).getDealHistories();
      ArrayList var30 = new ArrayList();
      this.mCheckins = var30;
      Iterator var31 = var5.iterator();

      while(var31.hasNext()) {
         FacebookCheckin var32 = (FacebookCheckin)var31.next();
         Long var33 = Long.valueOf(var32.mActorId);
         FacebookUser var34 = (FacebookUser)var11.get(var33);
         if(var34 != null) {
            var32.setActor(var34);
            Long var37 = Long.valueOf(var32.mCheckinId);
            FacebookCheckinDetails var40 = (FacebookCheckinDetails)var8.get(var37);
            if(var40 != null) {
               var32.setDetails(var40);
               Long var41 = Long.valueOf(var40.mAppId);
               FacebookApp var44 = (FacebookApp)var14.get(var41);
               var40.setAppInfo(var44);
               Long var45 = Long.valueOf(var40.mPageId);
               FacebookPlace var48 = (FacebookPlace)var17.get(var45);
               if(var48 != null) {
                  var40.setPlaceInfo(var48);
                  Long var51 = Long.valueOf(var40.mPageId);
                  FacebookPage var54 = (FacebookPage)var20.get(var51);
                  var48.setPageInfo(var54);
                  Long var57 = Long.valueOf(var40.mPageId);
                  FacebookDeal var60 = (FacebookDeal)var23.get(var57);
                  if(var60 != null) {
                     Long var61 = Long.valueOf(var60.mDealId);
                     FacebookDealStatus var64 = (FacebookDealStatus)var26.get(var61);
                     Long var65 = Long.valueOf(var60.mDealId);
                     FacebookDealHistory var68 = (FacebookDealHistory)var29.get(var65);
                     var60.mDealHistory = var68;
                     var60.mDealStatus = var64;
                  }

                  var48.setDealInfo(var60);
                  List var71 = this.mCheckins;
                  var71.add(var32);
               }
            }
         }
      }

      List var74 = this.mCheckins;
      Comparator var75 = FacebookCheckin.checkinsByTimeComparator;
      Collections.sort(var74, var75);
   }

   static class FqlGetCheckinDetails extends FqlGeneratedQuery {

      private static final String TAG = "FqlGetCheckinDetails";
      Map<Long, FacebookCheckinDetails> mDetails;


      public FqlGetCheckinDetails(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
         super(var1, var2, var3, var4, "checkin", var5, FacebookCheckinDetails.class);
      }

      protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
         List var2 = JMParser.parseObjectListJson(var1, FacebookCheckinDetails.class);
         HashMap var3 = new HashMap();
         this.mDetails = var3;
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            FacebookCheckinDetails var5 = (FacebookCheckinDetails)var4.next();
            Map var6 = this.mDetails;
            Long var7 = Long.valueOf(var5.mCheckinId);
            var6.put(var7, var5);
         }

      }
   }

   static class FqlGetCheckins extends FqlGeneratedQuery {

      private static final String TAG = "FqlGetCheckins";
      List<FacebookCheckin> mCheckins;


      public FqlGetCheckins(Context var1, Intent var2, String var3, ApiMethodListener var4) {
         super(var1, var2, var3, var4, "checkin_activity", "filter=\'friend_activity\'", FacebookCheckin.class);
      }

      protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
         List var2 = JMParser.parseObjectListJson(var1, FacebookCheckin.class);
         this.mCheckins = var2;
      }
   }
}

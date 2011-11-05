package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.features.places.PlacesNearby;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookDeal;
import com.facebook.katana.model.FacebookDealHistory;
import com.facebook.katana.model.FacebookDealStatus;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.GeoRegion;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetDealHistory;
import com.facebook.katana.service.method.FqlGetDealStatus;
import com.facebook.katana.service.method.FqlGetDeals;
import com.facebook.katana.service.method.FqlGetNearbyRegions;
import com.facebook.katana.service.method.FqlGetPages;
import com.facebook.katana.service.method.FqlGetPlaces;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetPlacesNearby extends FqlMultiQuery {

   private static final String TAG = "FqlGetPlacesNearby";
   public NetworkRequestCallback<PlacesNearby.PlacesNearbyArgType, FqlGetPlacesNearby, Object> callback;
   public String filter;
   public Location location;
   protected List<FacebookPlace> mPlaces;
   protected List<GeoRegion> mRegions;
   public double maxDistance;
   public int resultLimit;


   public FqlGetPlacesNearby(Context var1, Intent var2, String var3, ApiMethodListener var4, Location var5, double var6, String var8, int var9, NetworkRequestCallback<PlacesNearby.PlacesNearbyArgType, FqlGetPlacesNearby, Object> var10) {
      double var11 = var5.getLatitude();
      double var13 = var5.getLongitude();
      double var15 = (double)var5.getAccuracy();
      LinkedHashMap var24 = buildQueries(var1, var2, var3, var11, var13, var6, var15, var8, var9);
      super(var1, var2, var3, var24, var4);
      this.location = var5;
      this.maxDistance = var6;
      this.filter = var8;
      this.resultLimit = var9;
      this.callback = var10;
   }

   private static String buildDistanceFunction(double var0, double var2, double var4) {
      Locale var6 = (Locale)false;
      Object[] var7 = new Object[3];
      Double var8 = Double.valueOf(var0);
      var7[0] = var8;
      Double var9 = Double.valueOf(var2);
      var7[1] = var9;
      Double var10 = Double.valueOf(var4);
      var7[2] = var10;
      return String.format(var6, "distance(latitude, longitude, \"%f\", \"%f\", \"%f\")", var7);
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, double var3, double var5, double var7, double var9, String var11, int var12) {
      LinkedHashMap var13 = new LinkedHashMap();
      String var14 = buildWhereClause(var3, var5, var7, var9, var11, var12);
      FqlGetPlaces var18 = new FqlGetPlaces(var0, var1, var2, (ApiMethodListener)null, var14);
      String var20 = "places";
      var13.put(var20, var18);
      FqlGetPages var26 = new FqlGetPages(var0, var1, var2, (ApiMethodListener)null, "page_id IN (SELECT page_id FROM #places)", FacebookPage.class);
      String var28 = "pages";
      var13.put(var28, var26);
      FqlGetDeals var34 = new FqlGetDeals(var0, var1, var2, (ApiMethodListener)null, "creator_id IN (SELECT page_id FROM #places)");
      String var36 = "deals";
      var13.put(var36, var34);
      FqlGetDealStatus var42 = new FqlGetDealStatus(var0, var1, var2, (ApiMethodListener)null, "promotion_id IN (SELECT promotion_id FROM #deals)");
      String var44 = "deal_status";
      var13.put(var44, var42);
      FqlGetDealHistory var50 = new FqlGetDealHistory(var0, var1, var2, (ApiMethodListener)null, "promotion_id IN (SELECT promotion_id FROM #deals)");
      String var52 = "deal_history";
      var13.put(var52, var50);
      Locale var55 = (Locale)false;
      Object[] var56 = new Object[4];
      Double var57 = Double.valueOf(var3);
      var56[0] = var57;
      Double var58 = Double.valueOf(var5);
      var56[1] = var58;
      GeoRegion.Type var59 = GeoRegion.Type.city;
      var56[2] = var59;
      GeoRegion.Type var60 = GeoRegion.Type.state;
      var56[3] = var60;
      String var62 = "latitude=\'%f\' and longitude=\'%f\' and type in (\'%s\',\'%s\')";
      String var64 = String.format(var55, var62, var56);
      FqlGetNearbyRegions var68 = new FqlGetNearbyRegions(var0, var1, var2, (ApiMethodListener)null, var64);
      String var70 = "nearby_regions";
      var13.put(var70, var68);
      return var13;
   }

   private static String buildWhereClause(double var0, double var2, double var4, double var6, String var8, int var9) {
      StringBuilder var10 = new StringBuilder();
      String var17 = buildDistanceFunction(var0, var2, var6);
      var10.append(var17);
      StringBuilder var19 = var10.append("< \"");
      var10.append(var4);
      StringBuilder var21 = var10.append('\"');
      if(var8 != false && var8.length() > 0) {
         StringBuilder var22 = var10.append(" AND CONTAINS (");
         StringBuilder var26 = StringUtils.appendEscapedFQLString(var10, var8);
         StringBuilder var27 = var10.append(")");
      }

      StringBuilder var28 = var10.append(" LIMIT ");
      StringBuilder var31 = var10.append(var9);
      return var10.toString();
   }

   public List<FacebookPlace> getPlaces() {
      List var1;
      if(this.mPlaces != null) {
         var1 = Collections.unmodifiableList(this.mPlaces);
      } else {
         var1 = null;
      }

      return var1;
   }

   public List<GeoRegion> getRegions() {
      List var1;
      if(this.mPlaces != null) {
         var1 = Collections.unmodifiableList(this.mRegions);
      } else {
         var1 = null;
      }

      return var1;
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      Map var3 = ((FqlGetPlaces)this.getQueryByName("places")).getPlaces();
      Map var4 = ((FqlGetPages)this.getQueryByName("pages")).getPages();
      Map var5 = ((FqlGetDeals)this.getQueryByName("deals")).getDeals();
      Map var6 = ((FqlGetDealStatus)this.getQueryByName("deal_status")).getDealStatuses();
      Map var7 = ((FqlGetDealHistory)this.getQueryByName("deal_history")).getDealHistories();
      List var8 = ((FqlGetNearbyRegions)this.getQueryByName("nearby_regions")).regions;
      this.mRegions = var8;
      ArrayList var9 = new ArrayList();
      this.mPlaces = var9;
      Iterator var10 = var3.entrySet().iterator();

      while(var10.hasNext()) {
         Entry var11 = (Entry)var10.next();
         Object var12 = var11.getKey();
         FacebookPage var13 = (FacebookPage)var4.get(var12);
         Object var14 = var11.getKey();
         FacebookDeal var15 = (FacebookDeal)var5.get(var14);
         if(var15 != null) {
            Long var16 = Long.valueOf(var15.mDealId);
            FacebookDealStatus var17 = (FacebookDealStatus)var6.get(var16);
            Long var18 = Long.valueOf(var15.mDealId);
            FacebookDealHistory var19 = (FacebookDealHistory)var7.get(var18);
            var15.mDealHistory = var19;
            var15.mDealStatus = var17;
         }

         ((FacebookPlace)var11.getValue()).setPageInfo(var13);
         ((FacebookPlace)var11.getValue()).setDealInfo(var15);
         List var20 = this.mPlaces;
         Object var21 = var11.getValue();
         var20.add(var21);
      }

   }
}

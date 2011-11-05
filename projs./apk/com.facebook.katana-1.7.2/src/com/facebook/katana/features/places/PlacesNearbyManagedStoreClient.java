package com.facebook.katana.features.places;

import android.content.Context;
import android.location.Location;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.features.places.PlacesNearby;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.util.Utils;

class PlacesNearbyManagedStoreClient implements ManagedDataStore.Client<PlacesNearby.PlacesNearbyArgType, FqlGetPlacesNearby, Object> {

   public static final String TAG = Utils.getClassName(PlacesNearby.class);


   PlacesNearbyManagedStoreClient() {}

   public FqlGetPlacesNearby deserialize(String var1) {
      throw new IllegalStateException("Attempting to deserialize FqlGetPlacesNearby, currentlyunsupported");
   }

   public int getCacheTtl(PlacesNearby.PlacesNearbyArgType var1, FqlGetPlacesNearby var2) {
      return 300;
   }

   public String getKey(PlacesNearby.PlacesNearbyArgType var1) {
      return "places_nearby";
   }

   public int getPersistentStoreTtl(PlacesNearby.PlacesNearbyArgType var1, FqlGetPlacesNearby var2) {
      return 0;
   }

   public String initiateNetworkRequest(Context var1, PlacesNearby.PlacesNearbyArgType var2, NetworkRequestCallback<PlacesNearby.PlacesNearbyArgType, FqlGetPlacesNearby, Object> var3) {
      AppSession var4 = AppSession.getActiveSession(var1, (boolean)1);
      String var5;
      if(var4 == null) {
         var5 = null;
      } else {
         Location var6 = var2.location;
         double var7 = var2.maxDistance;
         String var9 = var2.filter;
         int var10 = var2.resultLimit;
         var5 = var4.getPlacesNearby(var1, var6, var7, var9, var10, var3);
      }

      return var5;
   }

   public boolean staleDataAcceptable(PlacesNearby.PlacesNearbyArgType var1, FqlGetPlacesNearby var2) {
      return false;
   }
}

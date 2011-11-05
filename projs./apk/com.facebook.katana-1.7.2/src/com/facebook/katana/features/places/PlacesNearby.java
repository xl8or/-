package com.facebook.katana.features.places;

import android.content.Context;
import android.location.Location;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.features.places.PlacesNearbyManagedStoreClient;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.util.Utils;

public class PlacesNearby {

   public static final String TAG = Utils.getClassName(PlacesNearby.class);
   protected static ManagedDataStore<PlacesNearby.PlacesNearbyArgType, FqlGetPlacesNearby, Object> store;


   public PlacesNearby() {}

   public static FqlGetPlacesNearby get(Context var0, PlacesNearby.PlacesNearbyArgType var1) {
      return (FqlGetPlacesNearby)getStore().get(var0, var1);
   }

   protected static ManagedDataStore<PlacesNearby.PlacesNearbyArgType, FqlGetPlacesNearby, Object> getStore() {
      if(store == null) {
         PlacesNearbyManagedStoreClient var0 = new PlacesNearbyManagedStoreClient();
         store = new ManagedDataStore(var0);
      }

      return store;
   }

   public static void reset() {
      store = null;
   }

   public static class PlacesNearbyArgType {

      public String filter;
      public Location location;
      public double maxDistance;
      public int resultLimit;


      public PlacesNearbyArgType(Location var1) {
         this(var1, 750.0D, "", 20);
      }

      public PlacesNearbyArgType(Location var1, double var2, String var4, int var5) {
         this.location = var1;
         this.maxDistance = var2;
         this.filter = var4;
         this.resultLimit = var5;
      }

      public boolean equals(Object var1) {
         return true;
      }

      public int hashCode() {
         return 0;
      }
   }
}

package com.facebook.katana.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import com.facebook.katana.util.ReentrantCallback;
import com.facebook.katana.util.StringUtils;
import java.util.Iterator;

public class FBLocationManager {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final int GPS_SHUTDOWN_DELAY_MS = 15000;
   private static final int LOCATION_DESIRED_ACCURACY_M = 33;
   private static final int LOCATION_FREQUENCY_ACCURATE_MIN_TIME_MS = 60000;
   public static final int LOCATION_FREQUENCY_INACCURATE_MIN_TIME_MS = 500;
   public static final int LOCATION_FREQUENCY_MIN_DISTANCE_M = 0;
   private static final int LOCATION_STALE_S = 120;
   private static final int LOCATION_STARTUP_STALE_S = 90;
   private static final int LOCATION_TIMOUT_ERROR_MS = 30000;
   protected static FBLocationManager.LocationDispatcher mDispatcher;
   private static Handler mHandler;
   protected static volatile ReentrantCallback<FBLocationManager.FBLocationListener> mListeners;
   protected static volatile LocationManager mLocationManager;
   private static Runnable mTimeout;
   protected static volatile Runnable mTimeoutError;


   static {
      byte var0;
      if(!FBLocationManager.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      mHandler = new Handler();
   }

   public FBLocationManager() {}

   public static void addLocationListener(Context var0, FBLocationManager.FBLocationListener var1) {
      initialize(var0);
      if(mListeners == null) {
         mListeners = new ReentrantCallback();
      }

      ReentrantCallback var2 = mListeners;
      synchronized(var2) {
         mListeners.addListener(var1);
         mDispatcher.activate();
         if(mDispatcher.mLastKnownLocation != null) {
            Location var3 = mDispatcher.mLastKnownLocation;
            var1.onLocationChanged(var3);
         }

         if(mTimeout != null) {
            Handler var4 = mHandler;
            Runnable var5 = mTimeout;
            var4.removeCallbacks(var5);
         }

         if(mTimeoutError == null) {
            FBLocationManager.1 var6 = new FBLocationManager.1();
            mTimeoutError = var6;
            boolean var7 = mHandler.postDelayed(var6, 30000L);
         }

      }
   }

   public static boolean areLocationServicesEnabled(Context var0) {
      initialize(var0);
      boolean var1;
      if(mLocationManager != null && (mLocationManager.isProviderEnabled("network") || mLocationManager.isProviderEnabled("gps"))) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static Location getRecentLocation(int var0) {
      Location var9;
      if(mDispatcher != null && mDispatcher.mLastKnownLocation != null) {
         long var1 = System.currentTimeMillis();
         long var3 = mDispatcher.mLastKnownLocation.getTime();
         long var5 = var1 - var3;
         long var7 = (long)(var0 * 1000);
         if(var5 <= var7) {
            var9 = mDispatcher.mLastKnownLocation;
            return var9;
         }
      }

      var9 = null;
      return var9;
   }

   public static void initialize(Context var0) {
      if(mLocationManager == null) {
         mLocationManager = (LocationManager)var0.getSystemService("location");
      }

      if(mDispatcher == null) {
         mDispatcher = new FBLocationManager.LocationDispatcher();
      }
   }

   protected static boolean isBetterLocation(Location var0, Location var1) {
      boolean var2;
      if(var1 == null) {
         var2 = true;
      } else {
         long var3 = var0.getTime();
         long var5 = var1.getTime();
         long var7 = var3 - var5;
         boolean var9;
         if(var7 > 120000L) {
            var9 = true;
         } else {
            var9 = false;
         }

         boolean var10;
         if(var7 < -120000L) {
            var10 = true;
         } else {
            var10 = false;
         }

         boolean var11;
         if(var7 > 0L) {
            var11 = true;
         } else {
            var11 = false;
         }

         if(var9) {
            var2 = true;
         } else if(var10) {
            var2 = false;
         } else {
            float var12 = var0.getAccuracy();
            float var13 = var1.getAccuracy();
            int var14 = (int)(var12 - var13);
            if(var14 > 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            boolean var15;
            if(var14 < 0) {
               var15 = true;
            } else {
               var15 = false;
            }

            if(var14 > 200) {
               var9 = true;
            } else {
               var9 = false;
            }

            String var16 = var0.getProvider();
            String var17 = var1.getProvider();
            boolean var18 = StringUtils.saneStringEquals(var16, var17);
            if(var15) {
               var2 = true;
            } else if(var11 && !var10) {
               var2 = true;
            } else if(var11 && !var9 && var18) {
               var2 = true;
            } else {
               var2 = false;
            }
         }
      }

      return var2;
   }

   public static void removeLocationListener(FBLocationManager.FBLocationListener var0) {
      if(mListeners != null) {
         ReentrantCallback var1 = mListeners;
         synchronized(var1) {
            if(!$assertionsDisabled && mDispatcher == null) {
               throw new AssertionError();
            } else {
               mListeners.removeListener(var0);
               if(mListeners.count() == 0) {
                  if(mDispatcher.mHighSampleMode) {
                     FBLocationManager.2 var3 = new FBLocationManager.2();
                     mTimeout = var3;
                     boolean var4 = mHandler.postDelayed(var3, 15000L);
                     if(mTimeoutError != null) {
                        Handler var5 = mHandler;
                        Runnable var6 = mTimeoutError;
                        var5.removeCallbacks(var6);
                        mTimeoutError = null;
                     }
                  } else {
                     mDispatcher.deactivate();
                  }
               }

            }
         }
      }
   }

   protected static boolean validForStartup(Location var0) {
      boolean var5;
      if(var0 != null) {
         long var1 = System.currentTimeMillis();
         long var3 = var0.getTime();
         if(var1 - var3 <= 90000L) {
            var5 = true;
            return var5;
         }
      }

      var5 = false;
      return var5;
   }

   static class 2 implements Runnable {

      2() {}

      public void run() {
         ReentrantCallback var1 = FBLocationManager.mListeners;
         synchronized(var1) {
            FBLocationManager.mDispatcher.deactivate();
         }
      }
   }

   static class 1 implements Runnable {

      1() {}

      public void run() {
         ReentrantCallback var1 = FBLocationManager.mListeners;
         synchronized(var1) {
            Iterator var2 = FBLocationManager.mListeners.getListeners().iterator();

            while(var2.hasNext()) {
               ((FBLocationManager.FBLocationListener)var2.next()).onTimeOut();
            }

            FBLocationManager.mDispatcher.mHighSampleMode = (boolean)1;
            FBLocationManager.mTimeoutError = null;
         }
      }
   }

   protected static class LocationDispatcher implements LocationListener {

      // $FF: synthetic field
      static final boolean $assertionsDisabled;
      protected boolean mEnabled = 0;
      protected boolean mHighSampleMode;
      protected Location mLastKnownLocation;


      static {
         byte var0;
         if(!FBLocationManager.class.desiredAssertionStatus()) {
            var0 = 1;
         } else {
            var0 = 0;
         }

         $assertionsDisabled = (boolean)var0;
      }

      public LocationDispatcher() {}

      public void activate() {
         if(!this.mEnabled) {
            LocationManager var1 = FBLocationManager.mLocationManager;
            var1.requestLocationUpdates("network", 500L, 0.0F, this);
            LocationManager var3 = FBLocationManager.mLocationManager;
            var3.requestLocationUpdates("gps", 500L, 0.0F, this);
            this.mEnabled = (boolean)1;
            this.mHighSampleMode = (boolean)1;
            Location var5 = FBLocationManager.mLocationManager.getLastKnownLocation("gps");
            if(FBLocationManager.validForStartup(var5)) {
               this.mLastKnownLocation = var5;
            } else {
               var5 = FBLocationManager.mLocationManager.getLastKnownLocation("network");
               if(FBLocationManager.validForStartup(var5)) {
                  this.mLastKnownLocation = var5;
               }
            }
         }
      }

      public void deactivate() {
         if(!$assertionsDisabled && this.mEnabled != 1) {
            throw new AssertionError();
         } else {
            FBLocationManager.mLocationManager.removeUpdates(this);
            this.mEnabled = (boolean)0;
            this.mHighSampleMode = (boolean)0;
         }
      }

      public void onLocationChanged(Location var1) {
         Location var2 = this.mLastKnownLocation;
         if(FBLocationManager.isBetterLocation(var1, var2)) {
            this.mLastKnownLocation = var1;
            if(this.mHighSampleMode && var1.hasAccuracy() && var1.getAccuracy() < 33.0F) {
               if(!$assertionsDisabled && this.mEnabled != 1) {
                  throw new AssertionError();
               }

               FBLocationManager.mLocationManager.removeUpdates(this);
               LocationManager var3 = FBLocationManager.mLocationManager;
               var3.requestLocationUpdates("network", 60000L, 0.0F, this);
               LocationManager var5 = FBLocationManager.mLocationManager;
               var5.requestLocationUpdates("gps", 60000L, 0.0F, this);
               this.mHighSampleMode = (boolean)0;
            }

            Iterator var7 = FBLocationManager.mListeners.getListeners().iterator();

            while(var7.hasNext()) {
               ((FBLocationManager.FBLocationListener)var7.next()).onLocationChanged(var1);
            }

            if(FBLocationManager.mTimeoutError != null) {
               Handler var8 = FBLocationManager.mHandler;
               Runnable var9 = FBLocationManager.mTimeoutError;
               var8.removeCallbacks(var9);
               FBLocationManager.mTimeoutError = null;
            }
         }
      }

      public void onProviderDisabled(String var1) {}

      public void onProviderEnabled(String var1) {}

      public void onStatusChanged(String var1, int var2, Bundle var3) {}
   }

   public interface FBLocationListener {

      void onLocationChanged(Location var1);

      void onTimeOut();
   }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FBLocationManager.java

package com.facebook.katana.util;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.os.Handler;
import java.util.Iterator;
import java.util.Set;

// Referenced classes of package com.facebook.katana.util:
//            ReentrantCallback, StringUtils

public class FBLocationManager
{
    protected static class LocationDispatcher
        implements LocationListener
    {

        public void activate()
        {
            if(mEnabled) goto _L2; else goto _L1
_L1:
            Location location;
            FBLocationManager.mLocationManager.requestLocationUpdates("network", 500L, 0F, this);
            FBLocationManager.mLocationManager.requestLocationUpdates("gps", 500L, 0F, this);
            mEnabled = true;
            mHighSampleMode = true;
            location = FBLocationManager.mLocationManager.getLastKnownLocation("gps");
            if(!FBLocationManager.validForStartup(location)) goto _L4; else goto _L3
_L3:
            mLastKnownLocation = location;
_L2:
            return;
_L4:
            Location location1 = FBLocationManager.mLocationManager.getLastKnownLocation("network");
            if(FBLocationManager.validForStartup(location1))
                mLastKnownLocation = location1;
            if(true) goto _L2; else goto _L5
_L5:
        }

        public void deactivate()
        {
            if(!$assertionsDisabled && !mEnabled)
            {
                throw new AssertionError();
            } else
            {
                FBLocationManager.mLocationManager.removeUpdates(this);
                mEnabled = false;
                mHighSampleMode = false;
                return;
            }
        }

        public void onLocationChanged(Location location)
        {
            if(FBLocationManager.isBetterLocation(location, mLastKnownLocation)) goto _L2; else goto _L1
_L1:
            return;
_L2:
            mLastKnownLocation = location;
            if(mHighSampleMode && location.hasAccuracy() && location.getAccuracy() < 33F)
            {
                if(!$assertionsDisabled && !mEnabled)
                    throw new AssertionError();
                FBLocationManager.mLocationManager.removeUpdates(this);
                FBLocationManager.mLocationManager.requestLocationUpdates("network", 60000L, 0F, this);
                FBLocationManager.mLocationManager.requestLocationUpdates("gps", 60000L, 0F, this);
                mHighSampleMode = false;
            }
            for(Iterator iterator = FBLocationManager.mListeners.getListeners().iterator(); iterator.hasNext(); ((FBLocationListener)iterator.next()).onLocationChanged(location));
            if(FBLocationManager.mTimeoutError != null)
            {
                FBLocationManager.mHandler.removeCallbacks(FBLocationManager.mTimeoutError);
                FBLocationManager.mTimeoutError = null;
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        public void onProviderDisabled(String s)
        {
        }

        public void onProviderEnabled(String s)
        {
        }

        public void onStatusChanged(String s, int i, Bundle bundle)
        {
        }

        static final boolean $assertionsDisabled;
        protected boolean mEnabled;
        protected boolean mHighSampleMode;
        protected Location mLastKnownLocation;

        static 
        {
            boolean flag;
            if(!com/facebook/katana/util/FBLocationManager.desiredAssertionStatus())
                flag = true;
            else
                flag = false;
            $assertionsDisabled = flag;
        }

        public LocationDispatcher()
        {
            mEnabled = false;
        }
    }

    public static interface FBLocationListener
    {

        public abstract void onLocationChanged(Location location);

        public abstract void onTimeOut();
    }


    public FBLocationManager()
    {
    }

    public static void addLocationListener(Context context, FBLocationListener fblocationlistener)
    {
        initialize(context);
        if(mListeners == null)
            mListeners = new ReentrantCallback();
        ReentrantCallback reentrantcallback = mListeners;
        reentrantcallback;
        JVM INSTR monitorenter ;
        mListeners.addListener(fblocationlistener);
        mDispatcher.activate();
        if(mDispatcher.mLastKnownLocation != null)
            fblocationlistener.onLocationChanged(mDispatcher.mLastKnownLocation);
        if(mTimeout != null)
            mHandler.removeCallbacks(mTimeout);
        if(mTimeoutError == null)
        {
            Runnable runnable = new Runnable() {

                public void run()
                {
                    ReentrantCallback reentrantcallback1 = FBLocationManager.mListeners;
                    reentrantcallback1;
                    JVM INSTR monitorenter ;
                    for(Iterator iterator = FBLocationManager.mListeners.getListeners().iterator(); iterator.hasNext(); ((FBLocationListener)iterator.next()).onTimeOut());
                    break MISSING_BLOCK_LABEL_49;
                    Exception exception;
                    exception;
                    throw exception;
                    FBLocationManager.mDispatcher.mHighSampleMode = true;
                    FBLocationManager.mTimeoutError = null;
                    reentrantcallback1;
                    JVM INSTR monitorexit ;
                }

            }
;
            mTimeoutError = runnable;
            mHandler.postDelayed(runnable, 30000L);
        }
        return;
    }

    public static boolean areLocationServicesEnabled(Context context)
    {
        initialize(context);
        boolean flag;
        if(mLocationManager == null || !mLocationManager.isProviderEnabled("network") && !mLocationManager.isProviderEnabled("gps"))
            flag = false;
        else
            flag = true;
        return flag;
    }

    public static Location getRecentLocation(int i)
    {
        Location location;
        if(mDispatcher != null && mDispatcher.mLastKnownLocation != null && System.currentTimeMillis() - mDispatcher.mLastKnownLocation.getTime() <= (long)(i * 1000))
            location = mDispatcher.mLastKnownLocation;
        else
            location = null;
        return location;
    }

    public static void initialize(Context context)
    {
        if(mLocationManager == null)
            mLocationManager = (LocationManager)context.getSystemService("location");
        if(mDispatcher == null)
            mDispatcher = new LocationDispatcher();
    }

    protected static boolean isBetterLocation(Location location, Location location1)
    {
        boolean flag7;
        if(location1 == null)
        {
            flag7 = true;
        } else
        {
            long l = location.getTime() - location1.getTime();
            boolean flag;
            boolean flag1;
            boolean flag2;
            if(l > 0x1d4c0L)
                flag = true;
            else
                flag = false;
            if(l < 0xfffe2b40L)
                flag1 = true;
            else
                flag1 = false;
            if(l > 0L)
                flag2 = true;
            else
                flag2 = false;
            if(flag)
                flag7 = true;
            else
            if(flag1)
            {
                flag7 = false;
            } else
            {
                int i = (int)(location.getAccuracy() - location1.getAccuracy());
                boolean flag3;
                boolean flag4;
                boolean flag5;
                boolean flag6;
                if(i > 0)
                    flag3 = true;
                else
                    flag3 = false;
                if(i < 0)
                    flag4 = true;
                else
                    flag4 = false;
                if(i > 200)
                    flag5 = true;
                else
                    flag5 = false;
                flag6 = StringUtils.saneStringEquals(location.getProvider(), location1.getProvider());
                if(flag4)
                    flag7 = true;
                else
                if(flag2 && !flag3)
                    flag7 = true;
                else
                if(flag2 && !flag5 && flag6)
                    flag7 = true;
                else
                    flag7 = false;
            }
        }
        return flag7;
    }

    public static void removeLocationListener(FBLocationListener fblocationlistener)
    {
        if(mListeners != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        ReentrantCallback reentrantcallback = mListeners;
        reentrantcallback;
        JVM INSTR monitorenter ;
        if(!$assertionsDisabled && mDispatcher == null)
            throw new AssertionError();
        break MISSING_BLOCK_LABEL_38;
        Exception exception;
        exception;
        throw exception;
        mListeners.removeListener(fblocationlistener);
        if(mListeners.count() == 0)
            if(mDispatcher.mHighSampleMode)
            {
                Runnable runnable = new Runnable() {

                    public void run()
                    {
                        ReentrantCallback reentrantcallback1 = FBLocationManager.mListeners;
                        reentrantcallback1;
                        JVM INSTR monitorenter ;
                        FBLocationManager.mDispatcher.deactivate();
                        return;
                    }

                }
;
                mTimeout = runnable;
                mHandler.postDelayed(runnable, 15000L);
                if(mTimeoutError != null)
                {
                    mHandler.removeCallbacks(mTimeoutError);
                    mTimeoutError = null;
                }
            } else
            {
                mDispatcher.deactivate();
            }
        reentrantcallback;
        JVM INSTR monitorexit ;
          goto _L1
    }

    protected static boolean validForStartup(Location location)
    {
        boolean flag;
        if(location != null && System.currentTimeMillis() - location.getTime() <= 0x15f90L)
            flag = true;
        else
            flag = false;
        return flag;
    }

    static final boolean $assertionsDisabled = false;
    private static final int GPS_SHUTDOWN_DELAY_MS = 15000;
    private static final int LOCATION_DESIRED_ACCURACY_M = 33;
    private static final int LOCATION_FREQUENCY_ACCURATE_MIN_TIME_MS = 60000;
    public static final int LOCATION_FREQUENCY_INACCURATE_MIN_TIME_MS = 500;
    public static final int LOCATION_FREQUENCY_MIN_DISTANCE_M = 0;
    private static final int LOCATION_STALE_S = 120;
    private static final int LOCATION_STARTUP_STALE_S = 90;
    private static final int LOCATION_TIMOUT_ERROR_MS = 30000;
    protected static LocationDispatcher mDispatcher;
    private static Handler mHandler = new Handler();
    protected static volatile ReentrantCallback mListeners;
    protected static volatile LocationManager mLocationManager;
    private static Runnable mTimeout;
    protected static volatile Runnable mTimeoutError;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/util/FBLocationManager.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }

}

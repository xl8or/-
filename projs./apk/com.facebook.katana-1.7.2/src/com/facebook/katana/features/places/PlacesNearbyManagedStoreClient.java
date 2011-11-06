// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesNearby.java

package com.facebook.katana.features.places;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.util.Utils;

// Referenced classes of package com.facebook.katana.features.places:
//            PlacesNearby

class PlacesNearbyManagedStoreClient
    implements com.facebook.katana.binding.ManagedDataStore.Client
{

    PlacesNearbyManagedStoreClient()
    {
    }

    public FqlGetPlacesNearby deserialize(String s)
    {
        throw new IllegalStateException("Attempting to deserialize FqlGetPlacesNearby, currentlyunsupported");
    }

    public volatile Object deserialize(String s)
    {
        return deserialize(s);
    }

    public int getCacheTtl(PlacesNearby.PlacesNearbyArgType placesnearbyargtype, FqlGetPlacesNearby fqlgetplacesnearby)
    {
        return 300;
    }

    public volatile int getCacheTtl(Object obj, Object obj1)
    {
        return getCacheTtl((PlacesNearby.PlacesNearbyArgType)obj, (FqlGetPlacesNearby)obj1);
    }

    public String getKey(PlacesNearby.PlacesNearbyArgType placesnearbyargtype)
    {
        return "places_nearby";
    }

    public volatile String getKey(Object obj)
    {
        return getKey((PlacesNearby.PlacesNearbyArgType)obj);
    }

    public int getPersistentStoreTtl(PlacesNearby.PlacesNearbyArgType placesnearbyargtype, FqlGetPlacesNearby fqlgetplacesnearby)
    {
        return 0;
    }

    public volatile int getPersistentStoreTtl(Object obj, Object obj1)
    {
        return getPersistentStoreTtl((PlacesNearby.PlacesNearbyArgType)obj, (FqlGetPlacesNearby)obj1);
    }

    public String initiateNetworkRequest(Context context, PlacesNearby.PlacesNearbyArgType placesnearbyargtype, NetworkRequestCallback networkrequestcallback)
    {
        AppSession appsession = AppSession.getActiveSession(context, true);
        String s;
        if(appsession == null)
            s = null;
        else
            s = appsession.getPlacesNearby(context, placesnearbyargtype.location, placesnearbyargtype.maxDistance, placesnearbyargtype.filter, placesnearbyargtype.resultLimit, networkrequestcallback);
        return s;
    }

    public volatile String initiateNetworkRequest(Context context, Object obj, NetworkRequestCallback networkrequestcallback)
    {
        return initiateNetworkRequest(context, (PlacesNearby.PlacesNearbyArgType)obj, networkrequestcallback);
    }

    public boolean staleDataAcceptable(PlacesNearby.PlacesNearbyArgType placesnearbyargtype, FqlGetPlacesNearby fqlgetplacesnearby)
    {
        return false;
    }

    public volatile boolean staleDataAcceptable(Object obj, Object obj1)
    {
        return staleDataAcceptable((PlacesNearby.PlacesNearbyArgType)obj, (FqlGetPlacesNearby)obj1);
    }

    public static final String TAG = Utils.getClassName(com/facebook/katana/features/places/PlacesNearby);

}

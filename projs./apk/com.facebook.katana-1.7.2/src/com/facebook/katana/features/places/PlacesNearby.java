// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesNearby.java

package com.facebook.katana.features.places;

import android.content.Context;
import android.location.Location;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.util.Utils;

// Referenced classes of package com.facebook.katana.features.places:
//            PlacesNearbyManagedStoreClient

public class PlacesNearby
{
    public static class PlacesNearbyArgType
    {

        public boolean equals(Object obj)
        {
            return true;
        }

        public int hashCode()
        {
            return 0;
        }

        public String filter;
        public Location location;
        public double maxDistance;
        public int resultLimit;

        public PlacesNearbyArgType(Location location1)
        {
            this(location1, 750D, "", 20);
        }

        public PlacesNearbyArgType(Location location1, double d, String s, int i)
        {
            location = location1;
            maxDistance = d;
            filter = s;
            resultLimit = i;
        }
    }


    public PlacesNearby()
    {
    }

    public static FqlGetPlacesNearby get(Context context, PlacesNearbyArgType placesnearbyargtype)
    {
        return (FqlGetPlacesNearby)getStore().get(context, placesnearbyargtype);
    }

    protected static ManagedDataStore getStore()
    {
        if(store == null)
            store = new ManagedDataStore(new PlacesNearbyManagedStoreClient());
        return store;
    }

    public static void reset()
    {
        store = null;
    }

    public static final String TAG = Utils.getClassName(com/facebook/katana/features/places/PlacesNearby);
    protected static ManagedDataStore store;

}

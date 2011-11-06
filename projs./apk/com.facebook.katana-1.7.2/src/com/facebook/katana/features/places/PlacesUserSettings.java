// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesUserSettings.java

package com.facebook.katana.features.places;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;

// Referenced classes of package com.facebook.katana.features.places:
//            PlacesUserSettingsClient

public class PlacesUserSettings
{

    public PlacesUserSettings()
    {
    }

    public static String get(Context context, String s)
    {
        return (String)getStore().get(context, s);
    }

    protected static ManagedDataStore getStore()
    {
        if(store == null)
            store = new ManagedDataStore(new PlacesUserSettingsClient());
        return store;
    }

    public static void setSetting(Context context, String s, String s1)
    {
        getStore().callback(context, true, s, s1, s1, null);
    }

    public static final String PLACES_PROJECT_NAME = "places";
    public static final String PLACES_SETTINGS_KEY_OPT_IN = "places_opt_in";
    protected static ManagedDataStore store;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComposerUserSettings.java

package com.facebook.katana.features.composer;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;

// Referenced classes of package com.facebook.katana.features.composer:
//            ComposerUserSettingsClient

public class ComposerUserSettings
{

    public ComposerUserSettings()
    {
    }

    public static String get(Context context, String s)
    {
        return (String)getStore().get(context, s);
    }

    protected static ManagedDataStore getStore()
    {
        if(store == null)
            store = new ManagedDataStore(new ComposerUserSettingsClient());
        return store;
    }

    public static boolean isImplicitLocOn(Context context)
    {
        return "1".equals(get(context, "composer_share_location"));
    }

    public static boolean isTourComplete(Context context)
    {
        return "1".equals(get(context, "composer_tour_completed"));
    }

    public static void setSetting(Context context, String s, String s1)
    {
        getStore().callback(context, true, s, s1, s1, null);
    }

    public static final String IS_TOUR_COMPLETED = "composer_tour_completed";
    public static final String PROJECT_NAME = "structured_composer";
    public static final String SETTINGS_SHARE_LOC = "composer_share_location";
    public static final String SHARE_LOC_ON = "1";
    public static final String TOUR_COMPLETED = "1";
    protected static ManagedDataStore store;
}

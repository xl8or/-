// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesUtils.java

package com.facebook.katana.features.places;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.UserValuesManager;

// Referenced classes of package com.facebook.katana.features.places:
//            PlacesUserSettings

public class PlacesUtils
{

    public PlacesUtils()
    {
    }

    public static boolean checkOptInStatus(AppSession appsession, Context context)
    {
        String s = PlacesUserSettings.get(context, "places_opt_in");
        boolean flag;
        if(s == null || "default".equals(s))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void setLastCheckin(Context context, FacebookCheckin facebookcheckin, long l)
    {
        UserValuesManager.setValue(context, "places:last_checkin_pageid", Long.valueOf(facebookcheckin.getDetails().getPlaceInfo().mPageId));
        UserValuesManager.setValue(context, "places:last_checkin_time", Long.valueOf(l));
        String s = facebookcheckin.jsonEncode();
        if(s != null)
            UserValuesManager.setValue(context, "places:last_checkin", s);
    }

    public static final String OPT_IN_DEFAULT_SETTING = "default";
}

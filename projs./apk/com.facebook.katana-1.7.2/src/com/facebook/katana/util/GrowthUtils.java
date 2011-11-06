// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GrowthUtils.java

package com.facebook.katana.util;

import android.content.Context;
import android.os.Build;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.provider.KeyValueManager;

public final class GrowthUtils
{

    public GrowthUtils()
    {
    }

    private static String getUserKey(Context context, String s)
    {
        StringBuilder stringbuilder = new StringBuilder(Long.toString(AppSession.getActiveSession(context, false).getSessionInfo().userId));
        stringbuilder.append(":");
        stringbuilder.append(s);
        return stringbuilder.toString();
    }

    public static boolean kddiImporterEnabled(Context context)
    {
        String s = Build.BRAND;
        boolean flag;
        if(s != null && s.equalsIgnoreCase("KDDI") && Boolean.TRUE.equals(Gatekeeper.get(context, "android_ci_kddi_intro_enabled")))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void setFindFriendsConsentApproved(Context context)
    {
        KeyValueManager.setValue(context, getUserKey(context, "findFriendsConsentApproved"), new Boolean(true));
    }

    public static void setLegalBarShown(Context context)
    {
        KeyValueManager.setValue(context, getUserKey(context, "findFriendsLegalBarShown"), new Boolean(true));
    }

    public static boolean shouldShowLegalBar(Context context)
    {
        Boolean boolean1 = Gatekeeper.get(context, "android_ci_legal_bar");
        boolean flag;
        if(boolean1 == null || boolean1.booleanValue())
            flag = true;
        else
        if(!KeyValueManager.getBooleanValue(context, getUserKey(context, "findFriendsLegalBarShown")))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static boolean shouldShowLegalScreen(Context context)
    {
        boolean flag;
        if(kddiImporterEnabled(context))
        {
            flag = true;
        } else
        {
            Boolean boolean1 = Gatekeeper.get(context, "android_ci_legal_screen");
            if(boolean1 != null && !boolean1.booleanValue())
                flag = false;
            else
            if(!KeyValueManager.getBooleanValue(context, getUserKey(context, "findFriendsConsentApproved")))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public static boolean showFindFriendsDialog(Context context)
    {
        Boolean boolean1 = Gatekeeper.get(context, "android_ci_alert_enabled");
        boolean flag;
        if(boolean1 == null || !boolean1.booleanValue())
            flag = false;
        else
        if(!KeyValueManager.getBooleanValue(context, getUserKey(context, "findFriendsDialogShown")))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static boolean showPhoneNumberDialog(Context context)
    {
        boolean flag;
        if(!KeyValueManager.getBooleanValue(context, getUserKey(context, "phoneNumberDialogShown")))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void stopFindFriendsDialog(Context context)
    {
        KeyValueManager.setValue(context, getUserKey(context, "findFriendsDialogShown"), Boolean.valueOf(true));
    }

    public static void stopPhoneNumberDialog(Context context)
    {
        KeyValueManager.setValue(context, getUserKey(context, "phoneNumberDialogShown"), Boolean.valueOf(true));
    }

    private static final String FIND_FRIENDS_CONSENT_APPROVED = "findFriendsConsentApproved";
    private static final String FRIENDS_NAG_FIELD = "findFriendsDialogShown";
    private static final String LEGAL_BAR_SHOWN = "findFriendsLegalBarShown";
    private static final String PHONE_NAG_FIELD = "phoneNumberDialogShown";
}

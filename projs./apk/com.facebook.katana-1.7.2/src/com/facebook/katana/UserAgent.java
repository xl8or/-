// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserAgent.java

package com.facebook.katana;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.util.StringUtils;
import java.util.Locale;

// Referenced classes of package com.facebook.katana:
//            Constants

public class UserAgent
{

    public UserAgent()
    {
    }

    private static String cleanString(String s)
    {
        return StringUtils.xmlEncodeNonLatin(s).replace("/", "-").replace(";", "-");
    }

    public static String getAppVersion(Context context)
    {
        String s1;
        PackageInfo packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        StringBuilder stringbuilder = new StringBuilder(packageinfo.versionName);
        if(Constants.isBetaBuild())
            stringbuilder.append("/").append(packageinfo.versionCode);
        s1 = stringbuilder.toString();
        String s = s1;
_L2:
        return s;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        s = "unknown";
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static String getCarrier(Context context)
    {
        return ((TelephonyManager)context.getSystemService("phone")).getNetworkOperatorName();
    }

    public static String getDevice()
    {
        return Build.MODEL;
    }

    public static String getDisplayMetrics(Context context)
    {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        return (new StringBuilder()).append("{density=").append(displaymetrics.density).append(",width=").append(displaymetrics.widthPixels).append(",height=").append(displaymetrics.heightPixels).append("}").toString();
    }

    public static String getLocale(Context context)
    {
        return context.getResources().getConfiguration().locale.toString();
    }

    public static String getOSVersion()
    {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getProductName()
    {
        return "FB4A";
    }

    public static String getUserAgentString(Context context, Boolean boolean1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[");
        Object aobj[] = new Object[16];
        aobj[0] = "FBAN";
        aobj[1] = cleanString(getProductName());
        aobj[2] = "FBAV";
        aobj[3] = cleanString(getAppVersion(context));
        aobj[4] = "FBPN";
        aobj[5] = context.getPackageName();
        aobj[6] = "FBDV";
        aobj[7] = cleanString(getDevice());
        aobj[8] = "FBSV";
        aobj[9] = cleanString(getOSVersion());
        aobj[10] = "FBDM";
        aobj[11] = cleanString(getDisplayMetrics(context));
        aobj[12] = "FBCR";
        aobj[13] = cleanString(getCarrier(context));
        aobj[14] = "FBLC";
        aobj[15] = cleanString(getLocale(context));
        stringbuilder.append(String.format("%s/%s;%s/%s;%s/%s;%s/%s;%s/%s;%s/%s;%s/%s;%s/%s;", aobj));
        if(boolean1.booleanValue())
        {
            Object aobj2[] = new Object[2];
            aobj2[0] = "FB_FW";
            aobj2[1] = "1";
            stringbuilder.append(String.format("%s/%s;", aobj2));
        }
        if(FacebookAffiliation.isCurrentUserEmployee())
        {
            Object aobj1[] = new Object[2];
            aobj1[0] = "FBBR";
            aobj1[1] = "release-1.7.2";
            stringbuilder.append(String.format("%s/%s;", aobj1));
        }
        stringbuilder.append("]");
        return stringbuilder.toString();
    }

    public static final String BRANCH_NAME = "release-1.7.2";
    public static final String FB_APP_NAME = "FBAN";
    public static final String FB_APP_VERSION = "FBAV";
    public static final String FB_BRANCH = "FBBR";
    public static final String FB_CARRIER = "FBCR";
    public static final String FB_DEVICE = "FBDV";
    public static final String FB_DISPLAY_METRICS = "FBDM";
    public static final String FB_LOCALE = "FBLC";
    public static final String FB_PACKAGE_NAME = "FBPN";
    public static final String FB_SYSTEM_VERSION = "FBSV";
}

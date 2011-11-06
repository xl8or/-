// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookWidgetProvider.java

package com.facebook.katana;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import com.facebook.katana.binding.AppSession;

public class FacebookWidgetProvider extends AppWidgetProvider
{

    public FacebookWidgetProvider()
    {
    }

    public void onEnabled(Context context)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        if(appsession != null)
        {
            AppSession.clearWidget(context, context.getString(0x7f0a0231), "");
            appsession.scheduleStatusPollingAlarm(context, 0, 0);
        }
    }

    public void onUpdate(Context context, AppWidgetManager appwidgetmanager, int ai[])
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        if(appsession != null)
            appsession.widgetUpdate(context);
        else
            AppSession.clearWidget(context, context.getString(0x7f0a0230), context.getString(0x7f0a022f));
    }
}

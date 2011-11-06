// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookApplication.java

package com.facebook.katana;

import android.app.Application;
import com.facebook.katana.util.Utils;
import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.sender.HttpPostSender;

public class FacebookApplication extends Application
{

    public FacebookApplication()
    {
    }

    public void onCreate()
    {
        ACRA.init(this);
        String s = Constants.URL.getCrashReportUrl(this);
        ErrorReporter.getInstance().setReportSender(new HttpPostSender(s, null));
        Utils.updateErrorReportingUserId(this);
        super.onCreate();
    }
}

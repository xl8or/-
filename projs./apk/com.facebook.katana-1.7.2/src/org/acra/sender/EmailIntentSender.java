// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailIntentSender.java

package org.acra.sender;

import android.content.Context;
import android.content.Intent;
import org.acra.*;
import org.acra.annotation.ReportsCrashes;

// Referenced classes of package org.acra.sender:
//            ReportSender, ReportSenderException

public class EmailIntentSender
    implements ReportSender
{

    public EmailIntentSender(Context context)
    {
        mContext = null;
        mContext = context;
    }

    private String buildBody(CrashReportData crashreportdata)
    {
        StringBuilder stringbuilder = new StringBuilder();
        ReportField areportfield[] = ACRA.getConfig().customReportContent();
        if(areportfield.length == 0)
            areportfield = ACRA.DEFAULT_MAIL_REPORT_FIELDS;
        ReportField areportfield1[] = areportfield;
        int i = areportfield1.length;
        for(int j = 0; j < i; j++)
        {
            ReportField reportfield = areportfield1[j];
            stringbuilder.append(reportfield.toString()).append("=");
            stringbuilder.append((String)crashreportdata.get(reportfield));
            stringbuilder.append('\n');
        }

        return stringbuilder.toString();
    }

    public void send(CrashReportData crashreportdata)
        throws ReportSenderException
    {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addFlags(0x10000000);
        intent.setType("text/plain");
        String s = (new StringBuilder()).append((String)crashreportdata.get(ReportField.PACKAGE_NAME)).append(" Crash Report").toString();
        String s1 = buildBody(crashreportdata);
        intent.putExtra("android.intent.extra.SUBJECT", s);
        intent.putExtra("android.intent.extra.TEXT", s1);
        String as[] = new String[1];
        as[0] = ACRA.getConfig().mailTo();
        intent.putExtra("android.intent.extra.EMAIL", as);
        mContext.startActivity(intent);
    }

    Context mContext;
}

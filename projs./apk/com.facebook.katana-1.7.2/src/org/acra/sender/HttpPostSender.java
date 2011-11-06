// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpPostSender.java

package org.acra.sender;

import android.net.Uri;
import android.util.Log;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.acra.*;
import org.acra.annotation.ReportsCrashes;
import org.acra.util.HttpUtils;

// Referenced classes of package org.acra.sender:
//            ReportSender, ReportSenderException

public class HttpPostSender
    implements ReportSender
{

    public HttpPostSender(String s, Map map)
    {
        mFormUri = null;
        mMapping = null;
        mFormUri = Uri.parse(s);
        mMapping = map;
    }

    private Map remap(Map map)
    {
        HashMap hashmap = new HashMap(map.size());
        ReportField areportfield[] = ACRA.getConfig().customReportContent();
        if(areportfield.length == 0)
            areportfield = ACRA.DEFAULT_REPORT_FIELDS;
        ReportField areportfield1[] = areportfield;
        int i = areportfield1.length;
        int j = 0;
        while(j < i) 
        {
            ReportField reportfield = areportfield1[j];
            if(mMapping == null || mMapping.get(reportfield) == null)
                hashmap.put(reportfield.toString(), map.get(reportfield));
            else
                hashmap.put(mMapping.get(reportfield), map.get(reportfield));
            j++;
        }
        return hashmap;
    }

    public void send(CrashReportData crashreportdata)
        throws ReportSenderException
    {
        try
        {
            Map map = remap(crashreportdata);
            URL url = new URL(mFormUri.toString());
            Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("Connect to ").append(url.toString()).toString());
            HttpUtils.doPost(map, url, ACRA.getConfig().formUriBasicAuthLogin(), ACRA.getConfig().formUriBasicAuthPassword());
            return;
        }
        catch(Exception exception)
        {
            throw new ReportSenderException("Error while sending report to Http Post Form.", exception);
        }
    }

    private Uri mFormUri;
    private Map mMapping;
}

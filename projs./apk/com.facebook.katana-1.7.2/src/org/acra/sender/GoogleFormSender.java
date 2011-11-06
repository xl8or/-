// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoogleFormSender.java

package org.acra.sender;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.acra.*;
import org.acra.annotation.ReportsCrashes;
import org.acra.util.HttpUtils;

// Referenced classes of package org.acra.sender:
//            ReportSender, ReportSenderException

public class GoogleFormSender
    implements ReportSender
{

    public GoogleFormSender(String s)
    {
        mFormUri = null;
        mFormUri = Uri.parse((new StringBuilder()).append("https://spreadsheets.google.com/formResponse?formkey=").append(s).append("&amp;ifq").toString());
    }

    private Map remap(Map map)
    {
        HashMap hashmap;
        int i;
        ReportField areportfield1[];
        int j;
        int k;
        hashmap = new HashMap();
        i = 0;
        ReportField areportfield[] = ACRA.getConfig().customReportContent();
        if(areportfield.length == 0)
            areportfield = ACRA.DEFAULT_REPORT_FIELDS;
        areportfield1 = areportfield;
        j = areportfield1.length;
        k = 0;
_L2:
        ReportField reportfield;
        if(k >= j)
            break MISSING_BLOCK_LABEL_266;
        reportfield = areportfield1[k];
        class _cls1
        {

            static final int $SwitchMap$org$acra$ReportField[];

            static 
            {
                $SwitchMap$org$acra$ReportField = new int[ReportField.values().length];
                NoSuchFieldError nosuchfielderror1;
                try
                {
                    $SwitchMap$org$acra$ReportField[ReportField.APP_VERSION_NAME.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                $SwitchMap$org$acra$ReportField[ReportField.ANDROID_VERSION.ordinal()] = 2;
_L2:
                return;
                nosuchfielderror1;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        switch(_cls1..SwitchMap.org.acra.ReportField[((ReportField)reportfield).ordinal()])
        {
        default:
            hashmap.put((new StringBuilder()).append("entry.").append(i).append(".single").toString(), map.get(reportfield));
            break;

        case 1: // '\001'
            break; /* Loop/switch isn't completed */

        case 2: // '\002'
            break MISSING_BLOCK_LABEL_203;
        }
_L3:
        i++;
        k++;
        if(true) goto _L2; else goto _L1
_L1:
        hashmap.put((new StringBuilder()).append("entry.").append(i).append(".single").toString(), (new StringBuilder()).append("'").append((String)map.get(reportfield)).toString());
          goto _L3
        hashmap.put((new StringBuilder()).append("entry.").append(i).append(".single").toString(), (new StringBuilder()).append("'").append((String)map.get(reportfield)).toString());
          goto _L3
        return hashmap;
    }

    public void send(CrashReportData crashreportdata)
        throws ReportSenderException
    {
        Map map = remap(crashreportdata);
        map.put("pageNumber", "0");
        map.put("backupCache", "");
        map.put("submit", "Envoyer");
        try
        {
            URL url = new URL(mFormUri.toString());
            Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("Sending report ").append((String)crashreportdata.get(ReportField.REPORT_ID)).toString());
            Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("Connect to ").append(url).toString());
            HttpUtils.doPost(map, url, null, null);
            return;
        }
        catch(IOException ioexception)
        {
            throw new ReportSenderException("Error while sending report to Google Form.", ioexception);
        }
    }

    private Uri mFormUri;
}

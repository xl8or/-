// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeviceFeaturesCollector.java

package org.acra;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

// Referenced classes of package org.acra:
//            Compatibility, ACRA

public class DeviceFeaturesCollector
{

    public DeviceFeaturesCollector()
    {
    }

    public static String getFeatures(Context context)
    {
        String s;
        if(Compatibility.getAPILevel() >= 5)
        {
            StringBuffer stringbuffer = new StringBuffer();
            PackageManager packagemanager = context.getPackageManager();
            try
            {
                Object aobj[] = (Object[])(Object[])android/content/pm/PackageManager.getMethod("getSystemAvailableFeatures", (Class[])null).invoke(packagemanager, new Object[0]);
                int i = aobj.length;
                int j = 0;
                while(j < i) 
                {
                    Object obj = aobj[j];
                    String s1 = (String)obj.getClass().getField("name").get(obj);
                    if(s1 != null)
                    {
                        stringbuffer.append(s1);
                    } else
                    {
                        String s2 = (String)obj.getClass().getMethod("getGlEsVersion", (Class[])null).invoke(obj, new Object[0]);
                        stringbuffer.append("glEsVersion = ");
                        stringbuffer.append(s2);
                    }
                    stringbuffer.append("\n");
                    j++;
                }
            }
            catch(Throwable throwable)
            {
                Log.w(ACRA.LOG_TAG, "Error : ", throwable);
                stringbuffer.append("Could not retrieve data: ");
                stringbuffer.append(throwable.getMessage());
            }
            s = stringbuffer.toString();
        } else
        {
            s = "Data available only with API Level > 5";
        }
        return s;
    }
}

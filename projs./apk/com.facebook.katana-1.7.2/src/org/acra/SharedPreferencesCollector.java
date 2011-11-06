// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SharedPreferencesCollector.java

package org.acra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.*;
import org.acra.annotation.ReportsCrashes;

// Referenced classes of package org.acra:
//            ACRA

public class SharedPreferencesCollector
{

    public SharedPreferencesCollector()
    {
    }

    public static String collect(Context context)
    {
        StringBuilder stringbuilder = new StringBuilder();
        TreeMap treemap = new TreeMap();
        treemap.put("default", PreferenceManager.getDefaultSharedPreferences(context));
        String as[] = ACRA.getConfig().additionalSharedPreferences();
        if(as != null)
        {
            int i = as.length;
            for(int j = 0; j < i; j++)
            {
                String s2 = as[j];
                treemap.put(s2, context.getSharedPreferences(s2, 0));
            }

        }
        Iterator iterator = treemap.keySet().iterator();
        while(iterator.hasNext()) 
        {
            String s = (String)iterator.next();
            stringbuilder.append(s).append("\n");
            SharedPreferences sharedpreferences = (SharedPreferences)treemap.get(s);
            if(sharedpreferences != null)
            {
                Map map = sharedpreferences.getAll();
                if(map != null && map.size() > 0)
                {
                    String s1;
                    for(Iterator iterator1 = map.keySet().iterator(); iterator1.hasNext(); stringbuilder.append(s1).append("=").append(map.get(s1).toString()).append("\n"))
                        s1 = (String)iterator1.next();

                } else
                {
                    stringbuilder.append("empty\n");
                }
            } else
            {
                stringbuilder.append("null\n");
            }
            stringbuilder.append("\n");
        }
        return stringbuilder.toString();
    }
}

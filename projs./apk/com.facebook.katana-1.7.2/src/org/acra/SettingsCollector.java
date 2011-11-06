// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SettingsCollector.java

package org.acra;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Field;

// Referenced classes of package org.acra:
//            ACRA

public class SettingsCollector
{

    public SettingsCollector()
    {
    }

    public static String collectSecureSettings(Context context)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Field afield[] = android/provider/Settings$Secure.getFields();
        int i = afield.length;
        int j = 0;
        while(j < i) 
        {
            Field field = afield[j];
            if(!field.isAnnotationPresent(java/lang/Deprecated) && field.getType() == java/lang/String && isAuthorized(field))
                try
                {
                    String s = android.provider.Settings.Secure.getString(context.getContentResolver(), (String)field.get(null));
                    if(s != null)
                        stringbuilder.append(field.getName()).append("=").append(s).append("\n");
                }
                catch(IllegalArgumentException illegalargumentexception)
                {
                    Log.w(ACRA.LOG_TAG, "Error : ", illegalargumentexception);
                }
                catch(IllegalAccessException illegalaccessexception)
                {
                    Log.w(ACRA.LOG_TAG, "Error : ", illegalaccessexception);
                }
            j++;
        }
        return stringbuilder.toString();
    }

    public static String collectSystemSettings(Context context)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Field afield[] = android/provider/Settings$System.getFields();
        int i = afield.length;
        int j = 0;
        while(j < i) 
        {
            Field field = afield[j];
            if(!field.isAnnotationPresent(java/lang/Deprecated) && field.getType() == java/lang/String)
                try
                {
                    String s = android.provider.Settings.System.getString(context.getContentResolver(), (String)field.get(null));
                    if(s != null)
                        stringbuilder.append(field.getName()).append("=").append(s).append("\n");
                }
                catch(IllegalArgumentException illegalargumentexception)
                {
                    Log.w(ACRA.LOG_TAG, "Error : ", illegalargumentexception);
                }
                catch(IllegalAccessException illegalaccessexception)
                {
                    Log.w(ACRA.LOG_TAG, "Error : ", illegalaccessexception);
                }
            j++;
        }
        return stringbuilder.toString();
    }

    private static boolean isAuthorized(Field field)
    {
        boolean flag;
        if(field == null || field.getName().startsWith("WIFI_AP"))
            flag = false;
        else
            flag = true;
        return flag;
    }
}

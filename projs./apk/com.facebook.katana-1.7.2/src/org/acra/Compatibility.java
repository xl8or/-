// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Compatibility.java

package org.acra;

import android.content.Context;
import java.lang.reflect.Field;

public class Compatibility
{

    public Compatibility()
    {
    }

    static int getAPILevel()
    {
        int j = android/os/Build$VERSION.getField("SDK_INT").getInt(null);
        int i = j;
_L2:
        return i;
        SecurityException securityexception;
        securityexception;
        i = Integer.parseInt(android.os.Build.VERSION.SDK);
        continue; /* Loop/switch isn't completed */
        NoSuchFieldException nosuchfieldexception;
        nosuchfieldexception;
        i = Integer.parseInt(android.os.Build.VERSION.SDK);
        continue; /* Loop/switch isn't completed */
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        i = Integer.parseInt(android.os.Build.VERSION.SDK);
        continue; /* Loop/switch isn't completed */
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        i = Integer.parseInt(android.os.Build.VERSION.SDK);
        if(true) goto _L2; else goto _L1
_L1:
    }

    static String getDropBoxServiceName()
        throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        Field field = android/content/Context.getField("DROPBOX_SERVICE");
        String s;
        if(field != null)
            s = (String)field.get(null);
        else
            s = null;
        return s;
    }
}

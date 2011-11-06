// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlatformUtils.java

package com.facebook.katana.util;

import android.content.Context;
import com.facebook.katana.platform.PlatformStorage;
import java.lang.reflect.Field;

public final class PlatformUtils
{

    public PlatformUtils()
    {
    }

    public static void fixContacts(Context context)
    {
        if(isEclairOrLater() && !platformStorageSupported(context))
            PlatformStorage.fixContactsHelper(context);
    }

    /**
     * @deprecated Method isEclairOrLater is deprecated
     */

    public static boolean isEclairOrLater()
    {
        com/facebook/katana/util/PlatformUtils;
        JVM INSTR monitorenter ;
        if(!sPlatformEclairOrLaterDetected) goto _L2; else goto _L1
_L1:
        boolean flag = sPlatformEclairOrLater;
_L5:
        com/facebook/katana/util/PlatformUtils;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        sPlatformEclairOrLater = false;
        if(!((String)android/os/Build$VERSION.getDeclaredField("CODENAME").get(null)).equals("Eclair")) goto _L4; else goto _L3
_L3:
        sPlatformEclairOrLater = true;
_L7:
        sPlatformEclairOrLaterDetected = true;
        flag = sPlatformEclairOrLater;
          goto _L5
_L4:
        if(android/os/Build$VERSION.getDeclaredField("SDK_INT").getInt(null) < 5) goto _L7; else goto _L6
_L6:
        sPlatformEclairOrLater = true;
          goto _L7
        Exception exception1;
        exception1;
          goto _L7
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method platformStorageSupported is deprecated
     */

    public static boolean platformStorageSupported(Context context)
    {
        com/facebook/katana/util/PlatformUtils;
        JVM INSTR monitorenter ;
        if(!sStorageSupportedDetected) goto _L2; else goto _L1
_L1:
        boolean flag = sStorageSupported;
_L4:
        com/facebook/katana/util/PlatformUtils;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        sStorageSupported = false;
        Exception exception;
        try
        {
            if(isEclairOrLater())
                sStorageSupported = PlatformStorage.checkUnrestrictedPackages(context);
        }
        catch(Exception exception1) { }
        sStorageSupportedDetected = true;
        flag = sStorageSupported;
        if(true) goto _L4; else goto _L3
_L3:
        exception;
        throw exception;
    }

    private static boolean sPlatformEclairOrLater;
    private static boolean sPlatformEclairOrLaterDetected = false;
    private static boolean sStorageSupported;
    private static boolean sStorageSupportedDetected = false;

}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SDK5.java

package com.facebook.katana.version;

import android.media.ExifInterface;
import android.webkit.WebStorage;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import java.io.IOException;

public class SDK5
{

    public SDK5()
    {
    }

    public static void clearWebStorage()
    {
        WebStorage.getInstance().deleteAllData();
    }

    public static int getJpegExifOrientation(String s)
    {
        ExifInterface exifinterface;
        char c;
        try
        {
            exifinterface = new ExifInterface(s);
        }
        catch(IOException ioexception)
        {
            c = '\uFFFF';
            continue; /* Loop/switch isn't completed */
        }
        exifinterface.getAttributeInt("Orientation", 1);
        JVM INSTR tableswitch 1 8: default 64
    //                   1 76
    //                   2 64
    //                   3 87
    //                   4 64
    //                   5 64
    //                   6 81
    //                   7 64
    //                   8 94;
           goto _L1 _L2 _L1 _L3 _L1 _L1 _L4 _L1 _L5
_L1:
        c = '\uFFFF';
_L7:
        return c;
_L2:
        c = '\0';
        continue; /* Loop/switch isn't completed */
_L4:
        c = 'Z';
        continue; /* Loop/switch isn't completed */
_L3:
        c = '\264';
        continue; /* Loop/switch isn't completed */
_L5:
        c = '\u010E';
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static boolean isQuickContactBadge(ImageView imageview)
    {
        return imageview instanceof QuickContactBadge;
    }
}

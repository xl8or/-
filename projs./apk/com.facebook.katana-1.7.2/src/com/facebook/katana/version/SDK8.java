// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SDK8.java

package com.facebook.katana.version;

import android.util.Patterns;
import java.util.regex.Pattern;

public class SDK8
{

    public SDK8()
    {
    }

    public static Pattern getWebUrlPattern()
    {
        return Patterns.WEB_URL;
    }
}

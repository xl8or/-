// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageFilter.java

package com.facebook.katana.features.imagefilters;

import android.graphics.Bitmap;

public abstract class ImageFilter
{

    public ImageFilter(String s)
    {
        mName = s;
    }

    public abstract Bitmap applyFilter(Bitmap bitmap);

    public String getName()
    {
        return mName;
    }

    private String mName;
}

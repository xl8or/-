// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileImage.java

package com.facebook.katana.binding;

import android.graphics.Bitmap;
import java.lang.ref.SoftReference;

public class ProfileImage
{

    public ProfileImage(long l, String s, Bitmap bitmap)
    {
        id = l;
        url = s;
        mUsageCount = 0;
        mBmpSoftReference = new SoftReference(bitmap);
    }

    public Bitmap getBitmap()
    {
        return (Bitmap)mBmpSoftReference.get();
    }

    public int getUsageCount()
    {
        return mUsageCount;
    }

    public void incrUsageCount()
    {
        mUsageCount = 1 + mUsageCount;
    }

    public void setBitmap(Bitmap bitmap)
    {
        mBmpSoftReference = new SoftReference(bitmap);
    }

    public final long id;
    private SoftReference mBmpSoftReference;
    private int mUsageCount;
    public final String url;
}

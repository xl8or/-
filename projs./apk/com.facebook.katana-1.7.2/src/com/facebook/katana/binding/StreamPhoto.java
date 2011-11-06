// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamPhoto.java

package com.facebook.katana.binding;

import android.graphics.Bitmap;
import android.net.Uri;
import java.lang.ref.SoftReference;

public class StreamPhoto
{

    public StreamPhoto(Uri uri, String s, String s1, long l, Bitmap bitmap)
    {
        mContentUri = uri;
        mUrl = s;
        mFilename = s1;
        mLength = l;
        mUsageCount = 0;
        mBmpSoftReference = new SoftReference(bitmap);
    }

    public Bitmap getBitmap()
    {
        return (Bitmap)mBmpSoftReference.get();
    }

    public Uri getContentUri()
    {
        return mContentUri;
    }

    public String getFilename()
    {
        return mFilename;
    }

    public long getLength()
    {
        return mLength;
    }

    public String getUrl()
    {
        return mUrl;
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

    private SoftReference mBmpSoftReference;
    private final Uri mContentUri;
    private final String mFilename;
    private final long mLength;
    private final String mUrl;
    private int mUsageCount;
}

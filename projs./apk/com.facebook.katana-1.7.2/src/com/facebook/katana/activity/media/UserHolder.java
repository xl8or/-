// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserHolder.java

package com.facebook.katana.activity.media;

import com.facebook.katana.ViewHolder;

public class UserHolder
{

    public UserHolder()
    {
    }

    public String getDisplayName()
    {
        return mDisplayName;
    }

    public String getImageURL()
    {
        return mImageURL;
    }

    public long getUid()
    {
        return mUid;
    }

    public ViewHolder getViewHolder()
    {
        return mViewHolder;
    }

    public void setDisplayName(String s)
    {
        mDisplayName = s;
    }

    public void setImageURL(String s)
    {
        mImageURL = s;
    }

    public void setUid(long l)
    {
        mUid = l;
    }

    public void setViewHolder(ViewHolder viewholder)
    {
        mViewHolder = viewholder;
    }

    private String mDisplayName;
    private String mImageURL;
    private long mUid;
    private ViewHolder mViewHolder;
}

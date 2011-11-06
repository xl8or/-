// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ViewHolder.java

package com.facebook.katana;

import android.view.View;
import android.widget.ImageView;

public class ViewHolder
{

    public ViewHolder(View view, int i)
    {
        mImageView = (ImageView)view.findViewById(i);
    }

    public Object getItemId()
    {
        return mItemId;
    }

    public void setItemId(Object obj)
    {
        mItemId = obj;
    }

    public final ImageView mImageView;
    private Object mItemId;
}

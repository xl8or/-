// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageFilterThumbnail.java

package com.facebook.katana.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.*;
import com.facebook.katana.features.imagefilters.ImageFilter;

public class ImageFilterThumbnail extends RelativeLayout
{

    public ImageFilterThumbnail(Context context, ImageFilter imagefilter)
    {
        super(context);
        mFilter = imagefilter;
        LayoutInflater.from(context).inflate(0x7f030024, this);
        mImageView = (ImageView)findViewById(0x7f0e0078);
        mTextView = (TextView)findViewById(0x7f0e0079);
        mTextView.setText(mFilter.getName());
        setBackgroundColor(0);
    }

    public void setThumbnailImage(Bitmap bitmap)
    {
        mImageView.setImageBitmap(mFilter.applyFilter(bitmap));
    }

    private ImageFilter mFilter;
    private ImageView mImageView;
    private TextView mTextView;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultiShotHorizontalScrollView.java

package com.facebook.katana.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class MultiShotHorizontalScrollView extends HorizontalScrollView
{
    public static interface ScrollViewListener
    {

        public abstract void onScrollChanged(int i, int j, int k, int l);
    }


    public MultiShotHorizontalScrollView(Context context)
    {
        super(context);
    }

    public MultiShotHorizontalScrollView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public MultiShotHorizontalScrollView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    protected void onScrollChanged(int i, int j, int k, int l)
    {
        super.onScrollChanged(i, j, k, l);
        if(mScrollListener != null)
            mScrollListener.onScrollChanged(i, j, k, l);
    }

    public void setScrollViewListener(ScrollViewListener scrollviewlistener)
    {
        mScrollListener = scrollviewlistener;
    }

    private ScrollViewListener mScrollListener;
}

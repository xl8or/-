// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RefreshableListViewOverflowItem.java

package com.facebook.orca.common.ui.widgets.refreshablelistview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class RefreshableListViewOverflowItem extends View
{

    public RefreshableListViewOverflowItem(Context context)
    {
        super(context);
        init();
    }

    public RefreshableListViewOverflowItem(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        init();
    }

    public RefreshableListViewOverflowItem(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        init();
    }

    private void init()
    {
        setMinimumHeight((int)(500F * getContext().getResources().getDisplayMetrics().density));
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookListView.java

package com.facebook.katana.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;
import com.facebook.katana.service.method.PerfLogging;

public class FacebookListView extends ListView
{

    public FacebookListView(Context context)
    {
        super(context);
        TAG = "FacebookListView";
        mNextDrawStep = INVALID_DRAW_STEP;
    }

    public FacebookListView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        TAG = "FacebookListView";
        mNextDrawStep = INVALID_DRAW_STEP;
    }

    public FacebookListView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        TAG = "FacebookListView";
        mNextDrawStep = INVALID_DRAW_STEP;
    }

    protected void onDraw(Canvas canvas)
    {
        if(mNextDrawStep != INVALID_DRAW_STEP)
        {
            PerfLogging.logStep(getContext(), mNextDrawStep, TAG, activityId);
            mNextDrawStep = INVALID_DRAW_STEP;
        }
        super.onDraw(canvas);
    }

    public void setNextDrawStep(com.facebook.katana.service.method.PerfLogging.Step step)
    {
        if(step == com.facebook.katana.service.method.PerfLogging.Step.UI_DRAWN_STALE || step == com.facebook.katana.service.method.PerfLogging.Step.UI_DRAWN_FRESH)
            mNextDrawStep = step;
    }

    private static com.facebook.katana.service.method.PerfLogging.Step INVALID_DRAW_STEP = null;
    public String TAG;
    public long activityId;
    private com.facebook.katana.service.method.PerfLogging.Step mNextDrawStep;
    public String reqId;
    public long startTime;

}

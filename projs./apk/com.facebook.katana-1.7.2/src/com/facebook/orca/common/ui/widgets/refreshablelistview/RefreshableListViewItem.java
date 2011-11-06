// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RefreshableListViewItem.java

package com.facebook.orca.common.ui.widgets.refreshablelistview;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.facebook.orca.common.ui.widgets.refreshablelistview:
//            RefreshableListViewState

public class RefreshableListViewItem extends FrameLayout
{

    public RefreshableListViewItem(Context context)
    {
        super(context);
        direction = -1;
        init();
    }

    public RefreshableListViewItem(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        direction = -1;
        init();
    }

    public RefreshableListViewItem(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        direction = -1;
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(0x7f03006d, this);
        actionContainer = findViewById(0x7f0e0114);
        refreshContainer = findViewById(0x7f0e011c);
        textViewPull = (TextView)findViewById(0x7f0e0118);
        textViewPull.setText(0x7f0a029a);
        textViewRelease = (TextView)findViewById(0x7f0e011a);
        textViewRelease.setText(0x7f0a029c);
        textViewPush = (TextView)findViewById(0x7f0e0119);
        textViewPush.setText(0x7f0a029b);
        TextView atextview[] = new TextView[3];
        atextview[0] = textViewPull;
        atextview[1] = textViewRelease;
        atextview[2] = textViewPush;
        textViews = Arrays.asList(atextview);
        textLastLoadedTime = (TextView)findViewById(0x7f0e011b);
        textLastLoadedTime.setText(0x7f0a029e);
        ((TextView)findViewById(0x7f0e011e)).setText(0x7f0a029d);
        arrowImage = (ImageView)findViewById(0x7f0e0115);
        arrowImage.setMinimumHeight(50);
        forwardFlipAnimation = new RotateAnimation(0F, -180F, 1, 0.5F, 1, 0.5F);
        forwardFlipAnimation.setInterpolator(new LinearInterpolator());
        forwardFlipAnimation.setDuration(250L);
        forwardFlipAnimation.setFillAfter(true);
        reverseFlipAnimation = new RotateAnimation(-180F, 0F, 1, 0.5F, 1, 0.5F);
        reverseFlipAnimation.setInterpolator(new LinearInterpolator());
        reverseFlipAnimation.setDuration(250L);
        reverseFlipAnimation.setFillAfter(true);
    }

    private void makeTextVisible(TextView textview)
    {
        for(Iterator iterator = textViews.iterator(); iterator.hasNext();)
        {
            TextView textview1 = (TextView)iterator.next();
            if(textview1 == textview)
                textview1.setVisibility(0);
            else
                textview1.setVisibility(4);
        }

    }

    public void setDirection(int i)
    {
        if(i != direction)
        {
            if(i == 0)
                setBackgroundResource(0x7f02010d);
            else
                setBackgroundResource(0x7f02010e);
            direction = i;
        }
    }

    public void setLastLoadedTime(long l)
    {
        if(l >= 0L)
        {
            Context context = getContext();
            Date date = new Date(l);
            String s = DateFormat.getDateFormat(context).format(date);
            String s1 = DateFormat.getTimeFormat(context).format(date);
            Object aobj[] = new Object[2];
            aobj[0] = s;
            aobj[1] = s1;
            String s2 = context.getString(0x7f0a029e, aobj);
            textLastLoadedTime.setText(s2);
            textLastLoadedTime.setVisibility(0);
        } else
        {
            textLastLoadedTime.setVisibility(8);
        }
    }

    public void setState(RefreshableListViewState refreshablelistviewstate)
    {
        RefreshableListViewState refreshablelistviewstate1 = state;
        if(refreshablelistviewstate1 == RefreshableListViewState.PULL_TO_REFRESH && refreshablelistviewstate == RefreshableListViewState.RELEASE_TO_REFRESH)
        {
            makeTextVisible(textViewRelease);
            arrowImage.setImageResource(0x7f0200d6);
            arrowImage.clearAnimation();
            arrowImage.startAnimation(forwardFlipAnimation);
        } else
        if(refreshablelistviewstate1 == RefreshableListViewState.PUSH_TO_REFRESH && refreshablelistviewstate == RefreshableListViewState.RELEASE_TO_REFRESH)
        {
            makeTextVisible(textViewRelease);
            arrowImage.setImageResource(0x7f0200d7);
            arrowImage.clearAnimation();
            arrowImage.startAnimation(forwardFlipAnimation);
        } else
        if(refreshablelistviewstate1 == RefreshableListViewState.RELEASE_TO_REFRESH && refreshablelistviewstate == RefreshableListViewState.PULL_TO_REFRESH)
        {
            makeTextVisible(textViewPull);
            arrowImage.setImageResource(0x7f0200d6);
            arrowImage.clearAnimation();
            arrowImage.startAnimation(reverseFlipAnimation);
        } else
        if(refreshablelistviewstate1 == RefreshableListViewState.RELEASE_TO_REFRESH && refreshablelistviewstate == RefreshableListViewState.PUSH_TO_REFRESH)
        {
            makeTextVisible(textViewPush);
            arrowImage.setImageResource(0x7f0200d7);
            arrowImage.clearAnimation();
            arrowImage.startAnimation(reverseFlipAnimation);
        } else
        if(refreshablelistviewstate == RefreshableListViewState.PULL_TO_REFRESH)
        {
            arrowImage.setImageResource(0x7f0200d6);
            makeTextVisible(textViewPull);
            arrowImage.clearAnimation();
        } else
        if(refreshablelistviewstate == RefreshableListViewState.PUSH_TO_REFRESH)
        {
            arrowImage.setImageResource(0x7f0200d7);
            makeTextVisible(textViewPush);
            arrowImage.clearAnimation();
        } else
        if(refreshablelistviewstate == RefreshableListViewState.RELEASE_TO_REFRESH)
        {
            makeTextVisible(textViewRelease);
            arrowImage.setImageResource(0x7f0200d6);
            arrowImage.clearAnimation();
            arrowImage.startAnimation(forwardFlipAnimation);
        }
        if(refreshablelistviewstate == RefreshableListViewState.LOADING)
        {
            actionContainer.setVisibility(8);
            refreshContainer.setVisibility(0);
        } else
        {
            actionContainer.setVisibility(0);
            refreshContainer.setVisibility(8);
        }
        state = refreshablelistviewstate;
    }

    private View actionContainer;
    private ImageView arrowImage;
    private int direction;
    private RotateAnimation forwardFlipAnimation;
    private View refreshContainer;
    private RotateAnimation reverseFlipAnimation;
    private RefreshableListViewState state;
    private TextView textLastLoadedTime;
    private TextView textViewPull;
    private TextView textViewPush;
    private TextView textViewRelease;
    private List textViews;
}

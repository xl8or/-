// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookActivity.java

package com.facebook.katana.activity;

import android.view.View;

public interface FacebookActivity
{

    public abstract boolean facebookOnBackPressed();

    public abstract long getActivityId();

    public abstract boolean isOnTop();

    public abstract void setTransitioningToBackground(boolean flag);

    public abstract void titleBarClickHandler(View view);

    public abstract void titleBarPrimaryActionClickHandler(View view);

    public abstract void titleBarSearchClickHandler(View view);
}

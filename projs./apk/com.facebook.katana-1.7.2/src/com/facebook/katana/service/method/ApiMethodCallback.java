// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApiMethodCallback.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;

public interface ApiMethodCallback
{

    public abstract void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception);
}

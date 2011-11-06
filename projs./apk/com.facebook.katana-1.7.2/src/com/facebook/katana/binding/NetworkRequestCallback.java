// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NetworkRequestCallback.java

package com.facebook.katana.binding;

import android.content.Context;

public interface NetworkRequestCallback
{

    public abstract void callback(Context context, boolean flag, Object obj, String s, Object obj1, Object obj2);
}

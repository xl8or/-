// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckboxAdapterListener.java

package com.facebook.katana;


public interface CheckboxAdapterListener
{

    public abstract void onMarkChanged(long l, boolean flag, int i);

    public abstract boolean shouldChangeState(long l, boolean flag, int i);
}

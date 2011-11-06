// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookChatConnectionListener.java

package com.facebook.katana.service.xmpp;


public interface FacebookChatConnectionListener
{

    public abstract void onConnectionEstablished();

    public abstract void onConnectionPaused();

    public abstract void onConnectionStopped();
}

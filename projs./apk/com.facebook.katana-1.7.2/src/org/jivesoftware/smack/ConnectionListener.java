// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionListener.java

package org.jivesoftware.smack;


public interface ConnectionListener
{

    public abstract void connectionClosed();

    public abstract void connectionClosedOnError(Exception exception);

    public abstract void reconnectingIn(int i);

    public abstract void reconnectionFailed(Exception exception);

    public abstract void reconnectionSuccessful();
}

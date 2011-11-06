// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrivacyListListener.java

package org.jivesoftware.smack;

import java.util.List;

public interface PrivacyListListener
{

    public abstract void setPrivacyList(String s, List list);

    public abstract void updatedPrivacyList(String s);
}

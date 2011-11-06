// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RosterListener.java

package org.jivesoftware.smack;

import java.util.Collection;
import org.jivesoftware.smack.packet.Presence;

public interface RosterListener
{

    public abstract void entriesAdded(Collection collection);

    public abstract void entriesDeleted(Collection collection);

    public abstract void entriesUpdated(Collection collection);

    public abstract void presenceChanged(Presence presence);
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RosterStorage.java

package org.jivesoftware.smack;

import java.util.List;

public interface RosterStorage
{

    public abstract void addEntry(org.jivesoftware.smack.packet.RosterPacket.Item item, String s);

    public abstract List getEntries();

    public abstract org.jivesoftware.smack.packet.RosterPacket.Item getEntry(String s);

    public abstract int getEntryCount();

    public abstract String getRosterVersion();

    public abstract void removeEntry(String s);

    public abstract void updateLocalEntry(org.jivesoftware.smack.packet.RosterPacket.Item item);
}

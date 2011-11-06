// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

public interface PacketFilter
{

    public abstract boolean accept(Packet packet);
}

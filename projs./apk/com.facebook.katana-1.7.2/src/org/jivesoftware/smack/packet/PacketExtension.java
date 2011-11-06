// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketExtension.java

package org.jivesoftware.smack.packet;


public interface PacketExtension
{

    public abstract String getElementName();

    public abstract String getNamespace();

    public abstract String toXML();
}

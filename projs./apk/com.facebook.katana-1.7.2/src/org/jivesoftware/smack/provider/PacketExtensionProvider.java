// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketExtensionProvider.java

package org.jivesoftware.smack.provider;

import org.jivesoftware.smack.packet.PacketExtension;
import org.xmlpull.v1.XmlPullParser;

public interface PacketExtensionProvider
{

    public abstract PacketExtension parseExtension(XmlPullParser xmlpullparser)
        throws Exception;
}

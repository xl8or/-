package org.jivesoftware.smack.provider;

import org.jivesoftware.smack.packet.PacketExtension;
import org.xmlpull.v1.XmlPullParser;

public interface PacketExtensionProvider {

   PacketExtension parseExtension(XmlPullParser var1) throws Exception;
}

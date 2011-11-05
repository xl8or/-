package org.jivesoftware.smack.packet;


public interface PacketExtension {

   String getElementName();

   String getNamespace();

   String toXML();
}

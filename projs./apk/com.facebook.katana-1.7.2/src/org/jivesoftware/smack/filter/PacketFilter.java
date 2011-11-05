package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

public interface PacketFilter {

   boolean accept(Packet var1);
}

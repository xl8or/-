package org.jivesoftware.smack;

import java.util.List;
import org.jivesoftware.smack.packet.RosterPacket;

public interface RosterStorage {

   void addEntry(RosterPacket.Item var1, String var2);

   List<RosterPacket.Item> getEntries();

   RosterPacket.Item getEntry(String var1);

   int getEntryCount();

   String getRosterVersion();

   void removeEntry(String var1);

   void updateLocalEntry(RosterPacket.Item var1);
}

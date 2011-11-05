package org.jivesoftware.smack;

import java.util.Collection;
import org.jivesoftware.smack.packet.Presence;

public interface RosterListener {

   void entriesAdded(Collection<String> var1);

   void entriesDeleted(Collection<String> var1);

   void entriesUpdated(Collection<String> var1);

   void presenceChanged(Presence var1);
}

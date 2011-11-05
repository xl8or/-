package org.jivesoftware.smack;

import java.util.List;
import org.jivesoftware.smack.packet.PrivacyItem;

public interface PrivacyListListener {

   void setPrivacyList(String var1, List<PrivacyItem> var2);

   void updatedPrivacyList(String var1);
}

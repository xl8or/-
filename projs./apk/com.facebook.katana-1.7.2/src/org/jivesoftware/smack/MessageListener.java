package org.jivesoftware.smack;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

public interface MessageListener {

   void processMessage(Chat var1, Message var2);
}

package org.xbill.DNS;

import java.util.EventListener;
import org.xbill.DNS.Message;

public interface ResolverListener extends EventListener {

   void handleException(Object var1, Exception var2);

   void receiveMessage(Object var1, Message var2);
}

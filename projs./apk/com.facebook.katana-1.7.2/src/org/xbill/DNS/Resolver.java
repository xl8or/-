package org.xbill.DNS;

import java.io.IOException;
import java.util.List;
import org.xbill.DNS.Message;
import org.xbill.DNS.ResolverListener;
import org.xbill.DNS.TSIG;

public interface Resolver {

   Message send(Message var1) throws IOException;

   Object sendAsync(Message var1, ResolverListener var2);

   void setEDNS(int var1);

   void setEDNS(int var1, int var2, int var3, List var4);

   void setIgnoreTruncation(boolean var1);

   void setPort(int var1);

   void setTCP(boolean var1);

   void setTSIGKey(TSIG var1);

   void setTimeout(int var1);

   void setTimeout(int var1, int var2);
}

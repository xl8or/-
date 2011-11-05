package org.apache.qpid.management.common.sasl;

import java.security.Provider;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.harmony.javax.security.sasl.SaslClientFactory;

public class JCAProvider extends Provider {

   private static final long serialVersionUID = 1L;


   public JCAProvider(Map<String, Class<? extends SaslClientFactory>> var1) {
      super("AMQSASLProvider", 1.0D, "A JCA provider that registers all AMQ SASL providers that want to be registered");
      this.register(var1);
   }

   private void register(Map<String, Class<? extends SaslClientFactory>> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         StringBuilder var4 = (new StringBuilder()).append("SaslClientFactory.");
         String var5 = (String)var3.getKey();
         String var6 = var4.append(var5).toString();
         String var7 = ((Class)var3.getValue()).getName();
         this.put(var6, var7);
      }

   }
}

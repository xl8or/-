package org.apache.qpid.management.common.sasl;

import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslClientFactory;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.apache.qpid.management.common.sasl.PlainSaslClient;

public class ClientSaslFactory implements SaslClientFactory {

   public ClientSaslFactory() {}

   public SaslClient createSaslClient(String[] var1, String var2, String var3, String var4, Map var5, CallbackHandler var6) throws SaslException {
      int var7 = 0;

      PlainSaslClient var9;
      while(true) {
         int var8 = var1.length;
         if(var7 >= var8) {
            var9 = null;
            break;
         }

         if(var1[var7].equals("PLAIN")) {
            var9 = new PlainSaslClient(var2, var6);
            break;
         }

         ++var7;
      }

      return var9;
   }

   public String[] getMechanismNames(Map var1) {
      String[] var2 = new String[]{"PLAIN"};
      return var2;
   }
}

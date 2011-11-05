package org.apache.qpid.management.common.sasl;

import de.measite.smack.Sasl;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslClientFactory;
import org.apache.harmony.javax.security.sasl.SaslException;

public class CRAMMD5HashedSaslClientFactory implements SaslClientFactory {

   public static final String MECHANISM = "CRAM-MD5-HASHED";


   public CRAMMD5HashedSaslClientFactory() {}

   public SaslClient createSaslClient(String[] var1, String var2, String var3, String var4, Map<String, ?> var5, CallbackHandler var6) throws SaslException {
      int var7 = 0;

      SaslClient var15;
      while(true) {
         int var8 = var1.length;
         if(var7 >= var8) {
            var15 = null;
            break;
         }

         if(var1[var7].equals("CRAM-MD5-HASHED")) {
            if(var6 == null) {
               throw new SaslException("CallbackHandler must not be null");
            }

            String[] var9 = new String[]{"CRAM-MD5"};
            var15 = Sasl.createSaslClient(var9, var2, var3, var4, var5, var6);
            break;
         }

         ++var7;
      }

      return var15;
   }

   public String[] getMechanismNames(Map var1) {
      String[] var2 = new String[]{"CRAM-MD5-HASHED"};
      return var2;
   }
}

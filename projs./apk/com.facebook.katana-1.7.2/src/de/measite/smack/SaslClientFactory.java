package de.measite.smack;

import com.novell.sasl.client.DigestMD5SaslClient;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.apache.qpid.management.common.sasl.PlainSaslClient;

public class SaslClientFactory implements org.apache.harmony.javax.security.sasl.SaslClientFactory {

   public SaslClientFactory() {}

   public SaslClient createSaslClient(String[] var1, String var2, String var3, String var4, Map<String, ?> var5, CallbackHandler var6) throws SaslException {
      int var7 = var1.length;
      int var8 = 0;

      Object var10;
      while(true) {
         if(var8 >= var7) {
            var10 = null;
            break;
         }

         String var9 = var1[var8];
         if("PLAIN".equals(var9)) {
            var10 = new PlainSaslClient(var2, var6);
            break;
         }

         if("DIGEST-MD5".equals(var9)) {
            var10 = DigestMD5SaslClient.getClient(var2, var3, var4, var5, var6);
            break;
         }

         ++var8;
      }

      return (SaslClient)var10;
   }

   public String[] getMechanismNames(Map<String, ?> var1) {
      String[] var2 = new String[]{"PLAIN", "DIGEST-MD5"};
      return var2;
   }
}

package de.measite.smack;

import de.measite.smack.SaslClientFactory;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.apache.harmony.javax.security.sasl.SaslServer;
import org.apache.harmony.javax.security.sasl.SaslServerFactory;

public class Sasl {

   private static final String CLIENTFACTORYSRV = "SaslClientFactory";
   public static final String MAX_BUFFER = "javax.security.sasl.maxbuffer";
   public static final String POLICY_FORWARD_SECRECY = "javax.security.sasl.policy.forward";
   public static final String POLICY_NOACTIVE = "javax.security.sasl.policy.noactive";
   public static final String POLICY_NOANONYMOUS = "javax.security.sasl.policy.noanonymous";
   public static final String POLICY_NODICTIONARY = "javax.security.sasl.policy.nodictionary";
   public static final String POLICY_NOPLAINTEXT = "javax.security.sasl.policy.noplaintext";
   public static final String POLICY_PASS_CREDENTIALS = "javax.security.sasl.policy.credentials";
   public static final String QOP = "javax.security.sasl.qop";
   public static final String RAW_SEND_SIZE = "javax.security.sasl.rawsendsize";
   public static final String REUSE = "javax.security.sasl.reuse";
   private static final String SERVERFACTORYSRV = "SaslServerFactory";
   public static final String SERVER_AUTH = "javax.security.sasl.server.authentication";
   public static final String STRENGTH = "javax.security.sasl.strength";


   public Sasl() {}

   public static SaslClient createSaslClient(String[] var0, String var1, String var2, String var3, Map<String, ?> var4, CallbackHandler var5) throws SaslException {
      if(var0 == null) {
         throw new NullPointerException("auth.33");
      } else {
         SaslClientFactory var6 = (SaslClientFactory)getSaslClientFactories().nextElement();
         String[] var7 = var6.getMechanismNames((Map)null);
         boolean var15;
         if(var7 != null) {
            int var8 = 0;
            boolean var9 = false;

            while(true) {
               int var10 = var7.length;
               if(var8 >= var10) {
                  var15 = var9;
                  break;
               }

               int var11 = 0;

               while(true) {
                  int var12 = var0.length;
                  if(var11 >= var12) {
                     break;
                  }

                  String var13 = var7[var8];
                  String var14 = var0[var11];
                  if(var13.equals(var14)) {
                     var9 = true;
                     break;
                  }

                  ++var11;
               }

               ++var8;
            }
         } else {
            var15 = false;
         }

         SaslClient var22;
         if(var15) {
            var22 = var6.createSaslClient(var0, var1, var2, var3, var4, var5);
         } else {
            var22 = null;
         }

         return var22;
      }
   }

   public static SaslServer createSaslServer(String var0, String var1, String var2, Map<String, ?> var3, CallbackHandler var4) throws SaslException {
      return org.apache.harmony.javax.security.sasl.Sasl.createSaslServer(var0, var1, var2, var3, var4);
   }

   public static Enumeration<SaslClientFactory> getSaslClientFactories() {
      Hashtable var0 = new Hashtable();
      SaslClientFactory var1 = new SaslClientFactory();
      Object var2 = new Object();
      var0.put(var1, var2);
      return var0.keys();
   }

   public static Enumeration<SaslServerFactory> getSaslServerFactories() {
      return org.apache.harmony.javax.security.sasl.Sasl.getSaslServerFactories();
   }
}

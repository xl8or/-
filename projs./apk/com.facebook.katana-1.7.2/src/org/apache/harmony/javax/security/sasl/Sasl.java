package org.apache.harmony.javax.security.sasl;

import java.security.Provider;
import java.security.Security;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslClientFactory;
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


   private Sasl() {}

   public static SaslClient createSaslClient(String[] var0, String var1, String var2, String var3, Map<String, ?> var4, CallbackHandler var5) throws SaslException {
      if(var0 == null) {
         throw new NullPointerException("auth.33");
      } else {
         Collection var6 = findFactories("SaslClientFactory");
         SaslClient var24;
         if(var6.isEmpty()) {
            var24 = null;
         } else {
            Iterator var7 = var6.iterator();

            while(true) {
               if(!var7.hasNext()) {
                  var24 = null;
                  break;
               }

               SaslClientFactory var23 = (SaslClientFactory)var7.next();
               String[] var8 = var23.getMechanismNames((Map)null);
               boolean var16;
               if(var8 != null) {
                  int var9 = 0;
                  boolean var10 = false;

                  while(true) {
                     int var11 = var8.length;
                     if(var9 >= var11) {
                        var16 = var10;
                        break;
                     }

                     int var12 = 0;

                     while(true) {
                        int var13 = var0.length;
                        if(var12 >= var13) {
                           break;
                        }

                        String var14 = var8[var9];
                        String var15 = var0[var12];
                        if(var14.equals(var15)) {
                           var10 = true;
                           break;
                        }

                        ++var12;
                     }

                     ++var9;
                  }
               } else {
                  var16 = false;
               }

               if(var16) {
                  var24 = var23.createSaslClient(var0, var1, var2, var3, var4, var5);
                  if(var24 != null) {
                     break;
                  }
               }
            }
         }

         return var24;
      }
   }

   public static SaslServer createSaslServer(String var0, String var1, String var2, Map<String, ?> var3, CallbackHandler var4) throws SaslException {
      if(var0 == null) {
         throw new NullPointerException("auth.32");
      } else {
         Collection var5 = findFactories("SaslServerFactory");
         SaslServer var16;
         if(var5.isEmpty()) {
            var16 = null;
         } else {
            Iterator var6 = var5.iterator();

            while(true) {
               if(!var6.hasNext()) {
                  var16 = null;
                  break;
               }

               boolean var10;
               SaslServerFactory var17;
               label32: {
                  var17 = (SaslServerFactory)var6.next();
                  String[] var7 = var17.getMechanismNames((Map)null);
                  if(var7 != null) {
                     int var8 = 0;

                     while(true) {
                        int var9 = var7.length;
                        if(var8 >= var9) {
                           break;
                        }

                        if(var7[var8].equals(var0)) {
                           var10 = true;
                           break label32;
                        }

                        ++var8;
                     }
                  }

                  var10 = false;
               }

               if(var10) {
                  var16 = var17.createSaslServer(var0, var1, var2, var3, var4);
                  if(var16 != null) {
                     break;
                  }
               }
            }
         }

         return var16;
      }
   }

   private static Collection<?> findFactories(String var0) {
      HashSet var1 = new HashSet();
      Provider[] var2 = Security.getProviders();
      HashSet var3;
      if(var2 != null && var2.length != 0) {
         HashSet var4 = new HashSet();
         int var5 = 0;

         while(true) {
            int var6 = var2.length;
            if(var5 >= var6) {
               var3 = var1;
               break;
            }

            String var7 = var2[var5].getName();
            Enumeration var8 = var2[var5].keys();

            while(var8.hasMoreElements()) {
               String var9 = (String)var8.nextElement();
               if(var9.startsWith(var0)) {
                  var9 = var2[var5].getProperty(var9);

                  try {
                     String var10 = var7.concat(var9);
                     if(var4.add(var10)) {
                        Provider var11 = var2[var5];
                        Object var12 = newInstance(var9, var11);
                        var1.add(var12);
                     }
                  } catch (SaslException var14) {
                     var14.printStackTrace();
                  }
               }
            }

            ++var5;
         }
      } else {
         var3 = var1;
      }

      return var3;
   }

   public static Enumeration<SaslClientFactory> getSaslClientFactories() {
      return Collections.enumeration(findFactories("SaslClientFactory"));
   }

   public static Enumeration<SaslServerFactory> getSaslServerFactories() {
      return Collections.enumeration(findFactories("SaslServerFactory"));
   }

   private static Object newInstance(String var0, Provider var1) throws SaslException {
      ClassLoader var2 = var1.getClass().getClassLoader();
      if(var2 == null) {
         var2 = ClassLoader.getSystemClassLoader();
      }

      byte var3 = 1;

      try {
         Object var4 = Class.forName(var0, (boolean)var3, var2).newInstance();
         return var4;
      } catch (IllegalAccessException var11) {
         String var6 = "auth.31" + var0;
         throw new SaslException(var6, var11);
      } catch (ClassNotFoundException var12) {
         String var8 = "auth.31" + var0;
         throw new SaslException(var8, var12);
      } catch (InstantiationException var13) {
         String var10 = "auth.31" + var0;
         throw new SaslException(var10, var13);
      }
   }
}

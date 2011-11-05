package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.AuthPermission;
import org.apache.harmony.javax.security.auth.login.AppConfigurationEntry;

public abstract class Configuration {

   private static final AuthPermission GET_LOGIN_CONFIGURATION = new AuthPermission("getLoginConfiguration");
   private static final String LOGIN_CONFIGURATION_PROVIDER = "login.configuration.provider";
   private static final AuthPermission SET_LOGIN_CONFIGURATION = new AuthPermission("setLoginConfiguration");
   private static Configuration configuration;


   protected Configuration() {}

   static Configuration getAccessibleConfiguration() {
      // $FF: Couldn't be decompiled
   }

   public static Configuration getConfiguration() {
      SecurityManager var0 = System.getSecurityManager();
      if(var0 != null) {
         AuthPermission var1 = GET_LOGIN_CONFIGURATION;
         var0.checkPermission(var1);
      }

      return getAccessibleConfiguration();
   }

   private static final Configuration getDefaultProvider() {
      return new Configuration.1();
   }

   public static void setConfiguration(Configuration var0) {
      SecurityManager var1 = System.getSecurityManager();
      if(var1 != null) {
         AuthPermission var2 = SET_LOGIN_CONFIGURATION;
         var1.checkPermission(var2);
      }

      configuration = var0;
   }

   public abstract AppConfigurationEntry[] getAppConfigurationEntry(String var1);

   public abstract void refresh();

   static class 1 extends Configuration {

      1() {}

      public AppConfigurationEntry[] getAppConfigurationEntry(String var1) {
         return new AppConfigurationEntry[0];
      }

      public void refresh() {}
   }
}

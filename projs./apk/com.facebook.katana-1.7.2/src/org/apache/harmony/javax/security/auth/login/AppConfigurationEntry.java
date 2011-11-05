package org.apache.harmony.javax.security.auth.login;

import java.util.Collections;
import java.util.Map;

public class AppConfigurationEntry {

   private final AppConfigurationEntry.LoginModuleControlFlag controlFlag;
   private final String loginModuleName;
   private final Map<String, ?> options;


   public AppConfigurationEntry(String var1, AppConfigurationEntry.LoginModuleControlFlag var2, Map<String, ?> var3) {
      if(var1 != null && var1.length() != 0) {
         if(var2 == null) {
            throw new IllegalArgumentException("auth.27");
         } else if(var3 == null) {
            throw new IllegalArgumentException("auth.1A");
         } else {
            this.loginModuleName = var1;
            this.controlFlag = var2;
            Map var4 = Collections.unmodifiableMap(var3);
            this.options = var4;
         }
      } else {
         throw new IllegalArgumentException("auth.26");
      }
   }

   public AppConfigurationEntry.LoginModuleControlFlag getControlFlag() {
      return this.controlFlag;
   }

   public String getLoginModuleName() {
      return this.loginModuleName;
   }

   public Map<String, ?> getOptions() {
      return this.options;
   }

   public static class LoginModuleControlFlag {

      public static final AppConfigurationEntry.LoginModuleControlFlag OPTIONAL = new AppConfigurationEntry.LoginModuleControlFlag("LoginModuleControlFlag: optional");
      public static final AppConfigurationEntry.LoginModuleControlFlag REQUIRED = new AppConfigurationEntry.LoginModuleControlFlag("LoginModuleControlFlag: required");
      public static final AppConfigurationEntry.LoginModuleControlFlag REQUISITE = new AppConfigurationEntry.LoginModuleControlFlag("LoginModuleControlFlag: requisite");
      public static final AppConfigurationEntry.LoginModuleControlFlag SUFFICIENT = new AppConfigurationEntry.LoginModuleControlFlag("LoginModuleControlFlag: sufficient");
      private final String flag;


      private LoginModuleControlFlag(String var1) {
         this.flag = var1;
      }

      public String toString() {
         return this.flag;
      }
   }
}

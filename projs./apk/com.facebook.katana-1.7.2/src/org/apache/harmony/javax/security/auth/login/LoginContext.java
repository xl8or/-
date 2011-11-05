package org.apache.harmony.javax.security.auth.login;

import java.io.IOException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import org.apache.harmony.javax.security.auth.AuthPermission;
import org.apache.harmony.javax.security.auth.Subject;
import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.harmony.javax.security.auth.login.AppConfigurationEntry;
import org.apache.harmony.javax.security.auth.login.Configuration;
import org.apache.harmony.javax.security.auth.login.LoginException;
import org.apache.harmony.javax.security.auth.spi.LoginModule;

public class LoginContext {

   private static final String DEFAULT_CALLBACK_HANDLER_PROPERTY = "auth.login.defaultCallbackHandler";
   private static final int OPTIONAL = 0;
   private static final int REQUIRED = 1;
   private static final int REQUISITE = 2;
   private static final int SUFFICIENT = 3;
   private CallbackHandler callbackHandler;
   private ClassLoader contextClassLoader;
   private boolean loggedIn;
   private LoginContext.Module[] modules;
   private Map<String, ?> sharedState;
   private Subject subject;
   private AccessControlContext userContext;
   private boolean userProvidedConfig;
   private boolean userProvidedSubject;


   public LoginContext(String var1) throws LoginException {
      this.init(var1, (Subject)null, (CallbackHandler)null, (Configuration)null);
   }

   public LoginContext(String var1, Subject var2) throws LoginException {
      if(var2 == null) {
         throw new LoginException("auth.03");
      } else {
         this.init(var1, var2, (CallbackHandler)null, (Configuration)null);
      }
   }

   public LoginContext(String var1, Subject var2, CallbackHandler var3) throws LoginException {
      if(var2 == null) {
         throw new LoginException("auth.03");
      } else if(var3 == null) {
         throw new LoginException("auth.34");
      } else {
         this.init(var1, var2, var3, (Configuration)null);
      }
   }

   public LoginContext(String var1, Subject var2, CallbackHandler var3, Configuration var4) throws LoginException {
      this.init(var1, var2, var3, var4);
   }

   public LoginContext(String var1, CallbackHandler var2) throws LoginException {
      if(var2 == null) {
         throw new LoginException("auth.34");
      } else {
         this.init(var1, (Subject)null, var2, (Configuration)null);
      }
   }

   private void init(String var1, Subject var2, CallbackHandler var3, Configuration var4) throws LoginException {
      this.subject = var2;
      byte var5;
      if(var2 != null) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      this.userProvidedSubject = (boolean)var5;
      if(var1 == null) {
         throw new LoginException("auth.00");
      } else {
         Configuration var6;
         if(var4 == null) {
            var6 = Configuration.getAccessibleConfiguration();
         } else {
            this.userProvidedConfig = (boolean)1;
            var6 = var4;
         }

         SecurityManager var7 = System.getSecurityManager();
         if(var7 != null && !this.userProvidedConfig) {
            String var8 = "createLoginContext." + var1;
            AuthPermission var9 = new AuthPermission(var8);
            var7.checkPermission(var9);
         }

         AppConfigurationEntry[] var10 = var6.getAppConfigurationEntry(var1);
         AppConfigurationEntry[] var12;
         if(var10 == null) {
            if(var7 != null && !this.userProvidedConfig) {
               AuthPermission var11 = new AuthPermission("createLoginContext.other");
               var7.checkPermission(var11);
            }

            var12 = var6.getAppConfigurationEntry("other");
            if(var12 == null) {
               String var13 = "auth.35 " + var1;
               throw new LoginException(var13);
            }
         } else {
            var12 = var10;
         }

         LoginContext.Module[] var14 = new LoginContext.Module[var12.length];
         this.modules = var14;
         int var15 = 0;

         while(true) {
            int var16 = this.modules.length;
            if(var15 >= var16) {
               try {
                  Object var20 = AccessController.doPrivileged(new LoginContext.1(var3));
               } catch (PrivilegedActionException var26) {
                  Throwable var22 = var26.getCause();
                  throw (LoginException)(new LoginException("auth.36")).initCause(var22);
               }

               if(this.userProvidedConfig) {
                  AccessControlContext var21 = AccessController.getContext();
                  this.userContext = var21;
                  return;
               } else if(this.callbackHandler == null) {
                  return;
               } else {
                  AccessControlContext var23 = AccessController.getContext();
                  this.userContext = var23;
                  CallbackHandler var24 = this.callbackHandler;
                  LoginContext.ContextedCallbackHandler var25 = new LoginContext.ContextedCallbackHandler(var24);
                  this.callbackHandler = var25;
                  return;
               }
            }

            LoginContext.Module[] var17 = this.modules;
            AppConfigurationEntry var18 = var12[var15];
            LoginContext.Module var19 = new LoginContext.Module(var18);
            var17[var15] = var19;
            ++var15;
         }
      }
   }

   private void loginImpl() throws LoginException {
      if(this.subject == null) {
         Subject var1 = new Subject();
         this.subject = var1;
      }

      if(this.sharedState == null) {
         HashMap var2 = new HashMap();
         this.sharedState = var2;
      }

      int[] var3 = new int[4];
      int[] var4 = new int[4];
      LoginContext.Module[] var5 = this.modules;
      int var6 = var5.length;
      Throwable var7 = null;
      int var8 = 0;

      LoginContext.Module var9;
      Throwable var18;
      while(true) {
         if(var8 >= var6) {
            var18 = var7;
            break;
         }

         var9 = var5[var8];

         label155: {
            int var17;
            try {
               Subject var10 = this.subject;
               CallbackHandler var11 = this.callbackHandler;
               Map var12 = this.sharedState;
               var9.create(var10, var11, var12);
               if(!var9.module.login()) {
                  break label155;
               }

               int var13 = var9.getFlag();
               int var14 = var4[var13] + 1;
               var4[var13] = var14;
               int var15 = var9.getFlag();
               int var16 = var3[var15] + 1;
               var3[var15] = var16;
               var17 = var9.getFlag();
            } catch (Throwable var52) {
               if(var7 == null) {
                  var7 = var52;
               }

               if(var9.klass == null) {
                  int var33 = var4[1] + 1;
                  var4[1] = var33;
                  var18 = var7;
                  break;
               }

               int var34 = var9.getFlag();
               int var35 = var4[var34] + 1;
               var4[var34] = var35;
               if(var9.getFlag() == 2) {
                  var18 = var7;
                  break;
               }
               break label155;
            }

            if(var17 == 3) {
               var18 = var7;
               break;
            }
         }

         ++var8;
      }

      LoginContext.Module[] var23;
      label140: {
         int var19 = var3[1];
         int var20 = var4[1];
         if(var19 == var20) {
            int var21 = var3[2];
            int var22 = var4[2];
            if(var21 != var22) {
               var23 = null;
               break label140;
            }

            if(var4[1] != 0 || var4[2] != 0) {
               var23 = null;
               break label140;
            }

            if(var3[0] != 0 || var3[3] != 0) {
               var23 = null;
               break label140;
            }
         }

         var23 = null;
      }

      int[] var24 = new int[4];
      var4[3] = 0;
      var4[2] = 0;
      var4[1] = 0;
      var4[0] = 0;
      if(var23 == null) {
         var23 = this.modules;
         int var25 = var23.length;
         var7 = var18;

         for(int var26 = 0; var26 < var25; ++var26) {
            var9 = var23[var26];
            if(var9.klass != null) {
               int var27 = var9.getFlag();
               int var28 = var4[var27] + 1;
               var4[var27] = var28;

               try {
                  boolean var29 = var9.module.commit();
                  int var30 = var9.getFlag();
                  int var31 = var24[var30] + 1;
                  var24[var30] = var31;
               } catch (Throwable var51) {
                  if(var7 == null) {
                     ;
                  }
               }
            }
         }

         var18 = var7;
      }

      boolean var41;
      label111: {
         int var37 = var24[1];
         int var38 = var4[1];
         if(var37 == var38) {
            int var39 = var24[2];
            int var40 = var4[2];
            if(var39 != var40) {
               var41 = true;
               break label111;
            }

            if(var4[1] != 0 || var4[2] != 0) {
               var41 = false;
               break label111;
            }

            if(var24[0] != 0 || var24[3] != 0) {
               var41 = false;
               break label111;
            }
         }

         var41 = true;
      }

      if(!var41) {
         this.loggedIn = (boolean)1;
      } else {
         LoginContext.Module[] var42 = this.modules;
         int var43 = var42.length;
         Throwable var44 = var18;

         for(int var45 = 0; var45 < var43; ++var45) {
            LoginContext.Module var46 = var42[var45];

            try {
               boolean var47 = var46.module.abort();
            } catch (Throwable var50) {
               if(var44 == null) {
                  var44 = var50;
               }
            }
         }

         Throwable var49;
         if(var44 instanceof PrivilegedActionException && var44.getCause() != null) {
            var49 = var44.getCause();
         } else {
            var49 = var44;
         }

         if(var49 instanceof LoginException) {
            throw (LoginException)var49;
         } else {
            throw (LoginException)(new LoginException("auth.37")).initCause(var49);
         }
      }
   }

   private void logoutImpl() throws LoginException {
      int var1 = 0;
      if(this.subject == null) {
         throw new LoginException("auth.38");
      } else {
         this.loggedIn = (boolean)0;
         LoginContext.Module[] var2 = this.modules;
         int var3 = var2.length;
         Throwable var4 = null;

         for(int var5 = 0; var5 < var3; ++var5) {
            LoginContext.Module var6 = var2[var5];

            try {
               boolean var7 = var6.module.logout();
            } catch (Throwable var9) {
               if(var4 == null) {
                  var4 = var9;
               }
               continue;
            }

            ++var1;
         }

         if(var4 != null || var1 == 0) {
            Throwable var8;
            if(var4 instanceof PrivilegedActionException && var4.getCause() != null) {
               var8 = var4.getCause();
            } else {
               var8 = var4;
            }

            if(var8 instanceof LoginException) {
               throw (LoginException)var8;
            } else {
               throw (LoginException)(new LoginException("auth.37")).initCause(var8);
            }
         }
      }
   }

   public Subject getSubject() {
      Subject var1;
      if(!this.userProvidedSubject && !this.loggedIn) {
         var1 = null;
      } else {
         var1 = this.subject;
      }

      return var1;
   }

   public void login() throws LoginException {
      LoginContext.2 var1 = new LoginContext.2();

      try {
         if(this.userProvidedConfig) {
            AccessControlContext var2 = this.userContext;
            AccessController.doPrivileged(var1, var2);
         } else {
            Object var4 = AccessController.doPrivileged(var1);
         }
      } catch (PrivilegedActionException var5) {
         throw (LoginException)var5.getException();
      }
   }

   public void logout() throws LoginException {
      LoginContext.3 var1 = new LoginContext.3();

      try {
         if(this.userProvidedConfig) {
            AccessControlContext var2 = this.userContext;
            AccessController.doPrivileged(var1, var2);
         } else {
            Object var4 = AccessController.doPrivileged(var1);
         }
      } catch (PrivilegedActionException var5) {
         throw (LoginException)var5.getException();
      }
   }

   private final class Module {

      AppConfigurationEntry entry;
      int flag;
      Class<?> klass;
      LoginModule module;


      Module(AppConfigurationEntry var2) {
         this.entry = var2;
         AppConfigurationEntry.LoginModuleControlFlag var3 = var2.getControlFlag();
         AppConfigurationEntry.LoginModuleControlFlag var4 = AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL;
         if(var3 == var4) {
            this.flag = 0;
         } else {
            AppConfigurationEntry.LoginModuleControlFlag var5 = AppConfigurationEntry.LoginModuleControlFlag.REQUISITE;
            if(var3 == var5) {
               this.flag = 2;
            } else {
               AppConfigurationEntry.LoginModuleControlFlag var6 = AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT;
               if(var3 == var6) {
                  this.flag = 3;
               } else {
                  this.flag = 1;
               }
            }
         }
      }

      void create(Subject var1, CallbackHandler var2, Map<String, ?> var3) throws LoginException {
         String var4 = this.entry.getLoginModuleName();
         if(this.klass == null) {
            byte var5 = 0;

            try {
               ClassLoader var6 = LoginContext.this.contextClassLoader;
               Class var7 = Class.forName(var4, (boolean)var5, var6);
               this.klass = var7;
            } catch (ClassNotFoundException var19) {
               String var12 = "auth.39 " + var4;
               throw (LoginException)(new LoginException(var12)).initCause(var19);
            }
         }

         if(this.module == null) {
            try {
               LoginModule var8 = (LoginModule)this.klass.newInstance();
               this.module = var8;
            } catch (IllegalAccessException var17) {
               String var14 = "auth.3A " + var4;
               throw (LoginException)(new LoginException(var14)).initCause(var17);
            } catch (InstantiationException var18) {
               String var16 = "auth.3A" + var4;
               throw (LoginException)(new LoginException(var16)).initCause(var18);
            }

            LoginModule var9 = this.module;
            Map var10 = this.entry.getOptions();
            var9.initialize(var1, var2, var3, var10);
         }
      }

      int getFlag() {
         return this.flag;
      }
   }

   private class ContextedCallbackHandler implements CallbackHandler {

      private final CallbackHandler hiddenHandlerRef;


      ContextedCallbackHandler(CallbackHandler var2) {
         this.hiddenHandlerRef = var2;
      }

      public void handle(Callback[] var1) throws IOException, UnsupportedCallbackException {
         try {
            LoginContext.ContextedCallbackHandler.1 var2 = new LoginContext.ContextedCallbackHandler.1(var1);
            AccessControlContext var3 = LoginContext.this.userContext;
            AccessController.doPrivileged(var2, var3);
         } catch (PrivilegedActionException var6) {
            if(var6.getCause() instanceof UnsupportedCallbackException) {
               throw (UnsupportedCallbackException)var6.getCause();
            } else {
               throw (IOException)var6.getCause();
            }
         }
      }

      class 1 implements PrivilegedExceptionAction<Void> {

         // $FF: synthetic field
         final Callback[] val$callbacks;


         1(Callback[] var2) {
            this.val$callbacks = var2;
         }

         public Void run() throws IOException, UnsupportedCallbackException {
            CallbackHandler var1 = ContextedCallbackHandler.this.hiddenHandlerRef;
            Callback[] var2 = this.val$callbacks;
            var1.handle(var2);
            return null;
         }
      }
   }

   class 1 implements PrivilegedExceptionAction<Void> {

      // $FF: synthetic field
      final CallbackHandler val$cbHandler;


      1(CallbackHandler var2) {
         this.val$cbHandler = var2;
      }

      public Void run() throws Exception {
         LoginContext var1 = LoginContext.this;
         ClassLoader var2 = Thread.currentThread().getContextClassLoader();
         var1.contextClassLoader = var2;
         if(LoginContext.this.contextClassLoader == null) {
            LoginContext var4 = LoginContext.this;
            ClassLoader var5 = ClassLoader.getSystemClassLoader();
            var4.contextClassLoader = var5;
         }

         String var7;
         if(this.val$cbHandler == null) {
            var7 = Security.getProperty("auth.login.defaultCallbackHandler");
            if(var7 == null || var7.length() == 0) {
               var7 = null;
               return var7;
            }

            ClassLoader var8 = LoginContext.this.contextClassLoader;
            Class var9 = Class.forName(var7, (boolean)1, var8);
            LoginContext var10 = LoginContext.this;
            CallbackHandler var11 = (CallbackHandler)var9.newInstance();
            var10.callbackHandler = var11;
         } else {
            LoginContext var13 = LoginContext.this;
            CallbackHandler var14 = this.val$cbHandler;
            var13.callbackHandler = var14;
         }

         var7 = null;
         return var7;
      }
   }

   class 3 implements PrivilegedExceptionAction<Void> {

      3() {}

      public Void run() throws LoginException {
         LoginContext.this.logoutImpl();
         return null;
      }
   }

   class 2 implements PrivilegedExceptionAction<Void> {

      2() {}

      public Void run() throws LoginException {
         LoginContext.this.loginImpl();
         return null;
      }
   }
}

package javax.mail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Provider;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;

public final class Session {

   private static final String CUSTOM_ADDRESS_MAP = "META-INF/javamail.address.map";
   private static final String CUSTOM_PROVIDERS = "META-INF/javamail.providers";
   private static final String DEFAULT_ADDRESS_MAP = "META-INF/javamail.default.address.map";
   private static final String DEFAULT_PROVIDERS = "META-INF/javamail.default.providers";
   private static final String SYSTEM_ADDRESS_MAP;
   private static final String SYSTEM_PROVIDERS;
   private static Session defaultSession;
   private Properties addressMap;
   private HashMap authTable;
   private Authenticator authenticator;
   private boolean debug;
   private Logger logger;
   private Properties props;
   private ArrayList providers;
   private HashMap providersByClassName;
   private HashMap providersByProtocol;


   static {
      StringBuilder var0 = new StringBuilder();
      String var1 = System.getProperty("java.home");
      StringBuilder var2 = var0.append(var1);
      String var3 = File.separator;
      StringBuilder var4 = var2.append(var3).append("lib");
      String var5 = File.separator;
      SYSTEM_PROVIDERS = var4.append(var5).append("javamail.providers").toString();
      StringBuilder var6 = new StringBuilder();
      String var7 = System.getProperty("java.home");
      StringBuilder var8 = var6.append(var7);
      String var9 = File.separator;
      StringBuilder var10 = var8.append(var9).append("lib");
      String var11 = File.separator;
      SYSTEM_ADDRESS_MAP = var10.append(var11).append("javamail.address.map").toString();
      defaultSession = null;
   }

   private Session(Properties var1, Authenticator var2) {
      HashMap var3 = new HashMap();
      this.authTable = var3;
      ArrayList var4 = new ArrayList();
      this.providers = var4;
      HashMap var5 = new HashMap();
      this.providersByProtocol = var5;
      HashMap var6 = new HashMap();
      this.providersByClassName = var6;
      Properties var7 = new Properties();
      this.addressMap = var7;
      Logger var8 = Logger.getLogger(Session.class.getName());
      this.logger = var8;
      this.props = var1;
      this.authenticator = var2;
      String var9 = var1.getProperty("mail.debug");
      boolean var10 = (new Boolean(var9)).booleanValue();
      this.debug = var10;
      Logger var11 = this.logger;
      Level var12;
      if(this.debug) {
         var12 = Level.FINER;
      } else {
         var12 = Level.SEVERE;
      }

      var11.setLevel(var12);
      this.logger.info("using GNU JavaMail 1.3");
      ClassLoader var13;
      if(var2 == null) {
         var13 = this.getClass().getClassLoader();
      } else {
         var13 = var2.getClass().getClassLoader();
      }

      InputStream var14 = this.getResourceAsStream(var13, "META-INF/javamail.default.providers");
      this.loadProviders(var14, "default");
      InputStream var15 = this.getResourceAsStream(var13, "META-INF/javamail.providers");
      this.loadProviders(var15, "custom");

      try {
         String var16 = SYSTEM_PROVIDERS;
         File var17 = new File(var16);
         FileInputStream var18 = new FileInputStream(var17);
         BufferedInputStream var19 = new BufferedInputStream(var18);
         this.loadProviders(var19, "system");
      } catch (FileNotFoundException var43) {
         Logger var37 = this.logger;
         Level var38 = Level.WARNING;
         var37.log(var38, "no system providers", var43);
      }

      Logger var20 = this.logger;
      Level var21 = Level.FINE;
      StringBuilder var22 = (new StringBuilder()).append("Providers by class name: ");
      String var23 = this.providersByClassName.toString();
      String var24 = var22.append(var23).toString();
      var20.log(var21, var24);
      Logger var25 = this.logger;
      Level var26 = Level.FINE;
      StringBuilder var27 = (new StringBuilder()).append("Providers by protocol: ");
      String var28 = this.providersByProtocol.toString();
      String var29 = var27.append(var28).toString();
      var25.log(var26, var29);
      InputStream var30 = this.getResourceAsStream(var13, "META-INF/javamail.default.address.map");
      this.loadAddressMap(var30, "default");
      InputStream var31 = this.getResourceAsStream(var13, "META-INF/javamail.address.map");
      this.loadAddressMap(var31, "custom");

      try {
         String var32 = SYSTEM_ADDRESS_MAP;
         File var33 = new File(var32);
         FileInputStream var34 = new FileInputStream(var33);
         BufferedInputStream var35 = new BufferedInputStream(var34);
         this.loadAddressMap(var35, "system");
      } catch (FileNotFoundException var42) {
         Logger var40 = this.logger;
         Level var41 = Level.WARNING;
         var40.log(var41, "no system address map", var42);
      }
   }

   public static Session getDefaultInstance(Properties var0) {
      return getDefaultInstance(var0, (Authenticator)null);
   }

   public static Session getDefaultInstance(Properties var0, Authenticator var1) {
      if(defaultSession == null) {
         defaultSession = new Session(var0, var1);
      } else if(defaultSession.authenticator != var1) {
         if(defaultSession.authenticator != null && var1 != null) {
            ClassLoader var2 = defaultSession.authenticator.getClass().getClassLoader();
            ClassLoader var3 = var1.getClass().getClassLoader();
            if(var2 == var3) {
               return defaultSession;
            }
         }

         throw new SecurityException("Access denied");
      }

      return defaultSession;
   }

   public static Session getInstance(Properties var0) {
      return getInstance(var0, (Authenticator)null);
   }

   public static Session getInstance(Properties var0, Authenticator var1) {
      return new Session(var0, var1);
   }

   private InputStream getResourceAsStream(ClassLoader param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private Object getService(Provider var1, URLName var2) throws NoSuchProviderException {
      if(var1 == null) {
         throw new NoSuchProviderException("null");
      } else {
         URLName var7;
         if(var2 == null) {
            String var3 = var1.getProtocol();
            Object var4 = null;
            Object var5 = null;
            Object var6 = null;
            var7 = new URLName(var3, (String)null, -1, (String)var4, (String)var5, (String)var6);
         } else {
            var7 = var2;
         }

         ClassLoader var8;
         if(this.authenticator != null) {
            var8 = this.authenticator.getClass().getClassLoader();
         } else {
            var8 = this.getClass().getClassLoader();
         }

         Class var11;
         label48: {
            Class var10;
            try {
               String var9 = var1.getClassName();
               var10 = var8.loadClass(var9);
            } catch (Exception var24) {
               try {
                  var10 = Class.forName(var1.getClassName());
               } catch (Exception var23) {
                  if(this.debug) {
                     var23.printStackTrace();
                  }

                  String var19 = var1.getProtocol();
                  throw new NoSuchProviderException(var19);
               }

               var11 = var10;
               break label48;
            }

            var11 = var10;
         }

         byte var12 = 2;

         try {
            Class[] var13 = new Class[var12];
            var13[0] = Session.class;
            var13[1] = URLName.class;
            Constructor var14 = var11.getConstructor(var13);
            Object[] var15 = new Object[]{this, var7};
            Object var16 = var14.newInstance(var15);
            return var16;
         } catch (Exception var22) {
            if(this.debug) {
               var22.printStackTrace();
            }

            String var21 = var1.getProtocol();
            throw new NoSuchProviderException(var21);
         }
      }
   }

   private Store getStore(Provider var1, URLName var2) throws NoSuchProviderException {
      if(var1 != null) {
         Provider.Type var3 = var1.getType();
         Provider.Type var4 = Provider.Type.STORE;
         if(var3 == var4) {
            try {
               Store var7 = (Store)this.getService(var1, var2);
               return var7;
            } catch (ClassCastException var6) {
               throw new NoSuchProviderException("not a store");
            }
         }
      }

      throw new NoSuchProviderException("invalid provider");
   }

   private Transport getTransport(Provider var1, URLName var2) throws NoSuchProviderException {
      if(var1 != null) {
         Provider.Type var3 = var1.getType();
         Provider.Type var4 = Provider.Type.TRANSPORT;
         if(var3 == var4) {
            try {
               Transport var7 = (Transport)this.getService(var1, var2);
               return var7;
            } catch (ClassCastException var6) {
               throw new NoSuchProviderException("incorrect class");
            }
         }
      }

      throw new NoSuchProviderException("invalid provider");
   }

   private void loadAddressMap(InputStream var1, String var2) {
      if(var1 == null) {
         Logger var3 = this.logger;
         String var4 = "no " + var2 + " address map";
         var3.info(var4);
      } else {
         try {
            this.addressMap.load(var1);
            var1.close();
            Logger var5 = this.logger;
            String var6 = "loaded " + var2 + " address map";
            var5.info(var6);
         } catch (IOException var15) {
            Logger var8 = this.logger;
            Level var9 = Level.WARNING;
            String var10 = var15.getMessage();
            var8.log(var9, var10, var15);
         } catch (SecurityException var16) {
            Logger var12 = this.logger;
            Level var13 = Level.WARNING;
            String var14 = "can\'t load " + var2 + " address map";
            var12.log(var13, var14, var16);
         }
      }
   }

   private void loadProviders(InputStream param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void addProvider(Provider var1) {
      String var2 = var1.getProtocol();
      String var3 = var1.getClassName();
      this.providers.add(var1);
      this.providersByClassName.put(var3, var1);
      if(!this.providersByProtocol.containsKey(var2)) {
         this.providersByProtocol.put(var2, var1);
      }
   }

   public boolean getDebug() {
      return this.debug;
   }

   public PrintStream getDebugOut() {
      return System.out;
   }

   public Folder getFolder(URLName var1) throws MessagingException {
      Store var2 = this.getStore(var1);
      var2.connect();
      return var2.getFolder(var1);
   }

   public PasswordAuthentication getPasswordAuthentication(URLName var1) {
      return (PasswordAuthentication)this.authTable.get(var1);
   }

   public Properties getProperties() {
      return this.props;
   }

   public String getProperty(String var1) {
      return this.props.getProperty(var1);
   }

   public Provider getProvider(String param1) throws NoSuchProviderException {
      // $FF: Couldn't be decompiled
   }

   public Provider[] getProviders() {
      Provider[] var1 = new Provider[this.providers.size()];
      this.providers.toArray(var1);
      return var1;
   }

   public Store getStore() throws NoSuchProviderException {
      String var1 = this.getProperty("mail.store.protocol");
      return this.getStore(var1);
   }

   public Store getStore(String var1) throws NoSuchProviderException {
      Object var3 = null;
      Object var4 = null;
      Object var5 = null;
      URLName var6 = new URLName(var1, (String)null, -1, (String)var3, (String)var4, (String)var5);
      return this.getStore(var6);
   }

   public Store getStore(Provider var1) throws NoSuchProviderException {
      return this.getStore(var1, (URLName)null);
   }

   public Store getStore(URLName var1) throws NoSuchProviderException {
      String var2 = var1.getProtocol();
      Provider var3 = this.getProvider(var2);
      return this.getStore(var3, var1);
   }

   public Transport getTransport() throws NoSuchProviderException {
      String var1 = this.getProperty("mail.transport.protocol");
      return this.getTransport(var1);
   }

   public Transport getTransport(String var1) throws NoSuchProviderException {
      Object var3 = null;
      Object var4 = null;
      Object var5 = null;
      URLName var6 = new URLName(var1, (String)null, -1, (String)var3, (String)var4, (String)var5);
      return this.getTransport(var6);
   }

   public Transport getTransport(Address var1) throws NoSuchProviderException {
      Properties var2 = this.addressMap;
      String var3 = var1.getType();
      String var4 = (String)var2.get(var3);
      if(var4 == null) {
         StringBuilder var5 = (new StringBuilder()).append("No provider for address: ");
         String var6 = var1.getType();
         String var7 = var5.append(var6).toString();
         throw new NoSuchProviderException(var7);
      } else {
         return this.getTransport(var4);
      }
   }

   public Transport getTransport(Provider var1) throws NoSuchProviderException {
      return this.getTransport(var1, (URLName)null);
   }

   public Transport getTransport(URLName var1) throws NoSuchProviderException {
      String var2 = var1.getProtocol();
      Provider var3 = this.getProvider(var2);
      return this.getTransport(var3, var1);
   }

   public PasswordAuthentication requestPasswordAuthentication(InetAddress var1, int var2, String var3, String var4, String var5) {
      PasswordAuthentication var12;
      if(this.authenticator != null) {
         Authenticator var6 = this.authenticator;
         var12 = var6.requestPasswordAuthentication(var1, var2, var3, var4, var5);
      } else {
         var12 = null;
      }

      return var12;
   }

   public void setDebug(boolean var1) {
      this.debug = var1;
   }

   public void setDebugOut(PrintStream var1) {
      if(var1 == null) {
         PrintStream var2 = System.out;
      }
   }

   public void setPasswordAuthentication(URLName var1, PasswordAuthentication var2) {
      if(var2 == null) {
         this.authTable.remove(var1);
      } else {
         this.authTable.put(var1, var2);
      }
   }

   public void setProtocolForAddress(String var1, String var2) {
      this.addressMap.put(var1, var2);
   }

   public void setProvider(Provider var1) throws NoSuchProviderException {
      if(var1 == null) {
         throw new NoSuchProviderException("Can\'t set null provider");
      } else {
         ArrayList var2 = this.providers;
         synchronized(var2) {
            String var3 = var1.getProtocol();
            this.providersByProtocol.put(var3, var1);
            String var5 = "mail." + var3 + ".class";
            Properties var6 = this.props;
            String var7 = var1.getClassName();
            var6.put(var5, var7);
         }
      }
   }
}

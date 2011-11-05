package gnu.mail.providers.pop3;

import gnu.inet.pop3.POP3Connection;
import gnu.mail.providers.pop3.POP3Folder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.net.ssl.TrustManager;

public final class POP3Store extends Store {

   POP3Connection connection;
   boolean disableApop;
   POP3Folder root;


   public POP3Store(Session var1, URLName var2) {
      super(var1, var2);
   }

   private int getIntProperty(String var1) {
      String var2 = this.getProperty(var1);
      int var4;
      if(var2 != null) {
         label23: {
            int var3;
            try {
               var3 = Integer.parseInt(var2);
            } catch (Exception var6) {
               break label23;
            }

            var4 = var3;
            return var4;
         }
      }

      var4 = -1;
      return var4;
   }

   private String getProperty(String var1) {
      Session var2 = this.session;
      String var3 = "mail.pop3." + var1;
      String var4 = var2.getProperty(var3);
      if(var4 == null) {
         Session var5 = this.session;
         String var6 = "mail." + var1;
         var4 = var5.getProperty(var6);
      }

      return var4;
   }

   private boolean propertyIsFalse(String var1) {
      String var2 = this.getProperty(var1);
      return "false".equals(var2);
   }

   private boolean propertyIsTrue(String var1) {
      String var2 = this.getProperty(var1);
      return "true".equals(var2);
   }

   public void close() throws MessagingException {
      if(this.connection != null) {
         POP3Connection var1 = this.connection;
         synchronized(var1) {
            try {
               if(this.propertyIsTrue("rsetbeforequit")) {
                  this.connection.rset();
               }

               boolean var2 = this.connection.quit();
            } catch (IOException var5) {
               throw new MessagingException("Close failed", var5);
            }
         }

         this.connection = null;
      }

      super.close();
   }

   public Folder getDefaultFolder() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Folder getFolder(String var1) throws MessagingException {
      return this.getDefaultFolder().getFolder(var1);
   }

   public Folder getFolder(URLName var1) throws MessagingException {
      try {
         String var2 = URLDecoder.decode(var1.getFile(), "UTF-8");
         Folder var3 = this.getDefaultFolder().getFolder(var2);
         return var3;
      } catch (UnsupportedEncodingException var6) {
         String var5 = var6.getMessage();
         throw new MessagingException(var5, var6);
      }
   }

   protected TrustManager getTrustManager() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean isConnected() {
      // $FF: Couldn't be decompiled
   }

   protected boolean protocolConnect(String param1, int param2, String param3, String param4) throws MessagingException {
      // $FF: Couldn't be decompiled
   }
}

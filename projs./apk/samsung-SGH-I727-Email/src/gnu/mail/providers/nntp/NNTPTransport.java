package gnu.mail.providers.nntp;

import gnu.inet.nntp.NNTPConnection;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;

public class NNTPTransport extends Transport {

   NNTPConnection connection;


   public NNTPTransport(Session var1, URLName var2) {
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
            } catch (RuntimeException var6) {
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
      String var3 = "mail.nntp." + var1;
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
      // $FF: Couldn't be decompiled
   }

   protected boolean protocolConnect(String var1, int var2, String var3, String var4) throws MessagingException {
      boolean var5;
      if(this.connection != null) {
         var5 = true;
      } else {
         String var6;
         if(var1 == null) {
            var6 = this.getProperty("host");
         } else {
            var6 = var1;
         }

         String var7;
         if(var3 == null) {
            var7 = this.getProperty("user");
         } else {
            var7 = var3;
         }

         int var8;
         if(var2 < 0) {
            var8 = this.getIntProperty("port");
         } else {
            var8 = var2;
         }

         if(var6 == null) {
            var5 = false;
         } else {
            boolean var14;
            label74: {
               try {
                  int var9 = this.getIntProperty("connectiontimeout");
                  int var10 = this.getIntProperty("timeout");
                  if(var8 < 0) {
                     var8 = 119;
                  }

                  if(this.session.getDebug()) {
                     Logger var11 = NNTPConnection.logger;
                     Level var12 = NNTPConnection.NNTP_TRACE;
                     var11.setLevel(var12);
                  }

                  NNTPConnection var13 = new NNTPConnection(var6, var8, var9, var10);
                  this.connection = var13;
                  if(var7 != null && var4 != null) {
                     var14 = this.connection.authinfo(var7, var4);
                     break label74;
                  }
               } catch (IOException var19) {
                  String var16 = var19.getMessage();
                  throw new MessagingException(var16, var19);
               } catch (SecurityException var20) {
                  if(var7 != null && var4 != null) {
                     String var18 = var20.getMessage();
                     throw new AuthenticationFailedException(var18);
                  }

                  var5 = false;
                  return var5;
               }

               var5 = true;
               return var5;
            }

            var5 = var14;
         }
      }

      return var5;
   }

   public void sendMessage(Message param1, Address[] param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }
}

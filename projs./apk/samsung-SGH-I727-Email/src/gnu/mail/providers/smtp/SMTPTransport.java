package gnu.mail.providers.smtp;

import gnu.inet.smtp.SMTPConnection;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.net.ssl.TrustManager;

public class SMTPTransport extends Transport {

   private List authenticationMechanisms = null;
   protected SMTPConnection connection;
   private List extensions = null;
   protected String localHostName;


   public SMTPTransport(Session var1, URLName var2) {
      super(var1, var2);
      String var3 = this.getProperty("localhost");
      this.localHostName = var3;
      if(this.localHostName == null) {
         try {
            String var4 = InetAddress.getLocalHost().getHostName();
            this.localHostName = var4;
         } catch (UnknownHostException var6) {
            this.localHostName = "localhost";
         }
      }
   }

   private void debugWarning(String var1) {
      PrintStream var2 = System.err;
      StringBuilder var3 = new StringBuilder();
      String var4 = this.url.getProtocol();
      String var5 = var3.append(var4).append(": WARNING: ").append(var1).toString();
      var2.println(var5);
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
      StringBuilder var3 = (new StringBuilder()).append("mail.");
      String var4 = this.url.getProtocol();
      String var5 = var3.append(var4).append(".").append(var1).toString();
      String var6 = var2.getProperty(var5);
      if(var6 == null) {
         Session var7 = this.session;
         String var8 = "mail." + var1;
         var6 = var7.getProperty(var8);
      }

      return var6;
   }

   private TrustManager getTrustManager() throws MessagingException {
      String var1 = this.getProperty("trustmanager");
      TrustManager var6;
      if(var1 != null) {
         TrustManager var5;
         try {
            var5 = (TrustManager)Class.forName(var1).newInstance();
         } catch (Exception var4) {
            String var3 = var4.getMessage();
            throw new MessagingException(var3, var4);
         }

         var6 = var5;
      } else {
         var6 = null;
      }

      return var6;
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

   public String getGreeting() throws MessagingException {
      if(!this.isConnected()) {
         throw new MessagingException("not connected");
      } else {
         SMTPConnection var1 = this.connection;
         synchronized(var1) {
            String var2 = this.connection.getGreeting();
            return var2;
         }
      }
   }

   protected boolean protocolConnect(String param1, int param2, String param3, String param4) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void sendMessage(Message param1, Address[] param2) throws MessagingException, SendFailedException {
      // $FF: Couldn't be decompiled
   }
}

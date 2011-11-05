package javax.mail;

import java.net.InetAddress;
import javax.mail.PasswordAuthentication;

public abstract class Authenticator {

   private String defaultUserName;
   private int requestingPort = -1;
   private String requestingPrompt;
   private String requestingProtocol;
   private InetAddress requestingSite;


   public Authenticator() {}

   protected final String getDefaultUserName() {
      return this.defaultUserName;
   }

   protected PasswordAuthentication getPasswordAuthentication() {
      return null;
   }

   protected final int getRequestingPort() {
      return this.requestingPort;
   }

   protected final String getRequestingPrompt() {
      return this.requestingPrompt;
   }

   protected final String getRequestingProtocol() {
      return this.requestingProtocol;
   }

   protected final InetAddress getRequestingSite() {
      return this.requestingSite;
   }

   final PasswordAuthentication requestPasswordAuthentication(InetAddress var1, int var2, String var3, String var4, String var5) {
      this.requestingSite = var1;
      this.requestingPort = var2;
      this.requestingProtocol = var3;
      this.requestingPrompt = var4;
      this.defaultUserName = var5;
      return this.getPasswordAuthentication();
   }
}

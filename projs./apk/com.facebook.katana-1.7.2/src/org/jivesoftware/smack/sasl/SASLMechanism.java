package org.jivesoftware.smack.sasl;

import de.measite.smack.Sasl;
import java.io.IOException;
import java.util.HashMap;
import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.NameCallback;
import org.apache.harmony.javax.security.auth.callback.PasswordCallback;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.harmony.javax.security.sasl.RealmCallback;
import org.apache.harmony.javax.security.sasl.RealmChoiceCallback;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.Base64;

public abstract class SASLMechanism implements CallbackHandler {

   protected String authenticationId;
   protected String hostname;
   protected String password;
   private SASLAuthentication saslAuthentication;
   protected SaslClient sc;


   public SASLMechanism(SASLAuthentication var1) {
      this.saslAuthentication = var1;
   }

   protected void authenticate() throws IOException, XMPPException {
      String var1 = null;

      label16: {
         String var4;
         try {
            if(!this.sc.hasInitialResponse()) {
               break label16;
            }

            SaslClient var2 = this.sc;
            byte[] var3 = new byte[0];
            var4 = Base64.encodeBytes(var2.evaluateChallenge(var3), 8);
         } catch (SaslException var9) {
            throw new XMPPException("SASL authentication failed", var9);
         }

         var1 = var4;
      }

      SASLAuthentication var5 = this.getSASLAuthentication();
      String var6 = this.getName();
      SASLMechanism.AuthMechanism var7 = new SASLMechanism.AuthMechanism(var6, var1);
      var5.send(var7);
   }

   public void authenticate(String var1, String var2, String var3) throws IOException, XMPPException {
      this.authenticationId = var1;
      this.password = var3;
      this.hostname = var2;
      String[] var4 = new String[1];
      String var5 = this.getName();
      var4[0] = var5;
      HashMap var6 = new HashMap();
      SaslClient var10 = Sasl.createSaslClient(var4, var1, "xmpp", var2, var6, this);
      this.sc = var10;
      this.authenticate();
   }

   public void authenticate(String var1, String var2, CallbackHandler var3) throws IOException, XMPPException {
      String[] var4 = new String[1];
      String var5 = this.getName();
      var4[0] = var5;
      HashMap var6 = new HashMap();
      SaslClient var10 = Sasl.createSaslClient(var4, var1, "xmpp", var2, var6, var3);
      this.sc = var10;
      this.authenticate();
   }

   public void challengeReceived(String var1) throws IOException {
      byte[] var4;
      if(var1 != null) {
         SaslClient var2 = this.sc;
         byte[] var3 = Base64.decode(var1);
         var4 = var2.evaluateChallenge(var3);
      } else {
         SaslClient var6 = this.sc;
         byte[] var7 = new byte[0];
         var4 = var6.evaluateChallenge(var7);
      }

      SASLMechanism.Response var5;
      if(var4 == null) {
         var5 = new SASLMechanism.Response();
      } else {
         String var8 = Base64.encodeBytes(var4, 8);
         var5 = new SASLMechanism.Response(var8);
      }

      this.getSASLAuthentication().send(var5);
   }

   protected abstract String getName();

   protected SASLAuthentication getSASLAuthentication() {
      return this.saslAuthentication;
   }

   public void handle(Callback[] var1) throws IOException, UnsupportedCallbackException {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         if(var1[var2] instanceof NameCallback) {
            NameCallback var4 = (NameCallback)var1[var2];
            String var5 = this.authenticationId;
            var4.setName(var5);
         } else if(var1[var2] instanceof PasswordCallback) {
            PasswordCallback var6 = (PasswordCallback)var1[var2];
            char[] var7 = this.password.toCharArray();
            var6.setPassword(var7);
         } else if(var1[var2] instanceof RealmCallback) {
            RealmCallback var8 = (RealmCallback)var1[var2];
            String var9 = this.hostname;
            var8.setText(var9);
         } else if(!(var1[var2] instanceof RealmChoiceCallback)) {
            Callback var10 = var1[var2];
            throw new UnsupportedCallbackException(var10);
         }

         ++var2;
      }
   }

   public static class Success extends Packet {

      private final String data;


      public Success(String var1) {
         this.data = var1;
      }

      public String toXML() {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("<success xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
         if(this.data != null && this.data.trim().length() > 0) {
            String var3 = this.data;
            var1.append(var3);
         }

         StringBuilder var5 = var1.append("</success>");
         return var1.toString();
      }
   }

   public class AuthMechanism extends Packet {

      private final String authenticationText;
      private final String name;


      public AuthMechanism(String var2, String var3) {
         if(var2 == null) {
            throw new NullPointerException("SASL mechanism name shouldn\'t be null.");
         } else {
            this.name = var2;
            this.authenticationText = var3;
         }
      }

      public String toXML() {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("<auth mechanism=\"");
         String var3 = this.name;
         var2.append(var3);
         StringBuilder var5 = var1.append("\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
         if(this.authenticationText != null && this.authenticationText.trim().length() > 0) {
            String var6 = this.authenticationText;
            var1.append(var6);
         }

         StringBuilder var8 = var1.append("</auth>");
         return var1.toString();
      }
   }

   public static class Failure extends Packet {

      private final String condition;


      public Failure(String var1) {
         this.condition = var1;
      }

      public String getCondition() {
         return this.condition;
      }

      public String toXML() {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("<failure xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
         if(this.condition != null && this.condition.trim().length() > 0) {
            StringBuilder var3 = var1.append("<");
            String var4 = this.condition;
            StringBuilder var5 = var3.append(var4).append("/>");
         }

         StringBuilder var6 = var1.append("</failure>");
         return var1.toString();
      }
   }

   public static class Challenge extends Packet {

      private final String data;


      public Challenge(String var1) {
         this.data = var1;
      }

      public String toXML() {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("<challenge xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
         if(this.data != null && this.data.trim().length() > 0) {
            String var3 = this.data;
            var1.append(var3);
         }

         StringBuilder var5 = var1.append("</challenge>");
         return var1.toString();
      }
   }

   public class Response extends Packet {

      private final String authenticationText;


      public Response() {
         this.authenticationText = null;
      }

      public Response(String var2) {
         if(var2 != null && var2.trim().length() != 0) {
            this.authenticationText = var2;
         } else {
            this.authenticationText = null;
         }
      }

      public String toXML() {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("<response xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
         if(this.authenticationText != null) {
            String var3 = this.authenticationText;
            var1.append(var3);
         } else {
            StringBuilder var6 = var1.append("=");
         }

         StringBuilder var5 = var1.append("</response>");
         return var1.toString();
      }
   }
}

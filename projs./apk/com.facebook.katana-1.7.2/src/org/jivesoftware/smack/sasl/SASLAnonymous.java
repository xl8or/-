package org.jivesoftware.smack.sasl;

import java.io.IOException;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.sasl.SASLMechanism;

public class SASLAnonymous extends SASLMechanism {

   public SASLAnonymous(SASLAuthentication var1) {
      super(var1);
   }

   protected void authenticate() throws IOException {
      SASLAuthentication var1 = this.getSASLAuthentication();
      String var2 = this.getName();
      SASLMechanism.AuthMechanism var3 = new SASLMechanism.AuthMechanism(var2, (String)null);
      var1.send(var3);
   }

   public void authenticate(String var1, String var2, String var3) throws IOException {
      this.authenticate();
   }

   public void authenticate(String var1, String var2, CallbackHandler var3) throws IOException {
      this.authenticate();
   }

   public void challengeReceived(String var1) throws IOException {
      SASLAuthentication var2 = this.getSASLAuthentication();
      SASLMechanism.Response var3 = new SASLMechanism.Response();
      var2.send(var3);
   }

   protected String getName() {
      return "ANONYMOUS";
   }
}

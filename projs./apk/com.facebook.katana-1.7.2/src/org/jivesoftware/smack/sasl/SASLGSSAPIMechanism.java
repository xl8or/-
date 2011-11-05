package org.jivesoftware.smack.sasl;

import de.measite.smack.Sasl;
import java.io.IOException;
import java.util.HashMap;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;

public class SASLGSSAPIMechanism extends SASLMechanism {

   public SASLGSSAPIMechanism(SASLAuthentication var1) {
      super(var1);
      String var2 = System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
      String var3 = System.setProperty("java.security.auth.login.config", "gss.conf");
   }

   public void authenticate(String var1, String var2, String var3) throws IOException, XMPPException {
      String[] var4 = new String[1];
      String var5 = this.getName();
      var4[0] = var5;
      HashMap var6 = new HashMap();
      Object var7 = var6.put("javax.security.sasl.server.authentication", "TRUE");
      SaslClient var11 = Sasl.createSaslClient(var4, var1, "xmpp", var2, var6, this);
      this.sc = var11;
      this.authenticate();
   }

   public void authenticate(String var1, String var2, CallbackHandler var3) throws IOException, XMPPException {
      String[] var4 = new String[1];
      String var5 = this.getName();
      var4[0] = var5;
      HashMap var6 = new HashMap();
      Object var7 = var6.put("javax.security.sasl.server.authentication", "TRUE");
      SaslClient var11 = Sasl.createSaslClient(var4, var1, "xmpp", var2, var6, var3);
      this.sc = var11;
      this.authenticate();
   }

   protected String getName() {
      return "GSSAPI";
   }
}

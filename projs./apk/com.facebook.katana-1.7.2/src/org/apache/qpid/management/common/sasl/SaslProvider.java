package org.apache.qpid.management.common.sasl;

import java.security.Provider;

public class SaslProvider extends Provider {

   private static final long serialVersionUID = -6978096016899676466L;


   public SaslProvider() {
      super("SaslClientFactory", 1.0D, "SASL PLAIN CLIENT MECHANISM");
      Object var1 = this.put("SaslClientFactory.PLAIN", "ClientSaslFactory");
   }
}

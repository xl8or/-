package org.jivesoftware.smack.sasl;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.sasl.SASLMechanism;

public class SASLExternalMechanism extends SASLMechanism {

   public SASLExternalMechanism(SASLAuthentication var1) {
      super(var1);
   }

   protected String getName() {
      return "EXTERNAL";
   }
}

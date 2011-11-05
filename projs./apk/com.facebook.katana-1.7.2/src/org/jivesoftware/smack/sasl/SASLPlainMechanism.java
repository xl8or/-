package org.jivesoftware.smack.sasl;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.sasl.SASLMechanism;

public class SASLPlainMechanism extends SASLMechanism {

   public SASLPlainMechanism(SASLAuthentication var1) {
      super(var1);
   }

   protected String getName() {
      return "PLAIN";
   }
}

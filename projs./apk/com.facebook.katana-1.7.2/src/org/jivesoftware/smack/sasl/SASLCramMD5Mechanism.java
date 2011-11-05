package org.jivesoftware.smack.sasl;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.sasl.SASLMechanism;

public class SASLCramMD5Mechanism extends SASLMechanism {

   public SASLCramMD5Mechanism(SASLAuthentication var1) {
      super(var1);
   }

   protected String getName() {
      return "CRAM-MD5";
   }
}

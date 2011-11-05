package org.jivesoftware.smack.sasl;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.sasl.SASLMechanism;

public class SASLDigestMD5Mechanism extends SASLMechanism {

   public SASLDigestMD5Mechanism(SASLAuthentication var1) {
      super(var1);
   }

   protected String getName() {
      return "DIGEST-MD5";
   }
}

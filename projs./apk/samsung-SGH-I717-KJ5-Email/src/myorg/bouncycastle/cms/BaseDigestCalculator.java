package myorg.bouncycastle.cms;

import myorg.bouncycastle.cms.DigestCalculator;
import myorg.bouncycastle.util.Arrays;

class BaseDigestCalculator implements DigestCalculator {

   private final byte[] digest;


   BaseDigestCalculator(byte[] var1) {
      this.digest = var1;
   }

   public byte[] getDigest() {
      return Arrays.clone(this.digest);
   }
}

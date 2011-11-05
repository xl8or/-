package myorg.bouncycastle.cms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import myorg.bouncycastle.cms.CMSSignedHelper;
import myorg.bouncycastle.cms.DigestCalculator;

class CounterSignatureDigestCalculator implements DigestCalculator {

   private final String alg;
   private final byte[] data;
   private final Provider provider;


   CounterSignatureDigestCalculator(String var1, Provider var2, byte[] var3) {
      this.alg = var1;
      this.provider = var2;
      this.data = var3;
   }

   public byte[] getDigest() throws NoSuchAlgorithmException {
      CMSSignedHelper var1 = CMSSignedHelper.INSTANCE;
      String var2 = this.alg;
      Provider var3 = this.provider;
      MessageDigest var4 = var1.getDigestInstance(var2, var3);
      byte[] var5 = this.data;
      return var4.digest(var5);
   }
}

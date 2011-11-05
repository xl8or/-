package myorg.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.agreement.srp.SRP6Util;

public class SRP6VerifierGenerator {

   protected BigInteger N;
   protected Digest digest;
   protected BigInteger g;


   public SRP6VerifierGenerator() {}

   public BigInteger generateVerifier(byte[] var1, byte[] var2, byte[] var3) {
      Digest var4 = this.digest;
      BigInteger var5 = this.N;
      BigInteger var6 = SRP6Util.calculateX(var4, var5, var1, var2, var3);
      BigInteger var7 = this.g;
      BigInteger var8 = this.N;
      return var7.modPow(var6, var8);
   }

   public void init(BigInteger var1, BigInteger var2, Digest var3) {
      this.N = var1;
      this.g = var2;
      this.digest = var3;
   }
}

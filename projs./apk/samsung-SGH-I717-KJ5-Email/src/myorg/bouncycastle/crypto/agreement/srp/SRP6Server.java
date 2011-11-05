package myorg.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.agreement.srp.SRP6Util;

public class SRP6Server {

   protected BigInteger A;
   protected BigInteger B;
   protected BigInteger N;
   protected BigInteger S;
   protected BigInteger b;
   protected Digest digest;
   protected BigInteger g;
   protected SecureRandom random;
   protected BigInteger u;
   protected BigInteger v;


   public SRP6Server() {}

   private BigInteger calculateS() {
      BigInteger var1 = this.v;
      BigInteger var2 = this.u;
      BigInteger var3 = this.N;
      BigInteger var4 = var1.modPow(var2, var3);
      BigInteger var5 = this.A;
      BigInteger var6 = var4.multiply(var5);
      BigInteger var7 = this.N;
      BigInteger var8 = var6.mod(var7);
      BigInteger var9 = this.b;
      BigInteger var10 = this.N;
      return var8.modPow(var9, var10);
   }

   public BigInteger calculateSecret(BigInteger var1) throws CryptoException {
      BigInteger var2 = SRP6Util.validatePublicValue(this.N, var1);
      this.A = var2;
      Digest var3 = this.digest;
      BigInteger var4 = this.N;
      BigInteger var5 = this.A;
      BigInteger var6 = this.B;
      BigInteger var7 = SRP6Util.calculateU(var3, var4, var5, var6);
      this.u = var7;
      BigInteger var8 = this.calculateS();
      this.S = var8;
      return this.S;
   }

   public BigInteger generateServerCredentials() {
      Digest var1 = this.digest;
      BigInteger var2 = this.N;
      BigInteger var3 = this.g;
      BigInteger var4 = SRP6Util.calculateK(var1, var2, var3);
      BigInteger var5 = this.selectPrivateValue();
      this.b = var5;
      BigInteger var6 = this.v;
      BigInteger var7 = var4.multiply(var6);
      BigInteger var8 = this.N;
      BigInteger var9 = var7.mod(var8);
      BigInteger var10 = this.g;
      BigInteger var11 = this.b;
      BigInteger var12 = this.N;
      BigInteger var13 = var10.modPow(var11, var12);
      BigInteger var14 = var9.add(var13);
      BigInteger var15 = this.N;
      BigInteger var16 = var14.mod(var15);
      this.B = var16;
      return this.B;
   }

   public void init(BigInteger var1, BigInteger var2, BigInteger var3, Digest var4, SecureRandom var5) {
      this.N = var1;
      this.g = var2;
      this.v = var3;
      this.random = var5;
      this.digest = var4;
   }

   protected BigInteger selectPrivateValue() {
      Digest var1 = this.digest;
      BigInteger var2 = this.N;
      BigInteger var3 = this.g;
      SecureRandom var4 = this.random;
      return SRP6Util.generatePrivateValue(var1, var2, var3, var4);
   }
}

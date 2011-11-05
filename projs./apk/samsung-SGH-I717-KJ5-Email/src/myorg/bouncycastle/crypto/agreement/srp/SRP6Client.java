package myorg.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.agreement.srp.SRP6Util;

public class SRP6Client {

   protected BigInteger A;
   protected BigInteger B;
   protected BigInteger N;
   protected BigInteger S;
   protected BigInteger a;
   protected Digest digest;
   protected BigInteger g;
   protected SecureRandom random;
   protected BigInteger u;
   protected BigInteger x;


   public SRP6Client() {}

   private BigInteger calculateS() {
      Digest var1 = this.digest;
      BigInteger var2 = this.N;
      BigInteger var3 = this.g;
      BigInteger var4 = SRP6Util.calculateK(var1, var2, var3);
      BigInteger var5 = this.u;
      BigInteger var6 = this.x;
      BigInteger var7 = var5.multiply(var6);
      BigInteger var8 = this.a;
      BigInteger var9 = var7.add(var8);
      BigInteger var10 = this.g;
      BigInteger var11 = this.x;
      BigInteger var12 = this.N;
      BigInteger var13 = var10.modPow(var11, var12).multiply(var4);
      BigInteger var14 = this.N;
      BigInteger var15 = var13.mod(var14);
      BigInteger var16 = this.B.subtract(var15);
      BigInteger var17 = this.N;
      BigInteger var18 = var16.mod(var17);
      BigInteger var19 = this.N;
      return var18.modPow(var9, var19);
   }

   public BigInteger calculateSecret(BigInteger var1) throws CryptoException {
      BigInteger var2 = SRP6Util.validatePublicValue(this.N, var1);
      this.B = var2;
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

   public BigInteger generateClientCredentials(byte[] var1, byte[] var2, byte[] var3) {
      Digest var4 = this.digest;
      BigInteger var5 = this.N;
      BigInteger var6 = SRP6Util.calculateX(var4, var5, var1, var2, var3);
      this.x = var6;
      BigInteger var7 = this.selectPrivateValue();
      this.a = var7;
      BigInteger var8 = this.g;
      BigInteger var9 = this.a;
      BigInteger var10 = this.N;
      BigInteger var11 = var8.modPow(var9, var10);
      this.A = var11;
      return this.A;
   }

   public void init(BigInteger var1, BigInteger var2, Digest var3, SecureRandom var4) {
      this.N = var1;
      this.g = var2;
      this.digest = var3;
      this.random = var4;
   }

   protected BigInteger selectPrivateValue() {
      Digest var1 = this.digest;
      BigInteger var2 = this.N;
      BigInteger var3 = this.g;
      SecureRandom var4 = this.random;
      return SRP6Util.generatePrivateValue(var1, var2, var3, var4);
   }
}

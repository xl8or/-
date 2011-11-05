package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECDomainParameters implements ECConstants {

   ECPoint G;
   ECCurve curve;
   BigInteger h;
   BigInteger n;
   byte[] seed;


   public ECDomainParameters(ECCurve var1, ECPoint var2, BigInteger var3) {
      this.curve = var1;
      this.G = var2;
      this.n = var3;
      BigInteger var4 = ONE;
      this.h = var4;
      this.seed = null;
   }

   public ECDomainParameters(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4) {
      this.curve = var1;
      this.G = var2;
      this.n = var3;
      this.h = var4;
      this.seed = null;
   }

   public ECDomainParameters(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4, byte[] var5) {
      this.curve = var1;
      this.G = var2;
      this.n = var3;
      this.h = var4;
      this.seed = var5;
   }

   public ECCurve getCurve() {
      return this.curve;
   }

   public ECPoint getG() {
      return this.G;
   }

   public BigInteger getH() {
      return this.h;
   }

   public BigInteger getN() {
      return this.n;
   }

   public byte[] getSeed() {
      return this.seed;
   }
}

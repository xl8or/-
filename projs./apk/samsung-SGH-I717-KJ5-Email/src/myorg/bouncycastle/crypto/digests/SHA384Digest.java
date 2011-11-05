package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.LongDigest;
import myorg.bouncycastle.crypto.util.Pack;

public class SHA384Digest extends LongDigest {

   private static final int DIGEST_LENGTH = 48;


   public SHA384Digest() {}

   public SHA384Digest(SHA384Digest var1) {
      super(var1);
   }

   public int doFinal(byte[] var1, int var2) {
      this.finish();
      Pack.longToBigEndian(this.H1, var1, var2);
      long var3 = this.H2;
      int var5 = var2 + 8;
      Pack.longToBigEndian(var3, var1, var5);
      long var6 = this.H3;
      int var8 = var2 + 16;
      Pack.longToBigEndian(var6, var1, var8);
      long var9 = this.H4;
      int var11 = var2 + 24;
      Pack.longToBigEndian(var9, var1, var11);
      long var12 = this.H5;
      int var14 = var2 + 32;
      Pack.longToBigEndian(var12, var1, var14);
      long var15 = this.H6;
      int var17 = var2 + 40;
      Pack.longToBigEndian(var15, var1, var17);
      this.reset();
      return 48;
   }

   public String getAlgorithmName() {
      return "SHA-384";
   }

   public int getDigestSize() {
      return 48;
   }

   public void reset() {
      super.reset();
      this.H1 = -3766243637369397544L;
      this.H2 = 7105036623409894663L;
      this.H3 = -7973340178411365097L;
      this.H4 = 1526699215303891257L;
      this.H5 = 7436329637833083697L;
      this.H6 = -8163818279084223215L;
      this.H7 = -2662702644619276377L;
      this.H8 = 5167115440072839076L;
   }
}

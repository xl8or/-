package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.LongDigest;
import myorg.bouncycastle.crypto.util.Pack;

public class SHA512Digest extends LongDigest {

   private static final int DIGEST_LENGTH = 64;


   public SHA512Digest() {}

   public SHA512Digest(SHA512Digest var1) {
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
      long var18 = this.H7;
      int var20 = var2 + 48;
      Pack.longToBigEndian(var18, var1, var20);
      long var21 = this.H8;
      int var23 = var2 + 56;
      Pack.longToBigEndian(var21, var1, var23);
      this.reset();
      return 64;
   }

   public String getAlgorithmName() {
      return "SHA-512";
   }

   public int getDigestSize() {
      return 64;
   }

   public void reset() {
      super.reset();
      this.H1 = 7640891576956012808L;
      this.H2 = -4942790177534073029L;
      this.H3 = 4354685564936845355L;
      this.H4 = -6534734903238641935L;
      this.H5 = 5840696475078001361L;
      this.H6 = -7276294671716946913L;
      this.H7 = 2270897969802886507L;
      this.H8 = 6620516959819538809L;
   }
}

package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.ExtendedDigest;

public class ShortenedDigest implements ExtendedDigest {

   private ExtendedDigest baseDigest;
   private int length;


   public ShortenedDigest(ExtendedDigest var1, int var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("baseDigest must not be null");
      } else {
         int var3 = var1.getDigestSize();
         if(var2 > var3) {
            throw new IllegalArgumentException("baseDigest output not large enough to support length");
         } else {
            this.baseDigest = var1;
            this.length = var2;
         }
      }
   }

   public int doFinal(byte[] var1, int var2) {
      byte[] var3 = new byte[this.baseDigest.getDigestSize()];
      this.baseDigest.doFinal(var3, 0);
      int var5 = this.length;
      System.arraycopy(var3, 0, var1, var2, var5);
      return this.length;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.baseDigest.getAlgorithmName();
      StringBuilder var3 = var1.append(var2).append("(");
      int var4 = this.length * 8;
      return var3.append(var4).append(")").toString();
   }

   public int getByteLength() {
      return this.baseDigest.getByteLength();
   }

   public int getDigestSize() {
      return this.length;
   }

   public void reset() {
      this.baseDigest.reset();
   }

   public void update(byte var1) {
      this.baseDigest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.baseDigest.update(var1, var2, var3);
   }
}

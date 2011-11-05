package myorg.bouncycastle.crypto.tls;

import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;

public class CombinedHash implements Digest {

   private Digest md5;
   private Digest sha1;


   public CombinedHash() {
      MD5Digest var1 = new MD5Digest();
      this.md5 = var1;
      SHA1Digest var2 = new SHA1Digest();
      this.sha1 = var2;
   }

   public int doFinal(byte[] var1, int var2) {
      int var3 = this.md5.doFinal(var1, var2);
      Digest var4 = this.sha1;
      int var5 = var2 + 16;
      int var6 = var4.doFinal(var1, var5);
      return var3 + var6;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.md5.getAlgorithmName();
      StringBuilder var3 = var1.append(var2).append(" and ");
      String var4 = this.sha1.getAlgorithmName();
      return var3.append(var4).append(" for TLS 1.0").toString();
   }

   public int getDigestSize() {
      return 36;
   }

   public void reset() {
      this.md5.reset();
      this.sha1.reset();
   }

   public void update(byte var1) {
      this.md5.update(var1);
      this.sha1.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.md5.update(var1, var2, var3);
      this.sha1.update(var1, var2, var3);
   }
}

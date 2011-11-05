package myorg.bouncycastle.jce.provider.util;

import java.io.ByteArrayOutputStream;
import myorg.bouncycastle.crypto.Digest;

public class NullDigest implements Digest {

   private ByteArrayOutputStream bOut;


   public NullDigest() {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      this.bOut = var1;
   }

   public int doFinal(byte[] var1, int var2) {
      byte[] var3 = this.bOut.toByteArray();
      int var4 = var3.length;
      System.arraycopy(var3, 0, var1, var2, var4);
      this.reset();
      return var3.length;
   }

   public String getAlgorithmName() {
      return "NULL";
   }

   public int getDigestSize() {
      return this.bOut.size();
   }

   public void reset() {
      this.bOut.reset();
   }

   public void update(byte var1) {
      this.bOut.write(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.bOut.write(var1, var2, var3);
   }
}

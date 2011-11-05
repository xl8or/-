package myorg.bouncycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.crypto.Digest;

public class DigestOutputStream extends FilterOutputStream {

   protected Digest digest;


   public DigestOutputStream(OutputStream var1, Digest var2) {
      super(var1);
      this.digest = var2;
   }

   public Digest getDigest() {
      return this.digest;
   }

   public void write(int var1) throws IOException {
      Digest var2 = this.digest;
      byte var3 = (byte)var1;
      var2.update(var3);
      this.out.write(var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.digest.update(var1, var2, var3);
      this.out.write(var1, var2, var3);
   }
}

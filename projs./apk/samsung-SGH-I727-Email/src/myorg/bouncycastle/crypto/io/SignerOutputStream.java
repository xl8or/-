package myorg.bouncycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.crypto.Signer;

public class SignerOutputStream extends FilterOutputStream {

   protected Signer signer;


   public SignerOutputStream(OutputStream var1, Signer var2) {
      super(var1);
      this.signer = var2;
   }

   public Signer getSigner() {
      return this.signer;
   }

   public void write(int var1) throws IOException {
      Signer var2 = this.signer;
      byte var3 = (byte)var1;
      var2.update(var3);
      this.out.write(var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.signer.update(var1, var2, var3);
      this.out.write(var1, var2, var3);
   }
}

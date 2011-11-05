package myorg.bouncycastle.sasn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.sasn1.DerGenerator;
import myorg.bouncycastle.sasn1.DerObject;

public class DerSequenceGenerator extends DerGenerator {

   private final ByteArrayOutputStream _bOut;


   public DerSequenceGenerator(OutputStream var1) throws IOException {
      super(var1);
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      this._bOut = var2;
   }

   public DerSequenceGenerator(OutputStream var1, int var2, boolean var3) throws IOException {
      super(var1, var2, var3);
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      this._bOut = var4;
   }

   public void addObject(DerObject var1) throws IOException {
      ByteArrayOutputStream var2 = this._bOut;
      byte[] var3 = var1.getEncoded();
      var2.write(var3);
   }

   public void close() throws IOException {
      byte[] var1 = this._bOut.toByteArray();
      this.writeDerEncoded(48, var1);
   }

   public OutputStream getRawOutputStream() {
      return this._bOut;
   }
}

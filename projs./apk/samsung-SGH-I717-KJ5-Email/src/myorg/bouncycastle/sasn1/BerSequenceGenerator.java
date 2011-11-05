package myorg.bouncycastle.sasn1;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.sasn1.BerGenerator;
import myorg.bouncycastle.sasn1.DerObject;

public class BerSequenceGenerator extends BerGenerator {

   public BerSequenceGenerator(OutputStream var1) throws IOException {
      super(var1);
      this.writeBerHeader(48);
   }

   public BerSequenceGenerator(OutputStream var1, int var2, boolean var3) throws IOException {
      super(var1, var2, var3);
      this.writeBerHeader(48);
   }

   public void addObject(DerObject var1) throws IOException {
      OutputStream var2 = this._out;
      byte[] var3 = var1.getEncoded();
      var2.write(var3);
   }

   public void close() throws IOException {
      this.writeBerEnd();
   }
}

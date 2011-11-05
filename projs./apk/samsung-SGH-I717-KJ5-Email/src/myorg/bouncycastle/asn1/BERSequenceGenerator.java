package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.asn1.BERGenerator;
import myorg.bouncycastle.asn1.BEROutputStream;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;

public class BERSequenceGenerator extends BERGenerator {

   public BERSequenceGenerator(OutputStream var1) throws IOException {
      super(var1);
      this.writeBERHeader(48);
   }

   public BERSequenceGenerator(OutputStream var1, int var2, boolean var3) throws IOException {
      super(var1, var2, var3);
      this.writeBERHeader(48);
   }

   public void addObject(DEREncodable var1) throws IOException {
      DERObject var2 = var1.getDERObject();
      OutputStream var3 = this._out;
      BEROutputStream var4 = new BEROutputStream(var3);
      var2.encode(var4);
   }

   public void close() throws IOException {
      this.writeBEREnd();
   }
}

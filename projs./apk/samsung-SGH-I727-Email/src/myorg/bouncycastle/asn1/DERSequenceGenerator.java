package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERGenerator;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERSequenceGenerator extends DERGenerator {

   private final ByteArrayOutputStream _bOut;


   public DERSequenceGenerator(OutputStream var1) throws IOException {
      super(var1);
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      this._bOut = var2;
   }

   public DERSequenceGenerator(OutputStream var1, int var2, boolean var3) throws IOException {
      super(var1, var2, var3);
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      this._bOut = var4;
   }

   public void addObject(DEREncodable var1) throws IOException {
      DERObject var2 = var1.getDERObject();
      ByteArrayOutputStream var3 = this._bOut;
      DEROutputStream var4 = new DEROutputStream(var3);
      var2.encode(var4);
   }

   public void close() throws IOException {
      byte[] var1 = this._bOut.toByteArray();
      this.writeDEREncoded(48, var1);
   }

   public OutputStream getRawOutputStream() {
      return this._bOut;
   }
}

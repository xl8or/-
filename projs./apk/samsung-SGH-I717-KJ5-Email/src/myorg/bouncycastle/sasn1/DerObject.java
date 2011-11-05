package myorg.bouncycastle.sasn1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import myorg.bouncycastle.sasn1.Asn1Object;
import myorg.bouncycastle.sasn1.DerGenerator;

public class DerObject extends Asn1Object {

   private byte[] _content;


   DerObject(int var1, int var2, byte[] var3) {
      super(var1, var2, (InputStream)null);
      this._content = var3;
   }

   void encode(OutputStream var1) throws IOException {
      DerObject.BasicDerGenerator var2 = new DerObject.BasicDerGenerator(var1);
      int var3 = this._baseTag;
      int var4 = this._tagNumber;
      int var5 = var3 | var4;
      byte[] var6 = this._content;
      var2.writeDerEncoded(var5, var6);
   }

   public byte[] getEncoded() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      this.encode(var1);
      return var1.toByteArray();
   }

   public InputStream getRawContentStream() {
      byte[] var1 = this._content;
      return new ByteArrayInputStream(var1);
   }

   public int getTagNumber() {
      return this._tagNumber;
   }

   private class BasicDerGenerator extends DerGenerator {

      protected BasicDerGenerator(OutputStream var2) {
         super(var2);
      }

      public OutputStream getRawOutputStream() {
         return this._out;
      }
   }
}

package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERConstructedSequence extends ASN1Sequence {

   public DERConstructedSequence() {}

   public void addObject(DEREncodable var1) {
      super.addObject(var1);
   }

   void encode(DEROutputStream var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      DEROutputStream var3 = new DEROutputStream(var2);
      Enumeration var4 = this.getObjects();

      while(var4.hasMoreElements()) {
         Object var5 = var4.nextElement();
         var3.writeObject(var5);
      }

      var3.close();
      byte[] var6 = var2.toByteArray();
      var1.writeEncoded(48, var6);
   }

   public int getSize() {
      return this.size();
   }
}

package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.BEROutputStream;
import myorg.bouncycastle.asn1.DERConstructedSequence;
import myorg.bouncycastle.asn1.DEROutputStream;

public class BERConstructedSequence extends DERConstructedSequence {

   public BERConstructedSequence() {}

   void encode(DEROutputStream var1) throws IOException {
      if(!(var1 instanceof ASN1OutputStream) && !(var1 instanceof BEROutputStream)) {
         super.encode(var1);
      } else {
         var1.write(48);
         var1.write(128);
         Enumeration var2 = this.getObjects();

         while(var2.hasMoreElements()) {
            Object var3 = var2.nextElement();
            var1.writeObject(var3);
         }

         var1.write(0);
         var1.write(0);
      }
   }
}

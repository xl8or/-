package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public abstract class ASN1Object extends DERObject {

   public ASN1Object() {}

   public static ASN1Object fromByteArray(byte[] var0) throws IOException {
      return (ASN1Object)(new ASN1InputStream(var0)).readObject();
   }

   abstract boolean asn1Equals(DERObject var1);

   abstract void encode(DEROutputStream var1) throws IOException;

   public final boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else {
         if(var1 instanceof DEREncodable) {
            DERObject var3 = ((DEREncodable)var1).getDERObject();
            if(this.asn1Equals(var3)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public abstract int hashCode();
}

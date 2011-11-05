package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public abstract class ASN1Null extends ASN1Object {

   public ASN1Null() {}

   boolean asn1Equals(DERObject var1) {
      boolean var2;
      if(!(var1 instanceof ASN1Null)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   abstract void encode(DEROutputStream var1) throws IOException;

   public int hashCode() {
      return -1;
   }

   public String toString() {
      return "NULL";
   }
}

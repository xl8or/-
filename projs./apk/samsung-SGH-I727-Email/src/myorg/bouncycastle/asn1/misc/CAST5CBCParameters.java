package myorg.bouncycastle.asn1.misc;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;

public class CAST5CBCParameters extends ASN1Encodable {

   ASN1OctetString iv;
   DERInteger keyLength;


   public CAST5CBCParameters(ASN1Sequence var1) {
      ASN1OctetString var2 = (ASN1OctetString)var1.getObjectAt(0);
      this.iv = var2;
      DERInteger var3 = (DERInteger)var1.getObjectAt(1);
      this.keyLength = var3;
   }

   public CAST5CBCParameters(byte[] var1, int var2) {
      DEROctetString var3 = new DEROctetString(var1);
      this.iv = var3;
      DERInteger var4 = new DERInteger(var2);
      this.keyLength = var4;
   }

   public static CAST5CBCParameters getInstance(Object var0) {
      CAST5CBCParameters var1;
      if(var0 instanceof CAST5CBCParameters) {
         var1 = (CAST5CBCParameters)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in CAST5CBCParameter factory");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CAST5CBCParameters(var2);
      }

      return var1;
   }

   public byte[] getIV() {
      return this.iv.getOctets();
   }

   public int getKeyLength() {
      return this.keyLength.getValue().intValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1OctetString var2 = this.iv;
      var1.add(var2);
      DERInteger var3 = this.keyLength;
      var1.add(var3);
      return new DERSequence(var1);
   }
}

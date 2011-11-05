package myorg.bouncycastle.asn1.misc;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;

public class IDEACBCPar extends ASN1Encodable {

   ASN1OctetString iv;


   public IDEACBCPar(ASN1Sequence var1) {
      if(var1.size() == 1) {
         ASN1OctetString var2 = (ASN1OctetString)var1.getObjectAt(0);
         this.iv = var2;
      } else {
         this.iv = null;
      }
   }

   public IDEACBCPar(byte[] var1) {
      DEROctetString var2 = new DEROctetString(var1);
      this.iv = var2;
   }

   public static IDEACBCPar getInstance(Object var0) {
      IDEACBCPar var1;
      if(var0 instanceof IDEACBCPar) {
         var1 = (IDEACBCPar)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in IDEACBCPar factory");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new IDEACBCPar(var2);
      }

      return var1;
   }

   public byte[] getIV() {
      byte[] var1;
      if(this.iv != null) {
         var1 = this.iv.getOctets();
      } else {
         var1 = null;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.iv != null) {
         ASN1OctetString var2 = this.iv;
         var1.add(var2);
      }

      return new DERSequence(var1);
   }
}

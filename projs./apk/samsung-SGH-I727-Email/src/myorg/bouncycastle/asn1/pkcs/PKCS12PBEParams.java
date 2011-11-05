package myorg.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;

public class PKCS12PBEParams extends ASN1Encodable {

   DERInteger iterations;
   ASN1OctetString iv;


   public PKCS12PBEParams(ASN1Sequence var1) {
      ASN1OctetString var2 = (ASN1OctetString)var1.getObjectAt(0);
      this.iv = var2;
      DERInteger var3 = (DERInteger)var1.getObjectAt(1);
      this.iterations = var3;
   }

   public PKCS12PBEParams(byte[] var1, int var2) {
      DEROctetString var3 = new DEROctetString(var1);
      this.iv = var3;
      DERInteger var4 = new DERInteger(var2);
      this.iterations = var4;
   }

   public static PKCS12PBEParams getInstance(Object var0) {
      PKCS12PBEParams var1;
      if(var0 instanceof PKCS12PBEParams) {
         var1 = (PKCS12PBEParams)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PKCS12PBEParams(var2);
      }

      return var1;
   }

   public byte[] getIV() {
      return this.iv.getOctets();
   }

   public BigInteger getIterations() {
      return this.iterations.getValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1OctetString var2 = this.iv;
      var1.add(var2);
      DERInteger var3 = this.iterations;
      var1.add(var3);
      return new DERSequence(var1);
   }
}

package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class Challenge extends ASN1Encodable {

   private ASN1OctetString challenge;
   private AlgorithmIdentifier owf;
   private ASN1OctetString witness;


   private Challenge(ASN1Sequence var1) {
      int var2 = 0;
      if(var1.size() == 3) {
         int var3 = 0 + 1;
         AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
         this.owf = var4;
         var2 = var3;
      }

      int var5 = var2 + 1;
      ASN1OctetString var6 = ASN1OctetString.getInstance(var1.getObjectAt(var2));
      this.witness = var6;
      ASN1OctetString var7 = ASN1OctetString.getInstance(var1.getObjectAt(var5));
      this.challenge = var7;
   }

   private void addOptional(ASN1EncodableVector var1, ASN1Encodable var2) {
      if(var2 != null) {
         var1.add(var2);
      }
   }

   public static Challenge getInstance(Object var0) {
      Challenge var1;
      if(var0 instanceof Challenge) {
         var1 = (Challenge)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Challenge(var2);
      }

      return var1;
   }

   public AlgorithmIdentifier getOwf() {
      return this.owf;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.owf;
      this.addOptional(var1, var2);
      ASN1OctetString var3 = this.witness;
      var1.add(var3);
      ASN1OctetString var4 = this.challenge;
      var1.add(var4);
      return new DERSequence(var1);
   }
}

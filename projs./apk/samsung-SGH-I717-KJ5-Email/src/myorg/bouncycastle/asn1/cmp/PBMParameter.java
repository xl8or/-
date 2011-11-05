package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PBMParameter extends ASN1Encodable {

   private DERInteger iterationCount;
   private AlgorithmIdentifier mac;
   private AlgorithmIdentifier owf;
   private ASN1OctetString salt;


   private PBMParameter(ASN1Sequence var1) {
      ASN1OctetString var2 = ASN1OctetString.getInstance(var1.getObjectAt(0));
      this.salt = var2;
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
      this.owf = var3;
      DERInteger var4 = DERInteger.getInstance(var1.getObjectAt(2));
      this.iterationCount = var4;
      AlgorithmIdentifier var5 = AlgorithmIdentifier.getInstance(var1.getObjectAt(3));
      this.mac = var5;
   }

   public static PBMParameter getInstance(Object var0) {
      PBMParameter var1;
      if(var0 instanceof PBMParameter) {
         var1 = (PBMParameter)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PBMParameter(var2);
      }

      return var1;
   }

   public DERInteger getIterationCount() {
      return this.iterationCount;
   }

   public AlgorithmIdentifier getMac() {
      return this.mac;
   }

   public AlgorithmIdentifier getOwf() {
      return this.owf;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1OctetString var2 = this.salt;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.owf;
      var1.add(var3);
      DERInteger var4 = this.iterationCount;
      var1.add(var4);
      AlgorithmIdentifier var5 = this.mac;
      var1.add(var5);
      return new DERSequence(var1);
   }
}

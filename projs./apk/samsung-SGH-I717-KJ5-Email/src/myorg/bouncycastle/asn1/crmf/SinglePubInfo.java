package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class SinglePubInfo extends ASN1Encodable {

   private GeneralName pubLocation;
   private DERInteger pubMethod;


   private SinglePubInfo(ASN1Sequence var1) {
      DERInteger var2 = DERInteger.getInstance(var1.getObjectAt(0));
      this.pubMethod = var2;
      if(var1.size() == 2) {
         GeneralName var3 = GeneralName.getInstance(var1.getObjectAt(1));
         this.pubLocation = var3;
      }
   }

   public static SinglePubInfo getInstance(Object var0) {
      SinglePubInfo var1;
      if(var0 instanceof SinglePubInfo) {
         var1 = (SinglePubInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SinglePubInfo(var2);
      }

      return var1;
   }

   public GeneralName getPubLocation() {
      return this.pubLocation;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.pubMethod;
      var1.add(var2);
      if(this.pubLocation != null) {
         GeneralName var3 = this.pubLocation;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}

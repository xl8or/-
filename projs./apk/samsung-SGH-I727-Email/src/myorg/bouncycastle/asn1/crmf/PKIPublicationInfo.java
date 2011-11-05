package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.crmf.SinglePubInfo;

public class PKIPublicationInfo extends ASN1Encodable {

   private DERInteger action;
   private ASN1Sequence pubInfos;


   private PKIPublicationInfo(ASN1Sequence var1) {
      DERInteger var2 = DERInteger.getInstance(var1.getObjectAt(0));
      this.action = var2;
      ASN1Sequence var3 = ASN1Sequence.getInstance(var1.getObjectAt(1));
      this.pubInfos = var3;
   }

   public static PKIPublicationInfo getInstance(Object var0) {
      PKIPublicationInfo var1;
      if(var0 instanceof PKIPublicationInfo) {
         var1 = (PKIPublicationInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PKIPublicationInfo(var2);
      }

      return var1;
   }

   public DERInteger getAction() {
      return this.action;
   }

   public SinglePubInfo[] getPubInfos() {
      SinglePubInfo[] var1;
      if(this.pubInfos == null) {
         var1 = null;
      } else {
         SinglePubInfo[] var2 = new SinglePubInfo[this.pubInfos.size()];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 == var4) {
               var1 = var2;
               break;
            }

            SinglePubInfo var5 = SinglePubInfo.getInstance(this.pubInfos.getObjectAt(var3));
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.action;
      var1.add(var2);
      ASN1Sequence var3 = this.pubInfos;
      var1.add(var3);
      return new DERSequence(var1);
   }
}

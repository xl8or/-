package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.PKIFreeText;

public class PollRepContent extends ASN1Encodable {

   private DERInteger certReqId;
   private DERInteger checkAfter;
   private PKIFreeText reason;


   private PollRepContent(ASN1Sequence var1) {
      DERInteger var2 = DERInteger.getInstance(var1.getObjectAt(0));
      this.certReqId = var2;
      DERInteger var3 = DERInteger.getInstance(var1.getObjectAt(1));
      this.checkAfter = var3;
      if(var1.size() > 2) {
         PKIFreeText var4 = PKIFreeText.getInstance(var1.getObjectAt(2));
         this.reason = var4;
      }
   }

   public static PollRepContent getInstance(Object var0) {
      PollRepContent var1;
      if(var0 instanceof PollRepContent) {
         var1 = (PollRepContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PollRepContent(var2);
      }

      return var1;
   }

   public DERInteger getCertReqId() {
      return this.certReqId;
   }

   public DERInteger getCheckAfter() {
      return this.checkAfter;
   }

   public PKIFreeText getReason() {
      return this.reason;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.certReqId;
      var1.add(var2);
      DERInteger var3 = this.checkAfter;
      var1.add(var3);
      if(this.reason != null) {
         PKIFreeText var4 = this.reason;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}

package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class OtherRecipientInfo extends ASN1Encodable {

   private DERObjectIdentifier oriType;
   private DEREncodable oriValue;


   public OtherRecipientInfo(ASN1Sequence var1) {
      DERObjectIdentifier var2 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
      this.oriType = var2;
      DEREncodable var3 = var1.getObjectAt(1);
      this.oriValue = var3;
   }

   public OtherRecipientInfo(DERObjectIdentifier var1, DEREncodable var2) {
      this.oriType = var1;
      this.oriValue = var2;
   }

   public static OtherRecipientInfo getInstance(Object var0) {
      OtherRecipientInfo var1;
      if(var0 != null && !(var0 instanceof OtherRecipientInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid OtherRecipientInfo: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OtherRecipientInfo(var2);
      } else {
         var1 = (OtherRecipientInfo)var0;
      }

      return var1;
   }

   public static OtherRecipientInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERObjectIdentifier getType() {
      return this.oriType;
   }

   public DEREncodable getValue() {
      return this.oriValue;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.oriType;
      var1.add(var2);
      DEREncodable var3 = this.oriValue;
      var1.add(var3);
      return new DERSequence(var1);
   }
}

package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class CommitmentTypeIndication extends ASN1Encodable {

   private DERObjectIdentifier commitmentTypeId;
   private ASN1Sequence commitmentTypeQualifier;


   public CommitmentTypeIndication(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.commitmentTypeId = var2;
      if(var1.size() > 1) {
         ASN1Sequence var3 = (ASN1Sequence)var1.getObjectAt(1);
         this.commitmentTypeQualifier = var3;
      }
   }

   public CommitmentTypeIndication(DERObjectIdentifier var1) {
      this.commitmentTypeId = var1;
   }

   public CommitmentTypeIndication(DERObjectIdentifier var1, ASN1Sequence var2) {
      this.commitmentTypeId = var1;
      this.commitmentTypeQualifier = var2;
   }

   public static CommitmentTypeIndication getInstance(Object var0) {
      CommitmentTypeIndication var1;
      if(var0 != null && !(var0 instanceof CommitmentTypeIndication)) {
         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new CommitmentTypeIndication(var2);
      } else {
         var1 = (CommitmentTypeIndication)var0;
      }

      return var1;
   }

   public DERObjectIdentifier getCommitmentTypeId() {
      return this.commitmentTypeId;
   }

   public ASN1Sequence getCommitmentTypeQualifier() {
      return this.commitmentTypeQualifier;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.commitmentTypeId;
      var1.add(var2);
      if(this.commitmentTypeQualifier != null) {
         ASN1Sequence var3 = this.commitmentTypeQualifier;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}

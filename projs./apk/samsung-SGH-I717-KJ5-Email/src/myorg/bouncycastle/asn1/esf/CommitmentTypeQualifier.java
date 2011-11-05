package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class CommitmentTypeQualifier extends ASN1Encodable {

   private DERObjectIdentifier commitmentTypeIdentifier;
   private DEREncodable qualifier;


   public CommitmentTypeQualifier(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.commitmentTypeIdentifier = var2;
      if(var1.size() > 1) {
         DEREncodable var3 = var1.getObjectAt(1);
         this.qualifier = var3;
      }
   }

   public CommitmentTypeQualifier(DERObjectIdentifier var1) {
      this(var1, (DEREncodable)null);
   }

   public CommitmentTypeQualifier(DERObjectIdentifier var1, DEREncodable var2) {
      this.commitmentTypeIdentifier = var1;
      this.qualifier = var2;
   }

   public static CommitmentTypeQualifier getInstance(Object var0) {
      CommitmentTypeQualifier var1;
      if(!(var0 instanceof CommitmentTypeQualifier) && var0 != null) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in getInstance.");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CommitmentTypeQualifier(var2);
      } else {
         var1 = (CommitmentTypeQualifier)var0;
      }

      return var1;
   }

   public DERObjectIdentifier getCommitmentTypeIdentifier() {
      return this.commitmentTypeIdentifier;
   }

   public DEREncodable getQualifier() {
      return this.qualifier;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.commitmentTypeIdentifier;
      var1.add(var2);
      if(this.qualifier != null) {
         DEREncodable var3 = this.qualifier;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}

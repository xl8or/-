package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cmp.CertOrEncCert;
import myorg.bouncycastle.asn1.crmf.EncryptedValue;
import myorg.bouncycastle.asn1.crmf.PKIPublicationInfo;

public class CertifiedKeyPair extends ASN1Encodable {

   private CertOrEncCert certOrEncCert;
   private EncryptedValue privateKey;
   private PKIPublicationInfo publicationInfo;


   private CertifiedKeyPair(ASN1Sequence var1) {
      CertOrEncCert var2 = CertOrEncCert.getInstance(var1.getObjectAt(0));
      this.certOrEncCert = var2;
      if(var1.size() >= 2) {
         if(var1.size() == 2) {
            ASN1TaggedObject var3 = ASN1TaggedObject.getInstance(var1.getObjectAt(1));
            if(var3.getTagNo() == 0) {
               EncryptedValue var4 = EncryptedValue.getInstance(var3.getObject());
               this.privateKey = var4;
            } else {
               PKIPublicationInfo var5 = PKIPublicationInfo.getInstance(var3.getObject());
               this.publicationInfo = var5;
            }
         } else {
            EncryptedValue var6 = EncryptedValue.getInstance(ASN1TaggedObject.getInstance(var1.getObjectAt(1)));
            this.privateKey = var6;
            PKIPublicationInfo var7 = PKIPublicationInfo.getInstance(ASN1TaggedObject.getInstance(var1.getObjectAt(2)));
            this.publicationInfo = var7;
         }
      }
   }

   public static CertifiedKeyPair getInstance(Object var0) {
      CertifiedKeyPair var1;
      if(var0 instanceof CertifiedKeyPair) {
         var1 = (CertifiedKeyPair)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertifiedKeyPair(var2);
      }

      return var1;
   }

   public CertOrEncCert getCertOrEncCert() {
      return this.certOrEncCert;
   }

   public EncryptedValue getPrivateKey() {
      return this.privateKey;
   }

   public PKIPublicationInfo getPublicationInfo() {
      return this.publicationInfo;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      CertOrEncCert var2 = this.certOrEncCert;
      var1.add(var2);
      if(this.privateKey != null) {
         EncryptedValue var3 = this.privateKey;
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, 0, var3);
         var1.add(var4);
      }

      if(this.publicationInfo != null) {
         PKIPublicationInfo var5 = this.publicationInfo;
         DERTaggedObject var6 = new DERTaggedObject((boolean)1, 1, var5);
         var1.add(var6);
      }

      return new DERSequence(var1);
   }
}

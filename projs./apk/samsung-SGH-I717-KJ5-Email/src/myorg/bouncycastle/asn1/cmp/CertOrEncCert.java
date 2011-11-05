package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cmp.CMPCertificate;
import myorg.bouncycastle.asn1.crmf.EncryptedValue;

public class CertOrEncCert extends ASN1Encodable implements ASN1Choice {

   private CMPCertificate certificate;
   private EncryptedValue encryptedCert;


   private CertOrEncCert(ASN1TaggedObject var1) {
      if(var1.getTagNo() == 0) {
         CMPCertificate var2 = CMPCertificate.getInstance(var1.getObject());
         this.certificate = var2;
      } else if(var1.getTagNo() == 1) {
         EncryptedValue var3 = EncryptedValue.getInstance(var1.getObject());
         this.encryptedCert = var3;
      } else {
         StringBuilder var4 = (new StringBuilder()).append("unknown tag: ");
         int var5 = var1.getTagNo();
         String var6 = var4.append(var5).toString();
         throw new IllegalArgumentException(var6);
      }
   }

   public static CertOrEncCert getInstance(Object var0) {
      CertOrEncCert var1;
      if(var0 instanceof CertOrEncCert) {
         var1 = (CertOrEncCert)var0;
      } else {
         if(!(var0 instanceof ASN1TaggedObject)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1TaggedObject var2 = (ASN1TaggedObject)var0;
         var1 = new CertOrEncCert(var2);
      }

      return var1;
   }

   public CMPCertificate getCertificate() {
      return this.certificate;
   }

   public EncryptedValue getEncryptedCert() {
      return this.encryptedCert;
   }

   public DERObject toASN1Object() {
      DERTaggedObject var2;
      if(this.certificate != null) {
         CMPCertificate var1 = this.certificate;
         var2 = new DERTaggedObject((boolean)1, 0, var1);
      } else {
         EncryptedValue var3 = this.encryptedCert;
         var2 = new DERTaggedObject((boolean)1, 1, var3);
      }

      return var2;
   }
}

package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;

public class CMPCertificate extends ASN1Encodable implements ASN1Choice {

   private X509CertificateStructure x509v3PKCert;


   public CMPCertificate(X509CertificateStructure var1) {
      if(var1.getVersion() != 3) {
         throw new IllegalArgumentException("only version 3 certificates allowed");
      } else {
         this.x509v3PKCert = var1;
      }
   }

   public static CMPCertificate getInstance(Object var0) {
      CMPCertificate var1;
      if(var0 instanceof CMPCertificate) {
         var1 = (CMPCertificate)var0;
      } else if(var0 instanceof X509CertificateStructure) {
         X509CertificateStructure var2 = (X509CertificateStructure)var0;
         var1 = new CMPCertificate(var2);
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var4 = (new StringBuilder()).append("Invalid object: ");
            String var5 = var0.getClass().getName();
            String var6 = var4.append(var5).toString();
            throw new IllegalArgumentException(var6);
         }

         X509CertificateStructure var3 = X509CertificateStructure.getInstance(var0);
         var1 = new CMPCertificate(var3);
      }

      return var1;
   }

   public X509CertificateStructure getX509v3PKCert() {
      return this.x509v3PKCert;
   }

   public DERObject toASN1Object() {
      return this.x509v3PKCert.toASN1Object();
   }
}

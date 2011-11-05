package myorg.bouncycastle.jce;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.x509.TBSCertList;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.X509Principal;

public class PrincipalUtil {

   public PrincipalUtil() {}

   public static X509Principal getIssuerX509Principal(X509CRL var0) throws CRLException {
      try {
         X509Name var1 = TBSCertList.getInstance(ASN1Object.fromByteArray(var0.getTBSCertList())).getIssuer();
         X509Principal var2 = new X509Principal(var1);
         return var2;
      } catch (IOException var4) {
         String var3 = var4.toString();
         throw new CRLException(var3);
      }
   }

   public static X509Principal getIssuerX509Principal(X509Certificate var0) throws CertificateEncodingException {
      try {
         X509Name var1 = TBSCertificateStructure.getInstance(ASN1Object.fromByteArray(var0.getTBSCertificate())).getIssuer();
         X509Principal var2 = new X509Principal(var1);
         return var2;
      } catch (IOException var4) {
         String var3 = var4.toString();
         throw new CertificateEncodingException(var3);
      }
   }

   public static X509Principal getSubjectX509Principal(X509Certificate var0) throws CertificateEncodingException {
      try {
         X509Name var1 = TBSCertificateStructure.getInstance(ASN1Object.fromByteArray(var0.getTBSCertificate())).getSubject();
         X509Principal var2 = new X509Principal(var1);
         return var2;
      } catch (IOException var4) {
         String var3 = var4.toString();
         throw new CertificateEncodingException(var3);
      }
   }
}

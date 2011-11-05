package myorg.bouncycastle.x509.extension;

import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertificateParsingException;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.x509.extension.X509ExtensionUtil;

public class SubjectKeyIdentifierStructure extends SubjectKeyIdentifier {

   private AuthorityKeyIdentifier authKeyID;


   public SubjectKeyIdentifierStructure(PublicKey var1) throws CertificateParsingException {
      ASN1OctetString var2 = fromPublicKey(var1);
      super(var2);
   }

   public SubjectKeyIdentifierStructure(byte[] var1) throws IOException {
      ASN1OctetString var2 = (ASN1OctetString)X509ExtensionUtil.fromExtensionValue(var1);
      super(var2);
   }

   private static ASN1OctetString fromPublicKey(PublicKey var0) throws CertificateParsingException {
      try {
         byte[] var1 = var0.getEncoded();
         ASN1Sequence var2 = (ASN1Sequence)(new ASN1InputStream(var1)).readObject();
         SubjectPublicKeyInfo var3 = new SubjectPublicKeyInfo(var2);
         ASN1OctetString var9 = (ASN1OctetString)((ASN1OctetString)(new SubjectKeyIdentifier(var3)).toASN1Object());
         return var9;
      } catch (Exception var8) {
         StringBuilder var5 = (new StringBuilder()).append("Exception extracting certificate details: ");
         String var6 = var8.toString();
         String var7 = var5.append(var6).toString();
         throw new CertificateParsingException(var7);
      }
   }
}

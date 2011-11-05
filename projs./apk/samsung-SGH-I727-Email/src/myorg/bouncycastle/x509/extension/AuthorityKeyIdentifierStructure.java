package myorg.bouncycastle.x509.extension;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.x509.extension.X509ExtensionUtil;

public class AuthorityKeyIdentifierStructure extends AuthorityKeyIdentifier {

   public AuthorityKeyIdentifierStructure(PublicKey var1) throws InvalidKeyException {
      ASN1Sequence var2 = fromKey(var1);
      super(var2);
   }

   public AuthorityKeyIdentifierStructure(X509Certificate var1) throws CertificateParsingException {
      ASN1Sequence var2 = fromCertificate(var1);
      super(var2);
   }

   public AuthorityKeyIdentifierStructure(byte[] var1) throws IOException {
      ASN1Sequence var2 = (ASN1Sequence)X509ExtensionUtil.fromExtensionValue(var1);
      super(var2);
   }

   private static ASN1Sequence fromCertificate(X509Certificate param0) throws CertificateParsingException {
      // $FF: Couldn't be decompiled
   }

   private static ASN1Sequence fromKey(PublicKey var0) throws InvalidKeyException {
      try {
         byte[] var1 = var0.getEncoded();
         ASN1Sequence var2 = (ASN1Sequence)(new ASN1InputStream(var1)).readObject();
         SubjectPublicKeyInfo var3 = new SubjectPublicKeyInfo(var2);
         ASN1Sequence var7 = (ASN1Sequence)(new AuthorityKeyIdentifier(var3)).toASN1Object();
         return var7;
      } catch (Exception var6) {
         String var5 = "can\'t process key: " + var6;
         throw new InvalidKeyException(var5);
      }
   }
}

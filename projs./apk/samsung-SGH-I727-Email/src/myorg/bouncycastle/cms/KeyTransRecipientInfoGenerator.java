package myorg.bouncycastle.cms;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import myorg.bouncycastle.asn1.cms.RecipientIdentifier;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInfoGenerator;

class KeyTransRecipientInfoGenerator implements RecipientInfoGenerator {

   private SubjectPublicKeyInfo info;
   private PublicKey recipientPublicKey;
   private TBSCertificateStructure recipientTBSCert;
   private ASN1OctetString subjectKeyIdentifier;


   KeyTransRecipientInfoGenerator() {}

   public RecipientInfo generate(SecretKey var1, SecureRandom var2, Provider var3) throws GeneralSecurityException {
      AlgorithmIdentifier var4 = this.info.getAlgorithmId();
      CMSEnvelopedHelper var5 = CMSEnvelopedHelper.INSTANCE;
      String var6 = var4.getObjectId().getId();
      Cipher var7 = var5.createAsymmetricCipher(var6, var3);

      DEROctetString var10;
      try {
         PublicKey var8 = this.recipientPublicKey;
         var7.init(3, var8, var2);
         byte[] var9 = var7.wrap(var1);
         var10 = new DEROctetString(var9);
      } catch (GeneralSecurityException var29) {
         PublicKey var17 = this.recipientPublicKey;
         var7.init(1, var17, var2);
         byte[] var18 = var1.getEncoded();
         byte[] var19 = var7.doFinal(var18);
         var10 = new DEROctetString(var19);
      } catch (IllegalStateException var30) {
         PublicKey var21 = this.recipientPublicKey;
         var7.init(1, var21, var2);
         byte[] var22 = var1.getEncoded();
         byte[] var23 = var7.doFinal(var22);
         var10 = new DEROctetString(var23);
      } catch (UnsupportedOperationException var31) {
         PublicKey var25 = this.recipientPublicKey;
         var7.init(1, var25, var2);
         byte[] var26 = var1.getEncoded();
         byte[] var27 = var7.doFinal(var26);
         var10 = new DEROctetString(var27);
      }

      RecipientIdentifier var14;
      if(this.recipientTBSCert != null) {
         X509Name var11 = this.recipientTBSCert.getIssuer();
         BigInteger var12 = this.recipientTBSCert.getSerialNumber().getValue();
         IssuerAndSerialNumber var13 = new IssuerAndSerialNumber(var11, var12);
         var14 = new RecipientIdentifier(var13);
      } else {
         ASN1OctetString var28 = this.subjectKeyIdentifier;
         var14 = new RecipientIdentifier(var28);
      }

      KeyTransRecipientInfo var15 = new KeyTransRecipientInfo(var14, var4, var10);
      return new RecipientInfo(var15);
   }

   void setRecipientCert(X509Certificate var1) {
      try {
         TBSCertificateStructure var2 = CMSUtils.getTBSCertificateStructure(var1);
         this.recipientTBSCert = var2;
      } catch (CertificateEncodingException var6) {
         throw new IllegalArgumentException("can\'t extract TBS structure from this cert");
      }

      PublicKey var3 = var1.getPublicKey();
      this.recipientPublicKey = var3;
      SubjectPublicKeyInfo var4 = this.recipientTBSCert.getSubjectPublicKeyInfo();
      this.info = var4;
   }

   void setRecipientPublicKey(PublicKey var1) {
      this.recipientPublicKey = var1;

      try {
         SubjectPublicKeyInfo var2 = SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray(var1.getEncoded()));
         this.info = var2;
      } catch (IOException var4) {
         throw new IllegalArgumentException("can\'t extract key algorithm from this key");
      }
   }

   void setSubjectKeyIdentifier(ASN1OctetString var1) {
      this.subjectKeyIdentifier = var1;
   }
}

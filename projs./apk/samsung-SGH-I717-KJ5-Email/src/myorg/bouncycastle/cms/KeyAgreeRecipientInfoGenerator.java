package myorg.bouncycastle.cms;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.KeyAgreeRecipientIdentifier;
import myorg.bouncycastle.asn1.cms.KeyAgreeRecipientInfo;
import myorg.bouncycastle.asn1.cms.OriginatorIdentifierOrKey;
import myorg.bouncycastle.asn1.cms.RecipientEncryptedKey;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInfoGenerator;

class KeyAgreeRecipientInfoGenerator implements RecipientInfoGenerator {

   private DERObjectIdentifier algorithmOID;
   private OriginatorIdentifierOrKey originator;
   private TBSCertificateStructure recipientTBSCert;
   private ASN1OctetString ukm;
   private DERObjectIdentifier wrapAlgorithmOID;
   private SecretKey wrapKey;


   KeyAgreeRecipientInfoGenerator() {}

   public RecipientInfo generate(SecretKey var1, SecureRandom var2, Provider var3) throws GeneralSecurityException {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      DERObjectIdentifier var5 = this.wrapAlgorithmOID;
      var4.add(var5);
      DERNull var6 = DERNull.INSTANCE;
      var4.add(var6);
      DERObjectIdentifier var7 = this.algorithmOID;
      DERSequence var8 = new DERSequence(var4);
      AlgorithmIdentifier var9 = new AlgorithmIdentifier(var7, var8);
      X509Name var10 = this.recipientTBSCert.getIssuer();
      BigInteger var11 = this.recipientTBSCert.getSerialNumber().getValue();
      IssuerAndSerialNumber var12 = new IssuerAndSerialNumber(var10, var11);
      CMSEnvelopedHelper var13 = CMSEnvelopedHelper.INSTANCE;
      String var14 = this.wrapAlgorithmOID.getId();
      Cipher var15 = var13.createAsymmetricCipher(var14, var3);
      SecretKey var16 = this.wrapKey;
      var15.init(3, var16, var2);
      byte[] var17 = var15.wrap(var1);
      DEROctetString var18 = new DEROctetString(var17);
      KeyAgreeRecipientIdentifier var19 = new KeyAgreeRecipientIdentifier(var12);
      RecipientEncryptedKey var20 = new RecipientEncryptedKey(var19, var18);
      OriginatorIdentifierOrKey var21 = this.originator;
      ASN1OctetString var22 = this.ukm;
      DERSequence var23 = new DERSequence(var20);
      KeyAgreeRecipientInfo var24 = new KeyAgreeRecipientInfo(var21, var22, var9, var23);
      return new RecipientInfo(var24);
   }

   void setAlgorithmOID(DERObjectIdentifier var1) {
      this.algorithmOID = var1;
   }

   void setOriginator(OriginatorIdentifierOrKey var1) {
      this.originator = var1;
   }

   void setRecipientCert(X509Certificate var1) {
      try {
         TBSCertificateStructure var2 = CMSUtils.getTBSCertificateStructure(var1);
         this.recipientTBSCert = var2;
      } catch (CertificateEncodingException var4) {
         throw new IllegalArgumentException("can\'t extract TBS structure from this cert");
      }
   }

   void setUKM(ASN1OctetString var1) {
      this.ukm = var1;
   }

   void setWrapAlgorithmOID(DERObjectIdentifier var1) {
      this.wrapAlgorithmOID = var1;
   }

   void setWrapKey(SecretKey var1) {
      this.wrapKey = var1;
   }
}

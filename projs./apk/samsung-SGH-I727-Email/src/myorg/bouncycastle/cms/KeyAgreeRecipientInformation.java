package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.KeyAgreeRecipientIdentifier;
import myorg.bouncycastle.asn1.cms.KeyAgreeRecipientInfo;
import myorg.bouncycastle.asn1.cms.OriginatorIdentifierOrKey;
import myorg.bouncycastle.asn1.cms.OriginatorPublicKey;
import myorg.bouncycastle.asn1.cms.RecipientEncryptedKey;
import myorg.bouncycastle.asn1.cms.RecipientKeyIdentifier;
import myorg.bouncycastle.asn1.cms.ecc.MQVuserKeyingMaterial;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.cms.CMSEnvelopedGenerator;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSTypedStream;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.OriginatorId;
import myorg.bouncycastle.cms.RecipientId;
import myorg.bouncycastle.cms.RecipientInformation;
import myorg.bouncycastle.jce.spec.MQVPrivateKeySpec;
import myorg.bouncycastle.jce.spec.MQVPublicKeySpec;

public class KeyAgreeRecipientInformation extends RecipientInformation {

   private ASN1OctetString encryptedKey;
   private KeyAgreeRecipientInfo info;


   public KeyAgreeRecipientInformation(KeyAgreeRecipientInfo var1, AlgorithmIdentifier var2, InputStream var3) {
      Object var7 = null;
      this(var1, var2, (AlgorithmIdentifier)null, (AlgorithmIdentifier)var7, var3);
   }

   public KeyAgreeRecipientInformation(KeyAgreeRecipientInfo var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, InputStream var4) {
      this(var1, var2, var3, (AlgorithmIdentifier)null, var4);
   }

   KeyAgreeRecipientInformation(KeyAgreeRecipientInfo var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4, InputStream var5) {
      AlgorithmIdentifier var6 = var1.getKeyEncryptionAlgorithm();
      super(var2, var3, var4, var6, var5);
      this.info = var1;
      RecipientId var12 = new RecipientId();
      this.rid = var12;

      try {
         RecipientEncryptedKey var13 = RecipientEncryptedKey.getInstance(this.info.getRecipientEncryptedKeys().getObjectAt(0));
         KeyAgreeRecipientIdentifier var14 = var13.getIdentifier();
         IssuerAndSerialNumber var15 = var14.getIssuerAndSerialNumber();
         if(var15 != null) {
            RecipientId var16 = this.rid;
            byte[] var17 = var15.getName().getEncoded();
            var16.setIssuer(var17);
            RecipientId var18 = this.rid;
            BigInteger var19 = var15.getSerialNumber().getValue();
            var18.setSerialNumber(var19);
         } else {
            RecipientKeyIdentifier var21 = var14.getRKeyID();
            RecipientId var22 = this.rid;
            byte[] var23 = var21.getSubjectKeyIdentifier().getOctets();
            var22.setSubjectKeyIdentifier(var23);
         }

         ASN1OctetString var20 = var13.getEncryptedKey();
         this.encryptedKey = var20;
      } catch (IOException var25) {
         throw new IllegalArgumentException("invalid rid in KeyAgreeRecipientInformation");
      }
   }

   private SecretKey calculateAgreedWrapKey(String var1, PublicKey var2, PrivateKey var3, Provider var4) throws CMSException, GeneralSecurityException, IOException {
      String var5 = this.keyEncAlg.getObjectId().getId();
      String var6 = CMSEnvelopedGenerator.ECMQV_SHA1KDF;
      if(var5.equals(var6)) {
         OriginatorPublicKey var7 = MQVuserKeyingMaterial.getInstance(ASN1Object.fromByteArray(this.info.getUserKeyingMaterial().getOctets())).getEphemeralPublicKey();
         PublicKey var8 = this.getPublicKeyFromOriginatorPublicKey((Key)var3, var7, var4);
         MQVPublicKeySpec var9 = new MQVPublicKeySpec((PublicKey)var2, var8);
         var3 = new MQVPrivateKeySpec((PrivateKey)var3, (PrivateKey)var3);
         var2 = var9;
      }

      KeyAgreement var10 = KeyAgreement.getInstance(var5, var4);
      var10.init((Key)var3);
      var10.doPhase((Key)var2, (boolean)1);
      return var10.generateSecret(var1);
   }

   private PublicKey getPublicKeyFromOriginatorId(OriginatorId var1, Provider var2) throws CMSException {
      throw new CMSException("No support for \'originator\' as IssuerAndSerialNumber or SubjectKeyIdentifier");
   }

   private PublicKey getPublicKeyFromOriginatorPublicKey(Key var1, OriginatorPublicKey var2, Provider var3) throws CMSException, GeneralSecurityException, IOException {
      AlgorithmIdentifier var4 = PrivateKeyInfo.getInstance(ASN1Object.fromByteArray(var1.getEncoded())).getAlgorithmId();
      byte[] var5 = var2.getPublicKey().getBytes();
      byte[] var6 = (new SubjectPublicKeyInfo(var4, var5)).getEncoded();
      X509EncodedKeySpec var7 = new X509EncodedKeySpec(var6);
      return KeyFactory.getInstance(this.keyEncAlg.getObjectId().getId(), var3).generatePublic(var7);
   }

   private PublicKey getSenderPublicKey(Key var1, OriginatorIdentifierOrKey var2, Provider var3) throws CMSException, GeneralSecurityException, IOException {
      OriginatorPublicKey var4 = var2.getOriginatorKey();
      PublicKey var5;
      if(var4 != null) {
         var5 = this.getPublicKeyFromOriginatorPublicKey(var1, var4, var3);
      } else {
         OriginatorId var6 = new OriginatorId();
         IssuerAndSerialNumber var7 = var2.getIssuerAndSerialNumber();
         if(var7 != null) {
            byte[] var8 = var7.getName().getEncoded();
            var6.setIssuer(var8);
            BigInteger var9 = var7.getSerialNumber().getValue();
            var6.setSerialNumber(var9);
         } else {
            byte[] var10 = var2.getSubjectKeyIdentifier().getKeyIdentifier();
            var6.setSubjectKeyIdentifier(var10);
         }

         var5 = this.getPublicKeyFromOriginatorId(var6, var3);
      }

      return var5;
   }

   private Key unwrapSessionKey(String var1, SecretKey var2, Provider var3) throws GeneralSecurityException {
      String var4 = this.getActiveAlgID().getObjectId().getId();
      byte[] var5 = this.encryptedKey.getOctets();
      Cipher var6 = Cipher.getInstance(var1, var3);
      var6.init(4, var2);
      return var6.unwrap(var5, var4, 3);
   }

   public CMSTypedStream getContentStream(Key var1, String var2) throws CMSException, NoSuchProviderException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getContentStream(var1, var3);
   }

   public CMSTypedStream getContentStream(Key var1, Provider var2) throws CMSException {
      Key var3 = this.getSessionKey(var1, var2);
      return this.getContentFromSessionKey(var3, var2);
   }

   protected Key getSessionKey(Key var1, Provider var2) throws CMSException {
      try {
         String var3 = DERObjectIdentifier.getInstance(ASN1Sequence.getInstance(this.keyEncAlg.getParameters()).getObjectAt(0)).getId();
         OriginatorIdentifierOrKey var4 = this.info.getOriginator();
         PublicKey var5 = this.getSenderPublicKey(var1, var4, var2);
         PrivateKey var6 = (PrivateKey)var1;
         SecretKey var7 = this.calculateAgreedWrapKey(var3, var5, var6, var2);
         Key var8 = this.unwrapSessionKey(var3, var7, var2);
         return var8;
      } catch (NoSuchAlgorithmException var14) {
         throw new CMSException("can\'t find algorithm.", var14);
      } catch (InvalidKeyException var15) {
         throw new CMSException("key invalid in message.", var15);
      } catch (InvalidKeySpecException var16) {
         throw new CMSException("originator key spec invalid.", var16);
      } catch (NoSuchPaddingException var17) {
         throw new CMSException("required padding not supported.", var17);
      } catch (Exception var18) {
         throw new CMSException("originator key invalid.", var18);
      }
   }
}

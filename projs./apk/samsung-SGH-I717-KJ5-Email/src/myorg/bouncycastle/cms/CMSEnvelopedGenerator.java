package myorg.bouncycastle.cms;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.cms.KEKIdentifier;
import myorg.bouncycastle.asn1.cms.OriginatorIdentifierOrKey;
import myorg.bouncycastle.asn1.cms.OriginatorPublicKey;
import myorg.bouncycastle.asn1.cms.OtherKeyAttribute;
import myorg.bouncycastle.asn1.cms.ecc.MQVuserKeyingMaterial;
import myorg.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PBKDF2Params;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSPBEKey;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.KEKRecipientInfoGenerator;
import myorg.bouncycastle.cms.KeyAgreeRecipientInfoGenerator;
import myorg.bouncycastle.cms.KeyTransRecipientInfoGenerator;
import myorg.bouncycastle.cms.PasswordRecipientInfoGenerator;
import myorg.bouncycastle.jce.spec.MQVPrivateKeySpec;
import myorg.bouncycastle.jce.spec.MQVPublicKeySpec;

public class CMSEnvelopedGenerator {

   public static final String AES128_CBC = NISTObjectIdentifiers.id_aes128_CBC.getId();
   public static final String AES128_WRAP = NISTObjectIdentifiers.id_aes128_wrap.getId();
   public static final String AES192_CBC = NISTObjectIdentifiers.id_aes192_CBC.getId();
   public static final String AES192_WRAP = NISTObjectIdentifiers.id_aes192_wrap.getId();
   public static final String AES256_CBC = NISTObjectIdentifiers.id_aes256_CBC.getId();
   public static final String AES256_WRAP = NISTObjectIdentifiers.id_aes256_wrap.getId();
   public static final String CAMELLIA128_CBC = NTTObjectIdentifiers.id_camellia128_cbc.getId();
   public static final String CAMELLIA128_WRAP = NTTObjectIdentifiers.id_camellia128_wrap.getId();
   public static final String CAMELLIA192_CBC = NTTObjectIdentifiers.id_camellia192_cbc.getId();
   public static final String CAMELLIA192_WRAP = NTTObjectIdentifiers.id_camellia192_wrap.getId();
   public static final String CAMELLIA256_CBC = NTTObjectIdentifiers.id_camellia256_cbc.getId();
   public static final String CAMELLIA256_WRAP = NTTObjectIdentifiers.id_camellia256_wrap.getId();
   public static final String CAST5_CBC = "1.2.840.113533.7.66.10";
   public static final String DES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC.getId();
   public static final String DES_EDE3_WRAP = PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId();
   public static final String ECDH_SHA1KDF = X9ObjectIdentifiers.dhSinglePass_stdDH_sha1kdf_scheme.getId();
   public static final String ECMQV_SHA1KDF = X9ObjectIdentifiers.mqvSinglePass_sha1kdf_scheme.getId();
   public static final String IDEA_CBC = "1.3.6.1.4.1.188.7.1.1.2";
   public static final String RC2_CBC = PKCSObjectIdentifiers.RC2_CBC.getId();
   public static final String SEED_CBC = KISAObjectIdentifiers.id_seedCBC.getId();
   public static final String SEED_WRAP = KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap.getId();
   final SecureRandom rand;
   final List recipientInfoGenerators;


   public CMSEnvelopedGenerator() {
      SecureRandom var1 = new SecureRandom();
      this(var1);
   }

   public CMSEnvelopedGenerator(SecureRandom var1) {
      ArrayList var2 = new ArrayList();
      this.recipientInfoGenerators = var2;
      this.rand = var1;
   }

   private static OriginatorPublicKey createOriginatorPublicKey(PublicKey var0) throws IOException {
      SubjectPublicKeyInfo var1 = SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray(var0.getEncoded()));
      DERObjectIdentifier var2 = var1.getAlgorithmId().getObjectId();
      DERNull var3 = DERNull.INSTANCE;
      AlgorithmIdentifier var4 = new AlgorithmIdentifier(var2, var3);
      byte[] var5 = var1.getPublicKeyData().getBytes();
      return new OriginatorPublicKey(var4, var5);
   }

   public void addKEKRecipient(SecretKey var1, byte[] var2) {
      KEKRecipientInfoGenerator var3 = new KEKRecipientInfoGenerator();
      KEKIdentifier var4 = new KEKIdentifier(var2, (DERGeneralizedTime)null, (OtherKeyAttribute)null);
      var3.setKEKIdentifier(var4);
      var3.setWrapKey(var1);
      this.recipientInfoGenerators.add(var3);
   }

   public void addKeyAgreementRecipient(String var1, PrivateKey var2, PublicKey var3, X509Certificate var4, String var5, String var6) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
      Provider var7 = CMSUtils.getProvider(var6);
      this.addKeyAgreementRecipient(var1, var2, var3, var4, var5, var7);
   }

   public void addKeyAgreementRecipient(String var1, PrivateKey var2, PublicKey var3, X509Certificate var4, String var5, Provider var6) throws NoSuchAlgorithmException, InvalidKeyException {
      OriginatorIdentifierOrKey var7;
      try {
         var7 = new OriginatorIdentifierOrKey;
         OriginatorPublicKey var8 = createOriginatorPublicKey(var3);
         var7.<init>(var8);
      } catch (IOException var88) {
         StringBuilder var71 = (new StringBuilder()).append("cannot extract originator public key: ");
         String var73 = var71.append(var88).toString();
         throw new InvalidKeyException(var73);
      }

      DEROctetString var11 = null;
      Object var12 = var4.getPublicKey();
      String var13 = ECMQV_SHA1KDF;
      if(var1.equals(var13)) {
         DEROctetString var25;
         MQVPrivateKeySpec var31;
         MQVPublicKeySpec var30;
         label40: {
            InvalidAlgorithmParameterException var74;
            label39: {
               IOException var78;
               label46: {
                  KeyPair var24;
                  try {
                     ECParameterSpec var16 = ((ECPublicKey)var3).getParams();
                     KeyPairGenerator var19 = KeyPairGenerator.getInstance(var1, var6);
                     SecureRandom var20 = this.rand;
                     var19.initialize(var16, var20);
                     var24 = var19.generateKeyPair();
                     var25 = new DEROctetString;
                     OriginatorPublicKey var26 = createOriginatorPublicKey(var24.getPublic());
                     MQVuserKeyingMaterial var27 = new MQVuserKeyingMaterial(var26, (ASN1OctetString)null);
                     var25.<init>((DEREncodable)var27);
                  } catch (InvalidAlgorithmParameterException var93) {
                     var74 = var93;
                     break label39;
                  } catch (IOException var94) {
                     var78 = var94;
                     break label46;
                  }

                  try {
                     var30 = new MQVPublicKeySpec((PublicKey)var12, (PublicKey)var12);
                  } catch (InvalidAlgorithmParameterException var91) {
                     var74 = var91;
                     break label39;
                  } catch (IOException var92) {
                     var78 = var92;
                     break label46;
                  }

                  try {
                     var31 = new MQVPrivateKeySpec;
                     PrivateKey var32 = var24.getPrivate();
                     PublicKey var33 = var24.getPublic();
                     var31.<init>((PrivateKey)var2, var32, var33);
                     break label40;
                  } catch (InvalidAlgorithmParameterException var89) {
                     var74 = var89;
                     break label39;
                  } catch (IOException var90) {
                     var78 = var90;
                  }
               }

               StringBuilder var79 = (new StringBuilder()).append("cannot extract MQV ephemeral public key: ");
               String var81 = var79.append(var78).toString();
               throw new InvalidKeyException(var81);
            }

            StringBuilder var75 = (new StringBuilder()).append("cannot determine MQV ephemeral key pair parameters from public key: ");
            String var77 = var75.append(var74).toString();
            throw new InvalidKeyException(var77);
         }

         var12 = var30;
         var11 = var25;
         var2 = var31;
      }

      KeyAgreement var40 = KeyAgreement.getInstance(var1, var6);
      SecureRandom var41 = this.rand;
      var40.init((Key)var2, var41);
      byte var47 = 1;
      var40.doPhase((Key)var12, (boolean)var47);
      SecretKey var51 = var40.generateSecret(var5);
      KeyAgreeRecipientInfoGenerator var52 = new KeyAgreeRecipientInfoGenerator();
      DERObjectIdentifier var53 = new DERObjectIdentifier(var1);
      var52.setAlgorithmOID(var53);
      var52.setOriginator(var7);
      var52.setRecipientCert(var4);
      var52.setUKM(var11);
      var52.setWrapKey(var51);
      DERObjectIdentifier var62 = new DERObjectIdentifier(var5);
      var52.setWrapAlgorithmOID(var62);
      List var67 = this.recipientInfoGenerators;
      var67.add(var52);
   }

   public void addKeyTransRecipient(PublicKey var1, byte[] var2) throws IllegalArgumentException {
      KeyTransRecipientInfoGenerator var3 = new KeyTransRecipientInfoGenerator();
      var3.setRecipientPublicKey(var1);
      DEROctetString var4 = new DEROctetString(var2);
      var3.setSubjectKeyIdentifier(var4);
      this.recipientInfoGenerators.add(var3);
   }

   public void addKeyTransRecipient(X509Certificate var1) throws IllegalArgumentException {
      KeyTransRecipientInfoGenerator var2 = new KeyTransRecipientInfoGenerator();
      var2.setRecipientCert(var1);
      this.recipientInfoGenerators.add(var2);
   }

   public void addPasswordRecipient(CMSPBEKey var1, String var2) {
      byte[] var3 = var1.getSalt();
      int var4 = var1.getIterationCount();
      PBKDF2Params var5 = new PBKDF2Params(var3, var4);
      PasswordRecipientInfoGenerator var6 = new PasswordRecipientInfoGenerator();
      DERObjectIdentifier var7 = PKCSObjectIdentifiers.id_PBKDF2;
      AlgorithmIdentifier var8 = new AlgorithmIdentifier(var7, var5);
      var6.setDerivationAlg(var8);
      byte[] var9 = var1.getEncoded(var2);
      SecretKeySpec var10 = new SecretKeySpec(var9, var2);
      var6.setWrapKey(var10);
      this.recipientInfoGenerators.add(var6);
   }

   protected AlgorithmParameters generateParameters(String param1, SecretKey param2, Provider param3) throws CMSException {
      // $FF: Couldn't be decompiled
   }

   protected AlgorithmIdentifier getAlgorithmIdentifier(String var1, AlgorithmParameters var2) throws IOException {
      Object var3;
      if(var2 != null) {
         var3 = ASN1Object.fromByteArray(var2.getEncoded("ASN.1"));
      } else {
         var3 = DERNull.INSTANCE;
      }

      DERObjectIdentifier var4 = new DERObjectIdentifier(var1);
      return new AlgorithmIdentifier(var4, (DEREncodable)var3);
   }
}

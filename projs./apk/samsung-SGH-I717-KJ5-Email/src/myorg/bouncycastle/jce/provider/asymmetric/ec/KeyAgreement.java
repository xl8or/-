package myorg.bouncycastle.jce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x9.X9IntegerConverter;
import myorg.bouncycastle.crypto.BasicAgreement;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DerivationFunction;
import myorg.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import myorg.bouncycastle.crypto.agreement.ECDHCBasicAgreement;
import myorg.bouncycastle.crypto.agreement.ECMQVBasicAgreement;
import myorg.bouncycastle.crypto.agreement.kdf.DHKDFParameters;
import myorg.bouncycastle.crypto.agreement.kdf.ECDHKEKGenerator;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.crypto.params.MQVPrivateParameters;
import myorg.bouncycastle.crypto.params.MQVPublicParameters;
import myorg.bouncycastle.jce.interfaces.ECPrivateKey;
import myorg.bouncycastle.jce.interfaces.ECPublicKey;
import myorg.bouncycastle.jce.interfaces.MQVPrivateKey;
import myorg.bouncycastle.jce.interfaces.MQVPublicKey;
import myorg.bouncycastle.jce.provider.asymmetric.ec.ECUtil;
import myorg.bouncycastle.math.ec.ECFieldElement;

public class KeyAgreement extends KeyAgreementSpi {

   private static final Hashtable algorithms = new Hashtable();
   private static final X9IntegerConverter converter = new X9IntegerConverter();
   private BasicAgreement agreement;
   private String kaAlgorithm;
   private DerivationFunction kdf;
   private ECDomainParameters parameters;
   private BigInteger result;


   static {
      Integer var0 = new Integer(128);
      Integer var1 = new Integer(192);
      Integer var2 = new Integer(256);
      Hashtable var3 = algorithms;
      String var4 = NISTObjectIdentifiers.id_aes128_CBC.getId();
      var3.put(var4, var0);
      Hashtable var6 = algorithms;
      String var7 = NISTObjectIdentifiers.id_aes192_CBC.getId();
      var6.put(var7, var1);
      Hashtable var9 = algorithms;
      String var10 = NISTObjectIdentifiers.id_aes256_CBC.getId();
      var9.put(var10, var2);
      Hashtable var12 = algorithms;
      String var13 = NISTObjectIdentifiers.id_aes128_wrap.getId();
      var12.put(var13, var0);
      Hashtable var15 = algorithms;
      String var16 = NISTObjectIdentifiers.id_aes192_wrap.getId();
      var15.put(var16, var1);
      Hashtable var18 = algorithms;
      String var19 = NISTObjectIdentifiers.id_aes256_wrap.getId();
      var18.put(var19, var2);
      Hashtable var21 = algorithms;
      String var22 = PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId();
      var21.put(var22, var1);
   }

   protected KeyAgreement(String var1, BasicAgreement var2, DerivationFunction var3) {
      this.kaAlgorithm = var1;
      this.agreement = var2;
      this.kdf = var3;
   }

   private byte[] bigIntToBytes(BigInteger var1) {
      X9IntegerConverter var2 = converter;
      X9IntegerConverter var3 = converter;
      ECFieldElement var4 = this.parameters.getG().getX();
      int var5 = var3.getByteLength(var4);
      return var2.integerToBytes(var1, var5);
   }

   private static String getSimpleName(Class var0) {
      String var1 = var0.getName();
      int var2 = var1.lastIndexOf(46) + 1;
      return var1.substring(var2);
   }

   private void initFromKey(Key var1) throws InvalidKeyException {
      if(this.agreement instanceof ECMQVBasicAgreement) {
         if(!(var1 instanceof MQVPrivateKey)) {
            StringBuilder var2 = new StringBuilder();
            String var3 = this.kaAlgorithm;
            StringBuilder var4 = var2.append(var3).append(" key agreement requires ");
            String var5 = getSimpleName(MQVPrivateKey.class);
            String var6 = var4.append(var5).append(" for initialisation").toString();
            throw new InvalidKeyException(var6);
         } else {
            MQVPrivateKey var7 = (MQVPrivateKey)var1;
            ECPrivateKeyParameters var8 = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter(var7.getStaticPrivateKey());
            ECPrivateKeyParameters var9 = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter(var7.getEphemeralPrivateKey());
            ECPublicKeyParameters var10 = null;
            if(var7.getEphemeralPublicKey() != null) {
               var10 = (ECPublicKeyParameters)ECUtil.generatePublicKeyParameter(var7.getEphemeralPublicKey());
            }

            MQVPrivateParameters var11 = new MQVPrivateParameters(var8, var9, var10);
            ECDomainParameters var12 = var8.getParameters();
            this.parameters = var12;
            this.agreement.init(var11);
         }
      } else if(!(var1 instanceof ECPrivateKey)) {
         StringBuilder var13 = new StringBuilder();
         String var14 = this.kaAlgorithm;
         StringBuilder var15 = var13.append(var14).append(" key agreement requires ");
         String var16 = getSimpleName(ECPrivateKey.class);
         String var17 = var15.append(var16).append(" for initialisation").toString();
         throw new InvalidKeyException(var17);
      } else {
         ECPrivateKeyParameters var18 = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)var1);
         ECDomainParameters var19 = var18.getParameters();
         this.parameters = var19;
         this.agreement.init(var18);
      }
   }

   protected Key engineDoPhase(Key var1, boolean var2) throws InvalidKeyException, IllegalStateException {
      if(this.parameters == null) {
         StringBuilder var3 = new StringBuilder();
         String var4 = this.kaAlgorithm;
         String var5 = var3.append(var4).append(" not initialised.").toString();
         throw new IllegalStateException(var5);
      } else if(!var2) {
         StringBuilder var6 = new StringBuilder();
         String var7 = this.kaAlgorithm;
         String var8 = var6.append(var7).append(" can only be between two parties.").toString();
         throw new IllegalStateException(var8);
      } else {
         Object var17;
         if(this.agreement instanceof ECMQVBasicAgreement) {
            if(!(var1 instanceof MQVPublicKey)) {
               StringBuilder var9 = new StringBuilder();
               String var10 = this.kaAlgorithm;
               StringBuilder var11 = var9.append(var10).append(" key agreement requires ");
               String var12 = getSimpleName(MQVPublicKey.class);
               String var13 = var11.append(var12).append(" for doPhase").toString();
               throw new InvalidKeyException(var13);
            }

            MQVPublicKey var14 = (MQVPublicKey)var1;
            ECPublicKeyParameters var15 = (ECPublicKeyParameters)ECUtil.generatePublicKeyParameter(var14.getStaticKey());
            ECPublicKeyParameters var16 = (ECPublicKeyParameters)ECUtil.generatePublicKeyParameter(var14.getEphemeralKey());
            var17 = new MQVPublicParameters(var15, var16);
         } else {
            if(!(var1 instanceof ECPublicKey)) {
               StringBuilder var19 = new StringBuilder();
               String var20 = this.kaAlgorithm;
               StringBuilder var21 = var19.append(var20).append(" key agreement requires ");
               String var22 = getSimpleName(ECPublicKey.class);
               String var23 = var21.append(var22).append(" for doPhase").toString();
               throw new InvalidKeyException(var23);
            }

            var17 = ECUtil.generatePublicKeyParameter((PublicKey)var1);
         }

         BigInteger var18 = this.agreement.calculateAgreement((CipherParameters)var17);
         this.result = var18;
         return null;
      }
   }

   protected int engineGenerateSecret(byte[] var1, int var2) throws IllegalStateException, ShortBufferException {
      byte[] var3 = this.engineGenerateSecret();
      int var4 = var1.length - var2;
      int var5 = var3.length;
      if(var4 < var5) {
         StringBuilder var6 = new StringBuilder();
         String var7 = this.kaAlgorithm;
         StringBuilder var8 = var6.append(var7).append(" key agreement: need ");
         int var9 = var3.length;
         String var10 = var8.append(var9).append(" bytes").toString();
         throw new ShortBufferException(var10);
      } else {
         int var11 = var3.length;
         System.arraycopy(var3, 0, var1, var2, var11);
         return var3.length;
      }
   }

   protected SecretKey engineGenerateSecret(String var1) throws NoSuchAlgorithmException {
      BigInteger var2 = this.result;
      byte[] var3 = this.bigIntToBytes(var2);
      if(this.kdf != null) {
         if(!algorithms.containsKey(var1)) {
            String var4 = "unknown algorithm encountered: " + var1;
            throw new NoSuchAlgorithmException(var4);
         }

         int var5 = ((Integer)algorithms.get(var1)).intValue();
         DERObjectIdentifier var6 = new DERObjectIdentifier(var1);
         DHKDFParameters var7 = new DHKDFParameters(var6, var5, var3);
         byte[] var8 = new byte[var5 / 8];
         this.kdf.init(var7);
         DerivationFunction var9 = this.kdf;
         int var10 = var8.length;
         var9.generateBytes(var8, 0, var10);
         var3 = var8;
      }

      return new SecretKeySpec(var3, var1);
   }

   protected byte[] engineGenerateSecret() throws IllegalStateException {
      if(this.kdf != null) {
         throw new UnsupportedOperationException("KDF can only be used when algorithm is known");
      } else {
         BigInteger var1 = this.result;
         return this.bigIntToBytes(var1);
      }
   }

   protected void engineInit(Key var1, SecureRandom var2) throws InvalidKeyException {
      this.initFromKey(var1);
   }

   protected void engineInit(Key var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException {
      this.initFromKey(var1);
   }

   public static class MQV extends KeyAgreement {

      public MQV() {
         ECMQVBasicAgreement var1 = new ECMQVBasicAgreement();
         super("ECMQV", var1, (DerivationFunction)null);
      }
   }

   public static class DH extends KeyAgreement {

      public DH() {
         ECDHBasicAgreement var1 = new ECDHBasicAgreement();
         super("ECDH", var1, (DerivationFunction)null);
      }
   }

   public static class DHC extends KeyAgreement {

      public DHC() {
         ECDHCBasicAgreement var1 = new ECDHCBasicAgreement();
         super("ECDHC", var1, (DerivationFunction)null);
      }
   }

   public static class DHwithSHA1KDF extends KeyAgreement {

      public DHwithSHA1KDF() {
         ECDHBasicAgreement var1 = new ECDHBasicAgreement();
         SHA1Digest var2 = new SHA1Digest();
         ECDHKEKGenerator var3 = new ECDHKEKGenerator(var2);
         super("ECDHwithSHA1KDF", var1, var3);
      }
   }

   public static class MQVwithSHA1KDF extends KeyAgreement {

      public MQVwithSHA1KDF() {
         ECMQVBasicAgreement var1 = new ECMQVBasicAgreement();
         SHA1Digest var2 = new SHA1Digest();
         ECDHKEKGenerator var3 = new ECDHKEKGenerator(var2);
         super("ECMQVwithSHA1KDF", var1, var3);
      }
   }
}

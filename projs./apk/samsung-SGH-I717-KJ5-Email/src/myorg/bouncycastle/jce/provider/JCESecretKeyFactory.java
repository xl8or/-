package myorg.bouncycastle.jce.provider;

import java.lang.reflect.Constructor;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.DESParameters;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.jce.provider.JCEPBEKey;
import myorg.bouncycastle.jce.provider.PBE;

public class JCESecretKeyFactory extends SecretKeyFactorySpi implements PBE {

   protected String algName;
   protected DERObjectIdentifier algOid;


   protected JCESecretKeyFactory(String var1, DERObjectIdentifier var2) {
      this.algName = var1;
      this.algOid = var2;
   }

   protected SecretKey engineGenerateSecret(KeySpec var1) throws InvalidKeySpecException {
      if(var1 instanceof SecretKeySpec) {
         return (SecretKey)var1;
      } else {
         throw new InvalidKeySpecException("Invalid KeySpec");
      }
   }

   protected KeySpec engineGetKeySpec(SecretKey var1, Class var2) throws InvalidKeySpecException {
      if(var2 == null) {
         throw new InvalidKeySpecException("keySpec parameter is null");
      } else if(var1 == null) {
         throw new InvalidKeySpecException("key parameter is null");
      } else {
         Object var5;
         if(SecretKeySpec.class.isAssignableFrom(var2)) {
            byte[] var3 = var1.getEncoded();
            String var4 = this.algName;
            var5 = new SecretKeySpec(var3, var4);
         } else {
            byte var6 = 1;

            KeySpec var13;
            try {
               Class[] var7 = new Class[var6];
               var7[0] = byte[].class;
               Constructor var8 = var2.getConstructor(var7);
               Object[] var9 = new Object[1];
               byte[] var10 = var1.getEncoded();
               var9[0] = var10;
               var13 = (KeySpec)var8.newInstance(var9);
            } catch (Exception var12) {
               String var11 = var12.toString();
               throw new InvalidKeySpecException(var11);
            }

            var5 = var13;
         }

         return (KeySpec)var5;
      }
   }

   protected SecretKey engineTranslateKey(SecretKey var1) throws InvalidKeyException {
      if(var1 == null) {
         throw new InvalidKeyException("key parameter is null");
      } else {
         String var2 = var1.getAlgorithm();
         String var3 = this.algName;
         if(!var2.equalsIgnoreCase(var3)) {
            StringBuilder var4 = (new StringBuilder()).append("Key not of type ");
            String var5 = this.algName;
            String var6 = var4.append(var5).append(".").toString();
            throw new InvalidKeyException(var6);
         } else {
            byte[] var7 = var1.getEncoded();
            String var8 = this.algName;
            return new SecretKeySpec(var7, var8);
         }
      }
   }

   public static class PBEWithSHA1AndRC2 extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHA1AndRC2() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC;
         byte var3 = 1;
         byte var4 = 64;
         super("PBEwithSHA1andRC2", var1, (boolean)1, 0, var3, 64, var4);
      }
   }

   public static class PBEWithMD5AndRC2 extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithMD5AndRC2() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC;
         byte var3 = 0;
         byte var4 = 64;
         super("PBEwithMD5andRC2", var1, (boolean)1, 0, var3, 64, var4);
      }
   }

   public static class PBEWithSHAAndDES2Key extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAndDES2Key() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC;
         byte var3 = 1;
         super("PBEwithSHAandDES2Key-CBC", var1, (boolean)1, 2, var3, 128, 64);
      }
   }

   public static class PBEWithMD5And192BitAESCBCOpenSSL extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithMD5And192BitAESCBCOpenSSL() {
         super("PBEWithMD5And192BitAES-CBC-OpenSSL", (DERObjectIdentifier)null, (boolean)1, 3, 0, 192, 128);
      }
   }

   public static class PBEWithMD5And128BitAESCBCOpenSSL extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithMD5And128BitAESCBCOpenSSL() {
         short var2 = 128;
         super("PBEWithMD5And128BitAES-CBC-OpenSSL", (DERObjectIdentifier)null, (boolean)1, 3, 0, 128, var2);
      }
   }

   public static class PBEWithSHA1AndDES extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHA1AndDES() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC;
         byte var3 = 1;
         byte var4 = 64;
         super("PBEwithSHA1andDES", var1, (boolean)1, 0, var3, 64, var4);
      }
   }

   public static class PBEWithSHA256And256BitAESBC extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHA256And256BitAESBC() {
         super("PBEWithSHA256And256BitAES-CBC-BC", (DERObjectIdentifier)null, (boolean)1, 2, 4, 256, 128);
      }
   }

   public static class PBEWithSHAAnd40BitRC4 extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAnd40BitRC4() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4;
         byte var3 = 1;
         super("PBEWithSHAAnd128BitRC4", var1, (boolean)1, 2, var3, 40, 0);
      }
   }

   public static class PBEWithSHAAnd40BitRC2 extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAnd40BitRC2() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbewithSHAAnd40BitRC2_CBC;
         byte var3 = 1;
         super("PBEwithSHAand40BitRC2-CBC", var1, (boolean)1, 2, var3, 40, 64);
      }
   }

   public static class PBEWithMD5AndDES extends JCESecretKeyFactory.DESPBEKeyFactory {

      public PBEWithMD5AndDES() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC;
         byte var3 = 0;
         byte var4 = 64;
         super("PBEwithMD5andDES", var1, (boolean)1, 0, var3, 64, var4);
      }
   }

   public static class PBEWithRIPEMD160 extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithRIPEMD160() {
         byte var2 = 2;
         byte var3 = 0;
         super("PBEwithHmacRIPEMD160", (DERObjectIdentifier)null, (boolean)0, 2, var2, 160, var3);
      }
   }

   public static class DES extends JCESecretKeyFactory {

      public DES() {
         super("DES", (DERObjectIdentifier)null);
      }

      protected SecretKey engineGenerateSecret(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof DESKeySpec) {
            byte[] var2 = ((DESKeySpec)var1).getKey();
            var3 = new SecretKeySpec(var2, "DES");
         } else {
            var3 = super.engineGenerateSecret(var1);
         }

         return (SecretKey)var3;
      }
   }

   public static class PBEWithMD2AndRC2 extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithMD2AndRC2() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithMD2AndRC2_CBC;
         byte var3 = 64;
         super("PBEwithMD2andRC2", var1, (boolean)1, 0, 5, 64, var3);
      }
   }

   public static class PBEWithSHA256And192BitAESBC extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHA256And192BitAESBC() {
         super("PBEWithSHA256And192BitAES-CBC-BC", (DERObjectIdentifier)null, (boolean)1, 2, 4, 192, 128);
      }
   }

   public static class PBEWithSHA256And128BitAESBC extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHA256And128BitAESBC() {
         short var2 = 128;
         super("PBEWithSHA256And128BitAES-CBC-BC", (DERObjectIdentifier)null, (boolean)1, 2, 4, 128, var2);
      }
   }

   public static class PBEWithSHAAnd128BitRC2 extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAnd128BitRC2() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC;
         byte var3 = 1;
         super("PBEwithSHAand128BitRC2-CBC", var1, (boolean)1, 2, var3, 128, 64);
      }
   }

   public static class PBEWithMD2AndDES extends JCESecretKeyFactory.DESPBEKeyFactory {

      public PBEWithMD2AndDES() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC;
         byte var3 = 64;
         super("PBEwithMD2andDES", var1, (boolean)1, 0, 5, 64, var3);
      }
   }

   public static class PBEWithSHAAnd128BitRC4 extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAnd128BitRC4() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4;
         byte var3 = 1;
         super("PBEWithSHAAnd128BitRC4", var1, (boolean)1, 2, var3, 128, 0);
      }
   }

   public static class PBEWithSHAAndTwofish extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAndTwofish() {
         byte var2 = 1;
         super("PBEwithSHAandTwofish-CBC", (DERObjectIdentifier)null, (boolean)1, 2, var2, 256, 128);
      }
   }

   public static class PBEWithSHAAndDES3Key extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAndDES3Key() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC;
         byte var3 = 1;
         super("PBEwithSHAandDES3Key-CBC", var1, (boolean)1, 2, var3, 192, 64);
      }
   }

   public static class PBEWithTiger extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithTiger() {
         byte var2 = 0;
         super("PBEwithHmacTiger", (DERObjectIdentifier)null, (boolean)0, 2, 3, 192, var2);
      }
   }

   public static class DESede extends JCESecretKeyFactory {

      public DESede() {
         super("DESede", (DERObjectIdentifier)null);
      }

      protected SecretKey engineGenerateSecret(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof DESedeKeySpec) {
            byte[] var2 = ((DESedeKeySpec)var1).getKey();
            var3 = new SecretKeySpec(var2, "DESede");
         } else {
            var3 = super.engineGenerateSecret(var1);
         }

         return (SecretKey)var3;
      }

      protected KeySpec engineGetKeySpec(SecretKey var1, Class var2) throws InvalidKeySpecException {
         if(var2 == null) {
            throw new InvalidKeySpecException("keySpec parameter is null");
         } else if(var1 == null) {
            throw new InvalidKeySpecException("key parameter is null");
         } else {
            Object var5;
            if(SecretKeySpec.class.isAssignableFrom(var2)) {
               byte[] var3 = var1.getEncoded();
               String var4 = this.algName;
               var5 = new SecretKeySpec(var3, var4);
            } else {
               if(!DESedeKeySpec.class.isAssignableFrom(var2)) {
                  throw new InvalidKeySpecException("Invalid KeySpec");
               }

               byte[] var6 = var1.getEncoded();

               try {
                  if(var6.length == 16) {
                     byte[] var7 = new byte[24];
                     System.arraycopy(var6, 0, var7, 0, 16);
                     System.arraycopy(var6, 0, var7, 16, 8);
                     var5 = new DESedeKeySpec(var7);
                  } else {
                     var5 = new DESedeKeySpec(var6);
                  }
               } catch (Exception var9) {
                  String var8 = var9.toString();
                  throw new InvalidKeySpecException(var8);
               }
            }

            return (KeySpec)var5;
         }
      }
   }

   public static class PBEKeyFactory extends JCESecretKeyFactory {

      private int digest;
      private boolean forCipher;
      private int ivSize;
      private int keySize;
      private int scheme;


      public PBEKeyFactory(String var1, DERObjectIdentifier var2, boolean var3, int var4, int var5, int var6, int var7) {
         super(var1, var2);
         this.forCipher = var3;
         this.scheme = var4;
         this.digest = var5;
         this.keySize = var6;
         this.ivSize = var7;
      }

      protected SecretKey engineGenerateSecret(KeySpec var1) throws InvalidKeySpecException {
         if(var1 instanceof PBEKeySpec) {
            PBEKeySpec var2 = (PBEKeySpec)var1;
            JCEPBEKey var9;
            if(var2.getSalt() == null) {
               String var3 = this.algName;
               DERObjectIdentifier var4 = this.algOid;
               int var5 = this.scheme;
               int var6 = this.digest;
               int var7 = this.keySize;
               int var8 = this.ivSize;
               var9 = new JCEPBEKey(var3, var4, var5, var6, var7, var8, var2, (CipherParameters)null);
            } else {
               CipherParameters var14;
               if(this.forCipher) {
                  int var10 = this.scheme;
                  int var11 = this.digest;
                  int var12 = this.keySize;
                  int var13 = this.ivSize;
                  var14 = PBE.Util.makePBEParameters(var2, var10, var11, var12, var13);
               } else {
                  int var21 = this.scheme;
                  int var22 = this.digest;
                  int var23 = this.keySize;
                  var14 = PBE.Util.makePBEMacParameters(var2, var21, var22, var23);
               }

               String var15 = this.algName;
               DERObjectIdentifier var16 = this.algOid;
               int var17 = this.scheme;
               int var18 = this.digest;
               int var19 = this.keySize;
               int var20 = this.ivSize;
               var9 = new JCEPBEKey(var15, var16, var17, var18, var19, var20, var2, var14);
            }

            return var9;
         } else {
            throw new InvalidKeySpecException("Invalid KeySpec");
         }
      }
   }

   public static class PBEWithSHAAnd128BitAESBC extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAnd128BitAESBC() {
         byte var2 = 1;
         short var3 = 128;
         super("PBEWithSHA1And128BitAES-CBC-BC", (DERObjectIdentifier)null, (boolean)1, 2, var2, 128, var3);
      }
   }

   public static class DESPBEKeyFactory extends JCESecretKeyFactory {

      private int digest;
      private boolean forCipher;
      private int ivSize;
      private int keySize;
      private int scheme;


      public DESPBEKeyFactory(String var1, DERObjectIdentifier var2, boolean var3, int var4, int var5, int var6, int var7) {
         super(var1, var2);
         this.forCipher = var3;
         this.scheme = var4;
         this.digest = var5;
         this.keySize = var6;
         this.ivSize = var7;
      }

      protected SecretKey engineGenerateSecret(KeySpec var1) throws InvalidKeySpecException {
         if(var1 instanceof PBEKeySpec) {
            PBEKeySpec var2 = (PBEKeySpec)var1;
            JCEPBEKey var9;
            if(var2.getSalt() == null) {
               String var3 = this.algName;
               DERObjectIdentifier var4 = this.algOid;
               int var5 = this.scheme;
               int var6 = this.digest;
               int var7 = this.keySize;
               int var8 = this.ivSize;
               var9 = new JCEPBEKey(var3, var4, var5, var6, var7, var8, var2, (CipherParameters)null);
            } else {
               CipherParameters var14;
               if(this.forCipher) {
                  int var10 = this.scheme;
                  int var11 = this.digest;
                  int var12 = this.keySize;
                  int var13 = this.ivSize;
                  var14 = PBE.Util.makePBEParameters(var2, var10, var11, var12, var13);
               } else {
                  int var21 = this.scheme;
                  int var22 = this.digest;
                  int var23 = this.keySize;
                  var14 = PBE.Util.makePBEMacParameters(var2, var21, var22, var23);
               }

               if(var14 instanceof ParametersWithIV) {
                  DESParameters.setOddParity(((KeyParameter)((ParametersWithIV)var14).getParameters()).getKey());
               } else {
                  DESParameters.setOddParity(((KeyParameter)var14).getKey());
               }

               String var15 = this.algName;
               DERObjectIdentifier var16 = this.algOid;
               int var17 = this.scheme;
               int var18 = this.digest;
               int var19 = this.keySize;
               int var20 = this.ivSize;
               var9 = new JCEPBEKey(var15, var16, var17, var18, var19, var20, var2, var14);
            }

            return var9;
         } else {
            throw new InvalidKeySpecException("Invalid KeySpec");
         }
      }
   }

   public static class PBEWithSHAAnd256BitAESBC extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAnd256BitAESBC() {
         byte var2 = 1;
         super("PBEWithSHA1And256BitAES-CBC-BC", (DERObjectIdentifier)null, (boolean)1, 2, var2, 256, 128);
      }
   }

   public static class PBEWithSHAAnd192BitAESBC extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAnd192BitAESBC() {
         byte var2 = 1;
         super("PBEWithSHA1And192BitAES-CBC-BC", (DERObjectIdentifier)null, (boolean)1, 2, var2, 192, 128);
      }
   }

   public static class PBEWithMD5And256BitAESCBCOpenSSL extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithMD5And256BitAESCBCOpenSSL() {
         super("PBEWithMD5And256BitAES-CBC-OpenSSL", (DERObjectIdentifier)null, (boolean)1, 3, 0, 256, 128);
      }
   }

   public static class PBEWithSHA extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHA() {
         byte var2 = 0;
         super("PBEwithHmacSHA", (DERObjectIdentifier)null, (boolean)0, 2, 1, 160, var2);
      }
   }
}

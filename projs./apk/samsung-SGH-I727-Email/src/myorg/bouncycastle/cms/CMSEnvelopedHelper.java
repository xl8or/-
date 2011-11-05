package myorg.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.cms.KEKRecipientInfo;
import myorg.bouncycastle.asn1.cms.KeyAgreeRecipientInfo;
import myorg.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import myorg.bouncycastle.asn1.cms.PasswordRecipientInfo;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedGenerator;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.KEKRecipientInformation;
import myorg.bouncycastle.cms.KeyAgreeRecipientInformation;
import myorg.bouncycastle.cms.KeyTransRecipientInformation;
import myorg.bouncycastle.cms.PasswordRecipientInformation;

class CMSEnvelopedHelper {

   private static final Map BASE_CIPHER_NAMES = new HashMap();
   private static final Map CIPHER_ALG_NAMES = new HashMap();
   static final CMSEnvelopedHelper INSTANCE = new CMSEnvelopedHelper();
   private static final Map KEYSIZES = new HashMap();
   private static final Map MAC_ALG_NAMES = new HashMap();


   static {
      Map var0 = KEYSIZES;
      String var1 = CMSEnvelopedGenerator.DES_EDE3_CBC;
      Integer var2 = new Integer(192);
      var0.put(var1, var2);
      Map var4 = KEYSIZES;
      String var5 = CMSEnvelopedGenerator.AES128_CBC;
      Integer var6 = new Integer(128);
      var4.put(var5, var6);
      Map var8 = KEYSIZES;
      String var9 = CMSEnvelopedGenerator.AES192_CBC;
      Integer var10 = new Integer(192);
      var8.put(var9, var10);
      Map var12 = KEYSIZES;
      String var13 = CMSEnvelopedGenerator.AES256_CBC;
      Integer var14 = new Integer(256);
      var12.put(var13, var14);
      Map var16 = BASE_CIPHER_NAMES;
      String var17 = CMSEnvelopedGenerator.DES_EDE3_CBC;
      var16.put(var17, "DESEDE");
      Map var19 = BASE_CIPHER_NAMES;
      String var20 = CMSEnvelopedGenerator.AES128_CBC;
      var19.put(var20, "AES");
      Map var22 = BASE_CIPHER_NAMES;
      String var23 = CMSEnvelopedGenerator.AES192_CBC;
      var22.put(var23, "AES");
      Map var25 = BASE_CIPHER_NAMES;
      String var26 = CMSEnvelopedGenerator.AES256_CBC;
      var25.put(var26, "AES");
      Map var28 = CIPHER_ALG_NAMES;
      String var29 = CMSEnvelopedGenerator.DES_EDE3_CBC;
      var28.put(var29, "DESEDE/CBC/PKCS5Padding");
      Map var31 = CIPHER_ALG_NAMES;
      String var32 = CMSEnvelopedGenerator.AES128_CBC;
      var31.put(var32, "AES/CBC/PKCS5Padding");
      Map var34 = CIPHER_ALG_NAMES;
      String var35 = CMSEnvelopedGenerator.AES192_CBC;
      var34.put(var35, "AES/CBC/PKCS5Padding");
      Map var37 = CIPHER_ALG_NAMES;
      String var38 = CMSEnvelopedGenerator.AES256_CBC;
      var37.put(var38, "AES/CBC/PKCS5Padding");
      Map var40 = MAC_ALG_NAMES;
      String var41 = CMSEnvelopedGenerator.DES_EDE3_CBC;
      var40.put(var41, "DESEDEMac");
      Map var43 = MAC_ALG_NAMES;
      String var44 = CMSEnvelopedGenerator.AES128_CBC;
      var43.put(var44, "AESMac");
      Map var46 = MAC_ALG_NAMES;
      String var47 = CMSEnvelopedGenerator.AES192_CBC;
      var46.put(var47, "AESMac");
      Map var49 = MAC_ALG_NAMES;
      String var50 = CMSEnvelopedGenerator.AES256_CBC;
      var49.put(var50, "AESMac");
   }

   CMSEnvelopedHelper() {}

   private AlgorithmParameters createAlgorithmParams(String var1, Provider var2) throws NoSuchAlgorithmException {
      AlgorithmParameters var3;
      if(var2 != null) {
         var3 = AlgorithmParameters.getInstance(var1, var2);
      } else {
         var3 = AlgorithmParameters.getInstance(var1);
      }

      return var3;
   }

   private AlgorithmParameterGenerator createAlgorithmParamsGenerator(String var1, Provider var2) throws NoSuchAlgorithmException {
      AlgorithmParameterGenerator var3;
      if(var2 != null) {
         var3 = AlgorithmParameterGenerator.getInstance(var1, var2);
      } else {
         var3 = AlgorithmParameterGenerator.getInstance(var1);
      }

      return var3;
   }

   private KeyGenerator createKeyGenerator(String var1, Provider var2) throws NoSuchAlgorithmException {
      KeyGenerator var3;
      if(var2 != null) {
         var3 = KeyGenerator.getInstance(var1, var2);
      } else {
         var3 = KeyGenerator.getInstance(var1);
      }

      return var3;
   }

   private Mac createMac(String var1, Provider var2) throws NoSuchAlgorithmException, NoSuchPaddingException {
      Mac var3;
      if(var2 != null) {
         var3 = Mac.getInstance(var1, var2);
      } else {
         var3 = Mac.getInstance(var1);
      }

      return var3;
   }

   private String getAsymmetricEncryptionAlgName(String var1) {
      String var2;
      if(PKCSObjectIdentifiers.rsaEncryption.getId().equals(var1)) {
         var2 = "RSA/ECB/PKCS1Padding";
      } else {
         var2 = var1;
      }

      return var2;
   }

   private static void readRecipientInfo(List var0, RecipientInfo var1, InputStream var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4, AlgorithmIdentifier var5) {
      DEREncodable var6 = var1.getInfo();
      if(var6 instanceof KeyTransRecipientInfo) {
         KeyTransRecipientInfo var7 = (KeyTransRecipientInfo)var6;
         KeyTransRecipientInformation var12 = new KeyTransRecipientInformation(var7, var3, var4, var5, var2);
         var0.add(var12);
      } else if(var6 instanceof KEKRecipientInfo) {
         KEKRecipientInfo var14 = (KEKRecipientInfo)var6;
         KEKRecipientInformation var19 = new KEKRecipientInformation(var14, var3, var4, var5, var2);
         var0.add(var19);
      } else if(var6 instanceof KeyAgreeRecipientInfo) {
         KeyAgreeRecipientInfo var21 = (KeyAgreeRecipientInfo)var6;
         KeyAgreeRecipientInformation var26 = new KeyAgreeRecipientInformation(var21, var3, var4, var5, var2);
         var0.add(var26);
      } else if(var6 instanceof PasswordRecipientInfo) {
         PasswordRecipientInfo var28 = (PasswordRecipientInfo)var6;
         PasswordRecipientInformation var33 = new PasswordRecipientInformation(var28, var3, var4, var5, var2);
         var0.add(var33);
      }
   }

   static List readRecipientInfos(Iterator var0, InputStream var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4) {
      ArrayList var5 = new ArrayList();

      while(var0.hasNext()) {
         RecipientInfo var6 = (RecipientInfo)var0.next();
         readRecipientInfo(var5, var6, var1, var2, var3, var4);
      }

      return var5;
   }

   static List readRecipientInfos(ASN1Set var0, byte[] var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4) {
      ArrayList var5 = new ArrayList();
      int var6 = 0;

      while(true) {
         int var7 = var0.size();
         if(var6 == var7) {
            return var5;
         }

         RecipientInfo var8 = RecipientInfo.getInstance(var0.getObjectAt(var6));
         ByteArrayInputStream var9 = new ByteArrayInputStream(var1);
         readRecipientInfo(var5, var8, var9, var2, var3, var4);
         ++var6;
      }
   }

   AlgorithmParameterGenerator createAlgorithmParameterGenerator(String var1, Provider var2) throws NoSuchAlgorithmException {
      AlgorithmParameterGenerator var3;
      AlgorithmParameterGenerator var4;
      try {
         var3 = this.createAlgorithmParamsGenerator(var1, var2);
      } catch (NoSuchAlgorithmException var9) {
         label21: {
            try {
               String var6 = (String)BASE_CIPHER_NAMES.get(var1);
               if(var6 != null) {
                  var3 = this.createAlgorithmParamsGenerator(var6, var2);
                  break label21;
               }
            } catch (NoSuchAlgorithmException var8) {
               ;
            }

            throw var9;
         }

         var4 = var3;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   AlgorithmParameters createAlgorithmParameters(String var1, Provider var2) throws NoSuchAlgorithmException {
      AlgorithmParameters var3;
      AlgorithmParameters var4;
      try {
         var3 = this.createAlgorithmParams(var1, var2);
      } catch (NoSuchAlgorithmException var9) {
         label21: {
            try {
               String var6 = (String)BASE_CIPHER_NAMES.get(var1);
               if(var6 != null) {
                  var3 = this.createAlgorithmParams(var6, var2);
                  break label21;
               }
            } catch (NoSuchAlgorithmException var8) {
               ;
            }

            throw var9;
         }

         var4 = var3;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   Cipher createAsymmetricCipher(String var1, Provider var2) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
      Cipher var4;
      Cipher var5;
      try {
         String var3 = this.getAsymmetricEncryptionAlgName(var1);
         var4 = this.getCipherInstance(var3, var2);
      } catch (NoSuchAlgorithmException var7) {
         var5 = this.getCipherInstance(var1, var2);
         return var5;
      }

      var5 = var4;
      return var5;
   }

   KeyGenerator createSymmetricKeyGenerator(String var1, Provider var2) throws NoSuchAlgorithmException {
      KeyGenerator var3;
      KeyGenerator var4;
      try {
         var3 = this.createKeyGenerator(var1, var2);
      } catch (NoSuchAlgorithmException var9) {
         label41: {
            try {
               String var6 = (String)BASE_CIPHER_NAMES.get(var1);
               if(var6 == null) {
                  break label41;
               }

               var3 = this.createKeyGenerator(var6, var2);
            } catch (NoSuchAlgorithmException var8) {
               break label41;
            }

            var4 = var3;
            return var4;
         }

         if(var2 == null) {
            throw var9;
         }

         var4 = this.createSymmetricKeyGenerator(var1, (Provider)null);
         return var4;
      }

      var4 = var3;
      return var4;
   }

   Cipher getCipherInstance(String var1, Provider var2) throws NoSuchAlgorithmException, NoSuchPaddingException {
      Cipher var3;
      if(var2 != null) {
         var3 = Cipher.getInstance(var1, var2);
      } else {
         var3 = Cipher.getInstance(var1);
      }

      return var3;
   }

   AlgorithmParameters getEncryptionAlgorithmParameters(String var1, byte[] var2, Provider var3) throws CMSException {
      AlgorithmParameters var4;
      if(var2 == null) {
         var4 = null;
      } else {
         AlgorithmParameters var5;
         try {
            var5 = this.createAlgorithmParameters(var1, var3);
            var5.init(var2, "ASN.1");
         } catch (NoSuchAlgorithmException var8) {
            throw new CMSException("can\'t find parameters for algorithm", var8);
         } catch (IOException var9) {
            throw new CMSException("can\'t find parse parameters", var9);
         }

         var4 = var5;
      }

      return var4;
   }

   int getKeySize(String var1) {
      Integer var2 = (Integer)KEYSIZES.get(var1);
      if(var2 == null) {
         String var3 = "no keysize for " + var1;
         throw new IllegalArgumentException(var3);
      } else {
         return var2.intValue();
      }
   }

   Mac getMac(String var1, Provider var2) throws NoSuchAlgorithmException, NoSuchPaddingException {
      Mac var3;
      Mac var4;
      try {
         var3 = this.createMac(var1, var2);
      } catch (NoSuchAlgorithmException var9) {
         String var6 = (String)MAC_ALG_NAMES.get(var1);

         try {
            var3 = this.createMac(var6, var2);
         } catch (NoSuchAlgorithmException var8) {
            if(var2 != null) {
               var4 = this.getMac(var1, (Provider)null);
               return var4;
            }

            throw var9;
         }

         var4 = var3;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   String getRFC3211WrapperName(String var1) {
      String var2 = (String)BASE_CIPHER_NAMES.get(var1);
      if(var2 == null) {
         String var3 = "no name for " + var1;
         throw new IllegalArgumentException(var3);
      } else {
         return var2 + "RFC3211Wrap";
      }
   }

   Cipher getSymmetricCipher(String var1, Provider var2) throws NoSuchAlgorithmException, NoSuchPaddingException {
      Cipher var3;
      Cipher var4;
      try {
         var3 = this.getCipherInstance(var1, var2);
      } catch (NoSuchAlgorithmException var9) {
         String var6 = (String)CIPHER_ALG_NAMES.get(var1);

         try {
            var3 = this.getCipherInstance(var6, var2);
         } catch (NoSuchAlgorithmException var8) {
            if(var2 != null) {
               var4 = this.getSymmetricCipher(var1, (Provider)null);
               return var4;
            }

            throw var9;
         }

         var4 = var3;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   String getSymmetricCipherName(String var1) {
      String var2 = (String)BASE_CIPHER_NAMES.get(var1);
      String var3;
      if(var2 != null) {
         var3 = var2;
      } else {
         var3 = var1;
      }

      return var3;
   }
}

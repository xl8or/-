package myorg.bouncycastle.cms;

import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.cms.KEKIdentifier;
import myorg.bouncycastle.asn1.cms.KEKRecipientInfo;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.RecipientInfoGenerator;

class KEKRecipientInfoGenerator implements RecipientInfoGenerator {

   private AlgorithmIdentifier keyEncAlg;
   private KEKIdentifier secKeyId;
   private SecretKey wrapKey;


   KEKRecipientInfoGenerator() {}

   private static AlgorithmIdentifier determineKeyEncAlg(SecretKey var0) {
      String var1 = var0.getAlgorithm();
      AlgorithmIdentifier var4;
      if(var1.startsWith("DES")) {
         DERObjectIdentifier var2 = new DERObjectIdentifier("1.2.840.113549.1.9.16.3.6");
         DERNull var3 = new DERNull();
         var4 = new AlgorithmIdentifier(var2, var3);
      } else if(var1.startsWith("RC2")) {
         DERObjectIdentifier var5 = new DERObjectIdentifier("1.2.840.113549.1.9.16.3.7");
         DERInteger var6 = new DERInteger(58);
         var4 = new AlgorithmIdentifier(var5, var6);
      } else {
         int var7;
         DERObjectIdentifier var8;
         if(var1.startsWith("AES")) {
            var7 = var0.getEncoded().length * 8;
            if(var7 == 128) {
               var8 = NISTObjectIdentifiers.id_aes128_wrap;
            } else if(var7 == 192) {
               var8 = NISTObjectIdentifiers.id_aes192_wrap;
            } else {
               if(var7 != 256) {
                  throw new IllegalArgumentException("illegal keysize in AES");
               }

               var8 = NISTObjectIdentifiers.id_aes256_wrap;
            }

            var4 = new AlgorithmIdentifier(var8);
         } else if(var1.startsWith("SEED")) {
            DERObjectIdentifier var9 = KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap;
            var4 = new AlgorithmIdentifier(var9);
         } else {
            if(!var1.startsWith("Camellia")) {
               throw new IllegalArgumentException("unknown algorithm");
            }

            var7 = var0.getEncoded().length * 8;
            if(var7 == 128) {
               var8 = NTTObjectIdentifiers.id_camellia128_wrap;
            } else if(var7 == 192) {
               var8 = NTTObjectIdentifiers.id_camellia192_wrap;
            } else {
               if(var7 != 256) {
                  throw new IllegalArgumentException("illegal keysize in Camellia");
               }

               var8 = NTTObjectIdentifiers.id_camellia256_wrap;
            }

            var4 = new AlgorithmIdentifier(var8);
         }
      }

      return var4;
   }

   public RecipientInfo generate(SecretKey var1, SecureRandom var2, Provider var3) throws GeneralSecurityException {
      CMSEnvelopedHelper var4 = CMSEnvelopedHelper.INSTANCE;
      String var5 = this.keyEncAlg.getObjectId().getId();
      Cipher var6 = var4.createAsymmetricCipher(var5, var3);
      SecretKey var7 = this.wrapKey;
      var6.init(3, var7, var2);
      byte[] var8 = var6.wrap(var1);
      DEROctetString var9 = new DEROctetString(var8);
      KEKIdentifier var10 = this.secKeyId;
      AlgorithmIdentifier var11 = this.keyEncAlg;
      KEKRecipientInfo var12 = new KEKRecipientInfo(var10, var11, var9);
      return new RecipientInfo(var12);
   }

   void setKEKIdentifier(KEKIdentifier var1) {
      this.secKeyId = var1;
   }

   void setWrapKey(SecretKey var1) {
      this.wrapKey = var1;
      AlgorithmIdentifier var2 = determineKeyEncAlg(var1);
      this.keyEncAlg = var2;
   }
}

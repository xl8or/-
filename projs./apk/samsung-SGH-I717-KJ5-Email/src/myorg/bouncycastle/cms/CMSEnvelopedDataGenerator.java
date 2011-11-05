package myorg.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.cms.EncryptedContentInfo;
import myorg.bouncycastle.asn1.cms.EnvelopedData;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedData;
import myorg.bouncycastle.cms.CMSEnvelopedGenerator;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInfoGenerator;

public class CMSEnvelopedDataGenerator extends CMSEnvelopedGenerator {

   public CMSEnvelopedDataGenerator() {}

   public CMSEnvelopedDataGenerator(SecureRandom var1) {
      super(var1);
   }

   private CMSEnvelopedData generate(CMSProcessable var1, String var2, KeyGenerator var3, Provider var4) throws NoSuchAlgorithmException, CMSException {
      Provider var5 = var3.getProvider();
      ASN1EncodableVector var6 = new ASN1EncodableVector();

      SecretKey var11;
      AlgorithmIdentifier var26;
      BERConstructedOctetString var31;
      try {
         CMSEnvelopedHelper var7 = CMSEnvelopedHelper.INSTANCE;
         Cipher var10 = var7.getSymmetricCipher(var2, var5);
         var11 = var3.generateKey();
         AlgorithmParameters var16 = this.generateParameters(var2, var11, var5);
         SecureRandom var17 = this.rand;
         byte var19 = 1;
         var10.init(var19, var11, var16, var17);
         if(var16 == null) {
            var16 = var10.getParameters();
         }

         var26 = this.getAlgorithmIdentifier(var2, var16);
         ByteArrayOutputStream var27 = new ByteArrayOutputStream();
         CipherOutputStream var28 = new CipherOutputStream(var27, var10);
         var1.write(var28);
         var28.close();
         var31 = new BERConstructedOctetString;
         byte[] var32 = var27.toByteArray();
         var31.<init>(var32);
      } catch (InvalidKeyException var98) {
         CMSException var49 = new CMSException;
         String var51 = "key invalid in message.";
         var49.<init>(var51, var98);
         throw var49;
      } catch (NoSuchPaddingException var99) {
         CMSException var54 = new CMSException;
         String var56 = "required padding not supported.";
         var54.<init>(var56, var99);
         throw var54;
      } catch (InvalidAlgorithmParameterException var100) {
         CMSException var59 = new CMSException;
         String var61 = "algorithm parameters invalid.";
         var59.<init>(var61, var100);
         throw var59;
      } catch (IOException var101) {
         CMSException var64 = new CMSException;
         String var66 = "exception decoding algorithm parameters.";
         var64.<init>(var66, var101);
         throw var64;
      }

      Iterator var35 = this.recipientInfoGenerators.iterator();

      while(var35.hasNext()) {
         RecipientInfoGenerator var36 = (RecipientInfoGenerator)var35.next();

         try {
            SecureRandom var37 = this.rand;
            RecipientInfo var42 = var36.generate(var11, var37, var4);
            var6.add(var42);
         } catch (InvalidKeyException var96) {
            CMSException var44 = new CMSException;
            String var46 = "key inappropriate for algorithm.";
            var44.<init>(var46, var96);
            throw var44;
         } catch (GeneralSecurityException var97) {
            CMSException var69 = new CMSException;
            String var71 = "error making encrypted content.";
            var69.<init>(var71, var97);
            throw var69;
         }
      }

      EncryptedContentInfo var73 = new EncryptedContentInfo;
      DERObjectIdentifier var74 = CMSObjectIdentifiers.data;
      var73.<init>(var74, var26, var31);
      ContentInfo var79 = new ContentInfo;
      DERObjectIdentifier var80 = CMSObjectIdentifiers.envelopedData;
      EnvelopedData var81 = new EnvelopedData;
      DERSet var82 = new DERSet(var6);
      Object var86 = null;
      Object var89 = null;
      var81.<init>((OriginatorInfo)var86, var82, var73, (ASN1Set)var89);
      var79.<init>(var80, var81);
      CMSEnvelopedData var93 = new CMSEnvelopedData(var79);
      return var93;
   }

   public CMSEnvelopedData generate(CMSProcessable var1, String var2, int var3, String var4) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var5 = CMSUtils.getProvider(var4);
      return this.generate(var1, var2, var3, var5);
   }

   public CMSEnvelopedData generate(CMSProcessable var1, String var2, int var3, Provider var4) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      KeyGenerator var5 = CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(var2, var4);
      SecureRandom var6 = this.rand;
      var5.init(var3, var6);
      return this.generate(var1, var2, var5, var4);
   }

   public CMSEnvelopedData generate(CMSProcessable var1, String var2, String var3) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var4 = CMSUtils.getProvider(var3);
      return this.generate(var1, var2, var4);
   }

   public CMSEnvelopedData generate(CMSProcessable var1, String var2, Provider var3) throws NoSuchAlgorithmException, CMSException {
      KeyGenerator var4 = CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(var2, var3);
      SecureRandom var5 = this.rand;
      var4.init(var5);
      return this.generate(var1, var2, var4, var3);
   }
}

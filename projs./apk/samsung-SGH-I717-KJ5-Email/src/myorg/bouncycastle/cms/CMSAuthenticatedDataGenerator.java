package myorg.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Iterator;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.AuthenticatedData;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSAuthenticatedData;
import myorg.bouncycastle.cms.CMSAuthenticatedGenerator;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInfoGenerator;

public class CMSAuthenticatedDataGenerator extends CMSAuthenticatedGenerator {

   public CMSAuthenticatedDataGenerator() {}

   public CMSAuthenticatedDataGenerator(SecureRandom var1) {
      super(var1);
   }

   private CMSAuthenticatedData generate(CMSProcessable var1, String var2, KeyGenerator var3, Provider var4) throws NoSuchAlgorithmException, CMSException {
      Provider var5 = var3.getProvider();
      ASN1EncodableVector var6 = new ASN1EncodableVector();

      SecretKey var11;
      AlgorithmIdentifier var24;
      BERConstructedOctetString var32;
      DEROctetString var37;
      try {
         CMSEnvelopedHelper var7 = CMSEnvelopedHelper.INSTANCE;
         Mac var10 = var7.getMac(var2, var5);
         var11 = var3.generateKey();
         AlgorithmParameterSpec var16 = this.generateParameterSpec(var2, var11, var5);
         var10.init(var11, var16);
         var24 = this.getAlgorithmIdentifier(var2, var16, var5);
         ByteArrayOutputStream var25 = new ByteArrayOutputStream();
         CMSAuthenticatedGenerator.MacOutputStream var26 = new CMSAuthenticatedGenerator.MacOutputStream(var25, var10);
         var1.write(var26);
         var26.close();
         var25.close();
         var32 = new BERConstructedOctetString;
         byte[] var33 = var25.toByteArray();
         var32.<init>(var33);
         byte[] var36 = var26.getMac();
         var37 = new DEROctetString(var36);
      } catch (InvalidKeyException var68) {
         throw new CMSException("key invalid in message.", var68);
      } catch (NoSuchPaddingException var69) {
         throw new CMSException("required padding not supported.", var69);
      } catch (InvalidAlgorithmParameterException var70) {
         throw new CMSException("algorithm parameters invalid.", var70);
      } catch (IOException var71) {
         throw new CMSException("exception decoding algorithm parameters.", var71);
      } catch (InvalidParameterSpecException var72) {
         throw new CMSException("exception setting up parameters.", var72);
      }

      Iterator var38 = this.recipientInfoGenerators.iterator();

      while(var38.hasNext()) {
         RecipientInfoGenerator var39 = (RecipientInfoGenerator)var38.next();

         try {
            SecureRandom var40 = this.rand;
            RecipientInfo var45 = var39.generate(var11, var40, var4);
            var6.add(var45);
         } catch (InvalidKeyException var66) {
            throw new CMSException("key inappropriate for algorithm.", var66);
         } catch (GeneralSecurityException var67) {
            throw new CMSException("error making encrypted content.", var67);
         }
      }

      ContentInfo var55 = new ContentInfo;
      DERObjectIdentifier var56 = CMSObjectIdentifiers.data;
      var55.<init>(var56, var32);
      DERObjectIdentifier var60 = CMSObjectIdentifiers.authenticatedData;
      DERSet var61 = new DERSet(var6);
      AuthenticatedData var64 = new AuthenticatedData((OriginatorInfo)null, var61, var24, (AlgorithmIdentifier)null, var55, (ASN1Set)null, var37, (ASN1Set)null);
      ContentInfo var65 = new ContentInfo(var60, var64);
      return new CMSAuthenticatedData(var65);
   }

   public CMSAuthenticatedData generate(CMSProcessable var1, String var2, String var3) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var4 = CMSUtils.getProvider(var3);
      return this.generate(var1, var2, var4);
   }

   public CMSAuthenticatedData generate(CMSProcessable var1, String var2, Provider var3) throws NoSuchAlgorithmException, CMSException {
      KeyGenerator var4 = CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(var2, var3);
      SecureRandom var5 = this.rand;
      var4.init(var5);
      return this.generate(var1, var2, var4, var3);
   }
}

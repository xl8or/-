package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
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
import myorg.bouncycastle.asn1.BERSequenceGenerator;
import myorg.bouncycastle.asn1.BERSet;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.AuthenticatedData;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSAuthenticatedGenerator;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInfoGenerator;

public class CMSAuthenticatedDataStreamGenerator extends CMSAuthenticatedGenerator {

   private boolean _berEncodeRecipientSet;
   private int _bufferSize;


   public CMSAuthenticatedDataStreamGenerator() {}

   public CMSAuthenticatedDataStreamGenerator(SecureRandom var1) {
      super(var1);
   }

   private OutputStream open(OutputStream var1, String var2, KeyGenerator var3, Provider var4) throws NoSuchAlgorithmException, CMSException {
      Provider var5 = var3.getProvider();
      SecretKey var6 = var3.generateKey();
      AlgorithmParameterSpec var7 = this.generateParameterSpec(var2, var6, var5);
      Iterator var8 = this.recipientInfoGenerators.iterator();
      ASN1EncodableVector var9 = new ASN1EncodableVector();

      while(var8.hasNext()) {
         RecipientInfoGenerator var10 = (RecipientInfoGenerator)var8.next();

         try {
            SecureRandom var11 = this.rand;
            RecipientInfo var12 = var10.generate(var6, var11, var4);
            var9.add(var12);
         } catch (InvalidKeyException var18) {
            throw new CMSException("key inappropriate for algorithm.", var18);
         } catch (GeneralSecurityException var19) {
            throw new CMSException("error making encrypted content.", var19);
         }
      }

      return this.open(var1, var2, var6, var7, var9, var5);
   }

   public OutputStream open(OutputStream var1, String var2, int var3, String var4) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException, IOException {
      Provider var5 = CMSUtils.getProvider(var4);
      return this.open(var1, var2, var3, var5);
   }

   public OutputStream open(OutputStream var1, String var2, int var3, Provider var4) throws NoSuchAlgorithmException, CMSException, IOException {
      KeyGenerator var5 = CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(var2, var4);
      SecureRandom var6 = this.rand;
      var5.init(var3, var6);
      return this.open(var1, var2, var5, var4);
   }

   public OutputStream open(OutputStream var1, String var2, String var3) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException, IOException {
      Provider var4 = CMSUtils.getProvider(var3);
      return this.open(var1, var2, var4);
   }

   public OutputStream open(OutputStream var1, String var2, Provider var3) throws NoSuchAlgorithmException, CMSException, IOException {
      KeyGenerator var4 = CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(var2, var3);
      SecureRandom var5 = this.rand;
      var4.init(var5);
      return this.open(var1, var2, var4, var3);
   }

   protected OutputStream open(OutputStream var1, String var2, SecretKey var3, AlgorithmParameterSpec var4, ASN1EncodableVector var5, String var6) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var7 = CMSUtils.getProvider(var6);
      return this.open(var1, var2, var3, var4, var5, var7);
   }

   protected OutputStream open(OutputStream var1, String var2, SecretKey var3, AlgorithmParameterSpec var4, ASN1EncodableVector var5, Provider var6) throws NoSuchAlgorithmException, CMSException {
      try {
         BERSequenceGenerator var7 = new BERSequenceGenerator(var1);
         DERObjectIdentifier var8 = CMSObjectIdentifiers.authenticatedData;
         var7.addObject(var8);
         OutputStream var9 = var7.getRawOutputStream();
         BERSequenceGenerator var10 = new BERSequenceGenerator(var9, 0, (boolean)1);
         int var11 = AuthenticatedData.calculateVersion((OriginatorInfo)null);
         DERInteger var12 = new DERInteger(var11);
         var10.addObject(var12);
         if(this._berEncodeRecipientSet) {
            OutputStream var13 = var10.getRawOutputStream();
            BERSet var14 = new BERSet(var5);
            byte[] var17 = var14.getEncoded();
            var13.write(var17);
         } else {
            OutputStream var40 = var10.getRawOutputStream();
            DERSet var41 = new DERSet(var5);
            byte[] var44 = var41.getEncoded();
            var40.write(var44);
         }

         CMSEnvelopedHelper var18 = CMSEnvelopedHelper.INSTANCE;
         Mac var21 = var18.getMac(var2, var6);
         var21.init(var3, var4);
         AlgorithmIdentifier var29 = this.getAlgorithmIdentifier(var2, var4, var6);
         OutputStream var30 = var10.getRawOutputStream();
         byte[] var31 = var29.getEncoded();
         var30.write(var31);
         OutputStream var32 = var10.getRawOutputStream();
         BERSequenceGenerator var33 = new BERSequenceGenerator(var32);
         DERObjectIdentifier var34 = CMSObjectIdentifiers.data;
         var33.addObject(var34);
         OutputStream var35 = var33.getRawOutputStream();
         int var36 = this._bufferSize;
         OutputStream var37 = CMSUtils.createBEROctetOutputStream(var35, 0, (boolean)0, var36);
         CMSAuthenticatedGenerator.MacOutputStream var38 = new CMSAuthenticatedGenerator.MacOutputStream(var37, var21);
         return new CMSAuthenticatedDataStreamGenerator.CmsAuthenticatedDataOutputStream(var38, var7, var10, var33);
      } catch (InvalidKeyException var50) {
         throw new CMSException("key invalid in message.", var50);
      } catch (NoSuchPaddingException var51) {
         throw new CMSException("required padding not supported.", var51);
      } catch (InvalidAlgorithmParameterException var52) {
         throw new CMSException("algorithm parameter invalid.", var52);
      } catch (InvalidParameterSpecException var53) {
         throw new CMSException("algorithm parameter spec invalid.", var53);
      } catch (IOException var54) {
         throw new CMSException("exception decoding algorithm parameters.", var54);
      }
   }

   public void setBEREncodeRecipients(boolean var1) {
      this._berEncodeRecipientSet = var1;
   }

   public void setBufferSize(int var1) {
      this._bufferSize = var1;
   }

   private class CmsAuthenticatedDataOutputStream extends OutputStream {

      private BERSequenceGenerator cGen;
      private BERSequenceGenerator eiGen;
      private BERSequenceGenerator envGen;
      private CMSAuthenticatedGenerator.MacOutputStream macStream;


      public CmsAuthenticatedDataOutputStream(CMSAuthenticatedGenerator.MacOutputStream var2, BERSequenceGenerator var3, BERSequenceGenerator var4, BERSequenceGenerator var5) {
         this.macStream = var2;
         this.cGen = var3;
         this.envGen = var4;
         this.eiGen = var5;
      }

      public void close() throws IOException {
         this.macStream.close();
         this.eiGen.close();
         BERSequenceGenerator var1 = this.envGen;
         byte[] var2 = this.macStream.getMac();
         DEROctetString var3 = new DEROctetString(var2);
         var1.addObject(var3);
         this.envGen.close();
         this.cGen.close();
      }

      public void write(int var1) throws IOException {
         this.macStream.write(var1);
      }

      public void write(byte[] var1) throws IOException {
         this.macStream.write(var1);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this.macStream.write(var1, var2, var3);
      }
   }
}

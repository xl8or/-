package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
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
import myorg.bouncycastle.asn1.BERSequenceGenerator;
import myorg.bouncycastle.asn1.BERSet;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedGenerator;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInfoGenerator;

public class CMSEnvelopedDataStreamGenerator extends CMSEnvelopedGenerator {

   private boolean _berEncodeRecipientSet;
   private int _bufferSize;
   private Object _originatorInfo = null;
   private Object _unprotectedAttributes = null;


   public CMSEnvelopedDataStreamGenerator() {}

   public CMSEnvelopedDataStreamGenerator(SecureRandom var1) {
      super(var1);
   }

   private DERInteger getVersion() {
      DERInteger var1;
      if(this._originatorInfo == null && this._unprotectedAttributes == null) {
         var1 = new DERInteger(0);
      } else {
         var1 = new DERInteger(2);
      }

      return var1;
   }

   private OutputStream open(OutputStream var1, String var2, KeyGenerator var3, Provider var4) throws NoSuchAlgorithmException, CMSException {
      Provider var5 = var3.getProvider();
      SecretKey var6 = var3.generateKey();
      AlgorithmParameters var7 = this.generateParameters(var2, var6, var5);
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

   protected OutputStream open(OutputStream var1, String var2, SecretKey var3, AlgorithmParameters var4, ASN1EncodableVector var5, String var6) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var7 = CMSUtils.getProvider(var6);
      return this.open(var1, var2, var3, var4, var5, var7);
   }

   protected OutputStream open(OutputStream var1, String var2, SecretKey var3, AlgorithmParameters var4, ASN1EncodableVector var5, Provider var6) throws NoSuchAlgorithmException, CMSException {
      try {
         BERSequenceGenerator var7 = new BERSequenceGenerator(var1);
         DERObjectIdentifier var10 = CMSObjectIdentifiers.envelopedData;
         var7.addObject(var10);
         OutputStream var11 = var7.getRawOutputStream();
         BERSequenceGenerator var12 = new BERSequenceGenerator(var11, 0, (boolean)1);
         DERInteger var13 = this.getVersion();
         var12.addObject(var13);
         if(this._berEncodeRecipientSet) {
            OutputStream var14 = var12.getRawOutputStream();
            BERSet var15 = new BERSet(var5);
            byte[] var18 = var15.getEncoded();
            var14.write(var18);
         } else {
            OutputStream var43 = var12.getRawOutputStream();
            DERSet var44 = new DERSet(var5);
            byte[] var47 = var44.getEncoded();
            var43.write(var47);
         }

         CMSEnvelopedHelper var19 = CMSEnvelopedHelper.INSTANCE;
         Cipher var22 = var19.getSymmetricCipher(var2, var6);
         SecureRandom var23 = this.rand;
         byte var25 = 1;
         var22.init(var25, var3, var4, var23);
         OutputStream var29 = var12.getRawOutputStream();
         BERSequenceGenerator var30 = new BERSequenceGenerator(var29);
         DERObjectIdentifier var31 = CMSObjectIdentifiers.data;
         var30.addObject(var31);
         if(var4 == null) {
            var4 = var22.getParameters();
         }

         AlgorithmIdentifier var35 = this.getAlgorithmIdentifier(var2, var4);
         OutputStream var36 = var30.getRawOutputStream();
         byte[] var37 = var35.getEncoded();
         var36.write(var37);
         OutputStream var38 = var30.getRawOutputStream();
         int var39 = this._bufferSize;
         OutputStream var40 = CMSUtils.createBEROctetOutputStream(var38, 0, (boolean)0, var39);
         CipherOutputStream var41 = new CipherOutputStream(var40, var22);
         return new CMSEnvelopedDataStreamGenerator.CmsEnvelopedDataOutputStream(var41, var7, var12, var30);
      } catch (InvalidKeyException var52) {
         throw new CMSException("key invalid in message.", var52);
      } catch (NoSuchPaddingException var53) {
         throw new CMSException("required padding not supported.", var53);
      } catch (InvalidAlgorithmParameterException var54) {
         throw new CMSException("algorithm parameters invalid.", var54);
      } catch (IOException var55) {
         throw new CMSException("exception decoding algorithm parameters.", var55);
      }
   }

   public void setBEREncodeRecipients(boolean var1) {
      this._berEncodeRecipientSet = var1;
   }

   public void setBufferSize(int var1) {
      this._bufferSize = var1;
   }

   private class CmsEnvelopedDataOutputStream extends OutputStream {

      private BERSequenceGenerator _cGen;
      private BERSequenceGenerator _eiGen;
      private BERSequenceGenerator _envGen;
      private CipherOutputStream _out;


      public CmsEnvelopedDataOutputStream(CipherOutputStream var2, BERSequenceGenerator var3, BERSequenceGenerator var4, BERSequenceGenerator var5) {
         this._out = var2;
         this._cGen = var3;
         this._envGen = var4;
         this._eiGen = var5;
      }

      public void close() throws IOException {
         this._out.close();
         this._eiGen.close();
         this._envGen.close();
         this._cGen.close();
      }

      public void write(int var1) throws IOException {
         this._out.write(var1);
      }

      public void write(byte[] var1) throws IOException {
         this._out.write(var1);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this._out.write(var1, var2, var3);
      }
   }
}

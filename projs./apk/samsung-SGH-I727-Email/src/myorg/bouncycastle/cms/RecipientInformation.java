package myorg.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import myorg.bouncycastle.asn1.ASN1Null;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSTypedStream;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientId;

public abstract class RecipientInformation {

   protected AlgorithmIdentifier authEncAlg;
   protected InputStream data;
   protected AlgorithmIdentifier encAlg;
   protected AlgorithmIdentifier keyEncAlg;
   protected AlgorithmIdentifier macAlg;
   private RecipientInformation.MacInputStream macStream;
   private byte[] resultMac;
   protected RecipientId rid;


   protected RecipientInformation(AlgorithmIdentifier var1, AlgorithmIdentifier var2, InputStream var3) {
      this(var1, (AlgorithmIdentifier)null, var2, var3);
   }

   protected RecipientInformation(AlgorithmIdentifier var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, InputStream var4) {
      this(var1, var2, (AlgorithmIdentifier)null, var3, var4);
   }

   RecipientInformation(AlgorithmIdentifier var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4, InputStream var5) {
      RecipientId var6 = new RecipientId();
      this.rid = var6;
      this.encAlg = var1;
      this.macAlg = var2;
      this.authEncAlg = var3;
      this.keyEncAlg = var4;
      this.data = var5;
   }

   private static RecipientInformation.MacInputStream createMacInputStream(AlgorithmIdentifier var0, Key var1, InputStream var2, Provider var3) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, InvalidParameterSpecException {
      CMSEnvelopedHelper var4 = CMSEnvelopedHelper.INSTANCE;
      String var5 = var0.getObjectId().getId();
      Mac var6 = var4.getMac(var5, var3);
      ASN1Object var7 = (ASN1Object)var0.getParameters();
      if(var7 != null && !(var7 instanceof ASN1Null)) {
         CMSEnvelopedHelper var8 = CMSEnvelopedHelper.INSTANCE;
         String var9 = var0.getObjectId().getId();
         AlgorithmParameters var10 = var8.createAlgorithmParameters(var9, var3);
         byte[] var11 = var7.getEncoded();
         var10.init(var11, "ASN.1");
         AlgorithmParameterSpec var12 = var10.getParameterSpec(IvParameterSpec.class);
         var6.init(var1, var12);
      } else {
         var6.init(var1);
      }

      return new RecipientInformation.MacInputStream(var6, var2);
   }

   private byte[] encodeObj(DEREncodable var1) throws IOException {
      byte[] var2;
      if(var1 != null) {
         var2 = var1.getDERObject().getEncoded();
      } else {
         var2 = null;
      }

      return var2;
   }

   AlgorithmIdentifier getActiveAlgID() {
      AlgorithmIdentifier var1;
      if(this.encAlg != null) {
         var1 = this.encAlg;
      } else if(this.macAlg != null) {
         var1 = this.macAlg;
      } else {
         var1 = this.authEncAlg;
      }

      return var1;
   }

   public byte[] getContent(Key var1, String var2) throws CMSException, NoSuchProviderException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getContent(var1, var3);
   }

   public byte[] getContent(Key var1, Provider var2) throws CMSException {
      try {
         if(this.data instanceof ByteArrayInputStream) {
            this.data.reset();
         }

         byte[] var3 = CMSUtils.streamToByteArray(this.getContentStream(var1, var2).getContentStream());
         return var3;
      } catch (IOException var6) {
         String var5 = "unable to parse internal stream: " + var6;
         throw new RuntimeException(var5);
      }
   }

   protected CMSTypedStream getContentFromSessionKey(Key param1, Provider param2) throws CMSException {
      // $FF: Couldn't be decompiled
   }

   public CMSTypedStream getContentStream(Key var1, String var2) throws CMSException, NoSuchProviderException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getContentStream(var1, var3);
   }

   public abstract CMSTypedStream getContentStream(Key var1, Provider var2) throws CMSException;

   public String getKeyEncryptionAlgOID() {
      return this.keyEncAlg.getObjectId().getId();
   }

   public byte[] getKeyEncryptionAlgParams() {
      try {
         DEREncodable var1 = this.keyEncAlg.getParameters();
         byte[] var2 = this.encodeObj(var1);
         return var2;
      } catch (Exception var5) {
         String var4 = "exception getting encryption parameters " + var5;
         throw new RuntimeException(var4);
      }
   }

   public AlgorithmParameters getKeyEncryptionAlgorithmParameters(String var1) throws CMSException, NoSuchProviderException {
      Provider var2 = CMSUtils.getProvider(var1);
      return this.getKeyEncryptionAlgorithmParameters(var2);
   }

   public AlgorithmParameters getKeyEncryptionAlgorithmParameters(Provider param1) throws CMSException {
      // $FF: Couldn't be decompiled
   }

   public byte[] getMac() {
      if(this.macStream != null && this.resultMac == null) {
         byte[] var1 = this.macStream.getMac();
         this.resultMac = var1;
      }

      return this.resultMac;
   }

   public RecipientId getRID() {
      return this.rid;
   }

   private static class MacInputStream extends InputStream {

      private final InputStream inStream;
      private final Mac mac;


      MacInputStream(Mac var1, InputStream var2) {
         this.inStream = var2;
         this.mac = var1;
      }

      public byte[] getMac() {
         return this.mac.doFinal();
      }

      public int read() throws IOException {
         int var1 = this.inStream.read();
         if(var1 > 0) {
            Mac var2 = this.mac;
            byte var3 = (byte)var1;
            var2.update(var3);
         }

         return var1;
      }

      public int read(byte[] var1) throws IOException {
         int var2 = var1.length;
         return this.read(var1, 0, var2);
      }

      public int read(byte[] var1, int var2, int var3) throws IOException {
         int var4 = this.inStream.read(var1, var2, var3);
         if(var4 > 0) {
            this.mac.update(var1, var2, var4);
         }

         return var4;
      }
   }
}

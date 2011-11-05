package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.List;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.cms.EncryptedContentInfo;
import myorg.bouncycastle.asn1.cms.EnvelopedData;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInformationStore;

public class CMSEnvelopedData {

   ContentInfo contentInfo;
   private AlgorithmIdentifier encAlg;
   RecipientInformationStore recipientInfoStore;
   private ASN1Set unprotectedAttributes;


   public CMSEnvelopedData(InputStream var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
   }

   public CMSEnvelopedData(ContentInfo var1) throws CMSException {
      this.contentInfo = var1;
      EnvelopedData var2 = EnvelopedData.getInstance(var1.getContent());
      EncryptedContentInfo var3 = var2.getEncryptedContentInfo();
      AlgorithmIdentifier var4 = var3.getContentEncryptionAlgorithm();
      this.encAlg = var4;
      byte[] var5 = var3.getEncryptedContent().getOctets();
      ASN1Set var6 = var2.getRecipientInfos();
      AlgorithmIdentifier var7 = this.encAlg;
      List var8 = CMSEnvelopedHelper.readRecipientInfos(var6, var5, var7, (AlgorithmIdentifier)null, (AlgorithmIdentifier)null);
      RecipientInformationStore var9 = new RecipientInformationStore(var8);
      this.recipientInfoStore = var9;
      ASN1Set var10 = var2.getUnprotectedAttrs();
      this.unprotectedAttributes = var10;
   }

   public CMSEnvelopedData(byte[] var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
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

   public ContentInfo getContentInfo() {
      return this.contentInfo;
   }

   public byte[] getEncoded() throws IOException {
      return this.contentInfo.getEncoded();
   }

   public String getEncryptionAlgOID() {
      return this.encAlg.getObjectId().getId();
   }

   public byte[] getEncryptionAlgParams() {
      try {
         DEREncodable var1 = this.encAlg.getParameters();
         byte[] var2 = this.encodeObj(var1);
         return var2;
      } catch (Exception var5) {
         String var4 = "exception getting encryption parameters " + var5;
         throw new RuntimeException(var4);
      }
   }

   public AlgorithmParameters getEncryptionAlgorithmParameters(String var1) throws CMSException, NoSuchProviderException {
      Provider var2 = CMSUtils.getProvider(var1);
      return this.getEncryptionAlgorithmParameters(var2);
   }

   public AlgorithmParameters getEncryptionAlgorithmParameters(Provider var1) throws CMSException {
      CMSEnvelopedHelper var2 = CMSEnvelopedHelper.INSTANCE;
      String var3 = this.getEncryptionAlgOID();
      byte[] var4 = this.getEncryptionAlgParams();
      return var2.getEncryptionAlgorithmParameters(var3, var4, var1);
   }

   public RecipientInformationStore getRecipientInfos() {
      return this.recipientInfoStore;
   }

   public AttributeTable getUnprotectedAttributes() {
      AttributeTable var1;
      if(this.unprotectedAttributes == null) {
         var1 = null;
      } else {
         ASN1Set var2 = this.unprotectedAttributes;
         var1 = new AttributeTable(var2);
      }

      return var1;
   }
}

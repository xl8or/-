package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.List;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.AuthenticatedData;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInformationStore;
import myorg.bouncycastle.util.Arrays;

public class CMSAuthenticatedData {

   private ASN1Set authAttrs;
   ContentInfo contentInfo;
   private byte[] mac;
   private AlgorithmIdentifier macAlg;
   RecipientInformationStore recipientInfoStore;
   private ASN1Set unauthAttrs;


   public CMSAuthenticatedData(InputStream var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
   }

   public CMSAuthenticatedData(ContentInfo var1) throws CMSException {
      this.contentInfo = var1;
      AuthenticatedData var2 = AuthenticatedData.getInstance(var1.getContent());
      ContentInfo var3 = var2.getEncapsulatedContentInfo();
      AlgorithmIdentifier var4 = var2.getMacAlgorithm();
      this.macAlg = var4;
      byte[] var5 = var2.getMac().getOctets();
      this.mac = var5;
      byte[] var6 = ASN1OctetString.getInstance(var3.getContent()).getOctets();
      ASN1Set var7 = var2.getRecipientInfos();
      AlgorithmIdentifier var8 = this.macAlg;
      List var9 = CMSEnvelopedHelper.readRecipientInfos(var7, var6, (AlgorithmIdentifier)null, var8, (AlgorithmIdentifier)null);
      ASN1Set var10 = var2.getAuthAttrs();
      this.authAttrs = var10;
      RecipientInformationStore var11 = new RecipientInformationStore(var9);
      this.recipientInfoStore = var11;
      ASN1Set var12 = var2.getUnauthAttrs();
      this.unauthAttrs = var12;
   }

   public CMSAuthenticatedData(byte[] var1) throws CMSException {
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

   public AttributeTable getAuthAttrs() {
      AttributeTable var1;
      if(this.authAttrs == null) {
         var1 = null;
      } else {
         ASN1Set var2 = this.authAttrs;
         var1 = new AttributeTable(var2);
      }

      return var1;
   }

   public ContentInfo getContentInfo() {
      return this.contentInfo;
   }

   public byte[] getEncoded() throws IOException {
      return this.contentInfo.getEncoded();
   }

   public byte[] getMac() {
      return Arrays.clone(this.mac);
   }

   public String getMacAlgOID() {
      return this.macAlg.getObjectId().getId();
   }

   public byte[] getMacAlgParams() {
      try {
         DEREncodable var1 = this.macAlg.getParameters();
         byte[] var2 = this.encodeObj(var1);
         return var2;
      } catch (Exception var5) {
         String var4 = "exception getting encryption parameters " + var5;
         throw new RuntimeException(var4);
      }
   }

   public AlgorithmParameters getMacAlgorithmParameters(String var1) throws CMSException, NoSuchProviderException {
      Provider var2 = CMSUtils.getProvider(var1);
      return this.getMacAlgorithmParameters(var2);
   }

   public AlgorithmParameters getMacAlgorithmParameters(Provider var1) throws CMSException {
      CMSEnvelopedHelper var2 = CMSEnvelopedHelper.INSTANCE;
      String var3 = this.getMacAlgOID();
      byte[] var4 = this.getMacAlgParams();
      return var2.getEncryptionAlgorithmParameters(var3, var4, var1);
   }

   public RecipientInformationStore getRecipientInfos() {
      return this.recipientInfoStore;
   }

   public AttributeTable getUnauthAttrs() {
      AttributeTable var1;
      if(this.unauthAttrs == null) {
         var1 = null;
      } else {
         ASN1Set var2 = this.unauthAttrs;
         var1 = new AttributeTable(var2);
      }

      return var1;
   }
}

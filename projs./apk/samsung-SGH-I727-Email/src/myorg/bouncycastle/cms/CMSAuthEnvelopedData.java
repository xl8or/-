package myorg.bouncycastle.cms;

import java.io.InputStream;
import java.util.List;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.cms.AuthEnvelopedData;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.cms.EncryptedContentInfo;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInformationStore;

class CMSAuthEnvelopedData {

   private ASN1Set authAttrs;
   private AlgorithmIdentifier authEncAlg;
   ContentInfo contentInfo;
   private byte[] mac;
   private OriginatorInfo originator;
   RecipientInformationStore recipientInfoStore;
   private ASN1Set unauthAttrs;


   public CMSAuthEnvelopedData(InputStream var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
   }

   public CMSAuthEnvelopedData(ContentInfo var1) throws CMSException {
      this.contentInfo = var1;
      AuthEnvelopedData var2 = AuthEnvelopedData.getInstance(var1.getContent());
      OriginatorInfo var3 = var2.getOriginatorInfo();
      this.originator = var3;
      EncryptedContentInfo var4 = var2.getAuthEncryptedContentInfo();
      AlgorithmIdentifier var5 = var4.getContentEncryptionAlgorithm();
      this.authEncAlg = var5;
      byte[] var6 = var4.getEncryptedContent().getOctets();
      ASN1Set var7 = var2.getRecipientInfos();
      AlgorithmIdentifier var8 = this.authEncAlg;
      List var9 = CMSEnvelopedHelper.readRecipientInfos(var7, var6, (AlgorithmIdentifier)null, (AlgorithmIdentifier)null, var8);
      RecipientInformationStore var10 = new RecipientInformationStore(var9);
      this.recipientInfoStore = var10;
      ASN1Set var11 = var2.getAuthAttrs();
      this.authAttrs = var11;
      byte[] var12 = var2.getMac().getOctets();
      this.mac = var12;
      ASN1Set var13 = var2.getUnauthAttrs();
      this.unauthAttrs = var13;
   }

   public CMSAuthEnvelopedData(byte[] var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
   }
}

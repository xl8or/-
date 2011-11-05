package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.cms.SignedData;
import myorg.bouncycastle.asn1.cms.SignerInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.BaseDigestCalculator;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.cms.CMSProcessableByteArray;
import myorg.bouncycastle.cms.CMSSignedHelper;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.DigestCalculator;
import myorg.bouncycastle.cms.SignerInformation;
import myorg.bouncycastle.cms.SignerInformationStore;
import myorg.bouncycastle.x509.NoSuchStoreException;
import myorg.bouncycastle.x509.X509Store;

public class CMSSignedData {

   private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;
   X509Store attributeStore;
   CertStore certStore;
   X509Store certificateStore;
   ContentInfo contentInfo;
   X509Store crlStore;
   private Map hashes;
   CMSProcessable signedContent;
   SignedData signedData;
   SignerInformationStore signerInfoStore;


   public CMSSignedData(InputStream var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
   }

   public CMSSignedData(Map var1, ContentInfo var2) {
      this.hashes = var1;
      this.contentInfo = var2;
      SignedData var3 = SignedData.getInstance(this.contentInfo.getContent());
      this.signedData = var3;
   }

   public CMSSignedData(Map var1, byte[] var2) throws CMSException {
      ContentInfo var3 = CMSUtils.readContentInfo(var2);
      this(var1, var3);
   }

   public CMSSignedData(ContentInfo var1) {
      this.contentInfo = var1;
      SignedData var2 = SignedData.getInstance(this.contentInfo.getContent());
      this.signedData = var2;
      if(this.signedData.getEncapContentInfo().getContent() != null) {
         byte[] var3 = ((ASN1OctetString)((ASN1OctetString)this.signedData.getEncapContentInfo().getContent())).getOctets();
         CMSProcessableByteArray var4 = new CMSProcessableByteArray(var3);
         this.signedContent = var4;
      } else {
         this.signedContent = null;
      }
   }

   public CMSSignedData(CMSProcessable var1, InputStream var2) throws CMSException {
      ContentInfo var3 = CMSUtils.readContentInfo((InputStream)(new ASN1InputStream(var2)));
      this(var1, var3);
   }

   public CMSSignedData(CMSProcessable var1, ContentInfo var2) {
      this.signedContent = var1;
      this.contentInfo = var2;
      SignedData var3 = SignedData.getInstance(this.contentInfo.getContent());
      this.signedData = var3;
   }

   public CMSSignedData(CMSProcessable var1, byte[] var2) throws CMSException {
      ContentInfo var3 = CMSUtils.readContentInfo(var2);
      this(var1, var3);
   }

   private CMSSignedData(CMSSignedData var1) {
      SignedData var2 = var1.signedData;
      this.signedData = var2;
      ContentInfo var3 = var1.contentInfo;
      this.contentInfo = var3;
      CMSProcessable var4 = var1.signedContent;
      this.signedContent = var4;
      CertStore var5 = var1.certStore;
      this.certStore = var5;
      SignerInformationStore var6 = var1.signerInfoStore;
      this.signerInfoStore = var6;
   }

   public CMSSignedData(byte[] var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
   }

   public static CMSSignedData replaceCertificatesAndCRLs(CMSSignedData var0, CertStore var1) throws CMSException {
      CMSSignedData var2 = new CMSSignedData(var0);
      var2.certStore = var1;
      ASN1Set var3 = null;
      ASN1Set var4 = null;

      ASN1Set var5;
      int var6;
      try {
         var5 = CMSUtils.createBerSetFromList(CMSUtils.getCertificatesFromStore(var1));
         var6 = var5.size();
      } catch (CertStoreException var17) {
         throw new CMSException("error getting certs from certStore", var17);
      }

      if(var6 != 0) {
         var3 = var5;
      }

      try {
         var5 = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(var1));
         var6 = var5.size();
      } catch (CertStoreException var16) {
         throw new CMSException("error getting crls from certStore", var16);
      }

      if(var6 != 0) {
         var4 = var5;
      }

      ASN1Set var7 = var0.signedData.getDigestAlgorithms();
      ContentInfo var8 = var0.signedData.getEncapContentInfo();
      ASN1Set var9 = var0.signedData.getSignerInfos();
      SignedData var10 = new SignedData(var7, var8, var3, var4, var9);
      var2.signedData = var10;
      DERObjectIdentifier var11 = var2.contentInfo.getContentType();
      SignedData var12 = var2.signedData;
      ContentInfo var13 = new ContentInfo(var11, var12);
      var2.contentInfo = var13;
      return var2;
   }

   public static CMSSignedData replaceSigners(CMSSignedData var0, SignerInformationStore var1) {
      CMSSignedData var2 = new CMSSignedData(var0);
      var2.signerInfoStore = var1;
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      Iterator var5 = var1.getSigners().iterator();

      while(var5.hasNext()) {
         SignerInformation var6 = (SignerInformation)var5.next();
         CMSSignedHelper var7 = CMSSignedHelper.INSTANCE;
         AlgorithmIdentifier var8 = var6.getDigestAlgorithmID();
         AlgorithmIdentifier var9 = var7.fixAlgID(var8);
         var3.add(var9);
         SignerInfo var10 = var6.toSignerInfo();
         var4.add(var10);
      }

      DERSet var11 = new DERSet(var3);
      DERSet var12 = new DERSet(var4);
      ASN1Sequence var13 = (ASN1Sequence)var0.signedData.getDERObject();
      ASN1EncodableVector var14 = new ASN1EncodableVector();
      DEREncodable var15 = var13.getObjectAt(0);
      var14.add(var15);
      var14.add(var11);
      int var16 = 2;

      while(true) {
         int var17 = var13.size() - 1;
         if(var16 == var17) {
            var14.add(var12);
            SignedData var19 = SignedData.getInstance(new BERSequence(var14));
            var2.signedData = var19;
            DERObjectIdentifier var20 = var2.contentInfo.getContentType();
            SignedData var21 = var2.signedData;
            ContentInfo var22 = new ContentInfo(var20, var21);
            var2.contentInfo = var22;
            return var2;
         }

         DEREncodable var18 = var13.getObjectAt(var16);
         var14.add(var18);
         ++var16;
      }
   }

   public X509Store getAttributeCertificates(String var1, String var2) throws NoSuchStoreException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getAttributeCertificates(var1, var3);
   }

   public X509Store getAttributeCertificates(String var1, Provider var2) throws NoSuchStoreException, CMSException {
      if(this.attributeStore == null) {
         CMSSignedHelper var3 = HELPER;
         ASN1Set var4 = this.signedData.getCertificates();
         X509Store var5 = var3.createAttributeStore(var1, var2, var4);
         this.attributeStore = var5;
      }

      return this.attributeStore;
   }

   public X509Store getCRLs(String var1, String var2) throws NoSuchStoreException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getCRLs(var1, var3);
   }

   public X509Store getCRLs(String var1, Provider var2) throws NoSuchStoreException, CMSException {
      if(this.crlStore == null) {
         CMSSignedHelper var3 = HELPER;
         ASN1Set var4 = this.signedData.getCRLs();
         X509Store var5 = var3.createCRLsStore(var1, var2, var4);
         this.crlStore = var5;
      }

      return this.crlStore;
   }

   public X509Store getCertificates(String var1, String var2) throws NoSuchStoreException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getCertificates(var1, var3);
   }

   public X509Store getCertificates(String var1, Provider var2) throws NoSuchStoreException, CMSException {
      if(this.certificateStore == null) {
         CMSSignedHelper var3 = HELPER;
         ASN1Set var4 = this.signedData.getCertificates();
         X509Store var5 = var3.createCertificateStore(var1, var2, var4);
         this.certificateStore = var5;
      }

      return this.certificateStore;
   }

   public CertStore getCertificatesAndCRLs(String var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getCertificatesAndCRLs(var1, var3);
   }

   public CertStore getCertificatesAndCRLs(String var1, Provider var2) throws NoSuchAlgorithmException, CMSException {
      if(this.certStore == null) {
         ASN1Set var3 = this.signedData.getCertificates();
         ASN1Set var4 = this.signedData.getCRLs();
         CertStore var5 = HELPER.createCertStore(var1, var2, var3, var4);
         this.certStore = var5;
      }

      return this.certStore;
   }

   public ContentInfo getContentInfo() {
      return this.contentInfo;
   }

   public byte[] getEncoded() throws IOException {
      return this.contentInfo.getEncoded();
   }

   public CMSProcessable getSignedContent() {
      return this.signedContent;
   }

   public String getSignedContentTypeOID() {
      return this.signedData.getEncapContentInfo().getContentType().getId();
   }

   public SignerInformationStore getSignerInfos() {
      if(this.signerInfoStore == null) {
         ASN1Set var1 = this.signedData.getSignerInfos();
         ArrayList var2 = new ArrayList();
         int var3 = 0;

         while(true) {
            int var4 = var1.size();
            if(var3 == var4) {
               SignerInformationStore var16 = new SignerInformationStore(var2);
               this.signerInfoStore = var16;
               break;
            }

            SignerInfo var5 = SignerInfo.getInstance(var1.getObjectAt(var3));
            DERObjectIdentifier var6 = this.signedData.getEncapContentInfo().getContentType();
            if(this.hashes == null) {
               CMSProcessable var7 = this.signedContent;
               SignerInformation var8 = new SignerInformation(var5, var6, var7, (DigestCalculator)null);
               var2.add(var8);
            } else {
               Map var10 = this.hashes;
               String var11 = var5.getDigestAlgorithm().getObjectId().getId();
               byte[] var12 = (byte[])((byte[])var10.get(var11));
               BaseDigestCalculator var13 = new BaseDigestCalculator(var12);
               SignerInformation var14 = new SignerInformation(var5, var6, (CMSProcessable)null, var13);
               var2.add(var14);
            }

            ++var3;
         }
      }

      return this.signerInfoStore;
   }

   public int getVersion() {
      return this.signedData.getVersion().getValue().intValue();
   }
}

package myorg.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Generator;
import myorg.bouncycastle.asn1.ASN1OctetStringParser;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1SetParser;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequenceGenerator;
import myorg.bouncycastle.asn1.BERSetParser;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.ContentInfoParser;
import myorg.bouncycastle.asn1.cms.SignedDataParser;
import myorg.bouncycastle.asn1.cms.SignerInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.BaseDigestCalculator;
import myorg.bouncycastle.cms.CMSContentInfoParser;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.cms.CMSSignedHelper;
import myorg.bouncycastle.cms.CMSTypedStream;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.SignerInformation;
import myorg.bouncycastle.cms.SignerInformationStore;
import myorg.bouncycastle.util.io.Streams;
import myorg.bouncycastle.x509.NoSuchStoreException;
import myorg.bouncycastle.x509.X509Store;

public class CMSSignedDataParser extends CMSContentInfoParser {

   private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;
   private X509Store _attributeStore;
   private ASN1Set _certSet;
   private CertStore _certStore;
   private X509Store _certificateStore;
   private ASN1Set _crlSet;
   private X509Store _crlStore;
   private Map _digests;
   private boolean _isCertCrlParsed;
   private CMSTypedStream _signedContent;
   private DERObjectIdentifier _signedContentType;
   private SignedDataParser _signedData;
   private SignerInformationStore _signerInfoStore;


   public CMSSignedDataParser(InputStream var1) throws CMSException {
      this((CMSTypedStream)null, var1);
   }

   public CMSSignedDataParser(CMSTypedStream param1, InputStream param2) throws CMSException {
      // $FF: Couldn't be decompiled
   }

   public CMSSignedDataParser(CMSTypedStream var1, byte[] var2) throws CMSException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      this(var1, (InputStream)var3);
   }

   public CMSSignedDataParser(byte[] var1) throws CMSException {
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      this((InputStream)var2);
   }

   private static ASN1Set getASN1Set(ASN1SetParser var0) {
      ASN1Set var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = ASN1Set.getInstance(var0.getDERObject());
      }

      return var1;
   }

   private static void pipeOctetString(ASN1OctetStringParser var0, OutputStream var1) throws IOException {
      OutputStream var2 = CMSUtils.createBEROctetOutputStream(var1, 0, (boolean)1, 0);
      Streams.pipeAll(var0.getOctetStream(), var2);
      var2.close();
   }

   private void populateCertCrlSets() throws CMSException {
      if(!this._isCertCrlParsed) {
         this._isCertCrlParsed = (boolean)1;

         try {
            ASN1Set var1 = getASN1Set(this._signedData.getCertificates());
            this._certSet = var1;
            ASN1Set var2 = getASN1Set(this._signedData.getCrls());
            this._crlSet = var2;
         } catch (IOException var4) {
            throw new CMSException("problem parsing cert/crl sets", var4);
         }
      }
   }

   public static OutputStream replaceCertificatesAndCRLs(InputStream var0, CertStore var1, OutputStream var2) throws CMSException, IOException {
      int var3 = CMSUtils.getMaximumMemory();
      ASN1SequenceParser var4 = (ASN1SequenceParser)(new ASN1StreamParser(var0, var3)).readObject();
      SignedDataParser var5 = SignedDataParser.getInstance((new ContentInfoParser(var4)).getContent(16));
      BERSequenceGenerator var6 = new BERSequenceGenerator(var2);
      DERObjectIdentifier var7 = CMSObjectIdentifiers.signedData;
      var6.addObject(var7);
      OutputStream var8 = var6.getRawOutputStream();
      BERSequenceGenerator var9 = new BERSequenceGenerator(var8, 0, (boolean)1);
      DERInteger var10 = var5.getVersion();
      var9.addObject(var10);
      OutputStream var11 = var9.getRawOutputStream();
      byte[] var12 = var5.getDigestAlgorithms().getDERObject().getEncoded();
      var11.write(var12);
      ContentInfoParser var13 = var5.getEncapContentInfo();
      OutputStream var14 = var9.getRawOutputStream();
      BERSequenceGenerator var15 = new BERSequenceGenerator(var14);
      DERObjectIdentifier var16 = var13.getContentType();
      var15.addObject(var16);
      ASN1OctetStringParser var17 = (ASN1OctetStringParser)var13.getContent(4);
      if(var17 != null) {
         OutputStream var18 = var15.getRawOutputStream();
         pipeOctetString(var17, var18);
      }

      var15.close();
      ASN1Set var19 = getASN1Set(var5.getCertificates());
      ASN1Set var20 = getASN1Set(var5.getCrls());

      ASN1Set var21;
      try {
         var21 = CMSUtils.createBerSetFromList(CMSUtils.getCertificatesFromStore(var1));
      } catch (CertStoreException var34) {
         throw new CMSException("error getting certs from certStore", var34);
      }

      if(var21.size() > 0) {
         OutputStream var23 = var9.getRawOutputStream();
         byte[] var24 = (new DERTaggedObject((boolean)0, 0, var21)).getEncoded();
         var23.write(var24);
      }

      ASN1Set var25;
      try {
         var25 = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(var1));
      } catch (CertStoreException var33) {
         throw new CMSException("error getting crls from certStore", var33);
      }

      if(var25.size() > 0) {
         OutputStream var27 = var9.getRawOutputStream();
         byte[] var28 = (new DERTaggedObject((boolean)0, 1, var25)).getEncoded();
         var27.write(var28);
      }

      OutputStream var29 = var9.getRawOutputStream();
      byte[] var30 = var5.getSignerInfos().getDERObject().getEncoded();
      var29.write(var30);
      var9.close();
      var6.close();
      return var2;
   }

   public static OutputStream replaceSigners(InputStream var0, SignerInformationStore var1, OutputStream var2) throws CMSException, IOException {
      int var3 = CMSUtils.getMaximumMemory();
      ASN1SequenceParser var4 = (ASN1SequenceParser)(new ASN1StreamParser(var0, var3)).readObject();
      SignedDataParser var5 = SignedDataParser.getInstance((new ContentInfoParser(var4)).getContent(16));
      BERSequenceGenerator var6 = new BERSequenceGenerator(var2);
      DERObjectIdentifier var7 = CMSObjectIdentifiers.signedData;
      var6.addObject(var7);
      OutputStream var8 = var6.getRawOutputStream();
      BERSequenceGenerator var9 = new BERSequenceGenerator(var8, 0, (boolean)1);
      DERInteger var10 = var5.getVersion();
      var9.addObject(var10);
      DERObject var11 = var5.getDigestAlgorithms().getDERObject();
      ASN1EncodableVector var12 = new ASN1EncodableVector();
      Iterator var13 = var1.getSigners().iterator();

      while(var13.hasNext()) {
         SignerInformation var14 = (SignerInformation)var13.next();
         CMSSignedHelper var15 = CMSSignedHelper.INSTANCE;
         AlgorithmIdentifier var16 = var14.getDigestAlgorithmID();
         AlgorithmIdentifier var17 = var15.fixAlgID(var16);
         var12.add(var17);
      }

      OutputStream var18 = var9.getRawOutputStream();
      byte[] var19 = (new DERSet(var12)).getEncoded();
      var18.write(var19);
      ContentInfoParser var20 = var5.getEncapContentInfo();
      OutputStream var21 = var9.getRawOutputStream();
      BERSequenceGenerator var22 = new BERSequenceGenerator(var21);
      DERObjectIdentifier var23 = var20.getContentType();
      var22.addObject(var23);
      ASN1OctetStringParser var24 = (ASN1OctetStringParser)var20.getContent(4);
      if(var24 != null) {
         OutputStream var25 = var22.getRawOutputStream();
         pipeOctetString(var24, var25);
      }

      var22.close();
      ASN1SetParser var26 = var5.getCertificates();
      writeSetToGeneratorTagged(var9, var26, 0);
      ASN1SetParser var27 = var5.getCrls();
      writeSetToGeneratorTagged(var9, var27, 1);
      ASN1EncodableVector var28 = new ASN1EncodableVector();
      Iterator var29 = var1.getSigners().iterator();

      while(var29.hasNext()) {
         SignerInfo var30 = ((SignerInformation)var29.next()).toSignerInfo();
         var28.add(var30);
      }

      OutputStream var31 = var9.getRawOutputStream();
      byte[] var32 = (new DERSet(var28)).getEncoded();
      var31.write(var32);
      var9.close();
      var6.close();
      return var2;
   }

   private static void writeSetToGeneratorTagged(ASN1Generator var0, ASN1SetParser var1, int var2) throws IOException {
      ASN1Set var3 = getASN1Set(var1);
      if(var3 != null) {
         Object var4;
         if(var1 instanceof BERSetParser) {
            var4 = new BERTaggedObject((boolean)0, var2, var3);
         } else {
            var4 = new DERTaggedObject((boolean)0, var2, var3);
         }

         OutputStream var5 = var0.getRawOutputStream();
         byte[] var6 = ((ASN1TaggedObject)var4).getEncoded();
         var5.write(var6);
      }
   }

   public X509Store getAttributeCertificates(String var1, String var2) throws NoSuchStoreException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getAttributeCertificates(var1, var3);
   }

   public X509Store getAttributeCertificates(String var1, Provider var2) throws NoSuchStoreException, CMSException {
      if(this._attributeStore == null) {
         this.populateCertCrlSets();
         CMSSignedHelper var3 = HELPER;
         ASN1Set var4 = this._certSet;
         X509Store var5 = var3.createAttributeStore(var1, var2, var4);
         this._attributeStore = var5;
      }

      return this._attributeStore;
   }

   public X509Store getCRLs(String var1, String var2) throws NoSuchStoreException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getCRLs(var1, var3);
   }

   public X509Store getCRLs(String var1, Provider var2) throws NoSuchStoreException, CMSException {
      if(this._crlStore == null) {
         this.populateCertCrlSets();
         CMSSignedHelper var3 = HELPER;
         ASN1Set var4 = this._crlSet;
         X509Store var5 = var3.createCRLsStore(var1, var2, var4);
         this._crlStore = var5;
      }

      return this._crlStore;
   }

   public X509Store getCertificates(String var1, String var2) throws NoSuchStoreException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getCertificates(var1, var3);
   }

   public X509Store getCertificates(String var1, Provider var2) throws NoSuchStoreException, CMSException {
      if(this._certificateStore == null) {
         this.populateCertCrlSets();
         CMSSignedHelper var3 = HELPER;
         ASN1Set var4 = this._certSet;
         X509Store var5 = var3.createCertificateStore(var1, var2, var4);
         this._certificateStore = var5;
      }

      return this._certificateStore;
   }

   public CertStore getCertificatesAndCRLs(String var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getCertificatesAndCRLs(var1, var3);
   }

   public CertStore getCertificatesAndCRLs(String var1, Provider var2) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      if(this._certStore == null) {
         this.populateCertCrlSets();
         CMSSignedHelper var3 = HELPER;
         ASN1Set var4 = this._certSet;
         ASN1Set var5 = this._crlSet;
         CertStore var6 = var3.createCertStore(var1, var2, var4, var5);
         this._certStore = var6;
      }

      return this._certStore;
   }

   public CMSTypedStream getSignedContent() {
      CMSTypedStream var5;
      if(this._signedContent != null) {
         Object var1 = this._signedContent.getContentStream();

         MessageDigest var3;
         for(Iterator var2 = this._digests.values().iterator(); var2.hasNext(); var1 = new DigestInputStream((InputStream)var1, var3)) {
            var3 = (MessageDigest)var2.next();
         }

         String var4 = this._signedContent.getContentType();
         var5 = new CMSTypedStream(var4, (InputStream)var1);
      } else {
         var5 = null;
      }

      return var5;
   }

   public String getSignedContentTypeOID() {
      return this._signedContentType.getId();
   }

   public SignerInformationStore getSignerInfos() throws CMSException {
      if(this._signerInfoStore == null) {
         this.populateCertCrlSets();
         ArrayList var1 = new ArrayList();
         HashMap var2 = new HashMap();
         Iterator var3 = this._digests.keySet().iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            byte[] var5 = ((MessageDigest)this._digests.get(var4)).digest();
            var2.put(var4, var5);
         }

         try {
            ASN1SetParser var7 = this._signedData.getSignerInfos();

            while(true) {
               DEREncodable var8 = var7.readObject();
               if(var8 == null) {
                  break;
               }

               SignerInfo var9 = SignerInfo.getInstance(var8.getDERObject());
               CMSSignedHelper var10 = HELPER;
               String var11 = var9.getDigestAlgorithm().getObjectId().getId();
               String var12 = var10.getDigestAlgName(var11);
               byte[] var13 = (byte[])((byte[])var2.get(var12));
               DERObjectIdentifier var14 = this._signedContentType;
               BaseDigestCalculator var15 = new BaseDigestCalculator(var13);
               SignerInformation var16 = new SignerInformation(var9, var14, (CMSProcessable)null, var15);
               var1.add(var16);
            }
         } catch (IOException var23) {
            StringBuilder var19 = (new StringBuilder()).append("io exception: ");
            String var20 = var23.getMessage();
            String var21 = var19.append(var20).toString();
            throw new CMSException(var21, var23);
         }

         SignerInformationStore var22 = new SignerInformationStore(var1);
         this._signerInfoStore = var22;
      }

      return this._signerInfoStore;
   }

   public int getVersion() {
      return this._signedData.getVersion().getValue().intValue();
   }
}

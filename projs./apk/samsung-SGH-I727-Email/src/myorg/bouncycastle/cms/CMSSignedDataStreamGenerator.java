package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequenceGenerator;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.SignerIdentifier;
import myorg.bouncycastle.asn1.cms.SignerInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DigestInfo;
import myorg.bouncycastle.cms.CMSAttributeTableGenerator;
import myorg.bouncycastle.cms.CMSSignedGenerator;
import myorg.bouncycastle.cms.CMSSignedHelper;
import myorg.bouncycastle.cms.CMSStreamException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import myorg.bouncycastle.cms.SignerInformation;
import myorg.bouncycastle.cms.SimpleAttributeTableGenerator;

public class CMSSignedDataStreamGenerator extends CMSSignedGenerator {

   private int _bufferSize;
   private List _messageDigests;
   private List _signerInfs;


   public CMSSignedDataStreamGenerator() {
      ArrayList var1 = new ArrayList();
      this._signerInfs = var1;
      ArrayList var2 = new ArrayList();
      this._messageDigests = var2;
   }

   public CMSSignedDataStreamGenerator(SecureRandom var1) {
      super(var1);
      ArrayList var2 = new ArrayList();
      this._signerInfs = var2;
      ArrayList var3 = new ArrayList();
      this._messageDigests = var3;
   }

   private static OutputStream attachDigestsToOutputStream(List var0, OutputStream var1) {
      OutputStream var2 = var1;

      CMSSignedGenerator.DigOutputStream var5;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var2 = getSafeTeeOutputStream(var2, var5)) {
         MessageDigest var4 = (MessageDigest)var3.next();
         var5 = new CMSSignedGenerator.DigOutputStream(var4);
      }

      return var2;
   }

   private DERInteger calculateVersion(String var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      Iterator var6;
      if(this._certs != null) {
         var6 = this._certs.iterator();

         while(var6.hasNext()) {
            Object var7 = var6.next();
            if(var7 instanceof ASN1TaggedObject) {
               ASN1TaggedObject var8 = (ASN1TaggedObject)var7;
               if(var8.getTagNo() == 1) {
                  var4 = true;
               } else if(var8.getTagNo() == 2) {
                  var5 = true;
               } else if(var8.getTagNo() == 3) {
                  var2 = true;
               }
            }
         }
      }

      DERInteger var9;
      if(var2) {
         var9 = new DERInteger(5);
      } else {
         if(this._crls != null && !var2) {
            var6 = this._crls.iterator();

            while(var6.hasNext()) {
               if(var6.next() instanceof ASN1TaggedObject) {
                  var3 = true;
               }
            }
         }

         if(var3) {
            var9 = new DERInteger(5);
         } else if(var5) {
            var9 = new DERInteger(4);
         } else if(var4) {
            var9 = new DERInteger(3);
         } else {
            String var10 = DATA;
            if(var1.equals(var10)) {
               List var11 = this._signers;
               if(this.checkForVersion3(var11)) {
                  var9 = new DERInteger(3);
               } else {
                  var9 = new DERInteger(1);
               }
            } else {
               var9 = new DERInteger(3);
            }
         }
      }

      return var9;
   }

   private boolean checkForVersion3(List var1) {
      Iterator var2 = var1.iterator();

      boolean var3;
      while(true) {
         if(var2.hasNext()) {
            if(SignerInfo.getInstance(((SignerInformation)var2.next()).toSignerInfo()).getVersion().getValue().intValue() != 3) {
               continue;
            }

            var3 = true;
            break;
         }

         var3 = false;
         break;
      }

      return var3;
   }

   private static OutputStream getSafeOutputStream(OutputStream var0) {
      Object var1;
      if(var0 == null) {
         var1 = new CMSSignedDataStreamGenerator.NullOutputStream((CMSSignedDataStreamGenerator.1)null);
      } else {
         var1 = var0;
      }

      return (OutputStream)var1;
   }

   private static OutputStream getSafeTeeOutputStream(OutputStream var0, OutputStream var1) {
      Object var2;
      if(var0 == null) {
         var2 = getSafeOutputStream(var1);
      } else if(var1 == null) {
         var2 = getSafeOutputStream(var0);
      } else {
         var2 = new CMSSignedDataStreamGenerator.TeeOutputStream(var0, var1);
      }

      return (OutputStream)var2;
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var5 = new DefaultSignedAttributeTableGenerator();
      CMSAttributeTableGenerator var6 = (CMSAttributeTableGenerator)false;
      this.addSigner(var1, var2, var3, (CMSAttributeTableGenerator)var5, var6, var4);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, String var5) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var6 = new DefaultSignedAttributeTableGenerator();
      CMSAttributeTableGenerator var7 = (CMSAttributeTableGenerator)false;
      this.addSigner(var1, var2, var3, var4, (CMSAttributeTableGenerator)var6, var7, var5);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, Provider var5) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var6 = new DefaultSignedAttributeTableGenerator();
      CMSAttributeTableGenerator var7 = (CMSAttributeTableGenerator)false;
      this.addSigner(var1, var2, var3, var4, (CMSAttributeTableGenerator)var6, var7, var5);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, AttributeTable var5, AttributeTable var6, String var7) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var8 = new DefaultSignedAttributeTableGenerator(var5);
      SimpleAttributeTableGenerator var9 = new SimpleAttributeTableGenerator(var6);
      this.addSigner(var1, var2, var3, var4, (CMSAttributeTableGenerator)var8, (CMSAttributeTableGenerator)var9, var7);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, AttributeTable var5, AttributeTable var6, Provider var7) throws NoSuchAlgorithmException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var8 = new DefaultSignedAttributeTableGenerator(var5);
      SimpleAttributeTableGenerator var9 = new SimpleAttributeTableGenerator(var6);
      this.addSigner(var1, var2, var3, var4, (CMSAttributeTableGenerator)var8, (CMSAttributeTableGenerator)var9, var7);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, CMSAttributeTableGenerator var5, CMSAttributeTableGenerator var6, String var7) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      Provider var8 = CMSUtils.getProvider(var7);
      this.addSigner(var1, var2, var3, var4, var5, var6, var8);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, CMSAttributeTableGenerator var5, CMSAttributeTableGenerator var6, Provider var7) throws NoSuchAlgorithmException, InvalidKeyException {
      CMSSignedHelper var8 = CMSSignedHelper.INSTANCE;
      String var10 = var8.getDigestAlgName(var4);
      CMSSignedHelper var11 = CMSSignedHelper.INSTANCE;
      MessageDigest var14 = var11.getDigestInstance(var10, var7);
      List var15 = this._signerInfs;
      SignerIdentifier var16 = getSignerIdentifier(var2);
      CMSSignedDataStreamGenerator.SignerInf var24 = new CMSSignedDataStreamGenerator.SignerInf(var1, var16, var4, var3, var5, var6, var14, var7);
      var15.add(var24);
      this._messageDigests.add(var14);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, Provider var4) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var5 = new DefaultSignedAttributeTableGenerator();
      CMSAttributeTableGenerator var6 = (CMSAttributeTableGenerator)false;
      this.addSigner(var1, var2, var3, (CMSAttributeTableGenerator)var5, var6, var4);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, AttributeTable var4, AttributeTable var5, String var6) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var7 = new DefaultSignedAttributeTableGenerator(var4);
      SimpleAttributeTableGenerator var8 = new SimpleAttributeTableGenerator(var5);
      this.addSigner(var1, var2, var3, (CMSAttributeTableGenerator)var7, (CMSAttributeTableGenerator)var8, var6);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, AttributeTable var4, AttributeTable var5, Provider var6) throws NoSuchAlgorithmException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var7 = new DefaultSignedAttributeTableGenerator(var4);
      SimpleAttributeTableGenerator var8 = new SimpleAttributeTableGenerator(var5);
      this.addSigner(var1, var2, var3, (CMSAttributeTableGenerator)var7, (CMSAttributeTableGenerator)var8, var6);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, CMSAttributeTableGenerator var4, CMSAttributeTableGenerator var5, String var6) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      Provider var7 = CMSUtils.getProvider(var6);
      this.addSigner(var1, var2, var3, var4, var5, var7);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, CMSAttributeTableGenerator var4, CMSAttributeTableGenerator var5, Provider var6) throws NoSuchAlgorithmException, InvalidKeyException {
      String var7 = this.getEncOID(var1, var3);
      this.addSigner(var1, var2, var7, var3, var4, var5, var6);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, String var4) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var5 = new DefaultSignedAttributeTableGenerator();
      CMSAttributeTableGenerator var6 = (CMSAttributeTableGenerator)false;
      this.addSigner(var1, var2, var3, (CMSAttributeTableGenerator)var5, var6, var4);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, String var4, String var5) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var6 = new DefaultSignedAttributeTableGenerator();
      CMSAttributeTableGenerator var7 = (CMSAttributeTableGenerator)false;
      this.addSigner(var1, var2, var3, var4, (CMSAttributeTableGenerator)var6, var7, var5);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, String var4, Provider var5) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var6 = new DefaultSignedAttributeTableGenerator();
      CMSAttributeTableGenerator var7 = (CMSAttributeTableGenerator)false;
      this.addSigner(var1, var2, var3, var4, (CMSAttributeTableGenerator)var6, var7, var5);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, String var4, CMSAttributeTableGenerator var5, CMSAttributeTableGenerator var6, String var7) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      Provider var8 = CMSUtils.getProvider(var7);
      this.addSigner(var1, var2, var3, var4, var5, var6, var8);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, String var4, CMSAttributeTableGenerator var5, CMSAttributeTableGenerator var6, Provider var7) throws NoSuchAlgorithmException, InvalidKeyException {
      CMSSignedHelper var8 = CMSSignedHelper.INSTANCE;
      String var10 = var8.getDigestAlgName(var4);
      CMSSignedHelper var11 = CMSSignedHelper.INSTANCE;
      MessageDigest var14 = var11.getDigestInstance(var10, var7);
      List var15 = this._signerInfs;
      SignerIdentifier var16 = getSignerIdentifier(var2);
      CMSSignedDataStreamGenerator.SignerInf var24 = new CMSSignedDataStreamGenerator.SignerInf(var1, var16, var4, var3, var5, var6, var14, var7);
      var15.add(var24);
      this._messageDigests.add(var14);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, Provider var4) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var5 = new DefaultSignedAttributeTableGenerator();
      CMSAttributeTableGenerator var6 = (CMSAttributeTableGenerator)false;
      this.addSigner(var1, var2, var3, (CMSAttributeTableGenerator)var5, var6, var4);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, AttributeTable var4, AttributeTable var5, String var6) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var7 = new DefaultSignedAttributeTableGenerator(var4);
      SimpleAttributeTableGenerator var8 = new SimpleAttributeTableGenerator(var5);
      this.addSigner(var1, var2, var3, (CMSAttributeTableGenerator)var7, (CMSAttributeTableGenerator)var8, var6);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, AttributeTable var4, AttributeTable var5, Provider var6) throws NoSuchAlgorithmException, InvalidKeyException {
      DefaultSignedAttributeTableGenerator var7 = new DefaultSignedAttributeTableGenerator(var4);
      SimpleAttributeTableGenerator var8 = new SimpleAttributeTableGenerator(var5);
      this.addSigner(var1, var2, var3, (CMSAttributeTableGenerator)var7, (CMSAttributeTableGenerator)var8, var6);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, CMSAttributeTableGenerator var4, CMSAttributeTableGenerator var5, String var6) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      Provider var7 = CMSUtils.getProvider(var6);
      this.addSigner(var1, var2, var3, var4, var5, var7);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, CMSAttributeTableGenerator var4, CMSAttributeTableGenerator var5, Provider var6) throws NoSuchAlgorithmException, InvalidKeyException {
      String var7 = this.getEncOID(var1, var3);
      this.addSigner(var1, var2, var7, var3, var4, var5, var6);
   }

   public OutputStream open(OutputStream var1) throws IOException {
      return this.open(var1, (boolean)0);
   }

   public OutputStream open(OutputStream var1, String var2, boolean var3) throws IOException {
      return this.open(var1, var2, var3, (OutputStream)null);
   }

   public OutputStream open(OutputStream var1, String var2, boolean var3, OutputStream var4) throws IOException {
      BERSequenceGenerator var5 = new BERSequenceGenerator(var1);
      DERObjectIdentifier var6 = CMSObjectIdentifiers.signedData;
      var5.addObject(var6);
      OutputStream var7 = var5.getRawOutputStream();
      BERSequenceGenerator var8 = new BERSequenceGenerator(var7, 0, (boolean)1);
      DERInteger var11 = this.calculateVersion(var2);
      var8.addObject(var11);
      ASN1EncodableVector var12 = new ASN1EncodableVector();
      Iterator var13 = this._signers.iterator();

      while(var13.hasNext()) {
         SignerInformation var14 = (SignerInformation)var13.next();
         CMSSignedHelper var15 = CMSSignedHelper.INSTANCE;
         AlgorithmIdentifier var16 = var14.getDigestAlgorithmID();
         AlgorithmIdentifier var17 = var15.fixAlgID(var16);
         var12.add(var17);
      }

      Iterator var18 = this._signerInfs.iterator();

      while(var18.hasNext()) {
         AlgorithmIdentifier var19 = ((CMSSignedDataStreamGenerator.SignerInf)var18.next()).getDigestAlgorithmID();
         var12.add(var19);
      }

      OutputStream var20 = var8.getRawOutputStream();
      byte[] var21 = (new DERSet(var12)).getEncoded();
      var20.write(var21);
      OutputStream var22 = var8.getRawOutputStream();
      BERSequenceGenerator var23 = new BERSequenceGenerator(var22);
      DERObjectIdentifier var24 = new DERObjectIdentifier(var2);
      var23.addObject(var24);
      OutputStream var29;
      if(var3) {
         OutputStream var27 = var23.getRawOutputStream();
         int var28 = this._bufferSize;
         var29 = CMSUtils.createBEROctetOutputStream(var27, 0, (boolean)1, var28);
      } else {
         var29 = null;
      }

      OutputStream var32 = getSafeTeeOutputStream(var4, var29);
      OutputStream var33 = attachDigestsToOutputStream(this._messageDigests, var32);
      return new CMSSignedDataStreamGenerator.CmsSignedDataOutputStream(var33, var2, var5, var8, var23);
   }

   public OutputStream open(OutputStream var1, boolean var2) throws IOException {
      String var3 = DATA;
      return this.open(var1, var3, var2);
   }

   public OutputStream open(OutputStream var1, boolean var2, OutputStream var3) throws IOException {
      String var4 = DATA;
      return this.open(var1, var4, var2, var3);
   }

   public void setBufferSize(int var1) {
      this._bufferSize = var1;
   }

   private static class TeeOutputStream extends OutputStream {

      private OutputStream s1;
      private OutputStream s2;


      public TeeOutputStream(OutputStream var1, OutputStream var2) {
         this.s1 = var1;
         this.s2 = var2;
      }

      public void close() throws IOException {
         this.s1.close();
         this.s2.close();
      }

      public void write(int var1) throws IOException {
         this.s1.write(var1);
         this.s2.write(var1);
      }

      public void write(byte[] var1) throws IOException {
         this.s1.write(var1);
         this.s2.write(var1);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this.s1.write(var1, var2, var3);
         this.s2.write(var1, var2, var3);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class CmsSignedDataOutputStream extends OutputStream {

      private DERObjectIdentifier _contentOID;
      private BERSequenceGenerator _eiGen;
      private OutputStream _out;
      private BERSequenceGenerator _sGen;
      private BERSequenceGenerator _sigGen;


      public CmsSignedDataOutputStream(OutputStream var2, String var3, BERSequenceGenerator var4, BERSequenceGenerator var5, BERSequenceGenerator var6) {
         this._out = var2;
         DERObjectIdentifier var7 = new DERObjectIdentifier(var3);
         this._contentOID = var7;
         this._sGen = var4;
         this._sigGen = var5;
         this._eiGen = var6;
      }

      public void close() throws IOException {
         this._out.close();
         this._eiGen.close();
         CMSSignedDataStreamGenerator.this._digests.clear();
         if(CMSSignedDataStreamGenerator.this._certs.size() != 0) {
            ASN1Set var1 = CMSUtils.createBerSetFromList(CMSSignedDataStreamGenerator.this._certs);
            OutputStream var2 = this._sigGen.getRawOutputStream();
            byte[] var3 = (new BERTaggedObject((boolean)0, 0, var1)).getEncoded();
            var2.write(var3);
         }

         if(CMSSignedDataStreamGenerator.this._crls.size() != 0) {
            ASN1Set var4 = CMSUtils.createBerSetFromList(CMSSignedDataStreamGenerator.this._crls);
            OutputStream var5 = this._sigGen.getRawOutputStream();
            byte[] var6 = (new BERTaggedObject((boolean)0, 1, var4)).getEncoded();
            var5.write(var6);
         }

         ASN1EncodableVector var7 = new ASN1EncodableVector();
         Iterator var8 = CMSSignedDataStreamGenerator.this._signers.iterator();

         while(var8.hasNext()) {
            SignerInfo var9 = ((SignerInformation)var8.next()).toSignerInfo();
            var7.add(var9);
         }

         Iterator var10 = CMSSignedDataStreamGenerator.this._signerInfs.iterator();

         while(var10.hasNext()) {
            CMSSignedDataStreamGenerator.SignerInf var11 = (CMSSignedDataStreamGenerator.SignerInf)var10.next();

            try {
               DERObjectIdentifier var12 = this._contentOID;
               SignerInfo var13 = var11.toSignerInfo(var12);
               var7.add(var13);
            } catch (IOException var21) {
               throw new CMSStreamException("encoding error.", var21);
            } catch (InvalidKeyException var22) {
               throw new CMSStreamException("key inappropriate for signature.", var22);
            } catch (SignatureException var23) {
               throw new CMSStreamException("error creating signature.", var23);
            } catch (CertificateEncodingException var24) {
               throw new CMSStreamException("error creating sid.", var24);
            } catch (NoSuchAlgorithmException var25) {
               throw new CMSStreamException("unknown signature algorithm.", var25);
            }
         }

         OutputStream var19 = this._sigGen.getRawOutputStream();
         byte[] var20 = (new DERSet(var7)).getEncoded();
         var19.write(var20);
         this._sigGen.close();
         this._sGen.close();
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

   private class SignerInf {

      private final MessageDigest _digest;
      private final String _digestOID;
      private final String _encOID;
      private final PrivateKey _key;
      private final CMSAttributeTableGenerator _sAttr;
      private final Provider _sigProvider;
      private final SignerIdentifier _signerIdentifier;
      private final CMSAttributeTableGenerator _unsAttr;


      SignerInf(PrivateKey var2, SignerIdentifier var3, String var4, String var5, CMSAttributeTableGenerator var6, CMSAttributeTableGenerator var7, MessageDigest var8, Provider var9) {
         this._key = var2;
         this._signerIdentifier = var3;
         this._digestOID = var4;
         this._encOID = var5;
         this._sAttr = var6;
         this._unsAttr = var7;
         this._digest = var8;
         this._sigProvider = var9;
      }

      AlgorithmIdentifier getDigestAlgorithmID() {
         String var1 = this._digestOID;
         DERObjectIdentifier var2 = new DERObjectIdentifier(var1);
         DERNull var3 = DERNull.INSTANCE;
         return new AlgorithmIdentifier(var2, var3);
      }

      SignerInfo toSignerInfo(DERObjectIdentifier var1) throws IOException, SignatureException, CertificateEncodingException, InvalidKeyException, NoSuchAlgorithmException {
         CMSSignedHelper var2 = CMSSignedHelper.INSTANCE;
         String var3 = this._digestOID;
         String var4 = var2.getDigestAlgName(var3);
         CMSSignedHelper var5 = CMSSignedHelper.INSTANCE;
         String var6 = this._encOID;
         String var7 = var5.getEncryptionAlgName(var6);
         String var8 = var4 + "with" + var7;
         AlgorithmIdentifier var9 = this.getDigestAlgorithmID();
         byte[] var10 = this._digest.digest();
         Map var11 = CMSSignedDataStreamGenerator.this._digests;
         String var12 = this._digestOID;
         Object var13 = var10.clone();
         var11.put(var12, var13);
         byte[] var15 = var10;
         ASN1Set var16 = null;
         Signature var32;
         if(this._sAttr != null) {
            CMSSignedDataStreamGenerator var17 = CMSSignedDataStreamGenerator.this;
            Map var21 = var17.getBaseParameters(var1, var9, var10);
            CMSAttributeTableGenerator var22 = this._sAttr;
            Map var23 = Collections.unmodifiableMap(var21);
            AttributeTable var24 = var22.getAttributes(var23);
            CMSSignedDataStreamGenerator var25 = CMSSignedDataStreamGenerator.this;
            var16 = var25.getAttributeSet(var24);
            var15 = var16.getEncoded("DER");
            CMSSignedHelper var27 = CMSSignedHelper.INSTANCE;
            Provider var28 = this._sigProvider;
            var32 = var27.getSignatureInstance(var8, var28);
         } else if(var7.equals("RSA")) {
            var15 = (new DigestInfo(var9, var10)).getEncoded("DER");
            CMSSignedHelper var67 = CMSSignedHelper.INSTANCE;
            Provider var68 = this._sigProvider;
            var32 = var67.getSignatureInstance("RSA", var68);
         } else {
            if(!var7.equals("DSA")) {
               String var71 = "algorithm: " + var7 + " not supported in base signatures.";
               throw new SignatureException(var71);
            }

            CMSSignedHelper var69 = CMSSignedHelper.INSTANCE;
            Provider var70 = this._sigProvider;
            var32 = var69.getSignatureInstance("NONEwithDSA", var70);
         }

         PrivateKey var33 = this._key;
         SecureRandom var34 = CMSSignedDataStreamGenerator.this.rand;
         var32.initSign(var33, var34);
         var32.update(var15);
         byte[] var40 = var32.sign();
         ASN1Set var41 = null;
         if(this._unsAttr != null) {
            CMSSignedDataStreamGenerator var42 = CMSSignedDataStreamGenerator.this;
            Map var46 = var42.getBaseParameters(var1, var9, var10);
            Object var47 = var40.clone();
            String var49 = "encryptedDigest";
            var46.put(var49, var47);
            CMSAttributeTableGenerator var52 = this._unsAttr;
            Map var53 = Collections.unmodifiableMap(var46);
            AttributeTable var54 = var52.getAttributes(var53);
            CMSSignedDataStreamGenerator var55 = CMSSignedDataStreamGenerator.this;
            var41 = var55.getAttributeSet(var54);
         }

         CMSSignedDataStreamGenerator var57 = CMSSignedDataStreamGenerator.this;
         String var58 = this._encOID;
         AlgorithmIdentifier var62 = var57.getEncAlgorithmIdentifier(var58, var32);
         SignerIdentifier var63 = this._signerIdentifier;
         DEROctetString var64 = new DEROctetString(var40);
         return new SignerInfo(var63, var9, var16, var62, var64, var41);
      }
   }

   private static class NullOutputStream extends OutputStream {

      private NullOutputStream() {}

      // $FF: synthetic method
      NullOutputStream(CMSSignedDataStreamGenerator.1 var1) {
         this();
      }

      public void write(int var1) throws IOException {}

      public void write(byte[] var1) throws IOException {}

      public void write(byte[] var1, int var2, int var3) throws IOException {}
   }
}

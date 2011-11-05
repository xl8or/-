package myorg.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.CMSAttributes;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.cms.SignedData;
import myorg.bouncycastle.asn1.cms.SignerIdentifier;
import myorg.bouncycastle.asn1.cms.SignerInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSAttributeTableGenerator;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.cms.CMSProcessableByteArray;
import myorg.bouncycastle.cms.CMSSignedData;
import myorg.bouncycastle.cms.CMSSignedGenerator;
import myorg.bouncycastle.cms.CMSSignedHelper;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import myorg.bouncycastle.cms.SignerInformation;
import myorg.bouncycastle.cms.SignerInformationStore;
import myorg.bouncycastle.cms.SimpleAttributeTableGenerator;

public class CMSSignedDataGenerator extends CMSSignedGenerator {

   List signerInfs;


   public CMSSignedDataGenerator() {
      ArrayList var1 = new ArrayList();
      this.signerInfs = var1;
   }

   public CMSSignedDataGenerator(SecureRandom var1) {
      super(var1);
      ArrayList var2 = new ArrayList();
      this.signerInfs = var2;
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3) throws IllegalArgumentException {
      String var4 = this.getEncOID(var1, var3);
      this.addSigner(var1, var2, var4, var3);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4) throws IllegalArgumentException {
      List var5 = this.signerInfs;
      SignerIdentifier var6 = getSignerIdentifier(var2);
      DefaultSignedAttributeTableGenerator var7 = new DefaultSignedAttributeTableGenerator();
      Object var12 = null;
      CMSSignedDataGenerator.SignerInf var13 = new CMSSignedDataGenerator.SignerInf(var1, var6, var4, var3, var7, (CMSAttributeTableGenerator)null, (AttributeTable)var12);
      var5.add(var13);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, AttributeTable var5, AttributeTable var6) throws IllegalArgumentException {
      List var7 = this.signerInfs;
      SignerIdentifier var8 = getSignerIdentifier(var2);
      DefaultSignedAttributeTableGenerator var9 = new DefaultSignedAttributeTableGenerator(var5);
      SimpleAttributeTableGenerator var12 = new SimpleAttributeTableGenerator(var6);
      CMSSignedDataGenerator.SignerInf var20 = new CMSSignedDataGenerator.SignerInf(var1, var8, var4, var3, var9, var12, var5);
      var7.add(var20);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, CMSAttributeTableGenerator var5, CMSAttributeTableGenerator var6) throws IllegalArgumentException {
      List var7 = this.signerInfs;
      SignerIdentifier var8 = getSignerIdentifier(var2);
      CMSSignedDataGenerator.SignerInf var15 = new CMSSignedDataGenerator.SignerInf(var1, var8, var4, var3, var5, var6, (AttributeTable)null);
      var7.add(var15);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, AttributeTable var4, AttributeTable var5) throws IllegalArgumentException {
      String var6 = this.getEncOID(var1, var3);
      this.addSigner(var1, var2, var6, var3, var4, var5);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, CMSAttributeTableGenerator var4, CMSAttributeTableGenerator var5) throws IllegalArgumentException {
      String var6 = this.getEncOID(var1, var3);
      this.addSigner(var1, var2, var6, var3, var4, var5);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3) throws IllegalArgumentException {
      String var4 = this.getEncOID(var1, var3);
      this.addSigner(var1, var2, var4, var3);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, String var4) throws IllegalArgumentException {
      List var5 = this.signerInfs;
      SignerIdentifier var6 = getSignerIdentifier(var2);
      DefaultSignedAttributeTableGenerator var7 = new DefaultSignedAttributeTableGenerator();
      Object var12 = null;
      CMSSignedDataGenerator.SignerInf var13 = new CMSSignedDataGenerator.SignerInf(var1, var6, var4, var3, var7, (CMSAttributeTableGenerator)null, (AttributeTable)var12);
      var5.add(var13);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, String var4, AttributeTable var5, AttributeTable var6) throws IllegalArgumentException {
      List var7 = this.signerInfs;
      SignerIdentifier var8 = getSignerIdentifier(var2);
      DefaultSignedAttributeTableGenerator var9 = new DefaultSignedAttributeTableGenerator(var5);
      SimpleAttributeTableGenerator var12 = new SimpleAttributeTableGenerator(var6);
      CMSSignedDataGenerator.SignerInf var20 = new CMSSignedDataGenerator.SignerInf(var1, var8, var4, var3, var9, var12, var5);
      var7.add(var20);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, String var4, CMSAttributeTableGenerator var5, CMSAttributeTableGenerator var6) throws IllegalArgumentException {
      List var7 = this.signerInfs;
      SignerIdentifier var8 = getSignerIdentifier(var2);
      CMSSignedDataGenerator.SignerInf var15 = new CMSSignedDataGenerator.SignerInf(var1, var8, var4, var3, var5, var6, (AttributeTable)null);
      var7.add(var15);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, AttributeTable var4, AttributeTable var5) throws IllegalArgumentException {
      String var6 = this.getEncOID(var1, var3);
      DefaultSignedAttributeTableGenerator var7 = new DefaultSignedAttributeTableGenerator(var4);
      SimpleAttributeTableGenerator var8 = new SimpleAttributeTableGenerator(var5);
      this.addSigner(var1, var2, var3, var6, (CMSAttributeTableGenerator)var7, (CMSAttributeTableGenerator)var8);
   }

   public void addSigner(PrivateKey var1, byte[] var2, String var3, CMSAttributeTableGenerator var4, CMSAttributeTableGenerator var5) throws IllegalArgumentException {
      String var6 = this.getEncOID(var1, var3);
      this.addSigner(var1, var2, var3, var6, var4, var5);
   }

   public CMSSignedData generate(String var1, CMSProcessable var2, boolean var3, String var4) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var5 = CMSUtils.getProvider(var4);
      return this.generate(var1, var2, var3, var5, (boolean)1);
   }

   public CMSSignedData generate(String var1, CMSProcessable var2, boolean var3, String var4, boolean var5) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var6 = CMSUtils.getProvider(var4);
      return this.generate(var1, var2, var3, var6, var5);
   }

   public CMSSignedData generate(String var1, CMSProcessable var2, boolean var3, Provider var4) throws NoSuchAlgorithmException, CMSException {
      return this.generate(var1, var2, var3, var4, (boolean)1);
   }

   public CMSSignedData generate(String var1, CMSProcessable var2, boolean var3, Provider var4, boolean var5) throws NoSuchAlgorithmException, CMSException {
      ASN1EncodableVector var6 = new ASN1EncodableVector();
      ASN1EncodableVector var7 = new ASN1EncodableVector();
      this._digests.clear();
      Iterator var8 = this._signers.iterator();

      while(var8.hasNext()) {
         SignerInformation var9 = (SignerInformation)var8.next();
         CMSSignedHelper var10 = CMSSignedHelper.INSTANCE;
         AlgorithmIdentifier var11 = var9.getDigestAlgorithmID();
         AlgorithmIdentifier var12 = var10.fixAlgID(var11);
         var6.add(var12);
         SignerInfo var15 = var9.toSignerInfo();
         var7.add(var15);
      }

      byte var18;
      if(var1 == null) {
         var18 = 1;
      } else {
         var18 = 0;
      }

      DERObjectIdentifier var19;
      if(var18 != 0) {
         var19 = CMSObjectIdentifiers.data;
      } else {
         DERObjectIdentifier var37 = new DERObjectIdentifier(var1);
         var19 = var37;
      }

      Iterator var20 = this.signerInfs.iterator();

      while(var20.hasNext()) {
         CMSSignedDataGenerator.SignerInf var21 = (CMSSignedDataGenerator.SignerInf)var20.next();

         try {
            AlgorithmIdentifier var22 = var21.getDigestAlgorithmID();
            var6.add(var22);
            SecureRandom var25 = this.rand;
            SignerInfo var29 = var21.toSignerInfo(var19, var2, var25, var4, var5, (boolean)var18);
            var7.add(var29);
         } catch (IOException var90) {
            CMSException var33 = new CMSException;
            String var35 = "encoding error.";
            var33.<init>(var35, var90);
            throw var33;
         } catch (InvalidKeyException var91) {
            CMSException var41 = new CMSException;
            String var43 = "key inappropriate for signature.";
            var41.<init>(var43, var91);
            throw var41;
         } catch (SignatureException var92) {
            CMSException var46 = new CMSException;
            String var48 = "error creating signature.";
            var46.<init>(var48, var92);
            throw var46;
         } catch (CertificateEncodingException var93) {
            CMSException var51 = new CMSException;
            String var53 = "error creating sid.";
            var51.<init>(var53, var93);
            throw var51;
         }
      }

      ASN1Set var55 = null;
      if(this._certs.size() != 0) {
         var55 = CMSUtils.createBerSetFromList(this._certs);
      }

      ASN1Set var56 = null;
      if(this._crls.size() != 0) {
         var56 = CMSUtils.createBerSetFromList(this._crls);
      }

      BERConstructedOctetString var57 = null;
      if(var3) {
         ByteArrayOutputStream var58 = new ByteArrayOutputStream();
         if(var2 != null) {
            try {
               var2.write(var58);
            } catch (IOException var89) {
               CMSException var85 = new CMSException;
               String var87 = "encapsulation error.";
               var85.<init>(var87, var89);
               throw var85;
            }
         }

         var57 = new BERConstructedOctetString;
         byte[] var61 = var58.toByteArray();
         var57.<init>(var61);
      }

      ContentInfo var64 = new ContentInfo(var19, var57);
      DERSet var68 = new DERSet(var6);
      DERSet var71 = new DERSet(var7);
      SignedData var74 = new SignedData(var68, var64, var55, var56, var71);
      ContentInfo var75 = new ContentInfo;
      DERObjectIdentifier var76 = CMSObjectIdentifiers.signedData;
      var75.<init>(var76, var74);
      CMSSignedData var80 = new CMSSignedData(var2, var75);
      return var80;
   }

   public CMSSignedData generate(CMSProcessable var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.generate(var1, var3);
   }

   public CMSSignedData generate(CMSProcessable var1, Provider var2) throws NoSuchAlgorithmException, CMSException {
      return this.generate(var1, (boolean)0, var2);
   }

   public CMSSignedData generate(CMSProcessable var1, boolean var2, String var3) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      String var4 = DATA;
      return this.generate(var4, var1, var2, var3);
   }

   public CMSSignedData generate(CMSProcessable var1, boolean var2, Provider var3) throws NoSuchAlgorithmException, CMSException {
      String var4 = DATA;
      return this.generate(var4, var1, var2, var3);
   }

   public SignerInformationStore generateCounterSigners(SignerInformation var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      byte[] var3 = var1.getSignature();
      CMSProcessableByteArray var4 = new CMSProcessableByteArray(var3);
      Provider var5 = CMSUtils.getProvider(var2);
      return this.generate((String)null, var4, (boolean)0, var5).getSignerInfos();
   }

   public SignerInformationStore generateCounterSigners(SignerInformation var1, Provider var2) throws NoSuchAlgorithmException, CMSException {
      byte[] var3 = var1.getSignature();
      CMSProcessableByteArray var4 = new CMSProcessableByteArray(var3);
      return this.generate((String)null, var4, (boolean)0, var2).getSignerInfos();
   }

   private class SignerInf {

      private final AttributeTable baseSignedTable;
      private final String digestOID;
      private final String encOID;
      private final PrivateKey key;
      private final CMSAttributeTableGenerator sAttr;
      private final SignerIdentifier signerIdentifier;
      private final CMSAttributeTableGenerator unsAttr;


      SignerInf(PrivateKey var2, SignerIdentifier var3, String var4, String var5, CMSAttributeTableGenerator var6, CMSAttributeTableGenerator var7, AttributeTable var8) {
         this.key = var2;
         this.signerIdentifier = var3;
         this.digestOID = var4;
         this.encOID = var5;
         this.sAttr = var6;
         this.unsAttr = var7;
         this.baseSignedTable = var8;
      }

      AlgorithmIdentifier getDigestAlgorithmID() {
         String var1 = this.digestOID;
         DERObjectIdentifier var2 = new DERObjectIdentifier(var1);
         DERNull var3 = new DERNull();
         return new AlgorithmIdentifier(var2, var3);
      }

      SignerInfo toSignerInfo(DERObjectIdentifier var1, CMSProcessable var2, SecureRandom var3, Provider var4, boolean var5, boolean var6) throws IOException, SignatureException, InvalidKeyException, NoSuchAlgorithmException, CertificateEncodingException, CMSException {
         AlgorithmIdentifier var7 = this.getDigestAlgorithmID();
         CMSSignedHelper var8 = CMSSignedHelper.INSTANCE;
         String var9 = this.digestOID;
         String var10 = var8.getDigestAlgName(var9);
         StringBuilder var11 = (new StringBuilder()).append(var10).append("with");
         CMSSignedHelper var12 = CMSSignedHelper.INSTANCE;
         String var13 = this.encOID;
         String var14 = var12.getEncryptionAlgName(var13);
         String var15 = var11.append(var14).toString();
         CMSSignedHelper var16 = CMSSignedHelper.INSTANCE;
         Signature var19 = var16.getSignatureInstance(var15, var4);
         CMSSignedHelper var20 = CMSSignedHelper.INSTANCE;
         MessageDigest var23 = var20.getDigestInstance(var10, var4);
         CMSSignedDataGenerator var24 = CMSSignedDataGenerator.this;
         String var25 = this.encOID;
         AlgorithmIdentifier var29 = var24.getEncAlgorithmIdentifier(var25, var19);
         if(var2 != null) {
            CMSSignedGenerator.DigOutputStream var30 = new CMSSignedGenerator.DigOutputStream(var23);
            var2.write(var30);
         }

         byte[] var33 = var23.digest();
         Map var34 = CMSSignedDataGenerator.this._digests;
         String var35 = this.digestOID;
         Object var36 = var33.clone();
         var34.put(var35, var36);
         AttributeTable var45;
         if(var5) {
            CMSSignedDataGenerator var38 = CMSSignedDataGenerator.this;
            Map var42 = var38.getBaseParameters(var1, var7, var33);
            if(this.sAttr != null) {
               CMSAttributeTableGenerator var43 = this.sAttr;
               Map var44 = Collections.unmodifiableMap(var42);
               var45 = var43.getAttributes(var44);
            } else {
               var45 = null;
            }
         } else {
            var45 = this.baseSignedTable;
         }

         ASN1Set var46 = null;
         byte[] var56;
         if(var45 != null) {
            if(var6) {
               Hashtable var47 = var45.toHashtable();
               DERObjectIdentifier var48 = CMSAttributes.contentType;
               Object var51 = var47.remove(var48);
               var45 = new AttributeTable(var47);
            }

            CMSSignedDataGenerator var54 = CMSSignedDataGenerator.this;
            var46 = var54.getAttributeSet(var45);
            var56 = var46.getEncoded("DER");
         } else {
            ByteArrayOutputStream var81 = new ByteArrayOutputStream();
            if(var2 != null) {
               var2.write(var81);
            }

            var56 = var81.toByteArray();
         }

         PrivateKey var57 = this.key;
         var19.initSign(var57, var3);
         var19.update(var56);
         byte[] var63 = var19.sign();
         ASN1Set var64 = null;
         if(this.unsAttr != null) {
            CMSSignedDataGenerator var65 = CMSSignedDataGenerator.this;
            Map var69 = var65.getBaseParameters(var1, var7, var33);
            Object var70 = var63.clone();
            var69.put("encryptedDigest", var70);
            CMSAttributeTableGenerator var72 = this.unsAttr;
            Map var73 = Collections.unmodifiableMap(var69);
            AttributeTable var74 = var72.getAttributes(var73);
            CMSSignedDataGenerator var75 = CMSSignedDataGenerator.this;
            var64 = var75.getAttributeSet(var74);
         }

         SignerIdentifier var77 = this.signerIdentifier;
         DEROctetString var78 = new DEROctetString(var63);
         return new SignerInfo(var77, var7, var46, var29, var78, var64);
      }
   }
}

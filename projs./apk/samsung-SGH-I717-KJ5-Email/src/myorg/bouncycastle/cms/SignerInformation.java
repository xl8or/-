package myorg.bouncycastle.cms;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import javax.crypto.Cipher;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Null;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.Attribute;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.CMSAttributes;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.SignerIdentifier;
import myorg.bouncycastle.asn1.cms.SignerInfo;
import myorg.bouncycastle.asn1.cms.Time;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DigestInfo;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.cms.CMSSignedGenerator;
import myorg.bouncycastle.cms.CMSSignedHelper;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.CounterSignatureDigestCalculator;
import myorg.bouncycastle.cms.DigestCalculator;
import myorg.bouncycastle.cms.SignerId;
import myorg.bouncycastle.cms.SignerInformationStore;
import myorg.bouncycastle.util.Arrays;

public class SignerInformation {

   private CMSProcessable content;
   private DERObjectIdentifier contentType;
   private AlgorithmIdentifier digestAlgorithm;
   private DigestCalculator digestCalculator;
   private AlgorithmIdentifier encryptionAlgorithm;
   private SignerInfo info;
   private byte[] resultDigest;
   private SignerId sid;
   private byte[] signature;
   private final ASN1Set signedAttributeSet;
   private AttributeTable signedAttributeValues;
   private final ASN1Set unsignedAttributeSet;
   private AttributeTable unsignedAttributeValues;


   SignerInformation(SignerInfo var1, DERObjectIdentifier var2, CMSProcessable var3, DigestCalculator var4) {
      this.info = var1;
      SignerId var5 = new SignerId();
      this.sid = var5;
      this.contentType = var2;

      try {
         SignerIdentifier var6 = var1.getSID();
         if(var6.isTagged()) {
            ASN1OctetString var7 = ASN1OctetString.getInstance(var6.getId());
            SignerId var8 = this.sid;
            byte[] var9 = var7.getEncoded();
            var8.setSubjectKeyIdentifier(var9);
         } else {
            IssuerAndSerialNumber var15 = IssuerAndSerialNumber.getInstance(var6.getId());
            SignerId var16 = this.sid;
            byte[] var17 = var15.getName().getEncoded();
            var16.setIssuer(var17);
            SignerId var18 = this.sid;
            BigInteger var19 = var15.getSerialNumber().getValue();
            var18.setSerialNumber(var19);
         }
      } catch (IOException var21) {
         throw new IllegalArgumentException("invalid sid in SignerInfo");
      }

      AlgorithmIdentifier var10 = var1.getDigestAlgorithm();
      this.digestAlgorithm = var10;
      ASN1Set var11 = var1.getAuthenticatedAttributes();
      this.signedAttributeSet = var11;
      ASN1Set var12 = var1.getUnauthenticatedAttributes();
      this.unsignedAttributeSet = var12;
      AlgorithmIdentifier var13 = var1.getDigestEncryptionAlgorithm();
      this.encryptionAlgorithm = var13;
      byte[] var14 = var1.getEncryptedDigest().getOctets();
      this.signature = var14;
      this.content = var3;
      this.digestCalculator = var4;
   }

   public static SignerInformation addCounterSigners(SignerInformation var0, SignerInformationStore var1) {
      SignerInfo var2 = var0.info;
      AttributeTable var3 = var0.getUnsignedAttributes();
      ASN1EncodableVector var4;
      if(var3 != null) {
         var4 = var3.toASN1EncodableVector();
      } else {
         var4 = new ASN1EncodableVector();
      }

      ASN1EncodableVector var5 = new ASN1EncodableVector();
      Iterator var6 = var1.getSigners().iterator();

      while(var6.hasNext()) {
         SignerInfo var7 = ((SignerInformation)var6.next()).toSignerInfo();
         var5.add(var7);
      }

      DERObjectIdentifier var8 = CMSAttributes.counterSignature;
      DERSet var9 = new DERSet(var5);
      Attribute var10 = new Attribute(var8, var9);
      var4.add(var10);
      SignerIdentifier var11 = var2.getSID();
      AlgorithmIdentifier var12 = var2.getDigestAlgorithm();
      ASN1Set var13 = var2.getAuthenticatedAttributes();
      AlgorithmIdentifier var14 = var2.getDigestEncryptionAlgorithm();
      ASN1OctetString var15 = var2.getEncryptedDigest();
      DERSet var16 = new DERSet(var4);
      SignerInfo var17 = new SignerInfo(var11, var12, var13, var14, var15, var16);
      DERObjectIdentifier var18 = var0.contentType;
      CMSProcessable var19 = var0.content;
      return new SignerInformation(var17, var18, var19, (DigestCalculator)null);
   }

   private DigestInfo derDecode(byte[] var1) throws IOException, CMSException {
      if(var1[0] != 48) {
         throw new IOException("not a digest info object");
      } else {
         ASN1Sequence var2 = (ASN1Sequence)(new ASN1InputStream(var1)).readObject();
         DigestInfo var3 = new DigestInfo(var2);
         int var4 = var3.getEncoded().length;
         int var5 = var1.length;
         if(var4 != var5) {
            throw new CMSException("malformed RSA signature");
         } else {
            return var3;
         }
      }
   }

   private boolean doVerify(PublicKey var1, Provider var2) throws CMSException, NoSuchAlgorithmException {
      CMSSignedHelper var3 = CMSSignedHelper.INSTANCE;
      String var4 = this.getDigestAlgOID();
      String var5 = var3.getDigestAlgName(var4);
      StringBuilder var6 = new StringBuilder();
      StringBuilder var8 = var6.append(var5).append("with");
      CMSSignedHelper var9 = CMSSignedHelper.INSTANCE;
      String var10 = this.getEncryptionAlgOID();
      String var11 = var9.getEncryptionAlgName(var10);
      String var12 = var8.append(var11).toString();
      CMSSignedHelper var13 = CMSSignedHelper.INSTANCE;
      Signature var16 = var13.getSignatureInstance(var12, var2);
      CMSSignedHelper var17 = CMSSignedHelper.INSTANCE;
      MessageDigest var20 = var17.getDigestInstance(var5, var2);

      try {
         if(this.digestCalculator != null) {
            byte[] var21 = this.digestCalculator.getDigest();
            this.resultDigest = var21;
         } else {
            if(this.content != null) {
               CMSProcessable var30 = this.content;
               CMSSignedGenerator.DigOutputStream var31 = new CMSSignedGenerator.DigOutputStream(var20);
               var30.write(var31);
            } else if(this.signedAttributeSet == null) {
               throw new CMSException("data not encapsulated in signature - use detached constructor.");
            }

            byte[] var34 = var20.digest();
            this.resultDigest = var34;
         }
      } catch (IOException var105) {
         CMSException var36 = new CMSException;
         String var38 = "can\'t process mime object to create signature.";
         var36.<init>(var38, var105);
         throw var36;
      }

      DERObjectIdentifier var22 = this.contentType;
      DERObjectIdentifier var23 = CMSAttributes.counterSignature;
      boolean var24 = var22.equals(var23);
      DERObjectIdentifier var25 = CMSAttributes.contentType;
      String var28 = "content-type";
      DERObject var29 = this.getSingleValuedSignedAttribute(var25, var28);
      if(var29 == null) {
         if(!var24 && this.signedAttributeSet != null) {
            throw new CMSException("The content-type attribute type MUST be present whenever signed attributes are present in signed-data");
         }
      } else {
         if(var24) {
            throw new CMSException("[For counter signatures,] the signedAttributes field MUST NOT contain a content-type attribute");
         }

         if(!(var29 instanceof DERObjectIdentifier)) {
            throw new CMSException("content-type attribute value not of ASN.1 type \'OBJECT IDENTIFIER\'");
         }

         DERObjectIdentifier var40 = (DERObjectIdentifier)var29;
         DERObjectIdentifier var41 = this.contentType;
         if(!var40.equals(var41)) {
            throw new CMSException("content-type attribute value does not match eContentType");
         }
      }

      DERObjectIdentifier var44 = CMSAttributes.messageDigest;
      String var47 = "message-digest";
      DERObject var48 = this.getSingleValuedSignedAttribute(var44, var47);
      if(var48 == null) {
         if(this.signedAttributeSet != null) {
            throw new CMSException("the message-digest signed attribute type MUST be present when there are any signed attributes present");
         }
      } else {
         if(!(var48 instanceof ASN1OctetString)) {
            throw new CMSException("message-digest attribute value not of ASN.1 type \'OCTET STRING\'");
         }

         ASN1OctetString var49 = (ASN1OctetString)var48;
         byte[] var50 = this.resultDigest;
         byte[] var51 = var49.getOctets();
         if(!Arrays.constantTimeAreEqual(var50, var51)) {
            throw new CMSException("message-digest attribute value does not match calculated value");
         }
      }

      AttributeTable var52 = this.getSignedAttributes();
      if(var52 != null) {
         DERObjectIdentifier var53 = CMSAttributes.counterSignature;
         if(var52.getAll(var53).size() > 0) {
            throw new CMSException("A countersignature attribute MUST NOT be a signed attribute");
         }
      }

      AttributeTable var56 = this.getUnsignedAttributes();
      if(var56 != null) {
         DERObjectIdentifier var57 = CMSAttributes.counterSignature;
         ASN1EncodableVector var60 = var56.getAll(var57);
         int var61 = 0;

         while(true) {
            int var62 = var60.size();
            if(var61 >= var62) {
               break;
            }

            int var65 = ((Attribute)var60.get(var61)).getAttrValues().size();
            byte var66 = 1;
            if(var65 < var66) {
               throw new CMSException("A countersignature attribute MUST contain at least one AttributeValue");
            }

            ++var61;
         }
      }

      try {
         var16.initVerify(var1);
         boolean var76;
         if(this.signedAttributeSet == null) {
            if(this.digestCalculator != null) {
               byte[] var69 = this.resultDigest;
               byte[] var70 = this.getSignature();
               var76 = this.verifyDigest(var69, var1, var70, var2);
               return var76;
            }

            if(this.content != null) {
               CMSProcessable var77 = this.content;
               CMSSignedGenerator.SigOutputStream var78 = new CMSSignedGenerator.SigOutputStream(var16);
               var77.write(var78);
            }
         } else {
            byte[] var84 = this.getEncodedSignedAttributes();
            var16.update(var84);
         }

         byte[] var81 = this.getSignature();
         var76 = var16.verify(var81);
         return var76;
      } catch (InvalidKeyException var106) {
         CMSException var88 = new CMSException;
         String var90 = "key not appropriate to signature in message.";
         var88.<init>(var90, var106);
         throw var88;
      } catch (IOException var107) {
         CMSException var93 = new CMSException;
         String var95 = "can\'t process mime object to create signature.";
         var93.<init>(var95, var107);
         throw var93;
      } catch (SignatureException var108) {
         CMSException var98 = new CMSException;
         StringBuilder var99 = (new StringBuilder()).append("invalid signature format in message: ");
         String var100 = var108.getMessage();
         String var101 = var99.append(var100).toString();
         var98.<init>(var101, var108);
         throw var98;
      }
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

   private Time getSigningTime() throws CMSException {
      DERObjectIdentifier var1 = CMSAttributes.signingTime;
      DERObject var2 = this.getSingleValuedSignedAttribute(var1, "signing-time");
      Time var3;
      if(var2 == null) {
         var3 = null;
      } else {
         Time var4;
         try {
            var4 = Time.getInstance(var2);
         } catch (IllegalArgumentException var6) {
            throw new CMSException("signing-time attribute value not a valid \'Time\' structure");
         }

         var3 = var4;
      }

      return var3;
   }

   private DERObject getSingleValuedSignedAttribute(DERObjectIdentifier var1, String var2) throws CMSException {
      AttributeTable var3 = this.getUnsignedAttributes();
      if(var3 != null && var3.getAll(var1).size() > 0) {
         String var4 = "The " + var2 + " attribute MUST NOT be an unsigned attribute";
         throw new CMSException(var4);
      } else {
         AttributeTable var5 = this.getSignedAttributes();
         DERObject var6;
         if(var5 == null) {
            var6 = null;
         } else {
            ASN1EncodableVector var7 = var5.getAll(var1);
            switch(var7.size()) {
            case 0:
               var6 = null;
               break;
            case 1:
               ASN1Set var9 = ((Attribute)var7.get(0)).getAttrValues();
               if(var9.size() != 1) {
                  String var10 = "A " + var2 + " attribute MUST have a single attribute value";
                  throw new CMSException(var10);
               }

               var6 = var9.getObjectAt(0).getDERObject();
               break;
            default:
               String var8 = "The SignedAttributes in a signerInfo MUST NOT include multiple instances of the " + var2 + " attribute";
               throw new CMSException(var8);
            }
         }

         return var6;
      }
   }

   private boolean isNull(DEREncodable var1) {
      boolean var2;
      if(!(var1 instanceof ASN1Null) && var1 != null) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static SignerInformation replaceUnsignedAttributes(SignerInformation var0, AttributeTable var1) {
      SignerInfo var2 = var0.info;
      DERSet var3 = null;
      if(var1 != null) {
         ASN1EncodableVector var4 = var1.toASN1EncodableVector();
         var3 = new DERSet(var4);
      }

      SignerIdentifier var5 = var2.getSID();
      AlgorithmIdentifier var6 = var2.getDigestAlgorithm();
      ASN1Set var7 = var2.getAuthenticatedAttributes();
      AlgorithmIdentifier var8 = var2.getDigestEncryptionAlgorithm();
      ASN1OctetString var9 = var2.getEncryptedDigest();
      SignerInfo var10 = new SignerInfo(var5, var6, var7, var8, var9, var3);
      DERObjectIdentifier var11 = var0.contentType;
      CMSProcessable var12 = var0.content;
      return new SignerInformation(var10, var11, var12, (DigestCalculator)null);
   }

   private boolean verifyDigest(byte[] var1, PublicKey var2, byte[] var3, Provider var4) throws NoSuchAlgorithmException, CMSException {
      CMSSignedHelper var5 = CMSSignedHelper.INSTANCE;
      String var6 = this.getEncryptionAlgOID();
      String var7 = var5.getEncryptionAlgName(var6);

      try {
         byte var13;
         if(var7.equals("RSA")) {
            Cipher var8 = CMSEnvelopedHelper.INSTANCE.getCipherInstance("RSA/ECB/PKCS1Padding", var4);
            var8.init(2, var2);
            byte[] var9 = var8.doFinal(var3);
            DigestInfo var10 = this.derDecode(var9);
            DERObjectIdentifier var11 = var10.getAlgorithmId().getObjectId();
            DERObjectIdentifier var12 = this.digestAlgorithm.getObjectId();
            if(!var11.equals(var12)) {
               var13 = 0;
            } else {
               DEREncodable var14 = var10.getAlgorithmId().getParameters();
               if(!this.isNull(var14)) {
                  var13 = 0;
               } else {
                  byte[] var15 = var10.getDigest();
                  var13 = Arrays.constantTimeAreEqual(var1, var15);
               }
            }
         } else {
            if(!var7.equals("DSA")) {
               String var17 = "algorithm: " + var7 + " not supported in base signatures.";
               throw new CMSException(var17);
            }

            Signature var16 = CMSSignedHelper.INSTANCE.getSignatureInstance("NONEwithDSA", var4);
            var16.initVerify(var2);
            var16.update(var1);
            var13 = var16.verify(var3);
         }

         return (boolean)var13;
      } catch (GeneralSecurityException var22) {
         String var19 = "Exception processing signature: " + var22;
         throw new CMSException(var19, var22);
      } catch (IOException var23) {
         String var21 = "Exception decoding signature: " + var23;
         throw new CMSException(var21, var23);
      }
   }

   public byte[] getContentDigest() {
      if(this.resultDigest == null) {
         throw new IllegalStateException("method can only be called after verify.");
      } else {
         return (byte[])((byte[])this.resultDigest.clone());
      }
   }

   public SignerInformationStore getCounterSignatures() {
      AttributeTable var1 = this.getUnsignedAttributes();
      SignerInformationStore var3;
      if(var1 == null) {
         ArrayList var2 = new ArrayList(0);
         var3 = new SignerInformationStore(var2);
      } else {
         ArrayList var4 = new ArrayList();
         DERObjectIdentifier var5 = CMSAttributes.counterSignature;
         ASN1EncodableVector var6 = var1.getAll(var5);
         int var7 = 0;

         while(true) {
            int var8 = var6.size();
            if(var7 >= var8) {
               var3 = new SignerInformationStore(var4);
               break;
            }

            ASN1Set var9 = ((Attribute)var6.get(var7)).getAttrValues();
            if(var9.size() < 1) {
               ;
            }

            Enumeration var10 = var9.getObjects();

            while(var10.hasMoreElements()) {
               SignerInfo var11 = SignerInfo.getInstance(var10.nextElement());
               CMSSignedHelper var12 = CMSSignedHelper.INSTANCE;
               String var13 = var11.getDigestAlgorithm().getObjectId().getId();
               String var14 = var12.getDigestAlgName(var13);
               DERObjectIdentifier var15 = CMSAttributes.counterSignature;
               byte[] var16 = this.getSignature();
               CounterSignatureDigestCalculator var17 = new CounterSignatureDigestCalculator(var14, (Provider)null, var16);
               SignerInformation var18 = new SignerInformation(var11, var15, (CMSProcessable)null, var17);
               var4.add(var18);
            }

            ++var7;
         }
      }

      return var3;
   }

   public String getDigestAlgOID() {
      return this.digestAlgorithm.getObjectId().getId();
   }

   public byte[] getDigestAlgParams() {
      try {
         DEREncodable var1 = this.digestAlgorithm.getParameters();
         byte[] var2 = this.encodeObj(var1);
         return var2;
      } catch (Exception var5) {
         String var4 = "exception getting digest parameters " + var5;
         throw new RuntimeException(var4);
      }
   }

   public AlgorithmIdentifier getDigestAlgorithmID() {
      return this.digestAlgorithm;
   }

   public byte[] getEncodedSignedAttributes() throws IOException {
      byte[] var1;
      if(this.signedAttributeSet != null) {
         var1 = this.signedAttributeSet.getEncoded("DER");
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getEncryptionAlgOID() {
      return this.encryptionAlgorithm.getObjectId().getId();
   }

   public byte[] getEncryptionAlgParams() {
      try {
         DEREncodable var1 = this.encryptionAlgorithm.getParameters();
         byte[] var2 = this.encodeObj(var1);
         return var2;
      } catch (Exception var5) {
         String var4 = "exception getting encryption parameters " + var5;
         throw new RuntimeException(var4);
      }
   }

   public SignerId getSID() {
      return this.sid;
   }

   public byte[] getSignature() {
      return (byte[])((byte[])this.signature.clone());
   }

   public AttributeTable getSignedAttributes() {
      if(this.signedAttributeSet != null && this.signedAttributeValues == null) {
         ASN1Set var1 = this.signedAttributeSet;
         AttributeTable var2 = new AttributeTable(var1);
         this.signedAttributeValues = var2;
      }

      return this.signedAttributeValues;
   }

   public AttributeTable getUnsignedAttributes() {
      if(this.unsignedAttributeSet != null && this.unsignedAttributeValues == null) {
         ASN1Set var1 = this.unsignedAttributeSet;
         AttributeTable var2 = new AttributeTable(var1);
         this.unsignedAttributeValues = var2;
      }

      return this.unsignedAttributeValues;
   }

   public int getVersion() {
      return this.info.getVersion().getValue().intValue();
   }

   public SignerInfo toSignerInfo() {
      return this.info;
   }

   public boolean verify(PublicKey var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.verify(var1, var3);
   }

   public boolean verify(PublicKey var1, Provider var2) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
      Time var3 = this.getSigningTime();
      return this.doVerify(var1, var2);
   }

   public boolean verify(X509Certificate var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, CertificateExpiredException, CertificateNotYetValidException, CMSException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.verify(var1, var3);
   }

   public boolean verify(X509Certificate var1, Provider var2) throws NoSuchAlgorithmException, CertificateExpiredException, CertificateNotYetValidException, CMSException {
      Time var3 = this.getSigningTime();
      if(var3 != null) {
         Date var4 = var3.getDate();
         var1.checkValidity(var4);
      }

      PublicKey var5 = var1.getPublicKey();
      return this.doVerify(var5, var2);
   }
}

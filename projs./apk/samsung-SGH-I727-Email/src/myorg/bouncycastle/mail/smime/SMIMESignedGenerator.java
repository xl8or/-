package myorg.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.MailcapCommandMap;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSSignedDataStreamGenerator;
import myorg.bouncycastle.cms.SignerInformation;
import myorg.bouncycastle.cms.SignerInformationStore;
import myorg.bouncycastle.mail.smime.SMIMEException;
import myorg.bouncycastle.mail.smime.SMIMEGenerator;
import myorg.bouncycastle.mail.smime.SMIMEStreamingProcessor;
import myorg.bouncycastle.mail.smime.SMIMEUtil;
import myorg.bouncycastle.mail.smime.util.CRLFOutputStream;
import myorg.bouncycastle.x509.X509Store;

public class SMIMESignedGenerator extends SMIMEGenerator {

   private static final String CERTIFICATE_MANAGEMENT_CONTENT = "application/pkcs7-mime; name=smime.p7c; smime-type=certs-only";
   private static final String DETACHED_SIGNATURE_TYPE = "application/pkcs7-signature; name=smime.p7s; smime-type=signed-data";
   public static final String DIGEST_GOST3411 = CryptoProObjectIdentifiers.gostR3411.getId();
   public static final String DIGEST_MD5 = PKCSObjectIdentifiers.md5.getId();
   public static final String DIGEST_RIPEMD128 = TeleTrusTObjectIdentifiers.ripemd128.getId();
   public static final String DIGEST_RIPEMD160 = TeleTrusTObjectIdentifiers.ripemd160.getId();
   public static final String DIGEST_RIPEMD256 = TeleTrusTObjectIdentifiers.ripemd256.getId();
   public static final String DIGEST_SHA1 = OIWObjectIdentifiers.idSHA1.getId();
   public static final String DIGEST_SHA224 = NISTObjectIdentifiers.id_sha224.getId();
   public static final String DIGEST_SHA256 = NISTObjectIdentifiers.id_sha256.getId();
   public static final String DIGEST_SHA384 = NISTObjectIdentifiers.id_sha384.getId();
   public static final String DIGEST_SHA512 = NISTObjectIdentifiers.id_sha512.getId();
   private static final String ENCAPSULATED_SIGNED_CONTENT_TYPE = "application/pkcs7-mime; name=smime.p7m; smime-type=signed-data";
   public static final String ENCRYPTION_DSA = X9ObjectIdentifiers.id_dsa_with_sha1.getId();
   public static final String ENCRYPTION_ECDSA = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
   public static final String ENCRYPTION_ECGOST3410 = CryptoProObjectIdentifiers.gostR3410_2001.getId();
   public static final String ENCRYPTION_GOST3410 = CryptoProObjectIdentifiers.gostR3410_94.getId();
   public static final String ENCRYPTION_RSA = PKCSObjectIdentifiers.rsaEncryption.getId();
   public static final String ENCRYPTION_RSA_PSS = PKCSObjectIdentifiers.id_RSASSA_PSS.getId();
   private List _attributeCerts;
   private List _certStores;
   private final String _defaultContentTransferEncoding;
   private Map _digests;
   private List _oldSigners;
   private List _signers;


   static {
      CommandMap.setDefaultCommandMap(addCommands(CommandMap.getDefaultCommandMap()));
   }

   public SMIMESignedGenerator() {
      ArrayList var1 = new ArrayList();
      this._certStores = var1;
      ArrayList var2 = new ArrayList();
      this._signers = var2;
      ArrayList var3 = new ArrayList();
      this._oldSigners = var3;
      ArrayList var4 = new ArrayList();
      this._attributeCerts = var4;
      HashMap var5 = new HashMap();
      this._digests = var5;
      this._defaultContentTransferEncoding = "7bit";
   }

   public SMIMESignedGenerator(String var1) {
      ArrayList var2 = new ArrayList();
      this._certStores = var2;
      ArrayList var3 = new ArrayList();
      this._signers = var3;
      ArrayList var4 = new ArrayList();
      this._oldSigners = var4;
      ArrayList var5 = new ArrayList();
      this._attributeCerts = var5;
      HashMap var6 = new HashMap();
      this._digests = var6;
      this._defaultContentTransferEncoding = var1;
   }

   private static MailcapCommandMap addCommands(CommandMap var0) {
      MailcapCommandMap var1 = (MailcapCommandMap)var0;
      var1.addMailcap("application/pkcs7-signature;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.pkcs7_signature");
      var1.addMailcap("application/pkcs7-mime;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.pkcs7_mime");
      var1.addMailcap("application/x-pkcs7-signature;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.x_pkcs7_signature");
      var1.addMailcap("application/x-pkcs7-mime;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
      var1.addMailcap("multipart/signed;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.multipart_signed");
      return var1;
   }

   private void addHashHeader(StringBuffer var1, List var2) {
      int var3 = 0;
      Iterator var4 = var2.iterator();
      HashSet var5 = new HashSet();

      while(var4.hasNext()) {
         Object var6 = var4.next();
         String var7;
         if(var6 instanceof SMIMESignedGenerator.Signer) {
            var7 = ((SMIMESignedGenerator.Signer)var6).getDigestOID();
         } else {
            var7 = ((SignerInformation)var6).getDigestAlgOID();
         }

         String var8 = DIGEST_SHA1;
         if(var7.equals(var8)) {
            boolean var9 = var5.add("sha1");
         } else {
            String var10 = DIGEST_MD5;
            if(var7.equals(var10)) {
               boolean var11 = var5.add("md5");
            } else {
               String var12 = DIGEST_SHA224;
               if(var7.equals(var12)) {
                  boolean var13 = var5.add("sha224");
               } else {
                  String var14 = DIGEST_SHA256;
                  if(var7.equals(var14)) {
                     boolean var15 = var5.add("sha256");
                  } else {
                     String var16 = DIGEST_SHA384;
                     if(var7.equals(var16)) {
                        boolean var17 = var5.add("sha384");
                     } else {
                        String var18 = DIGEST_SHA512;
                        if(var7.equals(var18)) {
                           boolean var19 = var5.add("sha512");
                        } else {
                           String var20 = DIGEST_GOST3411;
                           if(var7.equals(var20)) {
                              boolean var21 = var5.add("gostr3411-94");
                           } else {
                              boolean var22 = var5.add("unknown");
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      for(Iterator var23 = var5.iterator(); var23.hasNext(); ++var3) {
         String var24 = (String)var23.next();
         if(var3 == 0) {
            if(var5.size() != 1) {
               StringBuffer var25 = var1.append("; micalg=\"");
            } else {
               StringBuffer var27 = var1.append("; micalg=");
            }
         } else {
            StringBuffer var28 = var1.append(',');
         }

         var1.append(var24);
      }

      if(var3 != 0) {
         if(var5.size() != 1) {
            StringBuffer var29 = var1.append('\"');
         }
      }
   }

   private MimeMultipart make(MimeBodyPart var1, Provider var2) throws NoSuchAlgorithmException, SMIMEException {
      try {
         MimeBodyPart var3 = new MimeBodyPart();
         SMIMESignedGenerator.ContentSigner var4 = new SMIMESignedGenerator.ContentSigner(var1, (boolean)0, var2);
         var3.setContent(var4, "application/pkcs7-signature; name=smime.p7s; smime-type=signed-data");
         var3.addHeader("Content-Type", "application/pkcs7-signature; name=smime.p7s; smime-type=signed-data");
         var3.addHeader("Content-Disposition", "attachment; filename=\"smime.p7s\"");
         var3.addHeader("Content-Description", "S/MIME Cryptographic Signature");
         String var5 = this.encoding;
         var3.addHeader("Content-Transfer-Encoding", var5);
         StringBuffer var6 = new StringBuffer("signed; protocol=\"application/pkcs7-signature\"");
         List var7 = this._signers;
         ArrayList var8 = new ArrayList(var7);
         List var9 = this._oldSigners;
         var8.addAll(var9);
         this.addHashHeader(var6, var8);
         String var11 = var6.toString();
         MimeMultipart var12 = new MimeMultipart(var11);
         var12.addBodyPart(var1);
         var12.addBodyPart(var3);
         return var12;
      } catch (MessagingException var14) {
         throw new SMIMEException("exception putting multi-part together.", var14);
      }
   }

   private MimeBodyPart makeEncapsulated(MimeBodyPart var1, Provider var2) throws NoSuchAlgorithmException, SMIMEException {
      try {
         MimeBodyPart var3 = new MimeBodyPart();
         SMIMESignedGenerator.ContentSigner var4 = new SMIMESignedGenerator.ContentSigner(var1, (boolean)1, var2);
         var3.setContent(var4, "application/pkcs7-mime; name=smime.p7m; smime-type=signed-data");
         var3.addHeader("Content-Type", "application/pkcs7-mime; name=smime.p7m; smime-type=signed-data");
         var3.addHeader("Content-Disposition", "attachment; filename=\"smime.p7m\"");
         var3.addHeader("Content-Description", "S/MIME Cryptographic Signed Data");
         String var5 = this.encoding;
         var3.addHeader("Content-Transfer-Encoding", var5);
         return var3;
      } catch (MessagingException var7) {
         throw new SMIMEException("exception putting body part together.", var7);
      }
   }

   public void addAttributeCertificates(X509Store var1) throws CMSException {
      this._attributeCerts.add(var1);
   }

   public void addCertificatesAndCRLs(CertStore var1) throws CertStoreException, SMIMEException {
      this._certStores.add(var1);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3) throws IllegalArgumentException {
      List var4 = this._signers;
      Object var9 = null;
      SMIMESignedGenerator.Signer var10 = new SMIMESignedGenerator.Signer(var1, var2, var3, (AttributeTable)null, (AttributeTable)var9);
      var4.add(var10);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4) throws IllegalArgumentException {
      List var5 = this._signers;
      Object var11 = null;
      SMIMESignedGenerator.Signer var12 = new SMIMESignedGenerator.Signer(var1, var2, var3, var4, (AttributeTable)null, (AttributeTable)var11);
      var5.add(var12);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, String var4, AttributeTable var5, AttributeTable var6) throws IllegalArgumentException {
      List var7 = this._signers;
      SMIMESignedGenerator.Signer var15 = new SMIMESignedGenerator.Signer(var1, var2, var3, var4, var5, var6);
      var7.add(var15);
   }

   public void addSigner(PrivateKey var1, X509Certificate var2, String var3, AttributeTable var4, AttributeTable var5) throws IllegalArgumentException {
      List var6 = this._signers;
      SMIMESignedGenerator.Signer var13 = new SMIMESignedGenerator.Signer(var1, var2, var3, var4, var5);
      var6.add(var13);
   }

   public void addSigners(SignerInformationStore var1) {
      Iterator var2 = var1.getSigners().iterator();

      while(var2.hasNext()) {
         List var3 = this._oldSigners;
         Object var4 = var2.next();
         var3.add(var4);
      }

   }

   public MimeMultipart generate(MimeBodyPart var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      MimeBodyPart var3 = this.makeContentBodyPart(var1);
      Provider var4 = SMIMEUtil.getProvider(var2);
      return this.make(var3, var4);
   }

   public MimeMultipart generate(MimeBodyPart var1, Provider var2) throws NoSuchAlgorithmException, SMIMEException {
      MimeBodyPart var3 = this.makeContentBodyPart(var1);
      return this.make(var3, var2);
   }

   public MimeMultipart generate(MimeMessage var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      Provider var3 = SMIMEUtil.getProvider(var2);
      return this.generate(var1, var3);
   }

   public MimeMultipart generate(MimeMessage var1, Provider var2) throws NoSuchAlgorithmException, SMIMEException {
      try {
         var1.saveChanges();
      } catch (MessagingException var5) {
         throw new SMIMEException("unable to save message", var5);
      }

      MimeBodyPart var3 = this.makeContentBodyPart(var1);
      return this.make(var3, var2);
   }

   public MimeBodyPart generateCertificateManagement(String var1) throws SMIMEException, NoSuchProviderException {
      Provider var2 = SMIMEUtil.getProvider(var1);
      return this.generateCertificateManagement(var2);
   }

   public MimeBodyPart generateCertificateManagement(Provider var1) throws SMIMEException {
      try {
         MimeBodyPart var2 = new MimeBodyPart();
         SMIMESignedGenerator.ContentSigner var3 = new SMIMESignedGenerator.ContentSigner((MimeBodyPart)null, (boolean)1, var1);
         var2.setContent(var3, "application/pkcs7-mime; name=smime.p7c; smime-type=certs-only");
         var2.addHeader("Content-Type", "application/pkcs7-mime; name=smime.p7c; smime-type=certs-only");
         var2.addHeader("Content-Disposition", "attachment; filename=\"smime.p7c\"");
         var2.addHeader("Content-Description", "S/MIME Certificate Management Message");
         String var4 = this.encoding;
         var2.addHeader("Content-Transfer-Encoding", var4);
         return var2;
      } catch (MessagingException var6) {
         throw new SMIMEException("exception putting body part together.", var6);
      }
   }

   public MimeBodyPart generateEncapsulated(MimeBodyPart var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      MimeBodyPart var3 = this.makeContentBodyPart(var1);
      Provider var4 = SMIMEUtil.getProvider(var2);
      return this.makeEncapsulated(var3, var4);
   }

   public MimeBodyPart generateEncapsulated(MimeBodyPart var1, Provider var2) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      MimeBodyPart var3 = this.makeContentBodyPart(var1);
      return this.makeEncapsulated(var3, var2);
   }

   public MimeBodyPart generateEncapsulated(MimeMessage var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      Provider var3 = SMIMEUtil.getProvider(var2);
      return this.generateEncapsulated(var1, var3);
   }

   public MimeBodyPart generateEncapsulated(MimeMessage var1, Provider var2) throws NoSuchAlgorithmException, SMIMEException {
      try {
         var1.saveChanges();
      } catch (MessagingException var5) {
         throw new SMIMEException("unable to save message", var5);
      }

      MimeBodyPart var3 = this.makeContentBodyPart(var1);
      return this.makeEncapsulated(var3, var2);
   }

   public Map getGeneratedDigests() {
      Map var1 = this._digests;
      return new HashMap(var1);
   }

   private class Signer {

      final X509Certificate cert;
      final String digestOID;
      final String encryptionOID;
      final PrivateKey key;
      final AttributeTable signedAttr;
      final AttributeTable unsignedAttr;


      Signer(PrivateKey var2, X509Certificate var3, String var4, String var5, AttributeTable var6, AttributeTable var7) {
         this.key = var2;
         this.cert = var3;
         this.encryptionOID = var4;
         this.digestOID = var5;
         this.signedAttr = var6;
         this.unsignedAttr = var7;
      }

      Signer(PrivateKey var2, X509Certificate var3, String var4, AttributeTable var5, AttributeTable var6) {
         this(var2, var3, (String)null, var4, var5, var6);
      }

      public X509Certificate getCert() {
         return this.cert;
      }

      public String getDigestOID() {
         return this.digestOID;
      }

      public String getEncryptionOID() {
         return this.encryptionOID;
      }

      public PrivateKey getKey() {
         return this.key;
      }

      public AttributeTable getSignedAttr() {
         return this.signedAttr;
      }

      public AttributeTable getUnsignedAttr() {
         return this.unsignedAttr;
      }
   }

   private class ContentSigner implements SMIMEStreamingProcessor {

      private final MimeBodyPart _content;
      private final boolean _encapsulate;
      private final Provider _provider;


      ContentSigner(MimeBodyPart var2, boolean var3, Provider var4) {
         this._content = var2;
         this._encapsulate = var3;
         this._provider = var4;
      }

      private void writeBodyPart(OutputStream var1, MimeBodyPart var2) throws IOException, MessagingException {
         if(!(var2.getContent() instanceof Multipart)) {
            String var16 = SMIMESignedGenerator.this._defaultContentTransferEncoding;
            if(SMIMEUtil.isCanonicalisationRequired(var2, var16)) {
               var1 = new CRLFOutputStream((OutputStream)var1);
            }

            var2.writeTo((OutputStream)var1);
         } else {
            Multipart var3 = (Multipart)var2.getContent();
            String var4 = var3.getContentType();
            ContentType var5 = new ContentType(var4);
            StringBuilder var6 = (new StringBuilder()).append("--");
            String var7 = var5.getParameter("boundary");
            String var8 = var6.append(var7).toString();
            SMIMEUtil.LineOutputStream var9 = new SMIMEUtil.LineOutputStream((OutputStream)var1);
            Enumeration var10 = var2.getAllHeaderLines();

            while(var10.hasMoreElements()) {
               String var11 = (String)var10.nextElement();
               var9.writeln(var11);
            }

            var9.writeln();
            SMIMEUtil.outputPreamble(var9, var2, var8);
            int var12 = 0;

            while(true) {
               int var13 = var3.getCount();
               if(var12 >= var13) {
                  String var15 = var8 + "--";
                  var9.writeln(var15);
                  return;
               }

               var9.writeln(var8);
               MimeBodyPart var14 = (MimeBodyPart)var3.getBodyPart(var12);
               this.writeBodyPart((OutputStream)var1, var14);
               var9.writeln();
               ++var12;
            }
         }
      }

      protected CMSSignedDataStreamGenerator getGenerator() throws CMSException, CertStoreException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException {
         CMSSignedDataStreamGenerator var1 = new CMSSignedDataStreamGenerator();
         Iterator var2 = SMIMESignedGenerator.this._certStores.iterator();

         while(var2.hasNext()) {
            CertStore var3 = (CertStore)var2.next();
            var1.addCertificatesAndCRLs(var3);
         }

         Iterator var4 = SMIMESignedGenerator.this._attributeCerts.iterator();

         while(var4.hasNext()) {
            X509Store var5 = (X509Store)var4.next();
            var1.addAttributeCertificates(var5);
         }

         Iterator var6 = SMIMESignedGenerator.this._signers.iterator();

         while(var6.hasNext()) {
            SMIMESignedGenerator.Signer var7 = (SMIMESignedGenerator.Signer)var6.next();
            if(var7.getEncryptionOID() != null) {
               PrivateKey var8 = var7.getKey();
               X509Certificate var9 = var7.getCert();
               String var10 = var7.getEncryptionOID();
               String var11 = var7.getDigestOID();
               AttributeTable var12 = var7.getSignedAttr();
               AttributeTable var13 = var7.getUnsignedAttr();
               Provider var14 = this._provider;
               var1.addSigner(var8, var9, var10, var11, var12, var13, var14);
            } else {
               PrivateKey var15 = var7.getKey();
               X509Certificate var16 = var7.getCert();
               String var17 = var7.getDigestOID();
               AttributeTable var18 = var7.getSignedAttr();
               AttributeTable var19 = var7.getUnsignedAttr();
               Provider var20 = this._provider;
               var1.addSigner(var15, var16, var17, var18, var19, var20);
            }
         }

         List var21 = SMIMESignedGenerator.this._oldSigners;
         SignerInformationStore var22 = new SignerInformationStore(var21);
         var1.addSigners(var22);
         return var1;
      }

      public void write(OutputStream var1) throws IOException {
         try {
            CMSSignedDataStreamGenerator var2 = this.getGenerator();
            boolean var3 = this._encapsulate;
            OutputStream var4 = var2.open(var1, var3);
            if(this._content != null) {
               if(!this._encapsulate) {
                  MimeBodyPart var5 = this._content;
                  this.writeBodyPart(var4, var5);
               } else {
                  DataHandler var9 = this._content.getDataHandler();
                  MailcapCommandMap var10 = SMIMESignedGenerator.addCommands(CommandMap.getDefaultCommandMap());
                  var9.setCommandMap(var10);
                  this._content.writeTo(var4);
               }
            }

            var4.close();
            SMIMESignedGenerator var6 = SMIMESignedGenerator.this;
            Map var7 = var2.getGeneratedDigests();
            var6._digests = var7;
         } catch (MessagingException var17) {
            String var11 = var17.toString();
            throw new IOException(var11);
         } catch (NoSuchAlgorithmException var18) {
            String var12 = var18.toString();
            throw new IOException(var12);
         } catch (NoSuchProviderException var19) {
            String var13 = var19.toString();
            throw new IOException(var13);
         } catch (CMSException var20) {
            String var14 = var20.toString();
            throw new IOException(var14);
         } catch (InvalidKeyException var21) {
            String var15 = var21.toString();
            throw new IOException(var15);
         } catch (CertStoreException var22) {
            String var16 = var22.toString();
            throw new IOException(var16);
         }
      }
   }
}

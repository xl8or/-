package myorg.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.crypto.SecretKey;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.cms.CMSEnvelopedDataGenerator;
import myorg.bouncycastle.cms.CMSEnvelopedDataStreamGenerator;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.mail.smime.SMIMEException;
import myorg.bouncycastle.mail.smime.SMIMEGenerator;
import myorg.bouncycastle.mail.smime.SMIMEStreamingProcessor;
import myorg.bouncycastle.mail.smime.SMIMEUtil;

public class SMIMEEnvelopedGenerator extends SMIMEGenerator {

   public static final String AES128_CBC = CMSEnvelopedDataGenerator.AES128_CBC;
   public static final String AES128_WRAP = CMSEnvelopedDataGenerator.AES128_WRAP;
   public static final String AES192_CBC = CMSEnvelopedDataGenerator.AES192_CBC;
   public static final String AES256_CBC = CMSEnvelopedDataGenerator.AES256_CBC;
   public static final String AES256_WRAP = CMSEnvelopedDataGenerator.AES256_WRAP;
   public static final String CAMELLIA128_CBC = CMSEnvelopedDataGenerator.CAMELLIA128_CBC;
   public static final String CAMELLIA128_WRAP = CMSEnvelopedDataGenerator.CAMELLIA128_WRAP;
   public static final String CAMELLIA192_CBC = CMSEnvelopedDataGenerator.CAMELLIA192_CBC;
   public static final String CAMELLIA192_WRAP = CMSEnvelopedDataGenerator.CAMELLIA192_WRAP;
   public static final String CAMELLIA256_CBC = CMSEnvelopedDataGenerator.CAMELLIA256_CBC;
   public static final String CAMELLIA256_WRAP = CMSEnvelopedDataGenerator.CAMELLIA256_WRAP;
   public static final String CAST5_CBC = "1.2.840.113533.7.66.10";
   public static final String DES_EDE3_CBC = CMSEnvelopedDataGenerator.DES_EDE3_CBC;
   public static final String DES_EDE3_WRAP = CMSEnvelopedDataGenerator.DES_EDE3_WRAP;
   public static final String ECDH_SHA1KDF = CMSEnvelopedDataGenerator.ECDH_SHA1KDF;
   private static final String ENCRYPTED_CONTENT_TYPE = "application/pkcs7-mime; name=\"smime.p7m\"; smime-type=enveloped-data";
   public static final String IDEA_CBC = "1.3.6.1.4.1.188.7.1.1.2";
   public static final String RC2_CBC = CMSEnvelopedDataGenerator.RC2_CBC;
   public static final String SEED_CBC = CMSEnvelopedDataGenerator.SEED_CBC;
   public static final String SEED_WRAP = CMSEnvelopedDataGenerator.SEED_WRAP;
   private SMIMEEnvelopedGenerator.EnvelopedGenerator fact;


   static {
      CommandMap.setDefaultCommandMap(addCommands(CommandMap.getDefaultCommandMap()));
   }

   public SMIMEEnvelopedGenerator() {
      SMIMEEnvelopedGenerator.EnvelopedGenerator var1 = new SMIMEEnvelopedGenerator.EnvelopedGenerator((SMIMEEnvelopedGenerator.1)null);
      this.fact = var1;
   }

   // $FF: synthetic method
   static SMIMEEnvelopedGenerator.EnvelopedGenerator access$100(SMIMEEnvelopedGenerator var0) {
      return var0.fact;
   }

   // $FF: synthetic method
   static MailcapCommandMap access$200(CommandMap var0) {
      return addCommands(var0);
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

   private MimeBodyPart make(MimeBodyPart var1, String var2, int var3, Provider var4) throws NoSuchAlgorithmException, SMIMEException {
      this.createSymmetricKeyGenerator(var2, var4);

      try {
         MimeBodyPart var6 = new MimeBodyPart();
         SMIMEEnvelopedGenerator.ContentEncryptor var12 = new SMIMEEnvelopedGenerator.ContentEncryptor(var1, var2, var3, var4);
         var6.setContent(var12, "application/pkcs7-mime; name=\"smime.p7m\"; smime-type=enveloped-data");
         var6.addHeader("Content-Type", "application/pkcs7-mime; name=\"smime.p7m\"; smime-type=enveloped-data");
         var6.addHeader("Content-Disposition", "attachment; filename=\"smime.p7m\"");
         var6.addHeader("Content-Description", "S/MIME Encrypted Message");
         String var13 = this.encoding;
         var6.addHeader("Content-Transfer-Encoding", var13);
         return var6;
      } catch (MessagingException var15) {
         throw new SMIMEException("exception putting multi-part together.", var15);
      }
   }

   public void addKEKRecipient(SecretKey var1, byte[] var2) throws IllegalArgumentException {
      this.fact.addKEKRecipient(var1, var2);
   }

   public void addKeyAgreementRecipient(String var1, PrivateKey var2, PublicKey var3, X509Certificate var4, String var5, String var6) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
      SMIMEEnvelopedGenerator.EnvelopedGenerator var7 = this.fact;
      var7.addKeyAgreementRecipient(var1, var2, var3, var4, var5, var6);
   }

   public void addKeyAgreementRecipient(String var1, PrivateKey var2, PublicKey var3, X509Certificate var4, String var5, Provider var6) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
      SMIMEEnvelopedGenerator.EnvelopedGenerator var7 = this.fact;
      var7.addKeyAgreementRecipient(var1, var2, var3, var4, var5, var6);
   }

   public void addKeyTransRecipient(PublicKey var1, byte[] var2) throws IllegalArgumentException {
      this.fact.addKeyTransRecipient(var1, var2);
   }

   public void addKeyTransRecipient(X509Certificate var1) throws IllegalArgumentException {
      this.fact.addKeyTransRecipient(var1);
   }

   public MimeBodyPart generate(MimeBodyPart var1, String var2, int var3, String var4) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      Provider var5 = SMIMEUtil.getProvider(var4);
      return this.generate(var1, var2, var3, var5);
   }

   public MimeBodyPart generate(MimeBodyPart var1, String var2, int var3, Provider var4) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      MimeBodyPart var5 = this.makeContentBodyPart(var1);
      return this.make(var5, var2, var3, var4);
   }

   public MimeBodyPart generate(MimeBodyPart var1, String var2, String var3) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      MimeBodyPart var4 = this.makeContentBodyPart(var1);
      Provider var5 = SMIMEUtil.getProvider(var3);
      return this.make(var4, var2, 0, var5);
   }

   public MimeBodyPart generate(MimeBodyPart var1, String var2, Provider var3) throws NoSuchAlgorithmException, SMIMEException {
      MimeBodyPart var4 = this.makeContentBodyPart(var1);
      return this.make(var4, var2, 0, var3);
   }

   public MimeBodyPart generate(MimeMessage var1, String var2, int var3, String var4) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      Provider var5 = SMIMEUtil.getProvider(var4);
      return this.generate(var1, var2, var3, var5);
   }

   public MimeBodyPart generate(MimeMessage var1, String var2, int var3, Provider var4) throws NoSuchAlgorithmException, SMIMEException {
      try {
         var1.saveChanges();
      } catch (MessagingException var7) {
         throw new SMIMEException("unable to save message", var7);
      }

      MimeBodyPart var5 = this.makeContentBodyPart(var1);
      return this.make(var5, var2, var3, var4);
   }

   public MimeBodyPart generate(MimeMessage var1, String var2, String var3) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      Provider var4 = SMIMEUtil.getProvider(var3);
      return this.generate(var1, var2, var4);
   }

   public MimeBodyPart generate(MimeMessage var1, String var2, Provider var3) throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException {
      try {
         var1.saveChanges();
      } catch (MessagingException var6) {
         throw new SMIMEException("unable to save message", var6);
      }

      MimeBodyPart var4 = this.makeContentBodyPart(var1);
      return this.make(var4, var2, 0, var3);
   }

   public void setBerEncodeRecipients(boolean var1) {
      this.fact.setBEREncodeRecipients(var1);
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class EnvelopedGenerator extends CMSEnvelopedDataStreamGenerator {

      private SecretKey _encKey;
      private String _encryptionOID;
      private AlgorithmParameters _params;
      private ASN1EncodableVector _recipientInfos;


      private EnvelopedGenerator() {}

      // $FF: synthetic method
      EnvelopedGenerator(SMIMEEnvelopedGenerator.1 var2) {
         this();
      }

      protected OutputStream open(OutputStream var1, String var2, SecretKey var3, AlgorithmParameters var4, ASN1EncodableVector var5, Provider var6) throws NoSuchAlgorithmException, CMSException {
         this._encryptionOID = var2;
         this._encKey = var3;
         this._params = var4;
         this._recipientInfos = var5;
         return super.open(var1, var2, var3, var4, var5, var6);
      }

      OutputStream regenerate(OutputStream var1, Provider var2) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
         String var3 = this._encryptionOID;
         SecretKey var4 = this._encKey;
         AlgorithmParameters var5 = this._params;
         ASN1EncodableVector var6 = this._recipientInfos;
         return super.open(var1, var3, var4, var5, var6, var2);
      }
   }

   private class ContentEncryptor implements SMIMEStreamingProcessor {

      private final MimeBodyPart _content;
      private final String _encryptionOid;
      private boolean _firstTime = 1;
      private final int _keySize;
      private final Provider _provider;


      ContentEncryptor(MimeBodyPart var2, String var3, int var4, Provider var5) {
         this._content = var2;
         this._encryptionOid = var3;
         this._keySize = var4;
         this._provider = var5;
      }

      public void write(OutputStream param1) throws IOException {
         // $FF: Couldn't be decompiled
      }
   }

   private static class WrappingIOException extends IOException {

      private Throwable cause;


      WrappingIOException(String var1, Throwable var2) {
         super(var1);
         this.cause = var2;
      }

      public Throwable getCause() {
         return this.cause;
      }
   }
}

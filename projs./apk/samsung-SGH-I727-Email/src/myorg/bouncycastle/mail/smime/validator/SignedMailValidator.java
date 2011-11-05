package myorg.bouncycastle.mail.smime.validator;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.mail.internet.MimeMessage;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.Attribute;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.CMSAttributes;
import myorg.bouncycastle.asn1.cms.Time;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.cms.SignerId;
import myorg.bouncycastle.cms.SignerInformation;
import myorg.bouncycastle.cms.SignerInformationStore;
import myorg.bouncycastle.i18n.ErrorBundle;
import myorg.bouncycastle.i18n.filter.TrustedInput;
import myorg.bouncycastle.jce.PrincipalUtil;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.mail.smime.validator.SignedMailValidatorException;
import myorg.bouncycastle.x509.CertPathReviewerException;
import myorg.bouncycastle.x509.PKIXCertPathReviewer;

public class SignedMailValidator {

   private static final Class DEFAULT_CERT_PATH_REVIEWER = PKIXCertPathReviewer.class;
   private static final String EXT_KEY_USAGE = X509Extensions.ExtendedKeyUsage.getId();
   private static final String RESOURCE_NAME = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
   private static final String SUBJECT_ALTERNATIVE_NAME = X509Extensions.SubjectAlternativeName.getId();
   private static final long THIRTY_YEARS_IN_MILLI_SEC = 946728000000L;
   private static final int shortKeyLength = 512;
   private Class certPathReviewerClass;
   private CertStore certs;
   private String[] fromAddresses;
   private Map results;
   private SignerInformationStore signers;


   public SignedMailValidator(MimeMessage var1, PKIXParameters var2) throws SignedMailValidatorException {
      Class var3 = DEFAULT_CERT_PATH_REVIEWER;
      this(var1, var2, var3);
   }

   public SignedMailValidator(MimeMessage param1, PKIXParameters param2, Class param3) throws SignedMailValidatorException {
      // $FF: Couldn't be decompiled
   }

   static String addressesToString(Object[] var0) {
      String var1;
      if(var0 == null) {
         var1 = "null";
      } else {
         StringBuffer var2 = new StringBuffer();
         StringBuffer var3 = var2.append('[');
         int var4 = 0;

         while(true) {
            int var5 = var0.length;
            if(var4 == var5) {
               var1 = var2.append(']').toString();
               break;
            }

            if(var4 > 0) {
               StringBuffer var6 = var2.append(", ");
            }

            String var7 = String.valueOf(var0[var4]);
            var2.append(var7);
            ++var4;
         }
      }

      return var1;
   }

   public static CertPath createCertPath(X509Certificate var0, Set var1, List var2) throws GeneralSecurityException {
      return (CertPath)createCertPath(var0, var1, var2, (List)null)[0];
   }

   public static Object[] createCertPath(X509Certificate var0, Set var1, List var2, List var3) throws GeneralSecurityException {
      LinkedHashSet var4 = new LinkedHashSet();
      ArrayList var5 = new ArrayList();
      var4.add(var0);
      Boolean var8 = new Boolean((boolean)1);
      var5.add(var8);
      X509Certificate var10 = null;
      boolean var11 = false;
      Object var12 = var0;

      while(var12 != null && !var11) {
         Iterator var13 = var1.iterator();

         while(var13.hasNext()) {
            TrustAnchor var14 = (TrustAnchor)var13.next();
            X509Certificate var15 = var14.getTrustedCert();
            if(var15 != null) {
               X500Principal var16 = var15.getSubjectX500Principal();
               X500Principal var17 = ((X509Certificate)var12).getIssuerX500Principal();
               if(var16.equals(var17)) {
                  try {
                     PublicKey var18 = var15.getPublicKey();
                     ((X509Certificate)var12).verify(var18, "myBC");
                  } catch (Exception var63) {
                     continue;
                  }

                  var11 = true;
                  var10 = var15;
                  break;
               }
            } else {
               String var29 = var14.getCAName();
               String var30 = ((X509Certificate)var12).getIssuerX500Principal().getName();
               if(var29.equals(var30)) {
                  try {
                     PublicKey var31 = var14.getCAPublicKey();
                     ((X509Certificate)var12).verify(var31, "myBC");
                  } catch (Exception var64) {
                     continue;
                  }

                  var11 = true;
                  break;
               }
            }
         }

         if(!var11) {
            X509CertSelector var20 = new X509CertSelector();

            try {
               byte[] var21 = ((X509Certificate)var12).getIssuerX500Principal().getEncoded();
               var20.setSubject(var21);
            } catch (IOException var62) {
               String var32 = var62.toString();
               throw new IllegalStateException(var32);
            }

            String var22 = X509Extensions.AuthorityKeyIdentifier.getId();
            byte[] var23 = ((X509Certificate)var12).getExtensionValue(var22);
            if(var23 != null) {
               try {
                  AuthorityKeyIdentifier var68 = AuthorityKeyIdentifier.getInstance(getObject(var23));
                  if(var68.getKeyIdentifier() != null) {
                     byte[] var24 = var68.getKeyIdentifier();
                     byte[] var25 = (new DEROctetString(var24)).getDEREncoded();
                     var20.setSubjectKeyIdentifier(var25);
                  }
               } catch (IOException var61) {
                  ;
               }
            }

            var12 = null;
            X509Certificate var66 = findNextCert(var2, var20, var4);
            Object var65;
            if(var66 == null && var3 != null) {
               var12 = null;
               var66 = findNextCert(var3, var20, var4);
               var65 = var12;
            } else {
               var65 = var12;
            }

            if(var66 != null) {
               var4.add(var66);
               var12 = new Boolean((boolean)var65);
               var5.add(var12);
            }
         }
      }

      if(var11) {
         label123: {
            if(var10 != null) {
               X500Principal var33 = var10.getSubjectX500Principal();
               X500Principal var34 = var10.getIssuerX500Principal();
               if(var33.equals(var34)) {
                  var4.add(var10);
                  Boolean var36 = new Boolean((boolean)0);
                  var5.add(var36);
                  break label123;
               }
            }

            X509CertSelector var43 = new X509CertSelector();

            try {
               byte[] var44 = ((X509Certificate)var12).getIssuerX500Principal().getEncoded();
               var43.setSubject(var44);
               byte[] var45 = ((X509Certificate)var12).getIssuerX500Principal().getEncoded();
               var43.setIssuer(var45);
            } catch (IOException var60) {
               String var54 = var60.toString();
               throw new IllegalStateException(var54);
            }

            byte var67 = 0;
            X509Certificate var46 = findNextCert(var2, var43, var4);
            byte var47;
            X509Certificate var48;
            if(var46 == null && var3 != null) {
               var47 = 1;
               var48 = findNextCert(var3, var43, var4);
            } else {
               var48 = var46;
               var47 = var67;
            }

            if(var48 != null) {
               try {
                  PublicKey var49 = var48.getPublicKey();
                  ((X509Certificate)var12).verify(var49, "myBC");
                  var4.add(var48);
                  Boolean var51 = new Boolean((boolean)var47);
                  var5.add(var51);
               } catch (GeneralSecurityException var59) {
                  ;
               }
            }
         }
      }

      CertificateFactory var39 = CertificateFactory.getInstance("X.509", "myBC");
      ArrayList var40 = new ArrayList(var4);
      CertPath var41 = var39.generateCertPath(var40);
      Object[] var42 = new Object[]{var41, var5};
      return var42;
   }

   private static List findCerts(List var0, X509CertSelector var1) throws CertStoreException {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         Collection var4 = ((CertStore)var3.next()).getCertificates(var1);
         var2.addAll(var4);
      }

      return var2;
   }

   private static X509Certificate findNextCert(List var0, X509CertSelector var1, Set var2) throws CertStoreException {
      Iterator var3 = findCerts(var0, var1).iterator();
      boolean var4 = false;
      X509Certificate var5 = null;

      while(var3.hasNext()) {
         var5 = (X509Certificate)var3.next();
         if(!var2.contains(var5)) {
            var4 = true;
            break;
         }
      }

      X509Certificate var6;
      if(var4) {
         var6 = var5;
      } else {
         var6 = null;
      }

      return var6;
   }

   public static Set getEmailAddresses(X509Certificate var0) throws IOException, CertificateEncodingException {
      HashSet var1 = new HashSet();
      X509Principal var2 = PrincipalUtil.getSubjectX509Principal(var0);
      Vector var3 = var2.getOIDs();
      Vector var4 = var2.getValues();
      int var5 = 0;

      while(true) {
         int var6 = var3.size();
         if(var5 >= var6) {
            break;
         }

         Object var7 = var3.get(var5);
         DERObjectIdentifier var8 = X509Principal.EmailAddress;
         if(var7.equals(var8)) {
            String var9 = ((String)var4.get(var5)).toLowerCase();
            var1.add(var9);
            break;
         }

         ++var5;
      }

      String var11 = SUBJECT_ALTERNATIVE_NAME;
      byte[] var12 = var0.getExtensionValue(var11);
      if(var12 != null) {
         DERSequence var13 = (DERSequence)getObject(var12);
         int var14 = 0;

         while(true) {
            int var15 = var13.size();
            if(var14 >= var15) {
               break;
            }

            ASN1TaggedObject var16 = (ASN1TaggedObject)var13.getObjectAt(var14);
            if(var16.getTagNo() == 1) {
               String var17 = DERIA5String.getInstance(var16, (boolean)1).getString().toLowerCase();
               var1.add(var17);
            }

            ++var14;
         }
      }

      return var1;
   }

   private static DERObject getObject(byte[] var0) throws IOException {
      byte[] var1 = ((ASN1OctetString)(new ASN1InputStream(var0)).readObject()).getOctets();
      return (new ASN1InputStream(var1)).readObject();
   }

   public static Date getSignatureTime(SignerInformation var0) {
      AttributeTable var1 = var0.getSignedAttributes();
      Date var2 = null;
      if(var1 != null) {
         DERObjectIdentifier var3 = CMSAttributes.signingTime;
         Attribute var4 = var1.get(var3);
         if(var4 != null) {
            var2 = Time.getInstance(var4.getAttrValues().getObjectAt(0).getDERObject()).getDate();
         }
      }

      return var2;
   }

   protected void checkSignerCert(X509Certificate param1, List param2, List param3) {
      // $FF: Couldn't be decompiled
   }

   public CertStore getCertsAndCRLs() {
      return this.certs;
   }

   public SignerInformationStore getSignerInformationStore() {
      return this.signers;
   }

   public SignedMailValidator.ValidationResult getValidationResult(SignerInformation var1) throws SignedMailValidatorException {
      SignerInformationStore var2 = this.signers;
      SignerId var3 = var1.getSID();
      if(var2.getSigners(var3).isEmpty()) {
         ErrorBundle var4 = new ErrorBundle("myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages", "SignedMailValidator.wrongSigner");
         throw new SignedMailValidatorException(var4);
      } else {
         return (SignedMailValidator.ValidationResult)this.results.get(var1);
      }
   }

   protected void validateSignatures(PKIXParameters var1) {
      PKIXParameters var2 = (PKIXParameters)var1.clone();
      CertStore var3 = this.certs;
      var2.addCertStore(var3);
      Iterator var6 = this.signers.getSigners().iterator();

      while(var6.hasNext()) {
         ArrayList var7 = new ArrayList();
         ArrayList var8 = new ArrayList();
         SignerInformation var9 = (SignerInformation)var6.next();
         X509Certificate var10 = null;

         try {
            List var11 = var2.getCertStores();
            SignerId var12 = var9.getSID();
            Iterator var13 = findCerts(var11, var12).iterator();
            if(var13.hasNext()) {
               var10 = (X509Certificate)var13.next();
            }
         } catch (CertStoreException var207) {
            ErrorBundle var106 = new ErrorBundle;
            Object[] var107 = new Object[3];
            String var108 = var207.getMessage();
            var107[0] = var108;
            var107[1] = var207;
            String var109 = var207.getClass().getName();
            var107[2] = var109;
            String var111 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
            String var112 = "SignedMailValidator.exceptionRetrievingSignerCert";
            var106.<init>(var111, var112, var107);
            boolean var116 = var7.add(var106);
         }

         if(var10 != null) {
            boolean var18;
            try {
               PublicKey var14 = var10.getPublicKey();
               String var17 = "myBC";
               var18 = var9.verify(var14, var17);
               if(!var18) {
                  ErrorBundle var19 = new ErrorBundle;
                  String var21 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
                  String var22 = "SignedMailValidator.signatureNotVerified";
                  var19.<init>(var21, var22);
                  boolean var25 = var7.add(var19);
               }
            } catch (Exception var206) {
               ErrorBundle var118 = new ErrorBundle;
               Object[] var119 = new Object[3];
               String var120 = var206.getMessage();
               var119[0] = var120;
               var119[1] = var206;
               String var121 = var206.getClass().getName();
               var119[2] = var121;
               String var123 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
               String var124 = "SignedMailValidator.exceptionVerifyingSignature";
               var118.<init>(var123, var124, var119);
               boolean var128 = var7.add(var118);
            }

            this.checkSignerCert(var10, var7, var8);
            AttributeTable var30 = var9.getSignedAttributes();
            if(var30 != null) {
               DERObjectIdentifier var31 = PKCSObjectIdentifiers.id_aa_receiptRequest;
               if(var30.get(var31) != null) {
                  ErrorBundle var34 = new ErrorBundle;
                  String var36 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
                  String var37 = "SignedMailValidator.signedReceiptRequest";
                  var34.<init>(var36, var37);
                  boolean var40 = var8.add(var34);
               }
            }

            Date var41 = getSignatureTime(var9);
            if(var41 == null) {
               ErrorBundle var42 = new ErrorBundle;
               String var44 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
               String var45 = "SignedMailValidator.noSigningTime";
               var42.<init>(var44, var45);
               boolean var48 = var7.add(var42);
               var41 = new Date();
            } else {
               try {
                  var10.checkValidity(var41);
               } catch (CertificateExpiredException var204) {
                  ErrorBundle var132 = new ErrorBundle;
                  Object[] var133 = new Object[2];
                  TrustedInput var134 = new TrustedInput(var41);
                  var133[0] = var134;
                  Date var137 = var10.getNotAfter();
                  TrustedInput var138 = new TrustedInput(var137);
                  var133[1] = var138;
                  String var140 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
                  String var141 = "SignedMailValidator.certExpired";
                  var132.<init>(var140, var141, var133);
                  boolean var145 = var7.add(var132);
               } catch (CertificateNotYetValidException var205) {
                  ErrorBundle var147 = new ErrorBundle;
                  Object[] var148 = new Object[2];
                  TrustedInput var149 = new TrustedInput(var41);
                  var148[0] = var149;
                  Date var152 = var10.getNotBefore();
                  TrustedInput var153 = new TrustedInput(var152);
                  var148[1] = var153;
                  String var155 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
                  String var156 = "SignedMailValidator.certNotYetValid";
                  var147.<init>(var155, var156, var148);
                  boolean var160 = var7.add(var147);
               }
            }

            var2.setDate(var41);

            try {
               ArrayList var51 = new ArrayList();
               CertStore var52 = this.certs;
               boolean var55 = var51.add(var52);
               Set var56 = var2.getTrustAnchors();
               List var57 = var1.getCertStores();
               Object[] var62 = createCertPath(var10, var56, var57, var51);
               CertPath var63 = (CertPath)var62[0];
               List var64 = (List)var62[1];

               PKIXCertPathReviewer var65;
               try {
                  var65 = (PKIXCertPathReviewer)this.certPathReviewerClass.newInstance();
               } catch (IllegalAccessException var200) {
                  StringBuilder var162 = (new StringBuilder()).append("Cannot instantiate object of type ");
                  String var163 = this.certPathReviewerClass.getName();
                  StringBuilder var164 = var162.append(var163).append(": ");
                  String var165 = var200.getMessage();
                  String var166 = var164.append(var165).toString();
                  throw new IllegalArgumentException(var166);
               } catch (InstantiationException var201) {
                  StringBuilder var179 = (new StringBuilder()).append("Cannot instantiate object of type ");
                  String var180 = this.certPathReviewerClass.getName();
                  StringBuilder var181 = var179.append(var180).append(": ");
                  String var182 = var201.getMessage();
                  String var183 = var181.append(var182).toString();
                  throw new IllegalArgumentException(var183);
               }

               var65.init(var63, var2);
               if(!var65.isValidCertPath()) {
                  ErrorBundle var69 = new ErrorBundle;
                  String var71 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
                  String var72 = "SignedMailValidator.certPathInvalid";
                  var69.<init>(var71, var72);
                  boolean var75 = var7.add(var69);
               }

               Map var76 = this.results;
               SignedMailValidator.ValidationResult var78 = new SignedMailValidator.ValidationResult(var65, var18, var7, var8, var64);
               Object var82 = var76.put(var9, var78);
            } catch (GeneralSecurityException var202) {
               ErrorBundle var84 = new ErrorBundle;
               Object[] var85 = new Object[3];
               String var86 = var202.getMessage();
               var85[0] = var86;
               var85[1] = var202;
               String var87 = var202.getClass().getName();
               var85[2] = var87;
               String var89 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
               String var90 = "SignedMailValidator.exceptionCreateCertPath";
               var84.<init>(var89, var90, var85);
               boolean var94 = var7.add(var84);
               Map var95 = this.results;
               SignedMailValidator.ValidationResult var100 = new SignedMailValidator.ValidationResult((PKIXCertPathReviewer)null, var18, var7, var8, (List)null);
               Object var104 = var95.put(var9, var100);
            } catch (CertPathReviewerException var203) {
               ErrorBundle var167 = var203.getErrorMessage();
               var7.add(var167);
               Map var169 = this.results;
               SignedMailValidator.ValidationResult var174 = new SignedMailValidator.ValidationResult((PKIXCertPathReviewer)null, var18, var7, var8, (List)null);
               Object var178 = var169.put(var9, var174);
            }
         } else {
            ErrorBundle var184 = new ErrorBundle;
            String var186 = "myorg.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";
            String var187 = "SignedMailValidator.noSignerCert";
            var184.<init>(var186, var187);
            boolean var190 = var7.add(var184);
            Map var191 = this.results;
            SignedMailValidator.ValidationResult var195 = new SignedMailValidator.ValidationResult((PKIXCertPathReviewer)null, (boolean)0, var7, var8, (List)null);
            Object var199 = var191.put(var9, var195);
         }
      }

   }

   public class ValidationResult {

      private List errors;
      private List notifications;
      private PKIXCertPathReviewer review;
      private boolean signVerified;
      private List userProvidedCerts;


      ValidationResult(PKIXCertPathReviewer var2, boolean var3, List var4, List var5, List var6) {
         this.review = var2;
         this.errors = var4;
         this.notifications = var5;
         this.signVerified = var3;
         this.userProvidedCerts = var6;
      }

      public CertPath getCertPath() {
         CertPath var1;
         if(this.review != null) {
            var1 = this.review.getCertPath();
         } else {
            var1 = null;
         }

         return var1;
      }

      public PKIXCertPathReviewer getCertPathReview() {
         return this.review;
      }

      public List getErrors() {
         return this.errors;
      }

      public List getNotifications() {
         return this.notifications;
      }

      public List getUserProvidedCerts() {
         return this.userProvidedCerts;
      }

      public boolean isValidSignature() {
         boolean var1;
         if(this.review != null) {
            if(this.signVerified && this.review.isValidCertPath() && this.errors.isEmpty()) {
               var1 = true;
            } else {
               var1 = false;
            }
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isVerifiedSignature() {
         return this.signVerified;
      }
   }
}

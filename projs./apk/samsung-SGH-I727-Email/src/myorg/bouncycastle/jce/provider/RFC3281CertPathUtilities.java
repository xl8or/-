package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.x509.CRLDistPoint;
import myorg.bouncycastle.asn1.x509.DistributionPoint;
import myorg.bouncycastle.asn1.x509.DistributionPointName;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.ReasonFlags;
import myorg.bouncycastle.asn1.x509.TargetInformation;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.jce.exception.ExtCertPathValidatorException;
import myorg.bouncycastle.jce.provider.AnnotatedException;
import myorg.bouncycastle.jce.provider.CertPathValidatorUtilities;
import myorg.bouncycastle.jce.provider.CertStatus;
import myorg.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import myorg.bouncycastle.jce.provider.ReasonsMask;
import myorg.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import myorg.bouncycastle.x509.ExtendedPKIXParameters;
import myorg.bouncycastle.x509.PKIXAttrCertChecker;
import myorg.bouncycastle.x509.X509AttributeCertificate;
import myorg.bouncycastle.x509.X509CertStoreSelector;

class RFC3281CertPathUtilities {

   private static final String AUTHORITY_INFO_ACCESS = X509Extensions.AuthorityInfoAccess.getId();
   private static final String CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
   private static final String NO_REV_AVAIL = X509Extensions.NoRevAvail.getId();
   private static final String TARGET_INFORMATION = X509Extensions.TargetInformation.getId();


   RFC3281CertPathUtilities() {}

   protected static void additionalChecks(X509AttributeCertificate var0, ExtendedPKIXParameters var1) throws CertPathValidatorException {
      Iterator var2 = var1.getProhibitedACAttributes().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         if(var0.getAttributes(var3) != null) {
            String var4 = "Attribute certificate contains prohibited attribute: " + var3 + ".";
            throw new CertPathValidatorException(var4);
         }
      }

      Iterator var5 = var1.getNecessaryACAttributes().iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         if(var0.getAttributes(var6) == null) {
            String var7 = "Attribute certificate does not contain necessary attribute: " + var6 + ".";
            throw new CertPathValidatorException(var7);
         }
      }

   }

   private static void checkCRL(DistributionPoint var0, X509AttributeCertificate var1, ExtendedPKIXParameters var2, Date var3, X509Certificate var4, CertStatus var5, ReasonsMask var6, List var7) throws AnnotatedException {
      String var8 = X509Extensions.NoRevAvail.getId();
      if(var1.getExtensionValue(var8) == null) {
         long var11 = System.currentTimeMillis();
         Date var13 = new Date(var11);
         long var14 = var3.getTime();
         long var16 = var13.getTime();
         if(var14 > var16) {
            throw new AnnotatedException("Validation time is in future.");
         } else {
            Iterator var22 = CertPathValidatorUtilities.getCompleteCRLs(var0, var1, var13, var2).iterator();
            AnnotatedException var23 = null;
            boolean var24 = false;

            while(var22.hasNext() && var5.getCertStatus() == 11 && !var6.isAllReasons()) {
               X509CRL var34;
               X509CRL var25;
               ReasonsMask var26;
               try {
                  var25 = (X509CRL)var22.next();
                  var26 = RFC3280CertPathUtilities.processCRLD(var25, var0);
                  if(!var26.hasNewReasons(var6)) {
                     continue;
                  }

                  Set var32 = RFC3280CertPathUtilities.processCRLF(var25, var1, (X509Certificate)null, (PublicKey)null, var2, var7);
                  PublicKey var33 = RFC3280CertPathUtilities.processCRLG(var25, var32);
                  var34 = null;
                  if(var2.isUseDeltasEnabled()) {
                     var34 = RFC3280CertPathUtilities.processCRLH(CertPathValidatorUtilities.getDeltaCRLs(var13, var2, var25), var33);
                  }

                  if(var2.getValidityModel() != 1) {
                     long var38 = var1.getNotAfter().getTime();
                     long var40 = var25.getThisUpdate().getTime();
                     if(var38 < var40) {
                        throw new AnnotatedException("No valid CRL for current time found.");
                     }
                  }
               } catch (AnnotatedException var64) {
                  var23 = var64;
                  continue;
               }

               RFC3280CertPathUtilities.processCRLB1(var0, var1, var25);
               RFC3280CertPathUtilities.processCRLB2(var0, var1, var25);
               RFC3280CertPathUtilities.processCRLC(var34, var25, var2);
               RFC3280CertPathUtilities.processCRLI(var3, var34, var1, var5, var2);
               RFC3280CertPathUtilities.processCRLJ(var3, var25, var1, var5);
               if(var5.getCertStatus() == 8) {
                  byte var61 = 11;
                  var5.setCertStatus(var61);
               }

               var6.addReasons(var26);
               var24 = true;
            }

            if(!var24) {
               throw var23;
            }
         }
      }
   }

   protected static void checkCRLs(X509AttributeCertificate var0, ExtendedPKIXParameters var1, X509Certificate var2, Date var3, List var4) throws CertPathValidatorException {
      if(var1.isRevocationEnabled()) {
         String var5 = NO_REV_AVAIL;
         if(var0.getExtensionValue(var5) == null) {
            CRLDistPoint var7;
            try {
               String var6 = CRL_DISTRIBUTION_POINTS;
               var7 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var0, var6));
            } catch (AnnotatedException var60) {
               throw new CertPathValidatorException("CRL distribution point extension could not be read.", var60);
            }

            CRLDistPoint var8 = var7;

            try {
               CertPathValidatorUtilities.addAdditionalStoresFromCRLDistributionPoint(var8, var1);
            } catch (AnnotatedException var59) {
               throw new CertPathValidatorException("No additional CRL locations could be decoded from CRL distribution point extension.", var59);
            }

            CertStatus var9 = new CertStatus();
            ReasonsMask var10 = new ReasonsMask();
            Object var11 = null;
            boolean var12 = false;
            boolean var27;
            AnnotatedException var26;
            if(var7 != null) {
               label119: {
                  DistributionPoint[] var64;
                  try {
                     var64 = var8.getDistributionPoints();
                  } catch (Exception var58) {
                     throw new ExtCertPathValidatorException("Distribution points could not be read.", var58);
                  }

                  DistributionPoint[] var13 = var64;
                  int var14 = 0;
                  boolean var15 = var12;

                  while(true) {
                     try {
                        int var16 = var13.length;
                        if(var14 >= var16 || var9.getCertStatus() != 11 || var10.isAllReasons()) {
                           break;
                        }

                        ExtendedPKIXParameters var17 = (ExtendedPKIXParameters)var1.clone();
                        DistributionPoint var18 = var13[var14];
                        checkCRL(var18, var0, var17, var3, var2, var9, var10, var4);
                     } catch (AnnotatedException var63) {
                        AnnotatedException var43 = new AnnotatedException("No valid CRL for distribution point found.", var63);
                        var27 = var15;
                        var26 = var43;
                        break label119;
                     }

                     ++var14;
                     var15 = true;
                  }

                  var26 = (AnnotatedException)var11;
                  var27 = var15;
               }
            } else {
               var26 = (AnnotatedException)var11;
               var27 = var12;
            }

            boolean var40;
            AnnotatedException var41;
            if(var9.getCertStatus() == 11 && !var10.isAllReasons()) {
               label121: {
                  boolean var28 = false;

                  label90: {
                     AnnotatedException var44;
                     label122: {
                        DERObject var30;
                        try {
                           try {
                              byte[] var29 = ((X500Principal)var0.getIssuer().getPrincipals()[0]).getEncoded();
                              var30 = (new ASN1InputStream(var29)).readObject();
                           } catch (Exception var57) {
                              throw new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", var57);
                           }
                        } catch (AnnotatedException var62) {
                           var44 = var62;
                           break label122;
                        }

                        DERObject var65 = var30;

                        try {
                           GeneralName var31 = new GeneralName(4, var65);
                           GeneralNames var32 = new GeneralNames(var31);
                           DistributionPointName var33 = new DistributionPointName(0, var32);
                           DistributionPoint var34 = new DistributionPoint(var33, (ReasonFlags)null, (GeneralNames)null);
                           ExtendedPKIXParameters var35 = (ExtendedPKIXParameters)var1.clone();
                           checkCRL(var34, var0, var35, var3, var2, var9, var10, var4);
                           break label90;
                        } catch (AnnotatedException var61) {
                           var44 = var61;
                        }
                     }

                     var41 = new AnnotatedException("No valid CRL for distribution point found.", var44);
                     var40 = var27;
                     break label121;
                  }

                  var40 = true;
                  var41 = var26;
               }
            } else {
               var40 = var27;
               var41 = var26;
            }

            if(!var40) {
               throw new ExtCertPathValidatorException("No valid CRL found.", var41);
            } else if(var9.getCertStatus() != 11) {
               StringBuilder var46 = (new StringBuilder()).append("Attribute certificate revocation after ");
               Date var47 = var9.getRevocationDate();
               String var48 = var46.append(var47).toString();
               StringBuilder var49 = (new StringBuilder()).append(var48).append(", reason: ");
               String[] var50 = RFC3280CertPathUtilities.crlReasons;
               int var51 = var9.getCertStatus();
               String var52 = var50[var51];
               String var53 = var49.append(var52).toString();
               throw new CertPathValidatorException(var53);
            } else {
               if(!var10.isAllReasons() && var9.getCertStatus() == 11) {
                  var9.setCertStatus(12);
               }

               if(var9.getCertStatus() == 12) {
                  throw new CertPathValidatorException("Attribute certificate status could not be determined.");
               }
            }
         } else {
            String var54 = CRL_DISTRIBUTION_POINTS;
            if(var0.getExtensionValue(var54) == null) {
               String var55 = AUTHORITY_INFO_ACCESS;
               if(var0.getExtensionValue(var55) == null) {
                  return;
               }
            }

            throw new CertPathValidatorException("No rev avail extension is set, but also an AC revocation pointer.");
         }
      }
   }

   protected static CertPath processAttrCert1(X509AttributeCertificate var0, ExtendedPKIXParameters var1) throws CertPathValidatorException {
      HashSet var2 = new HashSet();
      if(var0.getHolder().getIssuer() != null) {
         X509CertStoreSelector var3 = new X509CertStoreSelector();
         BigInteger var4 = var0.getHolder().getSerialNumber();
         var3.setSerialNumber(var4);
         Principal[] var5 = var0.getHolder().getIssuer();
         int var6 = 0;

         while(true) {
            int var7 = var5.length;
            if(var6 >= var7) {
               if(var2.isEmpty()) {
                  throw new CertPathValidatorException("Public key certificate specified in base certificate ID for attribute certificate cannot be found.");
               }
               break;
            }

            try {
               if(var5[var6] instanceof X500Principal) {
                  byte[] var8 = ((X500Principal)var5[var6]).getEncoded();
                  var3.setIssuer(var8);
               }

               List var9 = var1.getStores();
               Collection var10 = CertPathValidatorUtilities.findCertificates(var3, var9);
               var2.addAll(var10);
            } catch (AnnotatedException var46) {
               throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", var46);
            } catch (IOException var47) {
               throw new ExtCertPathValidatorException("Unable to encode X500 principal.", var47);
            }

            ++var6;
         }
      }

      if(var0.getHolder().getEntityNames() != null) {
         X509CertStoreSelector var48 = new X509CertStoreSelector();
         Principal[] var14 = var0.getHolder().getEntityNames();
         int var15 = 0;

         while(true) {
            int var16 = var14.length;
            if(var15 >= var16) {
               if(var2.isEmpty()) {
                  throw new CertPathValidatorException("Public key certificate specified in entity name for attribute certificate cannot be found.");
               }
               break;
            }

            try {
               if(var14[var15] instanceof X500Principal) {
                  byte[] var17 = ((X500Principal)var14[var15]).getEncoded();
                  var48.setIssuer(var17);
               }

               List var18 = var1.getStores();
               Collection var19 = CertPathValidatorUtilities.findCertificates(var48, var18);
               var2.addAll(var19);
            } catch (AnnotatedException var44) {
               throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", var44);
            } catch (IOException var45) {
               throw new ExtCertPathValidatorException("Unable to encode X500 principal.", var45);
            }

            ++var15;
         }
      }

      ExtendedPKIXBuilderParameters var23 = (ExtendedPKIXBuilderParameters)ExtendedPKIXBuilderParameters.getInstance(var1);
      Iterator var24 = var2.iterator();
      ExtCertPathValidatorException var25 = null;

      CertPathBuilderResult var26;
      ExtCertPathValidatorException var35;
      for(var26 = null; var24.hasNext(); var25 = var35) {
         X509CertStoreSelector var27 = new X509CertStoreSelector();
         X509Certificate var28 = (X509Certificate)var24.next();
         var27.setCertificate(var28);
         var23.setTargetConstraints(var27);

         CertPathBuilder var29;
         try {
            var29 = CertPathBuilder.getInstance("PKIX", "myBC");
         } catch (NoSuchProviderException var40) {
            throw new ExtCertPathValidatorException("Support class could not be created.", var40);
         } catch (NoSuchAlgorithmException var41) {
            throw new ExtCertPathValidatorException("Support class could not be created.", var41);
         }

         CertPathBuilder var30 = var29;

         CertPathBuilderResult var34;
         label62: {
            CertPathBuilderResult var49;
            try {
               ExtendedPKIXParameters var31 = ExtendedPKIXBuilderParameters.getInstance(var23);
               var49 = var30.build(var31);
            } catch (CertPathBuilderException var42) {
               var35 = new ExtCertPathValidatorException("Certification path for public key certificate of attribute certificate could not be build.", var42);
               var34 = var26;
               break label62;
            } catch (InvalidAlgorithmParameterException var43) {
               String var39 = var43.getMessage();
               throw new RuntimeException(var39);
            }

            var34 = var49;
            var35 = var25;
         }

         var26 = var34;
      }

      if(var25 != null) {
         throw var25;
      } else {
         return var26.getCertPath();
      }
   }

   protected static CertPathValidatorResult processAttrCert2(CertPath var0, ExtendedPKIXParameters var1) throws CertPathValidatorException {
      CertPathValidator var2;
      try {
         var2 = CertPathValidator.getInstance("PKIX", "myBC");
      } catch (NoSuchProviderException var10) {
         throw new ExtCertPathValidatorException("Support class could not be created.", var10);
      } catch (NoSuchAlgorithmException var11) {
         throw new ExtCertPathValidatorException("Support class could not be created.", var11);
      }

      CertPathValidator var3 = var2;

      try {
         CertPathValidatorResult var12 = var3.validate(var0, var1);
         return var12;
      } catch (CertPathValidatorException var8) {
         throw new ExtCertPathValidatorException("Certification path for issuer certificate of attribute certificate could not be validated.", var8);
      } catch (InvalidAlgorithmParameterException var9) {
         String var7 = var9.getMessage();
         throw new RuntimeException(var7);
      }
   }

   protected static void processAttrCert3(X509Certificate var0, ExtendedPKIXParameters var1) throws CertPathValidatorException {
      if(var0.getKeyUsage() != null && !var0.getKeyUsage()[0] && !var0.getKeyUsage()[1]) {
         throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
      } else if(var0.getBasicConstraints() != -1) {
         throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
      }
   }

   protected static void processAttrCert4(X509Certificate var0, ExtendedPKIXParameters var1) throws CertPathValidatorException {
      Set var2 = var1.getTrustedACIssuers();
      boolean var3 = false;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         TrustAnchor var5 = (TrustAnchor)var4.next();
         String var6 = var0.getSubjectX500Principal().getName("RFC2253");
         String var7 = var5.getCAName();
         if(!var6.equals(var7)) {
            X509Certificate var8 = var5.getTrustedCert();
            if(!var0.equals(var8)) {
               continue;
            }
         }

         var3 = true;
      }

      if(!var3) {
         throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
      }
   }

   protected static void processAttrCert5(X509AttributeCertificate var0, ExtendedPKIXParameters var1) throws CertPathValidatorException {
      try {
         Date var2 = CertPathValidatorUtilities.getValidDate(var1);
         var0.checkValidity(var2);
      } catch (CertificateExpiredException var5) {
         throw new ExtCertPathValidatorException("Attribute certificate is not valid.", var5);
      } catch (CertificateNotYetValidException var6) {
         throw new ExtCertPathValidatorException("Attribute certificate is not valid.", var6);
      }
   }

   protected static void processAttrCert7(X509AttributeCertificate var0, CertPath var1, CertPath var2, ExtendedPKIXParameters var3) throws CertPathValidatorException {
      Set var4 = var0.getCriticalExtensionOIDs();
      String var5 = TARGET_INFORMATION;
      if(var4.contains(var5)) {
         try {
            String var6 = TARGET_INFORMATION;
            TargetInformation var7 = TargetInformation.getInstance(CertPathValidatorUtilities.getExtensionValue(var0, var6));
         } catch (AnnotatedException var14) {
            throw new ExtCertPathValidatorException("Target information extension could not be read.", var14);
         } catch (IllegalArgumentException var15) {
            throw new ExtCertPathValidatorException("Target information extension could not be read.", var15);
         }
      }

      String var8 = TARGET_INFORMATION;
      var4.remove(var8);
      Iterator var10 = var3.getAttrCertCheckers().iterator();

      while(var10.hasNext()) {
         ((PKIXAttrCertChecker)var10.next()).check(var0, var1, var2, var4);
      }

      if(!var4.isEmpty()) {
         String var13 = "Attribute certificate contains unsupported critical extensions: " + var4;
         throw new CertPathValidatorException(var13);
      }
   }
}

package myorg.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.CRLDistPoint;
import myorg.bouncycastle.asn1.x509.CRLNumber;
import myorg.bouncycastle.asn1.x509.CertificateList;
import myorg.bouncycastle.asn1.x509.DistributionPoint;
import myorg.bouncycastle.asn1.x509.DistributionPointName;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.PolicyInformation;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.jce.exception.ExtCertPathValidatorException;
import myorg.bouncycastle.jce.provider.AnnotatedException;
import myorg.bouncycastle.jce.provider.CertStatus;
import myorg.bouncycastle.jce.provider.PKIXPolicyNode;
import myorg.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import myorg.bouncycastle.jce.provider.X509CRLEntryObject;
import myorg.bouncycastle.jce.provider.X509CRLObject;
import myorg.bouncycastle.util.StoreException;
import myorg.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import myorg.bouncycastle.x509.ExtendedPKIXParameters;
import myorg.bouncycastle.x509.X509AttributeCertStoreSelector;
import myorg.bouncycastle.x509.X509AttributeCertificate;
import myorg.bouncycastle.x509.X509CRLStoreSelector;
import myorg.bouncycastle.x509.X509CertStoreSelector;
import myorg.bouncycastle.x509.X509Store;

public class CertPathValidatorUtilities {

   protected static final String ANY_POLICY = "2.5.29.32.0";
   protected static final String AUTHORITY_KEY_IDENTIFIER = X509Extensions.AuthorityKeyIdentifier.getId();
   protected static final String BASIC_CONSTRAINTS = X509Extensions.BasicConstraints.getId();
   protected static final String CERTIFICATE_POLICIES = X509Extensions.CertificatePolicies.getId();
   protected static final String CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
   protected static final String CRL_NUMBER = X509Extensions.CRLNumber.getId();
   protected static final int CRL_SIGN = 6;
   protected static final String DELTA_CRL_INDICATOR = X509Extensions.DeltaCRLIndicator.getId();
   protected static final String FRESHEST_CRL = X509Extensions.FreshestCRL.getId();
   protected static final String INHIBIT_ANY_POLICY = X509Extensions.InhibitAnyPolicy.getId();
   protected static final String ISSUING_DISTRIBUTION_POINT = X509Extensions.IssuingDistributionPoint.getId();
   protected static final int KEY_CERT_SIGN = 5;
   protected static final String KEY_USAGE = X509Extensions.KeyUsage.getId();
   protected static final String NAME_CONSTRAINTS = X509Extensions.NameConstraints.getId();
   protected static final String POLICY_CONSTRAINTS = X509Extensions.PolicyConstraints.getId();
   protected static final String POLICY_MAPPINGS = X509Extensions.PolicyMappings.getId();
   protected static final String SUBJECT_ALTERNATIVE_NAME = X509Extensions.SubjectAlternativeName.getId();
   protected static final String[] crlReasons;


   static {
      String[] var0 = new String[]{"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
      crlReasons = var0;
   }

   public CertPathValidatorUtilities() {}

   protected static void addAdditionalStoreFromLocation(String param0, ExtendedPKIXParameters param1) {
      // $FF: Couldn't be decompiled
   }

   protected static void addAdditionalStoresFromAltNames(X509Certificate var0, ExtendedPKIXParameters var1) throws CertificateParsingException {
      if(var0.getIssuerAlternativeNames() != null) {
         Iterator var2 = var0.getIssuerAlternativeNames().iterator();

         while(var2.hasNext()) {
            List var3 = (List)var2.next();
            Object var4 = var3.get(0);
            Integer var5 = new Integer(6);
            if(var4.equals(var5)) {
               addAdditionalStoreFromLocation((String)var3.get(1), var1);
            }
         }

      }
   }

   protected static void addAdditionalStoresFromCRLDistributionPoint(CRLDistPoint var0, ExtendedPKIXParameters var1) throws AnnotatedException {
      if(var0 != null) {
         DistributionPoint[] var2;
         try {
            var2 = var0.getDistributionPoints();
         } catch (Exception var11) {
            throw new AnnotatedException("Distribution points could not be read.", var11);
         }

         DistributionPoint[] var3 = var2;
         int var4 = 0;

         while(true) {
            int var5 = var3.length;
            if(var4 >= var5) {
               return;
            }

            DistributionPointName var6 = var3[var4].getDistributionPoint();
            if(var6 != null && var6.getType() == 0) {
               GeneralName[] var7 = GeneralNames.getInstance(var6.getName()).getNames();
               int var8 = 0;

               while(true) {
                  int var9 = var7.length;
                  if(var8 >= var9) {
                     break;
                  }

                  if(var7[var8].getTagNo() == 6) {
                     addAdditionalStoreFromLocation(DERIA5String.getInstance(var7[var8].getName()).getString(), var1);
                  }

                  ++var8;
               }
            }

            ++var4;
         }
      }
   }

   protected static final Collection findCRLs(X509CRLStoreSelector var0, List var1) throws AnnotatedException {
      HashSet var2 = new HashSet();
      Iterator var3 = var1.iterator();
      AnnotatedException var4 = null;
      boolean var5 = false;

      while(var3.hasNext()) {
         Object var6 = var3.next();
         if(var6 instanceof X509Store) {
            X509Store var16 = (X509Store)var6;

            try {
               Collection var8 = var16.getMatches(var0);
               var2.addAll(var8);
            } catch (StoreException var14) {
               var4 = new AnnotatedException("Exception searching in X.509 CRL store.", var14);
               continue;
            }

            var5 = true;
         } else {
            CertStore var7 = (CertStore)var6;

            try {
               Collection var11 = var7.getCRLs(var0);
               var2.addAll(var11);
            } catch (CertStoreException var15) {
               var4 = new AnnotatedException("Exception searching in X.509 CRL store.", var15);
               continue;
            }

            var5 = true;
         }
      }

      if(!var5 && var4 != null) {
         throw var4;
      } else {
         return var2;
      }
   }

   protected static Collection findCertificates(X509AttributeCertStoreSelector var0, List var1) throws AnnotatedException {
      HashSet var2 = new HashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if(var4 instanceof X509Store) {
            X509Store var5 = (X509Store)var4;

            try {
               Collection var6 = var5.getMatches(var0);
               var2.addAll(var6);
            } catch (StoreException var9) {
               throw new AnnotatedException("Problem while picking certificates from X.509 store.", var9);
            }
         }
      }

      return var2;
   }

   protected static Collection findCertificates(X509CertStoreSelector var0, List var1) throws AnnotatedException {
      HashSet var2 = new HashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if(var4 instanceof X509Store) {
            X509Store var5 = (X509Store)var4;

            try {
               Collection var6 = var5.getMatches(var0);
               var2.addAll(var6);
            } catch (StoreException var12) {
               throw new AnnotatedException("Problem while picking certificates from X.509 store.", var12);
            }
         } else {
            CertStore var14 = (CertStore)var4;

            try {
               Collection var9 = var14.getCertificates(var0);
               var2.addAll(var9);
            } catch (CertStoreException var13) {
               throw new AnnotatedException("Problem while picking certificates from certificate store.", var13);
            }
         }
      }

      return var2;
   }

   protected static Collection findIssuerCerts(X509Certificate var0, ExtendedPKIXBuilderParameters var1) throws AnnotatedException {
      X509CertStoreSelector var2 = new X509CertStoreSelector();
      HashSet var3 = new HashSet();

      try {
         byte[] var4 = var0.getIssuerX500Principal().getEncoded();
         var2.setSubject(var4);
      } catch (IOException var22) {
         throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", var22);
      }

      Iterator var15;
      try {
         ArrayList var5 = new ArrayList();
         List var6 = var1.getCertStores();
         Collection var7 = findCertificates(var2, var6);
         var5.addAll(var7);
         List var9 = var1.getStores();
         Collection var10 = findCertificates(var2, var9);
         var5.addAll(var10);
         List var12 = var1.getAdditionalStores();
         Collection var13 = findCertificates(var2, var12);
         var5.addAll(var13);
         var15 = var5.iterator();
      } catch (AnnotatedException var21) {
         throw new AnnotatedException("Issuer certificate cannot be searched.", var21);
      }

      Iterator var16 = var15;

      while(var16.hasNext()) {
         X509Certificate var17 = (X509Certificate)var16.next();
         var3.add(var17);
      }

      return var3;
   }

   protected static TrustAnchor findTrustAnchor(X509Certificate var0, Set var1) throws AnnotatedException {
      return findTrustAnchor(var0, var1, (String)null);
   }

   protected static TrustAnchor findTrustAnchor(X509Certificate var0, Set var1, String var2) throws AnnotatedException {
      TrustAnchor var3 = null;
      PublicKey var4 = null;
      Exception var5 = null;
      X509CertSelector var6 = new X509CertSelector();
      X500Principal var7 = getEncodedIssuerPrincipal(var0);

      try {
         byte[] var8 = var7.getEncoded();
         var6.setSubject(var8);
      } catch (IOException var17) {
         throw new AnnotatedException("Cannot set subject search criteria for trust anchor.", var17);
      }

      Iterator var9 = var1.iterator();

      while(var9.hasNext() && var3 == null) {
         var3 = (TrustAnchor)var9.next();
         if(var3.getTrustedCert() != null) {
            X509Certificate var10 = var3.getTrustedCert();
            if(var6.match(var10)) {
               var4 = var3.getTrustedCert().getPublicKey();
            } else {
               var3 = null;
            }
         } else if(var3.getCAName() != null && var3.getCAPublicKey() != null) {
            label54: {
               PublicKey var14;
               label53: {
                  try {
                     String var12 = var3.getCAName();
                     X500Principal var13 = new X500Principal(var12);
                     if(var7.equals(var13)) {
                        var14 = var3.getCAPublicKey();
                        break label53;
                     }
                  } catch (IllegalArgumentException var18) {
                     var3 = null;
                     break label54;
                  }

                  var3 = null;
                  break label54;
               }

               var4 = var14;
            }
         } else {
            var3 = null;
         }

         if(var4 != null) {
            try {
               verifyX509Certificate(var0, var4, var2);
            } catch (Exception var16) {
               var5 = var16;
               var3 = null;
            }
         }
      }

      if(var3 == null && var5 != null) {
         throw new AnnotatedException("TrustAnchor found but certificate validation failed.", var5);
      } else {
         return var3;
      }
   }

   protected static AlgorithmIdentifier getAlgorithmIdentifier(PublicKey var0) throws CertPathValidatorException {
      try {
         byte[] var1 = var0.getEncoded();
         AlgorithmIdentifier var2 = SubjectPublicKeyInfo.getInstance((new ASN1InputStream(var1)).readObject()).getAlgorithmId();
         return var2;
      } catch (Exception var4) {
         throw new ExtCertPathValidatorException("Subject public key cannot be decoded.", var4);
      }
   }

   protected static void getCRLIssuersFromDistributionPoint(DistributionPoint var0, Collection var1, X509CRLSelector var2, ExtendedPKIXParameters var3) throws AnnotatedException {
      ArrayList var4 = new ArrayList();
      if(var0.getCRLIssuer() != null) {
         GeneralName[] var5 = var0.getCRLIssuer().getNames();
         int var6 = 0;

         while(true) {
            int var7 = var5.length;
            if(var6 >= var7) {
               break;
            }

            if(var5[var6].getTagNo() == 4) {
               try {
                  byte[] var8 = var5[var6].getName().getDERObject().getEncoded();
                  X500Principal var9 = new X500Principal(var8);
                  var4.add(var9);
               } catch (IOException var19) {
                  throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", var19);
               }
            }

            ++var6;
         }
      } else {
         if(var0.getDistributionPoint() == null) {
            throw new AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
         }

         Iterator var12 = var1.iterator();

         while(var12.hasNext()) {
            X500Principal var13 = (X500Principal)var12.next();
            var4.add(var13);
         }
      }

      Iterator var15 = var4.iterator();

      while(var15.hasNext()) {
         try {
            byte[] var16 = ((X500Principal)var15.next()).getEncoded();
            var2.addIssuerName(var16);
         } catch (IOException var18) {
            throw new AnnotatedException("Cannot decode CRL issuer information.", var18);
         }
      }

   }

   protected static void getCertStatus(Date var0, X509CRL var1, Object var2, CertStatus var3) throws AnnotatedException {
      X509CRLObject var6;
      try {
         ASN1Sequence var4 = (ASN1Sequence)ASN1Sequence.fromByteArray(var1.getEncoded());
         CertificateList var5 = new CertificateList(var4);
         var6 = new X509CRLObject(var5);
      } catch (Exception var25) {
         throw new AnnotatedException("Bouncy Castle X509CRLObject could not be created.", var25);
      }

      BigInteger var7 = getSerialNumber(var2);
      X509CRLEntryObject var8 = (X509CRLEntryObject)var6.getRevokedCertificate(var7);
      if(var8 != null) {
         X500Principal var9 = getEncodedIssuerPrincipal(var2);
         X500Principal var10 = var8.getCertificateIssuer();
         if(!var9.equals(var10)) {
            X500Principal var11 = getEncodedIssuerPrincipal(var2);
            X500Principal var12 = getIssuerPrincipal(var1);
            if(!var11.equals(var12)) {
               return;
            }
         }

         DEREnumerated var13 = null;
         if(var8.hasExtensions()) {
            label43: {
               DEREnumerated var15;
               try {
                  String var14 = X509Extensions.ReasonCode.getId();
                  var15 = DEREnumerated.getInstance(getExtensionValue(var8, var14));
               } catch (Exception var26) {
                  new AnnotatedException("Reason code CRL entry extension could not be decoded.", var26);
                  break label43;
               }

               var13 = var15;
            }
         }

         long var16 = var0.getTime();
         long var18 = var8.getRevocationDate().getTime();
         if(var16 >= var18 || var13 == null || var13.getValue().intValue() == 0 || var13.getValue().intValue() == 1 || var13.getValue().intValue() == 2 || var13.getValue().intValue() == 8) {
            if(var13 != null) {
               int var20 = var13.getValue().intValue();
               var3.setCertStatus(var20);
            } else {
               var3.setCertStatus(0);
            }

            Date var21 = var8.getRevocationDate();
            var3.setRevocationDate(var21);
         }
      }
   }

   protected static Set getCompleteCRLs(DistributionPoint var0, Object var1, Date var2, ExtendedPKIXParameters var3) throws AnnotatedException {
      X509CRLStoreSelector var4 = new X509CRLStoreSelector();

      try {
         HashSet var5 = new HashSet();
         if(var1 instanceof X509AttributeCertificate) {
            Principal var6 = ((X509AttributeCertificate)var1).getIssuer().getPrincipals()[0];
            var5.add(var6);
         } else {
            X500Principal var28 = getEncodedIssuerPrincipal(var1);
            var5.add(var28);
         }

         getCRLIssuersFromDistributionPoint(var0, var5, var4, var3);
      } catch (AnnotatedException var41) {
         new AnnotatedException("Could not get issuer information from distribution point.", var41);
      }

      if(var1 instanceof X509Certificate) {
         X509Certificate var12 = (X509Certificate)var1;
         var4.setCertificateChecking(var12);
      } else if(var1 instanceof X509AttributeCertificate) {
         X509AttributeCertificate var32 = (X509AttributeCertificate)var1;
         var4.setAttrCertificateChecking(var32);
      }

      if(var3.getDate() != null) {
         Date var13 = var3.getDate();
         var4.setDateAndTime(var13);
      } else {
         var4.setDateAndTime(var2);
      }

      var4.setCompleteCRLEnabled((boolean)1);
      HashSet var14 = new HashSet();

      try {
         List var15 = var3.getStores();
         Collection var16 = findCRLs(var4, var15);
         var14.addAll(var16);
         List var18 = var3.getAdditionalStores();
         Collection var19 = findCRLs(var4, var18);
         var14.addAll(var19);
         List var21 = var3.getCertStores();
         Collection var22 = findCRLs(var4, var21);
         var14.addAll(var22);
      } catch (AnnotatedException var40) {
         throw new AnnotatedException("Could not search for CRLs.", var40);
      }

      if(var14.isEmpty()) {
         if(var1 instanceof X509AttributeCertificate) {
            X509AttributeCertificate var24 = (X509AttributeCertificate)var1;
            StringBuilder var25 = (new StringBuilder()).append("No CRLs found for issuer \"");
            Principal var26 = var24.getIssuer().getPrincipals()[0];
            String var27 = var25.append(var26).append("\"").toString();
            throw new AnnotatedException(var27);
         } else {
            X509Certificate var36 = (X509Certificate)var1;
            StringBuilder var37 = (new StringBuilder()).append("No CRLs found for issuer \"");
            X500Principal var38 = var36.getIssuerX500Principal();
            String var39 = var37.append(var38).append("\"").toString();
            throw new AnnotatedException(var39);
         }
      } else {
         return var14;
      }
   }

   protected static Set getDeltaCRLs(Date var0, ExtendedPKIXParameters var1, X509CRL var2) throws AnnotatedException {
      X509CRLStoreSelector var3 = new X509CRLStoreSelector();
      if(var1.getDate() != null) {
         Date var4 = var1.getDate();
         var3.setDateAndTime(var4);
      } else {
         var3.setDateAndTime(var0);
      }

      try {
         byte[] var5 = getIssuerPrincipal(var2).getEncoded();
         var3.addIssuerName(var5);
      } catch (IOException var35) {
         new AnnotatedException("Cannot extract issuer from CRL.", var35);
      }

      BigInteger var6 = null;

      label52: {
         BigInteger var9;
         try {
            String var7 = CRL_NUMBER;
            DERObject var8 = getExtensionValue(var2, var7);
            if(var8 == null) {
               break label52;
            }

            var9 = CRLNumber.getInstance(var8).getPositiveValue();
         } catch (Exception var36) {
            throw new AnnotatedException("CRL number extension could not be extracted from CRL.", var36);
         }

         var6 = var9;
      }

      byte[] var37;
      try {
         String var10 = ISSUING_DISTRIBUTION_POINT;
         var37 = var2.getExtensionValue(var10);
      } catch (Exception var34) {
         throw new AnnotatedException("Issuing distribution point extension value could not be read.", var34);
      }

      BigInteger var12;
      if(var6 == null) {
         var12 = null;
      } else {
         BigInteger var31 = BigInteger.valueOf(1L);
         var12 = var6.add(var31);
      }

      var3.setMinCRLNumber(var12);
      var3.setIssuingDistributionPoint(var37);
      var3.setIssuingDistributionPointEnabled((boolean)1);
      var3.setMaxBaseCRLNumber(var6);
      HashSet var13 = new HashSet();

      try {
         List var14 = var1.getAdditionalStores();
         Collection var15 = findCRLs(var3, var14);
         var13.addAll(var15);
         List var17 = var1.getStores();
         Collection var18 = findCRLs(var3, var17);
         var13.addAll(var18);
         List var20 = var1.getCertStores();
         Collection var21 = findCRLs(var3, var20);
         var13.addAll(var21);
      } catch (AnnotatedException var33) {
         throw new AnnotatedException("Could not search for delta CRLs.", var33);
      }

      HashSet var23 = new HashSet();
      Iterator var24 = var13.iterator();

      while(var24.hasNext()) {
         X509CRL var25 = (X509CRL)var24.next();
         if(isDeltaCRL(var25)) {
            var23.add(var25);
         }
      }

      return var23;
   }

   protected static X500Principal getEncodedIssuerPrincipal(Object var0) {
      X500Principal var1;
      if(var0 instanceof X509Certificate) {
         var1 = ((X509Certificate)var0).getIssuerX500Principal();
      } else {
         var1 = (X500Principal)((X509AttributeCertificate)var0).getIssuer().getPrincipals()[0];
      }

      return var1;
   }

   protected static DERObject getExtensionValue(X509Extension var0, String var1) throws AnnotatedException {
      byte[] var2 = var0.getExtensionValue(var1);
      DERObject var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = getObject(var1, var2);
      }

      return var3;
   }

   protected static X500Principal getIssuerPrincipal(X509CRL var0) {
      return var0.getIssuerX500Principal();
   }

   protected static PublicKey getNextWorkingKey(List var0, int var1) throws CertPathValidatorException {
      PublicKey var2 = ((Certificate)var0.get(var1)).getPublicKey();
      Object var3;
      if(!(var2 instanceof DSAPublicKey)) {
         var3 = var2;
      } else {
         DSAPublicKey var4 = (DSAPublicKey)var2;
         if(var4.getParams() != null) {
            var3 = var4;
         } else {
            int var5 = var1 + 1;

            while(true) {
               int var6 = var0.size();
               if(var5 >= var6) {
                  throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
               }

               var2 = ((X509Certificate)var0.get(var5)).getPublicKey();
               if(!(var2 instanceof DSAPublicKey)) {
                  throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
               }

               DSAPublicKey var7 = (DSAPublicKey)var2;
               if(var7.getParams() != null) {
                  DSAParams var8 = var7.getParams();
                  BigInteger var9 = var4.getY();
                  BigInteger var10 = var8.getP();
                  BigInteger var11 = var8.getQ();
                  BigInteger var12 = var8.getG();
                  DSAPublicKeySpec var13 = new DSAPublicKeySpec(var9, var10, var11, var12);

                  PublicKey var14;
                  try {
                     var14 = KeyFactory.getInstance("DSA", "myBC").generatePublic(var13);
                  } catch (Exception var16) {
                     String var15 = var16.getMessage();
                     throw new RuntimeException(var15);
                  }

                  var3 = var14;
                  break;
               }

               ++var5;
            }
         }
      }

      return (PublicKey)var3;
   }

   private static DERObject getObject(String var0, byte[] var1) throws AnnotatedException {
      try {
         ASN1InputStream var2 = new ASN1InputStream(var1);
         ASN1OctetString var3 = (ASN1OctetString)var2.readObject();
         if(var3 != null) {
            byte[] var4 = var3.getOctets();
            var2 = new ASN1InputStream(var4);
         }

         DERObject var5 = var2.readObject();
         return var5;
      } catch (Exception var8) {
         String var7 = "exception processing extension " + var0;
         throw new AnnotatedException(var7, var8);
      }
   }

   protected static final Set getQualifierSet(ASN1Sequence var0) throws CertPathValidatorException {
      HashSet var1 = new HashSet();
      if(var0 != null) {
         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         ASN1OutputStream var3 = new ASN1OutputStream(var2);

         for(Enumeration var4 = var0.getObjects(); var4.hasMoreElements(); var2.reset()) {
            try {
               Object var5 = var4.nextElement();
               var3.writeObject(var5);
               byte[] var6 = var2.toByteArray();
               PolicyQualifierInfo var7 = new PolicyQualifierInfo(var6);
               var1.add(var7);
            } catch (IOException var10) {
               throw new ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", var10);
            }
         }
      }

      return var1;
   }

   private static BigInteger getSerialNumber(Object var0) {
      BigInteger var1;
      if(var0 instanceof X509Certificate) {
         var1 = ((X509Certificate)var0).getSerialNumber();
      } else {
         var1 = ((X509AttributeCertificate)var0).getSerialNumber();
      }

      return var1;
   }

   protected static X500Principal getSubjectPrincipal(X509Certificate var0) {
      return var0.getSubjectX500Principal();
   }

   protected static Date getValidCertDateFromValidityModel(ExtendedPKIXParameters var0, CertPath var1, int var2) throws AnnotatedException {
      Date var3;
      if(var0.getValidityModel() == 1) {
         if(var2 <= 0) {
            var3 = getValidDate(var0);
         } else if(var2 - 1 == 0) {
            DERGeneralizedTime var4 = null;

            label33: {
               DERGeneralizedTime var10;
               try {
                  List var5 = var1.getCertificates();
                  int var6 = var2 - 1;
                  X509Certificate var7 = (X509Certificate)var5.get(var6);
                  String var8 = ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId();
                  byte[] var9 = var7.getExtensionValue(var8);
                  if(var9 == null) {
                     break label33;
                  }

                  var10 = DERGeneralizedTime.getInstance(ASN1Object.fromByteArray(var9));
               } catch (IOException var19) {
                  throw new AnnotatedException("Date of cert gen extension could not be read.");
               } catch (IllegalArgumentException var20) {
                  throw new AnnotatedException("Date of cert gen extension could not be read.");
               }

               var4 = var10;
            }

            if(var4 != null) {
               Date var21;
               try {
                  var21 = var4.getDate();
               } catch (ParseException var18) {
                  throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", var18);
               }

               var3 = var21;
            } else {
               List var14 = var1.getCertificates();
               int var15 = var2 - 1;
               var3 = ((X509Certificate)var14.get(var15)).getNotBefore();
            }
         } else {
            List var16 = var1.getCertificates();
            int var17 = var2 - 1;
            var3 = ((X509Certificate)var16.get(var17)).getNotBefore();
         }
      } else {
         var3 = getValidDate(var0);
      }

      return var3;
   }

   protected static Date getValidDate(PKIXParameters var0) {
      Date var1 = var0.getDate();
      if(var1 == null) {
         var1 = new Date();
      }

      return var1;
   }

   protected static boolean isAnyPolicy(Set var0) {
      boolean var1;
      if(var0 != null && !var0.contains("2.5.29.32.0") && !var0.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static boolean isDeltaCRL(X509CRL var0) {
      Set var1 = var0.getCriticalExtensionOIDs();
      String var2 = RFC3280CertPathUtilities.DELTA_CRL_INDICATOR;
      return var1.contains(var2);
   }

   protected static boolean isSelfIssued(X509Certificate var0) {
      Principal var1 = var0.getSubjectDN();
      Principal var2 = var0.getIssuerDN();
      return var1.equals(var2);
   }

   protected static void prepareNextCertB1(int var0, List[] var1, String var2, Map var3, X509Certificate var4) throws AnnotatedException, CertPathValidatorException {
      Iterator var5 = var1[var0].iterator();

      PKIXPolicyNode var6;
      while(var5.hasNext()) {
         var6 = (PKIXPolicyNode)var5.next();
         if(var6.getValidPolicy().equals(var2)) {
            Set var7 = (Set)var3.get(var2);
            var6.expectedPolicies = var7;
            break;
         }
      }

      if(true) {
         Iterator var8 = var1[var0].iterator();

         while(var8.hasNext()) {
            var6 = (PKIXPolicyNode)var8.next();
            String var9 = var6.getValidPolicy();
            if("2.5.29.32.0".equals(var9)) {
               Set var10 = null;

               ASN1Sequence var12;
               try {
                  String var11 = CERTIFICATE_POLICIES;
                  var12 = DERSequence.getInstance(getExtensionValue(var4, var11));
               } catch (Exception var36) {
                  throw new AnnotatedException("Certificate policies cannot be decoded.", var36);
               }

               Enumeration var13 = var12.getObjects();

               while(var13.hasMoreElements()) {
                  PolicyInformation var14;
                  try {
                     var14 = PolicyInformation.getInstance(var13.nextElement());
                  } catch (Exception var35) {
                     throw new AnnotatedException("Policy information cannot be decoded.", var35);
                  }

                  PolicyInformation var15 = var14;
                  String var16 = var14.getPolicyIdentifier().getId();
                  if("2.5.29.32.0".equals(var16)) {
                     Set var37;
                     try {
                        var37 = getQualifierSet(var15.getPolicyQualifiers());
                     } catch (CertPathValidatorException var34) {
                        throw new ExtCertPathValidatorException("Policy qualifier info set could not be built.", var34);
                     }

                     var10 = var37;
                     break;
                  }
               }

               byte var17 = 0;
               if(var4.getCriticalExtensionOIDs() != null) {
                  Set var18 = var4.getCriticalExtensionOIDs();
                  String var19 = CERTIFICATE_POLICIES;
                  var17 = var18.contains(var19);
               }

               PKIXPolicyNode var20 = (PKIXPolicyNode)var6.getParent();
               String var21 = var20.getValidPolicy();
               if("2.5.29.32.0".equals(var21)) {
                  ArrayList var22 = new ArrayList();
                  Set var23 = (Set)var3.get(var2);
                  PKIXPolicyNode var26 = new PKIXPolicyNode(var22, var0, var23, var20, var10, var2, (boolean)var17);
                  var20.addChild(var26);
                  boolean var27 = var1[var0].add(var26);
                  return;
               }
               break;
            }
         }

      }
   }

   protected static PKIXPolicyNode prepareNextCertB2(int var0, List[] var1, String var2, PKIXPolicyNode var3) {
      Iterator var4 = var1[var0].iterator();

      while(var4.hasNext()) {
         PKIXPolicyNode var5 = (PKIXPolicyNode)var4.next();
         if(var5.getValidPolicy().equals(var2)) {
            ((PKIXPolicyNode)var5.getParent()).removeChild(var5);
            var4.remove();
            int var6 = var0 - 1;

            while(var6 >= 0) {
               List var7 = var1[var6];
               int var8 = 0;

               while(true) {
                  int var9 = var7.size();
                  if(var8 < var9) {
                     label21: {
                        PKIXPolicyNode var10 = (PKIXPolicyNode)var7.get(var8);
                        if(!var10.hasChildren()) {
                           var3 = removePolicyNode(var3, var1, var10);
                           if(var3 == null) {
                              break label21;
                           }
                        }

                        ++var8;
                        continue;
                     }
                  }

                  var6 += -1;
                  break;
               }
            }
         }
      }

      return var3;
   }

   protected static boolean processCertD1i(int var0, List[] var1, DERObjectIdentifier var2, Set var3) {
      int var4 = var0 - 1;
      List var5 = var1[var4];
      int var6 = 0;

      boolean var20;
      while(true) {
         int var7 = var5.size();
         if(var6 >= var7) {
            var20 = false;
            break;
         }

         PKIXPolicyNode var8 = (PKIXPolicyNode)var5.get(var6);
         Set var9 = var8.getExpectedPolicies();
         String var10 = var2.getId();
         if(var9.contains(var10)) {
            HashSet var11 = new HashSet();
            String var12 = var2.getId();
            var11.add(var12);
            ArrayList var14 = new ArrayList();
            String var15 = var2.getId();
            PKIXPolicyNode var18 = new PKIXPolicyNode(var14, var0, var11, var8, var3, var15, (boolean)0);
            var8.addChild(var18);
            boolean var19 = var1[var0].add(var18);
            var20 = true;
            break;
         }

         ++var6;
      }

      return var20;
   }

   protected static void processCertD1ii(int var0, List[] var1, DERObjectIdentifier var2, Set var3) {
      int var4 = var0 - 1;
      List var5 = var1[var4];
      int var6 = 0;

      while(true) {
         int var7 = var5.size();
         if(var6 >= var7) {
            return;
         }

         PKIXPolicyNode var8 = (PKIXPolicyNode)var5.get(var6);
         String var9 = var8.getValidPolicy();
         if("2.5.29.32.0".equals(var9)) {
            HashSet var10 = new HashSet();
            String var11 = var2.getId();
            var10.add(var11);
            ArrayList var13 = new ArrayList();
            String var14 = var2.getId();
            PKIXPolicyNode var17 = new PKIXPolicyNode(var13, var0, var10, var8, var3, var14, (boolean)0);
            var8.addChild(var17);
            boolean var18 = var1[var0].add(var17);
            return;
         }

         ++var6;
      }
   }

   protected static PKIXPolicyNode removePolicyNode(PKIXPolicyNode var0, List[] var1, PKIXPolicyNode var2) {
      PKIXPolicyNode var3 = (PKIXPolicyNode)var2.getParent();
      PKIXPolicyNode var4;
      if(var0 == null) {
         var4 = null;
      } else if(var3 == null) {
         int var5 = 0;

         while(true) {
            int var6 = var1.length;
            if(var5 >= var6) {
               var4 = null;
               break;
            }

            ArrayList var7 = new ArrayList();
            var1[var5] = var7;
            ++var5;
         }
      } else {
         var3.removeChild(var2);
         removePolicyNodeRecurse(var1, var2);
         var4 = var0;
      }

      return var4;
   }

   private static void removePolicyNodeRecurse(List[] var0, PKIXPolicyNode var1) {
      int var2 = var1.getDepth();
      boolean var3 = var0[var2].remove(var1);
      if(var1.hasChildren()) {
         Iterator var4 = var1.getChildren();

         while(var4.hasNext()) {
            PKIXPolicyNode var5 = (PKIXPolicyNode)var4.next();
            removePolicyNodeRecurse(var0, var5);
         }

      }
   }

   protected static void verifyX509Certificate(X509Certificate var0, PublicKey var1, String var2) throws GeneralSecurityException {
      if(var2 == null) {
         var0.verify(var1);
      } else {
         var0.verify(var1, var2);
      }
   }
}

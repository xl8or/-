package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.BasicConstraints;
import myorg.bouncycastle.asn1.x509.CRLDistPoint;
import myorg.bouncycastle.asn1.x509.DistributionPoint;
import myorg.bouncycastle.asn1.x509.DistributionPointName;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.GeneralSubtree;
import myorg.bouncycastle.asn1.x509.IssuingDistributionPoint;
import myorg.bouncycastle.asn1.x509.NameConstraints;
import myorg.bouncycastle.asn1.x509.PolicyInformation;
import myorg.bouncycastle.asn1.x509.ReasonFlags;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.exception.ExtCertPathValidatorException;
import myorg.bouncycastle.jce.provider.AnnotatedException;
import myorg.bouncycastle.jce.provider.CertPathValidatorUtilities;
import myorg.bouncycastle.jce.provider.CertStatus;
import myorg.bouncycastle.jce.provider.PKIXNameConstraintValidator;
import myorg.bouncycastle.jce.provider.PKIXNameConstraintValidatorException;
import myorg.bouncycastle.jce.provider.PKIXPolicyNode;
import myorg.bouncycastle.jce.provider.ReasonsMask;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import myorg.bouncycastle.x509.ExtendedPKIXParameters;
import myorg.bouncycastle.x509.X509CRLStoreSelector;
import myorg.bouncycastle.x509.X509CertStoreSelector;

public class RFC3280CertPathUtilities {

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

   public RFC3280CertPathUtilities() {}

   private static void checkCRL(DistributionPoint param0, ExtendedPKIXParameters param1, X509Certificate param2, Date param3, X509Certificate param4, PublicKey param5, CertStatus param6, ReasonsMask param7, List param8) throws AnnotatedException {
      // $FF: Couldn't be decompiled
   }

   protected static void checkCRLs(ExtendedPKIXParameters var0, X509Certificate var1, Date var2, X509Certificate var3, PublicKey var4, List var5) throws AnnotatedException {
      boolean var6 = false;

      CRLDistPoint var8;
      try {
         String var7 = CRL_DISTRIBUTION_POINTS;
         var8 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var1, var7));
      } catch (Exception var61) {
         throw new AnnotatedException("CRL distribution point extension could not be read.", var61);
      }

      CRLDistPoint var9 = var8;

      try {
         CertPathValidatorUtilities.addAdditionalStoresFromCRLDistributionPoint(var9, var0);
      } catch (AnnotatedException var60) {
         throw new AnnotatedException("No additional CRL locations could be decoded from CRL distribution point extension.", var60);
      }

      CertStatus var10;
      ReasonsMask var11;
      boolean var31;
      AnnotatedException var30;
      label114: {
         var10 = new CertStatus();
         var11 = new ReasonsMask();
         boolean var12 = false;
         if(var8 != null) {
            DistributionPoint[] var65;
            try {
               var65 = var9.getDistributionPoints();
            } catch (Exception var59) {
               throw new AnnotatedException("Distribution points could not be read.", var59);
            }

            DistributionPoint[] var13 = var65;
            if(var65 != null) {
               int var14 = 0;
               boolean var15 = var12;
               AnnotatedException var16 = null;

               while(true) {
                  int var17 = var13.length;
                  if(var14 >= var17 || var10.getCertStatus() != 11 || var11.isAllReasons()) {
                     var30 = var16;
                     var31 = var15;
                     break label114;
                  }

                  ExtendedPKIXParameters var18 = (ExtendedPKIXParameters)var0.clone();

                  AnnotatedException var25;
                  label104: {
                     try {
                        DistributionPoint var19 = var13[var14];
                        checkCRL(var19, var18, var1, var2, var3, var4, var10, var11, var5);
                     } catch (AnnotatedException var64) {
                        var6 = var15;
                        var25 = var64;
                        break label104;
                     }

                     var6 = true;
                     var25 = var16;
                  }

                  ++var14;
                  var15 = var6;
                  var16 = var25;
               }
            }
         }

         var31 = var12;
         var30 = null;
      }

      AnnotatedException var46;
      boolean var45;
      if(var10.getCertStatus() == 11 && !var11.isAllReasons()) {
         label120: {
            boolean var32 = false;

            label87: {
               AnnotatedException var47;
               label121: {
                  DERObject var34;
                  try {
                     try {
                        byte[] var33 = CertPathValidatorUtilities.getEncodedIssuerPrincipal(var1).getEncoded();
                        var34 = (new ASN1InputStream(var33)).readObject();
                     } catch (Exception var58) {
                        throw new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", var58);
                     }
                  } catch (AnnotatedException var63) {
                     var47 = var63;
                     break label121;
                  }

                  DERObject var66 = var34;

                  try {
                     GeneralName var35 = new GeneralName(4, var66);
                     GeneralNames var36 = new GeneralNames(var35);
                     DistributionPointName var37 = new DistributionPointName(0, var36);
                     DistributionPoint var38 = new DistributionPoint(var37, (ReasonFlags)null, (GeneralNames)null);
                     ExtendedPKIXParameters var39 = (ExtendedPKIXParameters)var0.clone();
                     checkCRL(var38, var39, var1, var2, var3, var4, var10, var11, var5);
                     break label87;
                  } catch (AnnotatedException var62) {
                     var47 = var62;
                  }
               }

               var46 = var47;
               var45 = var31;
               break label120;
            }

            var45 = true;
            var46 = var30;
         }
      } else {
         var45 = var31;
         var46 = var30;
      }

      if(!var45) {
         if(var46 instanceof AnnotatedException) {
            throw var46;
         } else {
            throw new AnnotatedException("No valid CRL found.", var46);
         }
      } else if(var10.getCertStatus() != 11) {
         StringBuilder var49 = (new StringBuilder()).append("Certificate revocation after ");
         Date var50 = var10.getRevocationDate();
         String var51 = var49.append(var50).toString();
         StringBuilder var52 = (new StringBuilder()).append(var51).append(", reason: ");
         String[] var53 = crlReasons;
         int var54 = var10.getCertStatus();
         String var55 = var53[var54];
         String var56 = var52.append(var55).toString();
         throw new AnnotatedException(var56);
      } else {
         if(!var11.isAllReasons() && var10.getCertStatus() == 11) {
            var10.setCertStatus(12);
         }

         if(var10.getCertStatus() == 12) {
            throw new AnnotatedException("Certificate status could not be determined.");
         }
      }
   }

   protected static PKIXPolicyNode prepareCertB(CertPath var0, int var1, List[] var2, PKIXPolicyNode var3, int var4) throws CertPathValidatorException {
      List var5 = var0.getCertificates();
      X509Certificate var8 = (X509Certificate)var5.get(var1);
      int var9 = var5.size() - var1;

      ASN1Sequence var11;
      try {
         String var10 = POLICY_MAPPINGS;
         var11 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var8, var10));
      } catch (AnnotatedException var93) {
         ExtCertPathValidatorException var28 = new ExtCertPathValidatorException;
         String var30 = "Policy mappings extension could not be decoded.";
         var28.<init>(var30, var93, var0, var1);
         throw var28;
      }

      PKIXPolicyNode var13 = var3;
      if(var11 != null) {
         ASN1Sequence var14 = var11;
         HashMap var15 = new HashMap();
         HashSet var16 = new HashSet();
         int var17 = 0;

         while(true) {
            int var18 = var14.size();
            if(var17 >= var18) {
               Iterator var37 = var16.iterator();

               while(var37.hasNext()) {
                  String var38 = (String)var37.next();
                  if(var4 > 0) {
                     boolean var39 = false;
                     Iterator var96 = var2[var9].iterator();

                     PKIXPolicyNode var40;
                     while(var96.hasNext()) {
                        var40 = (PKIXPolicyNode)var96.next();
                        if(var40.getValidPolicy().equals(var38)) {
                           var39 = true;
                           Set var41 = (Set)var15.get(var38);
                           var40.expectedPolicies = var41;
                           break;
                        }
                     }

                     if(!var39) {
                        Iterator var42 = var2[var9].iterator();

                        while(var42.hasNext()) {
                           var40 = (PKIXPolicyNode)var42.next();
                           String var43 = var40.getValidPolicy();
                           if("2.5.29.32.0".equals(var43)) {
                              Set var94 = null;

                              ASN1Sequence var46;
                              try {
                                 String var45 = CERTIFICATE_POLICIES;
                                 var46 = (ASN1Sequence)CertPathValidatorUtilities.getExtensionValue(var8, var45);
                              } catch (AnnotatedException var92) {
                                 ExtCertPathValidatorException var61 = new ExtCertPathValidatorException;
                                 String var63 = "Certificate policies extension could not be decoded.";
                                 var61.<init>(var63, var92, var0, var1);
                                 throw var61;
                              }

                              Enumeration var47 = var46.getObjects();

                              while(var47.hasMoreElements()) {
                                 PolicyInformation var48;
                                 try {
                                    var48 = PolicyInformation.getInstance(var47.nextElement());
                                 } catch (Exception var91) {
                                    CertPathValidatorException var68 = new CertPathValidatorException;
                                    String var70 = "Policy information could not be decoded.";
                                    var68.<init>(var70, var91, var0, var1);
                                    throw var68;
                                 }

                                 PolicyInformation var49 = var48;
                                 String var50 = var48.getPolicyIdentifier().getId();
                                 if("2.5.29.32.0".equals(var50)) {
                                    Set var95;
                                    try {
                                       var95 = CertPathValidatorUtilities.getQualifierSet(var49.getPolicyQualifiers());
                                    } catch (CertPathValidatorException var90) {
                                       ExtCertPathValidatorException var75 = new ExtCertPathValidatorException;
                                       String var77 = "Policy qualifier info set could not be decoded.";
                                       var75.<init>(var77, var90, var0, var1);
                                       throw var75;
                                    }

                                    var94 = var95;
                                    break;
                                 }
                              }

                              byte var51 = 0;
                              if(var8.getCriticalExtensionOIDs() != null) {
                                 Set var52 = var8.getCriticalExtensionOIDs();
                                 String var53 = CERTIFICATE_POLICIES;
                                 var51 = var52.contains(var53);
                              }

                              PKIXPolicyNode var97 = (PKIXPolicyNode)var40.getParent();
                              String var54 = var97.getValidPolicy();
                              if("2.5.29.32.0".equals(var54)) {
                                 ArrayList var55 = new ArrayList();
                                 Set var56 = (Set)var15.get(var38);
                                 PKIXPolicyNode var57 = new PKIXPolicyNode(var55, var9, var56, var97, var94, var38, (boolean)var51);
                                 var97.addChild(var57);
                                 boolean var58 = var2[var9].add(var57);
                              }
                              break;
                           }
                        }
                     }
                  } else if(var4 <= 0) {
                     Iterator var44 = var2[var9].iterator();

                     while(var44.hasNext()) {
                        PKIXPolicyNode var81 = (PKIXPolicyNode)var44.next();
                        if(var81.getValidPolicy().equals(var38)) {
                           ((PKIXPolicyNode)var81.getParent()).removeChild(var81);
                           var44.remove();
                           int var99 = var9 - 1;

                           while(var99 >= 0) {
                              List var98 = var2[var99];
                              int var82 = 0;

                              while(true) {
                                 int var83 = var98.size();
                                 if(var82 < var83) {
                                    label112: {
                                       PKIXPolicyNode var84 = (PKIXPolicyNode)var98.get(var82);
                                       if(!var84.hasChildren()) {
                                          var13 = CertPathValidatorUtilities.removePolicyNode(var13, var2, var84);
                                          if(var13 == null) {
                                             break label112;
                                          }
                                       }

                                       ++var82;
                                       continue;
                                    }
                                 }

                                 var99 += -1;
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
               break;
            }

            ASN1Sequence var19 = (ASN1Sequence)var14.getObjectAt(var17);
            String var20 = ((DERObjectIdentifier)var19.getObjectAt(0)).getId();
            String var21 = ((DERObjectIdentifier)var19.getObjectAt(1)).getId();
            if(!var15.containsKey(var20)) {
               HashSet var22 = new HashSet();
               var22.add(var21);
               var15.put(var20, var22);
               var16.add(var20);
            } else {
               Set var34 = (Set)var15.get(var20);
               var34.add(var21);
            }

            ++var17;
         }
      }

      return var13;
   }

   protected static void prepareNextCertA(CertPath var0, int var1) throws CertPathValidatorException {
      X509Certificate var2 = (X509Certificate)var0.getCertificates().get(var1);

      ASN1Sequence var4;
      try {
         String var3 = POLICY_MAPPINGS;
         var4 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var2, var3));
      } catch (AnnotatedException var17) {
         throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", var17, var0, var1);
      }

      if(var4 != null) {
         ASN1Sequence var6 = var4;
         int var7 = 0;

         while(true) {
            int var8 = var6.size();
            if(var7 >= var8) {
               return;
            }

            DERObjectIdentifier var10;
            DERObjectIdentifier var18;
            try {
               ASN1Sequence var9 = DERSequence.getInstance(var6.getObjectAt(var7));
               var10 = DERObjectIdentifier.getInstance(var9.getObjectAt(0));
               var18 = DERObjectIdentifier.getInstance(var9.getObjectAt(1));
            } catch (Exception var16) {
               throw new ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", var16, var0, var1);
            }

            String var12 = var10.getId();
            if("2.5.29.32.0".equals(var12)) {
               throw new CertPathValidatorException("IssuerDomainPolicy is anyPolicy", (Throwable)null, var0, var1);
            }

            String var15 = var18.getId();
            if("2.5.29.32.0".equals(var15)) {
               throw new CertPathValidatorException("SubjectDomainPolicy is anyPolicy,", (Throwable)null, var0, var1);
            }

            ++var7;
         }
      }
   }

   protected static void prepareNextCertG(CertPath var0, int var1, PKIXNameConstraintValidator var2) throws CertPathValidatorException {
      X509Certificate var3 = (X509Certificate)var0.getCertificates().get(var1);
      NameConstraints var4 = null;

      try {
         String var5 = NAME_CONSTRAINTS;
         ASN1Sequence var6 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, var5));
         if(var6 != null) {
            var4 = new NameConstraints(var6);
         }
      } catch (Exception var16) {
         throw new ExtCertPathValidatorException("Name constraints extension could not be decoded.", var16, var0, var1);
      }

      if(var4 != null) {
         ASN1Sequence var7 = var4.getPermittedSubtrees();
         if(var7 != null) {
            try {
               var2.intersectPermittedSubtree(var7);
            } catch (Exception var14) {
               throw new ExtCertPathValidatorException("Permitted subtrees cannot be build from name constraints extension.", var14, var0, var1);
            }
         }

         ASN1Sequence var8 = var4.getExcludedSubtrees();
         if(var8 != null) {
            Enumeration var9 = var8.getObjects();

            try {
               while(var9.hasMoreElements()) {
                  GeneralSubtree var10 = GeneralSubtree.getInstance(var9.nextElement());
                  var2.addExcludedSubtree(var10);
               }

            } catch (Exception var15) {
               throw new ExtCertPathValidatorException("Excluded subtrees cannot be build from name constraints extension.", var15, var0, var1);
            }
         }
      }
   }

   protected static int prepareNextCertH1(CertPath var0, int var1, int var2) {
      int var3;
      if(!CertPathValidatorUtilities.isSelfIssued((X509Certificate)var0.getCertificates().get(var1)) && var2 != 0) {
         var3 = var2 - 1;
      } else {
         var3 = var2;
      }

      return var3;
   }

   protected static int prepareNextCertH2(CertPath var0, int var1, int var2) {
      int var3;
      if(!CertPathValidatorUtilities.isSelfIssued((X509Certificate)var0.getCertificates().get(var1)) && var2 != 0) {
         var3 = var2 - 1;
      } else {
         var3 = var2;
      }

      return var3;
   }

   protected static int prepareNextCertH3(CertPath var0, int var1, int var2) {
      int var3;
      if(!CertPathValidatorUtilities.isSelfIssued((X509Certificate)var0.getCertificates().get(var1)) && var2 != 0) {
         var3 = var2 - 1;
      } else {
         var3 = var2;
      }

      return var3;
   }

   protected static int prepareNextCertI1(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      X509Certificate var3 = (X509Certificate)var0.getCertificates().get(var1);

      ASN1Sequence var5;
      try {
         String var4 = POLICY_CONSTRAINTS;
         var5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, var4));
      } catch (Exception var13) {
         throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", var13, var0, var1);
      }

      int var10;
      if(var5 != null) {
         Enumeration var7 = var5.getObjects();

         while(var7.hasMoreElements()) {
            int var15;
            try {
               ASN1TaggedObject var8 = ASN1TaggedObject.getInstance(var7.nextElement());
               if(var8.getTagNo() != 0) {
                  continue;
               }

               var15 = DERInteger.getInstance(var8).getValue().intValue();
            } catch (IllegalArgumentException var14) {
               throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", var14, var0, var1);
            }

            if(var15 < var2) {
               var10 = var15;
               return var10;
            }
            break;
         }
      }

      var10 = var2;
      return var10;
   }

   protected static int prepareNextCertI2(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      X509Certificate var3 = (X509Certificate)var0.getCertificates().get(var1);

      ASN1Sequence var5;
      try {
         String var4 = POLICY_CONSTRAINTS;
         var5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, var4));
      } catch (Exception var13) {
         throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", var13, var0, var1);
      }

      int var10;
      if(var5 != null) {
         Enumeration var7 = var5.getObjects();

         while(var7.hasMoreElements()) {
            int var15;
            try {
               ASN1TaggedObject var8 = ASN1TaggedObject.getInstance(var7.nextElement());
               if(var8.getTagNo() != 1) {
                  continue;
               }

               var15 = DERInteger.getInstance(var8).getValue().intValue();
            } catch (IllegalArgumentException var14) {
               throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", var14, var0, var1);
            }

            if(var15 < var2) {
               var10 = var15;
               return var10;
            }
            break;
         }
      }

      var10 = var2;
      return var10;
   }

   protected static int prepareNextCertJ(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      X509Certificate var3 = (X509Certificate)var0.getCertificates().get(var1);

      DERInteger var5;
      try {
         String var4 = INHIBIT_ANY_POLICY;
         var5 = DERInteger.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, var4));
      } catch (Exception var10) {
         throw new ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", var10, var0, var1);
      }

      int var8;
      if(var5 != null) {
         int var7 = var5.getValue().intValue();
         if(var7 < var2) {
            var8 = var7;
            return var8;
         }
      }

      var8 = var2;
      return var8;
   }

   protected static void prepareNextCertK(CertPath var0, int var1) throws CertPathValidatorException {
      X509Certificate var2 = (X509Certificate)var0.getCertificates().get(var1);

      BasicConstraints var4;
      try {
         String var3 = BASIC_CONSTRAINTS;
         var4 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(var2, var3));
      } catch (Exception var7) {
         throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", var7, var0, var1);
      }

      if(var4 != null) {
         if(!var4.isCA()) {
            throw new CertPathValidatorException("Not a CA certificate");
         }
      } else {
         throw new CertPathValidatorException("Intermediate certificate lacks BasicConstraints");
      }
   }

   protected static int prepareNextCertL(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      int var3;
      if(!CertPathValidatorUtilities.isSelfIssued((X509Certificate)var0.getCertificates().get(var1))) {
         if(var2 <= 0) {
            throw new ExtCertPathValidatorException("Max path length not greater than zero", (Throwable)null, var0, var1);
         }

         var3 = var2 - 1;
      } else {
         var3 = var2;
      }

      return var3;
   }

   protected static int prepareNextCertM(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      X509Certificate var3 = (X509Certificate)var0.getCertificates().get(var1);

      BasicConstraints var5;
      try {
         String var4 = BASIC_CONSTRAINTS;
         var5 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, var4));
      } catch (Exception var11) {
         throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", var11, var0, var1);
      }

      int var9;
      if(var5 != null) {
         BigInteger var7 = var5.getPathLenConstraint();
         if(var7 != null) {
            int var8 = var7.intValue();
            if(var8 < var2) {
               var9 = var8;
               return var9;
            }
         }
      }

      var9 = var2;
      return var9;
   }

   protected static void prepareNextCertN(CertPath var0, int var1) throws CertPathValidatorException {
      boolean[] var2 = ((X509Certificate)var0.getCertificates().get(var1)).getKeyUsage();
      if(var2 != null) {
         if(!var2[5]) {
            throw new ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", (Throwable)null, var0, var1);
         }
      }
   }

   protected static void prepareNextCertO(CertPath var0, int var1, Set var2, List var3) throws CertPathValidatorException {
      X509Certificate var4 = (X509Certificate)var0.getCertificates().get(var1);
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         try {
            ((PKIXCertPathChecker)var5.next()).check(var4, var2);
         } catch (CertPathValidatorException var9) {
            String var7 = var9.getMessage();
            Throwable var8 = var9.getCause();
            throw new CertPathValidatorException(var7, var8, var0, var1);
         }
      }

      if(!var2.isEmpty()) {
         throw new ExtCertPathValidatorException("Certificate has unsupported critical extension.", (Throwable)null, var0, var1);
      }
   }

   protected static Set processCRLA1i(Date var0, ExtendedPKIXParameters var1, X509Certificate var2, X509CRL var3) throws AnnotatedException {
      HashSet var4 = new HashSet();
      if(var1.isUseDeltasEnabled()) {
         CRLDistPoint var6;
         try {
            String var5 = FRESHEST_CRL;
            var6 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var2, var5));
         } catch (AnnotatedException var19) {
            throw new AnnotatedException("Freshest CRL extension could not be decoded from certificate.", var19);
         }

         CRLDistPoint var7 = var6;
         if(var6 == null) {
            try {
               String var8 = FRESHEST_CRL;
               var6 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, var8));
            } catch (AnnotatedException var18) {
               throw new AnnotatedException("Freshest CRL extension could not be decoded from CRL.", var18);
            }
         }

         if(var6 != null) {
            try {
               CertPathValidatorUtilities.addAdditionalStoresFromCRLDistributionPoint(var7, var1);
            } catch (AnnotatedException var17) {
               throw new AnnotatedException("No new delta CRL locations could be added from Freshest CRL extension.", var17);
            }

            try {
               Set var10 = CertPathValidatorUtilities.getDeltaCRLs(var0, var1, var3);
               var4.addAll(var10);
            } catch (AnnotatedException var16) {
               throw new AnnotatedException("Exception obtaining delta CRLs.", var16);
            }
         }
      }

      return var4;
   }

   protected static Set[] processCRLA1ii(Date var0, ExtendedPKIXParameters var1, X509Certificate var2, X509CRL var3) throws AnnotatedException {
      HashSet var4 = new HashSet();
      HashSet var5 = new HashSet();
      X509CRLStoreSelector var6 = new X509CRLStoreSelector();
      var6.setCertificateChecking(var2);
      if(var1.getDate() != null) {
         Date var7 = var1.getDate();
         var6.setDateAndTime(var7);
      } else {
         var6.setDateAndTime(var0);
      }

      try {
         byte[] var8 = var3.getIssuerX500Principal().getEncoded();
         var6.addIssuerName(var8);
      } catch (IOException var27) {
         String var22 = "Cannot extract issuer from CRL." + var27;
         throw new AnnotatedException(var22, var27);
      }

      var6.setCompleteCRLEnabled((boolean)1);

      try {
         List var9 = var1.getAdditionalStores();
         Collection var10 = CertPathValidatorUtilities.findCRLs(var6, var9);
         var4.addAll(var10);
         List var12 = var1.getStores();
         Collection var13 = CertPathValidatorUtilities.findCRLs(var6, var12);
         var4.addAll(var13);
         List var15 = var1.getCertStores();
         Collection var16 = CertPathValidatorUtilities.findCRLs(var6, var15);
         var4.addAll(var16);
      } catch (AnnotatedException var26) {
         throw new AnnotatedException("Exception obtaining complete CRLs.", var26);
      }

      if(var1.isUseDeltasEnabled()) {
         try {
            Set var18 = CertPathValidatorUtilities.getDeltaCRLs(var0, var1, var3);
            var5.addAll(var18);
         } catch (AnnotatedException var25) {
            throw new AnnotatedException("Exception obtaining delta CRLs.", var25);
         }
      }

      Set[] var20 = new Set[]{var4, var5};
      return var20;
   }

   protected static void processCRLB1(DistributionPoint var0, Object var1, X509CRL var2) throws AnnotatedException {
      String var3 = ISSUING_DISTRIBUTION_POINT;
      DERObject var4 = CertPathValidatorUtilities.getExtensionValue(var2, var3);
      boolean var5 = false;
      if(var4 != null && IssuingDistributionPoint.getInstance(var4).isIndirectCRL()) {
         var5 = true;
      }

      byte[] var6 = CertPathValidatorUtilities.getIssuerPrincipal(var2).getEncoded();
      boolean var7 = false;
      if(var0.getCRLIssuer() != null) {
         GeneralName[] var8 = var0.getCRLIssuer().getNames();
         int var9 = 0;

         while(true) {
            int var10 = var8.length;
            if(var9 >= var10) {
               if(var7 && !var5) {
                  throw new AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
               }

               if(!var7) {
                  throw new AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
               }
               break;
            }

            if(var8[var9].getTagNo() == 4) {
               boolean var11;
               try {
                  var11 = Arrays.areEqual(var8[var9].getName().getDERObject().getEncoded(), var6);
               } catch (IOException var15) {
                  throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", var15);
               }

               if(var11) {
                  var7 = true;
               }
            }

            ++var9;
         }
      } else {
         X500Principal var13 = CertPathValidatorUtilities.getIssuerPrincipal(var2);
         X500Principal var14 = CertPathValidatorUtilities.getEncodedIssuerPrincipal(var1);
         if(var13.equals(var14)) {
            var7 = true;
         }
      }

      if(!var7) {
         throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
      }
   }

   protected static void processCRLB2(DistributionPoint var0, Object var1, X509CRL var2) throws AnnotatedException {
      IssuingDistributionPoint var4;
      try {
         String var3 = ISSUING_DISTRIBUTION_POINT;
         var4 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var2, var3));
      } catch (Exception var50) {
         throw new AnnotatedException("Issuing distribution point extension could not be decoded.", var50);
      }

      if(var4 != null) {
         if(var4.getDistributionPoint() != null) {
            DistributionPointName var6 = IssuingDistributionPoint.getInstance(var4).getDistributionPoint();
            ArrayList var7 = new ArrayList();
            if(var6.getType() == 0) {
               GeneralName[] var8 = GeneralNames.getInstance(var6.getName()).getNames();
               int var9 = 0;

               while(true) {
                  int var10 = var8.length;
                  if(var9 >= var10) {
                     break;
                  }

                  GeneralName var11 = var8[var9];
                  var7.add(var11);
                  ++var9;
               }
            }

            if(var6.getType() == 1) {
               ASN1EncodableVector var14 = new ASN1EncodableVector();

               try {
                  Enumeration var52 = ASN1Sequence.getInstance(ASN1Sequence.fromByteArray(CertPathValidatorUtilities.getIssuerPrincipal(var2).getEncoded())).getObjects();

                  while(var52.hasMoreElements()) {
                     DEREncodable var15 = (DEREncodable)var52.nextElement();
                     var14.add(var15);
                  }
               } catch (IOException var51) {
                  throw new AnnotatedException("Could not read CRL issuer.", var51);
               }

               ASN1Encodable var17 = var6.getName();
               var14.add(var17);
               X509Name var18 = X509Name.getInstance(new DERSequence(var14));
               GeneralName var19 = new GeneralName(var18);
               var7.add(var19);
            }

            Object var21 = null;
            int var54;
            if(var0.getDistributionPoint() != null) {
               DistributionPointName var53 = var0.getDistributionPoint();
               GeneralName[] var22 = null;
               if(var53.getType() == 0) {
                  var22 = GeneralNames.getInstance(var53.getName()).getNames();
               }

               if(var53.getType() == 1) {
                  if(var0.getCRLIssuer() != null) {
                     var22 = var0.getCRLIssuer().getNames();
                  } else {
                     GeneralName[] var28 = new GeneralName[1];
                     byte var23 = 0;

                     try {
                        ASN1Sequence var29 = (ASN1Sequence)ASN1Sequence.fromByteArray(CertPathValidatorUtilities.getEncodedIssuerPrincipal(var1).getEncoded());
                        X509Name var30 = new X509Name(var29);
                        GeneralName var31 = new GeneralName(var30);
                        var28[var23] = var31;
                     } catch (IOException var49) {
                        throw new AnnotatedException("Could not read certificate issuer.", var49);
                     }

                     var22 = var28;
                  }

                  int var55 = 0;

                  while(true) {
                     int var24 = var22.length;
                     if(var55 >= var24) {
                        break;
                     }

                     Enumeration var25 = ASN1Sequence.getInstance(var22[var55].getName().getDERObject()).getObjects();
                     ASN1EncodableVector var26 = new ASN1EncodableVector();

                     while(var25.hasMoreElements()) {
                        DEREncodable var27 = (DEREncodable)var25.nextElement();
                        var26.add(var27);
                     }

                     ASN1Encodable var33 = var53.getName();
                     var26.add(var33);
                     DERSequence var34 = new DERSequence(var26);
                     X509Name var35 = new X509Name(var34);
                     GeneralName var36 = new GeneralName(var35);
                     var22[var55] = var36;
                     ++var55;
                  }
               }

               label114: {
                  GeneralName[] var37 = var22;
                  if(var22 != null) {
                     var54 = 0;

                     while(true) {
                        int var38 = var37.length;
                        if(var54 >= var38) {
                           break;
                        }

                        GeneralName var39 = var37[var54];
                        if(var7.contains(var39)) {
                           var0 = null;
                           break label114;
                        }

                        ++var54;
                     }
                  }

                  var0 = (DistributionPoint)var21;
               }

               if(var0 == null) {
                  throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
               }
            } else {
               if(var0.getCRLIssuer() == null) {
                  throw new AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
               }

               GeneralName[] var40 = var0.getCRLIssuer().getNames();
               var54 = 0;

               while(true) {
                  int var41 = var40.length;
                  if(var54 >= var41) {
                     var0 = (DistributionPoint)var21;
                     break;
                  }

                  GeneralName var42 = var40[var54];
                  if(var7.contains(var42)) {
                     var0 = null;
                     break;
                  }

                  ++var54;
               }

               if(var0 == null) {
                  throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
               }
            }
         }

         BasicConstraints var45;
         try {
            X509Extension var43 = (X509Extension)var1;
            String var44 = BASIC_CONSTRAINTS;
            var45 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(var43, var44));
         } catch (Exception var48) {
            throw new AnnotatedException("Basic constraints extension could not be decoded.", var48);
         }

         if(var1 instanceof X509Certificate) {
            if(var4.onlyContainsUserCerts() && var45 != null && var45.isCA()) {
               throw new AnnotatedException("CA Cert CRL only contains user certificates.");
            }

            if(var4.onlyContainsCACerts() && (var45 == null || !var45.isCA())) {
               throw new AnnotatedException("End CRL only contains CA certificates.");
            }
         }

         if(var4.onlyContainsAttributeCerts()) {
            throw new AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
         }
      }
   }

   protected static void processCRLC(X509CRL var0, X509CRL var1, ExtendedPKIXParameters var2) throws AnnotatedException {
      if(var0 != null) {
         IssuingDistributionPoint var4;
         try {
            String var3 = ISSUING_DISTRIBUTION_POINT;
            var4 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var1, var3));
         } catch (Exception var23) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", var23);
         }

         if(var2.isUseDeltasEnabled()) {
            X500Principal var6 = var0.getIssuerX500Principal();
            X500Principal var7 = var1.getIssuerX500Principal();
            if(!var6.equals(var7)) {
               throw new AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
            } else {
               IssuingDistributionPoint var10;
               try {
                  String var9 = ISSUING_DISTRIBUTION_POINT;
                  var10 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var0, var9));
               } catch (Exception var22) {
                  throw new AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", var22);
               }

               boolean var12 = false;
               if(var4 == null) {
                  if(var10 == null) {
                     var12 = true;
                  }
               } else if(var4.equals(var10)) {
                  var12 = true;
               }

               if(!var12) {
                  throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
               } else {
                  DERObject var24;
                  try {
                     String var14 = AUTHORITY_KEY_IDENTIFIER;
                     var24 = CertPathValidatorUtilities.getExtensionValue(var1, var14);
                  } catch (AnnotatedException var21) {
                     throw new AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", var21);
                  }

                  DERObject var15 = var24;

                  try {
                     String var16 = AUTHORITY_KEY_IDENTIFIER;
                     var24 = CertPathValidatorUtilities.getExtensionValue(var0, var16);
                  } catch (AnnotatedException var20) {
                     throw new AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", var20);
                  }

                  if(var15 == null) {
                     throw new AnnotatedException("CRL authority key identifier is null.");
                  } else if(var24 == null) {
                     throw new AnnotatedException("Delta CRL authority key identifier is null.");
                  } else if(!var15.equals(var24)) {
                     throw new AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
                  }
               }
            }
         }
      }
   }

   protected static ReasonsMask processCRLD(X509CRL var0, DistributionPoint var1) throws AnnotatedException {
      IssuingDistributionPoint var3;
      try {
         String var2 = ISSUING_DISTRIBUTION_POINT;
         var3 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var0, var2));
      } catch (Exception var15) {
         throw new AnnotatedException("Issuing distribution point extension could not be decoded.", var15);
      }

      ReasonsMask var9;
      if(var3 != null && var3.getOnlySomeReasons() != null && var1.getReasons() != null) {
         int var5 = var1.getReasons().intValue();
         ReasonsMask var6 = new ReasonsMask(var5);
         int var7 = var3.getOnlySomeReasons().intValue();
         ReasonsMask var8 = new ReasonsMask(var7);
         var9 = var6.intersect(var8);
      } else if((var3 == null || var3.getOnlySomeReasons() == null) && var1.getReasons() == null) {
         var9 = ReasonsMask.allReasons;
      } else {
         ReasonsMask var11;
         if(var1.getReasons() == null) {
            var11 = ReasonsMask.allReasons;
         } else {
            int var13 = var1.getReasons().intValue();
            var11 = new ReasonsMask(var13);
         }

         ReasonsMask var12;
         if(var3 == null) {
            var12 = ReasonsMask.allReasons;
         } else {
            int var14 = var3.getOnlySomeReasons().intValue();
            var12 = new ReasonsMask(var14);
         }

         var9 = var11.intersect(var12);
      }

      return var9;
   }

   protected static Set processCRLF(X509CRL var0, Object var1, X509Certificate var2, PublicKey var3, ExtendedPKIXParameters var4, List var5) throws AnnotatedException {
      X509CertStoreSelector var6 = new X509CertStoreSelector();

      try {
         byte[] var7 = CertPathValidatorUtilities.getIssuerPrincipal(var0).getEncoded();
         var6.setSubject(var7);
      } catch (IOException var48) {
         throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate for CRL could not be set.", var48);
      }

      Collection var55;
      try {
         List var8 = var4.getStores();
         var55 = CertPathValidatorUtilities.findCertificates(var6, var8);
         List var9 = var4.getAdditionalStores();
         Collection var10 = CertPathValidatorUtilities.findCertificates(var6, var9);
         var55.addAll(var10);
         List var12 = var4.getCertStores();
         Collection var13 = CertPathValidatorUtilities.findCertificates(var6, var12);
         var55.addAll(var13);
      } catch (AnnotatedException var47) {
         throw new AnnotatedException("Issuer certificate for CRL cannot be searched.", var47);
      }

      var55.add(var2);
      Iterator var16 = var55.iterator();
      ArrayList var17 = new ArrayList();
      ArrayList var18 = new ArrayList();

      while(var16.hasNext()) {
         X509Certificate var19 = (X509Certificate)var16.next();
         if(var19.equals(var2)) {
            var17.add(var19);
            var18.add(var3);
         } else {
            Exception var34;
            label87: {
               X509CertStoreSelector var24;
               CertPathBuilderException var30;
               CertPathValidatorException var32;
               CertPathBuilder var56;
               try {
                  var56 = CertPathBuilder.getInstance("PKIX", "myBC");
                  var24 = new X509CertStoreSelector();
               } catch (CertPathBuilderException var52) {
                  var30 = var52;
                  throw new AnnotatedException("Internal error.", var30);
               } catch (CertPathValidatorException var53) {
                  var32 = var53;
                  throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", var32);
               } catch (Exception var54) {
                  var34 = var54;
                  break label87;
               }

               try {
                  var24.setCertificate(var19);
                  ExtendedPKIXParameters var25 = (ExtendedPKIXParameters)var4.clone();
                  var25.setTargetCertConstraints(var24);
                  ExtendedPKIXBuilderParameters var58 = (ExtendedPKIXBuilderParameters)ExtendedPKIXBuilderParameters.getInstance(var25);
                  if(var5.contains(var19)) {
                     var58.setRevocationEnabled((boolean)0);
                  } else {
                     var58.setRevocationEnabled((boolean)1);
                  }

                  List var26 = var56.build(var58).getCertPath().getCertificates();
                  var17.add(var19);
                  PublicKey var28 = CertPathValidatorUtilities.getNextWorkingKey(var26, 0);
                  var18.add(var28);
                  continue;
               } catch (CertPathBuilderException var49) {
                  var30 = var49;
                  throw new AnnotatedException("Internal error.", var30);
               } catch (CertPathValidatorException var50) {
                  var32 = var50;
                  throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", var32);
               } catch (Exception var51) {
                  var34 = var51;
               }
            }

            String var36 = var34.getMessage();
            throw new RuntimeException(var36);
         }
      }

      HashSet var57 = new HashSet();
      int var37 = 0;
      var3 = null;

      while(true) {
         int var38 = var17.size();
         if(var37 >= var38) {
            if(var57.isEmpty() && var3 == null) {
               throw new AnnotatedException("Cannot find a valid issuer certificate.");
            }

            if(var57.isEmpty() && var3 != null) {
               throw var3;
            }

            return var57;
         }

         boolean[] var39 = ((X509Certificate)var17.get(var37)).getKeyUsage();
         if(var39 != null && (var39.length < 7 || !var39[6])) {
            AnnotatedException var40 = new AnnotatedException("Issuer certificate key usage extension does not permit CRL signing.");
         } else {
            Object var42 = var18.get(var37);
            var57.add(var42);
         }

         ++var37;
      }
   }

   protected static PublicKey processCRLG(X509CRL var0, Set var1) throws AnnotatedException {
      Exception var2 = null;
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         PublicKey var4 = (PublicKey)var3.next();

         try {
            var0.verify(var4);
            return var4;
         } catch (Exception var5) {
            var2 = var5;
         }
      }

      throw new AnnotatedException("Cannot verify CRL.", var2);
   }

   protected static X509CRL processCRLH(Set var0, PublicKey var1) throws AnnotatedException {
      Exception var2 = null;
      Iterator var3 = var0.iterator();

      X509CRL var5;
      while(true) {
         if(var3.hasNext()) {
            X509CRL var4 = (X509CRL)var3.next();

            try {
               var4.verify(var1);
            } catch (Exception var6) {
               var2 = var6;
               continue;
            }

            var5 = var4;
            break;
         }

         if(var2 != null) {
            throw new AnnotatedException("Cannot verify delta CRL.", var2);
         }

         var5 = null;
         break;
      }

      return var5;
   }

   protected static void processCRLI(Date var0, X509CRL var1, Object var2, CertStatus var3, ExtendedPKIXParameters var4) throws AnnotatedException {
      if(var4.isUseDeltasEnabled()) {
         if(var1 != null) {
            CertPathValidatorUtilities.getCertStatus(var0, var1, var2, var3);
         }
      }
   }

   protected static void processCRLJ(Date var0, X509CRL var1, Object var2, CertStatus var3) throws AnnotatedException {
      if(var3.getCertStatus() == 11) {
         CertPathValidatorUtilities.getCertStatus(var0, var1, var2, var3);
      }
   }

   protected static void processCertA(CertPath var0, ExtendedPKIXParameters var1, int var2, PublicKey var3, boolean var4, X500Principal var5, X509Certificate var6) throws ExtCertPathValidatorException {
      List var7 = var0.getCertificates();
      X509Certificate var8 = (X509Certificate)var7.get(var2);
      if(!var4) {
         try {
            String var9 = var1.getSigProvider();
            CertPathValidatorUtilities.verifyX509Certificate(var8, var3, var9);
         } catch (GeneralSecurityException var34) {
            throw new ExtCertPathValidatorException("Could not validate certificate signature.", var34, var0, var2);
         }
      }

      try {
         Date var10 = CertPathValidatorUtilities.getValidCertDateFromValidityModel(var1, var0, var2);
         var8.checkValidity(var10);
      } catch (CertificateExpiredException var31) {
         StringBuilder var20 = (new StringBuilder()).append("Could not validate certificate: ");
         String var21 = var31.getMessage();
         String var22 = var20.append(var21).toString();
         throw new ExtCertPathValidatorException(var22, var31, var0, var2);
      } catch (CertificateNotYetValidException var32) {
         StringBuilder var24 = (new StringBuilder()).append("Could not validate certificate: ");
         String var25 = var32.getMessage();
         String var26 = var24.append(var25).toString();
         throw new ExtCertPathValidatorException(var26, var32, var0, var2);
      } catch (AnnotatedException var33) {
         throw new ExtCertPathValidatorException("Could not validate time of certificate.", var33, var0, var2);
      }

      if(var1.isRevocationEnabled()) {
         try {
            Date var11 = CertPathValidatorUtilities.getValidCertDateFromValidityModel(var1, var0, var2);
            checkCRLs(var1, var8, var11, var6, var3, var7);
         } catch (AnnotatedException var35) {
            Object var29 = var35;
            if(var35.getCause() != null) {
               var29 = var35.getCause();
            }

            String var30 = var35.getMessage();
            throw new ExtCertPathValidatorException(var30, (Throwable)var29, var0, var2);
         }
      }

      if(!CertPathValidatorUtilities.getEncodedIssuerPrincipal(var8).equals(var5)) {
         StringBuilder var15 = (new StringBuilder()).append("IssuerName(");
         X500Principal var16 = CertPathValidatorUtilities.getEncodedIssuerPrincipal(var8);
         String var17 = var15.append(var16).append(") does not match SubjectName(").append(var5).append(") of signing certificate.").toString();
         throw new ExtCertPathValidatorException(var17, (Throwable)null, var0, var2);
      }
   }

   protected static void processCertBC(CertPath var0, int var1, PKIXNameConstraintValidator var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      int var5 = var3.size();
      int var6 = var5 - var1;
      if(!CertPathValidatorUtilities.isSelfIssued(var4) || var6 >= var5) {
         byte[] var7 = CertPathValidatorUtilities.getSubjectPrincipal(var4).getEncoded();
         ASN1InputStream var8 = new ASN1InputStream(var7);

         ASN1Sequence var9;
         try {
            var9 = DERSequence.getInstance(var8.readObject());
         } catch (Exception var31) {
            throw new CertPathValidatorException("Exception extracting subject name when checking subtrees.", var31, var0, var1);
         }

         ASN1Sequence var10 = var9;

         try {
            var2.checkPermittedDN(var10);
            var2.checkExcludedDN(var10);
         } catch (PKIXNameConstraintValidatorException var30) {
            throw new CertPathValidatorException("Subtree check for certificate subject failed.", var30, var0, var1);
         }

         GeneralNames var36;
         try {
            String var11 = SUBJECT_ALTERNATIVE_NAME;
            var36 = GeneralNames.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, var11));
         } catch (Exception var29) {
            throw new CertPathValidatorException("Subject alternative name extension could not be decoded.", var29, var0, var1);
         }

         GeneralNames var35 = var36;
         X509Name var12 = new X509Name(var9);
         DERObjectIdentifier var13 = X509Name.EmailAddress;
         Enumeration var32 = var12.getValues(var13).elements();

         while(var32.hasMoreElements()) {
            String var14 = (String)var32.nextElement();
            GeneralName var15 = new GeneralName(1, var14);

            try {
               var2.checkPermitted(var15);
               var2.checkExcluded(var15);
            } catch (PKIXNameConstraintValidatorException var28) {
               throw new CertPathValidatorException("Subtree check for certificate subject alternative email failed.", var28, var0, var1);
            }
         }

         if(var36 != null) {
            GeneralName[] var20;
            try {
               var20 = var35.getNames();
            } catch (Exception var27) {
               throw new CertPathValidatorException("Subject alternative name contents could not be decoded.", var27, var0, var1);
            }

            GeneralName[] var34 = var20;
            int var33 = 0;

            while(true) {
               int var21 = var34.length;
               if(var33 >= var21) {
                  return;
               }

               try {
                  GeneralName var22 = var34[var33];
                  var2.checkPermitted(var22);
                  GeneralName var23 = var34[var33];
                  var2.checkExcluded(var23);
               } catch (PKIXNameConstraintValidatorException var26) {
                  throw new CertPathValidatorException("Subtree check for certificate subject alternative name failed.", var26, var0, var1);
               }

               ++var33;
            }
         }
      }
   }

   protected static PKIXPolicyNode processCertD(CertPath var0, int var1, Set var2, PKIXPolicyNode var3, List[] var4, int var5) throws CertPathValidatorException {
      List var6 = var0.getCertificates();
      X509Certificate var9 = (X509Certificate)var6.get(var1);
      int var10 = var6.size();
      int var11 = var10 - var1;

      ASN1Sequence var13;
      try {
         String var12 = CERTIFICATE_POLICIES;
         var13 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var9, var12));
      } catch (AnnotatedException var110) {
         ExtCertPathValidatorException var33 = new ExtCertPathValidatorException;
         String var35 = "Could not read certificate policies extension from certificate.";
         var33.<init>(var35, var110, var0, var1);
         throw var33;
      }

      PKIXPolicyNode var87;
      if(var13 != null && var3 != null) {
         Enumeration var15 = var13.getObjects();
         Object var16 = new HashSet();

         while(var15.hasMoreElements()) {
            PolicyInformation var17 = PolicyInformation.getInstance(var15.nextElement());
            DERObjectIdentifier var18 = var17.getPolicyIdentifier();
            String var19 = var18.getId();
            ((Set)var16).add(var19);
            String var21 = var18.getId();
            if(!"2.5.29.32.0".equals(var21)) {
               Set var22;
               try {
                  var22 = CertPathValidatorUtilities.getQualifierSet(var17.getPolicyQualifiers());
               } catch (CertPathValidatorException var109) {
                  ExtCertPathValidatorException var40 = new ExtCertPathValidatorException;
                  String var42 = "Policy qualifier info set could not be build.";
                  var40.<init>(var42, var109, var0, var1);
                  throw var40;
               }

               if(!CertPathValidatorUtilities.processCertD1i(var11, var4, var18, var22)) {
                  CertPathValidatorUtilities.processCertD1ii(var11, var4, var18, var22);
               }
            }
         }

         label142: {
            if(!var2.isEmpty()) {
               String var47 = "2.5.29.32.0";
               if(!var2.contains(var47)) {
                  Iterator var52 = var2.iterator();
                  HashSet var69 = new HashSet();

                  while(var52.hasNext()) {
                     Object var111 = var52.next();
                     if(((Set)var16).contains(var111)) {
                        boolean var74 = var69.add(var111);
                     }
                  }

                  var2.clear();
                  boolean var77 = var2.addAll(var69);
                  break label142;
               }
            }

            var2.clear();
            boolean var50 = var2.addAll((Collection)var16);
         }

         if(var5 > 0 || var11 < var10 && CertPathValidatorUtilities.isSelfIssued(var9)) {
            Enumeration var51 = var13.getObjects();

            label123:
            while(var51.hasMoreElements()) {
               PolicyInformation var117 = PolicyInformation.getInstance(var51.nextElement());
               String var53 = var117.getPolicyIdentifier().getId();
               if("2.5.29.32.0".equals(var53)) {
                  Set var54 = CertPathValidatorUtilities.getQualifierSet(var117.getPolicyQualifiers());
                  int var55 = var11 - 1;
                  List var116 = var4[var55];
                  int var56 = 0;

                  while(true) {
                     int var57 = var116.size();
                     if(var56 >= var57) {
                        break label123;
                     }

                     PKIXPolicyNode var118 = (PKIXPolicyNode)var116.get(var56);
                     Iterator var62 = var118.getExpectedPolicies().iterator();

                     while(var62.hasNext()) {
                        Object var63 = var62.next();
                        if(var63 instanceof String) {
                           var16 = (String)var63;
                        } else {
                           if(!(var63 instanceof DERObjectIdentifier)) {
                              continue;
                           }

                           String var78 = ((DERObjectIdentifier)var63).getId();
                        }

                        Iterator var112 = var118.getChildren();
                        var2 = null;

                        while(var112.hasNext()) {
                           String var64 = ((PKIXPolicyNode)var112.next()).getValidPolicy();
                           if(((String)var16).equals(var64)) {
                              Object var67 = null;
                           }
                        }

                        if(var2 == null) {
                           HashSet var79 = new HashSet();
                           var79.add(var16);
                           ArrayList var81 = new ArrayList();
                           PKIXPolicyNode var82 = new PKIXPolicyNode(var81, var11, var79, var118, var54, (String)var16, (boolean)0);
                           var118.addChild(var82);
                           List var83 = var4[var11];
                           var83.add(var82);
                        }
                     }

                     ++var56;
                  }
               }
            }
         }

         var87 = var3;

         int var114;
         for(int var88 = var11 - 1; var88 >= 0; var88 += -1) {
            List var89 = var4[var88];
            var114 = 0;

            while(true) {
               int var90 = var89.size();
               if(var114 >= var90) {
                  break;
               }

               var3 = (PKIXPolicyNode)var89.get(var114);
               if(!var3.hasChildren() && CertPathValidatorUtilities.removePolicyNode(var87, var4, var3) == null) {
                  break;
               }

               ++var114;
            }
         }

         Set var113 = var9.getCriticalExtensionOIDs();
         if(var113 != null) {
            String var98 = CERTIFICATE_POLICIES;
            boolean var99 = var113.contains(var98);
            List var115 = var4[var11];
            var114 = 0;

            while(true) {
               int var100 = var115.size();
               if(var114 >= var100) {
                  break;
               }

               PKIXPolicyNode var105 = (PKIXPolicyNode)var115.get(var114);
               var105.setCritical(var99);
               ++var114;
            }
         }
      }

      return var87;
   }

   protected static PKIXPolicyNode processCertE(CertPath var0, int var1, PKIXPolicyNode var2) throws CertPathValidatorException {
      X509Certificate var3 = (X509Certificate)var0.getCertificates().get(var1);

      ASN1Sequence var5;
      try {
         String var4 = CERTIFICATE_POLICIES;
         var5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, var4));
      } catch (AnnotatedException var7) {
         throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", var7, var0, var1);
      }

      if(var5 == null) {
         var2 = null;
      }

      return var2;
   }

   protected static void processCertF(CertPath var0, int var1, PKIXPolicyNode var2, int var3) throws CertPathValidatorException {
      if(var3 <= 0) {
         if(var2 == null) {
            throw new ExtCertPathValidatorException("No valid policy tree found when one expected.", (Throwable)null, var0, var1);
         }
      }
   }

   protected static int wrapupCertA(int var0, X509Certificate var1) {
      if(!CertPathValidatorUtilities.isSelfIssued(var1) && var0 != 0) {
         var0 += -1;
      }

      return var0;
   }

   protected static int wrapupCertB(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      X509Certificate var3 = (X509Certificate)var0.getCertificates().get(var1);

      ASN1Sequence var5;
      try {
         String var4 = POLICY_CONSTRAINTS;
         var5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, var4));
      } catch (AnnotatedException var13) {
         throw new ExtCertPathValidatorException("Policy constraints could no be decoded.", var13, var0, var1);
      }

      int var9;
      if(var5 != null) {
         Enumeration var7 = var5.getObjects();

         while(var7.hasMoreElements()) {
            ASN1TaggedObject var8 = (ASN1TaggedObject)var7.nextElement();
            switch(var8.getTagNo()) {
            case 0:
               int var14;
               try {
                  var14 = DERInteger.getInstance(var8).getValue().intValue();
               } catch (Exception var12) {
                  throw new ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could no be decoded.", var12, var0, var1);
               }

               if(var14 == 0) {
                  var9 = 0;
                  return var9;
               }
            }
         }
      }

      var9 = var2;
      return var9;
   }

   protected static void wrapupCertF(CertPath var0, int var1, List var2, Set var3) throws CertPathValidatorException {
      X509Certificate var4 = (X509Certificate)var0.getCertificates().get(var1);
      Iterator var5 = var2.iterator();

      while(var5.hasNext()) {
         try {
            ((PKIXCertPathChecker)var5.next()).check(var4, var3);
         } catch (CertPathValidatorException var7) {
            throw new ExtCertPathValidatorException("Additional certificate path checker failed.", var7, var0, var1);
         }
      }

      if(!var3.isEmpty()) {
         throw new ExtCertPathValidatorException("Certificate has unsupported critical extension", (Throwable)null, var0, var1);
      }
   }

   protected static PKIXPolicyNode wrapupCertG(CertPath var0, ExtendedPKIXParameters var1, Set var2, int var3, List[] var4, PKIXPolicyNode var5, Set var6) throws CertPathValidatorException {
      int var7 = var0.getCertificates().size();
      PKIXPolicyNode var38;
      if(var5 == null) {
         if(var1.isExplicitPolicyRequired()) {
            throw new ExtCertPathValidatorException("Explicit policy requested but none available.", (Throwable)null, var0, var3);
         }

         var38 = null;
      } else {
         PKIXPolicyNode var42;
         List var44;
         if(CertPathValidatorUtilities.isAnyPolicy(var2)) {
            if(var1.isExplicitPolicyRequired()) {
               if(var6.isEmpty()) {
                  throw new ExtCertPathValidatorException("Explicit policy requested but none available.", (Throwable)null, var0, var3);
               }

               HashSet var41 = new HashSet();
               byte var43 = 0;

               while(true) {
                  int var8 = var4.length;
                  if(var43 >= var8) {
                     Iterator var18 = var41.iterator();

                     while(var18.hasNext()) {
                        String var19 = ((PKIXPolicyNode)var18.next()).getValidPolicy();
                        if(!var6.contains(var19)) {
                           ;
                        }
                     }

                     if(var5 != null) {
                        for(int var39 = var7 - 1; var39 >= 0; var39 += -1) {
                           var44 = var4[var39];
                           byte var48 = 0;

                           while(true) {
                              int var20 = var44.size();
                              if(var48 >= var20) {
                                 break;
                              }

                              var42 = (PKIXPolicyNode)var44.get(var48);
                              if(!var42.hasChildren()) {
                                 var5 = CertPathValidatorUtilities.removePolicyNode(var5, var4, var42);
                              }

                              int var21 = var48 + 1;
                           }
                        }
                     }
                     break;
                  }

                  List var9 = var4[var43];
                  byte var10 = 0;

                  while(true) {
                     int var11 = var9.size();
                     if(var10 >= var11) {
                        int var17 = var43 + 1;
                        break;
                     }

                     PKIXPolicyNode var12 = (PKIXPolicyNode)var9.get(var10);
                     String var13 = var12.getValidPolicy();
                     if("2.5.29.32.0".equals(var13)) {
                        Iterator var47 = var12.getChildren();

                        while(var47.hasNext()) {
                           Object var14 = var47.next();
                           var41.add(var14);
                        }
                     }

                     int var16 = var10 + 1;
                  }
               }
            }

            var38 = var5;
         } else {
            HashSet var46 = new HashSet();
            byte var22 = 0;

            while(true) {
               int var23 = var4.length;
               if(var22 >= var23) {
                  Iterator var45 = var46.iterator();

                  while(var45.hasNext()) {
                     PKIXPolicyNode var32 = (PKIXPolicyNode)var45.next();
                     String var33 = var32.getValidPolicy();
                     if(!var2.contains(var33)) {
                        var5 = CertPathValidatorUtilities.removePolicyNode(var5, var4, var32);
                     }
                  }

                  if(var5 != null) {
                     for(int var34 = var7 - 1; var34 >= 0; var34 += -1) {
                        List var35 = var4[var34];
                        byte var40 = 0;

                        while(true) {
                           int var36 = var35.size();
                           if(var40 >= var36) {
                              break;
                           }

                           var42 = (PKIXPolicyNode)var35.get(var40);
                           if(!var42.hasChildren()) {
                              var5 = CertPathValidatorUtilities.removePolicyNode(var5, var4, var42);
                           }

                           int var37 = var40 + 1;
                        }
                     }
                  }

                  var38 = var5;
                  break;
               }

               var44 = var4[var22];
               int var24 = 0;

               while(true) {
                  int var25 = var44.size();
                  if(var24 >= var25) {
                     int var31 = var22 + 1;
                     break;
                  }

                  PKIXPolicyNode var26 = (PKIXPolicyNode)var44.get(var24);
                  String var27 = var26.getValidPolicy();
                  if("2.5.29.32.0".equals(var27)) {
                     Iterator var28 = var26.getChildren();

                     while(var28.hasNext()) {
                        var26 = (PKIXPolicyNode)var28.next();
                        String var29 = var26.getValidPolicy();
                        if(!"2.5.29.32.0".equals(var29)) {
                           var46.add(var26);
                        }
                     }
                  }

                  ++var24;
               }
            }
         }
      }

      return var38;
   }
}

package myorg.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.util.ASN1Dump;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.CRLDistPoint;
import myorg.bouncycastle.asn1.x509.CRLNumber;
import myorg.bouncycastle.asn1.x509.CertificateList;
import myorg.bouncycastle.asn1.x509.IssuingDistributionPoint;
import myorg.bouncycastle.asn1.x509.TBSCertList;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.jce.provider.ExtCRLException;
import myorg.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import myorg.bouncycastle.jce.provider.X509CRLEntryObject;
import myorg.bouncycastle.jce.provider.X509SignatureUtil;
import myorg.bouncycastle.util.encoders.Hex;
import myorg.bouncycastle.x509.extension.X509ExtensionUtil;

public class X509CRLObject extends X509CRL {

   private CertificateList c;
   private boolean isIndirect;
   private String sigAlgName;
   private byte[] sigAlgParams;


   public X509CRLObject(CertificateList var1) throws CRLException {
      this.c = var1;

      try {
         String var2 = X509SignatureUtil.getSignatureName(var1.getSignatureAlgorithm());
         this.sigAlgName = var2;
         if(var1.getSignatureAlgorithm().getParameters() != null) {
            byte[] var3 = ((ASN1Encodable)var1.getSignatureAlgorithm().getParameters()).getDEREncoded();
            this.sigAlgParams = var3;
         } else {
            this.sigAlgParams = null;
         }

         boolean var4 = this.isIndirectCRL();
         this.isIndirect = var4;
      } catch (Exception var7) {
         String var6 = "CRL contents invalid: " + var7;
         throw new CRLException(var6);
      }
   }

   private Set getExtensionOIDs(boolean var1) {
      HashSet var9;
      if(this.getVersion() == 2) {
         X509Extensions var2 = this.c.getTBSCertList().getExtensions();
         if(var2 != null) {
            HashSet var3 = new HashSet();
            Enumeration var4 = var2.oids();

            while(var4.hasMoreElements()) {
               DERObjectIdentifier var5 = (DERObjectIdentifier)var4.nextElement();
               boolean var6 = var2.getExtension(var5).isCritical();
               if(var1 == var6) {
                  String var7 = var5.getId();
                  var3.add(var7);
               }
            }

            var9 = var3;
            return var9;
         }
      }

      var9 = null;
      return var9;
   }

   private boolean isIndirectCRL() throws CRLException {
      String var1 = X509Extensions.IssuingDistributionPoint.getId();
      byte[] var2 = this.getExtensionValue(var1);
      boolean var3 = false;
      if(var2 != null) {
         boolean var4;
         try {
            var4 = IssuingDistributionPoint.getInstance(X509ExtensionUtil.fromExtensionValue(var2)).isIndirectCRL();
         } catch (Exception var6) {
            throw new ExtCRLException("Exception reading IssuingDistributionPoint", var6);
         }

         var3 = var4;
      }

      return var3;
   }

   private Set loadCRLEntries() {
      HashSet var1 = new HashSet();
      Enumeration var2 = this.c.getRevokedCertificateEnumeration();

      X509CRLEntryObject var6;
      for(X500Principal var3 = this.getIssuerX500Principal(); var2.hasMoreElements(); var3 = var6.getCertificateIssuer()) {
         TBSCertList.CRLEntry var4 = (TBSCertList.CRLEntry)var2.nextElement();
         boolean var5 = this.isIndirect;
         var6 = new X509CRLEntryObject(var4, var5, var3);
         var1.add(var6);
      }

      return var1;
   }

   public Set getCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)1);
   }

   public byte[] getEncoded() throws CRLException {
      try {
         byte[] var1 = this.c.getEncoded("DER");
         return var1;
      } catch (IOException var3) {
         String var2 = var3.toString();
         throw new CRLException(var2);
      }
   }

   public byte[] getExtensionValue(String var1) {
      X509Extensions var2 = this.c.getTBSCertList().getExtensions();
      byte[] var6;
      if(var2 != null) {
         DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
         X509Extension var4 = var2.getExtension(var3);
         if(var4 != null) {
            byte[] var5;
            try {
               var5 = var4.getValue().getEncoded();
            } catch (Exception var11) {
               StringBuilder var8 = (new StringBuilder()).append("error parsing ");
               String var9 = var11.toString();
               String var10 = var8.append(var9).toString();
               throw new IllegalStateException(var10);
            }

            var6 = var5;
            return var6;
         }
      }

      var6 = null;
      return var6;
   }

   public Principal getIssuerDN() {
      X509Name var1 = this.c.getIssuer();
      return new X509Principal(var1);
   }

   public X500Principal getIssuerX500Principal() {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         ASN1OutputStream var2 = new ASN1OutputStream(var1);
         X509Name var3 = this.c.getIssuer();
         var2.writeObject(var3);
         byte[] var4 = var1.toByteArray();
         X500Principal var5 = new X500Principal(var4);
         return var5;
      } catch (IOException var7) {
         throw new IllegalStateException("can\'t encode issuer DN");
      }
   }

   public Date getNextUpdate() {
      Date var1;
      if(this.c.getNextUpdate() != null) {
         var1 = this.c.getNextUpdate().getDate();
      } else {
         var1 = null;
      }

      return var1;
   }

   public Set getNonCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)0);
   }

   public X509CRLEntry getRevokedCertificate(BigInteger var1) {
      Enumeration var2 = this.c.getRevokedCertificateEnumeration();
      X500Principal var3 = this.getIssuerX500Principal();

      X509CRLEntryObject var8;
      while(true) {
         if(!var2.hasMoreElements()) {
            var8 = null;
            break;
         }

         TBSCertList.CRLEntry var4 = (TBSCertList.CRLEntry)var2.nextElement();
         boolean var5 = this.isIndirect;
         X509CRLEntryObject var6 = new X509CRLEntryObject(var4, var5, var3);
         BigInteger var7 = var4.getUserCertificate().getValue();
         if(var1.equals(var7)) {
            var8 = var6;
            break;
         }

         var3 = var6.getCertificateIssuer();
      }

      return var8;
   }

   public Set getRevokedCertificates() {
      Set var1 = this.loadCRLEntries();
      Set var2;
      if(!var1.isEmpty()) {
         var2 = Collections.unmodifiableSet(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public String getSigAlgName() {
      return this.sigAlgName;
   }

   public String getSigAlgOID() {
      return this.c.getSignatureAlgorithm().getObjectId().getId();
   }

   public byte[] getSigAlgParams() {
      byte[] var4;
      if(this.sigAlgParams != null) {
         byte[] var1 = new byte[this.sigAlgParams.length];
         byte[] var2 = this.sigAlgParams;
         int var3 = var1.length;
         System.arraycopy(var2, 0, var1, 0, var3);
         var4 = var1;
      } else {
         var4 = null;
      }

      return var4;
   }

   public byte[] getSignature() {
      return this.c.getSignature().getBytes();
   }

   public byte[] getTBSCertList() throws CRLException {
      try {
         byte[] var1 = this.c.getTBSCertList().getEncoded("DER");
         return var1;
      } catch (IOException var3) {
         String var2 = var3.toString();
         throw new CRLException(var2);
      }
   }

   public Date getThisUpdate() {
      return this.c.getThisUpdate().getDate();
   }

   public int getVersion() {
      return this.c.getVersion();
   }

   public boolean hasUnsupportedCriticalExtension() {
      Set var1 = this.getCriticalExtensionOIDs();
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         String var3 = RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT;
         var1.remove(var3);
         String var5 = RFC3280CertPathUtilities.DELTA_CRL_INDICATOR;
         var1.remove(var5);
         if(!var1.isEmpty()) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public boolean isRevoked(Certificate var1) {
      if(!var1.getType().equals("X.509")) {
         throw new RuntimeException("X.509 CRL used with non X.509 Cert");
      } else {
         TBSCertList.CRLEntry[] var2 = this.c.getRevokedCertificates();
         boolean var6;
         if(var2 != null) {
            BigInteger var3 = ((X509Certificate)var1).getSerialNumber();
            int var4 = 0;

            while(true) {
               int var5 = var2.length;
               if(var4 >= var5) {
                  break;
               }

               if(var2[var4].getUserCertificate().getValue().equals(var3)) {
                  var6 = true;
                  return var6;
               }

               ++var4;
            }
         }

         var6 = false;
         return var6;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      String var4 = "              Version: ";
      StringBuffer var5 = var1.append(var4);
      int var6 = this.getVersion();
      StringBuffer var7 = var5.append(var6);
      var7.append(var2);
      String var11 = "             IssuerDN: ";
      StringBuffer var12 = var1.append(var11);
      Principal var13 = this.getIssuerDN();
      StringBuffer var14 = var12.append(var13);
      var14.append(var2);
      String var18 = "          This update: ";
      StringBuffer var19 = var1.append(var18);
      Date var20 = this.getThisUpdate();
      StringBuffer var21 = var19.append(var20);
      var21.append(var2);
      String var25 = "          Next update: ";
      StringBuffer var26 = var1.append(var25);
      Date var27 = this.getNextUpdate();
      StringBuffer var28 = var26.append(var27);
      var28.append(var2);
      String var32 = "  Signature Algorithm: ";
      StringBuffer var33 = var1.append(var32);
      String var34 = this.getSigAlgName();
      StringBuffer var35 = var33.append(var34);
      var35.append(var2);
      byte[] var38 = this.getSignature();
      String var40 = "            Signature: ";
      StringBuffer var41 = var1.append(var40);
      byte var43 = 0;
      byte var44 = 20;
      byte[] var45 = Hex.encode(var38, var43, var44);
      String var46 = new String(var45);
      StringBuffer var47 = var41.append(var46);
      var47.append(var2);
      int var50 = 20;

      while(true) {
         int var51 = var38.length;
         if(var50 >= var51) {
            X509Extensions var80 = this.c.getTBSCertList().getExtensions();
            if(var80 != null) {
               Enumeration var81 = var80.oids();
               if(var81.hasMoreElements()) {
                  String var83 = "           Extensions: ";
                  StringBuffer var84 = var1.append(var83);
                  var84.append(var2);
               }

               while(var81.hasMoreElements()) {
                  DERObjectIdentifier var87 = (DERObjectIdentifier)var81.nextElement();
                  X509Extension var88 = var80.getExtension(var87);
                  if(var88.getValue() != null) {
                     byte[] var89 = var88.getValue().getOctets();
                     ASN1InputStream var90 = new ASN1InputStream(var89);
                     String var92 = "                       critical(";
                     StringBuffer var93 = var1.append(var92);
                     boolean var94 = var88.isCritical();
                     StringBuffer var95 = var93.append(var94).append(") ");

                     try {
                        DERObjectIdentifier var96 = X509Extensions.CRLNumber;
                        if(var87.equals(var96)) {
                           BigInteger var99 = DERInteger.getInstance(var90.readObject()).getPositiveValue();
                           CRLNumber var100 = new CRLNumber(var99);
                           StringBuffer var103 = var1.append(var100);
                           var103.append(var2);
                        } else {
                           DERObjectIdentifier var116 = X509Extensions.DeltaCRLIndicator;
                           if(var87.equals(var116)) {
                              StringBuilder var119 = (new StringBuilder()).append("Base CRL: ");
                              BigInteger var120 = DERInteger.getInstance(var90.readObject()).getPositiveValue();
                              CRLNumber var121 = new CRLNumber(var120);
                              String var122 = var119.append(var121).toString();
                              StringBuffer var125 = var1.append(var122);
                              var125.append(var2);
                           } else {
                              DERObjectIdentifier var128 = X509Extensions.IssuingDistributionPoint;
                              if(var87.equals(var128)) {
                                 IssuingDistributionPoint var131 = new IssuingDistributionPoint;
                                 ASN1Sequence var132 = (ASN1Sequence)var90.readObject();
                                 var131.<init>(var132);
                                 StringBuffer var137 = var1.append(var131);
                                 var137.append(var2);
                              } else {
                                 DERObjectIdentifier var140 = X509Extensions.CRLDistributionPoints;
                                 if(var87.equals(var140)) {
                                    CRLDistPoint var143 = new CRLDistPoint;
                                    ASN1Sequence var144 = (ASN1Sequence)var90.readObject();
                                    var143.<init>(var144);
                                    StringBuffer var149 = var1.append(var143);
                                    var149.append(var2);
                                 } else {
                                    DERObjectIdentifier var152 = X509Extensions.FreshestCRL;
                                    if(var87.equals(var152)) {
                                       CRLDistPoint var155 = new CRLDistPoint;
                                       ASN1Sequence var156 = (ASN1Sequence)var90.readObject();
                                       var155.<init>(var156);
                                       StringBuffer var161 = var1.append(var155);
                                       var161.append(var2);
                                    } else {
                                       String var164 = var87.getId();
                                       StringBuffer var167 = var1.append(var164);
                                       String var169 = " value = ";
                                       StringBuffer var170 = var1.append(var169);
                                       String var171 = ASN1Dump.dumpAsString(var90.readObject());
                                       StringBuffer var172 = var170.append(var171);
                                       var172.append(var2);
                                    }
                                 }
                              }
                           }
                        }
                     } catch (Exception var183) {
                        String var107 = var87.getId();
                        StringBuffer var110 = var1.append(var107);
                        String var112 = " value = ";
                        StringBuffer var113 = var1.append(var112).append("*****");
                        var113.append(var2);
                     }
                  } else {
                     var1.append(var2);
                  }
               }
            }

            Set var176 = this.getRevokedCertificates();
            if(var176 != null) {
               Iterator var177 = var176.iterator();

               while(var177.hasNext()) {
                  Object var178 = var177.next();
                  StringBuffer var181 = var1.append(var178);
                  var1.append(var2);
               }
            }

            return var1.toString();
         }

         int var54 = var38.length - 20;
         if(var50 < var54) {
            String var58 = "                       ";
            StringBuffer var59 = var1.append(var58);
            byte var62 = 20;
            byte[] var63 = Hex.encode(var38, var50, var62);
            String var64 = new String(var63);
            StringBuffer var65 = var59.append(var64);
            var65.append(var2);
         } else {
            String var69 = "                       ";
            StringBuffer var70 = var1.append(var69);
            int var71 = var38.length - var50;
            byte[] var75 = Hex.encode(var38, var50, var71);
            String var76 = new String(var75);
            StringBuffer var77 = var70.append(var76);
            var77.append(var2);
         }

         var50 += 20;
      }
   }

   public void verify(PublicKey var1) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      this.verify(var1, "myBC");
   }

   public void verify(PublicKey var1, String var2) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      AlgorithmIdentifier var3 = this.c.getSignatureAlgorithm();
      AlgorithmIdentifier var4 = this.c.getTBSCertList().getSignature();
      if(!var3.equals(var4)) {
         throw new CRLException("Signature algorithm on CertificateList does not match TBSCertList.");
      } else {
         Signature var5 = Signature.getInstance(this.getSigAlgName(), var2);
         var5.initVerify(var1);
         byte[] var6 = this.getTBSCertList();
         var5.update(var6);
         byte[] var7 = this.getSignature();
         if(!var5.verify(var7)) {
            throw new SignatureException("CRL does not verify with supplied public key.");
         }
      }
   }
}

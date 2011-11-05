package myorg.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import myorg.bouncycastle.asn1.misc.NetscapeCertType;
import myorg.bouncycastle.asn1.misc.NetscapeRevocationURL;
import myorg.bouncycastle.asn1.misc.VerisignCzagExtension;
import myorg.bouncycastle.asn1.util.ASN1Dump;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.BasicConstraints;
import myorg.bouncycastle.asn1.x509.KeyUsage;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import myorg.bouncycastle.jce.provider.JDKKeyFactory;
import myorg.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import myorg.bouncycastle.jce.provider.X509SignatureUtil;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.util.encoders.Hex;

public class X509CertificateObject extends X509Certificate implements PKCS12BagAttributeCarrier {

   private PKCS12BagAttributeCarrier attrCarrier;
   private BasicConstraints basicConstraints;
   private X509CertificateStructure c;
   private int hashValue;
   private boolean hashValueSet;
   private boolean[] keyUsage;


   public X509CertificateObject(X509CertificateStructure param1) throws CertificateParsingException {
      // $FF: Couldn't be decompiled
   }

   private int calculateHashCode() {
      int var1;
      int var2;
      try {
         var1 = Arrays.hashCode(this.getEncoded());
      } catch (CertificateEncodingException var4) {
         var2 = 0;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   private void checkSignature(PublicKey var1, Signature var2) throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      AlgorithmIdentifier var3 = this.c.getSignatureAlgorithm();
      AlgorithmIdentifier var4 = this.c.getTBSCertificate().getSignature();
      if(!var3.equals(var4)) {
         throw new CertificateException("signature algorithm in TBS cert not same as outer cert");
      } else {
         DEREncodable var5 = this.c.getSignatureAlgorithm().getParameters();
         X509SignatureUtil.setSignatureParameters(var2, var5);
         var2.initVerify(var1);
         byte[] var6 = this.getTBSCertificate();
         var2.update(var6);
         byte[] var7 = this.getSignature();
         if(!var2.verify(var7)) {
            throw new InvalidKeyException("Public key presented not for certificate signature");
         }
      }
   }

   private byte[] getExtensionBytes(String var1) {
      X509Extensions var2 = this.c.getTBSCertificate().getExtensions();
      byte[] var5;
      if(var2 != null) {
         DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
         X509Extension var4 = var2.getExtension(var3);
         if(var4 != null) {
            var5 = var4.getValue().getOctets();
            return var5;
         }
      }

      var5 = null;
      return var5;
   }

   public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
      Date var1 = new Date();
      this.checkValidity(var1);
   }

   public void checkValidity(Date var1) throws CertificateExpiredException, CertificateNotYetValidException {
      long var2 = var1.getTime();
      long var4 = this.getNotAfter().getTime();
      if(var2 > var4) {
         StringBuilder var6 = (new StringBuilder()).append("certificate expired on ");
         String var7 = this.c.getEndDate().getTime();
         String var8 = var6.append(var7).toString();
         throw new CertificateExpiredException(var8);
      } else {
         long var9 = var1.getTime();
         long var11 = this.getNotBefore().getTime();
         if(var9 < var11) {
            StringBuilder var13 = (new StringBuilder()).append("certificate not valid till ");
            String var14 = this.c.getStartDate().getTime();
            String var15 = var13.append(var14).toString();
            throw new CertificateNotYetValidException(var15);
         }
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof Certificate)) {
         var2 = false;
      } else {
         Certificate var3 = (Certificate)var1;

         boolean var6;
         try {
            byte[] var4 = this.getEncoded();
            byte[] var5 = var3.getEncoded();
            var6 = Arrays.areEqual(var4, var5);
         } catch (CertificateEncodingException var8) {
            var2 = false;
            return var2;
         }

         var2 = var6;
      }

      return var2;
   }

   public DEREncodable getBagAttribute(DERObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   public int getBasicConstraints() {
      int var1;
      if(this.basicConstraints != null) {
         if(this.basicConstraints.isCA()) {
            if(this.basicConstraints.getPathLenConstraint() == null) {
               var1 = Integer.MAX_VALUE;
            } else {
               var1 = this.basicConstraints.getPathLenConstraint().intValue();
            }
         } else {
            var1 = -1;
         }
      } else {
         var1 = -1;
      }

      return var1;
   }

   public Set getCriticalExtensionOIDs() {
      HashSet var7;
      if(this.getVersion() == 3) {
         HashSet var1 = new HashSet();
         X509Extensions var2 = this.c.getTBSCertificate().getExtensions();
         if(var2 != null) {
            Enumeration var3 = var2.oids();

            while(var3.hasMoreElements()) {
               DERObjectIdentifier var4 = (DERObjectIdentifier)var3.nextElement();
               if(var2.getExtension(var4).isCritical()) {
                  String var5 = var4.getId();
                  var1.add(var5);
               }
            }

            var7 = var1;
            return var7;
         }
      }

      var7 = null;
      return var7;
   }

   public byte[] getEncoded() throws CertificateEncodingException {
      try {
         byte[] var1 = this.c.getEncoded("DER");
         return var1;
      } catch (IOException var3) {
         String var2 = var3.toString();
         throw new CertificateEncodingException(var2);
      }
   }

   public List getExtendedKeyUsage() throws CertificateParsingException {
      byte[] var1 = this.getExtensionBytes("2.5.29.37");
      List var9;
      if(var1 != null) {
         List var8;
         try {
            ASN1Sequence var2 = (ASN1Sequence)(new ASN1InputStream(var1)).readObject();
            ArrayList var3 = new ArrayList();
            int var4 = 0;

            while(true) {
               int var5 = var2.size();
               if(var4 == var5) {
                  var8 = Collections.unmodifiableList(var3);
                  break;
               }

               String var6 = ((DERObjectIdentifier)var2.getObjectAt(var4)).getId();
               var3.add(var6);
               ++var4;
            }
         } catch (Exception var11) {
            throw new CertificateParsingException("error processing extended key usage extension");
         }

         var9 = var8;
      } else {
         var9 = null;
      }

      return var9;
   }

   public byte[] getExtensionValue(String var1) {
      X509Extensions var2 = this.c.getTBSCertificate().getExtensions();
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

   public boolean[] getIssuerUniqueID() {
      DERBitString var1 = this.c.getTBSCertificate().getIssuerUniqueId();
      boolean[] var13;
      if(var1 != null) {
         byte[] var2 = var1.getBytes();
         int var3 = var2.length * 8;
         int var4 = var1.getPadBits();
         boolean[] var5 = new boolean[var3 - var4];
         int var6 = 0;

         while(true) {
            int var7 = var5.length;
            if(var6 == var7) {
               var13 = var5;
               break;
            }

            int var8 = var6 / 8;
            byte var9 = var2[var8];
            int var10 = var6 % 8;
            int var11 = 128 >>> var10;
            boolean var12;
            if((var9 & var11) != 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            var5[var6] = var12;
            ++var6;
         }
      } else {
         var13 = null;
      }

      return var13;
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

   public boolean[] getKeyUsage() {
      return this.keyUsage;
   }

   public Set getNonCriticalExtensionOIDs() {
      HashSet var7;
      if(this.getVersion() == 3) {
         HashSet var1 = new HashSet();
         X509Extensions var2 = this.c.getTBSCertificate().getExtensions();
         if(var2 != null) {
            Enumeration var3 = var2.oids();

            while(var3.hasMoreElements()) {
               DERObjectIdentifier var4 = (DERObjectIdentifier)var3.nextElement();
               if(!var2.getExtension(var4).isCritical()) {
                  String var5 = var4.getId();
                  var1.add(var5);
               }
            }

            var7 = var1;
            return var7;
         }
      }

      var7 = null;
      return var7;
   }

   public Date getNotAfter() {
      return this.c.getEndDate().getDate();
   }

   public Date getNotBefore() {
      return this.c.getStartDate().getDate();
   }

   public PublicKey getPublicKey() {
      return JDKKeyFactory.createPublicKeyFromPublicKeyInfo(this.c.getSubjectPublicKeyInfo());
   }

   public BigInteger getSerialNumber() {
      return this.c.getSerialNumber().getValue();
   }

   public String getSigAlgName() {
      Provider var1 = Security.getProvider("myBC");
      String var5;
      String var6;
      if(var1 != null) {
         StringBuilder var2 = (new StringBuilder()).append("Alg.Alias.Signature.");
         String var3 = this.getSigAlgOID();
         String var4 = var2.append(var3).toString();
         var5 = var1.getProperty(var4);
         if(var5 != null) {
            var6 = var5;
            return var6;
         }
      }

      Provider[] var7 = Security.getProviders();
      int var8 = 0;

      while(true) {
         int var9 = var7.length;
         if(var8 == var9) {
            var6 = this.getSigAlgOID();
            break;
         }

         Provider var10 = var7[var8];
         StringBuilder var11 = (new StringBuilder()).append("Alg.Alias.Signature.");
         String var12 = this.getSigAlgOID();
         String var13 = var11.append(var12).toString();
         var5 = var10.getProperty(var13);
         if(var5 != null) {
            var6 = var5;
            break;
         }

         ++var8;
      }

      return var6;
   }

   public String getSigAlgOID() {
      return this.c.getSignatureAlgorithm().getObjectId().getId();
   }

   public byte[] getSigAlgParams() {
      byte[] var1;
      if(this.c.getSignatureAlgorithm().getParameters() != null) {
         var1 = this.c.getSignatureAlgorithm().getParameters().getDERObject().getDEREncoded();
      } else {
         var1 = null;
      }

      return var1;
   }

   public byte[] getSignature() {
      return this.c.getSignature().getBytes();
   }

   public Principal getSubjectDN() {
      X509Name var1 = this.c.getSubject();
      return new X509Principal(var1);
   }

   public boolean[] getSubjectUniqueID() {
      DERBitString var1 = this.c.getTBSCertificate().getSubjectUniqueId();
      boolean[] var13;
      if(var1 != null) {
         byte[] var2 = var1.getBytes();
         int var3 = var2.length * 8;
         int var4 = var1.getPadBits();
         boolean[] var5 = new boolean[var3 - var4];
         int var6 = 0;

         while(true) {
            int var7 = var5.length;
            if(var6 == var7) {
               var13 = var5;
               break;
            }

            int var8 = var6 / 8;
            byte var9 = var2[var8];
            int var10 = var6 % 8;
            int var11 = 128 >>> var10;
            boolean var12;
            if((var9 & var11) != 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            var5[var6] = var12;
            ++var6;
         }
      } else {
         var13 = null;
      }

      return var13;
   }

   public X500Principal getSubjectX500Principal() {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         ASN1OutputStream var2 = new ASN1OutputStream(var1);
         X509Name var3 = this.c.getSubject();
         var2.writeObject(var3);
         byte[] var4 = var1.toByteArray();
         X500Principal var5 = new X500Principal(var4);
         return var5;
      } catch (IOException var7) {
         throw new IllegalStateException("can\'t encode issuer DN");
      }
   }

   public byte[] getTBSCertificate() throws CertificateEncodingException {
      try {
         byte[] var1 = this.c.getTBSCertificate().getEncoded("DER");
         return var1;
      } catch (IOException var3) {
         String var2 = var3.toString();
         throw new CertificateEncodingException(var2);
      }
   }

   public int getVersion() {
      return this.c.getVersion();
   }

   public boolean hasUnsupportedCriticalExtension() {
      boolean var16;
      if(this.getVersion() == 3) {
         X509Extensions var1 = this.c.getTBSCertificate().getExtensions();
         if(var1 != null) {
            Enumeration var2 = var1.oids();

            while(var2.hasMoreElements()) {
               DERObjectIdentifier var3 = (DERObjectIdentifier)var2.nextElement();
               String var4 = var3.getId();
               String var5 = RFC3280CertPathUtilities.KEY_USAGE;
               if(!var4.equals(var5)) {
                  String var6 = RFC3280CertPathUtilities.CERTIFICATE_POLICIES;
                  if(!var4.equals(var6)) {
                     String var7 = RFC3280CertPathUtilities.POLICY_MAPPINGS;
                     if(!var4.equals(var7)) {
                        String var8 = RFC3280CertPathUtilities.INHIBIT_ANY_POLICY;
                        if(!var4.equals(var8)) {
                           String var9 = RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS;
                           if(!var4.equals(var9)) {
                              String var10 = RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT;
                              if(!var4.equals(var10)) {
                                 String var11 = RFC3280CertPathUtilities.DELTA_CRL_INDICATOR;
                                 if(!var4.equals(var11)) {
                                    String var12 = RFC3280CertPathUtilities.POLICY_CONSTRAINTS;
                                    if(!var4.equals(var12)) {
                                       String var13 = RFC3280CertPathUtilities.BASIC_CONSTRAINTS;
                                       if(!var4.equals(var13)) {
                                          String var14 = RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME;
                                          if(!var4.equals(var14)) {
                                             String var15 = RFC3280CertPathUtilities.NAME_CONSTRAINTS;
                                             if(!var4.equals(var15) && var1.getExtension(var3).isCritical()) {
                                                var16 = true;
                                                return var16;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      var16 = false;
      return var16;
   }

   public int hashCode() {
      synchronized(this){}

      int var2;
      try {
         if(!this.hashValueSet) {
            int var1 = this.calculateHashCode();
            this.hashValue = var1;
            this.hashValueSet = (boolean)1;
         }

         var2 = this.hashValue;
      } finally {
         ;
      }

      return var2;
   }

   public void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("  [0]         Version: ");
      int var4 = this.getVersion();
      StringBuffer var5 = var3.append(var4).append(var2);
      StringBuffer var6 = var1.append("         SerialNumber: ");
      BigInteger var7 = this.getSerialNumber();
      StringBuffer var8 = var6.append(var7).append(var2);
      StringBuffer var9 = var1.append("             IssuerDN: ");
      Principal var10 = this.getIssuerDN();
      StringBuffer var11 = var9.append(var10).append(var2);
      StringBuffer var12 = var1.append("           Start Date: ");
      Date var13 = this.getNotBefore();
      StringBuffer var14 = var12.append(var13).append(var2);
      StringBuffer var15 = var1.append("           Final Date: ");
      Date var16 = this.getNotAfter();
      StringBuffer var17 = var15.append(var16).append(var2);
      StringBuffer var18 = var1.append("            SubjectDN: ");
      Principal var19 = this.getSubjectDN();
      StringBuffer var20 = var18.append(var19).append(var2);
      StringBuffer var21 = var1.append("           Public Key: ");
      PublicKey var22 = this.getPublicKey();
      StringBuffer var23 = var21.append(var22).append(var2);
      StringBuffer var24 = var1.append("  Signature Algorithm: ");
      String var25 = this.getSigAlgName();
      StringBuffer var26 = var24.append(var25).append(var2);
      byte[] var27 = this.getSignature();
      StringBuffer var28 = var1.append("            Signature: ");
      byte[] var29 = Hex.encode(var27, 0, 20);
      String var30 = new String(var29);
      StringBuffer var31 = var28.append(var30).append(var2);
      int var32 = 20;

      while(true) {
         int var33 = var27.length;
         if(var32 >= var33) {
            X509Extensions var44 = this.c.getTBSCertificate().getExtensions();
            if(var44 != null) {
               Enumeration var45 = var44.oids();
               if(var45.hasMoreElements()) {
                  StringBuffer var46 = var1.append("       Extensions: \n");
               }

               while(var45.hasMoreElements()) {
                  DERObjectIdentifier var47 = (DERObjectIdentifier)var45.nextElement();
                  X509Extension var48 = var44.getExtension(var47);
                  if(var48.getValue() != null) {
                     byte[] var49 = var48.getValue().getOctets();
                     ASN1InputStream var50 = new ASN1InputStream(var49);
                     StringBuffer var51 = var1.append("                       critical(");
                     boolean var52 = var48.isCritical();
                     StringBuffer var53 = var51.append(var52).append(") ");

                     try {
                        DERObjectIdentifier var54 = X509Extensions.BasicConstraints;
                        if(var47.equals(var54)) {
                           ASN1Sequence var55 = (ASN1Sequence)var50.readObject();
                           BasicConstraints var56 = new BasicConstraints(var55);
                           StringBuffer var57 = var1.append(var56).append(var2);
                        } else {
                           DERObjectIdentifier var62 = X509Extensions.KeyUsage;
                           if(var47.equals(var62)) {
                              DERBitString var63 = (DERBitString)var50.readObject();
                              KeyUsage var64 = new KeyUsage(var63);
                              StringBuffer var65 = var1.append(var64).append(var2);
                           } else {
                              DERObjectIdentifier var66 = MiscObjectIdentifiers.netscapeCertType;
                              if(var47.equals(var66)) {
                                 DERBitString var67 = (DERBitString)var50.readObject();
                                 NetscapeCertType var68 = new NetscapeCertType(var67);
                                 StringBuffer var69 = var1.append(var68).append(var2);
                              } else {
                                 DERObjectIdentifier var70 = MiscObjectIdentifiers.netscapeRevocationURL;
                                 if(var47.equals(var70)) {
                                    DERIA5String var71 = (DERIA5String)var50.readObject();
                                    NetscapeRevocationURL var72 = new NetscapeRevocationURL(var71);
                                    StringBuffer var73 = var1.append(var72).append(var2);
                                 } else {
                                    DERObjectIdentifier var74 = MiscObjectIdentifiers.verisignCzagExtension;
                                    if(var47.equals(var74)) {
                                       DERIA5String var75 = (DERIA5String)var50.readObject();
                                       VerisignCzagExtension var76 = new VerisignCzagExtension(var75);
                                       StringBuffer var77 = var1.append(var76).append(var2);
                                    } else {
                                       String var78 = var47.getId();
                                       var1.append(var78);
                                       StringBuffer var80 = var1.append(" value = ");
                                       String var81 = ASN1Dump.dumpAsString(var50.readObject());
                                       StringBuffer var82 = var80.append(var81).append(var2);
                                    }
                                 }
                              }
                           }
                        }
                     } catch (Exception var84) {
                        String var59 = var47.getId();
                        var1.append(var59);
                        StringBuffer var61 = var1.append(" value = ").append("*****").append(var2);
                     }
                  } else {
                     var1.append(var2);
                  }
               }
            }

            return var1.toString();
         }

         int var34 = var27.length - 20;
         if(var32 < var34) {
            StringBuffer var35 = var1.append("                       ");
            byte[] var36 = Hex.encode(var27, var32, 20);
            String var37 = new String(var36);
            StringBuffer var38 = var35.append(var37).append(var2);
         } else {
            StringBuffer var39 = var1.append("                       ");
            int var40 = var27.length - var32;
            byte[] var41 = Hex.encode(var27, var32, var40);
            String var42 = new String(var41);
            StringBuffer var43 = var39.append(var42).append(var2);
         }

         var32 += 20;
      }
   }

   public final void verify(PublicKey var1) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      String var2 = X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());

      Signature var4;
      label13: {
         Signature var3;
         try {
            var3 = Signature.getInstance(var2, "myBC");
         } catch (Exception var6) {
            var4 = Signature.getInstance(var2);
            break label13;
         }

         var4 = var3;
      }

      this.checkSignature(var1, var4);
   }

   public final void verify(PublicKey var1, String var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      Signature var3 = Signature.getInstance(X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm()), var2);
      this.checkSignature(var1, var3);
   }
}

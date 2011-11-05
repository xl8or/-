package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.util.ASN1Dump;
import myorg.bouncycastle.asn1.x509.CRLReason;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.TBSCertList;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class X509CRLEntryObject extends X509CRLEntry {

   private TBSCertList.CRLEntry c;
   private X500Principal certificateIssuer;
   private int hashValue;
   private boolean isHashValueSet;
   private boolean isIndirect;
   private X500Principal previousCertificateIssuer;


   public X509CRLEntryObject(TBSCertList.CRLEntry var1) {
      this.c = var1;
      X500Principal var2 = this.loadCertificateIssuer();
      this.certificateIssuer = var2;
   }

   public X509CRLEntryObject(TBSCertList.CRLEntry var1, boolean var2, X500Principal var3) {
      this.c = var1;
      this.isIndirect = var2;
      this.previousCertificateIssuer = var3;
      X500Principal var4 = this.loadCertificateIssuer();
      this.certificateIssuer = var4;
   }

   private Set getExtensionOIDs(boolean var1) {
      X509Extensions var2 = this.c.getExtensions();
      HashSet var9;
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
      } else {
         var9 = null;
      }

      return var9;
   }

   private X500Principal loadCertificateIssuer() {
      // $FF: Couldn't be decompiled
   }

   public X500Principal getCertificateIssuer() {
      return this.certificateIssuer;
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
      X509Extensions var2 = this.c.getExtensions();
      byte[] var6;
      if(var2 != null) {
         DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
         X509Extension var4 = var2.getExtension(var3);
         if(var4 != null) {
            byte[] var5;
            try {
               var5 = var4.getValue().getEncoded();
            } catch (Exception var11) {
               StringBuilder var8 = (new StringBuilder()).append("error encoding ");
               String var9 = var11.toString();
               String var10 = var8.append(var9).toString();
               throw new RuntimeException(var10);
            }

            var6 = var5;
            return var6;
         }
      }

      var6 = null;
      return var6;
   }

   public Set getNonCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)0);
   }

   public Date getRevocationDate() {
      return this.c.getRevocationDate().getDate();
   }

   public BigInteger getSerialNumber() {
      return this.c.getUserCertificate().getValue();
   }

   public boolean hasExtensions() {
      boolean var1;
      if(this.c.getExtensions() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasUnsupportedCriticalExtension() {
      Set var1 = this.getCriticalExtensionOIDs();
      boolean var2;
      if(var1 != null && !var1.isEmpty()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      if(!this.isHashValueSet) {
         int var1 = super.hashCode();
         this.hashValue = var1;
         this.isHashValueSet = (boolean)1;
      }

      return this.hashValue;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("      userCertificate: ");
      BigInteger var4 = this.getSerialNumber();
      StringBuffer var5 = var3.append(var4).append(var2);
      StringBuffer var6 = var1.append("       revocationDate: ");
      Date var7 = this.getRevocationDate();
      StringBuffer var8 = var6.append(var7).append(var2);
      StringBuffer var9 = var1.append("       certificateIssuer: ");
      X500Principal var10 = this.getCertificateIssuer();
      StringBuffer var11 = var9.append(var10).append(var2);
      X509Extensions var12 = this.c.getExtensions();
      if(var12 != null) {
         Enumeration var13 = var12.oids();
         if(var13.hasMoreElements()) {
            StringBuffer var14 = var1.append("   crlEntryExtensions:").append(var2);

            while(var13.hasMoreElements()) {
               DERObjectIdentifier var15 = (DERObjectIdentifier)var13.nextElement();
               X509Extension var16 = var12.getExtension(var15);
               if(var16.getValue() != null) {
                  byte[] var17 = var16.getValue().getOctets();
                  ASN1InputStream var18 = new ASN1InputStream(var17);
                  StringBuffer var19 = var1.append("                       critical(");
                  boolean var20 = var16.isCritical();
                  StringBuffer var21 = var19.append(var20).append(") ");

                  try {
                     DERObjectIdentifier var22 = X509Extensions.ReasonCode;
                     if(var15.equals(var22)) {
                        DEREnumerated var23 = DEREnumerated.getInstance(var18.readObject());
                        CRLReason var24 = new CRLReason(var23);
                        StringBuffer var25 = var1.append(var24).append(var2);
                     } else {
                        DERObjectIdentifier var30 = X509Extensions.CertificateIssuer;
                        if(var15.equals(var30)) {
                           StringBuffer var31 = var1.append("Certificate issuer: ");
                           ASN1Sequence var32 = (ASN1Sequence)var18.readObject();
                           GeneralNames var33 = new GeneralNames(var32);
                           StringBuffer var34 = var31.append(var33).append(var2);
                        } else {
                           String var35 = var15.getId();
                           var1.append(var35);
                           StringBuffer var37 = var1.append(" value = ");
                           String var38 = ASN1Dump.dumpAsString(var18.readObject());
                           StringBuffer var39 = var37.append(var38).append(var2);
                        }
                     }
                  } catch (Exception var41) {
                     String var27 = var15.getId();
                     var1.append(var27);
                     StringBuffer var29 = var1.append(" value = ").append("*****").append(var2);
                  }
               } else {
                  var1.append(var2);
               }
            }
         }
      }

      return var1.toString();
   }
}

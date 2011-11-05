package myorg.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AttCertIssuer;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.V2Form;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.util.Selector;

public class AttributeCertificateIssuer implements CertSelector, Selector {

   final ASN1Encodable form;


   public AttributeCertificateIssuer(X500Principal var1) throws IOException {
      byte[] var2 = var1.getEncoded();
      X509Principal var3 = new X509Principal(var2);
      this(var3);
   }

   public AttributeCertificateIssuer(AttCertIssuer var1) {
      ASN1Encodable var2 = var1.getIssuer();
      this.form = var2;
   }

   public AttributeCertificateIssuer(X509Principal var1) {
      GeneralName var2 = new GeneralName(var1);
      DERSequence var3 = new DERSequence(var2);
      GeneralNames var4 = new GeneralNames(var3);
      V2Form var5 = new V2Form(var4);
      this.form = var5;
   }

   private Object[] getNames() {
      GeneralNames var1;
      if(this.form instanceof V2Form) {
         var1 = ((V2Form)this.form).getIssuerName();
      } else {
         var1 = (GeneralNames)this.form;
      }

      GeneralName[] var2 = var1.getNames();
      int var3 = var2.length;
      ArrayList var4 = new ArrayList(var3);
      int var5 = 0;

      while(true) {
         int var6 = var2.length;
         if(var5 == var6) {
            Object[] var11 = new Object[var4.size()];
            return var4.toArray(var11);
         }

         if(var2[var5].getTagNo() == 4) {
            try {
               byte[] var7 = ((ASN1Encodable)var2[var5].getName()).getEncoded();
               X500Principal var8 = new X500Principal(var7);
               var4.add(var8);
            } catch (IOException var12) {
               throw new RuntimeException("badly formed Name object");
            }
         }

         ++var5;
      }
   }

   private boolean matchesDN(X500Principal var1, GeneralNames var2) {
      GeneralName[] var3 = var2.getNames();
      int var4 = 0;

      boolean var9;
      while(true) {
         int var5 = var3.length;
         if(var4 == var5) {
            var9 = false;
            break;
         }

         GeneralName var6 = var3[var4];
         if(var6.getTagNo() == 4) {
            label21: {
               boolean var8;
               try {
                  byte[] var7 = ((ASN1Encodable)var6.getName()).getEncoded();
                  var8 = (new X500Principal(var7)).equals(var1);
               } catch (IOException var11) {
                  break label21;
               }

               if(var8) {
                  var9 = true;
                  break;
               }
            }
         }

         ++var4;
      }

      return var9;
   }

   public Object clone() {
      AttCertIssuer var1 = AttCertIssuer.getInstance(this.form);
      return new AttributeCertificateIssuer(var1);
   }

   public boolean equals(Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else if(!(var1 instanceof AttributeCertificateIssuer)) {
         var2 = 0;
      } else {
         AttributeCertificateIssuer var3 = (AttributeCertificateIssuer)var1;
         ASN1Encodable var4 = this.form;
         ASN1Encodable var5 = var3.form;
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   public Principal[] getPrincipals() {
      Object[] var1 = this.getNames();
      ArrayList var2 = new ArrayList();
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 == var4) {
            Principal[] var7 = new Principal[var2.size()];
            return (Principal[])((Principal[])var2.toArray(var7));
         }

         if(var1[var3] instanceof Principal) {
            Object var5 = var1[var3];
            var2.add(var5);
         }

         ++var3;
      }
   }

   public int hashCode() {
      return this.form.hashCode();
   }

   public boolean match(Object var1) {
      byte var2;
      if(!(var1 instanceof X509Certificate)) {
         var2 = 0;
      } else {
         Certificate var3 = (Certificate)var1;
         var2 = this.match(var3);
      }

      return (boolean)var2;
   }

   public boolean match(Certificate var1) {
      boolean var2;
      if(!(var1 instanceof X509Certificate)) {
         var2 = false;
      } else {
         X509Certificate var3 = (X509Certificate)var1;
         if(this.form instanceof V2Form) {
            V2Form var4 = (V2Form)this.form;
            if(var4.getBaseCertificateID() != null) {
               BigInteger var5 = var4.getBaseCertificateID().getSerial().getValue();
               BigInteger var6 = var3.getSerialNumber();
               if(var5.equals(var6)) {
                  X500Principal var7 = var3.getIssuerX500Principal();
                  GeneralNames var8 = var4.getBaseCertificateID().getIssuer();
                  if(this.matchesDN(var7, var8)) {
                     var2 = true;
                     return var2;
                  }
               }

               var2 = false;
               return var2;
            }

            GeneralNames var9 = var4.getIssuerName();
            X500Principal var10 = var3.getSubjectX500Principal();
            if(this.matchesDN(var10, var9)) {
               var2 = true;
               return var2;
            }
         } else {
            GeneralNames var11 = (GeneralNames)this.form;
            X500Principal var12 = var3.getSubjectX500Principal();
            if(this.matchesDN(var12, var11)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }
}

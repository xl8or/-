package myorg.bouncycastle.x509;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.x509.CertificatePair;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.jce.provider.X509CertificateObject;
import myorg.bouncycastle.x509.ExtCertificateEncodingException;

public class X509CertificatePair {

   private X509Certificate forward;
   private X509Certificate reverse;


   public X509CertificatePair(X509Certificate var1, X509Certificate var2) {
      this.forward = var1;
      this.reverse = var2;
   }

   public X509CertificatePair(CertificatePair var1) throws CertificateParsingException {
      if(var1.getForward() != null) {
         X509CertificateStructure var2 = var1.getForward();
         X509CertificateObject var3 = new X509CertificateObject(var2);
         this.forward = var3;
      }

      if(var1.getReverse() != null) {
         X509CertificateStructure var4 = var1.getReverse();
         X509CertificateObject var5 = new X509CertificateObject(var4);
         this.reverse = var5;
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(!(var1 instanceof X509CertificatePair)) {
         var2 = false;
      } else {
         X509CertificatePair var3 = (X509CertificatePair)var1;
         byte var4 = 1;
         byte var5 = 1;
         if(this.forward != null) {
            X509Certificate var6 = this.forward;
            X509Certificate var7 = var3.forward;
            var5 = var6.equals(var7);
         } else if(var3.forward != null) {
            var5 = 0;
         }

         if(this.reverse != null) {
            X509Certificate var8 = this.reverse;
            X509Certificate var9 = var3.reverse;
            var4 = var8.equals(var9);
         } else if(var3.reverse != null) {
            var4 = 0;
         }

         if(var5 != 0 && var4 != 0) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public byte[] getEncoded() throws CertificateEncodingException {
      X509CertificateStructure var1 = null;
      X509CertificateStructure var2 = null;

      try {
         if(this.forward != null) {
            byte[] var3 = this.forward.getEncoded();
            var1 = X509CertificateStructure.getInstance((new ASN1InputStream(var3)).readObject());
         }

         if(this.reverse != null) {
            byte[] var4 = this.reverse.getEncoded();
            var2 = X509CertificateStructure.getInstance((new ASN1InputStream(var4)).readObject());
         }

         byte[] var5 = (new CertificatePair(var1, var2)).getDEREncoded();
         return var5;
      } catch (IllegalArgumentException var10) {
         String var7 = var10.toString();
         throw new ExtCertificateEncodingException(var7, var10);
      } catch (IOException var11) {
         String var9 = var11.toString();
         throw new ExtCertificateEncodingException(var9, var11);
      }
   }

   public X509Certificate getForward() {
      return this.forward;
   }

   public X509Certificate getReverse() {
      return this.reverse;
   }

   public int hashCode() {
      int var1 = -1;
      if(this.forward != null) {
         int var2 = this.forward.hashCode();
         var1 = ~var2;
      }

      if(this.reverse != null) {
         int var3 = var1 * 17;
         int var4 = this.reverse.hashCode();
         var1 = var3 ^ var4;
      }

      return var1;
   }
}

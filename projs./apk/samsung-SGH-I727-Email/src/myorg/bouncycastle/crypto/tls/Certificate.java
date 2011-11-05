package myorg.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.crypto.tls.TlsUtils;

public class Certificate {

   protected X509CertificateStructure[] certs;


   private Certificate(X509CertificateStructure[] var1) {
      this.certs = var1;
   }

   protected static Certificate parse(InputStream var0) throws IOException {
      int var1 = TlsUtils.readUint24(var0);
      Vector var2 = new Vector();

      while(var1 > 0) {
         int var3 = TlsUtils.readUint24(var0);
         int var4 = var3 + 3;
         var1 -= var4;
         byte[] var5 = new byte[var3];
         TlsUtils.readFully(var5, var0);
         ByteArrayInputStream var6 = new ByteArrayInputStream(var5);
         X509CertificateStructure var7 = X509CertificateStructure.getInstance((new ASN1InputStream(var6)).readObject());
         var2.addElement(var7);
         if(var6.available() > 0) {
            throw new IllegalArgumentException("Sorry, there is garbage data left after the certificate");
         }
      }

      X509CertificateStructure[] var8 = new X509CertificateStructure[var2.size()];
      int var9 = 0;

      while(true) {
         int var10 = var2.size();
         if(var9 >= var10) {
            return new Certificate(var8);
         }

         X509CertificateStructure var11 = (X509CertificateStructure)var2.elementAt(var9);
         var8[var9] = var11;
         ++var9;
      }
   }

   public X509CertificateStructure[] getCerts() {
      X509CertificateStructure[] var1 = new X509CertificateStructure[this.certs.length];
      X509CertificateStructure[] var2 = this.certs;
      int var3 = this.certs.length;
      System.arraycopy(var2, 0, var1, 0, var3);
      return var1;
   }
}

package myorg.bouncycastle.ocsp;

import java.security.cert.X509Extension;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.ocsp.CertID;
import myorg.bouncycastle.asn1.ocsp.Request;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.ocsp.CertificateID;

public class Req implements X509Extension {

   private Request req;


   public Req(Request var1) {
      this.req = var1;
   }

   private Set getExtensionOIDs(boolean var1) {
      HashSet var2 = new HashSet();
      X509Extensions var3 = this.getSingleRequestExtensions();
      if(var3 != null) {
         Enumeration var4 = var3.oids();

         while(var4.hasMoreElements()) {
            DERObjectIdentifier var5 = (DERObjectIdentifier)var4.nextElement();
            boolean var6 = var3.getExtension(var5).isCritical();
            if(var1 == var6) {
               String var7 = var5.getId();
               var2.add(var7);
            }
         }
      }

      return var2;
   }

   public CertificateID getCertID() {
      CertID var1 = this.req.getReqCert();
      return new CertificateID(var1);
   }

   public Set getCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)1);
   }

   public byte[] getExtensionValue(String var1) {
      X509Extensions var2 = this.getSingleRequestExtensions();
      byte[] var6;
      if(var2 != null) {
         DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
         myorg.bouncycastle.asn1.x509.X509Extension var4 = var2.getExtension(var3);
         if(var4 != null) {
            byte[] var5;
            try {
               var5 = var4.getValue().getEncoded("DER");
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

   public X509Extensions getSingleRequestExtensions() {
      return this.req.getSingleRequestExtensions();
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
}

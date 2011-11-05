package myorg.bouncycastle.ocsp;

import java.security.cert.X509Extension;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.ocsp.CertID;
import myorg.bouncycastle.asn1.ocsp.CertStatus;
import myorg.bouncycastle.asn1.ocsp.RevokedInfo;
import myorg.bouncycastle.asn1.ocsp.SingleResponse;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.ocsp.CertificateID;
import myorg.bouncycastle.ocsp.RevokedStatus;
import myorg.bouncycastle.ocsp.UnknownStatus;

public class SingleResp implements X509Extension {

   SingleResponse resp;


   public SingleResp(SingleResponse var1) {
      this.resp = var1;
   }

   private Set getExtensionOIDs(boolean var1) {
      HashSet var2 = new HashSet();
      X509Extensions var3 = this.getSingleExtensions();
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
      CertID var1 = this.resp.getCertID();
      return new CertificateID(var1);
   }

   public Object getCertStatus() {
      CertStatus var1 = this.resp.getCertStatus();
      Object var2;
      if(var1.getTagNo() == 0) {
         var2 = null;
      } else if(var1.getTagNo() == 1) {
         RevokedInfo var3 = RevokedInfo.getInstance(var1.getStatus());
         var2 = new RevokedStatus(var3);
      } else {
         var2 = new UnknownStatus();
      }

      return var2;
   }

   public Set getCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)1);
   }

   public byte[] getExtensionValue(String var1) {
      X509Extensions var2 = this.getSingleExtensions();
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

   public Date getNextUpdate() {
      Date var1;
      if(this.resp.getNextUpdate() == null) {
         var1 = null;
      } else {
         Date var2;
         try {
            var2 = this.resp.getNextUpdate().getDate();
         } catch (ParseException var7) {
            StringBuilder var4 = (new StringBuilder()).append("ParseException: ");
            String var5 = var7.getMessage();
            String var6 = var4.append(var5).toString();
            throw new IllegalStateException(var6);
         }

         var1 = var2;
      }

      return var1;
   }

   public Set getNonCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)0);
   }

   public X509Extensions getSingleExtensions() {
      return this.resp.getSingleExtensions();
   }

   public Date getThisUpdate() {
      try {
         Date var1 = this.resp.getThisUpdate().getDate();
         return var1;
      } catch (ParseException var6) {
         StringBuilder var3 = (new StringBuilder()).append("ParseException: ");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new IllegalStateException(var5);
      }
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

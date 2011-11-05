package myorg.bouncycastle.ocsp;

import java.security.cert.X509Extension;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.ocsp.ResponderID;
import myorg.bouncycastle.asn1.ocsp.ResponseData;
import myorg.bouncycastle.asn1.ocsp.SingleResponse;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.ocsp.RespID;
import myorg.bouncycastle.ocsp.SingleResp;

public class RespData implements X509Extension {

   ResponseData data;


   public RespData(ResponseData var1) {
      this.data = var1;
   }

   private Set getExtensionOIDs(boolean var1) {
      HashSet var2 = new HashSet();
      X509Extensions var3 = this.getResponseExtensions();
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

   public Set getCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)1);
   }

   public byte[] getExtensionValue(String var1) {
      X509Extensions var2 = this.getResponseExtensions();
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

   public Date getProducedAt() {
      try {
         Date var1 = this.data.getProducedAt().getDate();
         return var1;
      } catch (ParseException var6) {
         StringBuilder var3 = (new StringBuilder()).append("ParseException:");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new IllegalStateException(var5);
      }
   }

   public RespID getResponderId() {
      ResponderID var1 = this.data.getResponderID();
      return new RespID(var1);
   }

   public X509Extensions getResponseExtensions() {
      return this.data.getResponseExtensions();
   }

   public SingleResp[] getResponses() {
      ASN1Sequence var1 = this.data.getResponses();
      SingleResp[] var2 = new SingleResp[var1.size()];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            return var2;
         }

         SingleResponse var5 = SingleResponse.getInstance(var1.getObjectAt(var3));
         SingleResp var6 = new SingleResp(var5);
         var2[var3] = var6;
         ++var3;
      }
   }

   public int getVersion() {
      return this.data.getVersion().getValue().intValue() + 1;
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

package myorg.bouncycastle.asn1.x509;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DERBoolean;

public class X509Extension {

   boolean critical;
   ASN1OctetString value;


   public X509Extension(DERBoolean var1, ASN1OctetString var2) {
      boolean var3 = var1.isTrue();
      this.critical = var3;
      this.value = var2;
   }

   public X509Extension(boolean var1, ASN1OctetString var2) {
      this.critical = var1;
      this.value = var2;
   }

   public static ASN1Object convertValueToObject(X509Extension var0) throws IllegalArgumentException {
      try {
         ASN1Object var1 = ASN1Object.fromByteArray(var0.getValue().getOctets());
         return var1;
      } catch (IOException var4) {
         String var3 = "can\'t convert extension: " + var4;
         throw new IllegalArgumentException(var3);
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof X509Extension)) {
         var2 = false;
      } else {
         X509Extension var3 = (X509Extension)var1;
         ASN1OctetString var4 = var3.getValue();
         ASN1OctetString var5 = this.getValue();
         if(var4.equals(var5)) {
            boolean var6 = var3.isCritical();
            boolean var7 = this.isCritical();
            if(var6 == var7) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public ASN1OctetString getValue() {
      return this.value;
   }

   public int hashCode() {
      int var1;
      if(this.isCritical()) {
         var1 = this.getValue().hashCode();
      } else {
         var1 = ~this.getValue().hashCode();
      }

      return var1;
   }

   public boolean isCritical() {
      return this.critical;
   }
}

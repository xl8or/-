package myorg.bouncycastle.cms;

import java.math.BigInteger;
import java.security.cert.X509CertSelector;
import myorg.bouncycastle.util.Arrays;

class OriginatorId extends X509CertSelector {

   OriginatorId() {}

   private boolean equalsObj(Object var1, Object var2) {
      boolean var3;
      if(var1 != null) {
         var3 = var1.equals(var2);
      } else if(var2 == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof OriginatorId)) {
         var2 = false;
      } else {
         OriginatorId var3 = (OriginatorId)var1;
         byte[] var4 = this.getSubjectKeyIdentifier();
         byte[] var5 = var3.getSubjectKeyIdentifier();
         if(Arrays.areEqual(var4, var5)) {
            BigInteger var6 = this.getSerialNumber();
            BigInteger var7 = var3.getSerialNumber();
            if(this.equalsObj(var6, var7)) {
               String var8 = this.getIssuerAsString();
               String var9 = var3.getIssuerAsString();
               if(this.equalsObj(var8, var9)) {
                  var2 = true;
                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      int var1 = Arrays.hashCode(this.getSubjectKeyIdentifier());
      if(this.getSerialNumber() != null) {
         int var2 = this.getSerialNumber().hashCode();
         var1 ^= var2;
      }

      if(this.getIssuerAsString() != null) {
         int var3 = this.getIssuerAsString().hashCode();
         var1 ^= var3;
      }

      return var1;
   }
}

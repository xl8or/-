package myorg.bouncycastle.cms;

import java.math.BigInteger;
import java.security.cert.X509CertSelector;
import myorg.bouncycastle.util.Arrays;

public class RecipientId extends X509CertSelector {

   byte[] keyIdentifier = null;


   public RecipientId() {}

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
      if(!(var1 instanceof RecipientId)) {
         var2 = false;
      } else {
         RecipientId var3 = (RecipientId)var1;
         byte[] var4 = this.keyIdentifier;
         byte[] var5 = var3.keyIdentifier;
         if(Arrays.areEqual(var4, var5)) {
            byte[] var6 = this.getSubjectKeyIdentifier();
            byte[] var7 = var3.getSubjectKeyIdentifier();
            if(Arrays.areEqual(var6, var7)) {
               BigInteger var8 = this.getSerialNumber();
               BigInteger var9 = var3.getSerialNumber();
               if(this.equalsObj(var8, var9)) {
                  String var10 = this.getIssuerAsString();
                  String var11 = var3.getIssuerAsString();
                  if(this.equalsObj(var10, var11)) {
                     var2 = true;
                     return var2;
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public byte[] getKeyIdentifier() {
      return this.keyIdentifier;
   }

   public int hashCode() {
      int var1 = Arrays.hashCode(this.keyIdentifier);
      int var2 = Arrays.hashCode(this.getSubjectKeyIdentifier());
      int var3 = var1 ^ var2;
      BigInteger var4 = this.getSerialNumber();
      if(var4 != null) {
         int var5 = var4.hashCode();
         var3 ^= var5;
      }

      String var6 = this.getIssuerAsString();
      if(var6 != null) {
         int var7 = var6.hashCode();
         var3 ^= var7;
      }

      return var3;
   }

   public void setKeyIdentifier(byte[] var1) {
      this.keyIdentifier = var1;
   }
}

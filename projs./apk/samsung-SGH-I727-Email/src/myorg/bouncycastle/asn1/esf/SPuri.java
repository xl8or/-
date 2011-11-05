package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;

public class SPuri {

   private DERIA5String uri;


   public SPuri(DERIA5String var1) {
      this.uri = var1;
   }

   public static SPuri getInstance(Object var0) {
      SPuri var1;
      if(var0 instanceof SPuri) {
         var1 = (SPuri)var0;
      } else {
         if(!(var0 instanceof DERIA5String)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'SPuri\' factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         DERIA5String var2 = (DERIA5String)var0;
         var1 = new SPuri(var2);
      }

      return var1;
   }

   public DERIA5String getUri() {
      return this.uri;
   }

   public DERObject toASN1Object() {
      return this.uri.getDERObject();
   }
}

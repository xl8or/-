package myorg.bouncycastle.asn1.isismtt.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.x500.DirectoryString;

public class Restriction extends ASN1Encodable {

   private DirectoryString restriction;


   public Restriction(String var1) {
      DirectoryString var2 = new DirectoryString(var1);
      this.restriction = var2;
   }

   private Restriction(DirectoryString var1) {
      this.restriction = var1;
   }

   public static Restriction getInstance(Object var0) {
      Restriction var1;
      if(var0 != null && !(var0 instanceof Restriction)) {
         if(!(var0 instanceof DERString)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         DirectoryString var2 = DirectoryString.getInstance(var0);
         var1 = new Restriction(var2);
      } else {
         var1 = (Restriction)var0;
      }

      return var1;
   }

   public DirectoryString getRestriction() {
      return this.restriction;
   }

   public DERObject toASN1Object() {
      return this.restriction.toASN1Object();
   }
}

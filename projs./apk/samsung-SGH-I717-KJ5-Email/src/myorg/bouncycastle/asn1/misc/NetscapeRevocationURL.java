package myorg.bouncycastle.asn1.misc;

import myorg.bouncycastle.asn1.DERIA5String;

public class NetscapeRevocationURL extends DERIA5String {

   public NetscapeRevocationURL(DERIA5String var1) {
      String var2 = var1.getString();
      super(var2);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("NetscapeRevocationURL: ");
      String var2 = this.getString();
      return var1.append(var2).toString();
   }
}

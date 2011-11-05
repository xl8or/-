package myorg.bouncycastle.asn1.util;

import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.util.ASN1Dump;

public class DERDump extends ASN1Dump {

   public DERDump() {}

   public static String dumpAsString(DEREncodable var0) {
      StringBuffer var1 = new StringBuffer();
      DERObject var2 = var0.getDERObject();
      _dumpAsString("", (boolean)0, var2, var1);
      return var1.toString();
   }

   public static String dumpAsString(DERObject var0) {
      StringBuffer var1 = new StringBuffer();
      _dumpAsString("", (boolean)0, var0, var1);
      return var1.toString();
   }
}

package myorg.bouncycastle.asn1.util;

import java.io.FileInputStream;
import java.io.PrintStream;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.util.ASN1Dump;

public class Dump {

   public Dump() {}

   public static void main(String[] var0) throws Exception {
      String var1 = var0[0];
      FileInputStream var2 = new FileInputStream(var1);
      ASN1InputStream var3 = new ASN1InputStream(var2);

      while(true) {
         DERObject var4 = var3.readObject();
         if(var4 == null) {
            return;
         }

         PrintStream var5 = System.out;
         String var6 = ASN1Dump.dumpAsString(var4);
         var5.println(var6);
      }
   }
}

package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.util.encoders.Base64;

public class PEMUtil {

   private final String _footer1;
   private final String _footer2;
   private final String _header1;
   private final String _header2;


   PEMUtil(String var1) {
      String var2 = "-----BEGIN " + var1 + "-----";
      this._header1 = var2;
      String var3 = "-----BEGIN X509 " + var1 + "-----";
      this._header2 = var3;
      String var4 = "-----END " + var1 + "-----";
      this._footer1 = var4;
      String var5 = "-----END X509 " + var1 + "-----";
      this._footer2 = var5;
   }

   private String readLine(InputStream var1) throws IOException {
      StringBuffer var2 = new StringBuffer();

      int var3;
      do {
         while(true) {
            var3 = var1.read();
            if(var3 == 13 || var3 == 10 || var3 < 0) {
               break;
            }

            if(var3 != 13) {
               char var4 = (char)var3;
               var2.append(var4);
            }
         }
      } while(var3 >= 0 && var2.length() == 0);

      String var6;
      if(var3 < 0) {
         var6 = null;
      } else {
         var6 = var2.toString();
      }

      return var6;
   }

   ASN1Sequence readPEMObject(InputStream var1) throws IOException {
      StringBuffer var2 = new StringBuffer();

      String var3;
      String var5;
      do {
         var3 = this.readLine(var1);
         if(var3 == null) {
            break;
         }

         String var4 = this._header1;
         if(var3.startsWith(var4)) {
            break;
         }

         var5 = this._header2;
      } while(!var3.startsWith(var5));

      while(true) {
         var3 = this.readLine(var1);
         if(var3 == null) {
            break;
         }

         String var6 = this._footer1;
         if(var3.startsWith(var6)) {
            break;
         }

         String var7 = this._footer2;
         if(var3.startsWith(var7)) {
            break;
         }

         var2.append(var3);
      }

      ASN1Sequence var11;
      if(var2.length() != 0) {
         byte[] var8 = Base64.decode(var2.toString());
         DERObject var9 = (new ASN1InputStream(var8)).readObject();
         if(!(var9 instanceof ASN1Sequence)) {
            throw new IOException("malformed PEM data encountered");
         }

         var11 = (ASN1Sequence)var9;
      } else {
         var11 = null;
      }

      return var11;
   }
}

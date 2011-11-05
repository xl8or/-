package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1OctetStringParser;
import myorg.bouncycastle.asn1.ASN1ParsingException;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DefiniteLengthInputStream;

public class DEROctetStringParser implements ASN1OctetStringParser {

   private DefiniteLengthInputStream stream;


   DEROctetStringParser(DefiniteLengthInputStream var1) {
      this.stream = var1;
   }

   public DERObject getDERObject() {
      try {
         byte[] var1 = this.stream.toByteArray();
         DEROctetString var2 = new DEROctetString(var1);
         return var2;
      } catch (IOException var7) {
         StringBuilder var4 = (new StringBuilder()).append("IOException converting stream to byte array: ");
         String var5 = var7.getMessage();
         String var6 = var4.append(var5).toString();
         throw new ASN1ParsingException(var6, var7);
      }
   }

   public InputStream getOctetStream() {
      return this.stream;
   }
}

package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1ParsingException;
import myorg.bouncycastle.asn1.ASN1SetParser;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSet;

public class DERSetParser implements ASN1SetParser {

   private ASN1StreamParser _parser;


   DERSetParser(ASN1StreamParser var1) {
      this._parser = var1;
   }

   public DERObject getDERObject() {
      try {
         ASN1EncodableVector var1 = this._parser.readVector();
         DERSet var2 = new DERSet(var1, (boolean)0);
         return var2;
      } catch (IOException var5) {
         String var4 = var5.getMessage();
         throw new ASN1ParsingException(var4, var5);
      }
   }

   public DEREncodable readObject() throws IOException {
      return this._parser.readObject();
   }
}

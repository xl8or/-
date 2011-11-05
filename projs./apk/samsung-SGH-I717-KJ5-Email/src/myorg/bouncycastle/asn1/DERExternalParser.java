package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1ParsingException;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERExternal;
import myorg.bouncycastle.asn1.DERObject;

public class DERExternalParser implements DEREncodable {

   private ASN1StreamParser _parser;


   public DERExternalParser(ASN1StreamParser var1) {
      this._parser = var1;
   }

   public DERObject getDERObject() {
      try {
         ASN1EncodableVector var1 = this._parser.readVector();
         DERExternal var2 = new DERExternal(var1);
         return var2;
      } catch (IOException var5) {
         throw new ASN1ParsingException("unable to get DER object", var5);
      } catch (IllegalArgumentException var6) {
         throw new ASN1ParsingException("unable to get DER object", var6);
      }
   }

   public DEREncodable readObject() throws IOException {
      return this._parser.readObject();
   }
}

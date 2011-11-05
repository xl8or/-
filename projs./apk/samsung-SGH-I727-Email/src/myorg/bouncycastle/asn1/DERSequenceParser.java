package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;

public class DERSequenceParser implements ASN1SequenceParser {

   private ASN1StreamParser _parser;


   DERSequenceParser(ASN1StreamParser var1) {
      this._parser = var1;
   }

   public DERObject getDERObject() {
      try {
         ASN1EncodableVector var1 = this._parser.readVector();
         DERSequence var2 = new DERSequence(var1);
         return var2;
      } catch (IOException var4) {
         String var3 = var4.getMessage();
         throw new IllegalStateException(var3);
      }
   }

   public DEREncodable readObject() throws IOException {
      return this._parser.readObject();
   }
}

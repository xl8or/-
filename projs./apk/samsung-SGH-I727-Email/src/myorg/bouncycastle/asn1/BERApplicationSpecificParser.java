package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1ApplicationSpecificParser;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1ParsingException;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.BERApplicationSpecific;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;

public class BERApplicationSpecificParser implements ASN1ApplicationSpecificParser {

   private final ASN1StreamParser parser;
   private final int tag;


   BERApplicationSpecificParser(int var1, ASN1StreamParser var2) {
      this.tag = var1;
      this.parser = var2;
   }

   public DERObject getDERObject() {
      try {
         int var1 = this.tag;
         ASN1EncodableVector var2 = this.parser.readVector();
         BERApplicationSpecific var3 = new BERApplicationSpecific(var1, var2);
         return var3;
      } catch (IOException var6) {
         String var5 = var6.getMessage();
         throw new ASN1ParsingException(var5, var6);
      }
   }

   public DEREncodable readObject() throws IOException {
      return this.parser.readObject();
   }
}

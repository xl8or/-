package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1ObjectParser;
import myorg.bouncycastle.asn1.ASN1OctetStringParser;
import myorg.bouncycastle.asn1.ASN1ParsingException;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.ConstructedOctetStream;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.util.io.Streams;

public class BEROctetStringParser implements ASN1OctetStringParser {

   private ASN1StreamParser _parser;


   protected BEROctetStringParser(ASN1ObjectParser var1) {
      ASN1StreamParser var2 = var1._aIn;
      this._parser = var2;
   }

   BEROctetStringParser(ASN1StreamParser var1) {
      this._parser = var1;
   }

   public DERObject getDERObject() {
      try {
         byte[] var1 = Streams.readAll(this.getOctetStream());
         BERConstructedOctetString var2 = new BERConstructedOctetString(var1);
         return var2;
      } catch (IOException var7) {
         StringBuilder var4 = (new StringBuilder()).append("IOException converting stream to byte array: ");
         String var5 = var7.getMessage();
         String var6 = var4.append(var5).toString();
         throw new ASN1ParsingException(var6, var7);
      }
   }

   public InputStream getOctetStream() {
      ASN1StreamParser var1 = this._parser;
      return new ConstructedOctetStream(var1);
   }
}

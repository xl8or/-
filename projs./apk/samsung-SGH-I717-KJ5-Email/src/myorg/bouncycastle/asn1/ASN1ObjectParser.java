package myorg.bouncycastle.asn1;

import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1StreamParser;

public class ASN1ObjectParser {

   ASN1StreamParser _aIn;


   protected ASN1ObjectParser(int var1, int var2, InputStream var3) {
      ASN1StreamParser var4 = new ASN1StreamParser(var3);
      this._aIn = var4;
   }
}

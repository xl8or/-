package myorg.bouncycastle.crypto.generators;

import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.generators.BaseKDFBytesGenerator;

public class KDF1BytesGenerator extends BaseKDFBytesGenerator {

   public KDF1BytesGenerator(Digest var1) {
      super(0, var1);
   }
}

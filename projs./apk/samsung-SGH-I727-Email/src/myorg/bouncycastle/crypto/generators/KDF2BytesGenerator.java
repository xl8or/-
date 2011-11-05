package myorg.bouncycastle.crypto.generators;

import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.generators.BaseKDFBytesGenerator;

public class KDF2BytesGenerator extends BaseKDFBytesGenerator {

   public KDF2BytesGenerator(Digest var1) {
      super(1, var1);
   }
}

package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.params.AEADParameters;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class CCMParameters extends AEADParameters {

   public CCMParameters(KeyParameter var1, int var2, byte[] var3, byte[] var4) {
      super(var1, var2, var3, var4);
   }
}

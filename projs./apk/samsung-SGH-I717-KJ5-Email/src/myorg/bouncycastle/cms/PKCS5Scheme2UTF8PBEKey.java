package myorg.bouncycastle.cms;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.spec.PBEParameterSpec;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSPBEKey;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class PKCS5Scheme2UTF8PBEKey extends CMSPBEKey {

   public PKCS5Scheme2UTF8PBEKey(char[] var1, AlgorithmParameters var2) throws InvalidAlgorithmParameterException {
      PBEParameterSpec var3 = getParamSpec(var2);
      super(var1, var3);
   }

   public PKCS5Scheme2UTF8PBEKey(char[] var1, byte[] var2, int var3) {
      super(var1, var2, var3);
   }

   byte[] getEncoded(String var1) {
      PKCS5S2ParametersGenerator var2 = new PKCS5S2ParametersGenerator();
      byte[] var3 = PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(this.getPassword());
      byte[] var4 = this.getSalt();
      int var5 = this.getIterationCount();
      var2.init(var3, var4, var5);
      int var6 = CMSEnvelopedHelper.INSTANCE.getKeySize(var1);
      return ((KeyParameter)var2.generateDerivedParameters(var6)).getKey();
   }
}

package myorg.bouncycastle.cms;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEParameterSpec;

public abstract class CMSPBEKey implements PBEKey {

   private int iterationCount;
   private char[] password;
   private byte[] salt;


   public CMSPBEKey(char[] var1, PBEParameterSpec var2) {
      byte[] var3 = var2.getSalt();
      int var4 = var2.getIterationCount();
      this(var1, var3, var4);
   }

   public CMSPBEKey(char[] var1, byte[] var2, int var3) {
      this.password = var1;
      this.salt = var2;
      this.iterationCount = var3;
   }

   protected static PBEParameterSpec getParamSpec(AlgorithmParameters var0) throws InvalidAlgorithmParameterException {
      try {
         PBEParameterSpec var6 = (PBEParameterSpec)var0.getParameterSpec(PBEParameterSpec.class);
         return var6;
      } catch (InvalidParameterSpecException var5) {
         StringBuilder var2 = (new StringBuilder()).append("cannot process PBE spec: ");
         String var3 = var5.getMessage();
         String var4 = var2.append(var3).toString();
         throw new InvalidAlgorithmParameterException(var4);
      }
   }

   public String getAlgorithm() {
      return "PKCS5S2";
   }

   public byte[] getEncoded() {
      return null;
   }

   abstract byte[] getEncoded(String var1);

   public String getFormat() {
      return "RAW";
   }

   public int getIterationCount() {
      return this.iterationCount;
   }

   public char[] getPassword() {
      return this.password;
   }

   public byte[] getSalt() {
      return this.salt;
   }
}

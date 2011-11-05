package myorg.bouncycastle.jce.provider;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class JCEPBEKey implements PBEKey {

   String algorithm;
   int digest;
   int ivSize;
   int keySize;
   DERObjectIdentifier oid;
   CipherParameters param;
   PBEKeySpec pbeKeySpec;
   boolean tryWrong = 0;
   int type;


   public JCEPBEKey(String var1, DERObjectIdentifier var2, int var3, int var4, int var5, int var6, PBEKeySpec var7, CipherParameters var8) {
      this.algorithm = var1;
      this.oid = var2;
      this.type = var3;
      this.digest = var4;
      this.keySize = var5;
      this.ivSize = var6;
      this.pbeKeySpec = var7;
      this.param = var8;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   int getDigest() {
      return this.digest;
   }

   public byte[] getEncoded() {
      byte[] var2;
      if(this.param != null) {
         KeyParameter var1;
         if(this.param instanceof ParametersWithIV) {
            var1 = (KeyParameter)((ParametersWithIV)this.param).getParameters();
         } else {
            var1 = (KeyParameter)this.param;
         }

         var2 = var1.getKey();
      } else if(this.type == 2) {
         var2 = PBEParametersGenerator.PKCS12PasswordToBytes(this.pbeKeySpec.getPassword());
      } else {
         var2 = PBEParametersGenerator.PKCS5PasswordToBytes(this.pbeKeySpec.getPassword());
      }

      return var2;
   }

   public String getFormat() {
      return "RAW";
   }

   public int getIterationCount() {
      return this.pbeKeySpec.getIterationCount();
   }

   int getIvSize() {
      return this.ivSize;
   }

   int getKeySize() {
      return this.keySize;
   }

   public DERObjectIdentifier getOID() {
      return this.oid;
   }

   CipherParameters getParam() {
      return this.param;
   }

   public char[] getPassword() {
      return this.pbeKeySpec.getPassword();
   }

   public byte[] getSalt() {
      return this.pbeKeySpec.getSalt();
   }

   int getType() {
      return this.type;
   }

   void setTryWrongPKCS12Zero(boolean var1) {
      this.tryWrong = var1;
   }

   boolean shouldTryWrongPKCS12() {
      return this.tryWrong;
   }
}

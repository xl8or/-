package myorg.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.cryptopro.GOST3410NamedParameters;
import myorg.bouncycastle.asn1.cryptopro.GOST3410ParamSetParameters;
import myorg.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import myorg.bouncycastle.jce.interfaces.GOST3410Params;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class GOST3410ParameterSpec implements AlgorithmParameterSpec, GOST3410Params {

   private String digestParamSetOID;
   private String encryptionParamSetOID;
   private String keyParamSetOID;
   private GOST3410PublicKeyParameterSetSpec keyParameters;


   public GOST3410ParameterSpec(String var1) {
      String var2 = CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet.getId();
      this(var1, var2, (String)null);
   }

   public GOST3410ParameterSpec(String var1, String var2) {
      this(var1, var2, (String)null);
   }

   public GOST3410ParameterSpec(String var1, String var2, String var3) {
      GOST3410ParamSetParameters var4 = null;

      label21: {
         GOST3410ParamSetParameters var5;
         try {
            var5 = GOST3410NamedParameters.getByOID(new DERObjectIdentifier(var1));
         } catch (IllegalArgumentException var12) {
            DERObjectIdentifier var7 = GOST3410NamedParameters.getOID(var1);
            if(var7 != null) {
               var1 = var7.getId();
               var4 = GOST3410NamedParameters.getByOID(var7);
            }
            break label21;
         }

         var4 = var5;
      }

      if(var4 == null) {
         throw new IllegalArgumentException("no key parameter set for passed in name/OID.");
      } else {
         BigInteger var8 = var4.getP();
         BigInteger var9 = var4.getQ();
         BigInteger var10 = var4.getA();
         GOST3410PublicKeyParameterSetSpec var11 = new GOST3410PublicKeyParameterSetSpec(var8, var9, var10);
         this.keyParameters = var11;
         this.keyParamSetOID = var1;
         this.digestParamSetOID = var2;
         this.encryptionParamSetOID = var3;
      }
   }

   public GOST3410ParameterSpec(GOST3410PublicKeyParameterSetSpec var1) {
      this.keyParameters = var1;
      String var2 = CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet.getId();
      this.digestParamSetOID = var2;
      this.encryptionParamSetOID = null;
   }

   public static GOST3410ParameterSpec fromPublicKeyAlg(GOST3410PublicKeyAlgParameters var0) {
      GOST3410ParameterSpec var4;
      if(var0.getEncryptionParamSet() != null) {
         String var1 = var0.getPublicKeyParamSet().getId();
         String var2 = var0.getDigestParamSet().getId();
         String var3 = var0.getEncryptionParamSet().getId();
         var4 = new GOST3410ParameterSpec(var1, var2, var3);
      } else {
         String var5 = var0.getPublicKeyParamSet().getId();
         String var6 = var0.getDigestParamSet().getId();
         var4 = new GOST3410ParameterSpec(var5, var6);
      }

      return var4;
   }

   public boolean equals(Object var1) {
      boolean var11;
      if(var1 instanceof GOST3410ParameterSpec) {
         GOST3410ParameterSpec var2 = (GOST3410ParameterSpec)var1;
         GOST3410PublicKeyParameterSetSpec var3 = this.keyParameters;
         GOST3410PublicKeyParameterSetSpec var4 = var2.keyParameters;
         if(var3.equals(var4)) {
            String var5 = this.digestParamSetOID;
            String var6 = var2.digestParamSetOID;
            if(var5.equals(var6)) {
               label29: {
                  String var7 = this.encryptionParamSetOID;
                  String var8 = var2.encryptionParamSetOID;
                  if(var7 != var8) {
                     if(this.encryptionParamSetOID == null) {
                        break label29;
                     }

                     String var9 = this.encryptionParamSetOID;
                     String var10 = var2.encryptionParamSetOID;
                     if(!var9.equals(var10)) {
                        break label29;
                     }
                  }

                  var11 = true;
                  return var11;
               }
            }
         }

         var11 = false;
      } else {
         var11 = false;
      }

      return var11;
   }

   public String getDigestParamSetOID() {
      return this.digestParamSetOID;
   }

   public String getEncryptionParamSetOID() {
      return this.encryptionParamSetOID;
   }

   public String getPublicKeyParamSetOID() {
      return this.keyParamSetOID;
   }

   public GOST3410PublicKeyParameterSetSpec getPublicKeyParameters() {
      return this.keyParameters;
   }

   public int hashCode() {
      int var1 = this.keyParameters.hashCode();
      int var2 = this.digestParamSetOID.hashCode();
      int var3 = var1 ^ var2;
      int var4;
      if(this.encryptionParamSetOID != null) {
         var4 = this.encryptionParamSetOID.hashCode();
      } else {
         var4 = 0;
      }

      return var3 ^ var4;
   }
}

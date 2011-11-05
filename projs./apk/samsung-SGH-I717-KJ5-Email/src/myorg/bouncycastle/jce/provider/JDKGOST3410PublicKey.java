package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.crypto.params.GOST3410PublicKeyParameters;
import myorg.bouncycastle.jce.interfaces.GOST3410Params;
import myorg.bouncycastle.jce.interfaces.GOST3410PublicKey;
import myorg.bouncycastle.jce.spec.GOST3410ParameterSpec;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeySpec;

public class JDKGOST3410PublicKey implements GOST3410PublicKey {

   private GOST3410Params gost3410Spec;
   private BigInteger y;


   JDKGOST3410PublicKey(BigInteger var1, GOST3410ParameterSpec var2) {
      this.y = var1;
      this.gost3410Spec = var2;
   }

   JDKGOST3410PublicKey(SubjectPublicKeyInfo var1) {
      ASN1Sequence var2 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
      GOST3410PublicKeyAlgParameters var3 = new GOST3410PublicKeyAlgParameters(var2);

      try {
         byte[] var4 = ((DEROctetString)var1.getPublicKey()).getOctets();
         byte[] var5 = new byte[var4.length];
         int var6 = 0;

         while(true) {
            int var7 = var4.length;
            if(var6 == var7) {
               BigInteger var10 = new BigInteger(1, var5);
               this.y = var10;
               break;
            }

            int var8 = var4.length - 1 - var6;
            byte var9 = var4[var8];
            var5[var6] = var9;
            ++var6;
         }
      } catch (IOException var13) {
         throw new IllegalArgumentException("invalid info structure in GOST3410 public key");
      }

      GOST3410ParameterSpec var11 = GOST3410ParameterSpec.fromPublicKeyAlg(var3);
      this.gost3410Spec = var11;
   }

   JDKGOST3410PublicKey(GOST3410PublicKeyParameters var1, GOST3410ParameterSpec var2) {
      BigInteger var3 = var1.getY();
      this.y = var3;
      this.gost3410Spec = var2;
   }

   JDKGOST3410PublicKey(GOST3410PublicKey var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      GOST3410Params var3 = var1.getParameters();
      this.gost3410Spec = var3;
   }

   JDKGOST3410PublicKey(GOST3410PublicKeySpec var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getP();
      BigInteger var4 = var1.getQ();
      BigInteger var5 = var1.getA();
      GOST3410PublicKeyParameterSetSpec var6 = new GOST3410PublicKeyParameterSetSpec(var3, var4, var5);
      GOST3410ParameterSpec var7 = new GOST3410ParameterSpec(var6);
      this.gost3410Spec = var7;
   }

   public boolean equals(Object var1) {
      boolean var7;
      if(var1 instanceof JDKGOST3410PublicKey) {
         JDKGOST3410PublicKey var2 = (JDKGOST3410PublicKey)var1;
         BigInteger var3 = this.y;
         BigInteger var4 = var2.y;
         if(var3.equals(var4)) {
            GOST3410Params var5 = this.gost3410Spec;
            GOST3410Params var6 = var2.gost3410Spec;
            if(var5.equals(var6)) {
               var7 = true;
               return var7;
            }
         }

         var7 = false;
      } else {
         var7 = false;
      }

      return var7;
   }

   public String getAlgorithm() {
      return "GOST3410";
   }

   public byte[] getEncoded() {
      byte[] var1 = this.getY().toByteArray();
      byte[] var2;
      if(var1[0] == 0) {
         var2 = new byte[var1.length - 1];
      } else {
         var2 = new byte[var1.length];
      }

      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            SubjectPublicKeyInfo var17;
            if(this.gost3410Spec instanceof GOST3410ParameterSpec) {
               if(this.gost3410Spec.getEncryptionParamSetOID() != null) {
                  DERObjectIdentifier var7 = CryptoProObjectIdentifiers.gostR3410_94;
                  String var8 = this.gost3410Spec.getPublicKeyParamSetOID();
                  DERObjectIdentifier var9 = new DERObjectIdentifier(var8);
                  String var10 = this.gost3410Spec.getDigestParamSetOID();
                  DERObjectIdentifier var11 = new DERObjectIdentifier(var10);
                  String var12 = this.gost3410Spec.getEncryptionParamSetOID();
                  DERObjectIdentifier var13 = new DERObjectIdentifier(var12);
                  DERObject var14 = (new GOST3410PublicKeyAlgParameters(var9, var11, var13)).getDERObject();
                  AlgorithmIdentifier var15 = new AlgorithmIdentifier(var7, var14);
                  DEROctetString var16 = new DEROctetString(var2);
                  var17 = new SubjectPublicKeyInfo(var15, var16);
               } else {
                  DERObjectIdentifier var18 = CryptoProObjectIdentifiers.gostR3410_94;
                  String var19 = this.gost3410Spec.getPublicKeyParamSetOID();
                  DERObjectIdentifier var20 = new DERObjectIdentifier(var19);
                  String var21 = this.gost3410Spec.getDigestParamSetOID();
                  DERObjectIdentifier var22 = new DERObjectIdentifier(var21);
                  DERObject var23 = (new GOST3410PublicKeyAlgParameters(var20, var22)).getDERObject();
                  AlgorithmIdentifier var24 = new AlgorithmIdentifier(var18, var23);
                  DEROctetString var25 = new DEROctetString(var2);
                  var17 = new SubjectPublicKeyInfo(var24, var25);
               }
            } else {
               DERObjectIdentifier var26 = CryptoProObjectIdentifiers.gostR3410_94;
               AlgorithmIdentifier var27 = new AlgorithmIdentifier(var26);
               DEROctetString var28 = new DEROctetString(var2);
               var17 = new SubjectPublicKeyInfo(var27, var28);
            }

            return var17.getDEREncoded();
         }

         int var5 = var1.length - 1 - var3;
         byte var6 = var1[var5];
         var2[var3] = var6;
         ++var3;
      }
   }

   public String getFormat() {
      return "X.509";
   }

   public GOST3410Params getParameters() {
      return this.gost3410Spec;
   }

   public BigInteger getY() {
      return this.y;
   }

   public int hashCode() {
      int var1 = this.y.hashCode();
      int var2 = this.gost3410Spec.hashCode();
      return var1 ^ var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("GOST3410 Public Key").append(var2);
      StringBuffer var4 = var1.append("            y: ");
      String var5 = this.getY().toString(16);
      StringBuffer var6 = var4.append(var5).append(var2);
      return var1.toString();
   }
}

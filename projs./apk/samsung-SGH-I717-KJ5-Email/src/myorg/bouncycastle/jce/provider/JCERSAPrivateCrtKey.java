package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import myorg.bouncycastle.jce.provider.JCERSAPrivateKey;

public class JCERSAPrivateCrtKey extends JCERSAPrivateKey implements RSAPrivateCrtKey {

   static final long serialVersionUID = 7834723820638524718L;
   private BigInteger crtCoefficient;
   private BigInteger primeExponentP;
   private BigInteger primeExponentQ;
   private BigInteger primeP;
   private BigInteger primeQ;
   private BigInteger publicExponent;


   JCERSAPrivateCrtKey(RSAPrivateCrtKey var1) {
      BigInteger var2 = var1.getModulus();
      this.modulus = var2;
      BigInteger var3 = var1.getPublicExponent();
      this.publicExponent = var3;
      BigInteger var4 = var1.getPrivateExponent();
      this.privateExponent = var4;
      BigInteger var5 = var1.getPrimeP();
      this.primeP = var5;
      BigInteger var6 = var1.getPrimeQ();
      this.primeQ = var6;
      BigInteger var7 = var1.getPrimeExponentP();
      this.primeExponentP = var7;
      BigInteger var8 = var1.getPrimeExponentQ();
      this.primeExponentQ = var8;
      BigInteger var9 = var1.getCrtCoefficient();
      this.crtCoefficient = var9;
   }

   JCERSAPrivateCrtKey(RSAPrivateCrtKeySpec var1) {
      BigInteger var2 = var1.getModulus();
      this.modulus = var2;
      BigInteger var3 = var1.getPublicExponent();
      this.publicExponent = var3;
      BigInteger var4 = var1.getPrivateExponent();
      this.privateExponent = var4;
      BigInteger var5 = var1.getPrimeP();
      this.primeP = var5;
      BigInteger var6 = var1.getPrimeQ();
      this.primeQ = var6;
      BigInteger var7 = var1.getPrimeExponentP();
      this.primeExponentP = var7;
      BigInteger var8 = var1.getPrimeExponentQ();
      this.primeExponentQ = var8;
      BigInteger var9 = var1.getCrtCoefficient();
      this.crtCoefficient = var9;
   }

   JCERSAPrivateCrtKey(PrivateKeyInfo var1) {
      ASN1Sequence var2 = (ASN1Sequence)var1.getPrivateKey();
      RSAPrivateKeyStructure var3 = new RSAPrivateKeyStructure(var2);
      this(var3);
   }

   JCERSAPrivateCrtKey(RSAPrivateKeyStructure var1) {
      BigInteger var2 = var1.getModulus();
      this.modulus = var2;
      BigInteger var3 = var1.getPublicExponent();
      this.publicExponent = var3;
      BigInteger var4 = var1.getPrivateExponent();
      this.privateExponent = var4;
      BigInteger var5 = var1.getPrime1();
      this.primeP = var5;
      BigInteger var6 = var1.getPrime2();
      this.primeQ = var6;
      BigInteger var7 = var1.getExponent1();
      this.primeExponentP = var7;
      BigInteger var8 = var1.getExponent2();
      this.primeExponentQ = var8;
      BigInteger var9 = var1.getCoefficient();
      this.crtCoefficient = var9;
   }

   JCERSAPrivateCrtKey(RSAPrivateCrtKeyParameters var1) {
      super((RSAKeyParameters)var1);
      BigInteger var2 = var1.getPublicExponent();
      this.publicExponent = var2;
      BigInteger var3 = var1.getP();
      this.primeP = var3;
      BigInteger var4 = var1.getQ();
      this.primeQ = var4;
      BigInteger var5 = var1.getDP();
      this.primeExponentP = var5;
      BigInteger var6 = var1.getDQ();
      this.primeExponentQ = var6;
      BigInteger var7 = var1.getQInv();
      this.crtCoefficient = var7;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof RSAPrivateCrtKey)) {
         var2 = false;
      } else {
         RSAPrivateCrtKey var3 = (RSAPrivateCrtKey)var1;
         BigInteger var4 = this.getModulus();
         BigInteger var5 = var3.getModulus();
         if(var4.equals(var5)) {
            BigInteger var6 = this.getPublicExponent();
            BigInteger var7 = var3.getPublicExponent();
            if(var6.equals(var7)) {
               BigInteger var8 = this.getPrivateExponent();
               BigInteger var9 = var3.getPrivateExponent();
               if(var8.equals(var9)) {
                  BigInteger var10 = this.getPrimeP();
                  BigInteger var11 = var3.getPrimeP();
                  if(var10.equals(var11)) {
                     BigInteger var12 = this.getPrimeQ();
                     BigInteger var13 = var3.getPrimeQ();
                     if(var12.equals(var13)) {
                        BigInteger var14 = this.getPrimeExponentP();
                        BigInteger var15 = var3.getPrimeExponentP();
                        if(var14.equals(var15)) {
                           BigInteger var16 = this.getPrimeExponentQ();
                           BigInteger var17 = var3.getPrimeExponentQ();
                           if(var16.equals(var17)) {
                              BigInteger var18 = this.getCrtCoefficient();
                              BigInteger var19 = var3.getCrtCoefficient();
                              if(var18.equals(var19)) {
                                 var2 = true;
                                 return var2;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public BigInteger getCrtCoefficient() {
      return this.crtCoefficient;
   }

   public byte[] getEncoded() {
      DERObjectIdentifier var1 = PKCSObjectIdentifiers.rsaEncryption;
      DERNull var2 = new DERNull();
      AlgorithmIdentifier var3 = new AlgorithmIdentifier(var1, var2);
      BigInteger var4 = this.getModulus();
      BigInteger var5 = this.getPublicExponent();
      BigInteger var6 = this.getPrivateExponent();
      BigInteger var7 = this.getPrimeP();
      BigInteger var8 = this.getPrimeQ();
      BigInteger var9 = this.getPrimeExponentP();
      BigInteger var10 = this.getPrimeExponentQ();
      BigInteger var11 = this.getCrtCoefficient();
      DERObject var12 = (new RSAPrivateKeyStructure(var4, var5, var6, var7, var8, var9, var10, var11)).getDERObject();
      return (new PrivateKeyInfo(var3, var12)).getDEREncoded();
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public BigInteger getPrimeExponentP() {
      return this.primeExponentP;
   }

   public BigInteger getPrimeExponentQ() {
      return this.primeExponentQ;
   }

   public BigInteger getPrimeP() {
      return this.primeP;
   }

   public BigInteger getPrimeQ() {
      return this.primeQ;
   }

   public BigInteger getPublicExponent() {
      return this.publicExponent;
   }

   public int hashCode() {
      int var1 = this.getModulus().hashCode();
      int var2 = this.getPublicExponent().hashCode();
      int var3 = var1 ^ var2;
      int var4 = this.getPrivateExponent().hashCode();
      return var3 ^ var4;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("RSA Private CRT Key").append(var2);
      StringBuffer var4 = var1.append("            modulus: ");
      String var5 = this.getModulus().toString(16);
      StringBuffer var6 = var4.append(var5).append(var2);
      StringBuffer var7 = var1.append("    public exponent: ");
      String var8 = this.getPublicExponent().toString(16);
      StringBuffer var9 = var7.append(var8).append(var2);
      StringBuffer var10 = var1.append("   private exponent: ");
      String var11 = this.getPrivateExponent().toString(16);
      StringBuffer var12 = var10.append(var11).append(var2);
      StringBuffer var13 = var1.append("             primeP: ");
      String var14 = this.getPrimeP().toString(16);
      StringBuffer var15 = var13.append(var14).append(var2);
      StringBuffer var16 = var1.append("             primeQ: ");
      String var17 = this.getPrimeQ().toString(16);
      StringBuffer var18 = var16.append(var17).append(var2);
      StringBuffer var19 = var1.append("     primeExponentP: ");
      String var20 = this.getPrimeExponentP().toString(16);
      StringBuffer var21 = var19.append(var20).append(var2);
      StringBuffer var22 = var1.append("     primeExponentQ: ");
      String var23 = this.getPrimeExponentQ().toString(16);
      StringBuffer var24 = var22.append(var23).append(var2);
      StringBuffer var25 = var1.append("     crtCoefficient: ");
      String var26 = this.getCrtCoefficient().toString(16);
      StringBuffer var27 = var25.append(var26).append(var2);
      return var1.toString();
   }
}

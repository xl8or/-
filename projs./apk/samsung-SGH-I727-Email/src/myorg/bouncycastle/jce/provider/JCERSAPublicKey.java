package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;

public class JCERSAPublicKey implements RSAPublicKey {

   static final long serialVersionUID = 2675817738516720772L;
   private BigInteger modulus;
   private BigInteger publicExponent;


   JCERSAPublicKey(RSAPublicKey var1) {
      BigInteger var2 = var1.getModulus();
      this.modulus = var2;
      BigInteger var3 = var1.getPublicExponent();
      this.publicExponent = var3;
   }

   JCERSAPublicKey(RSAPublicKeySpec var1) {
      BigInteger var2 = var1.getModulus();
      this.modulus = var2;
      BigInteger var3 = var1.getPublicExponent();
      this.publicExponent = var3;
   }

   JCERSAPublicKey(SubjectPublicKeyInfo var1) {
      try {
         ASN1Sequence var2 = (ASN1Sequence)var1.getPublicKey();
         RSAPublicKeyStructure var3 = new RSAPublicKeyStructure(var2);
         BigInteger var4 = var3.getModulus();
         this.modulus = var4;
         BigInteger var5 = var3.getPublicExponent();
         this.publicExponent = var5;
      } catch (IOException var7) {
         throw new IllegalArgumentException("invalid info structure in RSA public key");
      }
   }

   JCERSAPublicKey(RSAKeyParameters var1) {
      BigInteger var2 = var1.getModulus();
      this.modulus = var2;
      BigInteger var3 = var1.getExponent();
      this.publicExponent = var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof RSAPublicKey)) {
         var2 = false;
      } else {
         RSAPublicKey var3 = (RSAPublicKey)var1;
         BigInteger var4 = this.getModulus();
         BigInteger var5 = var3.getModulus();
         if(var4.equals(var5)) {
            BigInteger var6 = this.getPublicExponent();
            BigInteger var7 = var3.getPublicExponent();
            if(var6.equals(var7)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getAlgorithm() {
      return "RSA";
   }

   public byte[] getEncoded() {
      DERObjectIdentifier var1 = PKCSObjectIdentifiers.rsaEncryption;
      DERNull var2 = new DERNull();
      AlgorithmIdentifier var3 = new AlgorithmIdentifier(var1, var2);
      BigInteger var4 = this.getModulus();
      BigInteger var5 = this.getPublicExponent();
      DERObject var6 = (new RSAPublicKeyStructure(var4, var5)).getDERObject();
      return (new SubjectPublicKeyInfo(var3, var6)).getDEREncoded();
   }

   public String getFormat() {
      return "X.509";
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public BigInteger getPublicExponent() {
      return this.publicExponent;
   }

   public int hashCode() {
      int var1 = this.getModulus().hashCode();
      int var2 = this.getPublicExponent().hashCode();
      return var1 ^ var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("RSA Public Key").append(var2);
      StringBuffer var4 = var1.append("            modulus: ");
      String var5 = this.getModulus().toString(16);
      StringBuffer var6 = var4.append(var5).append(var2);
      StringBuffer var7 = var1.append("    public exponent: ");
      String var8 = this.getPublicExponent().toString(16);
      StringBuffer var9 = var7.append(var8).append(var2);
      return var1.toString();
   }
}

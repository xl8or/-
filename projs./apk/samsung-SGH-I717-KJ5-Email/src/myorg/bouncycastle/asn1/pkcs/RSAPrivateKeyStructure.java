package myorg.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;

public class RSAPrivateKeyStructure extends ASN1Encodable {

   private BigInteger coefficient;
   private BigInteger exponent1;
   private BigInteger exponent2;
   private BigInteger modulus;
   private ASN1Sequence otherPrimeInfos = null;
   private BigInteger prime1;
   private BigInteger prime2;
   private BigInteger privateExponent;
   private BigInteger publicExponent;
   private int version;


   public RSAPrivateKeyStructure(BigInteger var1, BigInteger var2, BigInteger var3, BigInteger var4, BigInteger var5, BigInteger var6, BigInteger var7, BigInteger var8) {
      this.version = 0;
      this.modulus = var1;
      this.publicExponent = var2;
      this.privateExponent = var3;
      this.prime1 = var4;
      this.prime2 = var5;
      this.exponent1 = var6;
      this.exponent2 = var7;
      this.coefficient = var8;
   }

   public RSAPrivateKeyStructure(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      BigInteger var3 = ((DERInteger)var2.nextElement()).getValue();
      if(var3.intValue() != 0 && var3.intValue() != 1) {
         throw new IllegalArgumentException("wrong version for RSA private key");
      } else {
         int var4 = var3.intValue();
         this.version = var4;
         BigInteger var5 = ((DERInteger)var2.nextElement()).getValue();
         this.modulus = var5;
         BigInteger var6 = ((DERInteger)var2.nextElement()).getValue();
         this.publicExponent = var6;
         BigInteger var7 = ((DERInteger)var2.nextElement()).getValue();
         this.privateExponent = var7;
         BigInteger var8 = ((DERInteger)var2.nextElement()).getValue();
         this.prime1 = var8;
         BigInteger var9 = ((DERInteger)var2.nextElement()).getValue();
         this.prime2 = var9;
         BigInteger var10 = ((DERInteger)var2.nextElement()).getValue();
         this.exponent1 = var10;
         BigInteger var11 = ((DERInteger)var2.nextElement()).getValue();
         this.exponent2 = var11;
         BigInteger var12 = ((DERInteger)var2.nextElement()).getValue();
         this.coefficient = var12;
         if(var2.hasMoreElements()) {
            ASN1Sequence var13 = (ASN1Sequence)var2.nextElement();
            this.otherPrimeInfos = var13;
         }
      }
   }

   public static RSAPrivateKeyStructure getInstance(Object var0) {
      RSAPrivateKeyStructure var1;
      if(var0 instanceof RSAPrivateKeyStructure) {
         var1 = (RSAPrivateKeyStructure)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RSAPrivateKeyStructure(var2);
      }

      return var1;
   }

   public static RSAPrivateKeyStructure getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public BigInteger getCoefficient() {
      return this.coefficient;
   }

   public BigInteger getExponent1() {
      return this.exponent1;
   }

   public BigInteger getExponent2() {
      return this.exponent2;
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public BigInteger getPrime1() {
      return this.prime1;
   }

   public BigInteger getPrime2() {
      return this.prime2;
   }

   public BigInteger getPrivateExponent() {
      return this.privateExponent;
   }

   public BigInteger getPublicExponent() {
      return this.publicExponent;
   }

   public int getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      int var2 = this.version;
      DERInteger var3 = new DERInteger(var2);
      var1.add(var3);
      BigInteger var4 = this.getModulus();
      DERInteger var5 = new DERInteger(var4);
      var1.add(var5);
      BigInteger var6 = this.getPublicExponent();
      DERInteger var7 = new DERInteger(var6);
      var1.add(var7);
      BigInteger var8 = this.getPrivateExponent();
      DERInteger var9 = new DERInteger(var8);
      var1.add(var9);
      BigInteger var10 = this.getPrime1();
      DERInteger var11 = new DERInteger(var10);
      var1.add(var11);
      BigInteger var12 = this.getPrime2();
      DERInteger var13 = new DERInteger(var12);
      var1.add(var13);
      BigInteger var14 = this.getExponent1();
      DERInteger var15 = new DERInteger(var14);
      var1.add(var15);
      BigInteger var16 = this.getExponent2();
      DERInteger var17 = new DERInteger(var16);
      var1.add(var17);
      BigInteger var18 = this.getCoefficient();
      DERInteger var19 = new DERInteger(var18);
      var1.add(var19);
      if(this.otherPrimeInfos != null) {
         ASN1Sequence var20 = this.otherPrimeInfos;
         var1.add(var20);
      }

      return new DERSequence(var1);
   }
}

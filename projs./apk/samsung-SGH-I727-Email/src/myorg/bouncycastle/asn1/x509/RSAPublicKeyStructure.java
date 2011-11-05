package myorg.bouncycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;

public class RSAPublicKeyStructure extends ASN1Encodable {

   private BigInteger modulus;
   private BigInteger publicExponent;


   public RSAPublicKeyStructure(BigInteger var1, BigInteger var2) {
      this.modulus = var1;
      this.publicExponent = var2;
   }

   public RSAPublicKeyStructure(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();
         BigInteger var6 = DERInteger.getInstance(var5.nextElement()).getPositiveValue();
         this.modulus = var6;
         BigInteger var7 = DERInteger.getInstance(var5.nextElement()).getPositiveValue();
         this.publicExponent = var7;
      }
   }

   public static RSAPublicKeyStructure getInstance(Object var0) {
      RSAPublicKeyStructure var1;
      if(var0 != null && !(var0 instanceof RSAPublicKeyStructure)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid RSAPublicKeyStructure: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RSAPublicKeyStructure(var2);
      } else {
         var1 = (RSAPublicKeyStructure)var0;
      }

      return var1;
   }

   public static RSAPublicKeyStructure getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public BigInteger getPublicExponent() {
      return this.publicExponent;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      BigInteger var2 = this.getModulus();
      DERInteger var3 = new DERInteger(var2);
      var1.add(var3);
      BigInteger var4 = this.getPublicExponent();
      DERInteger var5 = new DERInteger(var4);
      var1.add(var5);
      return new DERSequence(var1);
   }
}

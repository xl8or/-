package myorg.bouncycastle.asn1.cryptopro;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;

public class GOST3410ParamSetParameters extends ASN1Encodable {

   DERInteger a;
   int keySize;
   DERInteger p;
   DERInteger q;


   public GOST3410ParamSetParameters(int var1, BigInteger var2, BigInteger var3, BigInteger var4) {
      this.keySize = var1;
      DERInteger var5 = new DERInteger(var2);
      this.p = var5;
      DERInteger var6 = new DERInteger(var3);
      this.q = var6;
      DERInteger var7 = new DERInteger(var4);
      this.a = var7;
   }

   public GOST3410ParamSetParameters(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      int var3 = ((DERInteger)var2.nextElement()).getValue().intValue();
      this.keySize = var3;
      DERInteger var4 = (DERInteger)var2.nextElement();
      this.p = var4;
      DERInteger var5 = (DERInteger)var2.nextElement();
      this.q = var5;
      DERInteger var6 = (DERInteger)var2.nextElement();
      this.a = var6;
   }

   public static GOST3410ParamSetParameters getInstance(Object var0) {
      GOST3410ParamSetParameters var1;
      if(var0 != null && !(var0 instanceof GOST3410ParamSetParameters)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid GOST3410Parameter: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new GOST3410ParamSetParameters(var2);
      } else {
         var1 = (GOST3410ParamSetParameters)var0;
      }

      return var1;
   }

   public static GOST3410ParamSetParameters getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public BigInteger getA() {
      return this.a.getPositiveValue();
   }

   public int getKeySize() {
      return this.keySize;
   }

   public int getLKeySize() {
      return this.keySize;
   }

   public BigInteger getP() {
      return this.p.getPositiveValue();
   }

   public BigInteger getQ() {
      return this.q.getPositiveValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      int var2 = this.keySize;
      DERInteger var3 = new DERInteger(var2);
      var1.add(var3);
      DERInteger var4 = this.p;
      var1.add(var4);
      DERInteger var5 = this.q;
      var1.add(var5);
      DERInteger var6 = this.a;
      var1.add(var6);
      return new DERSequence(var1);
   }
}

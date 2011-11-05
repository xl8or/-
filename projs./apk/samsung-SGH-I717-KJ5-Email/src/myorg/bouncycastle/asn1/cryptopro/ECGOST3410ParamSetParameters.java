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

public class ECGOST3410ParamSetParameters extends ASN1Encodable {

   DERInteger a;
   DERInteger b;
   DERInteger p;
   DERInteger q;
   DERInteger x;
   DERInteger y;


   public ECGOST3410ParamSetParameters(BigInteger var1, BigInteger var2, BigInteger var3, BigInteger var4, int var5, BigInteger var6) {
      DERInteger var7 = new DERInteger(var1);
      this.a = var7;
      DERInteger var8 = new DERInteger(var2);
      this.b = var8;
      DERInteger var9 = new DERInteger(var3);
      this.p = var9;
      DERInteger var10 = new DERInteger(var4);
      this.q = var10;
      DERInteger var11 = new DERInteger(var5);
      this.x = var11;
      DERInteger var12 = new DERInteger(var6);
      this.y = var12;
   }

   public ECGOST3410ParamSetParameters(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERInteger var3 = (DERInteger)var2.nextElement();
      this.a = var3;
      DERInteger var4 = (DERInteger)var2.nextElement();
      this.b = var4;
      DERInteger var5 = (DERInteger)var2.nextElement();
      this.p = var5;
      DERInteger var6 = (DERInteger)var2.nextElement();
      this.q = var6;
      DERInteger var7 = (DERInteger)var2.nextElement();
      this.x = var7;
      DERInteger var8 = (DERInteger)var2.nextElement();
      this.y = var8;
   }

   public static ECGOST3410ParamSetParameters getInstance(Object var0) {
      ECGOST3410ParamSetParameters var1;
      if(var0 != null && !(var0 instanceof ECGOST3410ParamSetParameters)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid GOST3410Parameter: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ECGOST3410ParamSetParameters(var2);
      } else {
         var1 = (ECGOST3410ParamSetParameters)var0;
      }

      return var1;
   }

   public static ECGOST3410ParamSetParameters getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public BigInteger getA() {
      return this.a.getPositiveValue();
   }

   public BigInteger getP() {
      return this.p.getPositiveValue();
   }

   public BigInteger getQ() {
      return this.q.getPositiveValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.a;
      var1.add(var2);
      DERInteger var3 = this.b;
      var1.add(var3);
      DERInteger var4 = this.p;
      var1.add(var4);
      DERInteger var5 = this.q;
      var1.add(var5);
      DERInteger var6 = this.x;
      var1.add(var6);
      DERInteger var7 = this.y;
      var1.add(var7);
      return new DERSequence(var1);
   }
}

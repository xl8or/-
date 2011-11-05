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

public class DSAParameter extends ASN1Encodable {

   DERInteger g;
   DERInteger p;
   DERInteger q;


   public DSAParameter(BigInteger var1, BigInteger var2, BigInteger var3) {
      DERInteger var4 = new DERInteger(var1);
      this.p = var4;
      DERInteger var5 = new DERInteger(var2);
      this.q = var5;
      DERInteger var6 = new DERInteger(var3);
      this.g = var6;
   }

   public DSAParameter(ASN1Sequence var1) {
      if(var1.size() != 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();
         DERInteger var6 = DERInteger.getInstance(var5.nextElement());
         this.p = var6;
         DERInteger var7 = DERInteger.getInstance(var5.nextElement());
         this.q = var7;
         DERInteger var8 = DERInteger.getInstance(var5.nextElement());
         this.g = var8;
      }
   }

   public static DSAParameter getInstance(Object var0) {
      DSAParameter var1;
      if(var0 != null && !(var0 instanceof DSAParameter)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid DSAParameter: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new DSAParameter(var2);
      } else {
         var1 = (DSAParameter)var0;
      }

      return var1;
   }

   public static DSAParameter getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public BigInteger getG() {
      return this.g.getPositiveValue();
   }

   public BigInteger getP() {
      return this.p.getPositiveValue();
   }

   public BigInteger getQ() {
      return this.q.getPositiveValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.p;
      var1.add(var2);
      DERInteger var3 = this.q;
      var1.add(var3);
      DERInteger var4 = this.g;
      var1.add(var4);
      return new DERSequence(var1);
   }
}

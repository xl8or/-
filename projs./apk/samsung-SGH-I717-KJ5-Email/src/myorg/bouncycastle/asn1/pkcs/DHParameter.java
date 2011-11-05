package myorg.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;

public class DHParameter extends ASN1Encodable {

   DERInteger g;
   DERInteger l;
   DERInteger p;


   public DHParameter(BigInteger var1, BigInteger var2, int var3) {
      DERInteger var4 = new DERInteger(var1);
      this.p = var4;
      DERInteger var5 = new DERInteger(var2);
      this.g = var5;
      if(var3 != 0) {
         DERInteger var6 = new DERInteger(var3);
         this.l = var6;
      } else {
         this.l = null;
      }
   }

   public DHParameter(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERInteger var3 = (DERInteger)var2.nextElement();
      this.p = var3;
      DERInteger var4 = (DERInteger)var2.nextElement();
      this.g = var4;
      if(var2.hasMoreElements()) {
         DERInteger var5 = (DERInteger)var2.nextElement();
         this.l = var5;
      } else {
         this.l = null;
      }
   }

   public BigInteger getG() {
      return this.g.getPositiveValue();
   }

   public BigInteger getL() {
      BigInteger var1;
      if(this.l == null) {
         var1 = null;
      } else {
         var1 = this.l.getPositiveValue();
      }

      return var1;
   }

   public BigInteger getP() {
      return this.p.getPositiveValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.p;
      var1.add(var2);
      DERInteger var3 = this.g;
      var1.add(var3);
      if(this.getL() != null) {
         DERInteger var4 = this.l;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}

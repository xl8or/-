package myorg.bouncycastle.asn1.x9;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;

public class X9FieldID extends ASN1Encodable implements X9ObjectIdentifiers {

   private DERObjectIdentifier id;
   private DERObject parameters;


   public X9FieldID(int var1, int var2, int var3, int var4) {
      DERObjectIdentifier var5 = characteristic_two_field;
      this.id = var5;
      ASN1EncodableVector var6 = new ASN1EncodableVector();
      DERInteger var7 = new DERInteger(var1);
      var6.add(var7);
      if(var3 == 0) {
         DERObjectIdentifier var8 = tpBasis;
         var6.add(var8);
         DERInteger var9 = new DERInteger(var2);
         var6.add(var9);
      } else {
         DERObjectIdentifier var11 = ppBasis;
         var6.add(var11);
         ASN1EncodableVector var12 = new ASN1EncodableVector();
         DERInteger var13 = new DERInteger(var2);
         var12.add(var13);
         DERInteger var14 = new DERInteger(var3);
         var12.add(var14);
         DERInteger var15 = new DERInteger(var4);
         var12.add(var15);
         DERSequence var16 = new DERSequence(var12);
         var6.add(var16);
      }

      DERSequence var10 = new DERSequence(var6);
      this.parameters = var10;
   }

   public X9FieldID(BigInteger var1) {
      DERObjectIdentifier var2 = prime_field;
      this.id = var2;
      DERInteger var3 = new DERInteger(var1);
      this.parameters = var3;
   }

   public X9FieldID(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.id = var2;
      DERObject var3 = (DERObject)var1.getObjectAt(1);
      this.parameters = var3;
   }

   public DERObjectIdentifier getIdentifier() {
      return this.id;
   }

   public DERObject getParameters() {
      return this.parameters;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.id;
      var1.add(var2);
      DERObject var3 = this.parameters;
      var1.add(var3);
      return new DERSequence(var1);
   }
}

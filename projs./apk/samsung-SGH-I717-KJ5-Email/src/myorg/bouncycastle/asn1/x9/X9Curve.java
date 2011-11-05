package myorg.bouncycastle.asn1.x9;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x9.X9FieldElement;
import myorg.bouncycastle.asn1.x9.X9FieldID;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECFieldElement;

public class X9Curve extends ASN1Encodable implements X9ObjectIdentifiers {

   private ECCurve curve;
   private DERObjectIdentifier fieldIdentifier;
   private byte[] seed;


   public X9Curve(X9FieldID var1, ASN1Sequence var2) {
      Object var3 = null;
      this.fieldIdentifier = (DERObjectIdentifier)var3;
      DERObjectIdentifier var4 = var1.getIdentifier();
      this.fieldIdentifier = var4;
      DERObjectIdentifier var5 = this.fieldIdentifier;
      DERObjectIdentifier var6 = prime_field;
      if(var5.equals(var6)) {
         BigInteger var7 = ((DERInteger)var1.getParameters()).getValue();
         X9FieldElement var8 = new X9FieldElement;
         byte var10 = 0;
         ASN1OctetString var11 = (ASN1OctetString)var2.getObjectAt(var10);
         var8.<init>(var7, var11);
         X9FieldElement var15 = new X9FieldElement;
         byte var17 = 1;
         ASN1OctetString var18 = (ASN1OctetString)var2.getObjectAt(var17);
         var15.<init>(var7, var18);
         ECCurve.Fp var22 = new ECCurve.Fp;
         BigInteger var23 = var8.getValue().toBigInteger();
         BigInteger var24 = var15.getValue().toBigInteger();
         var22.<init>(var7, var23, var24);
         this.curve = var22;
      } else {
         DERObjectIdentifier var33 = this.fieldIdentifier;
         DERObjectIdentifier var34 = characteristic_two_field;
         if(var33.equals(var34)) {
            DERSequence var35 = (DERSequence)var1.getParameters();
            byte var37 = 0;
            int var38 = ((DERInteger)var35.getObjectAt(var37)).getValue().intValue();
            byte var40 = 1;
            DERObjectIdentifier var41 = (DERObjectIdentifier)var35.getObjectAt(var40);
            int var42 = 0;
            int var43 = 0;
            DERObjectIdentifier var44 = tpBasis;
            int var49;
            if(var41.equals(var44)) {
               byte var48 = 2;
               var49 = ((DERInteger)var35.getObjectAt(var48)).getValue().intValue();
            } else {
               byte var70 = 2;
               DERSequence var71 = (DERSequence)var35.getObjectAt(var70);
               byte var73 = 0;
               var49 = ((DERInteger)var71.getObjectAt(var73)).getValue().intValue();
               byte var75 = 1;
               var42 = ((DERInteger)var71.getObjectAt(var75)).getValue().intValue();
               byte var77 = 2;
               var43 = ((DERInteger)var71.getObjectAt(var77)).getValue().intValue();
            }

            byte var51 = 0;
            ASN1OctetString var52 = (ASN1OctetString)var2.getObjectAt(var51);
            X9FieldElement var53 = new X9FieldElement(var38, var49, var42, var43, var52);
            byte var55 = 1;
            ASN1OctetString var56 = (ASN1OctetString)var2.getObjectAt(var55);
            X9FieldElement var61 = new X9FieldElement(var38, var49, var42, var43, var56);
            BigInteger var62 = var53.getValue().toBigInteger();
            BigInteger var63 = var61.getValue().toBigInteger();
            ECCurve.F2m var68 = new ECCurve.F2m(var38, var49, var42, var43, var62, var63);
            this.curve = var68;
         }
      }

      if(var2.size() == 3) {
         byte var31 = 2;
         byte[] var32 = ((DERBitString)var2.getObjectAt(var31)).getBytes();
         this.seed = var32;
      }
   }

   public X9Curve(ECCurve var1) {
      this.fieldIdentifier = null;
      this.curve = var1;
      this.seed = null;
      this.setFieldIdentifier();
   }

   public X9Curve(ECCurve var1, byte[] var2) {
      this.fieldIdentifier = null;
      this.curve = var1;
      this.seed = var2;
      this.setFieldIdentifier();
   }

   private void setFieldIdentifier() {
      if(this.curve instanceof ECCurve.Fp) {
         DERObjectIdentifier var1 = prime_field;
         this.fieldIdentifier = var1;
      } else if(this.curve instanceof ECCurve.F2m) {
         DERObjectIdentifier var2 = characteristic_two_field;
         this.fieldIdentifier = var2;
      } else {
         throw new IllegalArgumentException("This type of ECCurve is not implemented");
      }
   }

   public ECCurve getCurve() {
      return this.curve;
   }

   public byte[] getSeed() {
      return this.seed;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.fieldIdentifier;
      DERObjectIdentifier var3 = prime_field;
      if(var2.equals(var3)) {
         ECFieldElement var4 = this.curve.getA();
         DERObject var5 = (new X9FieldElement(var4)).getDERObject();
         var1.add(var5);
         ECFieldElement var6 = this.curve.getB();
         DERObject var7 = (new X9FieldElement(var6)).getDERObject();
         var1.add(var7);
      } else {
         DERObjectIdentifier var10 = this.fieldIdentifier;
         DERObjectIdentifier var11 = characteristic_two_field;
         if(var10.equals(var11)) {
            ECFieldElement var12 = this.curve.getA();
            DERObject var13 = (new X9FieldElement(var12)).getDERObject();
            var1.add(var13);
            ECFieldElement var14 = this.curve.getB();
            DERObject var15 = (new X9FieldElement(var14)).getDERObject();
            var1.add(var15);
         }
      }

      if(this.seed != null) {
         byte[] var8 = this.seed;
         DERBitString var9 = new DERBitString(var8);
         var1.add(var9);
      }

      return new DERSequence(var1);
   }
}

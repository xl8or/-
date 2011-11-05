package myorg.bouncycastle.asn1.x9;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x9.X9Curve;
import myorg.bouncycastle.asn1.x9.X9ECPoint;
import myorg.bouncycastle.asn1.x9.X9FieldID;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class X9ECParameters extends ASN1Encodable implements X9ObjectIdentifiers {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private ECCurve curve;
   private X9FieldID fieldID;
   private ECPoint g;
   private BigInteger h;
   private BigInteger n;
   private byte[] seed;


   public X9ECParameters(ASN1Sequence var1) {
      if(var1.getObjectAt(0) instanceof DERInteger) {
         BigInteger var2 = ((DERInteger)var1.getObjectAt(0)).getValue();
         BigInteger var3 = ONE;
         if(var2.equals(var3)) {
            ASN1Sequence var4 = (ASN1Sequence)var1.getObjectAt(1);
            X9FieldID var5 = new X9FieldID(var4);
            ASN1Sequence var6 = (ASN1Sequence)var1.getObjectAt(2);
            X9Curve var7 = new X9Curve(var5, var6);
            ECCurve var8 = var7.getCurve();
            this.curve = var8;
            ECCurve var9 = this.curve;
            ASN1OctetString var10 = (ASN1OctetString)var1.getObjectAt(3);
            ECPoint var11 = (new X9ECPoint(var9, var10)).getPoint();
            this.g = var11;
            BigInteger var12 = ((DERInteger)var1.getObjectAt(4)).getValue();
            this.n = var12;
            byte[] var13 = var7.getSeed();
            this.seed = var13;
            if(var1.size() != 6) {
               return;
            }

            BigInteger var14 = ((DERInteger)var1.getObjectAt(5)).getValue();
            this.h = var14;
            return;
         }
      }

      throw new IllegalArgumentException("bad version in X9ECParameters");
   }

   public X9ECParameters(ECCurve var1, ECPoint var2, BigInteger var3) {
      BigInteger var4 = ONE;
      this(var1, var2, var3, var4, (byte[])null);
   }

   public X9ECParameters(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4) {
      this(var1, var2, var3, var4, (byte[])null);
   }

   public X9ECParameters(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4, byte[] var5) {
      this.curve = var1;
      this.g = var2;
      this.n = var3;
      this.h = var4;
      this.seed = var5;
      if(var1 instanceof ECCurve.Fp) {
         BigInteger var6 = ((ECCurve.Fp)var1).getQ();
         X9FieldID var7 = new X9FieldID(var6);
         this.fieldID = var7;
      } else if(var1 instanceof ECCurve.F2m) {
         ECCurve.F2m var8 = (ECCurve.F2m)var1;
         int var9 = var8.getM();
         int var10 = var8.getK1();
         int var11 = var8.getK2();
         int var12 = var8.getK3();
         X9FieldID var13 = new X9FieldID(var9, var10, var11, var12);
         this.fieldID = var13;
      }
   }

   public ECCurve getCurve() {
      return this.curve;
   }

   public ECPoint getG() {
      return this.g;
   }

   public BigInteger getH() {
      BigInteger var1;
      if(this.h == null) {
         var1 = ONE;
      } else {
         var1 = this.h;
      }

      return var1;
   }

   public BigInteger getN() {
      return this.n;
   }

   public byte[] getSeed() {
      return this.seed;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = new DERInteger(1);
      var1.add(var2);
      X9FieldID var3 = this.fieldID;
      var1.add(var3);
      ECCurve var4 = this.curve;
      byte[] var5 = this.seed;
      X9Curve var6 = new X9Curve(var4, var5);
      var1.add(var6);
      ECPoint var7 = this.g;
      X9ECPoint var8 = new X9ECPoint(var7);
      var1.add(var8);
      BigInteger var9 = this.n;
      DERInteger var10 = new DERInteger(var9);
      var1.add(var10);
      if(this.h != null) {
         BigInteger var11 = this.h;
         DERInteger var12 = new DERInteger(var11);
         var1.add(var12);
      }

      return new DERSequence(var1);
   }
}

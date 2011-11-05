package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.DistributionPoint;

public class CRLDistPoint extends ASN1Encodable {

   ASN1Sequence seq = null;


   public CRLDistPoint(ASN1Sequence var1) {
      this.seq = var1;
   }

   public CRLDistPoint(DistributionPoint[] var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 == var4) {
            DERSequence var6 = new DERSequence(var2);
            this.seq = var6;
            return;
         }

         DistributionPoint var5 = var1[var3];
         var2.add(var5);
         ++var3;
      }
   }

   public static CRLDistPoint getInstance(Object var0) {
      CRLDistPoint var1;
      if(!(var0 instanceof CRLDistPoint) && var0 != null) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CRLDistPoint(var2);
      } else {
         var1 = (CRLDistPoint)var0;
      }

      return var1;
   }

   public static CRLDistPoint getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DistributionPoint[] getDistributionPoints() {
      DistributionPoint[] var1 = new DistributionPoint[this.seq.size()];
      int var2 = 0;

      while(true) {
         int var3 = this.seq.size();
         if(var2 == var3) {
            return var1;
         }

         DistributionPoint var4 = DistributionPoint.getInstance(this.seq.getObjectAt(var2));
         var1[var2] = var4;
         ++var2;
      }
   }

   public DERObject toASN1Object() {
      return this.seq;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("CRLDistPoint:");
      var1.append(var2);
      DistributionPoint[] var5 = this.getDistributionPoints();
      int var6 = 0;

      while(true) {
         int var7 = var5.length;
         if(var6 == var7) {
            return var1.toString();
         }

         StringBuffer var8 = var1.append("    ");
         DistributionPoint var9 = var5[var6];
         var1.append(var9);
         var1.append(var2);
         ++var6;
      }
   }
}

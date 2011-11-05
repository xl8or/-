package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.DistributionPointName;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.ReasonFlags;

public class DistributionPoint extends ASN1Encodable {

   GeneralNames cRLIssuer;
   DistributionPointName distributionPoint;
   ReasonFlags reasons;


   public DistributionPoint(ASN1Sequence var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.size();
         if(var2 == var3) {
            return;
         }

         ASN1TaggedObject var4 = ASN1TaggedObject.getInstance(var1.getObjectAt(var2));
         switch(var4.getTagNo()) {
         case 0:
            DistributionPointName var5 = DistributionPointName.getInstance(var4, (boolean)1);
            this.distributionPoint = var5;
            break;
         case 1:
            DERBitString var6 = DERBitString.getInstance(var4, (boolean)0);
            ReasonFlags var7 = new ReasonFlags(var6);
            this.reasons = var7;
            break;
         case 2:
            GeneralNames var8 = GeneralNames.getInstance(var4, (boolean)0);
            this.cRLIssuer = var8;
         }

         ++var2;
      }
   }

   public DistributionPoint(DistributionPointName var1, ReasonFlags var2, GeneralNames var3) {
      this.distributionPoint = var1;
      this.reasons = var2;
      this.cRLIssuer = var3;
   }

   private void appendObject(StringBuffer var1, String var2, String var3, String var4) {
      StringBuffer var5 = var1.append("    ");
      var1.append(var3);
      StringBuffer var7 = var1.append(":");
      var1.append(var2);
      StringBuffer var9 = var1.append("    ");
      StringBuffer var10 = var1.append("    ");
      var1.append(var4);
      var1.append(var2);
   }

   public static DistributionPoint getInstance(Object var0) {
      DistributionPoint var1;
      if(var0 != null && !(var0 instanceof DistributionPoint)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid DistributionPoint: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new DistributionPoint(var2);
      } else {
         var1 = (DistributionPoint)var0;
      }

      return var1;
   }

   public static DistributionPoint getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public GeneralNames getCRLIssuer() {
      return this.cRLIssuer;
   }

   public DistributionPointName getDistributionPoint() {
      return this.distributionPoint;
   }

   public ReasonFlags getReasons() {
      return this.reasons;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.distributionPoint != null) {
         DistributionPointName var2 = this.distributionPoint;
         DERTaggedObject var3 = new DERTaggedObject(0, var2);
         var1.add(var3);
      }

      if(this.reasons != null) {
         ReasonFlags var4 = this.reasons;
         DERTaggedObject var5 = new DERTaggedObject((boolean)0, 1, var4);
         var1.add(var5);
      }

      if(this.cRLIssuer != null) {
         GeneralNames var6 = this.cRLIssuer;
         DERTaggedObject var7 = new DERTaggedObject((boolean)0, 2, var6);
         var1.add(var7);
      }

      return new DERSequence(var1);
   }

   public String toString() {
      String var1 = System.getProperty("line.separator");
      StringBuffer var2 = new StringBuffer();
      StringBuffer var3 = var2.append("DistributionPoint: [");
      var2.append(var1);
      if(this.distributionPoint != null) {
         String var5 = this.distributionPoint.toString();
         this.appendObject(var2, var1, "distributionPoint", var5);
      }

      if(this.reasons != null) {
         String var6 = this.reasons.toString();
         this.appendObject(var2, var1, "reasons", var6);
      }

      if(this.cRLIssuer != null) {
         String var7 = this.cRLIssuer.toString();
         this.appendObject(var2, var1, "cRLIssuer", var7);
      }

      StringBuffer var8 = var2.append("]");
      var2.append(var1);
      return var2.toString();
   }
}

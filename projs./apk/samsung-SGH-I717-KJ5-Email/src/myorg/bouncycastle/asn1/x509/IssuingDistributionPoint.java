package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.DistributionPointName;
import myorg.bouncycastle.asn1.x509.ReasonFlags;

public class IssuingDistributionPoint extends ASN1Encodable {

   private DistributionPointName distributionPoint;
   private boolean indirectCRL;
   private boolean onlyContainsAttributeCerts;
   private boolean onlyContainsCACerts;
   private boolean onlyContainsUserCerts;
   private ReasonFlags onlySomeReasons;
   private ASN1Sequence seq;


   public IssuingDistributionPoint(ASN1Sequence var1) {
      this.seq = var1;
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
            boolean var6 = DERBoolean.getInstance(var4, (boolean)0).isTrue();
            this.onlyContainsUserCerts = var6;
            break;
         case 2:
            boolean var7 = DERBoolean.getInstance(var4, (boolean)0).isTrue();
            this.onlyContainsCACerts = var7;
            break;
         case 3:
            DERBitString var8 = ReasonFlags.getInstance(var4, (boolean)0);
            ReasonFlags var9 = new ReasonFlags(var8);
            this.onlySomeReasons = var9;
            break;
         case 4:
            boolean var10 = DERBoolean.getInstance(var4, (boolean)0).isTrue();
            this.indirectCRL = var10;
            break;
         case 5:
            boolean var11 = DERBoolean.getInstance(var4, (boolean)0).isTrue();
            this.onlyContainsAttributeCerts = var11;
            break;
         default:
            throw new IllegalArgumentException("unknown tag in IssuingDistributionPoint");
         }

         ++var2;
      }
   }

   public IssuingDistributionPoint(DistributionPointName var1, boolean var2, boolean var3, ReasonFlags var4, boolean var5, boolean var6) {
      this.distributionPoint = var1;
      this.indirectCRL = var5;
      this.onlyContainsAttributeCerts = var6;
      this.onlyContainsCACerts = var3;
      this.onlyContainsUserCerts = var2;
      this.onlySomeReasons = var4;
      ASN1EncodableVector var7 = new ASN1EncodableVector();
      if(var1 != null) {
         DERTaggedObject var8 = new DERTaggedObject((boolean)1, 0, var1);
         var7.add(var8);
      }

      if(var2) {
         DERBoolean var9 = new DERBoolean((boolean)1);
         DERTaggedObject var10 = new DERTaggedObject((boolean)0, 1, var9);
         var7.add(var10);
      }

      if(var3) {
         DERBoolean var11 = new DERBoolean((boolean)1);
         DERTaggedObject var12 = new DERTaggedObject((boolean)0, 2, var11);
         var7.add(var12);
      }

      if(var4 != null) {
         DERTaggedObject var13 = new DERTaggedObject((boolean)0, 3, var4);
         var7.add(var13);
      }

      if(var5) {
         DERBoolean var14 = new DERBoolean((boolean)1);
         DERTaggedObject var15 = new DERTaggedObject((boolean)0, 4, var14);
         var7.add(var15);
      }

      if(var6) {
         DERBoolean var16 = new DERBoolean((boolean)1);
         DERTaggedObject var17 = new DERTaggedObject((boolean)0, 5, var16);
         var7.add(var17);
      }

      DERSequence var18 = new DERSequence(var7);
      this.seq = var18;
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

   private String booleanToString(boolean var1) {
      String var2;
      if(var1) {
         var2 = "true";
      } else {
         var2 = "false";
      }

      return var2;
   }

   public static IssuingDistributionPoint getInstance(Object var0) {
      IssuingDistributionPoint var1;
      if(var0 != null && !(var0 instanceof IssuingDistributionPoint)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new IssuingDistributionPoint(var2);
      } else {
         var1 = (IssuingDistributionPoint)var0;
      }

      return var1;
   }

   public static IssuingDistributionPoint getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DistributionPointName getDistributionPoint() {
      return this.distributionPoint;
   }

   public ReasonFlags getOnlySomeReasons() {
      return this.onlySomeReasons;
   }

   public boolean isIndirectCRL() {
      return this.indirectCRL;
   }

   public boolean onlyContainsAttributeCerts() {
      return this.onlyContainsAttributeCerts;
   }

   public boolean onlyContainsCACerts() {
      return this.onlyContainsCACerts;
   }

   public boolean onlyContainsUserCerts() {
      return this.onlyContainsUserCerts;
   }

   public DERObject toASN1Object() {
      return this.seq;
   }

   public String toString() {
      String var1 = System.getProperty("line.separator");
      StringBuffer var2 = new StringBuffer();
      StringBuffer var3 = var2.append("IssuingDistributionPoint: [");
      var2.append(var1);
      if(this.distributionPoint != null) {
         String var5 = this.distributionPoint.toString();
         this.appendObject(var2, var1, "distributionPoint", var5);
      }

      if(this.onlyContainsUserCerts) {
         boolean var6 = this.onlyContainsUserCerts;
         String var7 = this.booleanToString(var6);
         this.appendObject(var2, var1, "onlyContainsUserCerts", var7);
      }

      if(this.onlyContainsCACerts) {
         boolean var8 = this.onlyContainsCACerts;
         String var9 = this.booleanToString(var8);
         this.appendObject(var2, var1, "onlyContainsCACerts", var9);
      }

      if(this.onlySomeReasons != null) {
         String var10 = this.onlySomeReasons.toString();
         this.appendObject(var2, var1, "onlySomeReasons", var10);
      }

      if(this.onlyContainsAttributeCerts) {
         boolean var11 = this.onlyContainsAttributeCerts;
         String var12 = this.booleanToString(var11);
         this.appendObject(var2, var1, "onlyContainsAttributeCerts", var12);
      }

      if(this.indirectCRL) {
         boolean var13 = this.indirectCRL;
         String var14 = this.booleanToString(var13);
         this.appendObject(var2, var1, "indirectCRL", var14);
      }

      StringBuffer var15 = var2.append("]");
      var2.append(var1);
      return var2.toString();
   }
}

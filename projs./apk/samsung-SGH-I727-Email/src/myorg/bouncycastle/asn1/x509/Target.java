package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class Target extends ASN1Encodable implements ASN1Choice {

   public static final int targetGroup = 1;
   public static final int targetName;
   private GeneralName targGroup;
   private GeneralName targName;


   public Target(int var1, GeneralName var2) {
      DERTaggedObject var3 = new DERTaggedObject(var1, var2);
      this(var3);
   }

   private Target(ASN1TaggedObject var1) {
      switch(var1.getTagNo()) {
      case 0:
         GeneralName var5 = GeneralName.getInstance(var1, (boolean)1);
         this.targName = var5;
         return;
      case 1:
         GeneralName var6 = GeneralName.getInstance(var1, (boolean)1);
         this.targGroup = var6;
         return;
      default:
         StringBuilder var2 = (new StringBuilder()).append("unknown tag: ");
         int var3 = var1.getTagNo();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public static Target getInstance(Object var0) {
      Target var1;
      if(var0 instanceof Target) {
         var1 = (Target)var0;
      } else {
         if(!(var0 instanceof ASN1TaggedObject)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            Class var4 = var0.getClass();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1TaggedObject var2 = (ASN1TaggedObject)var0;
         var1 = new Target(var2);
      }

      return var1;
   }

   public GeneralName getTargetGroup() {
      return this.targGroup;
   }

   public GeneralName getTargetName() {
      return this.targName;
   }

   public DERObject toASN1Object() {
      DERTaggedObject var2;
      if(this.targName != null) {
         GeneralName var1 = this.targName;
         var2 = new DERTaggedObject((boolean)1, 0, var1);
      } else {
         GeneralName var3 = this.targGroup;
         var2 = new DERTaggedObject((boolean)1, 1, var3);
      }

      return var2;
   }
}

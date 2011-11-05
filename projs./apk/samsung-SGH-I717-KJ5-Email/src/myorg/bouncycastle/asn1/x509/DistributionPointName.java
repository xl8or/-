package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralNames;

public class DistributionPointName extends ASN1Encodable implements ASN1Choice {

   public static final int FULL_NAME = 0;
   public static final int NAME_RELATIVE_TO_CRL_ISSUER = 1;
   DEREncodable name;
   int type;


   public DistributionPointName(int var1, ASN1Encodable var2) {
      this.type = var1;
      this.name = var2;
   }

   public DistributionPointName(int var1, DEREncodable var2) {
      this.type = var1;
      this.name = var2;
   }

   public DistributionPointName(ASN1TaggedObject var1) {
      int var2 = var1.getTagNo();
      this.type = var2;
      if(this.type == 0) {
         GeneralNames var3 = GeneralNames.getInstance(var1, (boolean)0);
         this.name = var3;
      } else {
         ASN1Set var4 = ASN1Set.getInstance(var1, (boolean)0);
         this.name = var4;
      }
   }

   public DistributionPointName(GeneralNames var1) {
      this(0, (ASN1Encodable)var1);
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

   public static DistributionPointName getInstance(Object var0) {
      DistributionPointName var1;
      if(var0 != null && !(var0 instanceof DistributionPointName)) {
         if(!(var0 instanceof ASN1TaggedObject)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1TaggedObject var2 = (ASN1TaggedObject)var0;
         var1 = new DistributionPointName(var2);
      } else {
         var1 = (DistributionPointName)var0;
      }

      return var1;
   }

   public static DistributionPointName getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1TaggedObject.getInstance(var0, (boolean)1));
   }

   public ASN1Encodable getName() {
      return (ASN1Encodable)this.name;
   }

   public int getType() {
      return this.type;
   }

   public DERObject toASN1Object() {
      int var1 = this.type;
      DEREncodable var2 = this.name;
      return new DERTaggedObject((boolean)0, var1, var2);
   }

   public String toString() {
      String var1 = System.getProperty("line.separator");
      StringBuffer var2 = new StringBuffer();
      StringBuffer var3 = var2.append("DistributionPointName: [");
      var2.append(var1);
      if(this.type == 0) {
         String var5 = this.name.toString();
         this.appendObject(var2, var1, "fullName", var5);
      } else {
         String var8 = this.name.toString();
         this.appendObject(var2, var1, "nameRelativeToCRLIssuer", var8);
      }

      StringBuffer var6 = var2.append("]");
      var2.append(var1);
      return var2.toString();
   }
}

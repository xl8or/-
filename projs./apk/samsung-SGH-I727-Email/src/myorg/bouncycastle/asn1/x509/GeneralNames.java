package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class GeneralNames extends ASN1Encodable {

   private final GeneralName[] names;


   public GeneralNames(ASN1Sequence var1) {
      GeneralName[] var2 = new GeneralName[var1.size()];
      this.names = var2;
      int var3 = 0;

      while(true) {
         int var4 = var1.size();
         if(var3 == var4) {
            return;
         }

         GeneralName[] var5 = this.names;
         GeneralName var6 = GeneralName.getInstance(var1.getObjectAt(var3));
         var5[var3] = var6;
         ++var3;
      }
   }

   public GeneralNames(GeneralName var1) {
      GeneralName[] var2 = new GeneralName[]{var1};
      this.names = var2;
   }

   public static GeneralNames getInstance(Object var0) {
      GeneralNames var1;
      if(var0 != null && !(var0 instanceof GeneralNames)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new GeneralNames(var2);
      } else {
         var1 = (GeneralNames)var0;
      }

      return var1;
   }

   public static GeneralNames getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public GeneralName[] getNames() {
      GeneralName[] var1 = new GeneralName[this.names.length];
      GeneralName[] var2 = this.names;
      int var3 = this.names.length;
      System.arraycopy(var2, 0, var1, 0, var3);
      return var1;
   }

   public DERObject toASN1Object() {
      GeneralName[] var1 = this.names;
      return new DERSequence(var1);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("GeneralNames:");
      var1.append(var2);
      int var5 = 0;

      while(true) {
         int var6 = this.names.length;
         if(var5 == var6) {
            return var1.toString();
         }

         StringBuffer var7 = var1.append("    ");
         GeneralName var8 = this.names[var5];
         var1.append(var8);
         var1.append(var2);
         ++var5;
      }
   }
}

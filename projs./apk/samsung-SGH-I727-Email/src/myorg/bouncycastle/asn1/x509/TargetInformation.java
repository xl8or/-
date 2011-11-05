package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.Target;
import myorg.bouncycastle.asn1.x509.Targets;

public class TargetInformation extends ASN1Encodable {

   private ASN1Sequence targets;


   private TargetInformation(ASN1Sequence var1) {
      this.targets = var1;
   }

   public TargetInformation(Targets var1) {
      DERSequence var2 = new DERSequence(var1);
      this.targets = var2;
   }

   public TargetInformation(Target[] var1) {
      Targets var2 = new Targets(var1);
      this(var2);
   }

   public static TargetInformation getInstance(Object var0) {
      TargetInformation var1;
      if(var0 instanceof TargetInformation) {
         var1 = (TargetInformation)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            Class var4 = var0.getClass();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new TargetInformation(var2);
      }

      return var1;
   }

   public Targets[] getTargetsObjects() {
      Targets[] var1 = new Targets[this.targets.size()];
      int var2 = 0;

      int var4;
      for(Enumeration var3 = this.targets.getObjects(); var3.hasMoreElements(); var2 = var4) {
         var4 = var2 + 1;
         Targets var5 = Targets.getInstance(var3.nextElement());
         var1[var2] = var5;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.targets;
   }
}

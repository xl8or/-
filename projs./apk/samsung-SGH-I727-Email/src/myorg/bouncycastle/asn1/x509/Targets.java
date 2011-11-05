package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.Target;

public class Targets extends ASN1Encodable {

   private ASN1Sequence targets;


   private Targets(ASN1Sequence var1) {
      this.targets = var1;
   }

   public Targets(Target[] var1) {
      DERSequence var2 = new DERSequence(var1);
      this.targets = var2;
   }

   public static Targets getInstance(Object var0) {
      Targets var1;
      if(var0 instanceof Targets) {
         var1 = (Targets)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            Class var4 = var0.getClass();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Targets(var2);
      }

      return var1;
   }

   public Target[] getTargets() {
      Target[] var1 = new Target[this.targets.size()];
      int var2 = 0;

      int var4;
      for(Enumeration var3 = this.targets.getObjects(); var3.hasMoreElements(); var2 = var4) {
         var4 = var2 + 1;
         Target var5 = Target.getInstance(var3.nextElement());
         var1[var2] = var5;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.targets;
   }
}

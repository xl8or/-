package myorg.bouncycastle.asn1.tsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class Accuracy extends ASN1Encodable {

   protected static final int MAX_MICROS = 999;
   protected static final int MAX_MILLIS = 999;
   protected static final int MIN_MICROS = 1;
   protected static final int MIN_MILLIS = 1;
   DERInteger micros;
   DERInteger millis;
   DERInteger seconds;


   protected Accuracy() {}

   public Accuracy(ASN1Sequence var1) {
      this.seconds = null;
      this.millis = null;
      this.micros = null;
      int var2 = 0;

      while(true) {
         int var3 = var1.size();
         if(var2 >= var3) {
            return;
         }

         if(var1.getObjectAt(var2) instanceof DERInteger) {
            DERInteger var4 = (DERInteger)var1.getObjectAt(var2);
            this.seconds = var4;
         } else if(var1.getObjectAt(var2) instanceof DERTaggedObject) {
            DERTaggedObject var5 = (DERTaggedObject)var1.getObjectAt(var2);
            switch(var5.getTagNo()) {
            case 0:
               DERInteger var6 = DERInteger.getInstance(var5, (boolean)0);
               this.millis = var6;
               if(this.millis.getValue().intValue() < 1 || this.millis.getValue().intValue() > 999) {
                  throw new IllegalArgumentException("Invalid millis field : not in (1..999).");
               }
               break;
            case 1:
               DERInteger var7 = DERInteger.getInstance(var5, (boolean)0);
               this.micros = var7;
               if(this.micros.getValue().intValue() < 1 || this.micros.getValue().intValue() > 999) {
                  throw new IllegalArgumentException("Invalid micros field : not in (1..999).");
               }
               break;
            default:
               throw new IllegalArgumentException("Invalig tag number");
            }
         }

         ++var2;
      }
   }

   public Accuracy(DERInteger var1, DERInteger var2, DERInteger var3) {
      this.seconds = var1;
      if(var2 != null && (var2.getValue().intValue() < 1 || var2.getValue().intValue() > 999)) {
         throw new IllegalArgumentException("Invalid millis field : not in (1..999)");
      } else {
         this.millis = var2;
         if(var3 != null && (var3.getValue().intValue() < 1 || var3.getValue().intValue() > 999)) {
            throw new IllegalArgumentException("Invalid micros field : not in (1..999)");
         } else {
            this.micros = var3;
         }
      }
   }

   public static Accuracy getInstance(Object var0) {
      Accuracy var1;
      if(var0 != null && !(var0 instanceof Accuracy)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Unknown object in \'Accuracy\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Accuracy(var2);
      } else {
         var1 = (Accuracy)var0;
      }

      return var1;
   }

   public DERInteger getMicros() {
      return this.micros;
   }

   public DERInteger getMillis() {
      return this.millis;
   }

   public DERInteger getSeconds() {
      return this.seconds;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.seconds != null) {
         DERInteger var2 = this.seconds;
         var1.add(var2);
      }

      if(this.millis != null) {
         DERInteger var3 = this.millis;
         DERTaggedObject var4 = new DERTaggedObject((boolean)0, 0, var3);
         var1.add(var4);
      }

      if(this.micros != null) {
         DERInteger var5 = this.micros;
         DERTaggedObject var6 = new DERTaggedObject((boolean)0, 1, var5);
         var1.add(var6);
      }

      return new DERSequence(var1);
   }
}

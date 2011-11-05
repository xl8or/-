package myorg.bouncycastle.asn1.x509;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class GeneralSubtree extends ASN1Encodable {

   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private GeneralName base;
   private DERInteger maximum;
   private DERInteger minimum;


   public GeneralSubtree(ASN1Sequence var1) {
      GeneralName var2 = GeneralName.getInstance(var1.getObjectAt(0));
      this.base = var2;
      switch(var1.size()) {
      case 2:
         ASN1TaggedObject var6 = ASN1TaggedObject.getInstance(var1.getObjectAt(1));
         switch(var6.getTagNo()) {
         case 0:
            DERInteger var10 = DERInteger.getInstance(var6, (boolean)0);
            this.minimum = var10;
            break;
         case 1:
            DERInteger var11 = DERInteger.getInstance(var6, (boolean)0);
            this.maximum = var11;
            return;
         default:
            StringBuilder var7 = (new StringBuilder()).append("Bad tag number: ");
            int var8 = var6.getTagNo();
            String var9 = var7.append(var8).toString();
            throw new IllegalArgumentException(var9);
         }
      case 1:
         return;
      case 3:
         DERInteger var12 = DERInteger.getInstance(ASN1TaggedObject.getInstance(var1.getObjectAt(1)));
         this.minimum = var12;
         DERInteger var13 = DERInteger.getInstance(ASN1TaggedObject.getInstance(var1.getObjectAt(2)));
         this.maximum = var13;
         return;
      default:
         StringBuilder var3 = (new StringBuilder()).append("Bad sequence size: ");
         int var4 = var1.size();
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      }
   }

   public GeneralSubtree(GeneralName var1) {
      this(var1, (BigInteger)null, (BigInteger)null);
   }

   public GeneralSubtree(GeneralName var1, BigInteger var2, BigInteger var3) {
      this.base = var1;
      if(var3 != null) {
         DERInteger var4 = new DERInteger(var3);
         this.maximum = var4;
      }

      if(var2 == null) {
         this.minimum = null;
      } else {
         DERInteger var5 = new DERInteger(var2);
         this.minimum = var5;
      }
   }

   public static GeneralSubtree getInstance(Object var0) {
      GeneralSubtree var1;
      if(var0 == null) {
         var1 = null;
      } else if(var0 instanceof GeneralSubtree) {
         var1 = (GeneralSubtree)var0;
      } else {
         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new GeneralSubtree(var2);
      }

      return var1;
   }

   public static GeneralSubtree getInstance(ASN1TaggedObject var0, boolean var1) {
      ASN1Sequence var2 = ASN1Sequence.getInstance(var0, var1);
      return new GeneralSubtree(var2);
   }

   public GeneralName getBase() {
      return this.base;
   }

   public BigInteger getMaximum() {
      BigInteger var1;
      if(this.maximum == null) {
         var1 = null;
      } else {
         var1 = this.maximum.getValue();
      }

      return var1;
   }

   public BigInteger getMinimum() {
      BigInteger var1;
      if(this.minimum == null) {
         var1 = ZERO;
      } else {
         var1 = this.minimum.getValue();
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      GeneralName var2 = this.base;
      var1.add(var2);
      if(this.minimum != null) {
         BigInteger var3 = this.minimum.getValue();
         BigInteger var4 = ZERO;
         if(!var3.equals(var4)) {
            DERInteger var5 = this.minimum;
            DERTaggedObject var6 = new DERTaggedObject((boolean)0, 0, var5);
            var1.add(var6);
         }
      }

      if(this.maximum != null) {
         DERInteger var7 = this.maximum;
         DERTaggedObject var8 = new DERTaggedObject((boolean)0, 1, var7);
         var1.add(var8);
      }

      return new DERSequence(var1);
   }
}

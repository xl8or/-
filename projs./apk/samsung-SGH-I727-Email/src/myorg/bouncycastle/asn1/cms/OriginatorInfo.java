package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class OriginatorInfo extends ASN1Encodable {

   private ASN1Set certs;
   private ASN1Set crls;


   public OriginatorInfo(ASN1Sequence var1) {
      switch(var1.size()) {
      case 1:
         ASN1TaggedObject var2 = (ASN1TaggedObject)var1.getObjectAt(0);
         switch(var2.getTagNo()) {
         case 0:
            ASN1Set var6 = ASN1Set.getInstance(var2, (boolean)0);
            this.certs = var6;
            break;
         case 1:
            ASN1Set var7 = ASN1Set.getInstance(var2, (boolean)0);
            this.crls = var7;
            return;
         default:
            StringBuilder var3 = (new StringBuilder()).append("Bad tag in OriginatorInfo: ");
            int var4 = var2.getTagNo();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }
      case 0:
         return;
      case 2:
         ASN1Set var8 = ASN1Set.getInstance((ASN1TaggedObject)var1.getObjectAt(0), (boolean)0);
         this.certs = var8;
         ASN1Set var9 = ASN1Set.getInstance((ASN1TaggedObject)var1.getObjectAt(1), (boolean)0);
         this.crls = var9;
         return;
      default:
         throw new IllegalArgumentException("OriginatorInfo too big");
      }
   }

   public OriginatorInfo(ASN1Set var1, ASN1Set var2) {
      this.certs = var1;
      this.crls = var2;
   }

   public static OriginatorInfo getInstance(Object var0) {
      OriginatorInfo var1;
      if(var0 != null && !(var0 instanceof OriginatorInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid OriginatorInfo: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OriginatorInfo(var2);
      } else {
         var1 = (OriginatorInfo)var0;
      }

      return var1;
   }

   public static OriginatorInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1Set getCRLs() {
      return this.crls;
   }

   public ASN1Set getCertificates() {
      return this.certs;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.certs != null) {
         ASN1Set var2 = this.certs;
         DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var2);
         var1.add(var3);
      }

      if(this.crls != null) {
         ASN1Set var4 = this.crls;
         DERTaggedObject var5 = new DERTaggedObject((boolean)0, 1, var4);
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}

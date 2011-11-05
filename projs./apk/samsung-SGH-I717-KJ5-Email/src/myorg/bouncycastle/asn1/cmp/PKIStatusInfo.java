package myorg.bouncycastle.asn1.cmp;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.PKIFailureInfo;
import myorg.bouncycastle.asn1.cmp.PKIFreeText;

public class PKIStatusInfo extends ASN1Encodable {

   DERBitString failInfo;
   DERInteger status;
   PKIFreeText statusString;


   public PKIStatusInfo(int var1) {
      DERInteger var2 = new DERInteger(var1);
      this.status = var2;
   }

   public PKIStatusInfo(int var1, PKIFreeText var2) {
      DERInteger var3 = new DERInteger(var1);
      this.status = var3;
      this.statusString = var2;
   }

   public PKIStatusInfo(int var1, PKIFreeText var2, PKIFailureInfo var3) {
      DERInteger var4 = new DERInteger(var1);
      this.status = var4;
      this.statusString = var2;
      this.failInfo = var3;
   }

   public PKIStatusInfo(ASN1Sequence var1) {
      DERInteger var2 = DERInteger.getInstance(var1.getObjectAt(0));
      this.status = var2;
      this.statusString = null;
      this.failInfo = null;
      if(var1.size() > 2) {
         PKIFreeText var3 = PKIFreeText.getInstance(var1.getObjectAt(1));
         this.statusString = var3;
         DERBitString var4 = DERBitString.getInstance(var1.getObjectAt(2));
         this.failInfo = var4;
      } else if(var1.size() > 1) {
         DEREncodable var5 = var1.getObjectAt(1);
         if(var5 instanceof DERBitString) {
            DERBitString var6 = DERBitString.getInstance(var5);
            this.failInfo = var6;
         } else {
            PKIFreeText var7 = PKIFreeText.getInstance(var5);
            this.statusString = var7;
         }
      }
   }

   public static PKIStatusInfo getInstance(Object var0) {
      PKIStatusInfo var1;
      if(var0 instanceof PKIStatusInfo) {
         var1 = (PKIStatusInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PKIStatusInfo(var2);
      }

      return var1;
   }

   public static PKIStatusInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERBitString getFailInfo() {
      return this.failInfo;
   }

   public BigInteger getStatus() {
      return this.status.getValue();
   }

   public PKIFreeText getStatusString() {
      return this.statusString;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.status;
      var1.add(var2);
      if(this.statusString != null) {
         PKIFreeText var3 = this.statusString;
         var1.add(var3);
      }

      if(this.failInfo != null) {
         DERBitString var4 = this.failInfo;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}

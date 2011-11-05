package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.crmf.POPOPrivKey;
import myorg.bouncycastle.asn1.crmf.POPOSigningKey;

public class ProofOfPossession extends ASN1Encodable implements ASN1Choice {

   private ASN1Encodable obj;
   private int tagNo;


   private ProofOfPossession(ASN1TaggedObject var1) {
      int var2 = var1.getTagNo();
      this.tagNo = var2;
      switch(this.tagNo) {
      case 0:
         DERNull var6 = DERNull.INSTANCE;
         this.obj = var6;
         return;
      case 1:
         POPOSigningKey var7 = POPOSigningKey.getInstance(var1, (boolean)0);
         this.obj = var7;
         return;
      case 2:
      case 3:
         ASN1Encodable var8 = POPOPrivKey.getInstance(var1, (boolean)0);
         this.obj = var8;
         return;
      default:
         StringBuilder var3 = (new StringBuilder()).append("unknown tag: ");
         int var4 = this.tagNo;
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      }
   }

   public static ProofOfPossession getInstance(Object var0) {
      ProofOfPossession var1;
      if(var0 instanceof ProofOfPossession) {
         var1 = (ProofOfPossession)var0;
      } else {
         if(!(var0 instanceof ASN1TaggedObject)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1TaggedObject var2 = (ASN1TaggedObject)var0;
         var1 = new ProofOfPossession(var2);
      }

      return var1;
   }

   public ASN1Encodable getObject() {
      return this.obj;
   }

   public int getType() {
      return this.tagNo;
   }

   public DERObject toASN1Object() {
      int var1 = this.tagNo;
      ASN1Encodable var2 = this.obj;
      return new DERTaggedObject((boolean)0, var1, var2);
   }
}

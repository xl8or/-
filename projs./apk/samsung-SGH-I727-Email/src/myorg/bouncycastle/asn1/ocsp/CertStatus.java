package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.ocsp.RevokedInfo;

public class CertStatus extends ASN1Encodable implements ASN1Choice {

   private int tagNo;
   private DEREncodable value;


   public CertStatus() {
      this.tagNo = 0;
      DERNull var1 = new DERNull();
      this.value = var1;
   }

   public CertStatus(int var1, DEREncodable var2) {
      this.tagNo = var1;
      this.value = var2;
   }

   public CertStatus(ASN1TaggedObject var1) {
      int var2 = var1.getTagNo();
      this.tagNo = var2;
      switch(var1.getTagNo()) {
      case 0:
         DERNull var3 = new DERNull();
         this.value = var3;
         return;
      case 1:
         RevokedInfo var4 = RevokedInfo.getInstance(var1, (boolean)0);
         this.value = var4;
         return;
      case 2:
         DERNull var5 = new DERNull();
         this.value = var5;
         return;
      default:
      }
   }

   public CertStatus(RevokedInfo var1) {
      this.tagNo = 1;
      this.value = var1;
   }

   public static CertStatus getInstance(Object var0) {
      CertStatus var1;
      if(var0 != null && !(var0 instanceof CertStatus)) {
         if(!(var0 instanceof ASN1TaggedObject)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1TaggedObject var2 = (ASN1TaggedObject)var0;
         var1 = new CertStatus(var2);
      } else {
         var1 = (CertStatus)var0;
      }

      return var1;
   }

   public static CertStatus getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public DEREncodable getStatus() {
      return this.value;
   }

   public int getTagNo() {
      return this.tagNo;
   }

   public DERObject toASN1Object() {
      int var1 = this.tagNo;
      DEREncodable var2 = this.value;
      return new DERTaggedObject((boolean)0, var1, var2);
   }
}

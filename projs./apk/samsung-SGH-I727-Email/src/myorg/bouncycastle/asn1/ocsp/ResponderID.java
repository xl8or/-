package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.X509Name;

public class ResponderID extends ASN1Encodable implements ASN1Choice {

   private DEREncodable value;


   public ResponderID(ASN1OctetString var1) {
      this.value = var1;
   }

   public ResponderID(X509Name var1) {
      this.value = var1;
   }

   public static ResponderID getInstance(Object var0) {
      ResponderID var1;
      if(var0 != null && !(var0 instanceof ResponderID)) {
         if(var0 instanceof DEROctetString) {
            DEROctetString var2 = (DEROctetString)var0;
            var1 = new ResponderID(var2);
         } else if(var0 instanceof ASN1TaggedObject) {
            ASN1TaggedObject var3 = (ASN1TaggedObject)var0;
            if(var3.getTagNo() == 1) {
               X509Name var4 = X509Name.getInstance(var3, (boolean)1);
               var1 = new ResponderID(var4);
            } else {
               ASN1OctetString var5 = ASN1OctetString.getInstance(var3, (boolean)1);
               var1 = new ResponderID(var5);
            }
         } else {
            X509Name var6 = X509Name.getInstance(var0);
            var1 = new ResponderID(var6);
         }
      } else {
         var1 = (ResponderID)var0;
      }

      return var1;
   }

   public static ResponderID getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public DERObject toASN1Object() {
      DERTaggedObject var2;
      if(this.value instanceof ASN1OctetString) {
         DEREncodable var1 = this.value;
         var2 = new DERTaggedObject((boolean)1, 2, var1);
      } else {
         DEREncodable var3 = this.value;
         var2 = new DERTaggedObject((boolean)1, 1, var3);
      }

      return var2;
   }
}

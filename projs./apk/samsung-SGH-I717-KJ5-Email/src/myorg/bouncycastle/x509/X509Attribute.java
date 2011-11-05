package myorg.bouncycastle.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.x509.Attribute;

public class X509Attribute extends ASN1Encodable {

   Attribute attr;


   public X509Attribute(String var1, ASN1Encodable var2) {
      DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
      DERSet var4 = new DERSet(var2);
      Attribute var5 = new Attribute(var3, var4);
      this.attr = var5;
   }

   public X509Attribute(String var1, ASN1EncodableVector var2) {
      DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
      DERSet var4 = new DERSet(var2);
      Attribute var5 = new Attribute(var3, var4);
      this.attr = var5;
   }

   X509Attribute(ASN1Encodable var1) {
      Attribute var2 = Attribute.getInstance(var1);
      this.attr = var2;
   }

   public String getOID() {
      return this.attr.getAttrType().getId();
   }

   public ASN1Encodable[] getValues() {
      ASN1Set var1 = this.attr.getAttrValues();
      ASN1Encodable[] var2 = new ASN1Encodable[var1.size()];
      int var3 = 0;

      while(true) {
         int var4 = var1.size();
         if(var3 == var4) {
            return var2;
         }

         ASN1Encodable var5 = (ASN1Encodable)var1.getObjectAt(var3);
         var2[var3] = var5;
         ++var3;
      }
   }

   public DERObject toASN1Object() {
      return this.attr.toASN1Object();
   }
}

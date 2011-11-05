package myorg.bouncycastle.asn1.smime;

import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEREncodableVector;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class SMIMECapabilityVector {

   private ASN1EncodableVector capabilities;


   public SMIMECapabilityVector() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      this.capabilities = var1;
   }

   public void addCapability(DERObjectIdentifier var1) {
      ASN1EncodableVector var2 = this.capabilities;
      DERSequence var3 = new DERSequence(var1);
      var2.add(var3);
   }

   public void addCapability(DERObjectIdentifier var1, int var2) {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(var1);
      DERInteger var4 = new DERInteger(var2);
      var3.add(var4);
      ASN1EncodableVector var5 = this.capabilities;
      DERSequence var6 = new DERSequence(var3);
      var5.add(var6);
   }

   public void addCapability(DERObjectIdentifier var1, DEREncodable var2) {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(var1);
      var3.add(var2);
      ASN1EncodableVector var4 = this.capabilities;
      DERSequence var5 = new DERSequence(var3);
      var4.add(var5);
   }

   public DEREncodableVector toDEREncodableVector() {
      return this.capabilities;
   }
}

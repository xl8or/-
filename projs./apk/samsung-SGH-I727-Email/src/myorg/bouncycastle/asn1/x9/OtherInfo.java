package myorg.bouncycastle.asn1.x9;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x9.KeySpecificInfo;

public class OtherInfo extends ASN1Encodable {

   private KeySpecificInfo keyInfo;
   private ASN1OctetString partyAInfo;
   private ASN1OctetString suppPubInfo;


   public OtherInfo(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      ASN1Sequence var3 = (ASN1Sequence)var2.nextElement();
      KeySpecificInfo var4 = new KeySpecificInfo(var3);
      this.keyInfo = var4;

      while(var2.hasMoreElements()) {
         DERTaggedObject var5 = (DERTaggedObject)var2.nextElement();
         if(var5.getTagNo() == 0) {
            ASN1OctetString var6 = (ASN1OctetString)var5.getObject();
            this.partyAInfo = var6;
         } else if(var5.getTagNo() == 2) {
            ASN1OctetString var7 = (ASN1OctetString)var5.getObject();
            this.suppPubInfo = var7;
         }
      }

   }

   public OtherInfo(KeySpecificInfo var1, ASN1OctetString var2, ASN1OctetString var3) {
      this.keyInfo = var1;
      this.partyAInfo = var2;
      this.suppPubInfo = var3;
   }

   public KeySpecificInfo getKeyInfo() {
      return this.keyInfo;
   }

   public ASN1OctetString getPartyAInfo() {
      return this.partyAInfo;
   }

   public ASN1OctetString getSuppPubInfo() {
      return this.suppPubInfo;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      KeySpecificInfo var2 = this.keyInfo;
      var1.add(var2);
      if(this.partyAInfo != null) {
         ASN1OctetString var3 = this.partyAInfo;
         DERTaggedObject var4 = new DERTaggedObject(0, var3);
         var1.add(var4);
      }

      ASN1OctetString var5 = this.suppPubInfo;
      DERTaggedObject var6 = new DERTaggedObject(2, var5);
      var1.add(var6);
      return new DERSequence(var1);
   }
}

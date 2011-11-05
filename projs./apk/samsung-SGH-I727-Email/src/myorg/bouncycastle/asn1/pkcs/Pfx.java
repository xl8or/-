package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.pkcs.ContentInfo;
import myorg.bouncycastle.asn1.pkcs.MacData;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class Pfx extends ASN1Encodable implements PKCSObjectIdentifiers {

   private ContentInfo contentInfo;
   private MacData macData = null;


   public Pfx(ASN1Sequence var1) {
      if(((DERInteger)var1.getObjectAt(0)).getValue().intValue() != 3) {
         throw new IllegalArgumentException("wrong version for PFX PDU");
      } else {
         ContentInfo var2 = ContentInfo.getInstance(var1.getObjectAt(1));
         this.contentInfo = var2;
         if(var1.size() == 3) {
            MacData var3 = MacData.getInstance(var1.getObjectAt(2));
            this.macData = var3;
         }
      }
   }

   public Pfx(ContentInfo var1, MacData var2) {
      this.contentInfo = var1;
      this.macData = var2;
   }

   public ContentInfo getAuthSafe() {
      return this.contentInfo;
   }

   public MacData getMacData() {
      return this.macData;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = new DERInteger(3);
      var1.add(var2);
      ContentInfo var3 = this.contentInfo;
      var1.add(var3);
      if(this.macData != null) {
         MacData var4 = this.macData;
         var1.add(var4);
      }

      return new BERSequence(var1);
   }
}

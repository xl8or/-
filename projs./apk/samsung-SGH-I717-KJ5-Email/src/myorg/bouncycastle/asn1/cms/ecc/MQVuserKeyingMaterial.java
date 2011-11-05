package myorg.bouncycastle.asn1.cms.ecc;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.OriginatorPublicKey;

public class MQVuserKeyingMaterial extends ASN1Encodable {

   private ASN1OctetString addedukm;
   private OriginatorPublicKey ephemeralPublicKey;


   private MQVuserKeyingMaterial(ASN1Sequence var1) {
      OriginatorPublicKey var2 = OriginatorPublicKey.getInstance(var1.getObjectAt(0));
      this.ephemeralPublicKey = var2;
      if(var1.size() > 1) {
         ASN1OctetString var3 = ASN1OctetString.getInstance((ASN1TaggedObject)var1.getObjectAt(1), (boolean)1);
         this.addedukm = var3;
      }
   }

   public MQVuserKeyingMaterial(OriginatorPublicKey var1, ASN1OctetString var2) {
      this.ephemeralPublicKey = var1;
      this.addedukm = var2;
   }

   public static MQVuserKeyingMaterial getInstance(Object var0) {
      MQVuserKeyingMaterial var1;
      if(var0 != null && !(var0 instanceof MQVuserKeyingMaterial)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid MQVuserKeyingMaterial: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new MQVuserKeyingMaterial(var2);
      } else {
         var1 = (MQVuserKeyingMaterial)var0;
      }

      return var1;
   }

   public static MQVuserKeyingMaterial getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1OctetString getAddedukm() {
      return this.addedukm;
   }

   public OriginatorPublicKey getEphemeralPublicKey() {
      return this.ephemeralPublicKey;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      OriginatorPublicKey var2 = this.ephemeralPublicKey;
      var1.add(var2);
      if(this.addedukm != null) {
         ASN1OctetString var3 = this.addedukm;
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, 0, var3);
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}

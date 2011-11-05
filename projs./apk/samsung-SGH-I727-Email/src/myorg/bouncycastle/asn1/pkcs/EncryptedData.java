package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedData extends ASN1Encodable {

   DERObjectIdentifier bagId;
   DERObject bagValue;
   ASN1Sequence data;


   public EncryptedData(ASN1Sequence var1) {
      if(((DERInteger)var1.getObjectAt(0)).getValue().intValue() != 0) {
         throw new IllegalArgumentException("sequence not version 0");
      } else {
         ASN1Sequence var2 = (ASN1Sequence)var1.getObjectAt(1);
         this.data = var2;
      }
   }

   public EncryptedData(DERObjectIdentifier var1, AlgorithmIdentifier var2, DEREncodable var3) {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      var4.add(var1);
      DERObject var5 = var2.getDERObject();
      var4.add(var5);
      BERTaggedObject var6 = new BERTaggedObject((boolean)0, 0, var3);
      var4.add(var6);
      BERSequence var7 = new BERSequence(var4);
      this.data = var7;
   }

   public static EncryptedData getInstance(Object var0) {
      EncryptedData var1;
      if(var0 instanceof EncryptedData) {
         var1 = (EncryptedData)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new EncryptedData(var2);
      }

      return var1;
   }

   public ASN1OctetString getContent() {
      ASN1OctetString var1;
      if(this.data.size() == 3) {
         var1 = ASN1OctetString.getInstance(((DERTaggedObject)this.data.getObjectAt(2)).getObject());
      } else {
         var1 = null;
      }

      return var1;
   }

   public DERObjectIdentifier getContentType() {
      return (DERObjectIdentifier)this.data.getObjectAt(0);
   }

   public AlgorithmIdentifier getEncryptionAlgorithm() {
      return AlgorithmIdentifier.getInstance(this.data.getObjectAt(1));
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = new DERInteger(0);
      var1.add(var2);
      ASN1Sequence var3 = this.data;
      var1.add(var3);
      return new BERSequence(var1);
   }
}

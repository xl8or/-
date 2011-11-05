package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.KEKIdentifier;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class KEKRecipientInfo extends ASN1Encodable {

   private ASN1OctetString encryptedKey;
   private KEKIdentifier kekid;
   private AlgorithmIdentifier keyEncryptionAlgorithm;
   private DERInteger version;


   public KEKRecipientInfo(ASN1Sequence var1) {
      DERInteger var2 = (DERInteger)var1.getObjectAt(0);
      this.version = var2;
      KEKIdentifier var3 = KEKIdentifier.getInstance(var1.getObjectAt(1));
      this.kekid = var3;
      AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance(var1.getObjectAt(2));
      this.keyEncryptionAlgorithm = var4;
      ASN1OctetString var5 = (ASN1OctetString)var1.getObjectAt(3);
      this.encryptedKey = var5;
   }

   public KEKRecipientInfo(KEKIdentifier var1, AlgorithmIdentifier var2, ASN1OctetString var3) {
      DERInteger var4 = new DERInteger(4);
      this.version = var4;
      this.kekid = var1;
      this.keyEncryptionAlgorithm = var2;
      this.encryptedKey = var3;
   }

   public static KEKRecipientInfo getInstance(Object var0) {
      KEKRecipientInfo var1;
      if(var0 != null && !(var0 instanceof KEKRecipientInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid KEKRecipientInfo: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new KEKRecipientInfo(var2);
      } else {
         var1 = (KEKRecipientInfo)var0;
      }

      return var1;
   }

   public static KEKRecipientInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1OctetString getEncryptedKey() {
      return this.encryptedKey;
   }

   public KEKIdentifier getKekid() {
      return this.kekid;
   }

   public AlgorithmIdentifier getKeyEncryptionAlgorithm() {
      return this.keyEncryptionAlgorithm;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      KEKIdentifier var3 = this.kekid;
      var1.add(var3);
      AlgorithmIdentifier var4 = this.keyEncryptionAlgorithm;
      var1.add(var4);
      ASN1OctetString var5 = this.encryptedKey;
      var1.add(var5);
      return new DERSequence(var1);
   }
}

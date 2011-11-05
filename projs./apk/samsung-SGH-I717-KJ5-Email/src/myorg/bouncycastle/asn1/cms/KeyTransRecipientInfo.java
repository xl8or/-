package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.RecipientIdentifier;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class KeyTransRecipientInfo extends ASN1Encodable {

   private ASN1OctetString encryptedKey;
   private AlgorithmIdentifier keyEncryptionAlgorithm;
   private RecipientIdentifier rid;
   private DERInteger version;


   public KeyTransRecipientInfo(ASN1Sequence var1) {
      DERInteger var2 = (DERInteger)var1.getObjectAt(0);
      this.version = var2;
      RecipientIdentifier var3 = RecipientIdentifier.getInstance(var1.getObjectAt(1));
      this.rid = var3;
      AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance(var1.getObjectAt(2));
      this.keyEncryptionAlgorithm = var4;
      ASN1OctetString var5 = (ASN1OctetString)var1.getObjectAt(3);
      this.encryptedKey = var5;
   }

   public KeyTransRecipientInfo(RecipientIdentifier var1, AlgorithmIdentifier var2, ASN1OctetString var3) {
      if(var1.getDERObject() instanceof ASN1TaggedObject) {
         DERInteger var4 = new DERInteger(2);
         this.version = var4;
      } else {
         DERInteger var5 = new DERInteger(0);
         this.version = var5;
      }

      this.rid = var1;
      this.keyEncryptionAlgorithm = var2;
      this.encryptedKey = var3;
   }

   public static KeyTransRecipientInfo getInstance(Object var0) {
      KeyTransRecipientInfo var1;
      if(var0 != null && !(var0 instanceof KeyTransRecipientInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Illegal object in KeyTransRecipientInfo: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new KeyTransRecipientInfo(var2);
      } else {
         var1 = (KeyTransRecipientInfo)var0;
      }

      return var1;
   }

   public ASN1OctetString getEncryptedKey() {
      return this.encryptedKey;
   }

   public AlgorithmIdentifier getKeyEncryptionAlgorithm() {
      return this.keyEncryptionAlgorithm;
   }

   public RecipientIdentifier getRecipientIdentifier() {
      return this.rid;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      RecipientIdentifier var3 = this.rid;
      var1.add(var3);
      AlgorithmIdentifier var4 = this.keyEncryptionAlgorithm;
      var1.add(var4);
      ASN1OctetString var5 = this.encryptedKey;
      var1.add(var5);
      return new DERSequence(var1);
   }
}

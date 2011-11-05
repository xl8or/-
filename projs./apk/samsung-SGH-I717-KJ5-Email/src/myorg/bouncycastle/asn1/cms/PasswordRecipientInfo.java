package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PasswordRecipientInfo extends ASN1Encodable {

   private ASN1OctetString encryptedKey;
   private AlgorithmIdentifier keyDerivationAlgorithm;
   private AlgorithmIdentifier keyEncryptionAlgorithm;
   private DERInteger version;


   public PasswordRecipientInfo(ASN1Sequence var1) {
      DERInteger var2 = (DERInteger)var1.getObjectAt(0);
      this.version = var2;
      if(var1.getObjectAt(1) instanceof ASN1TaggedObject) {
         AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance((ASN1TaggedObject)var1.getObjectAt(1), (boolean)0);
         this.keyDerivationAlgorithm = var3;
         AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance(var1.getObjectAt(2));
         this.keyEncryptionAlgorithm = var4;
         ASN1OctetString var5 = (ASN1OctetString)var1.getObjectAt(3);
         this.encryptedKey = var5;
      } else {
         AlgorithmIdentifier var6 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
         this.keyEncryptionAlgorithm = var6;
         ASN1OctetString var7 = (ASN1OctetString)var1.getObjectAt(2);
         this.encryptedKey = var7;
      }
   }

   public PasswordRecipientInfo(AlgorithmIdentifier var1, ASN1OctetString var2) {
      DERInteger var3 = new DERInteger(0);
      this.version = var3;
      this.keyEncryptionAlgorithm = var1;
      this.encryptedKey = var2;
   }

   public PasswordRecipientInfo(AlgorithmIdentifier var1, AlgorithmIdentifier var2, ASN1OctetString var3) {
      DERInteger var4 = new DERInteger(0);
      this.version = var4;
      this.keyDerivationAlgorithm = var1;
      this.keyEncryptionAlgorithm = var2;
      this.encryptedKey = var3;
   }

   public static PasswordRecipientInfo getInstance(Object var0) {
      PasswordRecipientInfo var1;
      if(var0 != null && !(var0 instanceof PasswordRecipientInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid PasswordRecipientInfo: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PasswordRecipientInfo(var2);
      } else {
         var1 = (PasswordRecipientInfo)var0;
      }

      return var1;
   }

   public static PasswordRecipientInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1OctetString getEncryptedKey() {
      return this.encryptedKey;
   }

   public AlgorithmIdentifier getKeyDerivationAlgorithm() {
      return this.keyDerivationAlgorithm;
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
      if(this.keyDerivationAlgorithm != null) {
         AlgorithmIdentifier var3 = this.keyDerivationAlgorithm;
         DERTaggedObject var4 = new DERTaggedObject((boolean)0, 0, var3);
         var1.add(var4);
      }

      AlgorithmIdentifier var5 = this.keyEncryptionAlgorithm;
      var1.add(var5);
      ASN1OctetString var6 = this.encryptedKey;
      var1.add(var6);
      return new DERSequence(var1);
   }
}

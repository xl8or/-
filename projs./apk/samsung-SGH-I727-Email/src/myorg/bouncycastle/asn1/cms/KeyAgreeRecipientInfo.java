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
import myorg.bouncycastle.asn1.cms.OriginatorIdentifierOrKey;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class KeyAgreeRecipientInfo extends ASN1Encodable {

   private AlgorithmIdentifier keyEncryptionAlgorithm;
   private OriginatorIdentifierOrKey originator;
   private ASN1Sequence recipientEncryptedKeys;
   private ASN1OctetString ukm;
   private DERInteger version;


   public KeyAgreeRecipientInfo(ASN1Sequence var1) {
      int var2 = 0 + 1;
      DERInteger var3 = (DERInteger)var1.getObjectAt(0);
      this.version = var3;
      int var4 = var2 + 1;
      OriginatorIdentifierOrKey var5 = OriginatorIdentifierOrKey.getInstance((ASN1TaggedObject)var1.getObjectAt(var2), (boolean)1);
      this.originator = var5;
      if(var1.getObjectAt(var4) instanceof ASN1TaggedObject) {
         int var6 = var4 + 1;
         ASN1OctetString var7 = ASN1OctetString.getInstance((ASN1TaggedObject)var1.getObjectAt(var4), (boolean)1);
         this.ukm = var7;
         var4 = var6;
      }

      int var8 = var4 + 1;
      AlgorithmIdentifier var9 = AlgorithmIdentifier.getInstance(var1.getObjectAt(var4));
      this.keyEncryptionAlgorithm = var9;
      int var10 = var8 + 1;
      ASN1Sequence var11 = (ASN1Sequence)var1.getObjectAt(var8);
      this.recipientEncryptedKeys = var11;
   }

   public KeyAgreeRecipientInfo(OriginatorIdentifierOrKey var1, ASN1OctetString var2, AlgorithmIdentifier var3, ASN1Sequence var4) {
      DERInteger var5 = new DERInteger(3);
      this.version = var5;
      this.originator = var1;
      this.ukm = var2;
      this.keyEncryptionAlgorithm = var3;
      this.recipientEncryptedKeys = var4;
   }

   public static KeyAgreeRecipientInfo getInstance(Object var0) {
      KeyAgreeRecipientInfo var1;
      if(var0 != null && !(var0 instanceof KeyAgreeRecipientInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Illegal object in KeyAgreeRecipientInfo: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new KeyAgreeRecipientInfo(var2);
      } else {
         var1 = (KeyAgreeRecipientInfo)var0;
      }

      return var1;
   }

   public static KeyAgreeRecipientInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AlgorithmIdentifier getKeyEncryptionAlgorithm() {
      return this.keyEncryptionAlgorithm;
   }

   public OriginatorIdentifierOrKey getOriginator() {
      return this.originator;
   }

   public ASN1Sequence getRecipientEncryptedKeys() {
      return this.recipientEncryptedKeys;
   }

   public ASN1OctetString getUserKeyingMaterial() {
      return this.ukm;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      OriginatorIdentifierOrKey var3 = this.originator;
      DERTaggedObject var4 = new DERTaggedObject((boolean)1, 0, var3);
      var1.add(var4);
      if(this.ukm != null) {
         ASN1OctetString var5 = this.ukm;
         DERTaggedObject var6 = new DERTaggedObject((boolean)1, 1, var5);
         var1.add(var6);
      }

      AlgorithmIdentifier var7 = this.keyEncryptionAlgorithm;
      var1.add(var7);
      ASN1Sequence var8 = this.recipientEncryptedKeys;
      var1.add(var8);
      return new DERSequence(var1);
   }
}

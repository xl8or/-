package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.KeyAgreeRecipientIdentifier;

public class RecipientEncryptedKey extends ASN1Encodable {

   private ASN1OctetString encryptedKey;
   private KeyAgreeRecipientIdentifier identifier;


   private RecipientEncryptedKey(ASN1Sequence var1) {
      KeyAgreeRecipientIdentifier var2 = KeyAgreeRecipientIdentifier.getInstance(var1.getObjectAt(0));
      this.identifier = var2;
      ASN1OctetString var3 = (ASN1OctetString)var1.getObjectAt(1);
      this.encryptedKey = var3;
   }

   public RecipientEncryptedKey(KeyAgreeRecipientIdentifier var1, ASN1OctetString var2) {
      this.identifier = var1;
      this.encryptedKey = var2;
   }

   public static RecipientEncryptedKey getInstance(Object var0) {
      RecipientEncryptedKey var1;
      if(var0 != null && !(var0 instanceof RecipientEncryptedKey)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid RecipientEncryptedKey: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RecipientEncryptedKey(var2);
      } else {
         var1 = (RecipientEncryptedKey)var0;
      }

      return var1;
   }

   public static RecipientEncryptedKey getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1OctetString getEncryptedKey() {
      return this.encryptedKey;
   }

   public KeyAgreeRecipientIdentifier getIdentifier() {
      return this.identifier;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      KeyAgreeRecipientIdentifier var2 = this.identifier;
      var1.add(var2);
      ASN1OctetString var3 = this.encryptedKey;
      var1.add(var3);
      return new DERSequence(var1);
   }
}

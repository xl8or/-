package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.RecipientKeyIdentifier;

public class KeyAgreeRecipientIdentifier extends ASN1Encodable implements ASN1Choice {

   private IssuerAndSerialNumber issuerSerial;
   private RecipientKeyIdentifier rKeyID;


   public KeyAgreeRecipientIdentifier(IssuerAndSerialNumber var1) {
      this.issuerSerial = var1;
      this.rKeyID = null;
   }

   public KeyAgreeRecipientIdentifier(RecipientKeyIdentifier var1) {
      this.issuerSerial = null;
      this.rKeyID = var1;
   }

   public static KeyAgreeRecipientIdentifier getInstance(Object var0) {
      KeyAgreeRecipientIdentifier var1;
      if(var0 != null && !(var0 instanceof KeyAgreeRecipientIdentifier)) {
         if(var0 instanceof ASN1Sequence) {
            IssuerAndSerialNumber var2 = IssuerAndSerialNumber.getInstance(var0);
            var1 = new KeyAgreeRecipientIdentifier(var2);
         } else {
            if(!(var0 instanceof ASN1TaggedObject) || ((ASN1TaggedObject)var0).getTagNo() != 0) {
               StringBuilder var4 = (new StringBuilder()).append("Invalid KeyAgreeRecipientIdentifier: ");
               String var5 = var0.getClass().getName();
               String var6 = var4.append(var5).toString();
               throw new IllegalArgumentException(var6);
            }

            RecipientKeyIdentifier var3 = RecipientKeyIdentifier.getInstance((ASN1TaggedObject)var0, (boolean)0);
            var1 = new KeyAgreeRecipientIdentifier(var3);
         }
      } else {
         var1 = (KeyAgreeRecipientIdentifier)var0;
      }

      return var1;
   }

   public static KeyAgreeRecipientIdentifier getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public IssuerAndSerialNumber getIssuerAndSerialNumber() {
      return this.issuerSerial;
   }

   public RecipientKeyIdentifier getRKeyID() {
      return this.rKeyID;
   }

   public DERObject toASN1Object() {
      Object var1;
      if(this.issuerSerial != null) {
         var1 = this.issuerSerial.toASN1Object();
      } else {
         RecipientKeyIdentifier var2 = this.rKeyID;
         var1 = new DERTaggedObject((boolean)0, 0, var2);
      }

      return (DERObject)var1;
   }
}

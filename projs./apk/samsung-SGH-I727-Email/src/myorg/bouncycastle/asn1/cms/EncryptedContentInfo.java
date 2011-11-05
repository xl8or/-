package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedContentInfo extends ASN1Encodable {

   private AlgorithmIdentifier contentEncryptionAlgorithm;
   private DERObjectIdentifier contentType;
   private ASN1OctetString encryptedContent;


   public EncryptedContentInfo(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.contentType = var2;
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
      this.contentEncryptionAlgorithm = var3;
      if(var1.size() > 2) {
         ASN1OctetString var4 = ASN1OctetString.getInstance((ASN1TaggedObject)var1.getObjectAt(2), (boolean)0);
         this.encryptedContent = var4;
      }
   }

   public EncryptedContentInfo(DERObjectIdentifier var1, AlgorithmIdentifier var2, ASN1OctetString var3) {
      this.contentType = var1;
      this.contentEncryptionAlgorithm = var2;
      this.encryptedContent = var3;
   }

   public static EncryptedContentInfo getInstance(Object var0) {
      EncryptedContentInfo var1;
      if(var0 != null && !(var0 instanceof EncryptedContentInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid EncryptedContentInfo: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new EncryptedContentInfo(var2);
      } else {
         var1 = (EncryptedContentInfo)var0;
      }

      return var1;
   }

   public AlgorithmIdentifier getContentEncryptionAlgorithm() {
      return this.contentEncryptionAlgorithm;
   }

   public DERObjectIdentifier getContentType() {
      return this.contentType;
   }

   public ASN1OctetString getEncryptedContent() {
      return this.encryptedContent;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.contentType;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.contentEncryptionAlgorithm;
      var1.add(var3);
      if(this.encryptedContent != null) {
         ASN1OctetString var4 = this.encryptedContent;
         BERTaggedObject var5 = new BERTaggedObject((boolean)0, 0, var4);
         var1.add(var5);
      }

      return new BERSequence(var1);
   }
}

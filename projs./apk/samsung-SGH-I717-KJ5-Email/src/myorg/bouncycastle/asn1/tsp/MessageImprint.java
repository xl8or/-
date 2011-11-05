package myorg.bouncycastle.asn1.tsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class MessageImprint extends ASN1Encodable {

   AlgorithmIdentifier hashAlgorithm;
   byte[] hashedMessage;


   public MessageImprint(ASN1Sequence var1) {
      AlgorithmIdentifier var2 = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
      this.hashAlgorithm = var2;
      byte[] var3 = ASN1OctetString.getInstance(var1.getObjectAt(1)).getOctets();
      this.hashedMessage = var3;
   }

   public MessageImprint(AlgorithmIdentifier var1, byte[] var2) {
      this.hashAlgorithm = var1;
      this.hashedMessage = var2;
   }

   public static MessageImprint getInstance(Object var0) {
      MessageImprint var1;
      if(var0 != null && !(var0 instanceof MessageImprint)) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("Bad object in factory.");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new MessageImprint(var2);
      } else {
         var1 = (MessageImprint)var0;
      }

      return var1;
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public byte[] getHashedMessage() {
      return this.hashedMessage;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.hashAlgorithm;
      var1.add(var2);
      byte[] var3 = this.hashedMessage;
      DEROctetString var4 = new DEROctetString(var3);
      var1.add(var4);
      return new DERSequence(var1);
   }
}

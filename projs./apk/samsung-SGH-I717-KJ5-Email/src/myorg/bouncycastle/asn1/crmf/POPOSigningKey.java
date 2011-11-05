package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.crmf.POPOSigningKeyInput;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class POPOSigningKey extends ASN1Encodable {

   private AlgorithmIdentifier algorithmIdentifier;
   private POPOSigningKeyInput poposkInput;
   private DERBitString signature;


   private POPOSigningKey(ASN1Sequence var1) {
      int var2 = 0;
      if(var1.getObjectAt(0) instanceof ASN1TaggedObject) {
         int var3 = 0 + 1;
         POPOSigningKeyInput var4 = POPOSigningKeyInput.getInstance(var1.getObjectAt(0));
         this.poposkInput = var4;
         var2 = var3;
      }

      int var5 = var2 + 1;
      AlgorithmIdentifier var6 = AlgorithmIdentifier.getInstance(var1.getObjectAt(var2));
      this.algorithmIdentifier = var6;
      DERBitString var7 = DERBitString.getInstance(var1.getObjectAt(var5));
      this.signature = var7;
   }

   public static POPOSigningKey getInstance(Object var0) {
      POPOSigningKey var1;
      if(var0 instanceof POPOSigningKey) {
         var1 = (POPOSigningKey)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new POPOSigningKey(var2);
      }

      return var1;
   }

   public static POPOSigningKey getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.poposkInput != null) {
         POPOSigningKeyInput var2 = this.poposkInput;
         var1.add(var2);
      }

      AlgorithmIdentifier var3 = this.algorithmIdentifier;
      var1.add(var3);
      DERBitString var4 = this.signature;
      var1.add(var4);
      return new DERSequence(var1);
   }
}

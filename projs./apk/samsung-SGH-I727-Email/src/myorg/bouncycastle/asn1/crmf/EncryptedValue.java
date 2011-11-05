package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedValue extends ASN1Encodable {

   private DERBitString encSymmKey;
   private DERBitString encValue;
   private AlgorithmIdentifier intendedAlg;
   private AlgorithmIdentifier keyAlg;
   private AlgorithmIdentifier symmAlg;
   private ASN1OctetString valueHint;


   private EncryptedValue(ASN1Sequence var1) {
      int var2;
      for(var2 = 0; var1.getObjectAt(var2) instanceof ASN1TaggedObject; ++var2) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var1.getObjectAt(var2);
         switch(var3.getTagNo()) {
         case 0:
            AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance(var3, (boolean)0);
            this.intendedAlg = var4;
            break;
         case 1:
            AlgorithmIdentifier var5 = AlgorithmIdentifier.getInstance(var3, (boolean)0);
            this.symmAlg = var5;
            break;
         case 2:
            DERBitString var6 = DERBitString.getInstance(var3, (boolean)0);
            this.encSymmKey = var6;
            break;
         case 3:
            AlgorithmIdentifier var7 = AlgorithmIdentifier.getInstance(var3, (boolean)0);
            this.keyAlg = var7;
            break;
         case 4:
            ASN1OctetString var8 = ASN1OctetString.getInstance(var3, (boolean)0);
            this.valueHint = var8;
         }
      }

      DERBitString var9 = DERBitString.getInstance(var1.getObjectAt(var2));
      this.encValue = var9;
   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if(var3 != null) {
         DERTaggedObject var4 = new DERTaggedObject((boolean)0, var2, var3);
         var1.add(var4);
      }
   }

   public static EncryptedValue getInstance(Object var0) {
      EncryptedValue var1;
      if(var0 instanceof EncryptedValue) {
         var1 = (EncryptedValue)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new EncryptedValue(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.intendedAlg;
      this.addOptional(var1, 0, var2);
      AlgorithmIdentifier var3 = this.symmAlg;
      this.addOptional(var1, 1, var3);
      DERBitString var4 = this.encSymmKey;
      this.addOptional(var1, 2, var4);
      AlgorithmIdentifier var5 = this.keyAlg;
      this.addOptional(var1, 3, var5);
      ASN1OctetString var6 = this.valueHint;
      this.addOptional(var1, 4, var6);
      DERBitString var7 = this.encValue;
      var1.add(var7);
      return new DERSequence(var1);
   }
}

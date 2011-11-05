package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class AlgorithmIdentifier extends ASN1Encodable {

   private DERObjectIdentifier objectId;
   private DEREncodable parameters;
   private boolean parametersDefined = 0;


   public AlgorithmIdentifier(String var1) {
      DERObjectIdentifier var2 = new DERObjectIdentifier(var1);
      this.objectId = var2;
   }

   public AlgorithmIdentifier(ASN1Sequence var1) {
      if(var1.size() >= 1 && var1.size() <= 2) {
         DERObjectIdentifier var5 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
         this.objectId = var5;
         if(var1.size() == 2) {
            this.parametersDefined = (boolean)1;
            DEREncodable var6 = var1.getObjectAt(1);
            this.parameters = var6;
         } else {
            this.parameters = null;
         }
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public AlgorithmIdentifier(DERObjectIdentifier var1) {
      this.objectId = var1;
   }

   public AlgorithmIdentifier(DERObjectIdentifier var1, DEREncodable var2) {
      this.parametersDefined = (boolean)1;
      this.objectId = var1;
      this.parameters = var2;
   }

   public static AlgorithmIdentifier getInstance(Object var0) {
      AlgorithmIdentifier var1;
      if(var0 != null && !(var0 instanceof AlgorithmIdentifier)) {
         if(var0 instanceof DERObjectIdentifier) {
            DERObjectIdentifier var2 = (DERObjectIdentifier)var0;
            var1 = new AlgorithmIdentifier(var2);
         } else if(var0 instanceof String) {
            String var3 = (String)var0;
            var1 = new AlgorithmIdentifier(var3);
         } else {
            if(!(var0 instanceof ASN1Sequence)) {
               StringBuilder var5 = (new StringBuilder()).append("unknown object in factory: ");
               String var6 = var0.getClass().getName();
               String var7 = var5.append(var6).toString();
               throw new IllegalArgumentException(var7);
            }

            ASN1Sequence var4 = (ASN1Sequence)var0;
            var1 = new AlgorithmIdentifier(var4);
         }
      } else {
         var1 = (AlgorithmIdentifier)var0;
      }

      return var1;
   }

   public static AlgorithmIdentifier getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERObjectIdentifier getObjectId() {
      return this.objectId;
   }

   public DEREncodable getParameters() {
      return this.parameters;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.objectId;
      var1.add(var2);
      if(this.parametersDefined) {
         DEREncodable var3 = this.parameters;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}

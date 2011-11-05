package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class TBSRequest extends ASN1Encodable {

   private static final DERInteger V1 = new DERInteger(0);
   X509Extensions requestExtensions;
   ASN1Sequence requestList;
   GeneralName requestorName;
   DERInteger version;
   boolean versionSet;


   public TBSRequest(ASN1Sequence var1) {
      int var2 = 0;
      if(var1.getObjectAt(0) instanceof ASN1TaggedObject) {
         if(((ASN1TaggedObject)var1.getObjectAt(0)).getTagNo() == 0) {
            this.versionSet = (boolean)1;
            DERInteger var3 = DERInteger.getInstance((ASN1TaggedObject)var1.getObjectAt(0), (boolean)1);
            this.version = var3;
            var2 = 0 + 1;
         } else {
            DERInteger var11 = V1;
            this.version = var11;
         }
      } else {
         DERInteger var12 = V1;
         this.version = var12;
      }

      if(var1.getObjectAt(var2) instanceof ASN1TaggedObject) {
         int var4 = var2 + 1;
         GeneralName var5 = GeneralName.getInstance((ASN1TaggedObject)var1.getObjectAt(var2), (boolean)1);
         this.requestorName = var5;
         var2 = var4;
      }

      int var6 = var2 + 1;
      ASN1Sequence var7 = (ASN1Sequence)var1.getObjectAt(var2);
      this.requestList = var7;
      int var8 = var1.size();
      int var9 = var6 + 1;
      if(var8 == var9) {
         X509Extensions var10 = X509Extensions.getInstance((ASN1TaggedObject)var1.getObjectAt(var6), (boolean)1);
         this.requestExtensions = var10;
      }
   }

   public TBSRequest(GeneralName var1, ASN1Sequence var2, X509Extensions var3) {
      DERInteger var4 = V1;
      this.version = var4;
      this.requestorName = var1;
      this.requestList = var2;
      this.requestExtensions = var3;
   }

   public static TBSRequest getInstance(Object var0) {
      TBSRequest var1;
      if(var0 != null && !(var0 instanceof TBSRequest)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new TBSRequest(var2);
      } else {
         var1 = (TBSRequest)var0;
      }

      return var1;
   }

   public static TBSRequest getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public X509Extensions getRequestExtensions() {
      return this.requestExtensions;
   }

   public ASN1Sequence getRequestList() {
      return this.requestList;
   }

   public GeneralName getRequestorName() {
      return this.requestorName;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      DERInteger var3 = V1;
      if(!var2.equals(var3) || this.versionSet) {
         DERInteger var4 = this.version;
         DERTaggedObject var5 = new DERTaggedObject((boolean)1, 0, var4);
         var1.add(var5);
      }

      if(this.requestorName != null) {
         GeneralName var6 = this.requestorName;
         DERTaggedObject var7 = new DERTaggedObject((boolean)1, 1, var6);
         var1.add(var7);
      }

      ASN1Sequence var8 = this.requestList;
      var1.add(var8);
      if(this.requestExtensions != null) {
         X509Extensions var9 = this.requestExtensions;
         DERTaggedObject var10 = new DERTaggedObject((boolean)1, 2, var9);
         var1.add(var10);
      }

      return new DERSequence(var1);
   }
}

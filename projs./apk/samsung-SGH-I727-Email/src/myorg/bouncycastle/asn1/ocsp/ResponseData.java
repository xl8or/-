package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.ocsp.ResponderID;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class ResponseData extends ASN1Encodable {

   private static final DERInteger V1 = new DERInteger(0);
   private DERGeneralizedTime producedAt;
   private ResponderID responderID;
   private X509Extensions responseExtensions;
   private ASN1Sequence responses;
   private DERInteger version;
   private boolean versionPresent;


   public ResponseData(ASN1Sequence var1) {
      int var2 = 0;
      if(var1.getObjectAt(0) instanceof ASN1TaggedObject) {
         if(((ASN1TaggedObject)var1.getObjectAt(0)).getTagNo() == 0) {
            this.versionPresent = (boolean)1;
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

      int var4 = var2 + 1;
      ResponderID var5 = ResponderID.getInstance(var1.getObjectAt(var2));
      this.responderID = var5;
      int var6 = var4 + 1;
      DERGeneralizedTime var7 = (DERGeneralizedTime)var1.getObjectAt(var4);
      this.producedAt = var7;
      int var8 = var6 + 1;
      ASN1Sequence var9 = (ASN1Sequence)var1.getObjectAt(var6);
      this.responses = var9;
      if(var1.size() > var8) {
         X509Extensions var10 = X509Extensions.getInstance((ASN1TaggedObject)var1.getObjectAt(var8), (boolean)1);
         this.responseExtensions = var10;
      }
   }

   public ResponseData(DERInteger var1, ResponderID var2, DERGeneralizedTime var3, ASN1Sequence var4, X509Extensions var5) {
      this.version = var1;
      this.responderID = var2;
      this.producedAt = var3;
      this.responses = var4;
      this.responseExtensions = var5;
   }

   public ResponseData(ResponderID var1, DERGeneralizedTime var2, ASN1Sequence var3, X509Extensions var4) {
      DERInteger var5 = V1;
      this(var5, var1, var2, var3, var4);
   }

   public static ResponseData getInstance(Object var0) {
      ResponseData var1;
      if(var0 != null && !(var0 instanceof ResponseData)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ResponseData(var2);
      } else {
         var1 = (ResponseData)var0;
      }

      return var1;
   }

   public static ResponseData getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERGeneralizedTime getProducedAt() {
      return this.producedAt;
   }

   public ResponderID getResponderID() {
      return this.responderID;
   }

   public X509Extensions getResponseExtensions() {
      return this.responseExtensions;
   }

   public ASN1Sequence getResponses() {
      return this.responses;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1;
      label15: {
         var1 = new ASN1EncodableVector();
         if(!this.versionPresent) {
            DERInteger var2 = this.version;
            DERInteger var3 = V1;
            if(var2.equals(var3)) {
               break label15;
            }
         }

         DERInteger var4 = this.version;
         DERTaggedObject var5 = new DERTaggedObject((boolean)1, 0, var4);
         var1.add(var5);
      }

      ResponderID var6 = this.responderID;
      var1.add(var6);
      DERGeneralizedTime var7 = this.producedAt;
      var1.add(var7);
      ASN1Sequence var8 = this.responses;
      var1.add(var8);
      if(this.responseExtensions != null) {
         X509Extensions var9 = this.responseExtensions;
         DERTaggedObject var10 = new DERTaggedObject((boolean)1, 1, var9);
         var1.add(var10);
      }

      return new DERSequence(var1);
   }
}

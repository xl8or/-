package myorg.bouncycastle.asn1.tsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.tsp.MessageImprint;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class TimeStampReq extends ASN1Encodable {

   DERBoolean certReq;
   X509Extensions extensions;
   MessageImprint messageImprint;
   DERInteger nonce;
   DERObjectIdentifier tsaPolicy;
   DERInteger version;


   public TimeStampReq(ASN1Sequence var1) {
      int var2 = var1.size();
      DERInteger var3 = DERInteger.getInstance(var1.getObjectAt(0));
      this.version = var3;
      int var4 = 0 + 1;
      MessageImprint var5 = MessageImprint.getInstance(var1.getObjectAt(var4));
      this.messageImprint = var5;

      for(int var6 = var4 + 1; var6 < var2; ++var6) {
         if(var1.getObjectAt(var6) instanceof DERObjectIdentifier) {
            DERObjectIdentifier var7 = DERObjectIdentifier.getInstance(var1.getObjectAt(var6));
            this.tsaPolicy = var7;
         } else if(var1.getObjectAt(var6) instanceof DERInteger) {
            DERInteger var8 = DERInteger.getInstance(var1.getObjectAt(var6));
            this.nonce = var8;
         } else if(var1.getObjectAt(var6) instanceof DERBoolean) {
            DERBoolean var9 = DERBoolean.getInstance(var1.getObjectAt(var6));
            this.certReq = var9;
         } else if(var1.getObjectAt(var6) instanceof ASN1TaggedObject) {
            ASN1TaggedObject var10 = (ASN1TaggedObject)var1.getObjectAt(var6);
            if(var10.getTagNo() == 0) {
               X509Extensions var11 = X509Extensions.getInstance(var10, (boolean)0);
               this.extensions = var11;
            }
         }
      }

   }

   public TimeStampReq(MessageImprint var1, DERObjectIdentifier var2, DERInteger var3, DERBoolean var4, X509Extensions var5) {
      DERInteger var6 = new DERInteger(1);
      this.version = var6;
      this.messageImprint = var1;
      this.tsaPolicy = var2;
      this.nonce = var3;
      this.certReq = var4;
      this.extensions = var5;
   }

   public static TimeStampReq getInstance(Object var0) {
      TimeStampReq var1;
      if(var0 != null && !(var0 instanceof TimeStampReq)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Unknown object in \'TimeStampReq\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new TimeStampReq(var2);
      } else {
         var1 = (TimeStampReq)var0;
      }

      return var1;
   }

   public DERBoolean getCertReq() {
      return this.certReq;
   }

   public X509Extensions getExtensions() {
      return this.extensions;
   }

   public MessageImprint getMessageImprint() {
      return this.messageImprint;
   }

   public DERInteger getNonce() {
      return this.nonce;
   }

   public DERObjectIdentifier getReqPolicy() {
      return this.tsaPolicy;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      MessageImprint var3 = this.messageImprint;
      var1.add(var3);
      if(this.tsaPolicy != null) {
         DERObjectIdentifier var4 = this.tsaPolicy;
         var1.add(var4);
      }

      if(this.nonce != null) {
         DERInteger var5 = this.nonce;
         var1.add(var5);
      }

      if(this.certReq != null && this.certReq.isTrue()) {
         DERBoolean var6 = this.certReq;
         var1.add(var6);
      }

      if(this.extensions != null) {
         X509Extensions var7 = this.extensions;
         DERTaggedObject var8 = new DERTaggedObject((boolean)0, 0, var7);
         var1.add(var8);
      }

      return new DERSequence(var1);
   }
}

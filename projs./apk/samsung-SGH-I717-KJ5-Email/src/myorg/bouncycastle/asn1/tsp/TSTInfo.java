package myorg.bouncycastle.asn1.tsp;

import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.tsp.Accuracy;
import myorg.bouncycastle.asn1.tsp.MessageImprint;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class TSTInfo extends ASN1Encodable {

   Accuracy accuracy;
   X509Extensions extensions;
   DERGeneralizedTime genTime;
   MessageImprint messageImprint;
   DERInteger nonce;
   DERBoolean ordering;
   DERInteger serialNumber;
   GeneralName tsa;
   DERObjectIdentifier tsaPolicyId;
   DERInteger version;


   public TSTInfo(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERInteger var3 = DERInteger.getInstance(var2.nextElement());
      this.version = var3;
      DERObjectIdentifier var4 = DERObjectIdentifier.getInstance(var2.nextElement());
      this.tsaPolicyId = var4;
      MessageImprint var5 = MessageImprint.getInstance(var2.nextElement());
      this.messageImprint = var5;
      DERInteger var6 = DERInteger.getInstance(var2.nextElement());
      this.serialNumber = var6;
      DERGeneralizedTime var7 = DERGeneralizedTime.getInstance(var2.nextElement());
      this.genTime = var7;
      DERBoolean var8 = new DERBoolean((boolean)0);
      this.ordering = var8;

      while(var2.hasMoreElements()) {
         DERObject var9 = (DERObject)var2.nextElement();
         if(var9 instanceof ASN1TaggedObject) {
            DERTaggedObject var10 = (DERTaggedObject)var9;
            switch(var10.getTagNo()) {
            case 0:
               GeneralName var14 = GeneralName.getInstance(var10, (boolean)1);
               this.tsa = var14;
               break;
            case 1:
               X509Extensions var15 = X509Extensions.getInstance(var10, (boolean)0);
               this.extensions = var15;
               break;
            default:
               StringBuilder var11 = (new StringBuilder()).append("Unknown tag value ");
               int var12 = var10.getTagNo();
               String var13 = var11.append(var12).toString();
               throw new IllegalArgumentException(var13);
            }
         } else if(var9 instanceof DERSequence) {
            Accuracy var16 = Accuracy.getInstance(var9);
            this.accuracy = var16;
         } else if(var9 instanceof DERBoolean) {
            DERBoolean var17 = DERBoolean.getInstance(var9);
            this.ordering = var17;
         } else if(var9 instanceof DERInteger) {
            DERInteger var18 = DERInteger.getInstance(var9);
            this.nonce = var18;
         }
      }

   }

   public TSTInfo(DERObjectIdentifier var1, MessageImprint var2, DERInteger var3, DERGeneralizedTime var4, Accuracy var5, DERBoolean var6, DERInteger var7, GeneralName var8, X509Extensions var9) {
      DERInteger var10 = new DERInteger(1);
      this.version = var10;
      this.tsaPolicyId = var1;
      this.messageImprint = var2;
      this.serialNumber = var3;
      this.genTime = var4;
      this.accuracy = var5;
      this.ordering = var6;
      this.nonce = var7;
      this.tsa = var8;
      this.extensions = var9;
   }

   public static TSTInfo getInstance(Object var0) {
      TSTInfo var1;
      if(var0 != null && !(var0 instanceof TSTInfo)) {
         if(var0 instanceof ASN1Sequence) {
            ASN1Sequence var2 = (ASN1Sequence)var0;
            var1 = new TSTInfo(var2);
         } else {
            if(!(var0 instanceof ASN1OctetString)) {
               StringBuilder var6 = (new StringBuilder()).append("Unknown object in \'TSTInfo\' factory : ");
               String var7 = var0.getClass().getName();
               String var8 = var6.append(var7).append(".").toString();
               throw new IllegalArgumentException(var8);
            }

            TSTInfo var4;
            try {
               byte[] var3 = ((ASN1OctetString)var0).getOctets();
               var4 = getInstance((new ASN1InputStream(var3)).readObject());
            } catch (IOException var9) {
               throw new IllegalArgumentException("Bad object format in \'TSTInfo\' factory.");
            }

            var1 = var4;
         }
      } else {
         var1 = (TSTInfo)var0;
      }

      return var1;
   }

   public Accuracy getAccuracy() {
      return this.accuracy;
   }

   public X509Extensions getExtensions() {
      return this.extensions;
   }

   public DERGeneralizedTime getGenTime() {
      return this.genTime;
   }

   public MessageImprint getMessageImprint() {
      return this.messageImprint;
   }

   public DERInteger getNonce() {
      return this.nonce;
   }

   public DERBoolean getOrdering() {
      return this.ordering;
   }

   public DERObjectIdentifier getPolicy() {
      return this.tsaPolicyId;
   }

   public DERInteger getSerialNumber() {
      return this.serialNumber;
   }

   public GeneralName getTsa() {
      return this.tsa;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      DERObjectIdentifier var3 = this.tsaPolicyId;
      var1.add(var3);
      MessageImprint var4 = this.messageImprint;
      var1.add(var4);
      DERInteger var5 = this.serialNumber;
      var1.add(var5);
      DERGeneralizedTime var6 = this.genTime;
      var1.add(var6);
      if(this.accuracy != null) {
         Accuracy var7 = this.accuracy;
         var1.add(var7);
      }

      if(this.ordering != null && this.ordering.isTrue()) {
         DERBoolean var8 = this.ordering;
         var1.add(var8);
      }

      if(this.nonce != null) {
         DERInteger var9 = this.nonce;
         var1.add(var9);
      }

      if(this.tsa != null) {
         GeneralName var10 = this.tsa;
         DERTaggedObject var11 = new DERTaggedObject((boolean)1, 0, var10);
         var1.add(var11);
      }

      if(this.extensions != null) {
         X509Extensions var12 = this.extensions;
         DERTaggedObject var13 = new DERTaggedObject((boolean)0, 1, var12);
         var1.add(var13);
      }

      return new DERSequence(var1);
   }
}

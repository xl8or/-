package myorg.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cmp.PKIFreeText;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class PKIHeader extends ASN1Encodable {

   private PKIFreeText freeText;
   private ASN1Sequence generalInfo;
   private DERGeneralizedTime messageTime;
   private AlgorithmIdentifier protectionAlg;
   private DERInteger pvno;
   private ASN1OctetString recipKID;
   private ASN1OctetString recipNonce;
   private GeneralName recipient;
   private GeneralName sender;
   private ASN1OctetString senderKID;
   private ASN1OctetString senderNonce;
   private ASN1OctetString transactionID;


   private PKIHeader(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERInteger var3 = DERInteger.getInstance(var2.nextElement());
      this.pvno = var3;
      GeneralName var4 = GeneralName.getInstance(var2.nextElement());
      this.sender = var4;
      GeneralName var5 = GeneralName.getInstance(var2.nextElement());
      this.recipient = var5;

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var6 = (ASN1TaggedObject)var2.nextElement();
         switch(var6.getTagNo()) {
         case 0:
            DERGeneralizedTime var10 = DERGeneralizedTime.getInstance(var6, (boolean)1);
            this.messageTime = var10;
            break;
         case 1:
            AlgorithmIdentifier var11 = AlgorithmIdentifier.getInstance(var6, (boolean)1);
            this.protectionAlg = var11;
            break;
         case 2:
            ASN1OctetString var12 = ASN1OctetString.getInstance(var6, (boolean)1);
            this.senderKID = var12;
            break;
         case 3:
            ASN1OctetString var13 = ASN1OctetString.getInstance(var6, (boolean)1);
            this.recipKID = var13;
            break;
         case 4:
            ASN1OctetString var14 = ASN1OctetString.getInstance(var6, (boolean)1);
            this.transactionID = var14;
            break;
         case 5:
            ASN1OctetString var15 = ASN1OctetString.getInstance(var6, (boolean)1);
            this.senderNonce = var15;
            break;
         case 6:
            ASN1OctetString var16 = ASN1OctetString.getInstance(var6, (boolean)1);
            this.recipNonce = var16;
            break;
         case 7:
            PKIFreeText var17 = PKIFreeText.getInstance(var6, (boolean)1);
            this.freeText = var17;
            break;
         case 8:
            ASN1Sequence var18 = ASN1Sequence.getInstance(var6, (boolean)1);
            this.generalInfo = var18;
            break;
         default:
            StringBuilder var7 = (new StringBuilder()).append("unknown tag number: ");
            int var8 = var6.getTagNo();
            String var9 = var7.append(var8).toString();
            throw new IllegalArgumentException(var9);
         }
      }

   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if(var3 != null) {
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, var2, var3);
         var1.add(var4);
      }
   }

   public static PKIHeader getInstance(Object var0) {
      PKIHeader var1;
      if(var0 instanceof PKIHeader) {
         var1 = (PKIHeader)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PKIHeader(var2);
      }

      return var1;
   }

   public DERInteger getPvno() {
      return this.pvno;
   }

   public GeneralName getRecipient() {
      return this.recipient;
   }

   public GeneralName getSender() {
      return this.sender;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.pvno;
      var1.add(var2);
      GeneralName var3 = this.sender;
      var1.add(var3);
      GeneralName var4 = this.recipient;
      var1.add(var4);
      DERGeneralizedTime var5 = this.messageTime;
      this.addOptional(var1, 0, var5);
      AlgorithmIdentifier var6 = this.protectionAlg;
      this.addOptional(var1, 1, var6);
      ASN1OctetString var7 = this.senderKID;
      this.addOptional(var1, 2, var7);
      ASN1OctetString var8 = this.recipKID;
      this.addOptional(var1, 3, var8);
      ASN1OctetString var9 = this.transactionID;
      this.addOptional(var1, 4, var9);
      ASN1OctetString var10 = this.senderNonce;
      this.addOptional(var1, 5, var10);
      ASN1OctetString var11 = this.recipNonce;
      this.addOptional(var1, 6, var11);
      PKIFreeText var12 = this.freeText;
      this.addOptional(var1, 7, var12);
      ASN1Sequence var13 = this.generalInfo;
      this.addOptional(var1, 8, var13);
      return new DERSequence(var1);
   }
}

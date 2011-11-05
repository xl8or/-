package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.EncryptedContentInfo;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;

public class AuthEnvelopedData extends ASN1Encodable {

   private ASN1Set authAttrs;
   private EncryptedContentInfo authEncryptedContentInfo;
   private ASN1OctetString mac;
   private OriginatorInfo originatorInfo;
   private ASN1Set recipientInfos;
   private ASN1Set unauthAttrs;
   private DERInteger version;


   public AuthEnvelopedData(ASN1Sequence var1) {
      int var2 = 0 + 1;
      DERInteger var3 = (DERInteger)var1.getObjectAt(0).getDERObject();
      this.version = var3;
      int var4 = var2 + 1;
      DERObject var5 = var1.getObjectAt(var2).getDERObject();
      if(var5 instanceof ASN1TaggedObject) {
         OriginatorInfo var6 = OriginatorInfo.getInstance((ASN1TaggedObject)var5, (boolean)0);
         this.originatorInfo = var6;
         int var7 = var4 + 1;
         var5 = var1.getObjectAt(var4).getDERObject();
         var4 = var7;
      }

      ASN1Set var8 = ASN1Set.getInstance(var5);
      this.recipientInfos = var8;
      int var9 = var4 + 1;
      EncryptedContentInfo var10 = EncryptedContentInfo.getInstance(var1.getObjectAt(var4).getDERObject());
      this.authEncryptedContentInfo = var10;
      int var11 = var9 + 1;
      DERObject var12 = var1.getObjectAt(var9).getDERObject();
      if(var12 instanceof ASN1TaggedObject) {
         ASN1Set var13 = ASN1Set.getInstance((ASN1TaggedObject)var12, (boolean)0);
         this.authAttrs = var13;
         int var14 = var11 + 1;
         var12 = var1.getObjectAt(var11).getDERObject();
         var11 = var14;
      }

      ASN1OctetString var15 = ASN1OctetString.getInstance(var12);
      this.mac = var15;
      if(var1.size() > var11) {
         int var16 = var11 + 1;
         ASN1Set var17 = ASN1Set.getInstance((ASN1TaggedObject)var1.getObjectAt(var11).getDERObject(), (boolean)0);
         this.unauthAttrs = var17;
      }
   }

   public AuthEnvelopedData(OriginatorInfo var1, ASN1Set var2, EncryptedContentInfo var3, ASN1Set var4, ASN1OctetString var5, ASN1Set var6) {
      DERInteger var7 = new DERInteger(0);
      this.version = var7;
      this.originatorInfo = var1;
      this.recipientInfos = var2;
      this.authEncryptedContentInfo = var3;
      this.authAttrs = var4;
      this.mac = var5;
      this.unauthAttrs = var6;
   }

   public static AuthEnvelopedData getInstance(Object var0) {
      AuthEnvelopedData var1;
      if(var0 != null && !(var0 instanceof AuthEnvelopedData)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid AuthEnvelopedData: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AuthEnvelopedData(var2);
      } else {
         var1 = (AuthEnvelopedData)var0;
      }

      return var1;
   }

   public static AuthEnvelopedData getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1Set getAuthAttrs() {
      return this.authAttrs;
   }

   public EncryptedContentInfo getAuthEncryptedContentInfo() {
      return this.authEncryptedContentInfo;
   }

   public ASN1OctetString getMac() {
      return this.mac;
   }

   public OriginatorInfo getOriginatorInfo() {
      return this.originatorInfo;
   }

   public ASN1Set getRecipientInfos() {
      return this.recipientInfos;
   }

   public ASN1Set getUnauthAttrs() {
      return this.unauthAttrs;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      if(this.originatorInfo != null) {
         OriginatorInfo var3 = this.originatorInfo;
         DERTaggedObject var4 = new DERTaggedObject((boolean)0, 0, var3);
         var1.add(var4);
      }

      ASN1Set var5 = this.recipientInfos;
      var1.add(var5);
      EncryptedContentInfo var6 = this.authEncryptedContentInfo;
      var1.add(var6);
      if(this.authAttrs != null) {
         ASN1Set var7 = this.authAttrs;
         DERTaggedObject var8 = new DERTaggedObject((boolean)0, 1, var7);
         var1.add(var8);
      }

      ASN1OctetString var9 = this.mac;
      var1.add(var9);
      if(this.unauthAttrs != null) {
         ASN1Set var10 = this.unauthAttrs;
         DERTaggedObject var11 = new DERTaggedObject((boolean)0, 2, var10);
         var1.add(var11);
      }

      return new BERSequence(var1);
   }
}

package myorg.bouncycastle.asn1.cms;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.EncryptedContentInfo;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;
import myorg.bouncycastle.asn1.cms.RecipientInfo;

public class EnvelopedData extends ASN1Encodable {

   private EncryptedContentInfo encryptedContentInfo;
   private OriginatorInfo originatorInfo;
   private ASN1Set recipientInfos;
   private ASN1Set unprotectedAttrs;
   private DERInteger version;


   public EnvelopedData(ASN1Sequence var1) {
      int var2 = 0 + 1;
      DERInteger var3 = (DERInteger)var1.getObjectAt(0);
      this.version = var3;
      int var4 = var2 + 1;
      DEREncodable var5 = var1.getObjectAt(var2);
      if(var5 instanceof ASN1TaggedObject) {
         OriginatorInfo var6 = OriginatorInfo.getInstance((ASN1TaggedObject)var5, (boolean)0);
         this.originatorInfo = var6;
         int var7 = var4 + 1;
         var5 = var1.getObjectAt(var4);
         var4 = var7;
      }

      ASN1Set var8 = ASN1Set.getInstance(var5);
      this.recipientInfos = var8;
      int var9 = var4 + 1;
      EncryptedContentInfo var10 = EncryptedContentInfo.getInstance(var1.getObjectAt(var4));
      this.encryptedContentInfo = var10;
      if(var1.size() > var9) {
         ASN1Set var11 = ASN1Set.getInstance((ASN1TaggedObject)var1.getObjectAt(var9), (boolean)0);
         this.unprotectedAttrs = var11;
      }
   }

   public EnvelopedData(OriginatorInfo var1, ASN1Set var2, EncryptedContentInfo var3, ASN1Set var4) {
      if(var1 == null && var4 == null) {
         DERInteger var6 = new DERInteger(0);
         this.version = var6;
         Enumeration var7 = var2.getObjects();

         while(var7.hasMoreElements()) {
            DERInteger var8 = RecipientInfo.getInstance(var7.nextElement()).getVersion();
            DERInteger var9 = this.version;
            if(!var8.equals(var9)) {
               DERInteger var10 = new DERInteger(2);
               this.version = var10;
               break;
            }
         }
      } else {
         DERInteger var5 = new DERInteger(2);
         this.version = var5;
      }

      this.originatorInfo = var1;
      this.recipientInfos = var2;
      this.encryptedContentInfo = var3;
      this.unprotectedAttrs = var4;
   }

   public static EnvelopedData getInstance(Object var0) {
      EnvelopedData var1;
      if(var0 != null && !(var0 instanceof EnvelopedData)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid EnvelopedData: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new EnvelopedData(var2);
      } else {
         var1 = (EnvelopedData)var0;
      }

      return var1;
   }

   public static EnvelopedData getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public EncryptedContentInfo getEncryptedContentInfo() {
      return this.encryptedContentInfo;
   }

   public OriginatorInfo getOriginatorInfo() {
      return this.originatorInfo;
   }

   public ASN1Set getRecipientInfos() {
      return this.recipientInfos;
   }

   public ASN1Set getUnprotectedAttrs() {
      return this.unprotectedAttrs;
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
      EncryptedContentInfo var6 = this.encryptedContentInfo;
      var1.add(var6);
      if(this.unprotectedAttrs != null) {
         ASN1Set var7 = this.unprotectedAttrs;
         DERTaggedObject var8 = new DERTaggedObject((boolean)0, 1, var7);
         var1.add(var8);
      }

      return new BERSequence(var1);
   }
}

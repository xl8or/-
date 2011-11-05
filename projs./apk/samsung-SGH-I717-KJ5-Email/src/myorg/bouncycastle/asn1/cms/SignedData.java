package myorg.bouncycastle.asn1.cms;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERSet;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.cms.SignerInfo;

public class SignedData extends ASN1Encodable {

   private ASN1Set certificates;
   private boolean certsBer;
   private ContentInfo contentInfo;
   private ASN1Set crls;
   private boolean crlsBer;
   private ASN1Set digestAlgorithms;
   private ASN1Set signerInfos;
   private DERInteger version;


   public SignedData(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERInteger var3 = (DERInteger)var2.nextElement();
      this.version = var3;
      ASN1Set var4 = (ASN1Set)var2.nextElement();
      this.digestAlgorithms = var4;
      ContentInfo var5 = ContentInfo.getInstance(var2.nextElement());
      this.contentInfo = var5;

      while(var2.hasMoreElements()) {
         DERObject var6 = (DERObject)var2.nextElement();
         if(var6 instanceof ASN1TaggedObject) {
            ASN1TaggedObject var7 = (ASN1TaggedObject)var6;
            switch(var7.getTagNo()) {
            case 0:
               boolean var11 = var7 instanceof BERTaggedObject;
               this.certsBer = var11;
               ASN1Set var12 = ASN1Set.getInstance(var7, (boolean)0);
               this.certificates = var12;
               break;
            case 1:
               boolean var13 = var7 instanceof BERTaggedObject;
               this.crlsBer = var13;
               ASN1Set var14 = ASN1Set.getInstance(var7, (boolean)0);
               this.crls = var14;
               break;
            default:
               StringBuilder var8 = (new StringBuilder()).append("unknown tag value ");
               int var9 = var7.getTagNo();
               String var10 = var8.append(var9).toString();
               throw new IllegalArgumentException(var10);
            }
         } else {
            ASN1Set var15 = (ASN1Set)var6;
            this.signerInfos = var15;
         }
      }

   }

   public SignedData(ASN1Set var1, ContentInfo var2, ASN1Set var3, ASN1Set var4, ASN1Set var5) {
      DERObjectIdentifier var6 = var2.getContentType();
      DERInteger var7 = this.calculateVersion(var6, var3, var4, var5);
      this.version = var7;
      this.digestAlgorithms = var1;
      this.contentInfo = var2;
      this.certificates = var3;
      this.crls = var4;
      this.signerInfos = var5;
      boolean var8 = var4 instanceof BERSet;
      this.crlsBer = var8;
      boolean var9 = var3 instanceof BERSet;
      this.certsBer = var9;
   }

   private DERInteger calculateVersion(DERObjectIdentifier var1, ASN1Set var2, ASN1Set var3, ASN1Set var4) {
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;
      Enumeration var9;
      if(var2 != null) {
         var9 = var2.getObjects();

         while(var9.hasMoreElements()) {
            Object var10 = var9.nextElement();
            if(var10 instanceof ASN1TaggedObject) {
               ASN1TaggedObject var11 = (ASN1TaggedObject)var10;
               if(var11.getTagNo() == 1) {
                  var7 = true;
               } else if(var11.getTagNo() == 2) {
                  var8 = true;
               } else if(var11.getTagNo() == 3) {
                  var5 = true;
               }
            }
         }
      }

      DERInteger var12;
      if(var5) {
         var12 = new DERInteger(5);
      } else {
         if(var3 != null) {
            var9 = var3.getObjects();

            while(var9.hasMoreElements()) {
               if(var9.nextElement() instanceof ASN1TaggedObject) {
                  var6 = true;
               }
            }
         }

         if(var6) {
            var12 = new DERInteger(5);
         } else if(var8) {
            var12 = new DERInteger(4);
         } else if(var7) {
            var12 = new DERInteger(3);
         } else {
            DERObjectIdentifier var13 = CMSObjectIdentifiers.data;
            if(var1.equals(var13)) {
               if(this.checkForVersion3(var4)) {
                  var12 = new DERInteger(3);
               } else {
                  var12 = new DERInteger(1);
               }
            } else {
               var12 = new DERInteger(3);
            }
         }
      }

      return var12;
   }

   private boolean checkForVersion3(ASN1Set var1) {
      Enumeration var2 = var1.getObjects();

      boolean var3;
      while(true) {
         if(var2.hasMoreElements()) {
            if(SignerInfo.getInstance(var2.nextElement()).getVersion().getValue().intValue() != 3) {
               continue;
            }

            var3 = true;
            break;
         }

         var3 = false;
         break;
      }

      return var3;
   }

   public static SignedData getInstance(Object var0) {
      SignedData var1;
      if(var0 instanceof SignedData) {
         var1 = (SignedData)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SignedData(var2);
      }

      return var1;
   }

   public ASN1Set getCRLs() {
      return this.crls;
   }

   public ASN1Set getCertificates() {
      return this.certificates;
   }

   public ASN1Set getDigestAlgorithms() {
      return this.digestAlgorithms;
   }

   public ContentInfo getEncapContentInfo() {
      return this.contentInfo;
   }

   public ASN1Set getSignerInfos() {
      return this.signerInfos;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      ASN1Set var3 = this.digestAlgorithms;
      var1.add(var3);
      ContentInfo var4 = this.contentInfo;
      var1.add(var4);
      if(this.certificates != null) {
         if(this.certsBer) {
            ASN1Set var5 = this.certificates;
            BERTaggedObject var6 = new BERTaggedObject((boolean)0, 0, var5);
            var1.add(var6);
         } else {
            ASN1Set var10 = this.certificates;
            DERTaggedObject var11 = new DERTaggedObject((boolean)0, 0, var10);
            var1.add(var11);
         }
      }

      if(this.crls != null) {
         if(this.crlsBer) {
            ASN1Set var7 = this.crls;
            BERTaggedObject var8 = new BERTaggedObject((boolean)0, 1, var7);
            var1.add(var8);
         } else {
            ASN1Set var12 = this.crls;
            DERTaggedObject var13 = new DERTaggedObject((boolean)0, 1, var12);
            var1.add(var13);
         }
      }

      ASN1Set var9 = this.signerInfos;
      var1.add(var9);
      return new BERSequence(var1);
   }
}

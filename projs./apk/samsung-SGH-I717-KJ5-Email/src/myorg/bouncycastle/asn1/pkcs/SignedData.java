package myorg.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.pkcs.ContentInfo;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class SignedData extends ASN1Encodable implements PKCSObjectIdentifiers {

   private ASN1Set certificates;
   private ContentInfo contentInfo;
   private ASN1Set crls;
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
         if(var6 instanceof DERTaggedObject) {
            DERTaggedObject var7 = (DERTaggedObject)var6;
            switch(var7.getTagNo()) {
            case 0:
               ASN1Set var11 = ASN1Set.getInstance(var7, (boolean)0);
               this.certificates = var11;
               break;
            case 1:
               ASN1Set var12 = ASN1Set.getInstance(var7, (boolean)0);
               this.crls = var12;
               break;
            default:
               StringBuilder var8 = (new StringBuilder()).append("unknown tag value ");
               int var9 = var7.getTagNo();
               String var10 = var8.append(var9).toString();
               throw new IllegalArgumentException(var10);
            }
         } else {
            ASN1Set var13 = (ASN1Set)var6;
            this.signerInfos = var13;
         }
      }

   }

   public SignedData(DERInteger var1, ASN1Set var2, ContentInfo var3, ASN1Set var4, ASN1Set var5, ASN1Set var6) {
      this.version = var1;
      this.digestAlgorithms = var2;
      this.contentInfo = var3;
      this.certificates = var4;
      this.crls = var5;
      this.signerInfos = var6;
   }

   public static SignedData getInstance(Object var0) {
      SignedData var1;
      if(var0 instanceof SignedData) {
         var1 = (SignedData)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            String var3 = "unknown object in factory: " + var0;
            throw new IllegalArgumentException(var3);
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

   public ContentInfo getContentInfo() {
      return this.contentInfo;
   }

   public ASN1Set getDigestAlgorithms() {
      return this.digestAlgorithms;
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
         ASN1Set var5 = this.certificates;
         DERTaggedObject var6 = new DERTaggedObject((boolean)0, 0, var5);
         var1.add(var6);
      }

      if(this.crls != null) {
         ASN1Set var7 = this.crls;
         DERTaggedObject var8 = new DERTaggedObject((boolean)0, 1, var7);
         var1.add(var8);
      }

      ASN1Set var9 = this.signerInfos;
      var1.add(var9);
      return new BERSequence(var1);
   }
}

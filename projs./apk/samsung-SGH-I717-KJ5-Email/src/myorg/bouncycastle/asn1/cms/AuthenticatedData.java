package myorg.bouncycastle.asn1.cms;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class AuthenticatedData extends ASN1Encodable {

   private ASN1Set authAttrs;
   private AlgorithmIdentifier digestAlgorithm;
   private ContentInfo encapsulatedContentInfo;
   private ASN1OctetString mac;
   private AlgorithmIdentifier macAlgorithm;
   private OriginatorInfo originatorInfo;
   private ASN1Set recipientInfos;
   private ASN1Set unauthAttrs;
   private DERInteger version;


   public AuthenticatedData(ASN1Sequence var1) {
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
      AlgorithmIdentifier var10 = AlgorithmIdentifier.getInstance(var1.getObjectAt(var4));
      this.macAlgorithm = var10;
      int var11 = var9 + 1;
      DEREncodable var12 = var1.getObjectAt(var9);
      if(var12 instanceof ASN1TaggedObject) {
         AlgorithmIdentifier var13 = AlgorithmIdentifier.getInstance((ASN1TaggedObject)var12, (boolean)0);
         this.digestAlgorithm = var13;
         int var14 = var11 + 1;
         var12 = var1.getObjectAt(var11);
         var11 = var14;
      }

      ContentInfo var15 = ContentInfo.getInstance(var12);
      this.encapsulatedContentInfo = var15;
      int var16 = var11 + 1;
      DEREncodable var17 = var1.getObjectAt(var11);
      int var19;
      if(var17 instanceof ASN1TaggedObject) {
         ASN1Set var18 = ASN1Set.getInstance((ASN1TaggedObject)var17, (boolean)0);
         this.authAttrs = var18;
         var19 = var16 + 1;
         var17 = var1.getObjectAt(var16);
      } else {
         var19 = var16;
      }

      ASN1OctetString var20 = ASN1OctetString.getInstance(var17);
      this.mac = var20;
      if(var1.size() > var19) {
         ASN1Set var21 = ASN1Set.getInstance((ASN1TaggedObject)var1.getObjectAt(var19), (boolean)0);
         this.unauthAttrs = var21;
      }
   }

   public AuthenticatedData(OriginatorInfo var1, ASN1Set var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4, ContentInfo var5, ASN1Set var6, ASN1OctetString var7, ASN1Set var8) {
      if((var4 != null || var6 != null) && (var4 == null || var6 == null)) {
         throw new IllegalArgumentException("digestAlgorithm and authAttrs must be set together");
      } else {
         int var9 = calculateVersion(var1);
         DERInteger var10 = new DERInteger(var9);
         this.version = var10;
         this.originatorInfo = var1;
         this.macAlgorithm = var3;
         this.digestAlgorithm = var4;
         this.recipientInfos = var2;
         this.encapsulatedContentInfo = var5;
         this.authAttrs = var6;
         this.mac = var7;
         this.unauthAttrs = var8;
      }
   }

   public static int calculateVersion(OriginatorInfo var0) {
      byte var1;
      if(var0 == null) {
         var1 = 0;
      } else {
         byte var2 = 0;
         Enumeration var3 = var0.getCertificates().getObjects();

         Object var4;
         while(var3.hasMoreElements()) {
            var4 = var3.nextElement();
            if(var4 instanceof ASN1TaggedObject) {
               ASN1TaggedObject var5 = (ASN1TaggedObject)var4;
               if(var5.getTagNo() == 2) {
                  var2 = 1;
               } else if(var5.getTagNo() == 3) {
                  var2 = 3;
                  break;
               }
            }
         }

         Enumeration var6 = var0.getCRLs().getObjects();

         while(var6.hasMoreElements()) {
            var4 = var6.nextElement();
            if(var4 instanceof ASN1TaggedObject && ((ASN1TaggedObject)var4).getTagNo() == 1) {
               var2 = 3;
               break;
            }
         }

         var1 = var2;
      }

      return var1;
   }

   public static AuthenticatedData getInstance(Object var0) {
      AuthenticatedData var1;
      if(var0 != null && !(var0 instanceof AuthenticatedData)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid AuthenticatedData: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AuthenticatedData(var2);
      } else {
         var1 = (AuthenticatedData)var0;
      }

      return var1;
   }

   public static AuthenticatedData getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1Set getAuthAttrs() {
      return this.authAttrs;
   }

   public ContentInfo getEncapsulatedContentInfo() {
      return this.encapsulatedContentInfo;
   }

   public ASN1OctetString getMac() {
      return this.mac;
   }

   public AlgorithmIdentifier getMacAlgorithm() {
      return this.macAlgorithm;
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
      AlgorithmIdentifier var6 = this.macAlgorithm;
      var1.add(var6);
      if(this.digestAlgorithm != null) {
         AlgorithmIdentifier var7 = this.digestAlgorithm;
         DERTaggedObject var8 = new DERTaggedObject((boolean)0, 1, var7);
         var1.add(var8);
      }

      ContentInfo var9 = this.encapsulatedContentInfo;
      var1.add(var9);
      if(this.authAttrs != null) {
         ASN1Set var10 = this.authAttrs;
         DERTaggedObject var11 = new DERTaggedObject((boolean)0, 2, var10);
         var1.add(var11);
      }

      ASN1OctetString var12 = this.mac;
      var1.add(var12);
      if(this.unauthAttrs != null) {
         ASN1Set var13 = this.unauthAttrs;
         DERTaggedObject var14 = new DERTaggedObject((boolean)0, 3, var13);
         var1.add(var14);
      }

      return new BERSequence(var1);
   }
}

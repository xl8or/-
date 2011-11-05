package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.cms.EncryptedContentInfo;

public class EncryptedData extends ASN1Encodable {

   private EncryptedContentInfo encryptedContentInfo;
   private ASN1Set unprotectedAttrs;
   private DERInteger version;


   private EncryptedData(ASN1Sequence var1) {
      DERInteger var2 = DERInteger.getInstance(var1.getObjectAt(0));
      this.version = var2;
      EncryptedContentInfo var3 = EncryptedContentInfo.getInstance(var1.getObjectAt(1));
      this.encryptedContentInfo = var3;
      if(var1.size() == 3) {
         ASN1Set var4 = ASN1Set.getInstance(var1.getObjectAt(2));
         this.unprotectedAttrs = var4;
      }
   }

   public EncryptedData(EncryptedContentInfo var1) {
      this(var1, (ASN1Set)null);
   }

   public EncryptedData(EncryptedContentInfo var1, ASN1Set var2) {
      DERInteger var3 = new DERInteger;
      byte var4;
      if(var2 == null) {
         var4 = 0;
      } else {
         var4 = 2;
      }

      var3.<init>(var4);
      this.version = var3;
      this.encryptedContentInfo = var1;
      this.unprotectedAttrs = var2;
   }

   public static EncryptedData getInstance(Object var0) {
      EncryptedData var1;
      if(var0 instanceof EncryptedData) {
         var1 = (EncryptedData)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid EncryptedData: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new EncryptedData(var2);
      }

      return var1;
   }

   public EncryptedContentInfo getEncryptedContentInfo() {
      return this.encryptedContentInfo;
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
      EncryptedContentInfo var3 = this.encryptedContentInfo;
      var1.add(var3);
      if(this.unprotectedAttrs != null) {
         ASN1Set var4 = this.unprotectedAttrs;
         BERTaggedObject var5 = new BERTaggedObject((boolean)0, 1, var4);
         var1.add(var5);
      }

      return new BERSequence(var1);
   }
}

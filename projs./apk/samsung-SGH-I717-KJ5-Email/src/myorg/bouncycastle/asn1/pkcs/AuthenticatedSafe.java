package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.pkcs.ContentInfo;

public class AuthenticatedSafe extends ASN1Encodable {

   ContentInfo[] info;


   public AuthenticatedSafe(ASN1Sequence var1) {
      ContentInfo[] var2 = new ContentInfo[var1.size()];
      this.info = var2;
      int var3 = 0;

      while(true) {
         int var4 = this.info.length;
         if(var3 == var4) {
            return;
         }

         ContentInfo[] var5 = this.info;
         ContentInfo var6 = ContentInfo.getInstance(var1.getObjectAt(var3));
         var5[var3] = var6;
         ++var3;
      }
   }

   public AuthenticatedSafe(ContentInfo[] var1) {
      this.info = var1;
   }

   public ContentInfo[] getContentInfo() {
      return this.info;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      int var2 = 0;

      while(true) {
         int var3 = this.info.length;
         if(var2 == var3) {
            return new BERSequence(var1);
         }

         ContentInfo var4 = this.info[var2];
         var1.add(var4);
         ++var2;
      }
   }
}

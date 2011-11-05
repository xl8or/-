package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERSequence;

public class DERTaggedObject extends ASN1TaggedObject {

   private static final byte[] ZERO_BYTES = new byte[0];


   public DERTaggedObject(int var1) {
      DERSequence var2 = new DERSequence();
      super((boolean)0, var1, var2);
   }

   public DERTaggedObject(int var1, DEREncodable var2) {
      super(var1, var2);
   }

   public DERTaggedObject(boolean var1, int var2, DEREncodable var3) {
      super(var1, var2, var3);
   }

   void encode(DEROutputStream var1) throws IOException {
      if(!this.empty) {
         byte[] var2 = this.obj.getDERObject().getEncoded("DER");
         if(this.explicit) {
            int var3 = this.tagNo;
            var1.writeEncoded(160, var3, var2);
         } else {
            short var4;
            if((var2[0] & 32) != 0) {
               var4 = 160;
            } else {
               var4 = 128;
            }

            int var5 = this.tagNo;
            var1.writeTag(var4, var5);
            int var6 = var2.length - 1;
            var1.write(var2, 1, var6);
         }
      } else {
         int var7 = this.tagNo;
         byte[] var8 = ZERO_BYTES;
         var1.writeEncoded(160, var7, var8);
      }
   }
}

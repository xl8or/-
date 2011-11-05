package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.BEROutputStream;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class BERTaggedObject extends DERTaggedObject {

   public BERTaggedObject(int var1) {
      BERSequence var2 = new BERSequence();
      super((boolean)0, var1, var2);
   }

   public BERTaggedObject(int var1, DEREncodable var2) {
      super(var1, var2);
   }

   public BERTaggedObject(boolean var1, int var2, DEREncodable var3) {
      super(var1, var2, var3);
   }

   void encode(DEROutputStream var1) throws IOException {
      if(!(var1 instanceof ASN1OutputStream) && !(var1 instanceof BEROutputStream)) {
         super.encode(var1);
      } else {
         int var2 = this.tagNo;
         var1.writeTag(160, var2);
         var1.write(128);
         if(!this.empty) {
            if(!this.explicit) {
               Enumeration var3;
               if(this.obj instanceof ASN1OctetString) {
                  if(this.obj instanceof BERConstructedOctetString) {
                     var3 = ((BERConstructedOctetString)this.obj).getObjects();
                  } else {
                     byte[] var5 = ((ASN1OctetString)this.obj).getOctets();
                     var3 = (new BERConstructedOctetString(var5)).getObjects();
                  }
               } else if(this.obj instanceof ASN1Sequence) {
                  var3 = ((ASN1Sequence)this.obj).getObjects();
               } else {
                  if(!(this.obj instanceof ASN1Set)) {
                     StringBuilder var6 = (new StringBuilder()).append("not implemented: ");
                     String var7 = this.obj.getClass().getName();
                     String var8 = var6.append(var7).toString();
                     throw new RuntimeException(var8);
                  }

                  var3 = ((ASN1Set)this.obj).getObjects();
               }

               while(var3.hasMoreElements()) {
                  Object var4 = var3.nextElement();
                  var1.writeObject(var4);
               }
            } else {
               DEREncodable var9 = this.obj;
               var1.writeObject(var9);
            }
         }

         var1.write(0);
         var1.write(0);
      }
   }
}

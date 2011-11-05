package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEREncodableVector;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERSet extends ASN1Set {

   public DERSet() {}

   public DERSet(DEREncodable var1) {
      this.addObject(var1);
   }

   public DERSet(DEREncodableVector var1) {
      this(var1, (boolean)1);
   }

   DERSet(DEREncodableVector var1, boolean var2) {
      int var3 = 0;

      while(true) {
         int var4 = var1.size();
         if(var3 == var4) {
            if(!var2) {
               return;
            } else {
               this.sort();
               return;
            }
         }

         DEREncodable var5 = var1.get(var3);
         this.addObject(var5);
         ++var3;
      }
   }

   public DERSet(ASN1Encodable[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            this.sort();
            return;
         }

         ASN1Encodable var4 = var1[var2];
         this.addObject(var4);
         ++var2;
      }
   }

   void encode(DEROutputStream var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      DEROutputStream var3 = new DEROutputStream(var2);
      Enumeration var4 = this.getObjects();

      while(var4.hasMoreElements()) {
         Object var5 = var4.nextElement();
         var3.writeObject(var5);
      }

      var3.close();
      byte[] var6 = var2.toByteArray();
      var1.writeEncoded(49, var6);
   }
}

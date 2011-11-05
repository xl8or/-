package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEREncodableVector;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERSequence extends ASN1Sequence {

   public DERSequence() {}

   public DERSequence(DEREncodable var1) {
      this.addObject(var1);
   }

   public DERSequence(DEREncodableVector var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.size();
         if(var2 == var3) {
            return;
         }

         DEREncodable var4 = var1.get(var2);
         this.addObject(var4);
         ++var2;
      }
   }

   public DERSequence(ASN1Encodable[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
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
      var1.writeEncoded(48, var6);
   }
}

package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEREncodableVector;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERConstructedSet extends ASN1Set {

   public DERConstructedSet() {}

   public DERConstructedSet(DEREncodable var1) {
      this.addObject(var1);
   }

   public DERConstructedSet(DEREncodableVector var1) {
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

   public void addObject(DEREncodable var1) {
      super.addObject(var1);
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

   public int getSize() {
      return this.size();
   }
}

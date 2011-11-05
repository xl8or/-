package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.LazyDERConstructionEnumeration;

public class LazyDERSequence extends DERSequence {

   private byte[] encoded;
   private boolean parsed = 0;
   private int size = -1;


   LazyDERSequence(byte[] var1) throws IOException {
      this.encoded = var1;
   }

   private void parse() {
      byte[] var1 = this.encoded;
      LazyDERConstructionEnumeration var2 = new LazyDERConstructionEnumeration(var1);

      while(var2.hasMoreElements()) {
         DEREncodable var3 = (DEREncodable)var2.nextElement();
         this.addObject(var3);
      }

      this.parsed = (boolean)1;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.encoded;
      var1.writeEncoded(48, var2);
   }

   public DEREncodable getObjectAt(int var1) {
      if(!this.parsed) {
         this.parse();
      }

      return super.getObjectAt(var1);
   }

   public Enumeration getObjects() {
      Object var1;
      if(this.parsed) {
         var1 = super.getObjects();
      } else {
         byte[] var2 = this.encoded;
         var1 = new LazyDERConstructionEnumeration(var2);
      }

      return (Enumeration)var1;
   }

   public int size() {
      if(this.size < 0) {
         byte[] var1 = this.encoded;
         LazyDERConstructionEnumeration var2 = new LazyDERConstructionEnumeration(var1);

         int var4;
         for(this.size = 0; var2.hasMoreElements(); this.size = var4) {
            Object var3 = var2.nextElement();
            var4 = this.size + 1;
         }
      }

      return this.size;
   }
}

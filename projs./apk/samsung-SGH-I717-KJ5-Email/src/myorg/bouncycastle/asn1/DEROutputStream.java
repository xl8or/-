package myorg.bouncycastle.asn1;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTags;

public class DEROutputStream extends FilterOutputStream implements DERTags {

   public DEROutputStream(OutputStream var1) {
      super(var1);
   }

   private void writeLength(int var1) throws IOException {
      if(var1 <= 127) {
         byte var7 = (byte)var1;
         this.write(var7);
      } else {
         int var2 = 1;
         int var3 = var1;

         while(true) {
            var3 >>>= 8;
            if(var3 == 0) {
               byte var4 = (byte)(var2 | 128);
               this.write(var4);

               for(int var5 = (var2 - 1) * 8; var5 >= 0; var5 += -8) {
                  byte var6 = (byte)(var1 >> var5);
                  this.write(var6);
               }

               return;
            }

            ++var2;
         }
      }
   }

   public void write(byte[] var1) throws IOException {
      OutputStream var2 = this.out;
      int var3 = var1.length;
      var2.write(var1, 0, var3);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }

   void writeEncoded(int var1, int var2, byte[] var3) throws IOException {
      this.writeTag(var1, var2);
      int var4 = var3.length;
      this.writeLength(var4);
      this.write(var3);
   }

   void writeEncoded(int var1, byte[] var2) throws IOException {
      this.write(var1);
      int var3 = var2.length;
      this.writeLength(var3);
      this.write(var2);
   }

   protected void writeNull() throws IOException {
      this.write(5);
      this.write(0);
   }

   public void writeObject(Object var1) throws IOException {
      if(var1 == null) {
         this.writeNull();
      } else if(var1 instanceof DERObject) {
         ((DERObject)var1).encode(this);
      } else if(var1 instanceof DEREncodable) {
         ((DEREncodable)var1).getDERObject().encode(this);
      } else {
         throw new IOException("object not DEREncodable");
      }
   }

   void writeTag(int var1, int var2) throws IOException {
      if(var2 < 31) {
         int var3 = var1 | var2;
         this.write(var3);
      } else {
         int var4 = var1 | 31;
         this.write(var4);
         if(var2 < 128) {
            this.write(var2);
         } else {
            byte[] var5 = new byte[5];
            int var6 = var5.length + -1;
            byte var7 = (byte)(var2 & 127);
            var5[var6] = var7;

            do {
               var2 >>= 7;
               var6 += -1;
               byte var8 = (byte)(var2 & 127 | 128);
               var5[var6] = var8;
            } while(var2 > 127);

            int var9 = var5.length - var6;
            this.write(var5, var6, var9);
         }
      }
   }
}

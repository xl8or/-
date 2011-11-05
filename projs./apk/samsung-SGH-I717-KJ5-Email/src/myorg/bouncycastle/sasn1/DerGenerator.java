package myorg.bouncycastle.sasn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import myorg.bouncycastle.sasn1.Asn1Generator;

public abstract class DerGenerator extends Asn1Generator {

   private boolean _isExplicit;
   private int _tagNo;
   private boolean _tagged = 0;


   protected DerGenerator(OutputStream var1) {
      super(var1);
   }

   public DerGenerator(OutputStream var1, int var2, boolean var3) {
      super(var1);
      this._tagged = (boolean)1;
      this._isExplicit = var3;
      this._tagNo = var2;
   }

   private void writeLength(OutputStream var1, int var2) throws IOException {
      if(var2 <= 127) {
         byte var8 = (byte)var2;
         var1.write(var8);
      } else {
         int var3 = 1;
         int var4 = var2;

         while(true) {
            var4 >>>= 8;
            if(var4 == 0) {
               byte var5 = (byte)(var3 | 128);
               var1.write(var5);

               for(int var6 = (var3 - 1) * 8; var6 >= 0; var6 += -8) {
                  byte var7 = (byte)(var2 >> var6);
                  var1.write(var7);
               }

               return;
            }

            ++var3;
         }
      }
   }

   void writeDerEncoded(int var1, byte[] var2) throws IOException {
      if(this._tagged) {
         int var3 = this._tagNo | 128;
         if(this._isExplicit) {
            int var4 = this._tagNo | 32 | 128;
            ByteArrayOutputStream var5 = new ByteArrayOutputStream();
            this.writeDerEncoded(var5, var1, var2);
            OutputStream var6 = this._out;
            byte[] var7 = var5.toByteArray();
            this.writeDerEncoded(var6, var4, var7);
         } else if((var1 & 32) != 0) {
            OutputStream var8 = this._out;
            int var9 = var3 | 32;
            this.writeDerEncoded(var8, var9, var2);
         } else {
            OutputStream var10 = this._out;
            this.writeDerEncoded(var10, var3, var2);
         }
      } else {
         OutputStream var11 = this._out;
         this.writeDerEncoded(var11, var1, var2);
      }
   }

   void writeDerEncoded(OutputStream var1, int var2, InputStream var3) throws IOException {
      var1.write(var2);
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();

      while(true) {
         int var5 = var3.read();
         if(var5 < 0) {
            byte[] var6 = var4.toByteArray();
            int var7 = var6.length;
            this.writeLength(var1, var7);
            var1.write(var6);
            return;
         }

         var4.write(var5);
      }
   }

   void writeDerEncoded(OutputStream var1, int var2, byte[] var3) throws IOException {
      var1.write(var2);
      int var4 = var3.length;
      this.writeLength(var1, var4);
      var1.write(var3);
   }
}

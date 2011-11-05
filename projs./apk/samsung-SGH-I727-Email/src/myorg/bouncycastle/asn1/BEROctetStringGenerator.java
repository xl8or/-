package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.asn1.BERGenerator;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DEROutputStream;

public class BEROctetStringGenerator extends BERGenerator {

   public BEROctetStringGenerator(OutputStream var1) throws IOException {
      super(var1);
      this.writeBERHeader(36);
   }

   public BEROctetStringGenerator(OutputStream var1, int var2, boolean var3) throws IOException {
      super(var1, var2, var3);
      this.writeBERHeader(36);
   }

   public OutputStream getOctetOutputStream() {
      byte[] var1 = new byte[1000];
      return this.getOctetOutputStream(var1);
   }

   public OutputStream getOctetOutputStream(byte[] var1) {
      return new BEROctetStringGenerator.BufferedBEROctetStream(var1);
   }

   private class BufferedBEROctetStream extends OutputStream {

      private byte[] _buf;
      private DEROutputStream _derOut;
      private int _off;


      BufferedBEROctetStream(byte[] var2) {
         this._buf = var2;
         this._off = 0;
         OutputStream var3 = BEROctetStringGenerator.this._out;
         DEROutputStream var4 = new DEROutputStream(var3);
         this._derOut = var4;
      }

      public void close() throws IOException {
         if(this._off != 0) {
            byte[] var1 = new byte[this._off];
            byte[] var2 = this._buf;
            int var3 = this._off;
            System.arraycopy(var2, 0, var1, 0, var3);
            DEROctetString.encode(this._derOut, var1);
         }

         BEROctetStringGenerator.this.writeBEREnd();
      }

      public void write(int var1) throws IOException {
         byte[] var2 = this._buf;
         int var3 = this._off;
         int var4 = var3 + 1;
         this._off = var4;
         byte var5 = (byte)var1;
         var2[var3] = var5;
         int var6 = this._off;
         int var7 = this._buf.length;
         if(var6 == var7) {
            DEROutputStream var8 = this._derOut;
            byte[] var9 = this._buf;
            DEROctetString.encode(var8, var9);
            this._off = 0;
         }
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         while(var3 > 0) {
            int var4 = this._buf.length;
            int var5 = this._off;
            int var6 = var4 - var5;
            int var7 = Math.min(var3, var6);
            byte[] var8 = this._buf;
            int var9 = this._off;
            System.arraycopy(var1, var2, var8, var9, var7);
            int var10 = this._off + var7;
            this._off = var10;
            int var11 = this._off;
            int var12 = this._buf.length;
            if(var11 < var12) {
               return;
            }

            DEROutputStream var13 = this._derOut;
            byte[] var14 = this._buf;
            DEROctetString.encode(var13, var14);
            this._off = 0;
            var2 += var7;
            var3 -= var7;
         }

      }
   }
}

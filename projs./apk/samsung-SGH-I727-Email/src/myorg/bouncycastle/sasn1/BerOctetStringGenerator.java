package myorg.bouncycastle.sasn1;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.sasn1.BerGenerator;

public class BerOctetStringGenerator extends BerGenerator {

   public BerOctetStringGenerator(OutputStream var1) throws IOException {
      super(var1);
      this.writeBerHeader(36);
   }

   public BerOctetStringGenerator(OutputStream var1, int var2, boolean var3) throws IOException {
      super(var1, var2, var3);
      this.writeBerHeader(36);
   }

   public OutputStream getOctetOutputStream() {
      return new BerOctetStringGenerator.BerOctetStream((BerOctetStringGenerator.1)null);
   }

   public OutputStream getOctetOutputStream(byte[] var1) {
      return new BerOctetStringGenerator.BufferedBerOctetStream(var1);
   }

   private class BufferedBerOctetStream extends OutputStream {

      private byte[] _buf;
      private int _off;


      BufferedBerOctetStream(byte[] var2) {
         this._buf = var2;
         this._off = 0;
      }

      public void close() throws IOException {
         if(this._off != 0) {
            byte[] var1 = new byte[this._off];
            byte[] var2 = this._buf;
            int var3 = this._off;
            System.arraycopy(var2, 0, var1, 0, var3);
            OutputStream var4 = BerOctetStringGenerator.this._out;
            byte[] var5 = (new DEROctetString(var1)).getEncoded();
            var4.write(var5);
         }

         BerOctetStringGenerator.this.writeBerEnd();
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
            OutputStream var8 = BerOctetStringGenerator.this._out;
            byte[] var9 = this._buf;
            byte[] var10 = (new DEROctetString(var9)).getEncoded();
            var8.write(var10);
            this._off = 0;
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class BerOctetStream extends OutputStream {

      private byte[] _buf;


      private BerOctetStream() {
         byte[] var2 = new byte[1];
         this._buf = var2;
      }

      // $FF: synthetic method
      BerOctetStream(BerOctetStringGenerator.1 var2) {
         this();
      }

      public void close() throws IOException {
         BerOctetStringGenerator.this.writeBerEnd();
      }

      public void write(int var1) throws IOException {
         byte[] var2 = this._buf;
         byte var3 = (byte)var1;
         var2[0] = var3;
         OutputStream var4 = BerOctetStringGenerator.this._out;
         byte[] var5 = this._buf;
         byte[] var6 = (new DEROctetString(var5)).getEncoded();
         var4.write(var6);
      }

      public void write(byte[] var1) throws IOException {
         OutputStream var2 = BerOctetStringGenerator.this._out;
         byte[] var3 = (new DEROctetString(var1)).getEncoded();
         var2.write(var3);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         byte[] var4 = new byte[var3];
         System.arraycopy(var1, var2, var4, 0, var3);
         OutputStream var5 = BerOctetStringGenerator.this._out;
         byte[] var6 = (new DEROctetString(var4)).getEncoded();
         var5.write(var6);
      }
   }
}

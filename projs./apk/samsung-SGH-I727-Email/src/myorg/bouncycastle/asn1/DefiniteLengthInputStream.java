package myorg.bouncycastle.asn1;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.LimitedInputStream;
import myorg.bouncycastle.util.io.Streams;

class DefiniteLengthInputStream extends LimitedInputStream {

   private static final byte[] EMPTY_BYTES = new byte[0];
   private final int _originalLength;
   private int _remaining;


   DefiniteLengthInputStream(InputStream var1, int var2) {
      super(var1);
      if(var2 < 0) {
         throw new IllegalArgumentException("negative lengths not allowed");
      } else {
         this._originalLength = var2;
         this._remaining = var2;
         if(var2 == 0) {
            this.setParentEofDetect((boolean)1);
         }
      }
   }

   int getRemaining() {
      return this._remaining;
   }

   public int read() throws IOException {
      int var1;
      if(this._remaining == 0) {
         var1 = -1;
      } else {
         int var2 = this._in.read();
         if(var2 < 0) {
            StringBuilder var3 = (new StringBuilder()).append("DEF length ");
            int var4 = this._originalLength;
            StringBuilder var5 = var3.append(var4).append(" object truncated by ");
            int var6 = this._remaining;
            String var7 = var5.append(var6).toString();
            throw new EOFException(var7);
         }

         int var8 = this._remaining - 1;
         this._remaining = var8;
         if(var8 == 0) {
            this.setParentEofDetect((boolean)1);
         }

         var1 = var2;
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this._remaining == 0) {
         var4 = -1;
      } else {
         int var5 = this._remaining;
         int var6 = Math.min(var3, var5);
         int var7 = this._in.read(var1, var2, var6);
         if(var7 < 0) {
            StringBuilder var8 = (new StringBuilder()).append("DEF length ");
            int var9 = this._originalLength;
            StringBuilder var10 = var8.append(var9).append(" object truncated by ");
            int var11 = this._remaining;
            String var12 = var10.append(var11).toString();
            throw new EOFException(var12);
         }

         int var13 = this._remaining - var7;
         this._remaining = var13;
         if(var13 == 0) {
            this.setParentEofDetect((boolean)1);
         }

         var4 = var7;
      }

      return var4;
   }

   byte[] toByteArray() throws IOException {
      byte[] var1;
      if(this._remaining == 0) {
         var1 = EMPTY_BYTES;
      } else {
         byte[] var2 = new byte[this._remaining];
         int var3 = this._remaining;
         int var4 = Streams.readFully(this._in, var2);
         int var5 = var3 - var4;
         this._remaining = var5;
         if(var5 != 0) {
            StringBuilder var6 = (new StringBuilder()).append("DEF length ");
            int var7 = this._originalLength;
            StringBuilder var8 = var6.append(var7).append(" object truncated by ");
            int var9 = this._remaining;
            String var10 = var8.append(var9).toString();
            throw new EOFException(var10);
         }

         this.setParentEofDetect((boolean)1);
         var1 = var2;
      }

      return var1;
   }
}

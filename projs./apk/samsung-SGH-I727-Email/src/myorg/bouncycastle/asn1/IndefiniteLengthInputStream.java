package myorg.bouncycastle.asn1;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.LimitedInputStream;

class IndefiniteLengthInputStream extends LimitedInputStream {

   private int _b1;
   private int _b2;
   private boolean _eofOn00 = 1;
   private boolean _eofReached = 0;


   IndefiniteLengthInputStream(InputStream var1) throws IOException {
      super(var1);
      int var2 = var1.read();
      this._b1 = var2;
      int var3 = var1.read();
      this._b2 = var3;
      if(this._b2 < 0) {
         throw new EOFException();
      } else {
         boolean var4 = this.checkForEof();
      }
   }

   private boolean checkForEof() {
      if(!this._eofReached && this._eofOn00 && this._b1 == 0 && this._b2 == 0) {
         this._eofReached = (boolean)1;
         this.setParentEofDetect((boolean)1);
      }

      return this._eofReached;
   }

   public int read() throws IOException {
      int var1;
      if(this.checkForEof()) {
         var1 = -1;
      } else {
         int var2 = this._in.read();
         if(var2 < 0) {
            throw new EOFException();
         }

         int var3 = this._b1;
         int var4 = this._b2;
         this._b1 = var4;
         this._b2 = var2;
         var1 = var3;
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(!this._eofOn00 && var3 >= 3) {
         if(this._eofReached) {
            var4 = -1;
         } else {
            InputStream var5 = this._in;
            int var6 = var2 + 2;
            int var7 = var3 - 2;
            int var8 = var5.read(var1, var6, var7);
            if(var8 < 0) {
               throw new EOFException();
            }

            byte var9 = (byte)this._b1;
            var1[var2] = var9;
            int var10 = var2 + 1;
            byte var11 = (byte)this._b2;
            var1[var10] = var11;
            int var12 = this._in.read();
            this._b1 = var12;
            int var13 = this._in.read();
            this._b2 = var13;
            if(this._b2 < 0) {
               throw new EOFException();
            }

            var4 = var8 + 2;
         }
      } else {
         var4 = super.read(var1, var2, var3);
      }

      return var4;
   }

   void setEofOn00(boolean var1) {
      this._eofOn00 = var1;
      boolean var2 = this.checkForEof();
   }
}

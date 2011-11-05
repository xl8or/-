package myorg.bouncycastle.sasn1;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.sasn1.LimitedInputStream;

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
      byte var4;
      if(this._b2 < 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      this._eofReached = (boolean)var4;
   }

   void checkForEof() throws IOException {
      if(this._eofOn00) {
         if(this._b1 == 0) {
            if(this._b2 == 0) {
               this._eofReached = (boolean)1;
               this.setParentEofDetect((boolean)1);
            }
         }
      }
   }

   public int read() throws IOException {
      this.checkForEof();
      int var1;
      if(this._eofReached) {
         var1 = -1;
      } else {
         int var2 = this._in.read();
         if(var2 < 0) {
            this._eofReached = (boolean)1;
            var1 = -1;
         } else {
            int var3 = this._b1;
            int var4 = this._b2;
            this._b1 = var4;
            this._b2 = var2;
            var1 = var3;
         }
      }

      return var1;
   }

   void setEofOn00(boolean var1) {
      this._eofOn00 = var1;
   }
}

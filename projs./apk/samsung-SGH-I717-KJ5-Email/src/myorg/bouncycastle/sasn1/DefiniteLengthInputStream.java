package myorg.bouncycastle.sasn1;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.sasn1.LimitedInputStream;

class DefiniteLengthInputStream extends LimitedInputStream {

   private int _length;


   DefiniteLengthInputStream(InputStream var1, int var2) {
      super(var1);
      this._length = var2;
   }

   public int read() throws IOException {
      int var1 = this._length;
      int var2 = var1 - 1;
      this._length = var2;
      int var3;
      if(var1 > 0) {
         var3 = this._in.read();
      } else {
         this.setParentEofDetect((boolean)1);
         var3 = -1;
      }

      return var3;
   }
}

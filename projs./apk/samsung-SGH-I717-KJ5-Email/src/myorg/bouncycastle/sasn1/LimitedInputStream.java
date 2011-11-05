package myorg.bouncycastle.sasn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.sasn1.IndefiniteLengthInputStream;

abstract class LimitedInputStream extends InputStream {

   protected final InputStream _in;


   LimitedInputStream(InputStream var1) {
      this._in = var1;
   }

   InputStream getUnderlyingStream() {
      return this._in;
   }

   protected void setParentEofDetect(boolean var1) {
      if(this._in instanceof IndefiniteLengthInputStream) {
         ((IndefiniteLengthInputStream)this._in).setEofOn00(var1);
      }
   }

   byte[] toByteArray() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      while(true) {
         int var2 = this.read();
         if(var2 < 0) {
            return var1.toByteArray();
         }

         var1.write(var2);
      }
   }
}

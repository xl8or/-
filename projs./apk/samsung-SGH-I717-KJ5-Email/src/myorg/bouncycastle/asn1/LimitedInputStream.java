package myorg.bouncycastle.asn1;

import java.io.InputStream;
import myorg.bouncycastle.asn1.IndefiniteLengthInputStream;

abstract class LimitedInputStream extends InputStream {

   protected final InputStream _in;


   LimitedInputStream(InputStream var1) {
      this._in = var1;
   }

   protected void setParentEofDetect(boolean var1) {
      if(this._in instanceof IndefiniteLengthInputStream) {
         ((IndefiniteLengthInputStream)this._in).setEofOn00(var1);
      }
   }
}

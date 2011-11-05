package myorg.bouncycastle.sasn1;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.sasn1.Asn1InputStream;
import myorg.bouncycastle.sasn1.Asn1OctetString;

class ConstructedOctetStream extends InputStream {

   private final Asn1InputStream _aIn;
   private InputStream _currentStream;
   private boolean _first = 1;


   ConstructedOctetStream(InputStream var1) {
      Asn1InputStream var2 = new Asn1InputStream(var1);
      this._aIn = var2;
   }

   public int read() throws IOException {
      Asn1OctetString var1;
      int var2;
      if(this._first) {
         var1 = (Asn1OctetString)this._aIn.readObject();
         if(var1 == null) {
            var2 = -1;
            return var2;
         }

         this._first = (boolean)0;
         InputStream var3 = var1.getOctetStream();
         this._currentStream = var3;
      } else if(this._currentStream == null) {
         var2 = -1;
         return var2;
      }

      int var4 = this._currentStream.read();
      if(var4 < 0) {
         var1 = (Asn1OctetString)this._aIn.readObject();
         if(var1 == null) {
            this._currentStream = null;
            var2 = -1;
         } else {
            InputStream var5 = var1.getOctetStream();
            this._currentStream = var5;
            var2 = this._currentStream.read();
         }
      } else {
         var2 = var4;
      }

      return var2;
   }
}

package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1OctetStringParser;
import myorg.bouncycastle.asn1.ASN1StreamParser;

class ConstructedOctetStream extends InputStream {

   private InputStream _currentStream;
   private boolean _first = 1;
   private final ASN1StreamParser _parser;


   ConstructedOctetStream(ASN1StreamParser var1) {
      this._parser = var1;
   }

   public int read() throws IOException {
      int var1;
      ASN1OctetStringParser var2;
      if(this._currentStream == null) {
         if(!this._first) {
            var1 = -1;
            return var1;
         }

         var2 = (ASN1OctetStringParser)this._parser.readObject();
         if(var2 == null) {
            var1 = -1;
            return var1;
         }

         this._first = (boolean)0;
         InputStream var3 = var2.getOctetStream();
         this._currentStream = var3;
      }

      while(true) {
         int var4 = this._currentStream.read();
         if(var4 >= 0) {
            var1 = var4;
            break;
         }

         var2 = (ASN1OctetStringParser)this._parser.readObject();
         if(var2 == null) {
            this._currentStream = null;
            var1 = -1;
            break;
         }

         InputStream var5 = var2.getOctetStream();
         this._currentStream = var5;
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this._currentStream == null) {
         if(!this._first) {
            var4 = -1;
            return var4;
         }

         ASN1OctetStringParser var5 = (ASN1OctetStringParser)this._parser.readObject();
         if(var5 == null) {
            var4 = -1;
            return var4;
         }

         this._first = (boolean)0;
         InputStream var6 = var5.getOctetStream();
         this._currentStream = var6;
      }

      int var7 = 0;

      while(true) {
         InputStream var8 = this._currentStream;
         int var9 = var2 + var7;
         int var10 = var3 - var7;
         int var11 = var8.read(var1, var9, var10);
         if(var11 >= 0) {
            var7 += var11;
            if(var7 == var3) {
               var4 = var7;
               break;
            }
         } else {
            ASN1OctetStringParser var12 = (ASN1OctetStringParser)this._parser.readObject();
            if(var12 == null) {
               this._currentStream = null;
               if(var7 < 1) {
                  var4 = -1;
               } else {
                  var4 = var7;
               }
               break;
            }

            InputStream var13 = var12.getOctetStream();
            this._currentStream = var13;
         }
      }

      return var4;
   }
}

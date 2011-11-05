package myorg.bouncycastle.cms;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.util.io.Streams;

public class CMSTypedStream {

   private static final int BUF_SIZ = 32768;
   private final InputStream _in;
   private final String _oid;


   public CMSTypedStream(InputStream var1) {
      String var2 = PKCSObjectIdentifiers.data.getId();
      this(var2, var1, '\u8000');
   }

   public CMSTypedStream(String var1, InputStream var2) {
      this(var1, var2, '\u8000');
   }

   public CMSTypedStream(String var1, InputStream var2, int var3) {
      this._oid = var1;
      CMSTypedStream.FullReaderStream var4 = new CMSTypedStream.FullReaderStream(var2, var3);
      this._in = var4;
   }

   public void drain() throws IOException {
      Streams.drain(this._in);
      this._in.close();
   }

   public InputStream getContentStream() {
      return this._in;
   }

   public String getContentType() {
      return this._oid;
   }

   private class FullReaderStream extends InputStream {

      InputStream _stream;


      FullReaderStream(InputStream var2, int var3) {
         BufferedInputStream var4 = new BufferedInputStream(var2, var3);
         this._stream = var4;
      }

      public void close() throws IOException {
         this._stream.close();
      }

      public int read() throws IOException {
         return this._stream.read();
      }

      public int read(byte[] var1, int var2, int var3) throws IOException {
         int var4;
         int var5;
         for(var4 = 0; var3 != 0; var4 += var5) {
            var5 = this._stream.read(var1, var2, var3);
            if(var5 <= 0) {
               break;
            }

            var2 += var5;
            var3 -= var5;
         }

         int var6;
         if(var4 > 0) {
            var6 = var4;
         } else {
            var6 = -1;
         }

         return var6;
      }
   }
}

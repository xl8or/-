package gnu.inet.http;

import gnu.inet.http.Headers;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;

public class ChunkedInputStream extends FilterInputStream {

   private static final byte CR = 13;
   private static final byte LF = 10;
   int count;
   boolean eof;
   Headers headers;
   boolean meta;
   int size;


   public ChunkedInputStream(InputStream var1, Headers var2) {
      super(var1);
      this.headers = var2;
      this.size = -1;
      this.count = 0;
      this.meta = (boolean)1;
   }

   public int read() throws IOException {
      byte[] var1 = new byte[1];
      int var2;
      if(this.read(var1, 0, 1) == -1) {
         var2 = -1;
      } else {
         var2 = var1[0];
         if(var2 < 0) {
            var2 += 256;
         }
      }

      return var2;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.eof) {
         var4 = -1;
      } else {
         int var7;
         if(this.meta) {
            StringBuffer var5 = new StringBuffer();
            boolean var6 = false;
            var7 = 0;

            while(true) {
               int var8 = this.in.read();
               if(var8 == 59) {
                  var6 = true;
               } else {
                  if(var8 == 10 && var7 == 13) {
                     int var11 = Integer.parseInt(var5.toString(), 16);
                     this.size = var11;
                     break;
                  }

                  if(!var6 && var8 >= 48) {
                     char var12 = (char)var8;
                     var5.append(var12);
                  }
               }

               if(var8 == -1) {
                  break;
               }

               var7 = var8;
            }

            this.count = 0;
            this.meta = (boolean)0;
         }

         if(this.size == 0) {
            Headers var9 = this.headers;
            InputStream var10 = this.in;
            var9.parse(var10);
            this.eof = (boolean)1;
            var4 = -1;
         } else {
            int var14 = var3 - var2;
            int var15 = this.size;
            int var16 = this.count;
            int var17 = var15 - var16;
            if(var14 >= var17) {
               var14 = var17;
            }

            if(var14 > 0) {
               var4 = this.in.read(var1, var2, var14);
            } else {
               var4 = 0;
            }

            int var18 = this.count + var4;
            this.count = var18;
            int var19 = this.count;
            int var20 = this.size;
            if(var19 == var20) {
               int var21 = this.in.read();
               var7 = this.in.read();
               if(var21 == -1 && var7 == -1) {
                  this.eof = (boolean)1;
                  var4 = -1;
               } else {
                  if(var21 != 13 || var7 != 10) {
                     String var22 = "expecting CRLF: " + var21 + "," + var7;
                     throw new ProtocolException(var22);
                  }

                  this.meta = (boolean)1;
               }
            }
         }
      }

      return var4;
   }
}

package gnu.inet.http;

import gnu.inet.http.RequestBodyWriter;

public class ByteArrayRequestBodyWriter implements RequestBodyWriter {

   protected byte[] content;
   protected int pos;


   public ByteArrayRequestBodyWriter(byte[] var1) {
      this.content = var1;
      this.pos = 0;
   }

   public int getContentLength() {
      return this.content.length;
   }

   public void reset() {
      this.pos = 0;
   }

   public int write(byte[] var1) {
      int var2 = this.content.length;
      int var3 = this.pos;
      int var4 = var2 - var3;
      if(var1.length < var4) {
         var4 = var1.length;
      }

      if(var4 > -1) {
         byte[] var5 = this.content;
         int var6 = this.pos;
         System.arraycopy(var5, var6, var1, 0, var4);
         int var7 = this.pos + var4;
         this.pos = var7;
      }

      return var4;
   }
}

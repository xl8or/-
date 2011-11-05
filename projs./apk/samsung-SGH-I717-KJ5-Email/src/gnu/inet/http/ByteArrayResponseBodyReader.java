package gnu.inet.http;

import gnu.inet.http.Request;
import gnu.inet.http.Response;
import gnu.inet.http.ResponseBodyReader;

public class ByteArrayResponseBodyReader implements ResponseBodyReader {

   protected byte[] content;
   protected int len;
   protected int pos;


   public ByteArrayResponseBodyReader() {
      this(4096);
   }

   public ByteArrayResponseBodyReader(int var1) {
      byte[] var2 = new byte[var1];
      this.content = var2;
      this.len = 0;
      this.pos = 0;
   }

   public boolean accept(Request var1, Response var2) {
      return true;
   }

   public void close() {
      this.pos = 0;
   }

   public void read(byte[] var1, int var2, int var3) {
      int var4 = var3 - var2;
      int var5 = this.pos + var4;
      int var6 = this.content.length;
      if(var5 > var6) {
         byte[] var7 = new byte[this.content.length * 2];
         byte[] var8 = this.content;
         int var9 = this.pos;
         System.arraycopy(var8, 0, var7, 0, var9);
         this.content = var7;
      }

      byte[] var10 = this.content;
      int var11 = this.pos;
      System.arraycopy(var1, var2, var10, var11, var4);
      int var12 = this.pos;
      int var13 = var4 + var12;
      this.pos = var13;
      int var14 = this.pos;
      this.len = var14;
   }

   public byte[] toByteArray() {
      byte[] var1 = new byte[this.len];
      byte[] var2 = this.content;
      int var3 = this.len;
      System.arraycopy(var2, 0, var1, 0, var3);
      return var1;
   }
}

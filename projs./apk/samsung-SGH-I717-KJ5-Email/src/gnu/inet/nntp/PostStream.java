package gnu.inet.nntp;

import gnu.inet.nntp.NNTPConnection;
import gnu.inet.util.CRLFOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

public final class PostStream extends FilterOutputStream {

   NNTPConnection connection;
   boolean isTakethis;
   byte last;


   PostStream(NNTPConnection var1, boolean var2) {
      CRLFOutputStream var3 = var1.out;
      super(var3);
      this.connection = var1;
      this.isTakethis = var2;
   }

   public void close() throws IOException {
      if(this.last != 13) {
         this.write(13);
      }

      if(this.isTakethis) {
         this.connection.takethisComplete();
      } else {
         this.connection.postComplete();
      }
   }

   public void write(int var1) throws IOException {
      super.write(var1);
      byte var2 = (byte)var1;
      this.last = var2;
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      super.write(var1, var2, var3);
      if(var3 > 0) {
         int var4 = var2 + var3 - 1;
         byte var5 = var1[var4];
         this.last = var5;
      }
   }
}

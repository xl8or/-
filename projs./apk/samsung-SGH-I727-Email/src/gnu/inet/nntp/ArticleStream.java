package gnu.inet.nntp;

import gnu.inet.nntp.PendingData;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ArticleStream extends FilterInputStream implements PendingData {

   ArticleStream(InputStream var1) {
      super(var1);
   }

   public void readToEOF() throws IOException {
      if(this.in.available() != 0) {
         byte[] var1 = new byte[4096];

         for(int var2 = 0; var2 != -1; var2 = this.in.read(var1)) {
            ;
         }

      }
   }
}

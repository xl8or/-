package gnu.inet.nntp;

import gnu.inet.nntp.LineIterator;
import gnu.inet.nntp.NNTPConnection;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ArticleNumberIterator extends LineIterator {

   ArticleNumberIterator(NNTPConnection var1) {
      super(var1);
   }

   public Object next() {
      try {
         int var1 = this.nextArticleNumber();
         Integer var2 = new Integer(var1);
         return var2;
      } catch (IOException var7) {
         StringBuilder var4 = (new StringBuilder()).append("I/O error: ");
         String var5 = var7.getMessage();
         String var6 = var4.append(var5).toString();
         throw new NoSuchElementException(var6);
      }
   }

   public int nextArticleNumber() throws IOException {
      return Integer.parseInt(this.nextLine().trim());
   }
}

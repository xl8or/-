package gnu.inet.nntp;

import gnu.inet.nntp.LineIterator;
import gnu.inet.nntp.NNTPConnection;
import gnu.inet.nntp.Pair;
import java.io.IOException;
import java.util.NoSuchElementException;

public class PairIterator extends LineIterator {

   PairIterator(NNTPConnection var1) {
      super(var1);
   }

   public Object next() {
      try {
         Pair var1 = this.nextPair();
         return var1;
      } catch (IOException var6) {
         StringBuilder var3 = (new StringBuilder()).append("I/O error: ");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new NoSuchElementException(var5);
      }
   }

   public Pair nextPair() throws IOException {
      String var1 = this.nextLine();
      int var2 = var1.indexOf(32, 0);
      String var3 = var1.substring(0, var2);
      int var4 = var2 + 1;
      String var5 = var1.substring(var4);
      return new Pair(var3, var5);
   }
}

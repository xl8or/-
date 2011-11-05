package gnu.inet.nntp;

import gnu.inet.nntp.LineIterator;
import gnu.inet.nntp.NNTPConnection;
import gnu.inet.nntp.Overview;
import java.io.IOException;
import java.util.NoSuchElementException;

public class OverviewIterator extends LineIterator {

   OverviewIterator(NNTPConnection var1) {
      super(var1);
   }

   public Object next() {
      try {
         Overview var1 = this.nextOverview();
         return var1;
      } catch (IOException var6) {
         StringBuilder var3 = (new StringBuilder()).append("I/O error: ");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new NoSuchElementException(var5);
      }
   }

   public Overview nextOverview() throws IOException {
      String var1 = this.nextLine();
      int var2 = var1.indexOf(9, 0);
      int var3 = Integer.parseInt(var1.substring(0, var2));
      int var4 = var2 + 1;
      Overview var5 = new Overview(var3);

      int var9;
      for(int var6 = var1.indexOf(9, var4); var6 > -1; var6 = var9) {
         String var7 = var1.substring(var4, var6);
         var5.add(var7);
         int var8 = var6 + 1;
         var9 = var1.indexOf(9, var8);
         var4 = var8;
      }

      String var10 = var1.substring(var4);
      var5.add(var10);
      return var5;
   }
}

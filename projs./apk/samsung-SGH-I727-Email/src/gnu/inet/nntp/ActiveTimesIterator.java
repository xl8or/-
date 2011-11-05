package gnu.inet.nntp;

import gnu.inet.nntp.ActiveTime;
import gnu.inet.nntp.LineIterator;
import gnu.inet.nntp.NNTPConnection;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.NoSuchElementException;

public class ActiveTimesIterator extends LineIterator {

   ActiveTimesIterator(NNTPConnection var1) {
      super(var1);
   }

   public Object next() {
      try {
         ActiveTime var1 = this.nextGroup();
         return var1;
      } catch (IOException var6) {
         StringBuilder var3 = (new StringBuilder()).append("I/O error: ");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new NoSuchElementException(var5);
      }
   }

   public ActiveTime nextGroup() throws IOException {
      String var1 = this.nextLine();

      try {
         int var2 = var1.indexOf(32, 0);
         String var3 = var1.substring(0, var2);
         int var4 = var2 + 1;
         int var5 = var1.indexOf(32, var4);
         NNTPConnection var6 = this.connection;
         String var7 = var1.substring(var4, var5);
         Date var8 = var6.parseDate(var7);
         int var9 = var5 + 1;
         String var10 = var1.substring(var9);
         ActiveTime var11 = new ActiveTime(var3, var8, var10);
         return var11;
      } catch (ParseException var13) {
         String var12 = var13.getMessage();
         throw new IOException(var12);
      }
   }
}

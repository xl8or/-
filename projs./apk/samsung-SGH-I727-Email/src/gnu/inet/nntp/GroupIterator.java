package gnu.inet.nntp;

import gnu.inet.nntp.Group;
import gnu.inet.nntp.LineIterator;
import gnu.inet.nntp.NNTPConnection;
import java.io.IOException;
import java.util.NoSuchElementException;

public class GroupIterator extends LineIterator {

   static final String CAN_POST = "y";


   GroupIterator(NNTPConnection var1) {
      super(var1);
   }

   public Object next() {
      try {
         Group var1 = this.nextGroup();
         return var1;
      } catch (IOException var6) {
         StringBuilder var3 = (new StringBuilder()).append("I/O error: ");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new NoSuchElementException(var5);
      }
   }

   public Group nextGroup() throws IOException {
      String var1 = this.nextLine();
      int var2 = var1.indexOf(32, 0);
      String var3 = var1.substring(0, var2);
      int var4 = var2 + 1;
      int var5 = var1.indexOf(32, var4);
      int var6 = Integer.parseInt(var1.substring(var4, var5));
      int var7 = var5 + 1;
      int var8 = var1.indexOf(32, var7);
      int var9 = Integer.parseInt(var1.substring(var7, var8));
      int var10 = var8 + 1;
      String var11 = var1.substring(var10);
      boolean var12 = "y".equals(var11);
      return new Group(var3, var6, var9, var12);
   }
}

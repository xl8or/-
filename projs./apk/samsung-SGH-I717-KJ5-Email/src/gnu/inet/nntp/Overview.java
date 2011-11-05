package gnu.inet.nntp;

import java.util.ArrayList;
import java.util.List;

public final class Overview {

   int articleNumber;
   private List headers;


   Overview(int var1) {
      this.articleNumber = var1;
      ArrayList var2 = new ArrayList(8);
      this.headers = var2;
   }

   void add(String var1) {
      this.headers.add(var1);
   }

   public int getArticleNumber() {
      return this.articleNumber;
   }

   public String getHeader(int var1) {
      return (String)this.headers.get(var1);
   }
}

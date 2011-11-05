package gnu.inet.nntp;

import gnu.inet.nntp.StatusResponse;
import java.io.InputStream;

public class ArticleResponse extends StatusResponse {

   public int articleNumber;
   public InputStream in;
   public String messageId;


   protected ArticleResponse(short var1, String var2) {
      super(var1, var2);
   }
}

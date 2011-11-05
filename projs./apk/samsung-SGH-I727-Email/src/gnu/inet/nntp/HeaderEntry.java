package gnu.inet.nntp;


public final class HeaderEntry {

   String articleId;
   String header;


   HeaderEntry(String var1, String var2) {
      this.articleId = var1;
      this.header = var2;
   }

   public String getArticleId() {
      return this.articleId;
   }

   public String getHeader() {
      return this.header;
   }
}

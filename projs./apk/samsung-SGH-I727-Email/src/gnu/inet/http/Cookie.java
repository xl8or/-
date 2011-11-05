package gnu.inet.http;

import java.util.Date;

public class Cookie {

   protected final String comment;
   protected final String domain;
   protected final Date expires;
   protected final String name;
   protected final String path;
   protected final boolean secure;
   protected final String value;


   public Cookie(String var1, String var2, String var3, String var4, String var5, boolean var6, Date var7) {
      this.name = var1;
      this.value = var2;
      this.comment = var3;
      this.domain = var4;
      this.path = var5;
      this.secure = var6;
      this.expires = var7;
   }

   public String getComment() {
      return this.comment;
   }

   public String getDomain() {
      return this.domain;
   }

   public Date getExpiryDate() {
      return this.expires;
   }

   public String getName() {
      return this.name;
   }

   public String getPath() {
      return this.path;
   }

   public String getValue() {
      return this.value;
   }

   public boolean isSecure() {
      return this.secure;
   }

   public String toString() {
      return this.toString((boolean)1, (boolean)1);
   }

   public String toString(boolean var1, boolean var2) {
      StringBuffer var3 = new StringBuffer();
      String var4 = this.name;
      var3.append(var4);
      StringBuffer var6 = var3.append('=');
      String var7 = this.value;
      var3.append(var7);
      if(var1) {
         StringBuffer var9 = var3.append("; $Path=");
         String var10 = this.path;
         var3.append(var10);
      }

      if(var2) {
         StringBuffer var12 = var3.append("; $Domain=");
         String var13 = this.domain;
         var3.append(var13);
      }

      return var3.toString();
   }
}

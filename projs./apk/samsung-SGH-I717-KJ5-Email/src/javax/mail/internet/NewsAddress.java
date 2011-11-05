package javax.mail.internet;

import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.mail.Address;
import javax.mail.internet.AddressException;

public class NewsAddress extends Address {

   private static final String TYPE = "news";
   protected String host;
   protected String newsgroup;


   public NewsAddress() {}

   public NewsAddress(String var1) {
      this(var1, (String)null);
   }

   public NewsAddress(String var1, String var2) {
      this.newsgroup = var1;
      this.host = var2;
   }

   public static NewsAddress[] parse(String var0) throws AddressException {
      StringTokenizer var1 = new StringTokenizer(var0, ",");
      ArrayList var2 = new ArrayList();

      while(var1.hasMoreTokens()) {
         String var3 = var1.nextToken();
         NewsAddress var4 = new NewsAddress(var3);
         var2.add(var4);
      }

      NewsAddress[] var6 = new NewsAddress[var2.size()];
      var2.toArray(var6);
      return var6;
   }

   public static String toString(Address[] var0) {
      String var1;
      if(var0 != null && var0.length != 0) {
         StringBuffer var2 = new StringBuffer();
         int var3 = 0;

         while(true) {
            int var4 = var0.length;
            if(var3 >= var4) {
               var1 = var2.toString();
               break;
            }

            if(var3 > 0) {
               StringBuffer var5 = var2.append(',');
            }

            String var6 = ((NewsAddress)var0[var3]).toString();
            var2.append(var6);
            ++var3;
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean equals(Object var1) {
      boolean var6;
      if(var1 instanceof NewsAddress) {
         NewsAddress var7 = (NewsAddress)var1;
         String var2 = this.newsgroup;
         String var3 = var7.newsgroup;
         if(var2.equals(var3)) {
            label37: {
               if(this.host != null || var7.host != null) {
                  if(this.host == null || var7.host == null) {
                     break label37;
                  }

                  String var4 = this.host;
                  String var5 = var7.host;
                  if(!var4.equalsIgnoreCase(var5)) {
                     break label37;
                  }
               }

               var6 = true;
               return var6;
            }
         }

         var6 = false;
      } else {
         var6 = false;
      }

      return var6;
   }

   public String getHost() {
      return this.host;
   }

   public String getNewsgroup() {
      return this.newsgroup;
   }

   public String getType() {
      return "news";
   }

   public int hashCode() {
      int var1 = 0;
      if(this.newsgroup != null) {
         int var2 = this.newsgroup.hashCode();
         var1 = 0 + var2;
      }

      if(this.host != null) {
         int var3 = this.host.hashCode();
         var1 += var3;
      }

      return var1;
   }

   public void setHost(String var1) {
      this.host = var1;
   }

   public void setNewsgroup(String var1) {
      this.newsgroup = var1;
   }

   public String toString() {
      return this.newsgroup;
   }
}

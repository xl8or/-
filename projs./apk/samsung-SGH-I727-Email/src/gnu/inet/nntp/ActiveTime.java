package gnu.inet.nntp;

import java.util.Date;

public final class ActiveTime {

   String email;
   String group;
   Date time;


   ActiveTime(String var1, Date var2, String var3) {
      this.group = var1;
      this.time = var2;
      this.email = var3;
   }

   public String getEmail() {
      return this.email;
   }

   public String getGroup() {
      return this.group;
   }

   public Date getTime() {
      return this.time;
   }
}

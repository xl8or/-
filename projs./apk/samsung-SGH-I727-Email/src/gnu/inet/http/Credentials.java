package gnu.inet.http;


public class Credentials {

   private String password;
   private String username;


   public Credentials(String var1, String var2) {
      this.username = var1;
      this.password = var2;
   }

   public String getPassword() {
      return this.password;
   }

   public String getUsername() {
      return this.username;
   }
}

package javax.mail;


public final class PasswordAuthentication {

   private String password;
   private String userName;


   public PasswordAuthentication(String var1, String var2) {
      this.userName = var1;
      this.password = var2;
   }

   public String getPassword() {
      return this.password;
   }

   public String getUserName() {
      return this.userName;
   }
}

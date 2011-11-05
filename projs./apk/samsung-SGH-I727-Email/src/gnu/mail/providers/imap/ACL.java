package gnu.mail.providers.imap;

import gnu.mail.providers.imap.Rights;

public final class ACL {

   String name;
   Rights rights;


   public ACL(String var1) {
      this(var1, (Rights)null);
   }

   public ACL(String var1, Rights var2) {
      this.name = var1;
      this.rights = var2;
   }

   public String getName() {
      return this.name;
   }

   public Rights getRights() {
      return this.rights;
   }

   public void setRights(Rights var1) {
      this.rights = var1;
   }
}

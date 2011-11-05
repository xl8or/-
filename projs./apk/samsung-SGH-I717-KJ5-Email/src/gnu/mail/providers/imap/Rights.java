package gnu.mail.providers.imap;


public final class Rights {

   int rights;


   public Rights() {}

   public Rights(Rights.Right var1) {
      int var2 = var1.code;
      this.rights = var2;
   }

   public Rights(Rights var1) {
      int var2 = var1.rights;
      this.rights = var2;
   }

   public Rights(String var1) {
      if(var1 != null) {
         int var2 = var1.length();

         for(int var3 = 0; var3 < var2; ++var3) {
            int var4 = this.rights;
            int var5 = Rights.Right.getInstance(var1.charAt(var3)).code;
            int var6 = var4 | var5;
            this.rights = var6;
         }

      }
   }

   public void add(Rights.Right var1) {
      int var2 = this.rights;
      int var3 = var1.code;
      int var4 = var2 | var3;
      this.rights = var4;
   }

   public void add(Rights var1) {
      int var2 = this.rights;
      int var3 = var1.rights;
      int var4 = var2 | var3;
      this.rights = var4;
   }

   public boolean contains(Rights.Right var1) {
      int var2 = this.rights;
      int var3 = var1.code;
      boolean var4;
      if((var2 & var3) > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean contains(Rights var1) {
      int var2 = this.rights;
      int var3 = var1.rights;
      boolean var4;
      if((var2 & var3) > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof Rights) {
         int var2 = ((Rights)var1).rights;
         int var3 = this.rights;
         if(var2 == var3) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public int hashCode() {
      return this.rights;
   }

   public void remove(Rights.Right var1) {
      int var2 = this.rights;
      int var3 = var1.code;
      int var4 = var2 - var3;
      this.rights = var4;
   }

   public void remove(Rights var1) {
      int var2 = this.rights;
      int var3 = var1.rights;
      int var4 = var2 - var3;
      this.rights = var4;
   }

   public static final class Right {

      public static final Rights.Right ADMINISTER = new Rights.Right(256);
      public static final Rights.Right CREATE = new Rights.Right(64);
      public static final Rights.Right DELETE = new Rights.Right(128);
      public static final Rights.Right INSERT = new Rights.Right(16);
      public static final Rights.Right KEEP_SEEN = new Rights.Right(4);
      public static final Rights.Right LOOKUP = new Rights.Right(1);
      public static final Rights.Right POST = new Rights.Right(32);
      public static final Rights.Right READ = new Rights.Right(2);
      public static final Rights.Right WRITE = new Rights.Right(8);
      final int code;


      Right(int var1) {
         this.code = var1;
      }

      public static Rights.Right getInstance(char var0) {
         Rights.Right var1;
         switch(var0) {
         case 97:
            var1 = ADMINISTER;
            break;
         case 99:
            var1 = CREATE;
            break;
         case 100:
            var1 = DELETE;
            break;
         case 105:
            var1 = INSERT;
            break;
         case 108:
            var1 = LOOKUP;
            break;
         case 112:
            var1 = POST;
            break;
         case 114:
            var1 = READ;
            break;
         case 115:
            var1 = KEEP_SEEN;
            break;
         case 119:
            var1 = WRITE;
            break;
         default:
            throw new IllegalArgumentException();
         }

         return var1;
      }

      public String toString() {
         String var1;
         switch(this.code) {
         case 1:
            var1 = "l";
            break;
         case 2:
            var1 = "r";
            break;
         case 4:
            var1 = "s";
            break;
         case 8:
            var1 = "w";
            break;
         case 16:
            var1 = "i";
            break;
         case 32:
            var1 = "p";
            break;
         case 64:
            var1 = "c";
            break;
         case 128:
            var1 = "d";
            break;
         case 256:
            var1 = "a";
            break;
         default:
            throw new IllegalStateException();
         }

         return var1;
      }
   }
}

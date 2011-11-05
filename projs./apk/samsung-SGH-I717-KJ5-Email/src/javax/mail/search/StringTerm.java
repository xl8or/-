package javax.mail.search;

import javax.mail.search.SearchTerm;

public abstract class StringTerm extends SearchTerm {

   protected boolean ignoreCase;
   protected String pattern;


   protected StringTerm(String var1) {
      this(var1, (boolean)1);
   }

   protected StringTerm(String var1, boolean var2) {
      this.pattern = var1;
      this.ignoreCase = var2;
   }

   public boolean equals(Object var1) {
      boolean var6;
      if(var1 instanceof StringTerm) {
         StringTerm var11 = (StringTerm)var1;
         if(this.ignoreCase) {
            String var2 = var11.pattern;
            String var3 = this.pattern;
            if(var2.equalsIgnoreCase(var3)) {
               boolean var4 = var11.ignoreCase;
               boolean var5 = this.ignoreCase;
               if(var4 == var5) {
                  var6 = true;
                  return var6;
               }
            }

            var6 = false;
         } else {
            String var7 = var11.pattern;
            String var8 = this.pattern;
            if(var7.equals(var8)) {
               boolean var9 = var11.ignoreCase;
               boolean var10 = this.ignoreCase;
               if(var9 == var10) {
                  var6 = true;
                  return var6;
               }
            }

            var6 = false;
         }
      } else {
         var6 = false;
      }

      return var6;
   }

   public boolean getIgnoreCase() {
      return this.ignoreCase;
   }

   public String getPattern() {
      return this.pattern;
   }

   public int hashCode() {
      int var1;
      if(this.ignoreCase) {
         var1 = this.pattern.hashCode();
      } else {
         var1 = ~this.pattern.hashCode();
      }

      return var1;
   }

   protected boolean match(String var1) {
      int var2 = this.pattern.length();
      int var3 = var1.length() - var2;
      int var4 = 0;

      boolean var7;
      while(true) {
         if(var4 > var3) {
            var7 = false;
            break;
         }

         boolean var5 = this.ignoreCase;
         String var6 = this.pattern;
         if(var1.regionMatches(var5, var4, var6, 0, var2)) {
            var7 = true;
            break;
         }

         ++var4;
      }

      return var7;
   }
}

package javax.mail.search;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.search.SearchTerm;

public final class FlagTerm extends SearchTerm {

   protected Flags flags;
   protected boolean set;


   public FlagTerm(Flags var1, boolean var2) {
      this.flags = var1;
      this.set = var2;
   }

   public boolean equals(Object var1) {
      boolean var6;
      if(var1 instanceof FlagTerm) {
         FlagTerm var7 = (FlagTerm)var1;
         boolean var2 = var7.set;
         boolean var3 = this.set;
         if(var2 == var3) {
            Flags var4 = var7.flags;
            Flags var5 = this.flags;
            if(var4.equals(var5)) {
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

   public Flags getFlags() {
      return (Flags)this.flags.clone();
   }

   public boolean getTestSet() {
      return this.set;
   }

   public int hashCode() {
      int var1;
      if(this.set) {
         var1 = this.flags.hashCode();
      } else {
         var1 = ~this.flags.hashCode();
      }

      return var1;
   }

   public boolean match(Message param1) {
      // $FF: Couldn't be decompiled
   }
}

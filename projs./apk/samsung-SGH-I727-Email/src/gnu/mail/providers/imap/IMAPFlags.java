package gnu.mail.providers.imap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.mail.Flags;

class IMAPFlags extends Flags {

   Flags saved;


   IMAPFlags() {}

   static List getIMAPFlags(Flags var0) {
      Flags.Flag[] var1 = var0.getSystemFlags();
      String[] var2 = var0.getUserFlags();
      int var3 = var1.length;
      int var4 = var2.length;
      int var5 = var3 + var4;
      ArrayList var6 = new ArrayList(var5);
      int var7 = 0;

      while(true) {
         int var8 = var1.length;
         if(var7 >= var8) {
            List var20 = Arrays.asList(var2);
            var6.addAll(var20);
            return var6;
         }

         Flags.Flag var9 = var1[var7];
         Flags.Flag var10 = Flags.Flag.ANSWERED;
         if(var9 == var10) {
            boolean var11 = var6.add("\\Answered");
         } else {
            Flags.Flag var12 = Flags.Flag.DELETED;
            if(var9 == var12) {
               boolean var13 = var6.add("\\Deleted");
            } else {
               Flags.Flag var14 = Flags.Flag.DRAFT;
               if(var9 == var14) {
                  boolean var15 = var6.add("\\Draft");
               } else {
                  Flags.Flag var16 = Flags.Flag.FLAGGED;
                  if(var9 == var16) {
                     boolean var17 = var6.add("\\Flagged");
                  } else {
                     Flags.Flag var18 = Flags.Flag.SEEN;
                     if(var9 == var18) {
                        boolean var19 = var6.add("\\Seen");
                     }
                  }
               }
            }
         }

         ++var7;
      }
   }

   void checkpoint() {
      Flags var1 = new Flags(this);
      this.saved = var1;
   }

   List getAddedFlags() {
      List var1;
      if(this.saved == null) {
         var1 = Collections.EMPTY_LIST;
      } else {
         var1 = getIMAPFlags(this);
         List var2 = getIMAPFlags(this.saved);
         var1.removeAll(var2);
      }

      return var1;
   }

   List getRemovedFlags() {
      List var1;
      if(this.saved == null) {
         var1 = Collections.EMPTY_LIST;
      } else {
         List var2 = getIMAPFlags(this);
         List var3 = getIMAPFlags(this.saved);
         var3.removeAll(var2);
         var1 = var3;
      }

      return var1;
   }
}

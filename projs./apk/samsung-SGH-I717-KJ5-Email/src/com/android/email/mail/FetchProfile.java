package com.android.email.mail;

import com.android.email.mail.Part;
import java.util.ArrayList;
import java.util.Iterator;

public class FetchProfile extends ArrayList {

   public FetchProfile() {}

   public Part getFirstPart() {
      Iterator var1 = this.iterator();

      Part var3;
      while(true) {
         if(var1.hasNext()) {
            Object var2 = var1.next();
            if(!(var2 instanceof Part)) {
               continue;
            }

            var3 = (Part)var2;
            break;
         }

         var3 = null;
         break;
      }

      return var3;
   }

   public static enum Item {

      // $FF: synthetic field
      private static final FetchProfile.Item[] $VALUES;
      BODY("BODY", 4),
      BODY_SANE("BODY_SANE", 3),
      ENVELOPE("ENVELOPE", 1),
      FLAGS("FLAGS", 0),
      STRUCTURE("STRUCTURE", 2);


      static {
         FetchProfile.Item[] var0 = new FetchProfile.Item[5];
         FetchProfile.Item var1 = FLAGS;
         var0[0] = var1;
         FetchProfile.Item var2 = ENVELOPE;
         var0[1] = var2;
         FetchProfile.Item var3 = STRUCTURE;
         var0[2] = var3;
         FetchProfile.Item var4 = BODY_SANE;
         var0[3] = var4;
         FetchProfile.Item var5 = BODY;
         var0[4] = var5;
         $VALUES = var0;
      }

      private Item(String var1, int var2) {}
   }
}

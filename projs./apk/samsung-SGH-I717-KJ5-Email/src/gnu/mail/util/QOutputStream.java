package gnu.mail.util;

import gnu.mail.util.QPOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class QOutputStream extends QPOutputStream {

   private static final int SPACE = 32;
   private static String TEXT_SPECIALS = "=_?";
   private static final int UNDERSCORE = 95;
   private static String WORD_SPECIALS = "=_?\"#$%&\'(),.:;<>@[\\]^`{|}~";
   private String specials;


   public QOutputStream(OutputStream var1, boolean var2) {
      super(var1, Integer.MAX_VALUE);
      String var3;
      if(var2) {
         var3 = WORD_SPECIALS;
      } else {
         var3 = TEXT_SPECIALS;
      }

      this.specials = var3;
   }

   public static int encodedLength(byte[] var0, boolean var1) {
      int var2 = 0;
      String var3;
      if(var1) {
         var3 = WORD_SPECIALS;
      } else {
         var3 = TEXT_SPECIALS;
      }

      int var4 = 0;

      while(true) {
         int var5 = var0.length;
         if(var2 >= var5) {
            return var4;
         }

         int var6 = var0[var2] & 255;
         if(var6 >= 32 && var6 < 127 && var3.indexOf(var6) < 0) {
            ++var4;
         } else {
            var4 += 3;
         }

         ++var2;
      }
   }

   public void write(int var1) throws IOException {
      int var2 = var1 & 255;
      if(var2 == 32) {
         this.output(95, (boolean)0);
      } else if(var2 >= 32 && var2 < 127 && this.specials.indexOf(var2) < 0) {
         this.output(var2, (boolean)0);
      } else {
         this.output(var2, (boolean)1);
      }
   }
}

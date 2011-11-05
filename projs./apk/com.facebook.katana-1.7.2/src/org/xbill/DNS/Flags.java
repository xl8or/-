package org.xbill.DNS;

import org.xbill.DNS.Mnemonic;

public final class Flags {

   public static final byte AA = 5;
   public static final byte AD = 10;
   public static final byte CD = 11;
   public static final int DO = 32768;
   public static final byte QR = 0;
   public static final byte RA = 8;
   public static final byte RD = 7;
   public static final byte TC = 6;
   private static Mnemonic flags = new Mnemonic("DNS Header Flag", 3);


   static {
      flags.setMaximum(15);
      flags.setPrefix("FLAG");
      flags.setNumericAllowed((boolean)1);
      flags.add(0, "qr");
      flags.add(5, "aa");
      flags.add(6, "tc");
      flags.add(7, "rd");
      flags.add(8, "ra");
      flags.add(10, "ad");
      flags.add(11, "cd");
   }

   private Flags() {}

   public static boolean isFlag(int var0) {
      flags.check(var0);
      boolean var1;
      if((var0 < 1 || var0 > 4) && var0 < 12) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static String string(int var0) {
      return flags.getText(var0);
   }

   public static int value(String var0) {
      return flags.getValue(var0);
   }
}

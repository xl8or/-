package org.xbill.DNS;

import org.xbill.DNS.Mnemonic;

public final class ExtendedFlags {

   public static final int DO = 32768;
   private static Mnemonic extflags = new Mnemonic("EDNS Flag", 3);


   static {
      extflags.setMaximum('\uffff');
      extflags.setPrefix("FLAG");
      extflags.setNumericAllowed((boolean)1);
      extflags.add('\u8000', "do");
   }

   private ExtendedFlags() {}

   public static String string(int var0) {
      return extflags.getText(var0);
   }

   public static int value(String var0) {
      return extflags.getValue(var0);
   }
}

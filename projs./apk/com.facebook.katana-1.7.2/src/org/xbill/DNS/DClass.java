package org.xbill.DNS;

import org.xbill.DNS.InvalidDClassException;
import org.xbill.DNS.Mnemonic;

public final class DClass {

   public static final int ANY = 255;
   public static final int CH = 3;
   public static final int CHAOS = 3;
   public static final int HESIOD = 4;
   public static final int HS = 4;
   public static final int IN = 1;
   public static final int NONE = 254;
   private static Mnemonic classes = new DClass.DClassMnemonic();


   static {
      classes.add(1, "IN");
      classes.add(3, "CH");
      classes.addAlias(3, "CHAOS");
      classes.add(4, "HS");
      classes.addAlias(4, "HESIOD");
      classes.add(254, "NONE");
      classes.add(255, "ANY");
   }

   private DClass() {}

   public static void check(int var0) {
      if(var0 < 0 || var0 > '\uffff') {
         throw new InvalidDClassException(var0);
      }
   }

   public static String string(int var0) {
      return classes.getText(var0);
   }

   public static int value(String var0) {
      return classes.getValue(var0);
   }

   private static class DClassMnemonic extends Mnemonic {

      public DClassMnemonic() {
         super("DClass", 2);
         this.setPrefix("CLASS");
      }

      public void check(int var1) {
         DClass.check(var1);
      }
   }
}

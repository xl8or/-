package org.xbill.DNS;

import org.xbill.DNS.Mnemonic;

public final class Rcode {

   public static final int BADKEY = 17;
   public static final int BADMODE = 19;
   public static final int BADSIG = 16;
   public static final int BADTIME = 18;
   public static final int BADVERS = 16;
   public static final int FORMERR = 1;
   public static final int NOERROR = 0;
   public static final int NOTAUTH = 9;
   public static final int NOTIMP = 4;
   public static final int NOTIMPL = 4;
   public static final int NOTZONE = 10;
   public static final int NXDOMAIN = 3;
   public static final int NXRRSET = 8;
   public static final int REFUSED = 5;
   public static final int SERVFAIL = 2;
   public static final int YXDOMAIN = 6;
   public static final int YXRRSET = 7;
   private static Mnemonic rcodes = new Mnemonic("DNS Rcode", 2);
   private static Mnemonic tsigrcodes = new Mnemonic("TSIG rcode", 2);


   static {
      rcodes.setMaximum(4095);
      rcodes.setPrefix("RESERVED");
      rcodes.setNumericAllowed((boolean)1);
      rcodes.add(0, "NOERROR");
      rcodes.add(1, "FORMERR");
      rcodes.add(2, "SERVFAIL");
      rcodes.add(3, "NXDOMAIN");
      rcodes.add(4, "NOTIMP");
      rcodes.addAlias(4, "NOTIMPL");
      rcodes.add(5, "REFUSED");
      rcodes.add(6, "YXDOMAIN");
      rcodes.add(7, "YXRRSET");
      rcodes.add(8, "NXRRSET");
      rcodes.add(9, "NOTAUTH");
      rcodes.add(10, "NOTZONE");
      rcodes.add(16, "BADVERS");
      tsigrcodes.setMaximum('\uffff');
      tsigrcodes.setPrefix("RESERVED");
      tsigrcodes.setNumericAllowed((boolean)1);
      Mnemonic var0 = tsigrcodes;
      Mnemonic var1 = rcodes;
      var0.addAll(var1);
      tsigrcodes.add(16, "BADSIG");
      tsigrcodes.add(17, "BADKEY");
      tsigrcodes.add(18, "BADTIME");
      tsigrcodes.add(19, "BADMODE");
   }

   private Rcode() {}

   public static String TSIGstring(int var0) {
      return tsigrcodes.getText(var0);
   }

   public static String string(int var0) {
      return rcodes.getText(var0);
   }

   public static int value(String var0) {
      return rcodes.getValue(var0);
   }
}

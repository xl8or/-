package org.xbill.DNS;

import java.security.PrivateKey;
import java.util.Date;
import org.xbill.DNS.DNSSEC;
import org.xbill.DNS.KEYRecord;
import org.xbill.DNS.Message;
import org.xbill.DNS.Options;
import org.xbill.DNS.Record;
import org.xbill.DNS.SIGRecord;

public class SIG0 {

   private static final short VALIDITY = 300;


   private SIG0() {}

   public static void signMessage(Message var0, KEYRecord var1, PrivateKey var2, SIGRecord var3) throws DNSSEC.DNSSECException {
      int var4 = Options.intValue("sig0validity");
      if(var4 < 0) {
         var4 = 300;
      }

      long var5 = System.currentTimeMillis();
      Date var7 = new Date(var5);
      long var8 = (long)(var4 * 1000);
      long var10 = var5 + var8;
      Date var12 = new Date(var10);
      SIGRecord var17 = DNSSEC.signMessage(var0, var3, var1, var2, var7, var12);
      var0.addRecord(var17, 3);
   }

   public static void verifyMessage(Message var0, byte[] var1, KEYRecord var2, SIGRecord var3) throws DNSSEC.DNSSECException {
      Record[] var4 = var0.getSectionArray(3);
      int var5 = 0;

      SIGRecord var7;
      while(true) {
         int var6 = var4.length;
         if(var5 >= var6) {
            var7 = null;
            break;
         }

         if(var4[var5].getType() == 24 && ((SIGRecord)var4[var5]).getTypeCovered() == 0) {
            var7 = (SIGRecord)var4[var5];
            break;
         }

         ++var5;
      }

      DNSSEC.verifyMessage(var0, var1, var7, var3, var2);
   }
}

package org.xbill.DNS.tests;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TSIG;
import org.xbill.DNS.ZoneTransferIn;

public class xfrin {

   public xfrin() {}

   public static void main(String[] var0) throws Exception {
      byte var1 = 0;
      int var2 = 53;
      int var3 = -1;
      TSIG var4 = null;
      int var5 = 0;

      while(true) {
         int var6 = var0.length;
         if(var5 >= var6) {
            break;
         }

         int var8;
         int var9;
         TSIG var10;
         boolean var12;
         byte var13;
         if(var0[var5].equals("-i")) {
            ++var5;
            int var7 = Integer.parseInt(var0[var5]);
            if(var7 < 0) {
               usage("invalid serial number");
               var8 = var7;
               var9 = var2;
               var10 = var4;
               var12 = false;
               var13 = var1;
            } else {
               var8 = var7;
               var9 = var2;
               var10 = var4;
               var12 = false;
               var13 = var1;
            }
         } else if(var0[var5].equals("-k")) {
            ++var5;
            String var17 = var0[var5];
            int var18 = var17.indexOf(47);
            if(var18 < 0) {
               usage("invalid key");
            }

            String var19 = var17.substring(0, var18);
            int var20 = var18 + 1;
            String var21 = var17.substring(var20);
            TSIG var22 = new TSIG(var19, var21);
            var9 = var2;
            var8 = var3;
            var10 = var22;
            var12 = false;
            var13 = var1;
         } else if(var0[var5].equals("-s")) {
            ++var5;
            String var24 = var0[var5];
            var9 = var2;
            var8 = var3;
            var10 = var4;
            var13 = var1;
         } else if(var0[var5].equals("-p")) {
            ++var5;
            var9 = Integer.parseInt(var0[var5]);
            if(var9 >= 0 && var9 <= '\uffff') {
               var8 = var3;
               var10 = var4;
               var12 = false;
               var13 = var1;
            } else {
               usage("invalid port");
               var8 = var3;
               var10 = var4;
               var12 = false;
               var13 = var1;
            }
         } else if(var0[var5].equals("-f")) {
            var9 = var2;
            var12 = false;
            var8 = var3;
            var13 = 1;
            var10 = var4;
         } else {
            if(!var0[var5].startsWith("-")) {
               break;
            }

            usage("invalid option");
            var9 = var2;
            var8 = var3;
            var10 = var4;
            var12 = false;
            var13 = var1;
         }

         int var14 = var5 + 1;
         var3 = var8;
         var4 = var10;
         var2 = var9;
         var1 = var13;
      }

      int var28 = var0.length;
      if(var5 >= var28) {
         usage("no zone name specified");
      }

      Name var29 = Name.fromString(var0[var5]);
      if(true) {
         Lookup var30 = new Lookup(var29, 2);
         Record[] var31 = var30.run();
         if(var31 == null) {
            PrintStream var32 = System.out;
            StringBuilder var33 = (new StringBuilder()).append("failed to look up NS record: ");
            String var34 = var30.getErrorString();
            String var35 = var33.append(var34).toString();
            var32.println(var35);
            System.exit(1);
         }

         String var36 = var31[0].rdataToString();
         PrintStream var37 = System.out;
         String var38 = "sending to server \'" + var36 + "\'";
         var37.println(var38);
      }

      Object var39 = null;
      ZoneTransferIn var42;
      if(var3 >= 0) {
         long var40 = (long)var3;
         var42 = ZoneTransferIn.newIXFR(var29, var40, (boolean)var1, (String)var39, var2, var4);
      } else {
         var42 = ZoneTransferIn.newAXFR(var29, (String)var39, var2, var4);
      }

      List var43 = var42.run();
      Iterator var44;
      if(var42.isAXFR()) {
         if(var3 >= 0) {
            System.out.println("AXFR-like IXFR response");
         } else {
            System.out.println("AXFR response");
         }

         var44 = var43.iterator();

         while(var44.hasNext()) {
            PrintStream var45 = System.out;
            Object var46 = var44.next();
            var45.println(var46);
         }

      } else if(!var42.isIXFR()) {
         if(var42.isCurrent()) {
            System.out.println("up to date");
         }
      } else {
         System.out.println("IXFR response");
         var44 = var43.iterator();

         while(var44.hasNext()) {
            ZoneTransferIn.Delta var63 = (ZoneTransferIn.Delta)var44.next();
            PrintStream var47 = System.out;
            StringBuilder var48 = (new StringBuilder()).append("delta from ");
            long var49 = var63.start;
            StringBuilder var51 = var48.append(var49).append(" to ");
            long var52 = var63.end;
            String var54 = var51.append(var52).toString();
            var47.println(var54);
            System.out.println("deletes");
            Iterator var55 = var63.deletes.iterator();

            while(var55.hasNext()) {
               PrintStream var56 = System.out;
               Object var57 = var55.next();
               var56.println(var57);
            }

            System.out.println("adds");
            Iterator var58 = var63.adds.iterator();

            while(var58.hasNext()) {
               PrintStream var59 = System.out;
               Object var60 = var58.next();
               var59.println(var60);
            }
         }

      }
   }

   private static void usage(String var0) {
      PrintStream var1 = System.out;
      String var2 = "Error: " + var0;
      var1.println(var2);
      System.out.println("usage: xfrin [-i serial] [-k keyname/secret] [-s server] [-p port] [-f] zone");
      System.exit(1);
   }
}
